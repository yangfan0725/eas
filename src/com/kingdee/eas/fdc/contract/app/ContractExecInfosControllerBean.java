/*jadclipse*/package com.kingdee.eas.fdc.contract.app;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.*;
import com.kingdee.eas.fdc.contract.*;
import com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fi.cas.*;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import org.apache.log4j.Logger;
public class ContractExecInfosControllerBean extends AbstractContractExecInfosControllerBean
{
            public ContractExecInfosControllerBean()
            {
            }
            protected void _updateContract(Context ctx, String type, String contractId)
                throws BOSException, EASBizException
            {




































/*  65*/        if(contractId.equals(""))
/*  66*/            return;

/*  68*/        boolean isContract = isContract(contractId);






/*  75*/        if("audit".equals(type))
                {/*  76*/            ContractExecInfosInfo info = new ContractExecInfosInfo();
/*  77*/            if(isContract)
                    {/*  78*/                ContractBillInfo conBill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
/*  79*/                EntityViewInfo view = new EntityViewInfo();
/*  80*/                FilterInfo filter = new FilterInfo();
/*  81*/                filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
/*  82*/                view.setFilter(filter);
/*  83*/                ContractExecInfosCollection col = ContractExecInfosFactory.getLocalInstance(ctx).getContractExecInfosCollection(view);
/*  84*/                if(col.size() > 0)
/*  85*/                    info = col.get(0);

/*  87*/                info.setContractBill(conBill);
/*  88*/                info.setCurProject(conBill.getCurProject());
/*  89*/                info.setPeriod(conBill.getPeriod());
/*  90*/                info.setCU(conBill.getCU());
/*  91*/                info.setOrgUnit(conBill.getOrgUnit());
/*  92*/                info.setHasSettled(conBill.isHasSettled());

/*  94*/                info.setCostAmt(conBill.getAmount());

/*  96*/                info.setCostAmtOri(conBill.getOriginalAmount());
/*  97*/                info.setIsNoText(false);
/*  98*/                if(col.size() > 0)
                        {/*  99*/                    SelectorItemCollection sel = new SelectorItemCollection();
/* 100*/                    sel.add("contractBill");
/* 101*/                    sel.add("curProject");
/* 102*/                    sel.add("period");
/* 103*/                    sel.add("CU");
/* 104*/                    sel.add("orgUnit");
/* 105*/                    sel.add("hasSettled");
/* 106*/                    sel.add("costAmt");
/* 107*/                    sel.add("costAmtOri");
/* 108*/                    sel.add("isNoText");
/* 109*/                    _updatePartial(ctx, info, sel);
                        } else
                        {/* 111*/                    _addnew(ctx, info);
                        }



/* 116*/                _updateChange(ctx, "audit", contractId);
                    } else
                    {/* 118*/                ContractWithoutTextInfo conWithoutText = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
/* 119*/                EntityViewInfo view = new EntityViewInfo();
/* 120*/                FilterInfo filter = new FilterInfo();
/* 121*/                filter.getFilterItems().add(new FilterItemInfo("conWithoutText.id", contractId));
/* 122*/                view.setFilter(filter);
/* 123*/                ContractExecInfosCollection col = ContractExecInfosFactory.getLocalInstance(ctx).getContractExecInfosCollection(view);
/* 124*/                if(col.size() > 0)
/* 125*/                    info = col.get(0);

/* 127*/                info.setConWithoutText(conWithoutText);
/* 128*/                info.setCurProject(conWithoutText.getCurProject());

/* 130*/                info.setCostAmt(conWithoutText.getAmount());

/* 132*/                info.setCostAmtOri(conWithoutText.getOriginalAmount());
/* 133*/                info.setCompletedAmt(conWithoutText.getAmount());
/* 134*/                info.setInvoicedAmt(conWithoutText.getInvoiceAmt());
/* 135*/                info.setPeriod(conWithoutText.getPeriod());
/* 136*/                info.setCU(conWithoutText.getCU());
/* 137*/                info.setOrgUnit(conWithoutText.getOrgUnit());
/* 138*/                info.setIsNoText(true);
/* 139*/                if(col.size() > 0)
                        {/* 140*/                    SelectorItemCollection sel = new SelectorItemCollection();
/* 141*/                    sel.add("conWithoutText");
/* 142*/                    sel.add("curProject");
/* 143*/                    sel.add("period");
/* 144*/                    sel.add("CU");
/* 145*/                    sel.add("orgUnit");
/* 146*/                    sel.add("costAmt");
/* 147*/                    sel.add("costAmtOri");
/* 148*/                    sel.add("isNoText");
/* 149*/                    sel.add("completedAmt");
/* 150*/                    sel.add("invoicedAmt");
/* 151*/                    _updatePartial(ctx, info, sel);
                        } else
                        {/* 153*/                    _addnew(ctx, info);
                        }
                    }
                } else
/* 157*/        if("unAudit".equals(type))
                {




/* 163*/            FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 164*/            if(isContract)
/* 165*/                builder.appendSql("delete from T_CON_ContractExecInfos where FContractBillId=?");

/* 167*/            else/* 167*/                builder.appendSql("delete from T_CON_ContractExecInfos where FConWithoutTextID=?");
/* 168*/            builder.addParam(contractId);
/* 169*/            builder.execute();
                }
            }
            protected void _updateChange(Context ctx, String type, String contractId)
                throws BOSException, EASBizException
            {





/* 180*/        if(FDCHelper.isEmpty(contractId))
/* 181*/            return;






/* 188*/        if("audit".equals(type) || "unAudit".equals(type))
                {

/* 191*/            IContractExecInfos ice = ContractExecInfosFactory.getLocalInstance(ctx);
/* 192*/            EntityViewInfo view = new EntityViewInfo();
/* 193*/            view.getSelector().add("id");
/* 194*/            view.getSelector().add("hasSettled");
/* 195*/            view.getSelector().add("costAmt");
/* 196*/            view.getSelector().add("costAmtOri");
/* 197*/            view.getSelector().add("changeAmt");
/* 198*/            view.getSelector().add("changeAmtOri");
/* 199*/            FilterInfo filter = new FilterInfo();
/* 200*/            filter.getFilterItems().add(new FilterItemInfo("contractBill", contractId));
/* 201*/            view.setFilter(filter);

/* 203*/            ContractExecInfosCollection coll = ice.getContractExecInfosCollection(view);
/* 204*/            ContractExecInfosInfo info = null;
/* 205*/            if(coll != null && coll.size() == 1)
/* 206*/                info = coll.get(0);




/* 211*/            BigDecimal temp = FDCHelper.ZERO;
/* 212*/            BigDecimal tempOri = FDCHelper.ZERO;
/* 213*/            if(info == null)
/* 214*/                return;

/* 216*/            ContractBillInfo conBill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));


/* 219*/            temp = conBill.getAmount();
/* 220*/            tempOri = conBill.getOriginalAmount();

/* 222*/            FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 223*/            builder.appendSql("select sum(case fhasSettled when  0 then  famount else fbalanceamount end ) as changeAmt , sum(case fhasSettled when 0 then foriginalAmount else foriBalanceAmount end) as changeAmtOri from T_Con_ContractChangeBill where ");




/* 228*/            builder.appendParam("FContractBillID", contractId);
/* 229*/            builder.appendSql(" and (");
/* 230*/            builder.appendParam("FState", "4AUDITTED");
/* 231*/            builder.appendSql(" or ");
/* 232*/            builder.appendParam("FState", "8VISA");
/* 233*/            builder.appendSql(" or ");
/* 234*/            builder.appendParam("FState", "7ANNOUNCE");
/* 235*/            builder.appendSql(" )");
/* 236*/            IRowSet rs = builder.executeQuery();
/* 237*/            BigDecimal changeAmt = FDCHelper.ZERO;
/* 238*/            BigDecimal changeAmtOri = FDCHelper.ZERO;


/* 241*/            try
                    {
/* <-MISALIGNED-> */ /* 241*/                while(rs.next()) 
                        {
/* <-MISALIGNED-> */ /* 241*/                    changeAmt = rs.getBigDecimal("changeAmt");
/* <-MISALIGNED-> */ /* 242*/                    changeAmtOri = rs.getBigDecimal("changeAmtOri");
                        }
                    }
/* <-MISALIGNED-> */ /* 244*/            catch(SQLException e)
                    {
/* <-MISALIGNED-> */ /* 245*/                e.printStackTrace();
/* <-MISALIGNED-> */ /* 246*/                logger.error(e.getMessage());
                    }
/* <-MISALIGNED-> */ /* 248*/            info.setCostAmt(FDCHelper.add(temp, changeAmt));
/* <-MISALIGNED-> */ /* 249*/            info.setCostAmtOri(FDCHelper.add(tempOri, changeAmtOri));
/* <-MISALIGNED-> */ /* 250*/            info.setChangeAmt(changeAmt);
/* <-MISALIGNED-> */ /* 251*/            info.setChangeAmtOri(changeAmtOri);
/* <-MISALIGNED-> */ /* 252*/            SelectorItemCollection selector = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 253*/            selector.add("changeAmt");
/* <-MISALIGNED-> */ /* 254*/            selector.add("changeAmtOri");
/* <-MISALIGNED-> */ /* 255*/            selector.add("costAmt");
/* <-MISALIGNED-> */ /* 256*/            selector.add("costAmtOri");
/* <-MISALIGNED-> */ /* 257*/            if(!info.isHasSettled())
                    {
/* <-MISALIGNED-> */ /* 258*/                BigDecimal completePrjAmt = getProcessAmt(ctx, contractId);
/* <-MISALIGNED-> */ /* 259*/                info.setNotCompletedAmt(FDCHelper.subtract(FDCHelper.add(temp, changeAmt), completePrjAmt));
/* <-MISALIGNED-> */ /* 260*/                selector.add("notCompletedAmt");
                    }
/* <-MISALIGNED-> */ /* 262*/            _updatePartial(ctx, info, selector);
/* <-MISALIGNED-> */ /* 263*/            if(info.isHasSettled())
/* <-MISALIGNED-> */ /* 265*/                _updateSettle(ctx, "audit", contractId);
                }
/* <-MISALIGNED-> */ /* 270*/        _updatePayment(ctx, "pay", contractId);
            }
            protected void _updateSettle(Context ctx, String type, String contractId)
                throws BOSException, EASBizException
            {

/* 279*/        if(contractId.equals(""))
/* 280*/            return;










/* 291*/        ContractBillInfo conBill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));

