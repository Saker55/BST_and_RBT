package org.example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RBTree extends tree{

    @Override
    public boolean insert(int v) {
        RBNode y = null;
        RBNode z = new RBNode(v,null,null, null, false);
        RBNode x = new RBNode(getRoot().getVal(),getRoot().getParent(),getRoot().getLeft(), getRoot().getRight(), true);
        while (x != null) {
            y = x;
            if (z.getVal() < x.getVal()) {
                x = (RBNode) x.getLeft();
            }
            else {
                x = (RBNode) x.getRight();
            }
        }
        z.setParent(y);
        if (y == null) {
            setRoot(z);
        }
        else if (z.getVal() < y.getVal()) {
            y.setLeft(z);
        }
        else if (z.getVal() > y.getVal()) {
            y.setRight(z);
        }
        else {
            return false;
        }
        z.setLeft(null);
        z.setRight(null);
        z.setblack(false);
        InsertFixup(z);
        return true ;
    }

    @Override
    boolean delete(int v) {
        RBNode y = null;
        RBNode x = new RBNode(getRoot().getVal(),getRoot().getParent(),getRoot().getLeft(), getRoot().getRight(), true);
        while (x != null && x.getVal() != v) {
            y = x;
            if (v < x.getVal()) {
                x = (RBNode) x.getLeft();
            }
            else {
                x = (RBNode) x.getRight();
            }
        }

        if (x == null){
            return false;
        }

        delete(x);
        return true;
    }

    private void InsertFixup(RBNode z){
        while (!(((RBNode) z.getParent()).isblack()))
            if (z.getParent() == z.getParent().getParent().getLeft()) {
                RBNode y = (RBNode) (z.getParent().getParent().getRight());
                if (!y.isblack()) {
                    ((RBNode) z.getParent()).setblack(true);                    // case 1
                    y.setblack(true);                                           // case 1
                   ((RBNode) z.getParent().getParent()).setblack(false);        // case 1
                    z = (RBNode) (z.getParent().getParent());                   // case 1
                }                                                               // case 1
                else if (z == z.getParent().getRight()) {
                    z = (RBNode) z.getParent();                                 // case 2
                    leftRotate(z);                                              // case 2
                }                                                               // case 2
                ((RBNode) z.getParent()).setblack(true);                        // case 3
                ((RBNode) z.getParent().getParent()).setblack(false);           // case 3
                rightRotate(z.getParent().getParent());                         // case 3
            }                                                                   // case 3
            else {
                RBNode y = (RBNode) (z.getParent().getParent().getLeft());
                if (!y.isblack()) {
                    ((RBNode) z.getParent()).setblack(true);                    // case 1
                    y.setblack(true);                                           // case 1
                    ((RBNode) z.getParent().getParent()).setblack(false);       // case 1
                    z = (RBNode) (z.getParent().getParent());                   // case 1
                }                                                               // case 1
                else if (z == z.getParent().getLeft()) {
                    z = (RBNode) z.getParent();                                 // case 2
                    leftRotate(z);                                              // case 2
                }                                                               // case 2
                ((RBNode) z.getParent()).setblack(true);                        // case 3
                ((RBNode) z.getParent().getParent()).setblack(false);           // case 3
                rightRotate(z.getParent().getParent());
            }
        ((RBNode)getRoot()).setblack(true);
    }


    private void delete(RBNode z) {
        RBNode y = z;
        RBNode x;
        boolean y_original_color = y.isblack();
        if (z.getLeft() == null) {
            x = (RBNode) z.getRight();
            transplant(z, z.getRight());
        }
        else if (z.getRight() == null) {
            x = (RBNode) z.getLeft();
            transplant(z, z.getLeft());
        }
        else {
            y = (RBNode) min(z.getRight());
            y_original_color = y.isblack();
            x = (RBNode) y.getRight();
            if (y.getParent() == z) {
                x.setParent(y);
            }
            else {
                transplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
                transplant( z, y);
                y.setLeft(z.getLeft());
                y.getLeft().setParent(y);
                y.setblack(z.isblack());
        }
        if( y_original_color) {
            deleteFixup(x);
        }
    }

    private void deleteFixup(RBNode x){
        while (x != getRoot() && x.isblack()) {
            if (x == x.getParent().getLeft()) {
                RBNode w = (RBNode)x.getParent().getRight();
                if (!w.isblack()) {
                    w.setblack(true);                         // case 1
                    ((RBNode)x.getParent()).setblack(false) ;                        // case 1
                    leftRotate(x.getParent());                      // case 1
                    w = (RBNode) x.getParent().getRight();
                }// case 1
                if (((RBNode) w.getLeft()).isblack() && ((RBNode)w.getRight()).isblack())
                {
                    w.setblack(false);                            // case 2
                    x = (RBNode) x.getParent();
                }                // case 2
                else if (((RBNode)w.getRight()).isblack()) {
                    ((RBNode) w.getLeft()).setblack(true);                     // case 3
                    w.setblack(false);                            // case 3
                    rightRotate(w);                       // case 3
                    w = (RBNode) x.getParent().getRight();
                }                          // case 3
                w.setblack(((RBNode) x.getParent()).isblack());                         // case 4
                ((RBNode) x.getParent()).setblack(true);                            // case 4
                ((RBNode) w.getRight()).setblack(true);                        // case 4
                leftRotate(x.getParent());                          // case 4
                x = (RBNode) getRoot();
            } else {
                RBNode w = (RBNode)x.getParent().getLeft();
                if (!w.isblack()) {
                    w.setblack(true);                         // case 1
                    ((RBNode)x.getParent()).setblack(false) ;                        // case 1
                    leftRotate(x.getParent());                      // case 1
                    w = (RBNode) x.getParent().getLeft();
                }// case 1
                if (((RBNode) w.getRight()).isblack() && ((RBNode)w.getLeft()).isblack())
                {
                    w.setblack(false);                            // case 2
                    x = (RBNode) x.getParent();
                }                // case 2
                else if (((RBNode)w.getLeft()).isblack()) {
                    ((RBNode) w.getRight()).setblack(true);                     // case 3
                    w.setblack(false);                            // case 3
                    rightRotate(w);                       // case 3
                    w = (RBNode) x.getParent().getLeft();
                }                          // case 3
                w.setblack(((RBNode) x.getParent()).isblack());                         // case 4
                ((RBNode) x.getParent()).setblack(true);                            // case 4
                ((RBNode) w.getRight()).setblack(true);                        // case 4
                leftRotate(x.getParent());                          // case 4
                x = (RBNode) getRoot();
            }
        }
        x.setblack(true);
    }

    private Node min(Node x) {
        Node y = x;
        while (y.getLeft() != null){
            y = y.getLeft();
        }
        return y;
    }

    private void leftRotate(Node x){
        Node y = x.getRight();
        x.setRight(y.getLeft());

        if( y.getLeft() != null){
            y.getLeft().setParent(x);
        }

        y.setParent(x.getParent());

        if (x.getParent() == null){
            setRoot(y);
        }
        else if(x == x.getParent().getLeft()){
            x.getParent().setLeft(y);
        }
        else {
            x.getParent().setRight(y);
        }
        y.setLeft(x);
        x.setParent(y);
    }




    private void rightRotate(Node y){
        Node x = y.getLeft();
        y.setLeft(x.getRight());

        if( x.getRight() != null){
            x.getRight().setParent(y);
        }

        x.setParent(y.getParent());

        if (y.getParent() == null){
            setRoot(x);
        }
        else if(y == y.getParent().getLeft()){
            y.getParent().setLeft(x);
        }
        else {
            y.getParent().setRight(x);
        }

        x.setRight(y);
        y.setParent(x);
    }

    private void transplant(Node u,Node v) {
        if (u.getParent() == null) {
            setRoot(v);
        }
        else if (u == u.getParent().getLeft())
        {
            u.getParent().setLeft(v);
        }
        else{
            u.getParent().setRight(v);
        }
        v.setParent(u.getParent());
    }
}
