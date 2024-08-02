package com.kingdee.eas.basedata.master.cssp.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import net.sf.json.JSONObject;

import java.lang.String;

import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitCollection;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.app.ContextUtil;

public class CustomerImportFacadeControllerBean extends AbstractCustomerImportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.basedata.master.cssp.app.CustomerImportFacadeControllerBean");

	@Override
	protected String _customerImport(Context ctx, String str)
			throws BOSException, EASBizException {
		JSONObject obj = JSONObject.fromObject(str);	
		JSONObject rs = new JSONObject();
		try {
			
			if(obj.get("number")==null||"".equals(obj.getString("number").trim())){
				rs.put("state", "0");
				rs.put("msg", "编码不能为空！");
			}
			if(obj.get("name")==null||"".equals(obj.getString("name").trim())){
				rs.put("state", "0");
				rs.put("msg", "名称不能为空！");
			}	
			CustomerCollection cusCol=CustomerFactory.getLocalInstance(ctx).getCustomerCollection("select * from where number='"+obj.getString("number")+"'");
			if(cusCol.size()>0){
				SelectorItemCollection selCol = new SelectorItemCollection();
				selCol.add("number");
				selCol.add("name");
				
				CustomerInfo cus =cusCol.get(0);
				cus.setNumber(obj.getString("number"));
				cus.setName(obj.getString("name"));
				CustomerFactory.getLocalInstance(ctx).updatePartial(cus, selCol);
			}else{
				CustomerInfo cus =new CustomerInfo();
				CtrlUnitInfo cu = new CtrlUnitInfo();
				cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
				cus.setId(BOSUuid.create(CustomerInfo.getBosType()));
				cus.setCU(cu);
				cus.setAdminCU(cu);
				
				CSSPGroupInfo groupInfo =null;
				CustomerGroupDetailInfo Gdinfo = null;
				CSSPGroupCollection groupCol = CSSPGroupFactory.getLocalInstance(ctx).getCSSPGroupCollection("select number,name,groupStandard.id from where groupStandard.id='00000000-0000-0000-0000-000000000002BC122A7F' and cu.id='"+OrgConstants.DEF_CU_ID+"'");
				if(groupCol.size()>0){
					groupInfo=groupCol.get(0);
					cus.setBrowseGroup(groupInfo);
		    		
		    		Gdinfo = new CustomerGroupDetailInfo();
		    		Gdinfo.setCustomerGroup(groupInfo);
		    		Gdinfo.setCustomerGroupFullName(groupInfo.getName());
		    		Gdinfo.setCustomerGroupStandard(groupInfo.getGroupStandard());
		    		cus.getCustomerGroupDetails().add(Gdinfo);
				}
				
				cus.setNumber(obj.getString("number"));
				cus.setName(obj.getString("name"));
				cus.setVersion(1);
				cus.setUsedStatus(UsedStatusEnum.APPROVED);
				
				CustomerFactory.getLocalInstance(ctx).addnew(cus);
				
				CustomerCompanyInfoInfo info = new CustomerCompanyInfoInfo();
				CompanyOrgUnitInfo company=new CompanyOrgUnitInfo();
				company.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
				info.setCompanyOrgUnit(company);
				info.setCustomer(cus);
				info.setCU(cu);
				CustomerCompanyInfoFactory.getLocalInstance(ctx).addnew(info);
				
				Set cuIds = getMgeCu(ctx,cus.getAdminCU().getId().toString());
		    	for(Iterator itor = cuIds.iterator(); itor.hasNext(); ){
		    		String cuId = (String) itor.next();
		    		CustomerFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(OrgConstants.DEF_CU_ID), new ObjectUuidPK(cus.getId()), new ObjectUuidPK(cuId));
		    	}
			}
		} catch (Exception e) {
			rs.put("state", "0");
			rs.put("msg", e.getMessage());
			
			return rs.toString();
		}
    	rs.put("state", "1");
		rs.put("msg", "同步成功！");
		return rs.toString();
	}
	private Set getMgeCu(Context ctx,String cuId) throws BOSException {
		Set set = new HashSet();
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", OrgConstants.SYS_CU_ID,CompareType.NOTEQUALS));
		view.setFilter(filter);
		view.setSelector(sel);
		
		CtrlUnitCollection orgColl = CtrlUnitFactory.getLocalInstance(ctx).getCtrlUnitCollection(view);
		for (int i = 0; i < orgColl.size(); i++) {
			if(cuId.equals(orgColl.get(i).getId().toString())){
				continue;
			}
			set.add(orgColl.get(i).getId().toString());
		}
		return set;
	}
}