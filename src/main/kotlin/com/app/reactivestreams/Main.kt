package com.app.reactivestreams

import org.freedesktop.gstreamer.Gst
import org.freedesktop.gstreamer.Pipeline

fun main() {
    println("Starting...")

    Gst.init("ip-camera-stream")

    val pipelineStr = """
    v4l2src device=/dev/v4l/by-id/usb-MACROSILICON_AFN_Cap_video_20200909-video-index0 !
    image/jpeg,width=1280,height=720,framerate=30/1 !
    jpegdec !
    videoconvert !
    queue max-size-buffers=1 leaky=downstream !
    x264enc tune=zerolatency speed-preset=ultrafast bitrate=1500 !
    rtph264pay pt=96 !
    udpsink host=10.222.218.55 port=5001
""".trimIndent()

    println("Starting gst")

    val pipeline = Gst.parseLaunch(pipelineStr) as Pipeline

    pipeline.play()

    println("Streaming started...")

    // Keep app alive
    Thread.sleep(Long.MAX_VALUE)
}
