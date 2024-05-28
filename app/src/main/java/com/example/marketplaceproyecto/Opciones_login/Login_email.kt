package com.example.marketplaceproyecto.Opciones_login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Images
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.marketplaceproyecto.MainActivity
import com.example.marketplaceproyecto.Registro_email
import com.example.marketplaceproyecto.databinding.ActivityLoginEmailBinding
import com.google.firebase.auth.FirebaseAuth
import org.intellij.lang.annotations.Pattern



class Login_email : AppCompatActivity() {

    private lateinit var binding: ActivityLoginEmailBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progresDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progresDialog = ProgressDialog(this)
        progresDialog.setTitle("Espere por favor")
        progresDialog.setCanceledOnTouchOutside(false)

        binding.BtnIngresar.setOnClickListener {
            validarInfo()
        }

        binding.TxtRegistrarme.setOnClickListener {
            startActivity(Intent(this@Login_email, Registro_email::class.java))
        }
    }

    private var email = ""
    private var password = ""

    private fun validarInfo() {
        email = binding.EtEmail.text.toString().trim()
        password = binding.EtPassword.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EtEmail.error = "Email invalido"
            binding.EtEmail.requestFocus()
        }

        else if (email.isEmpty()){
            binding.EtEmail.error = "Ingrese el email "
            binding.EtEmail.requestFocus()
        }

        else if (password.isEmpty()){
            binding.EtPassword.error = "Ingrese password "
            binding.EtPassword.requestFocus()
        }else{
            loginUsuario()
        }
    }

    private fun loginUsuario() {
        progresDialog.setMessage("Ingresando")
        progresDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progresDialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
                Toast.makeText(
                    this,
                    "Binevenido",
                    Toast.LENGTH_SHORT
                ).show()

            }

            .addOnFailureListener {e->
                progresDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se pudo iniciar sesion a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }
}
