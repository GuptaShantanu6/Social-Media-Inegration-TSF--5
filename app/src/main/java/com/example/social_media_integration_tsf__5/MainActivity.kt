package com.example.social_media_integration_tsf__5

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth


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
        val debugGoogleView : View = findViewById(R.id.debugGoogleSignInView)
        val debugGoogleText : TextView = findViewById(R.id.debugGoogleTextView)

        val animationSlideDown = AnimationUtils.loadAnimation(this,R.anim.slide_down)
//        val animationSlideUp = AnimationUtils.loadAnimation(this,R.anim.slide_up)
        val animationSlideLeft = AnimationUtils.loadAnimation(this,R.anim.slide_left)
        val animationSlideRight = AnimationUtils.loadAnimation(this,R.anim.slide_right)

        debugGoogleText.visibility = View.GONE
        debugGoogleView.visibility = View.GONE

        introText.startAnimation(animationSlideDown)

        mAuth = Firebase.auth

        createGoogleSignInRequest()

        val gView : View = findViewById(R.id.googleView)
        gView.setOnClickListener {
            googleSignIn()
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

    private fun createGoogleSignInRequest() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)
    }

    private fun googleSignIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG,"firebaseAuthWithGoogle:"+account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e : ApiException) {
                    Toast.makeText(this,"Google Sign In Failed",Toast.LENGTH_SHORT).show()
                    Log.d(TAG,e.toString())
                }
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        try {
            mAuth.signInWithCredential(credential)
            startActivity(Intent(this@MainActivity,GoogleSignedInActivity::class.java))
        } catch (e : Exception) {
            Toast.makeText(this,"Google Sign In with Credential Failed",Toast.LENGTH_SHORT).show()
            Log.d(TAG,e.toString())
        }

    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 120
    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        if (currentUser != null){
            startActivity(Intent(this@MainActivity,GoogleSignedInActivity::class.java))
        }
    }
}