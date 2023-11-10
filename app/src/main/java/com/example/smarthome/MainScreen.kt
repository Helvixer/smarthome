package com.example.smarthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.smarthome.utils.UserMethods
import kotlinx.coroutines.launch

class MainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        lifecycleScope.launch {
            findViewById<TextView>(R.id.main_adress).text = UserMethods().getHome()!!.adress
        }
    }

    fun setting(view: View) {
        val int = Intent(this, Profile::class.java)
        startActivity(int)
        finish()
    }


}