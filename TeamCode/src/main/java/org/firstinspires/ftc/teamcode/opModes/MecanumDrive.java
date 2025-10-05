package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class MecanumDrive extends OpMode {
    private DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, vacuumingMotor, shooterMotor1, shooterMotor2, transferencemotor;
    Servo controlShooter, pushServo;
    private IMU imu;
    double maxSpeed = 1.0;
    double target = 0;
    // every motor name
    @Override
    public void init() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "lf");
        backLeftMotor = hardwareMap.get(DcMotor.class, "lr");
        frontRightMotor = hardwareMap.get(DcMotor.class, "rf");
        backRightMotor = hardwareMap.get(DcMotor.class, "rr");
        vacuumingMotor = hardwareMap.get(DcMotor.class, "vm");
        shooterMotor1 = hardwareMap.get(DcMotor.class, "sm1");
        shooterMotor2 = hardwareMap.get(DcMotor.class, "sm2");
//        controlShooter = hardwareMap.get(DcMotor.class, "csm");
//        controlShooter = hardwareMap.get(Servo.class, "cs");
        transferencemotor = hardwareMap.get(DcMotor.class, "tm");
        pushServo = hardwareMap.get(Servo.class, "ps");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        controlShooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        controlShooter.setTargetPosition(0);
//        controlShooter.setPower(1);
//        controlShooter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        controlShooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD);

        imu.initialize(new IMU.Parameters(RevOrientation));
    }

    public void drive(double forward, double strafe, double rotate) {
        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        double maxPower = 1.0;
//        double maxSpeed = 1.0;

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        frontLeftMotor.setPower(maxSpeed * (frontLeftPower / maxPower));
        backLeftMotor.setPower(maxSpeed * (backLeftPower / maxPower));
        frontRightMotor.setPower(maxSpeed * (frontRightPower / maxPower));
        backRightMotor.setPower(maxSpeed * (backRightPower / maxPower));
    }

    @Override
    public void loop() {
        telemetry.addData("heading", imu.getRobotYawPitchRollAngles());
        telemetry.addData("target", target);
        drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        if (gamepad1.left_trigger>0.5) {
            vacuumingMotor.setPower(-1.0);
            pushServo.setPosition(0);
        } else if (gamepad1.y) {
            vacuumingMotor.setPower(1.0);
        }
        else {
            vacuumingMotor.setPower(0);
            pushServo.setPosition(1);
        }
        if (gamepad1.right_bumper && !gamepad1.rightBumperWasPressed()) {
            if (maxSpeed == 1.0) {
                maxSpeed = 0.5;
            }
            else {
                maxSpeed = 1.0;
            }
        }

//        target += (int) ((gamepad1.dpad_up ? 0.01 : gamepad1.dpad_down ? -0.01 : 0.0));
        if (gamepad1.right_trigger>0.5) {
            shooterMotor1.setPower(0.9);
            shooterMotor2.setPower(-0.9);
        }
        else {
            shooterMotor1.setPower(0);
            shooterMotor2.setPower(0);
        }
//        controlShooter.setPosition(target);
        if (gamepad1.left_bumper) {
            transferencemotor.setPower(1.0);
        }
        else {
            transferencemotor.setPower(0);
        }
    }
}