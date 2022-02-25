package com.tellioglu.retrofitcompose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.tellioglu.retrofitcompose.model.CurrencyModel
import com.tellioglu.retrofitcompose.ui.theme.GlowIndicator
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun CurrencyList(currencyModel: CurrencyModel){

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
                    MyViewModel.getCurrencyList()},
        indicator = { state, trigger ->
            GlowIndicator(
                swipeRefreshState = state,
                refreshTriggerDistance = trigger
            )
        }
    ){
        LazyColumn (contentPadding = PaddingValues(5.dp)) {
            items(currencyModel.currrencyModList) { currrencyMod ->
                CurrencyRow(currrencyMod = currrencyMod)
            }
        }
    }


}
@Composable
fun CurrencyRow (currrencyMod: CurrencyModel.CurrrencyMod){
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
        currrencyMod.currencyName?.let {
            Text(text = it, style = MaterialTheme.typography.h4, modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(), fontWeight = FontWeight.Bold)
        }
        Row() {
            currrencyMod.banknoteBuying?.let {
                Text(text = it, style = MaterialTheme.typography.h5, modifier = Modifier
                    .padding(2.dp)
                )
            }
            currrencyMod.banknoteSelling?.let {
                Text(text = it, style = MaterialTheme.typography.h5, modifier = Modifier
                    .padding(2.dp)
                )
            }
        }


    }
}