package com.example.smarthome.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class Type(val type_id : Int, val image : String, val base_name : String, val values : Int, val value_name : String, val switch_type : Int)