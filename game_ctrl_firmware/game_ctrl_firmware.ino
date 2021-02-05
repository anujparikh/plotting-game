const int LED_PIN = 13;

// REMOTE 1
#define RM1_POWER_PIN (3)
#define RM1_X_PIN (A0)
#define RM1_Y_PIN (A1)


// REMOTE 2
#define RM2_POWER_PIN (4)
#define RM2_X_PIN (A2)
#define RM2_Y_PIN (A3)

int rm1_x_val, rm1_y_val, rm2_x_val, rm2_y_val;

void setup() {
  // put your setup code here, to run once:
  pinMode(RM1_POWER_PIN, OUTPUT);
  digitalWrite(RM1_POWER_PIN, OUTPUT);
  pinMode(RM2_POWER_PIN, OUTPUT);
  digitalWrite(RM2_POWER_PIN, OUTPUT);
  
  pinMode(RM1_X_PIN, INPUT);
  pinMode(RM1_Y_PIN, INPUT);
  pinMode(RM2_X_PIN, INPUT);
  pinMode(RM2_Y_PIN, INPUT);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  rm1_x_val = analogRead(RM1_X_PIN);
  rm1_y_val = analogRead(RM1_Y_PIN);

  rm2_x_val = analogRead(RM2_X_PIN);
  rm2_y_val = analogRead(RM2_Y_PIN);
  Serial.print(rm1_x_val); Serial.print(","); Serial.print(rm1_y_val); Serial.print(",");
  Serial.print(rm2_x_val); Serial.print(","); Serial.print(rm2_y_val); Serial.println();
  delay(50);
}
