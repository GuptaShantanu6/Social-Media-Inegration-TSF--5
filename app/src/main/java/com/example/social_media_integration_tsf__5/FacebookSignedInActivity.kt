package com.example.social_media_integration_tsf__5

import android.annotation.SuppressLint
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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class FacebookSignedInActivity : AppCompatActivity() {

    private lateinit var fNameFinal : TextView
    private lateinit var fMailFinal : TextView
    private lateinit var fAuth : FirebaseAuth

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_signed_in)

        supportActionBar?.hide()

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.black)

        fNameFinal = findViewById(R.id.fName)
        fMailFinal = findViewById(R.id.fMail)

        val fInfo = baseContext.getSharedPreferences("fInfoMain", Context.MODE_PRIVATE)
        fNameFinal.text = fInfo.getString("fName","none").toString()
        fMailFinal.text = fInfo.getString("fMailId","none").toString()

        val animationSlideUp = AnimationUtils.loadAnimation(this@FacebookSignedInActivity,R.anim.slide_up)
        val animationSlideLeft = AnimationUtils.loadAnimation(this@FacebookSignedInActivity,R.anim.slide_left)
        val animationSlideRight = AnimationUtils.loadAnimation(this@FacebookSignedInActivity,R.anim.slide_right)

        val fNameHeading : TextView = findViewById(R.id.fNameHeading)
        val fMailHeading : TextView = findViewById(R.id.fMailHeading)

        fNameHeading.startAnimation(animationSlideRight)
        fNameFinal.startAnimation(animationSlideLeft)
        fMailHeading.startAnimation(animationSlideRight)
        fMailFinal.startAnimation(animationSlideLeft)


        fAuth = FirebaseAuth.getInstance()

        val fSignOutBtn : View = findViewById(R.id.fSignOutView)
        val fSignOutText : TextView = findViewById(R.id.fSignOutText)

        fSignOutBtn.startAnimation(animationSlideUp)
        fSignOutText.startAnimation(animationSlideUp)

        fSignOutBtn.setOnClickListener {
            Firebase.auth.signOut()

            val aIntent = Intent(this@FacebookSignedInActivity,MainActivity::class.java)
            aIntent.putExtra("FROM","fSignIn")
            startActivity(aIntent)

            finish()
        }
    }
}