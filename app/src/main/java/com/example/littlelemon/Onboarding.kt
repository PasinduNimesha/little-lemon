package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Onboarding(navController: NavHostController) {
    var context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        Banner()
        val firstName = remember { mutableStateOf(TextFieldValue()) }
        val lastName = remember { mutableStateOf(TextFieldValue()) }
        val email = remember { mutableStateOf(TextFieldValue()) }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Personal Information",
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            InputField(label = "First Name", value = firstName.value, onValueChange = { firstName.value = it})
            Spacer(modifier = Modifier.height(8.dp))
            InputField(label = "Last Name", value = lastName.value, onValueChange = { lastName.value = it})
            Spacer(modifier = Modifier.height(8.dp))
            InputField(label = "Email", value = email.value, onValueChange = { email.value = it})
            Spacer(modifier = Modifier.fillMaxHeight(0.4f))
        }


        Button(
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCC603), contentColor = Color(0xFF000000)),
            onClick = {
                saveUserInfoToPrefs(
                    context = context,
                    firstName = firstName.value.text,
                    lastName = lastName.value.text,
                    email = email.value.text
                )
                navController.navigate("home")

            }
        ) {
            Text(text = stringResource(id = R.string.register_button_label))
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,)
    )
}

@Composable
fun Header() {
    val logo: Painter = painterResource(id = R.drawable.img)
    Image(
        painter = logo,
        contentDescription = "App Logo",
        modifier = Modifier
            .width(400.dp)
            .height(126.dp),
    )
}
@Composable
fun Banner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.Gray)
    ){
        Text(
            text = "Let's Get to Know You!",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.align(Alignment.Center)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOnboarding() {
    val navController = rememberNavController()
    Onboarding(navController)
}

fun saveUserInfoToPrefs(context: Context, firstName: String, lastName: String, email: String) {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("firstName", firstName)
    editor.putString("lastName", lastName)
    editor.putString("email", email)
    editor.apply()
}