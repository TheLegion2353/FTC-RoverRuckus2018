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

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

/*
    This program is for when your robot faces crater.
 */
@Autonomous(name="CraterAutonomous", group="CraterAutonomous")
public class CraterAutonomous extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "AZxtF4//////AAABmetmUJS+CUmtjoNMl4L3o+BO4uH/n1Kf+IQs2z9MfucFIkAZs191xmitHhU4pYQr0z+lkT6/5rST9rx5+Ao4BK+OP6KWvKRaK53qaHeM25HfOssRdkJ47P+ccYJaP4/Gm2rVRY3Fm5pSFYN71Q+9se9Qp1W4TtZpSCo7Y6zsv3IXEhR2jbvIHojbpJYjUZEHBpiEw1fa/L2iQ5hZYpi/ysSRW1cNqYY1oZms2BDUu5j2gnytc/TWOguPaIUfgsWQe22sknGJj2CuSXx/sAkLtfn90pizPNhPk5kKYaG/GCa61mU7979grWWTRHvcL6pSDdw3wvwgFxDy6uHlFdDRdBHc3eorItnH9WTobakR1T3X\n";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    //DC Motors
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeMotorFront;
    private DcMotor intakeMotorBack;

    //Servos
    private Servo intakeLiftServoRight;
    private Servo intakeLiftServoLeft;

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
        intakeMotorFront = hardwareMap.dcMotor.get("intakeMotorFront");
        intakeMotorBack = hardwareMap.dcMotor.get("intakeMotorBack");

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");

        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        waitForStart();

        if (opModeIsActive()) {
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0;

            if (tfod != null) {
                tfod.activate();
            }

            int position = -1;

            while (opModeIsActive() && elapsedTime <= 2000) {
                if (tfod != null) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 3) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }
                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    position = 0;
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    position = 2;
                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    position = 1;
                                }
                            }
                        }
                        else if(updatedRecognitions.size() == 2) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;

                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }
                            if(goldMineralX == -1) {
                                telemetry.addData("Gold Mineral Position", "Right");
                                position = 2;
                            }
                            else if(silverMineral1X != -1){
                                if (goldMineralX < silverMineral1X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    position = 2;
                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    position = 1;
                                }
                            }
                        }
                        if(position == -1) {
                            telemetry.addData("Gold Mineral Position", "Unknown");
                        }
                        telemetry.update();
                        elapsedTime = System.currentTimeMillis() - startTime;
                    }
                }
            }

            if (tfod != null) {
                tfod.shutdown();
            }

            //Robot starts facing backwards
            //Left
            if(position == 0) {
                turn(-45, 0.1);
                move(-24, 0.1);
                move(24, 0.1);
                turn(-135, 0.1);
            }
            //Center
            else if(position == 1) {
                move(-16.97, 0.1);
                move(16.97, 0.1);
                turn(-90, 0.1);
            }
            //Right
            else if(position == 2) {
                turn(45, 0.1);
                move(-24, 0.1);
                move(24, 0.1);
                turn(-45, 0.1);
            }
            //Unknown
            else {
                move(-9.5, 0.1);
                turn(-45, 0.1);
            }

            move(50.911, 0.1);
            turn(-135, 0.1);
            move(36, 0.1);
            spit(1);
            turn(360, 0.1);
            move(94, 0.6);
        }
    }

    public void turn(double degrees, double power) {
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorRight.setTargetPosition((int) Math.round(degrees * ticksPerDegree));
        motorLeft.setTargetPosition((int) Math.round(degrees * ticksPerDegree));

        motorLeft.setPower(power * Math.signum(degrees));
        motorRight.setPower(power * Math.signum(degrees));

        while(motorLeft.isBusy() || motorRight.isBusy()) {
        }

        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void move(double inches, double power) {
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorLeft.setTargetPosition((int) -Math.round(inches * ticksPerInch));
        motorRight.setTargetPosition((int) Math.round(inches * ticksPerInch));

        motorLeft.setPower(-power * Math.signum(inches));
        motorRight.setPower(power * Math.signum(inches));

        while(motorLeft.isBusy() || motorRight.isBusy()) {
            telemetry.addData("Position", motorRight.getCurrentPosition());
            telemetry.update();
        }

        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void spit(double power) {
        intakeMotorFront.setPower(power);
        intakeMotorBack.setPower(-(power));
    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}