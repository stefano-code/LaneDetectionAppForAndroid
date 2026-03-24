package com.android.test.linedetection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.widget.ImageView
import android.graphics.Bitmap
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.videoio.VideoCapture
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private var isRunning = true // variable to control thread lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        imageView = findViewById(R.id.videoFrame)

        OpenCVLoader.initLocal()

        Thread {
            playVideo()
        }.start()
    }

    private fun playVideo() {
        val videoFile = copyAssetToFile("lane_detection_video.mp4")

        // loop to restart the video from the beginning
        while (isRunning) {
            val capture = VideoCapture(videoFile.absolutePath)
            val frame = Mat()

            // inner loop to read frames
            while (isRunning && capture.read(frame)) {
                val processed = LaneDetector.detect(frame)

                val bitmap = Bitmap.createBitmap(
                    processed.cols(),
                    processed.rows(),
                    Bitmap.Config.ARGB_8888
                )

                Utils.matToBitmap(processed, bitmap)

                runOnUiThread {
                    if (isRunning) {
                        imageView.setImageBitmap(bitmap)
                    }
                }

                Thread.sleep(30) // simulated frame rate
            }

            // video is over
            capture.release()

            // wait 2 seconds before restarting the 'while' loop
            if (isRunning) {
                Thread.sleep(1000)
            }
        }
    }

    private fun copyAssetToFile(name: String): File {

        val file = File(cacheDir, name)

        if (file.exists()) return file

        assets.open(name).use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return file
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }
}