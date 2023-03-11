package a2;

import tage.*;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event;

public class GamepadTurn extends AbstractInputAction 
{
    private MyGame game; 
    private GameObject av;

    public GamepadTurn(MyGame g) 
    { 
        game = g; 
    } 

    @Override 
    public void performAction(float time, Event e) 
    {
        float keyValue = e.getValue();
        av = game.getAvatar();
        
        if(keyValue < -.4) 
            av.Yaw(e, game.getElapsedTime());      //dolphin turn left
        else if(keyValue > .4)
            av.Yaw(e, -game.getElapsedTime());     //dolphin turn right
    }

}
