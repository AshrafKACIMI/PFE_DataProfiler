/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testhierarchie.Graphics;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Ashraf
 */
public abstract class AbstractTreeItem extends TreeItem{
    public abstract ContextMenu getMenu();
}