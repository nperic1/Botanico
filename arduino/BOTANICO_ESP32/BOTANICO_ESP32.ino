#include <WiFi.h>   
#include <DNSServer.h>
#include <WebServer.h>
#include "WiFiManager.h"    
#include <IOXhop_FirebaseESP32.h>  
#include <Time.h> 
#include <TimeAlarms.h>  
#include <HashMap.h>
#include <iomanip>
#include "DHT.h"

#define DHTTYPE DHT11 
#define DURATION 5000
#define UPDATE_TIME 10000
#define DEVICE_ID "235112/"
#define FIREBASE_HOST "botanico-50074.firebaseio.com"
#define FIREBASE_AUTH "XxxXIEZstrWMWCNNnvR7RUGyizc8cjtBk8E56g4V"
#define LIGHT_SENSOR 35
#define HUMIDITY_SENSOR 32
#define DHT_SENSOR 34
#define WATER_PUMP 33

DHT dht(DHT_SENSOR, DHTTYPE);

String currentTime;
int minHumidity;
String scheduledWateringTime;
bool automaticWateringStatus;
bool scheduledWateringStatus;
int humidity = 0, light = 0, temp = 0;
int counter = 0;

void setup() {
  Serial.begin(9600);
  pinMode(LIGHT_SENSOR, INPUT);
  pinMode(HUMIDITY_SENSOR, INPUT);
  pinMode(WATER_PUMP, OUTPUT);
  dht.begin();

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
}

void loop() {
  if(counter * 1000 == UPDATE_TIME){
    updateHumidity();
    updateLight();
    updateTemp();
    counter = 0;
  }
  
  getCurrentTime();  
  
  automaticWatering();
  scheduledWatering();

  counter++;
  delay(1000); 
}

void automaticWatering(){
  if(automaticWateringStatus && humidity < minHumidity){
    Firebase.setBool((String) DEVICE_ID + "automatic_watering/notification", true);
    watering();
    Firebase.setBool((String) DEVICE_ID + "automatic_watering/status", false);
  }
}

void scheduledWatering(){
  if(scheduledWateringStatus && currentTime == scheduledWateringTime){
    Firebase.setBool((String) DEVICE_ID + "scheduled_watering/notification", true);
    watering();
  }
}

void updateTemp(){
  Serial.println( dht.readTemperature());
  if(!isnan(dht.readTemperature())){
    temp = dht.readTemperature();

    Firebase.setInt((String) DEVICE_ID + "/temp", temp);
  }
}

void updateHumidity(){ 
  humidity = map(analogRead(HUMIDITY_SENSOR), 4095, 1400, 0, 100);
  Firebase.setInt((String) DEVICE_ID + "/humidity", humidity);
}

void updateLight(){
  light = map(analogRead(LIGHT_SENSOR), 3600, 2000, 100 , 0);
  Firebase.setInt((String) DEVICE_ID + "/light", light);
}

void watering(){
  digitalWrite(WATER_PUMP, HIGH);
  Serial.println("Zaljevam!");
  delay(DURATION);
  digitalWrite(WATER_PUMP, LOW); 
  Serial.println("Gotovo zaljevanje!");
}

void getCurrentTime() {
  char buffer [30];
  time_t tnow = time(nullptr);
  strftime(buffer, 30, "%X", localtime(&tnow));
  currentTime = (String) buffer;
  Serial.println(currentTime);
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

