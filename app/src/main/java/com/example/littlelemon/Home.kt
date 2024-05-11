package com.example.littlelemon


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import coil.compose.AsyncImage
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Home(navController: NavHostController, database: AppDatabase) {
    val menuItems = database.menuItemDao().getAll().observeAsState().value ?: emptyList()

//    Column (
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ){
//
//        Text("Home")
//        Button(onClick = { navController.navigate("profile")}) {
//            Text("Go to Profile")
//        }
//        Button(onClick = { /*TODO*/ }) {
//            Text(text = "Fetch Data")
//        }
//
//    }
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.6f)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .background(color = Color(0xff495E57)),
        ) {
            Text(
                text = "Little Lemon",
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                color = Color(0xffF4CE14),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Chicago",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.5f)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.home_image),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }


            var searchPhrase by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = searchPhrase,
                label = { Text("Search") },
                onValueChange = {
                    searchPhrase = it
                }
            )
        }
        MenuItemsList(items = menuItems)
    }
}
@Composable
fun MenuItemsList(items: List<MenuItemRoom>) {
    LazyColumn {
        items(items) { menuItem ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(color = Color.White),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(8.dp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(menuItem.title, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        Text(menuItem.description, modifier = Modifier.fillMaxHeight(0.6f))
                        Text("$${menuItem.price}", fontWeight = FontWeight.SemiBold)

                    }
                    Box(
                        modifier = Modifier.clip(shape = RoundedCornerShape(100.dp))
                    ) {
                        AsyncImage(
                            model = menuItem.image,
                            placeholder = painterResource(id = R.drawable.place_holder),
                            error = painterResource(id = R.drawable.place_holder),
                            contentDescription = "The delasign logo",
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        )
                    }

                }
            }
        }
    }
}