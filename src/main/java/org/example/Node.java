package org.example;

public class Node {

    private int val;
    private Node parent;
    private Node left;
    private Node right;

    public Node(int val, Node parent, Node left, Node right) {
        this.val = val;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public Node getLeft() {

        return left;
    }
    public Node getRight() {

        return right;
    }
    public Node getParent() {

        return parent;
    }

    public void setParent(Node parent) {

        this.parent = parent;
    }

    public void setRight(Node right) {

        this.right = right;
    }

    public void setLeft(Node left) {

        this.left = left;
    }


    public int getVal() {

        return val;
    }

    public void setVal(int val) {

        this.val = val;
    }

}
