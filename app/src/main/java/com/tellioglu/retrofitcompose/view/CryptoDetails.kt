package com.tellioglu.retrofitcompose.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.tellioglu.retrofitcompose.model.CryptoItem
import com.tellioglu.retrofitcompose.ui.theme.Alabaster
import com.tellioglu.retrofitcompose.ui.theme.BlueMunsell

@Composable
fun CryptoDetailScreen(id:String,price:String,)
{


    MyViewModel.getCrypto(id)
    val cryptoItems = MyViewModel.crypto


    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Alabaster),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if(cryptoItems.isNotEmpty()) {






                val  selectedCrypto : CryptoItem? = cryptoItems.find { it.id ==id }
                if(selectedCrypto!=null){

                    Text(text = selectedCrypto.name,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = BlueMunsell,
                        textAlign = TextAlign.Center
                    )

                    Image(
                        painter = rememberAsyncImagePainter(model = selectedCrypto.logo_url),
                        contentDescription = selectedCrypto.name,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(200.dp, 200.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )

                    Text(text = price,
                        style = MaterialTheme.typography.h4,
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