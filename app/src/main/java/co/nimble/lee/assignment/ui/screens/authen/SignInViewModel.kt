package co.nimble.lee.assignment.ui.screens.authen

import co.nimble.lee.assignment.ui.base.BaseViewModel
import co.nimble.lee.assignment.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(dispatchers: DispatchersProvider) : BaseViewModel(dispatchers)
