package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.hardwaremap.HardwareCompetitionChassis;

import java.util.List;


/**
 * Created by Nathaniel on 11/4/2020.
 */
//@Disabled
@Autonomous(name = "Main Autonomous", group = "Competition")

public class MainAuto extends LinearOpMode {

    HardwareCompetitionChassis robot = new HardwareCompetitionChassis();



    @Override
    public void runOpMode() {

        robot.initWithVuforia(hardwareMap);

        waitForStart();
        int ringCondition = 0; //1 = zero rings, 2 = one ring, 3 = four rings

        if (robot.tfod != null) {

            ElapsedTime scanTime = new ElapsedTime();
            scanTime.reset();

            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            while (scanTime.seconds() < 5) {
                List<Recognition> updatedRecognitions = robot.tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());

                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        if(recognition.getLabel() == "Single") {
                            ringCondition = 2;
                        } else if (recognition.getLabel() == "Quad") {
                            ringCondition = 3;
                        }
                        telemetry.update();
                    }
                    telemetry.update();
                }
            }

            boolean atLine = false;
            while(!atLine) {
                if (robot.sensorColor.blue()>robot.sensorColor.red() && robot.sensorColor.blue()>robot.sensorColor.green()) {
                    robot.fl.setPower(0.5);
                    robot.fr.setPower(-0.5);
                    robot.bl.setPower(0.5);
                    robot.br.setPower(-0.5);
                    telemetry.addData("STATUS","Scanning for shot line");
                } else atLine = true;
                telemetry.addData("STATUS","Shot line found!");
                telemetry.update();
            }

            sleep(1000);
            if (ringCondition == 0) {
                telemetry.addLine("MOVING TO TARGET A");
                telemetry.update();

                moveTime(-0.5, 0, 0, 2000);
            } else if (ringCondition == 2) {
                telemetry.addLine("MOVING TO TARGET B");
                telemetry.update();

                moveTime(0, 0.5, 0, 2000);
            } else if (ringCondition == 3) {
                telemetry.addLine("MOVING TO TARGET C");
                telemetry.update();

                moveTime(-0.25, 1, 0, 3000);
            }
            telemetry.update();

            robot.tfod.getUpdatedRecognitions();

            robot.goToTarget(ringCondition);

        }else {telemetry.addLine("WARNING! TFOD INIT FAILURE"); telemetry.update();}

    }
    //Move method: define value for each axis and time
    public void moveTime ( double x, double y, double z, int time){

        robot.fl.setPower(-x - y + z);
        robot.fr.setPower(-x + y + z);
        robot.bl.setPower(x - y + z);
       robot.br.setPower(x + y + z);

        sleep(time);

        robot.fl.setPower(0);
        robot.fr.setPower(0);
        robot.bl.setPower(0);
        robot.br.setPower(0);

    }
}