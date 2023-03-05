package a2;

import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event;

public class DolphinView extends AbstractInputAction
{   
    private MyGame game;
    private boolean toggle = false;

    public DolphinView(MyGame g) 
    { 
        game = g; 
    }

    public boolean toggleRide()
    {
        return toggle;
    }

    public void performAction(float time, Event e) 
    {
        toggle = !toggle;
        if(toggle == false) game.positionCameraOffAvatar();
    }
}


