package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by George Stevens on 10/4/20.
 */


@TeleOp(name ="PID Test",group="Testing")
@Disabled

public class PrototypePIDShooterTest extends OpMode {

    //creating objects for components
    DcMotor shooter;

    PIDController pid;

    //125:45 gears for 2.777 gear ratio
    //REV HD Hex Motor has a base encoder count of 28 per rotation
    //28 / 2.77 = 10.083 encoder counts per revolution
    int driveGear = 125;
    int drivenGear = 90;
    float cpr = driveGear/drivenGear;

    public void init() {

    shooter = hardwareMap.dcMotor.get("shooter");

        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void loop() {

        shooter.setPower(1);
        getRPS();

    }

    public void getRPS() {
        float rps;
        int iPos;
        int fPos;
        int counts;
        double iTime;
        double fTime;
        double mTime;

        //get initial encoder position
        iPos = shooter.getCurrentPosition();
        //get current time
        iTime = getRuntime();
        //get final encoder position
        fPos = shooter.getCurrentPosition();
        //get final time
        fTime = getRuntime();
        //subtract current encoder position from original encoder position to get the difference
        counts = fPos-iPos;
        mTime = fTime-iTime;
        //divide encoder position by cpr for # of rotations in 1 millisecond
        rps = (counts/cpr)/Math.round(mTime);

        telemetry.addData("RPMS: ", rps);
    }
}