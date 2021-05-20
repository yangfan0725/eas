package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEProjectSetInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSHEProjectSetInfo()
    {
        this("id");
    }
    protected AbstractSHEProjectSetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¥����-��Ŀ���� 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ��¥����-��Ŀ���� 's ��Ŀ property 
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
     * Object:��¥����-��Ŀ����'s �ɽ��ܼ��Ƿ������޸�property 
     */
    public boolean isIsUpdateForAmount()
    {
        return getBoolean("isUpdateForAmount");
    }
    public void setIsUpdateForAmount(boolean item)
    {
        setBoolean("isUpdateForAmount", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s �Ƿ������ۿ۷���property 
     */
    public boolean isIsUseAgioScheme()
    {
        return getBoolean("isUseAgioScheme");
    }
    public void setIsUseAgioScheme(boolean item)
    {
        setBoolean("isUseAgioScheme", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s �Ƿ���������ۿ���ϸproperty 
     */
    public boolean isIsUpdateForAgioDetail()
    {
        return getBoolean("isUpdateForAgioDetail");
    }
    public void setIsUpdateForAgioDetail(boolean item)
    {
        setBoolean("isUpdateForAgioDetail", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s �Ƿ�׼ۿ���property 
     */
    public boolean isIsBasePriceControl()
    {
        return getBoolean("isBasePriceControl");
    }
    public void setIsBasePriceControl(boolean item)
    {
        setBoolean("isBasePriceControl", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s �Ƿ���������������зſ�property 
     */
    public boolean isIsBank()
    {
        return getBoolean("isBank");
    }
    public void setIsBank(boolean item)
    {
        setBoolean("isBank", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s �ɽ����Ƿ��Ա�׼�ܼ�Ϊ׼property 
     */
    public boolean isIsStandardTotalAmount()
    {
        return getBoolean("isStandardTotalAmount");
    }
    public void setIsStandardTotalAmount(boolean item)
    {
        setBoolean("isStandardTotalAmount", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s �˷��Ƿ����۸����property 
     */
    public boolean isIsPriceAuditedForQuitRoom()
    {
        return getBoolean("isPriceAuditedForQuitRoom");
    }
    public void setIsPriceAuditedForQuitRoom(boolean item)
    {
        setBoolean("isPriceAuditedForQuitRoom", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s �����Ƿ����۸����property 
     */
    public boolean isIsPriceAuditedForChanceRoom()
    {
        return getBoolean("isPriceAuditedForChanceRoom");
    }
    public void setIsPriceAuditedForChanceRoom(boolean item)
    {
        setBoolean("isPriceAuditedForChanceRoom", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s �������Ƿ����۸����property 
     */
    public boolean isIsPriceAuditedForAreaChance()
    {
        return getBoolean("isPriceAuditedForAreaChance");
    }
    public void setIsPriceAuditedForAreaChance(boolean item)
    {
        setBoolean("isPriceAuditedForAreaChance", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s ҵ����������Ƿ�����༭property 
     */
    public boolean isIsUpdateForbelongDate()
    {
        return getBoolean("isUpdateForbelongDate");
    }
    public void setIsUpdateForbelongDate(boolean item)
    {
        setBoolean("isUpdateForbelongDate", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s ����ҵ��Ĭ��ҵ���������property 
     */
    public com.kingdee.eas.fdc.sellhouse.BizDateToEnum getChangeRoomDefaultBelongDate()
    {
        return com.kingdee.eas.fdc.sellhouse.BizDateToEnum.getEnum(getString("changeRoomDefaultBelongDate"));
    }
    public void setChangeRoomDefaultBelongDate(com.kingdee.eas.fdc.sellhouse.BizDateToEnum item)
    {
		if (item != null) {
        setString("changeRoomDefaultBelongDate", item.getValue());
		}
    }
    /**
     * Object:��¥����-��Ŀ����'s �۸���ҵ��Ĭ��ҵ���������property 
     */
    public com.kingdee.eas.fdc.sellhouse.BizDateToEnum getPriceChangeDefaultBelongDate()
    {
        return com.kingdee.eas.fdc.sellhouse.BizDateToEnum.getEnum(getString("priceChangeDefaultBelongDate"));
    }
    public void setPriceChangeDefaultBelongDate(com.kingdee.eas.fdc.sellhouse.BizDateToEnum item)
    {
		if (item != null) {
        setString("priceChangeDefaultBelongDate", item.getValue());
		}
    }
    /**
     * Object:��¥����-��Ŀ����'s ����ҵ��Ĭ��ҵ���������property 
     */
    public com.kingdee.eas.fdc.sellhouse.BizDateToEnum getChangeNameDefaultBelongDate()
    {
        return com.kingdee.eas.fdc.sellhouse.BizDateToEnum.getEnum(getString("changeNameDefaultBelongDate"));
    }
    public void setChangeNameDefaultBelongDate(com.kingdee.eas.fdc.sellhouse.BizDateToEnum item)
    {
		if (item != null) {
        setString("changeNameDefaultBelongDate", item.getValue());
		}
    }
    /**
     * Object:��¥����-��Ŀ����'s �ǿ��۷�Դ�Ƿ�����ԤԼ�ź�property 
     */
    public boolean isIsSincerForNotSell()
    {
        return getBoolean("isSincerForNotSell");
    }
    public void setIsSincerForNotSell(boolean item)
    {
        setBoolean("isSincerForNotSell", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s �Ƿ����ø�����Ч��property 
     */
    public boolean isIsUseTrackLimeDate()
    {
        return getBoolean("isUseTrackLimeDate");
    }
    public void setIsUseTrackLimeDate(boolean item)
    {
        setBoolean("isUseTrackLimeDate", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s �ͻ�������Ч��property 
     */
    public int getCustomerTrackLimitDate()
    {
        return getInt("customerTrackLimitDate");
    }
    public void setCustomerTrackLimitDate(int item)
    {
        setInt("customerTrackLimitDate", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s �̻�������Ч��property 
     */
    public int getCommerTrackLimitDate()
    {
        return getInt("commerTrackLimitDate");
    }
    public void setCommerTrackLimitDate(int item)
    {
        setInt("commerTrackLimitDate", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s �̻���Ч��property 
     */
    public int getCommerLimitDate()
    {
        return getInt("commerLimitDate");
    }
    public void setCommerLimitDate(int item)
    {
        setInt("commerLimitDate", item);
    }
    /**
     * Object:��¥����-��Ŀ����'s �ͻ������ظ�����property 
     */
    public com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum getNameRepeatRule()
    {
        return com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum.getEnum(getString("nameRepeatRule"));
    }
    public void setNameRepeatRule(com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum item)
    {
		if (item != null) {
        setString("nameRepeatRule", item.getValue());
		}
    }
    /**
     * Object:��¥����-��Ŀ����'s �ͻ��绰�����ظ�����property 
     */
    public com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum getPhoneRepeatRule()
    {
        return com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum.getEnum(getString("phoneRepeatRule"));
    }
    public void setPhoneRepeatRule(com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum item)
    {
		if (item != null) {
        setString("phoneRepeatRule", item.getValue());
		}
    }
    /**
     * Object:��¥����-��Ŀ����'s �ͻ�֤�������ظ�����property 
     */
    public com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum getCerRepeatRule()
    {
        return com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum.getEnum(getString("cerRepeatRule"));
    }
    public void setCerRepeatRule(com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum item)
    {
		if (item != null) {
        setString("cerRepeatRule", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("42F0C45E");
    }
}