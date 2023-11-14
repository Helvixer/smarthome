package com.example.smarthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.content.res.AppCompatResources

class AddRoom : AppCompatActivity() {

    var select_type = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_room)
        findViewById<ImageButton>(R.id.living).background = AppCompatResources.getDrawable(this,R.drawable.active_btn)
    }

    fun back(view: View) {}
    fun save(view: View) {}
    fun type(view: View) {
        setActiveBtn(view.getTag().toString())
    }

    fun setActiveBtn(tag : String) {
        if (tag == "living") {
            findViewById<ImageButton>(R.id.living).background =
                AppCompatResources.getDrawable(this, R.drawable.active_btn)
            findViewById<ImageButton>(R.id.kitchen).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.bathroom).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.cabinet).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.bed).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.zal).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            select_type = 1
        } else if (tag == "bathroom") {
            findViewById<ImageButton>(R.id.living).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.bathroom).background =
                AppCompatResources.getDrawable(this, R.drawable.active_btn)
            findViewById<ImageButton>(R.id.cabinet).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.bed).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.kitchen).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.zal).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            select_type = 2
        } else if (tag == "cabinet") {
            findViewById<ImageButton>(R.id.living).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.kitchen).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.bathroom).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.cabinet).background =
                AppCompatResources.getDrawable(this, R.drawable.active_btn)
            findViewById<ImageButton>(R.id.bed).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.zal).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            select_type = 3
        } else if (tag == "bed") {
            findViewById<ImageButton>(R.id.living).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.kitchen).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.bathroom).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.cabinet).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.bed).background =
                AppCompatResources.getDrawable(this, R.drawable.active_btn)
            findViewById<ImageButton>(R.id.zal).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            select_type = 4
        } else if (tag == "kitchen") {
            findViewById<ImageButton>(R.id.living).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.kitchen).background =
                AppCompatResources.getDrawable(this, R.drawable.active_btn)
            findViewById<ImageButton>(R.id.bathroom).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.cabinet).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.bed).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.zal).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            select_type = 5
        } else if (tag == "zal") {
            findViewById<ImageButton>(R.id.living).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.kitchen).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.bathroom).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.cabinet).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.bed).background =
                AppCompatResources.getDrawable(this, R.drawable.unactive_btn)
            findViewById<ImageButton>(R.id.zal).background =
                AppCompatResources.getDrawable(this, R.drawable.active_btn)
            select_type = 6
        }
    }
}