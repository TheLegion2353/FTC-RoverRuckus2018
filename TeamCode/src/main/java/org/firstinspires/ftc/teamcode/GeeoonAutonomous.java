package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="GeeoonAutonomous", group="GeeoonAutonomous")
public class GeeoonAutonomous extends OpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeMotor;

    private Servo intakeLiftServoRight;
    private Servo intakeLiftServoLeft;

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
        turnRight();
        /*
        moveForward(500);
        turnLeft();
        moveForward(500);
        turnRight();
        moveForward(500);
        */
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }

    //This method makes the robot do a 90 left turn.
    public void moveForward (long time) {
        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        try {
            Thread.sleep(time);
        } catch(InterruptedException e) {

        }
    }
    public void turnLeft() {
        //The left motor is set to go backwards.
        motorLeft.setPower(-0.1);
        //The right motor is set to go forwards.
        motorRight.setPower(0.1);
        //Makes the code halt for a second so that the motor have time to spin.
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {

        }
        //Stop the motors from spinning.
        motorLeft.setPower(0.0);
        motorRight.setPower(0.0);
    }

    public void turnRight() {
        //The left motor is set to go backwards.
        motorLeft.setPower(0.1);
        //The right motor is set to go forwards.
        motorRight.setPower(-0.1);
        //Makes the code halt for a second so that the motor have time to spin.
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {

        }
        //Stop the motors from spinning.
        motorLeft.setPower(0.0);
        motorRight.setPower(0.0);
    }
}
