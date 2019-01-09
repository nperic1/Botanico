#include <WiFi.h>   
#include <DNSServer.h>
#include <WebServer.h>
#include "WiFiManager.h"    
#include <IOXhop_FirebaseESP32.h>  
#include <Time.h> 
#include <TimeAlarms.h>  
#include <HashMap.h>
#include <iomanip>

#define DURATION 10000
#define UPDATE_TIME 10000
#define DEVICE_ID "235112/"
#define FIREBASE_HOST "botanico-50074.firebaseio.com"
#define FIREBASE_AUTH "XxxXIEZstrWMWCNNnvR7RUGyizc8cjtBk8E56g4V"

String currentTime;
int minHumidity;
String scheduledWateringTime;
bool automaticWateringStatus;
bool scheduledWateringStatus;
int humidity;
int light;
int temp;

void setup() {
  Serial.begin(9600);

  WiFiManager wifiManager;
  wifiManager.autoConnect("botanicoAP"); 
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH); 
  Firebase.stream(DEVICE_ID, [](FirebaseStream stream) {
    String eventType = stream.getEvent();
    eventType.toLowerCase();
    
     if (eventType == "put") {
  
      if(stream.getPath() == "/automatic_watering/min_humidity"){
          minHumidity = stream.getDataInt(); 
      }
  
      if(stream.getPath() == "/automatic_watering/status"){
          automaticWateringStatus = stream.getDataBool();
      }

      if(stream.getPath() == "/scheduled_watering/time"){
          scheduledWateringTime = stream.getDataString();
      }
      
      if(stream.getPath() == "/scheduled_watering/status"){
          scheduledWateringStatus = stream.getDataBool();
      }  
    }
  });

  initializeClock();
  initializeVariables();
  
  //Alarm.timerRepeat(UPDATE_TIME, updateTemp()); // Update value every X seconds
  //Alarm.timerRepeat(UPDATE_TIME, updateHumidity()); // Update value every X seconds
  //Alarm.timerRepeat(UPDATE_TIME, updateLight()); // Update value every X seconds

  pinMode(2, OUTPUT);
}

void loop() {
  getCurrentTime();  
  
  automaticWatering();
  scheduledWatering();

  delay(1000);
}

void automaticWatering(){
  if(automaticWateringStatus && humidity < minHumidity){
    Firebase.setBool((String) DEVICE_ID + "automatic_watering/notification", true);
    watering();
  }
}

void scheduledWatering(){
  if(scheduledWateringStatus && currentTime == scheduledWateringTime){
    Firebase.setBool((String) DEVICE_ID + "scheduled_watering/notification", true);
    watering();
  }
}

void updateTemp(){

}

void updateHumidity(){

}

void updateLight(){

}

void watering(){
  digitalWrite(2, HIGH);
  Serial.println("Zaljevam!");
  delay(DURATION);
  digitalWrite(2, LOW); 
  Serial.println("Gotovo zaljevanje!");
}

void getCurrentTime() {
  char buffer [30];
  time_t tnow = time(nullptr);
  strftime(buffer, 30, "%X", localtime(&tnow));
  currentTime = (String) buffer;
  Serial.println(currentTime + "fb:" + scheduledWateringTime);
  //Firebase.setString((String) DEVICE_ID + "RTC",currentTime);
}

void initializeClock() {
  configTime(0, 0, "0.se.pool.ntp.org");
  setenv("TZ", "CET-1CEST,M3.5.0/2,M10.5.0/3", 1);
  tzset();
}

void initializeVariables(){
  minHumidity = Firebase.getInt((String) DEVICE_ID + "automatic_watering/min_humidity");
  automaticWateringStatus = Firebase.getBool((String) DEVICE_ID + "automatic_watering/status");
  scheduledWateringTime = Firebase.getString((String) DEVICE_ID + "scheduled_watering/time");
  scheduledWateringStatus = Firebase.getBool((String) DEVICE_ID + "scheduled_watering/status");
}

