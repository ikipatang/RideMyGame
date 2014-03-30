package model;

public class ModelInstruction
{

    private long        lSleepTime;
    private ModelAction oAction;

    /**
     * Action à effectuer après un temps d'attente
     * @param lSleepTime
     * @param oAction
     */
    public ModelInstruction(long lSleepTime, ModelAction oAction)
    {
        setSleepTime(lSleepTime);
        setAction(oAction);
    }

    /**
     * @param lSleepTime the sleepTime to set
     */
    public void setSleepTime(long lSleepTime)
    {
        this.lSleepTime = lSleepTime;
    }

    /**
     * @return the sleepTime
     */
    public long getSleepTime()
    {
        return lSleepTime;
    }

    /**
     * @param oAction the oAction to set
     */
    public void setAction(ModelAction oAction)
    {
        this.oAction = oAction;
    }

    /**
     * @return the oAction
     */
    public ModelAction getAction()
    {
        return oAction;
    }
}
