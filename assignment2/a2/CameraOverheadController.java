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
    private GameObject avatar; // the target avatar the camera looks at
    private float cameraAzimuth; // rotation around target Y axis
    private float cameraElevation; // elevation of camera above target
    private float cameraRadius; // distance between camera and target

    public CameraOverheadController(Camera cam, GameObject av, String gpName, Engine e)
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
        InputManager im = engine.getInputManager();

        if(gp != null) {
            im.associateAction(gp, net.java.games.input.Component.Identifier.Axis.RX, azmAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            im.associateAction(gp, net.java.games.input.Component.Identifier.Axis.RY, elvAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            im.associateAction(gp, net.java.games.input.Component.Identifier.Axis.Y, rdsAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        }
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
            { rotAmount=-0.3f; }
            else if (event.getValue() > 0.2)
            { rotAmount=0.3f; }
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
            { elvAmount=0.3f; }
            else if (event.getValue() > 0.2)
            { elvAmount=-0.3f; }
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
            { rdsAmount=-0.1f; }
            else if (event.getValue() > 0.2)
            { rdsAmount=0.1f; }
            else
            { rdsAmount=0.0f; }

            if(cameraRadius + rdsAmount > 2.0f && cameraRadius + rdsAmount < 20.0f)
            { cameraRadius += rdsAmount; }
            updateCameraPosition();
        }
    }
    private class TranslateX extends AbstractInputAction
    {
        public void performAction(float time, Event event)
        { 
            float rdsAmount;
            if (event.getValue() < -0.2)
            { rdsAmount=-0.1f; }
            else if (event.getValue() > 0.2)
            { rdsAmount=0.1f; }
            else
            { rdsAmount=0.0f; }

            if(cameraRadius + rdsAmount > 2.0f && cameraRadius + rdsAmount < 20.0f)
            { cameraRadius += rdsAmount; }
            updateCameraPosition();
        }
    }
    private class TranslateZ extends AbstractInputAction
    {
        public void performAction(float time, Event event)
        { 
            float rdsAmount;
            if (event.getValue() < -0.2)
            { rdsAmount=-0.1f; }
            else if (event.getValue() > 0.2)
            { rdsAmount=0.1f; }
            else
            { rdsAmount=0.0f; }

            if(cameraRadius + rdsAmount > 2.0f && cameraRadius + rdsAmount < 20.0f)
            { cameraRadius += rdsAmount; }
            updateCameraPosition();
        }
    }
}