/* 293*/        BigDecimal temp = FDCHelper.ZERO;
/* 294*/        BigDecimal tempOri = FDCHelper.ZERO;
/* 295*/        SelectorItemCollection selector = new SelectorItemCollection();
/* 296*/        IContractExecInfos ice = ContractExecInfosFactory.getLocalInstance(ctx);
/* 297*/        EntityViewInfo view = new EntityViewInfo();
/* 298*/        view.getSelector().add("id");
/* 299*/        view.getSelector().add("costAmt");
/* 300*/        view.getSelector().add("changeAmt");
/* 301*/        view.getSelector().add("costAmtOri");
/* 302*/        view.getSelector().add("changeAmtOri");
/* 303*/        view.getSelector().add("completedAmt");
/* 304*/        view.getSelector().add("notCompletedAmt");
/* 305*/        FilterInfo filter = new FilterInfo();
/* 306*/        filter.getFilterItems().add(new FilterItemInfo("contractBill", contractId));
/* 307*/        view.setFilter(filter);

/* 309*/        ContractExecInfosCollection coll = ice.getContractExecInfosCollection(view);
/* 310*/        ContractExecInfosInfo info = null;
/* 311*/        if(coll != null && coll.size() == 1)
/* 312*/            info = coll.get(0);

/* 314*/        if(info == null)
/* 315*/            return;

/* 317*/        if("audit".equals(type))
                {/* 318*/            if(conBill.isHasSettled())
                    {/* 319*/                temp = conBill.getSettleAmt();
/* 320*/                tempOri = getSettleAmtOri(ctx, contractId);
/* 321*/                info.setCostAmt(temp);
/* 322*/                info.setCostAmtOri(tempOri);
/* 323*/                info.setSettleAmt(temp);
/* 324*/                info.setSettleAmtOri(tempOri);
                    } else
                    {
/* 327*/                FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 328*/                builder.appendSql("select sum(case fhasSettled when  0 then  famount else fbalanceamount end ) as changeAmt , sum(case fhasSettled when 0 then foriginalAmount else foriBalanceAmount end) as changeAmtOri from T_Con_ContractChangeBill where ");




/* 333*/                builder.appendParam("FContractBillID", contractId);
/* 334*/                builder.appendSql(" and (");
/* 335*/                builder.appendParam("FState", "4AUDITTED");
/* 336*/                builder.appendSql(" or ");
/* 337*/                builder.appendParam("FState", "8VISA");
/* 338*/                builder.appendSql(" or ");
/* 339*/                builder.appendParam("FState", "7ANNOUNCE");
/* 340*/                builder.appendSql(" )");
/* 341*/                IRowSet rs = builder.executeQuery();
/* 342*/                BigDecimal changeAmt = FDCHelper.ZERO;
/* 343*/                BigDecimal changeAmtOri = FDCHelper.ZERO;


/* 346*/                try
                        {
/* <-MISALIGNED-> */ /* 346*/                    while(rs.next()) 
                            {
/* <-MISALIGNED-> */ /* 346*/                        changeAmt = rs.getBigDecimal("changeAmt");
/* <-MISALIGNED-> */ /* 347*/                        changeAmtOri = rs.getBigDecimal("changeAmtOri");
                            }
                        }
/* <-MISALIGNED-> */ /* 349*/                catch(SQLException e)
                        {
/* <-MISALIGNED-> */ /* 350*/                    e.printStackTrace();
/* <-MISALIGNED-> */ /* 351*/                    logger.error(e.getMessage());
                        }
/* <-MISALIGNED-> */ /* 354*/                temp = FDCHelper.add(conBill.getAmount(), changeAmt);
/* <-MISALIGNED-> */ /* 355*/                tempOri = FDCHelper.add(conBill.getOriginalAmount(), changeAmtOri);
/* <-MISALIGNED-> */ /* 356*/                info.setCostAmt(temp);
/* <-MISALIGNED-> */ /* 357*/                info.setCostAmtOri(tempOri);
                    }
/* <-MISALIGNED-> */ /* 360*/            info.setHasSettled(conBill.isHasSettled());
/* <-MISALIGNED-> */ /* 361*/            BigDecimal processAmt = getProcessAmt(ctx, contractId);
/* <-MISALIGNED-> */ /* 362*/            info.setCompletedAmt(processAmt);
/* <-MISALIGNED-> */ /* 364*/            info.setNotCompletedAmt(FDCHelper.subtract(temp, processAmt));
/* <-MISALIGNED-> */ /* 365*/            selector.add("costAmt");
/* <-MISALIGNED-> */ /* 366*/            selector.add("costAmtOri");
/* <-MISALIGNED-> */ /* 367*/            selector.add("hasSettled");
/* <-MISALIGNED-> */ /* 368*/            selector.add("settleAmt");
/* <-MISALIGNED-> */ /* 369*/            selector.add("settleAmtOri");
/* <-MISALIGNED-> */ /* 371*/            selector.add("completedAmt");
/* <-MISALIGNED-> */ /* 372*/            selector.add("notCompletedAmt");
/* <-MISALIGNED-> */ /* 373*/            _updatePartial(ctx, info, selector);
                } else
