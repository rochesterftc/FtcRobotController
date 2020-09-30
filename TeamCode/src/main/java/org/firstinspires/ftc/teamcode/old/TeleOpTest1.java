package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by George on 10/11/2018.
 */

@TeleOp(name ="Tele-Op Test",group="Testing")
@Disabled

public class TeleOpTest1 extends OpMode {

    DcMotor fr;
    DcMotor br;
    DcMotor fl;
    DcMotor bl;
    DcMotor arm;
    Servo foundr;
    Servo foundl;
    Servo armLock;

    public void init() {

        fr = hardwareMap.dcMotor.get("front left");
        br = hardwareMap.dcMotor.get("front right");
        fl = hardwareMap.dcMotor.get("back left");
        bl = hardwareMap.dcMotor.get("back right");
        arm = hardwareMap.dcMotor.get("arm");
        foundr = hardwareMap.servo.get("foundation right");
        foundl = hardwareMap.servo.get("foundation left");
        armLock = hardwareMap.servo.get("arm lock");

        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }

    public void loop() {

        //floats to streamline drive code
        float x = gamepad1.left_stick_x;
        float z = -gamepad1.left_stick_y;
        float y = gamepad1.right_stick_x;

        /*
        Holonomic Drive:
        Gamepad 1 left and right sticks control the robot main movement, holding down a moves 5x slower
         */
        if (gamepad1.a) {
            fl.setPower((-y - x + z) / 5);
            bl.setPower((-y + x + z) / 5);
            fr.setPower((-y - x - z) / 5);
            br.setPower((-y + x - z) / 5);
        } else {
            fl.setPower(-y - x + z);
            bl.setPower(-y + x + z);
            fr.setPower(-y - x - z);
            br.setPower(-y + x - z);
        }

        if (gamepad2.a) {
            arm.setPower(.25);
        }
        if (gamepad2.b) {
            arm.setPower(.5);
        }
        if (gamepad2.x) {
            arm.setPower(.75);
        }
        if (gamepad2.y) {
            arm.setPower(1);
        }
        if (gamepad2.right_bumper) {
            arm.setPower(0);
        }
        if (gamepad2.left_bumper) {
            armLock.setPosition(.175);
        }

        /*
        //calculate what the next encoder value should be for the arm
        int armPos = arm.getCurrentPosition();
        int nextPos = Math.round(gamepad2.right_stick_y * 50);
        arm.setTargetPosition(armPos+nextPos);

        //move arm to the next encoder value
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm.setPower(.75);
        while (arm.isBusy()) {}
        arm.setPower(0);
        */

        if (gamepad2.dpad_up) {
            arm.setTargetPosition(300);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm.setPower(1);
            while (arm.isBusy()) {}
            arm.setPower(0);
            arm.setMode(DcMotor.RunMode.RESET_ENCODERS);
        }

        if (gamepad2.dpad_down) {
            arm.setTargetPosition(100);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm.setPower(-1);
            while (arm.isBusy()) {}
            arm.setPower(0);
            arm.setMode(DcMotor.RunMode.RESET_ENCODERS);
        }

        if (gamepad1.a) {
            foundr.setPosition(1);
            foundl.setPosition(0);
        }
        if (gamepad1.b) {
            foundr.setPosition(0);
            foundl.setPosition(1);
        }
    }
}