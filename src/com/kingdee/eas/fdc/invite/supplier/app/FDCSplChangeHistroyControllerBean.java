package com.kingdee.eas.fdc.invite.supplier.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.supplier.FDCSplChangeHistroyInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierException;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		��Ӧ����Ϣ���
 *		
 * @author		��ΰ
 * @version		EAS7.0		
 * @createDate	2010-12-9	 
 * @see						
 */
public class FDCSplChangeHistroyControllerBean extends AbstractFDCSplChangeHistroyControllerBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6671543567314441431L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.FDCSplChangeHistroyControllerBean");
    /**
     * @description		����ʱ��Թ�Ӧ�̸ı�汾��״̬
     * @author			��ΰ		
     * @createDate		2010-11-26
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	IObjectPK pk = new ObjectUuidPK(billId.toString());
		FDCSplChangeHistroyInfo info = super.getFDCSplChangeHistroyInfo(ctx, pk );
		SupplierStockInfo supplier= info.getSupplier(); 
    	List list = new ArrayList();
    	list.add(supplier.getId().toString());
        /*
         * �������Ĺ�Ӧ���Ƿ���������Ϊtrue����ԭ��Ӧ���Ƿ���������Ϊfalse
         */
        String sql ="";
		try {
			sql="update T_FDC_SupplierStock  set  FIsLatestVer=1 , fstate=3 where FSrcSupplierID=? and fversion>1";
			this.querySQL(ctx,sql,list,false,0);
			sql="update T_FDC_SupplierStock  set  FIsLatestVer=0 where fid=?";
			this.querySQL(ctx,sql,list,false,0);

		} catch (SQLException e) {
			logger.error(e);
		}
    	super._audit(ctx, billId);
    }
    
    /**
     * @description		��������״̬��д
     * @author			��ΰ		
     * @createDate		2010-11-29
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	IObjectPK pk = new ObjectUuidPK(billId.toString());
		FDCSplChangeHistroyInfo info = super.getFDCSplChangeHistroyInfo(ctx, pk );
		SupplierStockInfo supplier= info.getSupplier(); 
    	List list = new ArrayList();
    	list.add(supplier.getId().toString());
        /*
         * �������Ĺ�Ӧ���Ƿ���������Ϊfalse����ԭ��Ӧ���Ƿ���������Ϊtrue
         */
        String sql ="";
		try {
			//����и��µİ汾�Ͳ��ܷ�����
			sql="select FIsLatestVer from T_FDC_SupplierStock where FIsLatestVer >( select max( FIsLatestVer) from T_FDC_SupplierStock where FSrcSupplierID=?) and fnumber = ( select distinct fnumber from T_FDC_SupplierStock where FSrcSupplierID=?)";
			list.add(supplier.getId().toString());
			List supplierNumber =this.querySQL(ctx,sql,list,true,1);
			if(supplierNumber != null && supplierNumber.size()>0){
				
				throw new SupplierException(SupplierException.UNAUDITEXCEPTION);
			}
			list = new ArrayList();
	    	list.add(supplier.getId().toString());

			sql="update T_FDC_SupplierStock  set  FIsLatestVer=0 , fstate=3 where FSrcSupplierID=? and fversion>1";
			this.querySQL(ctx,sql,list,false,0);
			sql="update T_FDC_SupplierStock  set  FIsLatestVer=1, fstate=3 where fid=?";
			this.querySQL(ctx,sql,list,false,0);
		} catch (SQLException e) {
			logger.error(e);
		}
    	super._unAudit(ctx, billId);
    }
    /**
     * @description		ͨ�ò�ѯ����
     * @author			��ΰ		
     * @createDate		2010-11-26
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
}