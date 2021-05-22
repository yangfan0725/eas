package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractChangeSettleBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractChangeSettleBillInfo()
    {
        this("id");
    }
    protected AbstractContractChangeSettleBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.contract.ConChangeSettleEntryCollection());
    }
    /**
     * Object:���ȷ�ϵ�'s �Ƿ������ʩ��property 
     */
    public boolean isIsFinish()
    {
        return getBoolean("isFinish");
    }
    public void setIsFinish(boolean item)
    {
        setBoolean("isFinish", item);
    }
    /**
     * Object:���ȷ�ϵ�'s ���ɷ�ʽproperty 
     */
    public com.kingdee.eas.fdc.contract.ResponsibleStyleEnum getResponsibleStyle()
    {
        return com.kingdee.eas.fdc.contract.ResponsibleStyleEnum.getEnum(getString("responsibleStyle"));
    }
    public void setResponsibleStyle(com.kingdee.eas.fdc.contract.ResponsibleStyleEnum item)
    {
		if (item != null) {
        setString("responsibleStyle", item.getValue());
		}
    }
    /**
     * Object:���ȷ�ϵ�'s ���½��property 
     */
    public java.math.BigDecimal getLastAmount()
    {
        return getBigDecimal("lastAmount");
    }
    public void setLastAmount(java.math.BigDecimal item)
    {
        setBigDecimal("lastAmount", item);
    }
    /**
     * Object:���ȷ�ϵ�'s �䶯ԭ��˵��property 
     */
    public String getReasonDescription()
    {
        return getString("reasonDescription");
    }
    public void setReasonDescription(String item)
    {
        setString("reasonDescription", item);
    }
    /**
     * Object:���ȷ�ϵ�'s ����������property 
     */
    public String getColseDescription()
    {
        return getString("colseDescription");
    }
    public void setColseDescription(String item)
    {
        setString("colseDescription", item);
    }
    /**
     * Object:���ȷ�ϵ�'s �������property 
     */
    public String getChangeReson()
    {
        return getString("changeReson");
    }
    public void setChangeReson(String item)
    {
        setString("changeReson", item);
    }
    /**
     * Object: ���ȷ�ϵ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.ConChangeSettleEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ConChangeSettleEntryCollection)get("entrys");
    }
    /**
     * Object: ���ȷ�ϵ� 's �а���λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object: ���ȷ�ϵ� 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object: ���ȷ�ϵ� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object:���ȷ�ϵ�'s ʩ����������property 
     */
    public java.math.BigDecimal getReportAmount()
    {
        return getBigDecimal("reportAmount");
    }
    public void setReportAmount(java.math.BigDecimal item)
    {
        setBigDecimal("reportAmount", item);
    }
    /**
     * Object:���ȷ�ϵ�'s �����󶨽��property 
     */
    public java.math.BigDecimal getAllowAmount()
    {
        return getBigDecimal("allowAmount");
    }
    public void setAllowAmount(java.math.BigDecimal item)
    {
        setBigDecimal("allowAmount", item);
    }
    /**
     * Object: ���ȷ�ϵ� 's ���ָ� property 
     */
    public com.kingdee.eas.fdc.contract.ContractChangeBillInfo getConChangeBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractChangeBillInfo)get("conChangeBill");
    }
    public void setConChangeBill(com.kingdee.eas.fdc.contract.ContractChangeBillInfo item)
    {
        put("conChangeBill", item);
    }
    /**
     * Object:���ȷ�ϵ�'s ��Ч�ɱ�property 
     */
    public java.math.BigDecimal getWxcb()
    {
        return getBigDecimal("wxcb");
    }
    public void setWxcb(java.math.BigDecimal item)
    {
        setBigDecimal("wxcb", item);
    }
    /**
     * Object:���ȷ�ϵ�'s �Ƿ���ú˶�property 
     */
    public boolean isIsFee()
    {
        return getBoolean("isFee");
    }
    public void setIsFee(boolean item)
    {
        setBoolean("isFee", item);
    }
    /**
     * Object:���ȷ�ϵ�'s oaְλproperty 
     */
    public String getOaPosition()
    {
        return getString("oaPosition");
    }
    public void setOaPosition(String item)
    {
        setString("oaPosition", item);
    }
    /**
     * Object:���ȷ�ϵ�'s �������property 
     */
    public String getOaOpinion()
    {
        return getString("oaOpinion");
    }
    public void setOaOpinion(String item)
    {
        setString("oaOpinion", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A12C522F");
    }
}