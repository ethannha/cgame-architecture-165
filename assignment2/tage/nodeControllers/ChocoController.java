package tage.nodeControllers;
import tage.*;
import org.joml.*;

/**
* A ChocoController is a node controller that, when enabled, causes any object
* that it is attached to, to shrink. Mainly is a controller specifically made for
* chocolate game objects that the dolphin collects for a speed boost upgrade.
* @author Ethan N. Ha
*/
public class ChocoController extends NodeController
{
    private float scaleRate = .0005f;
    private float direction = 1.0f;
    private Matrix4f curScale, newScale;
    private Engine engine;

	public ChocoController() { super(); }

    /** Constructor that creates the chocolate node controller */
	public ChocoController(Engine e)
	{	super();
        engine = e;
        newScale = new Matrix4f();
	}

    /** This is called automatically by the RenderSystem (via SceneGraph) once per frame
	*   during display().  It is for engine use and should not be called by the application.
	*/
	public void apply(GameObject choc)
	{	
        float elapsedTime = super.getElapsedTime();
        curScale = choc.getLocalScale();
        float scaleAmt = 1.0f + -direction * scaleRate * elapsedTime;
        newScale.scaling(curScale.m00()*scaleAmt, curScale.m11()*scaleAmt, curScale.m22()*scaleAmt);
        choc.setLocalScale(newScale);

	}
}