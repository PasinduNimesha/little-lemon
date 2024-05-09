package com.example.littlelemon

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
@Serializable
class MenuNetworkdata(
    @SerialName("menu")
    val menu: List<MenuItemNetwork>
){

}

@Serializable
data class MenuItemNetwork(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("price")
    val price: Double,
    @SerialName("image")
    val image: String,
    @SerialName("description")
    val description: String,
    @SerialName("category")
    val category: String
){
    fun toMenuItemRoom() = MenuItemRoom(
        id = id,
        title = title,
        price = price,
        image = image,
        description = description,
        category = category
    )
}

suspend fun fetchMenuData() : List<MenuItemNetwork> {
    return try {
        val httpClient = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("text", "plain"))
            }
        }
        val menuData : MenuNetworkdata = httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json").body()
        Log.d("zd", menuData.menu.toString())
        menuData.menu
    } catch (e: Exception) {
        Log.e("ze", e.message.toString())
        emptyList()
    }
}

