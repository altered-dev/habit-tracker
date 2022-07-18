package me.altered.habittracker.ui.list

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import me.altered.habittracker.data.Habit
import me.altered.habittracker.data.HabitDatabase

class HabitListViewModel : ViewModel() {
    private val database by lazy {
        Room.databaseBuilder(application, HabitDatabase::class.java, "habit_database")
            .allowMainThreadQueries()
            .build()
    }

    private val dao by lazy(database::habitDao)

    val habits get() = dao.getAll()

    operator fun get(type: Habit.Type) = dao.getByType(type)

//    fun getById(id: Int) = habits.value?.find { it.id == id }

    fun getById(id: Int) = dao.getById(id).value

    infix fun update(habit: Habit) = dao update habit

    operator fun plusAssign(habit: Habit) { dao += habit }

    operator fun minusAssign(habit: Habit) { dao -= habit }

    fun getSize(type: Habit.Type) = get(type).value?.size ?: 0

    companion object {
        lateinit var application: Application
    }
}