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
 * Determine whether the given node is a leaf.
 * 
 * @author Matthias.Hauswirth@usi.ch
 */
public final class IsLeafAttribute extends BooleanAttribute {

    private final ContextTree tree;

    public IsLeafAttribute(final ContextTree tree) {
        this.tree = tree;
    }

    public String getName() {
        return "IsLeaf";
    }

    public String getDescription() {
        return "Is the node a leaf node?";
    }

    public boolean evaluate(final Object node) {
        return this.tree.getChildCount(node) == 0;
    }

}
