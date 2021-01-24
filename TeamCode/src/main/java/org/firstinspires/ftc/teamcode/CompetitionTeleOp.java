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

        if(gamepad2.a){robot.lIntake.setPower(1); robot.rIntake.setPower(-1);}
        else if(gamepad2.b){robot.lIntake.setPower(-1); robot.rIntake.setPower(1);}
        else {robot.rIntake.setPower(0);robot.lIntake.setPower(0);}

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
          if (gamepad2.right_trigger>.2){
              robot.rConveyor.setPower(-1);
              robot.lConveyor.setPower(-1);
          }
        else if (gamepad2.left_trigger>.2){
            robot.rConveyor.setPower(1);
            robot.lConveyor.setPower(1);
        }
        else {robot.rConveyor.setPower(0);
              robot.lConveyor.setPower(0);}

        //Ring kicker and launch safety servos
        /*//8.333:1 ratio
        //15 degrees per .01 on servo position */
        if (gamepad1.a) {
            robot.kicker.setPosition(.5);
            robot.safety.setPosition(.6);
        }
        if (gamepad1.b) {
            robot.kicker.setPosition(0);
            robot.safety.setPosition(0);
        }

        //Speed restraints
        if (shooterSpeed>1) {shooterSpeed=1;}
        if (shooterSpeed<0) {shooterSpeed=0;}

//      Controls arm direction
        if (gamepad1.left_bumper) robot.arm.setPower(.5);
        else if (gamepad1.right_bumper) robot.arm.setPower(-.4);
        else robot.arm.setPower(0);

        //Drops intake mechanism
        if(gamepad1.x)
        robot.intakeservo.setPosition(0);

        //Shooter speed
        robot.shooter.setPower(-shooterSpeed);


        telemetry.addData("Shooter Speed", (int)(shooterSpeed*10));
        telemetry.update();
    }
}

