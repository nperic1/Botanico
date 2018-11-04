#include <ESP8266WiFi.h>
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>
#include <ESP8266TimeAlarms.h>
#include <FirebaseArduino.h>
#include "DHT.h"

#define DEVICEID "235112"
#define DHTPIN 0
#define DHTTYPE DHT11   
#define FIREBASE_HOST "botanico-50074.firebaseio.com"
#define FIREBASE_AUTH "XxxXIEZstrWMWCNNnvR7RUGyizc8cjtBk8E56g4V"

DHT dht(DHTPIN, DHTTYPE);
int temp = 0;

void setup() {
  Serial.begin(9600);
  dht.begin(); 
  
  WiFiManager wifiManager;
  wifiManager.autoConnect("botanicoAP");
  //initializeWifi();
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  initializeClock();

  Alarm.timerRepeat(60, setTemp()); // Update value every 60 seconds
}

void loop() {
  digitalClockDisplay();

  Alarm.delay(1000); 
  
 /*
  // set value
  Firebase.setFloat("temp", temp);
  // handle error
  if (Firebase.failed()) {
      Serial.print("setting /number failed:");
      Serial.println(Firebase.error());  
      return;
  } /*
  delay(1000);
  
  // update value
  Firebase.setFloat("number", 43.0);
  // handle error
  if (Firebase.failed()) {
      Serial.print("setting /number failed:");
      Serial.println(Firebase.error());  
      return;
  }
  delay(1000);

  // get value 
  Serial.print("number: ");
  Serial.println(Firebase.getFloat("number"));
  delay(1000);

  // remove value
  Firebase.remove("number");
  delay(1000);

  // set string value
  Firebase.setString("message", "hello world");
  // handle error
  if (Firebase.failed()) {
      Serial.print("setting /message failed:");
      Serial.println(Firebase.error());  
      return;
  }
  delay(1000);
  
  // set bool value
  Firebase.setBool("truth", false);
  // handle error
  if (Firebase.failed()) {
      Serial.print("setting /truth failed:");
      Serial.println(Firebase.error());  
      return;
  }
  delay(1000);

  // append a new value to /logs
  String name = Firebase.pushInt("logs", n++);
  // handle error
  if (Firebase.failed()) {
      Serial.print("pushing /logs failed:");
      Serial.println(Firebase.error());  
      return;
  }
  Serial.print("pushed: /logs/");
  Serial.println(name);*/
  
  //get child
  //Serial.println(Firebase.get("daily_watering").getBool("in_use"));
}

void setTemp(){
  // Reading temperature takes about 250 milliseconds!
  // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  temp = dht.readTemperature();
  if (isnan(temp)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }
  Firebase.setInt("235112/temp",temp);
  Serial.print("Temperature:");
  Serial.println(temp);
}

void digitalClockDisplay() {
  time_t tnow = time(nullptr);
  Serial.println(ctime(&tnow));
  Firebase.setString("235112/RTC",ctime(&tnow));
}

void initializeClock() {
  configTime(0, 0, "0.se.pool.ntp.org");
  setenv("TZ", "CET-1CEST,M3.5.0/2,M10.5.0/3", 1);
  tzset();
}

// create the alarms, to trigger at specific times every day (24 hour format)
// Alarm.alarmRepeat(hour, minute, second, [](){ Serial.println("Alarm!");});

// create the alarms, to trigger every n seconds
// Alarm.timerRepeat(n, [](){Serial.println("Alarm!");}); 


// create the alarm, to trigger once after n seconds
// Alarm.timerOnce(n, [](){Serial.println("Alarm!");}); 
