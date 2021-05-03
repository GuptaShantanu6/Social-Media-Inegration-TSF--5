package com.example.social_media_integration_tsf__5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import com.github.florent37.materialtextfield.MaterialTextField

class GoogleSignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_in)

        supportActionBar?.hide()

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.black)

        val gIcon : ImageView = findViewById(R.id.gIcon)
        val gUsernameField : MaterialTextField = findViewById(R.id.gUsernameField)
        val gPasswordField : MaterialTextField = findViewById(R.id.gPasswordField)
        val gBtn : Button = findViewById(R.id.gLogInBtn)

        val animationSlideDown = AnimationUtils.loadAnimation(this,R.anim.slide_down)
        val animationSlideUp = AnimationUtils.loadAnimation(this,R.anim.slide_up)
        val animationSlideLeft = AnimationUtils.loadAnimation(this,R.anim.slide_left)
        val animationSlideRight = AnimationUtils.loadAnimation(this,R.anim.slide_right)

        gIcon.startAnimation(animationSlideDown)
        gUsernameField.startAnimation(animationSlideRight)
        gPasswordField.startAnimation(animationSlideLeft)
        gBtn.startAnimation(animationSlideUp)

    }
}