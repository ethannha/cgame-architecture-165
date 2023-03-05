package a2;

import tage.*;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event;

public class GamepadMove extends AbstractInputAction 
{ 
    private MyGame game;
    private GameObject av;

    public GamepadMove(MyGame g) 
    { 
        game = g; 
    } 

    @Override 
    public void performAction(float time, Event e) 
    {
        float keyValue = e.getValue();
        av = game.getAvatar();
    
        if(keyValue < -.4)
            av.objMoveForward(e, game.getElapsedTime() + (game.getElapsedTime()*game.getChoco()));       //dolphin move forward
        else if(keyValue > .4)
            av.objMoveBackward(e, game.getElapsedTime() + (game.getElapsedTime()*game.getChoco()));      //dolphin move backward
    }
    
}
