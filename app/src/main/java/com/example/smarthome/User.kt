package com.example.smarthome

@kotlinx.serialization.Serializable
data class User(val username : String, val password : String, val email : String, val avatar : String?, val home_id : Int?)