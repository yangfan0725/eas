/*jadclipse*/package com.kingdee.eas.fi.gl.app;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.permission.PermissionException;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.*;
import com.kingdee.eas.basedata.master.account.*;
import com.kingdee.eas.basedata.master.auxacct.*;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.gl.*;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.LowTimer;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import javax.sql.RowSet;
import org.apache.log4j.Logger;
public class WSVoucherControllerBean extends AbstractWSVoucherControllerBean
{
            public WSVoucherControllerBean()
            {
            }
            private VoucherInfo contructVoucherHeader(Context ctx, WSVoucherInfo wsVoucher, CompanyOrgUnitInfo companyOrgUnitInfo, PeriodInfo period, VoucherTypeInfo voucherTypeInfo, CurrencyInfo currencyInfo)
                throws EASBizException, BOSException
            {



































































































/* 136*/        VoucherInfo voucherInfo = new VoucherInfo();


/* 139*/        voucherInfo.setSourceSys(SystemEnum.BASICSYSTEM);
/* 140*/        voucherInfo.setSourceType(SourceType.NONE);
/* 141*/        voucherInfo.setBizStatus(VoucherStatusEnum.TEMP);

/* 143*/        voucherInfo.setAttachments(wsVoucher.getAttaches());
/* 144*/        voucherInfo.setDescription(wsVoucher.getDescription());
/* 145*/        voucherInfo.setVoucherAbstract(wsVoucher.getVoucherAbstract());

/* 147*/        voucherInfo.setBookedDate(wsVoucher.getBookedDate());
/* 148*/        voucherInfo.setBizDate(wsVoucher.getBizDate());

/* 150*/        voucherInfo.setIsCheck(wsVoucher.isIsCheck());

/* 152*/        if(wsVoucher.getCompanyNumber() == null)
/* 153*/            wsVoucher.setCompanyNumber("");


/* 156*/        voucherInfo.setCompany(companyOrgUnitInfo);

/* 158*/        ctx.put(OrgType.Company, companyOrgUnitInfo);
/* 159*/        voucherInfo.setPeriod(period);

/* 161*/        voucherInfo.setVoucherType(voucherTypeInfo);
/* 162*/        voucherInfo.setCurrency(currencyInfo);

/* 164*/        return voucherInfo;
            }
            private VoucherEntryInfo contructVoucherEntry(Context ctx, WSVoucherInfo wsVoucher, VoucherInfo voucher, AccountViewInfo accountViewInfo, CurrencyInfo currencyInfo, IObjectCollection cashflowCols, boolean sSettleByLocal, 
                    com.kingdee.eas.fi.gl.VoucherInfo.ExchangeRateWithValue localToRpt)
                throws BOSException
            {















/* 185*/        VoucherEntryInfo entry = new VoucherEntryInfo();

/* 187*/        entry.setIsHand(true);
/* 188*/        String vouchernumber = wsVoucher.getVoucherNumber();
/* 189*/        int iSeq = wsVoucher.getEntrySeq();

/* 191*/        if(wsVoucher.getLocalRate() == 0.0D)
/* 192*/            wsVoucher.setLocalRate(1.0D);



/* 196*/        BigDecimal exchangeRate = new BigDecimal(String.valueOf(wsVoucher.getLocalRate()));
/* 197*/        exchangeRate = exchangeRate.setScale(10, 4);


/* 200*/        BigDecimal reportchangeRate = exchangeRate;
/* 201*/        com.kingdee.eas.fi.gl.VoucherInfo.ExchangeRateWithValue reportRate = null;
/* 202*/        if(sSettleByLocal)
/* 203*/            reportchangeRate = localToRpt.getValue();

/* 205*/        else/* 205*/        if(voucher.getCompany().getReportCurrency() != null && !currencyInfo.getId().toString().equals(voucher.getCompany().getReportCurrency().getId().toString()))
                {
/* 207*/            reportRate = getExChangeRate(ctx, voucher.getCompany(), currencyInfo, voucher.getPeriod());
/* 208*/            reportchangeRate = reportRate.getValue();
                }


/* 212*/        entry.setSeq(iSeq);
/* 213*/        entry.setBill(voucher);
/* 214*/        entry.setDescription(wsVoucher.getVoucherAbstract());

/* 216*/        entry.setAccount(accountViewInfo);
/* 217*/        entry.setCurrency(currencyInfo);


/* 220*/        entry.setLocalExchangeRate(exchangeRate);
/* 221*/        entry.setReportingExchangeRate(reportchangeRate);


/* 224*/        BigDecimal orignal = new BigDecimal(String.valueOf(wsVoucher.getOriginalAmount()));
/* 225*/        orignal = orignal.setScale(currencyInfo.getPrecision(), 4);
/* 226*/        entry.setOriginalAmount(orignal);


/* 229*/        if(wsVoucher.getEntryDC() == 1)
                {/* 230*/            entry.setEntryDC(EntryDC.DEBIT);
/* 231*/            BigDecimal local = new BigDecimal(String.valueOf(wsVoucher.getDebitAmount()));
/* 232*/            local = local.setScale(voucher.getCompany().getBaseCurrency().getPrecision(), 4);
/* 233*/            entry.setLocalAmount(local);

/* 235*/            if(sSettleByLocal)
                    {/* 236*/                if(localToRpt.getConvertMode() == ConvertModeEnum.DIRECTEXCHANGERATE)
/* 237*/                    entry.setReportingAmount(entry.getLocalAmount().multiply(reportchangeRate));

/* 239*/                else/* 239*/                    entry.setReportingAmount(entry.getLocalAmount().divide(reportchangeRate, 6));
                    } else

/* 242*/            if(reportRate == null || reportRate.getConvertMode() == ConvertModeEnum.DIRECTEXCHANGERATE)
/* 243*/                entry.setReportingAmount(orignal.multiply(reportchangeRate));

/* 245*/            else/* 245*/                entry.setReportingAmount(orignal.divide(reportchangeRate));
                } else
                {

/* 249*/            entry.setEntryDC(EntryDC.CREDIT);
/* 250*/            BigDecimal local = new BigDecimal(String.valueOf(wsVoucher.getCreditAmount()));
/* 251*/            local = local.setScale(voucher.getCompany().getBaseCurrency().getPrecision(), 4);
/* 252*/            entry.setLocalAmount(local);

/* 254*/            if(sSettleByLocal)
                    {/* 255*/                if(localToRpt.getConvertMode() == ConvertModeEnum.DIRECTEXCHANGERATE)
/* 256*/                    entry.setReportingAmount(entry.getLocalAmount().multiply(reportchangeRate));

/* 258*/                else/* 258*/                    entry.setReportingAmount(entry.getLocalAmount().divide(reportchangeRate, 6));
                    } else

/* 261*/            if(reportRate == null || reportRate.getConvertMode() == ConvertModeEnum.DIRECTEXCHANGERATE)
/* 262*/                entry.setReportingAmount(orignal.multiply(reportchangeRate));

/* 264*/            else/* 264*/                entry.setReportingAmount(orignal.divide(reportchangeRate));
                }



/* 269*/        if(accountViewInfo.isIsQty())
                {/* 270*/            BigDecimal price = new BigDecimal(String.valueOf(wsVoucher.getPrice()));
/* 271*/            entry.setPrice(price);
/* 272*/            String measurement = wsVoucher.getMeasurement();

/* 274*/            if(measurement != null && !StringUtils.isEmpty(measurement))
                    {/* 275*/                String measureUnitGroupID = null;

/* 277*/                if(accountViewInfo.getMeasureUnitGroupID() != null)
/* 278*/                    measureUnitGroupID = accountViewInfo.getMeasureUnitGroupID().getId().toString();


/* 281*/                MeasureUnitInfo measureUnitInfo = GlWebServiceUtil.findMeasureUnitByName(ctx, measureUnitGroupID, measurement);

/* 283*/                if(measureUnitInfo != null)
/* 284*/                    entry.setMeasureUnit(measureUnitInfo);

/* 286*/                else/* 286*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "916_WSVoucherControllerBean", ctx.getLocale())).append(measurement).toString());


/* 289*/                entry.setQuantity(new BigDecimal(String.valueOf(wsVoucher.getQty())));
                    } else
                    {/* 291*/                logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "917_WSVoucherControllerBean", ctx.getLocale())).append(measurement).toString());
                    }
                }


/* 296*/        entry.setCussent(CussentStatusEnum.getEnum(String.valueOf(wsVoucher.getCussent())));
/* 297*/        entry.setOprStatus(1);

/* 299*/        if(cashflowCols != null && cashflowCols.size() > 0)
                {
/* 301*/            int ca = 0;/* 301*/            for(int n = cashflowCols.size(); ca < n; ca++)
                    {/* 302*/                CashflowRecordImpInfo key = (CashflowRecordImpInfo)cashflowCols.getObject(ca);
/* 303*/                String vnumber = key.getVouchernumber();
/* 304*/                String seqnumber = key.getSeq();
/* 305*/                String oppseqnumber = key.getOppseq();
/* 306*/                if(vouchernumber.equalsIgnoreCase(vnumber) && seqnumber.equalsIgnoreCase(String.valueOf(iSeq)))
                        {
/* 308*/                    key.getRecord().setEntry(entry);
/* 309*/                    key.getRecord().setAccount(entry.getAccount());
/* 310*/                    key.getRecord().setEntryDC(entry.getEntryDC());
/* 311*/                    key.getRecord().setCurrency(entry.getCurrency());

/* 313*/                    int pc = 1;
/* 314*/                    if(key.getRecord().getPrimaryItem() != null)
                            {/* 315*/                        CashFlowDirection direct = key.getRecord().getPrimaryItem().getDirection();
/* 316*/                        if(EntryDC.DEBIT.equals(entry.getEntryDC()) && CashFlowDirection.OUT.equals(direct) || EntryDC.CREDIT.equals(entry.getEntryDC()) && CashFlowDirection.IN.equals(direct))
/* 317*/                            pc = -1;
                            }

/* 320*/                    key.getRecord().setPrimaryCoefficient(pc);
                        }
/* 322*/                if(vouchernumber.equalsIgnoreCase(vnumber) && oppseqnumber.equalsIgnoreCase(String.valueOf(iSeq)))
                        {/* 323*/                    key.getRecord().setOpposingAccountEntry(entry);
/* 324*/                    key.getRecord().setOppAccount(entry.getAccount());
/* 325*/                    key.getRecord().setOppEntryDC(entry.getEntryDC());

/* 327*/                    if(entry.getAccount().getAccountTypeID().getProperty().getValue() == 4 && (key.getRecord().getPrimaryItem() == null || key.getRecord().getPrimaryItem().isIsDealActivity()))


/* 330*/                        key.getRecord().setSupplementaryItem(null);
                        }


/* 334*/                CashflowRecordInfo record = key.getRecord();
/* 335*/                if(sSettleByLocal)
/* 336*/                    record.setReportingAmount(record.getLocalAmount().multiply(localToRpt.getValue()));

/* 338*/                else/* 338*/                    record.setReportingAmount(record.getOriginalAmount().multiply(reportchangeRate));
                    }
                }


