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
package ch.usi.inf.sape.trevis.model.attribute;

import ch.usi.inf.sape.trevis.model.ContextTree;

/**
 * This is a general attribute. It does not depend on a specific ContextTreeNode implementation. For any given node, it returns the number
 * of leaf descendants (for a leaf, it returns 1).
 * 
 * @author Matthias.Hauswirth@usi.ch
 */
public final class LeafCountAttribute extends ContextTreeLongAttribute {

    public LeafCountAttribute(ContextTree tree) {
        super(tree);
    }

    public String getName() {
        return "Leafs";
    }

    public String getDescription() {
        return "Number of leaf descendants";
    }

    public long evaluate(final ContextTree tree, final Object node) {
        if (tree.getChildCount(node) == 0) {
            return 1;
        } else {
            long childrensLeafs = 0;
            for (final Object child : tree.iterable(node)) {
                childrensLeafs += evaluate(child);
            }
            return childrensLeafs;
        }
    }

}
