package com.example.mywork

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface HotelApi {
    @GET("/v3/d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun getHotel():HotelModel
    @GET("/v3/8b532701-709e-4194-a41c-1a903af00195")
    suspend fun getRoom():Rooms
    @GET("/v3/63866c74-d593-432c-af8e-f279d1a8d2ff")
    suspend fun getBooking():BookingInfo
}
    val getHotel = Retrofit
        .Builder()
        .client(
            OkHttpClient
                .Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .also {
                            it.level = HttpLoggingInterceptor.Level.BODY
                        }).build()
        )
        .baseUrl("https://run.mocky.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HotelApi::class.java)



data class HotelModel(
    val id: Int,
    val name:String,
    val adress:String,
    val minimal_price:Int,
    val price_for_it:String,
    val rating:Int,
    val rating_name:String,
    val image_urls:MutableList<String>,
    val about_the_hotel:AboutTheHotel
)
data class AboutTheHotel(
    val description:String,
    val peculiarities:MutableList<String>
)

data class Rooms(
    val rooms:List<RoomModel>
)
data class RoomModel(
    val id:Int,
    val name:String,
    val price:Int,
    val price_per:String,
    val peculiarities:List<String>,
    val image_urls: MutableList<String>
)

data class BookingInfo(
    val id:Int,
    val hotel_name:String,
    val hotel_adress:String,
    val horating:Int,
    val rating_name: String,
    val departure:String,
    val arrival_country:String,
    val tour_date_start:String,
    val tour_date_stop:String,
    val number_of_nights:Int,
    val room:String,
    val nutrition:String,
    val tour_price:Int,
    val fuel_charge:Int,
    val service_charge:Int
)