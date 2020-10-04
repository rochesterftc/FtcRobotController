package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@Disabled
@Autonomous(name="Tensor Flow Object Detection",group="Competition")

public class tfodTest extends LinearOpMode {

    HardwareHolonomicChassis robot   = new HardwareHolonomicChassis();
    private ElapsedTime runtime = new ElapsedTime();

    public HardwareHolonomicChassis getRobot() {
        return robot;
    }

    public void runOpMode(){

        robot.init(hardwareMap);

        waitForStart();



    }


    public void moveTime (double x,double y,double z,int time) {

        robot.fl.setPower(-x-y+z);
        robot.fr.setPower(-x+y+z);
        robot.bl.setPower(x-y+z);
        robot.br.setPower(x+y+z);

        sleep(time);

        robot.fl.setPower(0);
        robot.fr.setPower(0);
        robot.bl.setPower(0);
        robot.br.setPower(0);

    }

}
