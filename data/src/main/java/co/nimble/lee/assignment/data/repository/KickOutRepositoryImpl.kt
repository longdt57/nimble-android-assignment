package co.nimble.lee.assignment.data.repository

import co.nimble.lee.assignment.data.repository.helper.LocalDataHelper
import co.nimble.lee.assignment.domain.repository.KickOutRepository
import javax.inject.Inject

class KickOutRepositoryImpl @Inject internal constructor(
    private val localCacheHelper: LocalDataHelper
) : KickOutRepository {

    override suspend fun kickOut() {
        localCacheHelper.clearLocalData()
    }

}
