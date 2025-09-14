package org.firstinspires.ftc.teamcode.util

import dev.frozenmilk.dairy.core.FeatureRegistrar

class HardwareDevice<T>(name: String, val type: Class<T>){
    private val device =
        FeatureRegistrar.activeOpMode.hardwareMap.get(type, name)

    fun get(): T {
        return device
    }
}