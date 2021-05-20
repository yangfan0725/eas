package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRevListInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractRevListInfo()
    {
        this("id");
    }
    protected AbstractRevListInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�տ���ϸ's Ӧ�ս��property 
     */
    public java.math.BigDecimal getAppAmount()
    {
        return getBigDecimal("appAmount");
    }
    public void setAppAmount(java.math.BigDecimal item)
    {
        setBigDecimal("appAmount", item);
    }
    /**
     * Object:�տ���ϸ's Ӧ������property 
     */
    public java.util.Date getAppDate()
    {
        return getDate("appDate");
    }
    public void setAppDate(java.util.Date item)
    {
        setDate("appDate", item);
    }
    /**
     * Object: �տ���ϸ 's ���� property 
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
     * Object:�տ���ϸ's ʵ�ս��property 
     */
    public java.math.BigDecimal getActRevAmount()
    {
        return getBigDecimal("actRevAmount");
    }
    public void setActRevAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actRevAmount", item);
    }
    /**
     * Object:�տ���ϸ's ������ý��property 
     */
    public java.math.BigDecimal getToFeeAmount()
    {
        return getBigDecimal("toFeeAmount");
    }
    public void setToFeeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("toFeeAmount", item);
    }
    /**
     * Object:�տ���ϸ's ��ת����ý��property 
     */
    public java.math.BigDecimal getHasToFeeAmount()
    {
        return getBigDecimal("hasToFeeAmount");
    }
    public void setHasToFeeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("hasToFeeAmount", item);
    }
    /**
     * Object:�տ���ϸ's ���ƽ��property 
     */
    public java.math.BigDecimal getLimitAmount()
    {
        return getBigDecimal("limitAmount");
    }
    public void setLimitAmount(java.math.BigDecimal item)
    {
        setBigDecimal("limitAmount", item);
    }
    /**
     * Object:�տ���ϸ's ��ת���property 
     */
    public java.math.BigDecimal getHasTransferredAmount()
    {
        return getBigDecimal("hasTransferredAmount");
    }
    public void setHasTransferredAmount(java.math.BigDecimal item)
    {
        setBigDecimal("hasTransferredAmount", item);
    }
    /**
     * Object:�տ���ϸ's ���˽��property 
     */
    public java.math.BigDecimal getHasRefundmentAmount()
    {
        return getBigDecimal("hasRefundmentAmount");
    }
    public void setHasRefundmentAmount(java.math.BigDecimal item)
    {
        setBigDecimal("hasRefundmentAmount", item);
    }
    /**
     * Object:�տ���ϸ's �ѵ����property 
     */
    public java.math.BigDecimal getHasAdjustedAmount()
    {
        return getBigDecimal("hasAdjustedAmount");
    }
    public void setHasAdjustedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("hasAdjustedAmount", item);
    }
    /**
     * Object:�տ���ϸ's �Ƿ�ʣ�������property 
     */
    public boolean isIsRemainCanRefundment()
    {
        return getBoolean("isRemainCanRefundment");
    }
    public void setIsRemainCanRefundment(boolean item)
    {
        setBoolean("isRemainCanRefundment", item);
    }
    /**
     * Object:�տ���ϸ's �տ�����ʶproperty 
     */
    public com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum getRevMoneyType()
    {
        return com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum.getEnum(getString("revMoneyType"));
    }
    public void setRevMoneyType(com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum item)
    {
		if (item != null) {
        setString("revMoneyType", item.getValue());
		}
    }
    /**
     * Object:�տ���ϸ's �˿�����ʶproperty 
     */
    public com.kingdee.eas.fdc.basecrm.RefundmentMoneyTypeEnum getRefundmentMoneyType()
    {
        return com.kingdee.eas.fdc.basecrm.RefundmentMoneyTypeEnum.getEnum(getString("refundmentMoneyType"));
    }
    public void setRefundmentMoneyType(com.kingdee.eas.fdc.basecrm.RefundmentMoneyTypeEnum item)
    {
		if (item != null) {
        setString("refundmentMoneyType", item.getValue());
		}
    }
    /**
     * Object:�տ���ϸ's �Ƿ�ɳ���property 
     */
    public boolean isIsCanRevBeyond()
    {
        return getBoolean("isCanRevBeyond");
    }
    public void setIsCanRevBeyond(boolean item)
    {
        setBoolean("isCanRevBeyond", item);
    }
    /**
     * Object:�տ���ϸ's ���ÿ����ʶproperty 
     */
    public com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum getFeeMoneyType()
    {
        return com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum.getEnum(getString("feeMoneyType"));
    }
    public void setFeeMoneyType(com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum item)
    {
		if (item != null) {
        setString("feeMoneyType", item.getValue());
		}
    }
    /**
     * Object:�տ���ϸ's ���property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:�տ���ϸ's ʵ������property 
     */
    public java.util.Date getActRevDate()
    {
        return getDate("actRevDate");
    }
    public void setActRevDate(java.util.Date item)
    {
        setDate("actRevDate", item);
    }
    /**
     * Object:�տ���ϸ's ��Ʊ���property 
     */
    public java.math.BigDecimal getInvoiceAmount()
    {
        return getBigDecimal("invoiceAmount");
    }
    public void setInvoiceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E004F905");
    }
}