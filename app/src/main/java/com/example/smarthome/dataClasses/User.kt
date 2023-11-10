package com.example.smarthome.dataClasses

@kotlinx.serialization.Serializable
data class User(val profile_id : String, val username : String, val avatar : String?)