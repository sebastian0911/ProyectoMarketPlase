package com.example.marketplaceproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marketplaceproyecto.Fragmentos.FragmentChats
import com.example.marketplaceproyecto.Fragmentos.FragmentCuenta
import com.example.marketplaceproyecto.Fragmentos.FragmentInicio
import com.example.marketplaceproyecto.Fragmentos.FragmentMisAnuncios
import com.example.marketplaceproyecto.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private  lateinit var  firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        verFragmentInicio()

        binding.BottonNV.setOnItemSelectedListener {item->
            when(item.itemId){
                R.id.Item_Inicio->{
                    verFragmentInicio()
                    true
                }
                R.id.Item_Chats->{
                    verFragmentChats()
                    true
                }
                R.id.Item_Mis_Anuncios->{
                    verFragmentMisAnuncios()
                    true
                }
                R.id.Item_Cuenta->{
                    verFragmentCuenta()
                    true
                }
                else->{
                    false
                }
            }
        }
    }

    private fun comprobarSesion (){
        if (firebaseAuth.currentUser == null){
            startActivity(Intent(this, OpcionesLogin:: class.java))
            finishAffinity()
        }
    }

    private fun verFragmentInicio(){
        binding.TituloRL.text = "Inicio"
        val fragment = FragmentInicio()
        val fragmentTransient = supportFragmentManager.beginTransaction()
        fragmentTransient.replace(binding.FragmentL1.id, fragment,"FragmentInicio")
        fragmentTransient.commit()
    }
    private fun verFragmentChats(){
        binding.TituloRL.text = "Chats"
        val fragment = FragmentChats()
        val fragmentTransient = supportFragmentManager.beginTransaction()
        fragmentTransient.replace(binding.FragmentL1.id, fragment,"FragmentChats")
        fragmentTransient.commit()
    }
    private fun verFragmentMisAnuncios(){
        binding.TituloRL.text = "Mis Anuncios"
        val fragment = FragmentMisAnuncios()
        val fragmentTransient = supportFragmentManager.beginTransaction()
        fragmentTransient.replace(binding.FragmentL1.id, fragment,"FragmentMisAnuncios")
        fragmentTransient.commit()
    }
    private fun verFragmentCuenta(){
        binding.TituloRL.text = "Cuenta"
        val fragment = FragmentCuenta()
        val fragmentTransient = supportFragmentManager.beginTransaction()
        fragmentTransient.replace(binding.FragmentL1.id, fragment,"FragmentCuenta")
        fragmentTransient.commit()
    }
}