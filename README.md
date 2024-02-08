# Mini Video Player App

This repository contains the source code for a mini video player app built for Android using Kotlin, Compose, Hilt, Retrofit, and Media3 (ExoPlayer).

[Download APK](./app-release.apk)

## **Project Goals:**

* Build a video player using Media3 (ExoPlayer), which prepares and plays media from a variety of sources.
* Integrate Compose for a modern and declarative UI.
* Implement a lazy layout to efficiently display a catalog of video content.

## **Features:**

* **Lazy Catalog:** Displays movies or videos in a scrollable, placeholder-based layout.
* **Video Player:** Plays video streams using Media3, offering basic controls like play/pause, seek bar, and volume.
* **Data Integration:** Integrates with [public test videos API](https://mdalbinhossain.github.io/Media/api/public-test-videos.json) for real-time content fetching.

## **Technology Stack:**

* **Frontend:** Kotlin, Compose
* **Dependency Injection:** Hilt
* **Networking:** Retrofit, kotlinx-serialization
* **Media Playback:** Media3 (ExoPlayer)

## **Requirements:**

* Android Studio Version: **Jellyfish** (23.3+)
* Minimum Sdk: 28 (Android 9.0; Pie)

## **Setup and Usage:**

1. Clone this repository.
2. Make sure to fullfill all the requiremtns above.
3. Install dependencies in Android Studio.
4. Build and run the app on your Android device.
