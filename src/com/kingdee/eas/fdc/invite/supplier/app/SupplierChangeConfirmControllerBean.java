package com.kingdee.eas.fdc.invite.supplier.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.app.TaxerQuaEnum;
import com.kingdee.eas.fdc.invite.supplier.EnterpriseKindEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierQuaLevelEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;

public class SupplierChangeConfirmControllerBean extends AbstractSupplierChangeConfirmControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.SupplierChangeConfirmControllerBean");
    

    @Override
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	checkOprt(billId,FDCBillStateEnum.SUBMITTED,ctx,"Audit");
    	super._audit(ctx, billId);
    	_changeSupplierInfo(ctx,billId);
    	
    }
    
    private void checkOprt(BOSUuid billId,FDCBillStateEnum state,Context ctx,String oprt) throws EASBizException, BOSException{
    	SupplierChangeConfirmInfo info = SupplierChangeConfirmFactory.getLocalInstance(ctx).getSupplierChangeConfirmInfo(new ObjectUuidPK(billId));
    	FDCBillStateEnum currState = info.getState();
    	List<FDCBillStateEnum> canOprtState = new ArrayList<FDCBillStateEnum>();
    	if("Audit".equals(oprt)){
    		canOprtState.add(FDCBillStateEnum.SUBMITTED);
    		canOprtState.add(FDCBillStateEnum.AUDITTING);
    	}else if("UnAudit".equals(oprt)){
    		canOprtState.add(FDCBillStateEnum.AUDITTED);
    	}
    	if(!canOprtState.contains(currState)){
    		throw new ContractException(ContractException.WITHMSG,new String[]{"当前状态的单据不适合此操作."});
    	}
    }
    
    @Override
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	checkOprt(billId,FDCBillStateEnum.AUDITTED,ctx,"UnAudit");
    	super._unAudit(ctx, billId);
    }
    
    
    @Override
    protected void _changeSupplierInfo(Context ctx, BOSUuid billID)
    		throws BOSException, EASBizException {

    	SupplierChangeConfirmInfo info = SupplierChangeConfirmFactory.getLocalInstance(ctx).getSupplierChangeConfirmInfo(new ObjectUuidPK(billID));
    	SupplierChangeEntryCollection cols = info.getEntry();
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	//更新信息到供应商信息
    	SelectorItemCollection sic = new SelectorItemCollection();
    	SupplierStockInfo supplierInfo = info.getSupplier();
    	Iterator<SupplierChangeEntryInfo> it = cols.iterator();
    	SupplierChangeEntryInfo entryInfo  = null;
    	String key = null;
    	String value = null;
    	for(;it.hasNext();){
    		entryInfo = it.next();
    	 	key = entryInfo.getPropertyName();
    		value  = entryInfo.getPropertyValue();
    		if(StringUtils.isEmpty(value)){
    			continue;
    		}
    		if(key.equalsIgnoreCase("province")){
				builder.appendSql("update t_fdc_supplierstock set fprovinceid = ? where fid = ?");
				builder.addParam(value);
				builder.addParam(supplierInfo.getId().toString());
				logger.info(builder.getTestSql());
				builder.executeUpdate();
				continue;
			}
    		if(key.equalsIgnoreCase("city")){
    			builder.clear();
    			builder.appendSql("update t_fdc_supplierstock set fcityid = ? where fid = ?");
				builder.addParam(value);
				builder.addParam(supplierInfo.getId().toString());
				logger.info(builder.getTestSql());
				builder.executeUpdate();
    			continue;
    		}
    		if(key.equalsIgnoreCase("enterpriseKind")){
    			sic.add("enterpriseKind");
    			EnterpriseKindEnum kindEnum = EnterpriseKindEnum.getEnum(value);
    			supplierInfo.put(key, kindEnum);
    			continue;
    		}
    		if(key.equalsIgnoreCase("taxerQua")){
    			sic.add("taxerQua");
    			TaxerQuaEnum taxerQuaEnum = TaxerQuaEnum.getEnum(value);
    			supplierInfo.put(key, taxerQuaEnum);
    			continue;
    		}
			if(key.equalsIgnoreCase("quaLevel")){
				String [] qulevels = value.split(",");
				SupplierQuaLevelEntryCollection quaEntry = new SupplierQuaLevelEntryCollection();
				List<List<String>> param = new ArrayList<List<String>>();
				for(int i=0;i<qulevels.length;i++){
					String q = qulevels[i];
					List<String>paramList = new ArrayList<String>();
					paramList.add(q);
					paramList.add(supplierInfo.getId().toString());
					param.add(paramList);					
					
				}
				
				builder.clear();
				String sql = "insert into t_gys_supplierqualevelentry(fqualevelid,fheadid,fid)values(?,?,newbosid('9C049447'))";
				builder.executeBatch(sql,param);
				continue;
			}
			
			
			supplierInfo.put(key, value);
			sic.add(key);
    	}
    	SupplierStockFactory.getLocalInstance(ctx).updatePartial(supplierInfo, sic);

    }
    
    @Override
    protected IObjectValue _getValue(Context ctx, IObjectPK pk)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("supplier.*");
    	sic.add("supplier.province.*");
    	sic.add("supplier.city.*");
    	sic.add("supplier.quaLevelEntry.*");
    	sic.add("entry.*");
    	return _getValue(ctx, pk,sic);
    }
}