package com.ifs21019.lostfounds.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ifs18005.delcomtodo.data.remote.response.DataUserResponse
import com.ifs18005.delcomtodo.data.remote.response.DelcomResponse
import com.ifs21019.lostfounds.data.remote.MyResult
import com.ifs21019.lostfounds.data.repository.AuthRepository
import com.ifs21019.lostfounds.data.repository.UserRepository
import com.ifs21019.lostfounds.presentation.ViewModelFactory
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun getMe(): LiveData<MyResult<DataUserResponse>> {
        return userRepository.getMe().asLiveData()
    }

    fun editPhoto(
        cover: MultipartBody.Part,
    ): LiveData<MyResult<DelcomResponse>> {
        return userRepository.addCoverProfile( cover).asLiveData()
    }
    companion object {
        @Volatile
        private var INSTANCE: ProfileViewModel? = null
        fun getInstance(
            authRepository: AuthRepository,
            userRepository: UserRepository
        ): ProfileViewModel {
            synchronized(ViewModelFactory::class.java) {
                INSTANCE = ProfileViewModel(
                    authRepository,
                    userRepository
                )
            }
            return INSTANCE as ProfileViewModel
        }
    }
}