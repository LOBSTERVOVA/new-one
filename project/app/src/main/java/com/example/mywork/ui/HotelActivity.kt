package com.example.mywork.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import com.example.mywork.adapters.SliderAdapter
import com.example.mywork.databinding.ActivityHotelBinding
import com.example.mywork.getHotel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val STAR = "★"
const val HOTEL_ARG = "hotel_argument"
const val ROOM_ARG = "room_arg"

class HotelActivity : AppCompatActivity() {
    var binding: ActivityHotelBinding? = null
    lateinit var adapter: SliderAdapter

    private val viewModel by viewModels<HotelViewModel>{
        object: Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HotelViewModel() as? T?: throw IllegalStateException()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        CoroutineScope(Dispatchers.Default).launch {
            val hotelModel = if(viewModel.hotelModel == null){
                val a = getHotel.getHotel()
                viewModel.hotelModel = a
                a
            } else viewModel.hotelModel

            Log.d("hotel downloaded", "TRUE")
            adapter = SliderAdapter(this@HotelActivity, hotelModel!!.image_urls)
            runOnUiThread {
                binding!!.viewPager.adapter = adapter
                binding!!.rating.text = "$STAR ${hotelModel.rating} ${hotelModel.rating_name} "
                binding!!.name.text = hotelModel.name
                binding!!.address.text = hotelModel.adress
                binding!!.price.text = hotelModel.minimal_price.toString() + "₽"
                binding!!.priceForIt.text = hotelModel.price_for_it

                binding!!.textView9.text = hotelModel.about_the_hotel.peculiarities[0]
                binding!!.textView10.text = hotelModel.about_the_hotel.peculiarities[1]
                binding!!.textView11.text = hotelModel.about_the_hotel.peculiarities[2]
                binding!!.textView12.text = hotelModel.about_the_hotel.peculiarities[3]
                binding!!.description.text = hotelModel.about_the_hotel.description
                binding!!.placeholder.visibility = View.GONE
                binding!!.button.setOnClickListener {
                    val intent = Intent(this@HotelActivity, RoomsActivity::class.java)
                    intent.putExtra(HOTEL_ARG, Gson().toJson(hotelModel))
                    startActivity(intent)
                }
            }
        }
    }
}