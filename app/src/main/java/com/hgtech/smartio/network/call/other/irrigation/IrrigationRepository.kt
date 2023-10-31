package com.hgtech.smartio.network.call.other.irrigation

import com.hgtech.smartio.network.call.base.BaseRepository
import com.hgtech.smartio.network.factory.ApiFactory
import com.hgtech.smartio.network.model.response.irrigation.IrrigationResponse

class IrrigationRepository : BaseRepository() {

    suspend fun get(userId: String): IrrigationResponse? {
        return safeApiCall(
            call = { ApiFactory.irrigationApi.get(userId) },
            errorMessage = "Something went wrong when trying to get Irrigation for: $userId"
        )
    }
}