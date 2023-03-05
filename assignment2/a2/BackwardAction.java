package a2;

import tage.*;
import tage.input.action.AbstractInputAction; 
import net.java.games.input.Event;

public class BackwardAction extends AbstractInputAction 
{ 
    private MyGame game;
    private GameObject av;
    private Camera c;

    public BackwardAction(MyGame g) 
    { 
        game = g; 
    } 

    @Override 
    public void performAction(float time, Event e) 
    {
        av = game.getAvatar();
        c = game.getCamera();
        if(game.getDolphinView()) av.objMoveBackward(e, game.getElapsedTime() + (game.getElapsedTime()*game.getChoco()));
        else if(!game.getDolphinView()) c.camMoveBackward(e, game.getElapsedTime() + (game.getElapsedTime()*game.getChoco()));
    }

}
