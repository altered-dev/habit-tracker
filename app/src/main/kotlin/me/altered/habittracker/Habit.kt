package me.altered.habittracker

import me.altered.habittracker.R.drawable.*

data class Habit(
    val name: String = "",
    val color: Int = Data.colors[0],
    val description: String = "",
    val priority: Priority = Priority.LOW,
    val type: Type = Type.GOOD,
    val frequency: String = "",
    val doneCount: Int = 0,
) {
    enum class Priority(val iconId: Int, val displayName: String) {
        LOW(ic_outline_looks_24, "Низкий"),
        MEDIUM(ic_outline_label_important_24, "Средний"),
        HIGH(ic_outline_error_outline_24, "Высокий");

        override fun toString() = displayName

        companion object : List<Priority> by values().toList()
    }

    enum class Type(val iconId: Int, val displayName: String, val buttonId: Int) {
        BAD(ic_outline_thumb_down_24, "Плохая", R.id.bad),
        GOOD(ic_outline_thumb_up_24, "Хорошая", R.id.good);

        override fun toString() = displayName

        companion object : List<Type> by values().toList() {
            fun findByButtonId(id: Int) = find { it.buttonId == id }
        }
    }
}