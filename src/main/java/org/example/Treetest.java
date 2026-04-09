package org.example;

import org.junit.jupiter.api.*;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


class TreeTest {


    private static BST    bst()  { return new BST(); }
    private static RBTree rbt()  { return new RBTree(); }

    //  INSERT


    @Test void bst_insert_single() { insertSingle(bst()); }
    @Test void rbt_insert_single() { insertSingle(rbt()); }
    private void insertSingle(tree t) {
        assertTrue(t.insert(10));
        assertEquals(1, t.size());
        assertTrue(t.contains(10));
    }

    @Test void bst_insert_duplicate_returns_false() { insertDuplicate(bst()); }
    @Test void rbt_insert_duplicate_returns_false() { insertDuplicate(rbt()); }
    private void insertDuplicate(tree t) {
        assertTrue(t.insert(5));
        assertFalse(t.insert(5));   // duplicate
        assertEquals(1, t.size());
    }

    @Test void bst_insert_multiple_unique() { insertMultiple(bst()); }
    @Test void rbt_insert_multiple_unique() { insertMultiple(rbt()); }
    private void insertMultiple(tree t) {
        int[] vals = {5, 3, 7, 1, 4, 6, 8};
        for (int v : vals) assertTrue(t.insert(v));
        assertEquals(vals.length, t.size());
    }

    @Test void bst_insert_ascending_order() { insertAscending(bst()); }
    @Test void rbt_insert_ascending_order() { insertAscending(rbt()); }
    private void insertAscending(tree t) {
        for (int i = 1; i <= 10; i++) t.insert(i);
        assertEquals(10, t.size());
        for (int i = 1; i <= 10; i++) assertTrue(t.contains(i));
    }

    @Test void bst_insert_descending_order() { insertDescending(bst()); }
    @Test void rbt_insert_descending_order() { insertDescending(rbt()); }
    private void insertDescending(tree t) {
        for (int i = 10; i >= 1; i--) t.insert(i);
        assertEquals(10, t.size());
    }

    //  CONTAINS

    @Test void bst_contains_present()    { containsPresent(bst()); }
    @Test void rbt_contains_present()    { containsPresent(rbt()); }
    private void containsPresent(tree t) {
        t.insert(42);
        assertTrue(t.contains(42));
    }

    @Test void bst_contains_absent()    { containsAbsent(bst()); }
    @Test void rbt_contains_absent()    { containsAbsent(rbt()); }
    private void containsAbsent(tree t) {
        t.insert(42);
        assertFalse(t.contains(99));
    }

    @Test void bst_contains_empty_tree()    { containsEmpty(bst()); }
    @Test void rbt_contains_empty_tree()    { containsEmpty(rbt()); }
    private void containsEmpty(tree t) {
        assertFalse(t.contains(1));
    }

    //  DELETE

    @Test void bst_delete_leaf()    { deleteLeaf(bst()); }
    @Test void rbt_delete_leaf()    { deleteLeaf(rbt()); }
    private void deleteLeaf(tree t) {
        t.insert(10); t.insert(5); t.insert(15);
        assertTrue(t.delete(5));
        assertFalse(t.contains(5));
        assertEquals(2, t.size());
    }

    @Test void bst_delete_node_with_one_child()    { deleteOneChild(bst()); }
    @Test void rbt_delete_node_with_one_child()    { deleteOneChild(rbt()); }
    private void deleteOneChild(tree t) {
        t.insert(10); t.insert(5); t.insert(3);
        assertTrue(t.delete(5));
        assertFalse(t.contains(5));
        assertTrue(t.contains(3));
        assertTrue(t.contains(10));
        assertEquals(2, t.size());
    }

