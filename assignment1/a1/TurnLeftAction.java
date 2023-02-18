package a1;

import tage.*;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event;

public class TurnLeftAction extends AbstractInputAction 
{
    private MyGame game; 
    private GameObject av; 
	private Camera c; 

    public TurnLeftAction(MyGame g) 
    { 
        game = g; 
    } 

    @Override 
    public void performAction(float time, Event e) 
    {
        av = game.getAvatar();
        c = game.getCamera();
        if(game.getDolphinView()) av.objTurnLeft(e, game.getElapsedTime());
        else if(!game.getDolphinView()) c.camTurnLeft(e, game.getElapsedTime());
    }

}
