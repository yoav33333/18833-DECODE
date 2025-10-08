package org.firstinspires.ftc.teamcode.subsystems.transfer

import dev.nextftc.hardware.impl.MotorEx
import dev.nextftc.hardware.impl.ServoEx

object TransferHardware {
    val transferServo by lazy { ServoEx("ps") }
    fun setPower(power: Double){
        transferServo.position = power
    }
}