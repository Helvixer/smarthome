package com.example.smarthome.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class Device (val device_id : Int, val room_id : Int, val name : String?, val id : String, val type_id : Int, val power_state : Boolean, val value1 : Int)