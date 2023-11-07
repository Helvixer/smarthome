package com.example.smarthome

import android.health.connect.datatypes.units.Length
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import io.github.jan.supabase.postgrest.postgrest

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun register(view: View) {
        val email : String = findViewById<EditText?>(R.id.email_edit).text.toString();
        val username : String = findViewById<EditText?>(R.id.login_edit).text.toString();
        val pass : String = findViewById<EditText?>(R.id.pass_edit).text.toString();
        val emailPattern : Regex = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        if(email == "" || username == "" || pass == "" || !email.matches(emailPattern) || pass.length < 6){
            Toast.makeText(this, "Проверьте правильность введенных данных!", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            SupaClient().client.postgrest["user"].insert(User(username, pass, email, null, null))
            Toast.makeText(Register().applicationContext, "Зареган", Toast.LENGTH_SHORT).show()
        }
    }
}