/* 343*/        return entry;
            }
            private String contructVoucherAsstRecord(Context ctx, WSVoucherInfo wsVoucher, AsstAccountInfo fcaa, VoucherEntryInfo entry, VoucherAssistRecordInfo assitInfo, boolean isVerify, boolean sSettleByLocal, 
                    com.kingdee.eas.fi.gl.VoucherInfo.ExchangeRateWithValue localToRpt, PeriodInfo period)
                throws EASBizException, BOSException
            {
















/* 365*/        String errorNumber = null;

/* 367*/        assitInfo.setDescription(wsVoucher.getVoucherAbstract());
/* 368*/        if(wsVoucher.getAssistBizDate() != null)
/* 369*/            assitInfo.setBizDate(wsVoucher.getAssistBizDate());

/* 371*/        else/* 371*/            assitInfo.setBizDate(wsVoucher.getBizDate());

/* 373*/        if(wsVoucher.getAssistEndDate() != null)
/* 374*/            assitInfo.setEndDate(wsVoucher.getAssistEndDate());

/* 376*/        else/* 376*/            assitInfo.setEndDate(wsVoucher.getBizDate());

/* 378*/        assitInfo.setIsFullProp(true);
/* 379*/        assitInfo.setSeq(wsVoucher.getAsstSeq());
/* 380*/        assitInfo.setBill(entry.getBill());
/* 381*/        assitInfo.setBizNumber(wsVoucher.getBizNumber());


/* 384*/        BigDecimal orignal = new BigDecimal(String.valueOf(wsVoucher.getOriginalAmount()));
/* 385*/        orignal = orignal.setScale(entry.getCurrency().getPrecision(), 4);
/* 386*/        assitInfo.setOriginalAmount(orignal);


/* 389*/        if(wsVoucher.getEntryDC() == 1)
                {/* 390*/            BigDecimal local = new BigDecimal(String.valueOf(wsVoucher.getDebitAmount()));
/* 391*/            local = local.setScale(entry.getBill().getCompany().getBaseCurrency().getPrecision(), 4);
/* 392*/            assitInfo.setLocalAmount(local);
                } else
                {/* 394*/            BigDecimal local = new BigDecimal(String.valueOf(wsVoucher.getCreditAmount()));
/* 395*/            local = local.setScale(entry.getBill().getCompany().getBaseCurrency().getPrecision(), 4);
/* 396*/            assitInfo.setLocalAmount(local);
                }

/* 399*/        if(sSettleByLocal)
                {/* 400*/            if(localToRpt.getConvertMode() == ConvertModeEnum.DIRECTEXCHANGERATE)
/* 401*/                assitInfo.setReportingAmount(assitInfo.getLocalAmount().multiply(localToRpt.getValue()));

/* 403*/            else/* 403*/                assitInfo.setReportingAmount(assitInfo.getLocalAmount().divide(localToRpt.getValue(), 6));
                } else
                {
/* 406*/            CompanyOrgUnitInfo companyOrgUnitInfo = (CompanyOrgUnitInfo)ctx.get(OrgType.Company);
/* 407*/            if(companyOrgUnitInfo.getReportCurrency() != null && !entry.getCurrency().getId().toString().equals(companyOrgUnitInfo.getReportCurrency().getId().toString()))
                    {/* 408*/                com.kingdee.eas.fi.gl.VoucherInfo.ExchangeRateWithValue orginalToRpt = getExChangeRate(ctx, companyOrgUnitInfo, entry.getCurrency(), period);
/* 409*/                if(orginalToRpt.getConvertMode() == ConvertModeEnum.DIRECTEXCHANGERATE)
/* 410*/                    assitInfo.setReportingAmount(orignal.multiply(entry.getReportingExchangeRate()));

/* 412*/                else/* 412*/                    assitInfo.setReportingAmount(orignal.divide(entry.getReportingExchangeRate(), 6));
                    }
                }




/* 419*/        if(entry.getAccount() != null && entry.getAccount().isIsQty())
/* 420*/            if(fcaa.isIsQty())
                    {/* 421*/                BigDecimal price = new BigDecimal(String.valueOf(wsVoucher.getPrice()));
/* 422*/                assitInfo.setPrice(price);
/* 423*/                String measurement = wsVoucher.getMeasurement();

/* 425*/                if(measurement != null && !StringUtils.isEmpty(measurement))
                        {/* 426*/                    String measureUnitGroupID = null;

/* 428*/                    if(fcaa.getMeasureUnitGroup() != null)
/* 429*/                        measureUnitGroupID = fcaa.getMeasureUnitGroup().getId().toString();


/* 432*/                    MeasureUnitInfo measureUnitInfo = GlWebServiceUtil.findMeasureUnitByName(ctx, measureUnitGroupID, measurement);

/* 434*/                    if(measureUnitInfo != null)
                            {/* 435*/                        assitInfo.setMeasureUnit(measureUnitInfo);
/* 436*/                        assitInfo.setQuantity(new BigDecimal(String.valueOf(wsVoucher.getQty())));
/* 437*/                        assitInfo.setStandardQuantity(assitInfo.getQuantity().multiply(measureUnitInfo.getCoefficient()));
                            } else
                            {/* 439*/                        logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "918_WSVoucherControllerBean", ctx.getLocale())).append(measurement).toString());
                            }

/* 442*/                    assitInfo.setQuantity(new BigDecimal(String.valueOf(wsVoucher.getQty())));
                        } else
                        {/* 444*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "919_WSVoucherControllerBean", ctx.getLocale())).append(measurement).toString());
                        }
                    } else
                    {/* 447*/                assitInfo.setQuantity(new BigDecimal(String.valueOf(wsVoucher.getQty())));
                    }



/* 452*/        String strSettleCode = wsVoucher.getSettlementType();

/* 454*/        if(strSettleCode != null)
                {/* 455*/            com.kingdee.eas.basedata.assistant.SettlementTypeInfo settlementTypeInfo = GlWebServiceUtil.findSettelmentTypeByName(ctx, strSettleCode);
/* 456*/            assitInfo.setSettlementType(settlementTypeInfo);
                }

/* 459*/        assitInfo.setSettlementCode(wsVoucher.getSettlementNumber());


/* 462*/        String asstactTypeName[] = getAsstactArray(wsVoucher, 1);
/* 463*/        String asstactTypeNumber[] = getAsstactArray(wsVoucher, 2);
/* 464*/        String asstactType[] = getAsstactArray(wsVoucher, 3);

/* 466*/        AssistantHGInfo assistantHGInfo = new AssistantHGInfo();
/* 467*/        AsstActTypeCollection arrayAsstActTypeInfo = new AsstActTypeCollection();
/* 468*/        int count = fcaa.getCount();

/* 470*/        for(int i = 0; i < count; i++)
                {/* 471*/            boolean hasResult = false;



/* 475*/            try
                    {
/* <-MISALIGNED-> */ /* 475*/                hasResult = constructAssistInfo(ctx, assistantHGInfo, asstactType[i], asstactTypeNumber[i], asstactTypeName[i], arrayAsstActTypeInfo, OrgConstants.DEF_CU_ID, entry.getBill().getCompany(), entry.getAccount(), entry.getCurrency());
                    }/* 478*/            catch(VoucherException e)
                    {/* 479*/                errorNumber = (new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "920_WSVoucherControllerBean", ctx.getLocale())).append(entry.getSeq()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "921_WSVoucherControllerBean", ctx.getLocale())).append(assitInfo.getSeq()).append(e.getMessage()).toString();
/* 480*/                logger.info(errorNumber);
/* 481*/                return errorNumber;
                    }

/* 484*/            if(!hasResult)
                    {/* 485*/                errorNumber = (new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "920_WSVoucherControllerBean", ctx.getLocale())).append(entry.getSeq()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "921_WSVoucherControllerBean", ctx.getLocale())).append(assitInfo.getSeq()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "922_WSVoucherControllerBean", ctx.getLocale())).append(asstactType[i]).append("(").append(asstactTypeNumber[i]).append(":").append(asstactTypeName[i]).append(")").toString();
/* 486*/                logger.info(errorNumber);
/* 487*/                return errorNumber;
                    }
                }

/* 491*/        IAssistantHG igCtrl = AssistantHGFactory.getLocalInstance(ctx);
/* 492*/        AssistantHGInfo actualAssistantHGInfo = igCtrl.getAssistantHG(assistantHGInfo, fcaa.getId().toString(), arrayAsstActTypeInfo);

/* 494*/        if(actualAssistantHGInfo == null)
/* 495*/            return null;

/* 497*/        assitInfo.setAssGrp(actualAssistantHGInfo);

/* 499*/        assitInfo.setInvoiceNumber(wsVoucher.getInvoiceNumber());


/* 502*/        if(entry.isIsVerify() && isVerify)
                {/* 503*/            AcctCussentInfo acctInfo = getAcctCussentInfo(ctx, assitInfo.getOriginalAmount(), actualAssistantHGInfo.getId().toString(), assitInfo.getBizNumber(), assitInfo.getInvoiceNumber());

/* 505*/            if(acctInfo == null)
                    {/* 506*/                errorNumber = (new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "920_WSVoucherControllerBean", ctx.getLocale())).append(entry.getSeq()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "921_WSVoucherControllerBean", ctx.getLocale())).append(assitInfo.getSeq()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "923_WSVoucherControllerBean", ctx.getLocale())).toString();
/* 507*/                logger.info(errorNumber);
/* 508*/                return errorNumber;
                    }

/* 511*/            assitInfo.setVerifiedCussent(acctInfo);
                }

/* 514*/        assitInfo.setIsFullProp(true);

