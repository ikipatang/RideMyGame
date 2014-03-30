package main;

import helper.HelperLog;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import view.ActionPanel;
import view.MainPanel;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 09/02/14
 * Time: 01:32
 */
public class RideMyGame
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // Initialisation des logs
        boolean out = true;
        boolean err = true;
        for(String arg : args){
            if(arg.toUpperCase().compareTo("-NOERROR") == 0){
                err = false;
            }else if(arg.toUpperCase().compareTo("-NOINFO") == 0){
                out = false;
            }
        }
        HelperLog.getLog(out, err);

        // User event listener initialisation
        try{
            GlobalScreen.registerNativeHook();
        }catch(NativeHookException ex){
            HelperLog.getLog().logErr("There was a problem registering the native hook.");
            HelperLog.getLog().logErr(ex.getMessage());
            System.exit(1);
        }

        // Construct the example object and initialze native hook.
//        GlobalScreen.getInstance().addNativeKeyListener(new MainPanel());
        GlobalScreen.getInstance().addNativeKeyListener(new ActionPanel());
    }
}
