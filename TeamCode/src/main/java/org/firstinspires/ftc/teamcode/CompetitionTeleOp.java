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

    //Closed/Open
    boolean clawButtonPushed;           boolean clawOn;
    boolean SSButtonPushed;             boolean SSOn;
    boolean SSnegativeButtonPushed;     boolean SSnegativeOn;
    double shooterSpeed;
    double intakeSpeed;

    //Stuff that say in case of robot rebuild
 /*   //Rings intake
    double liftServoSpeedA;
    //Speed up intake
    boolean LSButtonPushedA;             boolean LSOnA;
    //Slow down intake
    boolean LSnegativeButtonPushedA;     boolean LSnegativeOnA;


    //Conveyor
    double liftServoSpeedB;
    boolean LSButtonPushedB;             boolean LSOnB;
    boolean LSnegativeButtonPushedB;     boolean LSnegativeOnB;
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
   if (liftServoSpeedB>.9) {liftServoSpeedB=.9;}
        if (liftServoSpeedB<-.9) {liftServoSpeedB=-.9;}
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

        //Intake galore
        robot.lIntake.setPower(liftServoSpeedA);

        robot.rIntake.setPower(-liftServoSpeedA);

        if (intakeSpeed>.8) {intakeSpeed=.8;}
        if (intakeSpeed<-.8) {intakeSpeed=-.8;}
        if (liftServoSpeedA>1) {liftServoSpeedA=1;}
        if (liftServoSpeedA<-1) {liftServoSpeedA=-1;}*/
    public void init() {robot.init(hardwareMap);
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
        robot.fl.setPower(y + (x/1.5) + z);
        robot.fr.setPower(-y + (x/1.5) + z);
        robot.bl.setPower(y + (x/1.5) - z);
        robot.br.setPower(-y + (x/1.5) - z);

        //Controls the direction of intake by holding A, or B
        //Pull in Rings When A is Pressed
        if (gamepad2.a){robot.lIntake.setPower(1);robot.rIntake.setPower(-1);}
        //Push out Rings When B is Pressed
        else if (gamepad2.b) {robot.lIntake.setPower(-1);robot.rIntake.setPower(1);}
        //When neither a or B(NOR) pressed, set power to 0
        else { robot.lIntake.setPower(0); robot.rIntake.setPower(0);}

        //Controls arm servo
        if (gamepad1.left_bumper && !clawButtonPushed) {
            robot.claw.setPosition((clawOn ? 1 : .2));//Sets position to open or closed
            clawOn = !clawOn;
            clawButtonPushed = true;
        } else if (!gamepad1.left_bumper && clawButtonPushed) clawButtonPushed = false;

        //Launch mechanism
        if (gamepad2.left_bumper && !SSButtonPushed) {
            shooterSpeed=shooterSpeed+.1; //Increases launcher Speed When Left Bumper is pressed
            SSOn = !SSOn;
            SSButtonPushed = true;
        } else if (!gamepad2.left_bumper && SSButtonPushed) SSButtonPushed = false;

        if (gamepad2.right_bumper && !SSnegativeButtonPushed) {
            shooterSpeed=shooterSpeed-.1; //Decreases Launcher Speed When Left Bumper is pressed
            SSnegativeOn = !SSnegativeOn;
            SSnegativeButtonPushed = true;
        } else if (!gamepad2.right_bumper && SSnegativeButtonPushed) SSnegativeButtonPushed = false;

        //Conveyer code
        ///Set Conveyors Forward When Right Trigger is Pulled
        if (gamepad2.right_trigger>.2) {
            robot.rConveyor.setPower(-1);
            robot.lConveyor.setPower(-1);
            robot.mIntake.setPower(-.5);
        }
        //Sets Conveyors Backward When Left Trigger is Pulled
        else if (gamepad2.left_trigger>.2) {
            robot.rConveyor.setPower(1);
            robot.lConveyor.setPower(1);
            robot.mIntake.setPower(.5);
        }
        //Sets Conveyors to Standstill When Neither Left or Right Trigger is Pulled
        else {robot.rConveyor.setPower(0);robot.lConveyor.setPower(0);robot.mIntake.setPower(0);}

        //Ring kicker and launch safety servos
        //Kicks ring into Launcher if dpad-up is Pressed
        if (gamepad2.dpad_up) {
            robot.kicker.setPosition(1);
            robot.safety.setPosition(1);
        }
        //If dpad-up isn't Pressed set Position
        else {
            robot.kicker.setPosition(0);
            robot.safety.setPosition(0);
        }

        //Speed restraints
        if (shooterSpeed>1) {shooterSpeed=1;}
        if (shooterSpeed<0) {shooterSpeed=0;}

//      Controls arm direction
        //Lifts arm when Right Bumper is pressed
        if (gamepad1.left_stick_button) robot.arm.setPower(.5);
        //Lowers are when Right Bumper is pressed
        else if (gamepad1.right_stick_button) robot.arm.setPower(-.4);
        //When Neither Left or Right Bumpers are pressed
        else robot.arm.setPower(0);

        //Shooter speed
        robot.shooter.setPower(-shooterSpeed);

        telemetry.addData("Shooter Speed", Math.round(shooterSpeed*10));
        telemetry.update();
    }
}