/* 516*/        return errorNumber;
            }
            protected AcctCussentInfo getAcctCussentInfo(Context ctx, BigDecimal unHoldedAmtFor, String hgid, String bizNumber, String invoiceNumber)
                throws BOSException
            {












/* 533*/        AcctCussentInfo info = new AcctCussentInfo();


/* 536*/        try
                {
/* <-MISALIGNED-> */ /* 536*/            StringBuffer sql = new StringBuffer();
/* <-MISALIGNED-> */ /* 537*/            sql.append("select top 1 cust.FID,vast.FendDate").append("\n from t_gl_VoucherAssistRecord vast").append("\n inner join t_bd_assistanthg hg on vast.fassgrpid=hg.fid").append("\n inner join t_GL_AcctCussent cust on cust.FVchAssistRecordID = vast.fid").append("\n where hg.fid='").append(hgid).append("' \n  and cust.funholdedamtfor>=").append(unHoldedAmtFor).append("and vast.fbizNumber='").append(bizNumber).append("'").append(" order by vast.FbizDate");

/* 541*/            RowSet rs = DbUtil.executeQuery(ctx, sql.toString());
/* 542*/            if(rs.next())
                    {/* 543*/                info.setId(BOSUuid.read(rs.getString("fid")));
/* 544*/                info.setBizDate(rs.getDate("FendDate"));
                    }
                }
/* 547*/        catch(SQLException e)
                {/* 548*/            throw new BOSException(e);
                }

/* 551*/        return info;
            }
            protected boolean constructAssistInfo(Context ctx, AssistantHGInfo assistantHGInfo, String strAsstActTypeName, String strAsstActID, String strAsstName, AsstActTypeCollection arrayAsstActTypeInfo, String cuid, 
                    CompanyOrgUnitInfo infoCompany, AccountViewInfo accountViewInfo, CurrencyInfo currencyInfo)
                throws BOSException, EASBizException
            {
















/* 573*/        boolean hasResult = false;

/* 575*/        boolean isAssit = false;

/* 577*/        if(strAsstActTypeName != null && strAsstActTypeName.trim().length() > 0)
                {/* 578*/            isAssit = true;
/* 579*/            strAsstActTypeName = strAsstActTypeName.trim();
                }

/* 582*/        if(!StringUtil.isEmptyString(strAsstActID))
                {/* 583*/            isAssit = true;
/* 584*/            strAsstActID = strAsstActID.trim();
                }


/* 588*/        if(!StringUtil.isEmptyString(strAsstName))
                {/* 589*/            isAssit = true;
/* 590*/            strAsstName = strAsstName.trim();
                }

/* 593*/        if(isAssit)
                {
/* 595*/            AsstActTypeInfo asstActTypeInfo = GlWebServiceUtil.findAsstActtypeNumberByName(ctx, strAsstActTypeName);
/* 597*/            if(asstActTypeInfo != null)
                    {
/* 599*/                arrayAsstActTypeInfo.add(asstActTypeInfo);
/* 600*/                String asstHGAttribute = asstActTypeInfo.getAsstHGAttribute();
/* 601*/                com.kingdee.eas.framework.DataBaseInfo dataInfo = GlWebServiceUtil.findAsstActObject(ctx, asstHGAttribute, strAsstActID, strAsstName, cuid, infoCompany);

/* 603*/                String msg = "";
/* 604*/                if((dataInfo instanceof AccountBankInfo) && accountViewInfo != null)
                        {/* 605*/                    boolean accounterror = false;
/* 606*/                    boolean currenyerror = false;
/* 607*/                    boolean bankerror = false;
/* 608*/                    AccountBankInfo accountbank = (AccountBankInfo)dataInfo;




/* 613*/                    boolean isImp = false;

/* 615*/                    try
                            {
/* <-MISALIGNED-> */ /* 615*/                        isImp = Boolean.valueOf(GlUtils.getParamByKey(ctx, infoCompany.getId().toString(), "GL_0155").toString()).booleanValue();
                            }
/* <-MISALIGNED-> */ /* 616*/                    catch(EASBizException e)
                            {
/* <-MISALIGNED-> */ /* 617*/                        logger.error(e);
                            }
/* <-MISALIGNED-> */ /* 619*/                    if(accountViewInfo.isIsBank())
                            {
///* <-MISALIGNED-> */ /* 620*/                        if(accountbank.getAccount() == null || !accountViewInfo.getId().equals(accountbank.getAccount().getId()))
///* <-MISALIGNED-> */ /* 621*/                            accounterror = true;

                            } else
/* <-MISALIGNED-> */ /* 623*/                    if(accountViewInfo.isIsCashEquivalent() && isImp && (accountbank.getAccount() == null || !accountViewInfo.getId().equals(accountbank.getAccount().getId())))
/* <-MISALIGNED-> */ /* 626*/                        accounterror = true;
/* 630*/                    BankInfo bankinfo = assistantHGInfo.getRegion();
/* 631*/                    if(bankinfo != null && !accountbank.getBank().getId().equals(bankinfo.getId()))

/* 633*/                        bankerror = true;



/* 637*/                    if(currencyInfo != null && accountbank.isIsByCurrency() && !currencyInfo.getId().equals(accountbank.getCurrency().getId()))

/* 639*/                        currenyerror = true;



/* 643*/                    if(accounterror)
/* 644*/                        if(msg.equals(""))
/* 645*/                            msg = ResourceBase.getString("com.kingdee.eas.fi.gl.VoucherImpWizardUI", "bankaccount_notequals_accounview", ctx.getLocale());

/* 647*/                        else/* 647*/                            msg = (new StringBuilder()).append(", ").append(ResourceBase.getString("com.kingdee.eas.fi.gl.VoucherImpWizardUI", "bankaccount_notequals_accounview", ctx.getLocale())).toString();


/* 650*/                    if(currenyerror)
/* 651*/                        if(msg.equals(""))
/* 652*/                            msg = ResourceBase.getString("com.kingdee.eas.fi.gl.VoucherImpWizardUI", "bankaccount_notequals_currency", ctx.getLocale());

/* 654*/                        else/* 654*/                            msg = (new StringBuilder()).append(", ").append(ResourceBase.getString("com.kingdee.eas.fi.gl.VoucherImpWizardUI", "bankaccount_notequals_currency", ctx.getLocale())).toString();


/* 657*/                    if(bankerror)
/* 658*/                        if(msg.equals(""))
/* 659*/                            msg = ResourceBase.getString("com.kingdee.eas.fi.gl.VoucherImpWizardUI", "bankaccount_notequals_bank", ctx.getLocale());

/* 661*/                        else/* 661*/                            msg = (new StringBuilder()).append(", ").append(ResourceBase.getString("com.kingdee.eas.fi.gl.VoucherImpWizardUI", "bankaccount_notequals_bank", ctx.getLocale())).toString();
                        }


/* 665*/                if(dataInfo instanceof BankInfo)
                        {/* 666*/                    AccountBankInfo accountbank = assistantHGInfo.getBankAccount();
/* 667*/                    BankInfo bankinfo = (BankInfo)dataInfo;
/* 668*/                    if(accountbank != null && !accountbank.getBank().getId().equals(bankinfo.getId()))

/* 670*/                        if(msg.equals(""))
/* 671*/                            msg = ResourceBase.getString("com.kingdee.eas.fi.gl.VoucherImpWizardUI", "bankaccount_notequals_bank", ctx.getLocale());

/* 673*/                        else/* 673*/                            msg = (new StringBuilder()).append(", ").append(ResourceBase.getString("com.kingdee.eas.fi.gl.VoucherImpWizardUI", "bankaccount_notequals_bank", ctx.getLocale())).toString();
                        }




/* 679*/                if(!msg.equals(""))
/* 680*/                    throw new VoucherException(VoucherException.NO_MSG, new Object[] {/* 680*/                        msg
                            });

/* 683*/                if(dataInfo != null)
                        {/* 684*/                    assistantHGInfo.put(asstHGAttribute, dataInfo);
/* 685*/                    hasResult = true;
                        }
                    }
                }

/* 690*/        return hasResult;
            }
            private String[] getAsstactArray(WSVoucherInfo wsVoucher, int type)
            {

/* 695*/        String asstact[] = new String[8];
/* 696*/        switch(type)
                {
/* 698*/        case 1: /* 698*/            asstact[0] = wsVoucher.getAsstActName1();
/* 699*/            asstact[1] = wsVoucher.getAsstActName2();
/* 700*/            asstact[2] = wsVoucher.getAsstActName3();
/* 701*/            asstact[3] = wsVoucher.getAsstActName4();
/* 702*/            asstact[4] = wsVoucher.getAsstActName5();
/* 703*/            asstact[5] = wsVoucher.getAsstActName6();
/* 704*/            asstact[6] = wsVoucher.getAsstActName7();
/* 705*/            asstact[7] = wsVoucher.getAsstActName8();
                    break;

/* 708*/        case 2: /* 708*/            asstact[0] = wsVoucher.getAsstActNumber1();
/* 709*/            asstact[1] = wsVoucher.getAsstActNumber2();
/* 710*/            asstact[2] = wsVoucher.getAsstActNumber3();
/* 711*/            asstact[3] = wsVoucher.getAsstActNumber4();
/* 712*/            asstact[4] = wsVoucher.getAsstActNumber5();
/* 713*/            asstact[5] = wsVoucher.getAsstActNumber6();
/* 714*/            asstact[6] = wsVoucher.getAsstActNumber7();
/* 715*/            asstact[7] = wsVoucher.getAsstActNumber8();
                    break;

/* 718*/        case 3: /* 718*/            asstact[0] = wsVoucher.getAsstActType1();
/* 719*/            asstact[1] = wsVoucher.getAsstActType2();
/* 720*/            asstact[2] = wsVoucher.getAsstActType3();
/* 721*/            asstact[3] = wsVoucher.getAsstActType4();
/* 722*/            asstact[4] = wsVoucher.getAsstActType5();
/* 723*/            asstact[5] = wsVoucher.getAsstActType6();
/* 724*/            asstact[6] = wsVoucher.getAsstActType7();
/* 725*/            asstact[7] = wsVoucher.getAsstActType8();
                    break;
                }

/* 729*/        return asstact;
            }
            private String checkImpRepeated(Context ctx, String desdription, String comId, String vouchertypeid, String periodid, boolean importParam)
                throws BOSException, EASBizException
            {














/* 748*/        if(importParam)
                {/* 749*/            StringBuffer sql = new StringBuffer("");
/* 750*/            sql.append("SELECT fnumber from t_gl_voucher where fcompanyid=? and fdescription=?");



/* 754*/            try
                    {
/* <-MISALIGNED-> */ /* 754*/                IRowSet rs = DbUtil.executeQuery(ctx, sql.toString(), new Object[] {
/* <-MISALIGNED-> */ /* 754*/                    comId, desdription
                        });
/* <-MISALIGNED-> */ /* 755*/                if(rs.next())
/* <-MISALIGNED-> */ /* 756*/                    return rs.getString("fnumber");
                    }
/* <-MISALIGNED-> */ /* 758*/            catch(Exception e)
                    {
/* <-MISALIGNED-> */ /* 760*/                logger.error(e);
/* <-MISALIGNED-> */ /* 761*/                throw new BOSException(e);
                    }
                }






/* 774*/        return null;
            }
            public VoucherCollection getVoucherIsTemp(FilterInfo filter, IVoucher voucher)
                throws BOSException, EASBizException
            {








/* 787*/        EntityViewInfo evi = new EntityViewInfo();

/* 789*/        SelectorItemCollection selectorItemCollection = evi.getSelector();
/* 790*/        selectorItemCollection.add(new SelectorItemInfo("description"));
/* 791*/        selectorItemCollection.add(new SelectorItemInfo("id"));

/* 793*/        evi.setFilter(filter);
/* 794*/        VoucherCollection voucherCollection = voucher.getVoucherCollection(evi);

/* 796*/        return voucherCollection;
            }
            protected IObjectCollection importVoucher(Context ctx, IObjectCollection voucherCols, boolean isVerify, IObjectCollection cashflowCols, boolean isImpCashflow, boolean isSubmit, boolean post)
                throws BOSException, EASBizException
            {




















/* 821*/        if(voucherCols == null || voucherCols.size() == 0)
/* 822*/            return null;


/* 825*/        int tatal = 0;

/* 827*/        Map impList = new LinkedHashMap();

/* 829*/        Map errList = new LinkedHashMap();
/* 830*/        Map bufferList = new LinkedHashMap();

/* 832*/        Map repeatList = new LinkedHashMap();

/* 834*/        Map comList = new HashMap();

/* 836*/        VoucherInfo voucherInfo = null;
/* 837*/        VoucherEntryInfo entryInfo = null;
/* 838*/        VoucherAssistRecordInfo asstInfo = null;
/* 839*/        String prenumber = null;
/* 840*/        int preentrySeq = -1000;

/* 842*/        logger.info(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "924_WSVoucherControllerBean", ctx.getLocale()));
/* 843*/        LowTimer low = new LowTimer();
/* 844*/        low.reset();

