package presentation.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object MyModifier {
    val buttonModifier = Modifier
        .width(600.dp)
        .height(200.dp)
        .padding(50.dp)

    val dialogBoxModifier = Modifier
        .size(width = 900.dp, height = 800.dp)
        .background(Color.Gray)
        .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 4.dp)
}