/* <-MISALIGNED-> */ /* 374*/        if("unAudit".equals(type))
                {
/* <-MISALIGNED-> */ /* 376*/            info.setSettleAmt(null);
/* <-MISALIGNED-> */ /* 377*/            info.setSettleAmtOri(null);
/* <-MISALIGNED-> */ /* 378*/            info.setHasSettled(false);









/* 391*/            temp = getProcessAmt(ctx, contractId);

/* 393*/            info.setCompletedAmt(temp);
/* 394*/            BigDecimal changeAmt = getChangeAmt(ctx, contractId);
/* 395*/            BigDecimal changeAmtOri = getChangeAmtOri(ctx, contractId);

/* 397*/            if(changeAmt == null || changeAmt.compareTo(FDCHelper.ZERO) == 0)
                    {/* 398*/                info.setChangeAmt(null);
/* 399*/                info.setChangeAmtOri(null);
/* 400*/                info.setCostAmt(conBill.getAmount());
/* 401*/                info.setCostAmtOri(conBill.getOriginalAmount());
                    } else
                    {/* 403*/                info.setChangeAmt(changeAmt);
/* 404*/                info.setChangeAmtOri(changeAmtOri);
/* 405*/                info.setCostAmt(FDCHelper.add(conBill.getAmount(), changeAmt));
/* 406*/                info.setCostAmtOri(FDCHelper.add(conBill.getOriginalAmount(), changeAmtOri));
                    }

/* 409*/            info.setNotCompletedAmt(FDCHelper.subtract(info.getCostAmt(), info.getCompletedAmt()));
/* 410*/            selector.add("costAmt");
/* 411*/            selector.add("costAmtOri");
/* 412*/            selector.add("hasSettled");
/* 413*/            selector.add("settleAmt");
/* 414*/            selector.add("settleAmtOri");
/* 415*/            selector.add("completedAmt");
/* 416*/            selector.add("notCompletedAmt");
/* 417*/            _updatePartial(ctx, info, selector);
                }
            }
            protected void _updatePayment(Context ctx, String type, String contractId)
                throws BOSException, EASBizException
            {






/* 429*/        if(contractId.equals(""))
/* 430*/            return;











/* 442*/        boolean isCon = isContract(contractId);
/* 443*/        IContractExecInfos ice = ContractExecInfosFactory.getLocalInstance(ctx);
/* 444*/        EntityViewInfo view = new EntityViewInfo();
/* 445*/        SelectorItemCollection selector = new SelectorItemCollection();
/* 446*/        if(isCon)
                {/* 447*/            view.getSelector().add("id");
/* 448*/            view.getSelector().add("costAmt");
/* 449*/            view.getSelector().add("costAmtOri");
/* 450*/            view.getSelector().add("deductedAmt");
/* 451*/            view.getSelector().add("deductedAmtOri");
/* 452*/            view.getSelector().add("compensatedAmt");
/* 453*/            view.getSelector().add("compensatedAmtOri");
/* 454*/            view.getSelector().add("guerdonAmt");
/* 455*/            view.getSelector().add("guerdonAmtOri");
/* 456*/            view.getSelector().add("completedAmt");
/* 457*/            view.getSelector().add("notCompletedAmt");
/* 458*/            view.getSelector().add("invoicedAmt");
/* 459*/            view.getSelector().add("partAMatlAmt");
/* 460*/            view.getSelector().add("partAMatLoaAmt");
/* 461*/            FilterInfo filter = new FilterInfo();
/* 462*/            filter.getFilterItems().add(new FilterItemInfo("contractBill", contractId));
/* 463*/            view.setFilter(filter);
/* 464*/            ContractExecInfosCollection coll = ice.getContractExecInfosCollection(view);
/* 465*/            ContractExecInfosInfo info = null;
/* 466*/            if(coll != null && coll.size() == 1)
/* 467*/                info = coll.get(0);

/* 469*/            if(info == null)
/* 470*/                return;


/* 473*/            BigDecimal deductedAmt = FDCHelper.ZERO;
/* 474*/            BigDecimal deductedAmtOri = FDCHelper.ZERO;

/* 476*/            BigDecimal compensatedAmt = FDCHelper.ZERO;
/* 477*/            BigDecimal compensatedAmtOri = FDCHelper.ZERO;

/* 479*/            BigDecimal guerdonAmt = FDCHelper.ZERO;
/* 480*/            BigDecimal guerdonAmtOri = FDCHelper.ZERO;

/* 482*/            BigDecimal invoicedAmt = FDCHelper.ZERO;

/* 484*/            BigDecimal partAMatlAmt = FDCHelper.ZERO;
/* 485*/            BigDecimal partAMatLoaAmt = FDCHelper.ZERO;
/* 486*/            Map ortherAmt = getOrtherAmt(ctx, contractId);
/* 487*/            if(ortherAmt != null && ortherAmt.size() > 0)
                    {/* 488*/                deductedAmt = (BigDecimal)ortherAmt.get((new StringBuilder(String.valueOf(contractId))).append("deductedAmt").toString());
/* 489*/                deductedAmtOri = (BigDecimal)ortherAmt.get((new StringBuilder(String.valueOf(contractId))).append("deductedAmtOri").toString());
/* 490*/                compensatedAmt = (BigDecimal)ortherAmt.get((new StringBuilder(String.valueOf(contractId))).append("compensatedAmt").toString());
/* 491*/                compensatedAmtOri = (BigDecimal)ortherAmt.get((new StringBuilder(String.valueOf(contractId))).append("compensatedAmtOri").toString());
/* 492*/                guerdonAmt = (BigDecimal)ortherAmt.get((new StringBuilder(String.valueOf(contractId))).append("guerdonAmt").toString());
/* 493*/                guerdonAmtOri = (BigDecimal)ortherAmt.get((new StringBuilder(String.valueOf(contractId))).append("guerdonAmtOri").toString());
/* 494*/                invoicedAmt = (BigDecimal)ortherAmt.get((new StringBuilder(String.valueOf(contractId))).append("invoicedAmt").toString());
/* 495*/                partAMatlAmt = (BigDecimal)ortherAmt.get((new StringBuilder(String.valueOf(contractId))).append("partAMatlAmt").toString());
/* 496*/                partAMatLoaAmt = (BigDecimal)ortherAmt.get((new StringBuilder(String.valueOf(contractId))).append("partAMatLoaAmt").toString());
                    }

/* 499*/            if("pay".equals(type) || "unPay".equals(type))
                    {

/* 502*/                ContractBillInfo conBill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
/* 503*/                boolean hasPaySettle = hasPaySettle(ctx, contractId, conBill.isHasSettled());





/* 509*/                if(hasPaySettle)
                        {/* 510*/                    info.setCompletedAmt(conBill.getSettleAmt());
/* 511*/                    info.setNotCompletedAmt(FDCHelper.ZERO);
                        } else/* 512*/                if(conBill.isHasSettled())
                        {/* 513*/                    info.setCompletedAmt(getProcessAmt(ctx, contractId));
/* 514*/                    info.setNotCompletedAmt(FDCHelper.subtract(conBill.getSettleAmt(), info.getCompletedAmt()));
                        } else/* 515*/                if(!conBill.isHasSettled())
                        {/* 516*/                    info.setCompletedAmt(getProcessAmt(ctx, contractId));
/* 517*/                    info.setNotCompletedAmt(FDCHelper.subtract(info.getCostAmt(), info.getCompletedAmt()));
                        }
/* 519*/                info.setDeductedAmt(deductedAmt);
/* 520*/                info.setDeductedAmtOri(deductedAmtOri);
/* 521*/                info.setCompensatedAmt(compensatedAmt);
/* 522*/                info.setCompensatedAmtOri(compensatedAmtOri);
/* 523*/                info.setGuerdonAmt(guerdonAmt);
/* 524*/                info.setGuerdonAmtOri(guerdonAmtOri);
/* 525*/                info.setInvoicedAmt(invoicedAmt);
/* 526*/                info.setPaidAmt(getPaidAmt(ctx, contractId));
/* 527*/                info.setPaidAmtOri(getPaidAmtOri(ctx, contractId));
/* 528*/                info.setPartAMatlAmt(partAMatlAmt);
/* 529*/                info.setPartAMatLoaAmt(partAMatLoaAmt);
/* 530*/                selector.add("deductedAmt");
/* 531*/                selector.add("deductedAmtOri");
/* 532*/                selector.add("compensatedAmt");
/* 533*/                selector.add("compensatedAmtOri");
/* 534*/                selector.add("guerdonAmt");
/* 535*/                selector.add("guerdonAmtOri");
/* 536*/                selector.add("paidAmt");
/* 537*/                selector.add("paidAmtOri");
/* 538*/                selector.add("completedAmt");
/* 539*/                selector.add("notCompletedAmt");
/* 540*/                selector.add("invoicedAmt");
/* 541*/                selector.add("partAMatlAmt");
/* 542*/                selector.add("partAMatLoaAmt");
/* 543*/                _updatePartial(ctx, info, selector);
                    }
                } else
                {/* 546*/            view.getSelector().add("id");
/* 547*/            view.getSelector().add("costAmt");
/* 548*/            view.getSelector().add("costAmtOri");
/* 549*/            view.getSelector().add("completedAmt");
/* 550*/            view.getSelector().add("notCompletedAmt");
/* 551*/            view.getSelector().add("invoicedAmt");
/* 552*/            FilterInfo filter = new FilterInfo();
/* 553*/            filter.getFilterItems().add(new FilterItemInfo("conWithoutText", contractId));
/* 554*/            view.setFilter(filter);
/* 555*/            ContractExecInfosCollection coll = ice.getContractExecInfosCollection(view);
/* 556*/            ContractExecInfosInfo info = null;
/* 557*/            if(coll != null && coll.size() == 1)
/* 558*/                info = coll.get(0);

/* 560*/            if(info == null)
/* 561*/                return;



/* 565*/            if("pay".equals(type) || "unPay".equals(type))
                    {





/* 572*/                selector.add("paidAmt");
/* 573*/                selector.add("paidAmtOri");
/* 574*/                info.setPaidAmt(getPaidAmt(ctx, contractId));
/* 575*/                info.setPaidAmtOri(getPaidAmtOri(ctx, contractId));





















/* 597*/                _updatePartial(ctx, info, selector);
                    }
                }
            }
            protected BigDecimal getChangeAmt(Context ctx, String contractId)
                throws BOSException, EASBizException
            {








/* 612*/        if(contractId.equals(""))
/* 613*/            return null;

/* 615*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 616*/        builder.appendSql("select sum(case fhasSettled when  0 then  famount else fbalanceamount end ) as changeAmt from T_Con_ContractChangeBill where ");


/* 619*/        builder.appendParam("FContractBillID", contractId);
/* 620*/        builder.appendSql(" and (");
/* 621*/        builder.appendParam("FState", "4AUDITTED");
/* 622*/        builder.appendSql(" or ");
/* 623*/        builder.appendParam("FState", "8VISA");
/* 624*/        builder.appendSql(" or ");
/* 625*/        builder.appendParam("FState", "7ANNOUNCE");
/* 626*/        builder.appendSql(" )");
/* 627*/        IRowSet rs = builder.executeQuery();
/* 628*/        BigDecimal changeAmt = null;


/* 631*/        try
                {
/* <-MISALIGNED-> */ /* 631*/            while(rs.next()) 
/* <-MISALIGNED-> */ /* 631*/                changeAmt = rs.getBigDecimal("changeAmt");
                }
/* <-MISALIGNED-> */ /* 633*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 634*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 635*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /* 637*/        return changeAmt;
            }
            protected BigDecimal getChangeAmtOri(Context ctx, String contractId)
                throws BOSException, EASBizException
            {




/* 650*/        if(contractId.equals(""))
/* 651*/            return null;

/* 653*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 654*/        builder.appendSql("select sum(case fhasSettled when 0 then foriginalAmount else foriBalanceAmount end) as changeAmtOri from T_Con_ContractChangeBill where ");


/* 657*/        builder.appendParam("FContractBillID", contractId);
/* 658*/        builder.appendSql(" and (");
/* 659*/        builder.appendParam("FState", "4AUDITTED");
/* 660*/        builder.appendSql(" or ");
/* 661*/        builder.appendParam("FState", "8VISA");
/* 662*/        builder.appendSql(" or ");
/* 663*/        builder.appendParam("FState", "7ANNOUNCE");
/* 664*/        builder.appendSql(" )");
/* 665*/        IRowSet rs = builder.executeQuery();
/* 666*/        BigDecimal changeAmtOri = null;


/* 669*/        try
                {
/* <-MISALIGNED-> */ /* 669*/            while(rs.next()) 
/* <-MISALIGNED-> */ /* 669*/                changeAmtOri = rs.getBigDecimal("changeAmtOri");
                }
/* <-MISALIGNED-> */ /* 671*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 672*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 673*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /* 675*/        return changeAmtOri;
            }
            protected Map getChangeAmt(Context ctx, Set conIds)
                throws BOSException, EASBizException
            {




/* 688*/        Map changeMap = new HashMap();
/* 689*/        if(conIds == null || conIds.size() < 0)
/* 690*/            return changeMap;

/* 692*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 693*/        builder.appendSql("select FContractBillID as contractId , sum(case fhasSettled when  0 then  famount else fbalanceamount end ) as changeAmt, sum(case fhasSettled when 0 then foriginalAmount else foriBalanceAmount end) as changeAmtOri from T_Con_ContractChangeBill where ");



/* 697*/        builder.appendParam("FContractBillID", conIds.toArray());
/* 698*/        builder.appendSql(" and (");
/* 699*/        builder.appendParam("FState", "4AUDITTED");
/* 700*/        builder.appendSql(" or ");
/* 701*/        builder.appendParam("FState", "8VISA");
/* 702*/        builder.appendSql(" or ");
/* 703*/        builder.appendParam("FState", "7ANNOUNCE");
/* 704*/        builder.appendSql(" ) group by FContractBillID");
/* 705*/        IRowSet rs = builder.executeQuery();


/* 708*/        try
                {
/* <-MISALIGNED-> */ /* 708*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /* 708*/                String contractId = rs.getString("contractId");
/* <-MISALIGNED-> */ /* 709*/                BigDecimal changeAmt = rs.getBigDecimal("changeAmt");
/* <-MISALIGNED-> */ /* 710*/                BigDecimal changeAmtOri = rs.getBigDecimal("changeAmtOri");
/* <-MISALIGNED-> */ /* 711*/                changeMap.put(contractId, changeAmt);
/* <-MISALIGNED-> */ /* 712*/                changeMap.put((new StringBuilder(String.valueOf(contractId))).append("ori").toString(), changeAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /* 714*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 715*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 716*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /* 718*/        return changeMap;
            }
            protected Map getChangeAmtOri(Context ctx, Set conIds)
                throws BOSException, EASBizException
            {


/* 731*/        Map changeOriMap = new HashMap();
/* 732*/        if(conIds == null || conIds.size() < 0)
/* 733*/            return changeOriMap;

/* 735*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 736*/        builder.appendSql("select FContractBillID as contractId , sum(case fhasSettled when 0 then foriginalAmount else foriBalanceAmount end) as changeAmtOri from T_Con_ContractChangeBill where ");


/* 739*/        builder.appendParam("FContractBillID", conIds.toArray());
/* 740*/        builder.appendSql("  and (");
/* 741*/        builder.appendParam("FState", "4AUDITTED");
/* 742*/        builder.appendSql(" or ");
/* 743*/        builder.appendParam("FState", "8VISA");
/* 744*/        builder.appendSql(" or ");
/* 745*/        builder.appendParam("FState", "7ANNOUNCE");
/* 746*/        builder.appendSql(" ) group by FContractBillID");
/* 747*/        IRowSet rs = builder.executeQuery();


/* 750*/        try
                {
/* <-MISALIGNED-> */ /* 750*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /* 750*/                String contractId = rs.getString("contractId");
/* <-MISALIGNED-> */ /* 751*/                BigDecimal changeAmtOri = rs.getBigDecimal("changeAmtOri");
/* <-MISALIGNED-> */ /* 752*/                changeOriMap.put(contractId, changeAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /* 754*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 755*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 756*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /* 758*/        return changeOriMap;
            }
            protected BigDecimal getProcessAmt(Context ctx, String contractId)
                throws BOSException, EASBizException
            {


/* 771*/        if(contractId.equals(""))
/* 772*/            return null;

/* 774*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);










				SelectorItemCollection sic=new SelectorItemCollection();
				sic.add("curProject.fullOrgUnit.id");

/* 787*/        String companyID = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId), sic).getCurProject().getFullOrgUnit().getId().toString();
/* 788*/        BigDecimal temp = null;


/* 791*/        if(FDCUtils.getDefaultFDCParamByKey(ctx, companyID, "FDC317_SEPARATEFROMPAYMENT"))
                {/* 792*/            temp = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(contractId);
                } else
                {/* 794*/            builder.appendSql(" select fcontractid as contractId, sum(FCompletePrjAmt) as amount  from t_con_payrequestbill where fid in  (  select distinct pay.ffdcpayreqid from t_cas_paymentbill pay  inner join T_FDC_PaymentType type on type.fid = pay.ffdcPayTypeId  where ");




/* 799*/            builder.appendParam("pay.fbillstatus", new Integer(15));
/* 800*/            builder.appendSql(" and ");
/* 801*/            builder.appendParam("type.FPayTypeID", "Ga7RLQETEADgAAC6wKgOlOwp3Sw=");
/* 802*/            builder.appendSql(" and ");
/* 803*/            builder.appendParam("pay.fcontractbillid", contractId);
/* 804*/            builder.appendSql(" ) group by fcontractid");
/* 805*/            IRowSet rs = builder.executeQuery();



/* 809*/            try
                    {
/* <-MISALIGNED-> */ /* 809*/                while(rs.next()) 
/* <-MISALIGNED-> */ /* 809*/                    temp = rs.getBigDecimal("amount");
                    }
/* <-MISALIGNED-> */ /* 811*/            catch(SQLException e)
                    {
/* <-MISALIGNED-> */ /* 812*/                e.printStackTrace();
                    }
                }
/* <-MISALIGNED-> */ /* 815*/        return temp;
            }
            protected Map getProcessAmt(Context ctx, Set conIds)
                throws BOSException, EASBizException
            {



/* 827*/        Map processAmt = new HashMap();
/* 828*/        if(conIds == null || conIds.size() < 0)
/* 829*/            return processAmt;

/* 831*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 832*/        String companyID = ContextUtil.getCurrentFIUnit(ctx).getId().toString();












/* 845*/        if(FDCUtils.getDefaultFDCParamByKey(ctx, companyID, "FDC317_SEPARATEFROMPAYMENT"))
                {/* 846*/            processAmt = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(conIds);
                } else
                {/* 848*/            builder.appendSql(" select fcontractid as contractId, sum(FCompletePrjAmt) as amount  from t_con_payrequestbill where fid in  (  select distinct pay.ffdcpayreqid from t_cas_paymentbill pay  inner join T_FDC_PaymentType type on type.fid = pay.ffdcPayTypeId  where ");




/* 853*/            builder.appendParam("pay.fbillstatus", new Integer(15));
/* 854*/            builder.appendSql(" and ");
/* 855*/            builder.appendParam("type.FPayTypeID", "Ga7RLQETEADgAAC6wKgOlOwp3Sw=");
/* 856*/            builder.appendSql(" and  ");
/* 857*/            builder.appendParam("pay.fcontractbillid", conIds.toArray());
/* 858*/            builder.appendSql(" ) group by fcontractid");
/* 859*/            IRowSet rs = builder.executeQuery();


/* 862*/            try
                    {
/* <-MISALIGNED-> */ /* 862*/                while(rs.next()) 
                        {
/* <-MISALIGNED-> */ /* 862*/                    String conId = rs.getString("contractId");
/* <-MISALIGNED-> */ /* 863*/                    BigDecimal temp = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /* 864*/                    if(!processAmt.containsKey(conId))
/* <-MISALIGNED-> */ /* 865*/                        processAmt.put(conId, temp);
                        }
                    }
/* <-MISALIGNED-> */ /* 868*/            catch(SQLException e)
                    {
/* <-MISALIGNED-> */ /* 869*/                e.printStackTrace();
                    }
                }
/* <-MISALIGNED-> */ /* 872*/        return processAmt;
            }
            protected BigDecimal getPaidAmt(Context ctx, String contractId)
                throws BOSException, EASBizException
            {

/* 883*/        if(contractId.equals(""))
/* 884*/            return null;

/* 886*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 887*/        builder.appendSql("select FContractBillId as contractId , sum( FLocalAmount) as amount  from t_cas_paymentbill  where ");

/* 889*/        builder.appendParam("fbillstatus", new Integer(15));
/* 890*/        builder.appendSql(" and ");
/* 891*/        builder.appendParam("fcontractbillid", contractId);
/* 892*/        builder.appendSql(" group by FcontractBillId ");
/* 893*/        IRowSet rs = builder.executeQuery();
/* 894*/        BigDecimal temp = null;


/* 897*/        try
                {
/* <-MISALIGNED-> */ /* 897*/            while(rs.next()) 
/* <-MISALIGNED-> */ /* 897*/                temp = rs.getBigDecimal("amount");
                }
/* <-MISALIGNED-> */ /* 899*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 900*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 901*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /* 903*/        return temp;
            }
            protected BigDecimal getPaidAmtOri(Context ctx, String contractId)
                throws BOSException, EASBizException
            {


/* 914*/        if(contractId.equals(""))
/* 915*/            return null;

/* 917*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 918*/        builder.appendSql("select FContractBillId as contractId , sum( FAmount) as amount  from t_cas_paymentbill  where ");

/* 920*/        builder.appendParam("fbillstatus", new Integer(15));
/* 921*/        builder.appendSql(" and ");
/* 922*/        builder.appendParam("fcontractbillid", contractId);
/* 923*/        builder.appendSql(" group by FcontractBillId ");
/* 924*/        IRowSet rs = builder.executeQuery();
/* 925*/        BigDecimal temp = null;


/* 928*/        try
                {
/* <-MISALIGNED-> */ /* 928*/            while(rs.next()) 
/* <-MISALIGNED-> */ /* 928*/                temp = rs.getBigDecimal("amount");
                }
/* <-MISALIGNED-> */ /* 930*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 931*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 932*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /* 934*/        return temp;
            }
            protected Map getPaidAmt(Context ctx, Set conIds)
                throws BOSException, EASBizException
            {


/* 945*/        Map paidAmt = new HashMap();
/* 946*/        if(conIds == null || conIds.size() < 0)
/* 947*/            return paidAmt;

/* 949*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 950*/        builder.appendSql("select FContractBillId as contractId , sum( FLocalAmount) as amount  ,sum(FAmount) as oriAmount from t_cas_paymentbill  where ");

/* 952*/        builder.appendParam("fbillstatus", new Integer(15));
/* 953*/        builder.appendSql(" and  ");
/* 954*/        builder.appendParam("fcontractbillid", conIds.toArray());
/* 955*/        builder.appendSql("  group by FcontractBillId ");
/* 956*/        IRowSet rs = builder.executeQuery();


/* 959*/        try
                {
/* <-MISALIGNED-> */ /* 959*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /* 959*/                String id = rs.getString("contractId");
/* <-MISALIGNED-> */ /* 960*/                BigDecimal temp = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /* 961*/                BigDecimal tempOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /* 962*/                paidAmt.put(id, temp);
/* <-MISALIGNED-> */ /* 963*/                paidAmt.put((new StringBuilder(String.valueOf(id))).append("ori").toString(), tempOri);
                    }
                }
/* <-MISALIGNED-> */ /* 965*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 966*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 967*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /* 969*/        return paidAmt;
            }
            protected Map getOrtherAmt(Context ctx, String contractId)
                throws BOSException, EASBizException
            {


/* 982*/        Map ortherAmt = new HashMap();
/* 983*/        if(contractId == null || contractId.equals(""))
/* 984*/            return ortherAmt;

/* 986*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);


/* 989*/        builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount ,sum(doprb.foriginalAmount) as oriAmount from T_CON_DeductOfPayReqBill doprb ");
/* 990*/        builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
/* 991*/        builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
/* 992*/        builder.appendSql("where ");
/* 993*/        builder.appendParam("prb.FContractID", contractId);
/* 994*/        builder.appendSql(" and ");
/* 995*/        builder.appendParam(" doprb.FPasPaid", new Integer(1));
/* 996*/        builder.appendSql(" and ");
/* 997*/        builder.appendParam(" pmb.FBillstatus", new Integer(15));
/* 998*/        builder.appendSql(" group by prb.FContractID ");
/* 999*/        IRowSet rs = builder.executeQuery();


/*1002*/        try
                {
/* <-MISALIGNED-> */ /*1002*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1002*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1003*/                BigDecimal deductedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1004*/                BigDecimal deductedAmtOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /*1005*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("deductedAmt").toString(), deductedAmt);
/* <-MISALIGNED-> */ /*1006*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("deductedAmtOri").toString(), deductedAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /*1008*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1009*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1010*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /*1013*/        builder.clear();
/* <-MISALIGNED-> */ /*1014*/        builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount,sum(doprb.foriginalAmount) as oriAmount from T_CON_CompensationOfPayReqBill doprb ");
/* <-MISALIGNED-> */ /*1015*/        builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
/* <-MISALIGNED-> */ /*1016*/        builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
/* <-MISALIGNED-> */ /*1017*/        builder.appendSql("where ");
/* <-MISALIGNED-> */ /*1018*/        builder.appendParam("prb.FContractID", contractId);
/* <-MISALIGNED-> */ /*1019*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1020*/        builder.appendParam("doprb.FPasPaid", new Integer(1));
/* <-MISALIGNED-> */ /*1021*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1022*/        builder.appendParam(" pmb.FBillstatus", new Integer(15));
/* <-MISALIGNED-> */ /*1023*/        builder.appendSql(" group by prb.FContractID ");

/*1030*/        rs = builder.executeQuery();


/*1033*/        try
                {
/* <-MISALIGNED-> */ /*1033*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1033*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1034*/                BigDecimal compensatedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1035*/                BigDecimal compensatedAmtOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /*1036*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("compensatedAmt").toString(), compensatedAmt);
/* <-MISALIGNED-> */ /*1037*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("compensatedAmtOri").toString(), compensatedAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /*1039*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1040*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1041*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /*1044*/        builder.clear();
/* <-MISALIGNED-> */ /*1045*/        builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount ,sum(doprb.foriginalAmount) as oriAmount from T_CON_GuerdonOfPayReqBill doprb ");
/* <-MISALIGNED-> */ /*1046*/        builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
/* <-MISALIGNED-> */ /*1047*/        builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
/* <-MISALIGNED-> */ /*1048*/        builder.appendSql("where ");
/* <-MISALIGNED-> */ /*1049*/        builder.appendParam("prb.FContractID", contractId);
/* <-MISALIGNED-> */ /*1050*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1051*/        builder.appendParam(" doprb.FPasPaid", new Integer(1));
/* <-MISALIGNED-> */ /*1052*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1053*/        builder.appendParam(" pmb.FBillstatus", new Integer(15));
/* <-MISALIGNED-> */ /*1054*/        builder.appendSql(" group by prb.FContractID ");
/* <-MISALIGNED-> */ /*1055*/        rs = builder.executeQuery();
/* <-MISALIGNED-> */ /*1058*/        try
                {
/* <-MISALIGNED-> */ /*1058*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1058*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1059*/                BigDecimal deductedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1060*/                BigDecimal deductedAmtOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /*1061*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("guerdonAmt").toString(), deductedAmt);
/* <-MISALIGNED-> */ /*1062*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("guerdonAmtOri").toString(), deductedAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /*1064*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1065*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1066*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /*1069*/        builder.clear();/*1077*/        builder.appendSql(" select fcontractid ,sum(finvoiceamt) as amount from t_con_payrequestbill where ");
/*1078*/        builder.appendParam("FContractId", contractId);
/*1079*/        builder.appendSql(" and fid in ( select ffdcpayreqid from t_cas_paymentbill where ");
/*1080*/        builder.appendParam("fbillstatus", new Integer(15));
/*1081*/        builder.appendSql(" ) group by FContractID ");
/*1082*/        rs = builder.executeQuery();


/*1085*/        try
                {
/* <-MISALIGNED-> */ /*1085*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1085*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1086*/                BigDecimal invoicedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1087*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("invoicedAmt").toString(), invoicedAmt);
                    }
                }
/* <-MISALIGNED-> */ /*1089*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1090*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1091*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /*1094*/        builder.clear();/*1099*/        if(FDCUtils.getDefaultFDCParamByKey(ctx, null, "FDC305_CREATEPARTADEDUCT"))
                {/*1100*/            builder.appendSql("select sum(pa.FAMOUNT) as partAMatLoaAmt,sum(pa.FOriginalAmount) as  partAMatlAmt  from T_CON_PartAOfPayReqBill as pa,T_CAS_PaymentBill as py where ");

/*1102*/            builder.appendParam("py.FContractBillID", contractId);
/*1103*/            builder.appendSql(" and ");
/*1104*/            builder.appendParam(" py.FBillstatus", new Integer(15));
/*1105*/            builder.appendSql(" and pa.FPaymentBillID=py.FID");
                } else
                {/*1107*/            builder.appendSql("select sum(pa.FAMOUNT) as partAMatLoaAmt,sum(pa.FOriginalAmount) as  partAMatlAmt  from T_CON_PartAConfmOfPayReqBill as pa,T_CAS_PaymentBill as py where ");

/*1109*/            builder.appendParam("py.FContractBillID", contractId);
/*1110*/            builder.appendSql(" and ");
/*1111*/            builder.appendParam(" py.FBillstatus", new Integer(15));
/*1112*/            builder.appendSql(" and pa.FPaymentBillID=py.FID");
                }

/*1115*/        rs = builder.executeQuery();


/*1118*/        try
                {
/* <-MISALIGNED-> */ /*1118*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1118*/                BigDecimal partAMatlAmt = rs.getBigDecimal("partAMatlAmt");
/* <-MISALIGNED-> */ /*1119*/                BigDecimal partAMatLoaAmt = rs.getBigDecimal("partAMatLoaAmt");
/* <-MISALIGNED-> */ /*1120*/                ortherAmt.put((new StringBuilder(String.valueOf(contractId))).append("partAMatlAmt").toString(), partAMatlAmt);
/* <-MISALIGNED-> */ /*1121*/                ortherAmt.put((new StringBuilder(String.valueOf(contractId))).append("partAMatLoaAmt").toString(), partAMatLoaAmt);
                    }
                }
/* <-MISALIGNED-> */ /*1123*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1125*/            e.printStackTrace();
                }
/* <-MISALIGNED-> */ /*1127*/        return ortherAmt;
            }
            protected Map getOrtherAmt(Context ctx, Set conIds)
                throws BOSException, EASBizException
            {

/*1138*/        Map ortherAmt = new HashMap();
/*1139*/        if(conIds == null || conIds.size() < 0)
/*1140*/            return ortherAmt;

/*1142*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);


/*1145*/        builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount,sum(doprb.foriginalAmount) as oriAmount from T_CON_DeductOfPayReqBill doprb ");
/*1146*/        builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
/*1147*/        builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
/*1148*/        builder.appendSql("where  ");
/*1149*/        builder.appendParam("prb.FContractID", conIds.toArray());
/*1150*/        builder.appendSql(" and ");
/*1151*/        builder.appendParam("doprb.FPasPaid", new Integer(1));
/*1152*/        builder.appendSql(" and ");
/*1153*/        builder.appendParam(" pmb.FBillstatus", new Integer(15));
/*1154*/        builder.appendSql(" group by prb.FContractID ");
/*1155*/        IRowSet rs = builder.executeQuery();


/*1158*/        try
                {
/* <-MISALIGNED-> */ /*1158*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1158*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1159*/                BigDecimal deductedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1160*/                BigDecimal deductedAmtOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /*1161*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("deductedAmt").toString(), deductedAmt);
/* <-MISALIGNED-> */ /*1162*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("deductedAmtOri").toString(), deductedAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /*1164*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1165*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1166*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /*1169*/        builder.clear();
/* <-MISALIGNED-> */ /*1170*/        builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount ,sum(doprb.foriginalAmount) as oriAmount from T_CON_CompensationOfPayReqBill doprb ");
/* <-MISALIGNED-> */ /*1171*/        builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
/* <-MISALIGNED-> */ /*1172*/        builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
/* <-MISALIGNED-> */ /*1173*/        builder.appendSql("where ");
/* <-MISALIGNED-> */ /*1174*/        builder.appendParam("prb.FContractID", conIds.toArray());
/* <-MISALIGNED-> */ /*1175*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1176*/        builder.appendParam("doprb.FPasPaid", new Integer(1));
/* <-MISALIGNED-> */ /*1177*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1178*/        builder.appendParam(" pmb.FBillstatus", new Integer(15));
/* <-MISALIGNED-> */ /*1179*/        builder.appendSql(" group by prb.FContractID ");

/*1186*/        rs = builder.executeQuery();


/*1189*/        try
                {
/* <-MISALIGNED-> */ /*1189*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1189*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1190*/                BigDecimal compensatedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1191*/                BigDecimal compensatedAmtOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /*1192*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("compensatedAmt").toString(), compensatedAmt);
/* <-MISALIGNED-> */ /*1193*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("compensatedAmtOri").toString(), compensatedAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /*1195*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1196*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1197*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /*1200*/        builder.clear();
/* <-MISALIGNED-> */ /*1201*/        builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount ,sum(doprb.foriginalAmount) as oriAmount from T_CON_GuerdonOfPayReqBill doprb ");
/* <-MISALIGNED-> */ /*1202*/        builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
/* <-MISALIGNED-> */ /*1203*/        builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
/* <-MISALIGNED-> */ /*1204*/        builder.appendSql("where ");
/* <-MISALIGNED-> */ /*1205*/        builder.appendParam("prb.FContractID", conIds.toArray());
/* <-MISALIGNED-> */ /*1206*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1207*/        builder.appendParam("doprb.FPasPaid", new Integer(1));
/* <-MISALIGNED-> */ /*1208*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1209*/        builder.appendParam(" pmb.FBillstatus", new Integer(15));
/* <-MISALIGNED-> */ /*1210*/        builder.appendSql(" group by prb.FContractID ");
/* <-MISALIGNED-> */ /*1212*/        rs = builder.executeQuery();
/* <-MISALIGNED-> */ /*1215*/        try
                {
/* <-MISALIGNED-> */ /*1215*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1215*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1216*/                BigDecimal guerdonAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1217*/                BigDecimal guerdonAmtOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /*1218*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("guerdonAmt").toString(), guerdonAmt);
/* <-MISALIGNED-> */ /*1219*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("guerdonAmtOri").toString(), guerdonAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /*1221*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1222*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1223*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /*1226*/        builder.clear();
/*1234*/        builder.appendSql(" select fcontractid ,sum(finvoiceamt) as amount from t_con_payrequestbill where ");
/*1235*/        builder.appendParam("FContractId", conIds.toArray());
/*1236*/        builder.appendSql(" and fid in ( select ffdcpayreqid from t_cas_paymentbill where ");
/*1237*/        builder.appendParam("fbillstatus", new Integer(15));
/*1238*/        builder.appendSql(" ) group by FContractID ");
/*1239*/        rs = builder.executeQuery();


/*1242*/        try
                {
/* <-MISALIGNED-> */ /*1242*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1242*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1243*/                BigDecimal invoicedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1244*/                ortherAmt.put((new StringBuilder(String.valueOf(id))).append("invoicedAmt").toString(), invoicedAmt);
                    }
                }
/* <-MISALIGNED-> */ /*1246*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1247*/            e.printStackTrace();
                }
/* <-MISALIGNED-> */ /*1250*/        builder.clear();
/* <-MISALIGNED-> */ /*1253*/        if(FDCUtils.getDefaultFDCParamByKey(ctx, null, "FDC305_CREATEPARTADEDUCT"))
                {
/* <-MISALIGNED-> */ /*1254*/            builder.appendSql("select py.fcontractbillid, sum(pa.FAMOUNT) as partAMatLoaAmt, sum(pa.FOriginalAmount) as  partAMatlAmt from T_CON_PartAOfPayReqBill as pa,T_CAS_PaymentBill as py where ");
/* <-MISALIGNED-> */ /*1256*/            builder.appendParam("(py.FContractBillID", conIds.toArray());
/* <-MISALIGNED-> */ /*1257*/            builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1258*/            builder.appendParam(" py.FBillstatus", new Integer(15));
/* <-MISALIGNED-> */ /*1259*/            builder.appendSql(" and pa.FPaymentBillID=py.FID");
/* <-MISALIGNED-> */ /*1260*/            builder.appendSql(" ) group by py.fcontractbillid ");
                } else
                {
/* <-MISALIGNED-> */ /*1262*/            builder.appendSql("select py.fcontractbillid, sum(pa.FAMOUNT) as partAMatLoaAmt,sum(pa.FOriginalAmount) as partAMatlAmt  from T_CON_PartAConfmOfPayReqBill as pa,T_CAS_PaymentBill as py where ");
/* <-MISALIGNED-> */ /*1264*/            builder.appendParam("(py.FContractBillID", conIds.toArray());
/* <-MISALIGNED-> */ /*1265*/            builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1266*/            builder.appendParam(" py.FBillstatus", new Integer(15));
/* <-MISALIGNED-> */ /*1267*/            builder.appendSql(" and pa.FPaymentBillID=py.FID");
/* <-MISALIGNED-> */ /*1268*/            builder.appendSql(" ) group by py.fcontractbillid ");
                }
/* <-MISALIGNED-> */ /*1270*/        rs = builder.executeQuery();/*1273*/        try
                {
/* <-MISALIGNED-> */ /*1273*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1273*/                String contractId = rs.getString("fcontractbillid");
/* <-MISALIGNED-> */ /*1274*/                BigDecimal partAMatlAmt = rs.getBigDecimal("partAMatlAmt");
/* <-MISALIGNED-> */ /*1275*/                BigDecimal partAMatLoaAmt = rs.getBigDecimal("partAMatLoaAmt");
/* <-MISALIGNED-> */ /*1276*/                ortherAmt.put((new StringBuilder(String.valueOf(contractId))).append("partAMatlAmt").toString(), partAMatlAmt);
/* <-MISALIGNED-> */ /*1277*/                ortherAmt.put((new StringBuilder(String.valueOf(contractId))).append("partAMatLoaAmt").toString(), partAMatLoaAmt);
                    }
                }
/* <-MISALIGNED-> */ /*1279*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1280*/            e.printStackTrace();
                }
