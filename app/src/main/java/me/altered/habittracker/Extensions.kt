package me.altered.habittracker

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.lifecycle.LiveData

inline fun Spinner.setOnItemSelectedListener(
    crossinline listener: (AdapterView<*>?, View?, Int, Long) -> Unit
) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            p0: AdapterView<*>?,
            p1: View?,
            p2: Int,
            p3: Long
        ) = listener(p0, p1, p2, p3)

        override fun onNothingSelected(p0: AdapterView<*>?) = Unit
    }
}

tailrec fun Context.getActivity(): Activity? = this as? Activity
    ?: (this as? ContextWrapper)?.baseContext?.getActivity()

operator fun <E> LiveData<out List<E>>.get(index: Int) = value?.get(index)