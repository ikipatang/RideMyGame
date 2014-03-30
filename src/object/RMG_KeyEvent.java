package object;

import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.Vector;

/**
 * Cette classe à pour but de faciliter l'utilisation des nativeKeyEvent
 */
public class RMG_KeyEvent
{

    /**
     * Device type
     */
    public final static int DEVICE_KEYBOARD = 0;
    public final static int DEVICE_MOUSE    = 1;

    /**
     * List of input
     */
    public final static int EVENT_LEFTCLICK   = NativeKeyEvent.BUTTON1_MASK;
    public final static int EVENT_MIDDLECLICK = NativeKeyEvent.BUTTON2_MASK;
    public final static int EVENT_RIGHTCLICK  = NativeKeyEvent.BUTTON3_MASK;
    public final static int EVENT_BACKSPACE   = NativeKeyEvent.VK_BACK_SPACE;
    public final static int EVENT_F6          = NativeKeyEvent.VK_F6;
    public final static int EVENT_F7          = NativeKeyEvent.VK_F7;

    /**
     * Fournit une liste de device
     * @return Vector<String>
     */
    public static Vector<RMG_BaseModel> getDeviceList()
    {
        Vector<RMG_BaseModel> vDeviceList = new Vector<RMG_BaseModel>();
        RMG_BaseModel basemodel = new RMG_BaseModel();
        basemodel.set("id", DEVICE_KEYBOARD);
        basemodel.set("label", "Keyboard");
        vDeviceList.add(basemodel);

        basemodel = new RMG_BaseModel();
        basemodel.set("id", DEVICE_MOUSE);
        basemodel.set("label", "Mouse");
        vDeviceList.add(basemodel);

        return vDeviceList;
    }

    /**
     * Fournit une liste de RMG_KeyEvent correspondant au device passé en paramètre
     * @param iDevice
     * @return Vector<String>
     */
    public static Vector<RMG_BaseModel> getKeyList(int iDevice)
    {
        Vector<RMG_BaseModel> vKeyList = new Vector<RMG_BaseModel>();

        RMG_BaseModel basemodel;
        if(iDevice == DEVICE_MOUSE){
            basemodel = new RMG_BaseModel();
            basemodel.set("id", EVENT_LEFTCLICK);
            basemodel.set("label", "Left click");
            vKeyList.add(basemodel);

            basemodel = new RMG_BaseModel();
            basemodel.set("id", EVENT_MIDDLECLICK);
            basemodel.set("label", "Middle click");
            vKeyList.add(basemodel);

            basemodel = new RMG_BaseModel();
            basemodel.set("id", EVENT_RIGHTCLICK);
            basemodel.set("label", "Right click");
            vKeyList.add(basemodel);
        }else{
            basemodel = new RMG_BaseModel();
            basemodel.set("id", EVENT_BACKSPACE);
            basemodel.set("label", "Backspace");
            vKeyList.add(basemodel);
        }

        return vKeyList;
    }

    /**
     * @param iEventId
     * @return
     */
    public static String getModifiersText(int iEventId)
    {
        return NativeKeyEvent.getModifiersText(iEventId);
    }

    /**
     * @param iEventId
     * @return
     */
    public static String getKeyText(int iEventId)
    {
        return NativeKeyEvent.getKeyText(iEventId);
    }
}
