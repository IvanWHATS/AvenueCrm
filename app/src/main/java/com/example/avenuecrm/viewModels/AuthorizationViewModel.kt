package com.example.avenuecrm.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avenuecrm.models.AuthAnswer
import com.example.avenuecrm.retrofit.AvenueRepository
import com.example.avenuecrm.retrofit.AvenueRetrofitRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class AuthorizationViewModel: ViewModel() {

    private val repository: AvenueRepository = AvenueRetrofitRepository

    private val loginButtonIsEnabledFlow = MutableStateFlow(false)
    var loginButtonIsEnabled = loginButtonIsEnabledFlow.asStateFlow()


    private val _authorizationAnswer = MutableStateFlow<AuthAnswer?>(null)
    var authorizationAnswer = _authorizationAnswer.asStateFlow()

    suspend fun afterLoginOrPasswordChanged(login: String, password: String){
        loginButtonIsEnabledFlow.emit(login.isNotEmpty() && password.isNotEmpty())
    }

    fun authorize(login: String, password: String){
        viewModelScope.launch {_authorizationAnswer.emit(repository.authorize(login, password))
        }
    }
}