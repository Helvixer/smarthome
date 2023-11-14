package com.example.smarthome.dataClasses

import kotlinx.serialization.Serializable

data class RoomInsert(val home_id : Int, val name : String, val r_type : Int)

/*
rome_id,
name,
r_types (
r_type_id,
base_name
)*/
