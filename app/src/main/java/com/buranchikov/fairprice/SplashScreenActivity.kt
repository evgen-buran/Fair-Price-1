package com.buranchikov.fairprice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

class SplashScreenActivity : AppCompatActivity() {
    private val SHORT_DELAY = 500L
    private val LONG_DELAY = 500L
    lateinit var preferences: SplashPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        preferences = SplashPreferences(this)
        Log.d("myLog", "onCreate: ${preferences.getFlag()}")

    }

    override fun onStart() {
        super.onStart()

        when (preferences.getFlag()) {
            1 -> {
                Handler().postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, SHORT_DELAY)

            }

            0 -> {
                Handler().postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    preferences.setFlag()
                    finish()
                }, LONG_DELAY)


            }

        }

    }
}
