/*jadclipse*/package com.kingdee.eas.fdc.contract.app;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.*;
import com.kingdee.eas.fdc.contract.programming.*;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.IContextHelper;
import com.kingdee.util.NumericExceptionSubItem;
import java.math.BigDecimal;
import java.util.List;
import org.apache.log4j.Logger;
public class ContractEstimateChangeBillControllerBean extends AbstractContractEstimateChangeBillControllerBean
{
            public ContractEstimateChangeBillControllerBean()
            {
            }
            protected void checkNameDup(Context context, FDCBillInfo fdcbillinfo)
                throws BOSException, EASBizException
            {
            }
            protected IObjectPK _submit(Context ctx, IObjectValue model)
                throws BOSException, EASBizException
            {









































/*  73*/        return super._submit(ctx, model);
            }
            protected IObjectPK _save(Context ctx, IObjectValue model)
                throws BOSException, EASBizException
            {
/*  78*/        return super._save(ctx, model);
            }
            protected void _audit(Context ctx, BOSUuid billId)
                throws BOSException, EASBizException
            {





/*  88*/        IContractEstimateChangeBill iFaced = ContractEstimateChangeBillFactory.getLocalInstance(ctx);


/*  91*/        SelectorItemCollection sel = new SelectorItemCollection();
/*  92*/        sel.add("programmingContract.*");

/*  94*/        ContractEstimateChangeBillInfo info = iFaced.getContractEstimateChangeBillInfo(new ObjectUuidPK(billId), sel);



/*  98*/        ProgrammingContractInfo programmingInfo = info.getProgrammingContract();









/* 108*/        EntityViewInfo view = new EntityViewInfo();
/* 109*/        FilterInfo zFilter = new FilterInfo();
/* 110*/        zFilter.getFilterItems().add(new FilterItemInfo("isLastest", Boolean.TRUE));
/* 111*/        zFilter.getFilterItems().add(new FilterItemInfo("programmingContract.id", info.getProgrammingContract().getId().toString()));
/* 112*/        view.setFilter(zFilter);
/* 113*/        ContractEstimateChangeBillCollection coll1 = iFaced.getContractEstimateChangeBillCollection(view);
/* 114*/        if(coll1.size() > 0)
                {/* 115*/            ContractEstimateChangeBillInfo info1 = coll1.get(0);




/* 120*/            info1.setIsLastest(false);
/* 121*/            SelectorItemCollection sel1 = new SelectorItemCollection();
/* 122*/            sel1.add("isLastest");
/* 123*/            iFaced.updatePartial(info1, sel1);
                }
/* 125*/        SelectorItemCollection coll = new SelectorItemCollection();

/* 127*/        coll.add("estimateAmount");


/* 130*/        programmingInfo.setEstimateAmount(info.getEstimateAmount());
/* 131*/        ProgrammingContractFactory.getLocalInstance(ctx).updatePartial(programmingInfo, coll);

/* 133*/        info.setIsLastest(true);
/* 134*/        SelectorItemCollection sel2 = new SelectorItemCollection();
/* 135*/        sel2.add("isLastest");
/* 136*/        iFaced.updatePartial(info, sel2);
/* 137*/        super._audit(ctx, billId);
            }
            protected void _unAudit(Context ctx, BOSUuid billId)
                throws BOSException, EASBizException
            {































/* 173*/        super._unAudit(ctx, billId);
            }
            protected void _audit(Context ctx, List idList)
                throws BOSException, EASBizException
            {
/* 178*/        super._audit(ctx, idList);
            }
            protected void _unAudit(Context ctx, List idList)
                throws BOSException, EASBizException
            {
/* 183*/        super._unAudit(ctx, idList);
            }
            protected void _isReferenced(Context arg0, IObjectPK arg1)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 186*/        ContractEstimateChangeBillInfo info = getContractEstimateChangeBillInfo(arg0, arg1);
/* <-MISALIGNED-> */ /* 187*/        if(info.getProgrammingContract() != null)
                {
/* <-MISALIGNED-> */ /* 188*/            FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 189*/            filter.getFilterItems().add(new FilterItemInfo("programmingContract.id", info.getProgrammingContract().getId().toString()));
/* <-MISALIGNED-> */ /* 190*/            if(ContractBillFactory.getLocalInstance(arg0).exists(filter))
/* <-MISALIGNED-> */ /* 191*/                throw new EASBizException(new NumericExceptionSubItem("100", "\u5BF9\u5E94\u5408\u7EA6\u89C4\u5212\u5DF2\u7ECF\u88AB\u5408\u540C\u5F15\u7528\uFF0C\u4E0D\u80FD\u8FDB\u884C\u5220\u9664\u64CD\u4F5C\uFF01"));
                }
            }
            protected boolean _sub(Context ctx, ProgrammingContractInfo pc, ContractEstimateChangeTypeEnum type, BigDecimal amount, boolean isSub, String orgId)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 196*/        if(pc != null && type != null)
                {
/* <-MISALIGNED-> */ /* 197*/            if(orgId != null){
	
/* <-MISALIGNED-> */ /* 198*/                String paramValue = ParamControlFactory.getLocalInstance(ctx).getParamValue(new ObjectUuidPK(orgId), "YF_CHECKYG");
/* <-MISALIGNED-> */ /* 200*/                if("false".equals(paramValue))
/* <-MISALIGNED-> */ /* 202*/                    return false;
                }
/* <-MISALIGNED-> */ /* 205*/            if(type.equals(ContractEstimateChangeTypeEnum.SUPPLY) && amount.compareTo(FDCHelper.ZERO) == 0)
/* <-MISALIGNED-> */ /* 206*/                return true;
/* <-MISALIGNED-> */ /* 208*/            SelectorItemCollection sel = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 209*/            sel.add("programmingContract.*");
/* <-MISALIGNED-> */ /* 210*/            sel.add("*");
/* <-MISALIGNED-> */ /* 211*/            sel.add("entry.*");
/* <-MISALIGNED-> */ /* 212*/            EntityViewInfo view = new EntityViewInfo();
/* <-MISALIGNED-> */ /* 213*/            FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 214*/            view.setSelector(sel);
/* <-MISALIGNED-> */ /* 215*/            filter.getFilterItems().add(new FilterItemInfo("isLastest", Boolean.TRUE));
/* <-MISALIGNED-> */ /* 216*/            filter.getFilterItems().add(new FilterItemInfo("programmingContract.id", pc.getId().toString()));
/* <-MISALIGNED-> */ /* 217*/            view.setFilter(filter);
/* <-MISALIGNED-> */ /* 218*/            ContractEstimateChangeBillCollection col = ContractEstimateChangeBillFactory.getLocalInstance(ctx).getContractEstimateChangeBillCollection(view);
/* <-MISALIGNED-> */ /* 219*/            if(col.size() > 0)
                    {
/* <-MISALIGNED-> */ /* 220*/                ContractEstimateChangeBillInfo info = col.get(0);
/* <-MISALIGNED-> */ /* 221*/                BigDecimal entryAmount = FDCHelper.ZERO;
/* <-MISALIGNED-> */ /* 222*/                for(int i = 0; i < info.getEntry().size(); i++)
                        {
/* <-MISALIGNED-> */ /* 223*/                    ContractEstimateChangeEntryInfo entry = info.getEntry().get(i);
/* <-MISALIGNED-> */ /* 224*/                    if(type.equals(entry.getType()))
/* <-MISALIGNED-> */ /* 225*/                        entryAmount = entry.getAmount();
                        }
/* <-MISALIGNED-> */ /* 228*/                if(amount.compareTo(entryAmount) > 0 && !isSub)
/* <-MISALIGNED-> */ /* 229*/                    throw new EASBizException(new NumericExceptionSubItem("100", "\u91D1\u989D\u8D85\u8FC7\u9884\u4F30\u5408\u540C\u53D8\u52A8\u91D1\u989D\uFF0C\u8BF7\u5148\u786E\u8BA4\u9884\u4F30\u5408\u540C\u53D8\u52A8\u5355\u7684\u91D1\u989D\uFF01"));
/* <-MISALIGNED-> */ /* 231*/                if(isSub)
                        {
/* <-MISALIGNED-> */ /* 232*/                    BigDecimal subAmount = FDCHelper.ZERO;
/* <-MISALIGNED-> */ /* 233*/                    for(int i = 0; i < info.getEntry().size(); i++)
                            {
/* <-MISALIGNED-> */ /* 234*/                        ContractEstimateChangeEntryInfo entry = info.getEntry().get(i);
/* <-MISALIGNED-> */ /* 235*/                        if(type.equals(entry.getType()))
///* <-MISALIGNED-> */ /* 236*/                            if(entry.getAmount().compareTo(FDCHelper.ZERO) < 0)
//                                    {
///* <-MISALIGNED-> */ /* 237*/                                if(FDCHelper.subtract(entry.getAmount(), amount).compareTo(FDCHelper.ZERO) > 0)
//                                        {
///* <-MISALIGNED-> */ /* 238*/                                    subAmount = entry.getAmount();
///* <-MISALIGNED-> */ /* 239*/                                    entry.setAmount(FDCHelper.ZERO);
//                                        } else
//                                        {
///* <-MISALIGNED-> */ /* 241*/                                    subAmount = amount;
///* <-MISALIGNED-> */ /* 242*/                                    entry.setAmount(FDCHelper.subtract(entry.getAmount(), amount));
//                                        }
//                                    } else
//                                    {
/* <-MISALIGNED-> */ /* 245*/                                subAmount = amount;
/* <-MISALIGNED-> */ /* 246*/                                entry.setAmount(FDCHelper.subtract(entry.getAmount(), amount));
//                                    }
                            }
/* <-MISALIGNED-> */ /* 250*/                    info.setEstimateAmount(FDCHelper.subtract(info.getEstimateAmount(), subAmount));
/* <-MISALIGNED-> */ /* 252*/                    SelectorItemCollection upSel = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 253*/                    upSel.add("estimateAmount");
/* <-MISALIGNED-> */ /* 254*/                    upSel.add("entry.amount");
/* <-MISALIGNED-> */ /* 255*/                    ContractEstimateChangeBillFactory.getLocalInstance(ctx).updatePartial(info, upSel);
                        }
/* <-MISALIGNED-> */ /* 257*/                return true;
                    }
/* <-MISALIGNED-> */ /* 258*/            if(type.equals(ContractEstimateChangeTypeEnum.SUPPLY) && !isSub)
/* <-MISALIGNED-> */ /* 259*/                throw new EASBizException(new NumericExceptionSubItem("100", "\u65E0\u9884\u4F30\u5408\u540C\u53D8\u52A8\u91D1\u989D\uFF0C\u8BF7\u5148\u786E\u8BA4\u9884\u4F30\u5408\u540C\u53D8\u52A8\u5355\u7684\u91D1\u989D\uFF01"));
                }
/* <-MISALIGNED-> */ /* 262*/        return false;
            }
            private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractEstimateChangeBillControllerBean");
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_contract_client.jar
	Total time: 183 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/