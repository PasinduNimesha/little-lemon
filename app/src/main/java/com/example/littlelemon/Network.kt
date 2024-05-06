package com.example.littlelemon

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
//As soon as you get the JSON data from the server, you must convert it to a suitable Kotlin format. To do so, complete the following steps:
//
//Under the application package name, create a new file called Network.kt.
//
//In this file, create the MenuNetworkdata class and MenuItemNetwork data class with @Serializable and @SerialName annotations. These classes contain data classes that are used to decode the object received from the server. In the MainActivityclass, create the instance of the Ktor httpClient and install ContentNegotiation with JSON.
//
//Use the httpClient instance to make a network call and decode the MenuNetwork instance representing menu items from the server.
//
//Tip: The instance of the MenuNetwork class represents the entire menu. To retrieve individual menu items use the menu property of the MenuNetwork. Each item has id, title description, price and image attributes.

@Serializable
data class MenuNetworkData(
    @SerialName("menu") val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("price") val price: Double,
    @SerialName("image") val image: String
)

// Function to retrieve menu data from the server
suspend fun fetchMenuData(): List<MenuItemNetwork> {
    // Create an instance of HttpClient with JSON support
    val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }
    val url = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
    return try {
        // Make a network call to fetch the menu data
        val response = httpClient.get(url)
        Log.d("zz", response.toString())
        // Decode the JSON response to MenuNetworkData
        val menuNetworkData = Json.decodeFromString<MenuNetworkData>(response.toString())
        // Return the list of menu items
        menuNetworkData.menu
    } catch (e: Exception) {
        // Handle exceptions
        Log.e("zz", e.message.toString())
        emptyList()
    }
}
