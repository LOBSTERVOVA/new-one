package com.example.mywork.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mywork.HotelModel
import com.example.mywork.Rooms
import com.example.mywork.databinding.RoomLayoutBinding
import com.example.mywork.ui.BookingActivity
import com.example.mywork.ui.HOTEL_ARG
import com.example.mywork.ui.ROOM_ARG
import com.google.gson.Gson

class RecyclerViewRoomsAdapter(val rooms: Rooms, val activity:FragmentActivity, val context: Context, val hotelModel: HotelModel): RecyclerView.Adapter<RoomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = RoomLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return RoomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return rooms.rooms.size
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val currentRoom = rooms.rooms[position]
        with(holder.binding){
            val adapter = SliderAdapter(activity, currentRoom.image_urls)
            viewPager2.adapter = adapter
            roomName.text = currentRoom.name
            pec1.text = currentRoom.peculiarities[0]
            pec2.text = currentRoom.peculiarities[1]
            price1.text = currentRoom.price.toString()
            priceFor.text = currentRoom.price_per
            buttonBook.setOnClickListener {
                val intent = Intent(context, BookingActivity::class.java)
                intent.apply {
                    putExtra(HOTEL_ARG, Gson().toJson(hotelModel))
                    putExtra(ROOM_ARG, Gson().toJson(currentRoom))
                }
                context.startActivity(intent)
            }
        }
    }

}

class RoomViewHolder(val binding: RoomLayoutBinding) : RecyclerView.ViewHolder(binding.root)