package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="RightAuto", group="Linear OpMode")

public class RightAuto extends LinearOpMode 
{
    Hardware robot = new Hardware();
     
    boolean grabbed = false;

    @Override
    public void runOpMode() 
    {
        robot.init(hardwareMap);
        
        // Outputs that robot is initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for start and set runtime to 0
        waitForStart();
        
        rightAuto();
        
        // auto();
        // sleep(2000);
        // reverseAuto();
    }
    
    public void rightAuto()
    {
        motors(-1, 0, -0.05);
        sleep(400);
        motors(0, 0, 0);
    }
    
    public void leftAutoEdge()
    {
        motors(0, 1, 0);
        sleep(400);
        motors(-1, 0, 0);
        sleep(1500);
        motors(0, -1.5, 0);
        sleep(1000);
        motors(0, 0, 1);
        sleep(500);
        motors(-1, 0, 0);
        sleep(150);
    }
    
    public void leftAutoCorner()
    {
        motors(0, 1, 0);
        sleep(400);
        motors(-1, 0, 0);
        sleep(1500);
        motors(0, -1.5, 0);
        sleep(800);
        motors(0, 0, 1);
        sleep(50);
        motors(0, -1, 0);
        sleep(50);
        motors(0, 0, 0);
    }
    
    public void auto()
    {
        motors(-0.05, 1, 0.07);
        sleep(1500);
        motors(0, 0, 0);
        elevator(1);
        sleep(2100);
        elevator(0);
        motors(0.3, 0, 0);
        sleep(250);
        motors(0, 0, 0);
        elevator(-1);
        sleep(1000);
        grabber(false);
    }
    
    public void reverseAuto()
    {
        grabber(true);
        elevator(1);
        sleep(1000);
        motors(-0.35, 0, 0);
        sleep(300);
        motors(0, 0, 0);
        elevator(-1);
        sleep(1300);
        elevator(0);
        motors(-0.05, -1, -0.07);
        sleep(1500);
        motors(0, 0, 0);
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
    
    public void elevator(double elevatorPower)
    {
        robot.elevator.setPower(elevatorPower);
        
        telemetry.addData("Elevator Power", "%4.2f", elevatorPower);
        telemetry.update();
    }
    
    public void grabber(boolean grab)
    {
        // Grabber servo movement
        if(grab)
        {
            robot.grabber.setPosition(1);
            grabbed = true;
        }
        else
        {
            robot.grabber.setPosition(0);
            grabbed = false;
        }
        
        telemetry.addData("Grabbed", grabbed);
        telemetry.update();
    }
}

