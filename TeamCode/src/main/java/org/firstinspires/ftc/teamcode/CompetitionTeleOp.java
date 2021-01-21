package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
@TeleOp(name="Truman Competition 2020-2021",group="Master")


public class  CompetitionTeleOp extends OpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();

    boolean clawButtonPushed;           boolean clawOn;
    boolean SSButtonPushed;             boolean SSOn;
    boolean SSnegativeButtonPushed;     boolean SSnegativeOn;
    double shooterSpeed;
    double intakeSpeed;

    //Rings intake
    double liftServoSpeedA;
    boolean LSButtonPushedA;             boolean LSOnA;
    boolean LSnegativeButtonPushedA;     boolean LSnegativeOnA;

    //Conveyor
    double liftServoSpeedB;
    boolean LSButtonPushedB;             boolean LSOnB;
    boolean LSnegativeButtonPushedB;     boolean LSnegativeOnB;

    public void init() {
        robot.init(hardwareMap);
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



        //Controls arm servo
        if (gamepad1.dpad_right && !clawButtonPushed) {
            robot.claw.setPosition((clawOn ? 1 : .2));
            clawOn = !clawOn;
            clawButtonPushed = true;
        } else if (!gamepad1.dpad_right && clawButtonPushed) clawButtonPushed = false;

        //Controls intake direction
        if (gamepad2.b && !LSButtonPushedA) {
            liftServoSpeedA=liftServoSpeedA+1;
            LSOnA = !LSOnA;
            LSButtonPushedA = true;
        } else if (!gamepad2.b && LSButtonPushedA) LSButtonPushedA = false;

        if (gamepad2.a && !LSnegativeButtonPushedA) {
            liftServoSpeedA = liftServoSpeedA-1;
            LSnegativeOnA = !LSnegativeOnA;
            LSnegativeButtonPushedA = true;
        } else if (!gamepad2.a && LSnegativeButtonPushedA) LSnegativeButtonPushedA = false;

        //Controls conveyor direction
        if (gamepad2.y && !LSButtonPushedB) {
            liftServoSpeedB=liftServoSpeedB+.9;
            LSOnB = !LSOnB;
            LSButtonPushedB = true;
        } else if (!gamepad2.y && LSButtonPushedB) LSButtonPushedB = false;

        if (gamepad2.x && !LSnegativeButtonPushedB) {
            liftServoSpeedB = liftServoSpeedB-.9;
            LSnegativeOnB = !LSnegativeOnB;
            LSnegativeButtonPushedB = true;
        } else if (!gamepad2.x && LSnegativeButtonPushedB) LSnegativeButtonPushedB = false;

        //Launch mechanism
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

        //old conveyer code
          robot.lConveyor.setPower(gamepad2.right_trigger-gamepad2.left_trigger);
          robot.rConveyor.setPower(gamepad2.left_trigger-gamepad2.right_trigger);

        //Ring kicker and launch safety servos
        /*//8.333:1 ratio
        //15 degrees per .01 on servo position */
        if (gamepad2.dpad_up) {
            robot.kicker.setPosition(.12);
            robot.safety.setPosition(.6);
        }
        if (gamepad2.dpad_down) {
            robot.kicker.setPosition(0);
            robot.safety.setPosition(0);
        }

        //Speed restraints
        if (shooterSpeed>1) {shooterSpeed=1;}
        if (shooterSpeed<0) {shooterSpeed=0;}

        if (intakeSpeed>.8) {intakeSpeed=.8;}
        if (intakeSpeed<-.8) {intakeSpeed=-.8;}

        if (liftServoSpeedA>1) {liftServoSpeedA=1;}
        if (liftServoSpeedA<-1) {liftServoSpeedA=-1;}
        if (liftServoSpeedB>.9) {liftServoSpeedB=.9;}
        if (liftServoSpeedB<-.9) {liftServoSpeedB=-.9;}


//      Controls arm direction
        if (gamepad1.dpad_up && gamepad1.dpad_left) robot.arm.setPower(.4);
        else if (gamepad1.dpad_down && gamepad1.dpad_left) robot.arm.setPower(-.4);
        else robot.arm.setPower(0);

        //Drops intake mechanism
        if(gamepad1.x)
        robot.intakeservo.setPosition(-1);

        //Shooter speed
        robot.shooter.setPower(-shooterSpeed);

        //Intake galore
        robot.lIntake.setPower(liftServoSpeedB);

        robot.rIntake.setPower(-liftServoSpeedB);

        telemetry.addData("Shooter Speed", shooterSpeed);
        telemetry.update();
    }
}

