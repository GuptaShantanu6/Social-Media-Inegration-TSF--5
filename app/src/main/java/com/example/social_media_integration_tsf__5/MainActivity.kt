package com.example.social_media_integration_tsf__5

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
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
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var gProgressDialog : ProgressDialog
    private lateinit var fProgressDialog : ProgressDialog
    private lateinit var callbackManager: CallbackManager
    private lateinit var fAuth : FirebaseAuth
    private lateinit var whichAcc : String
    private lateinit var fLogInBtnMain : LoginButton

    var facebookLogInCheck : Int = 0

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FacebookSdk.sdkInitialize(applicationContext)

        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.black)

        mAuth = Firebase.auth
        fAuth = Firebase.auth

        whichAcc = "Facebook"

        gProgressDialog = ProgressDialog(this@MainActivity)
        fProgressDialog = ProgressDialog(this@MainActivity)

        val gCurrentUser = mAuth.currentUser
        val fCurrentUser = fAuth.currentUser

        if (gCurrentUser != null || fCurrentUser != null){
            Firebase.auth.signOut()
        }

        val introText : TextView = findViewById(R.id.introTextView)
        val gAnim : LottieAnimationView = findViewById(R.id.gAnimation)
        val gLogInText : TextView = findViewById(R.id.gLogInText)
        fLogInBtnMain = findViewById(R.id.fLogInBtnMain)

        val animationSlideDown = AnimationUtils.loadAnimation(this,R.anim.slide_down)
        val animationSlideLeft = AnimationUtils.loadAnimation(this,R.anim.slide_left)
        val animationSlideRight = AnimationUtils.loadAnimation(this,R.anim.slide_right)

        introText.startAnimation(animationSlideDown)

        createGoogleSignInRequest()

        val gView : View = findViewById(R.id.googleView)
        gView.setOnClickListener {
            gProgressDialog.setTitle("Please Wait")
            gProgressDialog.setCancelable(false)
            gProgressDialog.show()
            googleSignIn(gProgressDialog)
        }

        callbackManager = CallbackManager.Factory.create()

        gView.startAnimation(animationSlideRight)
        gAnim.startAnimation(animationSlideRight)
        gLogInText.startAnimation(animationSlideRight)
        fLogInBtnMain.startAnimation(animationSlideLeft)

        val aIntent = intent
        if (aIntent.getStringExtra("FROM").equals("fSignIn")) {
            fLogInBtnMain.performClick()
        }

        fLogInBtnMain.setPermissions("email","public_profile")
        fLogInBtnMain.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Log.d(fTAG,"facebook:onSuccess:$result")
                handleFacebookAccessToken(result?.accessToken, fProgressDialog)
            }

            override fun onCancel() {
                Log.d(fTAG,"facebook:onCancel")
            }

            override fun onError(error: FacebookException?) {
                Log.d(fTAG,"facebook:onError",error)
            }

        })

    }

    private fun createGoogleSignInRequest() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)
    }

    private fun googleSignIn(gProgressDialog: ProgressDialog) {
        val signInIntent = mGoogleSignInClient.signInIntent
        whichAcc = "Google"
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN && whichAcc == "Google") {
            if (resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG,"firebaseAuthWithGoogle:"+account.id)
                    firebaseAuthWithGoogle(account.idToken!!,gProgressDialog)
                } catch (e : ApiException) {
                    Toast.makeText(this,"Google Sign In Failed",Toast.LENGTH_SHORT).show()
                    Log.d(TAG,e.toString())
                    whichAcc = "Facebook"
                }
            }
            whichAcc = "Facebook"
        }

        else if (whichAcc == "Facebook") {
            callbackManager.onActivityResult(requestCode,resultCode,data)
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun firebaseAuthWithGoogle(idToken: String, gProgressDialog: ProgressDialog) {
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG,"signInWithCredential : Success")
                    val user = mAuth.currentUser
                    Log.d(TAG, user?.displayName!!)

                    val gInfo = baseContext.getSharedPreferences("gInfoMain",Context.MODE_PRIVATE).edit()
                    gInfo.apply {
                        putString("gName", user.displayName!!)
                        putString("gMailId", user.email!!)
                        apply()
                    }

                    gProgressDialog.dismiss()
                    startActivity(Intent(this@MainActivity,GoogleSignedInActivity::class.java))

                }
                else {
                    Log.w(TAG,"signInWithCredential : Failed", task.exception)

                }

                whichAcc = "Facebook"

            }
    }

    @SuppressLint("CommitPrefEdits")
    private fun handleFacebookAccessToken(accessToken: AccessToken?, fProgressDialog: ProgressDialog) {
        if (accessToken != null) {
            Log.d(fTAG,"handleFacebookAccessToken:${accessToken.token}")
        }

        val credential = FacebookAuthProvider.getCredential(accessToken?.token!!)

        fProgressDialog.setTitle("Please Wait")
        fProgressDialog.setCancelable(false)
        fProgressDialog.show()

        fAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(fTAG,"signInWithCredential:success")
                    val user = fAuth.currentUser
                    Log.d(fTAG,user?.displayName!!)

                    val providerData : List<UserInfo> = user.providerData
                    val email : String = providerData[1].email!!

                    Log.d(fTAG,email)

                    val fInfo = baseContext.getSharedPreferences("fInfoMain",Context.MODE_PRIVATE).edit()
                    fInfo.apply {
                        putString("fName", user.displayName!!)
                        putString("fMailId", email)
                        apply()
                    }

                    fProgressDialog.dismiss()

                    startActivity(Intent(this@MainActivity,FacebookSignedInActivity::class.java))

                }
                else {
                    Log.w(fTAG,"signInWithCredential:failure:${task.exception}")
                    Toast.makeText(baseContext,"Authentication Failed.",Toast.LENGTH_SHORT).show()
                }
            }

    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 120
        private const val fTAG = "FacebookActivity"
    }

}