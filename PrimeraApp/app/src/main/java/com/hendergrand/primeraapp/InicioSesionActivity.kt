package com.hendergrand.primeraapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.core.view.View
import com.google.firebase.ktx.Firebase
import com.hendergrand.primeraapp.databinding.ActivityInicioSesionBinding

class InicioSesionActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityInicioSesionBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.tietPassword.text.toString().trim()
          //  Log.i("Inicio de sesion email", "Inicia sesion email")
         //   Log.i("Inicio de sesion pass", "inicia sesion pass")

            if (email.isNotEmpty() && pass.isNotEmpty()){
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this){
                    if (it.isSuccessful){
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        fun goToRegister(view: View){
            val intent= Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        //Analytics google
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de FireBase completa")
        analytics.logEvent("InitScreen", bundle)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()

        val tvRegister = findViewById<TextView>(R.id.tvSignUP)
        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val tvRecuperarContrasena = findViewById<TextView>(R.id.tvForgotPassword)
        tvRecuperarContrasena.setOnClickListener {
            val intent = Intent(this, RecuperarPasswordActivity::class.java)
            startActivity(intent)
        }

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
        binding.ivGoogle.setOnClickListener {
            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)

        }

    }


    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                            //            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, HomeActivity::class.java))
                        }else {
                            Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                        }
                    }
                }else {
                    Toast.makeText(this, "failed", Toast.LENGTH_LONG).show()
                }
            }
        }

}