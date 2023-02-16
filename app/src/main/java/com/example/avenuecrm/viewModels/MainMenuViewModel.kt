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

    private val modulesTree: MutableList<List<Module>> = mutableListOf()

    var currentTreeLevel: Int = 0
        private set

    private val _currentMenuItems = MutableStateFlow<List<Module>>(listOf())

    var currentMenuItems = _currentMenuItems.asStateFlow()


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