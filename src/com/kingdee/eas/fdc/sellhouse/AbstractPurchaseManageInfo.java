package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseManageInfo extends com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo implements Serializable 
{
    public AbstractPurchaseManageInfo()
    {
        this("id");
    }
    protected AbstractPurchaseManageInfo(String pkField)
    {
        super(pkField);
        put("purSaleManEntry", new com.kingdee.eas.fdc.sellhouse.PurSaleManEntryCollection());
        put("purAgioEntry", new com.kingdee.eas.fdc.sellhouse.PurAgioEntryCollection());
        put("purPayListEntry", new com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection());
        put("purRoomAttachmentEntry", new com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryCollection());
        put("purCustomerEntry", new com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection());
    }
    /**
     * Object:认购管理's 约定签约日期property 
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
     * Object:认购管理's 定金标准property 
     */
    public java.math.BigDecimal getPurAmount()
    {
        return getBigDecimal("purAmount");
    }
    public void setPurAmount(java.math.BigDecimal item)
    {
        setBigDecimal("purAmount", item);
    }
    /**
     * Object: 认购管理 's 付款明细分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection getPurPayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection)get("purPayListEntry");
    }
    /**
     * Object: 认购管理 's 附属房产分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryCollection getPurRoomAttachmentEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryCollection)get("purRoomAttachmentEntry");
    }
    /**
     * Object: 认购管理 's 折扣分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurAgioEntryCollection getPurAgioEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurAgioEntryCollection)get("purAgioEntry");
    }
    /**
     * Object: 认购管理 's 客户信息分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection getPurCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection)get("purCustomerEntry");
    }
    /**
     * Object:认购管理's 预估补差property 
     */
    public java.math.BigDecimal getPlanningCompensate()
    {
        return getBigDecimal("planningCompensate");
    }
    public void setPlanningCompensate(java.math.BigDecimal item)
    {
        setBigDecimal("planningCompensate", item);
    }
    /**
     * Object:认购管理's 现售补差property 
     */
    public java.math.BigDecimal getCashSalesCompensate()
    {
        return getBigDecimal("cashSalesCompensate");
    }
    public void setCashSalesCompensate(java.math.BigDecimal item)
    {
        setBigDecimal("cashSalesCompensate", item);
    }
    /**
     * Object:认购管理's 预估面积property 
     */
    public java.math.BigDecimal getPlanningArea()
    {
        return getBigDecimal("planningArea");
    }
    public void setPlanningArea(java.math.BigDecimal item)
    {
        setBigDecimal("planningArea", item);
    }
    /**
     * Object:认购管理's 预售面积property 
     */
    public java.math.BigDecimal getPreArea()
    {
        return getBigDecimal("preArea");
    }
    public void setPreArea(java.math.BigDecimal item)
    {
        setBigDecimal("preArea", item);
    }
    /**
     * Object:认购管理's 实测面积property 
     */
    public java.math.BigDecimal getActualArea()
    {
        return getBigDecimal("actualArea");
    }
    public void setActualArea(java.math.BigDecimal item)
    {
        setBigDecimal("actualArea", item);
    }
    /**
     * Object: 认购管理 's 置业顾问分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurSaleManEntryCollection getPurSaleManEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurSaleManEntryCollection)get("purSaleManEntry");
    }
    /**
     * Object:认购管理's 置业顾问property 
     */
    public String getSaleManNames()
    {
        return getString("saleManNames");
    }
    public void setSaleManNames(String item)
    {
        setString("saleManNames", item);
    }
    /**
     * Object:认购管理's 渠道人员property 
     */
    public String getQdPerson()
    {
        return getString("qdPerson");
    }
    public void setQdPerson(String item)
    {
        setString("qdPerson", item);
    }
    /**
     * Object:认购管理's 一级渠道property 
     */
    public String getOneQd()
    {
        return getString("oneQd");
    }
    public void setOneQd(String item)
    {
        setString("oneQd", item);
    }
    /**
     * Object:认购管理's 二级渠道property 
     */
    public String getTwoQd()
    {
        return getString("twoQd");
    }
    public void setTwoQd(String item)
    {
        setString("twoQd", item);
    }
    /**
     * Object:认购管理's 认购方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurChangeStateEnum getChangeState()
    {
        return com.kingdee.eas.fdc.sellhouse.PurChangeStateEnum.getEnum(getString("changeState"));
    }
    public void setChangeState(com.kingdee.eas.fdc.sellhouse.PurChangeStateEnum item)
    {
		if (item != null) {
        setString("changeState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A38DD561");
    }
}