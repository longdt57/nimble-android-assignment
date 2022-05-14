package co.nimble.lee.assignment.ui.base

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.WindowManager

fun Activity.setLightStatusBar() {
    window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
    window?.statusBarColor = Color.TRANSPARENT
}

fun Activity.setWindowFlag(bits: Int, on: Boolean) {
    window?.let { win ->
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
}