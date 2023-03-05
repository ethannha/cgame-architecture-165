package a2;

import tage.*;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event;

public class TurnLeftAction extends AbstractInputAction 
{
    private MyGame game; 
    private GameObject av;

    public TurnLeftAction(MyGame g) 
    { 
        game = g; 
    } 

    @Override 
    public void performAction(float time, Event e) 
    {
        av = game.getAvatar();
        av.objTurnLeft(e, game.getElapsedTime());
    }

}
