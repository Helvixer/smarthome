package com.example.smarthome.dataClasses

import kotlinx.serialization.Serializable

@Serializable
class DeviceAdvancedDecode (val device_id : Int, val value2 : Int, val value_name : String, val switch_type : Int)