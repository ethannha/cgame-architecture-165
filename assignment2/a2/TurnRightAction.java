package a2;

import tage.*;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event;

public class TurnRightAction extends AbstractInputAction 
{
    private MyGame game; 
    private GameObject av; 
	private Camera c; 

    public TurnRightAction(MyGame g) 
    { 
        game = g; 
    } 

    @Override 
    public void performAction(float time, Event e) 
    {
        av = game.getAvatar();
        c = game.getCamera();
        if(game.getDolphinView()) av.objTurnRight(e, game.getElapsedTime());
        else if(!game.getDolphinView()) c.camTurnRight(e, game.getElapsedTime());
    }

}
