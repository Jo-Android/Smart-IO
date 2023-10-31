package com.hgtech.smartio.network.api

import com.hgtech.smartio.network.model.response.user.BaseResponse
import com.hgtech.smartio.network.model.response.user.get.UserResponse
import com.hgtech.smartio.network.model.response.user.login.LoginResponse
import com.hgtech.smartio.network.model.response.user.manager.ManagerResponse
import com.hgtech.smartio.network.model.response.user.signup.SignUPResponse
import com.hgtech.smartio.network.model.request.user.AssignUserRequest
import com.hgtech.smartio.network.model.request.user.LoginRequest
import com.hgtech.smartio.network.model.request.user.PasswordRequest
import com.hgtech.smartio.network.model.request.user.PhoneRequest
import com.hgtech.smartio.network.model.request.user.SignUPRequest
import com.hgtech.smartio.network.model.request.user.UserActiveRequest
import com.hgtech.smartio.network.model.request.user.UsernameRequest
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("signup.php")
    suspend fun signup(@Body request: SignUPRequest): Response<SignUPResponse>

    @GET("get.php")
    suspend fun get(@Query("id") id: String): Response<UserResponse>

    @PUT("isActive.php")
    suspend fun setActive(@Body request: UserActiveRequest): Response<BaseResponse>

    @PUT("username.php")
    suspend fun updateUsername(@Body request: UsernameRequest): Response<BaseResponse>

    @PUT("password.php")
    suspend fun updatePassword(@Body request: PasswordRequest): Response<BaseResponse>

    @PUT("phone.php")
    suspend fun updatePhone(@Body request: PhoneRequest): Response<BaseResponse>

    // Manager
    @GET("list_user.php")
    suspend fun getUsers(@Query("id") id: String): Response<ManagerResponse>

    @GET("list_regular_user.php")
    suspend fun getRegularUsers(): Response<ManagerResponse>

    @PUT("userManager.php")
    suspend fun assign(@Body assignUserRequest: AssignUserRequest): Response<BaseResponse>


}