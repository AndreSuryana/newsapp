package com.example.newsapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.newsapp.R

val SanFrancisco = FontFamily(
    Font(resId = R.font.sf_pro_display_black, weight = FontWeight.Black),
    Font(
        resId = R.font.sf_pro_display_black_italic,
        weight = FontWeight.Black,
        style = FontStyle.Italic
    ),
    Font(resId = R.font.sf_pro_display_bold, weight = FontWeight.Bold),
    Font(
        resId = R.font.sf_pro_display_bold_italic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    ),
    Font(resId = R.font.sf_pro_display_semi_bold, weight = FontWeight.SemiBold),
    Font(
        resId = R.font.sf_pro_display_semi_bold_italic,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic
    ),
    Font(resId = R.font.sf_pro_display_medium, weight = FontWeight.Medium),
    Font(
        resId = R.font.sf_pro_display_medium_italic,
        weight = FontWeight.Medium,
        style = FontStyle.Italic
    ),
    Font(resId = R.font.sf_pro_display_regular, weight = FontWeight.Normal),
    Font(
        resId = R.font.sf_pro_display_regular_italic,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(resId = R.font.sf_pro_display_light, weight = FontWeight.Light),
    Font(
        resId = R.font.sf_pro_display_light_italic,
        weight = FontWeight.Light,
        style = FontStyle.Italic
    ),
    Font(resId = R.font.sf_pro_display_ultra_light, weight = FontWeight.ExtraLight),
    Font(
        resId = R.font.sf_pro_display_ultra_light_italic,
        weight = FontWeight.ExtraLight,
        style = FontStyle.Italic
    ),
    Font(resId = R.font.sf_pro_display_thin, weight = FontWeight.Thin),
    Font(
        resId = R.font.sf_pro_display_thin_italic,
        weight = FontWeight.Thin,
        style = FontStyle.Italic
    ),
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = SanFrancisco,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    h2 = TextStyle(
        fontFamily = SanFrancisco,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontFamily = SanFrancisco,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    h4 = TextStyle(
        fontFamily = SanFrancisco,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    h5 = TextStyle(
        fontFamily = SanFrancisco,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h6 = TextStyle(
        fontFamily = SanFrancisco,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = SanFrancisco,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = SanFrancisco,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    body1 = TextStyle(
        fontFamily = SanFrancisco,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = SanFrancisco,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    )
)