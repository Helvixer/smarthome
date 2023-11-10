package com.example.smarthome.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class HomeInsert(val adress : String, val profile_id : String)