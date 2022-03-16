package me.altered.habittracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Adapter<T>(
    private val values: List<T>,
    private val layoutId: Int,
    private val onBind: (view: View, value: T, position: Int) -> Unit,
) : RecyclerView.Adapter<Adapter<T>.ViewHolder>() {

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(value: T, position: Int) = onBind(view, value, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(values[position], position)

    override fun getItemCount() = values.size
}