/* 846*/        CompanyOrgUnitInfo companyOrgUnitInfo = null;
/* 847*/        PeriodInfo period = null;
/* 848*/        CurrencyInfo currencyInfo = null;
/* 849*/        VoucherTypeInfo voucherTypeInfo = null;


/* 852*/        boolean importParam = false;
/* 853*/        StringBuffer buffer = null;


/* 856*/        Map voucherMap = null;
/* 857*/        Map orgUnitInfoMap = null;
/* 858*/        Map accountViewInfoMap = null;
/* 859*/        Map periodMap = null;
/* 860*/        Map currencyInfoMap = null;
/* 861*/        Map voucherTypeInfoMap = null;
/* 862*/        Map importParamMap = null;
/* 863*/        Map handnumberParamMap = null;
/* 864*/        Map userMap = null;
/* 865*/        Map verifyAfterParamMap = null;
/* 866*/        Map tempMap = null;
/* 867*/        Map customerMap = null;


/* 870*/        boolean isInTimeVerify = true;

/* 872*/        boolean isSettleByLocal = true;

/* 874*/        boolean handnumberParam = false;

/* 876*/        com.kingdee.eas.fi.gl.VoucherInfo.ExchangeRateWithValue localToRpt = com.kingdee.eas.fi.gl.VoucherInfo.ExchangeRateWithValue.STANDARD_EXCHANGERATE;

