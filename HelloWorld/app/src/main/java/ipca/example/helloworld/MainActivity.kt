package ipca.example.helloworld

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ipca.example.helloworld.ui.theme.HelloWorldTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Date.longDateString() : String  {
    val format = SimpleDateFormat("EEEE, dd MMMM yyyy - hh:mm:ss", Locale.getDefault())
    return format.format(this)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloWorldTheme {
                Scaffold{ innerPadding ->
                   GreetView(modifier = Modifier.padding(innerPadding))
                }
            }
        }

    }
}

