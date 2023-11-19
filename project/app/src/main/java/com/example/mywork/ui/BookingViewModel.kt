package com.example.mywork.ui

import androidx.lifecycle.ViewModel
import com.example.mywork.BookingInfo
import com.example.mywork.adapters.BookingRecyclerAdapter

class BookingViewModel:ViewModel() {
    var adapter:BookingRecyclerAdapter? = null
    var bookInfo:BookingInfo? = null
}