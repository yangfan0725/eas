package com.kingdee.eas.fdc.market;

import java.awt.Font;
import java.io.Serializable;

public class DocumentItemInfo extends AbstractDocumentItemInfo implements Comparable {

	public final static int MAX_FONT_SIZE = 80;
	public final static int MIN_FONT_SIZE = 8;

	public DocumentItemInfo() {
		super();
	}

	protected DocumentItemInfo(String pkField) {
		super(pkField);
	}
	

	public Font getFont() {
		Font font = null;
		if (getXFontName() == null || getXFontSize() <= MIN_FONT_SIZE
				|| getXFontSize() >= MAX_FONT_SIZE) {
			font = getDefaultFont();
		} else {
			font = new Font(getXFontName(), Font.PLAIN, getXFontSize());
		}
		return font;
	}

	public Font getDefaultFont() {
		return new Font("Dialog", Font.PLAIN, 12);
	}

	public int compareTo(Object o) {
		if(o instanceof DocumentItemInfo){
			DocumentItemInfo oth = (DocumentItemInfo)o;
			int thisN  = getItemNumber();
			int othN = oth.getItemNumber();
			if(thisN > othN){
				return 1;
			}else if(thisN == othN){
				return 0;
			}else{
				return -1;
			}
		} else {
			return 0;
		}
	}

}