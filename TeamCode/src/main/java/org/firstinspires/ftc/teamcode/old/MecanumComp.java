package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by George on 10/11/2018.
 */

@TeleOp(name ="Mecanum",group="Competition")
@Disabled

public class MecanumComp extends OpMode {

    DcMotor fr;
    DcMotor br;
    DcMotor fl;
    DcMotor bl;
    Servo foundPull;

    public void init() {

        fr = hardwareMap.dcMotor.get("front left");
        br = hardwareMap.dcMotor.get("front right");
        fl = hardwareMap.dcMotor.get("back left");
        bl = hardwareMap.dcMotor.get("back right");
        foundPull = hardwareMap.servo.get("foundation puller");
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

        if (gamepad1.a) {
            foundPull.setPosition(.5);
        }
        if (gamepad1.b) {
            foundPull.setPosition(0);
        }
    }
}