package com.app.reactivestreams

import com.app.reactivestreams.video.UsbToRtpStream

fun main() {
    val usbStream = UsbToRtpStream()
    usbStream.play()
}
