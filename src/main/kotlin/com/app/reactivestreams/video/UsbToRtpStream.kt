package com.app.reactivestreams.video

import org.freedesktop.gstreamer.Element
import org.freedesktop.gstreamer.ElementFactory
import org.freedesktop.gstreamer.Gst
import org.freedesktop.gstreamer.Pipeline
import org.freedesktop.gstreamer.State

class UsbToRtpStream {
    fun play() {
        Gst.init("usb-stream")

        val pipeline = Pipeline("usb-pipeline")

        val src = ElementFactory.make("v4l2src", "src")
        src.set("device", "/dev/v4l/by-id/usb-MACROSILICON_AFN_Cap_video_20200909-video-index0")

        val convert = ElementFactory.make("videoconvert", "convert")

        val enc = ElementFactory.make("x264enc", "enc")
        enc.set("bitrate", 2000)
        enc.set("tune", 0x00000004)

        val pay = ElementFactory.make("rtph264pay", "pay")

        val sink = ElementFactory.make("udpsink", "sink")
        sink.set("host", "10.222.218.55")
        sink.set("port", 5000)

        pipeline.addMany(src, convert, enc, pay, sink)

        if (!Element.linkMany(src, convert, enc, pay, sink)) {
            error("Failed to link pipeline")
        }

        pipeline.state = State.PLAYING

        Gst.main()
    }
}