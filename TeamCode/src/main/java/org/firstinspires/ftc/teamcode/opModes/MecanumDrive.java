package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

@TeleOp
public class MecanumDrive extends OpMode {
    private DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, vacuumingMotor, shooterMotor;
    private IMU imu;
    double maxSpeed = 0.5;
    private boolean wasrightbumperPressed = false;
    @Override
    public void init() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "lf");
        backLeftMotor = hardwareMap.get(DcMotor.class, "lr");
        frontRightMotor = hardwareMap.get(DcMotor.class, "rf");
        backRightMotor = hardwareMap.get(DcMotor.class, "rr");
        vacuumingMotor = hardwareMap.get(DcMotor.class, "vm");
        shooterMotor = hardwareMap.get(DcMotor.class, "sm");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
        drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        if (gamepad1.a){
            vacuumingMotor.setPower(1.0);
        } else if (gamepad1.y) {
            vacuumingMotor.setPower(-1.0);
        }
        else {
            vacuumingMotor.setPower(0);
        }
        if (gamepad1.right_bumper && !wasrightbumperPressed) {
            if (wasrightbumperPressed = true) {
                maxSpeed = 0.3;
            }
        }
        else {
            maxSpeed = 0.5;
        }
        if (gamepad1.x) {
            shooterMotor.setPower(1.0);
        }
        else {
            shooterMotor.setPower(0);
        }
        if (gamepad1.left_bumper) {
            maxSpeed = 1.0;
        }
        else {
            maxSpeed = 0.5;
        }

    }
}