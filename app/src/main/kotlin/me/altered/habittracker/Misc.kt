package me.altered.habittracker

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

inline fun <reified A : Activity> Activity.startActivity(extra: Intent.() -> Unit = {}) =
    startActivity(Intent(this, A::class.java).apply(extra))

inline fun Spinner.setOnItemSelectedListener(
    crossinline listener: (
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) -> Unit
) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            p0: AdapterView<*>?,
            p1: View?,
            p2: Int,
            p3: Long
        ) = listener(p0, p1, p2, p3)

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
}