package helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelperDate
{
    /**
     * Retourne la date d'aujourd'hui au format précisé
     * @param format
     * @return
     */
    public static String getToday(String format)
    {
        return new SimpleDateFormat(format).format(new Date());
    }
}
