/*jadclipse*/package com.kingdee.eas.fdc.contract.app;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.*;
import com.kingdee.eas.fdc.contract.*;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import org.apache.log4j.Logger;
public class ContractFacadeControllerBean extends AbstractContractFacadeControllerBean
{
            public ContractFacadeControllerBean()
            {
            }
            protected Map _getContractNumberAndName(Context ctx, String id, boolean isWithOut)
                throws BOSException, EASBizException
            {




















/*  46*/        SelectorItemCollection mainsic = new SelectorItemCollection();
/*  47*/        mainsic.add(new SelectorItemInfo("number"));
/*  48*/        mainsic.add(new SelectorItemInfo("name"));

/*  50*/        FDCBillInfo info = null;
/*  51*/        if(isWithOut)
/*  52*/            info = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(id)));

/*  54*/        else/*  54*/            info = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(id)));


/*  57*/        Map map = new HashMap();
/*  58*/        map.put("contractNumber", info.getNumber());
/*  59*/        map.put("contractName", info.getName());
/*  60*/        map.put("info", info);

/*  62*/        return map;
            }
            protected Map _getContractNumberAndNameMap(Context ctx, Map idList)
                throws BOSException, EASBizException
            {
/*  67*/        BOSObjectType withoutTextContract = (new ContractWithoutTextInfo()).getBOSType();
/*  68*/        Set contractIdList = new HashSet();
/*  69*/        Set contractNotextIdList = new HashSet();

/*  71*/        Set set = idList.keySet();
/*  72*/        for(Iterator it = set.iterator(); it.hasNext();)
                {

/*  75*/            String id = (String)it.next();
/*  76*/            if(BOSUuid.read(id).getType().equals(withoutTextContract))
/*  77*/                contractNotextIdList.add(id);

/*  79*/            else/*  79*/                contractIdList.add(id);
                }


/*  83*/        Map contract = getContractNumberAndName(ctx, contractIdList, false);
/*  84*/        Map contractNoText = getContractNumberAndName(ctx, contractNotextIdList, true);

/*  86*/        if(contract != null)
                {/*  87*/            if(contractNoText != null)
/*  88*/                contract.putAll(contractNoText);
                } else
                {
/*  91*/            contract = contractNoText;
                }

/*  94*/        return contract;
            }
            private Map getContractNumberAndName(Context ctx, Set idList, boolean isWithOut)
                throws BOSException, EASBizException
            {

/* 100*/        if(idList.size() == 0)
/* 101*/            return null;















/* 117*/        String sql = null;
/* 118*/        if(isWithOut)
/* 119*/            sql = "select fid,fnumber ,fname from t_con_contractWithoutText where fid in (";



/* 123*/        else/* 123*/            sql = "select fid,fnumber ,fname from t_con_contractBill where fid in (";



/* 127*/        Iterator it = idList.iterator();
/* 128*/        for(int j = 0; it.hasNext(); j++)
                {
/* 130*/            String id = (String)it.next();
/* 131*/            if(j != 0)
/* 132*/                sql = (new StringBuilder()).append(sql).append(",").toString();

/* 134*/            sql = (new StringBuilder()).append(sql).append("'").append(id).append("'").toString();
                }

/* 137*/        sql = (new StringBuilder()).append(sql).append(")").toString();


/* 140*/        Map map = new HashMap();
/* 141*/        IRowSet rs = DbUtil.executeQuery(ctx, sql);
/* 142*/        if(rs != null)

/* 144*/            try
                    {
                        String id;
                        FDCBillInfo bill;
/* <-MISALIGNED-> */ /* 144*/                for(; rs.next(); map.put(id, bill))
                        {
/* <-MISALIGNED-> */ /* 145*/                    id = rs.getString("fid");
/* <-MISALIGNED-> */ /* 146*/                    String number = rs.getString("fnumber");
/* <-MISALIGNED-> */ /* 147*/                    String name = rs.getString("fname");
/* <-MISALIGNED-> */ /* 149*/                    bill = new FDCBillInfo();
/* <-MISALIGNED-> */ /* 150*/                    bill.setNumber(number);
/* <-MISALIGNED-> */ /* 151*/                    bill.setName(name);
                        }
                    }
/* <-MISALIGNED-> */ /* 155*/            catch(SQLException e)
                    {
/* <-MISALIGNED-> */ /* 156*/                e.printStackTrace();
/* <-MISALIGNED-> */ /* 157*/                throw new BOSException(e);
                    }








/* 171*/        return map;
            }
            protected BigDecimal _getProjectAmount(Context ctx, String projectId, String id)
                throws BOSException, EASBizException
            {

/* 177*/        BigDecimal amount = FDCHelper.ZERO;

/* 179*/        String selectSql = (new StringBuilder()).append("select fcurprojectId , sum(famount)  amount from T_Con_ContractBill  where \t(fstate = '4AUDITTED' or fstate = '2SUBMITTED' ) and FIsAmtWithoutCost=0 and FIsCostSplit=1 and fcurprojectId=?").append(id == null ? "" : (new StringBuilder()).append(" and  fid<>'").append(id).append("'").toString()).append(" group by fcurprojectId\t").toString();





/* 185*/        IRowSet rs = DbUtil.executeQuery(ctx, selectSql, new Object[] {/* 185*/            projectId
                });
/* 187*/        try
                {
/* <-MISALIGNED-> */ /* 187*/            if(rs != null && rs.next())
/* <-MISALIGNED-> */ /* 188*/                amount = rs.getBigDecimal("amount");
                }
/* <-MISALIGNED-> */ /* 190*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 191*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 192*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 195*/        selectSql = (new StringBuilder()).append("select fcurprojectId , sum(famount)  amount from t_con_contractwithouttext  where \t(fstate = '4AUDITTED' or fstate = '2SUBMITTED' ) and FIsCostSplit=1  and fcurprojectId=? ").append(id == null ? "" : (new StringBuilder()).append(" and  fid<>'").append(id).append("'").toString()).append("group by fcurprojectId\t").toString();



/* 201*/        rs = DbUtil.executeQuery(ctx, selectSql, new Object[] {/* 201*/            projectId
                });
/* 203*/        try
                {
/* <-MISALIGNED-> */ /* 203*/            if(rs != null && rs.next())
/* <-MISALIGNED-> */ /* 204*/                amount = amount.add(rs.getBigDecimal("amount"));
                }
/* <-MISALIGNED-> */ /* 206*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 207*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 208*/            throw new BOSException(e);
                }/* 212*/        return amount;
            }
            protected Map _getTotalSettlePrice(Context ctx, Map param)
                throws BOSException, EASBizException
            {
/* 217*/        Map result = null;
/* 218*/        String contractId = (String)param.get("ContractBillId");
/* 219*/        String id = (String)param.get("ID");
/* 220*/        String sql = "select sum(FCurOriginalAmount) FOriginalAmount, sum(FCurSettlePrice) FSettlePrice  from t_con_contractSettlementbill where FContractBillId=? ";

/* 222*/        Object sqlParam[] = {/* 222*/            contractId
                };/* 223*/        if(id != null)
                {/* 224*/            sql = (new StringBuilder()).append(sql).append(" and fid<>?").toString();
/* 225*/            sqlParam = (new Object[] {/* 225*/                contractId, id
                    });
                }
/* 228*/        sql = (new StringBuilder()).append(sql).append(" and fstate = '4AUDITTED' Group by FContractBillId").toString();

/* 230*/        IRowSet rs = DbUtil.executeQuery(ctx, sql, sqlParam);
/* 231*/        BigDecimal amount = null;

/* 233*/        try
                {
/* <-MISALIGNED-> */ /* 233*/            if(rs != null && rs.next())
                    {
/* <-MISALIGNED-> */ /* 234*/                result = new HashMap();
/* <-MISALIGNED-> */ /* 235*/                amount = rs.getBigDecimal("FOriginalAmount");
/* <-MISALIGNED-> */ /* 236*/                result.put("OriginalAmount", amount);
/* <-MISALIGNED-> */ /* 237*/                amount = rs.getBigDecimal("FSettlePrice");
/* <-MISALIGNED-> */ /* 238*/                result.put("SettlePrice", amount);
                    }
                }
/* <-MISALIGNED-> */ /* 240*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 241*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 242*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 246*/        return result;
            }
            protected Map _getExRatePre(Context ctx, String exTableId, String objCurId)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 251*/        EntityViewInfo view = new EntityViewInfo();
