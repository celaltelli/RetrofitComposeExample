package com.tellioglu.retrofitcompose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tellioglu.retrofitcompose.model.CryptoModel
import com.tellioglu.retrofitcompose.ui.theme.GlowIndicator
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun CryptoScreen(cryptoModelList: List<CryptoModel>) {

    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(2000)
            refreshing = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { refreshing =true
                    MyViewModel.getCryptoList()},
        indicator = { state, trigger ->
            GlowIndicator(
                swipeRefreshState = state,
                refreshTriggerDistance = trigger
            )
        }
    ){
        CryptoList(cryptoModelList = cryptoModelList)
    }

}

@Composable
fun CryptoList(cryptoModelList: List<CryptoModel>){

    LazyColumn (contentPadding = PaddingValues(5.dp)) {
        items(cryptoModelList) { cryptoModel ->
            CryptoRow(cryptoModel = cryptoModel)
        }
    }

}

@Composable
fun CryptoRow (cryptoModel: CryptoModel){
    val colors: Array<Long> = arrayOf(0xff13bd27,0xff29c1e1,0xffb129e1,0xffd3df13,0xfff6bd0c,0xffa1fb93,0xff0d9de3,0xffffe48f)
    var color : Color? = null
    try {
        color =   Color(colors[Random.nextInt(8)% 8])
    } catch (e: Exception) {
        print(e.localizedMessage)
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = color!!) ){
        Text(text = cryptoModel.currency
            , style = MaterialTheme.typography.h4
            , modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
            , fontWeight = FontWeight.Bold)
        Text(text = cryptoModel.price
            , style = MaterialTheme.typography.h5
            , modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth())

    }
}