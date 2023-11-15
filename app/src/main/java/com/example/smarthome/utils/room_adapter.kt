package com.example.myapplication

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.AddRoom
import com.example.smarthome.R
import com.example.smarthome.dataClasses.Room
import com.example.smarthome.dataClasses.RoomList


class room_adapter(private var RoomList: List<RoomList>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<room_adapter.MyViewHolder>() {



    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.findViewById(R.id.room_name)
        var image: ImageView = itemView.findViewById(R.id.room_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.room_list, parent, false)
        return  MyViewHolder(v)
        //Log.e("!!!!!!ad", "wwfdewd")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val api = RoomList[position]
        holder.name.text = api.name.toString()
        /*val bucket = SBobj.getClient1().storage["avatars"]
        val bytes = bucket.downloadAuthenticated("test.png")*/
        val image1: Drawable = BitmapDrawable(BitmapFactory.decodeByteArray(api.icon, 0, api.icon.size))
        holder.image.setImageDrawable(image1)
      //  holder.itemView.setOnClickListener()
        holder.itemView.setOnClickListener(View.OnClickListener {
            onClickListener.onClick(api)
            Log.e("!", "!")
        })
        //Log.e("!!!!!!ad", "wwfde")
    }

    override fun getItemCount(): Int {
        Log.e("!!!!!!ad", "wwf")
        return RoomList.size
    }

    fun interface OnRoomClickListener{
         fun onRoomClick(room: Room, position: Int)
    }
    class OnClickListener(val clickListener: (meme: RoomList) -> Unit) {
        fun onClick(meme: RoomList) = clickListener(meme)
    }
}
