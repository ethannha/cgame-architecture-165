package a2;

import tage.*;
import tage.shapes.*;
import tage.input.*;
import tage.input.action.*;
import tage.nodeControllers.ChocoController;
import tage.nodeControllers.RotationController;
import net.java.games.input.*;
import net.java.games.input.Component.Identifier.*;

import java.lang.Math;
import java.util.ArrayList;
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
	private String xCount, yCount, zCount;

	private GameObject dol, cub, sph1, sph2, sph3, sph4, sph5, choc1, choc2, choc3, bigsph, xAxis, yAxis, zAxis, plane;
	private ObjShape dolS, cubS, sphS, chocS, linxS, linyS, linzS, planeS;
	private TextureImage doltx, brick, ball, choco, bigball, lime;
	private Light light1;

	private Camera cam, leftCamera, rightCamera;
	private CameraOrbitController orbitController;
	private CameraOverheadController overController;
	private Viewport leftVp, rightVp;
	private NodeController rc, cc;
	private ArrayList<GameObject> sphereCollection = new ArrayList<GameObject>();
	private ArrayList<GameObject> chocoCollection = new ArrayList<GameObject>();

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
		planeS = new GamePlane();
		
        // load line object shapes
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
		lime = new TextureImage("lime.png");
	}

	@Override
	public void buildObjects()
	{	Matrix4f initialTranslation, initialScale, initialRotation;
		Random rand = new Random();

		//build dolphin in the center of the window 
		dol = new GameObject(GameObject.root(), dolS, doltx);
		initialTranslation = (new Matrix4f()).translation(0.0f,0.2f,0.0f);
		initialScale = (new Matrix4f()).scaling(3.0f);
		initialRotation = (new Matrix4f()).rotationY((float)java.lang.Math.toRadians(135.0f)); 
		dol.setLocalTranslation(initialTranslation);
		dol.setLocalScale(initialScale);
  		dol.setLocalRotation(initialRotation); 

		//build plane
		plane = new GameObject(GameObject.root(), planeS, lime);
		plane.setLocalTranslation((new Matrix4f()).translation(0.0f, -0.4f, 0.0f));
		plane.setLocalScale((new Matrix4f()).scaling(20.0f));

		// add X,Y,Z axes 
		xAxis = new GameObject(GameObject.root(), linxS); 
		yAxis = new GameObject(GameObject.root(), linyS); 
		zAxis = new GameObject(GameObject.root(), linzS); 

		// render axes
		(xAxis.getRenderStates()).setColor(new Vector3f(1f,0f,0f)); 
		(yAxis.getRenderStates()).setColor(new Vector3f(0f,1f,0f)); 
		(zAxis.getRenderStates()).setColor(new Vector3f(0f,0f,1f)); 
		
		//build sphere as prize 1
		sph1 = new GameObject(GameObject.root(), sphS, ball);
		initialTranslation = (new Matrix4f()).translation(rand.nextInt(20) + (-rand.nextInt(20)), 0, rand.nextInt(20) + (-rand.nextInt(20))); 
		initialScale = (new Matrix4f()).scaling(0.4f); 
		sph1.setLocalTranslation(initialTranslation); 
		sph1.setLocalScale(initialScale); 
		// sph1.setParent(bigsph);
		// sph1.propagateTranslation(true);
		// sph1.propagateRotation(false);
		sphereCollection.add(sph1);
		
		//build sphere as prize 2
		sph2 = new GameObject(GameObject.root(), sphS, ball);
		initialTranslation = (new Matrix4f()).translation(rand.nextInt(20) + (-rand.nextInt(20)), 0, rand.nextInt(20) + (-rand.nextInt(20))); 
		initialScale = (new Matrix4f()).scaling(0.4f); 
		sph2.setLocalTranslation(initialTranslation); 
		sph2.setLocalScale(initialScale); 
		// sph2.setParent(bigsph);
		// sph2.propagateTranslation(true);
		// sph2.propagateRotation(false);
		sphereCollection.add(sph2);

		//build sphere as prize 3
		sph3 = new GameObject(GameObject.root(), sphS, ball);
		initialTranslation = (new Matrix4f()).translation(rand.nextInt(20) + (-rand.nextInt(20)), 0, rand.nextInt(20) + (-rand.nextInt(20))); 
		initialScale = (new Matrix4f()).scaling(0.4f); 
		sph3.setLocalTranslation(initialTranslation); 
		sph3.setLocalScale(initialScale); 
		// sph3.setParent(bigsph);
		// sph3.propagateTranslation(true);
		// sph3.propagateRotation(false);
		sphereCollection.add(sph3);

		//build sphere as prize 4
		sph4 = new GameObject(GameObject.root(), sphS, ball);
		initialTranslation = (new Matrix4f()).translation(rand.nextInt(20) + (-rand.nextInt(20)), 0, rand.nextInt(20) + (-rand.nextInt(20))); 
		initialScale = (new Matrix4f()).scaling(0.4f); 
		sph4.setLocalTranslation(initialTranslation); 
		sph4.setLocalScale(initialScale); 
		// sph4.setParent(bigsph);
		// sph4.propagateTranslation(true);
		// sph4.propagateRotation(false);
		sphereCollection.add(sph4);

		//build sphere as prize 5
		sph5 = new GameObject(GameObject.root(), sphS, ball);
		initialTranslation = (new Matrix4f()).translation(rand.nextInt(20) + (-rand.nextInt(20)), 0, rand.nextInt(20) + (-rand.nextInt(20))); 
		initialScale = (new Matrix4f()).scaling(0.4f); 
		sph5.setLocalTranslation(initialTranslation); 
		sph5.setLocalScale(initialScale); 
		// sph5.setParent(bigsph);
		// sph5.propagateTranslation(true);
		// sph5.propagateRotation(false);
		sphereCollection.add(sph5);
		
		//build big sphere that eats the prizes for score
		bigsph = new GameObject(GameObject.root(), sphS, bigball);
		initialTranslation = (new Matrix4f()).translation(rand.nextInt(25) + (-rand.nextInt(25)), 1, rand.nextInt(25) + (-rand.nextInt(25))); 
		initialScale = (new Matrix4f()).scaling(1.3f); 
		bigsph.setLocalTranslation(initialTranslation); 
		bigsph.setLocalScale(initialScale); 
		sphereCollection.add(bigsph);

		//build manual rectangle object shape as chocolate 1
		choc1 = new GameObject(GameObject.root(), chocS, choco);
		initialTranslation = (new Matrix4f()).translation(rand.nextInt(18) + (-rand.nextInt(18)), 0, rand.nextInt(18) + (-rand.nextInt(18))); 
		initialScale = (new Matrix4f()).scaling(0.2f); 
		choc1.setLocalTranslation(initialTranslation); 
		choc1.setLocalScale(initialScale); 

		//build manual rectangle object shape as chocolate 2
		choc2 = new GameObject(GameObject.root(), chocS, choco);
		initialTranslation = (new Matrix4f()).translation(rand.nextInt(18) + (-rand.nextInt(18)), 0, rand.nextInt(18) + (-rand.nextInt(18))); 
		initialScale = (new Matrix4f()).scaling(0.2f); 
		choc2.setLocalTranslation(initialTranslation); 
		choc2.setLocalScale(initialScale); 

		//build manual rectangle object shape as chocolate 3
		choc3 = new GameObject(GameObject.root(), chocS, choco);
		initialTranslation = (new Matrix4f()).translation(rand.nextInt(18) + (-rand.nextInt(18)), 0, rand.nextInt(18) + (-rand.nextInt(18))); 
		initialScale = (new Matrix4f()).scaling(0.2f); 
		choc3.setLocalTranslation(initialTranslation); 
		choc3.setLocalScale(initialScale); 

		//build cube at the right of the window 
		cub = new GameObject(GameObject.root(), cubS, brick); 
		initialTranslation = (new Matrix4f()).translation(15,4,0); 
		initialScale = (new Matrix4f()).scaling(3.0f); 
		cub.setLocalTranslation(initialTranslation); 
		cub.setLocalScale(initialScale); 

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
	{	
		lastFrameTime = System.currentTimeMillis();
		currFrameTime = System.currentTimeMillis();
		elapsedTime = 0.0;
		(engine.getRenderSystem()).setWindowDimensions(1900,1000);

		// ============ CREATE VIEWPORTS ============
		createViewports();

		// -------------- ROTATION NODE CONTROLLER --------------
		rc = new RotationController(engine, new Vector3f(0,1,0), 0.001f);
		for(int i=0; i < sphereCollection.size(); i++)
		{
			rc.addTarget(sphereCollection.get(i));
		}
		(engine.getSceneGraph()).addNodeController(rc);
		rc.toggle();

		// -------------- CHOCO NODE CONTROLLER --------------
		cc = new ChocoController(engine, dol, 2.0f);
		(engine.getSceneGraph()).addNodeController(cc);
		cc.toggle();

		// ----------------- INPUTS SECTION ---------------------- 
		im = engine.getInputManager();
		String gpName = im.getFirstGamepadName();

		// --------------------- CAMERAS -----------------------
		cam = (engine.getRenderSystem()).getViewport("LEFT").getCamera();
		orbitController = new CameraOrbitController(cam, dol, gpName, engine);
		overController = new CameraOverheadController(rightCamera, engine);
		
		// ------------- OTHER INPUTS SECTION --------------
		ForwardAction fwdAction = new ForwardAction(this); 
		TurnLeftAction turnLeftAction = new TurnLeftAction(this); 
		BackwardAction bwdAction = new BackwardAction(this);
		TurnRightAction turnRightAction = new TurnRightAction(this);
		ToggleAxis axisToggle = new ToggleAxis(this);

		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.M, axisToggle, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY); 

		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.W, fwdAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN); 
		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.A, turnLeftAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN); 
		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.S, bwdAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN); 
		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.D, turnRightAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

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

	public int getChoco() {
		return choc;
	}

	@Override
	public void createViewports()
	{ 
		(engine.getRenderSystem()).addViewport("LEFT",0,0,1f,1f);
		(engine.getRenderSystem()).addViewport("RIGHT",.75f,0,.25f,.25f);

		leftVp = (engine.getRenderSystem()).getViewport("LEFT");
		rightVp = (engine.getRenderSystem()).getViewport("RIGHT");
		leftCamera = leftVp.getCamera();
		rightCamera = rightVp.getCamera();

		rightVp.setHasBorder(true);
		rightVp.setBorderWidth(4);
		rightVp.setBorderColor(0.0f, 1.0f, 0.0f);

		leftCamera.setLocation(new Vector3f(-2,0,2));			//sets location of minicam aligning with dol on plane
		leftCamera.setU(new Vector3f(1,0,0));
		leftCamera.setV(new Vector3f(0,1,0));
		leftCamera.setN(new Vector3f(0,0,-1));
		
		rightCamera.setLocation(new Vector3f(0,2,0));		//sets location of minicam above dol
		rightCamera.setU(new Vector3f(1,0,0));
		rightCamera.setV(new Vector3f(0,0,-1));
		rightCamera.setN(new Vector3f(0,-1,0));
	}

	public void toggleAxisOn() {
		xAxis.getRenderStates().enableRendering();
		yAxis.getRenderStates().enableRendering();
		zAxis.getRenderStates().enableRendering();
	}

	public void toggleAxisOff() {
		xAxis.getRenderStates().disableRendering();
		yAxis.getRenderStates().disableRendering();
		zAxis.getRenderStates().disableRendering();
	}

	public void collideCamToPrize(GameObject obj)
	{
		if(dol.getWorldLocation().x() - obj.getWorldLocation().x() <= 0.8 && dol.getWorldLocation().x() - obj.getWorldLocation().x() >= -0.8
		&& dol.getWorldLocation().y() - obj.getWorldLocation().y() <= 0.8 && dol.getWorldLocation().y() - obj.getWorldLocation().y() >= -0.8
		&& dol.getWorldLocation().z() - obj.getWorldLocation().z() <= 0.8 && dol.getWorldLocation().z() - obj.getWorldLocation().z() >= -0.8
		&& engine.getSceneGraph().getGameObjects().contains(obj))
		{
			carry += 1;
			engine.getSceneGraph().removeGameObject(obj);
		}
	}

	public void collideCamToChoc(GameObject obj)
	{
		if(dol.getWorldLocation().x() - obj.getWorldLocation().x() <= 0.8 && dol.getWorldLocation().x() - obj.getWorldLocation().x() >= -0.8
		&& dol.getWorldLocation().y() - obj.getWorldLocation().y() <= 0.8 && dol.getWorldLocation().y() - obj.getWorldLocation().y() >= -0.8
		&& dol.getWorldLocation().z() - obj.getWorldLocation().z() <= 0.8 && dol.getWorldLocation().z() - obj.getWorldLocation().z() >= -0.8
		&& engine.getSceneGraph().getGameObjects().contains(obj))
		{
			if(chocoCollection.contains(obj) != true)
			{
				choc += 1;
				cc.addTarget(obj);
				chocoCollection.add(obj);
			}
			//engine.getSceneGraph().removeGameObject(obj);
		}
	}

	public void collideCamToFeed(GameObject obj)
	{
		if(dol.getWorldLocation().x() - obj.getWorldLocation().x() <= 1.0 && dol.getWorldLocation().x() - obj.getWorldLocation().x() >= -1.0
		&& dol.getWorldLocation().y() - obj.getWorldLocation().y() <= 1.0 && dol.getWorldLocation().y() - obj.getWorldLocation().y() >= -1.0
		&& dol.getWorldLocation().z() - obj.getWorldLocation().z() <= 1.0 && dol.getWorldLocation().z() - obj.getWorldLocation().z() >= -1.0)
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
		String chocStr = Integer.toString(choc);
		xCount = String.format("%.2f", dol.getWorldLocation().x());
		yCount = String.format("%.2f", dol.getWorldLocation().y());
		zCount = String.format("%.2f", dol.getWorldLocation().z());
		String dispStr1 = "Time: " + elapsedTimeStr + "  Score: " + scoreStr + "  Choco (SPD+): " + chocStr;;
		String dispStr2 = "XYZ: (" + xCount + ", " + yCount + ", " + zCount + ")";
		Vector3f hud1Color = new Vector3f(1,0,0);
		Vector3f hud2Color = new Vector3f(0,0,1);
		(engine.getHUDmanager()).setHUD1(dispStr1, hud1Color, 10, 15);
		(engine.getHUDmanager()).setHUD2(dispStr2, hud2Color, (int)((engine.getRenderSystem()).getWidth() - rightVp.getActualWidth()), 15);
		
		//update inputs and camera
		im.update((float)elapsedTime);
		
		//collision with prizes
		collideCamToPrize(sph1);
		collideCamToPrize(sph2);
		collideCamToPrize(sph3);
		collideCamToPrize(sph4);
		collideCamToPrize(sph5);
		collideCamToChoc(choc1);
		collideCamToChoc(choc2);
		collideCamToChoc(choc3);
		collideCamToFeed(bigsph);

		orbitController.updateCameraPosition();
		overController.updateCameraPosition();

	}

}