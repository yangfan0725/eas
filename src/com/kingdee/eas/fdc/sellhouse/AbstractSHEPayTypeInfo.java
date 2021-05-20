package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEPayTypeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractSHEPayTypeInfo()
    {
        this("id");
    }
    protected AbstractSHEPayTypeInfo(String pkField)
    {
        super(pkField);
        put("payLists", new com.kingdee.eas.fdc.sellhouse.PayListEntryCollection());
        put("bizLists", new com.kingdee.eas.fdc.sellhouse.BizListEntryCollection());
    }
    /**
     * Object: ����� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:�����'s �ۿ�property 
     */
    public java.math.BigDecimal getAgio()
    {
        return getBigDecimal("agio");
    }
    public void setAgio(java.math.BigDecimal item)
    {
        setBigDecimal("agio", item);
    }
    /**
     * Object:�����'s ���Ҵ�����㷽��property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum getLoanCalculateType()
    {
        return com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum.getEnum(getString("loanCalculateType"));
    }
    public void setLoanCalculateType(com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum item)
    {
		if (item != null) {
        setString("loanCalculateType", item.getValue());
		}
    }
    /**
     * Object:�����'s ��������㷽��property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum getAccumulationCalculateType()
    {
        return com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum.getEnum(getString("accumulationCalculateType"));
    }
    public void setAccumulationCalculateType(com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum item)
    {
		if (item != null) {
        setString("accumulationCalculateType", item.getValue());
		}
    }
    /**
     * Object:�����'s ��������property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:�����'s ���Ҿ���property 
     */
    public int getLoanPre()
    {
        return getInt("loanPre");
    }
    public void setLoanPre(int item)
    {
        setInt("loanPre", item);
    }
    /**
     * Object:�����'s �����𾫶�property 
     */
    public int getAfPre()
    {
        return getInt("afPre");
    }
    public void setAfPre(int item)
    {
        setInt("afPre", item);
    }
    /**
     * Object: ����� 's ������ϸ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PayListEntryCollection getPayLists()
    {
        return (com.kingdee.eas.fdc.sellhouse.PayListEntryCollection)get("payLists");
    }
    /**
     * Object: ����� 's ҵ���¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BizListEntryCollection getBizLists()
    {
        return (com.kingdee.eas.fdc.sellhouse.BizListEntryCollection)get("bizLists");
    }
    /**
     * Object: ����� 's ��������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanDataInfo getLoanLoanData()
    {
        return (com.kingdee.eas.fdc.sellhouse.LoanDataInfo)get("loanLoanData");
    }
    public void setLoanLoanData(com.kingdee.eas.fdc.sellhouse.LoanDataInfo item)
    {
        put("loanLoanData", item);
    }
    /**
     * Object: ����� 's ������������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanDataInfo getAfLoanData()
    {
        return (com.kingdee.eas.fdc.sellhouse.LoanDataInfo)get("afLoanData");
    }
    public void setAfLoanData(com.kingdee.eas.fdc.sellhouse.LoanDataInfo item)
    {
        put("afLoanData", item);
    }
    /**
     * Object:�����'s ��Ч����property 
     */
    public java.util.Date getValidDate()
    {
        return getDate("validDate");
    }
    public void setValidDate(java.util.Date item)
    {
        setDate("validDate", item);
    }
    /**
     * Object:�����'s ʧЧ����property 
     */
    public java.util.Date getInvalidDate()
    {
        return getDate("invalidDate");
    }
    public void setInvalidDate(java.util.Date item)
    {
        setDate("invalidDate", item);
    }
    /**
     * Object:�����'s �Ƿ�ɾ��property 
     */
    public boolean isIsDelete()
    {
        return getBoolean("isDelete");
    }
    public void setIsDelete(boolean item)
    {
        setBoolean("isDelete", item);
    }
    /**
     * Object:�����'s �����Ƿ���뷿��property 
     */
    public boolean isIsTotal()
    {
        return getBoolean("isTotal");
    }
    public void setIsTotal(boolean item)
    {
        setBoolean("isTotal", item);
    }
    /**
     * Object:�����'s �Ƿ񰴽�property 
     */
    public boolean isIsLoan()
    {
        return getBoolean("isLoan");
    }
    public void setIsLoan(boolean item)
    {
        setBoolean("isLoan", item);
    }
    /**
     * Object: ����� 's �������� property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getLoanBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("loanBank");
    }
    public void setLoanBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("loanBank", item);
    }
    /**
     * Object: ����� 's ���������� property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getAcfBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("acfBank");
    }
    public void setAcfBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("acfBank", item);
    }
    /**
     * Object: ����� 's �����𷽰� property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo getAfScheme()
    {
        return (com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo)get("afScheme");
    }
    public void setAfScheme(com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo item)
    {
        put("afScheme", item);
    }
    /**
     * Object: ����� 's ���ҷ��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo getLoanScheme()
    {
        return (com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo)get("loanScheme");
    }
    public void setLoanScheme(com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo item)
    {
        put("loanScheme", item);
    }
    /**
     * Object:�����'s �°��Ҿ���property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanPreEnum getNewLoanPre()
    {
        return com.kingdee.eas.fdc.sellhouse.LoanPreEnum.getEnum(getString("newLoanPre"));
    }
    public void setNewLoanPre(com.kingdee.eas.fdc.sellhouse.LoanPreEnum item)
    {
		if (item != null) {
        setString("newLoanPre", item.getValue());
		}
    }
    /**
     * Object:�����'s �¹����𾫶�property 
     */
    public com.kingdee.eas.fdc.sellhouse.AfPreEnum getNewAfPre()
    {
        return com.kingdee.eas.fdc.sellhouse.AfPreEnum.getEnum(getString("newAfPre"));
    }
    public void setNewAfPre(com.kingdee.eas.fdc.sellhouse.AfPreEnum item)
    {
		if (item != null) {
        setString("newAfPre", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DFF2AA4D");
    }
}