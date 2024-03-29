package a1;

import tage.*;
import tage.shapes.*;
import tage.input.*;
import tage.input.action.*;
import net.java.games.input.*;
import net.java.games.input.Component.Identifier.*;

import java.lang.Math;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import org.joml.*;

public class MyGame extends VariableFrameRateGame
{
	private static Engine engine;
	private InputManager im;

	private int score=0, choc=0, carry=0;
	private double lastFrameTime, currFrameTime, elapsedTime;

	private DolphinView rideDolphin;
	private GameObject dol, cub, sph1, sph2, sph3, sph4, sph5, choc1, choc2, choc3, bigsph, x, y, z;
	private ObjShape dolS, cubS, sphS, chocS, linxS, linyS, linzS;;
	private TextureImage doltx, brick, ball, choco, bigball;
	private Light light1;

	private Vector3f loc, fwd, up, right, lastCam;
	private Camera cam;


	public MyGame() { super(); }

	public static void main(String[] args)
	{	MyGame game = new MyGame();
		engine = new Engine(game);
		game.initializeSystem();
		game.game_loop();
	}

	@Override
	public void loadShapes()
	{	dolS = new ImportedModel("dolphinHighPoly.obj");
		sphS = new Sphere();
		cubS = new Cube();
		chocS = new ManualObj();
		linxS = new Line(new Vector3f(0f,0f,0f), new Vector3f(3f,0f,0f)); 
		linyS = new Line(new Vector3f(0f,0f,0f), new Vector3f(0f,3f,0f)); 
		linzS = new Line(new Vector3f(0f,0f,0f), new Vector3f(0f,0f,-3f));
	}

	@Override
	public void loadTextures()
	{	doltx = new TextureImage("Dolphin_HighPolyUV.png");
		brick = new TextureImage("brick.jpg");
		ball = new TextureImage("ball.png");
		choco = new TextureImage("choco.png");
		bigball = new TextureImage("bigball.png");
	}

