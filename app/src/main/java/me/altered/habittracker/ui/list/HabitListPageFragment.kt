package me.altered.habittracker.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.altered.habittracker.R
import me.altered.habittracker.data.Habit
import me.altered.habittracker.databinding.FragmentHabitListPageBinding

class HabitListPageFragment(val type: Habit.Type) : Fragment() {
    private val model by activityViewModels<HabitListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_habit_list_page, container, false)

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentHabitListPageBinding.bind(view).run {
            habitList.layoutManager = LinearLayoutManager(view.context)
            val data = model[type]
            habitList.adapter = HabitListAdapter(data, type)
            data.observe(viewLifecycleOwner) {
                habitList.adapter!!.notifyDataSetChanged()
            }
        }
    }
}