package org.firstinspires.ftc.teamcode;

import android.icu.text.UFormat;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;

import java.util.Random;

/**
 * Created by George on 10/11/2020.
 */

@TeleOp(name="Manual **PID Test",group="Testing")
//@Disabled

public class PidTest extends OpMode {

    HardwareHolonomicChassis robot   = new HardwareHolonomicChassis();
    PIDController pid = new PIDController(0.02,0.02,0);
    Random rand = new Random();

    double setpoint = 0;
    double actual = 0;
    double integralError = rand.nextDouble();


    public void init() {

        pid.setInputRange(-100,100);
        pid.setOutputRange(0,100);
        pid.setTolerance(2);
        pid.enable();

    }

    public void loop() {

        setpoint = setpoint - gamepad1.left_stick_y;
        actual = actual + integralError;
        pid.setSetpoint(setpoint);
        double pidReturn = pid.performPID(actual);
        actual = actual + pidReturn;
        telemetry.addData("PID Paramaters:","{Kp, Ki, Kd}",pid.getP(),pid.getI(),pid.getD());
        telemetry.addData("Points:", "{Setpoint, Actual} =  %.5f, %.5f", setpoint,actual);
        telemetry.addData("PID Return:",pidReturn);



        }
    }

