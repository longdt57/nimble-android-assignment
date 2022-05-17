package co.nimble.lee.assignment.ui.screens.detail

import android.os.Parcelable
import co.nimble.lee.assignment.model.SurveyUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurveyDetailBundle(
    val model: SurveyUiModel
) : Parcelable
