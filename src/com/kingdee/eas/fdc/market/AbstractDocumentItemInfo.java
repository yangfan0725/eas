package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDocumentItemInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractDocumentItemInfo()
    {
        this("id");
    }
    protected AbstractDocumentItemInfo(String pkField)
    {
        super(pkField);
        put("options", new com.kingdee.eas.fdc.market.DocumentOptionCollection());
    }
    /**
     * Object:�ʾ�������'s topicproperty 
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
     * Object: �ʾ������� 's subjectId property 
     */
    public com.kingdee.eas.fdc.market.DocumentSubjectInfo getSubjectId()
    {
        return (com.kingdee.eas.fdc.market.DocumentSubjectInfo)get("subjectId");
    }
    public void setSubjectId(com.kingdee.eas.fdc.market.DocumentSubjectInfo item)
    {
        put("subjectId", item);
    }
    /**
     * Object: �ʾ������� 's options property 
     */
    public com.kingdee.eas.fdc.market.DocumentOptionCollection getOptions()
    {
        return (com.kingdee.eas.fdc.market.DocumentOptionCollection)get("options");
    }
    /**
     * Object:�ʾ�������'s xFontNameproperty 
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
     * Object:�ʾ�������'s xFontSizeproperty 
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
     * Object:�ʾ�������'s itemNumberproperty 
     */
    public int getItemNumber()
    {
        return getInt("itemNumber");
    }
    public void setItemNumber(int item)
    {
        setInt("itemNumber", item);
    }
    /**
     * Object:�ʾ�������'s �����������IDproperty 
     */
    public String getRelatTypeId()
    {
        return getString("relatTypeId");
    }
    public void setRelatTypeId(String item)
    {
        setString("relatTypeId", item);
    }
    /**
     * Object:�ʾ�������'s �Ƿ���תproperty 
     */
    public boolean isIsToJump()
    {
        return getBoolean("isToJump");
    }
    public void setIsToJump(boolean item)
    {
        setBoolean("isToJump", item);
    }
    /**
     * Object:�ʾ�������'s ��ת����property 
     */
    public com.kingdee.eas.fdc.market.JumpConditionEnum getJumpCont()
    {
        return com.kingdee.eas.fdc.market.JumpConditionEnum.getEnum(getString("jumpCont"));
    }
    public void setJumpCont(com.kingdee.eas.fdc.market.JumpConditionEnum item)
    {
		if (item != null) {
        setString("jumpCont", item.getValue());
		}
    }
    /**
     * Object: �ʾ������� 's ѡ��� property 
     */
    public com.kingdee.eas.fdc.market.DocumentOptionInfo getChooseOption()
    {
        return (com.kingdee.eas.fdc.market.DocumentOptionInfo)get("chooseOption");
    }
    public void setChooseOption(com.kingdee.eas.fdc.market.DocumentOptionInfo item)
    {
        put("chooseOption", item);
    }
    /**
     * Object: �ʾ������� 's ��ת����Ŀ property 
     */
    public com.kingdee.eas.fdc.market.DocumentItemInfo getJumpToItem()
    {
        return (com.kingdee.eas.fdc.market.DocumentItemInfo)get("jumpToItem");
    }
    public void setJumpToItem(com.kingdee.eas.fdc.market.DocumentItemInfo item)
    {
        put("jumpToItem", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("17A5EF1F");
    }
}