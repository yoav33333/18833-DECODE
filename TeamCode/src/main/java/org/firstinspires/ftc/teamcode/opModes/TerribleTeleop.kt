package org.firstinspires.ftc.teamcode.opModes//package org.firstinspires.ftc.teamcode.opModes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.frozenmilk.dairy.core.util.supplier.logical.EnhancedBooleanSupplier
import dev.frozenmilk.mercurial.Mercurial
import dev.frozenmilk.mercurial.bindings.BoundBooleanSupplier
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveCommands.resetHeading
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveCommands.runLowGear

import kotlin.math.abs
@TeleOp
class TerribleTeleop: NewMegiddoOpMode() {
    override fun myInit() {
        /*operator controls*/

        /*drive controls*/
        BoundBooleanSupplier(EnhancedBooleanSupplier{Mercurial.gamepad1.rightTrigger.state > 0.2})
            .whileTrue(runLowGear)
        Mercurial.gamepad1.dpadUp.onTrue( resetHeading)
        Mercurial.gamepad1.leftBumper.onTrue( resetHeading)
    }
}