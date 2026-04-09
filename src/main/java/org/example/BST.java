package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class BST extends tree {
    private boolean deleted;

    @Override
    public boolean insert(int v) {
        return insert(getRoot(), v) != null;
    }

    private Node insert(Node root, int key) {
        if (root == null) {
            return new Node(key, null, null, null);
        }

        if (key < root.getVal()) {
            Node left = insert(root.getLeft(), key);
            if (left != null) {
                root.setLeft(left);
                left.setParent(root);
            }
            else {
                return null;
            }
        }
        else if (key > root.getVal()) {
            Node right = insert(root.getRight(), key);
            if (right != null) {
                root.setRight(right);
                right.setParent(root);
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }

        return root;
    }

    @Override
    public boolean delete(int key) {
        deleted = false;
        setRoot(delete(getRoot(), key));
        return deleted;
    }

    private Node delete(Node root, int key) {
        if (root == null) return null;

        if (key < root.getVal()) {
            root.setLeft(delete(root.getLeft(), key));
            if (root.getLeft() != null) {
                root.getLeft().setParent(root);
            }
        } else if (key > root.getVal()) {
            root.setRight(delete(root.getRight(), key));
            if (root.getRight() != null) {
                root.getRight().setParent(root);
            }
        } else {
            deleted = true;
            return getReplacement(root);
        }

        return root;
    }

    private Node getReplacement(Node root) {
        if (root == null || (root.getLeft() == null && root.getRight() == null)) return null;
        if (root.getRight() == null) return root.getLeft();
        if (root.getLeft() == null) return root.getRight();
        Node t = root.getRight().getLeft(), prev = root.getRight();
        if (t == null) {
            prev.setLeft(root.getLeft());
            prev.setParent(root.getParent());
            return prev;
        }
        while (t.getLeft() != null) {
            prev = t;
            t = t.getLeft();
        }
        prev.setLeft(t.getRight());
        t.getParent().setLeft(null);
        t.setRight(root.getRight());
        t.setLeft(root.getLeft());
        t.setParent(root.getParent());
        return t;
    }

}
