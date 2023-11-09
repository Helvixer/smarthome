package com.example.smarthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class Auth : AppCompatActivity() {

    val SB = SBobj.getClient1()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    fun login(view: View) {
        lifecycleScope.launch() {
            Log.e("test1", SB.gotrue.retrieveUserForCurrentSession().email.toString())
        }
        /*val intentn = Intent(this, CreateCode::class.java)
        startActivity(intentn)*/
    }

    fun register(view: View) {
        val intentn = Intent(this, Register::class.java)
        startActivity(intentn)
    }
}