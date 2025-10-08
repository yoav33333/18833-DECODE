package org.firstinspires.ftc.teamcode.subsystems.shooter

import dev.nextftc.hardware.impl.MotorEx

object ShooterHardware {
    val shooterMotor1 by lazy { MotorEx("sm1") }
    val shooterMotor2 by lazy { MotorEx("sm2").reversed() }

    fun setPower(power: Double){
        shooterMotor1.power = power
        shooterMotor2.power = power
    }
}