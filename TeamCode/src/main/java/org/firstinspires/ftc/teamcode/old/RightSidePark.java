package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by George on 9/27/2019.
 */
@Autonomous(name = "Right Side Park", group = "Park")
@Disabled
public class RightSidePark extends LinearOpMode {

    DcMotor fr;
    DcMotor fl;
    DcMotor br;
    DcMotor bl;
    /*DcMotor arm;
    CRServo wrist;
    Servo clamp;
    Servo foundr;
    Servo foundl;
    Servo stone;*/

    @Override
    public void runOpMode() {

        fr = hardwareMap.dcMotor.get("front right");
        fl = hardwareMap.dcMotor.get("front left");
        br = hardwareMap.dcMotor.get("back right");
        bl = hardwareMap.dcMotor.get("back left");
        /*arm = hardwareMap.dcMotor.get("arm");
        wrist = hardwareMap.crservo.get("wrist");
        clamp = hardwareMap.servo.get("clamp");
        foundr = hardwareMap.servo.get("foundation right");
        foundl = hardwareMap.servo.get("foundation left");
        stone = hardwareMap.servo.get("stone arm");*/


        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        //foundation = 34.5 by 18.5

        driveXY(12, 1, "left");
    }

    public void driveXY(float inches, double speed, String direction) {

        //1120 counts per rotation
        //13 inches per rotation
        //86 countsPerInch counts per X inch

        //8 inches per rotation
        //140 counts per Y inch

        float XcountsPerInch = 86f;
        float YcountsPerInch = 140f;

        fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
        br.setMode(DcMotor.RunMode.RESET_ENCODERS);
        fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
        bl.setMode(DcMotor.RunMode.RESET_ENCODERS);

        if (direction == "forward") {
            fr.setTargetPosition(Math.round(inches * XcountsPerInch));
            br.setTargetPosition(Math.round(inches * XcountsPerInch));
            fl.setTargetPosition(-Math.round(inches * XcountsPerInch));
            bl.setTargetPosition(-Math.round(inches * XcountsPerInch));
        }
        if (direction == "backward") {
            fr.setTargetPosition(-Math.round(inches * XcountsPerInch));
            br.setTargetPosition(-Math.round(inches * XcountsPerInch));
            fl.setTargetPosition(Math.round(inches * XcountsPerInch));
            bl.setTargetPosition(Math.round(inches * XcountsPerInch));
        }
        if (direction == "left") {
            fr.setTargetPosition(Math.round(inches * YcountsPerInch));
            br.setTargetPosition(-Math.round(inches * YcountsPerInch));
            fl.setTargetPosition(Math.round(inches * YcountsPerInch));
            bl.setTargetPosition(-Math.round(inches * YcountsPerInch));
        }
        if (direction == "right") {
            fr.setTargetPosition(-Math.round(inches * YcountsPerInch));
            br.setTargetPosition(Math.round(inches * YcountsPerInch));
            fl.setTargetPosition(-Math.round(inches * YcountsPerInch));
            bl.setTargetPosition(Math.round(inches * YcountsPerInch));
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

        fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
        br.setMode(DcMotor.RunMode.RESET_ENCODERS);
        fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
        bl.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }

    public void turn(int degrees, double speed, String direction) {

        //1120 counts per rotation
        //60 degrees per rotation
        //18.6 countsPerDegree counts per degree

        float countsPerDegree = 18.666f;

        fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
        br.setMode(DcMotor.RunMode.RESET_ENCODERS);
        fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
        bl.setMode(DcMotor.RunMode.RESET_ENCODERS);

        if (direction == "left") {
            fr.setTargetPosition(Math.round(degrees * countsPerDegree));
            br.setTargetPosition(Math.round(degrees * countsPerDegree));
            fl.setTargetPosition(Math.round(degrees * countsPerDegree));
            bl.setTargetPosition(Math.round(degrees * countsPerDegree));
        }
        if (direction == "right") {
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

        fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
        br.setMode(DcMotor.RunMode.RESET_ENCODERS);
        fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
        bl.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }

    public void arm(int locks, double speed, String direction) {

        //1120 counts per rotation
        // degrees per rotation
        // countsPerLock counts per degree

        float countsPerLock = 1260;

        /*arm.setMode(DcMotor.RunMode.RESET_ENCODERS);

        if(direction == "down") {
            arm.setTargetPosition(Math.round(locks * countsPerLock));
        }
        if(direction == "up") {
            arm.setTargetPosition(-Math.round(locks * countsPerLock));
        }

        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm.setPower(speed);

        while (arm.isBusy()) {

        }
        arm.setPower(0);

        arm.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }*/

    }
}
