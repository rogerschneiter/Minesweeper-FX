package sample;

public class Options {
    private int difficulty = 1;
    private int rowCount = 20;
    private int columnCount = 20;

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        if (difficulty > 0 && difficulty <= 5) {
            this.difficulty = difficulty;
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        if (rowCount > 0 && rowCount <= 50) {
            this.rowCount = rowCount;
        }
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        if (columnCount > 0 && columnCount <= 50) {
            this.columnCount = columnCount;
        }
    }
}
