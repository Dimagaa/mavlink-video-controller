package com.app.reactivestreams.video

import org.freedesktop.gstreamer.Element
import org.freedesktop.gstreamer.ElementFactory
import org.freedesktop.gstreamer.Gst
import org.freedesktop.gstreamer.Pipeline
import org.freedesktop.gstreamer.State

class RtspToRtpStream {
    fun play() {
        Gst.init("rtsp-to-rtp")

        val pipeline = Pipeline("rtsp-pipeline")

        val src = ElementFactory.make("rtspsrc", "src")
            ?: error("Failed to create rtspsrc")

        src.set("location", "rtsp://admin:Admin1234@192.168.1.124:554/cam/realmonitor?channel=1&subtype=0")
        src.set("latency", 0)

        val depay = ElementFactory.make("rtph264depay", "depay")
            ?: error("Failed to create depay")

        val pay = ElementFactory.make("rtph264pay", "pay")
            ?: error("Failed to create pay")

        val sink = ElementFactory.make("udpsink", "sink")
            ?: error("Failed to create udpsink")

        sink.set("host", "10.222.218.55")
        sink.set("port", 5001)

        sink.set("sync", false)
        sink.set("async", false)

        pipeline.addMany(src, depay, pay, sink)

        src.connect(Element.PAD_ADDED { element, pad ->
            val sinkPad = depay.getStaticPad("sink")

            if (!sinkPad.isLinked) {
                pad.link(sinkPad)
            }
        })

        if (!Element.linkMany(depay, pay, sink)) {
            error("Failed to link depay → pay → sink")
        }

        pipeline.state = State.PLAYING

        Gst.main()
    }
}
