package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;


/**
 * Created by George on 11/9/2020.
 */
@Autonomous(name = "Compeition Autonomous", group = "Competition")

public class CompetitionAutonomous extends LinearOpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        //Computervision code

/*        if (var = 1) {

        }
*/
    }
}