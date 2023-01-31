package com.example.avenuecrm.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.avenuecrm.R
import com.example.avenuecrm.viewModels.SplashScreenViewModel

class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val int = Intent(this, AuthorizationActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(int)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            },
            1500
        )
    }
}