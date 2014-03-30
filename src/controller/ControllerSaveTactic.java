package controller;

import helper.HelperFile;
import helper.HelperTools;
import model.ModelAction;
import model.ModelInstruction;
import model.ModelPosition;
import model.ModelTactic;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;

public class ControllerSaveTactic
{

    private String sSaveDir;
    private String sSaveName;
    //	private String	sPrefixFormat;

    /**
     * Création du controller permettant de lancer la sauvegarde
     */
    public ControllerSaveTactic(String sSaveDir, String sFileName)
    {
        // TODO si sSaveDir est vide alors demande de récupération du répertoire d'enregistrement et du nom du fichier
        if(sSaveDir.length() == 0){
            this.setSaveDir(HelperFile.getCurrentDir() + "save\\");
        }

        this.setSaveName(sFileName);
        //		sPrefixFormat = "yyyy-MM-dd HH:mm:ss";
    }

    /**
     * Sauvegarde la tactic passé en paramètre
     * @return
     */
    public boolean saveTactic(ModelTactic oTactic)
    {
        boolean isSaved = false;

        // Récupération des informations sous formes de noeud
        Element nTactic = new Element("tactic");
        nTactic.setAttribute(new Attribute("name", oTactic.getName()));

        Vector<ModelInstruction> vInstruction = oTactic.getInstructions();
        for(int i = 0; i < vInstruction.size(); i++){
            ModelInstruction oInstruction = vInstruction.get(i);

            // Récupération des données sous format de noeud
            Element nInstruction = this.getInstruction(i, oInstruction.getSleepTime());
            Element nAction = this.getAction(oInstruction.getAction());
            Element nPosition = this.getposition(oInstruction.getAction().getPosition());

            // Ajout des données récupéré dans les noeuds appropriés
            nAction.addContent(nPosition);
            nInstruction.addContent(nAction);
            nTactic.addContent(nInstruction);
        }

        // Sauvegarde de la tactic
        this.saveFile(new Document(nTactic), "test");

        return isSaved;
    }

    /**
     * @param iPosition
     * @param lSleepTime
     * @return
     */
    private Element getInstruction(int iPosition, long lSleepTime)
    {
        Element nInstruction = new Element("instruction");
        nInstruction.setAttribute(new Attribute("order", HelperTools.intToString(iPosition)));

        // Récupération du sleeptime
        Element nSleepTime = new Element("sleep_time");
        nSleepTime.setText(HelperTools.longToString(lSleepTime));
        nInstruction.addContent(nSleepTime);

        return nInstruction;
    }

    /**
     * @param oAction
     * @return
     */
    private Element getAction(ModelAction oAction)
    {
        Element nAction = new Element("action");
        nAction.setAttribute(new Attribute("name", oAction.getName()));

        Element nDevice = new Element("device").setText(HelperTools.intToString(oAction.getDevice()));
        nAction.addContent(nDevice);

        Element nEvent = new Element("id_event").setText(HelperTools.intToString(oAction.getIdEvent()));
        nAction.addContent(nEvent);

        return nAction;
    }

    /**
     * @param oPosition
     * @return
     */
    private Element getposition(ModelPosition oPosition)
    {
        Element nPosition = new Element("position");

        Element nPosX = new Element("x").setText(HelperTools.intToString(oPosition.getPosX()));
        nPosition.addContent(nPosX);

        Element nPosY = new Element("y").setText(HelperTools.intToString(oPosition.getPosY()));
        nPosition.addContent(nPosY);

        return nPosition;
    }

    /**
     * @param sFileName
     * @param nTactic
     * @return
     */
    private boolean saveFile(Document document, String sFileName)
    {
        boolean isSuccess = false;

        // Crée le répertoire de sauvegarde, s'il n'existe pas
        new File(getSaveDir()).mkdirs();

        String saveFile = getSaveDir() + sFileName + ".xml";

        try{
            // On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, new FileOutputStream(saveFile));
        }catch(java.io.IOException ignored){
        }

        return isSuccess;
    }

    /**
     * @return
     */
    private String getSaveDir()
    {
        return sSaveDir;
    }

    /**
     * Modifie le répertoire de sauvegarde par défaut
     * @param sDir
     */
    private void setSaveDir(String sDir)
    {
        sSaveDir = sDir;
    }

    /**
     * @param sSaveName the sSaveName to set
     */
    public void setSaveName(String sSaveName)
    {
        this.sSaveName = sSaveName;
    }

    /**
     * @return the sSaveName
     */
    public String getSaveName()
    {
        return sSaveName;
    }

}