/* <-MISALIGNED-> */ /* 252*/        FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 253*/        view.setFilter(filter);
/* <-MISALIGNED-> */ /* 254*/        filter.getFilterItems().add(new FilterItemInfo("exchangeTable.id", exTableId));
/* <-MISALIGNED-> */ /* 255*/        filter.getFilterItems().add(new FilterItemInfo("targetCurrency.id", objCurId));
/* <-MISALIGNED-> */ /* 256*/        view.getSelector().add("sourceCurrency.id");
/* <-MISALIGNED-> */ /* 257*/        view.getSelector().add("precision");
/* <-MISALIGNED-> */ /* 259*/        ExchangeAuxCollection exCol = ExchangeAuxFactory.getLocalInstance(ctx).getExchangeAuxCollection(view);
/* <-MISALIGNED-> */ /* 260*/        Map exMap = new HashMap();
/* <-MISALIGNED-> */ /* 261*/        if(exCol != null && exCol.size() > 0)
                {
/* <-MISALIGNED-> */ /* 262*/            for(int j = 0; j < exCol.size(); j++)
                    {
/* <-MISALIGNED-> */ /* 263*/                ExchangeAuxInfo ex = exCol.get(j);
/* <-MISALIGNED-> */ /* 265*/                exMap.put(ex.getSourceCurrency().getId().toString(), new Integer(ex.getPrecision()));
                    }
                }
