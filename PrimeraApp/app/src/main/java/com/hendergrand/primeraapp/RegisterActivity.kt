package com.hendergrand.primeraapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    private lateinit var etFullaName : EditText
    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var btn_SignUp : Button

    private lateinit var auth: FirebaseAuth
    private lateinit var reference : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        InicializarVariables()

        val tvInicioSesion = findViewById<TextView>(R.id.tvSignIn)
        tvInicioSesion.setOnClickListener{
            val intent: Intent = Intent(this, InicioSesionActivity::class.java)
            startActivity(intent)
        }

        btn_SignUp.setOnClickListener {
            ValidarDatos()
        }
    }

    private fun InicializarVariables () {
        etFullaName = findViewById(R.id.etFullaName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btn_SignUp = findViewById(R.id.btn_SignUp)
        auth = FirebaseAuth.getInstance()
    }

    private fun ValidarDatos() {
        val nombre_usuario : String = etFullaName.text.toString().trim()
        val email : String = etEmail.text.toString().trim()
        val password : String = btn_SignUp.text.toString().trim()

        if (nombre_usuario.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese completo el nombre de usuario", Toast.LENGTH_SHORT).show()
        }else if (email.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese su correo electronico", Toast.LENGTH_SHORT).show()
        }else if (password.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese su contraseña", Toast.LENGTH_SHORT).show()
        }
        else {
            RegistrarUsuario(email, password)
        }
    }

    private fun RegistrarUsuario(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    var usuarioid : String = ""
                    usuarioid = auth.currentUser!!.uid
                    reference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(usuarioid)

                    val hasmap = HashMap<String, Any>()
                    val h_nombre_usuario : String = etFullaName.text.toString()
                    val h_email : String = etEmail.text.toString()

                    hasmap["uid"] = usuarioid
                    hasmap["n_usuario"] = h_nombre_usuario
                    hasmap["email"] = h_email
                    hasmap["codigoTelefono"] = ""
                    hasmap["telefono"] = ""
                    hasmap["imagen"] = ""
                    hasmap["ocupacion"] = ""
                    hasmap["saldo"] = ""
                    hasmap["buscar"] = h_nombre_usuario.lowercase()

                    reference.updateChildren(hasmap).addOnCompleteListener{task2->
                        if (task2.isSuccessful){
                            val intent = Intent(this@RegisterActivity, InicioSesionActivity::class.java)
                            Toast.makeText(applicationContext, "El registro ha sido exitoso", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }

                    }
                        .addOnFailureListener{e->
                        Toast.makeText(applicationContext, "{${e.message}}", Toast.LENGTH_SHORT).show()

                    }

                }else {
                    Toast.makeText(applicationContext, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }
        }
            .addOnFailureListener{e->
                Toast.makeText(applicationContext, "{${e.message}}", Toast.LENGTH_SHORT).show()

            }
    }
}