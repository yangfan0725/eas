package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillExecuteDataHander;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctException;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum;
import com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategyFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctFactory;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctItemCollection;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctItemInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctCollection;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryCollection;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryFactory;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctFactory;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctItemCollection;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctItemInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

public class FDCBudgetAcctFacadeControllerBean extends AbstractFDCBudgetAcctFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.FDCBudgetAcctFacadeControllerBean");
    protected void _autoUpdateContractPayPlan(Context ctx, String budgetId)throws BOSException, EASBizException
    {
    	BigDecimal conLatestPrice = FDCHelper.ZERO;	//��ͬ�������
    	BigDecimal payProportion = FDCHelper.ZERO;	//�������
    	BigDecimal payOriAmount = FDCHelper.ZERO;	//ԭ�ҽ��
    	BigDecimal payAmount = FDCHelper.ZERO;		//����ƻ����
    	BigDecimal payedAmt = FDCHelper.ZERO;		//�Ѹ����
    	BigDecimal exRate = FDCHelper.ZERO;			//����
    	
    	String item = "";	//����Map�ļ�ֵ
    	String decItem = "";	//����decMap�ļ�ֵ
    	boolean nextYear = false;	//�Ƿ�����һ����·�
    	
    	int year = 0;
    	int newYear = 0;
    	Date payDate = new Date();//�ƻ���������
    	Map pAMap = new HashMap();	//����ƻ����
    	Map pDMap = new HashMap();	//�Ѹ����
    	Map pPMap = new HashMap();	//�������
    	Map decMap = new HashMap();	//������±�ע
//    	Map mMap = new HashMap(); //��ͬ�·ݵ�map

    	CompanyOrgUnitInfo company =null;
    	CurrencyInfo currency=null;
    	CurProjectInfo  curProject=null;
    	
    	//���¶ȼƻ���ȡ��Ԥ��������µ�����
    	EntityViewInfo ev = new EntityViewInfo();
    	FilterInfo fi = new FilterInfo();
    	/************֧�ֿ���Ŀ��ֵĹ�����ĿҲ�ɽ��к�ͬ���㼰��ͨ����ͬid���ڼ����ȡ������ķ�¼��  -by neo**************/
    	ev.getSelector().add("*");
    	ev.getSelector().add("items.*");
    	ev.getSelector().add("parent.*");
//    	ev.getSelector().add("parent.curProject.*");
    	ev.getSelector().add("contractBill.curProject.*");
    	ev.getSelector().add("parent.fdcPeriod.*");
    	ev.getSelector().add("contractBill.id");
    	ev.setFilter(fi);
    	String innerSQL = "select distinct accentry.fcontractbillid from t_fnc_fdcmonthbudgetacctentry accentry " +
    			"	inner join t_fnc_fdcmonthbudgetacct acc on accentry.fparentid=acc.fid " +
    			"	inner join t_fnc_fdcmonthbudgetacct accp  on accp.fperiodid=acc.fperiodid" +
    			"	where acc.fid='"+budgetId+"' ";
    	fi.getFilterItems().add(new FilterItemInfo("contractBill.id",innerSQL,CompareType.INNER));
    	fi.getFilterItems().add(new FilterItemInfo("parent.isLatestVer",Boolean.TRUE));//�����޶��汾,ȡ���հ�
    	
    	/**************************/
    	//�õ��¸���ƻ��ķ�¼��Ϣ
    	FDCMonthBudgetAcctEntryCollection c = FDCMonthBudgetAcctEntryFactory.getLocalInstance(ctx).getFDCMonthBudgetAcctEntryCollection(ev);
    	if(c.size()!=0){
    		String contractId = "";
    		String dec = "";
    		String dec2 = "";
    		Iterator it = c.iterator();
    		while(it.hasNext()){
    			Map pAMap2 = new HashMap();	//����ƻ����
    			Map pPMap2 = new HashMap();	//�������
//    			int itemQuantity = 0;	//�Ѿ�ǩ����ͬ���������������2����˵���ж�����ǩ����ͬ
    			FDCMonthBudgetAcctEntryInfo fdcInfo = (FDCMonthBudgetAcctEntryInfo)it.next();
    			
    			//�õ���ǩ����ͬ�����µĸ���ƻ���ͬ
    			if(fdcInfo.getItemType().toString().equalsIgnoreCase("��ǩ����ͬ")){
    				//�õ���Ŀ�¼ƻ����꣬��ͬ��������ۣ���ͬId
    				year = fdcInfo.getParent().getFdcPeriod().getYear();
    				conLatestPrice = fdcInfo.getConLatestPrice();
    				contractId = fdcInfo.getContractBill().getId().toString();
    				/*****************************************/
    				
    				
    				//�õ����ڣ���������Ϊĳ�µ�1��
    				payDate = fdcInfo.getParent().getFdcPeriod().toDate();
    				payDate.setDate(1);
    				
    				//�ж��Ƿ�����һ����·�
    				if(payDate.getMonth()>=10 && nextYear==false){
    					nextYear = true;
    				}
    				
    				//�����map���Ѿ��иĺ�ͬid����ȡ���ĺ�ͬid��Ӧ��ֵ�������ۼӣ��������map��
    				if(pAMap.containsKey(contractId)){
    					pAMap2 = (Map) pAMap.get(contractId);
    					Iterator itc2 = fdcInfo.getItems().iterator();
    					while(itc2.hasNext()){
    						FDCMonthBudgetAcctItemInfo itemInfo = (FDCMonthBudgetAcctItemInfo)itc2.next();
//  						dec = fdcInfo.getParent().getCurProject().getName()+"��"+itemInfo.getMonth()+"������";
//    						dec = itemInfo.getMonth()+"�¼ƻ�������������";
    						item = (new BigDecimal(itemInfo.getMonth())).toString();
    						decItem = contractId.concat(item);
    						if(payDate.getMonth()==itemInfo.getMonth()){
    							if(fdcInfo.getAmount()!=null && pAMap2.get(item)!=null){
    								payAmount = (BigDecimal) pAMap2.get(item);
    								payAmount = payAmount.add(fdcInfo.getAmount());
    								pAMap2.put(item, payAmount);
    								if(conLatestPrice==null||conLatestPrice.signum()==0){
    									payProportion=FDCHelper.ZERO;
    								}else{
//    									payProportion = (payAmount.multiply(new BigDecimal("100"))).divide(conLatestPrice, 2, BigDecimal.ROUND_HALF_UP);
    									payProportion = FDCHelper.divide(FDCHelper.multiply(payAmount, FDCHelper.ONE_HUNDRED), conLatestPrice, 2, BigDecimal.ROUND_HALF_UP);
    								}
    								pPMap2.put(item, payProportion);
    							}
    						}
    						else{
    							//�жϿ�ֵ
    							if(itemInfo.getAmount()!=null && pAMap2.get(item)!=null){
    								payAmount = (BigDecimal) pAMap2.get(item);
    								payAmount = payAmount.add(itemInfo.getAmount());
    								pAMap2.put(item, payAmount);
    								if(conLatestPrice==null||conLatestPrice.signum()==0){
    									payProportion=FDCHelper.ZERO;
    								}else{
    									payProportion = (payAmount.multiply(new BigDecimal("100"))).divide(conLatestPrice, 2, BigDecimal.ROUND_HALF_UP);
    								}
    								pPMap2.put(item, payProportion);
    							}
    						}
    						dec = fdcInfo.getDesc();
    						if(decMap.get(decItem)!=null){
    							if(dec != null && dec.length()>0){
    								dec2 = (String)decMap.get(decItem);
        							dec = dec2.concat("/".concat(dec));
        							decMap.put(decItem,dec);
    							}    							
    						}else{
    							dec2 = itemInfo.getMonth()+"�¼ƻ�������������";
    							if(dec != null && dec.length()>0){
    								dec = dec2.concat("/".concat(dec));
        							decMap.put(decItem,dec);
    							}else{
    								decMap.put(decItem,dec2);
    							}    							
    						}	
    					}
    				}
    				else{
    					Iterator itc2 = fdcInfo.getItems().iterator();
    					while(itc2.hasNext()){
    						FDCMonthBudgetAcctItemInfo itemInfo = (FDCMonthBudgetAcctItemInfo)itc2.next();
//    						dec = fdcInfo.getParent().getCurProject().getName()+"��"+itemInfo.getMonth()+"������";
    						item = (new BigDecimal(itemInfo.getMonth())).toString();
    						decItem = contractId.concat(item);
    						if(payDate.getMonth()==(itemInfo.getMonth()-1)){
    							if(fdcInfo.getAmount()!=null){
    								pAMap2.put(item, fdcInfo.getAmount());
//  								itemQuantity++;
    								if(conLatestPrice==null||conLatestPrice.signum()==0){
    									payProportion=FDCHelper.ZERO;
    								}else{
    									payProportion = fdcInfo.getAmount().multiply(new BigDecimal("100")).divide(conLatestPrice, 2, BigDecimal.ROUND_HALF_UP);
    								}
    								pPMap2.put(item, payProportion);
    							}
    							else if(fdcInfo.getAmount()==null && itemInfo.getAmount()!=null){
    								pAMap2.put(item, FDCHelper.ZERO);
    								pPMap2.put(item, FDCHelper.ZERO);
    							}
    							decMap.put(item,dec);
    						}
    						else{
    							//�жϿ�ֵ
    							if(itemInfo.getAmount()!=null){
    								pAMap2.put(item, itemInfo.getAmount());
    								
//  								itemQuantity++;
    								if(conLatestPrice==null||conLatestPrice.signum()==0){
    									payProportion=FDCHelper.ZERO;
    								}else{
    									payProportion = itemInfo.getAmount().multiply(new BigDecimal("100")).divide(conLatestPrice, 2, BigDecimal.ROUND_HALF_UP);
    								}
    								pPMap2.put(item, payProportion);
    							}
    							
    							decMap.put(item,dec);
    						}
    						dec = fdcInfo.getDesc();
    						if(decMap.get(decItem)!=null){
    							if(dec != null && dec.length()>0){
    								dec2 = (String)decMap.get(decItem);
        							dec = dec2.concat("/".concat(dec));
        							decMap.put(decItem,dec);
    							}    							
    						}else{
    							dec2 = itemInfo.getMonth()+"�¼ƻ�������������";
    							if(dec != null && dec.length()>0){
    								dec = dec2.concat("/".concat(dec));
        							decMap.put(decItem,dec);
    							}else{
    								decMap.put(decItem,dec2);
    							}    							
    						}	
    					}
    				}
					//�ú�ͬID����������
    				if(pAMap2.size()!=0){
    					pAMap.put(contractId, pAMap2);
    				}
    				if(pPMap2.size()!=0){
    					pPMap.put(contractId, pPMap2);
    				}
    			}
    		}
    		
    		//�õ��������Ѹ�����
    		Set set = pAMap.keySet();
    		Iterator its = set.iterator();
    		while(its.hasNext()){
    			Map pDMap2 = new HashMap();	//�Ѹ����
    			String cbillId = (String)its.next();  
    			Map map = (Map) pAMap.get(cbillId);
    			Set smap = map.keySet();
    			Iterator sit = smap.iterator();
    			while(sit.hasNext()){
    				String month = (String)sit.next();
    				
    				//�õ�һ���µ��Ѹ�����
    				Calendar cal = Calendar.getInstance();
    				
    				//�����ж��Ƿ�����һ����·�
    				if(nextYear){
    					newYear = year+1;
    					if(month.equals("1") || month.equals("2")){
    						cal.set(Calendar.YEAR, newYear);
    					}
    					else{
    						cal.set(Calendar.YEAR, year);
    					}
    				}
    				else{
    					cal.set(Calendar.YEAR, year);
    				}
    				
    				cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
    				cal.set(Calendar.DATE, 1);
    				Date beginDate = DateTimeUtils.truncateDate(cal.getTime());
    				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
    				Date endDate = DateTimeUtils.truncateDate(cal.getTime());
    				
    				EntityViewInfo view = new EntityViewInfo();
    				view.getSelector().add("amount");
    				FilterInfo filter = new FilterInfo();
    				view.setFilter(filter);
    				filter.getFilterItems().add(
    						new FilterItemInfo("contractBillId", cbillId));
    				if (beginDate != null) {
    					filter.getFilterItems().add(
    							new FilterItemInfo("payDate", beginDate,
    									CompareType.GREATER_EQUALS));
    				}
    				filter.getFilterItems().add(
    						new FilterItemInfo("payDate", endDate, CompareType.LESS));
    				filter.getFilterItems().add(
    						new FilterItemInfo("billStatus", new Integer(
    								BillStatusEnum.PAYED_VALUE)));
    				PaymentBillCollection payRequestBillCollection = PaymentBillFactory
    				.getLocalInstance(ctx).getPaymentBillCollection(view);
    				BigDecimal amount = FDCHelper.ZERO;
    				for (int i = 0; i < payRequestBillCollection.size(); i++) {
    					PaymentBillInfo info = payRequestBillCollection.get(i);
/*    					if (amount == null) {
    						amount = FDCHelper.ZERO;
    					}*/
    					if(info.getAmount()!=null){
    						amount = amount.add(info.getAmount());
    					}
    				}
    				payedAmt = amount;
    				pDMap2.put(month, payedAmt);
    			}
    			
    			pDMap.put(cbillId,pDMap2);
    		}
    		
    		boolean second2=false;
    		//������ͬ�ĸ���ƻ���¼��������е��¸���ƻ����������
    		EntityViewInfo evInfo = new EntityViewInfo();
    		FilterInfo fcInfo = new FilterInfo();
            evInfo.getSelector().add("contractId");
    		evInfo.getSelector().add("payDate");
    		evInfo.getSelector().add("exchangeRate");
    		evInfo.getSelector().add("currecy");
    		evInfo.getSelector().add("curProject");
    		evInfo.getSelector().add("curProject.company");
    		evInfo.getSelector().add("company");
    		
    		evInfo.setFilter(fcInfo);
//  		fcInfo.appendFilterItem("contractId", contractId);
    		
    		Set sea = pAMap.keySet();
    		Iterator ita = sea.iterator();
    		while(ita.hasNext()){
    			String cBillId = (String)ita.next();
    			if(second2){
    				FilterInfo fi2 = new FilterInfo();
    				evInfo.setFilter(fi2);
                    fi2.getFilterItems().add(new FilterItemInfo("contractId", cBillId));
    			}
    			else{
    				fcInfo.getFilterItems().add(new FilterItemInfo("contractId", cBillId));
    				second2=true;
    			}
    			ContractPayPlanCollection cp = ContractPayPlanFactory.getLocalInstance(ctx).getContractPayPlanCollection(evInfo);
    			
    			Map pDMap2 = (Map)pDMap.get(cBillId);
    			Map pPMap2 = (Map)pPMap.get(cBillId);
    			Map pAMap2 = (Map)pAMap.get(cBillId);
    			
    			
//  			���û�и���ƻ�
    			if(cp.size()==0){
    				//�����ǰ��ͬ�Ļ���
    				EntityViewInfo conInfo = new EntityViewInfo();
    				FilterInfo confInfo = new FilterInfo();
    				conInfo.getSelector().add("exRate");
    				conInfo.getSelector().add("currency");
    				conInfo.getSelector().add("curProject");
    				conInfo.getSelector().add("curProject.fullOrgUnit");
    				conInfo.setFilter(confInfo);
    				confInfo.appendFilterItem("id", cBillId);
    				ContractBillCollection conColl = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(conInfo);
    				
    				Iterator itcon = conColl.iterator();
    				while(itcon.hasNext()){
    					ContractBillInfo cbInfo = (ContractBillInfo)itcon.next();
    					exRate = cbInfo.getExRate();
    					curProject=cbInfo.getCurProject();
                        if(curProject!=null){
                         company= (CompanyOrgUnitInfo)curProject.getFullOrgUnit().cast(CompanyOrgUnitInfo.class);
                        }
                        currency=cbInfo.getCurrency();
    					
    				}	
    				
    				//ѭ��ȡ���ƻ�������Ѹ��������Ϣ
    				Set se = pAMap2.keySet();
    				Iterator itc = se.iterator();
    				while(itc.hasNext()){
    					String month = (String)itc.next();
    					int monthInt = Integer.parseInt(month);
    					payedAmt = (BigDecimal)pDMap2.get(month);
    					payAmount = (BigDecimal)pAMap2.get(month);
    					payOriAmount = payAmount.divide(exRate, 2, BigDecimal.ROUND_HALF_UP);
    					payProportion = (BigDecimal) pPMap2.get(month);

    					decItem = cBillId.concat(month);
    					dec = (String) decMap.get(decItem);
    					if(dec != null && dec.length()>80){
    	    				dec = dec.substring(0,80);
    	    			}
                        
//                      ������һ��Info��������������Ҫ���»������Ӹ���ƻ�ʱ������
                        ContractPayPlanInfo cPlanInfo = new ContractPayPlanInfo();
//    					cPlanInfo.setContractId(cBillId);//53����������,60��Ϊ���ö���
                        
                        ContractBillInfo con = new ContractBillInfo();
                        cPlanInfo.setContractId(con);
                        cPlanInfo.getContractId().setId(BOSUuid.read(cBillId));
                        // Ӧ��Ҫ����Currency Company CurProject����Ϣ �����ڸ���ƻ���ѯ�в�����ʾ�ü�¼    by Cassiel_peng  2009-10-4
                        if(currency!=null){
                        	cPlanInfo.setCurrecy(currency);
                        }
                        if(curProject!=null){
                        	cPlanInfo.setCurProject(curProject);
                        }
                        if(company!=null){
                        	cPlanInfo.setCompany(company);
                        }

    	    			cPlanInfo.setPayAmount(payAmount);
    	    			cPlanInfo.setPayedAmt(payedAmt);
    	    			cPlanInfo.setPayProportion(payProportion);
    	    			cPlanInfo.setDescription(dec);
    					cPlanInfo.setPayOriAmount(payOriAmount);
    					
    					//�ж��Ƿ�����һ����·�
    					if(nextYear){
    						newYear = year+1;
    						if(month.equals("1") || month.equals("2")){
    							payDate.setYear(newYear-1900);
    						}
    						else{
    							payDate.setYear(year-1900);
    						}
    					}
    					else{
    						payDate.setYear(year-1900);
    					}
    					payDate.setMonth(monthInt-1);
    					cPlanInfo.setPayDate(payDate);
    					//���÷������˵���������
    					ContractPayPlanFactory.getLocalInstance(ctx).addnew(cPlanInfo);
    				}
    			}
    			else{
    				/*
    				 * ѭ��ȡ���ƻ�������Ѹ��������Ϣ
    				 * �ж��ڸ���ƻ������Ƿ��Ѿ��������������µ��е��κ�һ�����͸��´�����¼��
    				 * ���û��ĳ���µĻ������ڲ�ѭ����������һ����¼��
    				 */    			
    				Set seh = pAMap2.keySet();
    				Iterator ith = seh.iterator();
    				while(ith.hasNext()){
    					boolean mFlag=false;
    					int debugAddNew =0;
    					BigDecimal pOriAmount = FDCHelper.ZERO;	//������Ҫ������һ�µ�ԭ�ҽ��
    					String month = (String)ith.next();
    					int monthInt = Integer.parseInt(month);
    					
//    					�ж��Ƿ�����һ����·�
    					if(nextYear){
    						newYear = year+1;
    						if(month.equals("1") || month.equals("2")){
    							payDate.setYear(newYear-1900);
    						}
    						else{
    							payDate.setYear(year-1900);
    						}
    					}
    					else{
    						payDate.setYear(year-1900);
    					}
    					
    					payDate.setMonth(monthInt-1);
    					decItem = cBillId.concat(month);
    					dec = (String) decMap.get(decItem);
    					if(dec != null && dec.length()>80){
    	    				dec = dec.substring(0,80);
    	    			}
    					payedAmt = (BigDecimal)pDMap2.get(month);
    					payAmount = (BigDecimal)pAMap2.get(month);
    					payProportion = (BigDecimal)pPMap2.get(month);
    					
    					boolean zero = false;
    					/*if(payAmount.toString().equalsIgnoreCase("0E-10")||payAmount.toString().equalsIgnoreCase("0.00") 
    							|| payAmount.toString().equalsIgnoreCase("0")){*/
    					if(payAmount.compareTo(FDCHelper.ZERO)==0){
    						zero = true;
    					}
                        
//                      ������һ��Info��������������Ҫ���»������Ӹ���ƻ�ʱ������
                        ContractPayPlanInfo cPlanInfo = new ContractPayPlanInfo();
                        ContractBillInfo con = new ContractBillInfo();
                        cPlanInfo.setContractId(con);
                        
//    					cPlanInfo.setContractId(cBillId);
                        cPlanInfo.getContractId().setId(BOSUuid.read(cBillId));
    					cPlanInfo.setPayDate(payDate);
    					cPlanInfo.setPayProportion(payProportion);
    	    			cPlanInfo.setPayAmount(payAmount);
    	    			cPlanInfo.setPayedAmt(payedAmt);
    	    			cPlanInfo.setDescription(dec);
                        
                        EntityViewInfo conInfo = new EntityViewInfo();
                        FilterInfo confInfo = new FilterInfo();
                        conInfo.getSelector().add("exRate");
                        conInfo.getSelector().add("currency");
                        conInfo.getSelector().add("curProject");
                        conInfo.getSelector().add("curProject.fullOrgUnit");
                        
                        conInfo.setFilter(confInfo);
                        confInfo.appendFilterItem("id", cBillId);
                        ContractBillCollection conColl = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(conInfo);
                        
                        Iterator itcon = conColl.iterator();
                        while(itcon.hasNext()){
                            ContractBillInfo cbInfo = (ContractBillInfo)itcon.next();
                            exRate = cbInfo.getExRate();
                            curProject=cbInfo.getCurProject();
                            if(curProject!=null){
                            	company= (CompanyOrgUnitInfo)curProject.getFullOrgUnit().cast(CompanyOrgUnitInfo.class);
                            }
                            currency=cbInfo.getCurrency();
                        }   
//                        ContractPayPlanCollection cp2 = ContractPayPlanFactory.getLocalInstance(ctx).getContractPayPlanCollection(evInfo);
    					//ѭ��ȡ������ƻ����·ݵ�����
    					Iterator itc = cp.iterator();
    					while(itc.hasNext()){
    						ContractPayPlanInfo cpInfo = (ContractPayPlanInfo)itc.next();
//    						exRate = cpInfo.getExchangeRate();
                            if(exRate!=null){
                                payOriAmount = payAmount.divide(exRate, 2, BigDecimal.ROUND_HALF_UP);
                            }
                            //by Cassiel_peng  2009-10-4
                            if(currency!=null){
                            	cPlanInfo.setCurrecy(currency);
                            }
                            if(curProject!=null){
                            	cPlanInfo.setCurProject(curProject);
                            }
                            if(company!=null){
                            	cPlanInfo.setCompany(company);
                            }
    						cPlanInfo.setPayOriAmount(payOriAmount);
    						
    						IObjectPK entryId = new ObjectUuidPK(cpInfo.getId());
    						if(!zero){
    							if(cpInfo.getPayDate().getYear()==payDate.getYear() && cpInfo.getPayDate().getMonth()==payDate.getMonth()
    									&& cpInfo.getContractId().getId().toString().equals(cBillId)){
    								++debugAddNew;
    								cPlanInfo.setId(cpInfo.getId());
    								mFlag=false;
    								//���÷������˵ĸ��·���
    								ContractPayPlanFactory.getLocalInstance(ctx).update(entryId,cPlanInfo);
    							}
    							else{
    								if((cpInfo.getPayDate().getYear()!=payDate.getYear() || cpInfo.getPayDate().getMonth()!=payDate.getMonth())
    										&& cpInfo.getContractId().getId().toString().equals(cBillId)){
    									if(debugAddNew<1){
    										++debugAddNew;
    										mFlag=true;
    										pOriAmount = payOriAmount;
    									}
    								}
    							}
    						}
    						else{
    							if(cpInfo.getPayDate().getYear()==payDate.getYear() && cpInfo.getPayDate().getMonth()==payDate.getMonth()
    									&& cpInfo.getContractId().getId().toString().equals(cBillId)) {
									FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
									builder.appendSql("delete from T_FNC_ContractPayPlan where FID=?");
									builder.addParam(cpInfo.getId().toString());
									builder.execute();
									builder.clear();
								}
    						}
    					}
    					
    					if(mFlag){
    						cPlanInfo.setPayOriAmount(pOriAmount);
    						//���÷������˵���������
    						ContractPayPlanFactory.getLocalInstance(ctx).addnew(cPlanInfo);
    					}
    				}
    				
    			}
    			
    		}
    	}
    
    }
    protected Map _getContractInfos(Context ctx, Set idSet)throws BOSException, EASBizException
    {
        return null;
    }
    protected void _bindToContract(Context ctx, String contractId,IObjectCollection budgetAcctEntrys)throws BOSException
    {
    	if(contractId==null||budgetAcctEntrys==null||budgetAcctEntrys.size()==0){
    		return;
    	}
    	Set budgetIds = new HashSet();
//    	for(int i=0;i<budgetAcctEntrys.size();i++){
//    		budgetIds.add(
//    				((FDCMonthBudgetAcctEntryInfo)budgetAcctEntrys.getObject(i)).getParent().getId().toString());
//    	}
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	//����ע�ͣ���ͬ��ֵ������Ŀ����Ŀ�¶ȸ���ƻ�  by hpw
    	builder.appendSql("select distinct acc.fid from T_FNC_FDCMonthBudgetAcct acc " +
    			"	inner join t_fnc_fdcmonthbudgetacctentry accentry on acc.fid=accentry.fparentid " +
    			"	inner join t_fnc_fdcmonthbudgetacct acc2 on acc.ffdcperiodid=acc2.ffdcperiodid" +
    			"	where acc.fisLatestVer=1 and accentry.fcontractbillid=? ");
    	builder.addParam(contractId);
    	IRowSet rowSet = builder.executeQuery(ctx);
    	for(int i=0;i<rowSet.size();i++){
    		try {
    			rowSet.next();
				budgetIds.add(rowSet.getString("fid"));
			} catch (SQLException e) {
				throw new BOSException(e);
			}
    	}
    	Set unSettledIdSet=new HashSet();
    	SelectorItemCollection selector=new SelectorItemCollection();
    	selector.add("name");
    	selector.add("number");
    	selector.add("respDept.id");
    	selector.add("id");
    	selector.add("curProject.id");
    	ContractBillInfo contract=null;
    	BigDecimal lstPrice=FDCHelper.ZERO;
    	Map splitMap=new HashMap();
		try {
			contract = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId),selector);
			// ��ͬ�������
			String[] contractids = new String[]{contractId};
			Map lastAmtMap = null;
			try{
				if(contractids.length >0){
					lastAmtMap = ContractBillExecuteDataHander.getLastAmt_Batch(ctx, contractids);
				}
			} catch (Exception e) {
				throw new BOSException(e);
			}
			
			//lstPrice=(BigDecimal)lastAmtMap.get(contractId);
			//update by renliang
			
			if(lastAmtMap!=null){
				lstPrice=(BigDecimal)lastAmtMap.get(contractId);
			}
			
			
			ContractCostSplitEntryCollection costSplitEntrys = getCostSplitEntrys(ctx, contractId, contract.getCurProject().getId().toString());
			for(Iterator iter=costSplitEntrys.iterator();iter.hasNext();){
				ContractCostSplitEntryInfo entry=(ContractCostSplitEntryInfo)iter.next();
				splitMap.put(entry.getCostAccount().getId().toString(), entry.getAmount());
			}
		} catch (EASBizException e1) {
			throw new BOSException(e1);
		}
    	contract.setId(BOSUuid.read(contractId));
    	int seq=1000;
    	//�����µ���ǩ����ͬ��¼ isAdd=true
    	for(Iterator iter=budgetAcctEntrys.iterator();iter.hasNext();){
    		FDCMonthBudgetAcctEntryInfo entry=(FDCMonthBudgetAcctEntryInfo)iter.next();
    		if(entry.getId()!=null){
    			unSettledIdSet.add(entry.getId().toString());
    		}
    		entry.setContractBill(contract);
    		entry.setId(BOSUuid.create(entry.getBOSType()));
    		entry.setIsAdd(true);
    		entry.setItemType(FDCBudgetAcctItemTypeEnum.CONTRACT);
    		entry.setProjectId(contract.getCurProject().getId().toString());
    		entry.setNumber(contract.getNumber());
    		entry.setName(contract.getName());
    		entry.setDeptment(contract.getRespDept());
    		entry.setConLatestPrice(lstPrice);
    		entry.setSplitAmt((BigDecimal)splitMap.get(entry.getCostAccount().getId().toString()));
    		entry.setCreator(ContextUtil.getCurrentUserInfo(ctx));
    		entry.setDesc("��ͬ������ǩ����ͬʱ����");
    		entry.setSeq(seq++);
    		//TODO Ŀ��ɱ�,��̬�ɱ�,������۵�
    		
    		FDCMonthBudgetAcctItemCollection items = entry.getItems();
    		for(Iterator iter2=items.iterator();iter2.hasNext();){
    			FDCMonthBudgetAcctItemInfo item=(FDCMonthBudgetAcctItemInfo)iter2.next();
    			item.setId(BOSUuid.create(item.getBOSType()));
    		}
    	}
    	try {
    		//�����ϵĴ�ǩ����ͬ��¼,һ�����˵ĺ�ͬ���ں�ͬID
    		builder.clear();
    		builder.appendSql("update T_FNC_FDCMonthBudgetAcctEntry set fcontractBillId=null where " +
    				" fcontractBillId=? and ");
    		builder.addParam(contractId);
    		if(budgetIds.size()>0){
        		builder.appendParam("fparentid",budgetIds.toArray());
        		builder.appendSql(" and ");
    		}
    		builder.appendSql(" fitemType=? ");
    		builder.addParam(FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT_VALUE);
    		builder.execute();
    		if(unSettledIdSet.size()>0){
    			builder.clear();
    			builder.appendSql("update T_FNC_FDCMonthBudgetAcctEntry set fcontractBillId=? where ");
    			builder.addParam(contractId);
    			builder.appendParam("fid", unSettledIdSet.toArray());
    			builder.appendSql(" and fitemType=?");
    			builder.addParam(FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT_VALUE);
    			builder.execute();
    		}
    		builder.clear();
    		
    		//��ɾ����ǰ���ɵĺ�ͬ��¼
    		builder.appendSql("delete from T_FNC_FDCMonthBudgetAcctItem where FEntryId in " +
    				" (select fid from T_FNC_FDCMonthBudgetAcctEntry where fcontractBillId=? and fisAdd=1 ");
    		builder.addParam(contractId);
    		if(budgetIds.size()>0){
    			builder.appendSql(" and ");
    			builder.appendParam("fparentid",budgetIds.toArray());
    		}
    		builder.appendSql(" )");
    		builder.execute();
    		builder.clear();
    		builder.appendSql("delete from T_FNC_FDCMonthBudgetAcctEntry where fcontractBillId=? and fisAdd=1  ");
    		builder.addParam(contractId);
    		if(budgetIds.size()>0){
    			builder.appendSql(" and ");
    			builder.appendParam("fparentid",budgetIds.toArray());
    		}
    		builder.execute();
    		//addNew
        	//�ٶԷ�¼����Ŀ�ϲ���Ȼ�����
        	Map entryMap=new HashMap();
        	 for(Iterator iter=budgetAcctEntrys.iterator();iter.hasNext();){
        		FDCMonthBudgetAcctEntryInfo entry=(FDCMonthBudgetAcctEntryInfo)iter.next();
        		String key = entry.getCostAccount().getId().toString();
        		if(entryMap.get(key)!=null){
        			FDCMonthBudgetAcctEntryInfo oldEntryInfo = (FDCMonthBudgetAcctEntryInfo)entryMap.get(key);
    				oldEntryInfo.setAmount(FDCNumberHelper.add(oldEntryInfo.getAmount(), entry.getAmount()));
    				oldEntryInfo.setCost(FDCNumberHelper.add(oldEntryInfo.getCost(), entry.getCost()));
        		}else{
        			entryMap.put(key, entry);
        		}
        	}
        	budgetAcctEntrys.clear();
        	for(Iterator iter=entryMap.values().iterator();iter.hasNext();){
        		budgetAcctEntrys.addObject((FDCMonthBudgetAcctEntryInfo)iter.next());
        	}
    		CoreBaseCollection c=(CoreBaseCollection)budgetAcctEntrys.cast(CoreBaseCollection.class);
    		FDCMonthBudgetAcctEntryFactory.getLocalInstance(ctx).addnew(c);
//			_autoUpdateContractPayPlan(ctx, budgetId);
		} catch (EASBizException e) {
			throw new BOSException(e);
		}
    }
    
    /**
     * �޸ģ�֧�ֿ���Ŀ��֣�ȥ���ù�����Ŀ���˵�����
     */
    protected Map _getAssociateAcctPlan(Context ctx, String prjId,String contractId, IObjectValue period)throws BOSException, EASBizException
    {
    	if(prjId==null||contractId==null||period==null){
    		throw new NullPointerException("some param is null!");
    	}
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	FDCBudgetPeriodInfo fdcPeriod=(FDCBudgetPeriodInfo)period;
    	if(fdcPeriod.getId()==null){
	    	builder.appendSql("select fid from T_FNC_FDCBudgetPeriod where fyear=? and fmonth=? and fisYear=?");
	    	builder.addParam(new Integer(fdcPeriod.getYear()));
	    	builder.addParam(new Integer(fdcPeriod.getMonth()));
	    	builder.addParam(Boolean.FALSE);
	    	IRowSet rowSet=builder.executeQuery();
	    	try {
				if (rowSet.size() > 0) {
					rowSet.next();
					fdcPeriod.setId(BOSUuid.read(rowSet.getString("fid")));
				}
			}catch(SQLException e){
	    		throw new BOSException(e);
	    	}
    	}
    	if(fdcPeriod.getId()==null){
    		throw new FDCBudgetAcctException(FDCBudgetAcctException.PERIODNODATA);
    	}
    	ContractCostSplitEntryCollection costSplitEntrys=getCostSplitEntrys(ctx, contractId, prjId);
    	Set costAccountIds=new HashSet();
    	for(int i=0;i<costSplitEntrys.size();i++){
    		costAccountIds.add(costSplitEntrys.get(i).getCostAccount().getId().toString());
    	}
    	if(costAccountIds.size() ==0){
    		throw new FDCBudgetAcctException(FDCBudgetAcctException.NOFITUNSETTLEDCON);
    	}
    	EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
//    	filter.appendFilterItem("parent.curProject.id", prjId);		-by neo
    	filter.appendFilterItem("parent.fdcPeriod.id", fdcPeriod.getId().toString());
    	filter.appendFilterItem("itemType", FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT_VALUE);
    	filter.getFilterItems().add(new FilterItemInfo("costAccount.id",costAccountIds,CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id",null));
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
    	filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.AUDITTED_VALUE));
    	//���°汾�ƻ�
    	filter.getFilterItems().add(new FilterItemInfo("parent.isLatestVer",Boolean.TRUE));
//    	filter.setMaskString("#0 and #1 and #2 and #3 and (#4 or #5) and #6 and #7");		-by neo
    	filter.setMaskString("#0 and #1 and #2 and (#3 or #4) and #5 and #6");
    	view.setFilter(filter);
    	view.getSelector().add("name");
    	view.getSelector().add("cost");
    	view.getSelector().add("amount");
    	view.getSelector().add("contractBill.id");
    	view.getSelector().add("costAccount.id");
    	view.getSelector().add("costAccount.curProject.name");
    	view.getSelector().add("costAccount.curProject.id");
    	view.getSelector().add("parent.id");
//    	view.getSelector().add("parent.fdcPeriod.*");
    	view.getSelector().add("items.*");
    	FDCMonthBudgetAcctEntryCollection budgetEntrys = FDCMonthBudgetAcctEntryFactory.getLocalInstance(ctx).getFDCMonthBudgetAcctEntryCollection(view);
    	Map budgetEntryMap=new HashMap();
    	for(Iterator iter=budgetEntrys.iterator();iter.hasNext();){
    		FDCMonthBudgetAcctEntryInfo entry=(FDCMonthBudgetAcctEntryInfo)iter.next();
    		String key = entry.getCostAccount().getId().toString();
			FDCMonthBudgetAcctEntryCollection c=(FDCMonthBudgetAcctEntryCollection)budgetEntryMap.get(key);
    		if(c==null){
    			c=new FDCMonthBudgetAcctEntryCollection();
    			budgetEntryMap.put(key,c);
    		}
    		c.add(entry);
    	}
    	Map retMap=new HashMap();
    	if(budgetEntrys.size()>0){
    		String budgetId=budgetEntrys.get(0).getParent().getId().toString();
    		retMap.put("budgetId", budgetId);
    		retMap.put("period", fdcPeriod);
    	}else{
    		//direct get budgetId
    		view=new EntityViewInfo();
    		view.setFilter(new FilterInfo());
    		view.getFilter().appendFilterItem("curProject.id", prjId);
    		//���°汾�ƻ�
    		view.getFilter().appendFilterItem("isLatestVer", Boolean.TRUE);
        	if(fdcPeriod.getId()!=null){
        		filter.appendFilterItem("fdcPeriod.id", fdcPeriod.getId().toString());
        	}else{
        		filter.appendFilterItem("fdcPeriod.year", new Integer(fdcPeriod.getYear()));
        		filter.appendFilterItem("fdcPeriod.month", new Integer(fdcPeriod.getMonth()));
        	}
        	view.getSelector().add("id");
        	view.getSelector().add("fdcPeriod.*");
        	FDCMonthBudgetAcctCollection c = FDCMonthBudgetAcctFactory.getLocalInstance(ctx).getFDCMonthBudgetAcctCollection(view);
        	if(c!=null&&c.size()==1){
        		retMap.put("budgetId", c.get(0).getId().toString());
        		retMap.put("period", c.get(0).getFdcPeriod());
        	}
        	
    	}
    	//get CurProject
    	CurProjectInfo prj=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(prjId));
    	ContractBillInfo contract=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId));
    	retMap.put("curProject", prj);
    	retMap.put("contractBill", contract);
    	if(retMap.get("period")==null){
    		retMap.put("period",period);
    	}
    	//get Contract
    	builder.clear();
    	builder.appendSql("select fcostaccountid from t_fnc_payrequestacctpay actPay ");
    	builder.appendSql("inner join t_fnc_fdcbudgetPeriod period on period.fid=actPay.fperiodid ");
    	builder.appendSql("where actPay.fcontractId=? and period.fyear=? and period.fmonth=? ");
    	builder.addParam(contractId);
    	builder.addParam(new Integer(fdcPeriod.getYear()));
    	builder.addParam(new Integer(fdcPeriod.getMonth()));
    	IRowSet rowSet=builder.executeQuery();
    	Set associateAcctSet=new HashSet();
    	try {
			while (rowSet.next()) {
				associateAcctSet.add(rowSet.getString("fcostaccountid"));
			}
		}catch (SQLException e) {
    		throw new BOSException(e);
    	}
    	
    	retMap.put("budgetEntryMap", budgetEntryMap);
    	retMap.put("costSplitEntrys", costSplitEntrys);
    	retMap.put("associateAcctSet", associateAcctSet);
    	return retMap;
    }
    protected Map _getAssociateAcctPay(Context ctx, String payReqId, IObjectValue period)throws BOSException, EASBizException
    {
    	Map retMap=new HashMap();
    	FDCBudgetPeriodInfo fdcPeriod=(FDCBudgetPeriodInfo)period;
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	//check exist
    	builder.appendSql("select 1 from t_fnc_payrequestacctpay head inner join t_fnc_fdcbudgetperiod period on period.fid=head.fperiodid where fpayrequestbillid=? and (period.fyear<>? or period.fmonth<>?)");
    	builder.addParam(payReqId);
    	builder.addParam(new Integer(fdcPeriod.getYear()));
    	builder.addParam(new Integer(fdcPeriod.getMonth()));
    	if(builder.isExist()){
    		throw new FDCBudgetAcctException(FDCBudgetAcctException.EXISTACCTPAY);
    	}
    	//get period
    	
    	if(fdcPeriod.getId()==null){
    		builder.clear();
	    	builder.appendSql("select fid from T_FNC_FDCBudgetPeriod where fyear=? and fmonth=? and fisYear=?");
	    	builder.addParam(new Integer(fdcPeriod.getYear()));
	    	builder.addParam(new Integer(fdcPeriod.getMonth()));
	    	builder.addParam(Boolean.FALSE);
	    	IRowSet rowSet=builder.executeQuery();
	    	try {
				if (rowSet.size() > 0) {
					rowSet.next();
					fdcPeriod.setId(BOSUuid.read(rowSet.getString("fid")));
				}
			}catch(SQLException e){
	    		throw new BOSException(e);
	    	}
    	}
    	builder.clear();
    	builder.appendSql("select fcontractid,fcurprojectid ,fstate,famount from t_con_payrequestbill where fid=?");
    	builder.addParam(payReqId);
    	IRowSet rowSet=builder.executeQuery();
    	String contractId=null;
    	String prjId=null;
    	String state=null;
    	//�������뵥���
    	BigDecimal amount=FDCHelper.ZERO;
    	if(rowSet.size()==1){
    		try {
				rowSet.next();
				contractId = rowSet.getString("fcontractid");
				prjId = rowSet.getString("fcurprojectid");
				state = rowSet.getString("fstate");
				amount= rowSet.getBigDecimal("famount");
			}catch (SQLException e) {
    			throw new BOSException(e);
    		}
    	}else{
    		return new HashMap();
    	}
    	
    	//û���걨��ȡ����
    	builder.clear();
//    	builder.appendSql("select head.fid from t_fnc_fdcmonthbudgetacct head ");
//    	builder.appendSql("inner join t_fnc_fdcbudgetperiod period on head.ffdcperiodid=period.fid ");
//    	builder.appendSql("where head.fprojectid=? and head.fstate=? and period.fyear=? and period.fmonth=? ");
//    	builder.addParam(prjId);
    	
    	builder.appendSql("	select head.fid from t_fnc_fdcmonthbudgetacct head ");
    	builder.appendSql("	inner join t_fnc_fdcbudgetperiod period on head.ffdcperiodid=period.fid ");
    	builder.appendSql("	inner join t_fnc_fdcmonthbudgetacctentry entr on entr.fparentid=head.fid ");
    	builder.appendSql("	where entr.fcontractbillid=? and head.fstate=? and period.fyear=? and period.fmonth=? ");
    	builder.addParam(contractId);

    	
    	builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
    	builder.addParam(new Integer(fdcPeriod.getYear()));
    	builder.addParam(new Integer(fdcPeriod.getMonth()));
    	boolean isNoContractPlan=false;
    	if(!builder.isExist()){;
//    		throw new FDCBudgetAcctException(new NumericExceptionSubItem("101","����û�����¶ȸ���ƻ�"));
//    		retMap.put("NoData", Boolean.TRUE); û�к�ͬҲ�������  by sxhong
    		isNoContractPlan=true;
    	}
    	ContractCostSplitEntryCollection splitEntrys = getCostSplitEntrys(ctx, contractId, prjId);
    	if(splitEntrys==null||splitEntrys.size()==0){
    		throw new FDCBudgetAcctException(FDCBudgetAcctException.NOSPLITDATA);
    	}
    	//get plan amount
    	Map planAmtMap=new HashMap();
    	if(!isNoContractPlan){
	    	builder.clear();
	    	builder.appendSql("select entry.fcostaccountid fcostacccountid,sum(entry.famount) planamt from  t_fnc_fdcmonthbudgetacctentry entry ");
	    	builder.appendSql("inner join t_fnc_fdcmonthbudgetacct head on entry.fparentid=head.fid ");
	    	builder.appendSql("inner join t_fnc_fdcbudgetperiod period on head.ffdcperiodid=period.fid ");
	    	builder.appendSql("where entry.fcontractbillid=? and entry.fparentid=head.fid  and head.fstate=? and period.fyear=? and period.fmonth=? and entry.fitemtype=? ");
	    	builder.appendSql("and head.fislatestver=1 ");
	    	builder.appendSql("group by entry.fcostaccountid");
	    	builder.addParam(contractId);
	//    	builder.addParam(prjId);
	//    	builder.addParam(prjId);
	    	builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
	    	builder.addParam(new Integer(fdcPeriod.getYear()));
	    	builder.addParam(new Integer(fdcPeriod.getMonth()));
	    	builder.addParam(FDCBudgetAcctItemTypeEnum.CONTRACT_VALUE);
	    	rowSet=builder.executeQuery();
	    	try{
		    	while(rowSet.next()){
		    		planAmtMap.put(rowSet.getString("fcostacccountid"), rowSet.getBigDecimal("planamt"));
		    	}
	    	}catch(SQLException e){
	    		throw new BOSException(e);
	    	}
    	}

    	//get requested amount
    	builder.clear();
    	builder.appendSql("select fcostaccountid,famount,flstallamount from t_fnc_payrequestacctpay where fpayrequestbillid=?");
    	builder.addParam(payReqId);
    	rowSet=builder.executeQuery();
    	Map acctReqAmtMap=new HashMap();//acctReqAmtMap 
    	Map acctReqAllAmtMap=new HashMap();//all amount map 
    	try {
			while (rowSet.next()) {
				acctReqAmtMap.put(rowSet.getString("fcostaccountid"), rowSet.getBigDecimal("famount"));
				if(state!=null&&state.equals(FDCBillStateEnum.AUDITTED_VALUE)){
					acctReqAllAmtMap.put(rowSet.getString("fcostaccountid"), rowSet.getBigDecimal("flstallamount"));
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}

		//request amount total
		builder.clear();
		if(state==null||!state.equals(FDCBillStateEnum.AUDITTED_VALUE)){
			//�޸ĳ��ۼƱ��ڵ��������������뵥�Ŀ�Ŀ���
			builder.appendSql("select fcostaccountid,sum (acctpay.famount) amount from t_fnc_payrequestacctpay acctpay ");
			builder.appendSql("inner join t_con_payrequestbill payreq on payreq.fid=acctpay.fpayrequestbillid  ");
			builder.appendSql("inner join t_fnc_fdcbudgetperiod period on acctpay.FPeriodId=period.fid ");
			builder.appendSql("where payreq.fcontractid=? and acctpay.fcontractid=? and payreq.fstate=? and period.fyear=? and period.fmonth=?");
			builder.appendSql("group by fcostaccountid");
			builder.addParam(contractId);
			builder.addParam(contractId);
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
	    	builder.addParam(new Integer(fdcPeriod.getYear()));
	    	builder.addParam(new Integer(fdcPeriod.getMonth()));
			rowSet=builder.executeQuery();
			try {
				while (rowSet.next()) {
					acctReqAllAmtMap.put(rowSet.getString("fcostaccountid"), rowSet.getBigDecimal("amount"));
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
    	
		for(Iterator iter=splitEntrys.iterator();iter.hasNext();){
			ContractCostSplitEntryInfo entry=(ContractCostSplitEntryInfo)iter.next();
			String key=entry.getCostAccount().getId().toString();
			//�ƻ����
			entry.put("planAmt", planAmtMap.get(key));
			//����������
			entry.put("reqAmt", acctReqAmtMap.get(key));
			//��������
			entry.put("reqAllAmt", acctReqAllAmtMap.get(key));
		}
		//return value
		retMap.put("splitEntrys", splitEntrys);
		
    	//get CurProject
    	CurProjectInfo prj=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(prjId));
//    	ContractBillInfo contract=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId));
    	retMap.put("curProject", prj);
    	retMap.put("contractId", contractId);
    	retMap.put("prjId", prjId);
    	retMap.put("period",period);
    	retMap.put("requestAmount", amount);
    	if(planAmtMap.size()==0){
    		//�ж��Ƿ��к�ͬ�ƻ�
    		retMap.put("noPlan", Boolean.TRUE);
    	}
    	return retMap;
    }
    
    /**
     * ������ֱ�ӹ�����ǩ����ǩ����ͬ<p>
     * ά����ο�_getAssociatePlan(),_getAssociatePay()
     */
	protected Map _getAssociateBudget(Context ctx, String payReqID,
			IObjectValue period) throws BOSException, EASBizException {
		Map retMap=new HashMap();
    	FDCBudgetPeriodInfo fdcPeriod=(FDCBudgetPeriodInfo)period;
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	//check exist
    	builder.appendSql("select 1 from t_fnc_payrequestacctpay head inner join t_fnc_fdcbudgetperiod period on period.fid=head.fperiodid where fpayrequestbillid=? and (period.fyear<>? or period.fmonth<>?)");
    	builder.addParam(payReqID);
    	builder.addParam(new Integer(fdcPeriod.getYear()));
    	builder.addParam(new Integer(fdcPeriod.getMonth()));
    	if(builder.isExist()){
    		throw new FDCBudgetAcctException(FDCBudgetAcctException.EXISTACCTPAY);
    	}
    	//get period
    	if(fdcPeriod.getId()==null){
    		builder.clear();
	    	builder.appendSql("select fid from T_FNC_FDCBudgetPeriod where fyear=? and fmonth=? and fisYear=?");
	    	builder.addParam(new Integer(fdcPeriod.getYear()));
	    	builder.addParam(new Integer(fdcPeriod.getMonth()));
	    	builder.addParam(Boolean.FALSE);
	    	IRowSet rowSet=builder.executeQuery();
	    	try {
				if (rowSet.size() > 0) {
					rowSet.next();
					fdcPeriod.setId(BOSUuid.read(rowSet.getString("fid")));
				}
			}catch(SQLException e){
	    		throw new BOSException(e);
	    	}
    	}
    	builder.clear();
    	builder.appendSql("select fcontractid,fcurprojectid ,fstate,famount from t_con_payrequestbill where fid=?");
    	builder.addParam(payReqID);
    	IRowSet rowSet=builder.executeQuery();
    	String contractId=null;
    	String prjId=null;
    	String state=null;
    	//�������뵥���
    	BigDecimal amount=FDCHelper.ZERO;
    	if(rowSet.size()==1){
    		try {
				rowSet.next();
				contractId = rowSet.getString("fcontractid");
				prjId = rowSet.getString("fcurprojectid");
				state = rowSet.getString("fstate");
				amount= rowSet.getBigDecimal("famount");
			}catch (SQLException e) {
    			throw new BOSException(e);
    		}
    	}else{
    		return new HashMap();
    	}
    	
    	//û���걨��ȡ����
    	builder.clear();
    	builder.appendSql("	select head.fid from t_fnc_fdcmonthbudgetacct head ");
    	builder.appendSql("	inner join t_fnc_fdcbudgetperiod period on head.ffdcperiodid=period.fid ");
    	builder.appendSql("	inner join t_fnc_fdcmonthbudgetacctentry entr on entr.fparentid=head.fid ");
    	builder.appendSql("	where entr.fcontractbillid=? and head.fstate=? and period.fyear=? and period.fmonth=? ");
    	builder.addParam(contractId);
    	
    	builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
    	builder.addParam(new Integer(fdcPeriod.getYear()));
    	builder.addParam(new Integer(fdcPeriod.getMonth()));
    	boolean isNoContractPlan=false;
    	if(!builder.isExist()){;
//    		throw new FDCBudgetAcctException(new NumericExceptionSubItem("101","����û�����¶ȸ���ƻ�"));
//    		retMap.put("NoData", Boolean.TRUE); û�к�ͬҲ�������  by sxhong
    		isNoContractPlan=true;
    	}
    	ContractCostSplitEntryCollection splitEntrys = getCostSplitEntrys(ctx, contractId, prjId);
    	if(splitEntrys==null||splitEntrys.size()==0){
    		throw new FDCBudgetAcctException(FDCBudgetAcctException.NOSPLITDATA);
    	}
    	
    	//get plan amount
    	//cost
    	Map costAmtMap=new HashMap();
    	//pay(auditPay)
    	Map planAmtMap=new HashMap();
    	if(!isNoContractPlan){
	    	builder.clear();
	    	builder.appendSql("select entry.fcostaccountid fcostacccountid,sum(fcost) costamt,sum(entry.famount) planamt from  t_fnc_fdcmonthbudgetacctentry entry ");
	    	builder.appendSql("inner join t_fnc_fdcmonthbudgetacct head on entry.fparentid=head.fid ");
	    	builder.appendSql("inner join t_fnc_fdcbudgetperiod period on head.ffdcperiodid=period.fid ");
	    	builder.appendSql("where entry.fcontractbillid=? and entry.fparentid=head.fid  and head.fstate=? and period.fyear=? and period.fmonth=? and entry.fitemtype=? ");
	    	// ȡ���հ汾 by hpw
	    	builder.appendSql("and head.fislatestver=1 ");
	    	builder.appendSql("group by entry.fcostaccountid");
	    	builder.addParam(contractId);
	    	builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
	    	builder.addParam(new Integer(fdcPeriod.getYear()));
	    	builder.addParam(new Integer(fdcPeriod.getMonth()));
	    	builder.addParam(FDCBudgetAcctItemTypeEnum.CONTRACT_VALUE);
	    	rowSet=builder.executeQuery();
	    	try{
		    	while(rowSet.next()){
		    		costAmtMap.put(rowSet.getString("fcostacccountid"), rowSet.getBigDecimal("costamt"));
		    		planAmtMap.put(rowSet.getString("fcostacccountid"), rowSet.getBigDecimal("planamt"));
		    	}
	    	}catch(SQLException e){
	    		throw new BOSException(e);
	    	}
    	}

    	//get requested amount
    	builder.clear();
    	builder.appendSql("select fcostaccountid,famount,flstallamount from t_fnc_payrequestacctpay where fpayrequestbillid=?");
    	builder.addParam(payReqID);
    	rowSet=builder.executeQuery();
    	Map acctReqAmtMap=new HashMap();//acctReqAmtMap 
    	Map acctReqAllAmtMap=new HashMap();//all amount map 
    	try {
			while (rowSet.next()) {
				acctReqAmtMap.put(rowSet.getString("fcostaccountid"), rowSet.getBigDecimal("famount"));
				if(state!=null&&state.equals(FDCBillStateEnum.AUDITTED_VALUE)){
					acctReqAllAmtMap.put(rowSet.getString("fcostaccountid"), rowSet.getBigDecimal("flstallamount"));
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}

		//request amount total
		builder.clear();
		if(state==null||!state.equals(FDCBillStateEnum.AUDITTED_VALUE)){
			//�޸ĳ��ۼƱ��ڵ��������������뵥�Ŀ�Ŀ���
			builder.appendSql("select fcostaccountid,sum (acctpay.famount) amount from t_fnc_payrequestacctpay acctpay ");
			builder.appendSql("inner join t_con_payrequestbill payreq on payreq.fid=acctpay.fpayrequestbillid  ");
			builder.appendSql("inner join t_fnc_fdcbudgetperiod period on acctpay.FPeriodId=period.fid ");
			builder.appendSql("where payreq.fcontractid=? and acctpay.fcontractid=? and payreq.fstate=? and period.fyear=? and period.fmonth=?");
			builder.appendSql("group by fcostaccountid");
			builder.addParam(contractId);
			builder.addParam(contractId);
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
	    	builder.addParam(new Integer(fdcPeriod.getYear()));
	    	builder.addParam(new Integer(fdcPeriod.getMonth()));
			rowSet=builder.executeQuery();
			try {
				while (rowSet.next()) {
					acctReqAllAmtMap.put(rowSet.getString("fcostaccountid"), rowSet.getBigDecimal("amount"));
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		Set costAccountIds=new HashSet();
		for(Iterator iter=splitEntrys.iterator();iter.hasNext();){
			ContractCostSplitEntryInfo entry=(ContractCostSplitEntryInfo)iter.next();
			String key=entry.getCostAccount().getId().toString();
			costAccountIds.add(key);
			//�ƻ��ɱ����
			entry.put("costAmt", costAmtMap.get(key));
			//�ƻ�������
			entry.put("planAmt", planAmtMap.get(key));
			//����������
			entry.put("reqAmt", acctReqAmtMap.get(key));
			//��������
			entry.put("reqAllAmt", acctReqAllAmtMap.get(key));
		}
		
		//get unSettledContract budgetEntrys
    	EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	filter.appendFilterItem("parent.fdcPeriod.id", fdcPeriod.getId().toString());
    	filter.appendFilterItem("itemType", FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT_VALUE);
    	filter.getFilterItems().add(new FilterItemInfo("costAccount.id",costAccountIds,CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id",null));
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
    	filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.AUDITTED_VALUE));
    	//���°汾�ƻ�
    	filter.getFilterItems().add(new FilterItemInfo("parent.isLatestVer",Boolean.TRUE));
    	filter.setMaskString("#0 and #1 and #2 and (#3 or #4) and #5 and #6");
    	view.setFilter(filter);
    	view.getSelector().add("name");
    	view.getSelector().add("cost");
    	view.getSelector().add("amount");
    	view.getSelector().add("contractBill.id");
    	view.getSelector().add("costAccount.id");
    	view.getSelector().add("costAccount.curProject.name");
    	view.getSelector().add("costAccount.curProject.id");
    	view.getSelector().add("parent.id");
    	view.getSelector().add("items.*");
    	FDCMonthBudgetAcctEntryCollection budgetEntrys = FDCMonthBudgetAcctEntryFactory.getLocalInstance(ctx).getFDCMonthBudgetAcctEntryCollection(view);
    	Map budgetEntryMap=new HashMap();
    	for(Iterator iter=budgetEntrys.iterator();iter.hasNext();){
    		FDCMonthBudgetAcctEntryInfo entry=(FDCMonthBudgetAcctEntryInfo)iter.next();
    		String key = entry.getCostAccount().getId().toString();
			FDCMonthBudgetAcctEntryCollection c=(FDCMonthBudgetAcctEntryCollection)budgetEntryMap.get(key);
    		if(c==null){
    			c=new FDCMonthBudgetAcctEntryCollection();
    			budgetEntryMap.put(key,c);
    		}
    		c.add(entry);
    	}
    	//TODO ���û��Ҫ��?
    	if(budgetEntrys.size()>0){
    		String budgetId=budgetEntrys.get(0).getParent().getId().toString();
    		retMap.put("budgetId", budgetId);
    		retMap.put("period", fdcPeriod);
    	}else{
    		//direct get budgetId
    		view=new EntityViewInfo();
    		view.setFilter(new FilterInfo());
    		view.getFilter().appendFilterItem("curProject.id", prjId);
    		//���°汾�ƻ�
    		view.getFilter().appendFilterItem("isLatestVer", Boolean.TRUE);
        	if(fdcPeriod.getId()!=null){
        		filter.appendFilterItem("fdcPeriod.id", fdcPeriod.getId().toString());
        	}else{
        		filter.appendFilterItem("fdcPeriod.year", new Integer(fdcPeriod.getYear()));
        		filter.appendFilterItem("fdcPeriod.month", new Integer(fdcPeriod.getMonth()));
        	}
        	view.getSelector().add("id");
        	view.getSelector().add("fdcPeriod.*");
        	FDCMonthBudgetAcctCollection c = FDCMonthBudgetAcctFactory.getLocalInstance(ctx).getFDCMonthBudgetAcctCollection(view);
        	if(c!=null&&c.size()==1){
        		retMap.put("budgetId", c.get(0).getId().toString());
        		retMap.put("period", c.get(0).getFdcPeriod());
        	}
        	
    	}
		
    	//get CurProject
    	CurProjectInfo prj=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(prjId));
    	ContractBillInfo contract=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId));//TODO ���Ϊseletor
    	
    	//get Contract
    	builder.clear();
    	builder.appendSql("select fcostaccountid from t_fnc_payrequestacctpay actPay ");
    	builder.appendSql("inner join t_fnc_fdcbudgetPeriod period on period.fid=actPay.fperiodid ");
    	builder.appendSql("where actPay.fcontractId=? and period.fyear=? and period.fmonth=? ");
    	builder.addParam(contractId);
    	builder.addParam(new Integer(fdcPeriod.getYear()));
    	builder.addParam(new Integer(fdcPeriod.getMonth()));
    	rowSet=builder.executeQuery();
    	Set associateAcctSet=new HashSet();
    	try {
			while (rowSet.next()) {
				associateAcctSet.add(rowSet.getString("fcostaccountid"));
			}
		}catch (SQLException e) {
    		throw new BOSException(e);
    	}
		
		//return value
		if(retMap.get("period")==null){
			retMap.put("period",period);
		}
		retMap.put("budgetEntryMap", budgetEntryMap);
		retMap.put("splitEntrys", splitEntrys);
		retMap.put("associateAcctSet",associateAcctSet);
    	retMap.put("curProject", prj);
    	retMap.put("contractBill", contract);
    	retMap.put("contractId", contractId);
    	retMap.put("prjId", prjId);
    	retMap.put("period",period);
    	retMap.put("requestAmount", amount);
    	if(planAmtMap.size()==0){
    		//�ж��Ƿ��к�ͬ�ƻ�
    		retMap.put("noPlan", Boolean.TRUE);
    	}
    	return retMap;
	}
	
    /**
     * �޸ģ�֧�ֿ���Ŀ��֣�������ַ�¼�Ĺ�����Ŀ��ȥ��������Ŀ����  -by neo
     * @param ctx
     * @param contractId
     * @param prjId
     * @return
     * @throws BOSException
     */
	private ContractCostSplitEntryCollection getCostSplitEntrys(Context ctx,String contractId,String prjId) throws BOSException {
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("parent.contractBill.id");
		view.getSelector().add("parent.contractBill.amount");
		view.getSelector().add("parent.contractBill.name");
		view.getSelector().add("parent.contractBill.number");
//		view.getSelector().add("parent.contractBill.number");
		view.getSelector().add("amount");
		view.getSelector().add("costAccount.longNumber");
		view.getSelector().add("costAccount.name");
		view.getSelector().add("costAccount.curProject.id");
		view.getSelector().add("costAccount.curProject.name");
		view.getSelector().add("amount");
		
		filter.appendFilterItem("parent.contractBill.id", contractId);
//		filter.appendFilterItem("costAccount.curProject.id", prjId);  -by neo
		filter.appendFilterItem("costAccount.isLeaf", Boolean.TRUE);
		filter.appendFilterItem("splitType", CostSplitTypeEnum.PRODSPLIT_VALUE);
		filter.appendFilterItem("product.id", null);
		filter.getFilterItems().add(new FilterItemInfo("splitType",CostSplitTypeEnum.PRODSPLIT_VALUE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("splitType",null));
		filter.appendFilterItem("isLeaf", Boolean.TRUE);
		filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
//		filter.setMaskString("#0 and #1 and #2 and ((#3 and #4) or ((#5 or #6) and #7))) and #8");	-by neo
		filter.setMaskString("#0 and #1 and ((#2 and #3) or ((#4 or #5) and #6))) and #7");
		view.getSorter().add(new SorterItemInfo("costAccount.longNumber"));
		ContractCostSplitEntryCollection splitEntrys=ContractCostSplitEntryFactory.getLocalInstance(ctx).getContractCostSplitEntryCollection(view);
		return splitEntrys;
	}
	protected  Map _invokeBudgetCtrl(Context ctx, IObjectValue payReqInfo, String state) throws BOSException, EASBizException{
		//R110317-191��û��������Ŀ�¶ȼƻ����Ƹ���Ĺ��ܣ����������ĸ����������̳����쳣  by zhiyuan_tang 2010-05-17
		String companyId = null;
		PayRequestBillInfo info = (PayRequestBillInfo)payReqInfo;
		if (info.getCurProject() != null) {
			SelectorItemCollection selector = new SelectorItemCollection();
			CurProjectInfo projectInfo = (CurProjectInfo) CurProjectFactory.getLocalInstance(ctx).getValue(
					new ObjectUuidPK(info.getCurProject().getId()), selector);
			String comId = projectInfo.getFullOrgUnit().getId().toString();
			// ���»�ȡ������Ŀ��Ӧ�Ĳ�����֯
			if (comId != null) {
				CompanyOrgUnitInfo company = GlUtils.getCurrentCompany(ctx, comId, null, false);
				if (company != null) {
					companyId = company.getId().toString();
				}
			}
		}
		return FDCBudgetCtrlStrategyFactory.getInstanceByOrgId(ctx, companyId).invokeBudgetCtrl((PayRequestBillInfo)payReqInfo, state);
	}
	
	/**
	 * �����������¶ȼƻ��걨������ͨ����ϵͳ�Զ����¶ȸ���ƻ��걨�����ɶ�Ӧ�¶ȵļ�¼�����Ѵ����¶ȸ���ƻ��걨������¶�Ӧ�����ݡ�
	 * ��������¶ȼƻ��걨��ֱ���������¶ȸ���ƻ��걨�����ݺϲ�ԭ��
	 * 
	 * 1.��ǩ����ͬ��ͬһ�ɱ���Ŀ��ͬһ��ͬ�������µ����ݸ���ԭ�е����ݣ����ۼӡ�
	 * 2.��ǩ����ͬ��ͬһ�ɱ���Ŀ���������У��������һ����¼��B����һ����¼��ԭ��һ����¼���������¶ȼƻ��걨���Ӧ�ɱ���Ŀ�Ĵ�ǩ����ͬ��3����¼��
	 */
	protected void _updateMonthBudget(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		Map depMonEntrys = new HashMap();
		Set contractIds = new HashSet();
		FDCDepMonBudgetAcctInfo depMonBudgetAcctInfo = null;
		FDCDepMonBudgetAcctEntryInfo depMonEntry = null;
		FDCMonthBudgetAcctInfo monthBudgetAcctInfo = null;
		
		// 1.����billIdȡ�������¶ȼƻ��걨����;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("*");
		selector.add("entrys.*");
		selector.add("entrys.items.*");
		depMonBudgetAcctInfo = FDCDepMonBudgetAcctFactory.getLocalInstance(ctx).getFDCDepMonBudgetAcctInfo(new ObjectUuidPK(billId.toString()), selector);
		System.err.println("ͷ"+depMonBudgetAcctInfo.getNumber());
		for(Iterator iter=depMonBudgetAcctInfo.getEntrys().iterator();iter.hasNext();){
			depMonEntry = (FDCDepMonBudgetAcctEntryInfo)iter.next();
			depMonEntrys.put(depMonEntry.getId().toString(), depMonEntry);
			System.err.println(depMonEntry.getCost());
			System.err.println(depMonEntry.getAmount());
			if(depMonEntry.getContractBill()!=null){
				contractIds.add(depMonEntry.getContractBill().getId().toString());
			}
		}
		
		//2.���Ѵ��ڷ�¼��ɾ��
		/*filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sourceId", billId.toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("itemType", FDCBudgetAcctItemTypeEnum.CONTRACT));
		//Ӧ�ð���ͬid������ by sxhong
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id", contractIds, CompareType.INCLUDE));
		filter.setMaskString(" #0 or ( #1 and #2)");
		FDCMonthBudgetAcctEntryFactory.getLocalInstance(ctx).delete(filter);*/
		
		//��setMaskString�������޷�ɾ����ǩ����¼����ɾ��sourceid�ģ���ɾ��ǩ����ͬ�����ݵ� by hpw
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sourceId", billId.toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("itemType",
						FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT));
		FDCMonthBudgetAcctEntryFactory.getLocalInstance(ctx).delete(filter);
		if (contractIds.size() > 0) {
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sourceId", billId.toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("itemType",
							FDCBudgetAcctItemTypeEnum.CONTRACT));
			// Ӧ�ð���ͬid������ by sxhong
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contractIds,
							CompareType.INCLUDE));
			FDCMonthBudgetAcctEntryFactory.getLocalInstance(ctx).delete(filter);
		}
		
		//�������������������ʾ��������������
		filter.getFilterItems().add(
				new FilterItemInfo("curProject.id", depMonBudgetAcctInfo.getCurProject().getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("fdcPeriod.id", depMonBudgetAcctInfo.getFdcPeriod().getId().toString()));
		filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
		// 3.���ݹ�����Ŀ���ڼ�ȡ����Ŀ�¶ȼƻ��걨���°汾����;
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("curProject.id", depMonBudgetAcctInfo.getCurProject().getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("fdcPeriod.id", depMonBudgetAcctInfo.getFdcPeriod().getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("islatestVer", Boolean.FALSE));
		view.getSelector().add("id");
		view.setFilter(filter);
		FDCMonthBudgetAcctCollection colls = FDCMonthBudgetAcctFactory
				.getLocalInstance(ctx).getFDCMonthBudgetAcctCollection(view);

		// 4.������¼
		if (colls != null && colls.size()==1) {
			monthBudgetAcctInfo = colls.get(0);
			monthBudgetAcctInfo.getEntrys().clear();//Ҫ���ԭ�е�����
			for(Iterator iter=depMonEntrys.keySet().iterator();iter.hasNext();){
				String key = (String)iter.next();
				depMonEntry = (FDCDepMonBudgetAcctEntryInfo)depMonEntrys.get(key);
				monthBudgetAcctInfo = setMonthBudgetAcctEntryInfo(ctx,monthBudgetAcctInfo,null,depMonEntry);
			}
			//ֱ��ת��  by sxhong
			CoreBaseCollection entrys=(CoreBaseCollection)monthBudgetAcctInfo.getEntrys().cast(CoreBaseCollection.class);
			//�ػ�����������������������
			FDCMonthBudgetAcctEntryFactory.getLocalInstance(ctx).addnew(entrys);
		}
		// 5.��������Ŀ��Ӧ�ڼ䲻������Ŀ�¶ȼƻ��걨����������
		else {
			checkNumberDup(ctx,depMonBudgetAcctInfo);
			monthBudgetAcctInfo = new FDCMonthBudgetAcctInfo();
			monthBudgetAcctInfo.setId(BOSUuid.create(monthBudgetAcctInfo.getBOSType()));
			monthBudgetAcctInfo.setCurProject(depMonBudgetAcctInfo.getCurProject());
			monthBudgetAcctInfo.setCreator(depMonBudgetAcctInfo.getCreator());
			monthBudgetAcctInfo.setVerNumber(depMonBudgetAcctInfo.getVerNumber());
			monthBudgetAcctInfo.setIsLatestVer(depMonBudgetAcctInfo.isIsLatestVer());
			monthBudgetAcctInfo.setFdcPeriod(depMonBudgetAcctInfo.getFdcPeriod());
			monthBudgetAcctInfo.setOrgUnit(depMonBudgetAcctInfo.getOrgUnit());
			monthBudgetAcctInfo.setState(FDCBillStateEnum.SAVED);
			monthBudgetAcctInfo.setNumber(depMonBudgetAcctInfo.getNumber());
			monthBudgetAcctInfo.setName(depMonBudgetAcctInfo.getName());
			monthBudgetAcctInfo.setCU(depMonBudgetAcctInfo.getCU());
			// 3.1�����Ѵ��ڷ�¼
			for(Iterator iter=depMonBudgetAcctInfo.getEntrys().iterator();iter.hasNext();){
				depMonEntry = (FDCDepMonBudgetAcctEntryInfo)iter.next();
				monthBudgetAcctInfo = setMonthBudgetAcctEntryInfo(ctx,monthBudgetAcctInfo,null,depMonEntry);
			}
			FDCMonthBudgetAcctFactory.getLocalInstance(ctx).addnew(monthBudgetAcctInfo);
		}

	}
	/**
	 * ���ɵĵ��������еĵ��ݱ��벻����ͬ
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkNumberDup(Context ctx,
			FDCDepMonBudgetAcctInfo billInfo) throws BOSException,
			EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
		filter.getFilterItems()
				.add(new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit().getId()));
		if(FDCMonthBudgetAcctFactory.getLocalInstance(ctx).exists(filter)){
			throw new FDCBudgetAcctException(FDCBudgetAcctException.EXISTDUPNUM);
		}
	}
	/**
	 * ���÷�¼����
	 * @param ctx
	 * @param monthBudgetAcctInfo
	 * @param monEntry
	 * @param depMonEntry
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private FDCMonthBudgetAcctInfo setMonthBudgetAcctEntryInfo(
			Context ctx, FDCMonthBudgetAcctInfo monthBudgetAcctInfo, FDCMonthBudgetAcctEntryInfo monEntry,
			FDCDepMonBudgetAcctEntryInfo depMonEntry) throws EASBizException, BOSException {
		if(monEntry==null){
			monEntry = new FDCMonthBudgetAcctEntryInfo();
			monthBudgetAcctInfo.getEntrys().add(monEntry);
		}
		monEntry.putAll(depMonEntry);
		//putAll֮��������parent������
//		monEntry.setParent(monthBudgetAcctInfo);
		monEntry.setId(BOSUuid.create(monEntry.getBOSType()));
		//��¼Դ����ID,����ά��
		monEntry.setSourceId(depMonEntry.getParent().getId().toString());
		FDCDepMonBudgetAcctItemCollection items=(FDCDepMonBudgetAcctItemCollection)depMonEntry.remove("items");
		FDCMonthBudgetAcctItemCollection monthItems = null;
		if(items!=null){
			monthItems = new FDCMonthBudgetAcctItemCollection();
			for(int i=0;i<items.size();i++){
				FDCDepMonBudgetAcctItemInfo depMonItem = items.get(i);
				FDCMonthBudgetAcctItemInfo item = new FDCMonthBudgetAcctItemInfo();
				item.setMonth(depMonItem.getMonth());
				item.setCost(depMonItem.getCost());
				item.setAmount(depMonItem.getAmount());
				// ��ǰ���ڲ���Ҫ�ڷ�¼�ϱ���,��Ϊ�¶ȼƻ��ڱ���ʱ�ᱣ��
				/*if(i==0){
					monEntry.setCost(depMonItem.getCost());
					monEntry.setAmount(depMonItem.getAmount());
				}*/
				item.setEntry(monEntry);
				item.setId(BOSUuid.create(item.getBOSType()));
				monthItems.add(item);
			}
		}
//		FDCMonthBudgetAcctItemCollection monthItems=(FDCMonthBudgetAcctItemCollection)items.cast(FDCMonthBudgetAcctItemCollection.class);
		monEntry.setParent(monthBudgetAcctInfo);
		monEntry.put("items", monthItems);
/*		FDCMonthBudgetAcctEntryCollection entrys = (FDCMonthBudgetAcctEntryCollection)monthBudgetAcctInfo.remove("entrys");
		entrys.add(monEntry);
		monthBudgetAcctInfo.put("entrys", entrys);*/
		
		return monthBudgetAcctInfo;
	}

}
