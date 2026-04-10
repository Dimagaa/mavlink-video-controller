package com.app.reactivestreams

import com.app.reactivestreams.video.RtspToRtpStream
import com.app.reactivestreams.video.UsbToRtpStream

fun main() {
    println("Starting up...")
    Thread.sleep(2000)
    val usbStream = UsbToRtpStream()
    val rtspStream = RtspToRtpStream()

    println("Starting USB stream")
    usbStream.play()
    println("Usb stream ready")

    println("Starting RTSP stream")
    rtspStream.play()
    println("RTSP stream ready")
}