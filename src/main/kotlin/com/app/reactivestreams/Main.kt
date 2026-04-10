package com.app.reactivestreams

import com.app.reactivestreams.video.RtspToRtpStream
import com.app.reactivestreams.video.UsbToRtpStream
import org.freedesktop.gstreamer.Gst

fun main() {
    println("Starting up...")

    Gst.init("app")

    val usbStream = UsbToRtpStream()
    val rtspStream = RtspToRtpStream()

    println("Starting USB stream")
    Thread { usbStream.play() }.start()
    println("Usb stream ready")

    println("Starting RTSP stream")
    Thread { rtspStream.play() }.start()
    println("RTSP stream ready")

    Gst.main()
}
