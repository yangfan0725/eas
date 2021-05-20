package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDocumentInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractDocumentInfo()
    {
        this("id");
    }
    protected AbstractDocumentInfo(String pkField)
    {
        super(pkField);
        put("subjects", new com.kingdee.eas.fdc.market.DocumentSubjectCollection());
    }
    /**
     * Object:�Ծ�'s nullproperty 
     */
    public String getTopic()
    {
        return getString("topic");
    }
    public void setTopic(String item)
    {
        setString("topic", item);
    }
    /**
     * Object: �Ծ� 's  property 
     */
    public com.kingdee.eas.fdc.market.DocumentSubjectCollection getSubjects()
    {
        return (com.kingdee.eas.fdc.market.DocumentSubjectCollection)get("subjects");
    }
    /**
     * Object:�Ծ�'s ��������property 
     */
    public String getXFontName()
    {
        return getString("xFontName");
    }
    public void setXFontName(String item)
    {
        setString("xFontName", item);
    }
    /**
     * Object:�Ծ�'s �����Сproperty 
     */
    public int getXFontSize()
    {
        return getInt("xFontSize");
    }
    public void setXFontSize(int item)
    {
        setInt("xFontSize", item);
    }
    /**
     * Object:�Ծ�'s ҳ����property 
     */
    public int getWidth()
    {
        return getInt("width");
    }
    public void setWidth(int item)
    {
        setInt("width", item);
    }
    /**
     * Object:�Ծ�'s ҳ��߶�property 
     */
    public int getHeight()
    {
        return getInt("height");
    }
    public void setHeight(int item)
    {
        setInt("height", item);
    }
    /**
     * Object:�Ծ�'s �ϱ߾�property 
     */
    public int getTopMarge()
    {
        return getInt("topMarge");
    }
    public void setTopMarge(int item)
    {
        setInt("topMarge", item);
    }
    /**
     * Object:�Ծ�'s �ұ߾�property 
     */
    public int getRightMarge()
    {
        return getInt("rightMarge");
    }
    public void setRightMarge(int item)
    {
        setInt("rightMarge", item);
    }
    /**
     * Object:�Ծ�'s �±߾�property 
     */
    public int getBottomMarge()
    {
        return getInt("bottomMarge");
    }
    public void setBottomMarge(int item)
    {
        setInt("bottomMarge", item);
    }
    /**
     * Object:�Ծ�'s ��߾�property 
     */
    public int getLeftMarge()
    {
        return getInt("leftMarge");
    }
    public void setLeftMarge(int item)
    {
        setInt("leftMarge", item);
    }
    /**
     * Object:�Ծ�'s ҳüproperty 
     */
    public String getHeader()
    {
        return getString("header");
    }
    public void setHeader(String item)
    {
        setString("header", item);
    }
    /**
     * Object:�Ծ�'s ҳ��property 
     */
    public String getFooter()
    {
        return getString("footer");
    }
    public void setFooter(String item)
    {
        setString("footer", item);
    }
    /**
     * Object:�Ծ�'s ����property 
     */
    public int getColumnCount()
    {
        return getInt("columnCount");
    }
    public void setColumnCount(int item)
    {
        setInt("columnCount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6477D5EC");
    }
}