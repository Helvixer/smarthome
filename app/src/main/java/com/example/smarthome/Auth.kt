package com.example.smarthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.smarthome.utils.SBobj
import com.example.smarthome.utils.UserMethods
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.launch

class Auth : AppCompatActivity() {

    //val SB = SBobj.getClient1()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    fun login(view: View) {
        val mail = findViewById<EditText>(R.id.email_edit).text.toString()
        val pass = findViewById<EditText>(R.id.pass_edit).text.toString()
        val tost = Toast.makeText(this, "1", Toast.LENGTH_SHORT)
        val int = Intent(this, CreateCode::class.java)
        lifecycleScope.launch() {
            if (!UserMethods().Auth(mail, pass)){
                tost.setText("Неправильный логин или пароль")
                tost.show()

            }else{
                val sPref = getSharedPreferences("login", MODE_PRIVATE).edit()
                UserMethods().saveUser(sPref, mail, pass)
                startActivity(int)
                finish()
            }
        }
    }

    fun register(view: View) {
        val intentn = Intent(this, Register::class.java)
        startActivity(intentn)
        finish()
    }
}