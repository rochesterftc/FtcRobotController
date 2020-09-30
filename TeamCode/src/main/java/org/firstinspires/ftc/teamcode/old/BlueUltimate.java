package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by George on 9/27/2019.
 */
@Autonomous(name = "Blue Ultimate", group = "Competition")
@Disabled
public class BlueUltimate extends LinearOpMode {

    DcMotor fr;
    DcMotor fl;
    DcMotor br;
    DcMotor bl;

    @Override
    public void runOpMode() {

        fr = hardwareMap.dcMotor.get("front right");
        fl = hardwareMap.dcMotor.get("front left");
        br = hardwareMap.dcMotor.get("back right");
        bl = hardwareMap.dcMotor.get("back left");


        driveXY (1, 1, "right");

        /*
        waitForStart();

        //foundation = 34.5 by 18.5
        driveXY (10, 1, "forward");
        driveXY (30, .5, "left");
        //Vuforia happens
        driveXY (8, 1, "forward");
        //grab skystone
        driveXY (8, 1, "backward");
        turn (90, 1, "left");

        driveXY (100, 1, "forward");
        turn (90, 1, "left");
        //leftClamp foundation
        driveXY (30, 1, "backward");
        driveXY (30, 1, "right");
        driveXY (40, 1, "forward"):
        driveXY (30, 1, "left");
        driveXY (10, 1, "backward");

        */

    }

    public void driveXY(float inches, double speed, String direction) {

        //1120 counts per rotation
        //8 inches per rotation
        //countsPerInch counts per inch

        float XcountsPerInch = 1120;
        float YcountsPerInch = 1120;

        if (direction == "forward") {
            fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
            br.setMode(DcMotor.RunMode.RESET_ENCODERS);
            fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
            bl.setMode(DcMotor.RunMode.RESET_ENCODERS);

            fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            fr.setTargetPosition(Math.round(inches * XcountsPerInch));
            br.setTargetPosition(Math.round(inches * XcountsPerInch));
            fl.setTargetPosition(-Math.round(inches * XcountsPerInch));
            bl.setTargetPosition(-Math.round(inches * XcountsPerInch));

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

        if (direction == "backward") {
            fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
            br.setMode(DcMotor.RunMode.RESET_ENCODERS);
            fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
            bl.setMode(DcMotor.RunMode.RESET_ENCODERS);

            fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            fr.setTargetPosition(-Math.round(inches * XcountsPerInch));
            br.setTargetPosition(-Math.round(inches * XcountsPerInch));
            fl.setTargetPosition(Math.round(inches * XcountsPerInch));
            bl.setTargetPosition(Math.round(inches * XcountsPerInch));

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

        if (direction == "left") {
            fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
            br.setMode(DcMotor.RunMode.RESET_ENCODERS);
            fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
            bl.setMode(DcMotor.RunMode.RESET_ENCODERS);

            fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            fr.setTargetPosition(Math.round(inches * YcountsPerInch));
            br.setTargetPosition(-Math.round(inches * YcountsPerInch));
            fl.setTargetPosition(Math.round(inches * YcountsPerInch));
            bl.setTargetPosition(-Math.round(inches * YcountsPerInch));

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

        if (direction == "right") {
            fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
            br.setMode(DcMotor.RunMode.RESET_ENCODERS);
            fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
            bl.setMode(DcMotor.RunMode.RESET_ENCODERS);

            fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            fr.setTargetPosition(-Math.round(inches * YcountsPerInch));
            br.setTargetPosition(Math.round(inches * YcountsPerInch));
            fl.setTargetPosition(-Math.round(inches * YcountsPerInch));
            bl.setTargetPosition(Math.round(inches * YcountsPerInch));

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
    }

    public void turn(int degrees, double speed, String direction) {

        //1120 counts per rotation
        //60 degrees per rotation
        //countsPerDegree66 counts per degree

        int countsPerDegree = 25;

        if(direction == "left") {
            fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
            br.setMode(DcMotor.RunMode.RESET_ENCODERS);
            fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
            bl.setMode(DcMotor.RunMode.RESET_ENCODERS);

            fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            fr.setTargetPosition(Math.round(degrees * countsPerDegree));
            br.setTargetPosition(Math.round(degrees * countsPerDegree));
            fl.setTargetPosition(Math.round(degrees * countsPerDegree));
            bl.setTargetPosition(Math.round(degrees * countsPerDegree));

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
        if(direction == "right") {

            fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
            br.setMode(DcMotor.RunMode.RESET_ENCODERS);
            fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
            bl.setMode(DcMotor.RunMode.RESET_ENCODERS);

            fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            fr.setTargetPosition(-Math.round(degrees * countsPerDegree));
            br.setTargetPosition(-Math.round(degrees * countsPerDegree));
            fl.setTargetPosition(-Math.round(degrees * countsPerDegree));
            bl.setTargetPosition(-Math.round(degrees * countsPerDegree));

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
    }

/*    public void lift (float inches, double speed, String direction) {
        //1120 counts per rotation
        //3 1/4 inches per rotation
        //344.6 counts per inch

        float lCountsPerInch = 140;

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
    } */
}
