package com.example.avenuecrm.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.avenuecrm.R
import com.example.avenuecrm.viewModels.AuthorizationViewModel
import kotlinx.coroutines.launch

class AuthorizationActivity : AppCompatActivity() {

    val viewModel: AuthorizationViewModel by viewModels()

    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        loginEditText = findViewById(R.id.loginEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener{
            viewModel.authorize(loginEditText.text.toString(), passwordEditText.text.toString())
        }

        lifecycleScope.launch{
            viewModel.authorizationAnswer.collect{
                if (it != null){
                    if (it.error == null) {
                        Toast.makeText(this@AuthorizationActivity, "Ошибка", Toast.LENGTH_SHORT).show()
                    } else {
                        if (it.error == 1) {
                            Toast.makeText(this@AuthorizationActivity, it.error_msg, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@AuthorizationActivity, it.key, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@AuthorizationActivity, MainMenuActivity::class.java))
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                            finish()
                        }
                    }
                }
            }
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

        lifecycleScope.launch {
            viewModel.loginButtonIsEnabled.collect{
                loginButton.isEnabled = it
            }
        }

    }
}