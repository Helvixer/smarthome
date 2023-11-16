package com.example.smarthome.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class Room(val rome_id : Int, val home_id : Int, val name : String?, val r_type_id : Int)

/*
rome_id,
name,
r_types (
r_type_id,
base_name
)*/
