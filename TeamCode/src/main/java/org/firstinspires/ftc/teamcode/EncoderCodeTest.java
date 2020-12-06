package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
 * Created by George on 11/9/2020.
 */

@Autonomous(name = "EncoderCodeTest", group = "Testing")

public class  EncoderCodeTest extends LinearOpMode {

    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;

    float pi = (float)Math.PI; //Float version of Pi, which is normally a double. This allows the encoder calculations to actually work.
    public float YcountsPerInch = 288/(pi*3.875f);
    public float XcountsPerInch = 288/(pi*3.875f);
    public float countsPerDegree = 288/360;


    @Override
    public void runOpMode() {

        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");

        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Finished init", "yes");
        telemetry.update();

        waitForStart();
        telemetry.addData("Started Autonomous", "yes");
        telemetry.update();

        driveXY(10, 1, "right");
        driveXY(10, 1, "forward");

        telemetry.addData("Finished Autonomous", "yes");
        telemetry.update();

    }


    public void driveXY(float inches, double speed, String direction) {

        float YCountsPerInch = YcountsPerInch;
        float XCountsPerInch = XcountsPerInch;

        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
            fr.setTargetPosition(Math.round(inches * XCountsPerInch));
            br.setTargetPosition(-Math.round(inches * XCountsPerInch));
            fl.setTargetPosition(Math.round(inches * XCountsPerInch));
            bl.setTargetPosition(-Math.round(inches * XCountsPerInch));
        }
        if (direction == "right") {
            fr.setTargetPosition(-Math.round(inches * XCountsPerInch));
            br.setTargetPosition(Math.round(inches * XCountsPerInch));
            fl.setTargetPosition(-Math.round(inches * XCountsPerInch));
            bl.setTargetPosition(Math.round(inches * XCountsPerInch));
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
            // Display it for the driver.
            telemetry.addData("Path2",  "Running at %7d :%7d",
                    fl.getCurrentPosition(),
                    fr.getCurrentPosition());
            telemetry.update();

        }

        fr.setPower(0);
        br.setPower(0);
        fl.setPower(0);
        bl.setPower(0);
    }
}