package me.altered.habittracker.ui.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.altered.habittracker.R
import me.altered.habittracker.data.Habit
import me.altered.habittracker.ui.list.HabitViewHolder

class HabitFilterAdapter(
    private val habits: List<Habit>,
    private val onClick: () -> Unit = {},
) : RecyclerView.Adapter<HabitViewHolder>() {
    private var filteredData = habits

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HabitViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.habit, parent, false))

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) =
        holder.bind(filteredData[position], position, onClick)

    override fun getItemCount() = filteredData.size

    @SuppressLint("NotifyDataSetChanged")
    fun setFilter(filter: String) {
        if (filter.isBlank()) {
            filteredData = habits
            return
        }
        val words = filter.split(' ')
        filteredData = habits.filter { words.any(it.name::contains) }
        notifyDataSetChanged()
    }
}