package presentation.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties

object MyStyle {
    val commonTextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color.White
    )

    val statsTextStyle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        color = Color.White
    )

    val titleTextStyle = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )

    val largeTextStyle = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )

    val extraLargeTextStyle = TextStyle(
        fontSize = 48.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.White
    )

    val menuButtonStyle = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.ExtraBold
    )

    val textColorBackpackNumbers = Color(0x44444444)

    val dialogProperties = DialogProperties(usePlatformDefaultWidth = false)
}