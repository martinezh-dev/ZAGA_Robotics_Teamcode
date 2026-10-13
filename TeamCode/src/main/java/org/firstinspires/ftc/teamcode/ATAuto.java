package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous(name="ATAuto", group="Linear OpMode")

public class ATAuto extends LinearOpMode
{
    private Hardware robot;
    private double targetID;
    private double targetDistance;
    private ElapsedTime runtime = new ElapsedTime();
    private double[] motorVals;

    private int allowedError = 2;

    @Override
    public void runOpMode() throws InterruptedException
    {
        robot = new Hardware();
        robot.init(hardwareMap);

        telemetry.addData("Type", " Shi");
        telemetry.update();

        waitForStart();
        runtime.reset();
        AprilTagDetection detection = robot.getDetection();

        while(detection.id != targetID) {
            robot.motors(0, 0, 0.5);
        }
        robot.motors(0,0,0);
        if(detection != null)
        {
            telemetry.addData("Found", "ID %d (%s)", detection.id, detection.metadata.name);
            telemetry.addData("Range",  "%5.1f inches", detection.ftcPose.range);
            telemetry.addData("Bearing","%3.0f degrees", detection.ftcPose.bearing);
            telemetry.addData("Yaw","%3.0f degrees", detection.ftcPose.yaw);
            telemetry.addData("\n>","Drive using joysticks to find valid target\n");
        }
        telemetry.addData("Beginning", "Turn");
        telemetry.update();

        double bearing = detection.ftcPose.bearing;
        while(opModeIsActive() && (bearing <= -allowedError || bearing >= allowedError))
        {
            if(bearing > 0)
            {
                robot.motors(0,0,0.5);
            }else
            {
                robot.motors(0,0,-0.5);
            }
        }
        telemetry.addData("Beginning", "Strafe");
        telemetry.update();
        robot.motors(0,0,0);

        double yaw = detection.ftcPose.yaw;

        while(opModeIsActive() && (yaw <= -allowedError || yaw >= allowedError))
        {
            if(yaw > 0)
            {
                robot.motors(0,0.5,0);
            }else
            {
                robot.motors(0,-0.5,0);
            }
        }
        robot.motors(0,0,0);

        telemetry.addData("Beginning", "Translation");
        telemetry.update();

        double distance = detection.ftcPose.range;
        while(opModeIsActive() && (distance < targetDistance - allowedError || distance > targetDistance - allowedError))
        {
            if(distance < targetDistance)
            {
                robot.motors(-0.5, 0, 0);
            }
            else
            {
                robot.motors(0.5,0,0);
            }
        }
    }
}