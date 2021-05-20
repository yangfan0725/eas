package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierReviewGatherSurveyInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractSupplierReviewGatherSurveyInfo()
    {
        this("id");
    }
    protected AbstractSupplierReviewGatherSurveyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ¿¼²ì¸Å¿ö 's Ä£°å property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo getTemplate()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo)get("template");
    }
    public void setTemplate(com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo item)
    {
        put("template", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6701EFD1");
    }
}