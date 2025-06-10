package com.example.weather.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.weather.R


@Composable
fun StatusBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()

            .height(40.dp).background(color = Color(0xFF87CEFA)).zIndex(5f)  .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "9:30",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight =  FontWeight(400),
            letterSpacing = 0.5.sp,
            textAlign = TextAlign.Start
        )
        Box(modifier = Modifier.padding(vertical = 8.dp)){
//        Icon(
//            painter = painterResource(id = R.drawable.camera),
//            contentDescription = "Wi-Fi",
//            tint = Color.Black,
//            modifier = Modifier.size(width = 46.dp, height = 52.dp)
//        )
        }
            Icon(
                painter = painterResource(id = R.drawable.statusicons),
                contentDescription = "Wi-Fi",
                tint = Color.White,
                modifier = Modifier.size(width = 46.dp, height = 52.dp)
            )





    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF4F6, name = "StatusBar")
@Composable
fun StatusBarPreview() {
    StatusBar()

}