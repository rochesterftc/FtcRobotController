package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by ncw22 on 1/16/20.
 */
@Disabled
@TeleOp(name ="Grabber Test",group="Competition")

public class ClawTest extends OpMode {

   Servo leftClaw;
    Servo rightClaw;
   boolean buttonPushed = false;
   boolean on = false;

    // DcMotor grablift;

    public void init() {

      leftClaw = hardwareMap.servo.get("left claw");
      rightClaw = hardwareMap.servo.get("right claw");

    }

    public void loop() {

        if(gamepad1.a && !buttonPushed) {
            leftClaw.setPosition((on ? 0.4 : 1));
            rightClaw.setPosition((on ? 0.6 : 0));
            on = !on;
            buttonPushed = true;
        } else if(!gamepad1.a && buttonPushed) buttonPushed = false;

    }
}

