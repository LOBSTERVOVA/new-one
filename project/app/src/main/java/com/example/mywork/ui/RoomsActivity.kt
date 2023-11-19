package com.example.mywork.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mywork.HotelModel
import com.example.mywork.adapters.RecyclerViewRoomsAdapter
import com.example.mywork.databinding.ActivityRoomsBinding
import com.example.mywork.framework.DaggerMyRoomComponent
import com.example.mywork.framework.MyDaggerRoomModule
import com.example.mywork.getHotel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import javax.inject.Inject


class RoomsActivity : AppCompatActivity() {
    lateinit var hotelModel: HotelModel
    private val viewModel by viewModels<RoomsViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RoomsViewModel() as? T ?: throw IllegalStateException()
            }
        }
    }

    @Inject
    lateinit var adapter: RecyclerViewRoomsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRoomsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.imageView5.setOnClickListener {
            finish()
        }
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        hotelModel = Gson().fromJson(intent.getStringExtra(HOTEL_ARG)!!, HotelModel::class.java)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        binding.hotelName.text = hotelModel.name
        lifecycleScope.launch {
            val rooms = if (viewModel.rooms == null) {
                viewModel.rooms = getHotel.getRoom()
                viewModel.rooms
            } else {
                viewModel.rooms
            }
            if (viewModel.adapter == null) {
                DaggerMyRoomComponent.builder()
                    .myDaggerRoomModule(
                        MyDaggerRoomModule(
                            viewModel.rooms!!,
                            this@RoomsActivity,
                            this@RoomsActivity,
                            hotelModel
                        )
                    )
                    .build()
                    .inject(this@RoomsActivity)
                viewModel.adapter = adapter
            }

            Log.d("rooms", rooms.toString())
            binding.recyclerView.adapter = viewModel.adapter
        }
    }
}