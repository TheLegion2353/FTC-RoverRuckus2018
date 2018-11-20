package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
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
    private Servo elevatePlatformServo;
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

        //Elevator Servo
        elevatePlatformServo = hardwareMap.servo.get("elevatePlatformServo");

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
        }

        //Spinning Collector Motor
        if(gamepad1.a) { //When A is pressed.
            //Set power to negative to make the things go in.
            intakeMotor.setPower(-0.75);
        }
        else if (gamepad1.b){ //When B is pressed.
            //Set power to positive to make the motor force thing out, just in case something gets stuck.
            intakeMotor.setPower(1);
        } else {
            //When nothing is being pressed, the motor stops moving.
            intakeMotor.setPower(0);
        }

        //Intake Platform Elevator
        if (gamepad1.right_trigger != 0) {
            motorElevate.setPower(1.00);
        } else if (gamepad1.left_trigger != 0) {
            motorElevate.setPower(-1.00);
        } else {
            motorElevate.setPower(0);
        }

        if (gamepad1.x) {
            //When X is pressed, the servo rotates.
            elevatePlatformServo.setPosition(1);
        } else if (gamepad1.y) {
            //When Y is pressed, the servo rotates.
            elevatePlatformServo.setPosition(0);
        } else {
            elevatePlatformServo.setPosition(0.5);
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
