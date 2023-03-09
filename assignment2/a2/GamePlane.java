package a2;

import tage.Utils;
import tage.shapes.Plane;

public class GamePlane extends Plane{
    public GamePlane() {
        super();

		setMatAmb(Utils.goldAmbient());
		setMatDif(Utils.goldDiffuse());
		setMatSpe(Utils.goldSpecular());
		setMatShi(Utils.goldShininess());
    }
}
