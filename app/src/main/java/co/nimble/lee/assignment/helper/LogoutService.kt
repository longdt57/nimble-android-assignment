package co.nimble.lee.assignment.helper

import android.app.Service
import android.content.Intent
import android.os.IBinder
import co.nimble.lee.assignment.domain.usecase.LogOutUseCase
import co.nimble.lee.assignment.ui.screens.auth.AuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LogoutService @Inject constructor(
    private val logOutUseCase: LogOutUseCase
) : Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (job.isActive.not()) {
            scope.launch {
                logOutUseCase.invoke(Unit)
            }
            openLoginScreen()
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun openLoginScreen() {
        val intent = Intent(this, AuthenticationActivity::class.java).apply {
            this.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
            )
        }
        startActivity(intent)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        job.complete()
        super.onDestroy()
    }
}