/* <-MISALIGNED-> */ /*1282*/        return ortherAmt;
            }
            protected boolean hasPaySettle(Context ctx, String contractId, boolean hasSettled)
                throws BOSException, EASBizException
            {

/*1294*/        if(!hasSettled)
/*1295*/            return false;

/*1297*/        boolean hasPaySettle = false;
/*1298*/        EntityViewInfo viewInfo = new EntityViewInfo();
/*1299*/        viewInfo.getSelector().add("FdcPayType.payType.id");
/*1300*/        FilterInfo filterInfo = new FilterInfo();
/*1301*/        filterInfo.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED));

/*1303*/        filterInfo.getFilterItems().add(new FilterItemInfo("contractBillId", contractId));

/*1305*/        viewInfo.setFilter(filterInfo);

/*1307*/        PaymentBillCollection pbc = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(viewInfo);

/*1309*/        if(pbc != null && pbc.size() > 0)
                {/*1310*/            for(int i = 0; i < pbc.size(); i++)
                    {/*1311*/                PaymentBillInfo pbi = pbc.get(i);
/*1312*/                if(pbi == null || pbi.getFdcPayType() == null || pbi.getFdcPayType().getPayType() == null || pbi.getFdcPayType().getPayType().getId() == null || !"Ga7RLQETEADgAAC/wKgOlOwp3Sw=".equals(pbi.getFdcPayType().getPayType().getId().toString()))






/*1319*/                    continue;/*1319*/                hasPaySettle = true;
/*1320*/                break;
                    }
                }

