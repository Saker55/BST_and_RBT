package org.example;

public class RBNode extends Node{

    private boolean black;

    public boolean isblack() {

        return black;
    }

    public void setblack(boolean color) {

        this.black = color;
    }

    public RBNode(int val, Node parent, Node left, Node right,boolean black) {
        super(val, parent, left, right);
        this.black = black;
    }
}