/* 878*/        int i = 0;/* 878*/        for(int count = voucherCols.size(); i < count; i++)
                {
/* 880*/            WSVoucherInfo wsVoucher = (WSVoucherInfo)voucherCols.getObject(i);

/* 882*/            String comNumber = wsVoucher.getCompanyNumber();

/* 884*/            if(comNumber != null && !comList.containsKey(comNumber))
/* 885*/                comList.put(comNumber, comNumber);


/* 888*/            voucherMap = GlWebServiceUtil.getWsObject(ctx, comNumber, "voucher");
/* 889*/            tempMap = GlWebServiceUtil.getWsObject(ctx, comNumber, "temp");
/* 890*/            orgUnitInfoMap = GlWebServiceUtil.getWsObject(ctx, comNumber, "orgUnit");
/* 891*/            accountViewInfoMap = GlWebServiceUtil.getWsObject(ctx, comNumber, "accountView");
/* 892*/            periodMap = GlWebServiceUtil.getWsObject(ctx, comNumber, "period");
/* 893*/            currencyInfoMap = GlWebServiceUtil.getWsObject(ctx, comNumber, "currency");
/* 894*/            voucherTypeInfoMap = GlWebServiceUtil.getWsObject(ctx, comNumber, "voucherType");
/* 895*/            importParamMap = GlWebServiceUtil.getWsObject(ctx, comNumber, "importParam");
/* 896*/            userMap = GlWebServiceUtil.getWsObject(ctx, comNumber, "user");
/* 897*/            verifyAfterParamMap = GlWebServiceUtil.getWsObject(ctx, comNumber, "verify");
/* 898*/            customerMap = GlWebServiceUtil.getWsObject(ctx, comNumber, "customer");
/* 899*/            handnumberParamMap = GlWebServiceUtil.getWsObject(ctx, comNumber, "handnumber");

/* 901*/            if(wsVoucher.getItemFlag() == 1)
/* 902*/                continue;



/* 906*/            String number = wsVoucher.getVoucherNumber();
/* 907*/            if(errList.containsKey(number))
/* 908*/                continue;


/* 911*/            if(prenumber == null || !prenumber.equalsIgnoreCase(number))
                    {/* 912*/                buffer = new StringBuffer();
/* 913*/                bufferList.put(number, buffer);

/* 915*/                prenumber = number;
/* 916*/                preentrySeq = -1000;
/* 917*/                tatal++;

/* 919*/                if(orgUnitInfoMap.containsKey(comNumber) && orgUnitInfoMap.get(comNumber) != null)
                        {/* 920*/                    companyOrgUnitInfo = (CompanyOrgUnitInfo)orgUnitInfoMap.get(comNumber);

/* 922*/                    if(importParamMap.get(comNumber) == null)
                            {
/* 924*/                        importParam = GlWebServiceUtil.getImportParam(ctx, companyOrgUnitInfo.getId().toString());
/* 925*/                        importParamMap.put(comNumber, Boolean.valueOf(importParam));
                            } else
                            {
/* 928*/                        importParam = ((Boolean)importParamMap.get(comNumber)).booleanValue();
                            }

/* 931*/                    if(verifyAfterParamMap.get(comNumber) == null)
                            {/* 932*/                        isInTimeVerify = GlUtils.getVerifyMode(ctx, companyOrgUnitInfo.getId().toString()) == 0;
/* 933*/                        verifyAfterParamMap.put(comNumber, Boolean.valueOf(isInTimeVerify));
                            } else
                            {/* 935*/                        isInTimeVerify = ((Boolean)verifyAfterParamMap.get(comNumber)).booleanValue();
                            }

/* 938*/                    if(handnumberParamMap.get(comNumber) == null)
                            {
/* 940*/                        handnumberParam = GlWebServiceUtil.getHandnumberParam(ctx, companyOrgUnitInfo.getId().toString());
/* 941*/                        handnumberParamMap.put(comNumber, Boolean.valueOf(handnumberParam));
                            } else
                            {
/* 944*/                        handnumberParam = ((Boolean)handnumberParamMap.get(comNumber)).booleanValue();
                            }
                        } else
                        {/* 947*/                    companyOrgUnitInfo = GlWebServiceUtil.getCompany(ctx, wsVoucher.getCompanyNumber());
/* 948*/                    if(companyOrgUnitInfo != null)
                            {
/* 950*/                        isInTimeVerify = GlUtils.getVerifyMode(ctx, companyOrgUnitInfo.getId().toString()) == 0;
/* 951*/                        importParam = GlWebServiceUtil.getImportParam(ctx, companyOrgUnitInfo.getId().toString());
/* 952*/                        handnumberParam = GlWebServiceUtil.getHandnumberParam(ctx, companyOrgUnitInfo.getId().toString());

/* 954*/                        importParamMap.put(comNumber, Boolean.valueOf(importParam));
/* 955*/                        verifyAfterParamMap.put(comNumber, Boolean.valueOf(isInTimeVerify));
/* 956*/                        handnumberParamMap.put(comNumber, Boolean.valueOf(handnumberParam));

/* 958*/                        orgUnitInfoMap.put(comNumber, companyOrgUnitInfo);
                            }
                        }

/* 962*/                if(companyOrgUnitInfo == null)
                        {/* 963*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "925_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getCompanyNumber()).toString());
/* 964*/                    errList.put(number, "1001");
/* 965*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "926_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getCompanyNumber()).toString());
/* 966*/                    continue;
                        }


/* 970*/                if(companyOrgUnitInfo.getReportConvertMode() == null)
                        {/* 971*/                    errList.put(number, "3008");
/* 972*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "927_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getCompanyNumber()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "928_WSVoucherControllerBean", ctx.getLocale())).toString());
/* 973*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "927_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getCompanyNumber()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "928_WSVoucherControllerBean", ctx.getLocale())).toString());
                        }


/* 977*/                if(!isInTimeVerify)
/* 978*/                    isVerify = false;


/* 981*/                isSettleByLocal = 3 == companyOrgUnitInfo.getReportConvertMode().getValue();

/* 983*/                String typeId = companyOrgUnitInfo.getAccountPeriodType().getId().toString();


/* 986*/                String periodNumber = String.valueOf((new StringBuilder()).append(wsVoucher.getPeriodYear()).append("_").append(wsVoucher.getPeriodNumber()).toString());


/* 989*/                try
                        {
/* <-MISALIGNED-> */ /* 989*/                    if(periodMap.containsKey(wsVoucher.getBookedDate()) && periodMap.get(wsVoucher.getBookedDate()) != null)
                            {
/* <-MISALIGNED-> */ /* 990*/                        period = (PeriodInfo)periodMap.get(wsVoucher.getBookedDate());
                            } else
                            {
/* <-MISALIGNED-> */ /* 993*/                        period = GlWebServiceUtil.fetchPeriod(ctx, typeId, wsVoucher.getBookedDate());
/* <-MISALIGNED-> */ /* 994*/                        if(period != null)
/* <-MISALIGNED-> */ /* 995*/                            periodMap.put(wsVoucher.getBookedDate(), period);
                            }
                        }
/* <-MISALIGNED-> */ /* 997*/                catch(Exception e)
                        {
/* <-MISALIGNED-> */ /* 998*/                    errList.put(number, "1004");
/* <-MISALIGNED-> */ /* 999*/                    logger.error(e.getMessage(), e);
/* <-MISALIGNED-> */ /*1000*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(e.getMessage()).toString());
/* <-MISALIGNED-> */ /*1001*/                    continue;
                        }
/* <-MISALIGNED-> */ /*1004*/                if(period == null)
                        {
/* <-MISALIGNED-> */ /*1005*/                    errList.put(number, "1004");
/* <-MISALIGNED-> */ /*1006*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "929_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getBookedDate()).toString());
/* <-MISALIGNED-> */ /*1007*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "929_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getBookedDate()).toString());
/* <-MISALIGNED-> */ /*1008*/                    continue;
                        }
/* <-MISALIGNED-> */ /*1012*/                String checkNumber = String.valueOf((new StringBuilder()).append(period.getPeriodYear()).append("_").append(period.getPeriodNumber()).toString());/*1015*/                if(!checkNumber.equals(periodNumber))
                        {/*1016*/                    errList.put(number, "3003");
/*1017*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "930_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getBookedDate()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "931_WSVoucherControllerBean", ctx.getLocale())).append(periodNumber).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "932_WSVoucherControllerBean", ctx.getLocale())).toString());
/*1018*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "930_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getBookedDate()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "931_WSVoucherControllerBean", ctx.getLocale())).append(periodNumber).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "932_WSVoucherControllerBean", ctx.getLocale())).toString());
/*1019*/                    continue;
                        }

/*1022*/                String voucherNumber = wsVoucher.getVoucherType();
/*1023*/                if(voucherTypeInfoMap.containsKey(voucherNumber) && voucherTypeInfoMap.get(voucherNumber) != null)
                        {/*1024*/                    voucherTypeInfo = (VoucherTypeInfo)voucherTypeInfoMap.get(voucherNumber);
                        } else
                        {/*1026*/                    voucherTypeInfo = GlWebServiceUtil.findVouherTypeByName(ctx, voucherNumber, companyOrgUnitInfo.getCU().getId());
/*1027*/                    if(voucherTypeInfo != null)
/*1028*/                        voucherTypeInfoMap.put(voucherNumber, voucherTypeInfo);
                        }

/*1031*/                if(voucherTypeInfo == null)
                        {/*1032*/                    errList.put(number, "1005");
/*1033*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "933_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getVoucherType()).toString());
/*1034*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "934_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getVoucherType()).toString());
/*1035*/                    continue;
                        }


/*1039*/                String desc = wsVoucher.getDescription();
/*1040*/                String repnumber = null;
/*1041*/                if(desc != null)
/*1042*/                    if(importParam && voucherMap.containsKey(desc) && voucherMap.get(desc) != null)
                            {/*1043*/                        repnumber = (String)voucherMap.get(desc);
                            } else
                            {/*1045*/                        repnumber = checkImpRepeated(ctx, wsVoucher.getDescription(), companyOrgUnitInfo.getId().toString(), voucherTypeInfo.getId().toString(), period.getId().toString(), importParam);

/*1047*/                        if(repnumber != null)
/*1048*/                            voucherMap.put(desc, repnumber);
                            }




/*1054*/                if(repnumber != null)
                        {
/*1056*/                    errList.put(number, "3100");
/*1057*/                    repeatList.put(number, repnumber);
/*1058*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "935_WSVoucherControllerBean", ctx.getLocale())).append(repnumber).toString());
/*1059*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "935_WSVoucherControllerBean", ctx.getLocale())).append(repnumber).toString());
/*1060*/                    continue;
                        }


/*1064*/                if(desc != null && importParam && tempMap.containsKey(desc) && tempMap.get(desc) != null)

/*1066*/                    repnumber = (String)tempMap.get(desc);


/*1069*/                if(repnumber != null)
                        {/*1070*/                    errList.put(number, "3101");
/*1071*/                    repeatList.put(number, repnumber);
/*1072*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "936_WSVoucherControllerBean", ctx.getLocale())).append(repnumber).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "937_WSVoucherControllerBean", ctx.getLocale())).toString());
/*1073*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "936_WSVoucherControllerBean", ctx.getLocale())).append(repnumber).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "938_WSVoucherControllerBean", ctx.getLocale())).toString());
/*1074*/                    continue;
                        }
/*1076*/                tempMap.put(desc, desc);


/*1079*/                if(wsVoucher.getCurrencyNumber() == null)
/*1080*/                    wsVoucher.setCurrencyNumber("BB01");


/*1083*/                String curNumber = wsVoucher.getCurrencyNumber();
/*1084*/                if(currencyInfoMap.containsKey(curNumber) && currencyInfoMap.get(curNumber) != null)
                        {/*1085*/                    currencyInfo = (CurrencyInfo)currencyInfoMap.get(curNumber);
                        } else
                        {/*1087*/                    currencyInfo = GlWebServiceUtil.findCurrencyByNumber(ctx, wsVoucher.getCurrencyNumber());
/*1088*/                    if(currencyInfo != null)
/*1089*/                        currencyInfoMap.put(curNumber, currencyInfo);
                        }

/*1092*/                if(currencyInfo == null)
                        {/*1093*/                    errList.put(number, "1003");
/*1094*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "939_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getCurrencyNumber()).toString());
/*1095*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "939_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getCurrencyNumber()).toString());
/*1096*/                    continue;
                        }


/*1100*/                String cteator = wsVoucher.getCreator();
/*1101*/                UserInfo creatorInfo = null;
/*1102*/                if(userMap.containsKey(cteator) && userMap.get(cteator) != null)
                        {/*1103*/                    creatorInfo = (UserInfo)userMap.get(cteator);
                        } else
                        {/*1105*/                    creatorInfo = GlWebServiceUtil.findUserUByName(ctx, cteator);
/*1106*/                    if(creatorInfo != null)
/*1107*/                        userMap.put(cteator, creatorInfo);
                        }

/*1110*/                if(creatorInfo == null)
                        {/*1111*/                    errList.put(number, "1008");
/*1112*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "940_WSVoucherControllerBean", ctx.getLocale())).append(cteator).toString());
/*1113*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "940_WSVoucherControllerBean", ctx.getLocale())).append(cteator).toString());
/*1114*/                    continue;
                        }



/*1119*/                UserInfo auditorInfo = null;
/*1120*/                UserInfo posterInfo = null;
/*1121*/                if(post)
                        {/*1122*/                    String auditor = wsVoucher.getPoster();

/*1124*/                    if(userMap.containsKey(auditor) && userMap.get(auditor) != null)
                            {/*1125*/                        auditorInfo = (UserInfo)userMap.get(auditor);
                            } else
                            {/*1127*/                        auditorInfo = GlWebServiceUtil.findUserUByName(ctx, auditor);
/*1128*/                        if(auditorInfo != null)
/*1129*/                            userMap.put(auditor, auditorInfo);
                            }








/*1139*/                    String poster = wsVoucher.getPoster();

/*1141*/                    if(userMap.containsKey(poster) && userMap.get(poster) != null)
                            {/*1142*/                        posterInfo = (UserInfo)userMap.get(poster);
                            } else
                            {/*1144*/                        posterInfo = GlWebServiceUtil.findUserUByName(ctx, poster);
/*1145*/                        if(posterInfo != null)
/*1146*/                            userMap.put(poster, posterInfo);
                            }

/*1149*/                    if(posterInfo == null)
                            {/*1150*/                        errList.put(number, "1010");
/*1151*/                        logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "941_WSVoucherControllerBean", ctx.getLocale())).append(poster).toString());
/*1152*/                        buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "941_WSVoucherControllerBean", ctx.getLocale())).append(poster).toString());
/*1153*/                        continue;
                            }
                        }



/*1159*/                try
                        {
/* <-MISALIGNED-> */ /*1159*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(tatal).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "942_WSVoucherControllerBean", ctx.getLocale())).append(comNumber).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "943_WSVoucherControllerBean", ctx.getLocale())).append(periodNumber).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "944_WSVoucherControllerBean", ctx.getLocale())).append(voucherNumber).toString());
/* <-MISALIGNED-> */ /*1160*/                    voucherInfo = contructVoucherHeader(ctx, wsVoucher, companyOrgUnitInfo, period, voucherTypeInfo, currencyInfo);
/* <-MISALIGNED-> */ /*1161*/                    voucherInfo.setCreator(creatorInfo);
/* <-MISALIGNED-> */ /*1162*/                    voucherInfo.setHandler(creatorInfo);
/* <-MISALIGNED-> */ /*1163*/                    voucherInfo.setAuditor(auditorInfo);
/* <-MISALIGNED-> */ /*1164*/                    voucherInfo.setPoster(posterInfo);
                        }
/* <-MISALIGNED-> */ /*1165*/                catch(EASBizException e)
                        {
/* <-MISALIGNED-> */ /*1166*/                    logger.info(e);
/* <-MISALIGNED-> */ /*1167*/                    logger.info(e.getMessage());
/* <-MISALIGNED-> */ /*1168*/                    buffer.append(e.getMessage());
/* <-MISALIGNED-> */ /*1170*/                    continue;
                        }/*1174*/                if(handnumberParam)
                        {/*1175*/                    voucherInfo.setNumber(number);

/*1177*/                    voucherInfo.setPostedByUI(true);
                        }

/*1180*/                if(companyOrgUnitInfo.getReportCurrency() != null && isSettleByLocal && companyOrgUnitInfo.getReportExchangeTable() != null && companyOrgUnitInfo.getBaseExchangeTable() != null)
                        {



/*1185*/                    localToRpt = GlUtils.getLocalToReportingExchangeRate(companyOrgUnitInfo.getReportCurrency(), companyOrgUnitInfo.getReportExchangeTable().getId().toString(), companyOrgUnitInfo.getBaseCurrency(), companyOrgUnitInfo.getBaseExchangeTable().getId().toString(), voucherInfo.getBookedDate(), period, false, ctx);

/*1187*/                    if(localToRpt == null)
/*1188*/                        localToRpt = com.kingdee.eas.fi.gl.VoucherInfo.ExchangeRateWithValue.STANDARD_EXCHANGERATE;
                        }