/*1324*/        return hasPaySettle;
            }
            protected Map hasPaySettle(Context ctx, Set conIds, boolean hasSettled)
                throws BOSException, EASBizException
            {







/*1336*/        Map hasPaySettleMap = new HashMap();
/*1337*/        if(!hasSettled)
/*1338*/            return hasPaySettleMap;



























/*1366*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/*1367*/        builder.appendSql("       select distinct(pay.fcontractbillid) as contractId from t_cas_paymentbill pay inner join \r\n");
/*1368*/        builder.appendSql("       t_fdc_paymenttype payType on payType.Fid = pay.ffdcpaytypeid \r\n");
/*1369*/        builder.appendSql("       where payType.Fpaytypeid = 'Ga7RLQETEADgAAC/wKgOlOwp3Sw=' and \r\n");
/*1370*/        builder.appendParam("pay.fcontractbillid", conIds.toArray());
/*1371*/        builder.appendSql("       and pay.fbillstatus = 15 \r\n");
/*1372*/        IRowSet rs = builder.executeQuery();


/*1375*/        try
                {
/* <-MISALIGNED-> */ /*1375*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1375*/                String contractId = rs.getString("contractId");
/* <-MISALIGNED-> */ /*1376*/                hasPaySettleMap.put(contractId, Boolean.TRUE);
                    }
                }
