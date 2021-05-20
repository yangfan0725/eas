package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierAppraiseTemplateE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierAppraiseTemplateE1Info()
    {
        this("id");
    }
    protected AbstractMarketSupplierAppraiseTemplateE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ָ���¼ 's null property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ָ���¼ 's ָ������ property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo getIndexName()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo)get("IndexName");
    }
    public void setIndexName(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo item)
    {
        put("IndexName", item);
    }
    /**
     * Object:ָ���¼'s ����ά��property 
     */
    public String getAccreditationwd()
    {
        return getString("Accreditationwd");
    }
    public void setAccreditationwd(String item)
    {
        setString("Accreditationwd", item);
    }
    /**
     * Object:ָ���¼'s 3�ֱ�׼property 
     */
    public String getThreeStandard()
    {
        return getString("threeStandard");
    }
    public void setThreeStandard(String item)
    {
        setString("threeStandard", item);
    }
    /**
     * Object:ָ���¼'s ָ������property 
     */
    public String getIndexDesc()
    {
        return getString("IndexDesc");
    }
    public void setIndexDesc(String item)
    {
        setString("IndexDesc", item);
    }
    /**
     * Object:ָ���¼'s �������property 
     */
    public com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum getScoreType()
    {
        return com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum.getEnum(getString("ScoreType"));
    }
    public void setScoreType(com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum item)
    {
		if (item != null) {
        setString("ScoreType", item.getValue());
		}
    }
    /**
     * Object:ָ���¼'s Ȩ��%property 
     */
    public java.math.BigDecimal getQz()
    {
        return getBigDecimal("qz");
    }
    public void setQz(java.math.BigDecimal item)
    {
        setBigDecimal("qz", item);
    }
    /**
     * Object:ָ���¼'s ��עproperty 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("58B92046");
    }
}