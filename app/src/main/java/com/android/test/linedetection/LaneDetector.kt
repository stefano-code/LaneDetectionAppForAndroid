package com.android.test.linedetection

import org.opencv.core.*
import org.opencv.imgproc.Imgproc

object LaneDetector {

    private fun drawLines(image: Mat, lines: Mat): Mat {

        val linesImage = Mat.zeros(image.size(), image.type())

        for (i in 0 until lines.rows()) {

            val points = lines.get(i, 0)

            val x1 = points[0].toInt()
            val y1 = points[1].toInt()
            val x2 = points[2].toInt()
            val y2 = points[3].toInt()

            Imgproc.line(
                linesImage,
                Point(x1.toDouble(), y1.toDouble()),
                Point(x2.toDouble(), y2.toDouble()),
                Scalar(255.0, 0.0, 0.0),
                3
            )
        }

        val output = Mat()
        Core.addWeighted(image, 0.8, linesImage, 1.0, 0.0, output)

        return output
    }

    private fun regionOfInterest(image: Mat, vertices: MatOfPoint): Mat {

        val mask = Mat.zeros(image.size(), image.type())

        val pts = ArrayList<MatOfPoint>()
        pts.add(vertices)

        Imgproc.fillPoly(mask, pts, Scalar(255.0))

        val masked = Mat()
        Core.bitwise_and(image, mask, masked)

        return masked
    }

    fun detect(frame: Mat): Mat {

        val height = frame.rows()
        val width = frame.cols()

        val gray = Mat()
        Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY)

        val edges = Mat()
        Imgproc.Canny(gray, edges, 100.0, 120.0)

        val roiVertices = MatOfPoint(
            Point(0.0, height.toDouble()),
            Point(width / 2.0, height * 0.65),
            Point(width.toDouble(), height.toDouble())
        )

        val cropped = regionOfInterest(edges, roiVertices)

        val lines = Mat()

        Imgproc.HoughLinesP(
            cropped,
            lines,
            2.0,
            Math.PI / 180,
            50,
            40.0,
            150.0
        )

        return drawLines(frame, lines)
    }
}
