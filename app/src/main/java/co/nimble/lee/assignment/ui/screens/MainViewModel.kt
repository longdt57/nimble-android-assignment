package co.nimble.lee.assignment.ui.screens

import co.nimble.lee.assignment.ui.base.BaseViewModel
import co.nimble.lee.assignment.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(dispatchers: DispatchersProvider) : BaseViewModel(dispatchers)
