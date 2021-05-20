package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingContractInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractProgrammingContractInfo()
    {
        this("id");
    }
    protected AbstractProgrammingContractInfo(String pkField)
    {
        super(pkField);
        put("costEntries", new com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection());
        put("economyEntries", new com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection());
    }
    /**
     * Object: ��Լ�滮 's ��Ŀ��Լ�滮 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingInfo getProgramming()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingInfo)get("programming");
    }
    public void setProgramming(com.kingdee.eas.fdc.contract.programming.ProgrammingInfo item)
    {
        put("programming", item);
    }
    /**
     * Object: ��Լ�滮 's �ϼ��滮��Լ property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��Լ�滮's �滮���property 
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
     * Object:��Լ�滮's ���ƽ��property 
     */
    public java.math.BigDecimal getControlAmount()
    {
        return getBigDecimal("controlAmount");
    }
    public void setControlAmount(java.math.BigDecimal item)
    {
        setBigDecimal("controlAmount", item);
    }
    /**
     * Object:��Լ�滮's �滮���property 
     */
    public java.math.BigDecimal getBalance()
    {
        return getBigDecimal("balance");
    }
    public void setBalance(java.math.BigDecimal item)
    {
        setBigDecimal("balance", item);
    }
    /**
     * Object:��Լ�滮's �������property 
     */
    public java.math.BigDecimal getControlBalance()
    {
        return getBigDecimal("controlBalance");
    }
    public void setControlBalance(java.math.BigDecimal item)
    {
        setBigDecimal("controlBalance", item);
    }
    /**
     * Object:��Լ�滮's ǩԼ���property 
     */
    public java.math.BigDecimal getSignUpAmount()
    {
        return getBigDecimal("signUpAmount");
    }
    public void setSignUpAmount(java.math.BigDecimal item)
    {
        setBigDecimal("signUpAmount", item);
    }
    /**
     * Object:��Լ�滮's ������property 
     */
    public java.math.BigDecimal getChangeAmount()
    {
        return getBigDecimal("changeAmount");
    }
    public void setChangeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmount", item);
    }
    /**
     * Object:��Լ�滮's ������property 
     */
    public java.math.BigDecimal getSettleAmount()
    {
        return getBigDecimal("settleAmount");
    }
    public void setSettleAmount(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmount", item);
    }
    /**
     * Object:��Լ�滮's �Ƿ�����property 
     */
    public boolean isIsCiting()
    {
        return getBoolean("isCiting");
    }
    public void setIsCiting(boolean item)
    {
        setBoolean("isCiting", item);
    }
    /**
     * Object:��Լ�滮's ���ð汾property 
     */
    public int getCiteVersion()
    {
        return getInt("citeVersion");
    }
    public void setCiteVersion(int item)
    {
        setInt("citeVersion", item);
    }
    /**
     * Object:��Լ�滮's �滮��ԼԴIDproperty 
     */
    public String getSrcId()
    {
        return getString("srcId");
    }
    public void setSrcId(String item)
    {
        setString("srcId", item);
    }
    /**
     * Object:��Լ�滮's ����property 
     */
    public String getAttachment()
    {
        return getString("attachment");
    }
    public void setAttachment(String item)
    {
        setString("attachment", item);
    }
    /**
     * Object: ��Լ�滮 's �ɱ����� property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection getCostEntries()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection)get("costEntries");
    }
    /**
     * Object: ��Լ�滮 's �������� property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection getEconomyEntries()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection)get("economyEntries");
    }
    /**
     * Object:��Լ�滮's ������property 
     */
    public int getSortNumber()
    {
        return getInt("sortNumber");
    }
    public void setSortNumber(int item)
    {
        setInt("sortNumber", item);
    }
    /**
     * Object:��Լ�滮's ��������property 
     */
    public String getWorkContent()
    {
        return getString("workContent");
    }
    public void setWorkContent(String item)
    {
        setString("workContent", item);
    }
    /**
     * Object:��Լ�滮's �׹�����ָ����property 
     */
    public String getSupMaterial()
    {
        return getString("supMaterial");
    }
    public void setSupMaterial(String item)
    {
        setString("supMaterial", item);
    }
    /**
     * Object:��Լ�滮's �б귽ʽproperty 
     */
    public com.kingdee.eas.fdc.invite.InviteFormEnum getInviteWay()
    {
        return com.kingdee.eas.fdc.invite.InviteFormEnum.getEnum(getString("inviteWay"));
    }
    public void setInviteWay(com.kingdee.eas.fdc.invite.InviteFormEnum item)
    {
		if (item != null) {
        setString("inviteWay", item.getValue());
		}
    }
    /**
     * Object: ��Լ�滮 's �б���֯ property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getInviteOrg()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("inviteOrg");
    }
    public void setInviteOrg(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("inviteOrg", item);
    }
    /**
     * Object:��Լ�滮's ��������property 
     */
    public java.math.BigDecimal getBuildPerSquare()
    {
        return getBigDecimal("buildPerSquare");
    }
    public void setBuildPerSquare(java.math.BigDecimal item)
    {
        setBigDecimal("buildPerSquare", item);
    }
    /**
     * Object:��Լ�滮's ���۵���property 
     */
    public java.math.BigDecimal getSoldPerSquare()
    {
        return getBigDecimal("soldPerSquare");
    }
    public void setSoldPerSquare(java.math.BigDecimal item)
    {
        setBigDecimal("soldPerSquare", item);
    }
    /**
     * Object:��Լ�滮's �ɱ������������property 
     */
    public String getCostAccountNames()
    {
        return getString("costAccountNames");
    }
    public void setCostAccountNames(String item)
    {
        setString("costAccountNames", item);
    }
    /**
     * Object:��Լ�滮's Ԥ�����property 
     */
    public java.math.BigDecimal getEstimateAmount()
    {
        return getBigDecimal("estimateAmount");
    }
    public void setEstimateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("estimateAmount", item);
    }
    /**
     * Object:��Լ�滮's ���ı���ͬ���property 
     */
    public java.math.BigDecimal getWithOutTextAmount()
    {
        return getBigDecimal("withOutTextAmount");
    }
    public void setWithOutTextAmount(java.math.BigDecimal item)
    {
        setBigDecimal("withOutTextAmount", item);
    }
    /**
     * Object:��Լ�滮's δǩ��ͬ���property 
     */
    public java.math.BigDecimal getBudgetAmount()
    {
        return getBigDecimal("budgetAmount");
    }
    public void setBudgetAmount(java.math.BigDecimal item)
    {
        setBigDecimal("budgetAmount", item);
    }
    /**
     * Object:��Լ�滮's ���ı���ͬ����property 
     */
    public String getWithOutTextNumber()
    {
        return getString("withOutTextNumber");
    }
    public void setWithOutTextNumber(String item)
    {
        setString("withOutTextNumber", item);
    }
    /**
     * Object:��Լ�滮's ���ı���ͬ����property 
     */
    public String getWithOutTextName()
    {
        return getString("withOutTextName");
    }
    public void setWithOutTextName(String item)
    {
        setString("withOutTextName", item);
    }
    /**
     * Object:��Լ�滮's ��ͬ�����ı���ͬIDproperty 
     */
    public String getBillId()
    {
        return getString("billId");
    }
    public void setBillId(String item)
    {
        setString("billId", item);
    }
    /**
     * Object: ��Լ�滮 's ��ͬ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    /**
     * Object:��Լ�滮's �����ͬ������Ԥ��������property 
     */
    public java.math.BigDecimal getUnAuditContractEA()
    {
        return getBigDecimal("unAuditContractEA");
    }
    public void setUnAuditContractEA(java.math.BigDecimal item)
    {
        setBigDecimal("unAuditContractEA", item);
    }
    /**
     * Object:��Լ�滮's ��ͬ���㷴����Ԥ��������property 
     */
    public java.math.BigDecimal getUnAuditContractSettleEA()
    {
        return getBigDecimal("unAuditContractSettleEA");
    }
    public void setUnAuditContractSettleEA(java.math.BigDecimal item)
    {
        setBigDecimal("unAuditContractSettleEA", item);
    }
    /**
     * Object:��Լ�滮's �����ɹ�property 
     */
    public boolean isIsInvite()
    {
        return getBoolean("isInvite");
    }
    public void setIsInvite(boolean item)
    {
        setBoolean("isInvite", item);
    }
    /**
     * Object:��Լ�滮's �Ƿ����ı�����property 
     */
    public boolean isIsWTCiting()
    {
        return getBoolean("isWTCiting");
    }
    public void setIsWTCiting(boolean item)
    {
        setBoolean("isWTCiting", item);
    }
    /**
     * Object:��Լ�滮's ������ɫproperty 
     */
    public String getCompare()
    {
        return getString("compare");
    }
    public void setCompare(String item)
    {
        setString("compare", item);
    }
    /**
     * Object:��Լ�滮's ���浥��property 
     */
    public java.math.BigDecimal getBuildPrice()
    {
        return getBigDecimal("buildPrice");
    }
    public void setBuildPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildPrice", item);
    }
    /**
     * Object:��Լ�滮's ¼���ۺϵ���property 
     */
    public boolean isIsInput()
    {
        return getBoolean("isInput");
    }
    public void setIsInput(boolean item)
    {
        setBoolean("isInput", item);
    }
    /**
     * Object:��Լ�滮's ʵ�幤����property 
     */
    public java.math.BigDecimal getQuantities()
    {
        return getBigDecimal("quantities");
    }
    public void setQuantities(java.math.BigDecimal item)
    {
        setBigDecimal("quantities", item);
    }
    /**
     * Object:��Լ�滮's ��λproperty 
     */
    public String getUnit()
    {
        return getString("unit");
    }
    public void setUnit(String item)
    {
        setString("unit", item);
    }
    /**
     * Object:��Լ�滮's �ۺϵ���property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:��Լ�滮's �б��ļ���������property 
     */
    public java.util.Date getDocumentsAuditDate()
    {
        return getDate("documentsAuditDate");
    }
    public void setDocumentsAuditDate(java.util.Date item)
    {
        setDate("documentsAuditDate", item);
    }
    /**
     * Object:��Լ�滮's �б���������property 
     */
    public java.util.Date getResultAuditDate()
    {
        return getDate("resultAuditDate");
    }
    public void setResultAuditDate(java.util.Date item)
    {
        setDate("resultAuditDate", item);
    }
    /**
     * Object:��Լ�滮's ��ͬ��������property 
     */
    public java.util.Date getContractAuditDate()
    {
        return getDate("contractAuditDate");
    }
    public void setContractAuditDate(java.util.Date item)
    {
        setDate("contractAuditDate", item);
    }
    /**
     * Object:��Լ�滮's Ԥ�ƽ�������property 
     */
    public java.util.Date getEnterAuditDate()
    {
        return getDate("enterAuditDate");
    }
    public void setEnterAuditDate(java.util.Date item)
    {
        setDate("enterAuditDate", item);
    }
    /**
     * Object:��Լ�滮's ͼֽ����Ʒȷ��property 
     */
    public java.util.Date getPaperDate()
    {
        return getDate("paperDate");
    }
    public void setPaperDate(java.util.Date item)
    {
        setDate("paperDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("ECE079DB");
    }
}