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
import com.airbnb.lottie.LottieAnimationView

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
        val gAnim : LottieAnimationView = findViewById(R.id.gAnimation)
        val gLogInText : TextView = findViewById(R.id.gLogInText)
        val fAnim : LottieAnimationView = findViewById(R.id.fAnimation)
        val fLogInText : TextView = findViewById(R.id.fLogInText)

        val animationSlideDown = AnimationUtils.loadAnimation(this,R.anim.slide_down)
        val animationSlideUp = AnimationUtils.loadAnimation(this,R.anim.slide_up)
        val animationSlideLeft = AnimationUtils.loadAnimation(this,R.anim.slide_left)
        val animationSlideRight = AnimationUtils.loadAnimation(this,R.anim.slide_right)

        introText.startAnimation(animationSlideDown)

        val gView : View = findViewById(R.id.googleView)
        gView.setOnClickListener {
            startActivity(Intent(this,GoogleSignInActivity::class.java))
        }

        val fView : View = findViewById(R.id.facebookView)
        fView.setOnClickListener {
            startActivity(Intent(this,FacebookSignInActivity::class.java))
        }

        gView.startAnimation(animationSlideRight)
        gAnim.startAnimation(animationSlideRight)
        gLogInText.startAnimation(animationSlideRight)

        fView.startAnimation(animationSlideLeft)
        fAnim.startAnimation(animationSlideLeft)
        fLogInText.startAnimation(animationSlideLeft)




    }
}