/* <-MISALIGNED-> */ /* 269*/        return exMap;
            }
            protected Map _getLastPrice(Context ctx, Map lastPriceMap)
                throws BOSException, EASBizException
            {

/* 277*/        Set set = lastPriceMap.keySet();
/* 278*/        Iterator it = set.iterator();
/* 279*/        Map amountMap = new HashMap();

/* 281*/        do
                {
/* <-MISALIGNED-> */ /* 281*/            if(!it.hasNext())
/* <-MISALIGNED-> */ /* 282*/                break;
/* <-MISALIGNED-> */ /* 282*/            String id = (String)it.next();
/* <-MISALIGNED-> */ /* 284*/            try
                    {
/* <-MISALIGNED-> */ /* 284*/                BigDecimal amount = FDCUtils.getContractLastPrice(ctx, id, true);
/* <-MISALIGNED-> */ /* 285*/                amountMap.put(id, amount);
                    }
/* <-MISALIGNED-> */ /* 286*/            catch(SQLException e)
                    {
/* <-MISALIGNED-> */ /* 287*/                e.printStackTrace();
/* <-MISALIGNED-> */ /* 288*/                throw new BOSException(e);
                    }
                } while(true);
/* <-MISALIGNED-> */ /* 293*/        return amountMap;
            }
            protected Map _getChangeAmt(Context ctx, String contractIds[])
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 300*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
                Map map;
/* <-MISALIGNED-> */ /* 302*/        Map changeAmtMap = new HashMap();

