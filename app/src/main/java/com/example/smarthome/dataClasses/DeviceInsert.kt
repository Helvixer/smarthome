package com.example.smarthome.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class DeviceInsert (val room_id : Int, val name : String?, val id : String, val type_id : Int, val power_state : Boolean = false, val value1 : Int = 1)