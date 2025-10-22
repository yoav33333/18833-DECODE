package org.firstinspires.ftc.teamcode.subsystems.transfer

import dev.nextftc.core.commands.utility.LambdaCommand

object TransferCommands {
    val push = LambdaCommand()
        .setStart { TransferHardware.push() }
//        .setIsDone { false }
        .setStop { TransferHardware.stop() }
}