package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
/**
 * Created by Rochesterftc10303 on 10/4/2018.
 */
@TeleOp(name="Competition",group="Master")

public class CompetitionTeleOp extends OpMode {

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    DcMotor lift;
    DcMotor launch;
    DcMotor intakemotor;
    CRServo intakeservo;
    DcMotor arm;
    Servo claw;
    boolean clawButtonPushed;
    boolean clawOn;
    boolean armButtonPushed;

    // Declare OpMode members.
   // private boolean helloThereFound;      // Sound file present flag

    public void init() {
        // Test Branch
        // Determine Resource IDs for sounds built into the RC application.

        fl = hardwareMap.dcMotor.get("front left");
        fr = hardwareMap.dcMotor.get("front right");
        bl = hardwareMap.dcMotor.get("back left");
        br = hardwareMap.dcMotor.get("back right");
        lift = hardwareMap.dcMotor.get("lift");
        launch = hardwareMap.dcMotor.get("launch");
        intakemotor = hardwareMap.dcMotor.get("IM");
        intakeservo = hardwareMap.crservo.get("IS");
        arm = hardwareMap.dcMotor.get("arm");
        claw = hardwareMap.servo.get("claw");

    }

    public void loop() {
        //3:1 84 counts per rotation
        //5:1 140 counts per rotation

        if (gamepad2.b); {
            arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm.setTargetPosition(-21);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (arm.isBusy()) {}
        }
        if (gamepad2.a) ;{
            arm.setTargetPosition(0);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (arm.isBusy()) {}
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        if (gamepad2.dpad_up); {
            intakemotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            intakemotor.setTargetPosition(-6);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (intakemotor.isBusy()){}
        }
        if (gamepad2.dpad_down); {
            intakemotor.setTargetPosition(0);
            intakemotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (intakemotor.isBusy()){}
            intakemotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        

        float x = gamepad1.left_stick_x;
        float z = gamepad1.right_stick_x;
        float y = gamepad1.left_stick_y;


        fl.setPower(y - x - z);
        fr.setPower(-y - x - z);
        bl.setPower(y + x - z);
        br.setPower(-y + x - z);
        lift.setPower(gamepad2.left_trigger);
        launch.setPower(gamepad2.right_trigger);

        if (gamepad2.y && !armButtonPushed) {
            claw.setPosition((clawOn ? 0.7 : 1));
            claw.setPosition((clawOn ? 0.3 : 0));
            clawOn = !clawOn;
            clawButtonPushed = true;
        } else if (!gamepad2.y && clawButtonPushed) clawButtonPushed = false;

    }
}

