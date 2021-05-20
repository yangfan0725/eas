package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierChangeGradeInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractSupplierChangeGradeInfo()
    {
        this("id");
    }
    protected AbstractSupplierChangeGradeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 供应商等级变更申请单 's 供应商 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object: 供应商等级变更申请单 's 调整等级 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo getGrade()
    {
        return (com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo)get("grade");
    }
    public void setGrade(com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo item)
    {
        put("grade", item);
    }
    /**
     * Object: 供应商等级变更申请单 's 申请人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getApplier()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("applier");
    }
    public void setApplier(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("applier", item);
    }
    /**
     * Object: 供应商等级变更申请单 's 申请小组 property 
     */
    public com.kingdee.eas.custom.purchasebaseinfomation.PurchaseCategoryInfo getPurchaseCategory()
    {
        return (com.kingdee.eas.custom.purchasebaseinfomation.PurchaseCategoryInfo)get("purchaseCategory");
    }
    public void setPurchaseCategory(com.kingdee.eas.custom.purchasebaseinfomation.PurchaseCategoryInfo item)
    {
        put("purchaseCategory", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4A3FEB63");
    }
}