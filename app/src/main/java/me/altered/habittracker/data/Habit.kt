package me.altered.habittracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.altered.habittracker.R
import me.altered.habittracker.R.drawable.*
import java.time.LocalDateTime

@Entity
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = idCounter++,
    val name: String = "",
    val color: Int = Data.colors[0],
    val description: String = "",
    val priority: Priority = Priority.LOW,
    val type: Type = Type.GOOD,
    val frequency: String = "",
    val doneCount: Int = 0,
    val dateCreated: LocalDateTime = LocalDateTime.now()
) {
    enum class Priority(val iconId: Int, val displayName: String) {
        LOW(ic_outline_looks_24, "Незначительная"),
        MEDIUM(ic_outline_label_important_24, "Важная"),
        HIGH(ic_outline_error_outline_24, "Необходимая");

        override fun toString() = displayName

        companion object : List<Priority> by values().toList()
    }

    enum class Type(val iconId: Int, val displayName: String, val buttonId: Int) {
        GOOD(ic_outline_thumb_up_24, "Хорошая", R.id.good),
        BAD(ic_outline_thumb_down_24, "Плохая", R.id.bad);

        override fun toString() = displayName

        companion object : List<Type> by values().toList() {
            fun findByButtonId(id: Int) = find { it.buttonId == id }
        }
    }

    private companion object {
        var idCounter = 0
    }
}