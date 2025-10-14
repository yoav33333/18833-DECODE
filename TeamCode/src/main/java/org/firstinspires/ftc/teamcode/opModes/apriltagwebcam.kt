package org.firstinspires.ftc.teamcode.opModes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName

@TeleOp(name = "AprilTag Webcam Servo + Stream", group = "Vision")
class apriltagwebcam : LinearOpMode() {

    private lateinit var visionPortal: VisionPortal
    private lateinit var tagProcessor: AprilTagProcessor
    private lateinit var servo: Servo

    override fun runOpMode() {
        // Initialize servo
        servo = hardwareMap.get(Servo::class.java, "camera_servo")
        servo.position = 0.5 // start centered

        // Initialize webcam
        val webcam = hardwareMap.get(WebcamName::class.java, "Webcam")

        // Create AprilTag processor
        tagProcessor = AprilTagProcessor.Builder()
            .setDrawTagOutline(true)
            .build()

        // Build VisionPortal (this automatically starts camera streaming)
        visionPortal = VisionPortal.Builder()
            .setCamera(webcam)
            .addProcessor(tagProcessor)
            .setStreamFormat(VisionPortal.StreamFormat.MJPEG) // ensures video display on Hub
            .enableLiveView(true) // show live camera view on Driver Hub
            .build()

        telemetry.addLine("Initialized - press PLAY")
        telemetry.update()

        waitForStart()

        // Make sure live view is on when we start
        visionPortal.setProcessorEnabled(tagProcessor, true)
        visionPortal.resumeLiveView()

        while (opModeIsActive()) {
            val detections = tagProcessor.detections

            if (detections.isNotEmpty()) {
                val tag: AprilTagDetection = detections[0]
                val x = tag.center.x
                val screenCenter = 320.0

                val error = (x - screenCenter) / screenCenter
                val servoPos = 0.5 - error * 0.3
                servo.position = servoPos.coerceIn(0.0, 1.0)

                telemetry.addData("Tag ID", tag.id)
                telemetry.addData("Tag X", "%.1f", x)
                telemetry.addData("Servo Position", "%.2f", servo.position)
            } else {
                telemetry.addLine("No tag detected")
            }

            telemetry.update()
            sleep(50)
        }

        visionPortal.stopStreaming()
        visionPortal.close()
    }
}
