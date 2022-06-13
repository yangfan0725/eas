/*jadclipse*/package com.kingdee.eas.fdc.contract.app;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.*;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.cp.bc.IExpenseType;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fi.cas.*;
import com.kingdee.eas.framework.report.util.*;
import com.kingdee.eas.ma.budget.*;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.*;
import org.apache.log4j.Logger;
public class AccActOnLoadBgGatherFacadeControllerBean extends AbstractAccActOnLoadBgGatherFacadeControllerBean
{
            public AccActOnLoadBgGatherFacadeControllerBean()
            {
            }
            protected IRowSet _getPrintData(Context ctx, Set idSet)
                throws BOSException
            {













































/*  73*/        return null;
            }
            protected RptParams _init(Context ctx, RptParams params)
                throws BOSException, EASBizException
            {/*  77*/        RptParams pp = new RptParams();
/*  78*/        return pp;
            }
            private void initColoum(RptTableHeader header, RptTableColumn col, String name, int width, boolean isHide)
            {/*  81*/        col = new RptTableColumn(name);
/*  82*/        col.setWidth(width);
/*  83*/        col.setHided(isHide);
/*  84*/        header.addColumn(col);
            }
            protected RptParams _createTempTable(Context ctx, RptParams params)
                throws BOSException, EASBizException
            {/*  88*/        RptTableHeader header = new RptTableHeader();
/*  89*/        RptTableColumn col = null;
/*  90*/        initColoum(header, col, "id", 100, true);
/*  91*/        initColoum(header, col, "name", 150, false);
/*  92*/        initColoum(header, col, "bgAmount", 150, false);
/*  93*/        initColoum(header, col, "requestAmount", 150, false);
/*  94*/        initColoum(header, col, "payAmount", 150, false);
/*  95*/        initColoum(header, col, "onLoadUnPayAmount", 150, false);
/*  96*/        initColoum(header, col, "requestUnOnLoadAmount", 150, false);
/*  97*/        initColoum(header, col, "canRequestAmount", 150, false);
/*  98*/        initColoum(header, col, "useRate", 150, false);
/*  99*/        initColoum(header, col, "remark", 150, false);
/* 100*/        header.setLabels(new Object[][] {
/* 101*/            new Object[] {
/* 102*/                "id", "\u9884\u7B97\u9879\u76EE", "\u672C\u671F\u53EF\u7528\u9884\u7B97\u6570", "\u672C\u671F\u7D2F\u8BA1\u7533\u8BF7\u91D1\u989D", "\u672C\u671F\u5DF2\u4ED8\u91D1\u989D", "\u622A\u81F3\u672C\u671F\u5DF2\u5360\u7528\u672A\u4ED8\u6B3E\u91D1\u989D", "\u622A\u81F3\u672C\u671F\u5DF2\u7533\u8BF7\u672A\u5360\u7528\u91D1\u989D", "\u5F53\u524D\u53EF\u7533\u8BF7/\u5360\u7528\u9884\u7B97\u4F59\u989D", "\u9884\u7B97\u4F7F\u7528\u767E\u5206\u6BD4", "\u5907\u6CE8"
                    }, new Object[] {


/* 106*/                "id", "\u9884\u7B97\u9879\u76EE", "(A)", "(B=C+D+E)", "(C)", "(D)", "(E)", "(F=A-C-D)", "(G=C/A)", "\u5907\u6CE8"
                    }
                }, true);
/* 109*/        params.setObject("header", header);
/* 110*/        return params;
            }
            protected BgCtrlResultCollection getBgCtrlResult(Context ctx, RptParams params, String bgItemNumber)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 113*/        PaymentBillInfo pay = new PaymentBillInfo();
/* <-MISALIGNED-> */ /* 114*/        CompanyOrgUnitInfo company = (CompanyOrgUnitInfo)params.getObject("costedCompany");
/* <-MISALIGNED-> */ /* 115*/        CostCenterOrgUnitInfo costedDept = (CostCenterOrgUnitInfo)params.getObject("costedDept");
/* <-MISALIGNED-> */ /* 116*/        com.kingdee.eas.basedata.assistant.CurrencyInfo currency = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("select * from where name='\u4EBA\u6C11\u5E01'").get(0);
/* <-MISALIGNED-> */ /* 117*/        StringBuffer sb = new StringBuffer();
/* <-MISALIGNED-> */ /* 118*/        sb.append(" select expenseType.fid id from T_BC_ExpenseType expenseType where expenseType.fnumber in(");
/* <-MISALIGNED-> */ /* 119*/        sb.append(" select SUBSTRING(max(map.fbillItemCombinValue),charindex(':',max(map.fbillItemCombinValue))+1,length(max(map.fbillItemCombinValue))-charindex(':',max(map.fbillItemCombinValue))) from T_BG_BgControlItemMap map left join T_BG_BgControlScheme scheme on scheme.fid=map.FBgCtrlSchemeID");
/* <-MISALIGNED-> */ /* 120*/        sb.append(" where scheme.fboName='com.kingdee.eas.fi.cas.app.PaymentBill' and scheme.fisValid=1 and scheme.fisSysDefault=0");
/* <-MISALIGNED-> */ /* 121*/        if(costedDept != null)
/* <-MISALIGNED-> */ /* 122*/            sb.append((new StringBuilder(" and scheme.fcostCenterId='")).append(costedDept.getId().toString()).append("'").toString());/* 124*/        if(bgItemNumber != null)
/* 125*/            sb.append((new StringBuilder(" and map.fbgItemCombinKey in(")).append(bgItemNumber).append(")").toString());

/* 127*/        sb.append(" group by map.fbgItemCombinKey)");
                PaymentBillEntryInfo entry;/* 128*/        for(RptRowSet rowSet = executeQuery(sb.toString(), null, ctx); rowSet.next(); pay.getEntries().add(entry))
                {
/* 130*/            entry = new PaymentBillEntryInfo();
/* 131*/            entry.setAmount(FDCHelper.ZERO);
/* 132*/            entry.setLocalAmt(FDCHelper.ZERO);
/* 133*/            entry.setActualAmt(FDCHelper.ZERO);
/* 134*/            entry.setActualLocAmt(FDCHelper.ZERO);
/* 135*/            entry.setCurrency(currency);
/* 136*/            entry.setExpenseType(ExpenseTypeFactory.getLocalInstance(ctx).getExpenseTypeInfo(new ObjectUuidPK(rowSet.getString("id"))));
/* 137*/            entry.setCostCenter(costedDept);
                }

/* 140*/        pay.setCompany(company);
/* 141*/        pay.setCostCenter(costedDept);
/* 142*/        pay.setPayDate(FDCCommonServerHelper.getServerTimeStamp());
//pay.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
/* 143*/        pay.setCurrency(currency);

/* 145*/        IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getLocalInstance(ctx);
/* 146*/        BgCtrlResultCollection coll = iBgControlFacade.getBudget("com.kingdee.eas.fi.cas.app.PaymentBill", new BgCtrlParamCollection(), pay);

/* 148*/        return coll;
            }
            protected RptParams _query(Context ctx, RptParams params, int from, int len)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 151*/        String bgItemNumber = null;
