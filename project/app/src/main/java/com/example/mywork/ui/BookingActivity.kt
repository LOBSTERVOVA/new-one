package com.example.mywork.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mywork.adapters.BookingRecyclerAdapter
import com.example.mywork.R
import com.example.mywork.databinding.ActivityBookingBinding
import com.example.mywork.framework.DaggerMyComponent
import com.example.mywork.framework.MyDaggerModule
import com.example.mywork.getHotel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val NUM_SYMBOLS = "1234567890"

class BookingActivity : AppCompatActivity() {
    private val viewModel by viewModels<BookingViewModel>{
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BookingViewModel() as? T?: throw IllegalStateException()
            }
        }
    }

    @Inject
    lateinit var adapter: BookingRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerMyComponent.builder()
            .myDaggerModule(MyDaggerModule(this))
            .build()
            .inject(this)
        if(viewModel.adapter == null){
            viewModel.adapter = adapter
        }

        binding.imageView5.setOnClickListener {
            finish()
        }

        binding.recyclerView2.layoutManager = GridLayoutManager(this, 1)
        binding.recyclerView2.adapter = viewModel.adapter
        binding.imagePlus.setOnClickListener {
            val error = viewModel.adapter!!.addUser()
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            val bookInfo = getHotel.getBooking()
            with(binding) {
                name2.text = bookInfo.hotel_name
                rating2.text = "$STAR ${bookInfo.horating} ${bookInfo.rating_name}"
                address2.text = bookInfo.hotel_adress
                deploy.text = bookInfo.departure
                city.text = bookInfo.arrival_country
                dates.text = "${bookInfo.tour_date_start}-${bookInfo.tour_date_stop}"
                nights.text = "${bookInfo.number_of_nights.toString()} ночей"
                hotelText.text = bookInfo.hotel_name
                roomHotel.text = bookInfo.room
                food.text = bookInfo.nutrition
                tourT.text = bookInfo.tour_price.toString()+" ₽"
                fuelPriceT.text = bookInfo.fuel_charge.toString()+" ₽"
                servicePriceT.text = bookInfo.service_charge.toString()+" ₽"
                totalPriceT.text = (bookInfo.tour_price+bookInfo.fuel_charge+bookInfo.service_charge).toString()+" ₽"
                buttonPay.text = "Оплатить "+totalPriceT.text
                editTextPhone.setText("+7 (***) ***-**-**")
                var phoneText = binding.editTextPhone.text.toString()
                editTextPhone.setSelection(getPos(editTextPhone.text.toString()))
                editTextPhone.setOnClickListener {
                    editTextPhone.setSelection(getPos(editTextPhone.text.toString()))
                    editTextPhone.setSelection(getPos(editTextPhone.text.toString()))
                }

                buttonPay.setOnClickListener {
                    var touristsCheckOk = viewModel.adapter!!.checkFields()
                    if (editTextPhone.text.contains("*")) {
                        editTextPhone.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                this@BookingActivity,
                                R.color.error_color
                            )
                        )
                        touristsCheckOk = false
                    }
                    if (!isValidEmail(editTextTextEmailAddress.text.toString())) {
                        editTextTextEmailAddress.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                this@BookingActivity,
                                R.color.error_color
                            )
                        )
                        touristsCheckOk = false
                    }
                    if(touristsCheckOk){
                        val intent = Intent(this@BookingActivity, SuccessActivity::class.java)
                        startActivity(intent)
                    }
                }

                editTextTextEmailAddress.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        if (!isValidEmail(editTextTextEmailAddress.text.toString())) {
                            editTextTextEmailAddress.backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    this@BookingActivity,
                                    R.color.error_color
                                )
                            )

                        }
                    } else {
                        editTextTextEmailAddress.backgroundTintList = null

                    }
                }

                editTextPhone.setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                        Log.d("BACKSPACE", "pressed")
                        for (i in phoneText.length - 1 downTo 3) {
                            if (NUM_SYMBOLS.contains(phoneText[i])) {
                                Log.d("FOUND", phoneText[i].toString())
                                phoneText = phoneText.reversed()
                                    .replaceFirst(phoneText[i].toString(), "*").reversed()
                                editTextPhone.setText(phoneText)
                                editTextPhone.setSelection(getPos(editTextPhone.text.toString()))
                                break
                            }
                        }
                        return@setOnKeyListener true
                    }
                    return@setOnKeyListener false
                }
                editTextPhone.addTextChangedListener {
                    editTextPhone.backgroundTintList = null

                    Log.d("EditText", editTextPhone.text.toString())
                    Log.d("PhoneText", phoneText)
                    if (editTextPhone.text.length > ("+7 (***) ***-**-**").length && !editTextPhone.text.contains(
                            "*"
                        )
                    ) {
                        editTextPhone.setText(phoneText)
                        editTextPhone.setSelection(getPos(editTextPhone.text.toString()))
                        return@addTextChangedListener
                    }
                    if (editTextPhone.text.length > phoneText.length) {
                        for (i in 0 until phoneText.length) {
                            if (phoneText[i].toString() == "*") {
                                Log.d("FOUND", i.toString())
                                phoneText = phoneText.replaceFirst(
                                    "*",
                                    editTextPhone.text.toString()[i].toString()
                                )
                                editTextPhone.setText(phoneText)
                                editTextPhone.setSelection(getPos(editTextPhone.text.toString()))
                                break
                            }
                        }
                        Log.d("PhoneTextReplaced", phoneText)
                    }
                }
            }
        }
    }

    private fun getPos(s: String): Int {
        var a = 0
        for (i in 0 until s.length) {
            if (s[i].toString() == "*") return a else a++
        }
        return s.length
    }

    fun isValidEmail(email: String): Boolean {
        Log.d("EMAIL CHECKING", Patterns.EMAIL_ADDRESS.matcher(email).matches().toString())
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}