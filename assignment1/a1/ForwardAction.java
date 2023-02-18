package a1;

import tage.*;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event;

public class ForwardAction extends AbstractInputAction 
{ 
    private MyGame game;
    private GameObject av;
    private Camera c;

    public ForwardAction(MyGame g) 
    { 
        game = g; 
    } 

    @Override 
    public void performAction(float time, Event e) 
    {
        
        av = game.getAvatar();
        c = game.getCamera();
        if(game.getDolphinView()) av.objMoveForward(e, game.getElapsedTime() + (game.getElapsedTime()*game.getChoco()));
        else if(!game.getDolphinView()) c.camMoveForward(e, game.getElapsedTime() + (game.getElapsedTime()*game.getChoco()));
    }
    
}
