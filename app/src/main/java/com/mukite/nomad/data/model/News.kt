package com.mukite.nomad.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class News(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
    @StringRes val date: Int,
)