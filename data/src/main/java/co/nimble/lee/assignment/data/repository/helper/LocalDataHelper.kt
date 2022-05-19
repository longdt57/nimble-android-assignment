package co.nimble.lee.assignment.data.repository.helper

import co.nimble.lee.assignment.data.database.AppDatabase
import co.nimble.lee.assignment.data.storage.local.TokenStorage
import javax.inject.Inject

internal class LocalDataHelper @Inject constructor(
    private val appDatabase: AppDatabase,
    private val tokenStorage: TokenStorage
) {

    fun clearLocalData() {
        tokenStorage.clearTokenInfo()
        appDatabase.clearAllTables()
    }
}
