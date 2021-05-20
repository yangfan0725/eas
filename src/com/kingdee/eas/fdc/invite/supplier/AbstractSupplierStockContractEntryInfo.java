package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierStockContractEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierStockContractEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierStockContractEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ҵ���������Ҫҵ�� 's ��Ӧ�̵��� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("head", item);
    }
    /**
     * Object:��ҵ���������Ҫҵ��'s ��ͬ����property 
     */
    public String getContractName()
    {
        return getString("contractName");
    }
    public void setContractName(String item)
    {
        setString("contractName", item);
    }
    /**
     * Object:��ҵ���������Ҫҵ��'s ��ͬ���property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    /**
     * Object:��ҵ���������Ҫҵ��'s ���̵ص�property 
     */
    public String getPlace()
    {
        return getString("place");
    }
    public void setPlace(String item)
    {
        setString("place", item);
    }
    /**
     * Object:��ҵ���������Ҫҵ��'s �׷�����property 
     */
    public String getSupplierName()
    {
        return getString("supplierName");
    }
    public void setSupplierName(String item)
    {
        setString("supplierName", item);
    }
    /**
     * Object:��ҵ���������Ҫҵ��'s �а�ģʽproperty 
     */
    public String getModel()
    {
        return getString("model");
    }
    public void setModel(String item)
    {
        setString("model", item);
    }
    /**
     * Object:��ҵ���������Ҫҵ��'s ��Ŀ����property 
     */
    public String getManager()
    {
        return getString("manager");
    }
    public void setManager(String item)
    {
        setString("manager", item);
    }
    /**
     * Object:��ҵ���������Ҫҵ��'s ����ְ�ģʽproperty 
     */
    public String getWorkModel()
    {
        return getString("workModel");
    }
    public void setWorkModel(String item)
    {
        setString("workModel", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("82AE6E2E");
    }
}