package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Nathaniel on 11/1/2020.
 */

@TeleOp(name="Mecanum Tele-Op",group="Testing")
//@Disabled

public class HolonomicTeleop extends OpMode {

    HardwareHolonomicChassis robot   = new HardwareHolonomicChassis();


    public void init() {
        robot.init(hardwareMap);

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
    }

    public void loop() {

        float x = gamepad1.left_stick_x;
        float y = gamepad1.left_stick_y;
        float z = gamepad1.right_stick_x;

        robot.fl.setPower(-x-y-z);
        robot.fr.setPower(-x+y-z);
        robot.bl.setPower(x-y-z);
        robot.br.setPower(x+y-z);

        robot.shooter.setPower(gamepad1.right_trigger);

        if (gamepad1.b) {
            robot.conveyer.setPower(1);
        }

        }
    }

