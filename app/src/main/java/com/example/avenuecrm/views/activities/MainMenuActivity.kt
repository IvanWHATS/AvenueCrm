package com.example.avenuecrm.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.avenuecrm.R
import com.example.avenuecrm.data.models.Module
import com.example.avenuecrm.viewModels.MainMenuViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainMenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private val viewModel: MainMenuViewModel by viewModels()

    private lateinit var navigationView: NavigationView
    private lateinit var btn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        navigationView = findViewById(R.id.navView)
        btn = findViewById(R.id.btn)

        btn.setOnClickListener{
            viewModel.logOut()
            startActivity(Intent(this, AuthorizationActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

        navigationView.setNavigationItemSelectedListener(this)

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.currentMenuItems.collect {
                addMenuItems(it)
            }
        }

        viewModel.loadMenuItems()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val module = viewModel.currentMenuItems.value.find { it.id == item.itemId }
        if (module != null) {
            if (module.childs != null) {
                viewModel.loadMenuItems(
                    listOf(
                        Module(
                            name = module.name,
                            link = module.link,
                            id = module.id,
                            childs = null
                        )
                    ).plus(module.childs)
                )
            }
        } else {
            if (item.itemId == MENU_BACK){
                viewModel.menuGoBack()

            }
        }
        return true
    }

    private fun addMenuItems(items: List<Module>) {
        navigationView.menu.clear()
        val menu: Menu = if (viewModel.currentTreeLevel == 0) navigationView.menu
        else {
            navigationView.menu.add(Menu.NONE, MENU_BACK, Menu.NONE, "Назад")
                .setIcon(R.drawable.arrow_back_icon)
            navigationView.menu.addSubMenu(items.firstOrNull()?.name)
        }
        items.forEach { module ->
            if (module.childs != null) {
                menu.add(Menu.NONE, module.id!!, Menu.NONE, module.name)
                    .setActionView(R.layout.menu_arrow_icon)
                    .setIcon(R.drawable.outline_folder)
            } else {
                menu.add(Menu.NONE, module.id!!, Menu.NONE, module.name)
            }
        }
    }

    companion object {
        const val MENU_BACK: Int = 0
    }
}