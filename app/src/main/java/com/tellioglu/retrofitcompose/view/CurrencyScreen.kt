package com.tellioglu.retrofitcompose.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tellioglu.retrofitcompose.model.CurrencyModel
import com.tellioglu.retrofitcompose.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun CurrencyList(currencyModel: CurrencyModel) {

    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(2000)
            refreshing = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = {
            refreshing = true
            MyViewModel.getCurrencyList()
        },
        indicator = { state, trigger ->
            GlowIndicator(
                swipeRefreshState = state,
                refreshTriggerDistance = trigger
            )
        }
    ) {



        LazyColumn(contentPadding = PaddingValues(5.dp), modifier = Modifier.background(color = Bone)) {
            items(currencyModel.currencyModList) { currencyMod ->
                if(!currencyMod.banknoteBuying.isNullOrEmpty() && !currencyMod.banknoteSelling.isNullOrEmpty())
                CurrencyItemShow(currencyMod = currencyMod)
            }
        }
    }


}

@Composable
fun CurrencyItemShow(currencyMod: CurrencyModel.CurrencyMod) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Alabaster)
            .padding(vertical = 5.dp)
            .border(BorderStroke(1.dp,Color.Black), shape = RoundedCornerShape(5.dp))
    ) {

        val nameStyle = TextStyle(
            fontWeight = FontWeight.Medium, fontSize = 25.sp,color = BlueMunsell,
            letterSpacing = 0.15.sp, textDecoration = TextDecoration.Underline
        )
        val textStyle = TextStyle(
            fontWeight = FontWeight.Medium, fontSize = 20.sp,color = BlueMunsell,
            letterSpacing = 0.15.sp
        )

        currencyMod.name?.let {
            Text(
                text = it, style = nameStyle,modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.Bold
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

            Text(
                text = "Alış", style = textStyle, modifier = Modifier
                    .padding(2.dp), fontWeight = FontWeight.Bold
            )
            Text(
                text = "Satış", style = textStyle, modifier = Modifier
                    .padding(2.dp), fontWeight = FontWeight.Bold
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

            currencyMod.banknoteBuying?.let {
                Text(
                    text = it, style = textStyle.plus(TextStyle(color = Color.Red)), modifier = Modifier
                        .padding(2.dp)
                )
            }
            currencyMod.banknoteSelling?.let {
                Text(
                    text = it, style = textStyle.plus(TextStyle(color = Color.Red)), modifier = Modifier
                        .padding(2.dp)
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrofitComposeTheme {

        val currencyModel = CurrencyModel()
        val currencyMod = currencyModel.CurrencyMod()
        currencyMod.banknoteBuying = "1445"
        currencyMod.banknoteSelling = "1500"
        currencyMod.name = "ABD Doları"
        currencyModel.currencyModList.plus(currencyMod)
        CurrencyItemShow(currencyMod = currencyMod)
    }
}