package com.hgtech.smartio.network.api.crop

import com.hgtech.smartio.network.model.request.crop.type.AddCropTypeRequest
import com.hgtech.smartio.network.model.response.crop.type.CropTypeListResponse
import com.hgtech.smartio.network.model.response.crop.type.CropTypeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CropTypeApi {
    @GET("getAll.php")
    suspend fun listAll(): Response<CropTypeListResponse>

    @POST("insert.php")
    suspend fun insert(@Body category: AddCropTypeRequest): Response<CropTypeResponse>
}