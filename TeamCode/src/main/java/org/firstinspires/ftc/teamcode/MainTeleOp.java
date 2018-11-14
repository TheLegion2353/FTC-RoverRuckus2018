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
                intakeLiftServoLeft.setPosition(1);
            }
            else if (gamepad1.left_bumper) {
                intakeLiftServoRight.setPosition(0);
                intakeLiftServoLeft.setPosition(0);
            }
            else {
                intakeLiftServoRight.setPosition(0.5);
                intakeLiftServoLeft.setPosition(0.5);
            }
        }

        //Spinning Collector Motor
        if(gamepad1.a) {
            intakeMotor.setPower(0.75);
        }
        else {
            intakeMotor.setPower(0);
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
