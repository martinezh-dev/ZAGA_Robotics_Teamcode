package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="BotRightAuto", group="Linear OpMode")

public class BotRightAuto extends LinearOpMode
{
    Hardware robot = new Hardware();

    @Override
    public void runOpMode()
    {
        robot.init(hardwareMap);
        
        // Outputs that robot is initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for start and set runtime to 0
        waitForStart();
        botRight();
    }

    public void botRight()
    {
        motors(1,0,0);
        sleep(3127);
        motors(0, 0, 1);
        sleep(25);
        robot.ballBlaster.setPower(1);
        motors(-1, 0, 0);
        sleep(1234);
        motors(0,0,0);

        //servo motions to fire three balls
        for(int i = 0; i < 3; i++)
        {
            robot.ballBlocker.setPosition(1);
            sleep(30);
            robot.ballBlocker.setPosition(0);
        }

        motors(1,0,0);
        sleep(1234);
        motors(0,0,-1);
        sleep(25);
        motors(-1,0,0);
        sleep(3127);
        motors(0,0,0);
    }

    public void motors(double vertical, double horizontal, double rotation)
    {
        double max; 
        
        // Calculate power per wheel based on input
        double leftFrontPower  = vertical + horizontal + rotation;
        double rightFrontPower = vertical - horizontal - rotation;
        double leftBackPower   = vertical - horizontal + rotation;
        double rightBackPower  = vertical + horizontal - rotation;

        // Normalize so maximum power a wheel can have is 1
        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));
        
        // Apply normalization to wheels if needed
        if (max > 1.0) 
        {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }
        
        // Power wheels
        robot.frontLeft.setPower(leftFrontPower);
        robot.frontRight.setPower(rightFrontPower);
        robot.backLeft.setPower(leftBackPower);
        robot.backRight.setPower(rightBackPower);

        // Update telemetry with appropriate data
        telemetry.addData("Front Left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
        telemetry.addData("Back  Left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
        telemetry.update();
    }
}