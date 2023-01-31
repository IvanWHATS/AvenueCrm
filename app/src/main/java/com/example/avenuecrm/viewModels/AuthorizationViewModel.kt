package com.example.avenuecrm.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthorizationViewModel: ViewModel() {



    private var loginButtonIsEnabledStateFlow = MutableStateFlow(false)
    var loginButtonIsEnabled = loginButtonIsEnabledStateFlow.asStateFlow()

    suspend fun afterLoginOrPasswordChanged(login: String, password: String){
        loginButtonIsEnabledStateFlow.emit(login.isNotEmpty() && password.isNotEmpty())
    }

    fun authorize(login: String, password: String){

    }
}