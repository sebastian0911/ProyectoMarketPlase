package com.example.marketplaceproyecto

import android.os.Bundle
import android.app.ProgressDialog
import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.marketplaceproyecto.databinding.ActivityRegistroEmailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Registro_email : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    private var email = ""
    private var password = ""
    private var r_password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.BtnRegistrar.setOnClickListener {
            validarInfo()
        }
    }

    private fun validarInfo() {
        email = binding.EtEmail.text.toString().trim()
        password = binding.EtPassword.text.toString().trim()
        r_password = binding.EtRPassword.text.toString().trim()

        when {
            email.isEmpty() -> {
                binding.EtEmail.error = "Ingrese email"
                binding.EtEmail.requestFocus()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.EtEmail.error = "Email inválido"
                binding.EtEmail.requestFocus()
            }
            password.isEmpty() -> {
                binding.EtPassword.error = "Ingrese password"
                binding.EtPassword.requestFocus()
            }
            r_password.isEmpty() -> {
                binding.EtRPassword.error = "Repita el password"
                binding.EtRPassword.requestFocus()
            }
            password != r_password -> {
                binding.EtRPassword.error = "No coinciden"
                binding.EtRPassword.requestFocus()
            }
            else -> {
                registrarUsuario()
            }
        }
    }

    private fun registrarUsuario() {
        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {

             llenarInfoBD()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "No se registró el usuario debido a: ${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }

    private fun llenarInfoBD() {

        progressDialog.setMessage("Guardando información")

        val tiempo = Constantes.obtenerTiempoDis()
        val emailUsuario = firebaseAuth.currentUser!!.email
        val uidUsuario = firebaseAuth.uid



        val hashMap = HashMap<String, Any>()
        hashMap["nombres"] = ""
        hashMap["codigoTelefono"] = ""
        hashMap["telefono"] = ""
        hashMap["urlImagenPerfil"] = ""
        hashMap["proveedor"] = "google"
        hashMap["escribiendo"] = ""
        hashMap["tiempo"] = tiempo
        hashMap["online"] = true
        hashMap["email"] = "${emailUsuario}"
        hashMap["uid"] = "${uidUsuario}"
        hashMap["fecha_nac"] = ""

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(uidUsuario!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()


            }

            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se registró debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }


    }
}


