package com.kingdee.eas.fdc.invite.supplier.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		������������
 *		
 * @author		��ΰ
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see						
 */
public class FDCSplPeriodAuditBillControllerBean extends AbstractFDCSplPeriodAuditBillControllerBean
{
    /**
	 * 1.0
	 */
	private static final long serialVersionUID = -5246587620336374968L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.FDCSplPeriodAuditBillControllerBean");
    
    /**
     * @description		����ʱ�Թ�Ӧ�̸ı�״̬
     * @author			��ΰ		
     * @createDate		2010-12-9
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
	  EASBizException {    	
      super._audit(ctx, billId); 
        /*
         * ��д��Ӧ��״̬
         */
    	String sql = "";
    	try {
	    	List list = new ArrayList();
	    	list.add(billId);
	        /*
	         * ������ʷ��һ
	         */
            sql ="update T_FDC_SupplierStock  set FappraiseHis=FappraiseHis+1  where FID=(select FSupplierID from T_SPL_FDCSplPeriodAuditBill as se where se.FID =? )";
			this.querySQL(ctx,sql,list,false,0);
	        /*
	         * ��ȡ��������
	         */
			sql="Select auditBillEntry.fgrade ,serviceType.fid,auditBill.FSupplierID,auditBillEntry.FSupplierTypeID,serviceType.FSeq from T_SPL_FDCSplPeriAuditBillEntry as auditBillEntry "+
    	      " left   JOIN   T_SPL_FDCSplPeriodAuditBill as auditBill  on   auditBill.fid = auditBillEntry.FAuditBillID  "+
    	      " left    JOIN  T_SPL_FDCSupplierServiceType as serviceType on   auditBillEntry.FSupplierTypeID=serviceType.FServiceTypeID and auditBill.FSupplierID=serviceType.FSupplierID "+
    	      " where   auditBill.FId = ?"+
    	      " order by serviceType.FSeq desc ";
			List valueList = this.querySQL(ctx,sql,list,true,5);
			int maxSeq = 0;
			for(int i=0;i<valueList.size();i++){
				List listValue = (List)valueList.get(i);
				
				String grade = (String)(listValue.get(0)==null?"":listValue.get(0));
				String fid = (String)(listValue.get(1)==null?"":listValue.get(1));
				String supplierID = (String)(listValue.get(2)==null?"":listValue.get(2));	
				String serviceTypeID = (String)(listValue.get(3)==null?"":listValue.get(3));	
				String seqStr =(String)(listValue.get(4)==null?"0":listValue.get(4));
				int seq =Integer.parseInt(seqStr);
				if(maxSeq<seq){
					maxSeq = seq;
				}
				String sqlUpdate = "";
				if(!grade.equals("") && !fid.equals("")){
					/*
					 * �Եȼ���Ϊ�յ�ͬʱ�ڹ�Ӧ���д��ڵĹ�Ӧ�����ͻ�д
					 */
					sqlUpdate ="update T_SPL_FDCSupplierServiceType set FState =? where FID=?";
					list = new ArrayList();
					list.add(grade);
					list.add(fid);
					this.querySQL(ctx,sqlUpdate,list,false,0);
				}else if(!grade.equals("") && fid.equals("")){
					/*
					 * �Եȼ���Ϊ�յ�ͬʱ�ڹ�Ӧ���в����ڵĹ�Ӧ����������
					 */
					sqlUpdate ="insert into T_SPL_FDCSupplierServiceType (FState,FSupplierID,FServiceTypeID,FSeq,FID) values "+
                             "(?,?,?,?,? )";
					fid = new ObjectUuidPK(new BOSObjectType("591DD2B6")).toString(); 
					maxSeq=maxSeq+1;
					list = new ArrayList();
					list.add(grade);
					list.add(supplierID);
					list.add(serviceTypeID);		
					list.add(String.valueOf(maxSeq));	
					list.add(fid);
					this.querySQL(ctx,sqlUpdate,list,false,0);
				}
				
			}

    	} catch (SQLException e) {
			logger.error(e);
		}


     }
    /**
     * 
     * @description		��ѯͨ�÷���
     * @author			��ΰ		
     * @createDate		2010-11-21
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see
     */
    private List querySQL(Context ctx,String sql,List valueList,boolean boo,int valueSize) throws BOSException, SQLException{
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	List list = new ArrayList();
    	
    	builder.appendSql(sql);
    	for(int i = 0 ; i < valueList.size() ; i++){
    		builder.addParam(valueList.get(i).toString().trim());
    	}
    	if(boo){
	    	IRowSet irowSet = builder.executeQuery();
	    	while(irowSet.next()){
	    		int i = 1 ;
	    		List lists  = new ArrayList();
	    		for(int j = 0 ; j < valueSize ; j++){
	    			lists.add(irowSet.getString(i++));
	    		}
	    		list.add(lists);
	    		
	    	}
	    	return list ;
    	}else{
    		builder.executeUpdate();
    		return null ;
    	}
    }
    /**
     * @description		
     * @author			��ΰ		
     * @createDate		2010-11-17
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		return super._save(ctx, model);
	}
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		return super._submit(ctx, model);
	}
    /**
     * @description		��������д
     * @author			��ΰ		
     * @createDate		2010-12-9
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._unAudit(ctx, billId);
		List list = new ArrayList();
		list.add(billId);
		try {
			String sql = "update T_FDC_SupplierStock  set FappraiseHis=FappraiseHis-1  where FID=(select FSupplierID from T_SPL_FDCSplPeriodAuditBill as se where se.FID =? )";
			this.querySQL(ctx, sql, list, false, 0);
		} catch (SQLException e) {
			logger.error(e);
		}
	}
    
    protected boolean isNeedWriteBack(Context ctx, FDCSplAuditBaseBillInfo info) throws BOSException, EASBizException {
    	
    	//��ȡȫ���ĺϸ�Ӧ�̵Ǽ�
    	HashMap gradesMap = new HashMap(); 
    	GradeSetUpCollection grades = GradeSetUpFactory.getLocalInstance(ctx).getGradeSetUpCollection(new EntityViewInfo());
    	GradeSetUpInfo grade = null;
    	for (Iterator it = grades.iterator(); it.hasNext();) {
    		grade = (GradeSetUpInfo)it.next();
    		if (grade != null) {
//    			gradesMap.put(grade.getName(), grade.getIsGrade());
    		}
    	}
    	//����õ������жԹ�Ӧ�̵ķ���������ۣ�����������һ������Ǻϸ�ģ�����Ҫ��Ӧ�������ݻ�д
    	if (info instanceof FDCSplPeriodAuditBillInfo) {
    		FDCSplPeriodAuditBillInfo periodInfo = (FDCSplPeriodAuditBillInfo) info;
    		FDCSplPeriodAuditBillEntryCollection entrys = periodInfo.getAuditBill();
    		if (entrys != null) {
    			for (Iterator it = entrys.iterator(); it.hasNext();) {
    				FDCSplPeriodAuditBillEntryInfo entry = (FDCSplPeriodAuditBillEntryInfo)it.next();
    				if (entry.isAvailable()) {
    					IsGradeEnum isGrade = (IsGradeEnum)gradesMap.get(entry.getGrade());
    					if(isGrade != null && IsGradeEnum.ELIGIBILITY.equals(isGrade)) {
    						return true;
    					}
    				}
    			}
    		}
    	}
    	return false;
    }
}