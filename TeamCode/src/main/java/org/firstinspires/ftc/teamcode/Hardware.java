package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class Hardware 
{
    // Declare motors
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    
    // Declare ballBlaster
    private DcMotor ballBlaster;
    private DcMotor intake;
    
    public void init(HardwareMap hardwareMap)    
    {
        // Initialize the hardware variables

        //Motors 
        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backRight = hardwareMap.get(DcMotor.class, "back_right");
        
        // Activity motors
        ballBlaster = hardwareMap.get(DcMotor.class, "ballShooter");
        intake = hardwareMap.get(DcMotor.class, "intake");

        // Initialize direction of motors
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        ballBlaster.setDirection(DcMotor.Direction.FORWARD);
        intake.setDirection(DcMotor.Direction.REVERSE);
        
        //camera
        //camera = hardwareMap.get(WebcamName.class, "Webcam 1");
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
        frontLeft.setPower(leftFrontPower);
        frontRight.setPower(rightFrontPower);
        backLeft.setPower(leftBackPower);
        backRight.setPower(rightBackPower);
    }

    public void setIntake(double power)
    {
        intake.setPower(power);
    }
    public void setBlaseter(double power)
    {
        ballBlaster.setPower(power);
    }

    int[] getVals()
    {
        int[] ret = {(int)(100*frontLeft.getPower()), (int)(100*frontRight.getPower()),
                     (int)(100*backLeft.getPower()), (int)(100*backRight.getPower()),
                     (int)(100*intake.getPower()), (int)(100*ballBlaster.getPower())};
        return ret;
    }
    double[] getValsPercise()
    {
        double[] ret = {frontLeft.getPower(), frontRight.getPower(),
                        backLeft.getPower(), backRight.getPower(),
                        intake.getPower(), ballBlaster.getPower()};
        return ret;
    }
}
