import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.Alignment
import com.exalted.exaltedapp.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ExaltedApp",
        state = WindowState(
            width = 800.dp,
            height = 1300.dp,
            position = WindowPosition.Aligned(Alignment.Center)
        ),
        alwaysOnTop = true
    ) {
        App()
    }
}
