package com.example.smarthome.dataClasses

import android.graphics.drawable.Drawable
import android.media.Image
import kotlinx.serialization.Serializable

@Serializable
data class RoomList(val name : String, val icon : ByteArray, val id : Int)