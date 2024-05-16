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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.layout.ContentScale
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
    var searchPhrase by remember { mutableStateOf("") }
    var selectedChoice by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeHeader(navController)
        Column(
            modifier = Modifier
                .fillMaxHeight(0.45f)
                .background(color = Color(0xff495E57)),
        ) {
            HomeBody()
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xffF4CE14),
                    unfocusedBorderColor = Color(0xffF4CE14),
                    cursorColor = Color(0xffF4CE14),
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedSupportingTextColor = Color.White,

                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                value = searchPhrase,
                label = { Text("Enter Search Phrase") },
                onValueChange = {
                    searchPhrase = it
                }
            )
        }
        Column {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                text = "Order For Delivery!",
                fontWeight = FontWeight.Bold,
                )
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
            ) {
                FilterButton(
                    text = "Starters",
                    color = ButtonDefaults.buttonColors(
                        containerColor = if (selectedChoice == "Starters") Color(0xffF4CE14) else Color.Gray
                    )
                ) {
                    selectedChoice = "Starters"
                }
                FilterButton(
                    text = "Main",
                    color = ButtonDefaults.buttonColors(
                        containerColor = if (selectedChoice == "Main") Color(0xffF4CE14) else Color.Gray
                    )
                ) {
                    selectedChoice = "Main"
                }
                FilterButton(
                    text = "Desserts",
                    color = ButtonDefaults.buttonColors(
                        containerColor = if (selectedChoice == "Desserts") Color(0xffF4CE14) else Color.Gray
                    )
                ) {
                    selectedChoice = "Desserts"
                }
                FilterButton(
                    text = "Drinks",
                    color = ButtonDefaults.buttonColors(
                        containerColor = if (selectedChoice == "Drinks") Color(0xffF4CE14) else Color.Gray
                    )
                ) {
                    selectedChoice = "Drinks"
                }
            }
        }
        if (searchPhrase.isNotEmpty()) {
            MenuItemsList(items = menuItems.filter { it.title.contains(searchPhrase, ignoreCase = true) })
        } else {
            MenuItemsList(items = menuItems)
        }
    }
}

@Composable
fun HomeBody() {
    Column {
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
            Column {
                Text(
                    text = "Chicago",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                Text(
                    text = "We are a family owned Mediterranean restaurant focused on traditional recipes served with a modern twist.",
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .width(200.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.home_image),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 32.dp, top = 16.dp, bottom = 16.dp)
                    .size(120.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun HomeHeader(navController: NavHostController){
    Row {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.6f)
        )
        Image(
            painter = painterResource(id = R.drawable.profile_image),
            contentDescription = "profile image placeholder",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.4f)
                .clip(shape = RoundedCornerShape(100.dp))
                .clickable { navController.navigate("profile") }
        )
    }
}

@Composable
fun FilterButton(text : String, color: ButtonColors ,onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier.padding(8.dp),
        colors = color
    ){
        Text(text)
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