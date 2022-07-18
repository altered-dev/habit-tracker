package me.altered.habittracker.ui.habit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.altered.habittracker.R
import me.altered.habittracker.data.Data

class HabitColorAdapter(
    private val onBind: (view: View, position: Int) -> Unit
) : RecyclerView.Adapter<HabitColorAdapter.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.habit_color_button, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        onBind(holder.view, position)

    override fun getItemCount() = Data.colors.size
}