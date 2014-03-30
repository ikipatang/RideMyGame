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
    public int getPoxY()
    {
        return iPosY;
    }
}
