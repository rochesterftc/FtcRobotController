package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;

/**
 * Created by Nathaniel on 11/11/2020.
 */

@TeleOp(name="Color Sensor Test",group="Testing")
//@Disabled

public class colorSensorTest extends OpMode {

    HardwareHolonomicChassis robot   = new HardwareHolonomicChassis();


    public void init() {
        robot.init(hardwareMap);
    }

    public void loop() {

        telemetry.addData("alpha", robot.sensorColor.alpha());
        telemetry.addData("red", robot.sensorColor.red());
        telemetry.addData("green", robot.sensorColor.green());
        telemetry.addData("blue", robot.sensorColor.blue());

        }
    }

