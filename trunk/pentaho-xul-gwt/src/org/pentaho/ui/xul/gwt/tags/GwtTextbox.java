package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtDomElement;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.TextType;

import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

public class GwtTextbox extends AbstractGwtXulComponent implements XulTextbox {
  
  static final String ELEMENT_NAME = "textbox"; //$NON-NLS-1$

  protected String max, min, oninput;
  protected TextType type = TextType.NORMAL;
  protected boolean readonly;
  protected boolean multiline = false;
  private Integer rows;
  private Integer cols = -1;
  private String value;
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTextbox();
      }
    });
  }
  
  private TextBoxBase textBox;
  
  public GwtTextbox() {
    super(ELEMENT_NAME);
    textBox = new TextBox();
    
    // Firefox 2 and sometimes 3 fails to render cursors in Textboxes if they're contained in absolutely
    // positioned div's, such as when they're in dialogs. The workaround is to wrap the <input> in a div
    // with overflow: auto;
    managedObject = textBox;
      
    // textBox.setPreferredSize(new Dimension(150,18));
  }

  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setValue(srcEle.getAttribute("value"));
    setDisabled("true".equals(srcEle.getAttribute("disabled")));
    setMultiline("true".equals(srcEle.getAttribute("multiline")));
    setRows(getInt(srcEle.getAttribute("rows")));
    setCols(getInt(srcEle.getAttribute("cols")));
  }
  
  public Integer getInt(String val) {
    if (val != null) {
      try {
        return Integer.parseInt(val);
      } catch (Exception e) {
      }
    }
    return null;
  }
  
  public String getValue(){
    return value;
  }

  public void setValue(String text){
      String prevVal = this.value;
      this.value = text;
      if(textBox.getText().equals(text) == false){
        textBox.setText(text);
      }
      this.firePropertyChange("value", prevVal, text);
  }
  
  // TODO: this double initialization is not good. Any values previously set will be lost in a second layout
  // move to local variables if this late binding is really needed and take advantage of the new onDomReady event 
  // to late bind instead of using layout.
  public void layout(){
    String typeString = this.getAttributeValue("type");
    if(typeString != null && typeString.length() > 0) {
      setType(typeString);
    }
    switch(this.type) {
      case PASSWORD:
          managedObject = textBox = new PasswordTextBox();
        break;        
      case NUMERIC:
      default: //regular text  
        if (multiline) {
          managedObject = textBox = new TextArea();
          
          if(cols != null && cols > -1){
            ((TextArea)textBox).setCharacterWidth(cols);
            ((TextArea)textBox).setVisibleLines(rows);
          }
          
        } else {
          //managedObject = textBox = new TextBox();
          managedObject = textBox;
        }
        break;
    }
    if(this.getWidth() > 0){
      textBox.setWidth(this.getWidth()+"px");
    } else {
      textBox.setWidth("100%");
    }
    if(this.getHeight() > 0){
      textBox.setHeight(this.getHeight()+"px");
    }
    textBox.setText(getValue());
    setupListeners();
  }
  
  private void setupListeners(){
    textBox.addKeyboardListener(new KeyboardListener(){

      public void onKeyDown(Widget arg0, char arg1, int arg2) {}

      public void onKeyPress(Widget arg0, char arg1, int arg2) {}

      public void onKeyUp(Widget arg0, char arg1, int arg2) {
        setValue(textBox.getText());
      }
      
    });
    
  }

  public int getMaxlength() {
    return 0;
  }

  public boolean isDisabled() {
    return !textBox.isEnabled();
  }

  public void setDisabled(boolean dis) {
    textBox.setEnabled(!dis);
  }

  public void setMaxlength(int length) {
  }

  public boolean isMultiline() {
    return false;
  }

  public void setMultiline(boolean multi) {
    this.multiline = multi;
  }

  public String getMax() {
    return min;  
  }

  public String getMin() {
    return min;  
  }

  public Object getTextControl() {
   return this.managedObject;   
  }

  public String getType() {
    if (type == null) {
      return null;
    }

    return type.toString(); 
  }

  public boolean isReadonly() {
    return readonly;  
  }

  public void selectAll() {
    
        // TODO Auto-generated method stub 
      
  }

  public void setFocus() {
    // TODO focus method impl
  }

  public void setMax(String max) {
    this.max = max;
  }

  public void setMin(String min) {
    this.min = min;
  }

  public void setOninput(String method) {
    this.oninput = method;
  }

  public void setReadonly(boolean readOnly) {
    this.readonly = readOnly;
  }

  public void setType(String type) {
    if (type == null) {
      return;
    }
    setType(TextType.valueOf(type.toUpperCase()));
  }

  public void setType(TextType type) {
    this.type = type;
  }

  public void adoptAttributes(XulComponent component) {
    super.adoptAttributes(component);

    setRows(getInt(component.getAttributeValue("rows")));
    setCols(getInt(component.getAttributeValue("cols")));

    if(component.getAttributeValue("value") != null){
      setValue(component.getAttributeValue("value"));
    }
    if(component.getAttributeValue("disabled") != null){
      setDisabled("true".equals(component.getAttributeValue("disabled")));
    }
    layout();
    ((AbstractGwtXulComponent) this.getParent()).layout();
  }

  void setRows(Integer rows) {
    this.rows = rows;
  }

  Integer getRows() {
    return rows;
  }

  void setCols(Integer cols) {
    this.cols = cols;
  }

  Integer getCols() {
    return cols;
  }
}