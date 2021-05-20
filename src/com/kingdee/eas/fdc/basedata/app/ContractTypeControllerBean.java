package com.kingdee.eas.fdc.basedata.app;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ICommonBOSType;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.ormapping.impl.ImplUtils;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractDetailDefCollection;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.IContractDetailDef;
import com.kingdee.eas.fdc.basedata.IContractType;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.db.SQLUtils;
/**
 * ����:��ͬ����
 * @author jackwang date:2006-7-7
 * @version EAS5.1
 */
public class ContractTypeControllerBean extends AbstractContractTypeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.ContractTypeControllerBean");

	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		trimBlank(model);
		ContractTypeInfo contractTypeModel = (ContractTypeInfo) model;
		// ����Ƿ������ͬ�ı��룬����ھ��׳��쳣
		CtrlUnitInfo rootCU = new CtrlUnitInfo();
		rootCU.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		contractTypeModel.setCU(rootCU);
		super._checkNumberDup(ctx, contractTypeModel);
		super._checkNameBlank(ctx, contractTypeModel);
		super._checkNameDup(ctx, contractTypeModel);
		super._checkNumberBlank(ctx, contractTypeModel);

		// ForSupportLongnumberCoding������ר�Ÿ���������õģ�����ʱ����������룬�����������ǿյģ�����ȵ�����ʱ����SQL���LongNumberд��
		// Added By Owen_wen 2010-11-22
		contractTypeModel.setForSupportLongnumberCoding(contractTypeModel.getLongNumber()); 
		
		// ȡ�ø����ڵ�
		ContractTypeInfo parentInfo = contractTypeModel.getParent();
		if (parentInfo != null && !parentInfo.getId().equals(contractTypeModel.getId())) {
			BOSUuid parentID = parentInfo.getId();
			parentInfo = super.getContractTypeInfo(ctx, new ObjectUuidPK(parentID));
			IObjectPK pk = new ObjectUuidPK(parentInfo.getId().toString());
			pk = super._addnew(ctx, contractTypeModel);
			//ͬ��ת�ƺ�ͬ����
			if(contractTypeModel.getParent()!=null){
			    String sql = "update  T_CON_ContractBill set FContractTypeID = ? where FcontractTypeID = ?  ";
			    Object[] params = new Object[]{contractTypeModel.getId().toString(), contractTypeModel.getParent().getId().toString()};
			    DbUtil.execute(ctx,sql,params);
			    
			    sql = "update  t_Con_Contractbillrevise set FContractTypeID = ? where FcontractTypeID = ?  ";
			    params = new Object[]{contractTypeModel.getId().toString(), contractTypeModel.getParent().getId().toString()};
			    DbUtil.execute(ctx,sql,params);
			}
			
			//ͬ�����Ӻ�ͬ��ϸ��Ϣ��������
			IContractDetailDef iContractDetailDef = ContractDetailDefFactory.getLocalInstance(ctx);
			EntityViewInfo view = new EntityViewInfo();
	        FilterInfo filter = new FilterInfo();	        
	        filter.getFilterItems().add(new FilterItemInfo("contractType.id", contractTypeModel.getParent().getId().toString()));
	        view.setFilter(filter);
			ContractDetailDefCollection contractDetailDefCollection = iContractDetailDef.getContractDetailDefCollection(view);
//			ContractDetailDefCollection toSave = new ContractDetailDefCollection();
			ContractDetailDefInfo tmp = new ContractDetailDefInfo() ;
			if(contractDetailDefCollection.size()!=0){
				String str = contractTypeModel.getLongNumber().replaceAll("!","");
				for(int i = 0;i<contractDetailDefCollection.size();i++){
					tmp = new ContractDetailDefInfo();
					tmp.setName(contractDetailDefCollection.get(i).getName());
					tmp.setDataTypeEnum(contractDetailDefCollection.get(i).getDataTypeEnum());
					tmp.setContractType(contractTypeModel);					
					tmp.setNumber(str+contractDetailDefCollection.get(i).getNumber());
					tmp.setIsEnabled(true);
					iContractDetailDef.addnew(tmp);
				}				
			}			
			return pk;
		} else {
			IObjectPK pk = super._addnew(ctx, contractTypeModel);
			return pk;
		}
	}

    protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException
    {
    	trimBlank(model);
    	super._update(ctx, pk, model);
    }
    
    /**
	 * ���ǰ��ո�
	 * 
	 * @param id
	 * @return
	 */
	private IObjectValue trimBlank(IObjectValue model){
		ContractTypeInfo contractTypeModel = (ContractTypeInfo) model;
		if(contractTypeModel.getNumber() != null){
			contractTypeModel.setNumber(contractTypeModel.getNumber().trim());
		}
		if(contractTypeModel.getName() != null){
			contractTypeModel.setName(contractTypeModel.getName().trim());
		}
		if(contractTypeModel.getDescription() != null){
			contractTypeModel.setDescription(contractTypeModel.getDescription().trim());
		}
		return model;
	}
	
	/**
	 * @Modified By Owen_wen 2010-11-17 ����������������б��������ã�����ɾ��
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		// ֣���˵��ɾ����ʣ��ȫ��Ϊ����״̬Ҳ������
		ContractTypeInfo contractTypeInfo = this.getContractTypeInfo(ctx, pk);
		ContractTypeInfo cti = contractTypeInfo.getParent();
		if (contractTypeInfo.isIsEnabled() && cti != null) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", cti.getId().toString()));
			evi.setFilter(filter);
			if (this.getContractTypeCollection(ctx, evi).size() > 1) {
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				if(this.getContractTypeCollection(ctx, evi).size() <= 1)
					throw new FDCBasedataException(FDCBasedataException.DISENABLE_CANNOT_ONLY);
			}
		}

		if(!contractTypeInfo.isIsLeaf()){//��Ҷ��㣬������ɾ��
			throw new FDCBasedataException(FDCBasedataException.DELETE_ISPARENT_FAIL);
		}
		
		if(isRefByContractBill(ctx, contractTypeInfo)){ //����ͬ����
			throw new FDCBasedataException(FDCBasedataException.CONTRACTTYPE_DEL_REFERENCE);
		}
		
		if (isRefByInviteType(ctx, contractTypeInfo)) // ���б��������ã�����ɾ��
			throw new FDCBasedataException(FDCBasedataException.INVITETYPE_DEL_REFERENCE);
		
		super._delete(ctx, pk);
		//ͬʱɾ����صĺ�ͬ��ϸ��Ϣ������
		IContractDetailDef iContractDetailDef = ContractDetailDefFactory.getLocalInstance(ctx);
		iContractDetailDef.delete("where contractType.id = '" + contractTypeInfo.getId().toString() + "'");
	}

	/**
	 * �Ƿ��б���������
	 * @author owen_wen 
	 * @param ctx
	 * @param contractTypeInfo
	 * @return true ���б��������ã�false δ������
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private boolean isRefByInviteType(Context ctx, ContractTypeInfo contractTypeInfo) throws BOSException,
			EASBizException {
		FilterInfo inviteTypefilter = new FilterInfo();
		inviteTypefilter.getFilterItems().add(new FilterItemInfo("contractType", contractTypeInfo.getId().toString()));
		boolean isRefByInviteType = InviteTypeFactory.getLocalInstance(ctx).exists(inviteTypefilter);
		return isRefByInviteType;
	}

	/**
	 * �Ƿ񱻺�ͬ����
	 * @author owen_wen 
	 * @param ctx
	 * @param contractTypeInfo
	 * @return true ����ͬ���ã�falseδ����ͬ����
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private boolean isRefByContractBill(Context ctx, ContractTypeInfo contractTypeInfo) throws BOSException,
			EASBizException {
		FilterInfo contractFilter = new FilterInfo();
		contractFilter.getFilterItems().add(new FilterItemInfo("contractType", contractTypeInfo.getId().toString()));
		boolean isRefByContract = ContractBillFactory.getLocalInstance(ctx).exists(contractFilter);
		return isRefByContract;
	}

	protected boolean _disEnabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
		if (this.checkIsOnlyOneEnabled(ctx, ctPK)) {
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DISENABLE_CANNOT_ONLY);
		}
		if(changeUseStatus(ctx,ctPK,false))
			return true;
		else 
			return false;
	}
	private boolean checkIsOnlyOneEnabled(Context ctx, IObjectPK PK) throws BOSException, EASBizException {
		ContractTypeInfo cti = this.getContractTypeInfo(ctx, PK).getParent();
		if (cti != null) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", cti.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
			evi.setFilter(filter);
			if (this.getContractTypeCollection(ctx, evi).size() == 1) {
				return true;
			}
		}
		return false;
	}
	protected boolean _enabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
		boolean flag = false;
		IContractType iContractType = ContractTypeFactory.getLocalInstance(ctx);    
    	ContractTypeInfo contractTypeInfo = new ContractTypeInfo();

			contractTypeInfo = iContractType.getContractTypeInfo(ctPK);
	    	if(contractTypeInfo.getParent()!=null){
	    		IObjectPK parentPK = new ObjectStringPK(contractTypeInfo.getParent().getId().toString());
	    		ContractTypeInfo parentContractTypeInfo = iContractType.getContractTypeInfo(parentPK);
	    		if(!parentContractTypeInfo.isIsEnabled()){
	    			//����ϼ�������,������ʾ������    			
	    			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.CONTRACTTYPE_PARENT_DISENABLE); 			  			
	    		}else{
	    			if(changeUseStatus(ctx,ctPK,true))
	    				flag = true;
	    		}
	    			
	    	}else{
				if(changeUseStatus(ctx,ctPK,true))
					flag = true;
	    	}				

    	return flag;
	}
    /*
     */
    protected boolean changeUseStatus(Context ctx, IObjectPK PK,boolean flag) throws EASBizException, BOSException {
    	IContractType iContractType = ContractTypeFactory.getLocalInstance(ctx);    
    	ContractTypeInfo contractTypeInfo = iContractType.getContractTypeInfo(PK);    
    	
    		EntityViewInfo evi=new EntityViewInfo();
    		FilterInfo filter=new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("parent.id",contractTypeInfo.getId().toString()));
    		evi.setFilter(filter); 
    		ContractTypeCollection contractTypeCollection = iContractType.getContractTypeCollection(evi);
    		//������¼�,Ҫͬʱ����/�����¼�
    		if(contractTypeCollection.size()>0){
    		//������/�����Լ�
    			contractTypeInfo .setIsEnabled(flag);
    			_update(ctx,PK,contractTypeInfo);
    			//������/�����¼�	
    			ContractTypeInfo childContractTypeInfo ;
    			IObjectPK childPK;
    			for(int i = 0;i<contractTypeCollection.size();i++){
    				//boolean�����Ƚ�
    				if(contractTypeCollection.get(i).isIsEnabled()!=flag){
    					childContractTypeInfo = contractTypeCollection.get(i);
    					childContractTypeInfo.setIsEnabled(flag);
	    				childPK = new ObjectStringPK(childContractTypeInfo.getId().toString());
	    				changeUseStatus(ctx,childPK,flag);

    				}
    			}    			
    		}else{
    			//���û���¼�
    			contractTypeInfo.setIsEnabled(flag);
    			_update(ctx,PK,contractTypeInfo);
    		}