	@Override
	public void buildObjects()
	{	Matrix4f initialTranslation, initialScale, initialRotation;
		Random rand = new Random();

		//build dolphin in the center of the window 
		dol = new GameObject(GameObject.root(), dolS, doltx);
		initialTranslation = (new Matrix4f()).translation(0,0,0);
		initialScale = (new Matrix4f()).scaling(3.0f);
		initialRotation = (new Matrix4f()).rotationY((float)java.lang.Math.toRadians(135.0f)); 
		dol.setLocalTranslation(initialTranslation);
		dol.setLocalScale(initialScale);
  		dol.setLocalRotation(initialRotation); 

		
		//build sphere as prize 1
		sph1 = new GameObject(GameObject.root(), sphS, ball);
		initialTranslation = (new Matrix4f()).translation(1 + rand.nextInt(6), 1 + rand.nextInt(6), rand.nextInt(6) + (-rand.nextInt(6))); 
		initialScale = (new Matrix4f()).scaling(0.3f); 
		sph1.setLocalTranslation(initialTranslation); 
		sph1.setLocalScale(initialScale); 
		
		//build sphere as prize 2
		sph2 = new GameObject(GameObject.root(), sphS, ball);
		initialTranslation = (new Matrix4f()).translation(1 + rand.nextInt(6), 1 + rand.nextInt(6), rand.nextInt(6) + (-rand.nextInt(6))); 
		initialScale = (new Matrix4f()).scaling(0.3f); 
		sph2.setLocalTranslation(initialTranslation); 
		sph2.setLocalScale(initialScale); 

		//build sphere as prize 3
		sph3 = new GameObject(GameObject.root(), sphS, ball);
		initialTranslation = (new Matrix4f()).translation(1 + rand.nextInt(6), 1 + rand.nextInt(6), rand.nextInt(6) + (-rand.nextInt(6))); 
		initialScale = (new Matrix4f()).scaling(0.3f); 
		sph3.setLocalTranslation(initialTranslation); 
		sph3.setLocalScale(initialScale); 

		//build sphere as prize 4
		sph4 = new GameObject(GameObject.root(), sphS, ball);
		initialTranslation = (new Matrix4f()).translation(1 + rand.nextInt(6), 1 + rand.nextInt(6), rand.nextInt(6) + (-rand.nextInt(6))); 
		initialScale = (new Matrix4f()).scaling(0.3f); 
		sph4.setLocalTranslation(initialTranslation); 
		sph4.setLocalScale(initialScale); 

		//build sphere as prize 5
		sph5 = new GameObject(GameObject.root(), sphS, ball);
		initialTranslation = (new Matrix4f()).translation(1 + rand.nextInt(6), 1 + rand.nextInt(6), rand.nextInt(6) + (-rand.nextInt(6))); 
		initialScale = (new Matrix4f()).scaling(0.3f); 
		sph5.setLocalTranslation(initialTranslation); 
		sph5.setLocalScale(initialScale); 
		
		//build big sphere that eats the prizes for score
		bigsph = new GameObject(GameObject.root(), sphS, bigball);
		initialTranslation = (new Matrix4f()).translation(5, 2, 5); 
		initialScale = (new Matrix4f()).scaling(1.0f); 
		bigsph.setLocalTranslation(initialTranslation); 
		bigsph.setLocalScale(initialScale); 

		//build manual rectangle object shape as chocolate 1
		choc1 = new GameObject(GameObject.root(), chocS, choco);
		initialTranslation = (new Matrix4f()).translation(1 + rand.nextInt(5), 1 + rand.nextInt(5), rand.nextInt(5) + (-rand.nextInt(5))); 
		initialScale = (new Matrix4f()).scaling(0.1f); 
		choc1.setLocalTranslation(initialTranslation); 
		choc1.setLocalScale(initialScale); 

		//build manual rectangle object shape as chocolate 2
		choc2 = new GameObject(GameObject.root(), chocS, choco);
		initialTranslation = (new Matrix4f()).translation(1 + rand.nextInt(5), 1 + rand.nextInt(5), rand.nextInt(5) + (-rand.nextInt(5))); 
		initialScale = (new Matrix4f()).scaling(0.1f); 
		choc2.setLocalTranslation(initialTranslation); 
		choc2.setLocalScale(initialScale); 

		//build manual rectangle object shape as chocolate 3
		choc3 = new GameObject(GameObject.root(), chocS, choco);
		initialTranslation = (new Matrix4f()).translation(1 + rand.nextInt(5), 1 + rand.nextInt(5), rand.nextInt(5) + (-rand.nextInt(5))); 
		initialScale = (new Matrix4f()).scaling(0.1f); 
		choc3.setLocalTranslation(initialTranslation); 
		choc3.setLocalScale(initialScale); 

		//build cube at the right of the window 
		cub = new GameObject(GameObject.root(), cubS, brick); 
		initialTranslation = (new Matrix4f()).translation(15,0,0); 
		initialScale = (new Matrix4f()).scaling(3.0f); 
		cub.setLocalTranslation(initialTranslation); 
		cub.setLocalScale(initialScale); 

		// add X,Y,Z axes 
		x = new GameObject(GameObject.root(), linxS); 
		y = new GameObject(GameObject.root(), linyS); 
		z = new GameObject(GameObject.root(), linzS); 
		(x.getRenderStates()).setColor(new Vector3f(1f,0f,0f)); 
		(y.getRenderStates()).setColor(new Vector3f(0f,1f,0f)); 
		(z.getRenderStates()).setColor(new Vector3f(0f,0f,1f)); 

	}

	@Override
	public void initializeLights()
	{	Light.setGlobalAmbient(0.5f, 0.5f, 0.5f);
		light1 = new Light();
		light1.setLocation(new Vector3f(5.0f, 4.0f, 2.0f));
		light1.setAmbient(1.0f, 0.6f, 0.0f);
		light1.setDiffuse(0.2f, 0.2f, 0.2f);
		light1.setSpecular(0.2f, 0.2f, 0.2f);
		(engine.getSceneGraph()).addLight(light1);
	}

