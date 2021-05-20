package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSignManageInfo extends com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo implements Serializable 
{
    public AbstractSignManageInfo()
    {
        this("id");
    }
    protected AbstractSignManageInfo(String pkField)
    {
        super(pkField);
        put("signPayListEntry", new com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection());
        put("signCustomerEntry", new com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection());
        put("signAgioEntry", new com.kingdee.eas.fdc.sellhouse.SignAgioEntryCollection());
        put("signSaleManEntry", new com.kingdee.eas.fdc.sellhouse.SignSaleManEntryCollection());
        put("signRoomAttachmentEntry", new com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryCollection());
    }
    /**
     * Object:ǩԼ����'s �����ܼ�property 
     */
    public java.math.BigDecimal getSellAmount()
    {
        return getBigDecimal("sellAmount");
    }
    public void setSellAmount(java.math.BigDecimal item)
    {
        setBigDecimal("sellAmount", item);
    }
    /**
     * Object:ǩԼ����'s �������property 
     */
    public java.math.BigDecimal getAreaCompensate()
    {
        return getBigDecimal("areaCompensate");
    }
    public void setAreaCompensate(java.math.BigDecimal item)
    {
        setBigDecimal("areaCompensate", item);
    }
    /**
     * Object:ǩԼ����'s Ԥ������property 
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
     * Object:ǩԼ����'s ���۲���property 
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
     * Object:ǩԼ����'s Ԥ�����property 
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
     * Object:ǩԼ����'s Ԥ�����property 
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
     * Object:ǩԼ����'s ʵ�����property 
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
     * Object: ǩԼ���� 's ������ϸ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection getSignPayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection)get("signPayListEntry");
    }
    /**
     * Object: ǩԼ���� 's ����������¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryCollection getSignRoomAttachmentEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryCollection)get("signRoomAttachmentEntry");
    }
    /**
     * Object: ǩԼ���� 's �ۿ۷�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignAgioEntryCollection getSignAgioEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignAgioEntryCollection)get("signAgioEntry");
    }
    /**
     * Object: ǩԼ���� 's �ͻ���Ϣ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection getSignCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection)get("signCustomerEntry");
    }
    /**
     * Object:ǩԼ����'s �Ƿ���ǩproperty 
     */
    public boolean isIsOnRecord()
    {
        return getBoolean("isOnRecord");
    }
    public void setIsOnRecord(boolean item)
    {
        setBoolean("isOnRecord", item);
    }
    /**
     * Object:ǩԼ����'s ��������property 
     */
    public java.util.Date getOnRecordDate()
    {
        return getDate("onRecordDate");
    }
    public void setOnRecordDate(java.util.Date item)
    {
        setDate("onRecordDate", item);
    }
    /**
     * Object:ǩԼ����'s Լ���������property 
     */
    public java.util.Date getJoinInDate()
    {
        return getDate("joinInDate");
    }
    public void setJoinInDate(java.util.Date item)
    {
        setDate("joinInDate", item);
    }
    /**
     * Object: ǩԼ���� 's ��ҵ���ʷ�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignSaleManEntryCollection getSignSaleManEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignSaleManEntryCollection)get("signSaleManEntry");
    }
    /**
     * Object:ǩԼ����'s ��ҵ����property 
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
     * Object: ǩԼ���� 's �������� property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getLoanBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("LoanBank");
    }
    public void setLoanBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("LoanBank", item);
    }
    /**
     * Object: ǩԼ���� 's ���������� property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getAcfBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("AcfBank");
    }
    public void setAcfBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("AcfBank", item);
    }
    /**
     * Object: ǩԼ���� 's �����ۿ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo getSpecialAgio()
    {
        return (com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo)get("specialAgio");
    }
    public void setSpecialAgio(com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo item)
    {
        put("specialAgio", item);
    }
    /**
     * Object:ǩԼ����'s �Ƿ񹤵ַ�property 
     */
    public boolean isIsWorkRoom()
    {
        return getBoolean("isWorkRoom");
    }
    public void setIsWorkRoom(boolean item)
    {
        setBoolean("isWorkRoom", item);
    }
    /**
     * Object:ǩԼ����'s ������Աproperty 
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
     * Object:ǩԼ����'s һ������property 
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
     * Object:ǩԼ����'s ��������property 
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
     * Object:ǩԼ����'s ǩԼ��ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.SignChangeStateEnum getChangeState()
    {
        return com.kingdee.eas.fdc.sellhouse.SignChangeStateEnum.getEnum(getString("changeState"));
    }
    public void setChangeState(com.kingdee.eas.fdc.sellhouse.SignChangeStateEnum item)
    {
		if (item != null) {
        setString("changeState", item.getValue());
		}
    }
    /**
     * Object:ǩԼ����'s Լ��ǩԼ����property 
     */
    public java.util.Date getPlanSignDate()
    {
        return getDate("planSignDate");
    }
    public void setPlanSignDate(java.util.Date item)
    {
        setDate("planSignDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D840369D");
    }
}