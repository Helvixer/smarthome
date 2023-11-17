package com.example.smarthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myapplication.room_adapter
import com.example.smarthome.dataClasses.Room
import com.example.smarthome.dataClasses.RoomList
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
        rooms!!.layoutManager = GridLayoutManager(this, GridLayoutManager.VERTICAL)
        /*val adressPattern : Regex = Regex("г\\. +[А-я]+, ул\\. +[А-я]+, д\\. +[0-9]+")
        if(adressPattern.matches("г. Омск, ул. Ленина, 24"))
            Log.e("TESTREGEX", "прошло")*/

        /*room_adapter.onRoomClickListener : OnRoo =  room_adapter.OnRoomClickListener {
            fun onRoomClick(room : Room){
                Toast.makeText(this.applicationContext, room.name, Toast.LENGTH_SHORT).show()
            }
        }*/

        lifecycleScope.launch {
            findViewById<TextView>(R.id.main_adress).text = UserMethods().getHome()!!.adress
            val test = SBobj.getClient1().postgrest["rooms"]
                .select(
                    columns = Columns.raw("""rome_id, name, r_types ( r_type_id, base_name, image )"""
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
                    var img = SBobj.getClient1().storage["test"].downloadPublic(testType.getString("image"))
                    var name : String
                    if(itemObj.getString("name") == "null")
                        name = testType.getString("base_name")
                    else
                        name = itemObj.getString("name")
                    var catalog: RoomList = RoomList(
                        name,
                        img,
                        itemObj.getInt("rome_id")
                        )
                    roomItems += catalog
                }
                val adapter = room_adapter(roomItems, room_adapter.OnClickListener{ room -> goRoom(room.id)})
                rooms!!.adapter = adapter
                /*rooms!!.setOnClickListener{

                }*/
                adapter.notifyDataSetChanged()
            }
        } catch (e: Exception){
            Log.e("ERROE", e.toString())
        }
    }

    fun goRoom(room_id : Int){
        Log.e("ROOMID", room_id.toString())
        UserMethods().setSelectedRoom(room_id)
        Log.e("ROOMID", SBobj.selected_room.toString())
        val int = Intent(this, DevicesList::class.java)
        startActivity(int)
    }

    fun addR(view: View) {
        val intent = Intent(this, AddRoom::class.java)
        startActivity(intent)
        finish()
    }

    /*fun selectRoom(view: View) {
        view.
    }*/

}