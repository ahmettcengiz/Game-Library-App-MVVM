package com.ahmethamdicengiz.appcentproject.model

data class GameDetail(
    val id: Int,
    val name: String,
    val description: String,
    val metacritic: String,
    val released: String,
    val background_image: String,
    val rating: Float
)