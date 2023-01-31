package com.example.avenuecrm.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import com.example.avenuecrm.R
import com.example.avenuecrm.viewModels.AuthorizationViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthorizationActivity : AppCompatActivity() {

    val viewModel: AuthorizationViewModel by viewModels()

    lateinit var loginEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        loginEditText = findViewById(R.id.loginEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener{
            //it.background = AppCompatResources.getDrawable(this, R.color.primary_active)
        }



        loginEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                lifecycleScope.launch {
                    viewModel.afterLoginOrPasswordChanged(loginEditText.text.toString(), passwordEditText.text.toString())
                }
            }
        })
        passwordEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                lifecycleScope.launch {
                    viewModel.afterLoginOrPasswordChanged(loginEditText.text.toString(), passwordEditText.text.toString())
                }
            }
        })

        /*lifecycleScope.launch {
            viewModel.loginButtonIsEnabled.collect{
                loginButton.isEnabled = it
            }
        }*/

    }
}