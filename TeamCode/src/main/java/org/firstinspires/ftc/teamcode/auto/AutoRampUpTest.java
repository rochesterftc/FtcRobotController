package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;


/**
 * Created by George on 1/31/2021.
 */
//@Disabled
@Autonomous(name = "AutonomousBase", group = "Testing")

public class AutoRampUpTest extends LinearOpMode {

HardwareHolonomicChassis robot = new HardwareHolonomicChassis();
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        driveRamp(0,1,0,1,0.333);
    }
    /**
     * Sets the drive motors to move the robot at the appropriate speed for each axis according to the holonimc algorithm
     * @param z Speed along the z axis (e.g. strafing)
     * @param y Speed along the y axis (e.g. forward/back)
     * @param x Speed along the z axis (e.g. rotation)
     */
    public void setMotorPower (double z, double y, double x) {
        //x = turning
        //y = forward
        //z = strafing
        robot.fl.setPower(y + x + z);
        robot.fr.setPower(-y + x + z);
        robot.bl.setPower(y + x - z);
        robot.br.setPower(-y + x - z);
        telemetry.addData("Motor Power", "{X,Y,rX} = %.2f, %.2f, %.2f", x, y, z);
    }

    /**
     * Ramp up drive speed over a specified time
     * @param z Max speed along the z axis (e.g. strafing)
     * @param y Max speed along the y axis (e.g. forward/back)
     * @param x Max speed along the x axis (e.g. rotation)
     * @param rampTime Time in seconds to get to full power
     */
    public void rampSpeed(double z, double y, double x, double rampTime){
        // Ramp motor speeds till stop pressed.
        ElapsedTime rampTimer = new ElapsedTime();
        double modifier = 0.1;
        while(opModeIsActive()) {
            if (rampTimer.seconds() >= rampTime * modifier) modifier = modifier + 0.1;
            setMotorPower(z * modifier, y * modifier, x * modifier);
            // Display the current value
            telemetry.addData("Motor Power", "{x,y,z}%5.2f", z * modifier, y * modifier, x * modifier);
            telemetry.addData("Timer", rampTimer.seconds());
            telemetry.addData(">", "Press Stop to end test.");
            telemetry.update();
            if(rampTimer.seconds()>= rampTime)break;
        }
        setMotorPower(z,y,x);
    }
    /**
     * Drive via time ramping speed up and down
     * @param z Max speed along the z axis (e.g. strafing)
     * @param y Max speed along the y axis (e.g. forward/back)
     * @param x Max speed along the x axis (e.g. rotation)
     * @param driveTime total drive time including ramp up and down
     * @param rampTime Time in seconds to get to full power
     */
    public void driveRamp(double z, double y, double x, double driveTime, double rampTime){
        rampSpeed(z,y,x,rampTime);
        sleep((long) (driveTime-(rampTime*2)*1000));
        rampSpeed(0,0,0,rampTime);

    }

}