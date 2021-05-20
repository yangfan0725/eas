/*jadclipse*/package com.kingdee.eas.fi.cas.app;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.*;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysConstant;
import com.kingdee.eas.fi.cas.*;
import com.kingdee.eas.fm.common.*;
import com.kingdee.eas.fm.common.util.AccountCurrencyVerifyUtil;
import java.math.BigDecimal;
import java.util.HashMap;
public class BSBatchSubmitVerifyDelegate
{
            public BSBatchSubmitVerifyDelegate(Context ctx)
            {





/*  28*/        acctView_Map = new HashMap();

/*  30*/        this.ctx = null;

/*  32*/        currentCompany = null;


/*  35*/        this.ctx = ctx;
/*  36*/        currentCompany = ContextHelperFactory.getLocalInstance(ctx).getCurrentCompany();
            }
            public void validate(BankStatementInfo info)
                throws EASBizException, BOSException
            {

/*  42*/        if(info.getCompany() == null)
/*  43*/            throw new CashCommonException(CashCommonException.NULL_COMPANY);

/*  45*/        if(info.getCurrency() == null)
/*  46*/            throw new CashCommonException(CashCommonException.NULL_CURRENCY);

/*  48*/        if(info.getAccountBank() == null)
/*  49*/            throw new CashCommonException(CashCommonException.NULL_BANK);


/*  52*/        if(info.getDebitAmount() == null && info.getCreditAmount() == null)
/*  53*/            throw new BankStatementException(BankStatementException.DEBIT_CREDIT_NULL);


/*  56*/        Object sett = info.get("settleType");
/*  57*/        if(!(sett instanceof SettlementTypeInfo))


/*  60*/            info.setSettleType(null);


/*  63*/        String number = info.getSettleNumber();
/*  64*/        if(FMHelper.isEmpty(number))
/*  65*/            info.setSettleNumber(null);


/*  68*/        FMVerifyHelper.checkBlank(ctx, info, "createDate");
/*  69*/        AccountViewInfo accountView = info.getAccountView();
/*  70*/        if(accountView == null)
/*  71*/            throw new CasException(CasException.ACCT_NULL);

/*  73*/        ObjectUuidPK acctViewPK = new ObjectUuidPK(accountView.getId());

/*  75*/        if(acctView_Map.get(acctViewPK) != null)
                {/*  76*/            accountView = (AccountViewInfo)acctView_Map.get(acctViewPK);
                } else
                {
/*  79*/            accountView = AccountViewFactory.getLocalInstance(ctx).getAccountViewInfo(acctViewPK, getAcctViewSelector());

/*  81*/            acctView_Map.put(acctViewPK, accountView);
                }



/*  86*/        if(!accountView.isIsBank() && !accountView.isIsCash())
/*  87*/            throw new CasException(CasException.ACCT_NOTCASBANK, new Object[] {/*  87*/                accountView.getNumber()
                    });

/*  90*/        if(info.getDebitAmount() != null && info.getDebitAmount().signum() > 0)
                {/*  91*/            info.setIsDebit(true);
/*  92*/            info.setCreditAmount(SysConstant.BIGZERO);
                } else/*  93*/        if(info.getCreditAmount() != null && info.getCreditAmount().signum() < 0)
                {
/*  95*/            info.setIsDebit(false);
/*  96*/            info.setDebitAmount(SysConstant.BIGZERO);
                }
///*  98*/        if(info.getDebitAmount().signum() > 0 && info.getCreditAmount().signum() > 0)
//                {
///* 100*/            throw new BankStatementException(BankStatementException.DEBIT_OR_CREDIT);
//                } else
//                {
///* 103*/            String currencyID = info.getCurrency().getId().toString();
///* 104*/            CurrencyInfo baseCurrency = ContextHelperFactory.getLocalInstance(ctx).getCompanyBaseCurrency(currentCompany);
//
///* 106*/            AccountCurrencyVerifyUtil.verifyAccountCurrency(ctx, accountView, currencyID, baseCurrency.getId().toString());
///* 107*/            return;
//                }
            }
            public SelectorItemCollection getAcctViewSelector()
            {/* 111*/        SelectorItemCollection sic = new SelectorItemCollection();
/* 112*/        sic.add(new SelectorItemInfo("*"));
/* 113*/        sic.add(new SelectorItemInfo("accountingcurrency"));
/* 114*/        sic.add(new SelectorItemInfo("accountCurrency.id"));
/* 115*/        sic.add(new SelectorItemInfo("accountCurrency.currency"));
/* 116*/        sic.add(new SelectorItemInfo("accountCurrency.currency.id"));
/* 117*/        return sic;
            }
            private HashMap acctView_Map;
            Context ctx;
            CompanyOrgUnitInfo currentCompany;
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\server\eas\fi_cas-server.jar
	Total time: 270 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/