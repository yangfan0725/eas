package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractWFEntryFactory;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.IContractWFType;
import com.kingdee.eas.fdc.contract.ContractWFTypeCollection;
import com.kingdee.eas.fdc.contract.ContractWFTypeFactory;
import com.kingdee.eas.fdc.contract.ContractWFTypeInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class ContractWFTypeControllerBean extends AbstractContractWFTypeControllerBean{
	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		trimBlank(model);
		ContractWFTypeInfo contractTypeModel = (ContractWFTypeInfo) model;
		// ����Ƿ������ͬ�ı��룬����ھ��׳��쳣
		CtrlUnitInfo rootCU = new CtrlUnitInfo();
		rootCU.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		contractTypeModel.setCU(rootCU);
		super._checkNumberDup(ctx, contractTypeModel);
		super._checkNameBlank(ctx, contractTypeModel);
		super._checkNameDup(ctx, contractTypeModel);
		super._checkNumberBlank(ctx, contractTypeModel);

		// ȡ�ø����ڵ�
		ContractWFTypeInfo parentInfo = contractTypeModel.getParent();
		if (parentInfo != null && !parentInfo.getId().equals(contractTypeModel.getId())) {
			BOSUuid parentID = parentInfo.getId();
			parentInfo = super.getContractWFTypeInfo(ctx, new ObjectUuidPK(parentID));
			IObjectPK pk = new ObjectUuidPK(parentInfo.getId().toString());
			pk = super._addnew(ctx, contractTypeModel);
			
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
		ContractWFTypeInfo contractTypeModel = (ContractWFTypeInfo) model;
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
		ContractWFTypeInfo ContractWFTypeInfo = this.getContractWFTypeInfo(ctx, pk);
		ContractWFTypeInfo cti = ContractWFTypeInfo.getParent();
		if (ContractWFTypeInfo.isIsEnabled() && cti != null) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", cti.getId().toString()));
			evi.setFilter(filter);
			if (this.getContractWFTypeCollection(ctx, evi).size() > 1) {
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				if(this.getContractWFTypeCollection(ctx, evi).size() <= 1)
					throw new FDCBasedataException(FDCBasedataException.DISENABLE_CANNOT_ONLY);
			}
		}

		if(!ContractWFTypeInfo.isIsLeaf()){//��Ҷ��㣬������ɾ��
			throw new FDCBasedataException(FDCBasedataException.DELETE_ISPARENT_FAIL);
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractWFType.id", ContractWFTypeInfo.getId().toString()));
		if(ContractWFEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","�Ѿ�����ͬ�������ã����ܽ���ɾ��������"));
		}
		if(ContractBillFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","�Ѿ�����ͬ���ã����ܽ���ɾ��������"));
		}
		super._delete(ctx, pk);
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
		ContractWFTypeInfo cti = this.getContractWFTypeInfo(ctx, PK).getParent();
		if (cti != null) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", cti.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
			evi.setFilter(filter);
			if (this.getContractWFTypeCollection(ctx, evi).size() == 1) {
				return true;
			}
		}
		return false;
	}
	protected boolean _enabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
		boolean flag = false;
		IContractWFType iContractType = ContractWFTypeFactory.getLocalInstance(ctx);    
    	ContractWFTypeInfo ContractWFTypeInfo = new ContractWFTypeInfo();

			ContractWFTypeInfo = iContractType.getContractWFTypeInfo(ctPK);
	    	if(ContractWFTypeInfo.getParent()!=null){
	    		IObjectPK parentPK = new ObjectStringPK(ContractWFTypeInfo.getParent().getId().toString());
	    		ContractWFTypeInfo parentContractWFTypeInfo = iContractType.getContractWFTypeInfo(parentPK);
	    		if(!parentContractWFTypeInfo.isIsEnabled()){
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
    	IContractWFType iContractType = ContractWFTypeFactory.getLocalInstance(ctx);    
    	ContractWFTypeInfo ContractWFTypeInfo = iContractType.getContractWFTypeInfo(PK);    
    	
    		EntityViewInfo evi=new EntityViewInfo();
    		FilterInfo filter=new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("parent.id",ContractWFTypeInfo.getId().toString()));
    		evi.setFilter(filter); 
    		ContractWFTypeCollection contractTypeCollection = iContractType.getContractWFTypeCollection(evi);
    		//������¼�,Ҫͬʱ����/�����¼�
    		if(contractTypeCollection.size()>0){
    		//������/�����Լ�
    			ContractWFTypeInfo .setIsEnabled(flag);
    			_update(ctx,PK,ContractWFTypeInfo);
    			//������/�����¼�	
    			ContractWFTypeInfo childContractWFTypeInfo ;
    			IObjectPK childPK;
    			for(int i = 0;i<contractTypeCollection.size();i++){
    				//boolean�����Ƚ�
    				if(contractTypeCollection.get(i).isIsEnabled()!=flag){
    					childContractWFTypeInfo = contractTypeCollection.get(i);
    					childContractWFTypeInfo.setIsEnabled(flag);
	    				childPK = new ObjectStringPK(childContractWFTypeInfo.getId().toString());
	    				changeUseStatus(ctx,childPK,flag);

    				}
    			}    			
    		}else{
    			//���û���¼�
    			ContractWFTypeInfo.setIsEnabled(flag);
    			_update(ctx,PK,ContractWFTypeInfo);
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
}