	@Override
	public void initializeGame()
	{	lastFrameTime = System.currentTimeMillis();
		currFrameTime = System.currentTimeMillis();
		elapsedTime = 0.0;
		(engine.getRenderSystem()).setWindowDimensions(1900,1000);

		// ------------- positioning the camera -------------
		(engine.getRenderSystem().getViewport("MAIN").getCamera()).setLocation(new Vector3f(0,0,5));
		positionCameraOffAvatar();
		lastCam = new Vector3f(cam.getLocation());
		// ----------------- INPUTS SECTION ----------------------------- 
		im = engine.getInputManager(); 

		rideDolphin = new DolphinView(this);

		ForwardAction fwdAction = new ForwardAction(this); 
		TurnLeftAction turnLeftAction = new TurnLeftAction(this); 
		BackwardAction bwdAction = new BackwardAction(this);
		TurnRightAction turnRightAction = new TurnRightAction(this);

		PitchUpAction pitchUpAction = new PitchUpAction(this);
		PitchDownAction pitchDownAction = new PitchDownAction(this);

		GamepadYaw padYaw = new GamepadYaw(this);
		GamepadMove padMove = new GamepadMove(this);
		GamepadPitch padPitch = new GamepadPitch(this);

		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.SPACE, rideDolphin, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY); 

		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.W, fwdAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN); 
		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.A, turnLeftAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN); 
		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.S, bwdAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN); 
		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.D, turnRightAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN); 

		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.UP, pitchUpAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN); 
		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.DOWN, pitchDownAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN); 

		im.associateActionWithAllGamepads(net.java.games.input.Component.Identifier.Key.Axis.X, padYaw, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN); 
		im.associateActionWithAllGamepads(net.java.games.input.Component.Identifier.Key.Axis.Y, padMove, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN); 
		im.associateActionWithAllGamepads(net.java.games.input.Component.Identifier.Key.Axis.RY, padPitch, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

		im.associateActionWithAllGamepads(net.java.games.input.Component.Identifier.Key.Button._2, rideDolphin, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
	}

	public Engine getEngine() {
		return engine;
	}

	public GameObject getAvatar() {
		return dol;
	}

	public Camera getCamera() {
		return cam;
	}

	public float getElapsedTime() {
		return (float)((currFrameTime - lastFrameTime) / 1000.0);
	}

	public boolean getDolphinView() {
		return rideDolphin.toggleRide();
	}

	public int getChoco() {
		return choc;
	}

	public boolean isCamAway() {
		return (cam.getLocation().x() >= 4 + dol.getWorldLocation().x() || cam.getLocation().x() <= -4 + dol.getWorldLocation().x()) 
			|| (cam.getLocation().z() >= 4 + dol.getWorldLocation().z() || cam.getLocation().z() <= -4 + dol.getWorldLocation().z()) 
			|| (cam.getLocation().y() >= 4 + dol.getWorldLocation().y() || cam.getLocation().y() <= -4 + dol.getWorldLocation().y());
	}

	public void positionCameraBehindAvatar()
	{
		cam = (engine.getRenderSystem().getViewport("MAIN").getCamera()); 
		loc = dol.getWorldLocation(); 
		fwd = dol.getWorldForwardVector(); 
		up = dol.getWorldUpVector(); 
		right = dol.getWorldRightVector(); 
		cam.setU(right); 
		cam.setV(up); 
		cam.setN(fwd); 
		cam.setLocation(loc.add(up.mul(1.3f)).add(fwd.mul(-1.8f)));
	}

	public void positionCameraOffAvatar()
	{
		cam = (engine.getRenderSystem().getViewport("MAIN").getCamera()); 
		loc = dol.getWorldLocation(); 
		fwd = dol.getWorldForwardVector(); 
		up = dol.getWorldUpVector(); 
		right = dol.getWorldRightVector(); 
		cam.setU(right); 
		cam.setV(up); 
		cam.setN(fwd); 
		cam.setLocation(loc.add(up.mul(0.7f)).add(right.mul(0.7f)).add(fwd.mul(-0.8f)));
	}


	public void collideCamToPrize(GameObject obj)
	{
		if(cam.getLocation().x() - obj.getWorldLocation().x() <= 0.4 && cam.getLocation().x() - obj.getWorldLocation().x() >= -0.4
		&& cam.getLocation().y() - obj.getWorldLocation().y() <= 0.4 && cam.getLocation().y() - obj.getWorldLocation().y() >= -0.4
		&& cam.getLocation().z() - obj.getWorldLocation().z() <= 0.4 && cam.getLocation().z() - obj.getWorldLocation().z() >= -0.4
		&& engine.getSceneGraph().getGameObjects().contains(obj))
		{
			carry += 1;
			engine.getSceneGraph().removeGameObject(obj);
		}
	}

	public void collideCamToChoc(GameObject obj)
	{
		if(cam.getLocation().x() - obj.getWorldLocation().x() <= 0.4 && cam.getLocation().x() - obj.getWorldLocation().x() >= -0.4
		&& cam.getLocation().y() - obj.getWorldLocation().y() <= 0.4 && cam.getLocation().y() - obj.getWorldLocation().y() >= -0.4
		&& cam.getLocation().z() - obj.getWorldLocation().z() <= 0.4 && cam.getLocation().z() - obj.getWorldLocation().z() >= -0.4
		&& engine.getSceneGraph().getGameObjects().contains(obj))
		{
			choc += 1;
			engine.getSceneGraph().removeGameObject(obj);
		}
	}

	public void collideCamToFeed(GameObject obj)
	{
		if(cam.getLocation().x() - obj.getWorldLocation().x() <= 1.0 && cam.getLocation().x() - obj.getWorldLocation().x() >= -1.0
		&& cam.getLocation().y() - obj.getWorldLocation().y() <= 1.0 && cam.getLocation().y() - obj.getWorldLocation().y() >= -1.0
		&& cam.getLocation().z() - obj.getWorldLocation().z() <= 1.0 && cam.getLocation().z() - obj.getWorldLocation().z() >= -1.0)
		{
			if(carry != 0) {
				score += carry;
			}	
			carry = 0;
		}
	}

	@Override
	public void update()
	{	
		lastFrameTime = currFrameTime;
		currFrameTime = System.currentTimeMillis();
		elapsedTime += (currFrameTime - lastFrameTime) / 1000.0;


		//build and set HUD
		int elapsedTimeSec = Math.round((float)elapsedTime);
		String elapsedTimeStr = Integer.toString(elapsedTimeSec);
		String scoreStr = Integer.toString(score);
		String carryStr = Integer.toString(carry);
		String chocStr = Integer.toString(choc);
		String dispStr1 = "Time: " + elapsedTimeStr;
		String dispStr2 = "Score: " + scoreStr + " pts   Balls Carried: " + carryStr + "   Choco (SPD+): " + chocStr;
		Vector3f hud1Color = new Vector3f(1,0,0);
		Vector3f hud2Color = new Vector3f(0,0,1);
		(engine.getHUDmanager()).setHUD1(dispStr1, hud1Color, 15, 15);
		(engine.getHUDmanager()).setHUD2(dispStr2, hud2Color, 500, 15);
		
		//update inputs and camera
		im.update((float)elapsedTime);
		
		if(isCamAway() && getDolphinView() == false)
		{
			cam.setLocation(lastCam);
		}
		
		lastCam = cam.getLocation();
		
		if(this.getDolphinView() == true) {
			positionCameraBehindAvatar();
		}
		if(this.getDolphinView() == false) {
			collideCamToPrize(sph1);
			collideCamToPrize(sph2);
			collideCamToPrize(sph3);
			collideCamToPrize(sph4);
			collideCamToPrize(sph5);
			collideCamToChoc(choc1);
			collideCamToChoc(choc2);
			collideCamToChoc(choc3);
			collideCamToFeed(bigsph);
		}

		

		//rotate balls
		sph1.setLocalRotation(new Matrix4f().rotation((float)elapsedTime, 0, 1, 0));
		sph2.setLocalRotation(new Matrix4f().rotation((float)elapsedTime, 0, 1, 0));
		sph3.setLocalRotation(new Matrix4f().rotation((float)elapsedTime, 0, 1, 0));
		sph4.setLocalRotation(new Matrix4f().rotation((float)elapsedTime, 0, 1, 0));
		sph5.setLocalRotation(new Matrix4f().rotation((float)elapsedTime, 0, 1, 0));

		//rotate chocolate
		choc1.setLocalRotation(new Matrix4f().rotation((float)elapsedTime, 0, 1, 0));
		choc2.setLocalRotation(new Matrix4f().rotation((float)elapsedTime, 0, 1, 0));
		choc3.setLocalRotation(new Matrix4f().rotation((float)elapsedTime, 0, 1, 0));

		//rotate big ball
		bigsph.setLocalRotation(new Matrix4f().rotation((float)elapsedTime, 0, 1, 0));

		

		
		
	}

}