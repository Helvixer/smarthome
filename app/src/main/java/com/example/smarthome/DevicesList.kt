package com.example.smarthome

import android.content.Intent
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
import com.example.smarthome.dataClasses.RoomType
import com.example.smarthome.dataClasses.Type
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
        devices!!.layoutManager = GridLayoutManager(this, 2)

        lifecycleScope.launch {
            val room = UserMethods().getSelectedRoom()
            var name = room.name
            if (name == null){
                name = SBobj.getClient1().postgrest["r_types"].select { Room::r_type_id eq room.r_type_id}.decodeSingle<RoomType>().base_name
            }
            findViewById<TextView>(R.id.header_text).setText("Устройства в ''"+name+"''")
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

    fun addD(view: View) {
        val int = Intent(this, AddDevice::class.java)
        startActivity(int)
        finish()

    }
    fun back(view: View) {
        val int = Intent(this, MainScreen::class.java)
        startActivity(int)
        finish()
    }

    private fun addItemsFromJSON(){
        try{
            var device_id = 0
            lifecycleScope.launch {
                for (i in 0..<dv_array.length()) {
                    var itemObj: JSONObject = dv_array.getJSONObject(i)
                    var testType = itemObj.getJSONObject("type")
                    Log.e("", testType.toString())
                    var img = SBobj.getClient1().storage["dv_icons"].downloadPublic(testType.getString("image")+"blue.png")
                    var name : String
                    if(itemObj.getString("name") == "null")
                        name = testType.getString("base_name")
                    else
                        name = itemObj.getString("name")
                    var catalog: DeviceList = DeviceList(
                        itemObj.getInt("device_id"),
                        name,
                        testType.getInt("type_id"),
                        itemObj.getBoolean("power_state"),
                        itemObj.getInt("value1"),
                        img
                    )
                    dvItems += catalog
                    device_id = itemObj.getInt("device_id")
                }
                val adapter = device_adapter(dvItems, device_adapter.OnClickListener{ device -> goDevice(device.device_id)}, device_adapter.OnCheckedEvent{ switch -> changeState(device_id, switch.isChecked)})
                devices!!.adapter = adapter
                /*rooms!!.setOnClickListener{

                }*/
                adapter.notifyDataSetChanged()
            }
        } catch (e: Exception){
            Log.e("ERROE", e.toString())
        }
    }

    fun changeState(device_id : Int, state : Boolean){
        val toast = Toast.makeText(applicationContext, "Изменено", Toast.LENGTH_SHORT)
        lifecycleScope.launch {
            UserMethods().changePowerState(device_id, state)
            toast.show()
        }
    }

    fun goDevice(device_id: Int){
        UserMethods().setSelectedDevice(device_id)
        startActivity(Intent(applicationContext, DeviceSettings::class.java))
        finish()
    }
}