package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.hardwaremap.HardwareHolonomicChassis;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;


/**
 * Created by Nathaniel on 12/9/2020.
 */
//@Disabled
@Autonomous(name = "Main Auto PID", group = "Competition")

public class MainAutoPID extends LinearOpMode {

    HardwareHolonomicChassis robot = new HardwareHolonomicChassis();
    int errorInches = 2;
    int errorDegrees = 2;

    // IMPORTANT: USB WebCam - "CAMERA_CHOICE = BACK;" and "PHONE_IS_PORTRAIT = false;"
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false;
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private static final String VUFORIA_KEY =
            "ASLZpXP/////AAABmcf6IAKpuUgbqERI9bu4hEZEPCSq/2sZe0zrgWI1rySsI2SfEEm2e6c+A5svGl6C6mv6fczUZsEDhyWIkVyvG1baGFjFP8YHOcX1Tme9oOUVBcrWbmAacREJcyQ0wQ7D9RlgohT8JVucF1NvWGyk8lqqUDY0QID9MbBw/YENyN84MKNK+c4E/sbsTui/bdYkcn11xwgx0G5fnP6wjpVhIeuHAosrWz/7Rq8vHH1swQ6E19knAfhOWjEn+GjDSCdSaqsSiyUpgRj105WDf8sVDKpvII5IqMa7QFEOBOd7bAirRaiUUCBHj0EOK0efgRO/Zq+wt/ZbF0R66fVj2HK6UuYZQ/vRK6Wsyv+DoNCGc0Rj";

    // Since ImageTarget trackables uses mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // Here we are just defining some constants and conversions
    private static final float mmPerInch = 25.4f;
    private static final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;

    /**
     * This is the webcam we are to use. As with other hardware devices such as motors and
     * servos, this device is identified using the robot configuration tool in the FTC application.
     */
    WebcamName webcamName = null;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    private boolean targetVisible = false;
    private float phoneXRotate = 0;
    private float phoneYRotate = 0;
    private float phoneZRotate = 0;


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        robot.fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /*
         * Retrieve the camera we are to use.
         */
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

        /**
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        //VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        /**
         * Here we indicate that we want to use an external webcam, comment this out to use the phone cam
         */
        parameters.cameraName = webcamName;

        // Make sure extended tracking is disabled for this example.
        parameters.useExtendedTracking = false;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsUltimateGoal = this.vuforia.loadTrackablesFromAsset("UltimateGoal");
        VuforiaTrackable blueTowerGoalTarget = targetsUltimateGoal.get(0);
        blueTowerGoalTarget.setName("Blue Tower Goal Target");
        VuforiaTrackable redTowerGoalTarget = targetsUltimateGoal.get(1);
        redTowerGoalTarget.setName("Red Tower Goal Target");
        VuforiaTrackable redAllianceTarget = targetsUltimateGoal.get(2);
        redAllianceTarget.setName("Red Alliance Target");
        VuforiaTrackable blueAllianceTarget = targetsUltimateGoal.get(3);
        blueAllianceTarget.setName("Blue Alliance Target");
        VuforiaTrackable frontWallTarget = targetsUltimateGoal.get(4);
        frontWallTarget.setName("Front Wall Target");

