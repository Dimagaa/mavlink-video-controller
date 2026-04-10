package com.app.reactivestreams.video

import org.freedesktop.gstreamer.Caps
import org.freedesktop.gstreamer.Element
import org.freedesktop.gstreamer.ElementFactory
import org.freedesktop.gstreamer.Pipeline
import org.freedesktop.gstreamer.State

class RtspToRtpStream {
    fun play() {
        val pipeline = Pipeline("rtsp-pipeline")

        val src = ElementFactory.make("rtspsrc", "src")
            ?: error("Failed to create rtspsrc")

        src.set("location", "rtsp://admin:Admin1234@192.168.1.124:554/cam/realmonitor?channel=1&subtype=0")
        src.set("latency", 0)

        val capsFilter = ElementFactory.make("capsfilter", "caps")
            ?: error("Failed to create capsfilter")

        val caps = Caps.fromString("application/x-rtp,media=video,encoding-name=H265")
        capsFilter.set("caps", caps)

        val depay = ElementFactory.make("rtph265depay", "depay")
            ?: error("Failed to create depay")

        val parse = ElementFactory.make("h265parse", "parse")
            ?: error("Failed to create h265parse")

        val pay = ElementFactory.make("rtph265pay", "pay")
            ?: error("Failed to create pay")

        pay.set("config-interval", 1)
        pay.set("pt", 96)

        val sink = ElementFactory.make("udpsink", "sink")
            ?: error("Failed to create udpsink")

        sink.set("host", "10.222.218.55")
        sink.set("port", 5001)
        sink.set("sync", false)
        sink.set("async", false)

        pipeline.addMany(src, capsFilter, depay, parse, pay, sink)

        src.connect(Element.PAD_ADDED { _, pad ->
            val sinkPad = capsFilter.getStaticPad("sink")

            if (!sinkPad.isLinked) {
                val result = pad.link(sinkPad)
                println("Link result: $result")
            }
        })

        if (!Element.linkMany(capsFilter, depay, parse, pay, sink)) {
            error("Failed to link depay → pay → sink")
        }

        pipeline.state = State.PLAYING
    }
}
