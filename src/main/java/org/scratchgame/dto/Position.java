package org.scratchgame.dto;

import lombok.Data;

/* This class represents a position on a 2D grid, with row and column coordinates. */
@Data
public class Position {
    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return row + ":" + column;
    }

    public static Position fromString(String position) {
        String[] parts = position.split(":");
        return new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
}
