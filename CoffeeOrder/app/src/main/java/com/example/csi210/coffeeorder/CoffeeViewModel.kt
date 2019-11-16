package com.example.csi210.coffeeorder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media.MediaBrowserCompatUtils

class CoffeeViewModel : ViewModel() {
    var spinnerPosition = MutableLiveData<Int>()
    var radioButtonID = MutableLiveData<Int>()
    var quantityString = MutableLiveData<String>()
    var coffeeList = MutableLiveData<ArrayList<Coffee>>()

    init {
        coffeeList.value = ArrayList()
    }

    fun addCoffee(coffee: Coffee) {
        coffeeList.value?.add(coffee)
    }

    fun removeCoffee(index: Int) {
        coffeeList.value?.removeAt(index)
    }

}