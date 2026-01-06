package ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import exaltedapp.composeapp.generated.resources.Chomsky
import exaltedapp.composeapp.generated.resources.FeENrm2
import exaltedapp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
@Composable
fun exaltedTypography(): Typography {
    val chomsky = FontFamily(
        Font(Res.font.Chomsky, weight = FontWeight.Normal)
    )

    val feENrm2 = FontFamily(
        Font(Res.font.FeENrm2, weight = FontWeight.Normal)
    )

    return Typography().run {
        copy(
            displayLarge = displayLarge.copy(fontFamily = chomsky),
            displayMedium = displayMedium.copy(fontFamily = chomsky),
            displaySmall = displaySmall.copy(fontFamily = chomsky),
            headlineLarge = headlineLarge.copy(fontFamily = chomsky),
            headlineMedium = headlineMedium.copy(fontFamily = chomsky),
            headlineSmall = headlineSmall.copy(fontFamily = chomsky),
            titleLarge = titleLarge.copy(fontFamily = chomsky),
            titleMedium = titleMedium.copy(fontFamily = chomsky),
            titleSmall = titleSmall.copy(fontFamily = chomsky),
            labelLarge = labelLarge.copy(fontFamily = chomsky),
            labelMedium = labelMedium.copy(fontFamily = chomsky),
            labelSmall = labelSmall.copy(fontFamily = chomsky),

            bodyLarge = bodyLarge.copy(fontFamily = feENrm2),
            bodyMedium = bodyMedium.copy(fontFamily = feENrm2),
            bodySmall = bodySmall.copy(fontFamily = feENrm2),
        )
    }
}
