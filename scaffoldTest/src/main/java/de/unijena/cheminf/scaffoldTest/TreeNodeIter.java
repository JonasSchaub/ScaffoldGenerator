/*
 * Copyright (c) 2021 Julian Zander, Jonas Schaub,  Achim Zielesny
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, version 2.1.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package de.unijena.cheminf.scaffoldTest;

import java.util.Iterator;
import java.util.Objects;

/**
 * Iterator class for the TreeNode class. Allows the tree to be walked through completely.
 *
 * Inspired by: https://github.com/gt4dev/yet-another-tree-structure
 */
public class TreeNodeIter<MoleculeType> implements Iterator<TreeNode<MoleculeType>> {

    /**
     * Indicates which part of the tree is currently being processed.
     */
    enum PROCESS_STAGES {
        ProcessParent, ProcessChildCurNode, ProcessChildSubNode
    }

    /**
     * Just handled node of the tree
     */
    private TreeNode<MoleculeType> treeNode;

    /**
     * Next PROCESS_STAGE
     */
    private PROCESS_STAGES doNext;

    /**
     * Next MoleculeType being processed
     */
    private TreeNode<MoleculeType> next;

    /**
     * Shows whether the nextNode() has already been calculated.
     * nextNode() must have been executed before next() can be executed.
     */
    private boolean nextNodeHasBeenCalculated;

    /**
     * Currently treated TreeNode
     */
    private Iterator<TreeNode<MoleculeType>> childrenCurNodeIter;
    /**
     * Next treated TreeNode
     */
    private Iterator<TreeNode<MoleculeType>> childrenSubNodeIter;

    /**
     * Constructor
     * Iteration through the tree of a specific node
     * @param aTreeNode Node through whose tree it is to be iterated
     */
    public TreeNodeIter(TreeNode<MoleculeType> aTreeNode) {
        Objects.requireNonNull(aTreeNode, "Given TreeNode is 'null'");
        this.treeNode = aTreeNode;
        this.doNext = PROCESS_STAGES.ProcessParent;
        this.childrenCurNodeIter = treeNode.getChildren().iterator();
    }

    /**
     * Indicates whether there is a next TreeNode. Returns false if the tree has already been traversed completely.
     * @return Is there a next TreeNode in the tree
     */
    @Override
    public boolean hasNext() {
        this.nextNodeHasBeenCalculated = true;
        if (this.doNext == PROCESS_STAGES.ProcessParent) {
            this.next = this.treeNode;
            this.doNext = PROCESS_STAGES.ProcessChildCurNode;
            return true;
        }

        if (this.doNext == PROCESS_STAGES.ProcessChildCurNode) {
            if (childrenCurNodeIter.hasNext()) {
                TreeNode<MoleculeType> childDirect = childrenCurNodeIter.next();
                childrenSubNodeIter = childDirect.iterator();
                this.doNext = PROCESS_STAGES.ProcessChildSubNode;
                return hasNext();
            }

            else {
                this.doNext = null;
                return false;
            }
        }

        if (this.doNext == PROCESS_STAGES.ProcessChildSubNode) {
            if (childrenSubNodeIter.hasNext()) {
                this.next = childrenSubNodeIter.next();
                return true;
            }
            else {
                this.next = null;
                this.doNext = PROCESS_STAGES.ProcessChildCurNode;
                return hasNext();
            }
        }
        return false;
    }

    /**
     * Outputs the next TreeNode in the tree
     * @return next TreeNode in the tree
     */
    @Override
    public TreeNode<MoleculeType> next() {
        if(this.nextNodeHasBeenCalculated == false) { //execute hasnext() if it has not been executed yet
            this.hasNext();
        }
        this.nextNodeHasBeenCalculated = false;
        return this.next;
    }
}