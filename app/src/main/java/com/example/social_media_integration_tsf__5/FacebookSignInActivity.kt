package com.example.social_media_integration_tsf__5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import com.github.florent37.materialtextfield.MaterialTextField

class FacebookSignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook__sign__in_)

        supportActionBar?.hide()

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.black)

        val fIcon : ImageView = findViewById(R.id.fIcon)
        val fUsernameField : MaterialTextField = findViewById(R.id.fUsernameField)
        val fPasswordField : MaterialTextField = findViewById(R.id.fPasswordField)
        val fBtn : Button = findViewById(R.id.fLogInBtn)

        val animationSlideDown = AnimationUtils.loadAnimation(this,R.anim.slide_down)
        val animationSlideUp = AnimationUtils.loadAnimation(this,R.anim.slide_up)
        val animationSlideLeft = AnimationUtils.loadAnimation(this,R.anim.slide_left)
        val animationSlideRight = AnimationUtils.loadAnimation(this,R.anim.slide_right)

        fIcon.startAnimation(animationSlideDown)
        fUsernameField.startAnimation(animationSlideRight)
        fPasswordField.startAnimation(animationSlideLeft)
        fBtn.startAnimation(animationSlideUp)

    }
}