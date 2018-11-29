package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Main TeleOp", group="TeleOp")
public class MainTeleOp extends OpMode {
    //DC Motors
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeMotor;
    private DcMotor motorElevate;
    //Servos
    private Servo intakeLiftServoRight;
    private Servo intakeLiftServoLeft;
    private Servo depositorServo;
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
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */

    int i = 0;

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
}
