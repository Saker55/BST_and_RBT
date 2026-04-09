//package org.example;
//
////TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
//// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//public class Main {
//    static void main(String[] args) {
//        tree BST = new BST();
//        tree RBTree = new RBTree();
//        validator v = new validator();
//        for (int i = 0; i < 10000; i++) {
//            BST.insert(i);
//            if (!v.test_tree(BST.getRoot(),true)){
//                System.out.println("error at " + (i+1) +" loop at BST");
//            }
//            RBTree.insert(i);
//            if (!v.test_tree(RBTree.getRoot(),false)){
//                System.out.println("error at " + (i+1) +" loop at RBT");
//            }
//        }
//        for (int i = 5000; i < 15000; i++) {
//            if((((i % 10000)== i) && BST.contains(i)) || (((i % 10000) != i) && !BST.contains(i)))
//            {
//                if (!v.test_tree(BST.getRoot(), true)) {
//                    System.out.println("error at " + (i + 1) + " loop at BST at deletion");
//                }
//            }
//            if((((i % 10000)== i) && RBTree.contains(i)) || (((i % 10000) != i) && !RBTree.contains(i)))
//            {
//                if (!v.test_tree(RBTree.getRoot(), false)) {
//                    System.out.println("error at " + (i + 1) + " loop at RBT at deletion");
//                }
//            }
//        }
//        for (int i = 0; i < 10000; i++) {
//            BST.delete(i);
//            if (!v.test_tree(BST.getRoot(),true)){
//                System.out.println("error at " + (i+1) +" loop at BST at deletion");
//            }
//            RBTree.delete(i);
//            if (!v.test_tree(RBTree.getRoot(),false)){
//                System.out.println("error at " + (i+1) +" loop at RBT at deletion");
//            }
//        }
//        System.out.println("finished testing");
//    }
//}
