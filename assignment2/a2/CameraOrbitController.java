package a2;

import tage.*;
import tage.input.InputManager;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event;
import java.lang.Math;
import org.joml.*;

public class CameraOrbitController
{ 
    private Engine engine;
    private Camera camera; // the camera being controlled
    private GameObject avatar; // the target avatar the camera looks at
    private float cameraAzimuth; // rotation around target Y axis
    private float cameraElevation; // elevation of camera above target
    private float cameraRadius; // distance between camera and target

    public CameraOrbitController(Camera cam, GameObject av, String gpName, Engine e)
    { 
        engine = e;
        camera = cam;
        avatar = av;
        cameraAzimuth = 0.0f; // start BEHIND and ABOVE the target
        cameraElevation = 20.0f; // elevation is in degrees
        cameraRadius = 2.0f; // distance from camera to avatar
        setupInputs(gpName);
        updateCameraPosition();
    } 

    private void setupInputs(String gp)
    { 
        OrbitAzimuthAction azmAction = new OrbitAzimuthAction();
        OrbitElevationAction elvAction = new OrbitElevationAction();
        OrbitRadiusAction rdsAction = new OrbitRadiusAction();

        kbAzimuthUp kbAziUp = new kbAzimuthUp();
        kbAzimuthDown kbAziDown = new kbAzimuthDown();
        kbElevateUp kbElvUp = new kbElevateUp();
        kbElevateDown kbElvDown = new kbElevateDown();
        kbRadiusUp kbRdsUp = new kbRadiusUp();
        kbRadiusDown kbRdsDown = new kbRadiusDown();
        InputManager im = engine.getInputManager();

        if(gp != null) {
            im.associateAction(gp, net.java.games.input.Component.Identifier.Axis.RX, azmAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            im.associateAction(gp, net.java.games.input.Component.Identifier.Axis.RY, elvAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            im.associateAction(gp, net.java.games.input.Component.Identifier.Axis.Y, rdsAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        }

        im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.PAGEUP, kbRdsDown, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.PAGEDOWN, kbRdsUp, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

        im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.UP, kbElvUp, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.LEFT, kbAziDown, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.DOWN, kbElvDown, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.RIGHT, kbAziUp, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

    }

    // Compute the camera’s azimuth, elevation, and distance, relative to
    // the target in spherical coordinates, then convert to world Cartesian
    // coordinates and set the camera position from that.
    public void updateCameraPosition()
    { 
        Vector3f avatarRot = avatar.getWorldForwardVector();
        double avatarAngle = Math.toDegrees((double)
        avatarRot.angleSigned(new Vector3f(0,0,-1), new Vector3f(0,1,0)));
        float totalAz = cameraAzimuth - (float)avatarAngle;
        double theta = Math.toRadians(cameraAzimuth);
        double phi = Math.toRadians(cameraElevation);
        float x = cameraRadius * (float)(Math.cos(phi) * Math.sin(theta));
        float y = cameraRadius * (float)(Math.sin(phi));
        float z = cameraRadius * (float)(Math.cos(phi) * Math.cos(theta));
        camera.setLocation(new Vector3f(x,y,z).add(avatar.getWorldLocation()));
        camera.lookAt(avatar);
    }

    private class OrbitAzimuthAction extends AbstractInputAction
    { 
        public void performAction(float time, Event event)
        { 
            float rotAmount;
            if (event.getValue() < -0.2)
            { rotAmount=-0.4f; }
            else if (event.getValue() > 0.2)
            { rotAmount=0.4f; }
            else
            { rotAmount=0.0f; }

        cameraAzimuth += rotAmount;
        cameraAzimuth = cameraAzimuth % 360;
        updateCameraPosition();
        }
    }
    private class OrbitElevationAction extends AbstractInputAction
    {
        public void performAction(float time, Event event)
        { 
            float elvAmount;
            if (event.getValue() < -0.2)
            { elvAmount=0.4f; }
            else if (event.getValue() > 0.2)
            { elvAmount=-0.4f; }
            else
            { elvAmount=0.0f; }

            if(cameraElevation + elvAmount > 0.0f && cameraElevation + elvAmount < 80.0f)
            { cameraElevation += elvAmount; }
            cameraElevation = cameraElevation % 360;
            updateCameraPosition();
        }
    }
    private class OrbitRadiusAction extends AbstractInputAction
    {
        public void performAction(float time, Event event)
        { 
            float rdsAmount;
            if (event.getValue() < -0.2)
            { rdsAmount=-0.2f; }
            else if (event.getValue() > 0.2)
            { rdsAmount=0.2f; }
            else
            { rdsAmount=0.0f; }

            if(cameraRadius + rdsAmount > 2.0f && cameraRadius + rdsAmount < 20.0f)
            { cameraRadius += rdsAmount; }
            updateCameraPosition();
        }
    }

    private class kbRadiusUp extends AbstractInputAction
    {
        public void performAction(float time, Event event)
        { 
            if(cameraRadius + 0.2 > 2.0f)
                cameraRadius += 0.2;
            updateCameraPosition();
        }
    }
    private class kbRadiusDown extends AbstractInputAction
    {
        public void performAction(float time, Event event)
        { 
            if(cameraRadius - 0.2 > 2.0f)
                cameraRadius += -0.2;
            updateCameraPosition();
        }
    }
    private class kbElevateUp extends AbstractInputAction
    {
        public void performAction(float time, Event event)
        { 
            if(cameraElevation + 0.2 < 80.0f)
                cameraElevation += 0.2;
            cameraElevation = cameraElevation % 360;
            updateCameraPosition();
        }
    }
    private class kbElevateDown extends AbstractInputAction
    {
        public void performAction(float time, Event event)
        { 
            if(cameraElevation - 0.2 > 0.0f)
                cameraElevation += -0.2;
            cameraElevation = cameraElevation % 360;
            updateCameraPosition();
        }
    }
    private class kbAzimuthUp extends AbstractInputAction
    {
        public void performAction(float time, Event event)
        { 
            cameraAzimuth += 0.2;
            cameraAzimuth = cameraAzimuth % 360;
            updateCameraPosition();
        }
    }
    private class kbAzimuthDown extends AbstractInputAction
    {
        public void performAction(float time, Event event)
        { 
            cameraAzimuth += -0.2;
            cameraAzimuth = cameraAzimuth % 360;
            updateCameraPosition();
        }
    }
}