package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;


/**
 * Created by George on 11/9/2020.
 */
@Autonomous(name = "Compeition Autonomous", group = "Competition")

public class CompetitionAutonomous extends LinearOpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();

    int var;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        /*Computervision code*/

        //move to the white shooter line, then the corner of the white line and 1st blue box
        if (var == 1 || var == 3) {
            do {
                robot.fr.setPower(-.5);
                robot.fl.setPower(.5);
                robot.br.setPower(-.5);
                robot.bl.setPower(.5);
            }
            while (robot.sensorColor.red() < 200 || robot.sensorColor.green() < 200 || robot.sensorColor.blue() < 200);

            robot.fr.setPower(0);
            robot.fl.setPower(0);
            robot.br.setPower(0);
            robot.bl.setPower(0);

            sleep(250);

            do {
                robot.fr.setPower(-.5);
                robot.fl.setPower(-.5);
                robot.br.setPower(.5);
                robot.bl.setPower(.5);
            }
            while (robot.sensorColor.blue() < robot.sensorColor.red() || robot.sensorColor.blue() < robot.sensorColor.green());

            robot.fr.setPower(0);
            robot.fl.setPower(0);
            robot.br.setPower(0);
            robot.bl.setPower(0);
        }
        if (var == 2) {
            do {
                robot.fr.setPower(-.5);
                robot.fl.setPower(.5);
                robot.br.setPower(-.5);
                robot.bl.setPower(.5);
            }
            while (robot.sensorColor.red() < 200 || robot.sensorColor.green() < 200 || robot.sensorColor.blue() < 200);

            robot.fr.setPower(0);
            robot.fl.setPower(0);
            robot.br.setPower(0);
            robot.bl.setPower(0);

            sleep(250);

            do {
                robot.fr.setPower(.5);
                robot.fl.setPower(.5);
                robot.br.setPower(-.5);
                robot.bl.setPower(-.5);
            }
            while (robot.sensorColor.blue() < robot.sensorColor.red() || robot.sensorColor.blue() < robot.sensorColor.green());

            robot.fr.setPower(0);
            robot.fl.setPower(0);
            robot.br.setPower(0);
            robot.bl.setPower(0);

            robot.driveXY(4, .5, "left");
        }

        //move behind white line
        robot.driveXY(6, .75, "backward");

        //move to 1st power shot position
        robot.driveXY(45, 1, "right");

        //shoot and keep motor running
        robot.shooter.setPower(1);
        sleep(2000);
        robot.conveyer.setPower(1);
        sleep(500);
        robot.conveyer.setPower(0);

        //move to 2nd power shot position
        robot.driveXY(7, .75, "right");

        //shoot and keep motor running
        robot.shooter.setPower(1);
        sleep(2000);
        robot.conveyer.setPower(1);
        sleep(500);
        robot.conveyer.setPower(0);

        //move to 3rd power shot position
        robot.driveXY(7, .75, "right");

        //shoot and stop motor
        robot.shooter.setPower(1);
        sleep(2000);
        robot.conveyer.setPower(1);
        sleep(500);
        robot.conveyer.setPower(0);
        robot.shooter.setPower(0);

        //move onto white line
        robot.driveXY(6, .75, "forward");

    }
}