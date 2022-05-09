package co.nimble.lee.assignment.ui

import android.content.Context
import co.nimble.lee.assignment.R

fun Throwable.userReadableMessage(context: Context): String {
    // TODO implement user readable message
    return context.getString(R.string.error_generic)
}
