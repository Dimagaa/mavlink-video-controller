package com.app.reactivestreams.domain

data class Camera(
    val id: Int,
    val type: Type,
    val label: String,
    val vendor: String,
    val sourceType: String,
    val address1: String,
    val address2: String?,
    val codec: String?,
    val formats: List<Format> = listOf()
) {

    enum class Type {
        USB, IP
    }

    enum class SourceType(val value: String) {
        RTSP("rtspsrc") , MJPEG("v4l2src")
    }
}

data class Format(
    val name: String,
    val width: Int,
    val height: Int,
    val fps: Int
)
