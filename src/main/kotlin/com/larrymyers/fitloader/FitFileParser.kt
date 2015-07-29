package com.larrymyers.fitloader

import com.garmin.fit.*
import java.io.FileInputStream

class FitFileParser {

    companion object {
        fun load(pathname: String) {
            val broadcaster = MesgBroadcaster(Decode())
            val checkStream = FileInputStream(pathname)

            try {
                if (!Decode.checkIntegrity(checkStream)) {
                    throw Exception("FIT file integrity check failed: ${pathname}")
                }
            } finally {
                checkStream.close()
            }

            val inStream = FileInputStream(pathname)

            broadcaster.addListener(object : ActivityMesgListener {
                override fun onMesg(msg: ActivityMesg?) {
                    if (msg == null) {
                        println("msg was null")
                        return
                    }

                    println("ACTIVITY")
                }
            })

            broadcaster.addListener(object : SessionMesgListener {
                override fun onMesg(msg: SessionMesg?) {
                    if (msg == null) {
                        println("msg was null")
                        return
                    }

                    println("SESSION: ${msg.getSport()}")
                }
            })

            broadcaster.addListener(object : LapMesgListener {
                override fun onMesg(msg: LapMesg?) {
                    if (msg == null) {
                        println("msg was null")
                        return
                    }

                    println("LAP: ${msg.getSport()}")
                }
            })

            broadcaster.addListener(object : RecordMesgListener {
                override fun onMesg(msg: RecordMesg?) {
                    if (msg == null) {
                        println("msg was null")
                        return
                    }

                    /**
                     * Semicircles to Degrees
                     * https://msdn.microsoft.com/en-us/library/Cc510650.aspx
                     */

                    val conversion = 180.0/Int.MAX_VALUE
                    val lat = msg.getPositionLat() * conversion
                    val lon = msg.getPositionLong() * conversion

                    println("RECORD: ${msg.getDistance()}, ${msg.getSpeed()}, ${lat}, ${lon}")
                }
            })

            try {
                broadcaster.run(inStream)
            } finally {
                inStream.close()
            }
        }
    }

}
