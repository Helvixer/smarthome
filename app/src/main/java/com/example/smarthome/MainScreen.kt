package com.example.smarthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.room_adapter
import com.example.smarthome.dataClasses.Room
import com.example.smarthome.dataClasses.RoomList
import com.example.smarthome.dataClasses.TestDec
import com.example.smarthome.utils.SBobj
import com.example.smarthome.utils.UserMethods
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder

class MainScreen : AppCompatActivity() {

    var roomItems: ArrayList<RoomList> = ArrayList<RoomList>()
    var room_array: JSONArray = JSONArray()
    var rooms: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        rooms = findViewById<RecyclerView>(R.id.list)
        rooms!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        lifecycleScope.launch {
            findViewById<TextView>(R.id.main_adress).text = UserMethods().getHome()!!.adress
            val test = SBobj.getClient1().postgrest["rooms"]
                .select(
                    columns = Columns.raw("""name, r_types ( r_type_id, base_name, image )"""
                    )
                ){  Room::home_id eq UserMethods().getHome()!!.home_id }
            Log.e("roomtest", test.body.toString())
            val buf = StringBuilder()
            var line = test.body.toString()
            buf.append(line).append("\n")
            room_array = JSONArray(buf.toString())
            addItemsFromJSON()

        }
    }

    fun setting(view: View) {
        val int = Intent(this, Profile::class.java)
        startActivity(int)
        finish()
    }

    private fun addItemsFromJSON(){
        try{
            lifecycleScope.launch {
                for (i in 0..<room_array.length()) {
                    var itemObj: JSONObject = room_array.getJSONObject(i)
                    var testType = itemObj.getJSONObject("r_types")
                    Log.e("", testType.toString())
                    var img = SBobj.getClient1().storage["rooms"].downloadPublic(testType.getString("image"))
                    var catalog: RoomList = RoomList(
                        itemObj.getString("name"),
                        img
                        )
                    roomItems += catalog
                }
                val adapter = room_adapter(roomItems)
                rooms!!.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        } catch (e: Exception){
            Log.e("ERROE", e.toString())
        }
    }

}