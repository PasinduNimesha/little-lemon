package com.example.littlelemon.ui.theme.composables

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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R

@Composable
fun Onboarding() {
    Column(
        modifier = Modifier
            .fillMaxSize()
,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        Banner()
        val firstName = remember { mutableStateOf(TextFieldValue()) }
        val lastName = remember { mutableStateOf(TextFieldValue()) }
        val email = remember { mutableStateOf(TextFieldValue()) }
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Personal Information", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = firstName.value,
                onValueChange = { firstName.value = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = lastName.value,
                onValueChange = { lastName.value = it },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(150.dp))
        }



        // TextFields



        // Register Button
        Button(
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCC603), contentColor = Color(0xFF000000)),
            onClick = { /* TODO: Implement registration logic */ }
        ) {
            Text(text = stringResource(id = R.string.register_button_label))
        }
    }
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
    Onboarding()
}