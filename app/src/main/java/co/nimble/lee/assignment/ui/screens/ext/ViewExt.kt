package co.nimble.lee.assignment.ui.screens.ext

import android.view.View

fun View.setOnSingleClickListener(action: (View) -> Unit) {
    setOnClickListener { view ->
        view.isClickable = false
        action(view)
        view.postDelayed(
            {
                view.isClickable = true
            },
            300L
        )
    }
}
