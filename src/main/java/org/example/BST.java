package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BST extends tree {

    private static final Logger log = LoggerFactory.getLogger(BST.class);

    private boolean deleted;

    @Override
    public boolean insert(int v) {
        log.debug("insert({})", v);
        Node result = insert(getRoot(), v);
        if (result == null) {
            log.debug("insert({}) — duplicate, ignored", v);
            return false;
        }
        setRoot(result);
        return true;
    }

    private Node insert(Node root, int key) {
        if (root == null) {
            log.debug("insert: creating new node with key={}", key);
            return new Node(key, null, null, null);
        }

        if (key < root.getVal()) {
            Node left = insert(root.getLeft(), key);
            if (left != null) {
                root.setLeft(left);
                left.setParent(root);
            } else {
                return null;
            }
        } else if (key > root.getVal()) {
            Node right = insert(root.getRight(), key);
            if (right != null) {
                root.setRight(right);
                right.setParent(root);
            } else {
                return null;
            }
        } else {
            return null; // duplicate
        }

        return root;
    }

    @Override
    public boolean delete(int key) {
        log.debug("delete({})", key);
        deleted = false;
        setRoot(delete(getRoot(), key));
        if (!deleted) {
            log.debug("delete({}) — not found", key);
        }
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
            log.debug("delete: found node {}, replacing", key);
            deleted = true;
            return getReplacement(root);
        }

        return root;
    }

    private Node getReplacement(Node root) {
        if (root == null || (root.getLeft() == null && root.getRight() == null)) return null;
        if (root.getRight() == null) return root.getLeft();
        if (root.getLeft() == null) return root.getRight();

        Node t = root.getRight().getLeft();
        Node prev = root.getRight();

        if (t == null) {
            prev.setLeft(root.getLeft());
            prev.setParent(root.getParent());
            log.debug("getReplacement: direct right child {} takes over", prev.getVal());
            return prev;
        }

        while (t.getLeft() != null) {
            prev = t;
            t = t.getLeft();
        }

        log.debug("getReplacement: in-order successor {} takes over", t.getVal());
        prev.setLeft(t.getRight());
        t.getParent().setLeft(null);
        t.setRight(root.getRight());
        t.setLeft(root.getLeft());
        t.setParent(root.getParent());
        return t;
    }
}