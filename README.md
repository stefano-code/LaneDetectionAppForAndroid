# 🚗 Android Lane Detection (OpenCV)

An Android demo application for real-time **lane detection** using **OpenCV**.

The app processes a pre-recorded video frame-by-frame and overlays detected lane lines on top of the original frames.

---

## 📸 Screenshot
<img width="360" height="780" alt="Screenshot_20260324_201648" src="https://github.com/user-attachments/assets/e8662de3-c4f3-4005-9895-14a28992e311" />
---

## ✨ Features

- 🎥 Video playback from assets
- 🧠 Frame-by-frame processing using OpenCV
- ⚡ Edge detection with Canny
- 📐 Line detection using Hough Transform
- 🔺 Region of Interest (ROI) masking
- 🎨 Lane overlay rendering
- 🔁 Automatic video loop

---

## 🧠 Processing Pipeline

Each frame goes through the following steps:

1. Convert to grayscale  
2. Apply Canny edge detection  
3. Apply Region of Interest (ROI) mask  
4. Detect lines using Hough Transform  
5. Draw detected lanes on the original frame  

---

## 🏗️ Architecture
---

## 📂 Project Structure

```text
app/
├── java/com/android/test/linedetection/
│ ├── MainActivity.kt
│ └── LaneDetector.kt
│
├── assets/
│ └── lane_detection_video.mp4
│
├── res/layout/
│ └── activity_main1.xml


```
---


