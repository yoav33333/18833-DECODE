package org.firstinspires.ftc.teamcode.subsystems.transfer

import dev.nextftc.core.commands.utility.LambdaCommand

object TransferCommands {
    val push = LambdaCommand()
        .setStart { TransferHardware.setPower(1.0) }
        .setIsDone { false }
        .setStop { TransferHardware.setPower(0.5) }
}