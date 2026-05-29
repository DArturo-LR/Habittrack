package com.example.habittrack.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.habittrack.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        // IMPORTANTE:
        // cerrar sesión automáticamente al abrir app
        auth.signOut()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // LOGIN
        btnLogin.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(
                            this,
                            "Acceso concedido",
                            Toast.LENGTH_SHORT
                        ).show()

                        startActivity(Intent(this, MainActivity::class.java))

                    } else {

                        Toast.makeText(
                            this,
                            "Correo o contraseña incorrectos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        // REGISTRO
        btnRegister.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(
                            this,
                            "Usuario registrado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        // cerrar sesión inmediatamente
                        auth.signOut()

                        // limpiar campos
                        etEmail.text.clear()
                        etPassword.text.clear()

                    } else {

                        Toast.makeText(
                            this,
                            "Error: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}