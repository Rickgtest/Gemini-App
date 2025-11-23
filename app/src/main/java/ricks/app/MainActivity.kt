package ricks.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ricks.app.ui.theme.NotmyappTheme
import ricks.app.uiApp.AiHomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotmyappTheme {
                AiHomeScreen()
            }
        }
    }
}
