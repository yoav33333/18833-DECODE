package org.firstinspires.ftc.teamcode.opModes;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.controller.PIDFController;

@TeleOp
@Configurable
public class PIDDrive extends OpMode {
    private PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;


    private DcMotorEx shooterMotor1, shooterMotor2;
    // other motors
    // private DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, vacuumingMotor, transferencemotor;

    // These values will need to be tuned for your specific motor and setup
    public static double p1 = 0.0025;
    public static double i1 = 0.0;
    public static double d1 = 0.001;
    public static double f1 = 0.0000; // Feedforward is important for velocity control

    static PIDFController controller1 = new PIDFController(p1, i1, d1, f1);

    // TODO: You must determine the Ticks Per Revolution for your specific motor.
    // For example, a GoBILDA 5202 series motor has 28 ticks per revolution at the motor shaft.
    public static final double TICKS_PER_REV = 28;

    // TODO: You must measure the gear ratio of your shooter mechanism.
    public static final double GEAR_RATIO = 1.0; // Assuming 1:1, change if different

    public static final double RPM_TARGET = 5000.0;
    public static final double TICKS_PER_SECOND_TARGET = (RPM_TARGET / 60.0) * TICKS_PER_REV * GEAR_RATIO;

    private ElapsedTime timer = new ElapsedTime();

    // Variables for manual velocity calculation
    private double lastPosition = 0;
    private double lastTime = 0;


    @Override
    public void init() {

        // Initialize shooter motors as DcMotorEx
        shooterMotor1 = hardwareMap.get(DcMotorEx.class, "sm1");
        shooterMotor2 = hardwareMap.get(DcMotorEx.class, "sm2");

        // Set motor directions
        shooterMotor2.setDirection(DcMotor.Direction.REVERSE);
        shooterMotor1.setDirection(DcMotor.Direction.FORWARD); // Assuming opposing direction for shooter

        // Set motors to run using encoders. This is crucial for velocity control.
        shooterMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Reset encoders
        shooterMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooterMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // It is recommended to use the internal PIDF controller for velocity if available
        // or ensure you set power in a loop as done below.
        shooterMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooterMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set the target for the PIDF controller
        controller1.setSetPoint(TICKS_PER_SECOND_TARGET);

        timer.reset();
        lastPosition = shooterMotor1.getCurrentPosition();
        lastTime = timer.seconds();

    }

    @Override
    public void loop() {
        // Manual velocity calculation
        double currentPosition = shooterMotor1.getCurrentPosition();
        double currentTime = timer.seconds();

        double deltaPosition = currentPosition - lastPosition;
        double deltaTime = currentTime - lastTime;

        // Avoid division by zero
        double currentVelocity1 = 0;
        if (deltaTime > 0) {
            currentVelocity1 = deltaPosition / deltaTime;
        }

        lastPosition = currentPosition;
        lastTime = currentTime;


        // Calculate the power adjustment from the PIDF controller
        double power1 = controller1.calculate(currentVelocity1);

        // Apply the calculated power to the motors
        if (gamepad1.left_bumper) {
            shooterMotor1.setPower(power1);
            shooterMotor2.setPower(power1); // Assuming second motor runs at the same power
        }
        else {
            shooterMotor1.setPower(0);
            shooterMotor2.setPower(0);
        }
        // Telemetry for debugging and tuning
        panelsTelemetry.getTelemetry().addData("Target Ticks/Sec", TICKS_PER_SECOND_TARGET);
        panelsTelemetry.getTelemetry().addData("Current Ticks/Sec", currentVelocity1);
        panelsTelemetry.getTelemetry().addData("Calculated Power", power1);
        panelsTelemetry.getTelemetry().update();
    }
}
