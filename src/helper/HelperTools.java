package helper;

public class HelperTools
{
    /**
     * Envoie un message d'erreur avec une date
     * @param message
     */
    public static void cErr(String message)
    {
        String now = HelperDate.getToday("HH:mm:ss.SSS");
        HelperLog.getLog().logErr("Trace à  " + now);
        HelperLog.getLog().logErr(message);
    }

    /**
     * Print stack trace avec une date
     * @param message
     */
    public static void cErr(Throwable message)
    {
        String now = HelperDate.getToday("HH:mm:ss.SSS");
        HelperLog.getLog().logErr("Trace à  " + now);
        message.printStackTrace();
        HelperLog.getLog().logInfo("Trace à  " + now);
    }

    /**
     * Converti une string en int. renvoie -1 si la conversion se passe mal
     * @param value
     * @return
     */
    public static int integerParse(String value)
    {
        int iValue = -1;

        try{
            iValue = Integer.parseInt(value);
        }catch(Exception e){
            cErr("Erreur lors de la conversion en int de la valeur " + value + "!");
        }

        return iValue;
    }

    /**
     * Converti une string en int. renvoie -1 si la conversion se passe mal
     * @param value
     * @return
     */
    public static long longParse(String value)
    {
        long lValue = -1;

        try{
            lValue = Long.parseLong(value);
        }catch(Exception e){
            cErr("Erreur lors de la conversion en long de la valeur " + value + "!");
        }

        return lValue;
    }

    /**
     * @param iValue
     * @return -1 en cas d'erreur
     */
    public static String intToString(int iValue)
    {
        String sValue = "-1";

        try{
            sValue = String.valueOf(iValue);
        }catch(Exception e){
            cErr("Erreur lors de la conversion en string !");
        }

        return sValue;
    }

    /**
     * @param lValue
     * @return -1 en cas d'erreur
     */
    public static String longToString(long lValue)
    {
        String sValue = "-1";

        try{
            sValue = String.valueOf(lValue);
        }catch(Exception e){
            cErr("Erreur lors de la conversion en string !");
        }

        return sValue;
    }

    /**
     * Convert a charCode (ASCII) to String
     * @param charCode : int
     * @return String
     */
    public static String charCodeToString(int charCode)
    {
        return Character.toString((char) charCode);
    }

    /**
     * Renvoie vrai si la string est un numérique
     * @param num
     * @return vrai si num est un numérique
     */
    public static boolean isNum(String num)
    {
        try{
            Double.parseDouble(num);
        }catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }

    /**
     * @param lSleep
     * @throws InterruptedException
     */
    public static void sleep(long lSleep)
            throws InterruptedException
    {
        Thread.sleep(lSleep);
    }
}
