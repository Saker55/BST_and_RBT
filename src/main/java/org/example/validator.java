package org.example;

public class validator {
    public boolean test_tree(Node root,boolean isBST){
        if (isBST) {
            if (root == null) {
                return true;
            }
            else if (root.getLeft() == null && root.getRight() == null) {
                return true;
            } else if (root.getLeft() == null && root.getRight().getVal() > root.getVal()) {
                return test_tree(root.getRight(),isBST);
            }
            else if (root.getRight() == null && root.getLeft().getVal() < root.getVal()) {
                return test_tree(root.getLeft(),isBST);
            }
            else if (root.getRight().getVal() > root.getVal() && root.getLeft().getVal() < root.getVal()){
                return test_tree(root.getLeft(),isBST) && test_tree(root.getRight(),isBST);
            }
            else {
                return false;
            }
        }
        else {

            RBNode r = (RBNode) root;

            if (r == null) return true;

            if (r.getParent() == null && !r.isblack()) {
                return false;
            }

            if (!isValidBlackHeight(r)) {
                return false;
            }

            if (isNIL((RBNode) r.getLeft()) && isNIL((RBNode) r.getRight())) {
                return true;
            }

            else if (isNIL((RBNode) r.getLeft()) && !isNIL((RBNode) r.getRight())) {

                if (!r.isblack() && !((RBNode) r.getRight()).isblack()) {
                    return false;
                }

                return test_tree(r.getRight(), false);
            }

            else if (isNIL((RBNode) r.getRight()) && !isNIL((RBNode) r.getLeft())) {

                if (!r.isblack() && !((RBNode) r.getLeft()).isblack()) {
                    return false;
                }

                return test_tree(r.getLeft(), false);
            }
            else {

                if ((!r.isblack() && !((RBNode) r.getLeft()).isblack()) ||
                        (!r.isblack() && !((RBNode) r.getRight()).isblack())) {
                    return false;
                }

                return test_tree(r.getLeft(), false) && test_tree(r.getRight(), false);
            }
        }
    }


    private boolean isNIL(RBNode node) {

        return node == null || (node.isblack() && node.getLeft() == null && node.getRight() == null);
    }


    private boolean isValidBlackHeight(RBNode root) {
        return checkBlackHeight(root) != -1;
    }

    private int checkBlackHeight(RBNode node) {

        if (isNIL(node)) {
            return 1;
        }

        int leftBH = checkBlackHeight((RBNode) node.getLeft());
        int rightBH = checkBlackHeight((RBNode) node.getRight());

        if (leftBH == -1 || rightBH == -1 || leftBH != rightBH) {
            return -1;
        }

        if (node.isblack()) {
            return leftBH + 1;
        } else {
            return leftBH;
        }
    }
}
