package org.firstinspires.ftc.teamcode.opModes

import com.bylazar.configurables.annotations.Configurable
import com.bylazar.gamepad.PanelsGamepad
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.util.controller.PIDFController

@TeleOp
@Configurable
class PIDkotlin : OpMode() {
    // PanelsTelemetry automatically hooks into the base 'telemetry' object.
    // We just need a PanelsGamepad instance to ensure the system is active.

    private lateinit var shooterMotor1: DcMotorEx
    private lateinit var shooterMotor2: DcMotorEx

    private val timer = ElapsedTime()

    // --- PID and Velocity Calculation Variables ---
    private var lastPosition: Double = 0.0
    private var lastTime: Double = 0.0

    // PID coefficients, tunable via dashboard
    private var p1: Double = 0.0025
    private var i1: Double = 0.0
    private var d1: Double = 0.001
    private var f1: Double = 0.0

    private lateinit var controller1: PIDFController

    // FIX 2 (Revised): Re-create the controller to apply new coefficients.
    // This is safer and works even if P, I, D, F are private.
    private fun updatePIDController() {
        controller1 = PIDFController(p1, i1, d1, f1)
        controller1.setSetPoint(TICKS_PER_SECOND_TARGET)
    }

    override fun init() {
        // FIX 1 (Revised): Initialize PanelsGamepad

        // Initialize shooter motors
        shooterMotor1 = hardwareMap.get(DcMotorEx::class.java, "sm1")
        shooterMotor2 = hardwareMap.get(DcMotorEx::class.java, "sm2")

        // Set motor directions
        shooterMotor1.direction = DcMotorSimple.Direction.REVERSE
        shooterMotor2.direction = DcMotorSimple.Direction.FORWARD

        // --- Encoder and Motor Mode Setup ---
        shooterMotor1.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        shooterMotor2.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        shooterMotor1.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        shooterMotor2.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        // Initialize the controller for the first time
        updatePIDController()

        // Initialize variables for the first loop() iteration
        timer.reset()
        lastPosition = shooterMotor1.currentPosition.toDouble()
        lastTime = timer.seconds()
    }

    override fun loop() {
        // Update the gamepad state

        // Re-create the controller if the coefficients have changed (e.g., via dashboard)
        updatePIDController()

        // --- Manual Velocity Calculation ---
        val currentPosition = shooterMotor1.currentPosition.toDouble()
        val currentTime = timer.seconds()
        val deltaPosition = currentPosition - lastPosition
        val deltaTime = currentTime - lastTime

        val currentVelocity1 = if (deltaTime > 0) {
            deltaPosition / deltaTime
        } else {
            0.0
        }

        lastPosition = currentPosition
        lastTime = currentTime

        // Calculate the required motor power
        val power1: Double = controller1.calculate(currentVelocity1)

        // Control motors with the gamepad
        if (gamepad1.right_bumper) {
            shooterMotor1.power = power1
            shooterMotor2.power = power1
        } else {
            shooterMotor1.power = 0.0
            shooterMotor2.power = 0.0
            controller1.reset()
        }

        // --- Telemetry for Debugging ---
        // The standard telemetry object is automatically handled by Panels.
        telemetry.addData("Target Ticks/Sec", TICKS_PER_SECOND_TARGET)
        telemetry.addData("Current Ticks/Sec", "%.2f".format(currentVelocity1))
        telemetry.addData("Calculated Power", "%.3f".format(power1))
        telemetry.addData("Loop Time (ms)", "%.2f".format(deltaTime * 1000))
        telemetry.update()
    }

    companion object {
        const val TICKS_PER_REV: Double = 28.0
        const val GEAR_RATIO: Double = 1.0
        const val RPM_TARGET: Double = 5000.0
        val TICKS_PER_SECOND_TARGET: Double = (RPM_TARGET / 60.0) * TICKS_PER_REV * GEAR_RATIO
    }
}
