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
     * Object:�Ϲ�����'s Լ��ǩԼ����property 
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
     * Object:�Ϲ�����'s �����׼property 
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
     * Object: �Ϲ����� 's ������ϸ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection getPurPayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection)get("purPayListEntry");
    }
    /**
     * Object: �Ϲ����� 's ����������¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryCollection getPurRoomAttachmentEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryCollection)get("purRoomAttachmentEntry");
    }
    /**
     * Object: �Ϲ����� 's �ۿ۷�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurAgioEntryCollection getPurAgioEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurAgioEntryCollection)get("purAgioEntry");
    }
    /**
     * Object: �Ϲ����� 's �ͻ���Ϣ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection getPurCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection)get("purCustomerEntry");
    }
    /**
     * Object:�Ϲ�����'s Ԥ������property 
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
     * Object:�Ϲ�����'s ���۲���property 
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
     * Object:�Ϲ�����'s Ԥ�����property 
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
     * Object:�Ϲ�����'s Ԥ�����property 
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
     * Object:�Ϲ�����'s ʵ�����property 
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
     * Object: �Ϲ����� 's ��ҵ���ʷ�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurSaleManEntryCollection getPurSaleManEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurSaleManEntryCollection)get("purSaleManEntry");
    }
    /**
     * Object:�Ϲ�����'s ��ҵ����property 
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
     * Object:�Ϲ�����'s ������Աproperty 
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
     * Object:�Ϲ�����'s һ������property 
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
     * Object:�Ϲ�����'s ��������property 
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
     * Object:�Ϲ�����'s �Ϲ���ʽproperty 
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