package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by George on 10/28/2020.
 */

@TeleOp(name="Broken Encoder Calibration",group="Testing")
@Disabled
public class BrokenEncoderTest extends OpMode {

    HardwareHolonomicChassis robot   = new HardwareHolonomicChassis();

    public void init() {

        robot.init(hardwareMap);

    }

    public void loop() {
        if (gamepad1.a) {
            robot.driveXY(5, .75, "forward");
        }
        if (gamepad1.b) {
            robot.fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.fr.setTargetPosition(Math.round(5*(288*(26/15))));
            robot.br.setTargetPosition(-Math.round(5*(288*(26/15))));
            robot.fl.setTargetPosition(Math.round(5*(288*(26/15))));
            robot.bl.setTargetPosition(-Math.round(5*(288*(26/15))));

            robot.fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            robot.fr.setPower(.75);
            robot.br.setPower(.75);
            robot.fl.setPower(.75);
            robot.bl.setPower(.75);

            while (robot.fr.isBusy() && robot.br.isBusy() && robot.fl.isBusy() && robot.bl.isBusy()) { }

            robot.fr.setPower(0);
            robot.br.setPower(0);
            robot.fl.setPower(0);
            robot.bl.setPower(0);

            robot.fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        }
        if (gamepad1.x) {
            robot.fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.fr.setTargetPosition(Math.round(5*(288*(26/15))));
            robot.br.setTargetPosition(Math.round(5*(288*(26/15))));
            robot.fl.setTargetPosition(Math.round(5*(288*(26/15))));
            robot.bl.setTargetPosition(Math.round(5*(288*(26/15))));

            robot.fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            robot.fr.setPower(.75);
            robot.br.setPower(.75);
            robot.fl.setPower(.75);
            robot.bl.setPower(.75);

            while (robot.fr.isBusy() && robot.br.isBusy() && robot.fl.isBusy() && robot.bl.isBusy()) { }

            robot.fr.setPower(0);
            robot.br.setPower(0);
            robot.fl.setPower(0);
            robot.bl.setPower(0);

            robot.fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        }

    }
}

