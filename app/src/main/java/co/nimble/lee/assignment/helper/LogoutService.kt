package co.nimble.lee.assignment.helper

import android.app.Service
import android.content.Intent
import android.os.IBinder
import co.nimble.lee.assignment.domain.repository.KickOutRepository
import co.nimble.lee.assignment.ui.screens.auth.AuthenticationActivity
import co.nimble.lee.assignment.ui.screens.ext.orFalse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LogoutService : Service() {

    @Inject
    lateinit var kickOutRepository: KickOutRepository

    private var job: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (job?.isActive.orFalse().not()) {
            job = scope.launch {
                kickOutRepository.kickOut()
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
        job = null
        super.onDestroy()
    }
}
