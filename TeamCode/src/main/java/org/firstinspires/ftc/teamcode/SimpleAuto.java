package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="SimpleAuto", group="Linear OpMode")

public class SimpleAuto extends LinearOpMode 
{
    private ElapsedTime runtime = new ElapsedTime();

    Hardware robot = new Hardware();
    int[] motorVals;

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
        
        simpleAuto();
        
        // auto();
        // sleep(2000);
        // reverseAuto();
    }
    
    public void simpleAuto()
    {
        robot.motors(-1, 0, -0.05);
        sleep(400);
    }
    
   
    public void updateTelemetry()
    {
        //pull out the values of each motor
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
