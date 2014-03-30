package controller;

import helper.HelperTools;
import model.ModelInstruction;

import java.util.Iterator;
import java.util.Vector;

public class ControllerTactic extends Thread
{

    private Vector<ModelInstruction> vInstruction;
    private boolean                  hasToRun;

    /**
     * Charge une liste d'instruction
     * @param vInstruction
     */
    public ControllerTactic(Vector<ModelInstruction> vInstruction)
    {
        setInstructions(vInstruction);
    }

    /**
     *
     */
    public void run()
    {
        if(vInstruction.size() > 0){
            hasToRun = true;
            while(hasToRun){
                try{
                    loop();
                }catch(Exception e){
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    /**
     * Arrête la suite d'instruction
     */
    public void tactiqueStop()
    {
        hasToRun = false;
        interrupt();
    }

    /**
     * @throws InterruptedException
     */
    private void loop()
            throws InterruptedException
    {
        Iterator<ModelInstruction> it = getInstructions().iterator();
        while(it.hasNext()){
            doInstruction(it.next());
        }
    }

    /**
     * Traite l'instruction
     * @param oInstruction
     * @throws InterruptedException
     */
    private void doInstruction(ModelInstruction oInstruction)
            throws InterruptedException
    {
        // Attente avant de lancer l'instruction
        if(oInstruction.getSleepTime() > 0){
            HelperTools.sleep(oInstruction.getSleepTime());
        }

        // Exécution de l'action
        oInstruction.getAction().doAction();
    }

    /**
     * @param vInstruction the vInstruction to set
     */
    public void setInstructions(Vector<ModelInstruction> vInstruction)
    {
        this.vInstruction = vInstruction;
    }

    /**
     * @return the vInstruction
     */
    public Vector<ModelInstruction> getInstructions()
    {
        return vInstruction;
    }
}
