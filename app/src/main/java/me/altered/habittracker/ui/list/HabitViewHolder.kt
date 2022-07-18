package me.altered.habittracker.ui.list

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import me.altered.habittracker.data.Data
import me.altered.habittracker.data.Habit
import me.altered.habittracker.databinding.HabitBinding
import me.altered.habittracker.getActivity
import me.altered.habittracker.ui.habit.HabitFragment

class HabitViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(habit: Habit, position: Int, onClick: () -> Unit = {}) = HabitBinding.bind(view).run {
        name.text = habit.name.ifBlank { "Без названия" }
        frequency.text =
            "${habit.frequency.ifBlank { "Частота не указана" }} • выполнено ${habit.doneCount} раз(а)"
        description.text = habit.description.ifBlank { "Без описания" }
        priority.setImageDrawable(
            AppCompatResources.getDrawable(view.context, habit.priority.iconId)
        )
        card.setCardBackgroundColor(view.context.getColor(habit.color))
        if (habit.color != Data.colors[0]) card.strokeWidth = 0
        card.setOnClickListener {
            onClick()
            HabitFragment(position).show(
                (it.context.getActivity() as FragmentActivity).supportFragmentManager,
                HabitFragment.TAG
            )
        }
    }
}