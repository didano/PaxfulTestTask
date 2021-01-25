package com.example.paxfultesttask.data.repositories

import com.example.paxfultesttask.data.models.JokeResponse
import com.example.paxfultesttask.domain.api.ErrorResponse
import com.example.paxfultesttask.domain.api.IcndbApiService
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ApiRepository(
    private val api: IcndbApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : IApiRepository {

    override suspend fun getJokes(): ResultWrapper<JokeResponse> {
        return safeApiCall(dispatcher) { api.getJokes().body()!! }
    }

    override suspend fun getNamedJokes(
        firstName: String,
        lastName: String
    ): ResultWrapper<JokeResponse> {
        return safeApiCall(dispatcher) { api.getNamedJokes(firstName, lastName).body()!! }
    }

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)
                        ResultWrapper.GenericError(code, errorResponse)
                    }
                    else -> {
                        ResultWrapper.GenericError(null, null)
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }

    sealed class ResultWrapper<out T> {
        data class Success<out T>(val value: T) : ResultWrapper<T>()
        data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) :
            ResultWrapper<Nothing>()

        object NetworkError : ResultWrapper<Nothing>()
    }
}