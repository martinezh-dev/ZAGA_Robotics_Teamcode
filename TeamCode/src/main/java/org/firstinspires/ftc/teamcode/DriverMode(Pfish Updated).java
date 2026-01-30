package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="DriverMode", group="Linear OpMode")

/*
Gamepad Key:
        Gamepad1:
              Left Trigger: Slow
              Left Stick: Forward-Back, Left-Right
              Right Stick: Rotational

        Gamepad2:
                Left Trigger: Hold to rev death-wheel output motor
                Right Stick: Towards the back brings balls into ramp and the death-wheel
*/

public class DriverMode extends LinearOpMode 
{
        
    // Initialize variable that gives elapsed time
    private ElapsedTime runtime = new ElapsedTime();
    
    private Hardware robot = new Hardware();
    private int[] motorVals;


    @Override
    public void runOpMode() 
    {
        robot.init(hardwareMap);
        
        // Outputs that robot is initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for start and set runtime to 0
        waitForStart();
        runtime.reset();

        // Runs until opMode is disabled
        while (opModeIsActive()) 
        {
                
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
}
