package model;

import helper.HelperLog;
import helper.HelperTools;

import java.awt.*;


public class ModelMouse
{
    private Robot robot;

    /**
     * Move the mouse.
     * @throws InterruptedException
     * @throws InterruptedException
     */
    public void move(ModelPosition oPosition, int input)
            throws InterruptedException
    {
        try{
            robot = new Robot();
        }catch(AWTException e){
            HelperLog.getLog().logErr("Erreur lors de la récupération de la nouvelle instance du robot dans la fonction ModelMouse.move()");
            e.printStackTrace();
        }

        // Move mouse.
        robot.mouseMove(oPosition.getPosX(), oPosition.getPosY());

        // Left click and release.
        if(input > 0){
            robot.mousePress(input);
            HelperTools.sleep(200);
            robot.mouseRelease(input);
        }
    }

    /**
     * Renvoie la position actuel de la souris
     * @return
     */
    public static ModelPosition getMousePosition()
    {
        return new ModelPosition(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
    }
}
