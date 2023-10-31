package com.hgtech.smartio.network.call.other.values

import com.hgtech.smartio.network.call.base.BaseRepository
import com.hgtech.smartio.network.factory.ApiFactory
import com.hgtech.smartio.network.model.response.values.ValuesResponse

class ValuesRepository : BaseRepository() {

    suspend fun get(id: String): ValuesResponse? {
        return safeApiCall(
            call = { ApiFactory.valuesApi.get(id) },
            errorMessage = "Something went wrong when trying to get Sensor Values for: $id"
        )
    }
}