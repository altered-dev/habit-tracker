package me.altered.habittracker.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import me.altered.habittracker.R
import me.altered.habittracker.data.Data
import me.altered.habittracker.data.Habit
import me.altered.habittracker.databinding.HabitBinding
import me.altered.habittracker.ui.habit.HabitFragment

class HabitListAdapter(
    val data: LiveData<List<Habit>>,
    val type: Habit.Type,
) : RecyclerView.Adapter<HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HabitViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.habit, parent, false))

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) =
        holder.bind(data.value!![position], position)

    override fun getItemCount() = data.value?.size ?: 0
}