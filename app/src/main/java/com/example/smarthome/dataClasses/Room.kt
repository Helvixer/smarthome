package com.example.smarthome.dataClasses

import kotlinx.serialization.Serializable

data class Room(val room_id : Int, val home_id : Int, val name : String, val r_type : Int)

@Serializable
data class TestDec(val room_id: Int, val name: String, val r_type_id: Int, val base_name: String)

/*
rome_id,
name,
r_types (
r_type_id,
base_name
)*/
