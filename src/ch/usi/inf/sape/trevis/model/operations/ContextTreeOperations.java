/*
 * This file is licensed to You under the "Simplified BSD License".
 * You may not use this software except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/bsd-license.php
 *
 * See the COPYRIGHT file distributed with this work for information
 * regarding copyright ownership.
 */
package ch.usi.inf.sape.trevis.model.operations;

import java.util.ArrayList;
import java.util.Comparator;

import ch.usi.inf.sape.trevis.model.ContextTree;

/**
 * ContextTreeOperations allows you to clone a ContexTree, or to compute the union, intersection, or difference of two ContextTrees.
 * 
 * This is useful for example to compute the tree representing a whole cluster of trees (union), or a tree representing the intersection
 * between trees (for highlighting the common nodes).
 * 
 * This also is useful as a means of computing certain similarity metrics, or to highlight all nodes in two trees that are part of their
 * intersection.
 * 
 * @see http://www.google.com/url?sa=t&source=web&ct=res&cd=5&ved=0CCQQFjAE&url=http%3A%2F%2Feprints.pascal-network.org%2Farchive%2F00002510%2F01%2F01.pdf&ei=I1M2S6PtHceC_Qb84ISLCQ&usg=AFQjCNGUmvpL6sVtJ-zGMDCOTt1eJgE4iA&sig2=bc5-78GECwZjP9NQyq3a2Q
 */
public final class ContextTreeOperations {

    /**
     * Create a ContextTree<T>C, where each node nc, contains the union of the children of the corresponding node in A, na, and the
     * corresponding node in B, nb.
     */
    public static <T> ContextTree<T> union(final ContextTree<T> a, final ContextTree<T> b, final ContextTreeFactory<T> factory) {
        final T root = factory.unionNodes(a.getRoot(), b.getRoot());
        unionChildren(a, b, a.getRoot(), b.getRoot(), root, factory);
        final ContextTree<T> tree = factory.createTree(root);
        return tree;
    }

    private static <T> void unionChildren(final ContextTree<T> aTree, final ContextTree<T> bTree, final T aNode, final T bNode,
            final T outNode, final ContextTreeFactory<T> factory) {
        final ArrayList<T> aChildren = factory.getOrderedChildren(aNode);
        final ArrayList<T> bChildren = factory.getOrderedChildren(bNode);
        final Comparator<T> comparator = factory.getNodeComparator();
        int a = 0;
        int b = 0;
        while (a < aChildren.size() || b < bChildren.size()) {
            if (a < aChildren.size() && b < bChildren.size()) {
                final T aChild = aChildren.get(a);
                final T bChild = bChildren.get(b);
                final int result = comparator.compare(aChild, bChild);
                if (result == 0) {
                    // a==b
                    final T outChild = factory.unionNodes(aChild, bChild);
                    factory.connectParentAndChild(outNode, outChild);
                    unionChildren(aTree, bTree, aChild, bChild, outChild, factory);
                    a++;
                    b++;
                } else if (result < 0) {
                    // a<b
                    final T outChild = factory.cloneNode(aChild);
                    factory.connectParentAndChild(outNode, outChild);
                    cloneChildren(aTree, aChild, outChild, factory);
                    a++;
                } else {
                    // b<a
                    final T outChild = factory.cloneNode(bChild);
                    factory.connectParentAndChild(outNode, outChild);
                    cloneChildren(bTree, bChild, outChild, factory);
                    b++;
                }
            } else if (a < aChildren.size()) {
                final T aChild = aChildren.get(a);
                final T outChild = factory.cloneNode(aChild);
                factory.connectParentAndChild(outNode, outChild);
                cloneChildren(aTree, aChild, outChild, factory);
                a++;
            } else {
                final T bChild = bChildren.get(b);
                final T outChild = factory.cloneNode(bChild);
                factory.connectParentAndChild(outNode, outChild);
                cloneChildren(bTree, bChild, outChild, factory);
                b++;
            }
        }
    }

    /**
     * Create a ContextTree<T>C, where each node nc, contains the intersection of the children of the corresponding node in A, na, and the
     * corresponding node in B, nb.
     */
    public static <T> ContextTree<T> intersection(final ContextTree<T> a, final ContextTree<T> b, final ContextTreeFactory<T> factory) {
        final T root = factory.intersectNodes(a.getRoot(), b.getRoot());
        intersectChildren(a, b, a.getRoot(), b.getRoot(), root, factory);
        final ContextTree<T> tree = factory.createTree(root);
        return tree;
    }

