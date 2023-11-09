package com.example.smarthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast

class CreateCode : AppCompatActivity() {
    var password = ""
    var a = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_code)
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
        Toast.makeText(this, password, Toast.LENGTH_SHORT).show()
        if(password.length == 4){
            val add = Intent(this, AddAdress::class.java)
            startActivity(add)
            finish()
        }
        when(password.length){
            1 -> findViewById<ImageView>(R.id.dot_0).setImageResource(R.drawable.dot_1)
            2 -> findViewById<ImageView>(R.id.dot_1).setImageResource(R.drawable.dot_1)
            3 -> findViewById<ImageView>(R.id.dot_2).setImageResource(R.drawable.dot_1)
            4 -> findViewById<ImageView>(R.id.dot_3).setImageResource(R.drawable.dot_1)
        }
    }
}