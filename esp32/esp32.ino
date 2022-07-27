#include <Adafruit_MLX90614.h>
//Thêm thư viện hỗ trợ
#include "ESPino32CAM.h"
#include <WiFi.h>
#include <FirebaseESP32.h>
//Cung cấp thông tin quy trình tạo mã thông báo.
#include "addons/TokenHelper.h"
#include "addons/RTDBHelper.h"
#include <Wire.h>






 
// Khai báo thuộc tính
ESPino32CAM cam;   // Image capture object
WiFiUDP ntpUDP;


/* 1. Xác định thông tin đăng nhập WiFi */
#define WIFI_SSID "Aitilen 101/85"
#define WIFI_PASSWORD "Th222222@"

/* 2. Xác định khóa API */
#define API_KEY "AIzaSyDOvesul0bK7OWEzKlq6Xs9QITN9dQXRDs"

/* 3. Xác định URL RTDB */
#define DATABASE_URL "mobileiot-8a1a4-default-rtdb.firebaseio.com/" //<databaseName>.firebaseio.com or <databaseName>.<region>.firebasedatabase.app

/* 4. Xác định người dùng Email và mật khẩu đã đăng ký */
#define USER_EMAIL "ngocduc.bk.hust@gmail.com"
#define USER_PASSWORD "75286628"

//Xác định đối tượng Dữ liệu Firebase
FirebaseData fbdo;

FirebaseAuth auth;
FirebaseConfig configF;

unsigned long sendDataPrevMillis = 0;
long newtemp;
//Khai báo biến nhận dữ liệu cảm biến
Adafruit_MLX90614 mlx = Adafruit_MLX90614();


// Thiết lập chân cho Camera
#define PWDN_GPIO_NUM     32
#define RESET_GPIO_NUM    -1
#define XCLK_GPIO_NUM      0
#define SIOD_GPIO_NUM     26
#define SIOC_GPIO_NUM     27
#define Y9_GPIO_NUM       35
#define Y8_GPIO_NUM       34
#define Y7_GPIO_NUM       39
#define Y6_GPIO_NUM       36
#define Y5_GPIO_NUM       21
#define Y4_GPIO_NUM       19
#define Y3_GPIO_NUM       18
#define Y2_GPIO_NUM        5
#define VSYNC_GPIO_NUM    25
#define HREF_GPIO_NUM     23
#define PCLK_GPIO_NUM     22
#define flash 4
#define I2C_SDA 15
#define I2C_SCL 13

void setup() {
  // Mở cổng Serial với baud 115200
  Serial.begin(115200);
  Serial.println("Start");
  // Thiết lập chân cho flash và turn off
  pinMode(flash,OUTPUT);
  digitalWrite(flash, LOW);
  
// Đăng nhập wifi
  WiFi.begin(WIFI_SSID,WIFI_PASSWORD);
  while (WiFi.status() != WL_CONNECTED){
    delay(500);
    Serial.print(".");
  }

  Firebase.begin(DATABASE_URL, "Rku9lwC38Mwn4r6EGdLiNujbb9an6SFF2fpdusrT");
  Firebase.enableClassicRequest(fbdo, true);

  // khởi động 
  Serial.println("QR CODE STARTED");
  pinMode(2,OUTPUT);
  pinMode(12,OUTPUT);
  pinMode(14,OUTPUT);
  Wire.begin(I2C_SDA,I2C_SCL);
  mlx.begin(); 

}

void loop() {
  // put your main code here, to run repeatedly:

  double temp = mlx.readObjectTempC();
  bool r = true;
  bool g = true;
  bool y = true;
  Firebase.setFloat(fbdo,"NhietDo",temp);
  Firebase.getBool(fbdo,"Green",g);
  Firebase.getBool(fbdo,"Red",r);
  Firebase.getBool(fbdo,"Yellow",y);
  if(g == true){
      digitalWrite(12, HIGH);
    } else if (g == false) {
        digitalWrite(12, LOW);
      }
  if(r == true){
      digitalWrite(14, HIGH);
    } else if (r == false) {
        digitalWrite(14, LOW);
      }
  if(y == true){
      digitalWrite(2, HIGH);
    } else if (y == false) {
        digitalWrite(2, LOW);
      }
  delay(1000);
}
