package a2;

import tage.*;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event; 

public class PitchUpAction extends AbstractInputAction  {
    
    private MyGame game; 
    private GameObject av; 
	private Camera c;

    public PitchUpAction(MyGame g) 
    { 
        game = g; 
    } 

    @Override 
    public void performAction(float time, Event e) 
    {
        av = game.getAvatar();
        c = game.getCamera();
        if(game.getDolphinView()) av.objPitchUp(e, game.getElapsedTime());
        else if(!game.getDolphinView()) c.camPitchUp(e, game.getElapsedTime());
    }

}
