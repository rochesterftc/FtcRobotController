package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

@Autonomous(name = "Autonomous Color Sensor", group = "Testing")

public class AutonomousColorSensor extends LinearOpMode {

     HardwareHolonomicChassis robot   = new HardwareHolonomicChassis();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);
        
        // wait for the start button to be pressed.
        waitForStart();

        //drive forward until the robot detects white
        do {
            robot.fr.setPower(.5);
            robot.fl.setPower(-.5);
            robot.br.setPower(.5);
            robot.bl.setPower(-.5);
        }
        while (robot.sensorColor.red() < 200 || robot.sensorColor.green() < 200 || robot.sensorColor.blue() < 200);

        robot.fr.setPower(0);
        robot.fl.setPower(0);
        robot.br.setPower(0);
        robot.bl.setPower(0);

        sleep(500);

        //Drive left until the robot detects blue
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

        sleep(500);

        //Drive backward for .5 seconds
        robot.fr.setPower(-.5);
        robot.fl.setPower(.5);
        robot.br.setPower(-.5);
        robot.bl.setPower(.5);
        sleep(500);
        robot.fr.setPower(0);
        robot.fl.setPower(0);
        robot.br.setPower(0);
        robot.bl.setPower(0);

        // loop and read the RGB and distance data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
        while (opModeIsActive()) {

            if (robot.sensorColor.blue()>robot.sensorColor.red() && robot.sensorColor.blue()>robot.sensorColor.green()) {
                telemetry.addData("On line: ", "Yes");
            }
            else {
                telemetry.addData("On line: ", "No");

                if (robot.sensorColor.red() > 200 && robot.sensorColor.green() > 200 && robot.sensorColor.blue() > 200) {
                    telemetry.addData("On line: ", "Detecting white");
                }
            }

            telemetry.update();
        }
    }
}
