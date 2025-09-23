package ipca.example.helloworld

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.example.helloworld.ui.theme.HelloWorldTheme


@Composable
fun GreetView(
    modifier: Modifier = Modifier
){
    var name by remember { mutableStateOf("") }
    var greeting by remember { mutableStateOf("") }
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = name,
            onValueChange = { name = it },
        )
        Button(onClick = {
            greeting = "Olá, $name!"
        }) {
            Image(Icons.Default.Face, contentDescription = "")
            Text("Cumprimentar")
        }
        Text(greeting,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetViewPreview(){
    HelloWorldTheme {
        GreetView()
    }
}