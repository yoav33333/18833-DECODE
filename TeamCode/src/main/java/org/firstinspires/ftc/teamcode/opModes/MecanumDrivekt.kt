package org.firstinspires.ftc.teamcode.opModes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.nextftc.bindings.BindingManager
import dev.nextftc.bindings.button
import dev.nextftc.core.commands.groups.ParallelGroup
import dev.nextftc.ftc.NextFTCOpMode
import org.firstinspires.ftc.teamcode.p2p.drive.Drive
import org.firstinspires.ftc.teamcode.p2p.drive.DriveVariables.maxSpeed
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeCommands.intake
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeCommands.outtake
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterCommands.shoot
import org.firstinspires.ftc.teamcode.subsystems.transfer.TransferCommands.push

@TeleOp
class MecanumDrivekt: NextFTCOpMode() {
    init {
        addComponents(Drive)
        button { gamepad1.left_trigger>0.2 ||gamepad1.left_bumper}
            .whenTrue (intake )
            .whenBecomesFalse { intake.cancel() }
        button { gamepad1.y }
            .whenTrue ( outtake )
            .whenBecomesFalse { intake.cancel() }
        button { gamepad1.left_bumper }
            .whenTrue  ( push)
            .whenBecomesFalse { push.cancel() }

        button { gamepad1.right_trigger>0.2 }
            .whenTrue (shoot)

        button { gamepad1.right_bumper }
            .whenTrue { Drive.setHeading(0.0) }
    }

    override fun onUpdate() {
        BindingManager.update()

        telemetry.addData("heading",Drive.getHeading())
        telemetry.addData("heading",Drive.imu.get())
        telemetry.update()
    }

    override fun onStop() {
        BindingManager.reset()
    }
}