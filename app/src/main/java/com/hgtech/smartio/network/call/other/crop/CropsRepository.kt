package com.hgtech.smartio.network.call.other.crop

import com.hgtech.smartio.network.call.base.BaseRepository
import com.hgtech.smartio.network.call.other.user.UserRepository
import com.hgtech.smartio.network.factory.ApiFactory.cropsApi
import com.hgtech.smartio.network.model.request.crop.AddCropRequest
import com.hgtech.smartio.network.model.response.crop.AddCropResponse
import com.hgtech.smartio.network.model.response.crop.AllCropsResponse

class CropsRepository : BaseRepository() {

    val cropTypeRepository by lazy {
        CropTypeRepository()
    }

    val userRepository: UserRepository by lazy {
        UserRepository()
    }

    suspend fun getAll(): AllCropsResponse? {
        return safeApiCall(
            call = { cropsApi.getAll() },
            errorMessage = "Something went wrong when trying to get All Crops"
        )
    }

    suspend fun insert(typeId: String, name: String): AddCropResponse? {
        return safeApiCall(
            call = { cropsApi.insert(AddCropRequest(typeId, name)) },
            errorMessage = "Something went wrong when trying to Create Crop: $name"
        )
    }
}