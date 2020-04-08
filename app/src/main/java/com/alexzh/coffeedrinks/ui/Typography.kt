package com.alexzh.coffeedrinks.ui

import androidx.ui.material.Typography
import androidx.ui.text.font.FontStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.font.ResourceFont
import androidx.ui.text.font.fontFamily
import com.alexzh.coffeedrinks.R

private val appFontFamily = fontFamily(
    fonts = listOf(
        ResourceFont(resId = R.font.roboto_black, weight = FontWeight.W900, style = FontStyle.Normal),
        ResourceFont(resId = R.font.roboto_black_italic, weight = FontWeight.W900, style = FontStyle.Italic),
        ResourceFont(resId = R.font.roboto_bold, weight = FontWeight.W700, style = FontStyle.Normal),
        ResourceFont(resId = R.font.roboto_bold_italic, weight = FontWeight.W700, style = FontStyle.Italic),
        ResourceFont(resId = R.font.roboto_light, weight = FontWeight.W300, style = FontStyle.Normal),
        ResourceFont(resId = R.font.roboto_light_italic, weight = FontWeight.W300, style = FontStyle.Italic),
        ResourceFont(resId = R.font.roboto_medium, weight = FontWeight.W500, style = FontStyle.Normal),
        ResourceFont(resId = R.font.roboto_medium_italic, weight = FontWeight.W500, style = FontStyle.Italic),
        ResourceFont(resId = R.font.roboto_regular, weight = FontWeight.W400, style = FontStyle.Normal),
        ResourceFont(resId = R.font.roboto_regular_italic, weight = FontWeight.W400, style = FontStyle.Italic),
        ResourceFont(resId = R.font.roboto_thin, weight = FontWeight.W100, style = FontStyle.Normal),
        ResourceFont(resId = R.font.roboto_thin_italic, weight = FontWeight.W100, style = FontStyle.Italic)
    )
)

private val defaultTypography = Typography()
val appTypography = Typography(
    h1 = defaultTypography.h1.copy(fontFamily = appFontFamily),
    h2 = defaultTypography.h2.copy(fontFamily = appFontFamily),
    h3 = defaultTypography.h3.copy(fontFamily = appFontFamily),
    h4 = defaultTypography.h4.copy(fontFamily = appFontFamily),
    h5 = defaultTypography.h5.copy(fontFamily = appFontFamily),
    h6 = defaultTypography.h6.copy(fontFamily = appFontFamily),
    subtitle1 = defaultTypography.subtitle1.copy(fontFamily = appFontFamily),
    subtitle2 = defaultTypography.subtitle2.copy(fontFamily = appFontFamily),
    body1 = defaultTypography.body1.copy(fontFamily = appFontFamily),
    body2 = defaultTypography.body2.copy(fontFamily = appFontFamily),
    button = defaultTypography.button.copy(fontFamily = appFontFamily),
    caption = defaultTypography.caption.copy(fontFamily = appFontFamily),
    overline = defaultTypography.overline.copy(fontFamily = appFontFamily )
)