    private static <T> void intersectChildren(final ContextTree<T> aTree, final ContextTree<T> bTree, final T aNode, final T bNode,
            final T outNode, final ContextTreeFactory<T> factory) {
        final ArrayList<T> aChildren = factory.getOrderedChildren(aNode);
        final ArrayList<T> bChildren = factory.getOrderedChildren(bNode);
        final Comparator<T> comparator = factory.getNodeComparator();
        int a = 0;
        int b = 0;
        while (a < aChildren.size() && b < bChildren.size()) {
            final T aChild = aChildren.get(a);
            final T bChild = bChildren.get(b);
            final int result = comparator.compare(aChild, bChild);
            if (result == 0) {
                // a==b
                final T outChild = factory.intersectNodes(aChild, bChild);
                factory.connectParentAndChild(outNode, outChild);
                intersectChildren(aTree, bTree, aChild, bChild, outChild, factory);
                a++;
                b++;
            } else if (result < 0) {
                // a<b
//                final T outChild = factory.cloneNode(aChild);
//                factory.connectParentAndChild(outNode, outChild);
//                cloneChildren(aTree, aChild, outChild, factory);
                a++;
            } else {
                // b<a
                b++;
            }
        }
    }

    /**
     * Create a ContextTree<T>C, where each node nc, contains the difference of the children of the corresponding node in A, na, and the
     * corresponding node in B, nb.
     */
    public static <T> ContextTree<T> subtract(final ContextTree<T> a, final ContextTree<T> b, final ContextTreeFactory<T> factory) {
        final T root = factory.subtractNodes(a.getRoot(), b.getRoot());
        subtractChildren(a, b, a.getRoot(), b.getRoot(), root, factory);
        final ContextTree<T> tree = factory.createTree(root);
        return tree;
    }

    private static <T> void subtractChildren(final ContextTree<T> aTree, final ContextTree<T> bTree, final T aNode, final T bNode,
            final T outNode, final ContextTreeFactory<T> factory) {
        final ArrayList<T> aChildren = factory.getOrderedChildren(aNode);
        final ArrayList<T> bChildren = factory.getOrderedChildren(bNode);
        final Comparator<T> comparator = factory.getNodeComparator();
        int a = 0;
        int b = 0;
        while (a < aChildren.size() || b < bChildren.size()) {
            if (a < aChildren.size() && b < bChildren.size()) {
                final T aChild = aChildren.get(a);
                final T bChild = bChildren.get(b);
                final int result = comparator.compare(aChild, bChild);
                if (result == 0) {
                    // a==b
                    final T outChild = factory.subtractNodes(aChild, bChild);
                    factory.connectParentAndChild(outNode, outChild);
                    subtractChildren(aTree, bTree, aChild, bChild, outChild, factory);
                    a++;
                    b++;
                } else if (result < 0) {
                    // a<b
                    final T outChild = factory.cloneNode(aChild);
                    factory.connectParentAndChild(outNode, outChild);
                    cloneChildren(aTree, aChild, outChild, factory);
                    a++;
                } else {
                    // b<a
                    b++;
                }
            } else if (a < aChildren.size()) {
                final T aChild = aChildren.get(a);
                final T outChild = factory.cloneNode(aChild);
                factory.connectParentAndChild(outNode, outChild);
                cloneChildren(aTree, aChild, outChild, factory);
                a++;
            } else {
                b++;
            }
        }
    }

    /**
     * Clone a complete ContextTree<T>(with all its node)
     * 
     * @param inTree
     *            A ContextTree
     * @param factory
     *            A ContextTreeFactory<T> for the given inTree
     * @return A deep clone of the given tree
     */
    public static <T> ContextTree<T> clone(final ContextTree<T> inTree, final ContextTreeFactory<T> factory) {
        final T outRoot = clone(inTree, inTree.getRoot(), factory);
        final ContextTree<T> outTree = factory.createTree(outRoot);
        return outTree;
    }

    /**
     * Clone a subtree (starting at the given subtree root node).
     * 
     * @param inSubtreeRoot
     *            A T
     * @param factory
     *            A ContextTreeFactory<T> for the given inSubtreeRoot
     * @return A deep clone of the given subtree
     */
    public static <T> T clone(final ContextTree<T> tree, final T inSubtreeRoot, final ContextTreeFactory<T> factory) {
        final T outSubtreeRoot = factory.cloneNode(inSubtreeRoot);
        cloneChildren(tree, inSubtreeRoot, outSubtreeRoot, factory);
        return outSubtreeRoot;
    }

    private static <T> void cloneChildren(final ContextTree<T> tree, final T inNode, final T outNode, final ContextTreeFactory<T> factory) {
        for (final T inChild : tree.iterable(inNode)) {
            final T outChild = factory.cloneNode(inChild);
            factory.connectParentAndChild(outNode, outChild);
            cloneChildren(tree, inChild, outChild, factory);
        }
    }

}
