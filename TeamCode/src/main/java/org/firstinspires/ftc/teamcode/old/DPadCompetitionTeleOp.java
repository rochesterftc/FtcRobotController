package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Rochesterftc10303 on 10/4/2018.
 */

@TeleOp(name="DPadCompetition",group="Master")
@Disabled
public class
DPadCompetitionTeleOp extends OpMode {

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    DcMotor arm;
    CRServo wrist;
    Servo leftClamp;
    Servo rightClamp;
    Servo lock;
    Servo foundationLeft;
    Servo foundationRight;
    Servo stoneArm;
    int slowModeModifier = 3;
    int driveMode = 1;
    double wristSpeed = 0.5;
    double wristPower;
    boolean clampButtonPushed;
    boolean clampOn;
    boolean slowButtonPushed;
    boolean slowMode;
    boolean foundationButtonPushed;
    boolean foundationOn;
    boolean stoneArmButtonPushed;
    boolean stoneArmOn;
    // Declare OpMode members.
    private boolean helloThereFound;      // Sound file present flag

    public void init() {
        // Test Branch
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
        arm = hardwareMap.dcMotor.get("arm");
        wrist = hardwareMap.crservo.get("wrist");
        leftClamp = hardwareMap.servo.get("left clamp");
        rightClamp = hardwareMap.servo.get("right clamp");
        lock = hardwareMap.servo.get("lock");
        foundationLeft = hardwareMap.servo.get("foundation left");
        foundationRight = hardwareMap.servo.get("foundation right");
        stoneArm = hardwareMap.servo.get("stone arm");
        clampButtonPushed = false;
        clampOn = false;
        slowButtonPushed = false;
        slowMode = false;

        /*arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Don't remember if next line is needed.  I believe setTargetPosition is inited to zero so probably not needed
        arm.setTargetPosition(0);
        arm.setPower(0.7); //set to the max speed you want the arm to move at*/

        // Display sound status
        telemetry.addData("hellothere resource",   helloThereFound ?   "Found" : "NOT found\n Add hellothere.wav to /src/main/res/raw" );
        telemetry.addData("Status", "Init complete! Press Start to Continue");
        telemetry.update();

        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, helloThereID);

    }

    public void loop() {

        //floats to streamline drive code
        float x = gamepad1.left_stick_x;
        float z = gamepad1.right_stick_x;
        float y = gamepad1.left_stick_y;

        /*sets drive mode for adjusting driver orentation
        1: Forward
        2: Backward
        3: Left
        4: Right
         */
        if(gamepad1.dpad_up) {driveMode = 1; telemetry.addData("Drive Mode","Forward");}
        if(gamepad1.dpad_down) {driveMode = 2;telemetry.addData("Drive Mode","Backward");}
        if(gamepad1.dpad_left) {driveMode = 3;telemetry.addData("Drive Mode","Left");}
        if(gamepad1.dpad_right) {driveMode = 4;telemetry.addData("Drive Mode","Right");}

        //sets xyz floats to aproprate analog stick inputs based on drive mode
        if(driveMode == 1) {
            x = gamepad1.left_stick_x;
            y = gamepad1.left_stick_y;
            z = gamepad1.right_stick_x;
        } else if (driveMode == 2){
            x = -gamepad1.left_stick_x;
            y = -gamepad1.left_stick_y;
            z = gamepad1.right_stick_x;
        } else if (driveMode == 3){
            x = -gamepad1.left_stick_y;
            y = gamepad1.left_stick_x;
            z = gamepad1.right_stick_x;
        } else if (driveMode == 4){
            x = gamepad1.left_stick_y;
            y = -gamepad1.left_stick_x;
            z = gamepad1.right_stick_x;
        }

        /*
        Holonomic Drive:
        Gamepad 1 left and right sticks control the robot main movement, holding down a moves 5x slower
         */

        if(gamepad1.a && !slowButtonPushed) {
            slowMode = !slowMode;
            slowButtonPushed = true;
        } else if(!gamepad1.a && slowButtonPushed) slowButtonPushed = false;

        if (slowMode) {
            fl.setPower((y - x - z) / slowModeModifier);
            fr.setPower((-y - x - z) / slowModeModifier);
            bl.setPower((y + x - z) / slowModeModifier);
            br.setPower((-y + x - z) / slowModeModifier);
            telemetry.addData("Slow Mode","On with factor of "+slowModeModifier);
        } else {
            fl.setPower(y - x - z);
            fr.setPower(-y - x - z);
            bl.setPower(y + x - z);
            br.setPower(-y + x - z);
            telemetry.addData("Slow Mode","Off");

        }
        float armPower = -gamepad2.left_stick_y;


        if (armPower < 0) armPower = armPower/2;
        arm.setPower(armPower);
      wristPower = gamepad2.right_trigger - gamepad2.left_trigger;
        wrist.setPower(wristPower*wristSpeed);

        /*float targetPosition = gamepad1.right_stick_y;
        int intTargetPosition = (int) targetPosition * 200;

        arm.setTargetPosition(arm.getCurrentPosition()+(intTargetPosition));
        if (gamepad1.right_stick_y > 0) {
            arm.setPower(.7);
        } else if (gamepad1.right_stick_y < 0) {
            arm.setPower(-0.7);
        } */

        //Clamp control
               if(gamepad2.a && !clampButtonPushed) {
            leftClamp.setPosition((clampOn ? 0.7 : 1));
            rightClamp.setPosition((clampOn ? 0.3 : 0));
           clampOn = !clampOn;
           clampButtonPushed = true;
        } else if(!gamepad2.a && clampButtonPushed) clampButtonPushed = false;

        //foundation control
        if(gamepad2.x && !foundationButtonPushed) {
            foundationLeft.setPosition((foundationOn ? 0.9 : 0.4));
            foundationRight.setPosition((foundationOn ? 0 : 0.4));
            foundationOn = !foundationOn;
            foundationButtonPushed = true;
        } else if(!gamepad2.x && foundationButtonPushed) foundationButtonPushed = false;
        telemetry.addData("Foundation Left",foundationLeft.getPosition());
        telemetry.addData("Foundation Right",foundationRight.getPosition());

        //Stone Arm control
        if(gamepad2.y && !stoneArmButtonPushed) {
            stoneArm.setPosition((stoneArmOn ? 0 : 1));
            stoneArmOn = !stoneArmOn;
            stoneArmButtonPushed = true;
        } else if(!gamepad2.y && stoneArmButtonPushed) stoneArmButtonPushed = false;

        telemetry.update();
    }
}