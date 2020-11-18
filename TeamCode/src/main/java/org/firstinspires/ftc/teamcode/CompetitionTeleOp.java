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


    }

    public void loop() {
        //1:1 28  counts per rotation
        //3:1 84  counts per rotation
        //5:1 140 counts per rotation

        if (gamepad2.b && !armButtonPushed) {
            robot.intakemotor.setPower(.2); }
        if (gamepad2.a && !intakeButtonPushed) {
            robot.intakemotor.setPower(-.2); }

        if (gamepad1.b && !armButtonPushed) {
            robot.intakemotor.setPower(.5); }
        if (gamepad1.a && !intakeButtonPushed) {
            robot.intakemotor.setPower(-.5); }

        float z = gamepad1.left_stick_x;
        float x = -gamepad1.right_stick_x;
        float y = gamepad1.left_stick_y;


        robot.fl.setPower(y - x + z);
        robot.fr.setPower(-y - x - z);
        robot.bl.setPower(y + x - z);
        robot.br.setPower(-y + x + z);


        if (gamepad1.y && !clawButtonPushed) {
            robot.claw.setPosition((clawOn ? 0.7 : 0.3));
            clawOn = !clawOn;
            clawButtonPushed = true;
        } else if (!gamepad2.y && clawButtonPushed) clawButtonPushed = false;

        if (gamepad2.x && !IsButtonPushed) {
            robot.intakeservo.setPower((IsOn ? 1:0));
            IsOn = !IsOn;
            IsButtonPushed = true;
        } else if (!gamepad2.x && IsButtonPushed) IsButtonPushed = false;

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

