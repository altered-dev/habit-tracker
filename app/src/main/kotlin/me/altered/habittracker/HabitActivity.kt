@file:SuppressLint("SetTextI18n")
package me.altered.habittracker

import android.R.layout.simple_spinner_dropdown_item
import android.R.layout.simple_spinner_item
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import me.altered.habittracker.databinding.ActivityHabitBinding
import me.altered.habittracker.databinding.ColorItemBinding

class HabitActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHabitBinding.inflate(layoutInflater) }
    private val position by lazy { intent.getIntExtra("position", -1) }

    private lateinit var habit: Habit
    private lateinit var currentPriority: Habit.Priority
    private lateinit var currentType: Habit.Type
    private var currentDoneCount = -1
    private var currentColor = -1

    override fun onCreate(
        savedInstanceState: Bundle?,
    ) = binding.run {
        super.onCreate(savedInstanceState)
        setContentView(root)
        habit = Data.habits.getOrElse(position) { return }
        currentPriority = habit.priority
        currentType = habit.type
        currentDoneCount = habit.doneCount

        name.setText(habit.name)
        frequency.setText(habit.frequency)
        description.setText(habit.description)
        priorityIcon.setImageDrawable(currentPriority.iconId.asDrawable())

        priority.adapter = ArrayAdapter(
            this@HabitActivity,
            simple_spinner_item,
            Habit.Priority
        ).apply { setDropDownViewResource(simple_spinner_dropdown_item) }
        priority.setSelection(currentPriority.ordinal, false)
        priority.setOnItemSelectedListener { _, _, position, _ ->
            currentPriority = Habit.Priority[position]
            binding.priorityIcon.setImageDrawable(currentPriority.iconId.asDrawable())
        }

        type.check(currentType.buttonId)
        type.setOnCheckedChangeListener { _, id ->
            currentType = Habit.Type.findByButtonId(id)
                ?: return@setOnCheckedChangeListener type.clearCheck()
        }

        doneCount.text = currentDoneCount.toString()
        increase.setOnClickListener { doneCount.text = (++currentDoneCount).toString() }
        decrease.setOnClickListener { doneCount.text = (--currentDoneCount).toString() }

        currentColor = habit.color
        applyColor(getColor(currentColor))

        back.setOnClickListener { onBackPressed() }

        colors.layoutManager = LinearLayoutManager(this@HabitActivity, HORIZONTAL, false)
        colors.adapter = Adapter(Data.colors, R.layout.color_item, ::bindColor)

        save.setOnClickListener {
            Data.habits[position] = Habit(
                name.text.toString(),
                currentColor,
                description.text.toString(),
                currentPriority,
                currentType,
                frequency.text.toString(),
                currentDoneCount,
            )
            onBackPressed()
        }
    }

    private fun bindColor(
        view: View,
        color: Int,
        index: Int
    ) = ColorItemBinding.bind(view).run {
        colorButton.setBackgroundColor(getColor(color))
        colorButton.setOnClickListener {
            currentColor = color
            applyColor(getColor(color))
        }
    }

    private fun applyColor(color: Int) {
        window.statusBarColor = color
        window.decorView.setBackgroundColor(color)
        binding.back.setBackgroundColor(color)
        val rgb = Color.valueOf(color)
        val hsv = floatArrayOf(0F, 0F, 0F)
        Color.colorToHSV(color, hsv)
        binding.colorText.text = """
            Цвет:
            RGB(${rgb.red()}, ${rgb.green()}, ${rgb.blue()})
            HSV(${hsv[0]}, ${hsv[1]}, ${hsv[2]})
        """.trimIndent()
    }

    private fun Int.asDrawable() = ResourcesCompat.getDrawable(resources, this, theme)
}