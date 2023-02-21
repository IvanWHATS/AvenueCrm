package com.example.avenuecrm.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avenuecrm.data.models.AuthAnswer
import com.example.avenuecrm.data.models.UserInformation
import com.example.avenuecrm.data.preferences.abstraction.DataStoreRepository
import com.example.avenuecrm.data.retrofit.AvenueRepository
import com.example.avenuecrm.data.retrofit.AvenueRetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    var isLoading = _isLoading.asStateFlow()


    private val repository: AvenueRepository = AvenueRetrofitRepository

    private val loginButtonIsEnabledFlow = MutableStateFlow(false)
    var loginButtonIsEnabled = loginButtonIsEnabledFlow.asStateFlow()


    private val _authorizationAnswer = MutableStateFlow<AuthAnswer?>(null)
    var authorizationAnswer = _authorizationAnswer.asStateFlow()


    fun afterLoginOrPasswordChanged(login: String, password: String) {
        viewModelScope.launch {
            loginButtonIsEnabledFlow.emit(login.isNotEmpty() && password.isNotEmpty())
        }
    }

    fun authorize(login: String, password: String) {
        viewModelScope.launch {
            _authorizationAnswer.emit(repository.authorize(UserInformation(login, password)))
        }
    }

    fun saveUser(login: String, password: String) {
        viewModelScope.launch {
            dataStoreRepository.putUserInformation(UserInformation(login, password))
        }
    }

    fun authorizeSavedUser() {
        viewModelScope.launch {
            val savedUserInfo = dataStoreRepository.getUserInformation()
            if (!savedUserInfo.login.isNullOrEmpty() && !savedUserInfo.pass.isNullOrEmpty()) {
                _authorizationAnswer.emit(
                    repository.authorize(savedUserInfo)
                )
            }
            _isLoading.emit(false)
        }
    }
}