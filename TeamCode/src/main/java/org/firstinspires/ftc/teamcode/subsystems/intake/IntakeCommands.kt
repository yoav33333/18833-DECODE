package org.firstinspires.ftc.teamcode.subsystems.intake

import dev.nextftc.core.commands.utility.LambdaCommand

object IntakeCommands {
    val intake = LambdaCommand()
        .setStart {
            IntakeHardware.run()
        }
        .setStop {interrupted ->
            if (!interrupted){
                IntakeHardware.stop()
            }
        }
        .setRequirements( IntakeHardware)


    val outtake = LambdaCommand()
        .setStart {
            IntakeHardware.outtake()
        }
        .setStop {interrupted ->
            if (!interrupted){
                IntakeHardware.stop()
            }
        }
        .setRequirements(IntakeHardware)
    val stop = LambdaCommand()
        .setStart {
            IntakeHardware.stop()
        }
}