/* 306*/        if(contractIds != null)
                {
/* 308*/            builder.clear();
/* 309*/            builder.appendSql("select FContractBillID,sum(fchangeAmount) as changeAmount from ( ");
/* 310*/            builder.appendSql("select FContractBillID,FBalanceAmount as fchangeAmount from T_CON_ContractChangeBill ");
/* 311*/            builder.appendSql("where FHasSettled=1 and ");
/* 312*/            builder.appendParam("FContractBillID", contractIds, "varchar(44)");
/* 313*/            builder.appendSql(" and (");
/* 314*/            builder.appendParam("FState", "4AUDITTED");
/* 315*/            builder.appendSql(" or ");
/* 316*/            builder.appendParam("FState", "8VISA");
/* 317*/            builder.appendSql(" or ");
/* 318*/            builder.appendParam("FState", "7ANNOUNCE");
/* 319*/            builder.appendSql(" ) union all ");
/* 320*/            builder.appendSql("select FContractBillID,FAmount as fchangeAmount from T_CON_ContractChangeBill ");
/* 321*/            builder.appendSql("where FHasSettled=0 and ");
/* 322*/            builder.appendParam("FContractBillID", contractIds, "varchar(44)");
/* 323*/            builder.appendSql(" and (");
/* 324*/            builder.appendParam("FState", "4AUDITTED");
/* 325*/            builder.appendSql(" or ");
/* 326*/            builder.appendParam("FState", "8VISA");
/* 327*/            builder.appendSql(" or ");
/* 328*/            builder.appendParam("FState", "7ANNOUNCE");
/* 329*/            builder.appendSql(" )) change group by FContractBillID");

/* 331*/            IRowSet rs = builder.executeQuery();

/* 333*/            try
                    {
/* <-MISALIGNED-> */ /* 333*/                while(rs.next()) 
                        {
/* <-MISALIGNED-> */ /* 334*/                    String contractId = rs.getString("FContractBillID");
/* <-MISALIGNED-> */ /* 335*/                    BigDecimal changeAmount = rs.getBigDecimal("changeAmount");
/* <-MISALIGNED-> */ /* 336*/                    changeAmtMap.put(contractId, changeAmount);
                        }
                    }
/* <-MISALIGNED-> */ /* 338*/            catch(SQLException e)
                    {
/* <-MISALIGNED-> */ /* 339*/                e.printStackTrace();
/* <-MISALIGNED-> */ /* 340*/                throw new BOSException(e);
                    }
                }
/* <-MISALIGNED-> */ /* 343*/        map = changeAmtMap;
/* <-MISALIGNED-> */ /* 345*/        builder.releasTempTables();
/* <-MISALIGNED-> */ /* 346*/        return map;
            }
            protected Map _getLastAmt(Context ctx, String contractIds[])
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 354*/        if(contractIds == null || contractIds.length == 0)
/* <-MISALIGNED-> */ /* 355*/            return new HashMap();
/* <-MISALIGNED-> */ /* 357*/        BigDecimal bdZero = FDCNumberHelper.ZERO;
/* <-MISALIGNED-> */ /* 358*/        String noSettleContractIdList = null;
/* <-MISALIGNED-> */ /* 359*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* <-MISALIGNED-> */ /* 360*/        builder.appendSql("select FID,FHasSettled,FSettleAmt,FAmount from T_CON_ContractBill where ");
/* <-MISALIGNED-> */ /* 361*/        builder.appendParam("fid", contractIds);
/* <-MISALIGNED-> */ /* 363*/        IRowSet rs = builder.executeQuery();
/* <-MISALIGNED-> */ /* 364*/        if(rs == null || rs.size() == 0)
/* <-MISALIGNED-> */ /* 365*/            return new HashMap();
/* <-MISALIGNED-> */ /* 367*/        Map lastAmtMap = new HashMap(rs.size());/* 369*/        try
                {
                    String contractId;
                    BigDecimal contractAmount;
/* <-MISALIGNED-> */ /* 369*/            for(; rs.next(); lastAmtMap.put(contractId, contractAmount != null ? ((Object) (contractAmount)) : ((Object) (bdZero))))
                    {
/* <-MISALIGNED-> */ /* 370*/                contractId = rs.getString("FID");
/* <-MISALIGNED-> */ /* 371*/                contractAmount = FDCNumberHelper.ZERO;
/* <-MISALIGNED-> */ /* 372*/                if(rs.getBoolean("FHasSettled"))
                        {
/* <-MISALIGNED-> */ /* 373*/                    contractAmount = rs.getBigDecimal("FSettleAmt");
                        } else
                        {
/* <-MISALIGNED-> */ /* 375*/                    if(noSettleContractIdList == null)
/* <-MISALIGNED-> */ /* 376*/                        noSettleContractIdList = contractId;
/* <-MISALIGNED-> */ /* 378*/                    else
/* <-MISALIGNED-> */ /* 378*/                        noSettleContractIdList = (new StringBuilder()).append(noSettleContractIdList).append(",").append(contractId).toString();
/* <-MISALIGNED-> */ /* 380*/                    contractAmount = rs.getBigDecimal("FAmount");
                        }
                    }
                }
