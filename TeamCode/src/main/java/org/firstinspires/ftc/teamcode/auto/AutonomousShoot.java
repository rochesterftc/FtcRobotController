package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HolonomicTeleop;
import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;


/**
 * Created by George on 10/11/2020.
 */
//@Disabled
@Autonomous(name = "Shoot", group = "Testing")

public class AutonomousShoot extends LinearOpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        robot.shooter.setPower(1);
        sleep(10000);
        robot.conveyor.setPower(-0.3);
        sleep(10000);
        robot.shooter.setPower(0);
        robot.conveyor.setPower(0);

    }
}