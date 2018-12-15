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
    private DcMotor intakeMotorFront;
    private DcMotor intakeMotorBack;

    //Servos
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
        intakeMotorFront = hardwareMap.dcMotor.get("intakeMotorFront");
        intakeMotorBack = hardwareMap.dcMotor.get("intakeMotorBack");

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

    @Override
    public void loop() {

        //Driving
        motorRight.setPower(-gamepad1.right_stick_y);
        motorLeft.setPower(gamepad1.left_stick_y);

        //Spinning Collector Motor
        //Set power to negative to make the things go in, positive to make things go out.
        intakeMotorFront.setPower(gamepad1.left_trigger-gamepad1.right_trigger);
        intakeMotorBack.setPower(-(gamepad1.left_trigger-gamepad1.right_trigger));
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
