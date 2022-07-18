package me.altered.habittracker.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.altered.habittracker.R
import me.altered.habittracker.data.Habit
import me.altered.habittracker.databinding.FragmentFilterBinding
import me.altered.habittracker.ui.list.HabitListViewModel

class FilterFragment : BottomSheetDialogFragment() {
    val vm by activityViewModels<HabitListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_filter, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentFilterBinding.bind(view).run {
            filteredHabits.layoutManager = LinearLayoutManager(view.context)
            val adapter =
                HabitFilterAdapter(
                    vm[Habit.Type.GOOD].value!! + vm[Habit.Type.BAD].value!!,
                    this@FilterFragment::dismiss,
                )
            filteredHabits.adapter = adapter
            filterInput.addTextChangedListener(onTextChanged = { text, _, _, _ ->
                adapter.setFilter(text.toString())
            })
        }
    }

    companion object {
        const val TAG = "FilterFragment"

        fun newInstance() = FilterFragment()
    }
}