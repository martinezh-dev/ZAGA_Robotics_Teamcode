package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Four-Mode Full Autonomous", group="Linear OpMode")
public class FourModeAuto extends BaseAuto {

    @Override
    public void runOpMode() {

        initHardware();

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Instructions", "Select start position using D-Pad");
        telemetry.update();

        int startPosition = 1;

        // Pre-start selection menu
        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Select Start", "1-LeftCorner 2-LeftEdge 3-RightCorner 4-RightEdge");
            telemetry.addData("Current", startPosition);
            telemetry.update();

            if(gamepad1.dpad_up) startPosition = 1;
            if(gamepad1.dpad_right) startPosition = 2;
            if(gamepad1.dpad_down) startPosition = 3;
            if(gamepad1.dpad_left) startPosition = 4;

            sleep(100); // small delay to avoid rapid changes
        }

        waitForStart();

        telemetry.addData("Start Position", startPosition);
        telemetry.update();

        ElapsedTime matchTimer = new ElapsedTime();
        matchTimer.reset();

        // Run autonomous routine repeatedly for first 30 seconds
        while(opModeIsActive() && matchTimer.seconds() < 30) {

            switch(startPosition){
                case 1: // Bottom-left corner
                    move(1.0, 0, 0, 1200);       // Move forward
                    move(0, 1.0, 0, 600);        // Move right
                    setElevator(1.0, 2000);      // Raise elevator
                    shootBalls(2);                // Shoot into basket
                    move(-0.5, 0, 0, 800);       // Back up
                    break;

                case 2: // Left edge
                    move(1.0, 0, 0, 1400);
                    setElevator(1.0, 2000);
                    shootBalls(2);
                    move(-0.5, 0, 0, 1000);
                    break;

                case 3: // Bottom-right corner
                    move(1.0, 0, 0, 1200);
                    move(0, -1.0, 0, 600);       // Move left
                    setElevator(1.0, 2000);
                    shootBalls(2);
                    move(-0.5, 0, 0, 800);
                    break;

                case 4: // Right edge
                    move(1.0, 0, 0, 1400);
                    setElevator(1.0, 2000);
                    shootBalls(2);
                    move(-0.5, 0, 0, 1000);
                    break;
            }

            sleep(500); // small delay between loops
        }

        telemetry.addData("Status", "Autonomous Phase Complete. Drivers take over!");
        telemetry.update();

        // --- TeleOp starts here ---
        while(opModeIsActive()) {
            // Mecanum drive
            double vertical = -gamepad1.left_stick_y;
            double horizontal = gamepad1.left_stick_x;
            double rotation = gamepad1.right_stick_x;

            double leftFrontPower  = vertical + horizontal + rotation;
            double rightFrontPower = vertical - horizontal - rotation;
            double leftBackPower   = vertical - horizontal + rotation;
            double rightBackPower  = vertical + horizontal - rotation;

            // Normalize powers
            double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));
            if(max > 1.0){
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
            }

            robot.frontLeft.setPower(leftFrontPower);
            robot.frontRight.setPower(rightFrontPower);
            robot.backLeft.setPower(leftBackPower);
            robot.backRight.setPower(rightBackPower);

            // Elevator control
            double elevatorPower = -gamepad2.right_stick_y;
            robot.elevator.setPower(elevatorPower);
            robot.reverseElevator.setPower(elevatorPower);

            // Grabber control
            if(gamepad2.x) robot.grabber.setPosition(1);
            else if(gamepad2.b) robot.grabber.setPosition(0);

            telemetry.addData("Status", "TeleOp Active");
            telemetry.update();
        }
    }
}
