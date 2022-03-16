package me.altered.habittracker

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import me.altered.habittracker.databinding.HabitBinding
import me.altered.habittracker.databinding.HabitListBinding

class HabitList : AppCompatActivity() {
    private val binding by lazy { HabitListBinding.inflate(layoutInflater) }
    private var lastPressedIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) = binding.run {
        super.onCreate(savedInstanceState)
        setContentView(root)
        habits.layoutManager = LinearLayoutManager(this@HabitList, VERTICAL, false)
        habits.adapter = Adapter(Data.habits, R.layout.habit, ::bindHabit)
        if (Data.habits.isNotEmpty()) emptyHint.visibility = View.GONE

        addHabit.setOnClickListener {
            emptyHint.visibility = View.GONE
            lastPressedIndex = Data.habits.size
            Data.habits += Habit()
            habits.adapter!!.notifyItemInserted(lastPressedIndex)
            startActivity<HabitActivity> { putExtra("position", lastPressedIndex) }
        }
    }

    private fun bindHabit(
        view: View,
        habit: Habit,
        position: Int,
    ) = HabitBinding.bind(view).run {
        name.text = habit.name.ifBlank { "Без названия" }
        description.text = habit.description.ifBlank { "Без описания" }
        frequency.text = habit.frequency.ifBlank { "Не указано" }
        doneCount.text = "Сделано ${habit.doneCount} раз(а)"

        priority.setImageDrawable(habit.priority.iconId.asDrawable())
        type.setImageDrawable(habit.type.iconId.asDrawable())

        card.setCardBackgroundColor(getColor(habit.color))
        if (habit.color != Data.colors[0]) card.strokeWidth = 0
        card.setOnClickListener {
            lastPressedIndex = position
            startActivity<HabitActivity> { putExtra("position", position) }
        }
    }

    override fun onResume() = binding.run {
        if (lastPressedIndex != -1) habits.adapter!!.notifyItemChanged(lastPressedIndex)
        super.onResume()
    }

    private fun Int.asDrawable() = ResourcesCompat.getDrawable(resources, this, theme)
}