/* <-MISALIGNED-> */ /* 152*/        Map bgItemMap = new HashMap();
/* <-MISALIGNED-> */ /* 153*/        Map onLoadUnPayAmount = new HashMap();
/* <-MISALIGNED-> */ /* 154*/        Map requestUnOnLoadAmount = new HashMap();
/* <-MISALIGNED-> */ /* 155*/        if(params.getObject("bgItem") != null)
                {
/* <-MISALIGNED-> */ /* 156*/            bgItemNumber = "";
/* <-MISALIGNED-> */ /* 157*/            Object bgItemObj[] = (Object[])params.getObject("bgItem");
/* <-MISALIGNED-> */ /* 158*/            for(int i = 0; i < bgItemObj.length; i++)
                    {
/* <-MISALIGNED-> */ /* 159*/                BgItemInfo bgItemInfo = (BgItemInfo)bgItemObj[i];
/* <-MISALIGNED-> */ /* 160*/                Set set = new HashSet();
/* <-MISALIGNED-> */ /* 161*/                set.add(bgItemInfo.getNumber());
/* <-MISALIGNED-> */ /* 162*/                bgItemMap.put(bgItemInfo.getNumber(), set);
/* <-MISALIGNED-> */ /* 163*/                if(i == 0)
/* <-MISALIGNED-> */ /* 164*/                    bgItemNumber = (new StringBuilder(String.valueOf(bgItemNumber))).append("'").append(bgItemInfo.getNumber()).append("'").toString();
/* <-MISALIGNED-> */ /* 166*/                else
/* <-MISALIGNED-> */ /* 166*/                    bgItemNumber = (new StringBuilder(String.valueOf(bgItemNumber))).append(",'").append(bgItemInfo.getNumber()).append("'").toString();
                    }
                }
