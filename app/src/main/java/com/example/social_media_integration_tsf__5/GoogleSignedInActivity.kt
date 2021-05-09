package com.example.social_media_integration_tsf__5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class GoogleSignedInActivity : AppCompatActivity() {

    private lateinit var gNameFinal : TextView
    private lateinit var gMailFinal : TextView
    private lateinit var gLoadingAnim : LottieAnimationView
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_signed_in)

        supportActionBar?.hide()

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.black)

        val gNameHeading : TextView = findViewById(R.id.gNameHeading)
        val gMailHeading : TextView = findViewById(R.id.gMailHeading)

        gNameFinal = findViewById(R.id.gName)
        gMailFinal= findViewById(R.id.gMail)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        gFetchData(currentUser)

    }

    private fun gFetchData(currentUser: FirebaseUser?) {
        gNameFinal.text = currentUser?.displayName
        gMailFinal.text = currentUser?.email
    }
}