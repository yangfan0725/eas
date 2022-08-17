package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeManageInfo extends com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo implements Serializable 
{
    public AbstractChangeManageInfo()
    {
        this("id");
    }
    protected AbstractChangeManageInfo(String pkField)
    {
        super(pkField);
        put("attachEntry", new com.kingdee.eas.fdc.sellhouse.ChangeManageAttachEntryCollection());
        put("customerEntry", new com.kingdee.eas.fdc.sellhouse.ChangeCustomerEntryCollection());
        put("payListEntry", new com.kingdee.eas.fdc.sellhouse.ChangePayListEntryCollection());
        put("roomAttachEntry", new com.kingdee.eas.fdc.sellhouse.ChangeRoomAttachEntryCollection());
        put("agioEntry", new com.kingdee.eas.fdc.sellhouse.ChangeAgioEntryCollection());
    }
    /**
     * Object:�������'s �µ���IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getNewId()
    {
        return getBOSUuid("newId");
    }
    public void setNewId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("newId", item);
    }
    /**
     * Object:�������'s ҵ������property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum getBizType()
    {
        return com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum.getEnum(getString("bizType"));
    }
    public void setBizType(com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum item)
    {
		if (item != null) {
        setString("bizType", item.getValue());
		}
    }
    /**
     * Object: ������� 's �ͻ���¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeCustomerEntryCollection getCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeCustomerEntryCollection)get("customerEntry");
    }
    /**
     * Object: ������� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object:�������'s ʵ���ܶ�property 
     */
    public java.math.BigDecimal getActAmount()
    {
        return getBigDecimal("actAmount");
    }
    public void setActAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actAmount", item);
    }
    /**
     * Object:�������'s ���ý��property 
     */
    public java.math.BigDecimal getFeeAmount()
    {
        return getBigDecimal("feeAmount");
    }
    public void setFeeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("feeAmount", item);
    }
    /**
     * Object:�������'s ���˽��property 
     */
    public java.math.BigDecimal getQuitAmount()
    {
        return getBigDecimal("quitAmount");
    }
    public void setQuitAmount(java.math.BigDecimal item)
    {
        setBigDecimal("quitAmount", item);
    }
    /**
     * Object:�������'s �������property 
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
     * Object:�������'s �滮����property 
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
     * Object:�������'s ���۲���property 
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
     * Object: ������� 's �ۿ۷�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeAgioEntryCollection getAgioEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeAgioEntryCollection)get("agioEntry");
    }
    /**
     * Object: ������� 's ����������¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeRoomAttachEntryCollection getRoomAttachEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeRoomAttachEntryCollection)get("roomAttachEntry");
    }
    /**
     * Object: ������� 's �ͻ������¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangePayListEntryCollection getPayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangePayListEntryCollection)get("payListEntry");
    }
    /**
     * Object:�������'s ��������Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getNewEntryId()
    {
        return getBOSUuid("newEntryId");
    }
    public void setNewEntryId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("newEntryId", item);
    }
    /**
     * Object:�������'s ����״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.DealStateEnum getDealState()
    {
        return com.kingdee.eas.fdc.sellhouse.DealStateEnum.getEnum(getString("dealState"));
    }
    public void setDealState(com.kingdee.eas.fdc.sellhouse.DealStateEnum item)
    {
		if (item != null) {
        setString("dealState", item.getValue());
		}
    }
    /**
     * Object:�������'s ԭ�ͻ���ϵ�绰property 
     */
    public String getSrcCustomerPhone()
    {
        return getString("srcCustomerPhone");
    }
    public void setSrcCustomerPhone(String item)
    {
        setString("srcCustomerPhone", item);
    }
    /**
     * Object:�������'s ԭ�ͻ�����property 
     */
    public String getSrcCustomerNames()
    {
        return getString("srcCustomerNames");
    }
    public void setSrcCustomerNames(String item)
    {
        setString("srcCustomerNames", item);
    }
    /**
     * Object: ������� 's ԭ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getSrcRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("srcRoom");
    }
    public void setSrcRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("srcRoom", item);
    }
    /**
     * Object:�������'s �����ܼ�property 
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
     * Object:�������'s Ԥ�����property 
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
     * Object:�������'s Ԥ�����property 
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
     * Object:�������'s ʵ�����property 
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
     * Object:�������'s �������property 
     */
    public java.util.Date getChangeDate()
    {
        return getDate("changeDate");
    }
    public void setChangeDate(java.util.Date item)
    {
        setDate("changeDate", item);
    }
    /**
     * Object: ������� 's ���ԭ�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeReasonInfo getChangeReason()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeReasonInfo)get("changeReason");
    }
    public void setChangeReason(com.kingdee.eas.fdc.sellhouse.ChangeReasonInfo item)
    {
        put("changeReason", item);
    }
    /**
     * Object: ������� 's �տ property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevBillInfo getSheRevBill()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevBillInfo)get("sheRevBill");
    }
    public void setSheRevBill(com.kingdee.eas.fdc.basecrm.SHERevBillInfo item)
    {
        put("sheRevBill", item);
    }
    /**
     * Object:�������'s �����ϸ˵��property 
     */
    public String getDetails()
    {
        return getString("details");
    }
    public void setDetails(String item)
    {
        setString("details", item);
    }
    /**
     * Object:�������'s �Ƿ�ǩԼ����property 
     */
    public boolean isIsSignChangeName()
    {
        return getBoolean("isSignChangeName");
    }
    public void setIsSignChangeName(boolean item)
    {
        setBoolean("isSignChangeName", item);
    }
    /**
     * Object:�������'s �����˷�����property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeTypeEnum getChangeType()
    {
        return com.kingdee.eas.fdc.sellhouse.ChangeTypeEnum.getEnum(getString("changeType"));
    }
    public void setChangeType(com.kingdee.eas.fdc.sellhouse.ChangeTypeEnum item)
    {
		if (item != null) {
        setString("changeType", item.getValue());
		}
    }
    /**
     * Object: ������� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeManageAttachEntryCollection getAttachEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeManageAttachEntryCollection)get("attachEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("27800F90");
    }
}