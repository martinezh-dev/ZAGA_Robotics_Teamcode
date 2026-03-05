package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

@TeleOp(name= "AprilTagTest", group = "Linear OpMode")
public class AprilTagTest extends LinearOpMode
{
    Hardware robot = new Hardware();
    VisionPortal webcam;

    private int targetTag = -1;
    AprilTagProcessor aprilTag;
    AprilTagDetection detectedTag = null;

    private int[] motorVals;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode()
    {
        robot.init(hardwareMap);
        robot.initCamera(hardwareMap, aprilTag);
        initAprilTag();

        telemetry.addData(">", "Touch START to start");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while(opModeIsActive())
        {
            //add the last detected tag to the telemetry
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            for(AprilTagDetection detection: currentDetections)
            {
                if(detection.metadata != null)
                {
                    detectedTag = detection;
                }
            }
            telemetry.addData("Found", "ID %d (%s)", detectedTag.id, detectedTag.metadata.name);
            telemetry.addData("Range",  "%5.1f inches", detectedTag.ftcPose.range);
            telemetry.addData("Bearing","%3.0f degrees", detectedTag.ftcPose.bearing);
            telemetry.addData("Yaw","%3.0f degrees", detectedTag.ftcPose.yaw);
            telemetry.addData("\n>","Drive using joysticks to find valid target\n");

            //use controllers to move the robot. Update the position in telemetry
            double max;

            //Gamepad 1 Settings
            // Left vertical moves forward and back
            double vertical = -gamepad1.left_stick_y;  // Upward on y-stick gives negative value

            // Left horizontal goes right and left
            double horizontal = gamepad1.left_stick_x;

            // Right hoirzontal rotates
            double rotation = gamepad1.right_stick_x;

            // Allows for more precise movements if left trigger is held
            if(gamepad1.left_trigger > 0)
            {
                vertical /= 3;
                horizontal /= 3;
                rotation /= 3;
            }

            // Test code that powers each motor individually based on gamepad input

            /*
            leftFrontPower  = gamepad1.x ? 1.0 : 0.0;  // X button
            leftBackPower   = gamepad1.a ? 1.0 : 0.0;  // A button
            rightFrontPower = gamepad1.y ? 1.0 : 0.0;  // Y button
            rightBackPower  = gamepad1.b ? 1.0 : 0.0;  // B button
            */

            // Power wheels
            robot.motors(vertical, horizontal, rotation);



            //Gamepad 2 Settings

            if (gamepad2.right_trigger > 0.1) {
                robot.setBlaseter(1.0);
            }
            else {
                robot.setBlaseter(0.0);
            }

            // Gamepad2 right vertical controls intake system
            robot.setIntake(gamepad2.right_stick_y);

            //Get the current values of the robot's motors for telemetry;
            //[0]: front left [1]: front right [2]: back left [3]: back right
            //[4]: intake [5]: blaster
            motorVals = robot.getVals();

            // Update telemetry with appropriate data
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front Left/Right", "%4.2f, %4.2f", motorVals[0], motorVals[1]);
            telemetry.addData("Back  Left/Right", "%4.2f, %4.2f", motorVals[2], motorVals[3]);
            telemetry.addData("Blaster Power", "%4.2f", motorVals[5]);
            telemetry.addData("Intake", "%4.2f", motorVals[4]);
            telemetry.update();
        }
    }

    private void initAprilTag()
    {
        aprilTag = new AprilTagProcessor.Builder().build();
        // Adjust Image Decimation to trade-off detection-range for detection-rate.
        // e.g. Some typical detection data using a Logitech C920 WebCam
        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second
        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second
        // Note: Decimation can be changed on-the-fly to adapt during a match.
        aprilTag.setDecimation(2);

        webcam = robot.getWebcam();
    }
}
