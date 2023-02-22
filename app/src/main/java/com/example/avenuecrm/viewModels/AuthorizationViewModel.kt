package com.example.avenuecrm.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avenuecrm.data.models.AuthAnswer
import com.example.avenuecrm.data.models.Credentials
import com.example.avenuecrm.data.preferences.abstraction.DataStoreRepository
import com.example.avenuecrm.data.retrofit.AvenueRepository
import com.example.avenuecrm.data.retrofit.AvenueRetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            _authorizationAnswer.emit(repository.authorize(Credentials(login, password)))
        }
    }

    fun saveUser(login: String, password: String) {
        viewModelScope.launch {
            dataStoreRepository.putCredentials(Credentials(login, password))
        }
    }

    fun authorizeSavedUser() {
        viewModelScope.launch {
            val savedUserInfo = dataStoreRepository.getCredentials()
            if (!savedUserInfo.login.isNullOrEmpty() && !savedUserInfo.pass.isNullOrEmpty()) {
                _authorizationAnswer.emit(
                    repository.authorize(savedUserInfo)
                )
            }
            _isLoading.emit(false)
        }
    }
}