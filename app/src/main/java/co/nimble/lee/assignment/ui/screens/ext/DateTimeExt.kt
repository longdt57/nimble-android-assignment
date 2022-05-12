package co.nimble.lee.assignment.ui.screens.ext

import android.text.format.DateFormat.*

private const val DATE_FORMAT_EEMMDD = "EEEE, MMM dd"
fun getDateTimeEEMMdd(time: Long): CharSequence {
    return format(DATE_FORMAT_EEMMDD, time)
}