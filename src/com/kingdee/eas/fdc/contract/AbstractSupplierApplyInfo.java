package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierApplyInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractSupplierApplyInfo()
    {
        this("id");
    }
    protected AbstractSupplierApplyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:供应商增加申请单's 纳税人识别号property 
     */
    public String getTaxerNum()
    {
        return getString("taxerNum");
    }
    public void setTaxerNum(String item)
    {
        setString("taxerNum", item);
    }
    /**
     * Object:供应商增加申请单's 开户银行property 
     */
    public String getBank()
    {
        return getString("bank");
    }
    public void setBank(String item)
    {
        setString("bank", item);
    }
    /**
     * Object:供应商增加申请单's 纳税人资质property 
     */
    public com.kingdee.eas.fdc.contract.app.TaxerQuaEnum getTaxerQua()
    {
        return com.kingdee.eas.fdc.contract.app.TaxerQuaEnum.getEnum(getString("taxerQua"));
    }
    public void setTaxerQua(com.kingdee.eas.fdc.contract.app.TaxerQuaEnum item)
    {
		if (item != null) {
        setString("taxerQua", item.getValue());
		}
    }
    /**
     * Object:供应商增加申请单's 银行账号property 
     */
    public String getBankAccount()
    {
        return getString("bankAccount");
    }
    public void setBankAccount(String item)
    {
        setString("bankAccount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3B110EA7");
    }
}