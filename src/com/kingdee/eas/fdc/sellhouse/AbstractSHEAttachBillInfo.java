package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEAttachBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractSHEAttachBillInfo()
    {
        this("id");
    }
    protected AbstractSHEAttachBillInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.sellhouse.SHEAttachBillEntryCollection());
    }
    /**
     * Object: 规范性附件上传 's 房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object:规范性附件上传's 产品类型属性property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypePropertyEnum getProductTypeProperty()
    {
        return com.kingdee.eas.fdc.basedata.ProductTypePropertyEnum.getEnum(getString("productTypeProperty"));
    }
    public void setProductTypeProperty(com.kingdee.eas.fdc.basedata.ProductTypePropertyEnum item)
    {
		if (item != null) {
        setString("productTypeProperty", item.getValue());
		}
    }
    /**
     * Object:规范性附件上传's 销售方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellTypeEnum getSellType()
    {
        return com.kingdee.eas.fdc.sellhouse.SellTypeEnum.getEnum(getString("sellType"));
    }
    public void setSellType(com.kingdee.eas.fdc.sellhouse.SellTypeEnum item)
    {
		if (item != null) {
        setString("sellType", item.getValue());
		}
    }
    /**
     * Object:规范性附件上传's 销售阶段property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellStageEnum getSellStage()
    {
        return com.kingdee.eas.fdc.sellhouse.SellStageEnum.getEnum(getString("sellStage"));
    }
    public void setSellStage(com.kingdee.eas.fdc.sellhouse.SellStageEnum item)
    {
		if (item != null) {
        setString("sellStage", item.getValue());
		}
    }
    /**
     * Object:规范性附件上传's 客户property 
     */
    public String getCustomer()
    {
        return getString("customer");
    }
    public void setCustomer(String item)
    {
        setString("customer", item);
    }
    /**
     * Object: 规范性附件上传 's 分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEAttachBillEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEAttachBillEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("434029C1");
    }
}