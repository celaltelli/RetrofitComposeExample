package com.tellioglu.retrofitcompose.view
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.tellioglu.retrofitcompose.ui.theme.*
import kotlinx.coroutines.launch

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
                    color = Alabaster
                ) {
                    Scaffold(topBar = {AppBar()}, backgroundColor = Alabaster) {

                        TabBar()

                     }

                }
            }
        }
    }

    @ExperimentalPagerApi
    @Composable
    fun TabBar(){
        val titles = listOf("Döviz", "Kripto Paralar", )
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()



        Column (modifier = Modifier.background(color =Alabaster)){
            TabRow(selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions -> // 3.
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(
                            pagerState,
                            tabPositions
                        ).border(BorderStroke(3.dp,Color.Red)),color = Alabaster
                    )
                }

            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title, fontWeight = FontWeight.Bold )},
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                              },
                       unselectedContentColor = BlueMunsell,
                        selectedContentColor = Color.Black,
                        modifier = Modifier.background(color = Alabaster)
                            .border(BorderStroke(1.dp, Bone) ).padding(2.dp)
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

            TabBar()
        }
    }

}