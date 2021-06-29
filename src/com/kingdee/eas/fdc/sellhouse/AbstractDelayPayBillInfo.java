package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDelayPayBillInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractDelayPayBillInfo()
    {
        this("id");
    }
    protected AbstractDelayPayBillInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.sellhouse.DelayPayBillEntryCollection());
        put("newEntry", new com.kingdee.eas.fdc.sellhouse.DelayPayBillNewEntryCollection());
    }
    /**
     * Object: 延期付款申请 's 分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.DelayPayBillEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.DelayPayBillEntryCollection)get("entry");
    }
    /**
     * Object: 延期付款申请 's 付款方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getPayType()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("payType");
    }
    public void setPayType(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("payType", item);
    }
    /**
     * Object: 延期付款申请 's 房间 property 
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
     * Object: 延期付款申请 's 新分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.DelayPayBillNewEntryCollection getNewEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.DelayPayBillNewEntryCollection)get("newEntry");
    }
    /**
     * Object:延期付款申请's 客户property 
     */
    public String getCustomerNames()
    {
        return getString("customerNames");
    }
    public void setCustomerNames(String item)
    {
        setString("customerNames", item);
    }
    /**
     * Object: 延期付款申请 's 项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object:延期付款申请's 单据状态property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:延期付款申请's 审批日期property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    /**
     * Object:延期付款申请's 认购日期property 
     */
    public java.util.Date getPurDate()
    {
        return getDate("purDate");
    }
    public void setPurDate(java.util.Date item)
    {
        setDate("purDate", item);
    }
    /**
     * Object:延期付款申请's 签约日期property 
     */
    public java.util.Date getSignDate()
    {
        return getDate("signDate");
    }
    public void setSignDate(java.util.Date item)
    {
        setDate("signDate", item);
    }
    /**
     * Object:延期付款申请's 延期类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.DelayPayTypeEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.DelayPayTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.DelayPayTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:延期付款申请's 约定签约日期property 
     */
    public java.util.Date getPlanSignDate()
    {
        return getDate("planSignDate");
    }
    public void setPlanSignDate(java.util.Date item)
    {
        setDate("planSignDate", item);
    }
    /**
     * Object:延期付款申请's 是否跨越property 
     */
    public com.kingdee.eas.fdc.sellhouse.BooleanEnum getIsPass()
    {
        return com.kingdee.eas.fdc.sellhouse.BooleanEnum.getEnum(getString("isPass"));
    }
    public void setIsPass(com.kingdee.eas.fdc.sellhouse.BooleanEnum item)
    {
		if (item != null) {
        setString("isPass", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("82836027");
    }
}