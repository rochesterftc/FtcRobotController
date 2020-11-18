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
@TeleOp(name="Competition",group="Master")

public class CompetitionTeleOp extends OpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();

    boolean clawButtonPushed;
    boolean clawOn;
    boolean IsButtonPushed;
    boolean IsOn;
    boolean armButtonPushed;
    boolean ArmOn;
    boolean intakeButtonPushed;
    boolean IntakeOn;
    double  armDirection;
    double  intakeMotorDirection;

    // Declare OpMode members.
   // private boolean helloThereFound;      // Sound file present flag

    public void init() {
        robot.init(hardwareMap);
        // Test Branch
        // Determine Resource IDs for sounds built into the RC application.
robot.fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
robot.fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
robot.bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
robot.br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void loop() {
        //1:1 28  counts per rotation
        //3:1 84  counts per rotation
        //5:1 140 counts per rotation
/*
        if (gamepad2.b && !armButtonPushed) {
            // ((ArmOn ? 21 : -21));
             if (ArmOn) {armDirection = 30;}
             else {armDirection = -30;}
            ArmOn = !ArmOn;
            armButtonPushed = true;
        } else if (!gamepad2.b && armButtonPushed) armButtonPushed = false;

        if (gamepad2.b) {
            robot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.arm.setTargetPosition((int)armDirection);
        }

        if (gamepad2.a && !intakeButtonPushed) {
          //  robot.intakemotor.setPower((IntakeOn ? 1 : -1));
            if (ArmOn) {intakeMotorDirection = -55;}
            else {intakeMotorDirection = 55;}
            IntakeOn = !IntakeOn;
            armButtonPushed = true;
        } else if (!gamepad2.a && intakeButtonPushed) intakeButtonPushed = false;
        if (gamepad2.a) {
            robot.intakemotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.intakemotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.intakemotor.setTargetPosition((int)intakeMotorDirection);
        }
*/
        float z = gamepad1.right_stick_x;
        float x = gamepad1.left_stick_x;
        float y = gamepad1.left_stick_y;


        robot.fl.setPower(y - x + z);
        robot.fr.setPower(-y + x + z);
        robot.bl.setPower(y + x - z);
        robot.br.setPower(-y - x - z);


        if (gamepad2.y && !clawButtonPushed) {
            robot.claw.setPosition((clawOn ? 0.7 : 0.3));
            clawOn = !clawOn;
            clawButtonPushed = true;
        } else if (!gamepad2.y && clawButtonPushed) clawButtonPushed = false;

        if (gamepad2.x && !IsButtonPushed) {
            if (IsOn)robot.intakeservo.setPower(1);
            else robot.intakeservo.setPower(0);
            IsOn = !IsOn;
            IsButtonPushed = true;
        } else if (!gamepad2.x && IsButtonPushed) IsButtonPushed = false;

        robot.arm.setPower(gamepad2.right_stick_y);

        robot.Conveyor.setPower(gamepad2.left_trigger);
        robot.shooter.setPower(-gamepad2.right_trigger);

     /*   if (gamepad2.left_bumper) {
            robot.shooter.setPower(.9);
        } else if (gamepad2.right_bumper) {
            robot.shooter.setPower(.1);
        } else {robot.shooter.setPower(0);
        } */
    }
}

