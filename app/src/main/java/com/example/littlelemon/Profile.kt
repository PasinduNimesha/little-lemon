package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Profile(navController: NavHostController) {
    val user = fetchUser(navController.context)
    val firstName = remember { mutableStateOf(TextFieldValue(user.firstName)) }
    val lastName = remember { mutableStateOf(TextFieldValue(user.lastName)) }
    val email = remember { mutableStateOf(TextFieldValue(user.email)) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.img), contentDescription = "logo")

        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.CenterStart,
                ) {
                Text(
                    "Personal Information",
                    fontSize = 20.sp,
                )
            }
            InputField(label = "First Name", value = firstName.value, onValueChange = { firstName.value = it })
            Spacer(modifier = Modifier.height(8.dp))
            InputField(label = "Last Name", value = lastName.value, onValueChange = { lastName.value = it })
            Spacer(modifier = Modifier.height(8.dp))
            InputField(label = "Email", value = email.value, onValueChange = { email.value = it })
        }

        Button(onClick = {
            user.firstName = firstName.value.text
            user.lastName = lastName.value.text
            user.email = email.value.text
            updateUser(navController.context, user)
            navController.navigate("home")
        }) {
            Text("Save")
        }

        Button(onClick = { logOut(navController)}) {
            Text("Log Out")
        }

    }
}

fun logOut(nav: NavHostController) {
    try {
        nav.context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE).edit().clear().apply()
        nav.navigate("onboarding")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
fun fetchUser(context: Context): User {
    return try {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val firstName = sharedPreferences.getString("firstName", "")!!
        val lastName = sharedPreferences.getString("lastName", "")!!
        val email = sharedPreferences.getString("email", "")!!
        User(firstName, lastName, email)
    } catch (e: Exception) {
        User("", "", "")
    }
}

fun updateUser(context: Context, user: User) {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit()
        .putString("firstName", user.firstName)
        .putString("lastName", user.lastName)
        .putString("email", user.email)
        .apply()
}

data class User(
    var firstName: String,
    var lastName: String,
    var email: String
)