/* <-MISALIGNED-> */ /*1378*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1379*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1380*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /*1382*/        return hasPaySettleMap;
            }
            protected void _synOldContract(Context ctx)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /*1389*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);












/*1406*/        Set newConIds = new HashSet();

/*1408*/        builder.appendSql(" select fid, fcurprojectid ,famount,foriginalAmount, FControlUnitID,FOrgUnitID,FPeriodID from T_CON_ContractBill where ");
/*1409*/        builder.appendParam("FState", "4AUDITTED");
/*1410*/        builder.appendSql(" and (fcontractpropert!='SUPPLY' or fisamtwithoutcost!=1 )");

/*1412*/        builder.appendSql(" and fid not in ( select FContractBillId from T_CON_ContractExecInfos ");

/*1414*/        builder.appendSql(" where FContractBillId is not null)");



/*1418*/        String insertSql = "insert into T_Con_ContractExecInfos (FID,FContractBillid,FCurProjectid,FCostAmt ,FCostAmtOri , FControlUnitID,FOrgUnitID,FPeriodID,FisNoText ) values (?,?,?,?,?,?,?,?,?)";


/*1421*/        List insertList = new ArrayList();

/*1423*/        try
                {
                    String id;
                    String contractId;
                    String curProjectId;
                    BigDecimal costAmt;
                    BigDecimal costAmtOri;
                    String controlUnitId;
                    String orgUnitId;
                    String periodId;
/* <-MISALIGNED-> */ /*1423*/            for(IRowSet rs = builder.executeQuery(); rs.next(); insertList.add(Arrays.asList(new Object[] {

/*1435*/    id, contractId, curProjectId, costAmt, costAmtOri, controlUnitId, orgUnitId, periodId, new Integer(0)
})))
                    {
/* <-MISALIGNED-> */ /*1425*/                ContractExecInfosInfo info = new ContractExecInfosInfo();
/* <-MISALIGNED-> */ /*1426*/                id = BOSUuid.create(info.getBOSType()).toString();
/* <-MISALIGNED-> */ /*1427*/                contractId = rs.getString("fid");
/* <-MISALIGNED-> */ /*1428*/                newConIds.add(contractId);
/* <-MISALIGNED-> */ /*1429*/                curProjectId = rs.getString("fcurprojectid").toString();
/* <-MISALIGNED-> */ /*1430*/                costAmt = rs.getBigDecimal("famount");
/* <-MISALIGNED-> */ /*1431*/                costAmtOri = rs.getBigDecimal("foriginalAmount");
/* <-MISALIGNED-> */ /*1432*/                controlUnitId = rs.getString("FControlUnitID");
/* <-MISALIGNED-> */ /*1433*/                orgUnitId = rs.getString("FOrgUnitID");
/* <-MISALIGNED-> */ /*1434*/                periodId = rs.getString("FPeriodID");
                    }
/* <-MISALIGNED-> */ /*1439*/            builder.executeBatch(insertSql, insertList);
                }
/* <-MISALIGNED-> */ /*1440*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1441*/            e.printStackTrace();
                }
