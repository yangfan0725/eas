package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierReviewGatherEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierReviewGatherEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierReviewGatherEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ӧ��������ܷ�¼'s �ۺϵ÷�property 
     */
    public java.math.BigDecimal getScore()
    {
        return getBigDecimal("score");
    }
    public void setScore(java.math.BigDecimal item)
    {
        setBigDecimal("score", item);
    }
    /**
     * Object: ��Ӧ��������ܷ�¼ 's ��Ӧ��������� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ��Ӧ��������ܷ�¼ 's ��Ӧ�������¼ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateInfo getAuditTemplate()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateInfo)get("auditTemplate");
    }
    public void setAuditTemplate(com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateInfo item)
    {
        put("auditTemplate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("130E551B");
    }
}