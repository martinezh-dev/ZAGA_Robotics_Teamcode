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
    private double[] motorVals;

    private final double MAX_OUT = 0.85;
    private final double MAX_IN = 0.65;

    private boolean blasting = false;

    private boolean inputting = false;

    private int blasterDir = 1;

    private int flipDrive = 1;



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

        robot.whack(90);

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

            if(gamepad1.aWasReleased())
            {
                flipDrive *= -1;
            }

            // Test code that powers each motor individually based on gamepad input
            
            /*
            leftFrontPower  = gamepad1.x ? 1.0 : 0.0;  // X button
            leftBackPower   = gamepad1.a ? 1.0 : 0.0;  // A button
            rightFrontPower = gamepad1.y ? 1.0 : 0.0;  // Y button
            rightBackPower  = gamepad1.b ? 1.0 : 0.0;  // B button
            */

            // Power wheels
            robot.motors(vertical * flipDrive, horizontal * flipDrive, rotation);



            //Gamepad 2 Settings

            double blaster = 0;
            if(gamepad2.aWasReleased())
            {
                blasterDir *= -1;
            }
            if(gamepad2.yWasReleased())
            {
                blasting = !blasting;
            }
            if (blasting) {
                blaster = MAX_OUT * blasterDir;
            }
            else {
                blaster = gamepad2.right_trigger*MAX_OUT * blasterDir;
            }

            if(gamepad2.bWasReleased())
            {
                inputting = !inputting;
            }

            //robot.whack((int)(180*gamepad2.left_stick_x));

            // Gamepad2 right vertical controls intake system
            robot.setIntake1(Math.min(gamepad2.right_stick_y, MAX_IN));
            robot.setIntake2(Math.min(gamepad2.left_stick_y, MAX_IN));

            //Get the current values of the robot's motors for telemetry;
            //[0]: front left [1]: front right [2]: back left [3]: back right
            //[4]: intake [5]: blaster
            motorVals = robot.getValsPercise();

            // Update telemetry with appropriate data
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front Left/Right", "%4.2f, %4.2f", motorVals[0], motorVals[1]);
            telemetry.addData("Back  Left/Right", "%4.2f, %4.2f", motorVals[2], motorVals[3]);
            telemetry.addData("Blaster Power", "%4.2f", motorVals[5]);
            telemetry.addData("Intake", "%4.2f", motorVals[4]);
            telemetry.addData("Whacker Position", "%4.2f", motorVals[6]);
            telemetry.update();
        }
    }
}
