package com.example.littlelemon.ui.theme.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()

        Spacer(modifier = Modifier.height(2.dp))

        Text(text = stringResource(id = R.string.onboarding_prompt))

        Spacer(modifier = Modifier.height(16.dp))

        // TextFields
        val firstName = remember { mutableStateOf(TextFieldValue()) }
        val lastName = remember { mutableStateOf(TextFieldValue()) }
        val email = remember { mutableStateOf(TextFieldValue()) }
        TextField(value = firstName.value, onValueChange = { firstName.value = it }, label = { Text("First Name") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = lastName.value, onValueChange = { lastName.value = it }, label = { Text("Last Name") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email Address") })

        Spacer(modifier = Modifier.height(24.dp))

        // Register Button
        Button(
            modifier = Modifier.fillMaxWidth(),
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
        modifier = Modifier.size(350.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewOnboarding() {
    Onboarding()
}