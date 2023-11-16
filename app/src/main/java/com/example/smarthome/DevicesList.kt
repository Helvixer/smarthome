package com.example.smarthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.device_adapter
import com.example.myapplication.room_adapter
import com.example.smarthome.dataClasses.Device
import com.example.smarthome.dataClasses.DeviceList
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

class DevicesList : AppCompatActivity() {

    var dvItems: ArrayList<DeviceList> = ArrayList<DeviceList>()
    var dv_array: JSONArray = JSONArray()
    var devices: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devices_list)
        devices = findViewById<RecyclerView>(R.id.dv_list)
        devices!!.layoutManager = GridLayoutManager(this, GridLayoutManager.VERTICAL)

        lifecycleScope.launch {
            findViewById<TextView>(R.id.header_text).setText("Устройства в "+UserMethods().getSelectedRoom().name+"")
            val test = SBobj.getClient1().postgrest["devices"]
                .select(
                    columns = Columns.raw("""device_id, name, type ( type_id, base_name, image ), power_state, value1"""
                    )
                ){  Device::room_id eq SBobj.selected_room }
            Log.e("devicetest", test.body.toString())
            val buf = StringBuilder()
            var line = test.body.toString()
            buf.append(line).append("\n")
            dv_array = JSONArray(buf.toString())
            addItemsFromJSON()

        }
    }

    fun addD(view: View) {}
    fun back(view: View) {
        finish()
    }

    private fun addItemsFromJSON(){
        try{
            lifecycleScope.launch {
                for (i in 0..<dv_array.length()) {
                    var itemObj: JSONObject = dv_array.getJSONObject(i)
                    var testType = itemObj.getJSONObject("type")
                    Log.e("", testType.toString())
                    var img = SBobj.getClient1().storage["dv_icons"].downloadPublic(testType.getString("image"+"blue.png"))
                    var name : String
                    if(itemObj.getString("name") == "null")
                        name = testType.getString("base_name")
                    else
                        name = itemObj.getString("name")
                    var catalog: DeviceList = DeviceList(
                        itemObj.getInt("device_id"),
                        name,
                        itemObj.getInt("type_id"),
                        itemObj.getBoolean("power_state"),
                        itemObj.getInt("value1"),
                        img
                    )
                    dvItems += catalog
                }
                val adapter = device_adapter(dvItems, device_adapter.OnClickListener{ device -> Toast.makeText(applicationContext, device.name, Toast.LENGTH_SHORT).show()})
                devices!!.adapter = adapter
                /*rooms!!.setOnClickListener{

                }*/
                adapter.notifyDataSetChanged()
            }
        } catch (e: Exception){
            Log.e("ERROE", e.toString())
        }
    }
}