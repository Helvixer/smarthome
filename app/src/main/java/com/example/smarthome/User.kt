package com.example.smarthome

import java.util.UUID

@kotlinx.serialization.Serializable
data class User(val id : String, val username : String, val avatar : String?)