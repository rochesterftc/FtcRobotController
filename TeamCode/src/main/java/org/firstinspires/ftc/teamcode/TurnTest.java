package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;

/**
 * Created by George on 11/4/2020.
 */

@Autonomous(name = "Turn Test", group = "Testing")

public class TurnTest extends LinearOpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        robot.fr.setPower(1);
        robot.br.setPower(1);
        robot.fl.setPower(1);
        robot.bl.setPower(1);
        sleep(1000);
        robot.fr.setPower(0);
        robot.br.setPower(0);
        robot.fl.setPower(0);
        robot.bl.setPower(0);
    }
}