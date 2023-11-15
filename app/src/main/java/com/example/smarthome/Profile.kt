package com.example.smarthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.example.smarthome.utils.UserMethods
import kotlinx.coroutines.launch

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        lifecycleScope.launch {
            val user = UserMethods().getUser()
            findViewById<EditText>(R.id.email_edit).setText(user!!.email)
            findViewById<EditText>(R.id.login_edit).setText(UserMethods().getUsername())
            findViewById<EditText>(R.id.adress_edit).setText(UserMethods().getHome()!!.adress)
            if(UserMethods().getAvatar() == null){
                Log.e("PROFTEST", "AVATAR NOT FOUND")
            }
        }
    }

    fun back(view: View) {
        finish()
    }

    fun exit(view: View) {
        val int = Intent(this, Auth::class.java)
        lifecycleScope.launch {
            UserMethods().logout()
            val sPref = getSharedPreferences("login", MODE_PRIVATE).edit()
            sPref.remove("email")
            sPref.remove("pass")
            sPref.remove("code")
            sPref.apply()
            startActivity(int)
            finish()
        }
    }

    fun save(view: View) {
        val mail = findViewById<EditText>(R.id.email_edit).text.toString()
        val uname = findViewById<EditText>(R.id.login_edit).text.toString()
        val adress = findViewById<EditText>(R.id.adress_edit).text.toString()
        lifecycleScope.launch {
            UserMethods().changeProfile(mail, uname, adress)
        }
    }
}