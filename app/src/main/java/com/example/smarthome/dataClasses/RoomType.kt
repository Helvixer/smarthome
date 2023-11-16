package com.example.smarthome.dataClasses

import kotlinx.serialization.Serializable


@Serializable
data class RoomType(val r_type_id : Int, val base_name : String, val image : String)