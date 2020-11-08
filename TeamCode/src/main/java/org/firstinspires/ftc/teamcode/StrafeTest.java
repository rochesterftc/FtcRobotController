package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by George on 11/4/2020.
 */

@Autonomous(name = "Strafe Test", group = "Testing")

public class StrafeTest extends LinearOpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        robot.fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.fr.setTargetPosition(Math.round((288 * (15/26))));
        robot.br.setTargetPosition(-Math.round((288 * (15/26))));
        robot.fl.setTargetPosition(Math.round((288 * (15/26))));
        robot.bl.setTargetPosition(-Math.round((288 * (15/26))));

        robot.fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.fr.setPower(.75);
        robot.br.setPower(.75);
        robot.fl.setPower(.75);
        robot.bl.setPower(.75);

        while (robot.fr.isBusy() && robot.br.isBusy() && robot.fl.isBusy() && robot.bl.isBusy()) {
        }

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