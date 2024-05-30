package com.hendergrand.primeraapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class RecuperarPasswordActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var btnSentMeEmail: Button

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_password)

        etEmail = findViewById(R.id.et_Email)
        btnSentMeEmail = findViewById(R.id.btn_Send_me_Email)

        auth = FirebaseAuth.getInstance()

        btnSentMeEmail.setOnClickListener {
            val sPassword = etEmail.text.toString()
            auth.sendPasswordResetEmail(sPassword)
                .addOnCompleteListener {
                    Toast.makeText(this, "Plase check you email", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }
}