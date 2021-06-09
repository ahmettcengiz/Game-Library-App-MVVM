package com.ahmethamdicengiz.appcentproject.model

data class GameListEntity(
    val results: List<Result>
)

data class Result(
    val id: String,
    val name: String,
    val released: String,
    val background_image: String,
    val rating: Float
)