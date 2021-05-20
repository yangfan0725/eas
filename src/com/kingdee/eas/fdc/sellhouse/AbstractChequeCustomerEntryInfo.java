package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChequeCustomerEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractChequeCustomerEntryInfo()
    {
        this("id");
    }
    protected AbstractChequeCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ʊ�ݿͻ���¼ 's Ʊ��ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo getCheque()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo)get("cheque");
    }
    public void setCheque(com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo item)
    {
        put("cheque", item);
    }
    /**
     * Object: Ʊ�ݿͻ���¼ 's �ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("customer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B35F8BD8");
    }
}