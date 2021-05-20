/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillCollection;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillFactory;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class TasksIn2MStoneListUI extends AbstractTasksIn2MStoneListUI
{
    private static final Logger logger = CoreUIObject.getLogger(TasksIn2MStoneListUI.class);
    
    /**
     * output class constructor
     */
    public TasksIn2MStoneListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
		super.onLoad();
		execQuery();
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo)
    {
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", getUIContext().get("fromTask"), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("longNumber", getUIContext().get("toTask"), CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("project.id", getUIContext().get("project"), CompareType.EQUALS));
//		SorterItemCollection sorter  = new SorterItemCollection();
//		SorterItemInfo si = new SorterItemInfo("longNumber");
//		sorter.add(si);
//		view.setSorter(sorter);
		view.setFilter(filter);
		
        return super.getQueryExecutor(queryPK, view);
    }
    
	protected void execQuery() {
		try {
	   	    super.execQuery();
			initStateCell();
		} catch (BOSException e) {
			handUIException(e);
		} catch (Exception e) {
			handUIException(e);
		}
	}
	//TODO �ø��ӣ��Ȱ��������������һ�¡�����
	/*
	 * չʾ��ʱ��ѭ������У����ݸ������е�ֵ��������ʾ��stateLogo�е�չʾ��ʽ��<br>
	 * 
	 * ע�⣺<br> 1���򹳵ĵ�Ԫ����Ҫ��������Ϊ���壬����̫ϸ����Ȧ������Ҫ��<br>
	 * 2����ɫδʹ�ñ�׼��Color.Green��Color.Orange����ʹ�����¹����Ľ�����ɫ���棬<br>
	 * ����Ϊ��ɫ̫�������״��ۣ�����ʹ��ʱ��������ȡ�
	 */
	public void initStateCell() throws BOSException {
		String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
		// ��
		String achieve = EASResource.getString(rsPath, "achieve");
		// Ȧ
		String pending = EASResource.getString(rsPath, "pending");
		// ��
		Color red = new Color(245, 0, 0);
		// ��
		Color green = new Color(10, 220, 10);
		// ��
		Color orange = new Color(220, 180, 0);
		boolean isNeedEvaluation =  getUIContext().get("isNeedEvaluation").equals(true);
		// ȡ����ǰ������̱������SRCID
		Set<String> srcIDs = new HashSet<String>();
		Map evaluationMap = null;
		IRow row = null;
		if (isNeedEvaluation) {
			evaluationMap = new HashMap();
			for (int i = 0; i < tblMain.getRowCount(); i++) {
				row = tblMain.getRow(i);
				if (row.getCell("srcid").getValue() != null) {
					srcIDs.add(row.getCell("srcid").getValue() + "");
				}
			}

			EntityViewInfo view = new EntityViewInfo();
			
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("evaluationResult.evaluationPass");
			view.setSelector(sic);
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("srcRelateTask", srcIDs, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("evaluationType", TaskEvaluationTypeEnum.SCHEDULE));
			
			SorterItemCollection sorter = new SorterItemCollection();
			SorterItemInfo si = new SorterItemInfo("lastupdatetime");
			si.setSortType(SortType.DESCEND);
			view.setSorter(sorter);
			view.setFilter(filter);

			try {
				TaskEvaluationBillCollection cols = TaskEvaluationBillFactory.getRemoteInstance().getTaskEvaluationBillCollection(view);
				TaskEvaluationBillInfo evaInfo = null;
				TaskEvaluationBillInfo otherInfo = null;
				for (int i = 0; i < cols.size(); i++) {
					evaInfo = cols.get(i);
					// if (!evaluationMap.containsKey(bill.getSrcRelateTask()))
					// evaluationMap.put(bill.getSrcRelateTask(),
					// bill.getEvaluationResult().isEvaluationPass());
					
					if (evaInfo.getEvaluationResult().isEvaluationPass()) {
						if (evaluationMap.get(evaInfo.getSrcRelateTask()) == null) {
							evaluationMap.put(evaInfo.getSrcRelateTask(), evaInfo);
						}
					} else {
						if (evaluationMap.get(evaInfo.getSrcRelateTask()) != null) {
							otherInfo = (TaskEvaluationBillInfo) evaluationMap.get(evaInfo.getSrcRelateTask());
							if (DateTimeUtils.dateDiff(evaInfo.getLastUpdateTime(), otherInfo.getLastUpdateTime()) < 0) {
								evaluationMap.remove(evaInfo.getSrcRelateTask());
								//evaMap.put(evaInfo.getSrcRelateTask(),evaInfo)
								// ;
							}

						}
					}
				}
			} catch (BOSException e) {
				handUIException(e);
			}

		}

		for (int i = 0; i < tblMain.getRowCount(); i++) {
			row = tblMain.getRow(i);
			if (row != null) {
				if (null == row.getCell("complete").getValue()) {
					row.getCell("complete").setValue("0");
				}
				int state = ((Integer) row.getCell("hiddenStatus").getValue()).intValue();
				int complete = new BigDecimal(row.getCell("complete").getValue().toString()).intValue();
				String srcId = "";
				String evaluationType = "";
				if (null != row.getCell("srcid").getValue()) {
					srcId = row.getCell("srcid").getValue().toString();
				}

			
				/*
				 * ��������ʱ���깤�ٷֱȵ���100%�����Ҿ����������ۣ�����ȷ����������״̬ ��ʱδ��ɶ���������˵��һ����
				 * 1���깤�ٷֱ�С��100%�����Ҽƻ���������ڵ�ǰ����֮ǰ 2���깤�ٷֱȵ���100%����Ҫ���ݲ���������Դ�
				 * a���깤�����ѽ����˽������� b���깤δ���н�������
				 */

				row.getCell("status").getStyleAttributes().setBold(true);
				if (state == 3) {
					Date end = (Date) row.getCell("end").getValue();
					Date currDate = FDCDateHelper.getServerTimeStamp();
					if (DateUtils.compareUpToDay(currDate, end) > 0) {
						row.getCell("status").setValue(pending);
						row.getCell("status").getStyleAttributes().setFontColor(red);
						row.getCell("hiddenStatus").setValue(3);
					} else {
						row.getCell("hiddenStatus").setValue(2);
					}
					continue;
				}
				
				
				if (isNeedEvaluation) {// ��Ҫ��������
					if (evaluationMap.containsKey(srcId) && complete == 100) {
						boolean isPass = ((TaskEvaluationBillInfo) evaluationMap.get(srcId)).getEvaluationResult().isEvaluationPass();
						if (isPass) {
							if (state == 0) {
								row.getCell("status").setValue(achieve);
								row.getCell("status").getStyleAttributes().setFontColor(green);
								row.getCell("hiddenStatus").setValue(new Integer(0));
							}
							if (state == 1) {
								row.getCell("status").setValue(achieve);
								row.getCell("status").getStyleAttributes().setFontColor(red);
								row.getCell("hiddenStatus").setValue(new Integer(1));
								// update by libing
								// ͬһ�죬����ʱ��������ȷ�Ļ������ܴ������⣬�����ֶ���һ��
								if (row.getCell("end") != null && row.getCell("actualEndDate") != null) {
									int finalstatus = DateUtils.compareUpToDay((Date) row.getCell("actualEndDate").getValue(), (Date) row.getCell("end").getValue());
									if (finalstatus <= 0) {
										row.getCell("status").getStyleAttributes().setFontColor(green);
										row.getCell("hiddenStatus").setValue(new Integer(0));
									}
								}
							}
						} else {
							row.getCell("status").setValue(pending);
							row.getCell("status").getStyleAttributes().setFontColor(orange);
						}
						
					} else if (!evaluationMap.containsKey(srcId) && complete == 100) {
						row.getCell("status").setValue(pending);
						row.getCell("status").getStyleAttributes().setFontColor(orange);
						// row.getCell("hiddenStatus").setValue(new Integer(2));
						if (row.getCell("end") != null && row.getCell("actualEndDate") != null) {
							int finalstatus = DateUtils.compareUpToDay((Date) row.getCell("actualEndDate").getValue(), (Date) row.getCell("end").getValue());
							if (finalstatus <= 0) {
								row.getCell("hiddenStatus").setValue(new Integer(0));
							}
						}
					} else if (complete < 100) {
						
					}

				} else {// ����Ҫ��������
					if (state == 0) {
						row.getCell("status").setValue(achieve);
						row.getCell("status").getStyleAttributes().setFontColor(green);
						row.getCell("hiddenStatus").setValue(new Integer(0));
					}
					if (state == 1) {
						row.getCell("status").setValue(achieve);
						row.getCell("status").getStyleAttributes().setFontColor(red);
						row.getCell("hiddenStatus").setValue(new Integer(1));
						// update by libing ͬһ�죬����ʱ��������ȷ�Ļ������ܴ������⣬�����ֶ���һ��
						if (row.getCell("end") != null && row.getCell("actualEndDate") != null) {
							int finalstatus = DateUtils.compareUpToDay((Date) row.getCell("actualEndDate").getValue(), (Date) row.getCell("end").getValue());
							if (finalstatus <= 0) {
								row.getCell("status").getStyleAttributes().setFontColor(green);
								row.getCell("hiddenStatus").setValue(new Integer(0));
							}
						}
					}
				}
			}
		}
	}
	
	protected FDCDataBaseInfo getBaseDataInfo() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		
	}

}