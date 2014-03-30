package model;

import helper.HelperLog;
import object.RMG_KeyEvent;

public class ModelAction
{
    private ModelPosition oPosition;
    private int           iDevice;
    private String        sName;
    private int           iIdEvent;

    /**
     * @param sName
     * @param oPosition
     * @param iDevice
     * @param iIdEvent
     */
    public ModelAction(String sName, ModelPosition oPosition, int iDevice, int iIdEvent)
    {
        setName(sName);
        setDevice(iDevice);
        setPosition(oPosition);
        setIdEvent(iIdEvent);
    }

    /**
     * @throws InterruptedException
     */
    public void doAction() throws InterruptedException
    {
        // Si ce n'est pas une action clavier
        if(!isKeyBoard()){
            ModelMouse oMouse = new ModelMouse();
            oMouse.move(oPosition, getIdEvent());
        }else{ // Action Clavier
            HelperLog.getLog().logInfo("Ajouter la gestion du clavier dans la fonction ModelAction.doAction");
        }
    }

    /**
     * Détermine si l'action doit être exécuté par le clavier
     * @return bool
     */
    private boolean isKeyBoard()
    {
        return getDevice() == RMG_KeyEvent.DEVICE_KEYBOARD;
    }

    /**
     * @param oPosition the oPosition to set
     */
    public void setPosition(ModelPosition oPosition)
    {
        this.oPosition = oPosition;
    }

    /**
     * @return the oPosition
     */
    public ModelPosition getPosition()
    {
        return oPosition;
    }

    /**
     * @param iType the iType to set
     */
    public void setDevice(int iType)
    {
        this.iDevice = iType;
    }

    /**
     * @return the iType
     */
    public int getDevice()
    {
        return iDevice;
    }

    /**
     * @param sName the sName to set
     */
    public void setName(String sName)
    {
        this.sName = sName;
    }

    /**
     * @return the sName
     */
    public String getName()
    {
        return sName;
    }

    /**
     * @param iInput the iInput to set
     */
    public void setIdEvent(int iInput)
    {
        this.iIdEvent = iInput;
    }

    /**
     * @return the iInput
     */
    public int getIdEvent()
    {
        return iIdEvent;
    }

    /**
     * @param iDevice
     * @return boolean
     */
    public static boolean needPosition(int iDevice)
    {
        return iDevice == RMG_KeyEvent.DEVICE_MOUSE;
    }

    /**
     * Get the text value of this action
     * @return
     */
    public String getText()
    {
        String sName = getName();

        sName += " (" + getMoreData() + ")";

        return sName;
    }

    /**
     * Give more data information
     * @return
     */
    private String getMoreData()
    {
        String sMoreData;
        if(getDevice() == RMG_KeyEvent.DEVICE_MOUSE){
            sMoreData = RMG_KeyEvent.getModifiersText(getIdEvent());
        }else{
            sMoreData = RMG_KeyEvent.getKeyText(getIdEvent());
        }
        String sPosition = getPosition().getText();
        if(sPosition.length() > 0){
            sMoreData += " => " + sPosition;
        }

        return sMoreData;
    }
}
