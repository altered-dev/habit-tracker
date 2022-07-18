package me.altered.habittracker.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    fun getAll(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE id == :id")
    fun getById(id: Int): LiveData<Habit>

    @Query("SELECT * FROM habit WHERE type == :type")
    fun getByType(type: Habit.Type): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE name LIKE :name")
    fun filterByName(name: String): LiveData<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    operator fun plusAssign(habit: Habit)

    @Delete
    operator fun minusAssign(habit: Habit)

    @Update
    infix fun update(habit: Habit)
}