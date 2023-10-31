package com.hgtech.smartio.network.call.other.user

import com.hgtech.smartio.network.call.base.BaseRepository
import com.hgtech.smartio.network.factory.ApiFactory.usersApi
import com.hgtech.smartio.network.manager.UserWrapper.get
import com.hgtech.smartio.network.model.request.user.AssignUserRequest
import com.hgtech.smartio.network.model.request.user.LoginRequest
import com.hgtech.smartio.network.model.request.user.PasswordRequest
import com.hgtech.smartio.network.model.request.user.PhoneRequest
import com.hgtech.smartio.network.model.request.user.SignUPRequest
import com.hgtech.smartio.network.model.request.user.UserActiveRequest
import com.hgtech.smartio.network.model.request.user.UsernameRequest
import com.hgtech.smartio.network.model.response.user.BaseResponse
import com.hgtech.smartio.network.model.response.user.get.UserResponse
import com.hgtech.smartio.network.model.response.user.login.LoginResponse
import com.hgtech.smartio.network.model.response.user.manager.ManagerResponse
import com.hgtech.smartio.network.model.response.user.signup.SignUPResponse

class UserRepository : BaseRepository() {

//    private val api: UserApi=usersApi

    suspend fun login(request: LoginRequest): LoginResponse? {
        return safeApiCall(
            call = { usersApi.login(request) },
            errorMessage = "Something went wrong when trying to login: $request"
        )
    }

    suspend fun signUp(request: SignUPRequest): SignUPResponse? {
        return safeApiCall(
            call = { usersApi.signup(request) },
            errorMessage = "Something went wrong when trying to sign Up: $request"
        )
    }

    suspend fun get(id: String): UserResponse? {
        return safeApiCall(
            call = { usersApi.get(id) },
            errorMessage = "Something went wrong when trying to get User: $id"
        )
    }

    suspend fun setActive(id: String, isActive: Boolean): BaseResponse? {
        return safeApiCall(
            call = { usersApi.setActive(UserActiveRequest(id, isActive.get())) },
            errorMessage = "Something went wrong when trying to Change Activation of User: $id"
        )
    }

    suspend fun updatePhone(id: String, phone: String): BaseResponse? {
        return safeApiCall(
            call = { usersApi.updatePhone(PhoneRequest(id, phone)) },
            errorMessage = "Something went wrong when trying to update Username: $id"
        )
    }

    suspend fun updateUsername(
        id: String,
        old: String,
        new: String,
        password: String
    ): BaseResponse? {
        return safeApiCall(
            call = { usersApi.updateUsername(UsernameRequest(id, old, new, password)) },
            errorMessage = "Something went wrong when trying to update Username: $id"
        )
    }

    suspend fun updatePassword(id: String, new: String, username: String): BaseResponse? {
        return safeApiCall(
            call = { usersApi.updatePassword(PasswordRequest(id, username, new)) },
            errorMessage = "Something went wrong when trying to update Password: $id"
        )
    }

    // Manager
    suspend fun getUsers(id: String): ManagerResponse? {
        return safeApiCall(
            call = { usersApi.getUsers(id) },
            errorMessage = "Something went wrong when trying to get list of users Managed by: $id"
        )
    }

    suspend fun assign(id: String, managerId: String?): BaseResponse? {
        return safeApiCall(
            call = { usersApi.assign(AssignUserRequest(id, managerId)) },
            errorMessage = "Something went wrong when trying to get list of users Managed by: $managerId"
        )
    }

    suspend fun getRegularUsers(): ManagerResponse? {
        return safeApiCall(
            call = { usersApi.getRegularUsers() },
            errorMessage = "Something went wrong when trying to get Regular users"
        )
    }

}