package com.tellioglu.retrofitcompose.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tellioglu.retrofitcompose.model.CryptoItem
import com.tellioglu.retrofitcompose.model.CryptoModel
import com.tellioglu.retrofitcompose.model.CurrencyModel
import com.tellioglu.retrofitcompose.ui.theme.Alabaster
import com.tellioglu.retrofitcompose.ui.theme.BlueMunsell
import com.tellioglu.retrofitcompose.ui.theme.GlowIndicator
import com.tellioglu.retrofitcompose.ui.theme.RetrofitComposeTheme
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

    Column{


    Spacer(modifier = Modifier.height(16.dp))
    SearchBar(
        hint = "Search...",
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(50.dp)
    ) {
        MyViewModel.searchCryptoList(it)
    }
    Spacer(modifier = Modifier.height(16.dp))
    LazyColumn (contentPadding = PaddingValues(5.dp)) {
        items(cryptoModelList) { cryptoModel ->
            CryptoRow(cryptoModel = cryptoModel)
        }
    }
    }

}

@Composable
fun CryptoRow (cryptoModel: CryptoModel) {

    var selectedRow by remember { mutableStateOf<CryptoModel?>(null) }.apply { this.value }
    var visible by remember { mutableStateOf( false)}


    val textStyle = TextStyle(
        fontWeight = FontWeight.Medium, fontSize = 25.sp, color = BlueMunsell,
        letterSpacing = 0.15.sp
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Alabaster)
            .padding(5.dp)
            .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(5.dp))
    ) {

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .clickable(onClick = {
                visible = !visible
                selectedRow = cryptoModel
            })
        ) {
            Text(
                text = cryptoModel.currency, style = textStyle, modifier = Modifier
                    .padding(2.dp), fontWeight = FontWeight.Bold
            )
            Text(
                text = cryptoModel.price,
                style = textStyle.plus(TextStyle(color = Color.Red)),
                modifier = Modifier
                    .padding(2.dp)
            )
        }


    }
    if (selectedRow != null) {
        selectedRow?.let {
            MyViewModel.getCrypto(it.currency)
        }
        if(visible)
      if(  MyViewModel.crypto.isNotEmpty()) {
          Card(
              modifier = Modifier
                  .fillMaxSize()
          ) {
              Column {

                  val selectedCrypto: CryptoItem? =
                      MyViewModel.crypto.find { it.id == selectedRow!!.currency }
                  Text(
                      text = selectedCrypto!!.name,
                      style = MaterialTheme.typography.h4,
                      modifier = Modifier.padding(2.dp),
                      fontWeight = FontWeight.Bold,
                      color = BlueMunsell,
                      textAlign = TextAlign.Center
                  )

                  Image(
                      painter = rememberImagePainter(data = selectedCrypto.logo_url),
                      contentDescription = selectedCrypto.name,
                      modifier = Modifier
                          .padding(16.dp)
                          .size(100.dp, 100.dp)
                          .clip(CircleShape)
                          .border(2.dp, Color.Gray, CircleShape)
                  )

                  Text(
                      text = selectedRow!!.price,
                      style = MaterialTheme.typography.h4,
                      modifier = Modifier.padding(2.dp),
                      fontWeight = FontWeight.Bold,
                      color = BlueMunsell,
                      textAlign = TextAlign.Center

                  )

              }
          }
      }
        else if(MyViewModel.errorMessage.isNotEmpty())
      {
          Card(
              modifier = Modifier
                  .fillMaxSize()
          ) {
              Column {


                  Text(text = MyViewModel.errorMessage,
                      style = MaterialTheme.typography.h6,
                      modifier = Modifier.padding(2.dp),
                      fontWeight = FontWeight.Bold,
                      color = BlueMunsell,
                      textAlign = TextAlign.Center
                  )





              }
          }
      }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    RetrofitComposeTheme {

        val cryptoModelList = ArrayList<CryptoModel>()
        CryptoScreen(cryptoModelList = cryptoModelList.plus(CryptoModel("BSd","test")))
    }
}