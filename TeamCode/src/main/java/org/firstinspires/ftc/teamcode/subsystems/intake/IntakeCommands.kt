package org.firstinspires.ftc.teamcode.subsystems.intake

import dev.nextftc.core.commands.utility.LambdaCommand

object IntakeCommands {
    val intake = LambdaCommand()
        .setStart { IntakeHardware.intake() }
        .setStop {i->
            if (!i) IntakeHardware.stop()
        }
        .setIsDone { false }
        .setRequirements(IntakeHardware)


    val outtake = LambdaCommand()
        .setStart { IntakeHardware.outtake() }
        .setStop {i->
            if (!i) IntakeHardware.stop()
        }
        .setIsDone { false }
        .setRequirements(IntakeHardware)
    val stop = LambdaCommand()
        .setStart {
            IntakeHardware.stop()
        }
}