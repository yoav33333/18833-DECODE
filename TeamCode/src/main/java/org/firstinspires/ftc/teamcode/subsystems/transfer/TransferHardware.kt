package org.firstinspires.ftc.teamcode.subsystems.transfer

import dev.nextftc.hardware.impl.MotorEx
import dev.nextftc.hardware.impl.ServoEx

object TransferHardware {
    val pusServo by lazy { ServoEx("ps") }
    fun setPower(power: Double){
        pusServo.position = power
    }
}