package com.ifs21019.lostfounds.data.repository

import com.google.gson.Gson
import com.ifs18005.delcomtodo.data.remote.response.DelcomResponse
import com.ifs21019.lostfounds.data.remote.MyResult
import com.ifs21019.lostfounds.data.remote.retrofit.IApiService
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException

class UserRepository private constructor(
    private val apiService: IApiService,
) {

    fun getMe() = flow {
        emit(MyResult.Loading)
        try {
            //get success message
            emit(MyResult.Success(apiService.getMe().data))
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            emit(
                MyResult.Error(
                    Gson()
                        .fromJson(jsonInString, DelcomResponse::class.java)
                        .message
                )
            )
        }
    }
    fun addCoverProfile(
        cover: MultipartBody.Part,
    ) = flow {
        emit(MyResult.Loading)
        try {
            //get success message
            emit(MyResult.Success(apiService.addCoverProfile( cover)))
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            emit(
                MyResult.Error(
                    Gson()
                        .fromJson(jsonInString, DelcomResponse::class.java)
                        .message
                )
            )
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null
        fun getInstance(
            apiService: IApiService,
        ): UserRepository {
            synchronized(UserRepository::class.java) {
                INSTANCE = UserRepository(
                    apiService
                )
            }
            return INSTANCE as UserRepository
        }
    }
}