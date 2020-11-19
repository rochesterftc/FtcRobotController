package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;


/**
 * Created by George on 11/9/2020.
 */
@Autonomous(name = "Time Autonomous", group = "Competition")

public class TimeCompetitionAutonomous extends LinearOpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();

    int var;

    //38.5in forward in 1 second @ 12.2v
    long YMSPerInch = 260;
    long XMSPerInch;
    //140* left in 1 second @ 12.17v
    long secondsPerDegree = 71;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        robot.fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
        timeDriveXY(6, .75, "backward");

        //move to 1st power shot position
        timeDriveXY(45, 1, "right");

        //shoot and keep motor running
/*        robot.shooter.setPower(1);
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
*/
    }

    public void timeDriveXY(long inches, double speed, String direction) {

        if (direction == "forward") {
            robot.fr.setPower(speed);
            robot.br.setPower(speed);
            robot.fl.setPower(-speed);
            robot.bl.setPower(-speed);
            sleep(inches*YMSPerInch);
        }
        if (direction == "backward") {
            robot.fr.setPower(-speed);
            robot.br.setPower(-speed);
            robot.fl.setPower(speed);
            robot.bl.setPower(speed);
            sleep(inches*YMSPerInch);
        }
        if (direction == "left") {
            robot.fr.setPower(speed);
            robot.br.setPower(-speed);
            robot.fl.setPower(speed);
            robot.bl.setPower(-speed);
            sleep(inches*XMSPerInch);
        }
        if (direction == "right") {
            robot.fr.setPower(-speed);
            robot.br.setPower(speed);
            robot.fl.setPower(-speed);
            robot.bl.setPower(speed);
            sleep(inches*XMSPerInch);
        }

        robot.fr.setPower(0);
        robot.br.setPower(0);
        robot.fl.setPower(0);
        robot.bl.setPower(0);
    }

    public void timeTurn(int degrees, double speed, String direction) {
        if (direction == "left") {
            robot.fr.setPower(speed);
            robot.br.setPower(speed);
            robot.fl.setPower(speed);
            robot.bl.setPower(speed);
        }
        if (direction == "right") {
            robot.fr.setPower(-speed);
            robot.br.setPower(-speed);
            robot.fl.setPower(-speed);
            robot.bl.setPower(-speed);
        }

        sleep(degrees*secondsPerDegree);

        robot.fr.setPower(0);
        robot.br.setPower(0);
        robot.fl.setPower(0);
        robot.bl.setPower(0);
    }
}