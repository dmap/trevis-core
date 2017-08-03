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
 * This is a general attribute. 
 * It does not depend on a specific ContextTreeNode implementation.
 * For any given node, it returns its height
 * (the length of the longest downward path from the node to a leaf).
 * 
 * @author Matthias.Hauswirth@usi.ch
 */
public final class HeightAttribute extends ContextTreeLongAttribute {

	public HeightAttribute(ContextTree tree) {
        super(tree);
    }

    public String getName() {
		return "Height";
	}
	
	public String getDescription() {
		return "Height of node (length of longest downward path to a leaf)";
	}
	
	public long evaluate(final ContextTree tree, final Object node) {
		long maxChildHeight = 0;
		for (final Object child : tree.iterable(node)) {
			maxChildHeight = Math.max(maxChildHeight, evaluate(tree, child));
		}
		return 1+maxChildHeight;
	}
	
}
