package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.EventListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fi.rpt.client.FormulaWizardUI;
import com.kingdee.eas.fi.rpt.client.ParamInductor;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @author Cassiel_peng
 * @see com.kingdee.eas.fdc.finance.client.FDCAcctFormulaWizard
 * @work 
 *       �����ᵥ��Ŀǰϵͳ��fdc_acct_actual_pay��fdc_acct_aimcost��fdc_acct_conCost_split������ͨ��
 *       ���¶ȡ��ֶν����ڼ�ѡ��ֻ�ܻ�ȡĳһ�·ݵ����ݣ������ܻ�ȡ������ݺ��ۼ����ݣ� ��ˣ���Ҫͨ������ʼ�ڼ䡱�͡������ڼ䡱���й�����ȡ����
 *       ����������Ҫ����FDCAcctFormulaWizard������Ϊ��������������������ã�����ά�������ٷ�װһ������������ᵥ����
 *       2009-10-10
 */
public class FDCAcctFormulaWizardForR090921_057 extends FormulaWizardUI {
	private static final Logger logger = Logger
			.getLogger(FDCFormulaWizard.class);

	private KDBizPromptBox fdcCompany;

	private KDBizPromptBox fdcProject;
	// ��ͬF7
	private KDBizPromptBox fdcContractF7;
	// �ɱ���Ŀ
	private KDBizPromptBox costAccountF7;
	// ��ʼ�ڼ�
	private KDDatePicker beginPeriod;
	// �����ڼ�
	private KDDatePicker endPeriod;
	// ��ʾȫ����֯
	private KDComboBox displayAllCompany;

	public FDCAcctFormulaWizardForR090921_057() throws Exception {
		super();
		// TODO �Զ����ɹ��캯�����
	}

	/**
	 * ��ʾȡ������
	 */
	protected void onOpen() {
		fdcCompany = (KDBizPromptBox) this.getParamComponent("fdcCompany");

		// �ı���ʾ��ʽ
		final IFormatter formCompany = fdcCompany.getDisplayFormatter();
		IFormatter formatCompany = new IFormatter() {
			public void applyPattern(String pattern) {
				formCompany.applyPattern(pattern);
			}

			public String valueToString(Object o) {
				if (formCompany.valueToString(o) != null) {
					return formCompany.valueToString(o).replaceAll("!", "\\.");
				}
				return null;
			}
		};
		fdcCompany.setDisplayFormatter(formatCompany);
		fdcCompany.setEnabledMultiSelection(false);

		displayAllCompany = (KDComboBox) this
				.getParamComponent("displayAllCompany");

		fdcProject = (KDBizPromptBox) this.getParamComponent("fdcProject");

		// �ı���ʾ��ʽ
		final IFormatter formProject = fdcProject.getDisplayFormatter();
		IFormatter formatProject = new IFormatter() {
			public void applyPattern(String pattern) {
				formProject.applyPattern(pattern);
			}

			public String valueToString(Object o) {
				if (formProject.valueToString(o) != null) {
					return formProject.valueToString(o).replaceAll("!", "\\.");
				}
				return null;
			}
		};

		fdcProject.setDisplayFormatter(formatProject);
		fdcProject.setEnabledMultiSelection(false);

		if (paramComponents.containsKey("longNumber")) {
			costAccountF7 = (KDBizPromptBox) this
					.getParamComponent("longNumber");
			costAccountF7.setEnabledMultiSelection(false);
		}
		// �ı���ʾ��ʽ
		final IFormatter formatCostAccountF7 = costAccountF7
				.getDisplayFormatter();
		IFormatter formatCostAccount = new IFormatter() {
			public void applyPattern(String pattern) {
				formatCostAccountF7.applyPattern(pattern);
			}

			public String valueToString(Object o) {
				if (formatCostAccountF7.valueToString(o) != null) {
					return formatCostAccountF7.valueToString(o).replaceAll("!",
							"\\.");
				}
				return null;
			}
		};

		if (paramComponents.containsKey("beginPeriod")) {
			beginPeriod = (KDDatePicker) this.getParamComponent("beginPeriod");
		}
		if (paramComponents.containsKey("endPeriod")) {
			endPeriod = (KDDatePicker) this.getParamComponent("endPeriod");
		}

		// ��"��ʾ������֯"Ϊ��ʱ������Ҫ��˾ѡ��ֵ����Ŀ����ѡ.
		// ��"��ʾ������֯"Ϊ��ʱ����֧��ѡ��������˾��Ŀ�������¹�˾���� by Cassiel_peng 2009-10-10
		fdcCompany.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				if (eventObj.getNewValue() == null||!eventObj.getNewValue().equals(eventObj.getOldValue())) {// ûѡֵ�����Ǵ˴�ѡ���ֵ���ϴεĲ�һ��������Ҫ�ѹ�����Ŀ�ÿ�
					fdcProject.setValue(null);
					fdcProject.setText(null);
					try {
						((KDTextField)((ParamInductor)paramComponents.get("fdcProject")).getBaseComponent()).setText("");
					} catch (Exception e) {
						
					}
					costAccountF7.setText(null);
					costAccountF7.setValue(null);
					try {
						((KDTextField)((ParamInductor)paramComponents.get("longNumber")).getBaseComponent()).setText("");
					} catch (Exception e) {
						
					}
					fdcProject.requestFocus();
					costAccountF7.requestFocus();
					costAccountF7.updateData();
					
					if(fdcContractF7!=null){
						fdcContractF7.setValue(null);
						fdcContractF7.setText(null);
						fdcContractF7.requestFocus();
						try {
							((KDTextField)((ParamInductor)paramComponents.get("fdcConAndNoTextConF7")).getBaseComponent()).setText("");
						} catch (Exception e) {
							
						}
					}
					
					fdcCompany.requestFocus();
				}
				if (displayAllCompany.getSelectedIndex() == 0) {// ��
						if (!(fdcCompany.getValue() instanceof String)) {
							try {
								if (!(fdcCompany.getValue() instanceof String&&fdcCompany.getValue() instanceof CompanyOrgUnitInfo)) {
									setFDCProject(((CompanyOrgUnitInfo) fdcCompany.getValue()).getId().toString());
								} else if(!(fdcCompany.getValue() instanceof String&&fdcCompany.getValue() instanceof FullOrgUnitInfo)){
									setFDCProject((String) fdcCompany.getValue());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							setFDCProject((String) fdcCompany.getValue());
						}
					}else{
						setFDCProject(null);
					}
				}
		});

