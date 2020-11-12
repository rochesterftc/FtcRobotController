package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by George Stevens on 10/4/20.
 */


@TeleOp(name ="Motor Test",group="Testing")

public class PrototypeMotorTest extends OpMode {

    DcMotor conveyor;
    DcMotor shooter;

    public void init() {
        conveyor = hardwareMap.dcMotor.get("elevator");
        shooter = hardwareMap.dcMotor.get("shooter");

    }

    public void loop() {

        conveyor.setPower(-gamepad1.left_trigger);
        shooter.setPower(-gamepad1.right_trigger);

    }
}