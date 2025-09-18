package org.firstinspires.ftc.teamcode.p2p.drive

import com.qualcomm.robotcore.hardware.DcMotor
import dev.nextftc.core.components.Component
import dev.nextftc.ftc.ActiveOpMode.gamepad1
import dev.nextftc.hardware.impl.Direction
import dev.nextftc.hardware.impl.IMUEx
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.teamcode.p2p.drive.DriveVariables.xMul
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

object Drive : Component {
    private val motors = arrayOf(
        MotorEx("lf"),
        MotorEx("rf"),
        MotorEx("lr"),
        MotorEx("rr"),
    )
    private val powers = arrayOf(0.0, 0.0, 0.0, 0.0)
    private val imu = IMUEx("imu", Direction.DOWN, Direction.BACKWARD).zeroed()


    override fun preInit() {
        for (motor in motors) {
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        }
        motors[Motor.FRONT_LEFT.index].reverse()
        motors[Motor.BACK_LEFT.index].reverse()
    }

    fun setDrivePower(fl: Double, fr: Double, bl: Double, br: Double) {
        powers[Motor.FRONT_LEFT.index] = fl
        powers[Motor.FRONT_RIGHT.index] = fr
        powers[Motor.BACK_LEFT.index] = bl
        powers[Motor.BACK_RIGHT.index] = br
        setDrivePower()
    }
    fun setDrivePower(powers: Array<Double>) {
        setDrivePower(powers[0], powers[1], powers[2], powers[3])
    }
    fun setDrivePower() {
        for (i in motors.indices) {
            motors[i].power = powers[i]*DriveVariables.maxSpeed
        }
    }
    fun stop() {
        setDrivePower(0.0, 0.0, 0.0, 0.0)
    }
    fun setHeading(angle: Double) {
        imu.zero()
        DriveVariables.imuOffset = angle
    }
    fun getHeading(): Double {
        return Math.toRadians(imu.get().inDeg + DriveVariables.imuOffset)
    }

    override fun postUpdate() {
        run()
    }
    fun run() {
        if (!DriveVariables.auto) {
            driveByVectors(
                gamepad1.left_stick_x.toDouble(),
                gamepad1.left_stick_y.toDouble(),
                gamepad1.right_stick_x.toDouble())
        }
    }

    fun driveByVectors(x: Double,y: Double, rx: Double) {
        var rotX: Double = x
        var rotY: Double = y
        if(DriveVariables.fieldOriented || DriveVariables.auto) {
            var botHeading: Double = getHeading()
            rotX = x * cos(-botHeading) - y * sin(-botHeading)
            rotY = x * sin(-botHeading) + y * cos(-botHeading)
        }
        rotX = rotX * xMul
        val denominator: Double = (abs(rotY) + abs(rotX) + abs(rx)).coerceAtLeast(1.0)
        val fl: Double = (rotY + rotX + rx) / denominator
        val bl: Double = (rotY - rotX + rx) / denominator
        val fr: Double = (rotY - rotX - rx) / denominator
        val br: Double = (rotY + rotX - rx) / denominator

        setDrivePower(fl, fr, bl, br)
    }

    enum class Motor(val index: Int) {
        FRONT_LEFT(0),
        FRONT_RIGHT(1),
        BACK_LEFT(2),
        BACK_RIGHT(3)
    }
}
