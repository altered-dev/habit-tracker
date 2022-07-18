package me.altered.habittracker.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import me.altered.habittracker.R
import me.altered.habittracker.data.Habit
import me.altered.habittracker.databinding.HabitListFragmentBinding
import me.altered.habittracker.ui.filter.FilterFragment
import me.altered.habittracker.ui.habit.HabitFragment

class HabitListFragment : Fragment() {
    private val model by activityViewModels<HabitListViewModel>()
    val listPages = Habit.Type.map(::HabitListPageFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.habit_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HabitListFragmentBinding.bind(view).run {
            pages.adapter = object : FragmentStateAdapter(this@HabitListFragment) {
                override fun getItemCount() = Habit.Type.size
                override fun createFragment(position: Int) = listPages[position]
            }

            TabLayoutMediator(tabs, pages) { tab, position ->
                Habit.Type[position].let {
                    tab.text = it.displayName
                    tab.setIcon(it.iconId)
                }
            }.attach()

            add.setOnClickListener {
                val type = Habit.Type[tabs.selectedTabPosition]
                val habit = Habit(type = type)
                model += habit
                HabitFragment(habit.id).show(parentFragmentManager, HabitFragment.TAG)
            }
            filter.setOnClickListener {
                FilterFragment().show(parentFragmentManager, FilterFragment.TAG)
            }
        }
    }

    companion object {
        fun newInstance() = HabitListFragment()
    }
}