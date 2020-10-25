package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@Autonomous(name = "Autonomous Color Sensor", group = "Testing")

public class AutonomousColorSensor extends LinearOpMode {

    ColorSensor sensorColor;

    @Override
    public void runOpMode() {

        // get a reference to the color sensor.
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");

        // wait for the start button to be pressed.
        waitForStart();

        // loop and read the RGB and distance data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
        while (opModeIsActive()) {

            if (sensorColor.blue()>sensorColor.red() && sensorColor.blue()>sensorColor.green()) {
                telemetry.addData("On line: ", "Yes");
                
            }
            else {
                telemetry.addData("On line: ", "No");
            }

            telemetry.update();
        }
    }
}
