package com.hgtech.smartio.network.api.values

import com.hgtech.smartio.network.model.response.values.ValuesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ValuesApi {

    @GET("read.php")
    suspend fun get(@Query("id") id: String): Response<ValuesResponse>
}