package com.kingdee.eas.fdc.aimcost.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.util.ObjectUtil;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDSplitPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.CommitListener;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.PreChangeEvent;
import com.kingdee.bos.ctrl.swing.event.PreChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CellBinder;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

public class PlanIndexTable {
	private KDTable table=null;
	private String headId=null; 
	public CellBinder binder=null;
	public Map entryBinder=new HashMap();
	public int dynRowBase=10;
	private AimMeasureCostEditUI measureCostEditU=null;
	private PlanIndexInfo planIndexInfo=null;
	private MeasureCostInfo costInfo=null;
	
	public PlanIndexTable(PlanIndexInfo planIndexInfo,AimMeasureCostEditUI measureCostEditUI) throws EASBizException, BOSException{
		table=new KDTable(23,1,0);
		table.setName("�滮ָ���");
		initCtrlListener();
		binder=new CellBinder();
		this.measureCostEditU=measureCostEditUI;
		isHasSubTable=measureCostEditUI.isUseCustomIndex();
		isPlanIndexLogic=measureCostEditUI.isPlanIndexLogic();
		isBuildPartLogic=measureCostEditUI.isBuildPartLogic();
		costInfo=measureCostEditUI.getEditData();
//		initTable(planIndexInfo);
		initTableForXU(planIndexInfo);
		initConstructTable(costInfo);
	}
	protected void initCtrlListener(){
		table.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
			public void editStopped(
					com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
				table_editStopped(e);
			}
		});
		table.addKDTMouseListener(new KDTMouseListener(){
			public void tableClicked(KDTMouseEvent e) {
				table_tableClicked(e);
			}
		});
		table.setBeforeAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e) {

				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					int colIndex=table.getSelectManager().getActiveColumnIndex();
					if(colIndex==1){
						e.setCancel(true);
					}
				}

			
			}
		});
		
		table.setAfterAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e) {

				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					KDTEditEvent event=new KDTEditEvent(table);
					int activeColumnIndex = table.getSelectManager().getActiveColumnIndex();
					int activeRowIndex =table.getSelectManager().getActiveRowIndex();
					event.setColIndex(activeColumnIndex);
					event.setRowIndex(activeRowIndex);
					event.setOldValue(FDCHelper.ONE);
					event.setValue(null);
					table_editStopped(event);
				}

			
			}
		});
	}
	public KDTable getTable(){
		return table;
	}
	
	public void initTableForXU(PlanIndexInfo info){
			((KDTTransferAction) table.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
			table.getHeadRow(0).getCell(0).setValue("�滮ָ���");
			table.getHeadRow(0).getCell(0).setUserObject("planIndex");
			table.getColumn(3).setWidth(120);
			table.getColumn(4).setWidth(120);
			table.getColumn(6).setWidth(120);
			table.getColumn(7).setWidth(120);
			table.getColumn(10).setWidth(200);
			table.getColumn(11).setWidth(150);
			table.getColumn(12).setWidth(170);
			table.getColumn(14).setWidth(150);
			initFixTableForXU(info);
			initDynTable(info);
			ICellEditor f7Editor = new KDTDefaultCellEditor(getF7productType());
			table.getColumn(1).setEditor(f7Editor);
			table.getCell(table.getRowCount()-1, 0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
			table.getColumn(1).getStyleAttributes().setNumberFormat("@");
			
			KDTextField textField=new  KDTextField();
			textField.setMaxLength(255);
			table.getColumn(21).setEditor(PlanIndexTable.getCheckBoxEdit());
			table.getColumn(22).setEditor(new KDTDefaultCellEditor(textField));
//				reSetExpressions();
			KDTEditEvent e=new KDTEditEvent(table, null,null, dynRowBase+1, 1,false,10000);
			calc(e);
			if(isHasSubTable()){
				initSubTable(info);
			}
			
	}
	public void initTable(PlanIndexInfo info){
/*		table.setHeadDisplayMode(KDTStyleConstants.HEAD_DISPLAY_EXCEL);
		IRow addHeadRow = table.addHeadRow();
		for(int i=0;i<table.getColumnCount()-1;i++){
			addHeadRow.getCell(i).setValue((i+1)+"");
		}*/
//		FDCTableHelper.setDebugHead(table);
		((KDTTransferAction) table.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
//		KDTMergeManager mm = table.getHeadMergeManager();
//		mm.mergeBlock(0, 0, 0, 11, KDTMergeManager.SPECIFY_MERGE);
		table.getHeadRow(0).getCell(0).setValue("�滮ָ���");
		table.getHeadRow(0).getCell(0).setUserObject("planIndex");
//		table.getHeadRow(0).getStyleAttributes().setBold(true);
//		table.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//		table.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
/*		for(int i=0;i<table.getColumnCount()-1;i++){
			table.getColumns().autoFitColumnWidth(i);
			if(i==0) continue;
//			table.getColumn(i).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//			table.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); 
		}*/
		
		table.getColumn(3).setWidth(150);
		table.getColumn(7).setWidth(150);
		table.getColumn(8).setWidth(120);
		table.getColumn(10).setWidth(50);
		table.getColumn(11).setWidth(50);
		
		table.getColumn(12).setWidth(50);
		table.getColumn(13).setWidth(50);
		table.getColumn(14).getStyleAttributes().setLocked(false);
		initFixTable(info);
		initDynTable(info);
		ICellEditor f7Editor = new KDTDefaultCellEditor(getF7productType());
		table.getColumn(1).setEditor(f7Editor);

		//���÷��
		Color lockColor=new Color(0xF0EDD9);
		StyleAttributes sa = table.getRow(3).getStyleAttributes();
		sa.setHorizontalAlign(HorizontalAlignment.CENTER);
		sa.setBackground(lockColor);
		sa = table.getRow(5).getStyleAttributes();
		sa.setHorizontalAlign(HorizontalAlignment.CENTER);
		sa.setBackground(lockColor);
		sa=table.getRow(7).getStyleAttributes();
		sa.setLocked(true);
		sa.setBackground(new Color(0xF0EDf1));
		sa=table.getRow(8).getStyleAttributes();
		sa.setHorizontalAlign(HorizontalAlignment.CENTER);
		sa.setBackground(lockColor);
		sa.setLocked(true);
		
		table.getCell(table.getRowCount()-1, 0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);

		table.getColumn(1).getStyleAttributes().setNumberFormat("@");
		KDTextField textField=new  KDTextField();
		textField.setMaxLength(300);
		//����������һ�����Բ���14��15 by hpw
		table.getColumn(15).setEditor(new KDTDefaultCellEditor(textField));
		for(int i=0;i<7;i++){
			for(int j=9;j<table.getColumnCount();j++)	{
				if(i<3&&j==9){
					continue;
				}
				if(table.getCell(i, j)==null) continue;
				if(isPlanIndexLogic()){
					if(j==8&&(i==6||i==7)){
						continue;
					}
				}
				table.getCell(i, j).getStyleAttributes().setLocked(true);
				table.getCell(i, j).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			}
		}
		table.getCell(2, 6).getStyleAttributes().setLocked(true);
		table.getCell(2, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getCell(2, 7).getStyleAttributes().setLocked(true);
		table.getCell(2, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getCell(3, 8).getStyleAttributes().setLocked(true);
		table.getCell(3, 8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getCell(4, 8).getStyleAttributes().setLocked(true);
		table.getCell(4, 8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		if(isPlanIndexLogic()){
			table.getCell(3, 6).getStyleAttributes().setLocked(true);
			table.getCell(3, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
			table.getCell(3, 7).getStyleAttributes().setLocked(true);
			table.getCell(3, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
			table.getCell(4, 6).getStyleAttributes().setLocked(true);
			table.getCell(4, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
			table.getCell(4, 7).getStyleAttributes().setLocked(true);
			table.getCell(4, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

		}else{
			table.getCell(5, 7).getStyleAttributes().setLocked(true);
			table.getCell(6, 7).getStyleAttributes().setLocked(true);
			table.getCell(5, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			table.getCell(6, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			table.getCell(5, 8).getStyleAttributes().setLocked(true);
			table.getCell(5, 8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			table.getCell(6, 8).getStyleAttributes().setLocked(true);
			table.getCell(6, 8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
		}
		
		//�ܽ���������Ա༭ by sxhong 2008/1/2
//		table.getCell(0, 6).getStyleAttributes().setLocked(true);
//		table.getCell(0, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(1, 4).getStyleAttributes().setLocked(true);
//		table.getCell(1, 4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		//�����ܶ�
		table.getCell(2, 5).getStyleAttributes().setLocked(true);
		table.getCell(2, 5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		//��·�õغϼ�
		table.getCell(4, 0).getStyleAttributes().setLocked(true);
		table.getCell(4, 0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		//�̵��õغϼ�
		table.getCell(6, 0).getStyleAttributes().setLocked(true);
		table.getCell(6, 0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(1, 6).getStyleAttributes().setLocked(true);
//		table.getCell(1, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(1, 4).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(1, 7).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(2, 7).getStyleAttributes().setNumberFormat("0.00%");
		reSetExpressions();
/*		KDFormattedTextField txtEditor=(KDFormattedTextField)table.getCell(0,6).getEditor().getComponent();
		txtEditor.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);*/
		KDTEditEvent e=new KDTEditEvent(table, null,null, dynRowBase+1, 1,false,10000);
		calc(e);
		if(isHasSubTable()){
			initSubTable(info);
		}
	}
	
	private void initFixTable(PlanIndexInfo info){
		table.addRows(9);

		//�󶨵�Ԫ��
//		binder.bindCell(table, 0, 0,"��ռ�����"	,"totalContainArea",true);
		binder.bindCell(table, 0, 1,"��ռ�����(m2)"	,"totalContainArea",true);//jf��ռ�����
		binder.bindCell(table, 0, 4,"�����õ����(m2)"	,"buildArea",true);//jf�����õ����
		binder.bindCell(table, 0, 6,"�ܽ������(m2)"	,"totalBuildArea",true);
		binder.bindCell(table, 0, 8,"�滮�ܽ������(m2)"	,"totalConstructArea",true);//jf
		
		binder.bindCell(table, 1, 1,"������ռ�����(m2)"	,"buildContainArea",true);
		binder.bindCell(table, 1, 4,"���������÷����(m2)"	,"publicSetHouse",true);
		binder.bindCell(table, 1, 6,"�̵���"	,"greenAreaRate",true);
		binder.bindCell(table, 1, 8,"���������(m2)"	,"basementArea",true);
		
		binder.bindCell(table, 2, 1,"���ݻ������(m2)"	,"cubageRateArea",true);
		binder.bindCell(table, 2, 4,"�ݻ���"	,"cubageRate",true);
		binder.bindCell(table, 2, 6,"�����ܶ�"	,"buildDensity",true);
		binder.bindCell(table, 2, 8,"��װ�����(m2)"	,"decorationArea",true);
		
		binder.bindCell(table, 3, 0,"��·�õغϼ�"	,"totalRoadArea",true,true);
		binder.bindCell(table, 3, 2,"����·�泵�е� "	,"pitchRoad",true,true);
		binder.bindCell(table, 3, 3,"��·�泵�е���ͣ������"	,"concreteRoad",true,true);
		binder.bindCell(table, 3, 5,"Ӳ����װ���е� "	,"hardRoad",true,true);
		if(isPlanIndexLogic()){
			binder.bindCell(table, 5, 7,"Ӳ����װ�㳡 "	,"hardSquare",true,true);
			binder.bindCell(table, 5, 8,"Ӳ����װ���е�  ","hardManRoad",true,true);
		}else{
			binder.bindCell(table, 3, 6,"Ӳ����װ�㳡 "	,"hardSquare",true,true);
			binder.bindCell(table, 3, 7,"Ӳ����װ���е�  ","hardManRoad",true,true);
		}
		binder.bindCell(table, 5, 0,"�̻��õغϼ� "	,"totalGreenArea",true,true);
		binder.bindCell(table, 5, 2,"��Ҫ�����̵� "	,"importPubGreenArea",true,true);
		binder.bindCell(table, 5, 3,"����լ���̻�  "	,"houseGreenArea",true,true);
		binder.bindCell(table, 5, 5,"�ײ�˽�һ�԰ "	,"privateGarden",true,true);
		binder.bindCell(table, 5, 6,"ˮ����� "	,"warterViewArea",true,true);
		KDTMergeManager mm = table.getMergeManager();
		for(int i=0;i<8;i++){
			mm.mergeBlock(i, 0, i, 1, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(i, 3, i, 4, KDTMergeManager.SPECIFY_MERGE);
		}
		mm.mergeBlock(7, 0, 7, 13);
		
		//��һ��,���ò�Ʒͷ
		table.getCell(8, 0).setValue("��Ʒ����");
		table.getCell(8, 1).setValue("��Ʒ����");
		table.getCell(8, 2).setValue("ռ�����");
		table.getCell(8, 3).setValue("�ݻ���");
		table.getCell(8, 4).setValue("�滮�������");
		table.getCell(8, 5).setValue("�������");
		table.getCell(8, 6).setValue("�������");
		table.getCell(8, 7).setValue("��Ʒ����");
		table.getCell(8, 8).setValue("ƽ��ÿ�����");
		table.getCell(8, 9).setValue("����");
		table.getCell(8, 10).setValue("����");
		table.getCell(8, 11).setValue("���");
		table.getCell(8, 12).setValue("��Ԫ��");
		table.getCell(8, 13).setValue("����");
		table.getCell(8, 14).setValue("�Ƿ��̯");
		table.getCell(8, 15).setValue("��ע");
		//�����ˣ����� by hpw jf
//		table.getColumn(4).getStyleAttributes().setHided(true);
//		table.getRow(3).getStyleAttributes().setHided(true);
//		table.getRow(4).getStyleAttributes().setHided(true);
//		table.getRow(5).getStyleAttributes().setHided(true);
//		table.getRow(6).getStyleAttributes().setHided(true);
		
		binder.bindCell(table, 3, 0,"��·�õغϼ�"	,"totalRoadArea",true,true);
		if(info!=null)
			binder.setCellsValue(info);
	}
	
	/**
	 * ���Ŀ��ɱ��������Ŀָ���
	 * @param info
	 */
	private void initFixTableForXU(PlanIndexInfo info){
		table.addRows(10);
		//�󶨵�Ԫ��
		binder.bindCell(table, 0, 1,"��ռ�����(m2)"	,"totalContainArea",true);
		binder.bindCell(table, 1, 1,"�ܽ������(m2)"	,"totalBuildArea",true);
		binder.bindCell(table, 2, 1,"�ܿ������(m2)"	,"totalSellArea",true);
		
		table.getCell(0,2).getStyleAttributes().setLocked(true);
		table.getCell(0,2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		
		table.getCell(1,2).getStyleAttributes().setLocked(true);
		table.getCell(1,2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		
		table.getCell(2,2).getStyleAttributes().setLocked(true);
		table.getCell(2,2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		
		setLockAndBk(0,3);
		setLockAndBk(1,3);
		setLockAndBk(2,3);
		table.getRow(3).getStyleAttributes().setHided(true);
		table.getRow(4).getStyleAttributes().setHided(true);
		table.getRow(5).getStyleAttributes().setHided(true);
		table.getRow(6).getStyleAttributes().setHided(true);
		table.getRow(7).getStyleAttributes().setHided(true);
				
		//�ϲ�������
		KDTMergeManager mm = table.getMergeManager();
		//�ϲ�ǰ8�еģ�0��1���У���3��4����
		for(int i=0;i<8;i++){
			mm.mergeBlock(i, 0, i, 1, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(i, 3, i, 4, KDTMergeManager.SPECIFY_MERGE);
		}
		//�����У��ڰ��п���
		mm.mergeBlock(4, 0, 4, 16);
		mm.mergeBlock(8, 0, 8, 16);
		setLockAndBk(0, 16);
		setLockAndBk(1, 14);
		setLockAndBk(2, 10);
		setLockAndBk(3, 8);
		setLockAndBk(5, 10);
		setLockAndBk(6, 12);
		setLockAndBk(7, 10);
		setLockAndBk(8, 0);
		table.getRow(4).getStyleAttributes().setLocked(true);
		table.getRow(4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getRow(8).getStyleAttributes().setLocked(true);
		table.getRow(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		
		//��һ��,���ò�Ʒͷ
		table.getCell(9, 0).setValue("��Ʒ����");
		table.getCell(9, 1).setValue("��Ʒ����");
		table.getCell(9, 2).setValue("ռ�����(m2)");
		table.getCell(9, 3).setValue("���Ͻ������(m2)");
		table.getCell(9, 4).setValue("���½������(m2)");
		table.getCell(9, 5).setValue("�������(m2)");
		
		table.getCell(9, 6).setValue("���ͽ������(m2)");
		table.getCell(9, 7).setValue("ʵ�ʽ������(m2)");
		
		table.getCell(9, 8).setValue("�������(m2)");
		
		table.getCell(9, 9).setValue("�������(m2)");
		table.getCell(9, 10).setValue("���Ͽ������������Ȩ�����(m2)");
		table.getCell(9, 11).setValue("��������װ���(m2)");
		table.getCell(9, 12).setValue("��װ�����Ŀ���������(m2)");
		
		table.getCell(9, 13).setValue("��Ʒ����");
		table.getCell(9, 14).setValue("ƽ��ÿ�����(m2)");
		table.getCell(9, 15).setValue("����");
		table.getCell(9, 16).setValue("����");
		table.getCell(9, 17).setValue("���");
		table.getCell(9, 18).setValue("����");
		table.getCell(9, 19).setValue("��Ԫ��");
		table.getCell(9, 20).setValue("����");
		table.getCell(9, 21).setValue("�Ƿ��̯");
		table.getCell(9, 22).setValue("��ע");
		
		entryBinder.put("containArea", 2);
		entryBinder.put("groundArea", 3);
		entryBinder.put("underGroundArea", 4);
		entryBinder.put("buildArea", 5);
		entryBinder.put("givingArea", 6);
		entryBinder.put("constructArea", 7);
		entryBinder.put("sellArea", 8);
		entryBinder.put("rentArea", 9);
		
		for(int i=10;i<21;i++){
			table.getColumn(i).getStyleAttributes().setHided(true);
		}
		
		table.getRow(9).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		table.getRow(9).getStyleAttributes().setLocked(true);
		table.getRow(9).getStyleAttributes().setBackground(new Color(0xF0EDD9));
		//��ʽ��
		table.getCell(1, 5).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(1, 7).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(6, 7).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(7, 2).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(7, 5).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(7, 7).getStyleAttributes().setNumberFormat("0.00%");
		
		table.getCell(6, 5).setEditor(CellBinder.getCellIntegerNumberEdit());
		table.getCell(6, 5).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(6, 9).setEditor(CellBinder.getCellIntegerNumberEdit());
		table.getCell(6, 9).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(6, 11).setEditor(CellBinder.getCellIntegerNumberEdit());
		table.getCell(6, 11).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		//�����޸ĵ�ֵ
//		table.getCell(0, 7).getStyleAttributes().setLocked(true);
//		table.getCell(0, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(0, 2).getStyleAttributes().setLocked(true);
//		table.getCell(0, 2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(1,2).getStyleAttributes().setLocked(true);
//		table.getCell(1,2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(1, 5).getStyleAttributes().setLocked(true);
//		table.getCell(1, 5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(1, 7).getStyleAttributes().setLocked(true);
//		table.getCell(1, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		
		if(info!=null)
			binder.setCellsValue(info);
	}
	private void  setLockAndBk(int row, int col ){
		int i = row;
		for(int j = col; j < 17 ;j++){
			table.getCell(i, j).getStyleAttributes().setLocked(true);
			table.getCell(i, j).getStyleAttributes().setBackground(new Color(15789529));
		}
		table.getMergeManager().mergeBlock(i, col, i, 22);
	}
	private void initDynTable(PlanIndexInfo info){
		int base=dynRowBase;
		int start=base;
		int rows=base;
		PlanIndexTypeEnum lastType=null;
		KDTMergeManager mm = table.getMergeManager();
		if(info==null){
			info=new PlanIndexInfo();
			PlanIndexEntryInfo entry=new PlanIndexEntryInfo();
			entry.setType(PlanIndexTypeEnum.house);
			info.getEntrys().add(entry);
			
			entry=new PlanIndexEntryInfo();
			entry.setType(PlanIndexTypeEnum.business);
			info.getEntrys().add(entry);
			
			entry=new PlanIndexEntryInfo();
			entry.setType(PlanIndexTypeEnum.publicBuild);
			info.getEntrys().add(entry);
			
			entry=new PlanIndexEntryInfo();
			entry.setType(PlanIndexTypeEnum.parking);
			info.getEntrys().add(entry);
		}else{
			//������������ҵ by hpw 2009-08-28
			PlanIndexEntryCollection entrys= new PlanIndexEntryCollection();
			boolean isOldData = true;
			for(int i=0;i<info.getEntrys().size();i++){
				if(PlanIndexTypeEnum.business==info.getEntrys().get(i).getType()){
					isOldData=false;
				}
				entrys.add(info.getEntrys().get(i));
			}
			if(isOldData){
				info.getEntrys().clear();
				PlanIndexEntryCollection newEntrys = new PlanIndexEntryCollection();
				for(int i=0;i<entrys.size();i++){
					if(PlanIndexTypeEnum.publicBuild==entrys.get(i).getType()){//�������׽���֮ǰ����
						PlanIndexEntryInfo entry=new PlanIndexEntryInfo();
						entry.setType(PlanIndexTypeEnum.business);
						newEntrys.add(entry);
					}
					newEntrys.add(entrys.get(i));
				}
				info.getEntrys().addCollection(newEntrys);
			}
		}
		for(int i=0;i<info.getEntrys().size();i++){
			PlanIndexEntryInfo entry=info.getEntrys().get(i);
			//�༭���鿴ʱ û�б�Ҫ��ʾ���� 20120509
			if(entry.getType()==lastType&&lastType!=null &&entry.getProduct() == null){
				continue;
			}
			IRow row=table.addRow(rows);
			if(entry.getType()!=lastType&&lastType!=null){
				row.getCell(0).setValue(lastType);
				row.getCell(1).setValue("С��");
				loadRow(null, row);
				row=table.addRow(++rows);
				start=rows;
				if(entry.getType()==PlanIndexTypeEnum.parking){
					mm.mergeBlock(rows, 0, rows, 1);
					row.getCell(2).setValue("ռ�����(m2)");
					row.getCell(3).setValue("���Ͻ������(m2)");
					row.getCell(4).setValue("���½������(m2)");//jf
					row.getCell(5).setValue("�������(m2)");
					row.getCell(8).setValue("�������(m2)");
					row.getCell(13).setValue("��λ��(��)");
					row.getCell(14).setValue("���۳�λ��(��)");
					row.getCell(15).setValue("��λ�����۱�");
					StyleAttributes sa = row.getStyleAttributes();
					sa.setHorizontalAlign(HorizontalAlignment.CENTER);
					sa.setLocked(true);
					sa.setBackground(new Color(0xF0EDD9));
					row=table.addRow(++rows);
					start=rows;
				}
			}
			loadRow(entry, row);
			lastType=entry.getType();
			rows++;
		}
		
		IRow row=table.addRow(rows);
		row.getCell(0).setValue(lastType);
		row.getCell(1).setValue("С��");
		loadRow(null, row);
		
		row=table.addRow(++rows);
		row.getCell(0).setValue("�ϼ�");
		loadRow(null, row);
	
		mm.mergeBlock(base, 0, rows-1, 0, KDTMergeManager.FREE_ROW_MERGE);
		mm.mergeBlock(row.getRowIndex(), 0, row.getRowIndex(), 1);
	}
	
	private void loadRow(PlanIndexEntryInfo entry,IRow row){
		StyleAttributes sa = row.getStyleAttributes();
		if(isSubTotalRow(row)){
			sa.getNumberFormat();
			sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
			sa.setLocked(true);
			sa.setBackground(FDCTableHelper.yearTotalColor);
			row.getCell(0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			row.getCell(1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			return;
		}else if(isTotalRow(row)){
			sa.setNumberFormat("#,##0.00;-#,##0.00");
			sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
			sa.setLocked(true);
			sa.setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
			return;
		}
		if(entry==null){
			row.getCell(21).setValue(Boolean.FALSE);
		}else{
			row.getCell(0).setValue(entry.getType());
			row.getCell(0).getStyleAttributes().setLocked(true);
			//��Ʒ
			if(entry.isIsProduct()){
				row.getCell(1).setValue(entry.getProduct());
			}else{
				row.getCell(1).setValue(entry.getName());
			}
			
			row.getCell(2).setValue(entry.getContainArea());
			row.getCell(3).setValue(entry.getGroundArea());
			row.getCell(4).setValue(entry.getUnderGroundArea());
			row.getCell(5).setValue(entry.getBuildArea());
			
			row.getCell(6).setValue(entry.getGivingArea());
			row.getCell(7).setValue(entry.getConstructArea());
			
			row.getCell(8).setValue(entry.getSellArea());
			
//			row.getCell(9).setValue(entry.getRentArea());
//			row.getCell(10).setValue(entry.getGroundRentArea());
//			row.getCell(11).setValue(entry.getPublicArea());
//			row.getCell(12).setValue(entry.getDeliveryArea());
			
			row.getCell(13).setValue(entry.getProductRate());
			row.getCell(14).setValue(entry.getUnitArea());
			if(entry.getElevators()>0){
				row.getCell(15).setValue(new Integer(entry.getElevators()));
			}
			if((PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.house || (PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.business
					|| (PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.publicBuild){
				if(entry.getFloors()>0){
					row.getCell(16).setValue(new Integer(entry.getFloors()));
				}
				row.getCell(17).setValue(entry.getFloorHeight());
				//����
				row.getCell(18).setValue(new Integer(entry.getBuild()));
				row.getCell(19).setValue(entry.getUnits());
				row.getCell(20).setValue(entry.getDoors());
				row.getCell(22).setValue(entry.getDesc());
			}
			row.getCell(21).setValue(Boolean.valueOf(entry.isIsSplit()));
		}
		row.getCell(0).getStyleAttributes().setLocked(true);
		row.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		for(int i=2;i<21;i++){
			row.getCell(i).getStyleAttributes().setLocked(true);
			row.getCell(i).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		}
		for(int i=2;i<21;i++){
			sa=row.getCell(i).getStyleAttributes();
			sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
			
			if((PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.house || (PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.business
					|| (PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.publicBuild){
				if(i==15||i==16||i==18||i==19||i==20){
					row.getCell(i).setEditor(CellBinder.getCellIntegerNumberEdit());
				}else{
					row.getCell(i).setEditor(CellBinder.getCellNumberEdit());
					sa.setNumberFormat("#,##0.00;-#,##0.00");
				}
			}else{
				if(i==13||i==14||i==15){
					row.getCell(i).setEditor(CellBinder.getCellIntegerNumberEdit());
				}else{
					row.getCell(i).setEditor(CellBinder.getCellNumberEdit());
					sa.setNumberFormat("#,##0.00;-#,##0.00");
				}
			}
		}
		row.getCell(0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		row.getCell(1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
	}
	
	public static KDTDefaultCellEditor getCheckBoxEdit(){
		KDCheckBox kdc = new KDCheckBox();
        kdc.setVisible(true);
        kdc.setEnabled(true);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	public KDTDefaultCellEditor getCellEditor(){
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor();
		KDFormattedTextField formatText = new KDFormattedTextField();
		formatText.setPrecision(8);
		cellEditor = new KDTDefaultCellEditor(formatText);
		return cellEditor;
	}
	
	public void addConstrIndexRow(ActionEvent e){
		int index = constrTable.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			constrTable.addRow();
		}else{
			constrTable.addRow(index+1);
		}
	}
	public void deleteConstrIndexRow(ActionEvent e){
		int index = constrTable.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		constrTable.removeRow(index);
		
	}
	
	public void addRow(ActionEvent e){
		//��С����֮ǰ���룬Ȼ������ͳ����
		boolean hasAdd=false;
		int index = table.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		IRow selectRow = table.getRow(index);
		if(selectRow.getCell(0).getValue() instanceof PlanIndexTypeEnum){
			for(int i=index;i<table.getRowCount()-1;i++){
				IRow row=table.getRow(i);
				if(isSubTotalRow(row)){
					row=table.addRow(i);
//					table.setRow(i);
					table.getScriptManager().removeAll();
					row.getCell(0).setValue(selectRow.getCell(0).getValue());
					loadRow(null, row);
					hasAdd=true;
					break;
				}
			}
		}
		if(hasAdd){
			reSetExpressions();
		}
		
	}
	
	public void deleteRow(ActionEvent e){
		//ɾ���к�����ͳ����
		boolean hasDelete=false;
		KDTSelectManager selectManager = table.getSelectManager();
		if (selectManager == null || selectManager.size() == 0) {
			return;
		}
		
//		table.getScriptManager().setAutoAdjustFormula(false);
		boolean mustAdd=false;
		for (int i = 0; i < selectManager.size(); i++) {
			KDTSelectBlock selectBlock = selectManager.get(i);
			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
				IRow selectRow = table.getRow(j);
				if (selectRow == null) {
					continue;
				}
				if(selectRow.getCell(0).getValue() instanceof PlanIndexTypeEnum){
					if(selectRow.getCell(1).getValue()!=null&&selectRow.getCell(1).getValue().equals("С��")){
						continue;
					}
					
					if((table.getCell(j-1, 1).getValue()!=null&&table.getCell(j-1, 1).getValue().equals("С��"))
							||(table.getCell(j+1, 1).getValue()!=null&&table.getCell(j+1, 1).getValue().equals("С��"))){
						mustAdd=true;
					}else{
						if((table.getCell(j-1, 2).getValue()!=null&&table.getCell(j-1, 2).getValue().equals("ռ�����"))
								&&(table.getCell(j+1, 1).getValue()!=null&&table.getCell(j+1, 1).getValue().equals("С��"))){
							mustAdd=true;
						}
					}
					table.getScriptManager().removeAll();
					if(mustAdd){
						addRow(e);
					}
					IRow removeRow = table.removeRow(selectRow.getRowIndex());
					hasDelete=true;
					Object value = removeRow.getCell(1).getValue();
					if(value instanceof ProductTypeInfo){
						ProductTypeInfo product = (ProductTypeInfo)value;
						//update by david_yang R110407-289 2011.04.18
						if(product!=null){
//						if(getPlanIndexEntryInfo(product.getId().toString())!=null){
							measureCostEditU.deleteProductTypeTable(product);
							if(measureCostEditU.getplTables().getSelectedIndex()!=2){
								measureCostEditU.getplTables().setSelectedIndex(2);
							}
						}
					}
//					table.getBody().removeRow(selectRow.getRowIndex());
				}
				break;//ֻ֧�ֵ���
			}
		}
		if(hasDelete&&!mustAdd){
			reSetExpressions();
		}
		
		KDTEditEvent event=new KDTEditEvent(table);
		int activeColumnIndex = 4;
		int activeRowIndex =table.getSelectManager().getActiveRowIndex();
		event.setColIndex(activeColumnIndex);
		event.setRowIndex(activeRowIndex);
		event.setOldValue(FDCHelper.ONE);
		event.setValue(null);
		table_editStopped(event);
		

	}
	private void reSetExpressions(){
		if(true) return;
//		TODO ̫���ˣ���ʱ�����Ż� by sxhong
		table.getScriptManager().setDiv0DefaultValue("0");
		PlanIndexTypeEnum lastType=PlanIndexTypeEnum.house;
		//�õ�סլС�Ƶ��к�;
		int houseSubIndex=dynRowBase;
		int businessSubIndex=dynRowBase;
		int publicSubIndex=dynRowBase;
		int parkingSubIndex=getTable().getRowCount()-2;
		for(int i=dynRowBase;i<getTable().getRowCount()-1;i++){
			IRow row=getTable().getRow(i);
			if(row.getCell(0).getValue()==PlanIndexTypeEnum.house
					&&isSubTotalRow(row)){
				houseSubIndex=i;
			}
			if(row.getCell(0).getValue()==PlanIndexTypeEnum.business
					&&isSubTotalRow(row)){
				businessSubIndex=i;
			}
			if(row.getCell(0).getValue()==PlanIndexTypeEnum.publicBuild
					&&isSubTotalRow(row)){
				publicSubIndex=i;
				break;
			}
		}
		//סլ
		if(houseSubIndex>dynRowBase){
			IRow houseRow=getTable().getRow(houseSubIndex);
			for(int j=2;j<getTable().getColumnCount()-2;j++){
				if(j==6){
					//��Ʒ����
					houseRow.getCell(j).setValue(FDCHelper.ONE);
					continue;
				}
				if(j==7){//ƽ��ÿ�����
					char c1=(char)'A'+5;
					String areaExp="sum("+c1+(dynRowBase+1)+":"+c1+(houseSubIndex)+")";
					c1=(char)'A'+9;
					String huExp="sum("+c1+(dynRowBase+1)+":"+c1+(houseSubIndex)+")";
					String exp="="+areaExp+"/"+huExp;
					houseRow.getCell(j).setExpressions(exp);
					continue;
				}
				char c=(char)('A'+j);
				String exp=null;				
				exp="=sum("+c+(dynRowBase+1)+":"+c+(houseSubIndex)+")";
				houseRow.getCell(j).setExpressions(exp);
			}
			
			
			//ռ�����=�������/�ݻ���,��Ʒ����=�������/�ܽ������ 
			//����ʤ��Ҫ���޸ĳɲ�Ʒ����=�������/�������С�ƣ�סլ��
			for(int i=dynRowBase;i<houseSubIndex;i++){
				IRow row=getTable().getRow(i);
				//ռ�����=�������/�ݻ���
//				row.getCell(2).setExpressions("=ROUND(E"+(i+1)+"/D"+(i+1)+",2)");
				//�������� = ���Ͻ������ + ���½������
				row.getCell(5).setExpressions("=sum(C"+(i+1)+":D"+(i+1));
				//��Ʒ����=�������/�������С�ƣ�סլ��
				row.getCell(6).setExpressions("=ROUND(F"+(i+1)+"/sum(F"+(dynRowBase+1)+":F"+(houseSubIndex)+"),2)");
				//ƽ���ڻ����=�������/����
				row.getCell(7).setExpressions("=ROUND(F"+(i+1)+"/J"+(i+1)+",2)");
				row.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			}
		}else{
			IRow houseRow=getTable().getRow(houseSubIndex);
			for(int j=2;j<getTable().getColumnCount()-2;j++){
				houseRow.getCell(j).setValue(null);
				houseRow.getCell(j).setExpressions("");
			}
		}
		//��ҵ
		if(businessSubIndex>(houseSubIndex+1)){
			IRow businessRow=getTable().getRow(businessSubIndex);
			for(int j=2;j<6;j++){
				char c=(char)('A'+j);
				String exp=null;				
				exp="=sum("+c+(houseSubIndex+2)+":"+c+(businessSubIndex)+")";
				businessRow.getCell(j).setExpressions(exp);
			}
			
			for(int i=houseSubIndex+1;i<businessSubIndex;i++){
				IRow row=getTable().getRow(i);
				//ռ�����=�������/�ݻ���
//				row.getCell(2).setExpressions("=ROUND(E"+(i+1)+"/D"+(i+1)+",2)");
				//�������� = ���Ͻ������ + ���½������
				row.getCell(5).setExpressions("=sum(C"+(i+1)+":D"+(i+1));
				//��Ʒ����=�������/�������С�ƣ�סլ��
				row.getCell(6).setExpressions("=ROUND(F"+(i+1)+"/sum(F"+(dynRowBase+1)+":F"+(houseSubIndex)+"),2)");
				//ƽ���ڻ����=�������/����
				row.getCell(7).setExpressions("=ROUND(F"+(i+1)+"/J"+(i+1)+",2)");
				row.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			}
			
		}else{
			IRow businessRow=getTable().getRow(businessSubIndex);
			for(int j=2;j<6;j++){
				businessRow.getCell(j).setValue(null);
				businessRow.getCell(j).setExpressions("");
			}
		}
		//����
		if(publicSubIndex>(businessSubIndex+1)){
			IRow publicRow=getTable().getRow(publicSubIndex);
			for(int j=2;j<6;j++){
				char c=(char)('A'+j);
				String exp=null;				
				exp="=sum("+c+(businessSubIndex+2)+":"+c+(publicSubIndex)+")";
				publicRow.getCell(j).setExpressions(exp);
			}
			
			
			for(int i=businessSubIndex+1;i<publicSubIndex;i++){
				IRow row=getTable().getRow(i);
				//ռ�����=�������/�ݻ���
//				row.getCell(2).setExpressions("=ROUND(E"+(i+1)+"/D"+(i+1)+",2)");
				//�������� = ���Ͻ������ + ���½������
				row.getCell(5).setExpressions("=sum(C"+(i+1)+":D"+(i+1));

			}
			
		}else{
			IRow publicRow=getTable().getRow(publicSubIndex);
			for(int j=2;j<6;j++){
				publicRow.getCell(j).setValue(null);
				publicRow.getCell(j).setExpressions("");
			}
		}
		//ͣ��
		if(parkingSubIndex>(publicSubIndex+2)){
			IRow parkRow=getTable().getRow(parkingSubIndex);
			for(int j=2;j<7;j++){
				char c=(char)('A'+j);
				String exp=null;				
				exp="=sum("+c+(publicSubIndex+2)+":"+c+(parkingSubIndex)+")";
				parkRow.getCell(j).setExpressions(exp);
			}
			
			for(int i=parkingSubIndex+1;i<table.getRowCount()-2;i++){
				IRow row=getTable().getRow(i);
				//ռ�����=�������/�ݻ���
//				row.getCell(2).setExpressions("=ROUND(E"+(i+1)+"/D"+(i+1)+",2)");
				//�������� = ���Ͻ������ + ���½������
				row.getCell(5).setExpressions("=sum(C"+(i+1)+":D"+(i+1));

			}
		}else{
			IRow parkRow=getTable().getRow(parkingSubIndex);
			for(int j=2;j<7;j++){
				parkRow.getCell(j).setValue(null);
				parkRow.getCell(j).setExpressions("");
			}
		}
		
		//�ϼ�
		IRow row=table.getRow(table.getRowCount()-1);
		row.getCell(0).setValue("�ϼ�");
		
		//���ڰ�С����ͳ��
		String exp="=ROUND(sum(";
//		String expSellArea="=ROUND(sum(";
		boolean isPark=false;
		for(int i=dynRowBase;i<table.getRowCount()-1;i++){
			if(table.getCell(i, 1).getValue()!=null&&table.getCell(i, 1).getValue().equals("С��")){
				continue;
			}				
//			if(table.getCell(i, 2).getValue()!=null&&table.getCell(i, 2).getValue().equals("ռ�����")){
//				isPark=true;
//				continue;
//			}
			exp=exp+"A"+(i+1)+",";
//			expSellArea=expSellArea+"A"+(i+1)+",";
		}
		
		exp=exp.substring(0,exp.length()-1)+"),2)"	;
//		expSellArea=expSellArea.substring(0,expSellArea.length()-1)+"),2)"	;
		//���������������
		for(int j=2;j<=5;j++){
//			if(j==3) continue;//�ݻ����복λ��������
			char c=(char)('A'+j);
//			if(j==5){
//				row.getCell(j).setExpressions(expSellArea.replaceAll("A", c+""));
//			}else{
				row.getCell(j).setExpressions(exp.replaceAll("A", c+""));
//			}
		}

		//�ܽ������
//		table.getCell(0, 6).setExpressions("=E"+table.getRowCount());
		//�����ܶ�=������ռ�����/�����õ������		
		//�ݻ���=���ݻ������/�����õ����
		table.getCell(3, 7).setExpressions("=ROUND(C2/F1,4)");
//		table.getCell(1, 4).getStyleAttributes().setNumberFormat("0.00%");
//		table.getCell(1, 6).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(3, 5).setExpressions("=ROUND(C3/F1,2)");
		KDTMergeManager mm = table.getMergeManager();
		mm.mergeBlock(dynRowBase, 0, table.getRowCount()-2, 0, KDTMergeManager.FREE_ROW_MERGE);
		table.getScriptManager().runAll();
		table.getScriptManager().setAutoRun(true);
		
	}
	public void save(String headID) throws EASBizException, BOSException{
		Object obj = table.getUserObject();
		PlanIndexInfo planIndexInfo=null;
		if(obj instanceof PlanIndexInfo){
			planIndexInfo=(PlanIndexInfo)obj;
			planIndexInfo.getEntrys().clear();
		}else{
			planIndexInfo=new PlanIndexInfo();
		}
		planIndexInfo.setHeadID(headID);
		binder.setObjectValue(planIndexInfo);
		
		//���÷�¼��ֵ
		PlanIndexEntryInfo entry=new PlanIndexEntryInfo();
		entry.setType(PlanIndexTypeEnum.house);
		planIndexInfo.getEntrys().add(entry);
		
		entry=new PlanIndexEntryInfo();
		entry.setType(PlanIndexTypeEnum.business);
		planIndexInfo.getEntrys().add(entry);
		
		entry=new PlanIndexEntryInfo();
		entry.setType(PlanIndexTypeEnum.publicBuild);
		planIndexInfo.getEntrys().add(entry);
		
		entry=new PlanIndexEntryInfo();
		entry.setType(PlanIndexTypeEnum.parking);
		planIndexInfo.getEntrys().add(entry);
		//���浽���ݿ�
		IObjectPK pk = PlanIndexFactory.getRemoteInstance().save(planIndexInfo);
		planIndexInfo.setId(BOSUuid.read(pk.toString()));
		table.setUserObject(planIndexInfo);
		
		
	}
	
	/**
	 * ȡ�ù滮ָ�������Ĳ�Ʒ
	 * @return
	 */
	public Set getProductIdSet(){
		Set idSet=new HashSet();
		for(int i=dynRowBase;i<table.getRowCount()-1;i++){
			IRow row=table.getRow(i);
			Object value = row.getCell(1).getValue();
			if(value instanceof ProductTypeInfo){
				idSet.add(((ProductTypeInfo)value).getId().toString());
			}
		}
		return idSet;
	}
	
	public PlanIndexInfo getPlanIndexInfo(){
//		Object obj = table.getUserObject();
//		PlanIndexInfo planIndexInfo=null;
//		if(obj instanceof PlanIndexInfo){
//			planIndexInfo=(PlanIndexInfo)obj;
//			planIndexInfo.getEntrys().clear();
//		}else{
//			planIndexInfo=new PlanIndexInfo();
//		}
		if(planIndexInfo==null){
			planIndexInfo=new PlanIndexInfo();
		}
//		planIndexInfo=new PlanIndexInfo();
		planIndexInfo.getEntrys().clear();
		binder.setObjectValue(planIndexInfo);
		
		//���÷�¼��ֵ
		for(int i=dynRowBase;i<table.getRowCount()-1;i++){
			IRow row=table.getRow(i);
			if(row.getCell(0).getValue() instanceof PlanIndexTypeEnum){
				Object value = row.getCell(1).getValue();
				if(value==null||value.toString().trim().length()<1){
					continue;
				}
				if(value.equals("С��")){
					//����һ����
					PlanIndexEntryInfo entry=new PlanIndexEntryInfo();
					entry.setType((PlanIndexTypeEnum)row.getCell(0).getValue());
					entry.setIsProduct(false);
					entry.setIndex(i);
					entry.setIsSplit(false);
					planIndexInfo.getEntrys().add(entry);
				}else{
					PlanIndexEntryInfo entry=new PlanIndexEntryInfo();
					entry.setIndex(i);
					entry.setType((PlanIndexTypeEnum)row.getCell(0).getValue());
					//��Ʒ
					if(value instanceof ProductTypeInfo){
						entry.setIsProduct(true);
						entry.setProduct((ProductTypeInfo)value);
					}else{
						//���˿հ�
						if(FDCHelper.isEmpty(value)){
							boolean isEmpty=true;
							for(int h=2;h<table.getColumnCount()-2;h++){
								ICell cell = row.getCell(h);
								if(cell!=null&&FDCHelper.toBigDecimal(cell.getValue()).compareTo(FDCHelper.ZERO)==0){
									isEmpty=true;
								}else{
									isEmpty=false;
									break;
								}
							}
							if(isEmpty){
								continue;
							}
						}
						
						entry.setName(value.toString());
						entry.setIsProduct(false);
					}
					
					if(FDCHelper.toBigDecimal(row.getCell(2).getValue()) instanceof BigDecimal){
						entry.setContainArea(FDCHelper.toBigDecimal(row.getCell(2).getValue()));
					}
					if(row.getCell(3).getValue() instanceof BigDecimal){
//						entry.setCubageRate((BigDecimal)row.getCell(3).getValue());
						entry.setGroundArea((BigDecimal)row.getCell(3).getValue());
					}
					if(row.getCell(4).getValue() instanceof BigDecimal){//jf
						entry.setUnderGroundArea((BigDecimal)row.getCell(4).getValue());
//						entry.setConstructArea((BigDecimal)row.getCell(4).getValue());
					}
					if(row.getCell(5).getValue() instanceof BigDecimal){
						entry.setBuildArea((BigDecimal)row.getCell(5).getValue());
					}
					if(row.getCell(6).getValue() instanceof BigDecimal){
						entry.setGivingArea((BigDecimal)row.getCell(6).getValue());
					}
					if(row.getCell(7).getValue() instanceof BigDecimal){
						entry.setConstructArea((BigDecimal)row.getCell(7).getValue());
					}
					
					if(row.getCell(8).getValue() instanceof BigDecimal){
						entry.setSellArea((BigDecimal)row.getCell(8).getValue());
					}
					
//					if(row.getCell(9).getValue() instanceof BigDecimal){
//						entry.setRentArea((BigDecimal)row.getCell(9).getValue());
//					}
//					if(row.getCell(10).getValue() instanceof BigDecimal){
//						entry.setGroundRentArea((BigDecimal)row.getCell(10).getValue());
//					}
//					if(row.getCell(11).getValue() instanceof BigDecimal){
//						entry.setPublicArea((BigDecimal)row.getCell(11).getValue());
//					}
//					if(row.getCell(12).getValue() instanceof BigDecimal){
//						entry.setDeliveryArea((BigDecimal)row.getCell(12).getValue());
//					}
					
					if(FDCHelper.toBigDecimal(row.getCell(13).getValue()) instanceof BigDecimal){
						entry.setProductRate(FDCHelper.toBigDecimal(row.getCell(13).getValue()));//��λ��������Ʒ����
					}
					if(FDCHelper.toBigDecimal(row.getCell(14).getValue()) instanceof BigDecimal){
						entry.setUnitArea(FDCHelper.toBigDecimal(row.getCell(14).getValue()));
//						entry.setElevators(((Integer)row.getCell(8).getValue()).intValue());//���ݡ����۳�λ��(��)
					}
					if(row.getCell(15).getValue() instanceof Integer){
//						entry.setUnitArea(FDCHelper.toBigDecimal(row.getCell(9).getValue()));//ƽ��ÿ���������λ���۱�
						entry.setElevators(((Integer)row.getCell(15).getValue()).intValue());
					}
					if(row.getCell(16).getValue() instanceof Integer){
						entry.setFloors(((Integer)row.getCell(16).getValue()).intValue());
					}
					if(row.getCell(17).getValue() instanceof BigDecimal){
						entry.setFloorHeight((BigDecimal)row.getCell(17).getValue());
					}
				
					if(row.getCell(18).getValue() instanceof Integer){
						entry.setBuild(((Integer)row.getCell(18).getValue()).intValue());
					}
					if(row.getCell(19).getValue() instanceof Integer){
						entry.setUnits(new BigDecimal(row.getCell(19).getValue().toString()));
					}
					if(row.getCell(20).getValue() instanceof Integer){
						entry.setDoors(new BigDecimal(row.getCell(20).getValue().toString()));
					}
					if(row.getCell(21).getValue() instanceof Boolean){
						entry.setIsSplit(((Boolean)row.getCell(21).getValue()).booleanValue());
					}else{
						entry.setIsSplit(false);
					}
					entry.setDesc((String)row.getCell(22).getValue());
					entry.setParent(planIndexInfo);
					planIndexInfo.getEntrys().add(entry);
				}
			}
			
			
		}
		//�����Զ����¼
		storeCustom(planIndexInfo);

		//���浽���ݿ�
//		IObjectPK pk = PlanIndexFactory.getRemoteInstance().save(planIndexInfo);
//		planIndexInfo.setId(BOSUuid.read(pk.toString()));
//		table.setUserObject(planIndexInfo);
//		table.setUserObject(planIndexInfo);
		return planIndexInfo;
		
	}
	public PlanIndexEntryInfo getPlanIndexEntryInfo(String productId){
		if(planIndexInfo==null||productId==null){
			return null;
		}
		for(int i=0;i<planIndexInfo.getEntrys().size();i++){
			PlanIndexEntryInfo entry = planIndexInfo.getEntrys().get(i);
			if(entry.getProduct()!=null&&entry.getProduct().getId().toString().equals(productId)){
				return entry;
			}
		}
		return null;
	}
	
	/**
	 * ȡ�Զ����ָ���¼,��ʱ������,��Ŀ���Ʒ
	 * @param productId
	 * @return
	 */
	public CustomPlanIndexEntryCollection getCustomPlanIndexs(String productId){
		CustomPlanIndexEntryCollection entrys=new CustomPlanIndexEntryCollection();
		if(planIndexInfo==null||!isHasSubTable()){
			return entrys;
		}
		for(int i=0;i<planIndexInfo.getCustomEntrys().size();i++){
			CustomPlanIndexEntryInfo entry = planIndexInfo.getCustomEntrys().get(i);
			if(productId==null){
				if(entry.getProductType()==null){
					entrys.add(entry);
				}
			}else{
				if(entry.getProductType()!=null
//						&&entry.getProductType().getId().toString().equals(productId)
						){
					entrys.add(entry);
				}
			}
		}
		return entrys;
	}
	
	
	public CustomPlanIndexEntryInfo getCustomPlanIndexEntryInfo(String apportTypeId,String productId){
		if(planIndexInfo==null||!isHasSubTable()){
			return null;
		}
		for(int i=0;i<planIndexInfo.getCustomEntrys().size();i++){
			CustomPlanIndexEntryInfo entry = planIndexInfo.getCustomEntrys().get(i);
			if (productId == null
					&& entry.getProductType() == null
					&& entry.getApportType().getId().toString().equals(
							apportTypeId)) {
				return entry;
			}
			if(productId!=null&&entry.getProductType()!=null&&entry.getProductType().getId().toString().equals(productId)&&entry.getApportType().getId().toString().equals(apportTypeId)){
				return entry;
			}
		}
		return null;
	}
	
	protected void table_editStopped(KDTEditEvent e) {
		if(table==null){
			return;
		}
		Object objOld=e.getOldValue();
		Object objNew=e.getValue();
		if(objOld==null&&objNew==null){
			return;
		}
		if(objOld!=null&&objNew!=null&&objOld.equals(objNew)){
			return;
		}
/*		int buildAreaIdx=4;
		if(e.getColIndex()==buildAreaIdx&&e.getRowIndex()>=dynRowBase){
			BigDecimal amount=FDCHelper.ZERO;
			for(int i=dynRowBase;i<table.getRowCount()-2;i++){
				IRow row=table.getRow(i);
				if(!isSubTotalRow(row)){
					amount=amount.add(FDCHelper.toBigDecimal(row.getCell(buildAreaIdx).getValue()));
				}
			}
			table.getCell(0, 6).setValue(amount);
		}*/
		calc(e);
		measureCostEditU.setDataChange(true);
	}
	private KDBizPromptBox f7productType=null; 
	public KDBizPromptBox  getF7productType(){
		f7productType = new KDBizPromptBox()
		{
			protected Object stringToValue(String t) {
				 Object obj= super.stringToValue(t);
				 if(obj instanceof  IObjectValue){
					 return obj;
				 }else{
					 return t;
				 }
				 
			}
		};
		f7productType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		view.setFilter(filter);
		f7productType.setEntityViewInfo(view);
		f7productType.setEditable(false);
		f7productType.setDisplayFormat("$name$");
		f7productType.setEditFormat("$number$");
		f7productType.setCommitFormat("$number$");
		f7productType.addPreChangeListener(new PreChangeListener(){
			public void preChange(PreChangeEvent e) {
				//�����Ʒֵ�ظ��򲻽��и���
				Object objNew=e.getData();
				if(objNew instanceof ProductTypeInfo){
//					PlanIndexInfo info=getPlanIndexInfo();
					int count=0;
					int flag=-1;//�ж��Ƿ�ѡ���˱���
					for(int i=dynRowBase;i<table.getRowCount()-1;i++){
						IRow row2=table.getRow(i);
						Object value = row2.getCell(1).getValue();
						if(value instanceof ProductTypeInfo){
							if(((ProductTypeInfo)value).getId().equals(((ProductTypeInfo)objNew).getId())){
								count++;
								flag=i;
							}
						}
					}
					int rowIndex=KDTableUtil.getSelectedRow(table);
					if(count==1&&rowIndex!=flag) {
						MsgBox.showWarning(measureCostEditU, "�ò�Ʒ�����Ѿ�������ָ��");
						e.setResult(PreChangeEvent.S_FALSE);
					}
				}
			}
		});
		f7productType.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent e) {
				int rowIndex=KDTableUtil.getSelectedRow(table);
				Object objOld=table.getCell(rowIndex,1).getValue();//e.getOldValue();
				Object objNew=e.getNewValue();
				if(objOld==null&&objNew==null) return;
				if(objOld!=null&&objOld.equals(objNew)) return;
				if(objNew!=null&&objNew.equals(f7productType.getUserObject())){
					return;
				}
				PlanIndexInfo info=null;
				if(objNew instanceof ProductTypeInfo){
					table.getCell(rowIndex,1).setValue(objNew);
					info=getPlanIndexInfo();
					measureCostEditU.addProductTypeTable((ProductTypeInfo)objNew);
				}
				if(objOld instanceof ProductTypeInfo){
					int count=0;
					if(info!=null){
						for(int i=0;i<info.getEntrys().size();i++){
							PlanIndexEntryInfo entry=info.getEntrys().get(i);
							if(entry.getProduct()!=null&&entry.getProduct().getId().equals(((ProductTypeInfo)objOld).getId())){
								count++;
								if(count>1) {
									break;
								}
							}
						}
					}
					if(count==0) measureCostEditU.deleteProductTypeTable((ProductTypeInfo)objOld);
				}
				
			}
		});
		
		return f7productType;
	}

	private void table_tableClicked(KDTMouseEvent e){
		if(measureCostEditU==null||measureCostEditU.getOprtState().equals(OprtState.VIEW)){
			return;
		}
		if(e.getColIndex()==10&&e.getRowIndex()>=9){
			ICell cell = table.getCell(e.getRowIndex(), 0);
			if(cell.getValue()!=null&&cell.getValue() instanceof PlanIndexTypeEnum){
				if(cell.getValue()==PlanIndexTypeEnum.house){
					return;
				}
			}
			cell = table.getCell(e.getRowIndex(), e.getColIndex());
			if(cell.getValue() instanceof Boolean){
				cell.setValue(Boolean.valueOf(!((Boolean)cell.getValue()).booleanValue()));
			}
		}
	}

	public static BigDecimal getAllSellArea(PlanIndexInfo info){
		BigDecimal amount=FDCHelper.ZERO;
		for(int i=0;i<info.getEntrys().size();i++){
			//��ʱ������
//			������ͣ����
//			if(info.getEntrys().get(i).getType()==PlanIndexTypeEnum.parking){
//				continue;
//			}
			if(info.getEntrys().get(i).isIsProduct()&&info.getEntrys().get(i).isIsSplit()){
				amount=amount.add(FDCHelper.toBigDecimal(info.getEntrys().get(i).getSellArea()));
			}
		}
		
		return amount;
	}
	
	/**
	 * �������ܽ������ = �滮ָ����ϵ��ܽ��������
	 * ����ָ��ʱҲ��ϵͳԭ���߼�����һ�£����ܽ������������Ϊ��Ŀ�Ľ������ָ�ꣻ
	 * 
	 * �����ǣ��ܽ������ = �滮ָ����ϵ��Ƿ��̯Ϊ�ǵĲ�Ʒ�������֮�ͣ�
	 * ����ָ��ʱ���滮ָ����ϵ��Ƿ��̯Ϊ�ǵĲ�Ʒ�������֮�͵�����Ϊ������Ŀ�Ľ������
	 * @param PlanIndexInfo
	 * @return
	 */
	public static BigDecimal getAllBuildArea(PlanIndexInfo info){
		BigDecimal amount=FDCHelper.ZERO;
		if(isBuildPartLogic()){
			for(int i=0;i<info.getEntrys().size();i++){
				//�����һ�£�����ͣ��
				if(info.getEntrys().get(i).isIsProduct()&&info.getEntrys().get(i).isIsSplit()){
					amount=amount.add(FDCHelper.toBigDecimal(info.getEntrys().get(i).getBuildArea()));
				}
			}
		}else{//ֱ�Ӹ�ֵ����Ϊ��amount = info.getTotalBuildArea();
			amount = amount.add(FDCHelper.toBigDecimal(info.getTotalBuildArea()));
		}
		
		return amount;
	}
	public static BigDecimal getAllConstructArea(PlanIndexInfo info){
		BigDecimal amount=FDCHelper.ZERO;
		if(isBuildPartLogic()){
			for(int i=0;i<info.getEntrys().size();i++){
				//�����һ�£�����ͣ��
				if(info.getEntrys().get(i).isIsProduct()&&info.getEntrys().get(i).isIsSplit()){
					amount=amount.add(FDCHelper.toBigDecimal(info.getEntrys().get(i).getConstructArea()));
				}
			}
		}else{//ֱ�Ӹ�ֵ����Ϊ��amount = info.getTotalBuildArea();
			amount = amount.add(FDCHelper.toBigDecimal(info.getTotalConstructArea()));
		}
		
		return amount;
	}
	private boolean isSubTotalRow(IRow row){
		boolean isSubTotalRow=false;
		if(row.getCell(1).getValue()!=null&&row.getCell(1).getValue().equals("С��")){
			isSubTotalRow=true;
		}
		return isSubTotalRow;
	}
	
	private boolean isTotalRow(IRow row){
		boolean isTotalRow=false;
		Object value = row.getCell(0).getValue();
		if(value!=null&&value.equals("�ϼ�")){
			isTotalRow=true;
		}
		return isTotalRow;
	}
	//----------��1��-------------------
	/**
	 * ��ռ�����(m2)
	 */
	private ICell totalContainAreaCell=null;
	/**
	 * �����õ����(m2)
	 */
	private ICell buildAreaCell=null;	
	/**
	 * �ܽ������(m2)
	 */
	private ICell totalBuildAreaCell=null;	
	
	//----------��2��-------------------
	/**
	 * �ܿ��������m2��
	 */
	private ICell totalSellAreaCell = null;
	/**
	 * �ܵ��������m2��
	 */
	private ICell totalGroundAreaCell = null;
	/**
	 * �ܵ��������m2��
	 */
	private ICell totalUnderGroundAreaCell = null;
	//----------��3��-------------------
	/**
	 * ������ռ�����(m2)
	 */
	private ICell buildContailAreaCell=null;	
	
	/**
	 * ���������û����(m2)
	 */
	private ICell publicSetHouseCell=null;
	/**
	 * �̵���
	 */
	private ICell greenAreaRateCell=null;
	//----------��4��-------------------
	/**
	 * ���ݻ������(m2)
	 */
	private ICell cubageRateAreaCell=null;
	/**
	 * �ݻ���
	 */
	private ICell cubageRateCell=null;
	/**
	 * �����ܶ�
	 */
	private ICell buildDensityCell=null;
	//----------��5��-------------------
	/**
	 * ��·�õغϼ�
	 */
	private ICell totalRoadAreaCell=null;
	/**
	 * ����·�泵�е�
	 */
	private ICell pitchRoadCell=null;
	/**
	 * ��·�泵�е���ͣ������
	 */
	private ICell concreteRoadCell=null;

	/**
	 * Ӳ����װ���е�
	 */
	private ICell hardRoadCell=null;
	/**
	 * Ӳ����װ�㳡
	 */
	private ICell hardSquareCell=null;
	/**
	 * Ӳ����װ���е�
	 */
	private ICell hardManRoadCell=null;
	/**
	 * �̻��õغϼ�
	 */
	private ICell totalGreenAreaCell=null;
	/**
	 * ��Ҫ�����̵�
	 */
	private ICell importPubGreenAreaCell=null;
	/**
	 * ����լ���̻�
	 */
	private ICell houseGreenAreaCell=null;
	/**
	 * �ײ�˽�һ�԰
	 */
	private ICell privateGardenCell=null;
	/**
	 * ˮ�����
	 */
	private ICell warterViewAreaCell=null;
	//��Ʒ������
	private int productColummnIndex=1;
	
	/**
	 * ռ�����
	 */
	private int containAreaColummnIndex=2;
	
	/**
	 * ���Ͻ������
	 */
	private int groundAreaColummnIndex=3;
	
	/**
	 * ���½������
	 */
	private int underGroundAreaColummnIndex=4;
	/**
	 * �������
	 */
	private int buildAreaColummnIndex=5;
	
	/**
	 * ���ͽ������
	 */
	private int givingAreaColummnIndex=6;
	
	/**
	 * ʵ�ʽ������
	 */
	private int constructAreaColummnIndex=7;
	/**
	 * �������
	 */
	private int sellAreaColummnIndex=8;
	/**
	 * �������
	 */
	private int rentAreaColummnIndex=9;
	/**
	 * ���Ͽ��������
	 */
	private int groundRentAreaColummnIndex=10;
	/**
	 * ��������װ���
	 */
	private int publicAreaColummnIndex=11;
	/**
	 * ��װ�����Ŀ���������
	 */
	private int deliveryAreaColummnIndex=12;
	/**
	 * ��Ʒ����
	 */
	private int productRateColummnIndex=13;
	/**
	 * ƽ��ÿ�����
	 */
	private int avgHuColummnIndex=14;
	/**
	 * ����
	 */
	private int elevatorsIndex=15;
	/**
	 * ¥��
	 */
	private int floorsIndex=16;
	/**
	 * ���
	 */
	private int floorHeightIndex=17;
	
	/**
	 * ����
	 */
	private int buildColummnIndex=18;
	/**
	 * ��Ԫ��
	 */
	private int unitColummnIndex=19;
	/**
	 * ����
	 */
	private int huColummnIndex=20;
	/**
	 * �Ƿ��̯
	 */
	private int splitColummnIndex=21;
	/**
	 * ��ע
	 */
	private int descColummnIndex=22;
	/**
	 * ��λ��
	 */
	private int parksColummnIndex=13;
	/**
	 * ���۳�λ��(��)
	 */
	private int sellParksColummnIndex=14;
	/**
	 * סլС����
	 */
	private int houseSubIndex=0;
	/**
	 * ��ҵС����
	 */
	private int businessSubIndex=0;
	/**
	 * �������׽�����
	 */
	private int publicSubIndex=0;
	/**
	 * ͣ��С����
	 */
	private int parkingSubIndex=0;
	/**
	 * �ϼ���
	 */
	private int totalIndex=0;
	
	private void initVariable(){
		KDTable table=getTable();
		//ռ�����
		totalContainAreaCell=table.getCell(0, 2);
		//�������
		totalBuildAreaCell=table.getCell(1,2);	
		
		totalSellAreaCell = table.getCell(2, 2);
		
		totalGroundAreaCell = table.getCell(2, 7);
		totalUnderGroundAreaCell = table.getCell(2, 9);
		
		buildAreaCell=table.getCell(2, 2);	
//		buildContailAreaCell=table.getCell(2, 2);	
		greenAreaRateCell=table.getCell(2, 5);
		publicSetHouseCell=table.getCell(2, 7);
		
		cubageRateAreaCell=table.getCell(3, 2);
		cubageRateCell=table.getCell(3,5);
		buildDensityCell=table.getCell(3, 7);
		
		
//		totalRoadAreaCell=table.getCell(4, 0);
//		pitchRoadCell=table.getCell(4, 2);
//		concreteRoadCell=table.getCell(4,3);
//		hardRoadCell=table.getCell(4,5);
//		if(isPlanIndexLogic()){
//			hardSquareCell=table.getCell(6,7);
//			hardManRoadCell=table.getCell(6,8);
//		}else{
//			hardSquareCell=table.getCell(4,6);
//			hardManRoadCell=table.getCell(4,7);
//		}
//		totalGreenAreaCell=table.getCell(6,0);
//		importPubGreenAreaCell=table.getCell(6,2);
//		houseGreenAreaCell=table.getCell(6,3);
//		privateGardenCell=table.getCell(6,5);
//		warterViewAreaCell=table.getCell(6,6);
		
		//�õ�סլС�Ƶ��к�;
		houseSubIndex=dynRowBase;
		businessSubIndex=dynRowBase;
		publicSubIndex=dynRowBase;
		parkingSubIndex=getTable().getRowCount()-2;
		for(int i=dynRowBase;i<getTable().getRowCount()-1;i++){
			IRow row=getTable().getRow(i);
			if(row.getCell(0).getValue()==PlanIndexTypeEnum.house
					&&isSubTotalRow(row)){
				houseSubIndex=i;
			}
			if(row.getCell(0).getValue()==PlanIndexTypeEnum.business
					&&isSubTotalRow(row)){
				businessSubIndex=i;
			}
			if(row.getCell(0).getValue()==PlanIndexTypeEnum.publicBuild
					&&isSubTotalRow(row)){
				publicSubIndex=i;
				break;
			}
		}
		totalIndex=table.getRowCount()-1;
//		calcArea();
	}
	private void calc(KDTEditEvent e){
		initVariable();
		KDTable table=getTable();
		if(e!=null&&e.getRowIndex()<dynRowBase){
	/*		��̯��
			�����ܶ�=������ռ�����/�����õ������		
			�ݻ���=���ݻ������/�����õ����
			��·�õغϼ�=����·�泵�е�+��·�泵�е�(ͣ����)+Ӳ����װ���е�+Ӳ����װ�㳡+Ӳ����װ���е�
			�̻��ϼ�=��Ҫ�����̵�+����լ���̻�+�ײ�˽�һ�԰+ˮ�����
			�̵��ʣ��̻��õغϼ�/��ռ�����
			�ܽ����������Ʒ�������֮�͵��ǿ����޸�
			
			����ֵΪ�ǣ��򽫡�Ӳ����װ�㳡������Ӳ����װ���е�������ָ�����Ƶ��̻��õغϼ��У����޸ļ��㹫ʽΪ��
			��·�õغϼ�=����·�泵�е�+��·�泵�е���ͣ������+Ӳ����װ���е�
			�̻��õغϼ�=��Ҫ�����̵�+����լ���̻�+�ײ�˽�һ�԰+ˮ�����+Ӳ����װ�㳡+Ӳ����װ���е�

	*/
//			BigDecimal oldGreenAmt=FDCHelper.toBigDecimal(totalGreenAreaCell.getValue());
		// �޸�20111104
//			BigDecimal oldContainAmt=FDCHelper.toBigDecimal(totalContainAreaCell.getValue());
//			if(e!=null&&e.getRowIndex()==totalContainAreaCell.getRowIndex()&&e.getColIndex()==totalContainAreaCell.getColumnIndex()){
//				oldContainAmt=FDCHelper.toBigDecimal(e.getOldValue());
//			}
//			BigDecimal buildAreaAmt=FDCHelper.toBigDecimal(buildAreaCell.getValue());
//			if(buildAreaAmt.signum()==0){
//				buildDensityCell.setValue(null);
//				cubageRateCell.setValue(null);
//			}else{
//				buildDensityCell.setValue(FDCHelper.divide(buildContailAreaCell.getValue(), buildAreaAmt, 4, BigDecimal.ROUND_HALF_UP));
//				cubageRateCell.setValue(FDCHelper.divide(cubageRateAreaCell.getValue(), buildAreaAmt, 2, BigDecimal.ROUND_HALF_UP));
//			}
			
			//----
//			BigDecimal amt=FDCHelper.ZERO;
//			amt=FDCNumberHelper.add(amt,pitchRoadCell.getValue());
//			amt=FDCNumberHelper.add(amt,concreteRoadCell.getValue());
//			amt=FDCNumberHelper.add(amt, hardRoadCell.getValue());
//			if(!isPlanIndexLogic()){
//				amt=FDCNumberHelper.add(amt, hardSquareCell.getValue());
//				amt=FDCNumberHelper.add(amt, hardManRoadCell.getValue());
//			}
//			totalRoadAreaCell.setValue(amt);
//			amt=FDCHelper.ZERO;
//			amt=FDCNumberHelper.add(amt,importPubGreenAreaCell.getValue());
//			amt=FDCNumberHelper.add(amt,houseGreenAreaCell.getValue());
//			amt=FDCNumberHelper.add(amt, privateGardenCell.getValue());
//			amt=FDCNumberHelper.add(amt, warterViewAreaCell.getValue());
//			if(isPlanIndexLogic()){
//				amt=FDCNumberHelper.add(amt, hardSquareCell.getValue());
//				amt=FDCNumberHelper.add(amt, hardManRoadCell.getValue());
//			}
////			totalGreenAreaCell.setValue(amt);
//			if(amt.compareTo(oldGreenAmt)!=0||FDCHelper.toBigDecimal(totalContainAreaCell.getValue()).compareTo(oldContainAmt)!=0){
//				greenAreaRateCell.setValue(FDCNumberHelper.divide(amt, totalContainAreaCell.getValue(),4,BigDecimal.ROUND_HALF_UP));
//			}
		}else{
/*
  			ռ�����=�������/�ݻ���
			��Ʒ����=�������/�������С�ƣ�סլ��
			ƽ��ÿ�����=�������/����
			ƽ��ÿ�����С�ƣ��������С��/����С��
			�ݻ���С�ƣ��������С��/ռ�����С��

			�ϼ�:
			ռ������ϼ�,��������ϼ�,��������ϼ�

			���ڷ�̯���ܵĿ������=�����̯�Ĳ�Ʒ���֮��
*/
			//�ϼƽ������
			BigDecimal oldtotalBuildArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, buildAreaColummnIndex).getValue());
			//ռ�����
			BigDecimal oldtotalContainArea =FDCHelper.toBigDecimal(table.getCell(totalIndex, containAreaColummnIndex).getValue());
			BigDecimal oldSellBuildArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, sellAreaColummnIndex).getValue());
			BigDecimal oldtotalGroundArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, groundAreaColummnIndex).getValue());
			BigDecimal oldtotalUnderGroundArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, underGroundAreaColummnIndex).getValue());
			 
//			BigDecimal oldSellBuildArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, sellAreaColummnIndex).getValue());
			boolean isChange = false;
//			if(e.getOldValue() instanceof BigDecimal && e.getValue() instanceof BigDecimal){
//				BigDecimal old = (BigDecimal)e.getOldValue();
//				BigDecimal value = (BigDecimal)e.getValue();
//				if(old != null && value != null){
//					isChange = !(old.floatValue() == value.floatValue());
//				}
//			}
			//סլ
			Map sumMap=new HashMap();
			int currentRow = e.getRowIndex();
			for(int i=dynRowBase;i<houseSubIndex;i++){
				if( i == currentRow && isChange){
					int changedCol = e.getColIndex();
					BigDecimal buildArea = FDCNumberHelper.toBigDecimal(
							table.getCell(i, buildAreaColummnIndex).getValue());
					if(changedCol == 2){
						
					}else if(changedCol == 3){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
							table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}else if(changedCol == 4){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}
					if(changedCol == 3||changedCol == 4||changedCol == 6){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, buildAreaColummnIndex).getValue()
								, table.getCell(i, givingAreaColummnIndex).getValue());
						table.getCell(i, constructAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}
					BigDecimal tmp = FDCHelper.ZERO;
					tmp=FDCNumberHelper.divide(table.getCell(i, sellAreaColummnIndex).getValue(), table.getCell(i, huColummnIndex).getValue());
					table.getCell(i, avgHuColummnIndex).setValue(tmp);
				}
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i, containAreaColummnIndex).getValue()));
				sumMap.put("groundArea", FDCNumberHelper.add(sumMap.get("groundArea"), table.getCell(i, groundAreaColummnIndex).getValue()));
				sumMap.put("underGroundArea", FDCNumberHelper.add(sumMap.get("underGroundArea"), table.getCell(i, underGroundAreaColummnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i, buildAreaColummnIndex).getValue()));
				
				sumMap.put("givingArea", FDCNumberHelper.add(sumMap.get("givingArea"), table.getCell(i, givingAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap.get("constructArea"), table.getCell(i, constructAreaColummnIndex).getValue()));
				
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i, sellAreaColummnIndex).getValue()));
				
				sumMap.put("rentArea", FDCNumberHelper.add(sumMap.get("rentArea"), table.getCell(i, rentAreaColummnIndex).getValue()));
				sumMap.put("groundRentArea", FDCNumberHelper.add(sumMap.get("groundRentArea"), table.getCell(i, groundRentAreaColummnIndex).getValue()));
				sumMap.put("publicArea", FDCNumberHelper.add(sumMap.get("publicArea"), table.getCell(i, publicAreaColummnIndex).getValue()));
				sumMap.put("deliveryArea", FDCNumberHelper.add(sumMap.get("deliveryArea"), table.getCell(i, deliveryAreaColummnIndex).getValue()));
				
				sumMap.put("elevators", FDCNumberHelper.add(sumMap.get("elevators"), table.getCell(i, elevatorsIndex).getValue()));
				sumMap.put("floors", FDCNumberHelper.add(sumMap.get("floors"), table.getCell(i, floorsIndex).getValue()));
				sumMap.put("floorHeight", FDCNumberHelper.add(sumMap.get("floorHeight"), table.getCell(i, floorHeightIndex).getValue()));
				sumMap.put("unit", FDCNumberHelper.add(sumMap.get("unit"), table.getCell(i, unitColummnIndex).getValue()));
				sumMap.put("hu", FDCNumberHelper.add(sumMap.get("hu"), table.getCell(i, huColummnIndex).getValue()));
				sumMap.put("build", FDCNumberHelper.add(sumMap.get("build"), table.getCell(i, buildColummnIndex).getValue()));
				
			}
			//��Ʒ����
			BigDecimal max=FDCHelper.ZERO;
			int maxProductRate=dynRowBase;
			for(int i=dynRowBase;i<houseSubIndex;i++){
				BigDecimal tmp=FDCNumberHelper.divide(table.getCell(i, sellAreaColummnIndex).getValue(), sumMap.get("sellArea"));
				table.getCell(i, productRateColummnIndex).setValue(tmp);
				if(tmp!=null&&tmp.compareTo(max)>0){
					max=tmp;
					maxProductRate=i;
				}
				sumMap.put("productRate", FDCNumberHelper.add(sumMap.get("productRate"), tmp));
			}
			BigDecimal diff=FDCHelper.ONE.subtract(FDCNumberHelper.toBigDecimal(sumMap.get("productRate")));
			if(diff.signum()!=0){
				table.getCell(maxProductRate, productRateColummnIndex).setValue(FDCNumberHelper.add(table.getCell(maxProductRate, productRateColummnIndex).getValue(), diff));
			}
			//С��
			table.getCell(houseSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			//���Ͻ������
			table.getCell(houseSubIndex, groundAreaColummnIndex).setValue(sumMap.get("groundArea"));
			table.getCell(houseSubIndex, underGroundAreaColummnIndex).setValue(sumMap.get("underGroundArea"));
			table.getCell(houseSubIndex, buildColummnIndex).setValue(sumMap.get("build"));
			
			table.getCell(houseSubIndex, buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			
			table.getCell(houseSubIndex, givingAreaColummnIndex).setValue(sumMap.get("givingArea"));
			table.getCell(houseSubIndex, constructAreaColummnIndex).setValue(sumMap.get("constructArea"));
			
			table.getCell(houseSubIndex, sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			
			table.getCell(houseSubIndex, rentAreaColummnIndex).setValue(sumMap.get("rentArea"));
			table.getCell(houseSubIndex, groundRentAreaColummnIndex).setValue(sumMap.get("groundRentArea"));
			table.getCell(houseSubIndex, publicAreaColummnIndex).setValue(sumMap.get("publicArea"));
			table.getCell(houseSubIndex, deliveryAreaColummnIndex).setValue(sumMap.get("deliveryArea"));
			
			table.getCell(houseSubIndex, elevatorsIndex).setValue(sumMap.get("elevators"));
//			table.getCell(houseSubIndex, floorsIndex).setValue(sumMap.get("floors"));
//			table.getCell(houseSubIndex, floorHeightIndex).setValue(sumMap.get("floorHeight"));
			table.getCell(houseSubIndex, unitColummnIndex).setValue(sumMap.get("unit"));
			table.getCell(houseSubIndex, huColummnIndex).setValue(sumMap.get("hu"));
			table.getCell(houseSubIndex, productRateColummnIndex).setValue(FDCNumberHelper.ONE);
			//�ݻ���С�ƣ��������С��/ռ�����С��
//			table.getCell(houseSubIndex, cubageRateColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("buildArea"), sumMap.get("containArea")));
			//ƽ��ÿ�����С�ƣ��������С��/����С��
			table.getCell(houseSubIndex, avgHuColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("sellArea"), sumMap.get("hu")));
			
			//��ҵ
			sumMap.clear();
			for(int i=houseSubIndex+1;i<businessSubIndex;i++){
				if( i == currentRow && isChange){
					BigDecimal tmp = FDCHelper.ZERO;
					int changedCol = e.getColIndex();
					BigDecimal buildArea = FDCNumberHelper.toBigDecimal(
							table.getCell(i, buildAreaColummnIndex).getValue());
					if(changedCol == 2){
					}else if(changedCol == 3){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}else if(changedCol == 4){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}
					if(changedCol == 3||changedCol == 4||changedCol == 6){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, buildAreaColummnIndex).getValue()
								, table.getCell(i, givingAreaColummnIndex).getValue());
						table.getCell(i, constructAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}
					tmp=FDCNumberHelper.divide(table.getCell(i, sellAreaColummnIndex).getValue(), table.getCell(i, huColummnIndex).getValue());
					table.getCell(i, avgHuColummnIndex).setValue(tmp);
				}
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i, containAreaColummnIndex).getValue()));
				sumMap.put("groundArea", FDCNumberHelper.add(sumMap.get("groundArea"), table.getCell(i, groundAreaColummnIndex).getValue()));
				sumMap.put("underGroundArea", FDCNumberHelper.add(sumMap.get("underGroundArea"), table.getCell(i, underGroundAreaColummnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i, buildAreaColummnIndex).getValue()));
				
				sumMap.put("givingArea", FDCNumberHelper.add(sumMap.get("givingArea"), table.getCell(i, givingAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap.get("constructArea"), table.getCell(i, constructAreaColummnIndex).getValue()));
				
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i, sellAreaColummnIndex).getValue()));
				
				sumMap.put("rentArea", FDCNumberHelper.add(sumMap.get("rentArea"), table.getCell(i, rentAreaColummnIndex).getValue()));
				sumMap.put("groundRentArea", FDCNumberHelper.add(sumMap.get("groundRentArea"), table.getCell(i, groundRentAreaColummnIndex).getValue()));
				sumMap.put("publicArea", FDCNumberHelper.add(sumMap.get("publicArea"), table.getCell(i, publicAreaColummnIndex).getValue()));
				sumMap.put("deliveryArea", FDCNumberHelper.add(sumMap.get("deliveryArea"), table.getCell(i, deliveryAreaColummnIndex).getValue()));
				
				sumMap.put("elevators", FDCNumberHelper.add(sumMap.get("elevators"), table.getCell(i, elevatorsIndex).getValue()));
				sumMap.put("floors", FDCNumberHelper.add(sumMap.get("floors"), table.getCell(i, floorsIndex).getValue()));
				sumMap.put("floorHeight", FDCNumberHelper.add(sumMap.get("floorHeight"), table.getCell(i, floorHeightIndex).getValue()));
				sumMap.put("unit", FDCNumberHelper.add(sumMap.get("unit"), table.getCell(i, unitColummnIndex).getValue()));
				sumMap.put("hu", FDCNumberHelper.add(sumMap.get("hu"), table.getCell(i, huColummnIndex).getValue()));
				sumMap.put("build", FDCNumberHelper.add(sumMap.get("build"), table.getCell(i, buildColummnIndex).getValue()));
			}
			table.getCell(businessSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			//С��
			table.getCell(businessSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			//���Ͻ������
			table.getCell(businessSubIndex, groundAreaColummnIndex).setValue(sumMap.get("groundArea"));
			table.getCell(businessSubIndex, underGroundAreaColummnIndex).setValue(sumMap.get("underGroundArea"));
			table.getCell(businessSubIndex, buildColummnIndex).setValue(sumMap.get("build"));
			
			table.getCell(businessSubIndex, buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			
			table.getCell(businessSubIndex, givingAreaColummnIndex).setValue(sumMap.get("givingArea"));
			table.getCell(businessSubIndex, constructAreaColummnIndex).setValue(sumMap.get("constructArea"));
			
			table.getCell(businessSubIndex, sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			
			table.getCell(businessSubIndex, rentAreaColummnIndex).setValue(sumMap.get("rentArea"));
			table.getCell(businessSubIndex, groundRentAreaColummnIndex).setValue(sumMap.get("groundRentArea"));
			table.getCell(businessSubIndex, publicAreaColummnIndex).setValue(sumMap.get("publicArea"));
			table.getCell(businessSubIndex, deliveryAreaColummnIndex).setValue(sumMap.get("deliveryArea"));
			
			table.getCell(businessSubIndex, elevatorsIndex).setValue(sumMap.get("elevators"));
//			table.getCell(businessSubIndex, floorsIndex).setValue(sumMap.get("floors"));
//			table.getCell(businessSubIndex, floorHeightIndex).setValue(sumMap.get("floorHeight"));
			table.getCell(businessSubIndex, unitColummnIndex).setValue(sumMap.get("unit"));
			table.getCell(businessSubIndex, huColummnIndex).setValue(sumMap.get("hu"));
			table.getCell(businessSubIndex, productRateColummnIndex).setValue(FDCNumberHelper.ONE);
			//�ݻ���С�ƣ��������С��/ռ�����С��
//			table.getCell(houseSubIndex, cubageRateColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("buildArea"), sumMap.get("containArea")));
			//ƽ��ÿ�����С�ƣ��������С��/����С��
			table.getCell(businessSubIndex, avgHuColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("sellArea"), sumMap.get("hu")));
			
			//��������
			sumMap.clear();
			for(int i=businessSubIndex+1;i<publicSubIndex;i++){
				if( i == currentRow && isChange){
					int changedCol = e.getColIndex();
					BigDecimal buildArea = FDCNumberHelper.toBigDecimal(
							table.getCell(i, buildAreaColummnIndex).getValue());
					if(changedCol == 2){
					}else if(changedCol == 3){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}else if(changedCol == 4){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}
					if(changedCol == 3||changedCol == 4||changedCol == 6){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, buildAreaColummnIndex).getValue()
								, table.getCell(i, givingAreaColummnIndex).getValue());
						table.getCell(i, constructAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}
				}
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i, containAreaColummnIndex).getValue()));
				sumMap.put("groundArea", FDCNumberHelper.add(sumMap.get("groundArea"), table.getCell(i, groundAreaColummnIndex).getValue()));
				sumMap.put("underGroundArea", FDCNumberHelper.add(sumMap.get("underGroundArea"), table.getCell(i, underGroundAreaColummnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i, buildAreaColummnIndex).getValue()));
				
				sumMap.put("givingArea", FDCNumberHelper.add(sumMap.get("givingArea"), table.getCell(i, givingAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap.get("constructArea"), table.getCell(i, constructAreaColummnIndex).getValue()));
				
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i, sellAreaColummnIndex).getValue()));
				
				sumMap.put("rentArea", FDCNumberHelper.add(sumMap.get("rentArea"), table.getCell(i, rentAreaColummnIndex).getValue()));
				sumMap.put("groundRentArea", FDCNumberHelper.add(sumMap.get("groundRentArea"), table.getCell(i, groundRentAreaColummnIndex).getValue()));
				sumMap.put("publicArea", FDCNumberHelper.add(sumMap.get("publicArea"), table.getCell(i, publicAreaColummnIndex).getValue()));
				sumMap.put("deliveryArea", FDCNumberHelper.add(sumMap.get("deliveryArea"), table.getCell(i, deliveryAreaColummnIndex).getValue()));
				
				sumMap.put("elevators", FDCNumberHelper.add(sumMap.get("elevators"), table.getCell(i, elevatorsIndex).getValue()));
				sumMap.put("floors", FDCNumberHelper.add(sumMap.get("floors"), table.getCell(i, floorsIndex).getValue()));
				sumMap.put("floorHeight", FDCNumberHelper.add(sumMap.get("floorHeight"), table.getCell(i, floorHeightIndex).getValue()));
				sumMap.put("unit", FDCNumberHelper.add(sumMap.get("unit"), table.getCell(i, unitColummnIndex).getValue()));
				sumMap.put("hu", FDCNumberHelper.add(sumMap.get("hu"), table.getCell(i, huColummnIndex).getValue()));
				sumMap.put("build", FDCNumberHelper.add(sumMap.get("build"), table.getCell(i, buildColummnIndex).getValue()));
			}
			table.getCell(publicSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			//С��
			table.getCell(publicSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			//���Ͻ������
			table.getCell(publicSubIndex, groundAreaColummnIndex).setValue(sumMap.get("groundArea"));
			table.getCell(publicSubIndex, underGroundAreaColummnIndex).setValue(sumMap.get("underGroundArea"));
			table.getCell(publicSubIndex, buildColummnIndex).setValue(sumMap.get("build"));
			
			table.getCell(publicSubIndex, buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			
			table.getCell(publicSubIndex, givingAreaColummnIndex).setValue(sumMap.get("givingArea"));
			table.getCell(publicSubIndex, constructAreaColummnIndex).setValue(sumMap.get("constructArea"));
			
			table.getCell(publicSubIndex, sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			
			table.getCell(publicSubIndex, rentAreaColummnIndex).setValue(sumMap.get("rentArea"));
			table.getCell(publicSubIndex, groundRentAreaColummnIndex).setValue(sumMap.get("groundRentArea"));
			table.getCell(publicSubIndex, publicAreaColummnIndex).setValue(sumMap.get("publicArea"));
			table.getCell(publicSubIndex, deliveryAreaColummnIndex).setValue(sumMap.get("deliveryArea"));
			
			table.getCell(publicSubIndex, elevatorsIndex).setValue(sumMap.get("elevators"));
//			table.getCell(publicSubIndex, floorsIndex).setValue(sumMap.get("floors"));
//			table.getCell(publicSubIndex, floorHeightIndex).setValue(sumMap.get("floorHeight"));
			table.getCell(publicSubIndex, unitColummnIndex).setValue(sumMap.get("unit"));
			table.getCell(publicSubIndex, huColummnIndex).setValue(sumMap.get("hu"));
			table.getCell(publicSubIndex, productRateColummnIndex).setValue(FDCNumberHelper.ONE);
			//�ݻ���С�ƣ��������С��/ռ�����С��
//			table.getCell(publicSubIndex, cubageRateColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("buildArea"), sumMap.get("containArea")));
			//ƽ��ÿ�����С�ƣ��������С��/����С��
			table.getCell(publicSubIndex, avgHuColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("sellArea"), sumMap.get("hu")));
			
			//ͣ��
			sumMap.clear();
			for(int i=publicSubIndex+2;i<parkingSubIndex;i++){
				if( i == currentRow && isChange){
					BigDecimal tmp = FDCHelper.ZERO;
					int changedCol = e.getColIndex();
					BigDecimal buildArea = FDCNumberHelper.toBigDecimal(table.getCell(i, buildAreaColummnIndex).getValue());
					 if(changedCol == 3){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}else if(changedCol == 4){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}
//					tmp=FDCNumberHelper.divide(table.getCell(i, sellAreaColummnIndex).getValue(), table.getCell(i, huColummnIndex).getValue());
//					table.getCell(i, avgHuColummnIndex).setValue(tmp);
				}
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i, containAreaColummnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i, buildAreaColummnIndex).getValue()));
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i, sellAreaColummnIndex).getValue()));
				sumMap.put("park", FDCNumberHelper.add(sumMap.get("park"), table.getCell(i, parksColummnIndex).getValue()));
				sumMap.put("sellPark", FDCNumberHelper.add(sumMap.get("sellPark"), table.getCell(i, sellParksColummnIndex).getValue()));
				//���Ͻ������
				sumMap.put("groundArea", FDCNumberHelper.add(sumMap.get("groundArea"), table.getCell(i, groundAreaColummnIndex).getValue()));
				//���½������
				sumMap.put("underGroundArea", FDCNumberHelper.add(sumMap.get("underGroundArea"), table.getCell(i, underGroundAreaColummnIndex).getValue()));
			}
			table.getCell(parkingSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			table.getCell(parkingSubIndex, buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			table.getCell(parkingSubIndex, sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			table.getCell(parkingSubIndex, parksColummnIndex).setValue(sumMap.get("park"));
			table.getCell(parkingSubIndex, sellParksColummnIndex).setValue(sumMap.get("sellPark"));
			//���Ͻ������
			table.getCell(parkingSubIndex, groundAreaColummnIndex).setValue(sumMap.get("groundArea"));
			table.getCell(parkingSubIndex, underGroundAreaColummnIndex).setValue(sumMap.get("underGroundArea"));

			//�ϼ�
			BigDecimal total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, containAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, containAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, containAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, containAreaColummnIndex).getValue());
			table.getCell(totalIndex, containAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, groundAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, groundAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, groundAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, groundAreaColummnIndex).getValue());
			table.getCell(totalIndex, groundAreaColummnIndex).setValue(total);//jf
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, underGroundAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, underGroundAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, underGroundAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, underGroundAreaColummnIndex).getValue());
			table.getCell(totalIndex, underGroundAreaColummnIndex).setValue(total);//jf
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, buildAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, buildAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, buildAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, buildAreaColummnIndex).getValue());
			table.getCell(totalIndex, buildAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, givingAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, givingAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, givingAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, givingAreaColummnIndex).getValue());
			table.getCell(totalIndex, givingAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, constructAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, constructAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, constructAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, constructAreaColummnIndex).getValue());
			table.getCell(totalIndex, constructAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, sellAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, sellAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, sellAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, sellAreaColummnIndex).getValue());
			table.getCell(totalIndex, sellAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, rentAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, rentAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, rentAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, rentAreaColummnIndex).getValue());
			table.getCell(totalIndex, rentAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, groundRentAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, groundRentAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, groundRentAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, groundRentAreaColummnIndex).getValue());
			table.getCell(totalIndex, groundRentAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, publicAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, publicAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, publicAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, publicAreaColummnIndex).getValue());
			table.getCell(totalIndex, publicAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, deliveryAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, deliveryAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, deliveryAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, deliveryAreaColummnIndex).getValue());
			table.getCell(totalIndex, deliveryAreaColummnIndex).setValue(total);
			
