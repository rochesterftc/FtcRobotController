package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by RochesterFTC11289 on 10/18/2018.
 */

@Disabled
@Autonomous(name = "Right A", group = "Competition")

public class Right extends LinearOpMode {

    DcMotor left;
    DcMotor right;
    DcMotor lift;
    Servo marker;


    @Override
    public void runOpMode() {

        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        lift = hardwareMap.dcMotor.get("lift1");
        marker = hardwareMap.servo.get("marker");

        marker.setPosition(0);

        waitForStart();

        driveForward(20, .75, "forward");
    }

    public void driveForward(float inches, double speed, String direction) {

        //1120 counts per rotation
        //8 inches per rotation
        //140 counts per inch

        if (direction == "forward") {
            left.setMode(DcMotor.RunMode.RESET_ENCODERS);
            right.setMode(DcMotor.RunMode.RESET_ENCODERS);

            left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            left.setTargetPosition(-Math.round(inches * 140));
            right.setTargetPosition(Math.round(inches * 140));

            left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            left.setPower(speed);
            right.setPower(speed);

            while (right.isBusy() && left.isBusy()) {
                telemetry.addData("left value:", left.getCurrentPosition());
                telemetry.addData("right value:", right.getCurrentPosition());
                telemetry.update();
            }
            right.setPower(0);
            right.setPower(0);
        }

        if (direction == "backward") {

            left.setMode(DcMotor.RunMode.RESET_ENCODERS);
            right.setMode(DcMotor.RunMode.RESET_ENCODERS);

            left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            left.setTargetPosition(Math.round(inches * 140));
            right.setTargetPosition(-Math.round(inches * 140));

            left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            left.setPower(speed);
            right.setPower(speed);

            while (right.isBusy() && left.isBusy()) {

            }
            right.setPower(0);
            right.setPower(0);
        }
    }

    public void turn(int degrees, double speed, String direction) {

        //1120 counts per rotation
        //60 degrees per rotation
        //18.66666666 counts per degree

        if(direction == "left") {
            left.setMode(DcMotor.RunMode.RESET_ENCODERS);
            right.setMode(DcMotor.RunMode.RESET_ENCODERS);

            left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            left.setTargetPosition(Math.round(degrees * 18.666666f));
            right.setTargetPosition(Math.round(degrees * 18.666666f));

            left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            right.setPower(speed);
            left.setPower(speed);

            while (right.isBusy() && left.isBusy()) {

            }
            right.setPower(0);
            right.setPower(0);
        }
        if(direction == "right") {

            //1120 counts per rotation
            //60 degrees per rotation
            //18.66666666 counts per degree

            left.setMode(DcMotor.RunMode.RESET_ENCODERS);
            right.setMode(DcMotor.RunMode.RESET_ENCODERS);

            left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            left.setTargetPosition(-Math.round(degrees * 18.666666f));
            right.setTargetPosition(-Math.round(degrees * 18.666666f));

            left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            right.setPower(speed);
            left.setPower(speed);

            while (right.isBusy() && left.isBusy()) {

            }
            right.setPower(0);
            right.setPower(0);

            left.setMode(DcMotor.RunMode.RESET_ENCODERS);
            right.setMode(DcMotor.RunMode.RESET_ENCODERS);
        }
    }

    public void lift (float inches, double speed, String direction) {
        //1120 counts per rotation
        //3 1/4 inches per rotation
        //344.6 counts per inch

        if (direction == "lift") {
            lift.setMode(DcMotor.RunMode.RESET_ENCODERS);

            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            lift.setTargetPosition(Math.round(299*inches));

            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            lift.setPower(speed);

            while (lift.isBusy()) {

            }
            lift.setPower(0);
        }

        if (direction == "drop") {
            lift.setMode(DcMotor.RunMode.RESET_ENCODERS);

            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            lift.setTargetPosition(-Math.round(299*inches));

            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            lift.setPower(speed);

            while (lift.isBusy()) {

            }
            lift.setPower(0);
        }
    }
}
