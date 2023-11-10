package com.example.smarthome.dataClasses

@kotlinx.serialization.Serializable
data class UserInsert(val profile_id : String, val username : String, val avatar : String?)