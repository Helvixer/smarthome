package com.example.smarthome.utils

import android.content.SharedPreferences
import android.util.Log
import com.example.smarthome.dataClasses.Home
import com.example.smarthome.dataClasses.HomeInsert
import com.example.smarthome.dataClasses.Room
import com.example.smarthome.dataClasses.RoomInsert
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

    suspend fun getSelectedRoom() : Room{
        val res = SBobj.getClient1().postgrest["rooms"].select() { Room::rome_id eq SBobj.selected_room }.decodeSingle<Room>()
        Log.e("LOGGG", res.name)
        return res
    }

    fun setSelectedRoom(room_id : Int){
        SBobj.selected_room = room_id
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

    suspend fun addHome(user_id : String, adress : String){
        val homeInsert = HomeInsert(adress, user_id)
        SBobj.getClient1().postgrest["home"].insert(homeInsert)
    }

}