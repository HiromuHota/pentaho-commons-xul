package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTreeCols extends AbstractGwtXulContainer implements XulTreeCols {

  public static void register() {
    GwtXulParser.registerHandler("treecols", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeCols();
      }
    });
  }
  
  public GwtTreeCols() {
    super("treecols");
  }
  
  public void addColumn(XulTreeCol column) {
    super.addChild(column);
  }
  
  private XulTree tree;
  private XulTree getParentTree(){
    return (tree != null) ? tree : (tree = (XulTree) this.getParent());
  }

  @Override
  public void addChild(Element element) {
    super.addChild(element);
    if(getParentTree() != null) {
      getParentTree().update();  
    }
  }

  public XulTreeCol getColumn(int index) {
    return (XulTreeCol) children.get(index);
  }

  public int getColumnCount() {
    return this.children.size();
  }

  public XulTree getTree() {
    return (XulTree) getParent();
  }

  public boolean isHierarchical() {
    // TODO Auto-generated method stub
    return false;
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }

}
