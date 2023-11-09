package com.example.smarthome

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        val sPref = getSharedPreferences("login", MODE_PRIVATE)
        //sPref.edit().putString("email", "blabla").apply()
        //sPref.edit().remove("email").apply()
        val test : String? = sPref.getString("email", "")
        if (test != "") {
            lifecycleScope.launch {
                SBobj.getClient1().gotrue.loginWith(Email){
                    email = sPref.getString("email", "").toString()
                    password = sPref.getString("pass", "").toString()
                }
                Log.e("TESTLOG", SBobj.getClient1().gotrue.retrieveUserForCurrentSession().email.toString())
            }
        }else{
            Log.e("TEST!1", "null")
        }
        val timer = object: CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val MainA = Intent(applicationContext, Auth::class.java)
                startActivity(MainA)
            }
        }
        timer.start()
    }
}