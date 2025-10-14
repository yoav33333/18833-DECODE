package org.firstinspires.ftc.teamcode.opModes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor

@TeleOp(name = "AprilTag Stream FIXED (Guaranteed View)", group = "Vision")
class AprilTagStreamFixed : LinearOpMode() {

    private lateinit var visionPortal: VisionPortal
    private lateinit var tagProcessor: AprilTagProcessor
    private lateinit var servo: Servo

    override fun runOpMode() {
        telemetry.addLine("Initializing webcam and VisionPortal...")
        telemetry.update()

        // --- הגדרת הסרוו ---
        servo = hardwareMap.get(Servo::class.java, "camera_servo")
        servo.position = 0.5

        // --- הגדרת המצלמה ---
        val webcam = hardwareMap.get(WebcamName::class.java, "Webcam")

        // --- הגדרת זיהוי AprilTag ---
        tagProcessor = AprilTagProcessor.Builder()
            .setDrawTagOutline(true)
            .setDrawAxes(true)
            .build()

        // --- הגדרת VisionPortal ---
        visionPortal = VisionPortal.Builder()
            .setCamera(webcam)
            .addProcessor(tagProcessor)
            .setStreamFormat(VisionPortal.StreamFormat.YUY2) // עובד טוב על רוב ה־Hubs
            .enableLiveView(true)
            .setAutoStopLiveView(false)
            .build()

        // נסה לוודא שהזרם נפתח
        telemetry.addLine("Camera initialized. Starting preview...")
        telemetry.update()

        // התחלת הזרם
        visionPortal.resumeStreaming()
        visionPortal.resumeLiveView()

        // המתנה להתחלה
        waitForStart()

        while (opModeIsActive()) {
            val detections = tagProcessor.detections
            if (detections.isNotEmpty()) {
                val tag = detections[0]
                val x = tag.center.x
                val error = (x - 320) / 320.0
                val servoPos = (0.5 - error * 0.3).coerceIn(0.0, 1.0)
                servo.position = servoPos

                telemetry.addData("Detected ID", tag.id)
                telemetry.addData("Tag X", "%.1f", x)
                telemetry.addData("Servo Position", "%.2f", servo.position)
            } else {
                telemetry.addLine("No tag detected")
            }

            telemetry.addData("Stream Active", visionPortal)
            telemetry.update()
            sleep(50)
        }

        visionPortal.stopStreaming()
        visionPortal.close()
    }
}
