package a2;

import tage.*;
import tage.input.InputManager;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event;
import java.lang.Math;
import org.joml.*;

public class CameraOverheadController
{ 
    private Engine engine;
    private Camera camera; // the camera being controlled
    private float cameraRadius; // distance between camera and target
    private float cameraNewX;
    private float cameraNewZ;

    public CameraOverheadController(Camera lrCam, Engine e)
    { 
        engine = e;
        camera = lrCam;
        cameraRadius = 2.0f; // distance from camera to avatar
        cameraNewX = 0.0f;
        cameraNewZ = 0.0f;
        setupInputs();
        updateCameraPosition();
    } 

    private void setupInputs()
    {
        TranslateRadiusPos transRadPos = new TranslateRadiusPos();
        TranslateRadiusNeg transRadNeg = new TranslateRadiusNeg();
        TranslateXPos transXPos = new TranslateXPos();
        TranslateXNeg transXNeg = new TranslateXNeg();
        TranslateZPos transZPos = new TranslateZPos();
        TranslateZNeg transZNeg = new TranslateZNeg();
        InputManager im = engine.getInputManager();

        im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.O, transRadPos, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.U, transRadNeg, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

        im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.I, transZNeg, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.J, transXNeg, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.K, transZPos, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.L, transXPos, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    }

    // Compute the camera’s azimuth, elevation, and distance, relative to
    // the target in spherical coordinates, then convert to world Cartesian
    // coordinates and set the camera position from that.
    public void updateCameraPosition()
    { 
        float x = cameraNewX;
        float y = cameraRadius;
        float z = cameraNewZ;
        camera.setLocation(new Vector3f(x,y,z));
    }



    private class TranslateRadiusPos extends AbstractInputAction    //zoom out - key U
    {
        public void performAction(float time, Event event)
        {
            cameraRadius += 0.1;
            updateCameraPosition();
        }
    }
    private class TranslateRadiusNeg extends AbstractInputAction        //zoom in - key O
    {
        public void performAction(float time, Event event)
        {
            cameraRadius += -0.1;
            updateCameraPosition();
        }
    }
    private class TranslateXPos extends AbstractInputAction     //right dir - key L
    {
        public void performAction(float time, Event event)
        { 
            cameraNewX += 0.1;
            updateCameraPosition();

        }
    }
    private class TranslateXNeg extends AbstractInputAction     //left dir - key J
    {
        public void performAction(float time, Event event)
        { 
            cameraNewX += -0.1;
            updateCameraPosition();

        }
    }
    private class TranslateZPos extends AbstractInputAction     //down dir - key K
    {
        public void performAction(float time, Event event)
        { 
            cameraNewZ += 0.1;
            updateCameraPosition();
        }
    }
    private class TranslateZNeg extends AbstractInputAction     //up dir - key I
    {
        public void performAction(float time, Event event)
        { 
            cameraNewZ += -0.1;
            updateCameraPosition();
        }
    }
}