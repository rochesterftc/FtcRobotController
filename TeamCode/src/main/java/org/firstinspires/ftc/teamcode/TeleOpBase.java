package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;

/**
 * Created by George on 10/11/2020.
 */

@TeleOp(name="Tele-Op Base",group="Testing")
@Disabled

public class TeleOpBase extends OpMode {

    HardwareHolonomicChassis robot   = new HardwareHolonomicChassis();


    public void init() {
    robot.init(hardwareMap);
    }

    public void loop() {



        }
    }

