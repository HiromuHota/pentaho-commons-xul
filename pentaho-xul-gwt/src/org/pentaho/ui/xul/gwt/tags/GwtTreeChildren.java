package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import java.util.List;

public class GwtTreeChildren extends AbstractGwtXulContainer implements XulTreeChildren {

  public static void register() {
    GwtXulParser.registerHandler("treechildren",
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeChildren();
      }
    });
  }
  
  public GwtTreeChildren() {
    super("treechildren");
  }
  
  public void addItem(XulTreeItem item) {
    addChild(item);
  }

  public XulTreeRow addNewRow() {
    try {
      XulTreeRow row = (XulTreeRow)getDocument().createElement("treerow");
      XulTreeItem item = (XulTreeItem)getDocument().createElement("treeitem");
      item.setRow(row);
      addItem(item);
      return row;
    } catch (XulException e) {
      e.printStackTrace();
    }
    return null;
  }

  public XulTreeItem getItem(int rowIndex) {
    return (XulTreeItem) children.get(rowIndex);
  }

  public int getItemCount() {
    if (children != null) {
      return children.size();
    } else {
      return 0;
    }
  }

  public XulTree getTree() {
    return (XulTree)getParent();
  }

  public boolean isAlternatingbackground() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isHierarchical() {
    // TODO Auto-generated method stub
    return false;
  }

  public void removeItem(XulTreeItem item) {
    removeChild(item);
  }

  public void removeItem(int rowIndex) {
    removeChild(children.get(rowIndex));
  }

  public void setAlternatingbackground(boolean alt) {
    // TODO Auto-generated method stub
    
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }

  public void removeAll() {
    children.clear();
    if (getTree() != null) {
      ((GwtTree)getTree()).updateUI();
    }
  }
  
}
