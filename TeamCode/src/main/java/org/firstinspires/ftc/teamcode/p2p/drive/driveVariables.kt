package org.firstinspires.ftc.teamcode.p2p.drive

import com.bylazar.configurables.annotations.Configurable

@Configurable
object DriveVariables {
    val frontLeft = "lf"
    val frontRight = "lr"
    val backLeft = "rf"
    val backRight = "rr"
    val imu = "imu"
    @JvmField var xMul = 1.1;
    @JvmField var fieldOriented = false
    @JvmField var auto = false
    @JvmField var maxSpeed = 1.0


    fun setXMultiplier(xMultiplier: Double) : DriveVariables{
        return this
    }
    fun setFieldOriented(fieldOriented: Boolean) : DriveVariables{
        return this
    }
    fun setAuto(auto: Boolean) : DriveVariables{
        return this
    }
    fun setMaxSpeed(maxSpeed: Double) : DriveVariables{
        return this
    }


}