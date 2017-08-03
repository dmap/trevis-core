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

public abstract class ContextTreeLongAttribute extends LongAttribute {

    private final ContextTree tree;

    public ContextTreeLongAttribute(final ContextTree tree) {
        this.tree = tree;
    }

    public long evaluate(final Object node) {
        return evaluate(tree, node);
    }

    public abstract long evaluate(ContextTree tree, Object node);

}
