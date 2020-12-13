package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.internal.android.dex.Code;
import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;

import static java.lang.Thread.sleep;

/**
 * Created by Rochesterftc10303 on 10/4/2018.
 */
@TeleOp(name="Competition2020-2021",group="Master")

public class  CompetitionTeleOp extends OpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();

    boolean clawButtonPushed;
    boolean clawOn;
    boolean IsButtonPushed;
    boolean IsOn;
    boolean LSButtonPushed;
    boolean LSOn;
    boolean LSnegativeButtonPushed;
    boolean LSnegativeOn;
    boolean SSButtonPushed;
    boolean SSOn;
    boolean SSnegativeButtonPushed;
    boolean SSnegativeOn;
    double shooterSpeed;
    double intakeSpeed;

    // Declare OpMode members.
   // private boolean helloThereFound;      // Sound file present flag

    public void init() {
        robot.init(hardwareMap);
        // Test Branch
        // Determine Resource IDs for sounds built into the RC application.


    }

    public void loop() {
        //1:1   28   counts per rotation
        //3:1   84   counts per rotation
        //5:1   140  counts per rotation
        //100:1 2800 counts per rotation
        float z = gamepad1.left_stick_x;
        float x = gamepad1.right_stick_x;
        float y = -gamepad1.left_stick_y;
        //x = turning
        //y = forward
        //z = strafing
        robot.fl.setPower(y + x + z);
        robot.fr.setPower(-y + x + z);
        robot.bl.setPower(y + x - z);
        robot.br.setPower(-y + x - z);

//      Controls arm direction
        if (gamepad1.b) robot.arm.setPower(.3);
        else if (gamepad1.a) robot.arm.setPower(-.3);
        else robot.arm.setPower(0);

        //Controls arm servo
        if (gamepad1.y && !clawButtonPushed) {
            robot.claw.setPosition((clawOn ? 1 : .2));
            clawOn = !clawOn;
            clawButtonPushed = true;
        } else if (!gamepad1.y && clawButtonPushed) clawButtonPushed = false;

//      Controls intake direction
        if (gamepad2.b && !LSButtonPushed) {
            intakeSpeed=intakeSpeed+.1;
            LSOn = !LSOn;
            SSButtonPushed = true;
        } else if (!gamepad2.b && LSButtonPushed) LSButtonPushed = false;

        if (gamepad2.a && !LSnegativeButtonPushed) {
            intakeSpeed=intakeSpeed-.5;
            LSnegativeOn = !LSnegativeOn;
            LSnegativeButtonPushed = true;
        } else if (!gamepad2.a && LSnegativeButtonPushed) LSnegativeButtonPushed = false;

        if (intakeSpeed>1) {intakeSpeed=1;}
        if (intakeSpeed<0) {intakeSpeed=0;}

//      Drops intake motor
        if (gamepad2.y && !IsButtonPushed) {
            robot.intakeservo.setPosition(IsOn ? .5 : .25);
            IsOn = !IsOn;
            IsButtonPushed = true;
        } else if (!gamepad2.y && IsButtonPushed) IsButtonPushed = false;

        if (gamepad2.left_bumper && !SSButtonPushed) {
            shooterSpeed=shooterSpeed+.1;
            SSOn = !SSOn;
            SSButtonPushed = true;
        } else if (!gamepad2.left_bumper && SSButtonPushed) SSButtonPushed = false;

        if (gamepad2.right_bumper && !SSnegativeButtonPushed) {
            shooterSpeed=shooterSpeed-.1;
            SSnegativeOn = !SSnegativeOn;
            SSnegativeButtonPushed = true;
        } else if (!gamepad2.right_bumper && SSnegativeButtonPushed) SSnegativeButtonPushed = false;

        if (shooterSpeed>1) {shooterSpeed=1;}
        if (shooterSpeed<0) {shooterSpeed=0;}
        //Conveyor controls
        robot.conveyor.setPower(-gamepad2.left_trigger);
        robot.conveyor.setPower(gamepad2.right_trigger);
        robot.shooter.setPower(shooterSpeed);
        robot.intakemotor.setPower(intakeSpeed);
        telemetry.addData("Shooter speed", shooterSpeed);
        telemetry.addData("intake speed", intakeSpeed);
        telemetry.update();
    }
}

