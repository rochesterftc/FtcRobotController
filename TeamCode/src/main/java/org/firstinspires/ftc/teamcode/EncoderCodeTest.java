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
    float ratio = 15/26;
    public float YcountsPerInch = (288*ratio)/(pi*3.875f);
    public float XcountsPerInch = (288*ratio);
    public float countsPerDegree = (288*ratio)/(360);


    @Override
    public void runOpMode() {

        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");

        waitForStart();
        driveXY(10, 1, "right");

    }


    public void driveXY(float inches, double speed, String direction) {

        float YCountsPerInch = YcountsPerInch;
        float XCountsPerInch = XcountsPerInch;
        
        fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
        br.setMode(DcMotor.RunMode.RESET_ENCODERS);
        fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
        bl.setMode(DcMotor.RunMode.RESET_ENCODERS);

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
        }

        fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
        br.setMode(DcMotor.RunMode.RESET_ENCODERS);
        fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
        bl.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }
}