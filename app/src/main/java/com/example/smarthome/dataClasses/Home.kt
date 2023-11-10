package com.example.smarthome.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class Home(val home_id : Int, val adress : String, val profile_id : String)