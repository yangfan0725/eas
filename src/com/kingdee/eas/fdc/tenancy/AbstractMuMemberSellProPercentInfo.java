package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMuMemberSellProPercentInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMuMemberSellProPercentInfo()
    {
        this("id");
    }
    protected AbstractMuMemberSellProPercentInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ӫ����Ա��Ŀ���� 's Ӫ����Ա property 
     */
    public com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo getMuMember()
    {
        return (com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo)get("muMember");
    }
    public void setMuMember(com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo item)
    {
        put("muMember", item);
    }
    /**
     * Object: Ӫ����Ա��Ŀ���� 's Ӫ����Ŀ property 
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
     * Object:Ӫ����Ա��Ŀ����'s �ۼƼ���Ӷ��property 
     */
    public java.math.BigDecimal getSumCommision()
    {
        return getBigDecimal("sumCommision");
    }
    public void setSumCommision(java.math.BigDecimal item)
    {
        setBigDecimal("sumCommision", item);
    }
    /**
     * Object:Ӫ����Ա��Ŀ����'s �ۼƳ���Ӧ��property 
     */
    public java.math.BigDecimal getSumGeneralCom()
    {
        return getBigDecimal("sumGeneralCom");
    }
    public void setSumGeneralCom(java.math.BigDecimal item)
    {
        setBigDecimal("sumGeneralCom", item);
    }
    /**
     * Object:Ӫ����Ա��Ŀ����'s �ۼ�����Ӧ��property 
     */
    public java.math.BigDecimal getSumSubsistenceCom()
    {
        return getBigDecimal("sumSubsistenceCom");
    }
    public void setSumSubsistenceCom(java.math.BigDecimal item)
    {
        setBigDecimal("sumSubsistenceCom", item);
    }
    /**
     * Object:Ӫ����Ա��Ŀ����'s �ۼ��ѷ�Ӷ��property 
     */
    public java.math.BigDecimal getSumProvide()
    {
        return getBigDecimal("sumProvide");
    }
    public void setSumProvide(java.math.BigDecimal item)
    {
        setBigDecimal("sumProvide", item);
    }
    /**
     * Object:Ӫ����Ա��Ŀ����'s �ۼƳ����ѷ�property 
     */
    public java.math.BigDecimal getSumGeneralProd()
    {
        return getBigDecimal("sumGeneralProd");
    }
    public void setSumGeneralProd(java.math.BigDecimal item)
    {
        setBigDecimal("sumGeneralProd", item);
    }
    /**
     * Object:Ӫ����Ա��Ŀ����'s �ۼ������ѷ�property 
     */
    public java.math.BigDecimal getSumSubsistenceProd()
    {
        return getBigDecimal("sumSubsistenceProd");
    }
    public void setSumSubsistenceProd(java.math.BigDecimal item)
    {
        setBigDecimal("sumSubsistenceProd", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BC5E45B5");
    }
}