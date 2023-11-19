package com.example.mywork.ui

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class SuccessViewModel:ViewModel() {
    val random = Random.nextInt(100000, 999999)
}