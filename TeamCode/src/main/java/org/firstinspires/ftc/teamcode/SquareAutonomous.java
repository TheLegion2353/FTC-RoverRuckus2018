package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/*
    This program is for when your robot faces crater.
 */
@Autonomous(name="SquareAutonomous", group="SquareAutonomous")
public class SquareAutonomous extends LinearOpMode {
    //DC Motors
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeMotor;
    private DcMotor motorElevate;
    //Servos
    private Servo intakeLiftServoRight;
    private Servo intakeLiftServoLeft;
    private Servo depositorServo;

    private int i = 0;

    private double Pi = Math.PI;

    private double diameter = 3.54331;
    private double circumfrence = diameter * Pi;
    private int ticksPerRevolution = 2240/2;
    private double ticksPerInch = ticksPerRevolution / circumfrence;

    private double width = 15;
    private double degreesPerRevolution = (circumfrence /  (2 * Pi * (width / 2))) * 360;
    private double ticksPerDegree = ticksPerRevolution / degreesPerRevolution;

    public void runOpMode()
    {
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

        waitForStart();

        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        move(21.5);
        turn(-90);
        move(49.85);
        turn(45);
        move(35.25);
        //intakeDrop();
        //intakeSpit();
        turn(360);
        move(94);

        while(opModeIsActive()) {
            telemetry.addData("Position", motorLeft.getCurrentPosition());
            telemetry.update();
        }
    }

    public void turn(double degrees) {
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorRight.setTargetPosition((int) Math.round(degrees * ticksPerDegree));
        motorLeft.setTargetPosition((int) Math.round(degrees * ticksPerDegree));

        motorLeft.setPower(0.1);
        motorRight.setPower(0.1);

        while(motorLeft.isBusy() || motorRight.isBusy()) {
        }

        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void move(double inches) {
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorLeft.setTargetPosition((int) -Math.round(inches * ticksPerInch));
        motorRight.setTargetPosition((int) Math.round(inches * ticksPerInch));

        motorLeft.setPower(-0.1);
        motorRight.setPower(0.1);

        while(motorLeft.isBusy() || motorRight.isBusy()) {
            telemetry.addData("Position", motorRight.getCurrentPosition());
            telemetry.update();
        }

        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}