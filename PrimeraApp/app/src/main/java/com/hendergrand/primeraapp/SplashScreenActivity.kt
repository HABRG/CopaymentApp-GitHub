package com.hendergrand.primeraapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            goToMainActivity()
        }, 2000L)
    }

    private fun goToMainActivity(){
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}