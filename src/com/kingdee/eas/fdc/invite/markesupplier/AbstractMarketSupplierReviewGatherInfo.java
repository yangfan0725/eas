package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierReviewGatherInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMarketSupplierReviewGatherInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierReviewGatherInfo(String pkField)
    {
        super(pkField);
        put("contractEntry", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherContractEntryCollection());
        put("evaluationEntry", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherEvaluationEntryCollection());
        put("entry", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherEntryCollection());
        put("Person", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherPersonCollection());
    }
    /**
     * Object: ��Ӧ��������� 's �������� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo getEvaluationType()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo)get("evaluationType");
    }
    public void setEvaluationType(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo item)
    {
        put("evaluationType", item);
    }
    /**
     * Object: ��Ӧ��������� 's ��Ӧ��������ܷ�¼ property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherEntryCollection)get("entry");
    }
    /**
     * Object: ��Ӧ��������� 's ��Ӧ�� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object: ��Ӧ��������� 's �ȼ� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpInfo getGrade()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpInfo)get("grade");
    }
    public void setGrade(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpInfo item)
    {
        put("grade", item);
    }
    /**
     * Object: ��Ӧ��������� 's ����ǰ�ȼ� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpInfo getSrcGrade()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpInfo)get("srcGrade");
    }
    public void setSrcGrade(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpInfo item)
    {
        put("srcGrade", item);
    }
    /**
     * Object:��Ӧ���������'s �Ƿ�ϸ�property 
     */
    public com.kingdee.eas.fdc.invite.supplier.IsGradeEnum getIsPass()
    {
        return com.kingdee.eas.fdc.invite.supplier.IsGradeEnum.getEnum(getInt("isPass"));
    }
    public void setIsPass(com.kingdee.eas.fdc.invite.supplier.IsGradeEnum item)
    {
		if (item != null) {
        setInt("isPass", item.getValue());
		}
    }
    /**
     * Object:��Ӧ���������'s ����ǰ�Ƿ�ϸ�property 
     */
    public com.kingdee.eas.fdc.invite.supplier.IsGradeEnum getSrcIsPass()
    {
        return com.kingdee.eas.fdc.invite.supplier.IsGradeEnum.getEnum(getInt("srcIsPass"));
    }
    public void setSrcIsPass(com.kingdee.eas.fdc.invite.supplier.IsGradeEnum item)
    {
		if (item != null) {
        setInt("srcIsPass", item.getValue());
		}
    }
    /**
     * Object: ��Ӧ��������� 's ����ģ�� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo getTemplate()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo)get("template");
    }
    public void setTemplate(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo item)
    {
        put("template", item);
    }
    /**
     * Object: ��Ӧ��������� 's �����¼ property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherEvaluationEntryCollection getEvaluationEntry()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherEvaluationEntryCollection)get("evaluationEntry");
    }
    /**
     * Object: ��Ӧ��������� 's ����ǰ���� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketLevelSetUpInfo getSrcLevel()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketLevelSetUpInfo)get("srcLevel");
    }
    public void setSrcLevel(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketLevelSetUpInfo item)
    {
        put("srcLevel", item);
    }
    /**
     * Object: ��Ӧ��������� 's ���� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketLevelSetUpInfo getLevel()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketLevelSetUpInfo)get("level");
    }
    public void setLevel(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketLevelSetUpInfo item)
    {
        put("level", item);
    }
    /**
     * Object:��Ӧ���������'s �Ƿ���ֹproperty 
     */
    public boolean isIsOver()
    {
        return getBoolean("isOver");
    }
    public void setIsOver(boolean item)
    {
        setBoolean("isOver", item);
    }
    /**
     * Object:��Ӧ���������'s ��Լ���������÷�property 
     */
    public java.math.BigDecimal getLygcScore()
    {
        return getBigDecimal("lygcScore");
    }
    public void setLygcScore(java.math.BigDecimal item)
    {
        setBigDecimal("lygcScore", item);
    }
    /**
     * Object:��Ӧ���������'s ��Լ����������ռ���أ�%��property 
     */
    public java.math.BigDecimal getLygcRate()
    {
        return getBigDecimal("lygcRate");
    }
    public void setLygcRate(java.math.BigDecimal item)
    {
        setBigDecimal("lygcRate", item);
    }
    /**
     * Object:��Ӧ���������'s ��Լ�������÷�property 
     */
    public java.math.BigDecimal getLyhScroe()
    {
        return getBigDecimal("lyhScroe");
    }
    public void setLyhScroe(java.math.BigDecimal item)
    {
        setBigDecimal("lyhScroe", item);
    }
    /**
     * Object:��Ӧ���������'s ��Լ��������ռ���أ�%��property 
     */
    public java.math.BigDecimal getLyhRate()
    {
        return getBigDecimal("lyhRate");
    }
    public void setLyhRate(java.math.BigDecimal item)
    {
        setBigDecimal("lyhRate", item);
    }
    /**
     * Object: ��Ӧ��������� 's ��ͬ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherContractEntryCollection getContractEntry()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherContractEntryCollection)get("contractEntry");
    }
    /**
     * Object: ��Ӧ��������� 's ����������Ա��¼ property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherPersonCollection getPerson()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherPersonCollection)get("Person");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3C4D1DC9");
    }
}