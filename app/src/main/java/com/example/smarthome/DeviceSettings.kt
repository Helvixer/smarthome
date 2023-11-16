package com.example.smarthome

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.lifecycleScope
import com.example.smarthome.dataClasses.DeviceAdvanced
import com.example.smarthome.dataClasses.DeviceAdvancedDecode
import com.example.smarthome.dataClasses.Type
import com.example.smarthome.utils.SBobj
import com.example.smarthome.utils.UserMethods
import com.google.android.material.slider.Slider
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch


class DeviceSettings : AppCompatActivity() {

    lateinit var slider2 : Slider
    var slider_null : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_settings)
        val text = TextView(this)
        slider2 = Slider(this)
        lifecycleScope.launch {
            val device = UserMethods().getSelectedDevice()
            val type = SBobj.getClient1().postgrest["type"].select { Type::type_id eq device.type_id }.decodeSingle<Type>()
            if(device.name == null)
                findViewById<TextView>(R.id.header).text = type.base_name
            else
                findViewById<TextView>(R.id.header).text = device.name
            findViewById<TextView>(R.id.dv_name).text = type.base_name
            val value_text = findViewById<TextView>(R.id.value1_name)
            val slider1 = findViewById<Slider>(R.id.slider1)
            val bytes = SBobj.getClient1().storage["dv_icons"].downloadPublic(type.image+"blue.png")
            val icon : Drawable = BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
            findViewById<ImageView>(R.id.dv_icon).setImageDrawable(icon)
            findViewById<SwitchCompat>(R.id.dv_power).isChecked = device.power_state
            if(type.switch_type == 1){
                value_text.text = device.value1.toString()+"% "+type.value_name

                slider1.value = device.value1.toFloat()
                slider1.addOnChangeListener(Slider.OnChangeListener{slider, value, fromUser -> value_text.text = value.toInt().toString()+"% "+type.value_name})
            }else{
                value_text.text = device.value1.toString()+"°C "+type.value_name
                slider1.value = device.value1.toFloat()
                slider1.valueFrom = 0F
                slider1.valueTo = 35F
                slider1.stepSize = 1F
                slider1.addOnChangeListener(Slider.OnChangeListener{slider, value, fromUser -> value_text.text = value.toInt().toString()+"°C "+type.value_name })
            }
            if(type.values == 2){
                val layout = findViewById<LinearLayout>(R.id.linear)
                val lpText = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text.layoutParams = lpText
                text.typeface = resources.getFont(R.font.montserrat_bold)
                text.textSize = 14F
                text.setTextColor(resources.getColor(R.color.gray))

                val lpSlide = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                slider2.layoutParams = lpSlide
                slider2.thumbRadius = slider1.thumbRadius
                slider2.thumbTintList = slider1.thumbTintList
                slider2.trackActiveTintList = slider1.thumbTintList

                val dv_adv = SBobj.getClient1().postgrest["dv_adv"].select { DeviceAdvanced::device_id eq device.device_id }.decodeSingle<DeviceAdvancedDecode>()
                Log.e("BABAHA", dv_adv.switch_type.toString())
                if(dv_adv.switch_type == 1){
                    text.text = dv_adv.value2.toString()+"% "+dv_adv.value_name
                    slider2.valueFrom = 0F
                    slider2.valueTo = 100F
                    slider2.value = dv_adv.value2.toFloat()
                    slider2.addOnChangeListener(Slider.OnChangeListener{slider, value, fromUser -> text.text = value.toInt().toString()+"% "+dv_adv.value_name})
                }else{
                    text.text = dv_adv.value2.toString()+"°C "+dv_adv.value_name
                    slider2.value = device.value1.toFloat()
                    slider2.valueFrom = 0F
                    slider2.valueTo = 35F
                    slider2.stepSize = 1F
                    slider2.addOnChangeListener(Slider.OnChangeListener{slider, value, fromUser -> text.text = value.toInt().toString()+"°C "+dv_adv.value_name})
                }
                layout.addView(text)
                layout.addView(slider2)
                slider_null = false
            }
        }
    }

    fun back(view: View) {
        val int = Intent(this, DevicesList::class.java)
        lifecycleScope.launch {
            var value2 : Int? = null
            if(!slider_null){
                value2 = slider2.value.toInt()
            }
            UserMethods().saveDevice(findViewById<Slider>(R.id.slider1).value.toInt(), findViewById<SwitchCompat>(R.id.dv_power).isChecked, value2)
            startActivity(int)
            finish()
        }
    }
}