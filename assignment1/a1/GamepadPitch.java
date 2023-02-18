package a1;

import tage.*;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event; 

public class GamepadPitch extends AbstractInputAction  {
    
    private MyGame game; 
    private GameObject av; 
	private Camera c;

    public GamepadPitch(MyGame g) 
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
                av.objPitchUp(e, game.getElapsedTime());        //dolphin pitch up
            else if(keyValue > .4)
                av.objPitchDown(e, game.getElapsedTime());       //dolphin pitch down
        }
        else if(!game.getDolphinView()) 
        {
            if(keyValue < -.4)
                c.camPitchUp(e, game.getElapsedTime());         //camera pitch up
            else if(keyValue > .4)
                c.camPitchDown(e, game.getElapsedTime());        //camera pitch down
        }
    }

}
