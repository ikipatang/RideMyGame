package model;

import java.util.Vector;

public class ModelTactic
{

    private Vector<ModelInstruction> vInstruction;
    private String                   sName;

    public ModelTactic(String sName)
    {
        this.sName = sName;
        vInstruction = new Vector<ModelInstruction>();
    }

    /**
     * @param vAction the vAction to set
     */
    public void addInstruction(ModelInstruction oInstruction)
    {
        vInstruction.add(oInstruction);
    }

    /**
     * @param oAction
     * @param iPos
     */
    public void addInstruction(int iPos, ModelInstruction oInstruction)
    {
        vInstruction.add(iPos, oInstruction);
    }

    /**
     * @return the vInstruction
     */
    public Vector<ModelInstruction> getInstructions()
    {
        return vInstruction;
    }

    /**
     * @return the sName
     */
    public String getName()
    {
        return sName;
    }
}
