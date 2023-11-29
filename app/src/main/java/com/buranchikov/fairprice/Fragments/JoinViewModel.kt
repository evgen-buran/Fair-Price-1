package com.buranchikov.fairprice.Fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JoinViewModel : ViewModel() {
    private val ldListPrices = MutableLiveData<ArrayList<Float>>()
    private val ldFairPrice = MutableLiveData<String>()


    private var price: Float = 0.0f
    private var weight: Float = 0.0f
    private var fairPrice: Float = 0.0f
    private val listPrices = ArrayList<Float>()

    fun getLiveDataListPrices() = ldListPrices

    fun getLiveDataFairPrice() = ldFairPrice

    fun calcFairPrice(price: Float, weight: Float) {
        fairPrice = ((price / weight) * 1000)
        if (fairPrice.isNaN() || !fairPrice.isFinite())
            ldFairPrice.postValue("––")
        else
            ldFairPrice.postValue(String.format("%.2f", fairPrice))
    }
    fun calcFairPrice(price: Float, piece: Int) {
        fairPrice = (price / piece).toFloat()
        if (fairPrice.isNaN() || !fairPrice.isFinite())
            ldFairPrice.postValue("––")
        else
            ldFairPrice.postValue(String.format("%.2f", fairPrice))
    }
    fun getFairPrice():Float {
        return fairPrice
    }
    fun getPricesArray(): ArrayList<Float> {
        return listPrices
    }

    fun addPriceList(fairPrice: Float) {
        listPrices.add(fairPrice)
        ldListPrices.postValue(listPrices)

    }

    fun reset() {
        listPrices.clear()
        price = 0.0f
        weight = 0.0f
        calcFairPrice(price,weight)
    }

    fun setValues(weight: Float, price: Float) {
        this.price = price
        this.weight = weight
    }

}