/*1193*/                if(isImpCashflow && cashflowCols != null && cashflowCols.size() > 0)
                        {/*1194*/                    CashflowRecordCollection cos = new CashflowRecordCollection();
/*1195*/                    int ca = 0;/*1195*/                    for(int n = cashflowCols.size(); ca < n; ca++)
                            {/*1196*/                        CashflowRecordImpInfo key = (CashflowRecordImpInfo)cashflowCols.getObject(ca);
/*1197*/                        String value = key.getVouchernumber();
/*1198*/                        if(key.getBoolean("error"))
                                {/*1199*/                            errList.put(number, "2001");
/*1200*/                            buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "945_WSVoucherControllerBean", ctx.getLocale())).append(value).toString());
/*1201*/                            continue;
                                }/*1202*/                        if(!number.equalsIgnoreCase(value))
/*1203*/                            continue;/*1203*/                        CashflowRecordInfo record = key.getRecord();

/*1205*/                        if(isSettleByLocal)
/*1206*/                            record.setReportingAmount(record.getLocalAmount().multiply(localToRpt.getValue()));


/*1209*/                        record.setVoucher(voucherInfo);
/*1210*/                        cos.add(record);
                            }



/*1215*/                    voucherInfo.setCashflowRecords(cos);



/*1219*/                    if(cos.size() > 0)
/*1220*/                        voucherInfo.setCashflowFlag(CashflowFlag.FINISHED);
                        }


/*1224*/                impList.put(number, voucherInfo);
                    }


/*1228*/            int entrySeq = wsVoucher.getEntrySeq();
/*1229*/            int asstCountSeq = 0;
/*1230*/            if(preentrySeq == -1000 || preentrySeq != entrySeq)
                    {/*1231*/                preentrySeq = entrySeq;
/*1232*/                asstCountSeq = 1;


/*1235*/                String accountNumber = wsVoucher.getAccountNumber();
/*1236*/                AccountViewInfo accountViewInfo = null;

/*1238*/                String accountKey = (new StringBuilder()).append(comNumber).append(accountNumber).toString();
/*1239*/                if(accountViewInfoMap.containsKey(accountKey) && accountViewInfoMap.get(accountKey) != null)
                        {/*1240*/                    accountViewInfo = (AccountViewInfo)accountViewInfoMap.get(accountKey);
                        } else
                        {/*1242*/                    accountViewInfo = GlWebServiceUtil.findAccountViewInfoByNumber(ctx, wsVoucher.getAccountNumber(), voucherInfo.getCompany());
/*1243*/                    if(accountViewInfo != null)
/*1244*/                        accountViewInfoMap.put(accountKey, accountViewInfo);
                        }

/*1247*/                if(accountViewInfo == null)
                        {/*1248*/                    errList.put(number, "1002");
/*1249*/                    logger.info("1002");
/*1250*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "946_WSVoucherControllerBean", ctx.getLocale())).append(entrySeq).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "947_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getAccountNumber()).toString());
/*1251*/                    continue;
                        }


/*1255*/                if(accountViewInfo.isAC())
/*1256*/                    voucherInfo.setIsAC(true);


/*1259*/                logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "948_WSVoucherControllerBean", ctx.getLocale())).append(preentrySeq).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "949_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getAccountNumber()).toString());


/*1262*/                String customerNumber = wsVoucher.getCustomerNumber();
/*1263*/                CustomerInfo customerInfo = null;
/*1264*/                if(customerMap.containsKey(customerNumber) && customerMap.get(customerNumber) != null)
                        {/*1265*/                    customerInfo = (CustomerInfo)customerMap.get(customerNumber);
                        } else
                        {/*1267*/                    customerInfo = GlWebServiceUtil.findCustomerNumber(ctx, wsVoucher.getCustomerNumber(), voucherInfo.getCompany());
/*1268*/                    if(customerInfo != null)
/*1269*/                        customerMap.put(customerNumber, customerInfo);
                        }











/*1282*/                String curNumber = wsVoucher.getCurrencyNumber();
/*1283*/                if(currencyInfoMap.containsKey(curNumber) && currencyInfoMap.get(curNumber) != null)
                        {/*1284*/                    currencyInfo = (CurrencyInfo)currencyInfoMap.get(curNumber);
                        } else
                        {/*1286*/                    currencyInfo = GlWebServiceUtil.findCurrencyByNumber(ctx, wsVoucher.getCurrencyNumber());

/*1288*/                    if(currencyInfo != null)
/*1289*/                        currencyInfoMap.put(curNumber, currencyInfo);
                        }


/*1293*/                if(currencyInfo == null)
                        {/*1294*/                    errList.put(number, "1003");
/*1295*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "939_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getCurrencyNumber()).toString());
/*1296*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "939_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getCurrencyNumber()).toString());
/*1297*/                    continue;
                        }


/*1301*/                boolean checkAccountAndCurrency = GlUtils.checkAccountAndCurrency(ctx, accountViewInfo, currencyInfo, voucherInfo.getCompany());

/*1303*/                if(currencyInfo == null)
                        {/*1304*/                    errList.put(number, "1003");
/*1305*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "939_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getCurrencyNumber()).toString());
/*1306*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "939_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getCurrencyNumber()).toString());
/*1307*/                    continue;
                        }

/*1310*/                if(!checkAccountAndCurrency)
                        {/*1311*/                    errList.put(number, "1006");
/*1312*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "950_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getCurrencyNumber()).toString());
/*1313*/                    buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "950_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getCurrencyNumber()).toString());
/*1314*/                    continue;
                        }

/*1317*/                if(accountViewInfo.isIsCash() || accountViewInfo.isIsBank() || accountViewInfo.isIsCashEquivalent())
/*1318*/                    voucherInfo.setHasCashAccount(true);


/*1321*/                entryInfo = contructVoucherEntry(ctx, wsVoucher, voucherInfo, accountViewInfo, currencyInfo, cashflowCols, isSettleByLocal, localToRpt);


/*1324*/                voucherInfo.getEntries().appendObject(entryInfo);


/*1327*/                entryInfo.setBill(voucherInfo);
/*1328*/                voucherInfo.setEntryCount(entrySeq);
/*1329*/                entryInfo.setCustomer(customerInfo);
/*1330*/                entryInfo.setIsVerify(isVerify(accountViewInfo, entryInfo.getEntryDC(), true));

/*1332*/                logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "948_WSVoucherControllerBean", ctx.getLocale())).append(preentrySeq).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "949_WSVoucherControllerBean", ctx.getLocale())).append(wsVoucher.getAccountNumber()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "951_WSVoucherControllerBean", ctx.getLocale())).append(entryInfo.isIsVerify()).toString());
                    }


/*1336*/            AsstAccountInfo fcaa = entryInfo.getAccount().getCAA();
/*1337*/            if(fcaa != null)
                    {
/*1339*/                int asstSeq = wsVoucher.getAsstSeq();

/*1341*/                logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "952_WSVoucherControllerBean", ctx.getLocale())).append(asstSeq).toString());

/*1343*/                try
                        {
/* <-MISALIGNED-> */ /*1343*/                    asstInfo = new VoucherAssistRecordInfo();
/* <-MISALIGNED-> */ /*1344*/                    String errNumber = contructVoucherAsstRecord(ctx, wsVoucher, fcaa, entryInfo, asstInfo, isVerify, isSettleByLocal, localToRpt, period);/*1346*/                    if(errNumber != null)
                            {/*1347*/                        errList.put(number, errNumber);
/*1348*/                        buffer.append((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "91_RepairlUI", ctx.getLocale())).append(number).append(errNumber).toString());
/*1349*/                        continue;
                            }
                        }/*1351*/                catch(EASBizException e)
                        {/*1352*/                    logger.info(e);
/*1353*/                    buffer.append("\n1007: \r\n");
/*1354*/                    buffer.append(e.getMessage());

/*1356*/                    continue;
                        }

/*1359*/                asstInfo.setSeq(asstSeq);
/*1360*/                entryInfo.getAssistRecords().add(asstInfo);
/*1361*/                asstInfo.setEntry(entryInfo);
/*1362*/                asstInfo.setBill(voucherInfo);

/*1364*/                if(asstCountSeq == 1)
                        {/*1365*/                    entryInfo.setOriginalAmount(asstInfo.getOriginalAmount());
/*1366*/                    entryInfo.setLocalAmount(asstInfo.getLocalAmount());
/*1367*/                    entryInfo.setReportingAmount(asstInfo.getReportingAmount());
/*1368*/                    asstCountSeq++;
                        } else
                        {/*1370*/                    entryInfo.setOriginalAmount(entryInfo.getOriginalAmount().add(asstInfo.getOriginalAmount()));
/*1371*/                    entryInfo.setLocalAmount(entryInfo.getLocalAmount().add(asstInfo.getLocalAmount()));
							if(entryInfo.getReportingAmount()==null){
								entryInfo.setReportingAmount(asstInfo.getReportingAmount());
							}else{
								entryInfo.setReportingAmount(entryInfo.getReportingAmount().add(asstInfo.getReportingAmount()));
							}
                        }
                    }

/*1376*/            if(wsVoucher.getEntryDC() == 0)
                    {/*1377*/                if(voucherInfo.getLocalCreditAmount() == null)
/*1378*/                    voucherInfo.setLocalCreditAmount(entryInfo.getLocalAmount());

/*1380*/                else/*1380*/                    voucherInfo.setLocalCreditAmount(voucherInfo.getLocalCreditAmount().add(entryInfo.getLocalAmount()));
                    } else

/*1383*/            if(voucherInfo.getLocalDebitAmount() == null)
/*1384*/                voucherInfo.setLocalDebitAmount(entryInfo.getLocalAmount());

/*1386*/            else/*1386*/                voucherInfo.setLocalDebitAmount(voucherInfo.getLocalDebitAmount().add(entryInfo.getLocalAmount()));



/*1390*/            voucherInfo.setReportingCreditAmount(voucherInfo.getReportingCreditAmount());
/*1391*/            voucherInfo.setReportingDebitAmount(voucherInfo.getReportingDebitAmount());
                }


/*1395*/        logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "953_WSVoucherControllerBean", ctx.getLocale())).append(low.msValue()).toString());
/*1396*/        low.reset();

