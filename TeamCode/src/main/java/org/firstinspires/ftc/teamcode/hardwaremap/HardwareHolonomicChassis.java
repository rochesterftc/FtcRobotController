 /* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.hardwaremap;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.DecimalFormat;

 /**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a basic robot chassis with Holonomic drive train(mecanum wheels).
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 *
 * Motor channel:  Front Left drive motor:  "fl"
 * Motor channel:  Front Right drive motor: "fr"
 * Motor channel:  Back Left drive motor:   "bl"
 * Motor channel:  Back Right drive motor:  "br"
 */
public class HardwareHolonomicChassis
{
    /* Public OpMode members. */
    public DcMotor  fl  = null;
    public DcMotor  fr  = null;
    public DcMotor  bl  = null;
    public DcMotor  br  = null;

    public CRServo lIntake = null;
    public CRServo rIntake = null;
    public CRServo mIntake = null;

    public DcMotor  lConveyor  = null;
    public DcMotor  rConveyor  = null;

    public Servo  kicker = null;
    public Servo safety = null;
    public DcMotor  shooter  = null;

    public DcMotor  arm = null;
    public Servo    claw  = null;

    public ColorSensor sensorColor = null;


    /* ENCODER MATH
    Wheel diameter - 3.875 inches
    Circumference - ~12.174 inches
    Drive reduction - 26:15 (1.7333...)
    Core Hex Motor has 288 counts per rev nolution
    Encoder counts per wheel revolution - ~166.154
    */
    float pi = (float)Math.PI; //Float version of Pi, which is normally a double. This allows the encoder calculations to actually work.
    float ratio = 15/26;
    float YcountsPerInch = (288*ratio)/(pi*3.875f);
    float XcountsPerInch = 86f;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareHolonomicChassis(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap HolonomichwMap) {
        // Save reference to Hardware map
        hwMap = HolonomichwMap;

        // Define and Initialize Motors
        fl = hwMap.get(DcMotor.class, "fl");
        fr = hwMap.get(DcMotor.class, "fr");
        bl = hwMap.get(DcMotor.class, "bl");
        br = hwMap.get(DcMotor.class, "br");

        lIntake=hwMap.get(CRServo.class, "left intake");
        rIntake=hwMap.get(CRServo.class, "right intake");
        mIntake=hwMap.get(CRServo.class,"middle intake");

        lConveyor = hwMap.get(DcMotor.class, "left conveyor");
        rConveyor = hwMap.get(DcMotor.class, "right conveyor");

        kicker = hwMap.get(Servo.class, "kicker");
        safety = hwMap.get(Servo.class, "safety");
        shooter = hwMap.get(DcMotor.class, "shooter");

        arm = hwMap.get(DcMotor.class, "arm");
        claw = hwMap.get(Servo.class, "claw");

        sensorColor = hwMap.get(ColorSensor.class, "sensor_color_distance");

        // Set all motors to zero power
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        //Reset encoders
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Change mode to RUN_USING_ENCODERS after resetting encoders because otherwise it won't work for some reason.
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //When given a power of 0, motors will brake instead of coast.
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void driveXY(float inches, double speed, String direction) {

        //Reset encoders
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Change mode to RUN_USING_ENCODERS after resetting encoders because otherwise it won't work for some reason.
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        if (direction == "forward") {
            fr.setTargetPosition(Math.round(inches * YcountsPerInch));
            br.setTargetPosition(Math.round(inches * YcountsPerInch));
            fl.setTargetPosition(-Math.round(inches * YcountsPerInch));
            bl.setTargetPosition(-Math.round(inches * YcountsPerInch));
        }
        if (direction == "backward") {
            fr.setTargetPosition(-Math.round(inches * YcountsPerInch));
            br.setTargetPosition(-Math.round(inches * YcountsPerInch));
            fl.setTargetPosition(Math.round(inches * YcountsPerInch));
            bl.setTargetPosition(Math.round(inches * YcountsPerInch));
        }
        if (direction == "left") {
            fr.setTargetPosition(Math.round(inches * XcountsPerInch));
            br.setTargetPosition(-Math.round(inches * XcountsPerInch));
            fl.setTargetPosition(Math.round(inches * XcountsPerInch));
            bl.setTargetPosition(-Math.round(inches * XcountsPerInch));
        }
        if (direction == "right") {
            fr.setTargetPosition(-Math.round(inches * XcountsPerInch));
            br.setTargetPosition(Math.round(inches * XcountsPerInch));
            fl.setTargetPosition(-Math.round(inches * XcountsPerInch));
            bl.setTargetPosition(Math.round(inches * XcountsPerInch));
        }

        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fr.setPower(speed);
        br.setPower(speed);
        fl.setPower(speed);
        bl.setPower(speed);

        while (fr.isBusy() && br.isBusy() && fl.isBusy() && bl.isBusy()) { }

        fr.setPower(0);
        br.setPower(0);
        fl.setPower(0);
        bl.setPower(0);
    }

    public void turn(int degrees, double speed, String direction) {

        //1120 counts per rotation
        //60 degrees per rotation
        //18.6 countsPerDegree counts per degree

        float countsPerDegree = 18.666f;

        //Reset encoders
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Change mode to RUN_USING_ENCODERS after resetting encoders because otherwise it won't work for some reason.
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        if(direction == "left") {
            fr.setTargetPosition(Math.round(degrees * countsPerDegree));
            br.setTargetPosition(Math.round(degrees * countsPerDegree));
            fl.setTargetPosition(Math.round(degrees * countsPerDegree));
            bl.setTargetPosition(Math.round(degrees * countsPerDegree));
        }
        if(direction == "right") {
            fr.setTargetPosition(-Math.round(degrees * countsPerDegree));
            br.setTargetPosition(-Math.round(degrees * countsPerDegree));
            fl.setTargetPosition(-Math.round(degrees * countsPerDegree));
            bl.setTargetPosition(-Math.round(degrees * countsPerDegree));
        }

        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fr.setPower(speed);
        br.setPower(speed);
        fl.setPower(speed);
        bl.setPower(speed);

        while (fr.isBusy() && br.isBusy() && fl.isBusy() && bl.isBusy()) {

         }
         fr.setPower(0);
         br.setPower(0);
         fl.setPower(0);
         bl.setPower(0);
     }

     /**
      * Creates a toggle switch controlled by a button input
       * @param button The button or other input to control the toggle
      * @param buttonPushed
      * @param toggleState
      * @return returns toggleState
      */
     public boolean toggle(boolean button, boolean buttonPushed, boolean toggleState) {
         if (button && !buttonPushed) {
             toggleState = !toggleState;
             buttonPushed = true;
         } else if (!button && buttonPushed) buttonPushed = false;
         return toggleState;
     }
 }

