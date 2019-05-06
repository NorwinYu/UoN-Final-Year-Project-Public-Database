package Project2;

/**********************************************************************
 * Individual cell to be used in a Mine Sweeper game.
 *
 * @author Corbin Bremmeyr
 * @version 12 February 2019
 *********************************************************************/
public class Cell {

    /** Number of adjacent cells with mines */
    private int mineCount;

    /** Tell if player has flagged cell as a mine */
    private boolean isFlagged;

    /** Tell if cell's contents is shown */
    private boolean isExposed;

    /** Tell if cell holds a mine */
    private boolean isMine;

    /******************************************************************
     * Default constructor that makes empty cell.
     *****************************************************************/
    public Cell() {

        this.mineCount = 0;
        this.isFlagged = false;
        this.isExposed = false;
        this.isMine = false;
    }

    /******************************************************************
     * Getter for number of adjacent cells with mines.
     *
     * @return number of adjacent cells with mines.
     *****************************************************************/
    public int getMineCount() {
        return mineCount;
    }

    /******************************************************************
     * Setter for number of adjacent cells with mines. If input is
     * invalid, less than 0, the value is set to zero.
     *
     * @param mineCount Number of adjacent cells with mines to be set.
     *                  Only non-negative values are valid.
     *****************************************************************/
    public void setMineCount(int mineCount) {

        if(mineCount >= 0) {
            this.mineCount = mineCount;
        }
        else {
            this.mineCount = 0;
        }
    }

    /******************************************************************
     * Getter for seeing if cell is flagged as a mine.
     *
     * @return if cell is flagged as a mine.
     *****************************************************************/
    public boolean isFlagged() {
        return isFlagged;
    }

    /******************************************************************
     * Set if the cell is flagged as a mine.
     *
     * @param flagged value to be set if the cell is flagged as a mine.
     *****************************************************************/
    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    /******************************************************************
     * Getter for seeing if the cell is exposed.
     *
     * @return if the cell is exposed.
     *****************************************************************/
    public boolean isExposed() {
        return isExposed;
    }

    /******************************************************************
     * Set if the cell is exposed.
     *
     * @param exposed if the cell is to be exposed.
     *****************************************************************/
    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    /******************************************************************
     * Getter to see if the cell is holding a mine.
     *
     * @return if the cell is holding a mine.
     *****************************************************************/
    public boolean isMine() {
        return isMine;
    }

    /******************************************************************
     * Set if the cell holds a mine.
     *
     * @param mine if the cell is to hold a mine.
     *****************************************************************/
    public void setMine(boolean mine) {
        isMine = mine;
    }
}
