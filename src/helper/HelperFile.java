package helper;

import java.io.File;

public class HelperFile
{

    private static final String separator  = File.separator;
    private static       String currentDir = "";

    /**
     * Définit le répertoire courant
     * @param dir
     */
    public static void setCurrentDir(String dir)
    {
        currentDir = dir;
    }

    /**
     * @return le répertoire courant, vide si pas de répertoire spécifié
     */
    public static String getCurrentDir()
    {
        if(currentDir.isEmpty()){
            currentDir = new File("").getAbsolutePath() + separator;
        }else if(!currentDir.isEmpty() && !currentDir.endsWith(separator)){
            currentDir += separator;
        }

        System.out.println("curDir : " + currentDir);

        return currentDir;
    }

    /**
     * Vérifie si le fichier existe
     * @param name
     * @return
     */
    public static boolean isFileExist(String name)
    {
        boolean fileExist = true;

        try{
            fileExist = new File(name).exists();
        }catch(Exception e){
            fileExist = false;
        }

        return fileExist;
    }
}
