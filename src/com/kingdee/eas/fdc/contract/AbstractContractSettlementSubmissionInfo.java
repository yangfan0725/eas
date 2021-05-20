package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractSettlementSubmissionInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractSettlementSubmissionInfo()
    {
        this("id");
    }
    protected AbstractContractSettlementSubmissionInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ͬ�������� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: ��ͬ�������� 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object:��ͬ��������'s �걨��property 
     */
    public String getApplier()
    {
        return getString("applier");
    }
    public void setApplier(String item)
    {
        setString("applier", item);
    }
    /**
     * Object:��ͬ��������'s �׷�ҵ�񾭰���property 
     */
    public String getPartAer()
    {
        return getString("partAer");
    }
    public void setPartAer(String item)
    {
        setString("partAer", item);
    }
    /**
     * Object:��ͬ��������'s ������property 
     */
    public String getCCer()
    {
        return getString("cCer");
    }
    public void setCCer(String item)
    {
        setString("cCer", item);
    }
    /**
     * Object:��ͬ��������'s ���㷶Χproperty 
     */
    public String getScope()
    {
        return getString("scope");
    }
    public void setScope(String item)
    {
        setString("scope", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9BC03542");
    }
}