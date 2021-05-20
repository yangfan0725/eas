package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplitBillEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractFDCSplitBillEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCSplitBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 成本拆分分录 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object: 成本拆分分录 's 产品 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("product");
    }
    public void setProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("product", item);
    }
    /**
     * Object:成本拆分分录's 归属金额property 
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
     * Object:成本拆分分录's 拆分类型property 
     */
    public com.kingdee.eas.fdc.basedata.CostSplitTypeEnum getSplitType()
    {
        return com.kingdee.eas.fdc.basedata.CostSplitTypeEnum.getEnum(getString("splitType"));
    }
    public void setSplitType(com.kingdee.eas.fdc.basedata.CostSplitTypeEnum item)
    {
		if (item != null) {
        setString("splitType", item.getValue());
		}
    }
    /**
     * Object:成本拆分分录's 级次property 
     */
    public int getLevel()
    {
        return getInt("level");
    }
    public void setLevel(int item)
    {
        setInt("level", item);
    }
    /**
     * Object:成本拆分分录's 是否叶子节点property 
     */
    public boolean isIsLeaf()
    {
        return getBoolean("isLeaf");
    }
    public void setIsLeaf(boolean item)
    {
        setBoolean("isLeaf", item);
    }
    /**
     * Object: 成本拆分分录 's 分摊类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ApportionTypeInfo getApportionType()
    {
        return (com.kingdee.eas.fdc.basedata.ApportionTypeInfo)get("apportionType");
    }
    public void setApportionType(com.kingdee.eas.fdc.basedata.ApportionTypeInfo item)
    {
        put("apportionType", item);
    }
    /**
     * Object:成本拆分分录's 分摊比值property 
     */
    public java.math.BigDecimal getApportionValue()
    {
        return getBigDecimal("apportionValue");
    }
    public void setApportionValue(java.math.BigDecimal item)
    {
        setBigDecimal("apportionValue", item);
    }
    /**
     * Object:成本拆分分录's 分摊比值汇总property 
     */
    public java.math.BigDecimal getApportionValueTotal()
    {
        return getBigDecimal("apportionValueTotal");
    }
    public void setApportionValueTotal(java.math.BigDecimal item)
    {
        setBigDecimal("apportionValueTotal", item);
    }
    /**
     * Object:成本拆分分录's 其他比值property 
     */
    public java.math.BigDecimal getOtherRatio()
    {
        return getBigDecimal("otherRatio");
    }
    public void setOtherRatio(java.math.BigDecimal item)
    {
        setBigDecimal("otherRatio", item);
    }
    /**
     * Object:成本拆分分录's 其他比值汇总property 
     */
    public java.math.BigDecimal getOtherRatioTotal()
    {
        return getBigDecimal("otherRatioTotal");
    }
    public void setOtherRatioTotal(java.math.BigDecimal item)
    {
        setBigDecimal("otherRatioTotal", item);
    }
    /**
     * Object:成本拆分分录's 直接费用property 
     */
    public java.math.BigDecimal getDirectAmount()
    {
        return getBigDecimal("directAmount");
    }
    public void setDirectAmount(java.math.BigDecimal item)
    {
        setBigDecimal("directAmount", item);
    }
    /**
     * Object:成本拆分分录's 直接费用汇总property 
     */
    public java.math.BigDecimal getDirectAmountTotal()
    {
        return getBigDecimal("directAmountTotal");
    }
    public void setDirectAmountTotal(java.math.BigDecimal item)
    {
        setBigDecimal("directAmountTotal", item);
    }
    /**
     * Object:成本拆分分录's 是否参与分摊property 
     */
    public boolean isIsApportion()
    {
        return getBoolean("isApportion");
    }
    public void setIsApportion(boolean item)
    {
        setBoolean("isApportion", item);
    }
    /**
     * Object:成本拆分分录's 是否附加科目property 
     */
    public boolean isIsAddlAccount()
    {
        return getBoolean("isAddlAccount");
    }
    public void setIsAddlAccount(boolean item)
    {
        setBoolean("isAddlAccount", item);
    }
    /**
     * Object:成本拆分分录's 成本来源单据property 
     */
    public com.kingdee.bos.util.BOSUuid getCostBillId()
    {
        return getBOSUuid("costBillId");
    }
    public void setCostBillId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("costBillId", item);
    }
    /**
     * Object:成本拆分分录's 序号property 
     */
    public int getIndex()
    {
        return getInt("index");
    }
    public void setIndex(int item)
    {
        setInt("index", item);
    }
    /**
     * Object:成本拆分分录's 指标刷新用指标IDproperty 
     */
    public String getIdxApportionId()
    {
        return getString("idxApportionId");
    }
    public void setIdxApportionId(String item)
    {
        setString("idxApportionId", item);
    }
    /**
     * Object:成本拆分分录's 拆分单据IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getSplitBillId()
    {
        return getBOSUuid("splitBillId");
    }
    public void setSplitBillId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("splitBillId", item);
    }
    /**
     * Object:成本拆分分录's 单价property 
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
     * Object:成本拆分分录's 工程量property 
     */
    public java.math.BigDecimal getWorkLoad()
    {
        return getBigDecimal("workLoad");
    }
    public void setWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("workLoad", item);
    }
    /**
     * Object:成本拆分分录's 拆分比例property 
     */
    public java.math.BigDecimal getSplitScale()
    {
        return getBigDecimal("splitScale");
    }
    public void setSplitScale(java.math.BigDecimal item)
    {
        setBigDecimal("splitScale", item);
    }
    /**
     * Object:成本拆分分录's 税率property 
     */
    public java.math.BigDecimal getTaxRate()
    {
        return getBigDecimal("taxRate");
    }
    public void setTaxRate(java.math.BigDecimal item)
    {
        setBigDecimal("taxRate", item);
    }
    /**
     * Object:成本拆分分录's 税额property 
     */
    public java.math.BigDecimal getTaxAmount()
    {
        return getBigDecimal("taxAmount");
    }
    public void setTaxAmount(java.math.BigDecimal item)
    {
        setBigDecimal("taxAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9BC2ACC4");
    }
}