package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.autoupdate.util.StringUtil;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlCollection;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.IFWStatus;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class MarketingUnitControllerBean extends AbstractMarketingUnitControllerBean
{
    private static Logger logger = Logger.getLogger(MarketingUnitControllerBean.class.getName());
    private final int startlevel = 1;
    
    // ��ȡ��ǰ�ĵ�½��֯ (���ܻ�ȡ��ǰ��������֯����Ϊ������֯�����¼���ϵ��Ӫ����Ԫ��洢����֯�Ĺ�ϵ�ǲ�һ�µ�)
    private OrgUnitInfo getCurrentOrgUnit(Context ctx)  throws BOSException, ContractException{
    	OrgUnitInfo oUInfo = ContextUtil.getCurrentOrgUnit(ctx,OrgType.Sale);
		if (oUInfo == null) {
			throw new ContractException(new NumericExceptionSubItem("100","�޷���ȡ����ǰ��֯��Ϣ��"));
		}
		return oUInfo;
    }
    
    //��ȡָ����֯�µ������ӹ�˾id���� (�����Լ�)
    private Set getLeafCompanyIdSet(Context ctx,OrgUnitInfo unitInfo) throws BOSException, SQLException {
    	Set companyIdSet = new HashSet();
    	if(unitInfo==null) return companyIdSet;
    		
    	StringBuffer sql = new StringBuffer();
        sql.append("select fid from  t_org_sale where ")
           .append(" (FNumber = '"+unitInfo.getNumber()+"'")
           .append(" or FLongNumber like '"+unitInfo.getLongNumber()+"!%') ")
           .append(" and FisLeaf =1 ");
        IRowSet rs = DbUtil.executeQuery(ctx,sql.toString());
        while (rs.next()) {
        	companyIdSet.add(rs.getString("fid"));
        }
        return companyIdSet;
    }
    
    
	
	/**
	 * ָ��Ӫ����Ա���ܿ�������Ŀ����
	 * ���ֹܿغͷǹܿ��������
	 */
	protected String _getPermitSellProjectIdSql(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException {
		OrgUnitInfo orgUnit = getCurrentOrgUnit(ctx);
		if(currSaleMan==null) currSaleMan = ContextUtil.getCurrentUserInfo(ctx);
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append("select sellPro.flongnumber as longnumber from t_she_sellproject sellPro where sellPro.fid in(");
		bufferSql.append("select distinct(AllSpFid.fid) from ");//����Ϊ��ǰ��֯�Ĺܿ���Աʱ��ȡ��ǰ���¼���֯����������Ŀ + �������Щ��֯����Ŀ
		bufferSql.append("(select fid,forgUnitId from t_she_sellProject ");
		bufferSql.append("union  select fheadid as fid,forgUnitId from T_SHE_ShareOrgUnit) AllSpFid ");
		bufferSql.append("where AllSpFid.forgUnitId in (select fid from t_org_sale where fnumber = '"+orgUnit.getNumber()+"' or flongNumber like '"+orgUnit.getLongNumber()+"!%') ");
		bufferSql.append("and exists (select * from T_SHE_MarketUnitControl where forgUnitId = '"+orgUnit.getId()+"' and fcontrolerid = '"+currSaleMan.getId()+"' ) ");
		bufferSql.append(" union ");
		bufferSql.append("select distinct(fsellProjectId) from T_SHE_MarketingUnitSellProject MuSp "); //����Ϊ�ǹܿ���Աʱ��ȥ��ǰ��֯��������Ч�Ŷ��е���Ŀ
		bufferSql.append("left join T_SHE_MarketingUnit MuUnit on MuUnit.fid = MuSp.fheadId ");
		bufferSql.append("where MuUnit.FIsEnabled = 1 ");
		bufferSql.append(" and MuUnit.fid in (select fheadId from T_SHE_MarketingUnitMember where forgUnitId = '"+orgUnit.getId()+"' and FMemberID = '"+currSaleMan.getId()+"') ");
		bufferSql.append("and not exists (select * from T_SHE_MarketUnitControl where forgUnitId = '"+orgUnit.getId()+"' and fcontrolerid = '"+currSaleMan.getId()+"' ) ");
		bufferSql.append(")");
		
		FDCSQLBuilder sqlbu = new FDCSQLBuilder(ctx);
		sqlbu.appendSql(bufferSql.toString());
		IRowSet rs = sqlbu.executeQuery();
		StringBuffer longNumberStr = new StringBuffer();
		StringBuffer idString = new StringBuffer();
		List idList = new ArrayList();
		try {
			while(rs.next()){
					longNumberStr.setLength(0);
					longNumberStr.append("select distinct(fid) from t_she_sellproject where flongnumber=");
					longNumberStr.append("'"+rs.getString("longnumber")+"'");
					longNumberStr.append(" or ");
					longNumberStr.append(" flongnumber like ");
					longNumberStr.append("'"+rs.getString("longnumber")+"!%"+"'");
					idList.add(longNumberStr.toString());
			}
			if(idList.size()>0){
				for (int i = 0; i < idList.size(); i++) {
				    idString.append(idList.get(i).toString());
				    if(i!=idList.size()-1){
				        idString.append(" union ");
				    }
				}
			}else{
				idString.append("select fid from t_she_sellproject where fid='nullnull'");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idString.toString();
	}
	

	
	/**
	 * ������������Ŀ id����
	 * ָ��Ӫ����Ա���ܿ�������Ŀ����
	 * ���ֹܿغͷǹܿ��������
	 */
	protected Set _getPermitSellProjectIdSet(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException {
		Set idSet = new HashSet();
		String permitSql = _getPermitSellProjectIdSql(ctx,currSaleMan);
		
		FDCSQLBuilder sqlBuild = new FDCSQLBuilder(ctx);
		sqlBuild.appendSql(permitSql);
		IRowSet rowSet = sqlBuild.executeQuery();
		try {
			while(rowSet.next()){
				idSet.add(rowSet.getString("FSellProjectID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idSet;
	}

	
	protected Set _getPermitAllSellProjectIdSet(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException {
		return _getPermitSellProjectIdSet(ctx,currSaleMan);
	}
	
	private String getIdStrFromSet(Set idSet) {
		String idStr = "'null'";
		if(idSet!=null) {
			Iterator iter = idSet.iterator();
			while(iter.hasNext()) {
				idStr += ",'" + (String)iter.next()+"'";
			}
		}
		return idStr;
	}
	

	/**
	 * ���ݿͻ����ƺ͵绰���Լ��Ŀͻ������
	 * �ͻ���Χ���Լ���Ȩ�޲鿴�ģ����ұ��������ƥ�� (���Ʋ���Ϊ�գ��绰����Ϊ��)
	 */
	protected Set _getCustomerIdSetByNameAndPhone(Context ctx, UserInfo saleMan , String customerName, String customerPhone) throws BOSException, EASBizException {
		Set customerIdSet = new HashSet();
		if(customerName==null || customerName.trim().equals("")) return customerIdSet;			

		//��Ҫ����д	
		
		return customerIdSet;
	}

	/**
	 * ��Ϊ���������ڵ�Ӫ����Ԫ����(��ǰ������֯�µ�)
	 */
	protected MarketingUnitCollection _getMarketUnitCollByDutySaleMan(Context ctx, UserInfo saleMan) throws BOSException, EASBizException {
		String idComStr = "";
		try{
			OrgUnitInfo orgUnit = getCurrentOrgUnit(ctx);  
			Set sonCompanyIds = this.getLeafCompanyIdSet(ctx,orgUnit);
			idComStr = this.getIdStrFromSet(sonCompanyIds);
		}catch(SQLException e){
			throw new BOSException("��ѯ�ӹ�˾�쳣��");
		}
			
		MarketingUnitCollection muColl = new MarketingUnitCollection();
		MarketingUnitMemberCollection muMemberColl = MarketingUnitMemberFactory.getLocalInstance(ctx).getMarketingUnitMemberCollection(
				"select head.id,head.name,head.number where head.orgUnit.id in ("+idComStr+") and member.id = '"+saleMan.getId().toString()+"' and isDuty =1 ");
		for(int i=0;i<muMemberColl.size();i++) {
			muColl.add(muMemberColl.get(i).getHead());
		}
		
		return muColl;
	}

	/**
	 * ��Ϊ��Ա�����ڵ�Ӫ����Ԫ����(��ǰ������֯�µ�)
	 */
	protected MarketingUnitCollection _getMarketUnitCollByMemberSaleMan(Context ctx, UserInfo saleMan) throws BOSException, EASBizException {
		String idComStr = "";
		try{
			OrgUnitInfo orgUnit = getCurrentOrgUnit(ctx);  
			Set sonCompanyIds = this.getLeafCompanyIdSet(ctx,orgUnit);
			idComStr = this.getIdStrFromSet(sonCompanyIds);
		}catch(SQLException e){
			throw new BOSException("��ѯ�ӹ�˾�쳣��");
		}
			
		MarketingUnitCollection muColl = new MarketingUnitCollection();
		MarketingUnitMemberCollection muMemberColl = MarketingUnitMemberFactory.getLocalInstance(ctx).getMarketingUnitMemberCollection(
				"select head.id,head.name,head.number where head.orgUnit.id in ("+idComStr+") and member.id = '"+saleMan.getId().toString()+"' and isDuty =0 ");
		for(int i=0;i<muMemberColl.size();i++) {
			muColl.add(muMemberColl.get(i).getHead());
		}
		
		return muColl;
	}
	
	
	/**
	 * ��õ�ǰ��������֯�ĳ�Ա�������¼���Ա
	 * ������ͨ�͸�����
	 */
	protected MarketingUnitMemberCollection _getMarketUnitCollMember(Context ctx, UserInfo saleMans)	throws BOSException, EASBizException {
		String idComStr = "";
		try {
			OrgUnitInfo orgUnit = getCurrentOrgUnit(ctx);
			Set sonCompanyIds = this.getLeafCompanyIdSet(ctx, orgUnit);
			idComStr = this.getIdStrFromSet(sonCompanyIds);
		} catch (SQLException e) {
			throw new BOSException("��ѯ�ӹ�˾�쳣��");
		}

		MarketingUnitCollection muColl = new MarketingUnitCollection();
		MarketingUnitMemberCollection muMemberColl = MarketingUnitMemberFactory
				.getLocalInstance(ctx)
				.getMarketingUnitMemberCollection(
						"select head.id,head.name,head.number,isDuty,id,member.id,member.number,member.name where head.orgUnit.id in ("
								+ idComStr	+ ") and member.id = '"	+ saleMans.getId().toString() + "'");
		
		StringBuffer sb  = new StringBuffer();
		for (int i = 0; i < muMemberColl.size(); i++) {
			muColl.add(muMemberColl.get(i).getHead());
			sb.append("'"+muMemberColl.get(i).getHead().getId().toString()+"'");	
			if(i!=muMemberColl.size()-1){
				sb.append(",");
			}
		}
		EntityViewInfo view  = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id",sb.toString(),CompareType.INNER));
		view.setFilter(filter);
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("name"));
		coll.add(new SelectorItemInfo("number"));
		coll.add(new SelectorItemInfo("member.id"));
		coll.add(new SelectorItemInfo("member.name"));
		coll.add(new SelectorItemInfo("member.number"));
		coll.add(new SelectorItemInfo("isDuty"));
		view.setSelector(coll);
		MarketingUnitMemberCollection memColl = MarketingUnitMemberFactory
		.getLocalInstance(ctx).getMarketingUnitMemberCollection(view);

		return memColl;
	}

	
	
	/**
	 * ָ����ouId�����µ�����Ӫ����Ԫ
	 */
	protected MarketingUnitCollection _getMarketUnitCollByOUIdSet(Context ctx, Set ouIdSet) throws BOSException, EASBizException {
		MarketingUnitCollection muColl = new MarketingUnitCollection();
		if(ouIdSet==null || ouIdSet.size()==0) return muColl;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",ouIdSet,CompareType.INCLUDE));
		view.setFilter(filter);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		view.setSelector(selector);
		return this.getMarketingUnitCollection(ctx,view);
	}
	
	

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {		
		_checkNameDup(ctx,model);
		ObjectUuidPK pk = (ObjectUuidPK)super._submit(ctx, model);
    	return pk;    	
	}	

    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {    
    	super._delete(ctx, pk);

    }    
	
    
    /**
     *isMuPerm=true ��ǰ�˵�Ӫ��ȫ���¿�������Ŀ�ڵ� ���������������֯�� (�����ͬ����Ŀ�ֱ������ڲ�ͬ����֯��)
     *isMuPerm=false ��Ӫ��Ȩ��ȥ�޹�
     * SellProject ��Building��BuildingUnit ��Ϊ�գ�Ϊ�մ�����Ŀ
     */
	protected ArrayList _getMuSellProTreeNodes(Context ctx, String detailNodeType, boolean isMuPerm) throws BOSException, EASBizException {
		return null;   //��Ҫ����д
	}
	


	protected Set _checkRefByNext(Context ctx, Set marketingSellProjectSet, MarketingUnitInfo marketingUnitInfo)
			throws BOSException {
		Set result = new HashSet();
		MarketingUnitInfo muInfo = marketingUnitInfo;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",muInfo.getId().toString()));
		view.setFilter(filter);
		MarketingUnitCollection muColl = MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitCollection(view);
		if(muColl.size()<1){//û���¼�Ӫ���Ŷ�ֱ�ӷ���
			return result;
		}else{//���¼�Ӫ���Ŷ�
			Set muHeadIds = new HashSet();
			for(int i = 0; i <muColl.size();i++){
				MarketingUnitInfo mu = muColl.get(i);
				muHeadIds.add(mu.getId().toString());
			}
			EntityViewInfo sview = new EntityViewInfo();
			FilterInfo sfilter = new FilterInfo();
			sfilter.getFilterItems().add(new FilterItemInfo("head.id",muHeadIds,CompareType.INCLUDE));
			sfilter.getFilterItems().add(new FilterItemInfo("sellProject.id",marketingSellProjectSet,CompareType.INCLUDE));
			sview.setFilter(sfilter);
			SelectorItemCollection sel = new SelectorItemCollection();
			sview.setSelector(sel);
			sel.add("sellProject.*");
			MarketingUnitSellProjectCollection muspColl = MarketingUnitSellProjectFactory.getLocalInstance(ctx).getMarketingUnitSellProjectCollection(sview);
			for(int i = 0 ; i < muspColl.size() ;i ++){
				result.add(muspColl.get(i));
			}
		}
		return result;
	}

	protected boolean _checkMemeberOfSameSellPorject(Context ctx, MarketingUnitInfo marketingUnitInfo)
			throws  EASBizException, BOSException {
		Map tempMap = new HashMap();
		MarketingUnitMemberCollection  memberColl = marketingUnitInfo.getMember();
		for(int i = 0 ; i < memberColl.size(); i++){
			UserInfo user = memberColl.get(i).getMember();
			if(memberColl.get(i).getDimissionDate()!=null && memberColl.get(i).getDimissionDate().before(new Date()))
				continue;
			String muInfo  = null;
			if(user!=null){
				muInfo = user.getId().toString();
			}
			tempMap.put(muInfo, new Integer(i));
		}
		
		//��ѡ�����ǹܿ���ԱЧ�鲻��Ϊ�ܿ���Ա
		EntityViewInfo cview = new EntityViewInfo();
		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("controler.*");
		cview.setSelector(selColl);
		FilterInfo cfilter = new FilterInfo();
		cfilter.getFilterItems().add(new FilterItemInfo("controler.id",tempMap.keySet(),CompareType.INCLUDE));
		cfilter.getFilterItems().add(new FilterItemInfo("orgUnit.id",marketingUnitInfo.getOrgUnit().getId().toString()));
		cview.setFilter(cfilter);
		MarketUnitControlCollection  conColl = MarketUnitControlFactory.getLocalInstance(ctx).getMarketUnitControlCollection(cview);
		StringBuffer sb = new StringBuffer();
		if(conColl.size()>0){
			for(int i = 0 ; i < conColl.size(); i++){
				if(i >0 ){
					sb.append(",");
				}
				sb.append(conColl.get(i).getControler().getName());
			}
			throw new EASBizException(new NumericExceptionSubItem("100", " Ӫ���Ŷӳ�Ա: "+sb.toString() +" �Ǹ�������֯�Ĺܿ���Ա,�������ΪӪ���Ŷӵĳ�Ա��" ));
		}
		
		MarketingUnitSellProjectCollection  MuSelProject = marketingUnitInfo.getSellProject();
		
		for(int i = 0 ; i < MuSelProject.size() ;i++){
			SellProjectInfo sellPorject = MuSelProject.get(i).getSellProject();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellPorject.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("head.orgUnit.id",marketingUnitInfo.getOrgUnit().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("head.isEnabled",new Boolean(true) ));
			view.setFilter(filter);
			Set headIdSet  = new HashSet();
			MarketingUnitSellProjectCollection oriMuSelProject = MarketingUnitSellProjectFactory.getLocalInstance(ctx).getMarketingUnitSellProjectCollection(view);
			if(marketingUnitInfo.getId()!=null){//�������޸�Ч�飬����Ч���Լ�Ӫ����Ԫ�Ƿ��ظ�
				for(int z = 0; z < oriMuSelProject.size();z++ ){
					String headString = oriMuSelProject.get(z).getHead().getId().toString();
					if(!headString.equals(marketingUnitInfo.getId().toString())){
						headIdSet.add(oriMuSelProject.get(z).getHead().getId().toString());
					}
				}
			} else {
				for(int z = 0; z < oriMuSelProject.size();z++){
					headIdSet.add(oriMuSelProject.get(z).getHead().getId().toString());
				}
			}
			
			EntityViewInfo mview = new EntityViewInfo();
			FilterInfo mfilter = new FilterInfo();
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("member.*");			
			mfilter.getFilterItems().add(new FilterItemInfo("head.id",headIdSet,CompareType.INCLUDE));
			mfilter.getFilterItems().add(new FilterItemInfo("head.isEnabled",new Boolean(true) ));
			mview.setFilter(mfilter);
			mview.setSelector(sel);
			MarketingUnitMemberCollection oriMuMemberColl = MarketingUnitMemberFactory.getLocalInstance(ctx).getMarketingUnitMemberCollection(mview);
			for(int j= 0 ; j < oriMuMemberColl.size();j++ ){
				UserInfo muInfo = oriMuMemberColl.get(j).getMember();
				if(tempMap.containsKey(muInfo.getId().toString())){
					throw new EASBizException(new NumericExceptionSubItem("100", "������Ŀ: "+sellPorject.getName()+"��Ӫ���Ŷӳ�Ա: "+muInfo.getName() +"�ظ���" ));
				}
			}
		}
		return false;
	}
	
	
	 protected void _checkNumberDup(Context ctx , IObjectValue model) throws    BOSException , EASBizException 
	{
	     TreeBaseInfo treeModel = (TreeBaseInfo) model;
	
	     //if no parent,no need to check
	     FilterInfo filter = new FilterInfo();
	     FilterItemInfo filterItem = null;
	     //���ڵ�Ϊ��ʱ������������Ƿ��ظ���
	     if (treeModel.innerGetParent() == null)	     {
	         filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number ,
	                                         treeModel.getNumber() ,
	                                         CompareType.EQUALS);
	         filter.getFilterItems().add(filterItem);
	         filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_Parent ,null ,CompareType.EQUALS));
	         filter.setMaskString("#0 and #1");
	         if (treeModel.getId() != null)
	         {
	             filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID ,
	                                             treeModel.getId().
	                                             toString() ,
	                                             CompareType.NOTEQUALS);
	             filter.getFilterItems().add(filterItem);
	             filter.setMaskString("#0 and #1 and #2");
	         }
	     }	 else	     {
	         filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number ,
	                                         treeModel.getNumber() ,
	                                         CompareType.EQUALS);
	         filter.getFilterItems().add(filterItem);
	         if (treeModel.innerGetParent().getId() != null)
	         {
	             filterItem = new FilterItemInfo(IFWEntityStruct.tree_Parent ,
	                                             treeModel.innerGetParent().
	                                             getId().
	                                             toString() , CompareType.EQUALS);
	             filter.getFilterItems().add(filterItem);
	             filter.setMaskString("#0 and #1");
	         }
	         if (treeModel.getId() != null)
	         {
	             filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID ,
	                                             treeModel.getId().
	                                             toString() ,
	                                             CompareType.NOTEQUALS);
	             filter.getFilterItems().add(filterItem);
	             if (treeModel.innerGetParent().getId() != null)
	             {
	                 filter.setMaskString("#0 and #1 and #2");
	             }
	             else
	             {
	                 filter.setMaskString("#0 and #1");
	             }
	         }
	     }
	
	     EntityViewInfo view = new EntityViewInfo();

	     FilterInfo filterOU = new FilterInfo();
	     filterOU.getFilterItems().add(new FilterItemInfo("orgUnit.id",((MarketingUnitInfo)model).getOrgUnit().getId().toString()));
	     filter.mergeFilter(filterOU,"AND");
	     view.setFilter(filter);
	
	     TreeBaseCollection results = this.getTreeBaseCollection(ctx , view);
	
	
	     if (results != null && results.size() > 0)
	     {
	         throw new TreeBaseException(TreeBaseException.CHECKNUMBERDUPLICATED ,
	                                     new Object[]
	                                     {treeModel.getNumber()});
	
	     }
	
	 }
	 protected void _checkNameDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
	        DataBaseInfo dataBaseInfo = (DataBaseInfo) model;
	        FilterInfo filter = new FilterInfo();
	        FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name ,
	            dataBaseInfo.getName() ,
	            CompareType.EQUALS);
	        filter.getFilterItems().add(filterItem);
	        if (dataBaseInfo.getId() != null)
	        {
	            filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID ,
	                                            dataBaseInfo.getId() ,
	                                            CompareType.NOTEQUALS);
	            filter.getFilterItems().add(filterItem);
	            filter.setMaskString("#0 and #1");
	        }
	        FilterInfo filterOU = new FilterInfo();
	        filterOU.getFilterItems().add(new FilterItemInfo("orgUnit.id",((MarketingUnitInfo)model).getOrgUnit().getId().toString()));
	        filter.mergeFilter(filterOU,"AND");
	        EntityViewInfo view = new EntityViewInfo();
	        view.setFilter(filter);
	        SorterItemCollection sorter = new SorterItemCollection();
	        sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
	        if (super._exists(ctx,filter))
	        {
	            String name = this._getPropertyAlias(ctx,dataBaseInfo,IFWEntityStruct.dataBase_Name) + dataBaseInfo.getName();
	            throw new EASBizException(EASBizException.CHECKDUPLICATED ,      new Object[]       {name});

	        }

	    }

	protected Set _getSellProjectIdSetAllChild(Context ctx, UserInfo saleMan) throws BOSException, EASBizException {		
		return null;
	}

	protected Set _getPermitFdcCustomerIdSet(Context ctx, UserInfo saleMan, boolean isInCludeDisEnable)
			throws BOSException, EASBizException {
		return _getPermitFdcCustomerIdSet(ctx,saleMan,isInCludeDisEnable,true);
	}

	protected Set _getPermitFdcCustomerIdSet(Context ctx, UserInfo saleMan, boolean isInCludeDisEnable,
			boolean isIncludeShared) throws BOSException, EASBizException {
		return null;	//��Ҫ����д
	}
	
	/**
	 * �ӵ���������bos������longnumberֻ֧��numberȫ�ֲ��ظ�����֧����֯����� ��������������ϳ������ʱ����Ҫ��һ���ڵ������֯����
	 */
	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
        TreeBaseInfo treeBaseInfo = (TreeBaseInfo) model;
        if (treeBaseInfo == null)
        {
            throw new TreeBaseException(TreeBaseException.CHECKISNULL);
            //throw new BOSException("The entity does not exist!");
        }
        try
        {
            _checkNumberDup(ctx , treeBaseInfo);
        }
        catch (EASBizException tbe)
        {
            throw tbe;
        }
        treeBaseInfo.setIsLeaf(true);

        TreeBaseInfo parent = getFullParent(ctx , treeBaseInfo);

        if (parent == null || parent.getId().equals(treeBaseInfo.getId()))
        {
            treeBaseInfo.setLevel(startlevel);
            treeBaseInfo.setLongNumber(((MarketingUnitInfo)treeBaseInfo).getOrgUnit().getId()+treeBaseInfo.getNumber());
            //add longNumberCheck.2006-4-26
            checkLongNumberLen(ctx,treeBaseInfo);
            //[begin]displayName
            treeBaseInfo.setDisplayName(treeBaseInfo.getName());
            treeBaseInfo.setDisplayName(treeBaseInfo.getName(locale_L1),locale_L1);
            limitDisplayNameLength(treeBaseInfo,locale_L1);
            treeBaseInfo.setDisplayName(treeBaseInfo.getName(locale_L2),locale_L2);
            limitDisplayNameLength(treeBaseInfo,locale_L2);
            treeBaseInfo.setDisplayName(treeBaseInfo.getName(locale_L3),locale_L3);
            limitDisplayNameLength(treeBaseInfo,locale_L3);
            //[end]
        }
        else
        {
            treeBaseInfo.setLevel(parent.getLevel() + 1);
            //treeBaseInfo.setNumber(parent.getNumber()+"."+treeBaseInfo.getNumber().substring(treeBaseInfo.getNumber().lastIndexOf(".")+1));
            treeBaseInfo.setLongNumber(parent.getLongNumber() + IFWEntityStruct.tree_LongNumber_Split +
                                       treeBaseInfo.getNumber());
            //add longNumberCheck.2006-4-26
            checkLongNumberLen(ctx,treeBaseInfo);

            //[begin]displayName
            treeBaseInfo.setDisplayName(parent.getDisplayName()+IFWEntityStruct.displayNameSeparator +
                    treeBaseInfo.getName());
//            treeBaseInfo.setDisplayName(parent.getDisplayName(locale_L1)+IFWEntityStruct.displayNameSeparator +
//                    treeBaseInfo.getName(locale_L1),locale_L1);
            LinkDisplayName(parent,treeBaseInfo,locale_L1);
            limitDisplayNameLength(treeBaseInfo,locale_L1);
//            treeBaseInfo.setDisplayName(parent.getDisplayName(locale_L2)+IFWEntityStruct.displayNameSeparator +
//                    treeBaseInfo.getName(locale_L2),locale_L2);
            LinkDisplayName(parent,treeBaseInfo,locale_L2);
            limitDisplayNameLength(treeBaseInfo,locale_L2);
//            treeBaseInfo.setDisplayName(parent.getDisplayName(locale_L3)+IFWEntityStruct.displayNameSeparator +
//                    treeBaseInfo.getName(locale_L3),locale_L3);
            LinkDisplayName(parent,treeBaseInfo,locale_L3);
            limitDisplayNameLength(treeBaseInfo,locale_L3);
            //[end]

            if (parent.isIsLeaf())
            {
                parent.setIsLeaf(false);
                super._update(ctx , new ObjectUuidPK(parent.getId()) , parent);
            }
        }

        super._addnew(ctx , pk , treeBaseInfo);

    }
	
	/**
	 * �ӵ���������bos���������longNumberֻ֧��numberȫ�ֲ��ظ�����֧����֯����� ��������������ϳ������ʱ����Ҫ��һ���ڵ������֯����
	 */
	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
        TreeBaseInfo treeBaseInfo = (TreeBaseInfo) model;
        if (treeBaseInfo == null)
        {
            throw new TreeBaseException(TreeBaseException.CHECKISNULL);
            //throw new BOSException("The entity does not exist!");
        }

        try
        {
            _checkNumberDup(ctx , treeBaseInfo);
        }
        catch (EASBizException tbe)
        {
            throw tbe;
        }

        TreeBaseInfo parent = getFullParent(ctx , treeBaseInfo);

        treeBaseInfo.setIsLeaf(true);
        if (parent == null || parent.getId().equals(treeBaseInfo.getId()))
        {
            treeBaseInfo.setLevel(startlevel);
            treeBaseInfo.setLongNumber(((MarketingUnitInfo)treeBaseInfo).getOrgUnit().getId()+treeBaseInfo.getNumber());
            //add longNumberCheck.2006-4-26
            checkLongNumberLen(ctx,treeBaseInfo);

            //[begin]displayName
            treeBaseInfo.setDisplayName(treeBaseInfo.getName());
            treeBaseInfo.setDisplayName(treeBaseInfo.getName(locale_L1),locale_L1);
            limitDisplayNameLength(treeBaseInfo,locale_L1);
            treeBaseInfo.setDisplayName(treeBaseInfo.getName(locale_L2),locale_L2);
            limitDisplayNameLength(treeBaseInfo,locale_L2);
            treeBaseInfo.setDisplayName(treeBaseInfo.getName(locale_L3),locale_L3);
            limitDisplayNameLength(treeBaseInfo,locale_L3);
            //[end]

        }
        else
        {
            treeBaseInfo.setLevel(parent.getLevel() + 1);
            //treeBaseInfo.setNumber(parent.getNumber() + "." +
            //                           treeBaseInfo.getNumber());
            treeBaseInfo.setLongNumber(parent.getLongNumber() + IFWEntityStruct.tree_LongNumber_Split +
                                       treeBaseInfo.getNumber());
            //add longNumberCheck.2006-4-26
            checkLongNumberLen(ctx,treeBaseInfo);

            //[begin]displayName
//            treeBaseInfo.setDisplayName(parent.getDisplayName()+IFWEntityStruct.displayNameSeparator +
//                    treeBaseInfo.getName());
            LinkDisplayName(parent,treeBaseInfo,null);
            limitDisplayNameLength(treeBaseInfo);
//            treeBaseInfo.setDisplayName(parent.getDisplayName(locale_L1)+IFWEntityStruct.displayNameSeparator +
//                    treeBaseInfo.getName(locale_L1),locale_L1);
            LinkDisplayName(parent,treeBaseInfo,locale_L1);
            limitDisplayNameLength(treeBaseInfo,locale_L1);
//            treeBaseInfo.setDisplayName(parent.getDisplayName(locale_L2)+IFWEntityStruct.displayNameSeparator +
//                    treeBaseInfo.getName(locale_L2),locale_L2);
            LinkDisplayName(parent,treeBaseInfo,locale_L2);
            limitDisplayNameLength(treeBaseInfo,locale_L2);
//            treeBaseInfo.setDisplayName(parent.getDisplayName(locale_L3)+IFWEntityStruct.displayNameSeparator +
//                    treeBaseInfo.getName(locale_L3),locale_L3);
            LinkDisplayName(parent,treeBaseInfo,locale_L3);
            limitDisplayNameLength(treeBaseInfo,locale_L3);
            //[end]
            if (parent.isIsLeaf())
            {

                parent.setIsLeaf(false);
                super._update(ctx , new ObjectUuidPK(parent.getId()) , parent);
            }
        }

        IObjectPK pk = super._addnew(ctx , treeBaseInfo);

        return pk;

    }
	
	
	 /**
     * �Գ�����ĳ��Ƚ������ơ�
     *
     * @param info
     * @throws BOSException
     */
    private void checkLongNumberLen(Context ctx,TreeBaseInfo info) throws EASBizException, BOSException
    {
        String propertyAlias = null;
        int maxLen = 0;
        propertyAlias = getPropertyAlias(ctx,info,IFWEntityStruct.tree_LongNumber,ctx.getLocale());

        int len = (null == info.getLongNumber())?-1:info.getLongNumber().getBytes().length;
        maxLen=getLongNumberLen(ctx,info);
        if(len>maxLen)
          {
              throw new TreeBaseException(TreeBaseException.CHECKLONGNUMBERLEN,
                      new Object[]{propertyAlias,String.valueOf(maxLen)});
          }
    }
    
    private String getPropertyAlias(Context ctx,CoreBaseInfo info,String propertyName,Locale locale)
    {
        String alias = null;
        PropertyInfo property = getProperty(ctx,info,propertyName);
        if(property != null)
        {
            alias = property.getAlias(locale);
        }
        return alias;
    }
    
    private PropertyInfo getProperty(Context ctx,CoreBaseInfo info,String propertyName)
    {
        EntityObjectInfo entity = getBOSEntity(ctx, info);
        PropertyInfo property = null;
        property = entity.getPropertyByNameRuntime(propertyName);

        return property;
    }
    
    //����������parent
    private TreeBaseInfo getFullParent(Context ctx , IObjectValue model) throws
        BOSException , EASBizException
    {
        TreeBaseInfo treeBaseInfo = (TreeBaseInfo) model;
        TreeBaseInfo parent = treeBaseInfo.innerGetParent();
        if (parent == null || parent.getId().equals(treeBaseInfo.getId()))
        {
            return null;
        }
        //else if (parent.getId() != null && parent.getLongNumber() == null)
        else //���»�ȡ���ڵ㡣��֤��ȡ����ֵ����ֹ�����������ݡ�
        {
            parent = lazyLoad(ctx , parent);
            //��ֻ֤Ҫһ��װ�ظ���
            treeBaseInfo.innerSetParent(parent);
        }
        return parent;

    }
    
  //�ӳ�װ��ʵ��,����BosĬ�Ϸ��صĹ���ʵ��ֻ��Uuid�ֶ�
    private TreeBaseInfo lazyLoad(Context ctx , IObjectValue model) throws
        BOSException , EASBizException
    {
        TreeBaseInfo treeBaseInfo = (TreeBaseInfo) model;
        //���»�ȡ���ڵ㡣

        //2007-1-26 �Ż�ȡ������
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add(new SelectorItemInfo(IFWEntityStruct.coreBase_ID));
        selector.add(new SelectorItemInfo(IFWEntityStruct.objectBase_CU));
        selector.add(new SelectorItemInfo(IFWEntityStruct.tree_LongNumber));
        selector.add(new SelectorItemInfo(IFWEntityStruct.tree_Parent));
        selector.add(new SelectorItemInfo(IFWEntityStruct.dataBase_Name));
        selector.add(new SelectorItemInfo(IFWEntityStruct.dataBase_Number));
        selector.add(new SelectorItemInfo(IFWEntityStruct.tree_Level));
        selector.add(new SelectorItemInfo(IFWEntityStruct.tree_isLeaf));
        selector.add(new SelectorItemInfo(IFWEntityStruct.displayName));
        selector.add(new SelectorItemInfo(IFWStatus.deletedStatus));


        TreeBaseInfo result = (TreeBaseInfo) getValue(ctx ,
            new ObjectUuidPK(treeBaseInfo.getId()),selector);
        return result;
        /*if (treeBaseInfo != null && treeBaseInfo.getId() != null &&
            treeBaseInfo.getLongNumber() == null)
        {
            TreeBaseInfo result = (TreeBaseInfo) getValue(ctx ,
                new ObjectUuidPK(treeBaseInfo.getId()));
            return result;
        }
        else
        {
            return treeBaseInfo;
        }*/
    }
    
    
    //�����ʾ���Ƴ��ȳ���80����ͷ��ʼ��ȡ��
    private void limitDisplayNameLength(TreeBaseInfo tree,Locale locale)
    {
        String displayName = tree.getDisplayName(locale);
        String[] displayNameVar = StringUtil.split(displayName,IFWEntityStruct.displayNameSeparator);
        if(displayNameVar == null)
        {
            return;
        }
        if(displayNameVar.length > 1)
        {
            if(displayName.length() > IFWEntityStruct.displayNameLong)
            {
                int len = displayName.indexOf(IFWEntityStruct.displayNameSeparator);
                displayName = displayName.substring(len +1);
                tree.setDisplayName(displayName,locale);
                limitDisplayNameLength(tree,locale);
            }
        }
    }

    /**
     *  ������ʾ���Ƶ����ԡ�
     *
     * @param parent
     * @param info
     * @param locale
     */
    private void LinkDisplayName(TreeBaseInfo parent,TreeBaseInfo info,Locale locale)
    {
        String disPlayName = null;
        String parentDisplayName = parent.getDisplayName(locale);
        if( parentDisplayName != null)
        {
            disPlayName = parentDisplayName + IFWEntityStruct.displayNameSeparator + info.getName(locale);
        }
        else
        {
            disPlayName = info.getName(locale);
        }
        info.setDisplayName(disPlayName,locale);
    }
    
    //�����ʾ���Ƴ��ȳ���80����ͷ��ʼ��ȡ��
    private void limitDisplayNameLength(TreeBaseInfo tree)
    {
        String displayName = tree.getDisplayName();
        String[] displayNameVar = StringUtil.split(displayName,IFWEntityStruct.displayNameSeparator);
        if(displayNameVar == null)
        {
            return;
        }
        if(displayNameVar.length > 1)
        {
            if(displayName.length() > IFWEntityStruct.displayNameLong)
            {
                int len = displayName.indexOf(IFWEntityStruct.displayNameSeparator);
                displayName = displayName.substring(len +1);
                tree.setDisplayName(displayName);
                limitDisplayNameLength(tree);
            }
        }
    }
    
    
    
    
    protected void _updatePartial(Context ctx, IObjectValue model,
    		SelectorItemCollection selector) throws BOSException,
    		EASBizException {
    	super._updatePartial(ctx, model, selector);
    }

	protected Set _getPermitSaleManIdSet(Context ctx, UserInfo currSaleMan)
			throws BOSException, EASBizException {
		Set idSet = new HashSet();
		String permitSql = _getPermitSaleManIdSql(ctx,currSaleMan);
		
		FDCSQLBuilder sqlBuild = new FDCSQLBuilder(ctx);
		sqlBuild.appendSql(permitSql);
		IRowSet rowSet = sqlBuild.executeQuery();
		try {
			while(rowSet.next()){
				idSet.add(rowSet.getString("fmemberid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idSet;
	}

	protected Set _getPermitSaleManIdSet(Context ctx, UserInfo currSaleMan,
			IObjectValue sellProInfo) throws BOSException, EASBizException {
		Set idSet = new HashSet();
		String permitSql = _getPermitSaleManIdSql(ctx,currSaleMan,sellProInfo);
		
		FDCSQLBuilder sqlBuild = new FDCSQLBuilder(ctx);
		sqlBuild.appendSql(permitSql);
		IRowSet rowSet = sqlBuild.executeQuery();
		try {
			while(rowSet.next()){
				idSet.add(rowSet.getString("fmemberid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idSet;
	}

	protected String _getPermitSaleManIdSql(Context ctx, UserInfo currSaleMan)
			throws BOSException, EASBizException {

		OrgUnitInfo orgUnit = getCurrentOrgUnit(ctx);	
		if(currSaleMan==null) currSaleMan = ContextUtil.getCurrentUserInfo(ctx);

		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append("select fcontrolerid fmemberid from T_SHE_MarketUnitControl where forgUnitId = '"+orgUnit.getId()+"' and fcontrolerid = '"+currSaleMan.getId()+"' ");
		bufferSql.append(" union ");
		bufferSql.append("select distinct(fmemberid) from (");
		bufferSql.append("select MuSp.fsellProjectId,MuMember.fmemberid ");//����Ϊ�ܿ���Աʱ��ȡ��ǰ���¼���֯���������õ��Ŷ��е����г�Ա
		bufferSql.append("from T_SHE_MarketingUnitMember MuMember ");
		bufferSql.append("left join T_SHE_MarketingUnit MuUnit on MuUnit.fid =MuMember.fheadId ");
		bufferSql.append("left join T_SHE_MarketingUnitSellProject MuSp on MuSp.fheadid = MuUnit.fid ");
		bufferSql.append("where MuUnit.FIsEnabled = 1 and MuUnit.forgUnitId = '"+orgUnit.getId()+"' ");
		bufferSql.append("and exists (select * from T_SHE_MarketUnitControl where forgUnitId = '"+orgUnit.getId()+"' and fcontrolerid = '"+currSaleMan.getId()+"') ");
		bufferSql.append(" union ");
		bufferSql.append("select * from (");
		bufferSql.append("select MuSp.fsellProjectId,MuMember.fmemberid  ");//�ǹܿ���Ա������£�1��Ϊ�����ˣ���ǰ��֯�µĸ���������Ŷ��е����г�Ա
		bufferSql.append("from T_SHE_MarketingUnitMember MuMember ");
		bufferSql.append("left join T_SHE_MarketingUnit MuUnit on MuUnit.fid =MuMember.fheadId ");
		bufferSql.append("left join T_SHE_MarketingUnitSellProject MuSp on MuSp.fheadid = MuUnit.fid ");
		bufferSql.append("where MuMember.FHeadID in ( ");
		bufferSql.append("select MuMember.fheadid from T_SHE_MarketingUnitMember MuMember ");
		bufferSql.append("left join T_SHE_MarketingUnit MuUnit on MuUnit.fid =MuMember.fheadId ");
		bufferSql.append("where MuMember.fmemberid = '"+currSaleMan.getId()+"' and MuMember.fisDuty = 1 and MuUnit.forgUnitId = '"+orgUnit.getId()+"' ");
		bufferSql.append("and MuMember.faccessionDate <= getDate() and (MuMember.fdimissionDate is null or MuMember.fdimissionDate >= getDate())  )   ");
		bufferSql.append(" union ");
		bufferSql.append("select MuSp.fsellProjectId,MuMember.fmemberid  ");//�ǹܿ���Ա������£�2����Ϊ��Ա����ǰ��֯�µ�����Ӫ����Ԫ�µ���Ŀ�¶��Լ���Ȩ��
		bufferSql.append("from T_SHE_MarketingUnitMember MuMember ");
		bufferSql.append("left join T_SHE_MarketingUnit MuUnit on MuUnit.fid =MuMember.fheadId ");
		bufferSql.append("left join T_SHE_MarketingUnitSellProject MuSp on MuSp.fheadid = MuUnit.fid ");
		bufferSql.append("where MuUnit.forgUnitId = '"+orgUnit.getId()+"' and MuMember.fmemberid = '"+currSaleMan.getId()+"' and MuMember.fisDuty = 0 ");
		bufferSql.append("and MuMember.faccessionDate <= getDate() and (MuMember.fdimissionDate is null or MuMember.fdimissionDate >= getDate()) ");
		bufferSql.append(") NotControler  where not exists ( "); //��ǰ��֯�·ǹܿ���Ա
		bufferSql.append("select * from T_SHE_MarketUnitControl where forgUnitId = '"+orgUnit.getId()+"' and fcontrolerid = '"+currSaleMan.getId()+"')");
		bufferSql.append(") AllMuUserId ");
		return bufferSql.toString();
	}

	protected String _getPermitSaleManIdSql(Context ctx, UserInfo currSaleMan,
			IObjectValue sellProInfo) throws BOSException, EASBizException {
		if(sellProInfo==null) 
			return _getPermitSaleManIdSql(ctx,currSaleMan);
		else {
			//��Ŀ�����Ǹ��ڵ���Ŀ
			SellProjectInfo spInfo = (SellProjectInfo)sellProInfo;
			spInfo = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select * where id = '"+spInfo.getId()+"'");
			if(spInfo.getLevel()!=1){
				String rootNumber = spInfo.getLongNumber().split("!")[0];
				SellProjectCollection spColl = SellProjectFactory.getLocalInstance(ctx).getSellProjectCollection(
						"select id where number = '"+rootNumber+"' and level = 1  ");
				if(spColl.size()>0)	spInfo = spColl.get(0);
				else return "select fid from t_pm_user where 1=0";
			}
			
			return  _getPermitSaleManIdSql(ctx,currSaleMan) + " where fsellProjectId = '"+spInfo.getId()+"'";
		}
	}
}