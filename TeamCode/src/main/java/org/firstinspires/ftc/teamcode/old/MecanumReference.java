 package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class MecanumReference {

    DcMotor fr;
    DcMotor fl;
    DcMotor br;
    DcMotor bl;
    DcMotor arm;

    CRServo wrist;

    Servo clamp;
    Servo lock;
    Servo foundationLeft;
    Servo foundationRight;
    Servo stoneArm;

    HardwareMap hardwareMap;


    //1120 counts per rotation

    //13 inches per rotation
    //86 countsPerInch counts per X inch
    float XcountsPerInch = 86f;

    //8 inches per rotation
    //140 counts per Y inch
    float YcountsPerInch = 140f;

    //60 degrees per rotation
    //18.6 countsPerDegree counts per degree
    float countsPerDegree = 18.666f;

    public void init() {

        fr = hardwareMap.dcMotor.get("front right");
        fr = hardwareMap.dcMotor.get("front right");
        bl = hardwareMap.dcMotor.get("back left");
        br = hardwareMap.dcMotor.get("back right");
        arm = hardwareMap.dcMotor.get("arm");

        wrist = hardwareMap.crservo.get("wrist");
        clamp = hardwareMap.servo.get("leftClamp");
        foundationLeft = hardwareMap.servo.get("foundation left");
        foundationRight = hardwareMap.servo.get("foundation right");
        stoneArm = hardwareMap.servo.get("stone arm");
        lock = hardwareMap.servo.get("lock");


    }

}