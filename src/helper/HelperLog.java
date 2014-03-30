package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class HelperLog
{

    private static HelperLog oLog = null;

    /**
     * Paramètre de fonctionnement
     */
    private static boolean info = true;
    private static boolean err  = true;
    // private static int level = 0;

    /**
     * Général
     */
    private static String launchTime = "";
    private static String sLogDir;
    private static String sPrefixFormat;

    /**
     * Fichier d'info
     */
    private static String infoFile = "";
    private static String errFile  = "";
    private static String qryFile  = "";
    private static String logFile  = "";

    /**
     * Définit les fichier de gestion des erreurs et de message standard
     */
    public HelperLog()
    {
        this(true, true);
    }

    /**
     * Définit les fichier de gestion des erreurs et de message standard
     * @param Out active le fichier de message standard
     * @param Err active le fichier des erreurs
     */
    public HelperLog(boolean Out, boolean Err)
    {
        info = Out;
        err = Err;

        initLogDir();

        // Définition du système d'erreur dans un fichier log
        // afin de débuger sur les sites lancés.

        // Crée le répertoire de log, s'il n'existe pas
        new File(getLogDir()).mkdirs();

        // Récupère la date de lancement afin de le mettre dans le nom du log
        if(launchTime.length() == 0){
            launchTime = HelperDate.getToday(sPrefixFormat);
        }

        /**
         * Récupération des URL de fichier de log
         */
        if(info){
            infoFile = getLogDir() + launchTime + "_Info.log";
            qryFile = getLogDir() + launchTime + "_Query.log";
            logFile = getLogDir() + launchTime + "_Logs.log";

            // Définition du fichier de sorti par défaut
            System.setOut(getStream(infoFile, false));
        }
        if(err){
            errFile = getLogDir() + launchTime + "_Errors.log";

            // Définition du fichier de sorti par défaut
            System.setErr(getStream(errFile, false));
        }
    }

    /**
     * Renvoie le singleton de la gestion de log
     * @return
     */
    public static HelperLog getLog()
    {

        if(oLog == null){
            oLog = new HelperLog();
        }

        return oLog;
    }

    /**
     * Renvoie le singleton de la gestion de log
     * @return
     */
    public static HelperLog getLog(boolean out, boolean err)
    {

        if(oLog == null){
            oLog = new HelperLog(out, err);
        }

        return oLog;
    }

    /**
     * Envoie le message dans le fichier de log prévu pour les erreurs
     * @param message
     */
    public void logErr(String message)
    {
        if(err){
            message = HelperDate.getToday("HH:mm:ss-> ") + message;
            getStream(errFile, true).println(message);
        }

        System.err.println(message);

        // Voir GReport.transfertLog() pour voir un log remis à 0 tous les jours.
    }

    /**
     * Println()
     */
    public void logInfo()
    {
        logInfo("");
    }

    /**
     * Envoie le message dans le fichier de log prévu pour les messages infos
     * @param message
     */
    public void logInfo(String message)
    {
        if(info){
            message = HelperDate.getToday("HH:mm:ss-> ") + message;
            getStream(infoFile, true).println(message);
        }

        System.out.println(message);

        // Voir GReport.transfertLog() pour voir un log remis à 0 tous les jours.
    }

    /**
     * Envoie le message dans le fichier de log prévu pour les requêtes
     * @param message
     */
    public void logQry(String message)
    {
        if(info){
            message = HelperDate.getToday("HH:mm:ss-> ") + message;
            getStream(qryFile, true).println(message);
        }else{
            System.out.println(message);
        }

        // Voir GReport.transfertLog() pour voir un log remis à 0 tous les jours.
    }

    /**
     * Envoie le message dans le fichier de log divers
     * @param message
     */
    public void logDivers(String message)
    {
        if(info){
            message = HelperDate.getToday("HH:mm:ss-> ") + message;
            getStream(logFile, true).println(message);
        }

        System.out.println(message);
    }

    /**
     * Crée un fichier d'écriture et renvoie le stream
     * @param fileURL url du fichier
     * @param append  ajoute à la fin
     * @return
     */
    private static PrintStream getStream(String fileURL, boolean append)
    {
        PrintStream outPrintStream = null;
        try{
            FileOutputStream outOuputFile = new FileOutputStream(fileURL, append);
            outPrintStream = new PrintStream(outOuputFile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        return outPrintStream;
    }

    /**
     *
     */
    private void initLogDir()
    {
        sLogDir = HelperFile.getCurrentDir() + "logs\\";
        sPrefixFormat = "yyyy-MM-dd";
    }

    /**
     * @return
     */
    private String getLogDir()
    {
        return sLogDir;
    }

}
