package com.example.social_media_integration_tsf__5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

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

        val animationFadeIn = AnimationUtils.loadAnimation(this,R.anim.slide_down)
        val animationSlideUp = AnimationUtils.loadAnimation(this,R.anim.slide_up)

        introText.startAnimation(animationFadeIn)

        val gView : View = findViewById(R.id.googleView)
        gView.setOnClickListener {
            startActivity(Intent(this,GoogleSignInActivity::class.java))
        }

        val fView : View = findViewById(R.id.facebookView)
        fView.setOnClickListener {
            startActivity(Intent(this,FacebookSignInActivity::class.java))
        }



    }
}