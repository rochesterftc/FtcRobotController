package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;

/**
 * Created by Rochesterftc10303 on 10/4/2018.
 */
@TeleOp(name="Competition Plz Work",group="Master")

public class CompetitionTeleOpPlzWork extends OpMode {

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
        float x = gamepad1.left_stick_x;
        float z = -gamepad1.right_stick_x;
        float y = -gamepad1.left_stick_y;


        robot.fl.setPower(y - x - z);
        robot.fr.setPower(-y - x - z);
        robot.bl.setPower(y + x - z);
        robot.br.setPower(-y + x - z);


        if (gamepad2.y && !clawButtonPushed) {
            robot.claw.setPosition((clawOn ? 0.7 : 0.3));
            clawOn = !clawOn;
            clawButtonPushed = true;
        } else if (!gamepad2.y && clawButtonPushed) clawButtonPushed = false;

        if (gamepad2.x && !IsButtonPushed) {
            robot.intakeservo.setPower((IsOn ? 1:0));
            IsOn = !IsOn;
            IsButtonPushed = true;
        } else if (!gamepad2.x && IsButtonPushed) IsButtonPushed = false;

        robot.conveyor.setPower(gamepad2.left_trigger);
        robot.shooter.setPower(gamepad2.right_trigger);

//        if (gamepad2.left_bumper) {
//            robot.shooter.setPower(.9);
//        } else if (gamepad2.right_bumper) {
//            robot.shooter.setPower(.1);
//        } else {robot.shooter.setPower(0);
//        }
    }
}

