package com.example.smarthome

import android.health.connect.datatypes.units.Length
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.*
import io.github.jan.supabase.postgrest.postgrest
import io.ktor.util.sha1

class Register : AppCompatActivity() {

    val appCon = this
    val client = SupaClient().getClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        lifecycleScope.launch {
            //val client = SupaClient().getClient()
            client.gotrue.loginWith(Email) {
                email = "test1@mail.ru"//email_e
                password = "123456"//pass
            }
            Log.e("!!", client.toString())

//            SupaClient().client.gotrue.logout()
            var user = client.gotrue.retrieveUserForCurrentSession(updateSession = false)

            Log.e("!!!!!!", user.email.toString())
            //Log.e("!!!!!!", user2.email.toString())
/*            val client = createSupabaseClient(
                supabaseUrl = "https://sqrerppgkdgwjqprutgz.supabase.co",

                supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNxcmVycHBna2Rnd2pxcHJ1dGd6Iiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTU3MjM5NTAsImV4cCI6MjAxMTI5OTk1MH0.5THfzTq00a32NMZiC-Hqt0KNWlBNBQi1wRjxXZmsLKs"
            ) {
                install(GoTrue)
                install(Postgrest)
                //install other modules
            }
            val user2 = client.gotrue.loginWith(Email) {
                email = "test1@mail.ru"//email_e
                password = "123456"//pass
            }

//            SupaClient().client.gotrue.logout()
            var user = client.gotrue.retrieveUserForCurrentSession(updateSession = false)
            Log.e("!!!!!!", user.email.toString())*/


        }
    }

    fun register(view: View) {
        val email_e : String = findViewById<EditText?>(R.id.email_edit).text.toString();
        val username : String = findViewById<EditText?>(R.id.login_edit).text.toString();
        val pass : String = findViewById<EditText?>(R.id.pass_edit).text.toString();
/*
        val emailPattern : Regex = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        if(email_e == "" || username == "" || pass == "" || !email_e.matches(emailPattern) || pass.length < 6){
            Toast.makeText(this, "Проверьте правильность введенных данных!", Toast.LENGTH_SHORT).show()
            return
        }
*/

        lifecycleScope.launch {
/*            val user2 = SupaClient().client.gotrue.loginWith(Email) {
                email = "test1@mail.ru"//email_e
                password = "123456"//pass
            }

            SupaClient().client.gotrue.retrieveUserForCurrentSession(updateSession = false)
            Log.e("!12", user2.toString())

            val us = SupaClient().client.gotrue.retrieveUserForCurrentSession()
            Log.e("!12", us.email.toString())



           if(user == null){
                Toast.makeText(applicationContext, "Произошла ошибка", Toast.LENGTH_SHORT).show()
            }
            SupaClient().client.postgrest["user"].insert(User(user.id, username, null))
            Toast.makeText(applicationContext, "Зареган", Toast.LENGTH_SHORT).show()*/
        }
    }
}