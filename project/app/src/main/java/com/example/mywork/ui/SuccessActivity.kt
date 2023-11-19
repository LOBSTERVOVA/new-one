package com.example.mywork.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mywork.databinding.ActivitySuccessBinding
import kotlin.random.Random

class SuccessActivity : AppCompatActivity() {
    private val viewModel by viewModels<SuccessViewModel>{
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SuccessViewModel() as? T?: throw IllegalStateException()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView5.setOnClickListener{
            finish()
        }
        binding.textView14.text = "Подтверждение заказа №${viewModel.random} может занять некоторое время (от 1 часа до суток). Как только мы получим ответ от туроператора, вам на почту придет уведомление"

        binding.buttonPay.setOnClickListener {
            val intent = Intent(this, HotelActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }
}