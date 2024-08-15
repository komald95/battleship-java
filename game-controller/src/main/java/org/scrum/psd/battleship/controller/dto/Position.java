package org.scrum.psd.battleship.controller.dto;

public class Position {
    private Letter column;
    private int row;
    public boolean isHit;

    public Position() {
        super();
    }

    public Position(Letter column, int row) {
        this();

        this.column = column;
        this.row = row;
        this.isHit = false;
    }

    public Letter getColumn() {
        return column;
    }

    public void setColumn(Letter column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean getHit() {
        return isHit;
    }

    public void setHit(boolean isHit) {
        this.isHit = isHit;
    }

    @Override
    public String toString() {
        return column.name() + row; // Example: "A3"
    }


    @Override public boolean equals(Object o) {
        if(o instanceof Position) {
            Position position = (Position) o;

            if(position == null) {
                return false;
            }

            return position.column.equals(column) && position.row == row;
        }

        return false;
    }
}
