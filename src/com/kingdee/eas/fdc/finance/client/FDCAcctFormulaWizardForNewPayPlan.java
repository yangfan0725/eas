package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fi.rpt.client.FormulaWizardUI;
import com.kingdee.eas.fi.rpt.client.ParamInductor;
import com.kingdee.eas.util.SysUtil;

public class FDCAcctFormulaWizardForNewPayPlan extends FormulaWizardUI {

	public FDCAcctFormulaWizardForNewPayPlan() throws Exception {
		super();
	}

	private KDBizPromptBox fdcCompany;// ��˾

	private KDBizPromptBox fdcProject;// ������Ŀ

	private KDBizPromptBox costAccountF7;// �ɱ���Ŀ

	private KDDatePicker month;// �·�

	protected void onOpen() {
		/**
		 * ��˾
		 */
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
				}
			}
		});

		/**
		 * ������Ŀ
		 */
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
		if (fdcProject != null) {
			fdcProject.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					if (fdcCompany.getValue() != null
							&& !"".equals(fdcCompany.getValue())) {
						if (!(fdcCompany.getValue() instanceof String)
								&& fdcCompany.getValue() instanceof CompanyOrgUnitInfo) {
							setFDCProject(((CompanyOrgUnitInfo) fdcCompany
									.getValue()).getId().toString());
						} else if (!(fdcCompany.getValue() instanceof String)
								&& fdcCompany.getValue() instanceof FullOrgUnitInfo) {
							setFDCProject(((FullOrgUnitInfo) fdcCompany
									.getValue()).getId().toString());
						} else if (fdcCompany.getValue() instanceof String) {
							setFDCProject((String) fdcCompany.getValue());
						}
					} else {
						FDCMsgBox.showWarning(
								FDCAcctFormulaWizardForNewPayPlan.this,
								"����ѡ��˾");
						SysUtil.abort();
					}
				}
			});
			fdcProject.setDisplayFormatter(formatProject);
			fdcProject.setEnabledMultiSelection(false);

			/**
			 * �ɱ���Ŀ
			 */
			costAccountF7 = (KDBizPromptBox) this
					.getParamComponent("longNumber");
			// �ı���ʾ��ʽ
			final IFormatter formatCostAccountF7 = costAccountF7
					.getDisplayFormatter();
			IFormatter formatCostAccount = new IFormatter() {
				public void applyPattern(String pattern) {
					formatCostAccountF7.applyPattern(pattern);
				}

				public String valueToString(Object o) {
					if (formatCostAccountF7.valueToString(o) != null) {
						return formatCostAccountF7.valueToString(o).replaceAll(
								"!", "\\.");
					}
					return null;
				}
			};
			/**
			 * �·�
			 */
			month = (KDDatePicker) this.getParamComponent("month");

			if (fdcProject != null) {
				fdcProject.addSelectorListener(new SelectorListener() {
					public void willShow(SelectorEvent e) {
						setFDCProject(null);
					}
				});

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
									FDCMsgBox
											.showWarning(
													FDCAcctFormulaWizardForNewPayPlan.this,
													"����ѡ�񹤳���Ŀ");
									SysUtil.abort();
								}
							} catch (BOSException e1) {
								e1.printStackTrace();
							}
						}
					});
				}
			}
		}
	}

	/**
	 * ���ݹ�����Ŀ���˳ɱ���Ŀ
	 */
	private void setFDCCostAccountFilter(String id) throws BOSException {
		costAccountF7.getQueryAgent().resetRuntimeEntityView();
		if (id == null) {
			costAccountF7.setValue(null);
			return;
		} else {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(new FilterItemInfo("curProject.id", id));
			filter.getFilterItems().add(
					new FilterItemInfo("curProject.longNumber", id));
			// filter.getFilterItems().add(new
			// FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.setMaskString(" #0 or #1 ");
			view.setFilter(filter);
			costAccountF7.getQueryAgent().setEntityViewInfo(view);
		}
	}

	/**
	 * ������֯���˳�������Ŀ
	 */
	private void setFDCProject(String companyId) {
		fdcProject.getQueryAgent().resetRuntimeEntityView();
		if (companyId == null) {
			fdcProject.setValue(null);
			/** ��Ӱ��յ�ǰ������֯�Թ�����Ŀ���й��ˣ�ֻ��ȡ��ǰ��֯�¶�Ӧ�Ĺ�����Ŀ(begin) -by neo */
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", SysContext
							.getSysContext().getCurrentFIUnit().getId()));
			// ����ѡȡ��ϸ������Ŀ
			filter.getFilterItems().add(
					new FilterItemInfo("isLeaf", Boolean.TRUE));
			view.setFilter(filter);
			fdcProject.setEntityViewInfo(view);
			/** ��Ӱ��յ�ǰ������֯�Թ�����Ŀ���й��ˣ�ֻ��ȡ��ǰ��֯�¶�Ӧ�Ĺ�����Ŀ(end) -by neo */
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
}
