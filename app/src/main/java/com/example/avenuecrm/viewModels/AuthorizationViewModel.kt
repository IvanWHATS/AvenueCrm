package com.example.avenuecrm.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avenuecrm.models.AuthAnswer
import com.example.avenuecrm.retrofit.AvenueRepository
import com.example.avenuecrm.retrofit.AvenueRetrofitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthorizationViewModel: ViewModel() {

    private val repository: AvenueRepository = AvenueRetrofitRepository()

    private var loginButtonIsEnabledStateFlow = MutableStateFlow(false)
    var loginButtonIsEnabled = loginButtonIsEnabledStateFlow.asStateFlow()

    suspend fun afterLoginOrPasswordChanged(login: String, password: String){
        loginButtonIsEnabledStateFlow.emit(login.isNotEmpty() && password.isNotEmpty())
    }

    fun authorize(login: String, password: String): Boolean{
        var answer: AuthAnswer? = null
        viewModelScope.launch {
            answer = repository.authorize(login, password)
        }

        return answer?.error == 0
    }
}