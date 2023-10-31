package com.hgtech.smartio.network.call.base

import android.util.Log
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result: Result<T> = safeApiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is Result.Success -> data = result.data
            is Result.Error -> {
                methodLogger("Error Occurred during getting safe Api result::::: $errorMessage")
            }
        }

        return data
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Result<T> {
        val response = call.invoke()
        if (response.isSuccessful) return Result.Success(response.body()!!)
        return Result.Error(IOException(errorMessage))
    }

    fun methodLogger(message: Any? = null) {
//        if (BuildConfig.DEBUG) {
        StringBuilder().apply {
            val stackTraceElement = Thread.currentThread().stackTrace[4]
            append("[ ")
            append(stackTraceElement.fileName)
            append(": ")
            append(stackTraceElement.lineNumber)
            append(": ")
            append(stackTraceElement.methodName)
            append("() ] _____ ")
            append(message)
            Log.e("Retrofit Error", toString())
        }
//        }
    }
}