package controller;

import helper.HelperFile;
import helper.HelperLog;
import helper.HelperTools;
import model.ModelAction;
import model.ModelInstruction;
import model.ModelPosition;
import model.ModelTactic;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class ControllerReadTactic
{

    private String      sSaveDir;
    private String      sSaveName;
    private ModelTactic oTactic;

    /**
     * Initialise les éléments pour la récupération de la sauvegarde
     * @param sSaveDir
     * @param sFileName
     */
    public ControllerReadTactic(String sSaveDir, String sFileName)
    {
        // TODO si sSaveDir est vide alors demande de récupération du répertoire d'enregistrement et du nom du fichier
        if(sSaveDir.length() == 0){
            setSaveDir(HelperFile.getCurrentDir() + "save\\");
        }

        setSaveName(sFileName);
    }

    /**
     * Lancement de la lecture de la tactique
     * @return
     */
    public boolean readTactique()
    {
        boolean bIsSuccess = false;
        SAXBuilder xmlBuilder = new SAXBuilder();
        Document document;
        String sXMLFile = getSaveDir() + getSaveName() + ".xml";
        try{
            document = xmlBuilder.build(new File(sXMLFile));
        }catch(Exception e){
            HelperLog.getLog().logErr("Erreur lors de l'ouverture du fichier xml " + sXMLFile);
            return bIsSuccess;
        }

        // Récupération de la racine
        Element nTactic = document.getRootElement();
        oTactic = getTactic(nTactic);

        return oTactic == null;
    }

    /**
     * Renvoie la tactique du noeud actuel
     * @param nTactic
     * @return
     */
    private ModelTactic getTactic(Element nTactic)
    {
        String sName = nTactic.getAttributeValue("name");
        ModelTactic oTactic = new ModelTactic(sName);

        List<Element> listInstruction = nTactic.getChildren("instruction");
        // Parcours de la liste d'instructions
        Iterator<Element> itInstruction = listInstruction.iterator();
        while(itInstruction.hasNext()){
            // Récupération de l'instruction
            Element nInstruction = itInstruction.next();
            int iOrder = HelperTools.integerParse(nInstruction.getAttributeValue("order"));
            if(iOrder == -1){
                HelperLog.getLog().logErr("Erreur lors de la récupération de la position d'une instruction de la tactique " + oTactic.getName());
                continue;
            }

            // Récupération de l'instruction
            ModelInstruction oInstruction = getInstruction(nInstruction);
            if(oInstruction == null){
                continue;
            }

            // Ajout de l'instruction à la liste
            oTactic.addInstruction(iOrder, oInstruction);
        }

        return oTactic;
    }

    /**
     * Récupère les informations de l'instruction
     * @param nInstruction
     * @return
     */
    private ModelInstruction getInstruction(Element nInstruction)
    {
        ModelInstruction oInstruction = null;
        if(nInstruction == null){
            HelperLog.getLog().logErr("Erreur le noeud instruction passé en paramètre est incorrect");
            return oInstruction;
        }

        // Récupération du sleep_time
        long lSleepTime = HelperTools.longParse(nInstruction.getChildText("sleep_time"));
        if(lSleepTime == -1){
            HelperLog.getLog().logErr("Erreur lors de la récupération du sleep_time");
            return oInstruction;
        }

        // Récupération de l'action
        ModelAction oAction = getAction(nInstruction.getChild("action"));
        if(oAction == null){
            return oInstruction;
        }

        // Création de l'instruction correspondant aux données récupérées dans ce noeud
        oInstruction = new ModelInstruction(lSleepTime, oAction);

        return oInstruction;
    }

    /**
     * @param nAction
     * @return
     */
    private ModelAction getAction(Element nAction)
    {
        ModelAction oAction = null;
        if(nAction == null){
            HelperLog.getLog().logErr("Erreur le noeud action passé en paramètre est incorrect");
            return oAction;
        }

        // Action name
        String sName = nAction.getAttributeValue("name");
        if(sName == null){
            HelperLog.getLog().logErr("Erreur lors de la récupération du nom de l'action");
            return oAction;
        }

        // Device
        int iDevice = HelperTools.integerParse(nAction.getChildText("device"));
        if(iDevice == -1){
            HelperLog.getLog().logErr("Erreur lors de la récupération du device");
            return oAction;
        }

        // Event
        int iIdEvent = HelperTools.integerParse(nAction.getChildText("id_event"));
        if(iIdEvent == -1){
            HelperLog.getLog().logErr("Erreur lors de la récupération de l'identifiante évennement");
            return oAction;
        }

        // Position
        ModelPosition oPosition = null;
        // Si il s'agit d'un device de type souris
        if(ModelAction.needPosition(iDevice)){
            oPosition = getPosition(nAction.getChild("position"));
            if(oPosition == null){
                return oAction;
            }
        }


        // Action
        oAction = new ModelAction(sName, oPosition, iDevice, iIdEvent);

        return oAction;
    }

    /**
     * @param nPosition
     * @return
     */
    private ModelPosition getPosition(Element nPosition)
    {
        ModelPosition oPosition = null;

        int iPosX = HelperTools.integerParse(nPosition.getChildText("x"));
        if(iPosX == -1){
            HelperLog.getLog().logErr("Erreur lors de la récupération de la position horizontal (x)");
            return oPosition;
        }

        int iPosY = HelperTools.integerParse(nPosition.getChildText("y"));
        if(iPosX == -1){
            HelperLog.getLog().logErr("Erreur lors de la récupération de la position vertical (y)");
            return oPosition;
        }

        oPosition = new ModelPosition(iPosX, iPosY);

        return oPosition;
    }

    /**
     * @param sSaveDir the sSaveDir to set
     */
    public void setSaveDir(String sSaveDir)
    {
        this.sSaveDir = sSaveDir;
    }

    /**
     * @return the sSaveDir
     */
    public String getSaveDir()
    {
        return sSaveDir;
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

    /**
     * Renvoie la tactique lu
     * @return
     */
    public ModelTactic getTactic()
    {
        return oTactic;
    }
}
