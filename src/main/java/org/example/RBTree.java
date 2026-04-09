package org.example;

public class RBTree extends tree {

    private final RBNode NIL;

    public RBTree() {
        NIL = new RBNode(0, null, null, null, true); // black NIL
        setRoot(NIL);
    }

    @Override
    public boolean insert(int v) {
        RBNode z = new RBNode(v, NIL, NIL, NIL, false);
        RBNode y = NIL;
        RBNode x = (RBNode) getRoot();

        while (x != NIL) {
            y = x;
            if (z.getVal() < x.getVal()) {
                x = (RBNode) x.getLeft();
            } else if (z.getVal() > x.getVal()) {
                x = (RBNode) x.getRight();
            } else {
                return false;
            }
        }

        z.setParent(y);

        if (y == NIL) {
            setRoot(z);
        } else if (z.getVal() < y.getVal()) {
            y.setLeft(z);
        } else {
            y.setRight(z);
        }

        InsertFixup(z);
        return true;
    }

    private void InsertFixup(RBNode z) {
        while (!((RBNode) z.getParent()).isblack()) {
            if (z.getParent() == z.getParent().getParent().getLeft()) {
                RBNode y = (RBNode) z.getParent().getParent().getRight();

                if (!y.isblack()) {
                    ((RBNode) z.getParent()).setblack(true);
                    y.setblack(true);
                    ((RBNode) z.getParent().getParent()).setblack(false);
                    z = (RBNode) z.getParent().getParent();
                } else {
                    if (z == z.getParent().getRight()) {
                        z = (RBNode) z.getParent();
                        leftRotate(z);
                    }
                    ((RBNode) z.getParent()).setblack(true);
                    ((RBNode) z.getParent().getParent()).setblack(false);
                    rightRotate(z.getParent().getParent());
                }
            } else {
                RBNode y = (RBNode) z.getParent().getParent().getLeft();

                if (!y.isblack()) {
                    ((RBNode) z.getParent()).setblack(true);
                    y.setblack(true);
                    ((RBNode) z.getParent().getParent()).setblack(false);
                    z = (RBNode) z.getParent().getParent();
                } else {
                    if (z == z.getParent().getLeft()) {
                        z = (RBNode) z.getParent();
                        rightRotate(z);
                    }
                    ((RBNode) z.getParent()).setblack(true);
                    ((RBNode) z.getParent().getParent()).setblack(false);
                    leftRotate(z.getParent().getParent());
                }
            }
        }
        ((RBNode) getRoot()).setblack(true);
    }

    @Override
    boolean delete(int v) {
        RBNode z = (RBNode) getRoot();

        while (z != NIL && z.getVal() != v) {
            if (v < z.getVal()) {
                z = (RBNode) z.getLeft();
            } else {
                z = (RBNode) z.getRight();
            }
        }

        if (z == NIL) return false;

        delete(z);
        return true;
    }

    private void delete(RBNode z) {
        RBNode y = z;
        RBNode x;
        boolean yOriginalBlack = y.isblack();

        if (z.getLeft() == NIL) {
            x = (RBNode) z.getRight();
            transplant(z, z.getRight());
        } else if (z.getRight() == NIL) {
            x = (RBNode) z.getLeft();
            transplant(z, z.getLeft());
        } else {
            y = (RBNode) min(z.getRight());
            yOriginalBlack = y.isblack();
            x = (RBNode) y.getRight();

            if (y.getParent() == z) {
                x.setParent(y);
            } else {
                transplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }

            transplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            y.setblack(z.isblack());
        }

        if (yOriginalBlack) {
            deleteFixup(x);
        }
    }

    private void deleteFixup(RBNode x) {
        while (x != getRoot() && x.isblack()) {
            if (x == x.getParent().getLeft()) {
                RBNode w = (RBNode) x.getParent().getRight();

                if (!w.isblack()) {
                    w.setblack(true);
                    ((RBNode) x.getParent()).setblack(false);
                    leftRotate(x.getParent());
                    w = (RBNode) x.getParent().getRight();
                }

                if (((RBNode) w.getLeft()).isblack() &&
                        ((RBNode) w.getRight()).isblack()) {
                    w.setblack(false);
                    x = (RBNode) x.getParent();
                } else {
                    if (((RBNode) w.getRight()).isblack()) {
                        ((RBNode) w.getLeft()).setblack(true);
                        w.setblack(false);
                        rightRotate(w);
                        w = (RBNode) x.getParent().getRight();
                    }

                    w.setblack(((RBNode) x.getParent()).isblack());
                    ((RBNode) x.getParent()).setblack(true);
                    ((RBNode) w.getRight()).setblack(true);
                    leftRotate(x.getParent());
                    x = (RBNode) getRoot();
                }
            } else {
                RBNode w = (RBNode) x.getParent().getLeft();

                if (!w.isblack()) {
                    w.setblack(true);
                    ((RBNode) x.getParent()).setblack(false);
                    rightRotate(x.getParent());
                    w = (RBNode) x.getParent().getLeft();
                }

                if (((RBNode) w.getRight()).isblack() &&
                        ((RBNode) w.getLeft()).isblack()) {
                    w.setblack(false);
                    x = (RBNode) x.getParent();
                } else {
                    if (((RBNode) w.getLeft()).isblack()) {
                        ((RBNode) w.getRight()).setblack(true);
                        w.setblack(false);
                        leftRotate(w);
                        w = (RBNode) x.getParent().getLeft();
                    }

                    w.setblack(((RBNode) x.getParent()).isblack());
                    ((RBNode) x.getParent()).setblack(true);
                    ((RBNode) w.getLeft()).setblack(true);
                    rightRotate(x.getParent());
                    x = (RBNode) getRoot();
                }
            }
        }
        x.setblack(true);
    }

    private Node min(Node x) {
        while (x.getLeft() != NIL) {
            x = x.getLeft();
        }
        return x;
    }

    private void leftRotate(Node x) {
        Node y = x.getRight();
        x.setRight(y.getLeft());

        if (y.getLeft() != NIL) {
            y.getLeft().setParent(x);
        }

        y.setParent(x.getParent());

        if (x.getParent() == NIL) {
            setRoot(y);
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }

        y.setLeft(x);
        x.setParent(y);
    }

    private void rightRotate(Node y) {
        Node x = y.getLeft();
        y.setLeft(x.getRight());

        if (x.getRight() != NIL) {
            x.getRight().setParent(y);
        }

        x.setParent(y.getParent());

        if (y.getParent() == NIL) {
            setRoot(x);
        } else if (y == y.getParent().getLeft()) {
            y.getParent().setLeft(x);
        } else {
            y.getParent().setRight(x);
        }

        x.setRight(y);
        y.setParent(x);
    }

    private void transplant(Node u, Node v) {
        if (u.getParent() == NIL) {
            setRoot(v);
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        v.setParent(u.getParent());
    }
}