/*1398*/        IObjectCollection cols = new WSVoucherCollection();
/*1399*/        WSVoucherInfo wsVoucher = null;
/*1400*/        int sucCount = 0;
/*1401*/        Set set = impList.keySet();
/*1402*/        Iterator it = set.iterator();
/*1403*/        Map singerVoucher = null;
/*1404*/        IWSVoucher iwsVoucher = WSVoucherFactory.getLocalInstance(ctx);
/*1405*/        Map result = null;
/*1406*/        while(it.hasNext()) 
                {/*1407*/            singerVoucher = new HashMap();
/*1408*/            String key = (String)it.next();
/*1409*/            VoucherInfo tmpVoucherInfo = (VoucherInfo)impList.get(key);
/*1410*/            singerVoucher.put(key, tmpVoucherInfo);
/*1411*/            result = iwsVoucher.saveVoucher(singerVoucher, errList, voucherMap, low, isSubmit, post, sucCount);

/*1413*/            cols.addObjectCollection((IObjectCollection)result.get("cols"));

/*1415*/            voucherMap = (Map)result.get("voucherMap");

/*1417*/            sucCount = Integer.parseInt((String)result.get("sucCount"));
                }
/*1419*/        logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "954_WSVoucherControllerBean", ctx.getLocale())).append(tatal).toString());
/*1420*/        logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "955_WSVoucherControllerBean", ctx.getLocale())).append(sucCount).toString());

/*1422*/        set = errList.keySet();
/*1423*/        it = set.iterator();

/*1425*/        boolean isClearTemp = true;

/*1427*/        for(; it.hasNext(); cols.addObject(wsVoucher))
                {/*1428*/            String key = (String)it.next();
/*1429*/            wsVoucher = new WSVoucherInfo();
/*1430*/            wsVoucher.setVoucherNumber(key);

/*1432*/            String errCode = (String)errList.get(key);
/*1433*/            wsVoucher.setDescription(errCode);

/*1435*/            buffer = (StringBuffer)bufferList.get(key);
/*1436*/            if(buffer != null)
/*1437*/                wsVoucher.setVoucherAbstract(buffer.toString());


/*1440*/            if("3100".equals(errCode) && repeatList.get(key) != null)
/*1441*/                wsVoucher.setAccountNumber((String)repeatList.get(key));


/*1444*/            if("3101".equals(errCode))
/*1445*/                isClearTemp = false;
                }




/*1451*/        if(isClearTemp && comList.size() > 0)
                {/*1452*/            Set s = comList.keySet();
/*1453*/            for(Iterator ii = s.iterator(); ii.hasNext(); GlWebServiceUtil.setWsObject(ctx, (String)ii.next(), "temp"));
                }




/*1459*/        if(comList.size() > 0)
                {/*1460*/            Set s = comList.keySet();
/*1461*/            for(Iterator ii = s.iterator(); ii.hasNext(); GlWebServiceUtil.cleanWsObject(ctx, (String)ii.next()));
                }




/*1467*/        return cols;
            }
            private SelectorItemCollection getSelector()
            {



/*1474*/        SelectorItemCollection sic = new SelectorItemCollection();

/*1476*/        sic.add(new SelectorItemInfo("number"));

/*1478*/        return sic;
            }
            protected boolean isVerify(AccountViewInfo acc, EntryDC entryDC, boolean allowCussent)
            {
/*1482*/        if(allowCussent && acc != null && acc.isAC())
                {/*1483*/            BalanceDirectionEnum accDC = acc.getDC();
/*1484*/            if(BalanceDirectionEnum.DEBIT.equals(accDC) && EntryDC.CREDIT.equals(entryDC) || BalanceDirectionEnum.CREDIT.equals(accDC) && EntryDC.DEBIT.equals(entryDC))
/*1485*/                return true;
                }

/*1488*/        return false;
            }
            protected String[][] _importVoucher(Context ctx, IObjectCollection voucherCols, int isVerify, int isImpCashflow)
                throws BOSException, EASBizException
            {


/*1495*/        return importVoucherOfReturnInfo(ctx, voucherCols, isVerify, isImpCashflow, false);
            }
            protected String[][] _importVoucherOfReturnID(Context ctx, IObjectCollection voucherCols, int isVerify, int isImpCashflow)
                throws BOSException, EASBizException
            {



/*1503*/        return importVoucherOfReturnInfo(ctx, voucherCols, isVerify, isImpCashflow, true);
            }
            private String[][] importVoucherOfReturnInfo(Context ctx, IObjectCollection voucherCols, int isVerify, int isImpCashflow, boolean returnVchID)
                throws BOSException, EASBizException
            {










/*1518*/        int stringSize = 7;
/*1519*/        if(returnVchID)
/*1520*/            stringSize = 8;
                String result[][];

/*1523*/        if(voucherCols == null || voucherCols.size() == 0)
                {/*1524*/            result = new String[1][stringSize];
/*1525*/            result[0][0] = "none voucher!";
/*1526*/            result[0][4] = "1000";
/*1527*/            return (String[][])null;
                }
/*1529*/        result = (String[][])null;

/*1531*/        IObjectCollection cashflowCols = null;
/*1532*/        IObjectCollection voucher2Cols = null;


/*1535*/        if(isImpCashflow == 1)
                {/*1536*/            cashflowCols = new CashflowRecordImpCollection();
/*1537*/            voucher2Cols = new WSVoucherCollection();
/*1538*/            int i = 0;/*1538*/            for(int count = voucherCols.size(); i < count; i++)
                    {/*1539*/                WSVoucherInfo wsVoucher = (WSVoucherInfo)voucherCols.getObject(i);
/*1540*/                if(wsVoucher.getItemFlag() == 1)
                        {/*1541*/                    CashflowRecordImpInfo record = getCashFlowMap(ctx, wsVoucher);
/*1542*/                    if(record.getRecord() != null)
/*1543*/                        cashflowCols.addObject(record);
                        } else
                        {
/*1546*/                    voucher2Cols.addObject(wsVoucher);
                        }
                    }
                } else
                {/*1550*/            voucher2Cols = voucherCols;
                }

/*1553*/        boolean verify = isVerify == 1 || isVerify == 3;
/*1554*/        boolean isSubmit = isVerify == 2 || isVerify == 3;

/*1556*/        logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "956_WSVoucherControllerBean", ctx.getLocale())).append(voucher2Cols.size()).toString());
/*1557*/        if(isImpCashflow == 1)
/*1558*/            logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "957_WSVoucherControllerBean", ctx.getLocale())).append(cashflowCols.size()).toString());

/*1560*/        else/*1560*/            logger.info(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "958_WSVoucherControllerBean", ctx.getLocale()));

/*1562*/        logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "959_WSVoucherControllerBean", ctx.getLocale())).append(verify).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "960_WSVoucherControllerBean", ctx.getLocale())).append(isSubmit).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "961_WSVoucherControllerBean", ctx.getLocale())).append(isImpCashflow == 1).toString());


/*1565*/        boolean post = false;
/*1566*/        if(isVerify == 99)
                {/*1567*/            post = true;
/*1568*/            verify = false;
/*1569*/            isSubmit = false;
                }


/*1573*/        IObjectCollection resultCol = importVoucher(ctx, voucher2Cols, verify, cashflowCols, isImpCashflow == 1, isSubmit, post);

/*1575*/        logger.info(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "962_WSVoucherControllerBean", ctx.getLocale()));
/*1576*/        if(resultCol != null)
                {/*1577*/            result = new String[resultCol.size()][stringSize];
/*1578*/            int i = 0;/*1578*/            for(int count = resultCol.size(); i < count; i++)
                    {/*1579*/                WSVoucherInfo wsVoucher = (WSVoucherInfo)resultCol.getObject(i);
/*1580*/                result[i][0] = wsVoucher.getVoucherNumber();
/*1581*/                result[i][1] = wsVoucher.getVoucherType();
/*1582*/                result[i][2] = String.valueOf(wsVoucher.getPeriodYear());
/*1583*/                result[i][3] = String.valueOf(wsVoucher.getPeriodNumber());
/*1584*/                result[i][4] = wsVoucher.getDescription();
/*1585*/                result[i][5] = wsVoucher.getVoucherAbstract();
/*1586*/                result[i][6] = wsVoucher.getAccountNumber();
/*1587*/                if(returnVchID)
/*1588*/                    result[i][7] = (String)wsVoucher.get("id");

/*1590*/                if(returnVchID)
/*1591*/                    logger.info((new StringBuilder()).append("[").append(i).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "963_WSVoucherControllerBean", ctx.getLocale())).append(result[i][0]).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "964_WSVoucherControllerBean", ctx.getLocale())).append(result[i][4]).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "965_WSVoucherControllerBean", ctx.getLocale())).append(result[i][5]).append(result[i][6]).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "966_WSVoucherControllerBean", ctx.getLocale())).append(result[i][7]).toString());

/*1593*/                else/*1593*/                    logger.info((new StringBuilder()).append("[").append(i).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "963_WSVoucherControllerBean", ctx.getLocale())).append(result[i][0]).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "964_WSVoucherControllerBean", ctx.getLocale())).append(result[i][4]).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "965_WSVoucherControllerBean", ctx.getLocale())).append(result[i][5]).append(result[i][6]).toString());
                    }
                }