/* <-MISALIGNED-> */ /* 384*/        catch(SQLException e1)
                {
/* <-MISALIGNED-> */ /* 386*/            e1.printStackTrace();
/* <-MISALIGNED-> */ /* 387*/            throw new BOSException(e1);
                }
/* <-MISALIGNED-> */ /* 393*/        if(noSettleContractIdList != null)
                {
/* <-MISALIGNED-> */ /* 394*/            String noSettleContractIdArray[] = FDCHelper.stringToStrArray(noSettleContractIdList);
/* <-MISALIGNED-> */ /* 396*/            builder.clear();
/* <-MISALIGNED-> */ /* 397*/            builder.appendSql("select FContractBillID,sum(fchangeAmount) as changeAmount from ( ");
/* <-MISALIGNED-> */ /* 398*/            builder.appendSql("select FContractBillID,FBalanceAmount as fchangeAmount from T_CON_ContractChangeBill ");
/* <-MISALIGNED-> */ /* 399*/            builder.appendSql("where FHasSettled=1 and ");
/* <-MISALIGNED-> */ /* 400*/            builder.appendParam("FContractBillID", noSettleContractIdArray);
/* <-MISALIGNED-> */ /* 401*/            builder.appendSql(" and (");
/* <-MISALIGNED-> */ /* 402*/            builder.appendParam("FState", "4AUDITTED");
/* <-MISALIGNED-> */ /* 403*/            builder.appendSql(" or ");
/* <-MISALIGNED-> */ /* 404*/            builder.appendParam("FState", "8VISA");
/* <-MISALIGNED-> */ /* 405*/            builder.appendSql(" or ");
/* <-MISALIGNED-> */ /* 406*/            builder.appendParam("FState", "7ANNOUNCE");
/* <-MISALIGNED-> */ /* 407*/            builder.appendSql(" ) union all ");
/* <-MISALIGNED-> */ /* 408*/            builder.appendSql("select FContractBillID,FAmount as fchangeAmount from T_CON_ContractChangeBill ");
/* <-MISALIGNED-> */ /* 409*/            builder.appendSql("where FHasSettled=0 and ");
/* <-MISALIGNED-> */ /* 410*/            builder.appendParam("FContractBillID", noSettleContractIdArray);
/* <-MISALIGNED-> */ /* 411*/            builder.appendSql(" and (");
/* <-MISALIGNED-> */ /* 412*/            builder.appendParam("FState", "4AUDITTED");
/* <-MISALIGNED-> */ /* 413*/            builder.appendSql(" or ");
/* <-MISALIGNED-> */ /* 414*/            builder.appendParam("FState", "8VISA");
/* <-MISALIGNED-> */ /* 415*/            builder.appendSql(" or ");
/* <-MISALIGNED-> */ /* 416*/            builder.appendParam("FState", "7ANNOUNCE");
/* <-MISALIGNED-> */ /* 417*/            builder.appendSql(" )) change group by FContractBillID");/* 419*/            rs = builder.executeQuery();

/* 421*/            try
                    {
/* <-MISALIGNED-> */ /* 421*/                do
                        {
/* <-MISALIGNED-> */ /* 421*/                    if(!rs.next())
/* <-MISALIGNED-> */ /* 422*/                        break;
/* <-MISALIGNED-> */ /* 422*/                    String contractId = rs.getString("FContractBillID");
/* <-MISALIGNED-> */ /* 423*/                    BigDecimal changeAmount = rs.getBigDecimal("changeAmount");
/* <-MISALIGNED-> */ /* 424*/                    if(lastAmtMap.containsKey(contractId) && changeAmount != null)
/* <-MISALIGNED-> */ /* 425*/                        lastAmtMap.put(contractId, ((BigDecimal)lastAmtMap.get(contractId)).add(changeAmount));
                        } while(true);
                    }
/* <-MISALIGNED-> */ /* 428*/            catch(SQLException e)
                    {
/* <-MISALIGNED-> */ /* 429*/                e.printStackTrace();
/* <-MISALIGNED-> */ /* 430*/                throw new BOSException(e);
                    }
                }
























































