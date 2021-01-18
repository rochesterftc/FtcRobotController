package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;

/**
 * Created by Rochesterftc10303 on 10/4/2018.
 */
@TeleOp(name="Competition Tele-Op",group="Master")

public class JanuaryCompTeleOp extends OpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();

    //Intake variables
    boolean intakeDown;                 boolean intakeButtonPushed;

    //Conveyor variables

    //Kicker and Safety variables
    boolean safetyEngaged;                   boolean safetyButtonPushed;
    
    //Shooter variables
    double shooterSpeed;

    //Arm variables
    boolean clawClosed;                     boolean clawButtonPushed;

    public void init() {
        robot.init(hardwareMap);
    }

    public void loop() {

        //Drive
        /*1:1   28   counts per rotation
        3:1   84   counts per rotation
        5:1   140  counts per rotation
        100:1 2800 counts per rotation*/
        float z = gamepad1.left_stick_x;
        float x = gamepad1.right_stick_x;
        float y = -gamepad1.left_stick_y;
        /*x = turning
        y = forward
        z = strafing*/
        robot.fl.setPower(y + x + z);
        robot.fr.setPower(-y + x + z);
        robot.bl.setPower(y + x - z);
        robot.br.setPower(-y + x - z);

        //---------Intake---------//
        /*If x on gamepad 1 is pressed and isn't being held down, set the intake dropper servo to
        whichever position it is not currently in (up/down) */
        if (gamepad1.x && !intakeButtonPushed) {
            //if intakeDown is true, set the position to 0 (up). If it's false, set it to .75 (down).
            robot.intakeservo.setPosition((intakeDown ? 0:.75));
            //set intakeDown to the opposite value
            intakeDown = !intakeDown;
            //Because x is currently being pressed, set it to being true so it doesn't constantly change servo positions
            intakeButtonPushed = true;
        }
        /*if x is not being pressed and intakeButtonPushed is true, set intakeButtonPushed to false
        because it is no longer being pressed*/
        else if (!gamepad1.x && intakeButtonPushed) {
            intakeButtonPushed = false;
        }

        //if x on gamepad 2 is pressed, set the intake servos to max power pulling rings in
        if (gamepad2.x) {
            robot.lIntake.setPower(1);
            robot.rIntake.setPower(-1);
        }
        //if y on gamepad 2 is pressed, set the intake servos to max power pushing rings out
        if (gamepad2.y) {
            robot.lIntake.setPower(-1);
            robot.rIntake.setPower(1);
        }

        //---------Conveyor---------//
        //Right trigger moves conveyor forward, left trigger moves it backwards
        robot.lConveyor.setPower(gamepad2.right_trigger-gamepad2.left_trigger);
        robot.rConveyor.setPower(-gamepad2.right_trigger+gamepad2.left_trigger);

        //---------Kicker---------//
        //While b on gamepad 2 is being held, set the servo position to 1, which kicks a ring into the shooter
        if (gamepad2.b) {
            robot.kicker.setPosition(1);
        }
        else {
            robot.kicker.setPosition(0);
        }

        //---------Safety---------//
        /*If a on gamepad 2 is pressed and isn't being held down, set the safety servo to
        whichever position it is not currently in (engaged/disengaged) */
        if (gamepad1.a && !safetyButtonPushed) {
            //if safetyEngaged is true, set the position to 0 (engaged). If it's false, set it to .75 (disengaged).
            robot.intakeservo.setPosition((safetyEngaged ? 0:.75));
            //set safetyEngaged to the opposite value
            safetyEngaged = !safetyEngaged;
            //Because a is currently being pressed, set it to being true so it doesn't constantly change servo positions
            safetyButtonPushed = true;
        }
        /*if a is not being pressed and safetyButtonPushed is true, set safetyButtonPushed to false
        because it is no longer being pressed*/
        else if (!gamepad1.a && safetyButtonPushed) {
            safetyButtonPushed = false;
        }
        
        //---------Shooter---------//
        /*When the left bumper is pressed, raise the shooter speed by .1 as long as it is currently 
        less than or equal to 1 */
        robot.shooter.setPower(shooterSpeed);
        if (gamepad2.left_bumper && shooterSpeed <= 1) {
            shooterSpeed = shooterSpeed + .1;
        }
        /*When the right bumper is pressed, lower the shooter speed by .1 as long as it is currently 
        greater than or equal to 0 */
        if (gamepad2.right_bumper && shooterSpeed >= 0) {
            shooterSpeed = shooterSpeed - .1;
        }
        //if shooterSpeed is less than 0 (minimum), set it to 0
        if (shooterSpeed < 0) {
            shooterSpeed = 0;
        }
        //if shooterSpeed is greater than 1 (maximum), set it to 1
        if (shooterSpeed > 1) {
            shooterSpeed = 1;
        }

        //---------Arm---------//
        robot.arm.setPower(gamepad1.right_trigger-gamepad1.left_trigger);

        /*If the right bumper on gamepad 1 is pressed and isn't being held down, set the claw servo to
        whichever position it is not currently in (open/closed) */
        if (gamepad1.right_bumper && !clawButtonPushed) {
            //if clawClosed is true, set the position to 1 (open). If it's false, set it to .2 (closed).
            robot.claw.setPosition((clawClosed ? 1:.2));
            //set clawClosed to the opposite value
            clawClosed = !clawClosed;
            //Because right bumper is currently being pressed, set it to being true so it doesn't constantly change servo positions
            clawButtonPushed = true;
        }
        /*if right bumper is not being pressed and clawButtonPushed is true, set clawButtonPushed to false
        because it is no longer being pressed*/
        else if (!gamepad1.right_bumper && clawButtonPushed) {
            clawButtonPushed = false;
        }

        telemetry.addData("Shooter Speed", shooterSpeed);
        telemetry.update();
    }
}

