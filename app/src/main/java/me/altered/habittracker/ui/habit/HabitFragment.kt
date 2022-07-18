package me.altered.habittracker.ui.habit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import me.altered.habittracker.R
import me.altered.habittracker.data.Data
import me.altered.habittracker.data.Habit
import me.altered.habittracker.databinding.FragmentHabitBinding
import me.altered.habittracker.setOnItemSelectedListener
import me.altered.habittracker.ui.list.HabitListViewModel

class HabitFragment(
    private val habitId: Int,
) : BottomSheetDialogFragment() {
    private val model by activityViewModels<HabitListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_habit, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val habit = model.getById(habitId) ?: return dismiss()
        FragmentHabitBinding.bind(view).run {
            name.setText(habit.name)
            frequency.setText(habit.frequency)
            description.setText(habit.description)

            root.setBackgroundResource(habit.color)
            var currentColorPos = Data.colors.indexOf(habit.color)

            type.check(habit.type.buttonId)
            type.setOnCheckedChangeListener { _, id ->
                Habit.Type.findByButtonId(id) ?: type.clearCheck()
            }

            priority.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                Habit.Priority,
            ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            priority.setOnItemSelectedListener { _, _, position, _ ->
                priorityIcon.setImageResource(Habit.Priority[position].iconId)
            }

            doneCount.setText(habit.doneCount.toString())
            plus.setOnClickListener {
                doneCount.setText(
                    doneCount.text.toString()
                        .ifBlank { "0" }.toInt().plus(1).toString()
                )
            }
            minus.setOnClickListener {
                doneCount.setText(
                    doneCount.text.toString()
                        .ifBlank { "0" }.toInt().minus(1).coerceAtLeast(0).toString()
                )
            }

            colors.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            colors.adapter = HabitColorAdapter { view, position ->
                val button = view as? MaterialButton ?: return@HabitColorAdapter
                button.setBackgroundColor(button.context.getColor(Data.colors[position]))
                button.setStrokeWidthResource(
                    if (position == currentColorPos) R.dimen.color_button_stroke_width
                    else R.dimen.zero
                )
                button.setOnClickListener {
                    (colors.layoutManager as LinearLayoutManager).getChildAt(currentColorPos).let {
                        (it as? MaterialButton)?.setStrokeWidthResource(R.dimen.zero)
                    }
                    currentColorPos = position
                    button.setStrokeWidthResource(R.dimen.color_button_stroke_width)
                    root.setBackgroundResource(Data.colors[currentColorPos])
                }
            }

            save.setOnClickListener {
                model update Habit(
                    id = habit.id,
                    name = name.text.toString(),
                    color = Data.colors[currentColorPos],
                    description = description.text.toString(),
                    priority = Habit.Priority[priority.selectedItemPosition],
                    type = Habit.Type.findByButtonId(type.checkedRadioButtonId) ?: Habit.Type.GOOD,
                    frequency = frequency.text.toString(),
                    doneCount = doneCount.text.toString().toInt(),
                )
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(id: Int) = HabitFragment(id)
        const val TAG = "HabitFragment"
    }
}