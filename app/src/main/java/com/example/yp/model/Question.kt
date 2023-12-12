package com.example.yp.model

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean) {

}