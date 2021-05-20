package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPropertyBatchInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractRoomPropertyBatchInfo()
    {
        this("id");
    }
    protected AbstractRoomPropertyBatchInfo(String pkField)
    {
        super(pkField);
        put("book", new com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchBooksCollection());
        put("material", new com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchMaterialsCollection());
        put("step", new com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchStepCollection());
    }
    /**
     * Object:������Ȩ's ��Ȩ֤״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.PropertyStateEnum getPropertyState()
    {
        return com.kingdee.eas.fdc.sellhouse.PropertyStateEnum.getEnum(getString("propertyState"));
    }
    public void setPropertyState(com.kingdee.eas.fdc.sellhouse.PropertyStateEnum item)
    {
		if (item != null) {
        setString("propertyState", item.getValue());
		}
    }
    /**
     * Object: ������Ȩ 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getTransactor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("transactor");
    }
    public void setTransactor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("transactor", item);
    }
    /**
     * Object: ������Ȩ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchMaterialsCollection getMaterial()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchMaterialsCollection)get("material");
    }
    /**
     * Object: ������Ȩ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchStepCollection getStep()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchStepCollection)get("step");
    }
    /**
     * Object: ������Ȩ 's ��Ȩ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo getScheme()
    {
        return (com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo)get("scheme");
    }
    public void setScheme(com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo item)
    {
        put("scheme", item);
    }
    /**
     * Object: ������Ȩ 's ��Ȩ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchBooksCollection getBook()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchBooksCollection)get("book");
    }
    /**
     * Object:������Ȩ's ����״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum getBookState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum.getEnum(getString("bookState"));
    }
    public void setBookState(com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum item)
    {
		if (item != null) {
        setString("bookState", item.getValue());
		}
    }
    /**
     * Object:������Ȩ's ��������property 
     */
    public java.util.Date getUpdateDate()
    {
        return getDate("updateDate");
    }
    public void setUpdateDate(java.util.Date item)
    {
        setDate("updateDate", item);
    }
    /**
     * Object:������Ȩ's ��ǰ����property 
     */
    public String getCurStep()
    {
        return getString("curStep");
    }
    public void setCurStep(String item)
    {
        setString("curStep", item);
    }
    /**
     * Object: ������Ȩ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("96195BCF");
    }
}