/* <-MISALIGNED-> */ /*1444*/        if(newConIds != null && newConIds.size() > 0)
                {
/* <-MISALIGNED-> */ /*1445*/            List list = new ArrayList();
/* <-MISALIGNED-> */ /*1446*/            if(newConIds.size() > 500)
                    {
/* <-MISALIGNED-> */ /*1447*/                int i = 0;
/* <-MISALIGNED-> */ /*1448*/                Set mySet = new HashSet();
/* <-MISALIGNED-> */ /*1449*/                for(Iterator iter = newConIds.iterator(); iter.hasNext();)
                        {
/* <-MISALIGNED-> */ /*1450*/                    if(i % 500 == 0)
                            {
/* <-MISALIGNED-> */ /*1451*/                        list.add(mySet);
/* <-MISALIGNED-> */ /*1452*/                        mySet = new HashSet();
                            }
/* <-MISALIGNED-> */ /*1454*/                    mySet.add(iter.next());
/* <-MISALIGNED-> */ /*1449*/                    i++;
                        }
/* <-MISALIGNED-> */ /*1456*/                list.add(mySet);
                    } else
                    {
/* <-MISALIGNED-> */ /*1458*/                list.add(newConIds);
                    }
/* <-MISALIGNED-> */ /*1461*/            for(int i = 0; i < list.size(); i++)
                    {
/* <-MISALIGNED-> */ /*1463*/                newConIds = (Set)list.get(i);
/* <-MISALIGNED-> */ /*1464*/                if(newConIds.size() != 0)
                        {
/* <-MISALIGNED-> */ /*1468*/                    builder.clear();
/* <-MISALIGNED-> */ /*1469*/                    String updateChangeSql = " update T_CON_ContractExecInfos set FChangeAmt = ? ,FChangeAmtOri = ?, FCostAmt = ?, FCostAmtOri = ? where FContractBillId = ?";
/* <-MISALIGNED-> */ /*1471*/                    List updateChangeList = new ArrayList();
/* <-MISALIGNED-> */ /*1473*/                    builder.appendSql("select con.FID as contractId,con.FAmount as amount ,con.FOriginalAmount as oriAmount , sum(case conChange.fhasSettled when  0 then  conChange.famount  else conChange.fbalanceamount end ) as changeAmt , sum(case conChange.fhasSettled when 0 then conChange.foriginalAmount else conChange.foriBalanceAmount end) as changeAmtOri from T_Con_ContractChangeBill conChange inner join T_CON_ContractBill con on  con.FID = conChange.FContractBillID where ");
/* <-MISALIGNED-> */ /*1479*/                    builder.appendParam("con.FID", newConIds.toArray());
/* <-MISALIGNED-> */ /*1480*/                    builder.appendSql(" and ( ");
/* <-MISALIGNED-> */ /*1481*/                    builder.appendParam("conChange.FState", "4AUDITTED");
/* <-MISALIGNED-> */ /*1482*/                    builder.appendSql(" or ");
/* <-MISALIGNED-> */ /*1483*/                    builder.appendParam("conChange.FState", "8VISA");
/* <-MISALIGNED-> */ /*1484*/                    builder.appendSql(" or ");
/* <-MISALIGNED-> */ /*1485*/                    builder.appendParam("conChange.FState", "7ANNOUNCE");
/* <-MISALIGNED-> */ /*1486*/                    builder.appendSql(" ) ");
/* <-MISALIGNED-> */ /*1487*/                    builder.appendSql(" group by con.FID,con.FAmount,con.FOriginalAmount ");
                            IRowSet rs;
/* <-MISALIGNED-> */ /*1489*/                    try
                            {
                                String contractId;
                                BigDecimal amount;
                                BigDecimal amountOri;
                                BigDecimal changeAmt;
                                BigDecimal changeAmtOri;
/* <-MISALIGNED-> */ /*1489*/                        for(rs = builder.executeQuery(); rs.next(); updateChangeList.add(Arrays.asList(new Object[] {
/* <-MISALIGNED-> */ /*1496*/    changeAmt, changeAmtOri, FDCHelper.add(changeAmt, amount), FDCHelper.add(changeAmtOri, amountOri), contractId
})))
                                {
/* <-MISALIGNED-> */ /*1491*/                            contractId = rs.getString("contractId");
/* <-MISALIGNED-> */ /*1492*/                            amount = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1493*/                            amountOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /*1494*/                            changeAmt = rs.getBigDecimal("changeAmt");
/* <-MISALIGNED-> */ /*1495*/                            changeAmtOri = rs.getBigDecimal("changeAmtOri");
                                }








/*1521*/                        builder.executeBatch(updateChangeSql, updateChangeList);
                            }/*1522*/                    catch(SQLException e)
                            {/*1523*/                        e.printStackTrace();
                            }





/*1530*/                    builder.clear();
/*1531*/                    String updateSettleSql = " update T_CON_ContractExecInfos set FCostAmt = ? ,FCostAmtOri = ?, FSettleAmt = ? , FSettleAmtOri =? , FHasSettled = ? , FCompleteAmt = ? ,FNotCompletedAmt = ? ,FDeductedAmt = ? , FDeductedAmtOri = ? , FCompensatedAmt = ?, FCompensatedAmtOri = ?, FGuerdonAmt = ?, FGuerdonAmtOri = ? ,FInvoicedAmt = ? , FPaidAmt = ? ,FPaidAmtOri = ?,FpartAMatlAmt=?, FpartAMatLoaAmt=? where FContractBillId = ? ";



/*1535*/                    List updateSettleList = new ArrayList();

/*1537*/                    Map processAmtMap = getProcessAmt(ctx, newConIds);

/*1539*/                    Map hasPaySettleMap = hasPaySettle(ctx, newConIds, true);

/*1541*/                    Map changeAmtMap = getChangeAmt(ctx, newConIds);

/*1543*/                    Map settleMap = getSettleAmtOri(ctx, newConIds);

/*1545*/                    Map ortherMap = getOrtherAmt(ctx, newConIds);

/*1547*/                    Map paidMap = getPaidAmt(ctx, newConIds);
/*1548*/                    builder.appendSql("select FID,FHasSettled,FSettleAmt,FAmount,FOriginalAmount from T_CON_ContractBill where ");
/*1549*/                    builder.appendParam("fid", newConIds.toArray());


/*1552*/                    rs = builder.executeQuery();



/*1556*/                    try
                            {
/* <-MISALIGNED-> */ /*1556*/                        while(rs.next()) 
                                {
/* <-MISALIGNED-> */ /*1556*/                            String id = rs.getString("FID");
/* <-MISALIGNED-> */ /*1557*/                            boolean hasSettled = rs.getBoolean("FHasSettled");
/* <-MISALIGNED-> */ /*1558*/                            boolean hasPaySettle = false;
/* <-MISALIGNED-> */ /*1559*/                            if(hasPaySettleMap != null && hasPaySettleMap.containsKey(id))
/* <-MISALIGNED-> */ /*1560*/                                hasPaySettle = ((Boolean)hasPaySettleMap.get(id)).booleanValue();
/* <-MISALIGNED-> */ /*1562*/                            Integer hasSett = new Integer(0);
/* <-MISALIGNED-> */ /*1564*/                            BigDecimal settleAmt = rs.getBigDecimal("FSettleAmt");/*1566*/                            BigDecimal settleAmtOri = FDCHelper.ZERO;

/*1568*/                            BigDecimal processAmt = FDCHelper.ZERO;

/*1570*/                            BigDecimal completeAmt = FDCHelper.ZERO;

/*1572*/                            BigDecimal notCompleteAmt = FDCHelper.ZERO;

/*1574*/                            BigDecimal costAmt = FDCHelper.ZERO;

/*1576*/                            BigDecimal costAmtOri = FDCHelper.ZERO;

/*1578*/                            BigDecimal deductedAmt = FDCHelper.ZERO;

/*1580*/                            BigDecimal deductedAmtOri = FDCHelper.ZERO;

/*1582*/                            BigDecimal compensatedAmt = FDCHelper.ZERO;

/*1584*/                            BigDecimal compensatedAmtOri = FDCHelper.ZERO;

/*1586*/                            BigDecimal guerdonAmt = FDCHelper.ZERO;

/*1588*/                            BigDecimal guerdonAmtOri = FDCHelper.ZERO;

/*1590*/                            BigDecimal invoicedAmt = FDCHelper.ZERO;

/*1592*/                            BigDecimal paidAmt = FDCHelper.ZERO;

/*1594*/                            BigDecimal paidAmtOri = FDCHelper.ZERO;

/*1596*/                            BigDecimal partAMatlAmt = FDCHelper.ZERO;

/*1598*/                            BigDecimal partAMatLoaAmt = FDCHelper.ZERO;
/*1599*/                            if(settleMap != null && settleMap.containsKey(id))
/*1600*/                                settleAmtOri = (BigDecimal)settleMap.get((new StringBuilder(String.valueOf(id))).append("ori").toString());

/*1602*/                            if(ortherMap != null)
                                    {/*1603*/                                deductedAmt = (BigDecimal)ortherMap.get((new StringBuilder(String.valueOf(id))).append("deductedAmt").toString());
/*1604*/                                deductedAmtOri = (BigDecimal)ortherMap.get((new StringBuilder(String.valueOf(id))).append("deductedAmtOri").toString());
/*1605*/                                compensatedAmt = (BigDecimal)ortherMap.get((new StringBuilder(String.valueOf(id))).append("compensatedAmt").toString());
/*1606*/                                compensatedAmtOri = (BigDecimal)ortherMap.get((new StringBuilder(String.valueOf(id))).append("compensatedAmtOri").toString());
/*1607*/                                guerdonAmt = (BigDecimal)ortherMap.get((new StringBuilder(String.valueOf(id))).append("guerdonAmt").toString());
/*1608*/                                guerdonAmtOri = (BigDecimal)ortherMap.get((new StringBuilder(String.valueOf(id))).append("guerdonAmtOri").toString());
/*1609*/                                invoicedAmt = (BigDecimal)ortherMap.get((new StringBuilder(String.valueOf(id))).append("invoicedAmt").toString());
/*1610*/                                partAMatlAmt = (BigDecimal)ortherMap.get((new StringBuilder(String.valueOf(id))).append("partAMatlAmt").toString());
/*1611*/                                partAMatLoaAmt = (BigDecimal)ortherMap.get((new StringBuilder(String.valueOf(id))).append("partAMatLoaAmt").toString());
                                    }
/*1613*/                            if(paidMap != null && paidMap.containsKey(id))
                                    {/*1614*/                                paidAmt = (BigDecimal)paidMap.get(id);
/*1615*/                                paidAmtOri = (BigDecimal)paidMap.get((new StringBuilder(String.valueOf(id))).append("ori").toString());
                                    }
/*1617*/                            if(processAmtMap != null && processAmtMap.containsKey(id))
/*1618*/                                processAmt = (BigDecimal)processAmtMap.get(id);


/*1621*/                            if(hasSettled)
                                    {/*1622*/                                hasSett = new Integer(1);

/*1624*/                                if(hasPaySettle)
                                        {/*1625*/                                    costAmt = settleAmt;
/*1626*/                                    costAmtOri = settleAmtOri;
/*1627*/                                    completeAmt = settleAmt;
/*1628*/                                    notCompleteAmt = FDCHelper.ZERO;
                                        } else
                                        {

/*1632*/                                    costAmt = settleAmt;
/*1633*/                                    costAmtOri = settleAmtOri;
/*1634*/                                    completeAmt = processAmt;
/*1635*/                                    notCompleteAmt = FDCHelper.subtract(settleAmt, processAmt);
                                        }
                                    } else
                                    {

/*1640*/                                BigDecimal amount = rs.getBigDecimal("FAmount");
/*1641*/                                BigDecimal amountOri = rs.getBigDecimal("FOriginalAmount");
/*1642*/                                BigDecimal changeAmt = FDCHelper.ZERO;
/*1643*/                                BigDecimal changeAmtOri = FDCHelper.ZERO;
/*1644*/                                if(changeAmtMap != null && changeAmtMap.containsKey(id))
                                        {/*1645*/                                    changeAmt = (BigDecimal)changeAmtMap.get(id);
/*1646*/                                    changeAmtOri = (BigDecimal)changeAmtMap.get((new StringBuilder(String.valueOf(id))).append("ori").toString());
                                        }
/*1648*/                                costAmt = FDCHelper.add(amount, changeAmt);
/*1649*/                                costAmtOri = FDCHelper.add(amountOri, changeAmtOri);
/*1650*/                                settleAmt = FDCHelper.ZERO;
/*1651*/                                completeAmt = processAmt;
/*1652*/                                notCompleteAmt = FDCHelper.subtract(costAmt, processAmt);
                                    }
/*1654*/                            updateSettleList.add(Arrays.asList(new Object[] {/*1654*/                                costAmt, costAmtOri, settleAmt, settleAmtOri, hasSett, completeAmt, notCompleteAmt, deductedAmt, deductedAmtOri, compensatedAmt, 
/*1655*/                                compensatedAmtOri, guerdonAmt, guerdonAmtOri, invoicedAmt, paidAmt, paidAmtOri, partAMatlAmt, partAMatLoaAmt, id
                                    }));
                                }
/*1658*/                        builder.executeBatch(updateSettleSql, updateSettleList);
                            }/*1659*/                    catch(SQLException e)
                            {/*1660*/                        e.printStackTrace();
                            }
                        }
                    }
                }






