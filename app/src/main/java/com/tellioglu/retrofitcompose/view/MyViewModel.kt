package com.tellioglu.retrofitcompose.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tellioglu.retrofitcompose.model.CryptoItem
import com.tellioglu.retrofitcompose.model.CryptoModel
import com.tellioglu.retrofitcompose.model.CurrencyModel
import com.tellioglu.retrofitcompose.service.CryptoAPI
import kotlinx.coroutines.*

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object MyViewModel : ViewModel() {

    private const val BASE_URL = "https:///api.nomics.com/v1/"
    private const val API_KEY = "43808c5e98edc700a28ad22f47caf1592b8ba687"
    private const val CALL_ATTRIBUTES = "id,name,logo_url"

    var cryptoModelList:List<CryptoModel> by mutableStateOf(listOf())
    var currencyModel: CurrencyModel by mutableStateOf(CurrencyModel())
    var crypto: List<CryptoItem> by mutableStateOf(listOf())

    var errorMessage: String by mutableStateOf("")
     private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

         errorMessage =  throwable.localizedMessage!!
     }

    private var initialCryptoList = listOf<CryptoModel>()
    private var isSearchStarting = true

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

    fun searchCryptoList(query: String) {
        val listToSearch = if(isSearchStarting) {
            cryptoModelList
        } else {
            initialCryptoList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                cryptoModelList = initialCryptoList
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.currency.contains(query.trim(), ignoreCase = true)
            }
            if(isSearchStarting) {
                initialCryptoList = cryptoModelList
                isSearchStarting = false
            }
            cryptoModelList = results
        }
    }

    fun getCrypto(id: String) {

        crypto = emptyList()

            viewModelScope.safeLaunch {

                   // val baseURl = "http://hasanadiguzel.com.tr/api/"


                    val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(CryptoAPI::class.java)

                    val response = retrofit.getCrypto(API_KEY, id, CALL_ATTRIBUTES)




                    if (response.isSuccessful) {
                        response.body().let {
                            crypto = it!!

                        }
                    }
                   else{
                       response.errorBody().let {

                         //  val exceptionBody =   Gson().fromJson(it?.stringSuspending(), ExceptionResponse::class.java)

                           errorMessage = response.message()
                       }
                      // errorMessage = response.errorBody().toString()
                        print(errorMessage)
                    }

                }

    }

    private fun CoroutineScope.safeLaunch(launchBody: suspend () -> Unit): Job {
        val coroutineExceptionHandler = CoroutineExceptionHandler {
                _, throwable ->
            // handle thrown exceptions from coroutine scope
            throwable.printStackTrace()
            print("cant get data")
            errorMessage = throwable.localizedMessage!!
                print(throwable.localizedMessage)
        }

        return this.launch(coroutineExceptionHandler) {
            launchBody.invoke()
        }
    }

}