    @Test void bst_delete_node_with_two_children()    { deleteTwoChildren(bst()); }
    @Test void rbt_delete_node_with_two_children()    { deleteTwoChildren(rbt()); }
    private void deleteTwoChildren(tree t) {
        t.insert(10); t.insert(5); t.insert(15); t.insert(3); t.insert(7);
        assertTrue(t.delete(5));
        assertFalse(t.contains(5));
        assertTrue(t.contains(3));
        assertTrue(t.contains(7));
        assertEquals(4, t.size());
    }

    @Test void bst_delete_root()    { deleteRoot(bst()); }
    @Test void rbt_delete_root()    { deleteRoot(rbt()); }
    private void deleteRoot(tree t) {
        t.insert(10);
        assertTrue(t.delete(10));
        assertFalse(t.contains(10));
        assertEquals(0, t.size());
    }

    @Test void bst_delete_absent_returns_false()    { deleteAbsent(bst()); }
    @Test void rbt_delete_absent_returns_false()    { deleteAbsent(rbt()); }
    private void deleteAbsent(tree t) {
        t.insert(10);
        assertFalse(t.delete(99));
        assertEquals(1, t.size());
    }

    @Test void bst_delete_from_empty_returns_false()    { deleteFromEmpty(bst()); }
    @Test void rbt_delete_from_empty_returns_false()    { deleteFromEmpty(rbt()); }
    private void deleteFromEmpty(tree t) {
        assertFalse(t.delete(1));
    }

    @Test void bst_delete_all_elements()    { deleteAll(bst()); }
    @Test void rbt_delete_all_elements()    { deleteAll(rbt()); }
    private void deleteAll(tree t) {
        int[] vals = {5, 3, 7, 1, 4, 6, 8};
        for (int v : vals) t.insert(v);
        for (int v : vals) {
            assertTrue(t.delete(v), "Failed to delete " + v);
        }
        assertEquals(0, t.size());
        assertEquals(0, t.height());
    }

    //  IN-ORDER  (sorted output)

    @Test void bst_inOrder_sorted()    { inOrderSorted(bst()); }
    @Test void rbt_inOrder_sorted()    { inOrderSorted(rbt()); }
    private void inOrderSorted(tree t) {
        int[] vals = {5, 3, 7, 1, 4, 6, 8};
        for (int v : vals) t.insert(v);
        int[] result = t.inOrder();
        int[] expected = vals.clone();
        Arrays.sort(expected);
        assertArrayEquals(expected, result);
    }

    @Test void bst_inOrder_empty_returns_empty_array()    { inOrderEmpty(bst()); }
    @Test void rbt_inOrder_empty_returns_empty_array()    { inOrderEmpty(rbt()); }
    private void inOrderEmpty(tree t) {
        assertArrayEquals(new int[0], t.inOrder());
    }

    @Test void bst_inOrder_single_element()    { inOrderSingle(bst()); }
    @Test void rbt_inOrder_single_element()    { inOrderSingle(rbt()); }
    private void inOrderSingle(tree t) {
        t.insert(42);
        assertArrayEquals(new int[]{42}, t.inOrder());
    }

    @Test void bst_inOrder_after_delete()    { inOrderAfterDelete(bst()); }
    @Test void rbt_inOrder_after_delete()    { inOrderAfterDelete(rbt()); }
    private void inOrderAfterDelete(tree t) {
        for (int v : new int[]{5, 3, 7, 1, 4, 6, 8}) t.insert(v);
        t.delete(3);
        int[] result = t.inOrder();
        for (int i = 1; i < result.length; i++) {
            assertTrue(result[i] > result[i - 1],
                    "Not sorted after delete at index " + i);
        }
    }

    //  HEIGHT

    @Test void bst_height_empty_is_zero()    { heightEmpty(bst()); }
    @Test void rbt_height_empty_is_zero()    { heightEmpty(rbt()); }
    private void heightEmpty(tree t) {
        assertEquals(0, t.height());
    }

    @Test void bst_height_single_node()    { heightSingle(bst()); }
    @Test void rbt_height_single_node()    { heightSingle(rbt()); }
    private void heightSingle(tree t) {
        t.insert(10);
        assertEquals(1, t.height());
    }

