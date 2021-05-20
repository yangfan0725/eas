package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanGatherPayEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectMonthPlanGatherPayEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanGatherPayEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: δ���֧�����ݷ�¼ 's ��Ŀ�¶ȸ���ƻ����� property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherInfo item)
    {
        put("head", item);
    }
    /**
     * Object: δ���֧�����ݷ�¼ 's �������뵥 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getPayRequestBill()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("payRequestBill");
    }
    public void setPayRequestBill(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("payRequestBill", item);
    }
    /**
     * Object: δ���֧�����ݷ�¼ 's ���ı���ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractWithoutTextInfo getContractWithoutText()
    {
        return (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)get("contractWithoutText");
    }
    public void setContractWithoutText(com.kingdee.eas.fdc.contract.ContractWithoutTextInfo item)
    {
        put("contractWithoutText", item);
    }
    /**
     * Object:δ���֧�����ݷ�¼'s ���property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: δ���֧�����ݷ�¼ 's Ԥ����Ŀ property 
     */
    public com.kingdee.eas.ma.budget.BgItemInfo getBgItem()
    {
        return (com.kingdee.eas.ma.budget.BgItemInfo)get("bgItem");
    }
    public void setBgItem(com.kingdee.eas.ma.budget.BgItemInfo item)
    {
        put("bgItem", item);
    }
    /**
     * Object:δ���֧�����ݷ�¼'s �Ѹ����property 
     */
    public java.math.BigDecimal getActPayAmount()
    {
        return getBigDecimal("actPayAmount");
    }
    public void setActPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actPayAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9F5B2364");
    }
}