/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Image,
  NativeModules,
} from 'react-native';

export default class RNAndroidViewDemo extends Component {
  openDialog = () => {
    console.log('open dialog======');
    NativeModules.ImagePicker.openSelectDialog({}, (uri) => {console.log(uri)}, (error) => {console.log(error)} )
  }
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome} onPress={this.openDialog}>
          Welcome to React Native!
        </Text>
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
  },
});

AppRegistry.registerComponent('RNAndroidViewDemo', () => RNAndroidViewDemo);
