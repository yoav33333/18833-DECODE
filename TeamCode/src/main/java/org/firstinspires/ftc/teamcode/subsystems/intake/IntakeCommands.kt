package org.firstinspires.ftc.teamcode.subsystems.intake

import dev.nextftc.core.commands.utility.LambdaCommand

object IntakeCommands {
    val intake = LambdaCommand()
        .setUpdate { IntakeHardware.intake() }
        .setStop {
            IntakeHardware.stop()
        }
        .setRequirements(IntakeHardware)


    val outtake = LambdaCommand()
        .setUpdate {
            IntakeHardware.outtake()
        }
        .setStop{
            IntakeHardware.stop()
        }
        .setRequirements(IntakeHardware)
    val stop = LambdaCommand()
        .setStart {
            IntakeHardware.stop()
        }
}