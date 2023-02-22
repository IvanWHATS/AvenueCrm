package com.example.avenuecrm.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avenuecrm.data.models.Module
import com.example.avenuecrm.data.models.UserInformation
import com.example.avenuecrm.data.preferences.abstraction.DataStoreRepository
import com.example.avenuecrm.data.retrofit.AvenueRepository
import com.example.avenuecrm.data.retrofit.AvenueRetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val repository: AvenueRepository = AvenueRetrofitRepository

    private val modulesTree: MutableList<List<Module>> = mutableListOf()

    var currentTreeLevel: Int = 0
        private set


    private val _userInformation = MutableStateFlow(UserInformation())

    var userInformation = _userInformation.asStateFlow()

    private val _currentMenuItems = MutableStateFlow<List<Module>>(listOf())

    var currentMenuItems = _currentMenuItems.asStateFlow()


    fun logOut(){
        viewModelScope.launch {
            dataStoreRepository.clearCredentials()
        }
    }

    fun loadUserInformation(){
        viewModelScope.launch {
            repository.getUserInformation()?.let {
                _userInformation.emit(it)
            }
        }
    }

    fun loadMenuItems(){
        viewModelScope.launch {
            repository.getTreeModule()?.let {
                if (it.isNotEmpty()) modulesTree.add(it)
                _currentMenuItems.emit(it)
            }
        }
    }

    fun loadMenuItems(modules: List<Module>){
        currentTreeLevel++
        modulesTree.add(modules)
        viewModelScope.launch {
            _currentMenuItems.emit(modules)
        }
    }

    fun menuGoBack(){
        if (currentTreeLevel > 0){
            modulesTree.removeAt(currentTreeLevel--)
            viewModelScope.launch{
                _currentMenuItems.emit(modulesTree[currentTreeLevel])
            }
        }
    }

}