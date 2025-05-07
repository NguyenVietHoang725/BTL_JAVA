package com.battleship.model.board;

public class Node {
    private final int x; // row
    private final int y; // col
    private boolean hasShip;
    private boolean isHit;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.hasShip = false;
        this.isHit = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isHasShip() { return hasShip; }
    public void setHasShip(boolean hasShip) { this.hasShip = hasShip; }
    public boolean isHit() { return isHit; }
    public void setHit(boolean hit) { isHit = hit; }
}