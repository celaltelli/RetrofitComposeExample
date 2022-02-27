package com.tellioglu.retrofitcompose.service
import com.tellioglu.retrofitcompose.model.Crypto
import com.tellioglu.retrofitcompose.model.CryptoModel
import com.tellioglu.retrofitcompose.model.CurrencyModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoAPI {
    @GET("prices?key=43808c5e98edc700a28ad22f47caf1592b8ba687")
    suspend fun getData(): Response<List<CryptoModel>>

    @GET("kurgetir")
    suspend fun getCurrencyData(): Response<CurrencyModel>

    @GET("currencies?key=43808c5e98edc700a28ad22f47caf1592b8ba687")
    suspend fun getCrypto(
        @Query("key") key: String,
        @Query("ids") id : String,
        @Query("attributes") attributes: String
    ): Response<Crypto>
}