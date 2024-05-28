package com.example.marketplaceproyecto.Fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.marketplaceproyecto.Constantes
import com.example.marketplaceproyecto.OpcionesLogin
import com.example.marketplaceproyecto.databinding.FragmentCuentaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentCuenta : Fragment() {

    private lateinit var binding: FragmentCuentaBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCuentaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        leerInfo()

        binding.BtnCerrarSesion.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(mContext, OpcionesLogin::class.java))
            activity?.finishAffinity()
        }
    }

    private fun leerInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nombres = "${snapshot.child("nombres").value}"
                val email = "${snapshot.child("email").value}"
                var f_nac = "${snapshot.child("fecha_nac").value}"
                val imagen = "${snapshot.child("urlImagenPerfil").value}"
                var tiempo = "${snapshot.child("tiempo").value}"
                val telefono = "${snapshot.child("telefono").value}"
                val codTelefono = "${snapshot.child("codigoTelefono").value}"
                val proveedor = "${snapshot.child("proveedor").value}"

                val cod_tel = "$codTelefono $telefono"

                if (tiempo == "null") {
                    tiempo = "0"
                }

                val for_tiempo = Constantes.obtenerFecha(tiempo.toLong())

                //Setear info
                binding.TvEmail.text = email
                binding.TvNombres.text = nombres
                binding.TvNacimiento.text = f_nac
                binding.TvTelefono.text = cod_tel
                binding.TvMienbro.text = for_tiempo

                //Setear info


                if (proveedor == "Email") {
                    val esVerificado = firebaseAuth.currentUser!!.isEmailVerified
                    if (esVerificado) {
                        binding.TvEstadoCuenta.text = "Verificado"
                    } else {

                    binding.TvEstadoCuenta.text = "No verificado"
                }
            }  else{
                binding.TvEstadoCuenta.text = "Verificado"
            }




            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(mContext, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
