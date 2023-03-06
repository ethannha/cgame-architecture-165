package a2;

import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;

public class ToggleAxis extends AbstractInputAction 
{
    private MyGame game;
    private boolean toggle = true;

    public ToggleAxis(MyGame g) 
    { 
        game = g; 
    }

    public boolean axisOn() 
    {
        return toggle;
    }

    @Override 
    public void performAction(float time, Event e) 
    {
        toggle = !toggle;
        if(toggle == false) game.toggleAxis();
    }

}

