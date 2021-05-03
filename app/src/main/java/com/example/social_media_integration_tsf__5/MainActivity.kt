package com.example.social_media_integration_tsf__5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.black)

        val introText : TextView = findViewById(R.id.introTextView)

        val animationFadeIn = AnimationUtils.loadAnimation(this,R.anim.bounce)
        val animationSlideUp = AnimationUtils.loadAnimation(this,R.anim.slide_up)

        introText.startAnimation(animationFadeIn)

        val logInBox : LinearLayout = findViewById(R.id.logInLL)
        logInBox.startAnimation(animationSlideUp)

        val gFrame : FrameLayout = findViewById(R.id.google_frame)
        gFrame.setOnClickListener {
            startActivity(Intent(this,GoogleSignInActivity::class.java))
        }


    }
}