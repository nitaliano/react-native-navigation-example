# react-native-navigation-example
React Native Navigation Android Drop In UI Example

# Install

```
cd react-native-navigation-example
npm i
```

# Set Mapbox access token

Set your access token [here](https://github.com/nitaliano/react-native-navigation-example/blob/master/android/app/src/main/java/com/rnmapboxnav/MainApplication.java#L47)

# Running the application

* Open up the project in Android Studio
* Start one of your Android emulators or connect your device
* Run `adb reverse tcp:8081 tcp:8081`
* Run `npm start`
* Run the application from Android Studio

# React binding overview

* Create a [react native package](https://github.com/nitaliano/react-native-navigation-example/blob/master/android/app/src/main/java/com/mapboxexample/NavigationPackage.java) that will contain all of our native Mapbox code
* Initialize this package in `MainApplication.java`
* Create a [react native module](https://github.com/nitaliano/react-native-navigation-example/blob/master/android/app/src/main/java/com/mapboxexample/NavigationModule.java), that contains a method called `showNavigation` that accepts a destination
* Invoke this module from the [Javascript](https://github.com/nitaliano/react-native-navigation-example/blob/master/App.js#L27)
* Once the method is invoked from the Javascript, `showNavigation` will
  * Fetch the last known location of the user
  * Fetch the route between the location of the user and the supplied destination
  * Create an intent to launch the drop in ui.
