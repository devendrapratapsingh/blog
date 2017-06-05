import { Component, } from '@angular/core';
import { NavController, Platform } from 'ionic-angular';
import { SpeechRecognition, SpeechRecognitionListeningOptionsAndroid, SpeechRecognitionListeningOptionsIOS } from '@ionic-native/speech-recognition';
//import {SpeechRecognitionFeatSiri} from 'cordova-plugin-speech-recognition-feat-siri';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

	recordings = [];
	isRecording = false;
  action = "Push Command";
  androidOptions: SpeechRecognitionListeningOptionsAndroid;
  iosOptions: SpeechRecognitionListeningOptionsIOS;

  constructor(public navCtrl: NavController, private platform: Platform, private speechrecognition: SpeechRecognition, private changeRef: ChangeDetectorRef) {
    this.getPermission();
    //this.startListening();
  }

  async getPermission(): Promise<void> {

    try {
      if (this.platform.is('android')) {
        const permission = await this.speechrecognition.hasPermission().then((hasPermission: boolean) => {
          if (!hasPermission) {
            this.speechrecognition.requestPermission();
          }
        });
      }
    } catch (error) {
      console.log(error);
    }

  }
  async isSpeechSupported(): Promise<boolean> {
    if (this.platform.is('android')) {
      const isAvailable = await this.speechrecognition.isRecognitionAvailable();
      return isAvailable;
    }

  }

  async startListening(): Promise<void> {
    this.androidOptions = {
      prompt: 'Speak into your phone!'
    }

    this.iosOptions = {
      language: 'en-US'
    }
    this.action = "recording";
    try {
      if (this.platform.is('android')) {
        this.speechrecognition.startListening(this.androidOptions).subscribe(data => this.handleData(data), error => console.log(error));
      }
      else if (this.platform.is('ios')) {
        this.speechrecognition.startListening(this.iosOptions).subscribe(data => this.handleData(data), error => console.log(error));
      }
    } catch (error) {
      console.log(error);
    }

  }

  handleData(data) {
    this.recordings = data;
    this.action = "Push Command";
  }

  async  stopListening() {
    if (this.platform.is('android')) {
      this.speechrecognition.stopListening().then(() => {
        this.isRecording = false;
        this.changeRef.detectChanges();
      })
    }
  }
  async  isIOS() {
    return this.platform.is('ios');
  }

}
