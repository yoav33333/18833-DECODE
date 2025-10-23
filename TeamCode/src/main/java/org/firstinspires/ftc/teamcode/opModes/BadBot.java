package org.firstinspires.ftc.teamcode.opModes;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class BadBot {
    private DcMotor frontLeftMotor, frontRightMotor;

    double maxSpeed = 1.0;

    public void init() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "fl");
        frontRightMotor = hardwareMap.get(DcMotor.class, "fr");
//        backRightMotor = hardwareMap.get(DcMotor.class, "br");
//        backLeftMotor = hardwareMap.get(DcMotor.class, "bl");

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("the Speed is", maxSpeed);


    }
    public void drive(double forward, double strafe) {

        double maxPower = 1.0;
//        double maxSpeed = 1.0;
        double frontLeftPower = forward + strafe;
        double backLeftPower = forward - strafe;
        double frontRightPower = forward - strafe;
        double backRightPower = forward + strafe;
        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));

    }
    public void loop() {
        frontLeftMotor.setPower(gamepad1.left_stick_y);
        frontRightMotor.setPower(-gamepad1.right_stick_y);
    }
}
