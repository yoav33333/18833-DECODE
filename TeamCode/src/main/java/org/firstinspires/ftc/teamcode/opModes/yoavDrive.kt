package org.firstinspires.ftc.teamcode.opModes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.nextftc.bindings.BindingManager
import dev.nextftc.bindings.button
import dev.nextftc.ftc.NextFTCOpMode
import org.firstinspires.ftc.teamcode.p2p.drive.Drive
import org.firstinspires.ftc.teamcode.p2p.drive.DriveVariables.maxSpeed
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeCommands.intake
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeCommands.outtake

@TeleOp
class YoavDrive: NextFTCOpMode() {
    init {
        addComponents(Drive)
        button { gamepad1.a }
            .whenTrue { intake }
        button { gamepad1.y }
            .whenFalse { outtake }
        button { gamepad1.left_bumper }
            .whenBecomesTrue { maxSpeed = if (maxSpeed == 1.0) 0.4 else 1.0 }
        button { gamepad1.right_bumper }
            .whenTrue { Drive.setHeading(0.0) }
    }

    override fun onUpdate() {
        BindingManager.update()
    }

    override fun onStop() {
        BindingManager.reset()
    }
}