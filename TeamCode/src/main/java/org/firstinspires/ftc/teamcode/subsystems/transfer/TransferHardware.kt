package org.firstinspires.ftc.teamcode.subsystems.transfer

import dev.nextftc.hardware.impl.ServoEx

object TransferHardware {
    val pushServo by lazy { ServoEx("ps") }
    val gateServo by lazy { ServoEx("gs") }
    fun setPower(power: Double){
        pushServo.position = power
    }
    fun push(){
        setPower(0.0)
        gateServo.position = 1.0
    }
    fun stop(){
        setPower(0.5)
        gateServo.position = 0.0
    }
}