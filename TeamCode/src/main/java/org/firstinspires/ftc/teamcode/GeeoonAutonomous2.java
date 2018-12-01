package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/*
    This program is for when your robot faces away from the deposit spot.
 */
@TeleOp(name="GeeoonAutonomous2", group="GeeoonAutonomous")
public class GeeoonAutonomous2 extends OpMode {
    //DC Motors
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeMotor;
    private DcMotor motorElevate;
    //Servos
    private Servo intakeLiftServoRight;
    private Servo intakeLiftServoLeft;
    private Servo depositorServo;
    public int i;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        //Driving
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");

        //Intake Motor
        intakeMotor = hardwareMap.dcMotor.get("intakeMotor");

        //Intake Lift Motor
        intakeLiftServoRight = hardwareMap.servo.get("intakeLiftServoRight");
        intakeLiftServoLeft = hardwareMap.servo.get("intakeLiftServoLeft");

        //Depositor Servo
        depositorServo = hardwareMap.servo.get("depositorServo");

        //Elevator Motor
        motorElevate = hardwareMap.dcMotor.get("motorElevate");

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        //This is where you'll do the autonomous programming.
        /*
        For every 100ms, the robot moves about 10 inches.
        For every 200ms, the robot moves about inches.
        For every 300ms, the robot moves about inches.

         */

        moveForward(320);
        turnLeft90();
        moveForward(400);
        turnLeft(25);
        moveForward(450);
        intakeSpit();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        i++;

        //Driving
        motorRight.setPower(-gamepad1.right_stick_y);
        motorLeft.setPower(gamepad1.left_stick_y);

        if(i % 25 == 0) {
            //Intake Lift Servo
            if (gamepad1.right_bumper) {
                intakeLiftServoRight.setPosition(1);
                intakeLiftServoLeft.setPosition(0);
            }
            else if (gamepad1.left_bumper) {
                intakeLiftServoRight.setPosition(0);
                intakeLiftServoLeft.setPosition(1);
            }
            else {
                intakeLiftServoRight.setPosition(0.5);
                intakeLiftServoLeft.setPosition(0.5);
            }

            //Depositor Servo
            if(gamepad1.dpad_up) {
                depositorServo.setPosition(0.75);
            }
            else if(gamepad1.dpad_down) {
                depositorServo.setPosition(0.25);
            }
            else {
                depositorServo.setPosition(0.5);
            }
        }

        //Spinning Collector Motor
        if(gamepad1.a) { //When A is pressed.
            //Set power to negative to make the things go in.
            intakeMotor.setPower(-0.75);
        }
        else if (gamepad1.x){ //When X is pressed.
            //Set power to positive to make the motor force thing out, just in case something gets stuck.
            intakeMotor.setPower(1);
        } else {
            //When nothing is being pressed, the motor stops moving.
            intakeMotor.setPower(0);
        }

        //Intake Platform Elevator
        if (motorElevate.getCurrentPosition() > 0) {
            motorElevate.setPower(-gamepad1.right_trigger);
        }
        else if(motorElevate.getCurrentPosition() < -3250) {
            motorElevate.setPower(gamepad1.left_trigger);
        }
        else {
            motorElevate.setPower(gamepad1.left_trigger-gamepad1.right_trigger);
        }

        telemetry.addData("Position", motorElevate.getCurrentPosition());
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }

    //This method makes the robot move forward for a specific amount of time in miliseconds.
    public void intakeElevate() {
        i++;
        if (i % 25 == 0) {

        }
        if (i < 300) {
            intakeSpit();
        } else {
            i = 0;
        }
    }

    public void turnLeft(double degrees) {
        //The left motor is set to go backwards.
        motorLeft.setPower(1);
        //The right motor is set to go forwards.
        motorRight.setPower(1);
        //Converts degrees to milliseconds of turning.
        degrees = 3.7 * degrees;
        long time = (long) degrees;
        //Makes the code halt so that the motor have time to spin.
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }
        //Stop the motors from spinning.
        motorLeft.setPower(0.0);
        motorRight.setPower(0.0);

        //Give time to let the motor come to a full stop
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }
    }

    public void turnRight(double degrees) {
        //The left motor is set to go backwards.
        motorLeft.setPower(1);
        //The right motor is set to go forwards.
        motorRight.setPower(1);
        //Converts degrees to milliseconds of turning.
        degrees = 3.7 * degrees;
        long time = (long) degrees;
        //Makes the code halt so that the motor have time to spin.
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }
        //Stop the motors from spinning.
        motorLeft.setPower(0.0);
        motorRight.setPower(0.0);

        //Give time to let the motor come to a full stop
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }
    }

    public void intakeSpit() {
        intakeMotor.setPower(0.75);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            intakeMotor.setPower(0);
        }
        intakeMotor.setPower(0);
    }

    public void intakeSuck() {
        intakeMotor.setPower(-0.75);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            intakeMotor.setPower(0);
        }
        intakeMotor.setPower(0);
    }

    public void moveForward(long time) {
        motorLeft.setPower(-1);
        motorRight.setPower(1);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    //This method makes the robot move backward for a specific amount of time in miliseconds.
    public void moveBackward(long time) {
        motorLeft.setPower(1);
        motorRight.setPower(-1);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    //This method makes the robot do a 90 degree left turn.
    public void turnLeft90() {
        //The left motor is set to go backwards.
        motorLeft.setPower(1);
        //The right motor is set to go forwards.
        motorRight.setPower(1);
        //Makes the code halt for a second so that the motor have time to spin.
        try {
            Thread.sleep(333);
        } catch (InterruptedException e) {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }
        //Stop the motors from spinning.
        motorLeft.setPower(0.0);
        motorRight.setPower(0.0);

        //Give time to let the motor come to a full stop
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }
    }

    //This method makes the robot do a 90 degree right turn.
    public void turnRight90() {
        //The left motor is set to go backwards.
        motorLeft.setPower(-1);
        //The right motor is set to go forwards.
        motorRight.setPower(-1);
        //Makes the code halt for a second so that the motor have time to spin.
        try {
            Thread.sleep(333);
        } catch (InterruptedException e) {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }
        //Stop the motors from spinning.
        motorLeft.setPower(0.0);
        motorRight.setPower(0.0);

        //Give time to let the motor come to a full stop
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }
    }
}