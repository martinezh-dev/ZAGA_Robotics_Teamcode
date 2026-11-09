package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Camera
{
    private AprilTagLibrary bioBuzzTags;
    private VisionPortal webcam = null;

    private AprilTagProcessor aprilTag = null;

    private AprilTagDetection detected = null;

    public void init(HardwareMap hardwareMap)
    {
        bioBuzzTags = new AprilTagLibrary.Builder()
                .addTag(34, "red1", 2, DistanceUnit.INCH)
                .addTag(32, "red2", 2, DistanceUnit.INCH)
                .addTag(35, "red3", 2, DistanceUnit.INCH)
                .addTag(36, "red4", 2, DistanceUnit.INCH)
                .addTag(37, "red4", 2, DistanceUnit.INCH)
                .build();
        aprilTag = new AprilTagProcessor.Builder()
                .setDrawTagOutline(true)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(bioBuzzTags)
                .build();
        webcam = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .addProcessor(aprilTag)
                .build();

    //VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);
    }

    public List<AprilTagDetection> getTags()
    {
        return aprilTag.getDetections();
    }

}