package org.scrum.psd.battleship.controller.dto;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Ship {
    private boolean isPlaced;
    private String name;
    private int size;
    private List<Position> positions;
    private Color color;

    public Ship() {
        this.positions = new ArrayList<>();
    }

    public Ship(String name, int size) {
        this();

        this.name = name;
        this.size = size;
    }

    public Ship(String name, int size, List<Position> positions) {
        this(name, size);

        this.positions = positions;
    }

    public Ship(String name, int size, Color color) {
        this(name, size);

        this.color = color;
    }

    public void addPosition(String input) {
        if (positions == null) {
            positions = new ArrayList<>();
        }

        Letter letter;

        String columnInput = input.toUpperCase().substring(0, 1);

        try {
             letter = Letter.valueOf(columnInput);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Position is out of bounds.");
        }

        //Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1));

        if (!isValidPosition(new Position(letter, number))) {
            throw new IllegalArgumentException("Position is out of bounds.");
        }
        if (positions.contains(new Position(letter, number))) {
            throw new IllegalArgumentException("Position already occupied by this ship.");
        }

        positions.add(new Position(letter, number));

    }

    public void removePosition(Position input) {
        Letter letter = Letter.valueOf(input.toString().toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.toString().substring(1));
        positions.remove(new Position(letter, number));
    }

    private boolean isValidPosition(Position position) {
        // Assuming Position class has getColumn() and getRow() methods
        Letter column = position.getColumn();
        int row = position.getRow();

        // Validate the column is within the Enum class Letter
        //EnumSet<Letter> validColumns = EnumSet.allOf(Letter.class);
        //if (!validColumns.contains(column)) {
        //    return false;
        //}

        // Validate the row is between 1 and 8
        if (row < 1 || row > 8) {
            return false;
        }

        return true;
    }




    public boolean checkSunk(){
        for (Position position : positions) {
            if ( ! position.getHit() ) {
                return false;
            }
        }        
        return true;
    }

    // TODO: property change listener implementieren

    public boolean isPlaced() {
        return isPlaced;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
