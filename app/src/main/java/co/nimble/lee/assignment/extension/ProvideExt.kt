package co.nimble.lee.assignment.extension

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs

@MainThread
inline fun <reified N : NavArgs> Fragment.provideNavArgs(): Lazy<N> = OverridableLazy(navArgs())
