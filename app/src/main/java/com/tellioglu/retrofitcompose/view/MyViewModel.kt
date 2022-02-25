package com.tellioglu.retrofitcompose.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tellioglu.retrofitcompose.model.CryptoModel
import com.tellioglu.retrofitcompose.model.CurrencyModel
import com.tellioglu.retrofitcompose.service.CryptoAPI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyViewModel : ViewModel() {

    var cryptoModelList:List<CryptoModel> by mutableStateOf(listOf())
    var currencyModel: CurrencyModel by mutableStateOf(CurrencyModel())

    var errorMessage: String by mutableStateOf("")
     private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

         errorMessage =  throwable.localizedMessage
     }

     fun getCryptoList() {
         try {
        viewModelScope.launch {
            withContext(Dispatchers.IO + exceptionHandler){
            val baseUrl = "https://api.nomics.com/v1/"


                val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CryptoAPI::class.java)
            val response = retrofit.getData()




                    if (response.isSuccessful) {
                        response.body().let {
                            cryptoModelList = it!!.toMutableStateList()

                        }
                    }

            }
         }
         }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }

    }
    fun getCurrencyList() {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO + exceptionHandler){
                    val baseURl = "http://hasanadiguzel.com.tr/api/"


                    val retrofit = Retrofit.Builder()
                        .baseUrl(baseURl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(CryptoAPI::class.java)
                    val response = retrofit.getCurrencyData()




                        if (response.isSuccessful) {
                            response.body().let {
                                currencyModel = it!!

                            }
                        }

                }
            }
        }
        catch (e: Exception) {
            errorMessage = e.message.toString()
        }

    }
}