package co.nimble.lee.assignment.ui.screens.ext

import android.widget.ImageView
import co.nimble.lee.assignment.R
import com.bumptech.glide.Glide

fun ImageView.loadSurveyCoverImage(url: String) {
    Glide.with(this)
        .load(url.getUrlHighResolution())
        .placeholder(R.drawable.nb_background)
        .error(R.drawable.nb_background)
        .into(this)
}