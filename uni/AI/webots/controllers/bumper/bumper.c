/*
 * Copyright 1996-2023 Cyberbotics Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Description:  An example of use of a bumper touch sensor device.
 */

#include <webots/motor.h>
#include <webots/robot.h>
#include <webots/speaker.h>
#include <webots/touch_sensor.h>

#define SPEED 4
#define TIME_STEP 64

int main() {
  WbDeviceTag bumper, speaker;
  WbDeviceTag left_motor, right_motor, prop_motor;
  int movement_counter = 0;
  int left_speed = 0, right_speed = 0, prop_speed = 0, prop_time = 0;

  wb_robot_init();

  /* get a handle the the bumper and activate it. */
  bumper = wb_robot_get_device("bumper");
  speaker = wb_robot_get_device("speaker");
  wb_touch_sensor_enable(bumper, TIME_STEP);

  /* get a handler to the motors and set target position to infinity (speed control) */
  left_motor = wb_robot_get_device("left wheel motor");
  right_motor = wb_robot_get_device("right wheel motor");
  prop_motor = wb_robot_get_device("prop motor");
  wb_motor_set_position(left_motor, INFINITY);
  wb_motor_set_position(right_motor, INFINITY);
  wb_motor_set_position(prop_motor, INFINITY);
  wb_motor_set_velocity(left_motor, 0.0);
  wb_motor_set_velocity(right_motor, 0.0);
  wb_motor_set_velocity(prop_motor, 0.0);

  /* control loop */
  while (wb_robot_step(TIME_STEP) != -1) {
    /*
     * When the touch sensor has detected something we begin the avoidance
     * movement.
     */
    if (wb_touch_sensor_get_value(bumper) > 0)
      movement_counter = 30;

    /*
     * We use the movement_counter to manage the movements of the robot. When the value
     * is 0 we move straight, then when there is another value this means that
     * we are avoiding an obstacle. For avoiding we first move backward for
     * some cycles and then we turn on ourself.
     */
    if (movement_counter <= 0) {
      left_speed = SPEED - movement_counter / 3;
      right_speed = SPEED - movement_counter / 3;

      movement_counter--;
    } else if (movement_counter >= 15) {
      left_speed = -movement_counter / 3;
      right_speed = -movement_counter / 3;

      movement_counter--;
    } else {
      left_speed = -SPEED - movement_counter / 5;
      right_speed = SPEED + movement_counter / 5;

      movement_counter--;
    }

    prop_time ++;
    if(prop_time > 250) {
      prop_speed = 300;
      left_speed = 0;
      right_speed = 0;
      wb_speaker_play_sound(speaker, speaker, "./sound.wav", 1.0, 1.0, 0, false);
    }

    /* Set the motor speeds. */
    wb_motor_set_velocity(left_motor, left_speed);
    wb_motor_set_velocity(right_motor, right_speed);
    wb_motor_set_velocity(prop_motor, prop_speed);
  }

  wb_robot_cleanup();

  return 0;
}
