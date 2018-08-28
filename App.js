import React from 'react';

import {
  Platform,
  StyleSheet,
  TouchableOpacity,
  Text,
  View,
  NativeModules,
  PermissionsAndroid,
} from 'react-native';

const MapboxNavigation = NativeModules.NavigationModule;

export default class App extends React.Component {

  constructor(props) {
    super(props);
    this.onShowNavigation = this.onShowNavigation.bind(this);
  }

  async componentDidMount() {
    await this.requestLocationPermission();
  }

  onShowNavigation() {
    MapboxNavigation.showNavigation([ -122.399812, 37.789525]);
  }

  async requestLocationPermission() {
    if (Platform.OS !== 'android') {
      return;
    }

    try {
      const granted = await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
        {
          'title': 'User Location Permission',
          message: 'Used for navigation.',
        },
      )

      if (granted !== PermissionsAndroid.RESULTS.GRANTED) {
        requestLocationPermission();
      }
    } catch (err) {
      console.warn(err)
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity style={styles.showNavBtn} onPress={this.onShowNavigation}>
          <Text style={styles.showNavBtnTxt}>Show Navigation</Text>
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
    padding: 30,
  },
  showNavBtn: {
    maxHeight: 90,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'blue',
    borderRadius: 4,
    paddingHorizontal: 16,
    paddingVertical: 8,
    elevation: 2,
  },
  showNavBtnTxt: {
    color: 'white',
  },
});
