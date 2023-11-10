package com.example.smarthome

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.smarthome.utils.SBobj
import com.example.smarthome.utils.UserMethods
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        val sPref = getSharedPreferences("login", MODE_PRIVATE)
        var res : Boolean = false
        val MainA = Intent(applicationContext, LoginCode::class.java)
        //sPref.edit().putString("email", "blabla").apply()
        //sPref.edit().remove("email").apply()
        val mail : String? = sPref.getString("email", "")
        if (mail != "") {
            lifecycleScope.launch {
                UserMethods().Auth(mail.toString(), sPref.getString("pass", "").toString())
                startActivity(MainA)
                Log.e("TESTLOG", SBobj.getClient1().gotrue.retrieveUserForCurrentSession().email.toString())
                finish()
            }
        }else{
            val timer = object: CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    val MainB = Intent(applicationContext, Auth::class.java)
                    startActivity(MainB)
                    finish()
                }
            }
            timer.start()
        }
    }
}