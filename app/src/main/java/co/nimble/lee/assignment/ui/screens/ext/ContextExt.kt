package co.nimble.lee.assignment.ui.screens.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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

fun View.hideKeyboard(context: Context) {
    val imm: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