		displayAllCompany.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (displayAllCompany.getSelectedIndex() == 1) {// ��ʾȫ����֯
					setFDCProject(null);
				} else {
					if (fdcCompany.getValue() != null
							&& !"".equals(fdcCompany.getValue())) {
						try {if (!(fdcCompany.getValue() instanceof String) && fdcCompany.getValue() instanceof CompanyOrgUnitInfo) {
							setFDCProject(((CompanyOrgUnitInfo) fdcCompany.getValue()).getId().toString());
						} else if (!(fdcCompany.getValue() instanceof String && fdcCompany.getValue() instanceof FullOrgUnitInfo)) {
							setFDCProject(((FullOrgUnitInfo) fdcCompany	.getValue()).getId().toString());
						}else if(fdcCompany.getValue() instanceof String){
							setFDCProject((String) fdcCompany.getValue());
						}
						} catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
					} 
				}
			}

		});

		if (fdcProject != null) {
			fdcProject.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					if(displayAllCompany.getSelectedIndex()==0){//����ʾ������֯
						if (fdcCompany.getValue() != null&& !"".equals(fdcCompany.getValue())) {
							if (!(fdcCompany.getValue() instanceof String) && fdcCompany.getValue() instanceof CompanyOrgUnitInfo) {
								setFDCProject(((CompanyOrgUnitInfo) fdcCompany.getValue()).getId().toString());
							} else if (!(fdcCompany.getValue() instanceof String) && fdcCompany.getValue() instanceof FullOrgUnitInfo) {
								setFDCProject(((FullOrgUnitInfo) fdcCompany	.getValue()).getId().toString());
							}else if(fdcCompany.getValue() instanceof String){
								setFDCProject((String) fdcCompany.getValue());
							}
						} else {
								FDCMsgBox.showWarning(FDCAcctFormulaWizardForR090921_057.this,"����ѡ��˾");
								SysUtil.abort();
							}
						}else{//��ʾ������֯
						
					}
						
					}

			});
			fdcProject.addDataChangeListener(new DataChangeListener() {
				public void dataChanged(DataChangeEvent eventObj) {
					EventListener[] listeners = fdcCompany.getListeners(DataChangeListener.class);
					for(int i=0;i<listeners.length;i++){
						fdcCompany.removeDataChangeListener((DataChangeListener)listeners[i]);
					}
					if (fdcProject.getValue() != null
							&& !"".equals(fdcProject.getValue())) {
						CurProjectInfo curProject = (CurProjectInfo) fdcProject
								.getValue();
						if (curProject.getId() != null) {
							String prjId = curProject.getId().toString();
							SelectorItemCollection selector = new SelectorItemCollection();
							selector.add("fullOrgUnit.name");
							selector.add("fullOrgUnit.longNumber");
							selector.add("startDate");
							try {
								CurProjectInfo curProjectBill = CurProjectFactory
										.getRemoteInstance()
										.getCurProjectInfo(
												new ObjectUuidPK(BOSUuid
														.read(prjId)), selector);
								fdcCompany.setValue(curProjectBill
										.getFullOrgUnit().getLongNumber());
								fdcCompany.requestFocus();// ��ѡȡ�˹�����Ŀ���˾ֵ��ʱ��˾��ֵ���ἴʱ����
															// ����˴�����
								beginPeriod.setValue(curProjectBill
										.getStartDate());
								beginPeriod.requestFocus();
								fdcProject.requestFocus();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					for(int i=0;i<listeners.length;i++){
						fdcCompany.addDataChangeListener((DataChangeListener)listeners[i]);
					}
				}
			});
		}
		if (costAccountF7 != null) {
			costAccountF7.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					try {
						if (fdcProject.getValue() != null
								&& !"".equals(fdcProject.getValue())) {
							if (!(fdcProject.getValue() instanceof String)) {
								setFDCCostAccountFilter(((CurProjectInfo) fdcProject
										.getValue()).getId().toString());
							} else {
								setFDCCostAccountFilter((String) fdcProject
										.getValue());
							}
						} else {
							FDCMsgBox.showWarning(
									FDCAcctFormulaWizardForR090921_057.this,
									"����ѡ�񹤳���Ŀ");
							SysUtil.abort();
						}
					} catch (EASBizException e1) {
						e1.printStackTrace();
					} catch (BOSException e1) {
						logger.error(e1);
					}
				}

			});
		}

		// ��ͬ
		if (paramComponents.containsKey("fdcConAndNoTextConF7")) {
			fdcContractF7 = (KDBizPromptBox) this
					.getParamComponent("fdcConAndNoTextConF7");
			fdcContractF7.setEnabledMultiSelection(false);
		}
		if (fdcContractF7 != null) {
			fdcContractF7.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					try {
						if (fdcProject.getValue() != null
								&& !"".equals(fdcProject.getValue())) {
							if (!(fdcProject.getValue() instanceof String)) {
								setFDCContractFilter(((CurProjectInfo) fdcProject
										.getValue()).getId().toString());
							} else {
								setFDCContractFilter((String) fdcProject
										.getValue());
							}
						} else {
							FDCMsgBox.showWarning(
									FDCAcctFormulaWizardForR090921_057.this,
									"����ѡ�񹤳���Ŀ");
							SysUtil.abort();
						}
					} catch (EASBizException e1) {
						e1.printStackTrace();
					} catch (BOSException e1) {
						logger.error(e1);
					}
				}

			});
		}
	}

	/**
	 * ������֯���˳�������Ŀ
	 */
	private void setFDCProject(String companyId) {
		fdcProject.getQueryAgent().resetRuntimeEntityView();
		if (companyId == null) {
			fdcProject.setValue(null);	
			/** ��Ӱ��յ�ǰ������֯�Թ�����Ŀ���й��ˣ�ֻ��ȡ��ǰ��֯�¶�Ӧ�Ĺ�����Ŀ(begin) -by neo*/
			EntityViewInfo view = new EntityViewInfo();
	    	FilterInfo filter = new FilterInfo();
//	    	filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",
//	    			SysContext.getSysContext().getCurrentFIUnit().getId()));
	    	// ����ѡȡ��ϸ������Ŀ
	    	filter.getFilterItems().add(
	    			new FilterItemInfo("isLeaf", Boolean.TRUE));
			view.setFilter(filter);
			fdcProject.setEntityViewInfo(view);
			/**��Ӱ��յ�ǰ������֯�Թ�����Ŀ���й��ˣ�ֻ��ȡ��ǰ��֯�¶�Ӧ�Ĺ�����Ŀ(end) -by neo*/
			return;
		} else {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", companyId));
			filter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.longNumber", companyId));
			// ����ѡȡ��ϸ������Ŀ
			filter.getFilterItems().add(
					new FilterItemInfo("isLeaf", Boolean.TRUE));
			filter.setMaskString("( #0 or #1) and #2");
			view.setFilter(filter);
			fdcProject.getQueryAgent().setEntityViewInfo(view);
		}
	}

	/**
	 * ���ݹ�����Ŀ���˳ɱ���Ŀ
	 */
	private void setFDCCostAccountFilter(String id) throws BOSException,
			EASBizException {
		costAccountF7.getQueryAgent().resetRuntimeEntityView();
		if (id == null) {
			costAccountF7.setValue(null);
			return;
		} else {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(new FilterItemInfo("curProject.id", id));
			filter.getFilterItems()
			.add(new FilterItemInfo("curProject.longNumber", id));
			// filter.getFilterItems().add(new
			// FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.setMaskString(" #0 or #1 ");
			view.setFilter(filter);
			costAccountF7.getQueryAgent().setEntityViewInfo(view);
		}
	}

	/**
	 * ���ݹ�����Ŀ���˺�ͬ
	 */
	private void setFDCContractFilter(String idOrLongNubmber) throws BOSException,
			EASBizException {
		fdcContractF7.getQueryAgent().resetRuntimeEntityView();
		if (idOrLongNubmber == null) {
			fdcContractF7.setValue(null);
			return;
		} else {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(new FilterItemInfo("curProject.id", idOrLongNubmber));
			//�����ʱ���ݹ������Ѿ����ǹ�����Ŀ��ID���ǳ�����Ļ�
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select FID from T_FDC_CurProject where FLongNumber = ? ");
			builder.addParam(idOrLongNubmber);
			IRowSet rowSet = builder.executeQuery();
			try {
				if(rowSet.next()){
					idOrLongNubmber = rowSet.getString("FID");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			filter.getFilterItems()
			.add(new FilterItemInfo("curProject.id", idOrLongNubmber));
			filter.setMaskString(" #0 or #1 ");
			// filter.getFilterItems().add(new
			// FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			view.setFilter(filter);
			fdcContractF7.getQueryAgent().setEntityViewInfo(view);
		}
	}
}
