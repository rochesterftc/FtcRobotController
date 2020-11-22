package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;

import java.util.List;

/**
 * Created by George on 11/9/2020.
 */
@Autonomous(name = "Time Autonomous TFOD", group = "Competition")

public class TimeCompetitionAutonomousTFOD extends LinearOpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();

    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private static final String VUFORIA_KEY =
            "ASLZpXP/////AAABmcf6IAKpuUgbqERI9bu4hEZEPCSq/2sZe0zrgWI1rySsI2SfEEm2e6c+A5svGl6C6mv6fczUZsEDhyWIkVyvG1baGFjFP8YHOcX1Tme9oOUVBcrWbmAacREJcyQ0wQ7D9RlgohT8JVucF1NvWGyk8lqqUDY0QID9MbBw/YENyN84MKNK+c4E/sbsTui/bdYkcn11xwgx0G5fnP6wjpVhIeuHAosrWz/7Rq8vHH1swQ6E19knAfhOWjEn+GjDSCdSaqsSiyUpgRj105WDf8sVDKpvII5IqMa7QFEOBOd7bAirRaiUUCBHj0EOK0efgRO/Zq+wt/ZbF0R66fVj2HK6UuYZQ/vRK6Wsyv+DoNCGc0Rj";

    //38.5in forward in 1 second @ 12.2v
    long YMSPerInch = 260;
    long XMSPerInch;
    //140* left in 1 second @ 12.17v
    long secondsPerDegree = 71;
    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        robot.fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If ywour target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 1.78 or 16/9).

            // Uncomment the following line if you want to adjust the magnification and/or the aspect ratio of the input images.
            //tfod.setZoom(2.5, 1.78);

            /** Wait for the game to begin */
            telemetry.addData(">", "Press Play to start op mode");
            telemetry.update();

            waitForStart();

            if (tfod != null) {

                ElapsedTime scanTime = new ElapsedTime();
                scanTime.reset();

                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                int var = 1; //1 = zero rings, 2 = one ring, 3 = four rings
                while (scanTime.seconds() < 2 && opModeIsActive()) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
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
                            if (recognition.getLabel() == "Single") {
                                var = 2;
                            } else if (recognition.getLabel() == "Quad") {
                                var = 3;
                            }
                            telemetry.update();
                        }
                        telemetry.update();
                    }
                }

                //move to the white shooter line, then the corner of the white line and 1st blue box
                if (var == 1 || var == 3) {
                    do {
                        robot.fr.setPower(-.5);
                        robot.fl.setPower(.5);
                        robot.br.setPower(-.5);
                        robot.bl.setPower(.5);
                    }
                    while (robot.sensorColor.red() < 200 || robot.sensorColor.green() < 200 || robot.sensorColor.blue() < 200);

                    robot.fr.setPower(0);
                    robot.fl.setPower(0);
                    robot.br.setPower(0);
                    robot.bl.setPower(0);

                    sleep(250);

                    do {
                        robot.fr.setPower(-.5);
                        robot.fl.setPower(-.5);
                        robot.br.setPower(.5);
                        robot.bl.setPower(.5);
                    }
                    while (robot.sensorColor.blue() < robot.sensorColor.red() || robot.sensorColor.blue() < robot.sensorColor.green());

                    robot.fr.setPower(0);
                    robot.fl.setPower(0);
                    robot.br.setPower(0);
                    robot.bl.setPower(0);
                }
                if (var == 2) {
                    do {
                        robot.fr.setPower(-.5);
                        robot.fl.setPower(.5);
                        robot.br.setPower(-.5);
                        robot.bl.setPower(.5);
                    }
                    while (robot.sensorColor.red() < 200 || robot.sensorColor.green() < 200 || robot.sensorColor.blue() < 200);

                    robot.fr.setPower(0);
                    robot.fl.setPower(0);
                    robot.br.setPower(0);
                    robot.bl.setPower(0);

                    sleep(250);

                    do {
                        robot.fr.setPower(.5);
                        robot.fl.setPower(.5);
                        robot.br.setPower(-.5);
                        robot.bl.setPower(-.5);
                    }
                    while (robot.sensorColor.blue() < robot.sensorColor.red() || robot.sensorColor.blue() < robot.sensorColor.green());

                    robot.fr.setPower(0);
                    robot.fl.setPower(0);
                    robot.br.setPower(0);
                    robot.bl.setPower(0);

                    timeDriveXY(4, .5, "left");
                }

                //move behind white line
                timeDriveXY(6, .75, "backward");

                //move to 1st power shot position
                timeDriveXY(45, 1, "right");

                //shoot and keep motor running
                robot.shooter.setPower(1);
                sleep(2000);
                robot.conveyer.setPower(1);
                sleep(500);
                robot.conveyer.setPower(0);

                //move to 2nd power shot position
                timeDriveXY(7, .75, "right");

                //shoot and keep motor running
                robot.shooter.setPower(1);
                sleep(2000);
                robot.conveyer.setPower(1);
                sleep(500);
                robot.conveyer.setPower(0);

                //move to 3rd power shot position
                timeDriveXY(7, .75, "right");

                //shoot and stop motor
                robot.shooter.setPower(1);
                sleep(2000);
                robot.conveyer.setPower(1);
                sleep(500);
                robot.conveyer.setPower(0);
                robot.shooter.setPower(0);

                //move onto white line
                timeDriveXY(6, .75, "forward");

            }
        }
    }

            /**
             * Initialize the Vuforia localization engine.
             */
            private void initVuforia() {
                /*
                 * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
                 */
                VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

                parameters.vuforiaLicenseKey = VUFORIA_KEY;
                parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

                //  Instantiate the Vuforia engine
                vuforia = ClassFactory.getInstance().createVuforia(parameters);

                // Loading trackables is not necessary for the TensorFlow Object Detection engine.
            }

            /**
             * Initialize the TensorFlow Object Detection engine.
             */
            private void initTfod() {
                int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                        "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
                TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
                tfodParameters.minResultConfidence = 0.8f;
                tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
                tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
            }

    public void timeDriveXY(long inches, double speed, String direction) {

        if (direction == "forward") {
            robot.fr.setPower(-speed);
            robot.br.setPower(-speed);
            robot.fl.setPower(speed);
            robot.bl.setPower(speed);
            sleep(inches*YMSPerInch);
        }
        if (direction == "backward") {
            robot.fr.setPower(speed);
            robot.br.setPower(speed);
            robot.fl.setPower(-speed);
            robot.bl.setPower(-speed);
            sleep(inches*YMSPerInch);
        }
        if (direction == "left") {
            robot.fr.setPower(-speed);
            robot.br.setPower(speed);
            robot.fl.setPower(-speed);
            robot.bl.setPower(speed);
            sleep(inches*XMSPerInch);
        }
        if (direction == "right") {
            robot.fr.setPower(speed);
            robot.br.setPower(-speed);
            robot.fl.setPower(speed);
            robot.bl.setPower(-speed);
            sleep(inches*XMSPerInch);
        }

        robot.fr.setPower(0);
        robot.br.setPower(0);
        robot.fl.setPower(0);
        robot.bl.setPower(0);
    }

    public void timeTurn(int degrees, double speed, String direction) {
        if (direction == "left") {
            robot.fr.setPower(-speed);
            robot.br.setPower(-speed);
            robot.fl.setPower(-speed);
            robot.bl.setPower(-speed);
        }
        if (direction == "right") {
            robot.fr.setPower(speed);
            robot.br.setPower(speed);
            robot.fl.setPower(speed);
            robot.bl.setPower(speed);
        }

        sleep(degrees*secondsPerDegree);

        robot.fr.setPower(0);
        robot.br.setPower(0);
        robot.fl.setPower(0);
        robot.bl.setPower(0);
    }
}