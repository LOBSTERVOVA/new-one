package com.example.mywork.framework

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.mywork.HotelModel
import com.example.mywork.Rooms
import com.example.mywork.adapters.BookingRecyclerAdapter
import com.example.mywork.adapters.RecyclerViewRoomsAdapter
import com.example.mywork.ui.BookingActivity
import com.example.mywork.ui.RoomsActivity
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class MyDaggerModule(private val context: Context) {
    @Provides
    fun provideAdapter(): BookingRecyclerAdapter {
        return BookingRecyclerAdapter(context)
    }
}

@Component(modules = [MyDaggerModule::class])
interface MyComponent {
    fun inject(activity: BookingActivity)
}

@Module
class MyDaggerRoomModule(
    private val rooms: Rooms,
    private val activity: FragmentActivity,
    private val context: Context,
    private val hotelModel: HotelModel
) {
    @Provides
    fun provideAdapter(): RecyclerViewRoomsAdapter {
        return RecyclerViewRoomsAdapter(rooms, activity, context, hotelModel)
    }
}

@Component(modules = [MyDaggerRoomModule::class])
interface MyRoomComponent {
    fun inject(activity: RoomsActivity)
}