//    	}else{
    		
//    	}
    		
    		
     return true;
    }
    /**
     * ��ͬ�ĸ��ڵ��²�����ͬ�ı��롣
     * @param ctx Context
     * @param model DataBaseInfo
     * @throws BOSException
     * @throws EASBizException
     */
    protected void _checkNumberDup(Context ctx , IObjectValue model) throws
        BOSException , EASBizException
    {

        TreeBaseInfo treeModel = (TreeBaseInfo) model;

        //if no parent,no need to check
        FilterInfo filter = new FilterInfo();
        FilterItemInfo filterItem = null;
        //���ڵ�Ϊ��ʱ������������Ƿ��ظ���
        if (treeModel.innerGetParent() == null)
        {
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
                //filter.getFilterItems().add(new FilterItemInfo("level",new Integer(treeModel.getLevel()),CompareType.EQUALS));
                //�޸ģ�Ӧ��ʹ��parentID����Ϊlevle�Ǽ������ɵģ���Ӧ�ɿͻ��˴��ݡ� Jacky at 2004-11-4
                filter.setMaskString("#0 and #1 and #2");
            }
        }
        else
        {
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
        //CU����
//        FilterInfo filterCU = getFilterForDefaultCU(ctx,treeModel);
//        if(FilterUtility.hasFilterItem(filterCU))
//        {
//            if(FilterUtility.hasFilterItem(filter))
//            {
//                filter.mergeFilter(filterCU,"AND");
//            }
//            else
//            {
//                filter = filterCU;
//            }
//        }

        view.setFilter(filter);

        TreeBaseCollection results = this.getTreeBaseCollection(ctx , view);

        //DataBaseCollection results = this.getDataBaseCollection(ctx,view);

        if (results != null && results.size() > 0)
        {
            throw new TreeBaseException(TreeBaseException.CHECKNUMBERDUPLICATED ,
                                        new Object[]
                                        {treeModel.getNumber()});

        }

    }
    /**
     * Ĭ��ʵ�ֶ����� ͬ�ĳ����룬��ID��ͬ���д�������ɰ���Ҫ����ʵ�֡�
	 * @param ctx
	 * @param treeBaseInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws TreeBaseException
	 */
	protected void checkLNForTree(Context ctx, TreeBaseInfo treeBaseInfo) throws BOSException, EASBizException, TreeBaseException {
		//������ݿ������ͬlongNumber��ID��ͬ�����ݣ����쳣��
		FilterInfo lNfilter = new FilterInfo();
		lNfilter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_LongNumber,treeBaseInfo.getLongNumber()));
		lNfilter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.coreBase_ID,treeBaseInfo.getId().toString(),CompareType.NOTEQUALS));
		lNfilter.setMaskString("#0 AND #1");
		
