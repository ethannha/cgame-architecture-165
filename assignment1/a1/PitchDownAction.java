package a1;

import tage.*;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event; 

public class PitchDownAction extends AbstractInputAction  {
    
    private MyGame game; 
    private GameObject av; 
	private Camera c;

    public PitchDownAction(MyGame g) 
    { 
        game = g; 
    } 

    @Override 
    public void performAction(float time, Event e) 
    {
        av = game.getAvatar();
        c = game.getCamera();
        if(game.getDolphinView()) av.objPitchDown(e, game.getElapsedTime());
        else if(!game.getDolphinView()) c.camPitchDown(e, game.getElapsedTime());
    }

}
