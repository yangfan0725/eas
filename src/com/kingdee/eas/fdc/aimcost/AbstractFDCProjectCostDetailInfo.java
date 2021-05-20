package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProjectCostDetailInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractFDCProjectCostDetailInfo()
    {
        this("id");
    }
    protected AbstractFDCProjectCostDetailInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.aimcost.FDCProjectCostDetailPlanEntryCollection());
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ������ĿIDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getProjectID()
    {
        return getBOSUuid("projectID");
    }
    public void setProjectID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("projectID", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ������Ŀ����property 
     */
    public String getProjectNumber()
    {
        return getString("projectNumber");
    }
    public void setProjectNumber(String item)
    {
        setString("projectNumber", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ������Ŀ����property 
     */
    public String getProjectName()
    {
        return getProjectName((Locale)null);
    }
    public void setProjectName(String item)
    {
		setProjectName(item,(Locale)null);
    }
    public String getProjectName(Locale local)
    {
        return TypeConversionUtils.objToString(get("projectName", local));
    }
    public void setProjectName(String item, Locale local)
    {
        put("projectName", item, local);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's �ɱ���������property 
     */
    public com.kingdee.bos.util.BOSUuid getCostCenterID()
    {
        return getBOSUuid("costCenterID");
    }
    public void setCostCenterID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("costCenterID", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's �ɱ����ı���property 
     */
    public String getCostCenterNumber()
    {
        return getString("costCenterNumber");
    }
    public void setCostCenterNumber(String item)
    {
        setString("costCenterNumber", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's �ɱ���������property 
     */
    public String getCostCenterName()
    {
        return getCostCenterName((Locale)null);
    }
    public void setCostCenterName(String item)
    {
		setCostCenterName(item,(Locale)null);
    }
    public String getCostCenterName(Locale local)
    {
        return TypeConversionUtils.objToString(get("costCenterName", local));
    }
    public void setCostCenterName(String item, Locale local)
    {
        put("costCenterName", item, local);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's �ɱ���ĿIDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getCostAccountID()
    {
        return getBOSUuid("costAccountID");
    }
    public void setCostAccountID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("costAccountID", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's �ɱ���Ŀ����property 
     */
    public String getCostAccountNumber()
    {
        return getString("costAccountNumber");
    }
    public void setCostAccountNumber(String item)
    {
        setString("costAccountNumber", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's �ɱ���Ŀ����property 
     */
    public String getCostAccountName()
    {
        return getCostAccountName((Locale)null);
    }
    public void setCostAccountName(String item)
    {
		setCostAccountName(item,(Locale)null);
    }
    public String getCostAccountName(Locale local)
    {
        return TypeConversionUtils.objToString(get("costAccountName", local));
    }
    public void setCostAccountName(String item, Locale local)
    {
        put("costAccountName", item, local);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's Ŀ��ɱ����property 
     */
    public java.math.BigDecimal getAimCostAmt()
    {
        return getBigDecimal("aimCostAmt");
    }
    public void setAimCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("aimCostAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's �ѷ����ɱ����property 
     */
    public java.math.BigDecimal getSoFarHasAmt()
    {
        return getBigDecimal("soFarHasAmt");
    }
    public void setSoFarHasAmt(java.math.BigDecimal item)
    {
        setBigDecimal("soFarHasAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's �������ɱ����property 
     */
    public java.math.BigDecimal getSoFarToAmt()
    {
        return getBigDecimal("soFarToAmt");
    }
    public void setSoFarToAmt(java.math.BigDecimal item)
    {
        setBigDecimal("soFarToAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��̬�ɱ����property 
     */
    public java.math.BigDecimal getDynCostAmt()
    {
        return getBigDecimal("dynCostAmt");
    }
    public void setDynCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("dynCostAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬIDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getContractID()
    {
        return getBOSUuid("contractID");
    }
    public void setContractID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("contractID", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ����property 
     */
    public String getContractNumber()
    {
        return getString("contractNumber");
    }
    public void setContractNumber(String item)
    {
        setString("contractNumber", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ����property 
     */
    public String getContractName()
    {
        return getContractName((Locale)null);
    }
    public void setContractName(String item)
    {
		setContractName(item,(Locale)null);
    }
    public String getContractName(Locale local)
    {
        return TypeConversionUtils.objToString(get("contractName", local));
    }
    public void setContractName(String item, Locale local)
    {
        put("contractName", item, local);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's �Ƿ����ı���ͬproperty 
     */
    public boolean isIsNoText()
    {
        return getBoolean("isNoText");
    }
    public void setIsNoText(boolean item)
    {
        setBoolean("isNoText", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ��ֽ��property 
     */
    public java.math.BigDecimal getContractSplitAmt()
    {
        return getBigDecimal("contractSplitAmt");
    }
    public void setContractSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractSplitAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��������ֽ��property 
     */
    public java.math.BigDecimal getWorkloadSplitAmt()
    {
        return getBigDecimal("workloadSplitAmt");
    }
    public void setWorkloadSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("workloadSplitAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's �����ֽ��property 
     */
    public java.math.BigDecimal getPaymentSplitAmt()
    {
        return getBigDecimal("paymentSplitAmt");
    }
    public void setPaymentSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("paymentSplitAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's �����ֽ��property 
     */
    public java.math.BigDecimal getChangeSplitAmt()
    {
        return getBigDecimal("changeSplitAmt");
    }
    public void setChangeSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("changeSplitAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's �����ֽ��property 
     */
    public java.math.BigDecimal getSettleSplitAmt()
    {
        return getBigDecimal("settleSplitAmt");
    }
    public void setSettleSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settleSplitAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��Ŀ��ʵ�ֲ�ֵproperty 
     */
    public java.math.BigDecimal getTotalSettleAmt()
    {
        return getBigDecimal("totalSettleAmt");
    }
    public void setTotalSettleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("totalSettleAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ���ڹ�����ĿIDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getContractProjectID()
    {
        return getBOSUuid("contractProjectID");
    }
    public void setContractProjectID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("contractProjectID", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ���ڹ�����Ŀ����property 
     */
    public String getContractProjectNumber()
    {
        return getString("contractProjectNumber");
    }
    public void setContractProjectNumber(String item)
    {
        setString("contractProjectNumber", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ���ڹ�������property 
     */
    public String getContractProjectName()
    {
        return getContractProjectName((Locale)null);
    }
    public void setContractProjectName(String item)
    {
		setContractProjectName(item,(Locale)null);
    }
    public String getContractProjectName(Locale local)
    {
        return TypeConversionUtils.objToString(get("contractProjectName", local));
    }
    public void setContractProjectName(String item, Locale local)
    {
        put("contractProjectName", item, local);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ�ҷ�����property 
     */
    public String getPartBName()
    {
        return getPartBName((Locale)null);
    }
    public void setPartBName(String item)
    {
		setPartBName(item,(Locale)null);
    }
    public String getPartBName(Locale local)
    {
        return TypeConversionUtils.objToString(get("partBName", local));
    }
    public void setPartBName(String item, Locale local)
    {
        put("partBName", item, local);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬǩԼ����property 
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
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬǩԼ���property 
     */
    public java.math.BigDecimal getContractAmt()
    {
        return getBigDecimal("contractAmt");
    }
    public void setContractAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's �ұ�����property 
     */
    public String getCurrencyName()
    {
        return getCurrencyName((Locale)null);
    }
    public void setCurrencyName(String item)
    {
		setCurrencyName(item,(Locale)null);
    }
    public String getCurrencyName(Locale local)
    {
        return TypeConversionUtils.objToString(get("currencyName", local));
    }
    public void setCurrencyName(String item, Locale local)
    {
        put("currencyName", item, local);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ���ҽ��property 
     */
    public java.math.BigDecimal getContractLocalAmt()
    {
        return getBigDecimal("contractLocalAmt");
    }
    public void setContractLocalAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractLocalAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ�������property 
     */
    public java.math.BigDecimal getContractLastAmt()
    {
        return getBigDecimal("contractLastAmt");
    }
    public void setContractLastAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractLastAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ�Ѹ����property 
     */
    public java.math.BigDecimal getContractPayAmt()
    {
        return getBigDecimal("contractPayAmt");
    }
    public void setContractPayAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractPayAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ�Ѹ����ȿ�property 
     */
    public java.math.BigDecimal getContractPayProgAmt()
    {
        return getBigDecimal("contractPayProgAmt");
    }
    public void setContractPayProgAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractPayProgAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ�Ѹ������property 
     */
    public java.math.BigDecimal getContractPaySetlAmt()
    {
        return getBigDecimal("contractPaySetlAmt");
    }
    public void setContractPaySetlAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractPaySetlAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ�Ѹ����޿�property 
     */
    public java.math.BigDecimal getContractPayKeepAmt()
    {
        return getBigDecimal("contractPayKeepAmt");
    }
    public void setContractPayKeepAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractPayKeepAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬӦ�����property 
     */
    public java.math.BigDecimal getContractReqAmt()
    {
        return getBigDecimal("contractReqAmt");
    }
    public void setContractReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractReqAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬӦ�����ȿ�property 
     */
    public java.math.BigDecimal getContractReqProgAmt()
    {
        return getBigDecimal("contractReqProgAmt");
    }
    public void setContractReqProgAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractReqProgAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬӦ�������property 
     */
    public java.math.BigDecimal getContractReqSetlAmt()
    {
        return getBigDecimal("contractReqSetlAmt");
    }
    public void setContractReqSetlAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractReqSetlAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬӦ�����޿�property 
     */
    public java.math.BigDecimal getContractReqKeepAmt()
    {
        return getBigDecimal("contractReqKeepAmt");
    }
    public void setContractReqKeepAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractReqKeepAmt", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��ͬ��ʵ�ֲ�ֵproperty 
     */
    public java.math.BigDecimal getContractTotalSettle()
    {
        return getBigDecimal("contractTotalSettle");
    }
    public void setContractTotalSettle(java.math.BigDecimal item)
    {
        setBigDecimal("contractTotalSettle", item);
    }
    /**
     * Object: ���ز�������Ŀ�ɱ���ϸ 's �ƻ���¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.FDCProjectCostDetailPlanEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.FDCProjectCostDetailPlanEntryCollection)get("entrys");
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:���ز�������Ŀ�ɱ���ϸ's ��property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("10ACC969");
    }
}