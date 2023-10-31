package com.hgtech.smartio.network.call.other.crop

import com.hgtech.smartio.network.call.base.BaseRepository
import com.hgtech.smartio.network.factory.ApiFactory.cropsTypeApi
import com.hgtech.smartio.network.model.request.crop.type.AddCropTypeRequest
import com.hgtech.smartio.network.model.response.crop.type.CropTypeListResponse
import com.hgtech.smartio.network.model.response.crop.type.CropTypeResponse

class CropTypeRepository : BaseRepository() {

    suspend fun listAll(): CropTypeListResponse? {
        return safeApiCall(
            call = { cropsTypeApi.listAll() },
            errorMessage = "Something went wrong when trying to get All Crop Type"
        )
    }

    suspend fun insert(type: String): CropTypeResponse? {
        return safeApiCall(
            call = { cropsTypeApi.insert(AddCropTypeRequest(type)) },
            errorMessage = "Something went wrong when trying to insert Crop Type: $type"
        )
    }
}