        //Puts all targets into a list to be easily referenced later in the program
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsUltimateGoal);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        //Set the position of the perimeter targets with relation to origin (center of field)
        redAllianceTarget.setLocation(OpenGLMatrix
                .translation(0, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        blueAllianceTarget.setLocation(OpenGLMatrix
                .translation(0, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));
        frontWallTarget.setLocation(OpenGLMatrix
                .translation(-halfField, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        // The tower goal targets are located a quarter field length from the ends of the back perimeter wall.
        blueTowerGoalTarget.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));
        redTowerGoalTarget.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //
        // Create a transformation matrix describing where the phone is on the robot.
        //
        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
        // Lock it into Portrait for these numbers to work.
        //
        // Info:  The coordinate frame for the robot looks the same as the field.
        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
        //
        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
        // pointing to the LEFT side of the Robot.
        // The two examples below assume that the camera is facing forward out the front of the robot.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
        final float CAMERA_VERTICAL_DISPLACEMENT = 4.75f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT = -1;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
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

            robot.claw.setPosition(0);
            robot.kicker.setPosition(0);

            /** Wait for the game to begin */
            telemetry.addData(">", "Press Play to start op mode");
            telemetry.update();

            waitForStart();

            if (tfod != null) {



                ElapsedTime scanTime = new ElapsedTime();
                scanTime.reset();

                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                int ringCondition = 1; //1 = zero rings, 2 = one ring, 3 = four rings
                while (scanTime.seconds() < 2 && opModeIsActive()) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());

                        // step through the list of recognitions and display boundary info.
                        int i = 1;
                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                            if (recognition.getLabel() == "Single") {
                                ringCondition = 2;
                            } else if (recognition.getLabel() == "Quad") {
                                ringCondition = 3;
                            }
                            telemetry.update();
                        }
                        telemetry.update();
                    }
                }

                targetsUltimateGoal.activate();

                boolean atTarget = false;
                /**drive forward from start*/
                setMotorPower(0, 1, 0);
                sleep(2250);
                setMotorPower(0, 0, 0);

                /**Start flywheel then allign with shooting position*/
                goToPosition(30,6,110, allTrackables);
                setMotorPower(0,0,0);
                /**Shoot then stop flywheel*/
                robot.shooter.setPower(-1);
                robot.lConveyor.setPower(1);
                robot.rConveyor.setPower(1);
                robot.mIntake.setPower(.5);
                sleep(4000);
                robot.kicker.setPosition(1);
                sleep(1000);
                robot.lConveyor.setPower(0);
                robot.rConveyor.setPower(0);
                robot.mIntake.setPower(0);
                robot.shooter.setPower(0);
                robot.kicker.setPosition(0);


                /**
                 * Go to target for dropping wobble goal
                 * Each section corresponds to the moves the robot should take depending on the
                 * wobble goal target for that match
                 */
                if (ringCondition == 1) {
                    goToPosition(36,14,90, allTrackables);
                    setMotorPower(0,0,-1);
                    sleep(1200);
                    setMotorPower(0,0,0);
                    setMotorPower((float) 0.5,0,0);
                    sleep(500);
                    setMotorPower(0,0,0);
                }
                else if (ringCondition == 2) {
                    goToPosition(36,14,90, allTrackables);
                    setMotorPower(0,0,-1);
                    sleep(600);
                    setMotorPower(0,0,0);
                    setMotorPower((float) 0.5,0,0);
                    sleep(500);
                    setMotorPower(0,0,0);
                }
                else if (ringCondition == 3) {
                    goToPosition(36,43 ,90, allTrackables);
                    setMotorPower(0,0,-1);
                    sleep(600);
                    setMotorPower(0,0,0);
                    setMotorPower(0, (float) 0.5,0);
                    sleep(750);
                    setMotorPower(0,0,0);
                }

                //release wobble goal
                robot.arm.setPower(-1);
                sleep(500);
                robot.arm.setPower(0);
                sleep(100);
                robot.claw.setPosition(0.9);
                sleep(200);
                robot.arm.setPower(1);
                sleep(250);
                robot.arm.setPower(0);

                /**
                 * Park on the shot line
                 * Again, each section corresponds to the moves the robot should take depending on the
                 * wobble goal target for that match
                 */
                if(ringCondition == 1) {
                    setMotorPower((float) -0.5, 0,0);
                    sleep(500);
                    setMotorPower(0,0,0);
                } else if (ringCondition == 2) {
                    setMotorPower((float) -0.5, 0,0);
                    sleep(500);
                    setMotorPower(0,0,0);
                }else if(ringCondition == 3){
                    setMotorPower(-1,0,0);
                    sleep(2000);
                    setMotorPower(0,0,0);
                }

            }
        }
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
        rampTimer.reset();
        double modifier = 0.1;
        while(opModeIsActive()) {
            if (rampTimer.seconds() >= rampTime * modifier) modifier = modifier + 0.1;
            setMotorPower(z * modifier, y * modifier, x * modifier);
            // Display the current value
            telemetry.addData("Timer", rampTimer.seconds());
            telemetry.update();
            if(rampTimer.seconds()>= rampTime)break;
        }
        setMotorPower(z,y,x);
    }
    /**
     * Drive via time ramping speed up and down
     * see also: {@link #rampSpeed}
     * @param z Max speed along the z axis (e.g. strafing)
     * @param y Max speed along the y axis (e.g. forward/back)
     * @param x Max speed along the x axis (e.g. rotation)
     * @param driveTime total drive time including ramp up and down
     * @param rampTime Time in seconds to get to full power
     */
    public void driveRamp(double z, double y, double x, double driveTime, double rampTime) {
        if (driveTime > (rampTime / 2)) {
            rampSpeed(z, y, x, rampTime);
            sleep((long) ((driveTime * 1000) - (rampTime * 2) * 1000));
            rampSpeed(0, 0, 0, rampTime);
        } else telemetry.addData("WARNING:", "rampTime longer than half driveTime");
        telemetry.update();
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

    /**
     * Drives to a target position using vuforia localization and pid controller
     * @param xInches target x coordinate in inches
     * @param yInches target y coordinate in inches
     * @param degrees target heading in degrees
     * @param allTrackables passthrough for allTrackables object
     */
    public void goToPosition (int xInches, int yInches, int degrees, List<VuforiaTrackable> allTrackables ) {

        /**
         * Initializing the PID controller object and passing the relevant info, we're using two separate instances for each axis of the robot
         */
        PIDController xPid = new PIDController(0.00005,0.01,0);
//        xPid.setInputRange(-72,72);
        xPid.setOutputRange(0,1);
        xPid.setTolerance(errorInches/144);
        xPid.enable();
        PIDController yPid = new PIDController(0.00005,0.01,0);
//        yPid.setInputRange(-72,72);
        yPid.setOutputRange(0,1);
        yPid.setTolerance(errorInches/144);
        yPid.enable();
        double xPower;
        double yPower;


        /**
         * This is the overall loop which should run until:
         *      The robot is at location
         *      Timeout is true
         *      Or the opmode is stopped.
         */
        ElapsedTime localizerTimeout = new ElapsedTime();
        boolean atTarget = false;
        while (!atTarget && localizerTimeout.seconds() < 5 && opModeIsActive()) {

            // check all the trackable targets to see which one (if any) is visible.
            targetVisible = false;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    targetVisible = true;

                    // getUpdatedRobotLocation() will return null if no new information is available since
                    // the last time that call was made, or if the trackable is not currently visible.
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }

            /// Provide feedback as to where the robot is located (if we know).
            if (targetVisible) {
                // express position (translation) of robot in inches.
                VectorF translation = lastLocation.getTranslation();
                telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                        translation.get(1) / mmPerInch, translation.get(0) / mmPerInch, translation.get(2) / mmPerInch);

                // express the rotation of the robot in degrees.
                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
                VectorF targetPosition = lastLocation.getTranslation();

                /**
                 * Running pid calculation and setting motor power appropriately
                 */
                xPower = xPid.performPID(translation.get(1));
                yPower = yPid.performPID(translation.get(0));
                setMotorPower((float)(-xPower), (float)(yPower),0 /*((rotation.thirdAngle-95) / 1000)*/);
                //(translation.get(1)-xInches*mmPerInch) / mmPerInch / 24/16
                // mm Distance-distance to object

                /**
                 * Checking if the robot position is within the error margin for target position
                 * This section is redundant because the pid object already has an integrated method for this check
                 */
//                if (((translation.get(1)-xInches*mmPerInch) / mmPerInch) > (+errorInches) || ((translation.get(1)-xInches*mmPerInch) / mmPerInch) < (-errorInches) ||
//                        ((translation.get(0)-yInches*mmPerInch) / mmPerInch) > (+errorInches) || ((translation.get(0)-yInches*mmPerInch) / mmPerInch) <(-errorInches) ||
//                        (rotation.thirdAngle-degrees) > (+errorDegrees) || (rotation.thirdAngle-degrees) < (-errorDegrees)) {
//                    atTarget = false;
//                } else atTarget = true;

            }
            else {
                telemetry.addData("Visible Target", "none");
                robot.fl.setPower(0);
                robot.fr.setPower(0);
                robot.bl.setPower(0);
                robot.br.setPower(0);
            }
            telemetry.update();
        }
        if (localizerTimeout.seconds()>5) telemetry.addLine("Localizer Timeout!");
        setMotorPower(0,0,0);
        telemetry.update();
    }

}
