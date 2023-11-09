package com.example.smarthome

import android.content.Intent
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
import io.github.jan.supabase.gotrue.user.UserSession
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.*
import io.github.jan.supabase.postgrest.postgrest
import io.ktor.util.sha1

class Register : AppCompatActivity() {

    val SB = SBobj.getClient1()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
    /*fun test_pars(view: View){
        lifecycleScope.launch {
            lifecycleScope.launch {
                SB.gotrue.loginWith(Email) {
                    email = "test1@mail.ru"//email_e
                    password = "123456"//pass
                }
                var user = SB.gotrue.retrieveUserForCurrentSession(updateSession = false)
                if(user == null){
                    Toast.makeText(applicationContext, "Произошла ошибка", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(applicationContext, "Зареган", Toast.LENGTH_SHORT).show()
            }
        }
    }*/

    fun register(view: View) {
        val email_e : String = findViewById<EditText?>(R.id.email_edit).text.toString();
        val username : String = findViewById<EditText?>(R.id.login_edit).text.toString();
        val pass : String = findViewById<EditText?>(R.id.pass_edit).text.toString();

        val emailPattern : Regex = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        if(email_e == "" || username == "" || pass == "" || !email_e.matches(emailPattern) || pass.length < 6){
            Toast.makeText(this, "Проверьте правильность введенных данных!", Toast.LENGTH_SHORT).show()
            return
        }
        val toast: Toast = Toast.makeText(this, "1", Toast.LENGTH_SHORT)
        val code = Intent(this, CreateCode::class.java)
        try {
            lifecycleScope.launch {
                try {
                    SB.gotrue.signUpWith(Email) {
                        email = email_e
                        password = pass
                    }
                    var user = SB.gotrue.retrieveUserForCurrentSession(updateSession = false)
                    if (user == null) {
                        toast.setText("Произошла ошибка")
                        toast.show()
                        return@launch
                    }
                    SB.postgrest["user"].insert(User(user.id, username, null))
                    val sPref = getSharedPreferences("login", MODE_PRIVATE).edit()
                    sPref.putString("email", email_e)
                    sPref.putString("pass", pass)
                    sPref.apply()
                    // Toast.makeText(applicationContext, "Зареган", Toast.LENGTH_SHORT).show()
                    toast.setText("Зареган")
                    toast.show()
                    startActivity(code)
                }catch (_:Exception){
                    toast.setText("Данный E-Mail уже зарегестрирован")
                    toast.show()
                }
            }
        }catch (_: Exception){
            toast.setText("Произошла ошибка")
            toast.show()
        }

    }
}