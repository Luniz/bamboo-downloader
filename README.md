<a href="https://travis-ci.org/gregbiv/bamboo-downloader" target="_blank">
<img src="https://travis-ci.org/gregbiv/bamboo-downloader.svg?branch=develop" alt="Travis build status" />
</a>

# Introduction

Bamboo Downloader is an application that can browse builds and download build artifacts from a Bamboo server. 
If the artifact file is supported by your device, it will download and automatically open/install it. 
It is much faster and easier to navigate than the Bamboo web interface on a mobile browser. 
It has been tested on Bamboo 5.8.0

The application supports Android 4.0 Ice Cream Sandwich (API level 14) and above.

Features include:

  * An easy-to-use interface.

# Installation

There are different ways to get the Bamboo Downloader app for Android; through
the app store, from github or building it yourself.

### App Store

<a href="http://play.google.com/store/apps/details?id=kz.gregbiv.bamboo">
  <img alt="Android app on Google Play" src="http://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

### From GitHub

Download the .apk from https://github.com/gregbiv/bamboo-downloader/releases

## Building

### With Gradle

This project requires the [Android SDK](http://developer.android.com/sdk/index.html)
to be installed in your development environment. In addition you'll need to set
the `ANDROID_HOME` environment variable to the location of your SDK. For example:

    export ANDROID_HOME=/home/<user>/tools/android-sdk

After satisfying those requirements, the build is pretty simple:

* Run `./gradlew build installDebug` from the within the project folder.
It will build the project for you and install it to the connected Android device or running emulator.

The app is configured to allow you to install a development and production version in parallel on your device.

### With Android Studio
The easiest way to build is to install [Android Studio](https://developer.android.com/sdk/index.html) v2.+
with [Gradle](https://www.gradle.org/) v3.4.1
Once installed, then you can import the project into Android Studio:

1. Open `File`
2. Import Project
3. Select `build.gradle` under the project directory
4. Click `OK`

Then, Gradle will do everything for you.

## Support

Google+ Community: https://plus.google.com/communities/104728406764752407046

## Contributing

There are several ways you could contribute to the development.

* Pull requests are always welcome! You could contribute code by fixing bugs, adding new features or automated tests.
Take a look at the [bug tracker](https://github.com/gregbiv/bamboo-downloaderissues?state=open)
for ideas where to start.

For development, it is recommended to use the Android Studio for development which is available for free.
Import the project into the IDE using the build.gradle file. The IDE will resolve dependencies automatically.

# Licence
GnuCash Android is free software; you can redistribute it and/or
modify it under the terms of the Apache license, version 2.0.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
