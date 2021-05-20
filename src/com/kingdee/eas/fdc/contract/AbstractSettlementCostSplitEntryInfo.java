package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettlementCostSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractSettlementCostSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractSettlementCostSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 结算单拆分分录 's 父 property 
     */
    public com.kingdee.eas.fdc.contract.SettlementCostSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.SettlementCostSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.SettlementCostSplitInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:结算单拆分分录's 合同签证金额property 
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
     * Object:结算单拆分分录's 变更签证金额property 
     */
    public java.math.BigDecimal getChangeAmt()
    {
        return getBigDecimal("changeAmt");
    }
    public void setChangeAmt(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmt", item);
    }
    /**
     * Object:结算单拆分分录's 直接金额property 
     */
    public java.math.BigDecimal getDirectAmt()
    {
        return getBigDecimal("directAmt");
    }
    public void setDirectAmt(java.math.BigDecimal item)
    {
        setBigDecimal("directAmt", item);
    }
    /**
     * Object:结算单拆分分录's 归属保修金金额property 
     */
    public java.math.BigDecimal getGrtSplitAmt()
    {
        return getBigDecimal("grtSplitAmt");
    }
    public void setGrtSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("grtSplitAmt", item);
    }
    /**
     * Object: 结算单拆分分录 's 会计科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountView()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountView");
    }
    public void setAccountView(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountView", item);
    }
    /**
     * Object:结算单拆分分录's 合同工程量property 
     */
    public java.math.BigDecimal getContractWorkLoad()
    {
        return getBigDecimal("contractWorkLoad");
    }
    public void setContractWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("contractWorkLoad", item);
    }
    /**
     * Object:结算单拆分分录's 合同单价property 
     */
    public java.math.BigDecimal getContractPrice()
    {
        return getBigDecimal("contractPrice");
    }
    public void setContractPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractPrice", item);
    }
    /**
     * Object:结算单拆分分录's 变更工程量property 
     */
    public java.math.BigDecimal getChangeWorkLoad()
    {
        return getBigDecimal("changeWorkLoad");
    }
    public void setChangeWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("changeWorkLoad", item);
    }
    /**
     * Object:结算单拆分分录's 变更单价property 
     */
    public java.math.BigDecimal getChangePrice()
    {
        return getBigDecimal("changePrice");
    }
    public void setChangePrice(java.math.BigDecimal item)
    {
        setBigDecimal("changePrice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EB18A7E9");
    }
}