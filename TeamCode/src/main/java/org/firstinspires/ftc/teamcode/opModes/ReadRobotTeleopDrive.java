package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class ReadRobotTeleopDrive extends OpMode {


    @Override
    public void init() {

    }
//   to now every button/joystick mode
    @Override
    public void loop() {
        telemetry.addLine("to now every button/joystick mode");
        telemetry.addData("lx", gamepad1.left_stick_x);
        telemetry.addData("ly", gamepad1.left_stick_y);
        telemetry.addData("rx", gamepad1.right_stick_x);
        telemetry.addData("ry", gamepad1.right_stick_y);
        telemetry.addData("a", gamepad1.a);
        telemetry.addData("x", gamepad1.x);
        telemetry.addData("y", gamepad1.y);
        telemetry.addData("b", gamepad1.b);
        telemetry.addData("dpad_up", gamepad1.dpad_up);
        telemetry.addData("dpad_down", gamepad1.dpad_down);
        telemetry.addData("dpad_left", gamepad1.dpad_left);
        telemetry.addData("dpad_right", gamepad1.dpad_right);
    }
}