//        //CU����
//        FilterInfo filterCU = getFilterForDefaultCU(ctx,treeBaseInfo);
//        if(FilterUtility.hasFilterItem(filterCU))
//        {
//            lNfilter.mergeFilter(filterCU,"AND");
//        }
        
		
		if(exists(ctx,lNfilter))
		{
			 throw new TreeBaseException(TreeBaseException.CHECKNUMBERDUPLICATED ,
		            new Object[]
		            {treeBaseInfo.getNumber()});
		}
	}

	protected IObjectValue _getNoPValue(Context ctx, IObjectPK pk) throws BOSException {
		Connection cn = null;
        IObjectValue iobjectvalue;
        cn = getConnection(ctx);
        EntityViewInfo view = new EntityViewInfo();
        IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
        EntityObjectInfo bo = null;
        if((this instanceof ICommonBOSType) && ((ICommonBOSType)this).getPK() != null)
            bo = loader.getEntity(((ICommonBOSType)this).getPK());
        else
            bo = loader.getEntity(getBOSType());
        FilterInfo filterData = ImplUtils.createFilter(bo, pk);
        view.setFilter(filterData);
        if(bo.getDefaultView() != null)
        {
            SelectorItemCollection selector = bo.getDefaultView().getSelector();
            view.setSelector(selector);
        }
        iobjectvalue = getDAO(ctx, cn).getValue(view);
        SQLUtils.cleanup(cn);
        return iobjectvalue;
	}
}