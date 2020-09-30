package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Rochestefrtc10303 on 10/4/2018.
 */

@TeleOp(name="Sponsor Demo",group="Master")
@Disabled
public class
Puckup extends OpMode {

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    DcMotor lf;
    DcMotor rf;
    Servo clamp;

    // Declare OpMode members.
    private boolean helloThereFound;      // Sound file present flag

    public void init() {

        // Determine Resource IDs for sounds built into the RC application.
        int helloThereID = hardwareMap.appContext.getResources().getIdentifier("hellothere", "raw", hardwareMap.appContext.getPackageName());

        // Determine if sound resources are found.
        // Note: Preloading is NOT required, but it's a good way to verify all your sounds are available before you run.
        if (helloThereID != 0) {
            helloThereFound = SoundPlayer.getInstance().preload(hardwareMap.appContext, helloThereID);
        }

        fl = hardwareMap.dcMotor.get("front left");
        fr = hardwareMap.dcMotor.get("front right");
        bl = hardwareMap.dcMotor.get("back left");
        br = hardwareMap.dcMotor.get("back right");
        clamp = hardwareMap.servo.get("leftClamp");

        //LeftRL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //RightRL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //RightRL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //LeftRL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Display sound status
        telemetry.addData("hellothere resource",   helloThereFound ?   "Found" : "NOT found\n Add hellothere.wav to /src/main/res/raw" );
        telemetry.addData("Status", "Init complete! Press Start to Continue");
        telemetry.update();

        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, helloThereID);

    }

    public void loop() {

        //floats to streamline drive code
        float x = gamepad1.left_stick_x;
        float z = -gamepad1.left_stick_y;
        float y = gamepad1.right_stick_x;

        /*
        Holonomic Drive:
        Gamepad 1 left and right sticks control the robot main movement, holding down a moves 5x slower
         */
        if (gamepad1.a) {
            fl.setPower((-y - x + z) / 5);
            bl.setPower((-y + x + z) / 5);
            fr.setPower((-y - x - z) / 5);
            br.setPower((-y + x - z) / 5);
        } else {
            fl.setPower(-y - x + z);
            bl.setPower(-y + x + z);
            fr.setPower(-y - x - z);
            br.setPower(-y + x - z);
        }

        if (gamepad1.left_bumper) {
            clamp.setPosition(1);
        } else if (gamepad1.right_bumper){
            clamp.setPosition(-1);
        } else {
            clamp.setPosition(0);
        }

        //if(gamepad1.x) {
        //    SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, helloThereID);
        //}
        //if(gamepad2.x) {
        //    SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, helloThereID);
        //}

        telemetry.update();
    }
}