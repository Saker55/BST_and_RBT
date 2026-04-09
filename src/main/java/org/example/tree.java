package org.example;

import static java.lang.Math.max;

public abstract class tree {
    private Node root;
    private int[] arr;
    private int index;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }


    abstract boolean insert(int v);
    abstract boolean delete(int v);

    boolean contains(int v){
        return contains(root,v);
    }

    boolean contains(Node r, int v){
        if (r == null){
            return false;
        }
        else if (r.getVal() > v) {
            return contains(r.getLeft(),v);
        }
        else if (r.getVal() < v) {
            return contains(r.getRight(),v);
        }
        else {
            return true;
        }
    }

    int height(){
        return height(root);
    }
    int height(Node r){
       if (r == null) {
           return 0;
       }

       return max(height(r.getLeft()),height(r.getRight()))+1;
    }

    int size(){
        return  size(root);
    }

    private int size(Node r){
        if (r == null) {
            return 0;
        }

        return size(r.getLeft()) + size(r.getRight()) + 1 ;
    }

    public int[] inOrder() {
        this.arr = new int[size(this.root)];
        this.index = 0;
        inOrder(getRoot());
        return this.arr;
    }

    private void inOrder(Node root) {

        if (root == null) {
            return;
        }

        inOrder(root.getLeft());

        arr[index] = root.getVal();
        index++;

        inOrder(root.getRight());
    }
}
