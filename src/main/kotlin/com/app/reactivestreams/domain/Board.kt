package com.app.reactivestreams.domain

data class Board(
    val id: String,
    val name: String,
    val cameras: List<Camera>
)
