package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import java.text.DateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class SaleScheduleOnLoadDetailReportFacadeControllerBean extends AbstractSaleScheduleOnLoadDetailReportFacadeControllerBean
{
  private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.SaleScheduleOnLoadDetailReportFacadeControllerBean");

  protected RptParams _init(Context ctx, RptParams params) throws BOSException, EASBizException
  {
    RptParams pp = new RptParams();
    return pp;
  }
  private void initColoum(RptTableHeader header, RptTableColumn col, String name, int width, boolean isHide) {
    col = new RptTableColumn(name);
    col.setWidth(width);
    col.setHided(isHide);
    header.addColumn(col);
  }

  protected RptParams _createTempTable(Context ctx, RptParams params) throws BOSException, EASBizException {
    RptTableHeader header = new RptTableHeader();
    RptTableColumn col = null;
    Boolean type = (Boolean)params.getObject("type");
    if(type){
    	initColoum(header, col, "id", 150, true);
        initColoum(header, col, "sellProject", 100, false);
        initColoum(header, col, "room", 320, false);
        initColoum(header, col, "customer", 100, false);
        initColoum(header, col, "bizDate", 70, false);
        initColoum(header, col, "contractTotalAmount", 100, false);
        initColoum(header, col, "onLoadAmount", 100, false);
        initColoum(header, col, "rate", 60, false);
        initColoum(header, col, "blrq", 100, false);
        initColoum(header, col, "afbizDate", 110, false);
        initColoum(header, col, "afdays", 60, false);
        initColoum(header, col, "srcsaleMans", 50, false);
        initColoum(header, col, "saleMans", 50, false);
        initColoum(header, col, "moneyDefine", 150, false);
        initColoum(header, col, "description", 150, false);
        initColoum(header, col, "approach2",120,false);
        initColoum(header, col, "barq",100,false);
        initColoum(header, col, "approach3",120,false);
        initColoum(header, col, "approach4",130,false);
        initColoum(header, col, "loanBank",150,false);
        initColoum(header, col, "checkAmount", 150, true);
        header.setLabels(new Object[][] { 
          { 
          "Id", "��Ŀ", "����", "�ͻ�","ǩԼ����","��ͬ�ܼ�","��;���","��;��","��ͬԼ�����Ұ�������","��ͬԼ���¿�����","��������","ҵ��Ա","������","��������","ԭ��˵��","��������׼�����","��������","�����������","����Ԥ�֣���Ѻ�����","��������","checkAmount"} }, 
          true);
    }else{
    	initColoum(header, col, "id", 150, true);
        initColoum(header, col, "sellProject", 100, false);
        initColoum(header, col, "room", 320, false);
        initColoum(header, col, "customer", 100, false);
        initColoum(header, col, "bizDate", 70, false);
        initColoum(header, col, "contractTotalAmount", 100, false);
        initColoum(header, col, "onLoadAmount", 100, false);
        initColoum(header, col, "rate", 60, false);
        initColoum(header, col, "srcsaleMans", 50, false);
        initColoum(header, col, "saleMans", 50, false);
        initColoum(header, col, "moneyDefine",70, false);
        initColoum(header, col, "appAmount", 100, false);
        initColoum(header, col, "appDate", 70, false);
        initColoum(header, col, "actAmount", 100, false);
        initColoum(header, col, "actDate", 70, false);
        initColoum(header, col, "afdays", 60, false);
        initColoum(header, col, "description", 150, false);
        initColoum(header, col, "checkAmount", 150, true);
        header.setLabels(new Object[][] { 
          { 
          "Id", "��Ŀ", "����", "�ͻ�","ǩԼ����","��ͬ�ܼ�","��;���","��;��","ҵ��Ա","������","����","Ӧ�ս��","Ӧ������","ʵ�ս��","ʵ������","��������","ԭ��˵��","checkAmount"} }, 
          true);
    }
    
    params.setObject("header", header);
    return params;
  }
  protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException {
	  Date fromDate = (Date)params.getObject("fromDate");
	  Date toDate = (Date)params.getObject("toDate");
    String orgId = (String)params.getObject("orgId");
    Boolean type = (Boolean)params.getObject("type");
    StringBuffer sb = new StringBuffer();
    if (type){
    	sb.append(" select sign.fid id,sp.fname_l2 sellProject,room.fname_l2 room,sign.fcustomerNames customer,sign.fbizDate bizDate,sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0) contractTotalAmount,sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0) onLoadAmount,(sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0))*100/(sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)) rate,''blrq,DATEADD(day,60,sign.fbizDate)afbizDate,DATEDIFF(day,sign.fbizDate,getdate())afdays,srcsaleMans.fname_l2 srcsaleMans,sign.FSaleManNames saleMans,md.fname_l2 moneyDefine,loan.fdescription description,approach2.actdate approach2,''barq,approach3.actdate approach3,approach4.actdate approach4,bank.fname_l2 loanBank,sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0)-unRev.unAmount checkAmount from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId");
        sb.append(" left join t_she_sellProject sp on sp.fid=sign.fsellProjectid left join t_she_room room on room.fid=sign.froomId left join T_PM_User srcsaleMans on srcsaleMans.fid=sign.fsalesmanId");
//        sb.append(" left join T_BDC_SHERevBill revBill on revBill.frelatetransId=sign.ftransactionId left join T_BDC_SHERevBillEntry entry on entry.fparentid=revBill.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
        sb.append(" left join (select sum(isnull(entry.famount,0)+isnull(entry.frevAmount,0)) amount,revBill.frelateTransId billId from T_BDC_SHERevBill revBill left join T_BDC_SHERevBillEntry entry on entry.fparentid=revBill.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
        sb.append(" where 1=1");
//        if(fromDate!=null){
//			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
//		}
//		if(toDate!=null){
//			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
//		}
		 sb.append(" and revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') group by revBill.frelateTransId)sumRevBill on sumRevBill.billId=sign.ftransactionId");
	       
        sb.append(" left join (select fsignId,fid,fBankId,FMmType,fdescription from T_SHE_RoomLoan where FAFMortgagedState!=4) loan on sign.fid =loan.fsignId");
        sb.append(" left join (select FActualFinishDate actdate,fparentid from T_SHE_RoomLoanAFMEntrys where FApproach='��������׼�����')approach2 on approach2.fparentid=loan.fid");
    	sb.append(" left join (select FActualFinishDate actdate,fparentid from T_SHE_RoomLoanAFMEntrys where FApproach='�����������')approach3 on approach3.fparentid=loan.fid");
    	sb.append(" left join (select FActualFinishDate actdate,fparentid from T_SHE_RoomLoanAFMEntrys where FApproach='����Ԥ�֣���Ѻ��')approach4 on approach4.fparentid=loan.fid");
    	sb.append(" left join T_BD_Bank bank on bank.fid=loan.fBankId left join t_she_moneyDefine md on md.fid=loan.FMmType");
    	
    	sb.append(" LEFT JOIN (select sum(ov.fappAmount-isnull(sumSherevbill.actAmount,0)) unAmount,t.FBILLID billId from T_SHE_Transaction t");
 		sb.append(" LEFT  JOIN T_SHE_TranBusinessOverView ov on t.FID = ov.FHeadID");
 		sb.append(" LEFT JOIN t_she_moneyDefine md on md.fid=ov.fmoneyDefineId");
 		sb.append(" LEFT  JOIN (select sum(revmap.famount) actAmount,revmap.FPayListEntryId from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId) sumSherevbill on sumSherevbill.FPayListEntryId=ov.fid where ov.ftype='Pay' and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') group by t.fbillId)unRev on unRev.billId=sign.fid");
 		
    	
        sb.append(" where sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0)>0");
//        sb.append(" and ((revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount'))or revBill.fid is null)");
    	sb.append(" and sign.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit')");
        sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED','3AUDITTING') and sign.fid=tt.fnewId )");
        sb.append(" and EXISTS (select t1.fid from t_she_signManage t1 left join t_she_signPayListEntry t2 on t2.fheadid=t1.fid left join t_she_moneyDefine md on md.fid=t2.fmoneyDefineId where t1.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit') and md.fmoneyType in('LoanAmount','AccFundAmount') and sign.fid=t1.fid )");
//        if(fromDate!=null){
//			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
//		}
        if (toDate != null) {
        	sb.append(" and sign.fbusAdscriptionDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate)) + "'}");
        }
        if (orgId != null) {
        	sb.append(" and org.fid ='" + orgId + "'");
        }
        sb.append(" order by sp.flongNumber,room.fnumber");
    }else{
    	sb.append(" select sign.fid id,sp.fname_l2 sellProject,room.fname_l2 room,sign.fcustomerNames customer,sign.fbizDate bizDate,sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0) contractTotalAmount,sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0) onLoadAmount,(sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0))*100/(sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)) rate,srcsaleMans.fname_l2 srcsaleMans,sign.FSaleManNames saleMans,md.fname_l2 moneyDefine,ov.FAppAmount appAmount,ov.FAppDate appDate,isnull(sumSherevbill.actAmount,0) actAmount,sumSherevbill.actDate actDate,DATEDIFF(day,ov.FAppDate, getdate()) afdays,''description,sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0)-unRev.unAmount checkAmount from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId");
        sb.append(" left join t_she_sellProject sp on sp.fid=sign.fsellProjectid left join t_she_room room on room.fid=sign.froomId left join T_PM_User srcsaleMans on srcsaleMans.fid=sign.fsalesmanId");
        sb.append(" left join (select sum(isnull(entry.famount,0)+isnull(entry.frevAmount,0)) amount,revBill.frelateTransId billId from T_BDC_SHERevBill revBill left join T_BDC_SHERevBillEntry entry on entry.fparentid=revBill.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
        sb.append(" where 1=1");
//        if(fromDate!=null){
//			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
//		}
//		if(toDate!=null){
//			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
//		}
		sb.append(" and revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') group by revBill.frelateTransId)sumRevBill on sumRevBill.billId=sign.ftransactionId");
        
        sb.append(" LEFT  JOIN T_SHE_Transaction t on t.FBILLID = sign.FID ");
		sb.append(" LEFT  JOIN T_SHE_TranBusinessOverView ov on t.FID = ov.FHeadID");
		sb.append(" LEFT JOIN t_she_moneyDefine md on md.fid=ov.fmoneyDefineId");
		sb.append(" LEFT  JOIN (select sum(revmap.famount) actAmount,revmap.FPayListEntryId,max(revbill.fbizDate) actDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId) sumSherevbill on sumSherevbill.FPayListEntryId=ov.fid");
		
		sb.append(" LEFT JOIN (select sum(ov.fappAmount-isnull(sumSherevbill.actAmount,0)) unAmount,t.FBILLID billId from T_SHE_Transaction t");
 		sb.append(" LEFT  JOIN T_SHE_TranBusinessOverView ov on t.FID = ov.FHeadID");
 		sb.append(" LEFT JOIN t_she_moneyDefine md on md.fid=ov.fmoneyDefineId");
 		sb.append(" LEFT  JOIN (select sum(revmap.famount) actAmount,revmap.FPayListEntryId from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId) sumSherevbill on sumSherevbill.FPayListEntryId=ov.fid where ov.ftype='Pay' and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') group by t.fbillId)unRev on unRev.billId=sign.fid");
 		
        sb.append(" where ov.ftype='Pay' and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') and ov.FAppAmount-isnull(sumSherevbill.actAmount,0)>0 and sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0)>0");
    	sb.append(" and sign.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit')");
        sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED','3AUDITTING') and sign.fid=tt.fnewId )");
        sb.append(" and NOT EXISTS (select t1.fid from t_she_signManage t1 left join t_she_signPayListEntry t2 on t2.fheadid=t1.fid left join t_she_moneyDefine md on md.fid=t2.fmoneyDefineId where t1.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit') and md.fmoneyType in('LoanAmount','AccFundAmount') and sign.fid=t1.fid )");
//        if(fromDate!=null){
//			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
//		}
        if (toDate != null) {
        	sb.append(" and sign.fbusAdscriptionDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate)) + "'}");
        }
        if (orgId != null) {
        	sb.append(" and org.fid ='" + orgId + "'");
        }
        sb.append(" order by sp.flongNumber,room.fnumber,md.fnumber,ov.fappDate");
    }
    RptRowSet rowSet = executeQuery(sb.toString(), null, ctx);
    params.setObject("rowset", rowSet);
    return params;
  }
}