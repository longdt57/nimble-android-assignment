package co.nimble.lee.assignment.ui.screens.ext

import android.widget.ImageView
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.ui.screens.util.UrlUtil
import com.bumptech.glide.Glide

fun ImageView.loadSurveyCoverImage(url: String) {
    Glide.with(this)
        .load(UrlUtil.getUrlHighResolution(url))
        .placeholder(R.drawable.overlay)
        .error(R.drawable.nb_background)
        .into(this)
}
