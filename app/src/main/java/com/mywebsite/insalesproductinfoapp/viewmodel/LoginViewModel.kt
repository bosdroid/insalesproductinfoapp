package com.mywebsite.insalesproductinfoapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.mywebsite.insalesproductinfoapp.retrofit.ApiRepository
import com.mywebsite.insalesproductinfoapp.utils.AppSettings
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(context: Context) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    val isLogged = MutableLiveData(false)
    val appSettings = AppSettings(context)
    var accountResponse = MutableLiveData<JsonObject?>()

    init {
        viewModelScope.launch {
            delay(2000)
            checkUserLoginStatus()
        }
    }

    private fun checkUserLoginStatus(){
        val insalesStatus = appSettings.getString("INSALES_STATUS")
        _isLoading.value = false
        isLogged.value = insalesStatus!!.isNotEmpty() && insalesStatus == "logged"
    }

    fun callSalesAccount(context: Context,shopName:String,email:String,password:String){
        accountResponse = ApiRepository.getInstance(context).salesAccount(shopName,email,password)
    }

    fun getSalesAccountResponse(): LiveData<JsonObject?> {
        return accountResponse
    }

}