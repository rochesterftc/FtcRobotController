package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.hardwaremap.HardwareCompetitionChassis;
import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;

import java.util.List;

//@Disabled
@Autonomous(name="Tensor Flow Object Detection",group="Competition")

public class tfodTest extends LinearOpMode {

    HardwareCompetitionChassis robot   = new HardwareCompetitionChassis();

    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private static final String VUFORIA_KEY =
            "ASLZpXP/////AAABmcf6IAKpuUgbqERI9bu4hEZEPCSq/2sZe0zrgWI1rySsI2SfEEm2e6c+A5svGl6C6mv6fczUZsEDhyWIkVyvG1baGFjFP8YHOcX1Tme9oOUVBcrWbmAacREJcyQ0wQ7D9RlgohT8JVucF1NvWGyk8lqqUDY0QID9MbBw/YENyN84MKNK+c4E/sbsTui/bdYkcn11xwgx0G5fnP6wjpVhIeuHAosrWz/7Rq8vHH1swQ6E19knAfhOWjEn+GjDSCdSaqsSiyUpgRj105WDf8sVDKpvII5IqMa7QFEOBOd7bAirRaiUUCBHj0EOK0efgRO/Zq+wt/ZbF0R66fVj2HK6UuYZQ/vRK6Wsyv+DoNCGc0Rj";

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


    public void runOpMode() {

        robot.init(hardwareMap);

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
                int ringCondition = 0; //1 = zero rings, 2 = one ring, 3 = four rings
                while (scanTime.seconds() < 5) {
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
                while(atLine = false) {
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

                tfod.getUpdatedRecognitions();

            }else {telemetry.addLine("WARNING! TFOD INIT FAILURE"); telemetry.update();}

        }

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

        /**
         * Initialize the Vuforia localization engine.
         */
        private void initVuforia() {
            /*
             * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
             */
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

            parameters.vuforiaLicenseKey = VUFORIA_KEY;
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

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


}
