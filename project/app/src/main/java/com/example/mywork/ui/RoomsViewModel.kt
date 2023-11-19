package com.example.mywork.ui

import androidx.lifecycle.ViewModel
import com.example.mywork.Rooms
import com.example.mywork.adapters.RecyclerViewRoomsAdapter

class RoomsViewModel:ViewModel() {
    var rooms:Rooms? = null
    var adapter:RecyclerViewRoomsAdapter? = null
}