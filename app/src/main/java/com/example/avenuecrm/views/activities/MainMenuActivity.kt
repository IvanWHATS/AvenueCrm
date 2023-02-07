package com.example.avenuecrm.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.avenuecrm.R
import com.example.avenuecrm.viewModels.MainMenuViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainMenuActivity : AppCompatActivity() {


    private val viewModel: MainMenuViewModel by viewModels()

    private lateinit var navigationView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        navigationView = findViewById(R.id.navView)

        lifecycleScope.launch {
            viewModel.menuItems.collect{
                navigationView.menu.clear()
                it.forEach{ module ->
                    if (module.childs != null) {
                        val subMenu = navigationView.menu.addSubMenu(0, module.id!!, Menu.NONE, module.name)
                        module.childs.forEach { subModule ->
                            subMenu.add(0, subModule.id!!, Menu.NONE, subModule.name)
                        }
                    } else {
                        navigationView.menu.add(0, module.id!!, Menu.NONE, module.name)
                    }
                }
            }
        }

        viewModel.loadMenuItems()
    }
}