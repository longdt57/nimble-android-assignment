package co.nimble.lee.assignment.ui.screens.ext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import co.nimble.lee.assignment.ui.screens.MainActivity
import co.nimble.lee.assignment.ui.screens.auth.AuthenticationActivity

fun Context.navigateToMain() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
}

fun Context.navigateToAuthentication(bundle: Bundle? = null) {
    val intent = Intent(this, AuthenticationActivity::class.java).apply {
        bundle?.let { putExtras(it) }
    }
    startActivity(intent)
}

fun Context.getScreenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

fun Context.getScreenHeight(): Int {
    return resources.displayMetrics.heightPixels
}