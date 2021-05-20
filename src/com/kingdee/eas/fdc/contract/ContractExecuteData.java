/*jadclipse*/package com.kingdee.eas.fdc.contract;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
public class ContractExecuteData
    implements Serializable, Comparable
{
            public ContractExecuteData()
            {
/* <-MISALIGNED-> */ /*  10*/        children = new ArrayList();
            }
            public String getPartB()
            {






































/*  54*/        return partB;
            }
            public void setPartB(String partB)
            {
/*  58*/        this.partB = partB;
            }
            public BigDecimal getNotPay()
            {
/*  62*/        return notPay;
            }
            public void setNotPay(BigDecimal notPay)
            {
/*  66*/        this.notPay = notPay;
            }
            public BigDecimal getCompleteNotPay()
            {
/*  70*/        return completeNotPay;
            }
            public void setCompleteNotPay(BigDecimal completeNotPay)
            {
/*  74*/        this.completeNotPay = completeNotPay;
            }
            public List getChildren()
            {
/*  78*/        return children;
            }
            public void setChildren(List children)
            {
/*  82*/        this.children = children;
            }
            public ContractBillInfo getContract()
            {
/*  86*/        return contract;
            }
            public void setContract(ContractBillInfo contract)
            {
/*  90*/        this.contract = contract;
            }
            public BigDecimal getContractLastAmount()
            {
/*  94*/        return contractLastAmount;
            }
            public void setContractLastAmount(BigDecimal contractLastAmount)
            {
/*  98*/        this.contractLastAmount = contractLastAmount;
            }
            public BigDecimal getTotalSettPrice()
            {
/* 102*/        return totalSettPrice;
            }
            public void setTotalSettPrice(BigDecimal totalSettPrice)
            {
/* 106*/        this.totalSettPrice = totalSettPrice;
            }
            public Object getPlanPayId()
            {
/* 110*/        return planPayId;
            }
            public void setPlanPayId(Object planPayId)
            {
/* 114*/        this.planPayId = planPayId;
            }
            public Object getPlanPayDate()
            {
/* 118*/        return planPayDate;
            }
            public void setPlanPayDate(Object planPayDate)
            {
/* 122*/        this.planPayDate = planPayDate;
            }
            public BigDecimal getPlanPayAmount()
            {
/* 126*/        return planPayAmount;
            }
            public void setPlanPayAmount(BigDecimal planPayAmount)
            {
/* 130*/        this.planPayAmount = planPayAmount;
            }
            public Object getRealPayId()
            {
/* 134*/        return realPayId;
            }
            public void setRealPayId(Object realPayId)
            {
/* 138*/        this.realPayId = realPayId;
            }
            public Object getRealPayDate()
            {
/* 142*/        return realPayDate;
            }
            public void setRealPayDate(Object realPayDate)
            {
/* 146*/        this.realPayDate = realPayDate;
            }
            public BigDecimal getRealPaySrcAmount()
            {
/* 150*/        return realPaySrcAmount;
            }
            public void setRealPaySrcAmount(BigDecimal realPaySrcAmount)
            {
/* 154*/        this.realPaySrcAmount = realPaySrcAmount;
            }
            public BigDecimal getRealPayAmount()
            {
/* 158*/        return realPayAmount;
            }
            public void setRealPayAmount(BigDecimal realPayAmount)
            {
/* 162*/        this.realPayAmount = realPayAmount;
            }
            public BigDecimal getProjectPriceInContract()
            {
/* 166*/        return projectPriceInContract;
            }
            public void setProjectPriceInContract(BigDecimal projectPriceInContract)
            {
/* 170*/        this.projectPriceInContract = projectPriceInContract;
            }
            public BigDecimal getCompleteProjectAmount()
            {

/* 175*/        return completeProjectAmount;
            }
            public void setCompleteProjectAmount(BigDecimal completeProjectAmount)
            {
/* 179*/        this.completeProjectAmount = completeProjectAmount;
            }
            public BigDecimal getChangeAmount()
            {
/* 183*/        return changeAmount;
            }
            public void setChangeAmount(BigDecimal changeAmount)
            {
/* 187*/        this.changeAmount = changeAmount;
            }
            public ContractWithoutTextInfo getNoTextContract()
            {/* 190*/        return noTextContract;
            }
            public void setNoTextContract(ContractWithoutTextInfo noTextContract)
            {
/* 194*/        this.noTextContract = noTextContract;
            }
            public BigDecimal getContractLastSrcAmount()
            {
/* 198*/        return contractLastSrcAmount;
            }
            public void setContractLastSrcAmount(BigDecimal contractLastSrcAmount)
            {
/* 202*/        this.contractLastSrcAmount = contractLastSrcAmount;
            }
            public BigDecimal getPlanPaySrcAmount()
            {
/* 206*/        return planPaySrcAmount;
            }
            public void setPlanPaySrcAmount(BigDecimal planPaySrcAmount)
            {
/* 210*/        this.planPaySrcAmount = planPaySrcAmount;
            }
            public Object getPayBizDate()
            {
/* 214*/        return payBizDate;
            }
            public void setPayBizDate(Object payBizDate)
            {
/* 218*/        this.payBizDate = payBizDate;
            }
            public int compareTo(Object arg0)
            {

/* 223*/        ContractExecuteData other = (ContractExecuteData)arg0;
/* 224*/        String otherProjectName = "";
/* 225*/        if(other.getContract() != null)
/* 226*/            otherProjectName = other.getContract().getCurProject().getName();

/* 228*/        else/* 228*/            otherProjectName = other.getNoTextContract().getCurProject().getName();

/* 230*/        String projectName = "";
/* 231*/        if(getContract() != null)
/* 232*/            projectName = getContract().getCurProject().getName();

/* 234*/        else/* 234*/            projectName = getNoTextContract().getCurProject().getName();

/* 236*/        return projectName.compareTo(otherProjectName);
            }
            private List children;
            private ContractBillInfo contract;
            private ContractWithoutTextInfo noTextContract;
            private String partB;
            private BigDecimal notPay;
            private BigDecimal completeNotPay;
            private BigDecimal completeProjectAmount;
            private BigDecimal changeAmount;
            private BigDecimal contractLastAmount;
            private BigDecimal contractLastSrcAmount;
            private BigDecimal totalSettPrice;
            private Object planPayId;
            private Object planPayDate;
            private BigDecimal planPayAmount;
            private BigDecimal planPaySrcAmount;
            private Object realPayId;
            private Object realPayDate;
            private BigDecimal realPaySrcAmount;
            private BigDecimal realPayAmount;
            private BigDecimal projectPriceInContract;
            private Object payBizDate;
            private BigDecimal deductAmt;
			public BigDecimal getDeductAmt() {
				return deductAmt;
			}
			public void setDeductAmt(BigDecimal deductAmt) {
				this.deductAmt = deductAmt;
			}
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_contract_client.jar
	Total time: 218 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/