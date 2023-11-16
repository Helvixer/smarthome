package com.example.smarthome.utils

import android.content.SharedPreferences
import android.util.Log
import com.example.smarthome.dataClasses.Device
import com.example.smarthome.dataClasses.DeviceAdvanced
import com.example.smarthome.dataClasses.DeviceAdvancedDecode
import com.example.smarthome.dataClasses.DeviceInsert
import com.example.smarthome.dataClasses.Home
import com.example.smarthome.dataClasses.HomeInsert
import com.example.smarthome.dataClasses.Room
import com.example.smarthome.dataClasses.RoomInsert
import com.example.smarthome.dataClasses.Type
import com.example.smarthome.dataClasses.UserInsert
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.postgrest
import kotlin.math.log

class UserMethods {

    suspend fun Auth(mail : String, pass : String) : Boolean{
        try {
            SBobj.getClient1().gotrue.loginWith(Email) {
                email = mail
                password = pass
            }
            return true
        }catch (_:Exception){
            return false
        }
    }

    suspend fun SignUp(mail : String, pass : String) : Boolean{
        try {
            SBobj.getClient1().gotrue.signUpWith(Email) {
                email = mail
                password = pass
            }
            return true
        }catch (_:Exception){
            return false
        }
    }

    suspend fun logout(){
        SBobj.getClient1().gotrue.logout()
    }

    suspend fun getUser() : UserInfo?{
        val result : UserInfo? = try{
            SBobj.getClient1().gotrue.retrieveUserForCurrentSession()
        } catch (_:Exception){
            null
        }
        return result
    }

    fun saveUser(sPref : SharedPreferences.Editor, mail : String, pass : String){
        sPref.putString("email", mail)
        sPref.putString("pass", pass)
        sPref.apply()
    }

    fun saveCode(sPref : SharedPreferences.Editor, code : String){
        sPref.putString("code", code)
        sPref.apply()
    }

    suspend fun getUsername() : String {
        return SBobj.getClient1().postgrest["user"].select().decodeSingle<UserInsert>().username
    }

    suspend fun getAvatar() : String? {
        return SBobj.getClient1().postgrest["user"].select().decodeSingle<UserInsert>().avatar
    }

    suspend fun getHome() : Home?{
        return try{
            SBobj.getClient1().postgrest["home"].select(){
                Home::profile_id eq getUser()!!.id
            }.decodeSingle<Home>()
        }catch (_:Exception){
            null
        }
    }

    suspend fun addRoom(r_type : Int, name : String? = null){
        val inser = RoomInsert(getHome()!!.home_id, name, r_type)
        SBobj.getClient1().postgrest["rooms"].insert(inser)
    }

    suspend fun addDevice(type_id : Int, id : String, name : String){
        val name1 = if(name == "")
            null
        else
            name
        val inser = DeviceInsert(getSelectedRoom().rome_id, name1, id, type_id)
        val device_id = SBobj.getClient1().postgrest["devices"].insert(inser).decodeSingle<Device>().device_id
        if(SBobj.getClient1().postgrest["type"].select { Type::type_id eq type_id }.decodeSingle<Type>().values == 2){
            SBobj.getClient1().postgrest["dv_adv"].insert(DeviceAdvanced(device_id, 1))
        }
        /*if(SBobj.getClient1().postgrest["type"].select() { Type::type_id eq type_id }.decodeSingle<Type>().values == 2){

        }*/
    }

    suspend fun getSelectedRoom() : Room{
        val res = SBobj.getClient1().postgrest["rooms"].select() { Room::rome_id eq SBobj.selected_room }.decodeSingle<Room>()
        //Log.e("LOGGG", res.name)
        return res
    }

    suspend fun getSelectedDevice() : Device{
        val res = SBobj.getClient1().postgrest["devices"].select() {Device::device_id eq SBobj.selected_device }.decodeSingle<Device>()
        Log.e("DEVICE", "Selected  device: "+res.device_id)
        return res
    }

    fun setSelectedRoom(room_id : Int){
        SBobj.selected_room = room_id
    }

    fun setSelectedDevice(dv_id : Int){
        SBobj.selected_device = dv_id
    }

    suspend fun saveDevice(value1 : Int, power_state : Boolean, value2 : Int?){
        SBobj.getClient1().postgrest["devices"].update({
            Device::power_state setTo power_state
            Device::value1 setTo value1
        }) { Device::device_id eq SBobj.selected_device }
        if(value2 != null){
            SBobj.getClient1().postgrest["dv_adv"].update(
                {
                    DeviceAdvancedDecode::value2 setTo value2
                }
            ) { DeviceAdvancedDecode::device_id eq SBobj.selected_device }
        }
    }

    suspend fun changeProfile(mail : String, username : String, adress: String){
        if(getUser()!!.email != mail){
            SBobj.getClient1().gotrue.modifyUser(true) {
                email = mail
            }
            Log.e("PROFILE", "mail changed")
        }
        if(getUsername() != username){
            SBobj.getClient1().postgrest["user"].update({
                UserInsert::username setTo  username
            }) { UserInsert::profile_id eq getUser()!!.id }
            Log.e("PROFILE", "username changed")
        }
        if(getHome()!!.adress != adress){
            SBobj.getClient1().postgrest["home"].update({
                Home::adress setTo adress
            }) { Home::profile_id eq getUser()!!.id }
            Log.e("PROFILE", "adress changed")
        }
        //SBobj.getClient1().gotrue.reauthenticate()
        Log.e("PROFILE", "SAVED")
    }

    suspend fun changePowerState(device_id : Int, state : Boolean){
        SBobj.getClient1().postgrest["devices"].update({
            Device::power_state setTo state
        }){ Device::device_id eq device_id }
    }

    suspend fun addHome(user_id : String, adress : String){
        val homeInsert = HomeInsert(adress, user_id)
        SBobj.getClient1().postgrest["home"].insert(homeInsert)
    }

}