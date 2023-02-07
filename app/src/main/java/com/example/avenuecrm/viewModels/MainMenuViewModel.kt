package com.example.avenuecrm.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avenuecrm.models.Module
import com.example.avenuecrm.retrofit.AvenueRepository
import com.example.avenuecrm.retrofit.AvenueRetrofitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainMenuViewModel: ViewModel() {

    private val repository: AvenueRepository = AvenueRetrofitRepository

    private val _menuItems = MutableStateFlow<List<Module>>(listOf())

    var menuItems = _menuItems.asStateFlow()


    fun loadMenuItems(){
        viewModelScope.launch {
            repository.getTreeModule()?.let { _menuItems.emit(it) }
        }
    }

}