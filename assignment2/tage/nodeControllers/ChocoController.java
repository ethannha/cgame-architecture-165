package tage.nodeControllers;
import tage.*;
import org.joml.*;

public class ChocoController extends NodeController
{
    private float scaleRate = .0003f;
    private float cycleTime = 2000.0f;
    private float totalTime = 0.0f;
    private float direction = 1.0f;
    private Matrix4f curScale, newScale;
    private GameObject dol;
    private Vector3f dolLoc;
    private float locX, locY, locZ;
	private Engine engine;

	public ChocoController() { super(); }

	public ChocoController(Engine e, GameObject dol, float ctime)
	{	super();
        cycleTime = ctime;
        engine = e;
        newScale = new Matrix4f();
        locX = dol.getWorldLocation().x();
        locY = dol.getWorldLocation().y();
        locZ = dol.getWorldLocation().z();
        dolLoc = new Vector3f(locX, locY, locZ);
	}

	public void apply(GameObject choc)
	{	
        float elapsedTime = super.getElapsedTime();
        totalTime += elapsedTime/1000.0f;
        if (totalTime > cycleTime)
        { 
            direction = -direction;
            totalTime = 0.0f;
        }
        curScale = choc.getLocalScale();
        float scaleAmt = 1.0f + direction * scaleRate * elapsedTime;
        newScale.scaling(curScale.m00()*scaleAmt, curScale.m11(), curScale.m22()*scaleAmt);
        choc.setLocalScale(newScale);
        choc.setLocalLocation(dolLoc);
        System.out.println("LOC" + dolLoc);

	}
}