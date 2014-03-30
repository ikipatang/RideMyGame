package object;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;

public class RMG_BaseModel extends HashMap<Object, Object>
{
    private static final long serialVersionUID = 1L;

    private static String typeToken = "%"; // Non final pour que la classe soit sérialisable

    public static ListCellRenderer<RMG_BaseModel> getListCellRenderer(){
        ListCellRenderer<RMG_BaseModel> renderer = new ListCellRenderer<RMG_BaseModel>()
        {
            public JLabel getListCellRendererComponent(JList<? extends RMG_BaseModel> list, RMG_BaseModel value, int index, boolean isSelected,
                                                       boolean cellHasFocus)
            {
                JLabel label = new JLabel();

                label.setText(value.get("label"));

                Color background;
                Color foreground;

                // check if this cell represents the current DnD drop location
                JList.DropLocation dropLocation = list.getDropLocation();
                if(dropLocation != null
                   && !dropLocation.isInsert()
                   && dropLocation.getIndex() == index){

                    background = Color.BLUE;
                    foreground = Color.WHITE;

                    // check if this cell is selected
                }else if(isSelected){
                    background = Color.RED;
                    foreground = Color.WHITE;

                    // unselected, and not the DnD drop location
                }else{
                    background = Color.WHITE;
                    foreground = Color.BLACK;
                }

                label.setBackground(background);
                label.setForeground(foreground);
                return label;
            }
        };

        return renderer;
    }

    /**
     * Duplique THIS dans RMG_BaseModel
     * @param oBaseModel cible de la copie
     */
    public void duplicate(RMG_BaseModel oBaseModel)
    {
        Iterator<Object> it = getIterator();
        // Parcours des attributs
        while(it.hasNext()){
            String fieldName = (String) it.next();
            if(!fieldName.contains(getTypeToken())){
                oBaseModel.set(fieldName, this.get(fieldName), this.getType(fieldName));
            }
        }
    }

    /**
     * Définit la valeur de la propriété dans le hashmap
     * @param property
     * @param value
     * @return
     */
    public String set(String property, Object value)
    {
        put(property, value);
        return value.toString();
    }

    /**
     * Définit la valeur de la propriété dans le hashmap
     * si type est un numérique et que la valeur est à null alors la valeur = 0
     * si type est un string alors le type n'est pas définit dans le basemodel. (getType renvoie String par défaut)
     * @param property
     * @param value
     * @param type
     * @return
     */
    public String set(String property, String value, int type)
    {
        String oldValue = "";

        // Définit une valeur par défaut à 0 pour le type numérique
        if((typeIsNum(type)) && (value == null)){
            value = "0";
        }

        oldValue = set(property, value);

        // Si le type est un numérique ou une date alors on le définit dans le basemodel
        if(typeIsDate(type) || typeIsNum(type)){
            set(typeToken + property, type);
        }

        return oldValue;
    }

    /**
     * Renvoie la valeur de property avec les quotes protégés.
     * @param property
     * @return
     */
    public String get(String property)
    {
        if(super.get(property) != null){
            return super.get(property).toString();
        }else{
            return null;
        }
    }

    /**
     * Renvoie la valeur de property avec les quotes protégés.
     * <br/>Les quotes sont doublés afin de les protéger dans les requêtes SQL
     * @param property
     * @return
     */
    public String getQP(String property)
    {
        if(super.get(property) != null){
            return super.get(property).toString().replace("'", "''");
        }else{
            return null;
        }
    }

    /**
     * Retourne le type de l'attribut en int.
     * Si le type n'est pas définit dans le basemodel, le type string est retourné par défaut.
     * Le token est insérer automatiquement, ne passer que le nom de l'attribut.
     * @param property
     * @return 1 = String,
     *         2 = Int,
     *         8 = Double,
     *         91 = Date,
     *         93 = Timestamp,
     *         101 = Binary Double
     */
    public int getType(String property)
    {
        String type = get(typeToken + property);
        if(type == null){
            type = "1";
        }
        return Integer.parseInt(type);
    }

    /**
     * retourne vrai si numérique
     * @param type
     * @return vrai si type = 2, 8 ou 101
     */
    public static Boolean typeIsNum(int type)
    {
        return (type == 2) || (type == 8) || (type == 101);
    }

    /**
     * retourne vrai si timestamp ou datetime
     * @param type
     * @return vrai si type = 93 ou 91
     */
    public static Boolean typeIsDate(int type)
    {
        return (type == 93) || (type == 91);
    }

    /**
     * renvoie le token présent dans le nom de l'attribut permettant
     * de récupérer le type de la donnée
     * @return
     */
    public static String getTypeToken()
    {
        return typeToken;
    }

    /**
     * Renvoie le type date.
     * @return 2
     */
    public static int setTypeNum()
    {
        return 2;
    }

    /**
     * Renvoie le type date.
     * @return 93
     */
    public static int setTypeDate()
    {
        return 93;
    }

    /**
     * Récupère l'iterateur du hashmap
     * @return
     */
    public Iterator<Object> getIterator()
    {
        return keySet().iterator();
    }
}
