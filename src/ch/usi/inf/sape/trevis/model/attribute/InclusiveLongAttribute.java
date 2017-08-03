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
 * Given an exclusive (value only for the given node) LongAttribute, computes the inclusive value (value for the given node and all its
 * descendants).
 * 
 * @author Matthias.Hauswirth@usi.ch
 */
public final class InclusiveLongAttribute extends ContextTreeLongAttribute {

    private final LongAttribute exclusiveAttribute;

    public InclusiveLongAttribute(final ContextTree tree, final LongAttribute exclusiveAttribute) {
        super(tree);
        this.exclusiveAttribute = exclusiveAttribute;
    }

    public String getName() {
        return "Inclusive(" + exclusiveAttribute.getName() + ")";
    }

    public String getDescription() {
        return "Inclusive(" + exclusiveAttribute.getDescription() + ")";
    }

    public long evaluate(final ContextTree tree, final Object node) {
        long value = exclusiveAttribute.evaluate(node);
        for (final Object child : tree.iterable(node)) {
            value += evaluate(child);
        }
        return value;
    }

}
