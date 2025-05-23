package com.vanshika.klf

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.CreationExtras
import com.vanshika.klf.databinding.ActivityLoginBinding
import com.vanshika.klf.model.UserData
import com.vanshika.klf.remote.EventViewModelFactory
import com.vanshika.klf.utils.Result
import com.vanshika.klf.utils.TokenManager
import com.vanshika.klf.viewmodel.EventViewModel
import kotlin.Lazy
import kotlin.jvm.functions.Function0


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val eventViewModel: Lazy<EventViewModel> by lazy {
        ViewModelLazy(
            EventViewModel::class.java.kotlin, // Correct way to get KClass from Class
            { viewModelStore },
            { EventViewModelFactory(this) },
            { defaultViewModelCreationExtras } // Or a custom CreationExtras if needed
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (TokenManager.isUserLoggedIn(this)) {
            navigateToHome()
            return
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                showToast("Please enter username and password")
            } else {
                loginUser(username, password)
            }
        }
        observeLoginResult()
    }

    private fun loginUser(username: String, password: String) {
        eventViewModel.value.login(username, password)
    }

    private fun observeLoginResult() {
        eventViewModel.value.loginResult.observe(this, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressCircular.visibility = View.GONE
                    val loginResponse = result.data
                    if (loginResponse != null) {
                        handleLoginResponse(this, loginResponse.user_token, loginResponse.user_id)
                        TokenManager.saveLoginState(this)
                        navigateToHome()
                    }
                }
                is Result.Failure -> {
                    binding.progressCircular.visibility = View.GONE
                    showToast("Login failed: ${result.exception.message}")
                }
            }
        })
    }

    private fun navigateToHome() {
        val intent = Intent(this, EventListActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (view != null) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    view.clearFocus()
                    hideKeyboard(view)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleLoginResponse(context: Context, token: String, userId: Int) {
        TokenManager.saveToken(context, token)
        TokenManager.setUserId(context, userId)
    }
}