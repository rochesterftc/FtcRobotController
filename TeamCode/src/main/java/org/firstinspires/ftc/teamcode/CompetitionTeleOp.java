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

public class CompetitionTeleOp extends OpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();

    boolean clawButtonPushed;
    boolean clawOn;
    boolean IsButtonPushed;
    boolean IsOn;
    boolean IMButtonPushed;
    boolean IMOn;
    boolean LSButtonPushed;
    boolean LSOn;
    boolean IMnegativeButtonPushed;
    boolean IMnegativeOn;
    boolean SSButtonPushed;
    boolean SSOn;
    boolean SSnegativeButtonPushed;
    boolean SSnegativeOn;
    double shooterSpeed;
    double intakeSpeed;
    double conveyorSpeed;
    double liftServoSpeed;

    // Declare OpMode members.
   // private boolean helloThereFound;      // Sound file present flag

    public void init() {
        robot.init(hardwareMap);
        // Test Branch
        // Determine Resource IDs for sounds built into the RC application.


    }

    public void loop() {
        if (gamepad2.dpad_up){
            liftServoSpeed=1;
            shooterSpeed=.7;
            intakeSpeed=1;
            robot.intakeservo.setPosition(.5);
        }
        if (gamepad2.dpad_down) {
            liftServoSpeed=-1;
            shooterSpeed=.5;
            intakeSpeed=-1;
            robot.intakeservo.setPosition(.25);
        }
        //1:1   28   counts per rotation
        //3:1   84   counts per rotation
        //5:1   140  counts per rotation
        //100:1 2800 counts per rotation
        float z = gamepad1.left_stick_x;
        float x = gamepad1.right_stick_x;
        float y = -gamepad1.left_stick_y;
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
        if (gamepad2.b && !IMButtonPushed) {
            intakeSpeed=intakeSpeed+.1;
            IMOn = !IMOn;
            IMButtonPushed = true;
        } else if (!gamepad2.b && IMButtonPushed) IMButtonPushed = false;

        if (gamepad2.a && !IMnegativeButtonPushed) {
            intakeSpeed=intakeSpeed-.1;
            IMnegativeOn = !IMnegativeOn;
            IMnegativeButtonPushed = true;
        } else if (!gamepad2.a && IMnegativeButtonPushed) IMnegativeButtonPushed = false;

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

        if (gamepad2.x && LSButtonPushed) {
            liftServoSpeed=(LSOn ? -1 : 1);
            LSOn = !LSOn;
            LSButtonPushed = true;
        } else if (!gamepad2.x && LSButtonPushed) LSButtonPushed = false;

        if (shooterSpeed>1) {shooterSpeed=1;}
        if (shooterSpeed<0) {shooterSpeed=0;}
        //Conveyor controls
        conveyorSpeed=-gamepad2.left_trigger;
        conveyorSpeed=gamepad2.right_trigger;
        conveyorSpeed=0;

        robot.shooter.setPower(shooterSpeed);
        robot.intakemotor.setPower(intakeSpeed);
        robot.conveyor.setPower(conveyorSpeed);
        robot.liftServo.setPower(liftServoSpeed);

        telemetry.addData("Shooter Speed", shooterSpeed);
        telemetry.addData("Intake Speed", intakeSpeed);
        telemetry.update();
    }
}

