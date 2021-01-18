package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;

/**
 * Created by Rochesterftc10303 on 10/4/2018.
 */
@Disabled
@TeleOp(name="N Tele-Op Test",group="Master")

public class NTeleOpTest extends OpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();

    boolean clawButtonPushed;
    boolean clawOn;
    boolean IsButtonPushed;
    boolean IsOn;
    boolean LSButtonPushed;
    boolean LSOn;

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
/*
        if (gamepad2.b) robot.intakemotor.setPower(1);
        else if (gamepad2.a) robot.intakemotor.setPower(-1);
        else robot.intakemotor.setPower(0);
        if (gamepad1.b) robot.arm.setPower(.3);
        else if (gamepad1.a) robot.arm.setPower(-.3);
        else robot.arm.setPower(0);
*/
        float z = gamepad1.left_stick_x;
        float x = gamepad1.right_stick_x;
        float y = -gamepad1.left_stick_y;


        robot.fl.setPower(y + x + z);
        robot.fr.setPower(-y + x - z);
        robot.bl.setPower(y + x - z);
        robot.br.setPower(-y + x + z);

        if (gamepad1.y && !clawButtonPushed) {
            robot.claw.setPosition((clawOn ? 0.7 : 0.3));
            clawOn = !clawOn;
            clawButtonPushed = true;
        } else if (!gamepad1.y && clawButtonPushed) clawButtonPushed = false;

        if (gamepad2.right_bumper && !LSButtonPushed) {
            robot.shooter.setPower((LSOn ? 1:0));
            LSOn = !LSOn;
            LSButtonPushed = true;
        } else if (!gamepad2.right_bumper && LSButtonPushed) LSButtonPushed = false;

        robot.shooter.setPower(gamepad2.right_trigger);

       // robot.conveyor.setPower(-gamepad2.left_trigger/2);
        //robot.conveyor.setPower(gamepad2.right_trigger/2);

    }
}

