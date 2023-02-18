package a1;

import tage.*;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event;

public class GamepadMove extends AbstractInputAction 
{ 
    private MyGame game;
    private GameObject av;
    private Camera c;

    public GamepadMove(MyGame g) 
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
                av.objMoveForward(e, game.getElapsedTime() + (game.getElapsedTime()*game.getChoco()));       //dolphin move forward
            else if(keyValue > .4)
                av.objMoveBackward(e, game.getElapsedTime() + (game.getElapsedTime()*game.getChoco()));      //dolphin move backward
        }
        else if(!game.getDolphinView()) 
        {
            if(keyValue < -.4)
                c.camMoveForward(e, game.getElapsedTime() + (game.getElapsedTime()*game.getChoco()));        //camera move forward
            else if(keyValue > .4)
                c.camMoveBackward(e, game.getElapsedTime() + (game.getElapsedTime()*game.getChoco()));       //camera move backward
        }
    }
    
}
