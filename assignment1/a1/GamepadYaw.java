package a1;

import tage.*;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event;

public class GamepadYaw extends AbstractInputAction 
{
    private MyGame game; 
    private GameObject av; 
	private Camera c; 

    public GamepadYaw(MyGame g) 
    { 
        game = g; 
    } 

    @Override 
    public void performAction(float time, Event e) 
    {
        float keyValue = e.getValue();
        av = game.getAvatar();
        c = game.getCamera();
        if(game.getDolphinView()) 
        {
            if(keyValue < -.4) 
                av.Yaw(e, game.getElapsedTime());      //dolphin turn left
            else if(keyValue > .4)
                av.Yaw(e, -game.getElapsedTime());     //dolphin turn right
        }
        else if(!game.getDolphinView()) 
        {
            if(keyValue < -.4)
                c.Yaw(e, game.getElapsedTime());       //camera turn left
            else if(keyValue > .4)
                c.Yaw(e, -game.getElapsedTime());      //camera turn right
        }
    }

}
