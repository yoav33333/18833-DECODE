package org.firstinspires.ftc.teamcode.subsystems.intake

import com.qualcomm.robotcore.hardware.DcMotor
import dev.nextftc.core.components.Component
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.teamcode.p2p.drive.Drive
import kotlin.getValue

object IntakeHardware: Component {
    val intakeMotor by lazy { MotorEx("vm") }
    val transferMotor by lazy { MotorEx("tm") }

    override fun preInit() {
        intakeMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        transferMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    fun setPower(power: Double) {
        intakeMotor.power = power
        transferMotor.power = power
    }
    fun stop() {
        setPower(0.0)
    }
    fun intake() {
        setPower(1.0)
    }
    fun outtake() {
        setPower(-1.0)
    }
}