    @Test void rbt_height_balanced_log_n() {
        // An RBT with n nodes has height ≤ 2*log2(n+1)
        RBTree t = new RBTree();
        int n = 1000;
        for (int i = 1; i <= n; i++) t.insert(i);
        int maxAllowed = 2 * (int) (Math.log(n + 1) / Math.log(2)) + 2;
        assertTrue(t.height() <= maxAllowed,
                "RBT height " + t.height() + " exceeds 2*log2(" + n + "+1)=" + maxAllowed);
    }

    @Test void bst_sorted_input_worst_case_height() {
        BST t = new BST();
        int n = 100;
        for (int i = 1; i <= n; i++) t.insert(i);
        assertEquals(n, t.height());   // degenerate linked list
    }

    //  SIZE

    @Test void bst_size_tracks_insert_delete()    { sizeTracking(bst()); }
    @Test void rbt_size_tracks_insert_delete()    { sizeTracking(rbt()); }
    private void sizeTracking(tree t) {
        assertEquals(0, t.size());
        t.insert(1); assertEquals(1, t.size());
        t.insert(2); assertEquals(2, t.size());
        t.insert(2); assertEquals(2, t.size()); // duplicate ignored
        t.delete(1); assertEquals(1, t.size());
        t.delete(99);assertEquals(1, t.size()); // missing ignored
    }

    //  RBT STRUCTURAL INVARIANTS

    @Test void rbt_root_is_always_black() {
        RBTree t = rbt();
        t.insert(10);
        assertTrue(((RBNode) t.getRoot()).isblack(), "Root must be black");
        t.insert(5); t.insert(15);
        assertTrue(((RBNode) t.getRoot()).isblack(), "Root must stay black after inserts");
    }

    @Test void rbt_no_red_red_violations_random() {
        RBTree t = rbt();
        Random rng = new Random(42);
        for (int i = 0; i < 500; i++) t.insert(rng.nextInt(1000));
        validator v = new validator();
        assertTrue(v.test_tree(t.getRoot(), false),
                "Red-red or black-height violation after 500 random inserts");
    }

    @Test void rbt_invariants_hold_after_deletes() {
        RBTree t = rbt();
        Random rng = new Random(7);
        int[] vals = new int[300];
        for (int i = 0; i < vals.length; i++) { vals[i] = rng.nextInt(500); t.insert(vals[i]); }
        for (int i = 0; i < 100; i++) t.delete(vals[rng.nextInt(vals.length)]);
        validator v = new validator();
        assertTrue(v.test_tree(t.getRoot(), false),
                "RBT invariant violation after mixed insert/delete");
    }

    //  BST STRUCTURAL INVARIANT

    @Test void bst_bst_property_random() {
        BST t = bst();
        Random rng = new Random(13);
        for (int i = 0; i < 300; i++) t.insert(rng.nextInt(1000));
        validator v = new validator();
        assertTrue(v.test_tree(t.getRoot(), true),
                "BST ordering property violated after random inserts");
    }

    //  LARGE-SCALE SMOKE TESTS

    @Test void bst_insert_contains_delete_1000_elements()    { smoke(bst()); }
    @Test void rbt_insert_contains_delete_1000_elements()    { smoke(rbt()); }
    private void smoke(tree t) {
        int n = 1000;
        Random rng = new Random(99);
        int[] vals = rng.ints(n, 0, 10_000).distinct().limit(n).toArray();
        for (int v : vals) t.insert(v);
        assertEquals(vals.length, t.size());
        for (int v : vals) assertTrue(t.contains(v));
        for (int i = 0; i < vals.length / 2; i++) t.delete(vals[i]);
        assertEquals(vals.length - vals.length / 2, t.size());
        for (int i = 0; i < vals.length / 2; i++) assertFalse(t.contains(vals[i]));
    }
}