/* 495*/        return lastAmtMap;
            }
            protected Map _getTotalSettlePrice(Context ctx, Set contractIds)
                throws BOSException, EASBizException
            {
                Map result;
                FDCSQLBuilder fdcBuilder;
/* 502*/        result = new HashMap();
/* 503*/        fdcBuilder = new FDCSQLBuilder(ctx);

/* 505*/        fdcBuilder.appendSql("select FContractBillId,sum(FCurOriginalAmount) FOriginalAmount, sum(FCurSettlePrice) FSettlePrice  from t_con_contractSettlementbill where");

/* 507*/        fdcBuilder.appendParam("FContractBillId", contractIds.toArray(), "varchar(44)");
/* 508*/        fdcBuilder.appendSql("and fstate='4AUDITTED' Group by FContractBillId");
/* 509*/        logger.debug(fdcBuilder.getTestSql());
/* 510*/        IRowSet rs = fdcBuilder.executeQuery();
/* 511*/        fdcBuilder.releasTempTables();
/* 512*/        BigDecimal amount = null;

/* 514*/        try
                {
/* <-MISALIGNED-> */ /* 514*/            if(rs != null)
                    {
                        String key;
/* <-MISALIGNED-> */ /* 515*/                for(; rs.next(); result.put(key.concat("_SettlePrice"), amount))
                        {
/* <-MISALIGNED-> */ /* 516*/                    key = rs.getString("FContractBillId");
/* <-MISALIGNED-> */ /* 517*/                    amount = rs.getBigDecimal("FOriginalAmount");
/* <-MISALIGNED-> */ /* 518*/                    result.put(key, amount);
/* <-MISALIGNED-> */ /* 519*/                    amount = rs.getBigDecimal("FSettlePrice");
                        }
                    }
                }
/* <-MISALIGNED-> */ /* 524*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 525*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 526*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 529*/        fdcBuilder.releasTempTables();
/* <-MISALIGNED-> */ /* 533*/        return result;
            }
            @Override
			protected void _updateAmount(Context ctx) throws BOSException,EASBizException {
            	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
            	builder.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
            	
            	StringBuffer sql = new StringBuffer();
        		sql.append("update T_CON_PAYREQUESTBILL set famount=FORIGINALAMOUNT where FORIGINALAMOUNT !=famount and FCURRENCYID ='dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC'");
        		builder.addBatch(sql.toString());
        		
        		sql = new StringBuffer();
        		sql.append("update T_CON_CONTRACTWITHOUTTEXT set famount=FORIGINALAMOUNT where FORIGINALAMOUNT !=famount and FCURRENCYID ='dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC'");
        		builder.addBatch(sql.toString());
        		
        		sql = new StringBuffer();
        		sql.append("update t_con_contractbill set famount=FORIGINALAMOUNT where FORIGINALAMOUNT !=famount and FCURRENCYID ='dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC'");
        		builder.addBatch(sql.toString());
        		 
        		sql = new StringBuffer();
        		sql.append("update t_con_marketproject a set famount=(select amount from(select fheadid,sum(famount) amount from T_CON_MarketProjectCostEntry group by fheadid )t where t.fheadid=a.fid)where fid in(");
        		sql.append(" select a.fid from t_con_marketproject a left join (select fheadid,sum(famount) amount from T_CON_MarketProjectCostEntry group by fheadid )t on t.fheadid=a.fid where a.famount!=t.amount)");
        		builder.addBatch(sql.toString());
        				
        		builder.executeBatch();
			}

			private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractFacadeControllerBean");
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\server\eas\fdc_contract-server.jar
	Total time: 155 ms
	Jad reported messages/errors:
Overlapped try statements detected. Not all exception handlers will be resolved in the method _getChangeAmt
Couldn't fully decompile method _getChangeAmt
Couldn't resolve all exception handlers in method _getChangeAmt
Overlapped try statements detected. Not all exception handlers will be resolved in the method _getTotalSettlePrice
Couldn't fully decompile method _getTotalSettlePrice
Couldn't resolve all exception handlers in method _getTotalSettlePrice
	Exit status: 0
	Caught exceptions:
*/