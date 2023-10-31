package com.hgtech.smartio.network.api.crop

import com.hgtech.smartio.network.model.request.crop.AddCropRequest
import com.hgtech.smartio.network.model.response.crop.AddCropResponse
import com.hgtech.smartio.network.model.response.crop.AllCropsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CropsApi {

    @GET("getAll.php")
    suspend fun getAll(): Response<AllCropsResponse>

    @POST("insert.php")
    suspend fun insert(@Body crop: AddCropRequest): Response<AddCropResponse>
}