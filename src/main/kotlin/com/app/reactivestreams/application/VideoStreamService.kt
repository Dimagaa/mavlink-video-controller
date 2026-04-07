package com.app.reactivestreams.application

interface VideoStreamService {
    fun startStream()
    fun stopStream()
    fun switchQuality()
}