/*1671*/        synOldNoTextContract(ctx);
            }
            protected BigDecimal getSettleAmtOri(Context ctx, String contractId)
                throws BOSException, EASBizException
            {






/*1682*/        BigDecimal settlePriceOri = FDCHelper.ZERO;
/*1683*/        if(contractId == null || "".equals(contractId))
/*1684*/            return settlePriceOri;
/*1685*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/*1686*/        builder.appendSql(" select fsettleprice,foriginalamount from t_con_contractsettlementbill where fissettled = 1 and ");
/*1687*/        builder.appendParam("fcontractbillid", contractId);
/*1688*/        IRowSet rs = builder.executeQuery();


/*1691*/        try
                {
/* <-MISALIGNED-> */ /*1691*/            while(rs.next()) 
/* <-MISALIGNED-> */ /*1691*/                settlePriceOri = rs.getBigDecimal("foriginalamount");
                }
/* <-MISALIGNED-> */ /*1693*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1694*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1695*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /*1697*/        return settlePriceOri;
            }
            protected Map getSettleAmtOri(Context ctx, Set conIds)
                throws BOSException, EASBizException
            {



/*1709*/        Map settleAmtOriMap = new HashMap();
/*1710*/        if(conIds == null || conIds.size() <= 0)
/*1711*/            return settleAmtOriMap;
/*1712*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/*1713*/        builder.appendSql(" select fcontractbillid, fsettleprice,foriginalamount from t_con_contractsettlementbill where fissettled = 1 and ");
/*1714*/        builder.appendParam("fcontractbillid", conIds.toArray());
/*1715*/        IRowSet rs = builder.executeQuery();


/*1718*/        try
                {
/* <-MISALIGNED-> */ /*1718*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1718*/                String contractId = rs.getString("FContractBillID");
/* <-MISALIGNED-> */ /*1719*/                BigDecimal settleAmt = rs.getBigDecimal("fsettleprice");
/* <-MISALIGNED-> */ /*1720*/                BigDecimal settleAmtOri = rs.getBigDecimal("foriginalamount");
/* <-MISALIGNED-> */ /*1721*/                settleAmtOriMap.put(contractId, settleAmt);
/* <-MISALIGNED-> */ /*1722*/                settleAmtOriMap.put((new StringBuilder(String.valueOf(contractId))).append("ori").toString(), settleAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /*1724*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1725*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1726*/            logger.error(e.getMessage());
                }
/* <-MISALIGNED-> */ /*1728*/        return settleAmtOriMap;
            }
            private boolean isContract(String billID)
            {
/*1738*/        boolean isContract = false;
/*1739*/        BOSObjectType conType = BOSUuid.read(billID).getType();
/*1740*/        if(conType.equals(new BOSObjectType("0D6DD1F4")))
/*1741*/            isContract = true;
/*1742*/        else/*1742*/        if(conType.equals(new BOSObjectType("3D9A5388")))
/*1743*/            isContract = false;
/*1744*/        return isContract;
            }
            protected void _updateSuppliedContract(Context ctx, String type, String contractId)
                throws BOSException, EASBizException
            {






/*1755*/        if(contractId == null)
                {
/* <-MISALIGNED-> */ /*1755*/            return;
                } else
                {
/* <-MISALIGNED-> */ /*1756*/            FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* <-MISALIGNED-> */ /*1757*/            builder.appendSql("update T_CON_ContractExecInfos set (FCostAmt,FCostAmtOri,Fnotcompletedamt)=(select c.famount,c.fOriginalAmount,c.famount-i.fcompleteamt from t_con_contractbill c,t_con_contractexecinfos i  where c.fid=? and c.fid=i.fcontractbillid)  where FContractBillId=?");/*1761*/            builder.addParam(contractId);
/*1762*/            builder.addParam(contractId);
/*1763*/            builder.executeUpdate(ctx);
/*1764*/            return;
                }
            }
            private void synOldNoTextContract(Context ctx)
            {
/* <-MISALIGNED-> */ /*1766*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* <-MISALIGNED-> */ /*1768*/        List newNoTextConIds = new ArrayList();/*1770*/        builder.appendSql(" select fid, fcurprojectid ,famount,foriginalAmount, FControlUnitID,FOrgUnitID,FPeriodID,FInvoiceAmt from T_CON_ContractWithoutText where ");
/*1771*/        builder.appendParam("FState", "4AUDITTED");
/*1772*/        builder.appendSql(" and fid not in ( select FConWithoutTextID from T_CON_ContractExecInfos ");

/*1774*/        builder.appendSql(" where FConWithoutTextID is not null)");


/*1777*/        String insertSQL = "insert into T_Con_ContractExecInfos (FID,FConWithoutTextID,FCurProjectid,FAmount,FOriginalAmount,FCostAmt ,FCostAmtOri , FControlUnitID,FOrgUnitID,FPeriodID,FisNoText,FCompleteAmt,FInvoicedAmt ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";


/*1780*/        List insertList = new ArrayList();

/*1782*/        try
                {
                    String id;
                    String contractId;
                    String curProjectId;
                    BigDecimal costAmt;
                    BigDecimal costAmtOri;
                    BigDecimal invoiceAmt;
                    String controlUnitId;
                    String orgUnitId;
                    String periodId;
/* <-MISALIGNED-> */ /*1782*/            for(IRowSet rs = builder.executeQuery(); rs.next(); insertList.add(Arrays.asList(new Object[] {

/*1795*/    id, contractId, curProjectId, costAmt, costAmtOri, costAmt, costAmtOri, controlUnitId, orgUnitId, periodId, 
/*1796*/    new Integer(1), costAmt, invoiceAmt
})))
                    {
/* <-MISALIGNED-> */ /*1784*/                ContractExecInfosInfo info = new ContractExecInfosInfo();
/* <-MISALIGNED-> */ /*1785*/                id = BOSUuid.create(info.getBOSType()).toString();
/* <-MISALIGNED-> */ /*1786*/                contractId = rs.getString("fid");
/* <-MISALIGNED-> */ /*1787*/                newNoTextConIds.add(contractId);
/* <-MISALIGNED-> */ /*1788*/                curProjectId = rs.getString("fcurprojectid").toString();
/* <-MISALIGNED-> */ /*1789*/                costAmt = rs.getBigDecimal("famount");
/* <-MISALIGNED-> */ /*1790*/                costAmtOri = rs.getBigDecimal("foriginalAmount");
/* <-MISALIGNED-> */ /*1791*/                invoiceAmt = rs.getBigDecimal("FInvoiceAmt");
/* <-MISALIGNED-> */ /*1792*/                controlUnitId = rs.getString("FControlUnitID");
/* <-MISALIGNED-> */ /*1793*/                orgUnitId = rs.getString("FOrgUnitID");
/* <-MISALIGNED-> */ /*1794*/                periodId = rs.getString("FPeriodID");
                    }
/* <-MISALIGNED-> */ /*1798*/            builder.executeBatch(insertSQL, insertList);
/* <-MISALIGNED-> */ /*1800*/            builder.clear();
/* <-MISALIGNED-> */ /*1804*/            String updateSQL = "update t_con_contractexecinfos set FPaidAmt=?,FPaidAmtOri=? where FConWithoutTextID=?";
/* <-MISALIGNED-> */ /*1805*/            List updatePaiedList = new ArrayList();
/* <-MISALIGNED-> */ /*1806*/            if(newNoTextConIds != null && newNoTextConIds.size() > 0)
                    {
/* <-MISALIGNED-> */ /*1807*/                for(int i = 0; i < newNoTextConIds.size(); i++)
                        {
/* <-MISALIGNED-> */ /*1808*/                    String noTextContractId = newNoTextConIds.get(i).toString();
/* <-MISALIGNED-> */ /*1810*/                    builder.clear();
/* <-MISALIGNED-> */ /*1811*/                    builder.appendSql("select fcontractbillid,famount,flocalamount from t_cas_paymentbill where fbillstatus=? and fcontractbillid=?");
/* <-MISALIGNED-> */ /*1812*/                    builder.addParam(new Integer(15));
/* <-MISALIGNED-> */ /*1813*/                    builder.addParam(noTextContractId);
                            BigDecimal paiedAmt;
                            BigDecimal paiedOriAmt;
/* <-MISALIGNED-> */ /*1814*/                    for(IRowSet rs1 = builder.executeQuery(); rs1.next(); updatePaiedList.add(Arrays.asList(new Object[] {
/* <-MISALIGNED-> */ /*1819*/    paiedAmt, paiedOriAmt, noTextContractId
})))
                            {
/* <-MISALIGNED-> */ /*1816*/                        noTextContractId = rs1.getString("fcontractbillid");
/* <-MISALIGNED-> */ /*1817*/                        paiedAmt = rs1.getBigDecimal("famount");
/* <-MISALIGNED-> */ /*1818*/                        paiedOriAmt = rs1.getBigDecimal("flocalamount");
                            }
                        }
/* <-MISALIGNED-> */ /*1822*/                builder.executeBatch(updateSQL, updatePaiedList);
                    }
                }
/* <-MISALIGNED-> */ /*1824*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1825*/            e.printStackTrace();
                }
/* <-MISALIGNED-> */ /*1826*/        catch(BOSException e)
                {
/* <-MISALIGNED-> */ /*1827*/            e.printStackTrace();
                }
            }
            private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractExecInfosControllerBean");
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_contract_server.jar
	Total time: 260 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/