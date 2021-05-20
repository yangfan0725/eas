/**
 * 
 */
package com.kingdee.eas.fdc.schedule.framework.parser;

import net.sourceforge.ganttproject.parser.FileFormatException;
import net.sourceforge.ganttproject.parser.TagHandler;

import org.xml.sax.Attributes;

/**
 * @author nbohn
 */
public class IconPositionTagHandler implements TagHandler {
    private int[] myList;

    private String myPositions = "";

    private int[] myDeletedList;

    private String myDeletedPositions = "";

    public IconPositionTagHandler() {
    }

    public void startElement(String namespaceURI, String sName, String qName,
            Attributes attrs) throws FileFormatException {
        if (qName.equals("positions")) {
            loadIcon(attrs);
            loadDeletedIcon(attrs);
        }
    }

    public void endElement(String namespaceURI, String sName, String qName) {
    }

    private void loadIcon(Attributes atts) {
        myPositions = atts.getValue("icons-list");
        if (!myPositions.equals("")) {
            String[] positions = myPositions.split(",");
            myList = new int[positions.length];
            for (int i = 0; i < positions.length; i++)
                myList[i] = (new Integer(positions[i])).intValue();
        }
    }

    private void loadDeletedIcon(Attributes atts) {
        myDeletedPositions = atts.getValue("deletedIcons-list");
        if ((myDeletedPositions != null) && (!myDeletedPositions.equals(""))) {
            String[] positions = myDeletedPositions.split(",");
            myDeletedList = new int[positions.length];
            for (int i = 0; i < positions.length; i++)
                myDeletedList[i] = (new Integer(positions[i])).intValue();
        }
    }

    public int[] getList() {
        return myList;
    }

    public String getPositions() {
        return myPositions;
    }

    public int[] getDeletedList() {
        return myDeletedList;
    }

    public String getDeletedPositions() {
        return myDeletedPositions;
    }
}