/* <-MISALIGNED-> */ /* 170*/        params.setObject("bgCtrlResult", getBgCtrlResult(ctx, params, bgItemNumber));
/* <-MISALIGNED-> */ /* 172*/        CostCenterOrgUnitInfo costedDept = (CostCenterOrgUnitInfo)params.getObject("costedDept");
/* <-MISALIGNED-> */ /* 173*/        StringBuffer sb = new StringBuffer();
/* <-MISALIGNED-> */ /* 174*/        sb.append(" select distinct SUBSTRING(t2.fformula,10,charindex(',',t2.fformula)-11) bgItemNumber,SUBSTRING(t1.fformula,10,charindex(',',t1.fformula)-11) comBgItemNumber from T_BG_BgTemplateCtrlSetting t1 left join T_BG_BgTemplateCtrlSetting t2 on t1.fgroupno=t2.fgroupno");
/* <-MISALIGNED-> */ /* 175*/        sb.append(" where t1.fgroupno!='-1' and t2.fgroupno!='-1' and SUBSTRING(t2.fformula,10,charindex(',',t2.fformula)-11)!=SUBSTRING(t1.fformula,10,charindex(',',t1.fformula)-11)");
/* <-MISALIGNED-> */ /* 176*/        if(costedDept != null)
/* <-MISALIGNED-> */ /* 177*/            sb.append((new StringBuilder(" and t1.FOrgUnitId='")).append(costedDept.getId().toString()).append("'").toString());/* 179*/        if(bgItemNumber != null)
/* 180*/            sb.append((new StringBuilder(" and SUBSTRING(t2.fformula,10,charindex(',',t2.fformula)-11) in(")).append(bgItemNumber).append(")").toString());

/* 182*/        for(RptRowSet rowSet = executeQuery(sb.toString(), null, ctx); rowSet.next();)
                {
/* 184*/            if(bgItemMap.containsKey(rowSet.getString("bgItemNumber")))
/* 185*/                ((Set)bgItemMap.get(rowSet.getString("bgItemNumber"))).add(rowSet.getString("comBgItemNumber"));

/* 187*/            if(bgItemNumber.indexOf(rowSet.getString("comBgItemNumber")) < 0)
/* 188*/                bgItemNumber = (new StringBuilder(String.valueOf(bgItemNumber))).append(",'").append(rowSet.getString("comBgItemNumber")).append("'").toString();
                }

/* 191*/        for(RptRowSet amountRowSet = executeQuery(getAmountSql(ctx, params, bgItemNumber), null, ctx); amountRowSet.next();)

/* 193*/            if(amountRowSet.getString("type").equals("onLoadUnPayAmount"))
/* 194*/                onLoadUnPayAmount.put(amountRowSet.getString("bgItemNumber"), amountRowSet.getBigDecimal("amount"));

/* 196*/            else/* 196*/                requestUnOnLoadAmount.put(amountRowSet.getString("bgItemNumber"), amountRowSet.getBigDecimal("amount"));


/* 199*/        params.setObject("bgItemMap", bgItemMap);
/* 200*/        params.setObject("onLoadUnPayAmount", onLoadUnPayAmount);
/* 201*/        params.setObject("requestUnOnLoadAmount", requestUnOnLoadAmount);
/* 202*/        return params;
            }
            protected String getAmountSql(Context ctx, RptParams params, String bgItem)
            {/* 205*/        CostCenterOrgUnitInfo costedDept = (CostCenterOrgUnitInfo)params.getObject("costedDept");
/* 206*/        StringBuffer sb = new StringBuffer();
/* 207*/        sb.append(" select 'onLoadUnPayAmount' type,t.amount,t.bgItemNumber from(select sum(entry.frequestAmount-isnull(entry.factPayAmount,0)) amount,bgItem.fnumber bgItemNumber from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
/* 208*/        sb.append(" left join T_BG_BgItem bgItem on bgItem.fid=entry.fbgitemid");
/* 209*/        sb.append(" where bill.fhasClosed=0 and bill.famount is not null and bill.fisBgControl=1 and bill.fstate in('3AUDITTING','4AUDITTED')");
/* 210*/        if(costedDept != null)
/* 211*/            sb.append((new StringBuilder(" and bill.FCostedDeptId='")).append(costedDept.getId().toString()).append("'").toString());

/* 213*/        if(bgItem != null)
/* 214*/            sb.append((new StringBuilder(" and bgItem.fnumber in(")).append(bgItem).append(")").toString());

/* 216*/        sb.append(" group by bgItem.fnumber)t");
/* 217*/        sb.append(" union all select 'requestUnOnLoadAmount' type,t.amount,t.bgItemNumber from(select sum(entry.frequestAmount-isnull(entry.factPayAmount,0)) amount,bgItem.fnumber bgItemNumber from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
/* 218*/        sb.append(" left join T_BG_BgItem bgItem on bgItem.fid=entry.fbgitemid");
/* 219*/        sb.append(" where (bill.fhasClosed=1 or bill.fisBgControl=0 or bill.fstate not in('3AUDITTING','4AUDITTED'))");
/* 220*/        if(costedDept != null)
/* 221*/            sb.append((new StringBuilder(" and bill.FCostedDeptId='")).append(costedDept.getId().toString()).append("'").toString());

/* 223*/        if(bgItem != null)
/* 224*/            sb.append((new StringBuilder(" and bgItem.fnumber in(")).append(bgItem).append(")").toString());

/* 226*/        sb.append(" group by bgItem.fnumber)t");
/* 227*/        return sb.toString();
            }
            private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.app.AccActOnLoadBgGatherFacadeControllerBean");
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_contract_server.jar
	Total time: 86 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/