/*1598*/        return result;
            }
            private CashflowRecordImpInfo getCashFlowMap(Context ctx, WSVoucherInfo wsVoucher)
                throws BOSException
            {

/*1604*/        CashflowRecordImpInfo recodImp = new CashflowRecordImpInfo();

/*1606*/        recodImp.setVouchernumber(wsVoucher.getVoucherNumber());
/*1607*/        recodImp.setSeq(String.valueOf(wsVoucher.getEntrySeq()));
/*1608*/        recodImp.setOppseq(String.valueOf(wsVoucher.getOppAccountSeq()));


/*1611*/        String primarynumber = wsVoucher.getPrimaryItem();
/*1612*/        CashFlowItemInfo cashInfo = GlWebServiceUtil.findCashflowItemByName(ctx, primarynumber);

/*1614*/        if(primarynumber != null && cashInfo == null)
/*1615*/            recodImp.setBoolean("error", true);


/*1618*/        String supplynumber = wsVoucher.getSupplyItem();
/*1619*/        CashFlowItemInfo sucashInfo = GlWebServiceUtil.findCashflowItemByName(ctx, supplynumber);

/*1621*/        BigDecimal originalAmount = new BigDecimal(String.valueOf(wsVoucher.getCashflowAmountOriginal()));
/*1622*/        BigDecimal localamount = new BigDecimal(String.valueOf(wsVoucher.getCashflowAmountLocal()));
/*1623*/        BigDecimal reportamount = new BigDecimal(String.valueOf(wsVoucher.getCashflowAmountRpt()));

/*1625*/        int primarycoef = (int)wsVoucher.getPrimaryCoef();

/*1627*/        int supplycoef = (int)wsVoucher.getSupplyCoef();

/*1629*/        CashflowRecordInfo record = new CashflowRecordInfo();
/*1630*/        record.setOriginalAmount(originalAmount);
/*1631*/        record.setLocalAmount(localamount);
/*1632*/        record.setReportingAmount(reportamount);

/*1634*/        record.setPrimaryCoefficient(primarycoef);
/*1635*/        record.setSupplementaryCoefficient(supplycoef);

/*1637*/        record.setSupplementaryItem(sucashInfo);
/*1638*/        record.setPrimaryItem(cashInfo);
/*1639*/        if(sucashInfo == null)
/*1640*/            record.setItemFlag(ItemFlag.PRIMARY);
/*1641*/        else/*1641*/        if(cashInfo == null)
/*1642*/            record.setItemFlag(ItemFlag.SUPPLEMENTARY);

/*1644*/        else/*1644*/            record.setItemFlag(ItemFlag.BOTH);

/*1646*/        recodImp.setRecord(record);

/*1648*/        return recodImp;
            }
            private com.kingdee.eas.fi.gl.VoucherInfo.ExchangeRateWithValue getExChangeRate(Context ctx, CompanyOrgUnitInfo companyOrgUnitInfo, CurrencyInfo currencyInfo, PeriodInfo period)
                throws BOSException
            {
/*1653*/        Object exs[] = null;

/*1655*/        try
                {
/* <-MISALIGNED-> */ /*1655*/            exs = GlWebServiceUtil.getExchangeRate(ctx, companyOrgUnitInfo, currencyInfo, period);
                }
/* <-MISALIGNED-> */ /*1656*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /*1658*/            logger.error(e);
                }
/* <-MISALIGNED-> */ /*1661*/        com.kingdee.eas.fi.gl.VoucherInfo.ExchangeRateWithValue rptchangeRate = com.kingdee.eas.fi.gl.VoucherInfo.ExchangeRateWithValue.STANDARD_EXCHANGERATE;/*1663*/        if(exs != null)
                {




/*1669*/            rptchangeRate = (com.kingdee.eas.fi.gl.VoucherInfo.ExchangeRateWithValue)exs[1];
/*1670*/            if(rptchangeRate == null)
/*1671*/                rptchangeRate = com.kingdee.eas.fi.gl.VoucherInfo.ExchangeRateWithValue.STANDARD_EXCHANGERATE;
                }


/*1675*/        return rptchangeRate;
            }
            protected IObjectCollection _importVoucher(Context ctx, IObjectCollection voucherCols, boolean isTempSave, boolean isVerify, boolean hasCashflow)
                throws BOSException, EASBizException
            {




/*1684*/        int verify = 0;
/*1685*/        if(isTempSave && !isVerify)
/*1686*/            verify = 0;
/*1687*/        else/*1687*/        if(isTempSave && isVerify)
/*1688*/            verify = 1;
/*1689*/        else/*1689*/        if(!isTempSave && !isVerify)
/*1690*/            verify = 2;
/*1691*/        else/*1691*/        if(!isTempSave && isVerify)
/*1692*/            verify = 3;



/*1696*/        String result[][] = _importVoucher(ctx, voucherCols, verify, hasCashflow ? 1 : 0);


/*1699*/        WSRtnInfoCollection rtnCol = new WSRtnInfoCollection();
/*1700*/        if(result != null)
                {/*1701*/            int i = 0;/*1701*/            for(int count = result.length; i < count; i++)
                    {/*1702*/                WSRtnInfoInfo wsRtnInfo = new WSRtnInfoInfo();

/*1704*/                wsRtnInfo.setNumber(result[i][0]);
/*1705*/                wsRtnInfo.setVoucherType(result[i][1]);
/*1706*/                wsRtnInfo.setYear(result[i][2]);
/*1707*/                wsRtnInfo.setPeriod(result[i][3]);
/*1708*/                wsRtnInfo.setMessage(result[i][4]);
/*1709*/                wsRtnInfo.setException(result[i][5]);
/*1710*/                wsRtnInfo.setEasNumber(result[i][6]);

/*1712*/                rtnCol.add(wsRtnInfo);
                    }
                }

/*1716*/        return rtnCol;
            }
            protected Map _saveVoucher(Context ctx, Map impList, Map errList, Map voucherMap, LowTimer low, boolean isSubmit, boolean post, 
                    int sucCount)
                throws BOSException, EASBizException
            {
/*1722*/        IObjectCollection cols = new WSVoucherCollection();

/*1724*/        IVoucher bean = VoucherFactory.getLocalInstance(ctx);
/*1725*/        IGLPeriodEndFacade iGLPeriodEndFacade = GLPeriodEndFacadeFactory.getLocalInstance(ctx);
/*1726*/        Set set = impList.keySet();
/*1727*/        Iterator it = set.iterator();

/*1729*/        WSVoucherInfo wsVoucher = null;
/*1730*/        do
                {
/* <-MISALIGNED-> */ /*1730*/            if(!it.hasNext())
/* <-MISALIGNED-> */ /*1731*/                break;
/* <-MISALIGNED-> */ /*1731*/            String key = (String)it.next();
/* <-MISALIGNED-> */ /*1733*/            if(!errList.containsKey(key))
                    {
/*1737*/                VoucherInfo tmpVoucherInfo = (VoucherInfo)impList.get(key);

/*1739*/                wsVoucher = new WSVoucherInfo();
/*1740*/                wsVoucher.setVoucherNumber(key);

/*1742*/                try
                        {
/* <-MISALIGNED-> */ /*1742*/                    com.kingdee.bos.dao.IObjectPK pk = null;
/* <-MISALIGNED-> */ /*1743*/                    String creatorid = tmpVoucherInfo.getCreator().getId().toString();
/*1746*/                    if(!tmpVoucherInfo.isHasCashAccount() && tmpVoucherInfo.getCashflowRecords() != null)
                            {

/*1749*/                        for(int i = tmpVoucherInfo.getCashflowRecords().size() - 1; i >= 0; i--)
/*1750*/                            tmpVoucherInfo.getCashflowRecords().get(i).setPrimaryItem(null);
                            }






/*1758*/                    if(post)
                            {/*1759*/                        tmpVoucherInfo.setBizStatus(VoucherStatusEnum.POSTED);

/*1761*/                        pk = VoucherUtil._addnewVoucher(ctx, tmpVoucherInfo);

/*1763*/                        iGLPeriodEndFacade.postVoucherOnBook(tmpVoucherInfo.getCU().getId().toString(), tmpVoucherInfo.getCompany().getId().toString(), new String[] {/*1763*/                            pk.toString()
                                }, true, 1, false);/*1764*/                        iGLPeriodEndFacade.postVoucherOnBook(tmpVoucherInfo.getCU().getId().toString(), tmpVoucherInfo.getCompany().getId().toString(), new String[] {/*1764*/                            pk.toString()
                                }, true, 5, false);
                            } else
/*1767*/                    if(isSubmit)
/*1768*/                        pk = bean.submit(tmpVoucherInfo);

/*1770*/                    else/*1770*/                        pk = bean.save(tmpVoucherInfo);



/*1774*/                    String sql = "update t_gl_voucher set fcreatorid=? where fid=?";
/*1775*/                    DbUtil.execute(ctx, sql, new Object[] {/*1775*/                        creatorid, pk.toString()
                            });

/*1778*/                    sucCount++;

/*1780*/                    VoucherInfo voucherinfo = (VoucherInfo)bean.getValue(pk, getSelector());


/*1783*/                    if(tmpVoucherInfo.getDescription() != null)
/*1784*/                        voucherMap.put(tmpVoucherInfo.getDescription(), voucherinfo.getNumber());


/*1787*/                    wsVoucher.setVoucherType(tmpVoucherInfo.getVoucherType().getName());
/*1788*/                    wsVoucher.setPeriodYear(tmpVoucherInfo.getPeriod().getPeriodYear());
/*1789*/                    wsVoucher.setPeriodNumber(tmpVoucherInfo.getPeriod().getPeriodNumber());
/*1790*/                    wsVoucher.put("id", tmpVoucherInfo.getId().toString());

/*1792*/                    wsVoucher.setDescription("0000");
/*1793*/                    wsVoucher.setVoucherAbstract(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "967_WSVoucherControllerBean", ctx.getLocale()));
/*1794*/                    wsVoucher.setAccountNumber(voucherinfo.getNumber());

/*1796*/                    cols.addObject(wsVoucher);

/*1798*/                    logger.info((new StringBuilder()).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "968_WSVoucherControllerBean", ctx.getLocale())).append(sucCount).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "969_WSVoucherControllerBean", ctx.getLocale())).append(low.msValue()).toString());
/*1799*/                    low.reset();
                        }
/*1801*/                catch(Exception e)
                        {/*1802*/                    if(e instanceof PermissionException)

/*1804*/                        wsVoucher.setDescription("3001");
/*1805*/                    else/*1805*/                    if(e instanceof CodingRuleException)

/*1807*/                        wsVoucher.setDescription("3002");
/*1808*/                    else/*1808*/                    if(e instanceof VoucherException)
/*1809*/                        wsVoucher.setDescription((new StringBuilder()).append("4").append(((VoucherException)e).getSubCode()).toString());


/*1812*/                    else/*1812*/                        wsVoucher.setDescription("1111");

/*1814*/                    logger.info(e);

/*1816*/                    wsVoucher.setVoucherAbstract(e.getMessage());

/*1818*/                    cols.addObject(wsVoucher);
                        }
                    }
                } while(true);
/*1822*/        Map result = new HashMap();
/*1823*/        result.put("cols", cols);

/*1825*/        result.put("voucherMap", voucherMap);

/*1827*/        result.put("sucCount", (new StringBuilder()).append(sucCount).append("").toString());
/*1828*/        return result;
            }
            private static final long serialVersionUID = 7464876043639725995L;
            private static Logger logger = Logger.getLogger("com.kingdee.eas.fi.gl.app.WSVoucherControllerBean");
            private static final int SEQ = -1000;
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\patch\sp-fi_gl-server.jar
	Total time: 250 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/