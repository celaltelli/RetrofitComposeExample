package com.tellioglu.retrofitcompose.view
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.tellioglu.retrofitcompose.ui.theme.RetrofitComposeTheme

class TabbedActivity : ComponentActivity() {

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MyViewModel.getCryptoList()
        MyViewModel.getCurrencyList()

        setContent {
            RetrofitComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                    Scaffold(topBar = {AppBar()},) {

                        tabBar()

                     }

                }
            }
        }
    }

    @ExperimentalPagerApi
    @Composable
    fun tabBar(){
        var state by remember { mutableStateOf(0) }
        val titles = listOf("Currencies", "CryptoCurrencies", )
        val pagerState = rememberPagerState()

        Column {
            TabRow(selectedTabIndex = state,
                indicator = { tabPositions -> // 3.
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(
                            pagerState,
                            tabPositions
                        )
                    )
                }

            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = state == index,
                        onClick = { state = index }
                    )
                }
            }
            HorizontalPager( // 4.
                count = titles.size,
                state = pagerState,
            ) { tabIndex ->
                when(tabIndex){
                    1 -> CryptoScreen(cryptoModelList = MyViewModel.cryptoModelList)
                    0 -> CurrencyList(currencyModel = MyViewModel.currencyModel)
                }
            }

//            Text(
//                modifier = Modifier.align(Alignment.CenterHorizontally),
//                text = "Text tab ${state + 1} selected",
//                style = MaterialTheme.typography.body1
//            )
        }
    }

    @Composable
    fun AppBar() {
        Column(Modifier.fillMaxWidth()) {
            TopAppBar(
                title = {
                    Column() {
                        Row { Text("Döviz ve Crypto Paralar", fontSize = 15.sp) }
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                modifier = Modifier.height(30.dp)

            )
        }
    }



    @ExperimentalPagerApi
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        RetrofitComposeTheme {

            tabBar()
        }
    }

}