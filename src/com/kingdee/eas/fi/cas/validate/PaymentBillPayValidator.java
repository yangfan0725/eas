package com.kingdee.eas.fi.cas.validate;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.cas.*;
import com.kingdee.eas.fi.cas.validate.AbstractPaymentBillValidator;
import com.kingdee.eas.fi.cas.validate.ValidateResult;
import com.kingdee.eas.fm.common.*;
import java.util.HashSet;
import java.util.Set;
public class PaymentBillPayValidator extends AbstractPaymentBillValidator
{
            public PaymentBillPayValidator()
            {














/*  35*/        agentBillColl = null;

/*  37*/        needComit = false;
            }
            protected void init(Context ctx)
            {


///*  43*/        IContextHelper ctxHelper = ContextHelperFactory.getLocalInstance(ctx);
///*  44*/        CompanyOrgUnitInfo companyInfo = ctxHelper.getCurrentCompany();
///*  45*/        needComit = ctxHelper.getBooleanParam("CS011", new ObjectUuidPK(companyInfo.getId()));
            }
            public ValidateResult validate(Context ctx, IObjectCollection abstractObjectCollection, boolean throwException)
                throws BOSException, EASBizException
            {







/*  57*/        PaymentBillCollection coll = (PaymentBillCollection)abstractObjectCollection;
/*  58*/        getAgentBillCollection(ctx, coll);

/*  60*/        apValidate(ctx, coll);

/*  62*/        return super.validate(ctx, abstractObjectCollection, throwException);
            }
            private void apValidate(Context ctx, PaymentBillCollection coll)
                throws BOSException, CasForArApException
            {







/*  74*/        Set apSet = new HashSet();
/*  75*/        int i = 0;/*  75*/        for(int j = coll.size(); i < j; i++)
                {/*  76*/            PaymentBillInfo info = coll.get(i);
/*  77*/            if(info.getSourceType() == SourceTypeEnum.AP && info.getBillStatus().equals(BillStatusEnum.AUDITED))


/*  80*/                apSet.add(info.getId().toString());
                }
            }
            private void getAgentBillCollection(Context ctx, PaymentBillCollection coll)
                throws BOSException
            {




























/* 114*/        Set agentBillIDSet = new HashSet();
/* 115*/        int i = 0;/* 115*/        for(int size = coll.size(); i < size; i++)
                {/* 116*/            PaymentBillInfo paymentbill = coll.get(i);
/* 117*/            if(!FMHelper.isEmpty(paymentbill.getAgentPaymentBillID()))
/* 118*/                agentBillIDSet.add(paymentbill.getAgentPaymentBillID());
                }



/* 123*/        if(agentBillIDSet.size() > 0)
                {
/* 125*/            EntityViewInfo view = new EntityViewInfo();
/* 126*/            FilterInfo filter = new FilterInfo();
/* 127*/            view.setFilter(filter);
/* 128*/            filter.getFilterItems().add(new FilterItemInfo("id", agentBillIDSet, CompareType.INCLUDE));


/* 131*/            agentBillColl = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(view);
                }
            }
            public void internValidate(Context ctx, IObjectValue objectValue)
                throws BOSException, EASBizException
            {



/* 140*/        PaymentBillInfo info = (PaymentBillInfo)objectValue;
/* 141*/        checkAcctViewIsValid(ctx, info);
/* 142*/        if(info.getBillStatus().equals(BillStatusEnum.PAYED))
/* 143*/            throw new RecPayException(RecPayException.CANNOT_PAY_AFTER_PAYED);


/* 146*/        if(!info.getBillStatus().equals(BillStatusEnum.AUDITED))
/* 147*/            throw new RecPayException(RecPayException.CANNOT_PAY_BEFORE_AUDITED);



/* 151*/        if(agentBillColl != null)
                {/* 152*/            String agentBillID = info.getAgentPaymentBillID();


/* 155*/            if(!FMHelper.isEmpty(agentBillID) && agentBillColl.contains(BOSUuid.read(agentBillID)))
                    {
/* 157*/                PaymentBillInfo agentBillInfo = agentBillColl.get(BOSUuid.read(agentBillID));


/* 160*/                if(!BillStatusEnum.PAYED.equals(agentBillInfo.getBillStatus()))

/* 162*/                    throw new CasException(CasException.AGENTBILLNOTPAYCANTPAY);
                    }
                }



/* 168*/        if(info.needCommitCH() && needComit)
/* 169*/            throw new RecPayException(RecPayException.CANNOTPAY);


/* 172*/        boolean isAgentBill = info.getPaymentBillType().equals(CasRecPayBillTypeEnum.AgentType);
/* 173*/        boolean isTurstAcctBank = info.getPayerAccountBank() != null && !info.getPayerAccountBank().getCompany().getId().equals(info.getCompany().getId());



/* 177*/        if((!isAgentBill || !isTurstAcctBank) && info.getPayerAccount() == null)
/* 178*/            throw new RecPayException(RecPayException.PAYERACCOUNT_CANNOT_BE_NULL);



/* 182*/        if(info.getSourceType() != SourceTypeEnum.AP && info.getBillStatus().equals(BillStatusEnum.AUDITED))
                {


/* 186*/            boolean isRecBySettle = checkIsActionFromSettle(ctx, info.getId().toString());

/* 188*/            boolean isRecValid = true;
/* 189*/            IContextHelper ctxHelper = ContextHelperFactory.getLocalInstance(ctx);
/* 190*/            boolean isSettleAutoRecPay = ctxHelper.getBooleanParam("CS062", new ObjectUuidPK(info.getCompany().getId()));


/* 193*/            if(!isRecBySettle && isSettleAutoRecPay)
/* 194*/                isRecValid = isCanManualRecPay(ctx, info);

/* 196*/            if(!isRecValid)

/* 198*/                throw new RecPayException(RecPayException.CANNOT_PAY_CS062);
                }
            }
            private PaymentBillCollection agentBillColl;
            private boolean needComit;
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\patch\sp-fi_cas-server.jar
	Total time: 250 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/