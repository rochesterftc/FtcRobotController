package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;

/**
 * Created by George on 10/11/2020.
 */

@TeleOp(name="Toggle Test",group="Testing")
//@Disabled

public class toggleTest extends OpMode {

    HardwareHolonomicChassis robot   = new HardwareHolonomicChassis();

    boolean buttonPushed = false;
    boolean toggleState = false;

    public void init() {
    robot.init(hardwareMap);

    }

    public void loop() {

    telemetry.addData("Toggle Output:",robot.toggle(gamepad1.a, buttonPushed, toggleState));

    robot.claw.setPosition(robot.toggle(gamepad1.a, buttonPushed, toggleState) ? 0.2:1);

        }
    }

