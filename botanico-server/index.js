var dateFormat = require('dateformat');

const functions = require('firebase-functions');
const firebase = require('firebase-admin');
const request = require('request');

const express = require('express');
const app = express();

// Firebase Cloud Messaging Server API
var API_KEY = "AAAAahJZebY:APA91bFjNBmhY_RhaxdJ9u-fzmpx5AkC18Mxig31YM4Nu8cxgXm9otQJ5CmzLma_jCoc3FtPLikfP-mS-EJyln1T9qwLdpFea0saA-EYfF12YVx6GGDLpd1NpOI29uCyJFz-Fr9wURVv";   
// Fetch the service account key JSON file contents
var serviceAccount = require("./botanico-50074-firebase-adminsdk-ak7n5-fe6b68961f");

var TEMP_WARNING = 40;

// Initialize the app with a service account, granting admin privileges
firebase.initializeApp({
  credential: firebase.credential.cert(serviceAccount),
  databaseURL: "https://botanico-50074.firebaseio.com/"
});

exports.checkForDataUpdates = functions.database.ref('/{id}').onUpdate((snapshot, context) => {
    const id = snapshot.after.key;
    
    const humidityBefore = snapshot.before.child('humidity').val();
    const humidtyAfter = snapshot.after.child('humidity').val();
    const minHumidity = snapshot.after.child('automatic_watering/min_humidity').val();   
    
    var date = new Date().getTime();
    date = dateFormat(date, "dd.mm.yyyy.");
    
    //spremanje povijesti
    history = snapshot.after.child('history').val();
    if(humidityBefore !== humidtyAfter){
        for(var i = 1; i <= history.length - 2; i++){ //history.length == 30
            if(history[history.length - 1].date !== date){
                snapshot.after.ref.child('history/'+i).set(history[i+1]);
            }
        }
        if(history[history.length - 1].date !== date){
            snapshot.after.ref.child('history/29/date').set(date);
            snapshot.after.ref.child('history/29/moisture').set(humidtyAfter);
        }
    }
    
    //obavijest za prenisku vlažnost tla
    if(humidityBefore !== humidtyAfter && humidtyAfter < minHumidity){
        var topic = id + "_low_moisture";
        var message = "Oops! Humidity is too low!";
        const payload = {notification: {
         title: 'Min humidity',
         body: `${message}`
         }};
        
        firebase.messaging().sendToTopic(topic, payload)
        .then(function(response){
            console.log('Notification sent successfully:', response);
         }) 
        .catch(function(error){
            console.log('Notification sent failed:', error);
        });
    }
    
    //obavijest za previsoku temperaturu
    const tempBefore = snapshot.before.child('temp').val();
    const tempAfter = snapshot.after.child('temp').val();
    
    if(tempBefore !== tempAfter && tempAfter > TEMP_WARNING){
        var topic = id + "_temperature";
        var message = "Whoa! Temperature is too high!";
        const payload = {notification: {
         title: 'Temperature warning',
         body: `${message}`
         }};
     
        firebase.messaging().sendToTopic(topic, payload)
        .then(function(response){
            console.log('Notification sent successfully:', response);
         }) 
        .catch(function(error){
            console.log('Notification sent failed:', error);
        });
    }
    
    //obavijest za obavljeno zalijevanje koje je bilo zakazano
    const notificationBefore = snapshot.before.child('scheduled_watering/notification').val();
    const notificationAfter = snapshot.after.child('scheduled_watering/notification').val();
   
    if(notificationBefore !== notificationAfter && notificationAfter){
        var topic = id + "_watering";
        var message = "Your lovely plant has just been watered!";
        const payload = {notification: {
         title: 'Scheduled watering info',
         body: `${message}`
         }};
     
        firebase.messaging().sendToTopic(topic, payload)
        .then(function(response){
            console.log('Notification sent successfully:', response);
         }) 
        .catch(function(error){
            console.log('Notification sent failed:', error);
        });
        
        snapshot.after.ref.child('scheduled_watering/notification').set(false);
    }
    
    //obavijest za zalijevanje na temelju minimalne vlage
    const automaticWateringNotificationBefore = snapshot.before.child('automatic_watering/notification').val();
    const automaticWateringNotificationAfter = snapshot.after.child('automatic_watering/notification').val();
    
    if(automaticWateringNotificationBefore !== automaticWateringNotificationAfter && automaticWateringNotificationAfter){
        var topic = id + "_unplanned_watering";
        var message = "Your lovely plant has been watered because it was too dry!";
        const payload = {notification: {
         title: 'Automatic watering info',
         body: `${message}`
         }};
     
        firebase.messaging().sendToTopic(topic, payload)
        .then(function(response){
            console.log('Notification sent successfully:', response);
         }) 
        .catch(function(error){
            console.log('Notification sent failed:', error);
        });
        
        snapshot.after.ref.child('automatic_watering/notification').set(false);
    }
    
    return snapshot;
});