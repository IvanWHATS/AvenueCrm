package com.example.avenuecrm.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.avenuecrm.R
import com.example.avenuecrm.data.models.Module
import com.example.avenuecrm.viewModels.MainMenuViewModel
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainMenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private val viewModel: MainMenuViewModel by viewModels()

    private lateinit var navigationView: NavigationView
    private lateinit var logoutBtn: Button
    private lateinit var userImageView: ImageView
    private lateinit var userNameTextView: TextView
    private lateinit var userFioTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        navigationView = findViewById(R.id.navView)
        val navHeader = navigationView.getHeaderView(0)
        logoutBtn = navHeader.findViewById(R.id.logout_btn)
        userImageView = navHeader.findViewById(R.id.user_image)
        userNameTextView = navHeader.findViewById(R.id.user_name)
        userFioTextView = navHeader.findViewById(R.id.user_fio)


        navigationView.setNavigationItemSelectedListener(this)

        logoutBtn.setOnClickListener {
            viewModel.logOut()
            startActivity(Intent(this, AuthorizationActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }


        lifecycleScope.launch(Dispatchers.Main){
            viewModel.userInformation.collect{
                it.file?.let { file -> Picasso.get().load(file.path).into(userImageView) }
                userNameTextView.text = it.name
                userFioTextView.text = it.fioFull
            }
        }

        viewModel.loadUserInformation()

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
            if (module.subModules != null) {
                viewModel.loadMenuItems(
                    listOf(module.copy(subModules = null)).plus(module.subModules)
                )
            }
        } else {
            if (item.itemId == MENU_BACK) {
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
            if (module.subModules != null) {
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