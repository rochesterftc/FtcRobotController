package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;

/**
 * Created by George on 10/11/2020.
 */

@TeleOp(name="Test  Tele-Op",group="Testing")
//@Disabled

public class TeleOpTest extends OpMode {

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    public DcMotor Conveyor = null;
    public DcMotor shooter = null;
    public DcMotor intakemotor = null;
    public DcMotor arm = null;
    public DcMotor conveyer;
    public CRServo intakeservo = null;
    public Servo claw = null;

    boolean clawButtonPushed;
    boolean clawOn;
    boolean IsButtonPushed;
    boolean IsOn;


    public void init() {

        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");
        intakemotor = hardwareMap.get(DcMotor.class, "IM");
        intakeservo = hardwareMap.get(CRServo.class, "IS");
        arm = hardwareMap.get(DcMotor.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");



    }

    public void loop() {

        float x = gamepad1.left_stick_x;
        float z = -gamepad1.right_stick_x;
        float y = -gamepad1.left_stick_y;

        fl.setPower(y - x - z);
        fr.setPower(-y - x - z);
        bl.setPower(y + x - z);
        br.setPower(-y + x - z);

        if (gamepad2.y && !clawButtonPushed) {
            claw.setPosition((clawOn ? 0.7 : 0.3));
            clawOn = !clawOn;
            clawButtonPushed = true;
        } else if (!gamepad2.y && clawButtonPushed) clawButtonPushed = false;

        if (gamepad2.x && !IsButtonPushed) {
            intakeservo.setPower((IsOn ? 1:0));
            IsOn = !IsOn;
            IsButtonPushed = true;
        } else if (!gamepad2.x && IsButtonPushed) IsButtonPushed = false;

        if (gamepad2.left_bumper) {
            shooter.setPower(.9);
        } else if (gamepad2.right_bumper) {
            shooter.setPower(.1);
        } else {
            shooter.setPower(0);
        }


    }
    }

