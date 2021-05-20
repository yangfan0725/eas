package com.kingdee.eas.fdc.finance.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fi.rpt.client.FormulaWizardUI;

public class FDCAcctFormulaWizard extends FormulaWizardUI
{
	private static final Logger logger = Logger.getLogger(FDCFormulaWizard.class);
	
	private KDBizPromptBox fdcCompany ; 
	
	private KDBizPromptBox fdcProject ;
	//��ͬF7
	private KDBizPromptBox fdcContractF7;
	//���ı���ͬF7
	private KDBizPromptBox fdcConWithoutTextF7;
	//����ԭ��
	private KDBizPromptBox adjustReasonF7;
	
	private KDTextField  longNumber ;
	
	private KDDatePicker month ;
	//����汾
	private KDTextField verNo ;
	
	public FDCAcctFormulaWizard() throws Exception
	{
		super();
		// TODO �Զ����ɹ��캯�����
	}
	/**
	 * ��ʾȡ������
	 */
	protected void onOpen()
	{
		fdcCompany = (KDBizPromptBox) this.getParamComponent("fdcCompany");
	
		//�ı���ʾ��ʽ
		final IFormatter formCompany = fdcCompany.getDisplayFormatter();
		IFormatter formatCompany = new IFormatter()
		{
			public void applyPattern(String pattern)
			{
				formCompany.applyPattern(pattern);
			}

			public String valueToString(Object o)
			{
				if (formCompany.valueToString(o) != null)
				{
					return formCompany.valueToString(o).replaceAll("!",
							"\\.");
				}
				return null;
			}
		};
		fdcCompany.setDisplayFormatter(formatCompany);
		fdcCompany.setEnabledMultiSelection(false);
		
		fdcProject = (KDBizPromptBox) this.getParamComponent("fdcProject");

		//�ı���ʾ��ʽ
		final IFormatter formProject = fdcProject.getDisplayFormatter();
		IFormatter formatProject = new IFormatter()
		{
			public void applyPattern(String pattern)
			{
				formProject.applyPattern(pattern);
			}

			public String valueToString(Object o)
			{
				if (formProject.valueToString(o) != null)
				{
					return formProject.valueToString(o).replaceAll("!",
							"\\.");
				}
				return null;
			}
		};
		
		fdcProject.setDisplayFormatter(formatProject);
		fdcProject.setEnabledMultiSelection(false);
		
		/** ��Ӱ��յ�ǰ������֯�Թ�����Ŀ���й��ˣ�ֻ��ȡ��ǰ��֯�¶�Ӧ�Ĺ�����Ŀ(begin) -by neo*/
		EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",
    			SysContext.getSysContext().getCurrentFIUnit().getId()));
		view.setFilter(filter);
		fdcProject.setEntityViewInfo(view);
		/**��Ӱ��յ�ǰ������֯�Թ�����Ŀ���й��ˣ�ֻ��ȡ��ǰ��֯�¶�Ӧ�Ĺ�����Ŀ(end) -by neo*/
		
		longNumber = (KDTextField) this.getParamComponent("longNumber");
		if(paramComponents.containsKey("month")){
			month = (KDDatePicker)this.getParamComponent("month");			
		}
		if(paramComponents.containsKey("verNo")){
			verNo = (KDTextField)getParamComponent("verNo");
		}
		
		fdcProject.setEnabled(false);
		
		/**
		 * ����˾ѡ��ֵ����Ŀ����ѡ
		 */
		fdcCompany.addDataChangeListener(new DataChangeListener()
				{
					public void dataChanged(DataChangeEvent eventObj) 
					{
						if(eventObj.getNewValue()!=null)
						{
							fdcProject.setEnabled(true);
						}
						else
						{
							fdcProject.setEnabled(false);
						}
						
					}
				});
		//��ͬ
		if(paramComponents.containsKey("fdcContract")){
			fdcContractF7 = (KDBizPromptBox) this.getParamComponent("fdcContract");		
			fdcContractF7.setEnabledMultiSelection(false);
		}
		//���ı���ͬ
		if(paramComponents.containsKey("fdcConWithoutText")){
			fdcConWithoutTextF7 = (KDBizPromptBox) this.getParamComponent("fdcConWithoutText");		
			fdcConWithoutTextF7.setEnabledMultiSelection(false);
		}
		if(paramComponents.containsKey(""))
		//����ԭ��
		if(paramComponents.containsKey("adjustReasonF7")){
			adjustReasonF7 = (KDBizPromptBox) this.getParamComponent("adjustReasonF7");		
			adjustReasonF7.setEnabledMultiSelection(false);
		}
		if(fdcContractF7 != null){
			fdcProject.addDataChangeListener(new DataChangeListener() {
				public void dataChanged(DataChangeEvent event) {
					try {
						if(fdcProject.getValue()!=null){
							if (!(fdcProject.getValue() instanceof String)) {
								setFDCContractFilter(((CurProjectInfo) fdcProject.getValue()).getId().toString());
							} else {
								setFDCContractFilter((String) fdcProject.getValue());
							}
						}else{
							fdcProject.setValue(null);
							return ;
						}
					} catch (Exception e) {
						logger.error(e);
					}
				}
			});						
		}
		if(fdcConWithoutTextF7 != null){
			fdcProject.addDataChangeListener(new DataChangeListener() {
				public void dataChanged(DataChangeEvent event) {
					try {
						if(fdcProject.getValue()!=null){
							if (!(fdcProject.getValue() instanceof String)) {
								setFDCConNoTextFilter(((CurProjectInfo) fdcProject.getValue()).getId().toString());
							} else {
								setFDCConNoTextFilter((String) fdcProject.getValue());
							}
						}else{
							fdcProject.setValue(null);
							return ;
						}
					} catch (Exception e) {
						logger.error(e);
					}
				}
			});	
		}
		
	}
	
	/**
	 * ���ݹ�˾������֯���˹�����Ŀ
	 * @param id
	 */
	private void setFDCProjectFilter(String id){
		
	}
	
	/**
	 * ���ݹ�����Ŀ���˺�ͬ
	 * 
	 * @param id
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void setFDCContractFilter(String id) throws BOSException, EASBizException {
		if(id==null){
			fdcContractF7.setValue(null);
			return ;
		}else{
			if(fdcContractF7.getValue()!=null){
				//String 
				if(fdcContractF7.getValue() instanceof ContractBillInfo){
					ContractBillInfo info = (ContractBillInfo)fdcContractF7.getValue();
					if(!id.equals(info.getCurProject().getId().toString())){
						fdcContractF7.setValue(null);
						fdcContractF7.setText(null);
						fdcContractF7.setData(null);
						fdcContractF7.repaint();
					}
				}else if(fdcContractF7.getValue() instanceof String){
					String number = (String)fdcContractF7.getValue() ;

					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("number", number));
					filter.getFilterItems().add(new FilterItemInfo("curProject.id", id));
					
					if(!ContractBillFactory.getRemoteInstance().exists(filter)){
						fdcContractF7.setValue(null);
						fdcContractF7.setText(null);
						fdcContractF7.setData(null);
					}
				}
			}
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", id));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		view.setFilter(filter);

		ContractBillCollection coll = ContractBillFactory.getRemoteInstance().getContractBillCollection(view);
		if (coll == null || coll.size() == 0) {
			//
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", null));
			view.setFilter(filter);
			fdcContractF7.getQueryAgent().setEntityViewInfo(view);
			fdcContractF7.getQueryAgent().resetRuntimeEntityView();
			return;
		}
		fdcContractF7.getQueryAgent().setEntityViewInfo(view);
		fdcContractF7.getQueryAgent().resetRuntimeEntityView();
	}
	/**
	 * ���ݹ�����Ŀ�������ı���ͬ
	 * 
	 * @param id
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void setFDCConNoTextFilter(String id) throws BOSException, EASBizException {
		if(id==null){
			fdcConWithoutTextF7.setValue(null);
			return ;
		}else{
			if(fdcConWithoutTextF7.getValue()!=null){
				//String 
				if(fdcConWithoutTextF7.getValue() instanceof CurProjectInfo){
					ContractWithoutTextInfo info = (ContractWithoutTextInfo)fdcConWithoutTextF7.getValue();
					if(!id.equals(info.getCurProject().getId().toString())){
						fdcConWithoutTextF7.setValue(null);
						fdcConWithoutTextF7.setText(null);
						fdcConWithoutTextF7.setData(null);
						fdcConWithoutTextF7.repaint();
					}
				}else if(fdcConWithoutTextF7.getValue() instanceof String){
					String number = (String)fdcConWithoutTextF7.getValue() ;

					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("number", number));
					filter.getFilterItems().add(new FilterItemInfo("curProject.id", id));
					
					if(!ContractWithoutTextFactory.getRemoteInstance().exists(filter)){
						fdcConWithoutTextF7.setValue(null);
						fdcConWithoutTextF7.setText(null);
						fdcConWithoutTextF7.setData(null);
					}
				}
			}
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", id));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		view.setFilter(filter);

		ContractWithoutTextCollection coll = ContractWithoutTextFactory
			.getRemoteInstance().getContractWithoutTextCollection(view);
		if (coll == null || coll.size() == 0) {
			//
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", null));
			view.setFilter(filter);
			fdcConWithoutTextF7.getQueryAgent().setEntityViewInfo(view);
			fdcConWithoutTextF7.getQueryAgent().resetRuntimeEntityView();
			return;
		}
		fdcConWithoutTextF7.getQueryAgent().setEntityViewInfo(view);
		fdcConWithoutTextF7.getQueryAgent().resetRuntimeEntityView();
	}
}
