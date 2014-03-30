package model;

public class ModelPosition
{
    private int iPosX;
    private int iPosY;

    public ModelPosition(int posX, int posY)
    {
        setPosX(posX);
        setPoxY(posY);
    }

    /**
     * @param posX the posX to set
     */
    public void setPosX(int posX)
    {
        this.iPosX = posX;
    }

    /**
     * @return the posX
     */
    public int getPosX()
    {
        return iPosX;
    }

    /**
     * @param poxY the poxY to set
     */
    public void setPoxY(int poxY)
    {
        this.iPosY = poxY;
    }

    /**
     * @return the poxY
     */
    public int getPosY()
    {
        return iPosY;
    }

    /**
     * @return
     */
    public String getText()
    {
        String sPosition = "";

        // Si on a une position de définit
        if(getPosX() > 0 || getPosY() > 0){
            sPosition = getPosX() + ":" + getPosY();
        }

        return sPosition;
    }
}
