package org.firstinspires.ftc.teamcode.subsystems.shooter

import dev.nextftc.core.commands.utility.LambdaCommand

object ShooterCommands {
    val shoot = LambdaCommand()
        .setStart { ShooterHardware.setPower(0.7) }
        .setStop{ ShooterHardware.setPower(0.0) }
        .setIsDone { false }
}