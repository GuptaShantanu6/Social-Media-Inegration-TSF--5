package com.example.social_media_integration_tsf__5

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GoogleSignedInActivity : AppCompatActivity() {

    private lateinit var gNameFinal : TextView
    private lateinit var gMailFinal : TextView
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_signed_in)

        supportActionBar?.hide()

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.black)

        gNameFinal = findViewById(R.id.gName)
        gMailFinal = findViewById(R.id.gMail)

        val gInfo = baseContext.getSharedPreferences("gInfoMain",Context.MODE_PRIVATE)
        gNameFinal.text = gInfo.getString("gName","none").toString()
        gMailFinal.text = gInfo.getString("gMailId","none").toString()

        val animationSlideUp = AnimationUtils.loadAnimation(this@GoogleSignedInActivity,R.anim.slide_up)
        val animationSlideLeft = AnimationUtils.loadAnimation(this@GoogleSignedInActivity,R.anim.slide_left)
        val animationSlideRight = AnimationUtils.loadAnimation(this@GoogleSignedInActivity,R.anim.slide_right)

        val gNameHeading : TextView = findViewById(R.id.gNameHeading)
        val gMailHeading : TextView = findViewById(R.id.gMailHeading)

        gNameHeading.startAnimation(animationSlideRight)
        gNameFinal.startAnimation(animationSlideLeft)
        gMailHeading.startAnimation(animationSlideRight)
        gMailFinal.startAnimation(animationSlideLeft)


        mAuth = FirebaseAuth.getInstance()

        val gSignOutBtn : View = findViewById(R.id.gSignOutView)
        val gSignOutText : TextView = findViewById(R.id.gSignOutText)

        gSignOutBtn.startAnimation(animationSlideUp)
        gSignOutText.startAnimation(animationSlideUp)

        gSignOutBtn.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this@GoogleSignedInActivity,MainActivity::class.java))
            finish()
        }

    }

}