package com.example.smarthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.smarthome.utils.UserMethods
import kotlinx.coroutines.launch

class LoginCode : AppCompatActivity() {

    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_code)
    }

    fun exit(view: View) {
        lifecycleScope.launch {
            UserMethods().logout()
            val sPref = getSharedPreferences("login", MODE_PRIVATE).edit()
            sPref.remove("email")
            sPref.remove("pass")
            sPref.remove("code")
            sPref.apply()
            finish()
        }
    }

    fun num_click(view: View) {
        if(view.getTag().toString() != "dlt")
            setPassword(view.getTag().toString().toInt())
        else
            dltPassword()
    }

    fun dltPassword(){
        if(password.length != 0){
            when(password.length){
                1 -> findViewById<ImageView>(R.id.dot_0).setImageResource(R.drawable.dot_0)
                2 -> findViewById<ImageView>(R.id.dot_1).setImageResource(R.drawable.dot_0)
                3 -> findViewById<ImageView>(R.id.dot_2).setImageResource(R.drawable.dot_0)
                4 -> findViewById<ImageView>(R.id.dot_3).setImageResource(R.drawable.dot_0)
            }
            password = password.substring(0, password.length-1)
        }
        Toast.makeText(this, password, Toast.LENGTH_SHORT).show()
    }

    fun setPassword(num : Int){
        password += num
        //Toast.makeText(this, password, Toast.LENGTH_SHORT).show()
        when(password.length){
            1 -> findViewById<ImageView>(R.id.dot_0).setImageResource(R.drawable.dot_1)
            2 -> findViewById<ImageView>(R.id.dot_1).setImageResource(R.drawable.dot_1)
            3 -> findViewById<ImageView>(R.id.dot_2).setImageResource(R.drawable.dot_1)
            4 -> findViewById<ImageView>(R.id.dot_3).setImageResource(R.drawable.dot_1)
        }
        if(password.length == 4){
            val sPref = getSharedPreferences("login", MODE_PRIVATE)
            val add = Intent(this, AddAdress::class.java)
            val main = Intent(this, MainScreen::class.java)
            if(sPref.getString("code", "") == password) {
                lifecycleScope.launch {
                    if(UserMethods().getHome() != null) {
                        Log.e("TESTHOME", UserMethods().getHome()!!.adress)
                            //main.putExtra("adress", UserMethods().getHome()!!.adress)
                        startActivity(main)
                        finish()
                    }else{
                        startActivity(add)
                        finish()
                    }
                }
            }else{
                Toast.makeText(this, "Неправильный код", Toast.LENGTH_SHORT).show()
                password = ""
                findViewById<ImageView>(R.id.dot_0).setImageResource(R.drawable.dot_0)
                findViewById<ImageView>(R.id.dot_1).setImageResource(R.drawable.dot_0)
                findViewById<ImageView>(R.id.dot_2).setImageResource(R.drawable.dot_0)
                findViewById<ImageView>(R.id.dot_3).setImageResource(R.drawable.dot_0)
            }
        }
    }
}