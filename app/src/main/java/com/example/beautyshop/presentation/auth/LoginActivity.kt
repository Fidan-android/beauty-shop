package com.example.beautyshop.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.beautyshop.MainActivity
import com.example.beautyshop.R
import com.example.beautyshop.conventions.SharedKeys
import com.example.beautyshop.databinding.ActivityLoginBinding
import com.example.beautyshop.helper.shared

class LoginActivity : AppCompatActivity(), ILoginActivity {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        if (this.shared<String>(SharedKeys.AccessToken).isEmpty()) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.loginContainer, LoginFragment(), LoginFragment::class.java.name)
                .commitAllowingStateLoss()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onReplaceFragment(pFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.loginContainer, pFragment, pFragment::class.java.name)
            .commitAllowingStateLoss()
    }

    override fun onPushFragment(pFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.loginContainer, pFragment, pFragment::class.java.name)
            .addToBackStack(pFragment::class.java.name)
            .commitAllowingStateLoss()
    }

    override fun onPopBack() {
        supportFragmentManager.popBackStack()
    }
}