//			BigDecimal newGroundBuildArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, groundAreaColummnIndex).getValue());
//			if(totalGroundAreaCell!=null&&e.getType()!=10000&&oldtotalGroundArea.compareTo(newGroundBuildArea)!=0){
//				totalGroundAreaCell.setValue(newGroundBuildArea);
//			}
//			
//			BigDecimal newUnderGroundBuildArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, underGroundAreaColummnIndex).getValue());
//			if(totalUnderGroundAreaCell!=null&&e.getType()!=10000&&oldtotalUnderGroundArea.compareTo(newUnderGroundBuildArea)!=0){
//				totalUnderGroundAreaCell.setValue(newUnderGroundBuildArea);
//			}
//			//�ܽ������
//			totalBuildAreaCell.setValue(FDCHelper.toBigDecimal(totalGroundAreaCell.getValue()).add(FDCHelper.toBigDecimal(totalUnderGroundAreaCell.getValue())));
//		
//			BigDecimal newtotalBuildArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, sellAreaColummnIndex).getValue());
//			if(totalSellAreaCell!=null&&e.getType()!=10000&&oldSellBuildArea.compareTo(newtotalBuildArea)!=0){
//				totalSellAreaCell.setValue(newtotalBuildArea);
//			}
//			//ռ�����
//			BigDecimal newtotalContainArea =FDCHelper.toBigDecimal(table.getCell(totalIndex, containAreaColummnIndex).getValue());
//			if(totalContainAreaCell!=null&&e.getType()!=10000&&oldtotalContainArea.compareTo(newtotalContainArea)!=0){
//				totalContainAreaCell.setValue(newtotalContainArea);
//			}
			
			
			
		}
		
	}
	//��ֹ�ͻ�����Ĳ�������·�õغϼƣ��̻��õغϼ����¼���
	private void calcArea(){
		BigDecimal oldGreenAmt=FDCHelper.toBigDecimal(totalGreenAreaCell.getValue());
		BigDecimal oldContainAmt=FDCHelper.toBigDecimal(totalContainAreaCell.getValue());
		BigDecimal amt=FDCHelper.ZERO;
		amt=FDCNumberHelper.add(amt,pitchRoadCell.getValue());
		amt=FDCNumberHelper.add(amt,concreteRoadCell.getValue());
		amt=FDCNumberHelper.add(amt, hardRoadCell.getValue());
		if(!isPlanIndexLogic()){
			amt=FDCNumberHelper.add(amt, hardSquareCell.getValue());
			amt=FDCNumberHelper.add(amt, hardManRoadCell.getValue());
		}
//		totalRoadAreaCell.setValue(amt);
		amt=FDCHelper.ZERO;
		amt=FDCNumberHelper.add(amt,importPubGreenAreaCell.getValue());
		amt=FDCNumberHelper.add(amt,houseGreenAreaCell.getValue());
		amt=FDCNumberHelper.add(amt, privateGardenCell.getValue());
		amt=FDCNumberHelper.add(amt, warterViewAreaCell.getValue());
		if(isPlanIndexLogic()){
			amt=FDCNumberHelper.add(amt, hardSquareCell.getValue());
			amt=FDCNumberHelper.add(amt, hardManRoadCell.getValue());
		}
//		totalGreenAreaCell.setValue(amt);
		if(amt.compareTo(oldGreenAmt)!=0||FDCHelper.toBigDecimal(totalContainAreaCell.getValue()).compareTo(oldContainAmt)!=0){
			greenAreaRateCell.setValue(FDCNumberHelper.divide(amt, totalContainAreaCell.getValue(),4,BigDecimal.ROUND_HALF_UP));
		}
	}
	void clear(){
		EventListener[] listeners =f7productType.getListeners(PreChangeListener.class);
		for(int i=0;i<listeners.length;i++){
			f7productType.removePreChangeListener((PreChangeListener)listeners[i]);
		}
		listeners =f7productType.getListeners(DataChangeListener.class);
		for(int i=0;i<listeners.length;i++){
			f7productType.removeDataChangeListener((DataChangeListener)listeners[i]);
		}
		f7productType.removeAll();
		f7productType=null;
		listeners = this.table.getListeners(KDTEditListener.class);
		for(int i=0;i<listeners.length;i++){
			this.table.removeKDTEditListener((KDTEditListener)listeners[i]);
		}
		
		listeners = this.table.getListeners(KDTMouseListener.class);
		for(int i=0;i<listeners.length;i++){
			this.table.removeKDTMouseListener((KDTMouseListener)listeners[i]);
		}
		this.table.setBeforeAction(null);
		this.table.setAfterAction(null);
		this.table.removeAll();
		this.table=null;
		this.headId=null;
		this.binder=null;
		this.measureCostEditU=null;
		this.planIndexInfo=null;
	}
	

	public JComponent getContentPanel(){
		if(isHasSubTable()){
			KDSplitPane panel=new KDSplitPane();
			panel.setOrientation(0);
			panel.setDividerLocation(300);
			panel.setOneTouchExpandable(true);
//			panel.setLayout(new BorderLayout());
//			this.getTable().setSize(500, 500);
			panel.add(this.getTable(),KDSplitPane.TOP);
			KDContainer con=new KDContainer();
			con.setContainerType(KDContainer.STATICSTYLE);
			con.setEnableActive(false);
			con.setTitle("�Զ���ָ��");
			con.getContentPane().setLayout(new BorderLayout());
			con.getContentPane().add(this.getSubTable(),BorderLayout.CENTER);
			JButton btnAdd=con.add(new AbstractAction(){
				{
					putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_addline"));
					putValue(Action.SHORT_DESCRIPTION, "�����");
				}
				public void actionPerformed(ActionEvent e) {
					subTable.addRow();
				}
			});
			
			JButton btnDel=con.add(new AbstractAction(){
				{
					putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
					putValue(Action.SHORT_DESCRIPTION, "ɾ����");
				}
				public void actionPerformed(ActionEvent e) {
					int[] selectedRows = KDTableUtil.getSelectedRows(subTable);
					for(int i=selectedRows.length-1;i>=0;i--){
						subTable.removeRow(selectedRows[i]);
					}
				}
			});
//			con.setSize(500, 100);
			btnAdd.setEnabled(true);
			btnAdd.setVisible(true);
			btnDel.setEnabled(true);
			btnDel.setVisible(true);
			panel.add(con,KDSplitPane.BOTTOM);
//			panel.setDividerLocation(0.7);
	        if(measureCostEditU.getOprtState().equals(OprtState.VIEW)){
	        	subTable.getStyleAttributes().setLocked(true);
	        	btnDel.setEnabled(false);
	        	btnAdd.setEnabled(false);
			}
			return panel;
		}else{
			return getTable();
		}
		
	}
	private static boolean isBuildPartLogic = false;
	private static boolean isBuildPartLogic(){
		return !isBuildPartLogic;
	}
	private boolean isPlanIndexLogic = false;
	private boolean isPlanIndexLogic(){
		return isPlanIndexLogic;
	}
	private boolean isHasSubTable=false;
	private boolean isHasSubTable(){
		return isHasSubTable;
	}
	private KDTable subTable=null;
	private void initSubTable(PlanIndexInfo info){
		//id,number,name,value,isProduct
		subTable=new KDTable(5,1,0);
		subTable.setName("�Զ���ָ��");
		IRow headRow=subTable.getHeadRow(0);
		subTable.getColumn(0).setKey("id");
		subTable.getColumn(0).getStyleAttributes().setHided(true);
		subTable.getColumn(1).setKey("number");
		subTable.getColumn(2).setKey("name");
		subTable.getColumn(3).setKey("value");
		subTable.getColumn(4).setKey("productType");
		subTable.getColumn("name").getStyleAttributes().setLocked(true);
		subTable.getColumn("number").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		subTable.getColumn("value").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		subTable.addKDTEditListener(new KDTEditAdapter(){
			public void editStopped(KDTEditEvent e) {
				
				int rowIndex = e.getRowIndex();
				int colIndex=e.getColIndex();
				IRow row=subTable.getRow(rowIndex);
				if(colIndex==subTable.getColumnIndex("number")){
					ApportionTypeInfo info=(ApportionTypeInfo)row.getCell("number").getValue();
					if(info!=null){
						row.getCell("name").setValue(info.getName());
					}else{
						row.getCell("name").setValue(null);
					}
				}
			}
		});
		subTable.addKDTMouseListener(new KDTMouseListener(){
			public void tableClicked(KDTMouseEvent e) {/*
				int rowIndex = e.getRowIndex();
				int colIndex=e.getColIndex();
				IRow row=subTable.getRow(rowIndex);
				if(colIndex==subTable.getColumnIndex("product")){
					Object object = row.getCell("isProduct").getValue();
					row.getCell("isProduct").setValue(Boolean.valueOf(object==Boolean.FALSE));
				}
			*/}
		});
		headRow.getCell("id").setValue("ID");
		headRow.getCell("number").setValue("����");
		headRow.getCell("name").setValue("����");
		headRow.getCell("value").setValue("ָ��ֵ");
		headRow.getCell("productType").setValue("��Ʒ");
		
		
		final String indexSql = "select fapportiontypeid from t_fdc_measureindex where fisenabled=1 and ftype='1CUSTOM' ";
		//��ʱ���ˣ���������Ԥ��ָ��
		final Set nameSet = new HashSet();
		
		String[] name =  new String[] {
					"��ռ�����(m2)", "�����õ����(m2)", "�ܽ������(m2)",
					"������ռ�����(m2)", "�����ܶ�", "�̵���", "���ݻ������(m2)",
					"�ݻ���", "����·�泵�е�", "��·�泵�е���ͣ������", "Ӳ����װ���е�",
					"Ӳ����װ�㳡", "Ӳ����װ���е�", "��Ҫ�����̵�", "����լ���̻�",
					"�ײ�˽�һ�԰", "ˮ�����", "ռ�����", "�������", "�������",
					"��Ʒ����", "ƽ��ÿ�����", "��Ԫ��", "����"};
		for(int i=0;i<name.length;i++){
			nameSet.add(name[i]);
		}
		KDBizPromptBox f7 = new KDBizPromptBox();
		f7.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ApportionTypeQuery");
		f7.setDisplayFormat("$number$");
		f7.setCommitFormat("$number$");
		f7.setEditFormat("$number$");
		f7.setRequired(true);
		f7.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7 =(KDBizPromptBox)e.getSource();
				f7.getQueryAgent().setDefaultFilterInfo(null);
				f7.getQueryAgent().setHasCUDefaultFilter(false);
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				try {
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.buildAreaType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.sellAreaType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.placeAreaType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.aimCostType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.appointType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("name", nameSet,
									CompareType.NOTINCLUDE));
					filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
					//TODO �ɱ���̯����
					filter.getFilterItems().add(new FilterItemInfo("forCostApportion",Boolean.TRUE));
					//TODO �е��������� ���˼���ָ��
					boolean isMeasureIndex = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MEASUREINDEX);
					if(isMeasureIndex){
						filter.getFilterItems().add(new FilterItemInfo("id",indexSql,CompareType.INNER));
					}
					filter.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
				} catch (BOSException e1) {
					measureCostEditU.handUIException(e1);
				} catch (EASBizException e2) {
					measureCostEditU.handUIException(e2);
				}
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});
		//verifyCustomУ�飬ָ��+��Ʒ�����ظ�
		/*f7.addPreChangeListener(new PreChangeListener(){
			public void preChange(PreChangeEvent e) {
				Object objNew=e.getData();
				int rowIndex=KDTableUtil.getSelectedRow(table);
				if(objNew instanceof ApportionTypeInfo){
					for(int i = 0; i < subTable.getRowCount(); ++i){
						if(rowIndex != i){
							Object obj=subTable.getRow(i).getCell("number").getValue();
							if(obj != null && obj instanceof ApportionTypeInfo){
								if(((ApportionTypeInfo)obj).getId().equals(((ApportionTypeInfo)objNew).getId())){
									MsgBox.showWarning(measureCostEditU, "��ָ���Ѿ��������ڵ�"+(i+1)+"��");
									e.setResult(PreChangeEvent.S_FALSE);
									return;
								}
							}
						}
					}					
				}
			}
		});*/
		f7.addCommitListener(new CommitListener(){
			public void willCommit(CommitEvent e) {
				KDBizPromptBox f7 =(KDBizPromptBox)e.getSource();
				f7.getQueryAgent().setDefaultFilterInfo(null);
				f7.getQueryAgent().setHasCUDefaultFilter(false);
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				try {
					filter.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
				} catch (BOSException e1) {
					measureCostEditU.handUIException(e1);
				}
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});
		
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(f7);
        subTable.getColumn("number").setEditor(editor);
        
        ObjectValueRender render = new ObjectValueRender();
        render.setFormat(new com.kingdee.bos.ctrl.extendcontrols.BizDataFormat("$number$"));
		subTable.getColumn("number").setRenderer(render);
		
        f7 = new KDBizPromptBox();
		f7.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		
		f7.setDisplayFormat("$name$");
		f7.setCommitFormat("$number$");
		f7.setEditFormat("$number$");
		//willShow��һ�μ�����������Ч���������в�Ʒ by hpw 2010.09.03
		Set productSet=getProductIdSet();
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		if(productSet.size()>0){
			view.getFilter().getFilterItems().add(new FilterItemInfo("id",productSet,CompareType.INCLUDE));
		}
		f7.setEntityViewInfo(view);
		f7.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				Set productSet=getProductIdSet();
				EntityViewInfo view=new EntityViewInfo();
				view.setFilter(new FilterInfo());
				if(productSet.size()>0){
					view.getFilter().getFilterItems().add(new FilterItemInfo("id",productSet,CompareType.INCLUDE));
				}
				((KDBizPromptBox)e.getSource()).setEntityViewInfo(view);
				
			}
		});
        editor = new KDTDefaultCellEditor(f7);
        subTable.getColumn("productType").setEditor(editor);
        subTable.getColumn("value").setEditor(getCellNumberEdit(8));
        FDCHelper.formatTableNumber(subTable, "value");
        if(info==null||info.getCustomEntrys()==null||info.getCustomEntrys().size()==0){
        	return;
        }
        for(Iterator iter=info.getCustomEntrys().iterator();iter.hasNext();){
        	IRow row=subTable.addRow();
        	CustomPlanIndexEntryInfo entry=(CustomPlanIndexEntryInfo)iter.next();
        	row.getCell("id").setValue(entry.getId().toString());
        	row.getCell("number").setValue(entry.getApportType());
        	row.getCell("name").setValue(entry.getApportType().getName());
        	row.getCell("productType").setValue(entry.getProductType());
        	row.getCell("value").setValue(entry.getValue());
        	row.setUserObject(entry);
        }
	}
	
	private KDTable constrTable=null;
	public KDTable getConstructTable(){
		return constrTable;
	}
	private void initConstructTable(MeasureCostInfo info){
		constrTable = new KDTable(4,1,0);
		constrTable.setName("�����׼");
		IRow headRow=constrTable.getHeadRow(0);
		constrTable.getColumn(0).setKey("id");
		constrTable.getColumn(0).getStyleAttributes().setHided(true);
		constrTable.getColumn(1).setKey("indexName1");
		constrTable.getColumn(2).setKey("indexName2");
		constrTable.getColumn(3).setKey("indexName3");
		
		headRow.getCell(0).setUserObject("construct");
		headRow.getCell(0).setValue("ID");
		headRow.getCell(1).setValue("һ��ָ������");
		headRow.getCell(2).setValue("����ָ������");
		headRow.getCell(3).setValue("����ָ������");
		
		if(info==null||info.getConstrEntrys()==null||info.getConstrEntrys().size()==0){
        	return;
        }
		
		for(Iterator iter2=info.getConstrEntrys().iterator();iter2.hasNext();){
			ConstructPlanIndexEntryInfo indexEntry = (ConstructPlanIndexEntryInfo)iter2.next();
			
			if(indexEntry.getProductType()==null){
				continue;
			}
			ProductTypeInfo indexProduct = indexEntry.getProductType();
			String productId = indexProduct.getId().toString();
			String entryKey = indexEntry.getIndexName1()+indexEntry.getIndexName2()+indexEntry.getIndexName3();
			if(constrTable.getRowCount()==0){
				IColumn column = constrTable.addColumn();
    			column.setWidth(150);
    			column.setKey(productId);
    			headRow.getCell(productId).setValue(indexProduct.getName());
    			headRow.getCell(productId).setUserObject(indexProduct);
    			IRow row = constrTable.addRow();
    			row.getCell("id").setValue(indexEntry.getId().toString());
	        	row.getCell("indexName1").setValue(indexEntry.getIndexName1());
	        	row.getCell("indexName2").setValue(indexEntry.getIndexName2());
	        	row.getCell("indexName3").setValue(indexEntry.getIndexName3());
	        	row.getCell(productId).setValue(indexEntry.getDesc());
	        	row.getCell(productId).setUserObject(indexEntry);
			}
			boolean isAdd = false;
			for(int i=0;i<constrTable.getRowCount();i++){
        		IRow idxRow = constrTable.getRow(i);
        		String idxKey = (String)idxRow.getCell("indexName1").getValue()+idxRow.getCell("indexName2").getValue()+idxRow.getCell("indexName3").getValue();
        		if(entryKey.equals(idxKey)){
        			isAdd=true;
        			if(headRow.getCell(productId)==null){
        				IColumn column = constrTable.addColumn();
            			column.setWidth(150);
            			column.setKey(productId);
            			headRow.getCell(productId).setValue(indexProduct.getName());
            			headRow.getCell(productId).setUserObject(indexProduct);
        			}
        			idxRow.getCell(productId).setValue(indexEntry.getDesc());
        			idxRow.getCell(productId).setUserObject(indexEntry);
        		}
			}
        	if (!isAdd) {
				IRow row = constrTable.addRow();
				row.getCell("id").setValue(indexEntry.getId().toString());
				row.getCell("indexName1").setValue(indexEntry.getIndexName1());
				row.getCell("indexName2").setValue(indexEntry.getIndexName2());
				row.getCell("indexName3").setValue(indexEntry.getIndexName3());
				if (headRow.getCell(productId) == null) {
					IColumn column = constrTable.addColumn();
					column.setWidth(150);
					column.setKey(productId);
					headRow.getCell(productId).setValue(indexProduct.getName());
					headRow.getCell(productId).setUserObject(indexProduct);
				}
				row.getCell(productId).setValue(indexEntry.getDesc());
				row.getCell(productId).setUserObject(indexEntry);
			}
	        
		}
		
		
		if(true)return;
		for(Iterator iter2=info.getConstrEntrys().iterator();iter2.hasNext();){
			//��ͷ
			ConstructPlanIndexEntryInfo indexEntry = (ConstructPlanIndexEntryInfo)iter2.next();
			if(indexEntry.getProductType()==null){
				continue;
			}
			ProductTypeInfo indexProduct = indexEntry.getProductType();
			String productId = indexProduct.getId().toString();
			
			//����
			for(Iterator iter=info.getConstrEntrys().iterator();iter.hasNext();){
	        	ConstructPlanIndexEntryInfo entry=(ConstructPlanIndexEntryInfo)iter.next();
	        	String entryKey = entry.getIndexName1()+entry.getIndexName2()+entry.getIndexName3();
	        	ProductTypeInfo entryProduct = entry.getProductType();	
	        	String entryProductId = entryProduct.getId().toString();
	        	boolean isAdd=false;
	        	for(int i=0;i<constrTable.getRowCount();i++){
	        		IRow idxRow = constrTable.getRow(i);
	        		String idxKey = (String)idxRow.getCell("indexName1").getValue()+idxRow.getCell("indexName2").getValue()+idxRow.getCell("indexName3").getValue();
	        		if(entryKey.equals(idxKey)){
	        			IColumn column = constrTable.addColumn();
	        			column.setWidth(150);
	        			column.setKey(entryProductId);
	        			headRow.getCell(entryProductId).setValue(entryProduct.getName());
	        			headRow.getCell(entryProductId).setUserObject(entryProduct);
	        			
	        			idxRow.getCell(entryProductId).setValue(entry.getDesc());
	        			idxRow.getCell(entryProductId).setUserObject(entry);
	        		}else{
	        			IRow row=constrTable.addRow();
			        	if(productId.equals(entryProductId)){
				        	row.getCell("id").setValue(entry.getId().toString());
				        	row.getCell("indexName1").setValue(entry.getIndexName1());
				        	row.getCell("indexName2").setValue(entry.getIndexName2());
				        	row.getCell("indexName3").setValue(entry.getIndexName3());
				        	row.getCell(entryProductId).setValue(entry.getDesc());
				        	row.getCell(entryProductId).setUserObject(entry);
			        	}
	        		}
	        	}
	        	if(!isAdd){
		        	IRow row=constrTable.addRow();
		        	if(productId.equals(entryProductId)){
			        	row.getCell("id").setValue(entry.getId().toString());
			        	row.getCell("indexName1").setValue(entry.getIndexName1());
			        	row.getCell("indexName2").setValue(entry.getIndexName2());
			        	row.getCell("indexName3").setValue(entry.getIndexName3());
			        	row.getCell(productId).setValue(entry.getDesc());
			        	row.getCell(productId).setUserObject(entry);
		        	}
	        	}
			}
        }
		
	}
	
	public KDTable getSubTable(){
		return subTable;
	}
	
	private void storeCustom(PlanIndexInfo planIndexInfo) {
		if(!isHasSubTable()){
			return;
		}
		verifyCustom();
		CustomPlanIndexEntryCollection customEntrys = planIndexInfo.getCustomEntrys();
		if(customEntrys==null){
			customEntrys=new CustomPlanIndexEntryCollection();
		}
		customEntrys.clear();
		
		for(int i=0;i<subTable.getRowCount();i++){
			IRow row=subTable.getRow(i);
			CustomPlanIndexEntryInfo entry=(CustomPlanIndexEntryInfo)row.getUserObject();
			if(entry==null){
				entry=new CustomPlanIndexEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				row.setUserObject(entry);
			}
			entry.setApportType((ApportionTypeInfo)row.getCell("number").getValue());
			entry.setProductType((ProductTypeInfo)row.getCell("productType").getValue());
			entry.setValue((BigDecimal)row.getCell("value").getValue());
			entry.setParent(planIndexInfo);
			if(entry.getProductType()!=null){
				entry.setIsProduct(true);
			}else{
				entry.setIsProduct(false);
			}
			customEntrys.add(entry);
		}
		
	}
	
	public ConstructPlanIndexEntryCollection getConstrEntrys() {
//		verifyConstruct();
		ConstructPlanIndexEntryCollection constructEntrys = costInfo.getConstrEntrys();
		if(constructEntrys==null){
			constructEntrys=new ConstructPlanIndexEntryCollection();
		}
		constructEntrys.clear();
		//��Ʒ��
		for(int j=4;j<constrTable.getColumnCount();j++){
			IRow headRow = constrTable.getHeadRow(0);
			ProductTypeInfo product = (ProductTypeInfo)headRow.getCell(j).getUserObject();
			String productId = product.getId().toString();
			//ָ����
			for(int i=0;i<constrTable.getRowCount();i++){
				IRow row=constrTable.getRow(i);
				if(row==null) continue;
				ConstructPlanIndexEntryInfo entry=row.getCell(productId)==null?null:(ConstructPlanIndexEntryInfo)row.getCell(productId).getUserObject();
				if(entry==null){
					entry=new ConstructPlanIndexEntryInfo();
					entry.setId(BOSUuid.create(entry.getBOSType()));
					row.getCell(productId).setUserObject(entry);
				}
				entry.setIndexName1((String)row.getCell("indexName1").getValue());
				entry.setIndexName2((String)row.getCell("indexName2").getValue());
				entry.setIndexName3((String)row.getCell("indexName3").getValue());
				entry.setDesc((String)row.getCell(productId).getValue());
				entry.setProductType(product);
				constructEntrys.add(entry);
			}
		}
		return constructEntrys;
	}
	
	private void verifyConstruct() {
		// TODO Auto-generated method stub
		
	}
	private void verifyCustom(){
		//ָ��+��ƷΨһ,ָ���¼
		for(int i=0;i<subTable.getRowCount();i++){
			IRow row=subTable.getRow(i);
			ApportionTypeInfo apportType = (ApportionTypeInfo)row.getCell("number").getValue();
			if(apportType==null){
				FDCMsgBox.showError(measureCostEditU, "�����¼");
				subTable.getSelectManager().select(i, 1,i,1);
				measureCostEditU.getplTables().setSelectedIndex(1);
				SysUtil.abort();
			}
			
			if(row.getCell("value").getValue()==null){
				FDCMsgBox.showError(measureCostEditU, "ָ��ֵ��¼");
				subTable.getSelectManager().select(i, 3,i,3);
				measureCostEditU.getplTables().setSelectedIndex(1);
				SysUtil.abort();
			}
		}
		
		for(int i=0;i<subTable.getRowCount();i++){
			IRow row=subTable.getRow(i);
			ApportionTypeInfo apportType = (ApportionTypeInfo)row.getCell("number").getValue();
			ProductTypeInfo productType = (ProductTypeInfo)row.getCell("productType").getValue();
			for(int j=i+1;j<subTable.getRowCount();j++){
				IRow row2=subTable.getRow(j);
				ApportionTypeInfo apportType2= (ApportionTypeInfo)row2.getCell("number").getValue();
				ProductTypeInfo productType2 = (ProductTypeInfo)row2.getCell("productType").getValue();
				if(apportType.getId().toString().equals(apportType2.getId().toString())){
					boolean isDup=false;
					if(productType==null&&productType2==null){
						isDup=true;
					}
					if(productType!=null&&productType2!=null&&productType.getId().toString().equals(productType2.getId().toString())){
						isDup=true;
					}
					if(isDup){
						FDCMsgBox.showError(measureCostEditU, "��"+(i+1)+"�����"+(j+1)+"�д����ظ�");
						SysUtil.abort();
					}
				}
			}
		}
	}
	
	/**
	 * �õ�һ��-1.0E17����1.0E17��BigDecimal CellEditor
	 * @author sxhong  		Date 2006-10-26
	 * @return
	 */
	public static KDTDefaultCellEditor getCellNumberEdit(int Precision){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(Precision);
        kdc.setRequired(true);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
}
