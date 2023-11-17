package com.example.smarthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.smarthome.utils.UserMethods
import kotlinx.coroutines.launch

class AddAdress : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_adress)
    }

    fun addHome1(view: View) {
        val adr = findViewById<EditText>(R.id.edit_adress).text.toString()
        val int = Intent(this, MainScreen::class.java)
        val adressPattern : Regex = Regex("г\\. +[А-я]+, ул\\. +[А-я]+, д\\. +[0-9]+")
        if(adressPattern.matches(adr)){
            lifecycleScope.launch {
                val user = UserMethods().getUser()
                UserMethods().addHome(user!!.id, adr)
                startActivity(int)
                Log.e("HOMETEST", UserMethods().getHome()!!.adress)
                finish()
            }
        }else
            Toast.makeText(applicationContext, "Проверьте правильность введенного адреса!", Toast.LENGTH_SHORT).show()

    }
}