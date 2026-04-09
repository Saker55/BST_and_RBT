package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main(String[] args) {
        tree BST = new BST();
        tree RBTree = new RBTree();
        validator v = new validator();
        for (int i = 0; i < 10000; i++) {
            BST.insert(i);
            if (!v.test_tree(BST.getRoot(),true)){
                System.out.println("error at " + (i+1) +" loop at BST");
            }
            RBTree.insert(i);
            if (!v.test_tree(RBTree.getRoot(),false)){
                System.out.println("error at " + (i+1) +" loop at RBT");
            }
        }
        System.out.println("finished testing");
    }
}
