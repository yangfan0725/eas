/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.IRoomDes;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDesCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDesInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.AbstractCoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomDefineEditUI extends AbstractRoomDefineEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomDefineEditUI.class);
    
    private int unitCount = 0;
    
    
	SellProjectInfo sellPro = null;
	
	KDPanel basePanel = this.kDPanel1;
	
	RoomDesInfo baseRoomDes = this.editData;
	
	RoomDesCollection roomDesCol = new RoomDesCollection();
	
	
    /**
     * output class constructor
     */
	
    public RoomDefineEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
        
        this.editData.setNewNoise((RoomAssistantInfo)this.prmtNoise.getValue());
        this.editData.setNewEyeSight((RoomAssistantInfo)this.prmtEyeSignht.getValue());
        this.editData.setNewDecorastd((RoomAssistantInfo)this.prmtDecoraStd.getValue());
        this.editData.setNewPossstd((RoomAssistantInfo)this.prmtPosseStd.getValue());
//        this.editData.setNewProduceRemark((RoomAssistantInfo)this.prmt);
        this.editData.setNewRoomUsage((RoomAssistantInfo)this.prmtRoomUsage.getValue());
        this.editData.setNewSight((RoomAssistantInfo)this.prmtSight.getValue());
        
        this.editData.setBuilding((BuildingInfo) this.prmtBuilding.getValue());
        this.editData.setProductType((ProductTypeInfo) this.prmtProductType.getValue());
        this.editData.setBuildingProperty((BuildingPropertyInfo) this.prmtBuildingProperty.getValue());
        this.editData.setDirection((HopedDirectionInfo) this.prmtDirection.getValue());
//        this.editData.setEyeSignht((EyeSignhtInfo)this.prmtEyeSignht.getValue());
//        this.editData.setRoomModel((RoomModelInfo) this.prmtRoomModel.getValue());
//        this.editData.setPosseStd((PossessionStandardInfo) this.prmtPosseStd.getValue());
//        this.editData.setSight((SightRequirementInfo) this.prmtSight.getValue());
//        this.editData.setRoomUsage((RoomUsageInfo) this.prmtRoomUsage.getValue());
        this.editData.setBuildStruct((BuildingStructureInfo)this.prmtBuilStruct.getValue());
//        this.editData.setDecoraStd((DecorationStandardInfo) this.prmtDecoraStd.getValue());
//        this.editData.setNoise((NoiseInfo)this.prmtNoise.getValue());
        this.editData.setPlanBuildingArea((BigDecimal)this.txtPlanBuildingArea.getValue(BigDecimal.class));
        this.editData.setPlanRoomArea((BigDecimal)this.txtPlanRoomArea.getValue(BigDecimal.class));
        this.editData.setBuildingArea((BigDecimal)this.txtBuildingArea.getValue(BigDecimal.class));
        this.editData.setRoomArea((BigDecimal)this.txtRoomArea.getValue(BigDecimal.class));
        this.editData.setActualBuildingArea((BigDecimal)this.txtActualBuildingArea.getValue(BigDecimal.class));
        this.editData.setActualRoomArea((BigDecimal)this.txtActualRoomArea.getValue(BigDecimal.class));
        this.editData.setFlatArea((BigDecimal)this.txtFlatArea.getValue(BigDecimal.class));
        this.editData.setCarbarnArea((BigDecimal)this.txtCarbarnArea.getValue(BigDecimal.class));
        this.editData.setBalconyArea((BigDecimal)this.txtBalconyArea.getValue(BigDecimal.class));
        this.editData.setUserArea((BigDecimal)this.txtUseArea.getValue(BigDecimal.class));
        this.editData.setGuardenArea((BigDecimal)this.txtGuardenArea.getValue(BigDecimal.class));
        this.editData.setIbasement((BigDecimal)this.txtIbasement.getValue(BigDecimal.class));		//add by shilei
        this.editData.setIbaInnside((BigDecimal)this.txtIbaInnside.getValue(BigDecimal.class));		
        this.editData.setIbameasured((BigDecimal)this.txtIbameasured.getValue(BigDecimal.class));	
        this.editData.setInsside((BigDecimal)this.txtinnside.getValue(BigDecimal.class));			
    }

    
    public void loadFields()
    {
        super.loadFields();
        BuildingInfo build = (BuildingInfo)this.prmtBuilding.getValue();
        
       
        if(OprtState.EDIT.equals(this.getOprtState())){
        	
        	if(null!= build){
        		this.tblBuiding.checkParsed();
        		EntityViewInfo view = new EntityViewInfo();
            	FilterInfo filter = new FilterInfo();
            	filter.getFilterItems().add(new FilterItemInfo("building",build.getId().toString()));
            	
            	view.setFilter(filter);
            	RoomDesCollection roomDesCol =null;
        		try {
					 roomDesCol = ((IRoomDes)this.getBizInterface()).getRoomDesCollection(view);
				} catch (BOSException e) {
					// TODO Auto-generated catch block
					logger.warn("��Ԫ��¼����ʧ�ܣ�");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.warn("��Ԫ��¼����ʧ�ܣ�");
				}
				//���ط�¼
				Iterator it = roomDesCol.iterator();
//				while(it.hasNext()){
//					RoomDesInfo roomDes = (RoomDesInfo)it.next();
//					IRow row = this.tblBuiding.addRow();
//					row.getCell("unitId").setValue(value);
//					row.getCell("unitName").setValue(value);
//					row.getCell("floorFrom").setValue(value);
//					row.getCell("floorTo").setValue(value);
//					row.getCell("serialNumFrom").setValue(value);
//					row.getCell("serialNumTo").setValue(value);
//					row.getCell("isConvertToChar").setValue(value);
//					
//				}
        	}
        	
        	
         }
     
    
		
		
    }
    
    public SelectorItemCollection getSelectors()
    {
    		SelectorItemCollection selector = super.getSelectors();
    		selector.add("building");
    		selector.add("*");
    		selector.add("unit.*");
    		selector.add("unit");
    		return selector;
    }
    
    public void initTable() throws BOSException{
    	//�������ÿ��cell�����ô�����ͬ��ֵ��
    	
		
    	
		KDTDefaultCellEditor spinnerCellFloorFrom = new KDTDefaultCellEditor(new KDNumberTextField());
		KDTDefaultCellEditor spinnerCellFloorTo = new KDTDefaultCellEditor(new KDNumberTextField());
		KDTDefaultCellEditor spinnerCellSeqFrom = new KDTDefaultCellEditor(new KDNumberTextField());
		KDTDefaultCellEditor spinnerCellSeqTo = new KDTDefaultCellEditor(new KDNumberTextField());
		
		this.tblBuiding.getColumn("roomModel").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery", SHEHelper.getModelBySellProjectView(sellPro)));
		 this.tblBuiding.getColumn("serialNumFrom").setEditor(spinnerCellSeqFrom);
	        this.tblBuiding.getColumn("serialNumTo").setEditor(spinnerCellSeqTo);
	        this.tblBuiding.getColumn("floorFrom").setEditor(spinnerCellFloorFrom);
	        this.tblBuiding.getColumn("floorTo").setEditor(spinnerCellFloorTo);
//	        this.tblBuiding.getColumn("serialNumFrom").setEditor(spinnerCellSeq);
//	        this.tblBuiding.getColumn("serialNumTo").setEditor(spinnerCellSeq);
	        KDCheckBox checkBox = new KDCheckBox();
			KDTDefaultCellEditor chkEditor = new KDTDefaultCellEditor(checkBox);
			
		
	        this.tblBuiding.getColumn("isConvertToChar").setEditor(chkEditor);
	      
	        this.tblBuiding.getColumn("isSelected").setEditor(chkEditor);
 		SpinnerNumberModel model = new SpinnerNumberModel(0, 0,999, 1);
 		this.unitAmount.setModel(model);
    }
    /**
     * ������鷳�����ǳ��쳣
     * @param name
     * @return
     * @throws UIException
     */
    public CoreUIObject newPanelUI(String name,RoomDesInfo roomDes) throws UIException{
    	CoreUIObject detailUI =null;
    	UIContext uiContext = new UIContext(ui);
		uiContext.put("SellProject", sellPro);
		uiContext.put("Data",roomDes);
		
		detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(RoomDesPaneUI.class.getName(),
				uiContext, null, this.STATUS_ADDNEW);
		detailUI.loadFields();
		return detailUI;
    }
  
    public static ICellEditor getKDFormattedTextDecimalEditor(){
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.INTEGER);
//		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(false);
		formattedTextField.setNegatived(true);
		ICellEditor formatTextEditor = new KDTDefaultCellEditor(formattedTextField);
		return formatTextEditor;		
	}
    
    public void onLoad() throws Exception {
    		super.onLoad();
    		
    		//ȥ������ҳǩ
    		this.kDTabbedPane1.removeAll();

    		initAllF7();
    		this.btnAddNew.setVisible(false);
    		this.actionAddNew.setEnabled(false);
    		this.tblBuiding.checkParsed();
    		setTxtPrecistion();
    		if(null!=((BuildingInfo)this.getUIContext().get("building")).getSellProject()){
    			 sellPro = ((BuildingInfo)this.getUIContext().get("building")).getSellProject();
    		}
    		BuildingInfo build =null;
    		if(OprtState.ADDNEW.equalsIgnoreCase(this.getOprtState())){
    			SelectorItemCollection selector = new SelectorItemCollection();
    			selector.add("*");
    			selector.add("buildingStructure.*");
    			selector.add("buildingProperty.*");
    			selector.add("productType.*");
    			Object building = (BuildingInfo)this.getUIContext().get("building");
    			 build  = BuildingFactory.getRemoteInstance()
    			 			.getBuildingInfo(new ObjectUuidPK(BOSUuid.read
    			 					(((AbstractCoreBaseInfo) building).getId().toString())),selector);
    			if(null!=building){
    			this.editData.setBuilding(build);
    			this.editData.setBuildStruct(build.getBuildingStructure());
    			this.editData.setBuildingProperty(build.getBuildingProperty());
    			this.editData.setProductType(build.getProductType());
    			this.prmtBuilding.setValue(build);
    			}
    			else{
    				this.prmtBuilding.setEditable(true);
    			}
    		}
    		
    		//����һ��Ҫ�½���������ֻnew һ��kdspinnerCelleditor ÿ��CELL�����һ��kdspinnerCelleditor ���ڴ�
    		//�������ÿ��cell�����ô�����ͬ��ֵ��
//    		KDTDefaultCellEditor spinnerCellFloorFrom = new KDTDefaultCellEditor(new KDNumberTextField());
//    		KDTDefaultCellEditor spinnerCellFloorTo = new KDTDefaultCellEditor(new KDNumberTextField());
//    		KDTDefaultCellEditor spinnerCellSeqFrom = new KDTDefaultCellEditor(new KDNumberTextField());
//    		KDTDefaultCellEditor spinnerCellSeqTo = new KDTDefaultCellEditor(new KDNumberTextField());

    		this.tblBuiding.getColumn("serialNumFrom").setEditor(getKDFormattedTextDecimalEditor());
	        this.tblBuiding.getColumn("serialNumTo").setEditor(getKDFormattedTextDecimalEditor());
 	        this.tblBuiding.getColumn("floorFrom").setEditor(getKDFormattedTextDecimalEditor());
 	        this.tblBuiding.getColumn("floorTo").setEditor(getKDFormattedTextDecimalEditor());

 	        KDCheckBox checkBox = new KDCheckBox();
 			KDTDefaultCellEditor chkEditor = new KDTDefaultCellEditor(checkBox);
 			this.tblBuiding.getColumn("isConvertToChar").setEditor(chkEditor);
 			this.tblBuiding.getColumn("isSelected").setEditor(chkEditor);
    		SpinnerNumberModel model = new SpinnerNumberModel(0, 0,999, 1);
    		this.unitAmount.setModel(model);
    		//������д
    		((DefaultEditor) this.unitAmount.getEditor()).getTextField().setEditable(false);
    		for (int m =0 ; m<this.unitAmount.getEditor().getComponentListeners().length ; m++){
    			((DefaultEditor)this.unitAmount.getEditor()).getTextField().removeComponentListener(this.unitAmount.getEditor().getComponentListeners()[m]);
    		}
    		this.tblBuiding.checkParsed();
    		  
    		if(OprtState.EDIT.equalsIgnoreCase(this.getOprtState())){
    			this.tblBuiding.getMergeManager().mergeBlock(0, 2, this.tblBuiding.getRowCount() -1,  3, KDTMergeManager.FREE_ROW_MERGE);
    		}
    			
    			
    	//��¼�ϼ�ֵ
    			//��Ϊÿ�ν��붼��������������ôд
    			EntityViewInfo view = new EntityViewInfo();
    	    	FilterInfo filter = new FilterInfo();
    	    	filter.getFilterItems().add(new FilterItemInfo("building",((BuildingInfo)this.prmtBuilding.getValue()).getId().toString()));
    	    
    	    	SorterItemCollection sortCol = new SorterItemCollection();
                
            	SorterItemInfo sort =new SorterItemInfo("unit.seq");
            	
            	sort.setSortType(SortType.ASCEND);
            	sortCol.add(sort);
            	view.setSorter(sortCol);
    	    	view.setFilter(filter);
    	    	view.setSelector(SHEHelper.getRoomDesSelector());
    	    	RoomDesCollection roomDesCol =null;
    	    	//�Ƿ���ʾ��Ԫ
    	    	boolean isShowUnit = true;
    	    	try {
    	    		roomDesCol = ((IRoomDes)this.getBizInterface()).getRoomDesCollection(view);
    			} catch (BOSException e) {
    				// TODO Auto-generated catch block
    				logger.warn("��Ԫ��Ϣ����ʧ�ܣ�");
    				FDCMsgBox.showError("��Ԫ��Ϣ����ʧ�ܣ�");
    				SysUtil.abort();
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				logger.warn("��Ԫ��Ϣ����ʧ�ܣ�");
    				FDCMsgBox.showError("��Ԫ��Ϣ����ʧ�ܣ�");
    				SysUtil.abort();
    			}
    			if(null!=roomDesCol&&roomDesCol.size()>0){
    				
    		
    			for(int i = 0; i<roomDesCol.size() ; i++){
    				RoomDesInfo roomDes = roomDesCol.get(i);
    				IRow row = this.tblBuiding.addRow();
    				
    			
    				//����һ��Ҫ�½���������ֻnew һ��kdspinnerCelleditor ÿ��CELL�����һ��kdspinnerCelleditor ���ڴ�
    	    		//�������ÿ��cell�����ô�����ͬ��ֵ��
    				
//    				spinnerCellFloorFrom = new KDTDefaultCellEditor(new KDNumberTextField());
//    	    		 spinnerCellFloorTo = new KDTDefaultCellEditor(new KDNumberTextField());
//    	    		 spinnerCellSeqFrom = new KDTDefaultCellEditor(new KDNumberTextField());
//    	    		spinnerCellSeqTo = new KDTDefaultCellEditor(new KDNumberTextField());
    				
    				
//    				   spinnerCellFloorFrom = new KDSpinnerCellEditor(new SpinnerNumberModel(1,-999,
//    	     	    		   999,1));
//    	     	      spinnerCellFloorTo = new KDSpinnerCellEditor(new SpinnerNumberModel(1,-999,
//    	     	    		   999,1));
//    	     	       spinnerCellSeqFrom = new KDSpinnerCellEditor(new SpinnerNumberModel(1,0,9999,1));
//    	     	       spinnerCellSeqTo = new KDSpinnerCellEditor(new SpinnerNumberModel(1,0,9999,1));
////    	     	      KDFormattedTextField txtnum =new KDFormattedTextField();
//    	     	      txtnum.setDataType(Integer.class);
    	     	       
    	     	       
//    	     	       KDTDefaultCellEditor spinner = new KDTDefaultCellEditor (txtnum);
//    	     	      this.tblBuiding.getColumn("serialNumFrom").setEditor(spinnerCellSeqFrom);
//    	    	        this.tblBuiding.getColumn("serialNumTo").setEditor(spinnerCellSeqTo);
//    	     	        this.tblBuiding.getColumn("floorFrom").setEditor(spinnerCellFloorFrom);
//    	     	        this.tblBuiding.getColumn("floorTo").setEditor(spinnerCellFloorTo);
    	    		//�¼�ҳǩ
    	    		
    	    		addRoomDesPanel(row,roomDes);
//    	     	    row.getCell("floorFrom").setEditor(spinnerCellFloorFrom);
//           			row.getCell("floorTo").setEditor(spinnerCellFloorTo);
//           			row.getCell("serialNumFrom").setEditor(spinnerCellSeqFrom);
//           			row.getCell("serialNumTo").setEditor(spinnerCellSeqTo);
//           			
    				row.getCell("unitName").setValue(roomDes.getUnit()!=null?roomDes.getUnit().getName():null);
        			row.getCell("unitSeq").setValue(roomDes.getUnit()!=null?new Integer(roomDes.getUnit().getSeq()):null);
        			row.getCell("floorFrom").setValue(new Integer(roomDes.getFloorBegin()));
        			row.getCell("floorTo").setValue(new Integer(roomDes.getFloorEnd()));
        		
        			if(0!=roomDes.getSerialNumberBegin()){
        				row.getCell("serialNumFrom").setValue(new Integer(roomDes.getSerialNumberBegin()));
        			}
        			
        			if(0!=roomDes.getSerialNumberEnd()){
        				row.getCell("serialNumTo").setValue(new Integer(roomDes.getSerialNumberEnd()));
        			}
        		
        			row.getCell("isConvertToChar").setValue(new Boolean(roomDes.isIsConvertToChar()));
        			row.getCell("isSelected").setValue(Boolean.FALSE);
        			row.getCell("unitId").setValue(roomDes.getUnit()!=null?roomDes.getUnit().getId().toString():null);
        			row.getCell("billId").setValue(roomDes.getId().toString());
        			if(roomDes.getUnit().isHaveUnit()){
        				row.getCell("unitSeq").getStyleAttributes().setFontColor(Color.GRAY);
                		row.getCell("unitName").getStyleAttributes().setFontColor(Color.GRAY);
                		row.getCell("unitSeq").getStyleAttributes().setBackground(Color.GRAY);
                		row.getCell("unitName").getStyleAttributes().setBackground(Color.GRAY);
                		isShowUnit =false;
        			}
        			
    			}
    			}else{
    				 if(OprtState.ADDNEW.equals(this.getOprtState())){
     		        	
     		        	this.prmtProductType.setValue( build.getProductType());
     		        	this.prmtBuildingProperty.setValue(build.getBuildingProperty());
     		        	this.prmtBuilStruct.setValue(build.getBuildingStructure());
     		        	
     		        }
    			}
    			
    			//���ض�����Ϣ
    			loadRoomDesBaseData(roomDesCol);
    		
//    			this.tblBuiding.getColumn("isConvertToChar").setGroup(true);
//    			this.tblBuiding.getGroupManager().setGroup(true);
    			 //�õ�unitCoun
    	    	this.unitCount =((BuildingInfo)this.getUIContext().get("building")).getUnitCount();
    			this.unitAmount.setValue(new Integer(unitCount));
    			if(!isShowUnit){
    				this.tblBuiding.getColumn("isSelected").getStyleAttributes().setHided(true);
    				this.unitCount =0;
        			this.unitAmount.setValue(new Integer(0));
    			}
    			
    			btnCreateRoom.setVisible(true);
    			btnCreateRoom.setEnabled(true);
    			setTableCellIsChar();
    			exchangePanelAndMap();
    			
    }
    public void verifyRoomDes(RoomDesInfo roomDes,int i ){
    	if(null!=roomDes){
    		if(null==roomDes.getProductType()){
        		FDCMsgBox.showWarning("���䶨����Ϣ"+i+"��Ʒ���Ͳ���Ϊ�գ�");
        		SysUtil.abort();
        	}
        	if(null==roomDes.getBuildingProperty()){
        		FDCMsgBox.showWarning("���䶨����Ϣ"+i+"�������ʲ���Ϊ�գ�");
        		SysUtil.abort();
        	}
//        	if(null==roomDes.getRoomModel()){
//        		FDCMsgBox.showWarning("���䶨����Ϣ"+i+"���Ͳ���Ϊ�գ�");
//        		SysUtil.abort();
//        	}
    	}
    	
    	
    }
    
    public RoomDesInfo getRoomDesInfo(String panelName){
    	
    	RoomDesInfo roomDes =null;
    	for(int i = 0 ; i < kDTabbedPane1.getTabCount() ; i++){
    	
    			if (null != kDTabbedPane1.getComponentAt(i)) {
    				if (kDTabbedPane1.getComponentAt(i)instanceof KDPanel){
//					if (kDTabbedPane1.getComponentAt(i)instanceof CoreUIObject) {//���޸ļ���һ��KDPANEL����ȡ�����ò���coreUiobject��
						if(panelName.equals( kDTabbedPane1.getTitleAt(i).trim())){

							CoreUIObject  tempUI = (CoreUIObject)((KDPanel) this.kDTabbedPane1.getComponentAt(i)).getComponent(0);
							tempUI.storeFields();
							 roomDes = (RoomDesInfo) tempUI.getDataObject();
							
						}
					}
    			
    		}
    	}
    	
    	
    	return roomDes;
    }
    /**
     * 
     * @author tim_gao
     * @param billID  ���䶨��ID
     * @param unit    ��Ԫ
     * @param build   ¥��
     * @param fFrom   ¥�㿪ʼ
     * @param fTo     ¥�����
     * @param sFrom   ��ſ�ʼ
     * @param sTo     ��Ž���
     * @param isCharTo   �Ƿ�ת����ĸ
     * @param tempRoomDes   ��ʱ���� ������Ϣ
     * @return
     */
    public RoomDesInfo combineRoomDesInfo(String billID,BuildingUnitInfo unit,BuildingInfo build,
    		int fFrom,int fTo,int sFrom,int sTo,boolean isCharTo,RoomDesInfo tempRoomDes){
    	RoomDesInfo roomDes = null;
    	if(null!=tempRoomDes){
    		roomDes = (RoomDesInfo) tempRoomDes.clone();
    	}else{
    		roomDes = new RoomDesInfo();
    	}
    	
    	//ƴװ
    	//�̶�ֵ
    	
    	roomDes.setIsForSHE(true);
    	roomDes.setBizDate(SHEHelper.getCurrentTime());
    	//ID ����Ϊnull
    	if(null!=billID){
    		roomDes.setId(BOSUuid.read(billID));
    	}else{
    		roomDes.setId(null);
    	}
    	
    	//��Ԫ
    	roomDes.setUnit(unit);
    	if(null!= unit){
    		roomDes.setUnitBegin(unit.getSeq());
        	roomDes.setUnitEnd(unit.getSeq());
    	}
    	//¥��
    	roomDes.setBuilding(build);
    	//¥�㿪ʼ������
    	roomDes.setFloorBegin(fFrom);
    	roomDes.setFloorEnd(fTo);
    	//��ſ�ʼ������
    	
       	roomDes.setSerialNumberBegin(sFrom);
    	roomDes.setSerialNumberEnd(sTo);
    	//�Ƿ�ת����ĸ
    	roomDes.setIsConvertToChar(isCharTo);
    	return roomDes;
    }
    /**
     * 
     * @author tim_gao
     * @param row
     * @param roomDes<p> ���������tblBuiding ��ʱ���ϣ�1��ID��roomDes��װID 2û��ID��,Ϊ�в���ֵ��RoomDesInfo
     * @throws UIException
     */
    public void addRoomDesPanel(IRow row , RoomDesInfo roomDes) throws UIException{
		row.getCell("defineInfo").setValue(""+row.getRowIndex());	
		//���Ӷ���ҳǩ
		CoreUIObject detailUI =newPanelUI(""+row.getRowIndex(),roomDes);
		
		KDPanel unitPanel = new KDPanel();
		unitPanel.setLayout(new KDLayout());
		unitPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 996, 225));
		unitPanel.add(detailUI, new KDLayout.Constraints(10, 10, 986, 215, 384));
		unitPanel.setName(row.getRowIndex()  + "");
		
		this.kDTabbedPane1.add(unitPanel,row.getRowIndex());
    }
    public void delRoomDesPanel(IRow row) throws UIException{
    	String roomDesName = row.getCell("defineInfo").getValue().toString();	
		for (int n = 0; n <kDTabbedPane1.getTabCount(); n++) {
    	if (null != kDTabbedPane1.getComponentAt(n)) {
			if (kDTabbedPane1.getComponentAt(n)instanceof KDPanel) {
				KDPanel tempUI = (KDPanel)kDTabbedPane1.getComponentAt(n);
				if (roomDesName.equals(n+"")) {
					this.kDTabbedPane1.remove(tempUI);
				}
			}
    	}
		}
    }
    public void exchangePanelAndMap() {
    	if(this.tblBuiding.getRowCount()>0){
    		for (int i = 0; i < this.tblBuiding.getRowCount(); i++) {
    			//����ҳǩ
//    			int index =0;
    			IRow row = this.tblBuiding.getRow(i);
    			String roomDesNum = row.getCell("defineInfo").getValue().toString().trim();
    			String transName = ""+i;
    		
    			//ҳǩ����ͬ������
    				if (null != kDTabbedPane1.getComponentAt(i)) {
    					if (kDTabbedPane1.getComponentAt(i)instanceof KDPanel){
    						
//    						if (kDTabbedPane1.getComponentAt(i)instanceof CoreUIObject) {//���޸ļ���һ��KDPANEL����ȡ�����ò���coreUiobject��
    						((KDPanel) this.kDTabbedPane1.getComponentAt(i)).setName(transName);
  
    						 this.kDTabbedPane1.setTitleAt(i, transName);
    						}
    					}
    			
    			row.getCell("defineInfo").setValue(transName);
  					
    		}
    	}
		
		
	}
    
    
    public void initAllF7(){
    	//��Ұ 
    	EntityViewInfo viewEysSight = new EntityViewInfo();
    	FilterInfo filterEysSight = new FilterInfo();
    	filterEysSight.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
     	filterEysSight.getFilterItems().add(new FilterItemInfo("type.id","pj3Lru9+QVqK/PZoIaNxYLyNrUg="));
    	viewEysSight.setFilter(filterEysSight);
    	this.prmtEyeSignht.setEntityViewInfo(viewEysSight);
    	
    	//����
    	EntityViewInfo viewSight = new EntityViewInfo();
    	FilterInfo filterSight = new FilterInfo();
    	filterSight.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterSight.getFilterItems().add(new FilterItemInfo("type.id","aDya2NbITr+ymXXU7/kCW7yNrUg="));
    	viewSight.setFilter(filterSight);
       	this.prmtSight.setEntityViewInfo(viewSight);
    	
       	//����
    	EntityViewInfo viewNose = new EntityViewInfo();
    	FilterInfo filterNose = new FilterInfo();
    	filterNose.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterNose.getFilterItems().add(new FilterItemInfo("type.id","Cr7p6ri/QWWy+vdLTV1erLyNrUg="));
    	viewNose.setFilter(filterNose);
     	this.prmtNoise.setEntityViewInfo(viewNose);
    	
     	//�����ṹ
    	EntityViewInfo viewBuilStruct = new EntityViewInfo();
    	FilterInfo filterBuilStruct = new FilterInfo();
    	filterBuilStruct.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	viewBuilStruct.setFilter(filterBuilStruct);
     	this.prmtBuilStruct.setEntityViewInfo(viewBuilStruct);
    	
     	//װ�ޱ�׼
    	EntityViewInfo viewDecoraStd = new EntityViewInfo();
    	FilterInfo filterDecoraStd = new FilterInfo();
    	filterDecoraStd.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterDecoraStd.getFilterItems().add(new FilterItemInfo("type.id","YPBFahQ6TY+RUDdxyiElfryNrUg="));
    	viewDecoraStd.setFilter(filterDecoraStd);
    	this.prmtDecoraStd.setEntityViewInfo(viewDecoraStd);
    
//    	this.prmtRoomModel.setEntityViewInfo(view);
    	
    	
      
//      	this.prmtRoomModel.setEntityViewInfo(view);
    	//������׼
    	EntityViewInfo viewPossStd = new EntityViewInfo();
    	FilterInfo filterPossStd  = new FilterInfo();
    	filterPossStd .getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterPossStd.getFilterItems().add(new FilterItemInfo("type.id","ByHexvLVSEusji3qeetJHLyNrUg="));
    	viewPossStd .setFilter(filterPossStd );
      	this.prmtPosseStd.setEntityViewInfo(viewPossStd);
   
     	//������;
    	EntityViewInfo viewRoomUsage = new EntityViewInfo();
    	FilterInfo filterRoomUsage = new FilterInfo();
    	filterRoomUsage.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterRoomUsage.getFilterItems().add(new FilterItemInfo("type.id","zzYdGSPQSuWiAJXK3JKjcbyNrUg="));
    	viewRoomUsage.setFilter(filterRoomUsage);
      	this.prmtRoomUsage.setEntityViewInfo(viewRoomUsage);

    	//��Ч 
    	EntityViewInfo view =new EntityViewInfo();
    	FilterInfo filter =new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	view.setFilter(filter);
    	this.prmtDirection.setEntityViewInfo(view);
    	//¥������
 
    	this.prmtBuildingProperty.setEntityViewInfo(view);
    	
    	//��Ʒ����
    	
    	this.prmtProductType.setEntityViewInfo(view);
      	
    }
    /**
     *@description ��Ϊÿ�ζ�������������������Ժ�����Ϣ�����ض�����Ϣ
     *@author tim_gao
     *
     */
    
    public void loadRoomDesBaseData(RoomDesCollection roomDesCol){
    	if(null!=roomDesCol&&roomDesCol.size()>0){
    		RoomDesInfo baseRoomDes = roomDesCol.get(0);
    		this.prmtProductType.setValue(baseRoomDes.getProductType());
    		this.prmtBuildingProperty.setValue(baseRoomDes.getBuildingProperty());
    		this.prmtDirection.setValue(baseRoomDes.getDirection());
    		this.prmtEyeSignht.setValue(baseRoomDes.getNewEyeSight());
//    		this.prmtRoomModel.setValue(baseRoomDes.getRoomModel());
    		this.prmtPosseStd.setValue(baseRoomDes.getNewPossstd());
    		this.prmtSight.setValue(baseRoomDes.getNewSight());
    		this.prmtRoomUsage.setValue(baseRoomDes.getNewRoomUsage());
    		this.prmtDecoraStd.setValue(baseRoomDes.getNewDecorastd());
    		this.prmtNoise.setValue(baseRoomDes.getNewNoise());
    		this.txtBuildingArea.setValue(baseRoomDes.getBuildingArea());
    		this.txtRoomArea.setValue(baseRoomDes.getRoomArea());
    		this.txtBalconyArea.setValue(baseRoomDes.getBalconyArea());
    		this.txtUseArea.setValue(baseRoomDes.getUserArea());
    		this.txtActualBuildingArea.setValue(baseRoomDes.getActualBuildingArea());
    		this.txtActualRoomArea.setValue(baseRoomDes.getActualRoomArea());
    		this.txtGuardenArea.setValue(baseRoomDes.getGuardenArea());
    		this.txtPlanBuildingArea.setValue(baseRoomDes.getPlanBuildingArea());
    		this.txtPlanRoomArea.setValue(baseRoomDes.getPlanRoomArea());
    		this.txtCarbarnArea.setValue(baseRoomDes.getCarbarnArea());
    		this.txtFlatArea.setValue(baseRoomDes.getFlatArea());
    		this.prmtBuilStruct.setValue(baseRoomDes.getBuildStruct());
    		this.txtIbaInnside.setValue(baseRoomDes.getIbaInnside());   //add by shilei
    		this.txtIbameasured.setValue(baseRoomDes.getIbameasured());
    		this.txtIbasement.setValue(baseRoomDes.getIbasement());
    		this.txtinnside.setValue(baseRoomDes.getInsside());
    	
    	}
    }
    /**
     * 
     * @author tim_gao
     */
    public void setTxtPrecistion(){
    	
		this.txtUseArea.setRemoveingZeroInDispaly(false);
		this.txtUseArea.setRemoveingZeroInEdit(false);
		this.txtUseArea.setNegatived(false);
		this.txtUseArea.setPrecision(3);
		this.txtUseArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtUseArea.setSupportedEmpty(true);
		
		this.txtPlanRoomArea.setRemoveingZeroInDispaly(false);
		this.txtPlanRoomArea.setRemoveingZeroInEdit(false);
		this.txtPlanRoomArea.setNegatived(false);
		this.txtPlanRoomArea.setPrecision(3);
		this.txtPlanRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtPlanRoomArea.setSupportedEmpty(true);
		
		this.txtRoomArea.setRemoveingZeroInDispaly(false);
		this.txtRoomArea.setRemoveingZeroInEdit(false);
		this.txtRoomArea.setNegatived(false);
		this.txtRoomArea.setPrecision(3);
		this.txtRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRoomArea.setSupportedEmpty(true);
		
		this.txtPlanBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtPlanBuildingArea.setRemoveingZeroInEdit(false);
		this.txtPlanBuildingArea.setNegatived(false);
		this.txtPlanBuildingArea.setPrecision(3);
		this.txtPlanBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtPlanBuildingArea.setSupportedEmpty(true);
		
		this.txtGuardenArea.setRemoveingZeroInDispaly(false);
		this.txtGuardenArea.setRemoveingZeroInEdit(false);
		this.txtGuardenArea.setNegatived(false);
		this.txtGuardenArea.setPrecision(3);
		this.txtGuardenArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtGuardenArea.setSupportedEmpty(true);
		
		this.txtFlatArea.setRemoveingZeroInDispaly(false);
		this.txtFlatArea.setRemoveingZeroInEdit(false);
		this.txtFlatArea.setNegatived(false);
		this.txtFlatArea.setPrecision(3);
		this.txtFlatArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFlatArea.setSupportedEmpty(true);
		
		this.txtCarbarnArea.setRemoveingZeroInDispaly(false);
		this.txtCarbarnArea.setRemoveingZeroInEdit(false);
		this.txtCarbarnArea.setNegatived(false);
		this.txtCarbarnArea.setPrecision(3);
		this.txtCarbarnArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtCarbarnArea.setSupportedEmpty(true);
		
		this.txtBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtBuildingArea.setRemoveingZeroInEdit(false);
		this.txtBuildingArea.setNegatived(false);
		this.txtBuildingArea.setPrecision(3);
		this.txtBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingArea.setSupportedEmpty(true);
		
		this.txtBalconyArea.setRemoveingZeroInDispaly(false);
		this.txtBalconyArea.setRemoveingZeroInEdit(false);
		this.txtBalconyArea.setNegatived(false);
		this.txtBalconyArea.setPrecision(3);
		this.txtBalconyArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBalconyArea.setSupportedEmpty(true);
		
		this.txtActualRoomArea.setRemoveingZeroInDispaly(false);
		this.txtActualRoomArea.setRemoveingZeroInEdit(false);
		this.txtActualRoomArea.setNegatived(false);
		this.txtActualRoomArea.setPrecision(3);
		this.txtActualRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActualRoomArea.setSupportedEmpty(true);
		
		this.txtActualBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtActualBuildingArea.setRemoveingZeroInEdit(false);
		this.txtActualBuildingArea.setNegatived(false);
		this.txtActualBuildingArea.setPrecision(3);
		this.txtActualBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActualBuildingArea.setSupportedEmpty(true);
		
		this.txtIbasement.setRemoveingZeroInDispaly(false); //add by shilei
		this.txtIbasement.setRemoveingZeroInEdit(false);
		this.txtIbasement.setNegatived(false);
		this.txtIbasement.setPrecision(3);
		this.txtIbasement.setHorizontalAlignment(JTextField.RIGHT);
		this.txtIbasement.setSupportedEmpty(true);
		
		this.txtIbaInnside.setRemoveingZeroInDispaly(false); //add by shilei
		this.txtIbaInnside.setRemoveingZeroInEdit(false);
		this.txtIbaInnside.setNegatived(false);
		this.txtIbaInnside.setPrecision(3);
		this.txtIbaInnside.setHorizontalAlignment(JTextField.RIGHT);
		this.txtIbaInnside.setSupportedEmpty(true);
		
		this.txtIbameasured.setRemoveingZeroInDispaly(false); //add by shilei
		this.txtIbameasured.setRemoveingZeroInEdit(false);
		this.txtIbameasured.setNegatived(false);
		this.txtIbameasured.setPrecision(3);
		this.txtIbameasured.setHorizontalAlignment(JTextField.RIGHT);
		this.txtIbameasured.setSupportedEmpty(true);
		
		this.txtinnside.setRemoveingZeroInDispaly(false); //add by shilei
		this.txtinnside.setRemoveingZeroInEdit(false);
		this.txtinnside.setNegatived(false);
		this.txtinnside.setPrecision(3);
		this.txtinnside.setHorizontalAlignment(JTextField.RIGHT);
		this.txtinnside.setSupportedEmpty(true);
    }
    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

  

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    public void setTableCellIsChar(){
    	if( null!=this.tblBuiding.getRow(0)){
    		Boolean isChar = (Boolean) this.tblBuiding.getRow(0).getCell("isConvertToChar").getValue();
        	for(int i = 0 ; i <this.tblBuiding.getRowCount() ; i++){
        		IRow row = this.tblBuiding.getRow(i);
        		row.getCell("isConvertToChar").setValue(isChar);
        	}
        	this.tblBuiding.getMergeManager().mergeBlock(0, 2, this.tblBuiding.getRowCount() -1,  3, KDTMergeManager.FREE_ROW_MERGE);
    		this.tblBuiding.getMergeManager().mergeBlock(0, 9, this.tblBuiding.getRowCount() -1,  9, KDTMergeManager.FREE_ROW_MERGE);

    		this.tblBuiding.getMergeManager().setDataMode(KDTMergeManager.DATA_KEEPFIRST);
    	}
    	
	
    }
    /**
     *  @description �޷���������ѭ���ύ����ĺ��鷳��
     *  ԭ���ĵ����� RoomDesEditUI �ǿ���������ַ�ʽ���ɵ�
     *  ���ڵ�������Ϊ�Ǽ����ɵ�Ԫ�ִ���¥�㣬���Ը����һ��roomdes �����Ӧһ����Ԫ���������
     *  ***@process ÿ���ύ���Ƚ�������û�е��ύ�����µģ��е��޸�
     *  			��ȡ�ã�¥�������еĵ�Ԫ��Ϣ���Ӧ���䶨����Ϣ C��
     *  			����ȡ����ϢC�뱾���ύ��Ϣ�ĶԱ�A��ע�⣺��ȡ�����е�C�뱾�εĶԱ�A�����Ǳ��ε�A�Ա������е�C����������һ�������ܴ�
     *  			(A���� C ����)			
     *  			1.�����A����C �򲻼�
     *  			2.�����A��û��C����ɾ��C�е�û�е����ݣ���ôд��ĺ����ƣ�
     *  ***@remark 1.��ô������Ϊ�µ�һ�ζ�����4����Ԫ�������޸ĺ������ύΪ3����Ԫ�����ݣ���ô����һ����Ԫ�˷ѵ���û�����ݣ�
     *  			����֮ǰ�У��������ڲ���Ҫ����Ȼû�ж�����Ϣ������Ԫ�����ڣ���Ԫû�еط�ά������������
     *            2.��Ϊ��Ԫһ��һ����û��ID�����жϵ�Ԫ��û�в�����ID��Ҫ��seq
     *
     *�� �������ɰ�ť�ϲ���
     *
     */
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {	
    	this.tblBuiding.checkParsed();
    	if(	this.tblBuiding.getRowCount()<=0){
    		FDCMsgBox.showWarning("�붨�巿����Ϣ��");
    		SysUtil.abort();
    	}
    	verifyInput(e);
    	
    	Boolean isChar = (Boolean) this.tblBuiding.getRow(0).getCell("isConvertToChar").getValue();
    	
    	//¥����Ϣ
    	BuildingInfo building = (BuildingInfo)this.prmtBuilding.getValue();
    	Integer zero = new Integer(0);
    	Integer one= new Integer(1);
    	boolean flag=false;
    	if(null!=building ){
    		
    		if(RoomFactory.getRemoteInstance().exists("where building.id ='"+building.getId().toString()+"' and sellState != 'Init' ")) {
    			MsgBox.showInfo("¥�����з����Ѿ����̻�������,���ܶ��巿�䣡");
    			SysUtil.abort();
    		}
    	}	else{
			FDCMsgBox.showWarning("¥����Ϣ����Ϊ�գ�");
			SysUtil.abort();
		}
    		
    	
    	//�ý��ĵ�Ԫ��Ϣ
    	BuildingUnitCollection buildingUnitCol = new BuildingUnitCollection();
    	//�ý��ķ��䶨����Ϣ
    	RoomDesCollection roomDesCol = new RoomDesCollection();
    	if(null!=building ){
    		this.storeFields();
    		//�ȷ�װ�ý����ϵĵ�Ԫ��Ϣ���װ���䶨����Ϣ
    		for(int i = 0 ; i <this.tblBuiding.getRowCount() ; i++){
    			IRow row = this.tblBuiding.getRow(i);
    			String unitName = (String)row.getCell("unitName").getValue();
    			Integer unitSeq = new Integer(row.getCell("unitSeq").getValue().toString());
    			Integer floorFrom = new Integer(row.getCell("floorFrom").getValue().toString());
    			Integer floorTo = new Integer(row.getCell("floorTo").getValue().toString());
    			Integer serialNumFrom = new Integer(row.getCell("serialNumFrom").getValue().toString());
    			Integer serialNumTo = new Integer(row.getCell("serialNumTo").getValue().toString());
//    			Boolean isConvertToChar = (Boolean)row.getCell("isConvertToChar").getValue();
    			String unitId = (String)row.getCell("unitId").getValue();
    			String roomDesId = (String)row.getCell("billId").getValue();
    			
    			
    			//У��һ��
    			//��Ų�Ϊ0
    			int v =i+1;
    			if(zero.intValue()==serialNumFrom.intValue()){
//    				row.getCell("serialNumFrom").setValue(one);
    				
    				FDCMsgBox.showWarning("��"+v+"�У���ʼ ��Ų���Ϊ0��");
    				SysUtil.abort();
    		
    			}
//    			if(null==row.getCell("roomModel").getValue()||("").equals(row.getCell("roomModel").getValue())){
////    				row.getCell("serialNumTo").setValue(one);
//    				FDCMsgBox.showWarning("��"+v+"�У����Ͳ���Ϊ�գ�");
//    				SysUtil.abort();
//    			}
    			
    			
    			if(zero.intValue()==serialNumTo.intValue()){
//    				row.getCell("serialNumTo").setValue(one);
    				FDCMsgBox.showWarning("��"+v+"�У� ������Ų���Ϊ0��");
    				SysUtil.abort();
    			}	
    			
    	    		//�Ա�У��,ת��int�Ƚ�
    	    		if(floorFrom.intValue()>
    	    		floorTo.intValue()){
    	    			FDCMsgBox.showError("��"+v+"�У���ʼ¥�㲻�ܴ��ڽ���¥�㣡");
    	    			SysUtil.abort();
    	    		}
    	    		if(serialNumFrom.intValue()>
    	    		serialNumTo.intValue()){
    	    			FDCMsgBox.showError("��"+v+"�У���ʼ��Ų��ܴ��ڽ�����ţ�");
    	    			SysUtil.abort();
    	    		}
    			BuildingUnitInfo unit = new BuildingUnitInfo();
    			unit.setName(unitName);
    			unit.setSeq(unitSeq.intValue());
    			//����Ԫ��Ϊ0ʱ ����ʾ
    			if(0==((Integer)this.unitAmount.getValue()).intValue()){
    				//(false��ʾ��true����ʾ)
    				unit.setHaveUnit(true);
    			}else{
    				unit.setHaveUnit(false);
    			}
    			//����Ԫ����Ϊ0��ʱ�� 
    			
    			if(null!=unitId){
    				unit.setId(BOSUuid.read(unitId));
    			}
    			//���ݷ��䶨��������ҳǩ��editData
    			
    			//ƴװ��Ϣ
    			
    			//���䶨����Ϣ��һ��Ҫ����� 
    			RoomDesInfo roomDesData = getRoomDesInfo(""+i);
    			 verifyRoomDes(roomDesData,i );
    			roomDesCol.add(combineRoomDesInfo(roomDesId,unit,building,
    					floorFrom.intValue(),floorTo.intValue(),serialNumFrom.intValue(),serialNumTo.intValue(),
    					isChar.booleanValue(),roomDesData));
//    			roomDesCol.add(roomDesInfo);
    			if(null==buildingUnitCol||buildingUnitCol.size()==0){//��һ��
    				unit.setBuilding(building);
    				buildingUnitCol.add(unit);
    			}else{//�Ա��Ƿ��ѷ��뼯��,��Ϊ���ںϵ�
    				boolean isAdd = true;
    				for(int u = 0 ; u<buildingUnitCol.size() ;u++){
    					BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo)buildingUnitCol.get(u);
    					if(buildUnitInfo.getSeq()==unitSeq.intValue()){//���ظ��ľͲ������
    						isAdd = false;
    					}
    				}
    				
    				if(isAdd){
    					unit.setBuilding(building);
    					buildingUnitCol.add(unit);
    				}
    			}
    			
    			
    		}
    	
    		if(null==buildingUnitCol||buildingUnitCol.size()<=0||roomDesCol==null||roomDesCol.size()<0){
    			FDCMsgBox.showWarning("�޶�����Ϣ��");
    			SysUtil.abort();
    		}
    		boolean isSumbit = ((IRoomDes)this.getBizInterface()).roomDesSumbit(building, roomDesCol, buildingUnitCol);
    		if(isSumbit){
    			FDCMsgBox.showWarning("������Ϣ����ɹ���");
    			
        	}else{
    			FDCMsgBox.showWarning("������Ϣ����ʧ�ܣ�");
    			SysUtil.abort();
    		}
    		
    	}
    	 
    	
    	this.btnSubmit.setEnabled(false);
    	this.btnBuildingClearedUp.setEnabled(false);
    	this.tblBuiding.setEnabled(false);
    	this.btnAllSelected.setEnabled(false);
    	this.btnAllDrop.setEnabled(false);
    	this.btnAddLineRoomDes.setEnabled(false);
    	this.btnDelLineRoomDes.setEnabled(false);
    
//    	//��Ԫ������
//		EntityViewInfo view  = new EntityViewInfo();
//		view.setFilter(new FilterInfo());
////		view.getFilter().getFilterItems().add(new FilterItemInfo("name",row.getCell("unitName").getValue()!=null?row.getCell("unitName").getValue().toString():null));
////		view.getFilter().getFilterItems().add(new FilterItemInfo("seq",row.getCell("unitSeq").getValue()!=null?new Integer(row.getCell("unitSeq").getValue().toString()):null));
//		view.getFilter().getFilterItems().add(new FilterItemInfo("building",((BuildingInfo)this.prmtBuilding.getValue()).getId().toString()));
//		SorterItemInfo sort = new SorterItemInfo("seq");
//		sort.setSortType(SortType.ASCEND);
//		view.getSorter().add(sort);
//		//�鿴��Ԫ�Ƿ����
//		BuildingUnitCollection getbuildUnitCol = BuildingUnitFactory.getRemoteInstance().getBuildingUnitCollection(view);
//		
//    	BuildingUnitCollection buildingUnitCol = new BuildingUnitCollection();
//    	RoomDesCollection roomDesCol = new RoomDesCollection();
//    	BuildingInfo builidng = (BuildingInfo) this.prmtBuilding.getValue();
//    	int temp = 0;
//    	Integer zero = new Integer(0);
//    	Integer one= new Integer(1);
//    	for(int i =0 ; i < this.tblBuiding.getRowCount() ; i++){
//    		RoomDesInfo roomDes = new RoomDesInfo();
//    		IRow row = this.tblBuiding.getRow(i);
//    		//����0
//    		
//    		if(null==row.getCell("floorFrom").getValue()){
//			row.getCell("floorFrom").setValue(zero);
//		}
//		if(null==row.getCell("floorTo").getValue()){
//			row.getCell("floorTo").setValue(zero);
//		}
//		if(null==row.getCell("serialNumFrom").getValue()){
//			row.getCell("serialNumFrom").setValue(zero);
//		}
//		if(null==row.getCell("serialNumTo").getValue()){
//			row.getCell("serialNumTo").setValue(zero);
//		}
//    	
//		//��Ų�Ϊ0
//		temp = new Integer(row.getCell("serialNumFrom").getValue().toString()).intValue();
//		if(zero.intValue()==temp){
//			row.getCell("serialNumFrom").setValue(one);
//			FDCMsgBox.showWarning("��"+i+"�У���ʼ ��Ų���Ϊ0��");
//			SysUtil.abort();
//	
//		}
//		temp = new Integer(row.getCell("serialNumTo").getValue().toString()).intValue();
//		if(null==row.getCell("serialNumTo").getValue()){
//			row.getCell("serialNumTo").setValue(one);
//			FDCMsgBox.showWarning("��"+i+"�У� ������Ų���Ϊ0��");
//			SysUtil.abort();
//		}	
//		
//    		//�Ա�У��,ת��int�Ƚ�
//    		if(((Integer)row.getCell("floorFrom").getValue()).intValue()>
//    			((Integer)row.getCell("floorTo").getValue()).intValue()){
//    			FDCMsgBox.showError("��"+i+"�У���ʼ¥�㲻�ܴ��ڽ���¥�㣡");
//    			SysUtil.abort();
//    		}
//    		if(((Integer)row.getCell("serialNumFrom").getValue()).intValue()>
//    			((Integer)row.getCell("serialNumTo").getValue()).intValue()){
//    			FDCMsgBox.showError("��"+i+"�У���ʼ��Ų��ܴ��ڽ�����ţ�");
//    			SysUtil.abort();
//    		}
//    		
//    		//�õ�UnitCol
//    		BuildingUnitInfo buildingUnit = new BuildingUnitInfo();
//    		buildingUnit.setSeq(new Integer(row.getCell("unitSeq").getValue().toString()).intValue());
//    		buildingUnit.setName(row.getCell("unitName").getValue().toString());
//    		buildingUnit.setBuilding(builidng);
//    		//�鿴��Ԫ�Ƿ����
//    			if(null==getbuildUnitCol&&getbuildUnitCol.size()<=0){
//    				IObjectPK pkId = BuildingUnitFactory.getRemoteInstance().submit(buildingUnit);
//    				
//            		buildingUnit.setId(BOSUuid.read(pkId.toString()));
//            		
//    			}else{
//    				Iterator it = getbuildUnitCol.iterator();
//    				while(it.hasNext()){
//    					BuildingUnitInfo unit = (BuildingUnitInfo) it.next();
//    					if(unit.getSeq()==buildingUnit.getSeq()){
//    						buildingUnit.setId(BOSUuid.read(unit.getId().toString()));
//    					}
//    				}
////    				for(int m = 0 ; m<buildingUnitCol.size() ; m++){
////    					BuildingUnitInfo unit = buildingUnitCol.get(m);
////    					if(buildingUnit.getSeq()){
////    						if(row.getCell("unitName").getValue().toString().equals(unit.getName())&&
////    								new Integer(row.getCell("seq").getValue().toString()).intValue()==unit.getSeq()){
////    									buildingUnit.setId(BOSUuid.read(unit.getId().toString()));
////        					}
////    					}
////    				}
//    				
//    			}
//    		//���뷿�ݶ�����
//    		roomDes.setUnit(buildingUnit);
//    		
//    		//�������ɵ�����Ԫ�ģ���Ӧһ��roomdes
//    		int unit = new Integer( row.getCell("unitSeq").getValue().toString()).intValue();
//    		roomDes.setUnitBegin(unit);
//    		roomDes.setUnitEnd(unit);
//    		roomDes.setFloorBegin(new Integer(row.getCell("floorFrom").getValue().toString()).intValue());
//    		roomDes.setFloorEnd(new Integer(row.getCell("floorTo").getValue().toString()).intValue());
//    		roomDes.setSerialNumberBegin(new Integer(row.getCell("serialNumFrom").getValue().toString()).intValue());
//    		roomDes.setSerialNumberEnd(new Integer(row.getCell("serialNumTo").getValue().toString()).intValue());
//    		roomDes.setIsConvertToChar(new Boolean(row.getCell("isConvertToChar").getValue().toString()).booleanValue());
//    		//roomdes Ϊ��¥����
//    		roomDes.setIsForSHE(true);
//    		
//    		
//    		roomDes.setBuilding(builidng);
//    		roomDesCol.add(roomDes);
//    	}
//    	
//    		
//    	//���ύ
//    		//���·�¼�����ݵ�������
//    		for(int i = 0 ; i < roomDesCol.size() ; i++){
//    				this.editData = roomDesCol.get(i);
//    			((IRoomDes)this.getBizInterface()).submit(this.editData);
//    		}
//    		
//    		
//    		
//    	this.destroyWindow();
//        super.actionSubmit_actionPerformed(e);
    	
    	
}

  
    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

   
	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return RoomDesFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}

	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return null;
	}
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		
	}
	

	
//  protected void unitAmount_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
//  {
//	  	IRow row = this.tblBuiding.addRow();
//	  	row.getCell("isConvertToChar").setValue(Boolean.FALSE);
//		row.getCell("isSelected").setValue(Boolean.FALSE);
//		row.getCell("unitSeq").setValue(new Integer(1));
//		row.getCell("unitName").setValue("��Ԫ");
////		if(new Integer(this.unitAmount.getValue().toString()).intValue()==2){
//			int top =0;
//			int bottom=0;
//			int left =0;
//			int right = 0;
//			IRow row1= this.tblBuiding.getRow(0);
////			this.tblBuiding.getStyleAttributes().setLocked(true);
////			this.tblBuiding.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
////			this.tblBuiding.getIndexColumn().getStyleAttributes().setHided(true);
////			this.tblBuiding.getStyleAttributes().setWrapText(true);
////			this.tblBuiding.setHorizonGridLineVisible(true);
////			this.tblBuiding.setVerticalGridLineVisible(true);
////			this.tblBuiding.setVerticalHeadGridLineVisible(true);
////			this.tblBuiding.setHorizonHeadGridLineVisible(true);
////			row1.getCell(1).getKDTCell().getMergeBlock().getTop();
////			KDTMergeBlock block1 = this.tblBuiding.getMergeManager().getMergeBlockOfCell(1, 1);
////			KDTMergeBlock block
//			
////			top=block.getTop();
////			left = block.getLeft();
////			right = block.getRight();
////			bottom= block1.getBottom();
//			
////		}
//		
//  }

	 /**
     * output unitAmount_stateChanged method
     */
    protected void unitAmount_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {	
    	this.tblBuiding.checkParsed();
    	Integer one = new Integer(1);
    	int units = ((Integer)this.unitAmount.getValue()).intValue();
    	this.tblBuiding.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
        	if(units==this.unitCount){
        			return;
        		}else if(units>this.unitCount) {
        			if(0==unitCount&&this.tblBuiding.getRowCount()>0){//Ϊ0���ת��
        	    		this.tblBuiding.getColumn("isSelected").getStyleAttributes().setHided(false);
        	    		IRow row = this.tblBuiding.getRow(0);
        	    		row.getCell("unitSeq").getStyleAttributes().setFontColor(Color.BLACK);
        	    		row.getCell("unitName").getStyleAttributes().setFontColor(Color.BLACK);
        	    		row.getCell("unitSeq").getStyleAttributes().setBackground(Color.WHITE);
        	    		row.getCell("unitName").getStyleAttributes().setBackground(Color.WHITE);
        	    	}else{
        	    		IRow row = this.tblBuiding.addRow();
        	        	
            			//����һ��Ҫ�½���������ֻnew һ��kdspinnerCelleditor ÿ��CELL�����һ��kdspinnerCelleditor ���ڴ�
                		//�������ÿ��cell�����ô�����ͬ��ֵ��
//            			KDTDefaultCellEditor spinnerCellFloorFrom = new KDTDefaultCellEditor(new KDNumberTextField());
//                		KDTDefaultCellEditor spinnerCellFloorTo = new KDTDefaultCellEditor(new KDNumberTextField());
//                		KDTDefaultCellEditor spinnerCellSeqFrom = new KDTDefaultCellEditor(new KDNumberTextField());
//                		KDTDefaultCellEditor spinnerCellSeqTo = new KDTDefaultCellEditor(new KDNumberTextField());
//                		row.getCell("roomModel").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery", SHEHelper.getModelBySellProjectView(sellPro)));
//             	       KDSpinnerCellEditor spinnerCellFloorFrom = new KDSpinnerCellEditor(new SpinnerNumberModel(1,-999,
//             	    		  999,1));
//             	       KDSpinnerCellEditor spinnerCellFloorTo = new KDSpinnerCellEditor(new SpinnerNumberModel(1,-999,
//             	    		 999,1));
//             	       KDSpinnerCellEditor spinnerCellSeqFrom = new KDSpinnerCellEditor(new SpinnerNumberModel(1,0,9999,1));
//             	      KDSpinnerCellEditor spinnerCellSeqTo = new KDSpinnerCellEditor(new SpinnerNumberModel(1,0,9999,1));
////             	 
                		//�¼�ҳǩ
                		//ÿ�ζ����ص�һ�θ������ҳǩ
            	  		RoomDesInfo roomDesBase = this.getRoomDesInfo(""+0);
            	  		if(null==roomDesBase){
            	  			roomDesBase = this.editData;
            	  		}
        	    		addRoomDesPanel(row,roomDesBase);
             	     
            			//������
                		if(null==row.getCell("floorFrom").getValue()){
                			row.getCell("floorFrom").setValue(one);
//                			row.getCell("floorFrom").setEditor(spinnerCellFloorFrom);
                		}
                		if(null==row.getCell("floorTo").getValue()){
                			row.getCell("floorTo").setValue(one);
//                			row.getCell("floorTo").setEditor(spinnerCellFloorTo);
                		}
                		if(null==row.getCell("serialNumFrom").getValue()){
                			row.getCell("serialNumFrom").setValue(one);
//                			row.getCell("serialNumFrom").setEditor(spinnerCellSeqFrom);
                		}
                		if(null==row.getCell("serialNumTo").getValue()){
                			row.getCell("serialNumTo").setValue(one);
//                			row.getCell("serialNumTo").setEditor(spinnerCellSeqTo);
                		}
            			row.getCell("isConvertToChar").setValue(Boolean.FALSE);
            			row.getCell("isSelected").setValue(Boolean.FALSE);
            			row.getCell("unitSeq").setValue(new Integer(units));
//            			row.getCell("unitName").setValue("��Ԫ");
            			row.getCell("unitName").setValue(units+"��Ԫ");
            			
            			row.getCell("unitName").getStyleAttributes().setLocked(false);
        	    	}
        			
        			
        		}
        		else if (units<this.unitCount){
        			for(int i = this.tblBuiding.getRowCount() ; i >0; i--){
        				IRow row = this.tblBuiding.getRow(i-1);
        				if(this.unitCount == new Integer(row.getCell("unitSeq").getValue().toString()).intValue()){
        					this.tblBuiding.removeRow(i-1);
        					//ɾ��ҳǩ
        					delRoomDesPanel(row);
        				}
        			}
        		}
    		
    
    		
    		
    		
    		this.unitCount=units;
    		
    		setTableCellIsChar();
    		exchangePanelAndMap();
    		super.unitAmount_stateChanged(e);
     }
    
    
    /**
     * output actionCreateRoom_actionPerformed method
     */
    public void actionCreateRoom_actionPerformed(ActionEvent e) throws Exception{
    	
    	//�ϲ��˰�ť���������涯��
    	actionSubmit_actionPerformed(e);
    	
    	
    	
//    	TreeModel treeMain = FDCTreeHelper.getUnitTreeForSHE(actionOnLoad, MoneySysTypeEnum.SalehouseSys);
    	KDTree treeMain = new KDTree();
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)this.getUIContext().get("node");
    		
    	treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(actionOnLoad,MoneySysTypeEnum.SalehouseSys));
    	Map idNodeMap = FDCTreeHelper.getAllObjectIdMap((TreeNode)treeMain.getModel().getRoot(),"Building");
    	String id = ((BuildingInfo)node.getUserObject()).getId().toString();
    	node = (DefaultKingdeeTreeNode)idNodeMap.get(id);
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("building",((BuildingInfo)node.getUserObject()).getId().toString()));
    	
    	if(!((IRoomDes)this.getBizInterface()).exists(filter)){
    		FDCMsgBox.showWarning("�뱣�淿�䶨����Ϣ�����ɷ��䣡");
    		SysUtil.abort();
    	}
	if(node ==null){
		FDCMsgBox.showWarning("��ѡ��¥����");
		SysUtil.abort();
	}
	if (node != null && node.getUserObject() instanceof BuildingInfo) {
		BuildingInfo building = (BuildingInfo) node.getUserObject();
		RoomCollection rooms2 = SHEHelper.getRooms(building);
		for(int i=0;i<rooms2.size();i++)
		{
			RoomInfo room = (RoomInfo)rooms2.get(i);
			if(room.isIsPre() || room.isIsActualAreaAudited()||room.isIsPlan())
			{
				MsgBox.showInfo("�����Ѹ��ˣ������������ɷ���!");
				this.abort();
			}
		}
		RoomCollection rooms = SHEHelper.getRoomsByDesForCreate(building.getId()
				.toString());
		if(rooms.size()==0){
			return;
		}
		
		this.initOldData(editData);
		this.destroyWindow();
		RoomNewCreateUI.showWindows(this, rooms,node,Boolean.TRUE);
//		RoomCreateUI.showWindows(this, rooms);
	}
    }
 
  public boolean checkRowNullAndInteger(IRow row,String title){
	  boolean flag = false;
	 if(null!=title){
		if(null== row.getCell(title).getValue()){
			flag = true;
			return flag;
		}else if("".equals(row.getCell(title).getValue())){
			flag =true;
		}
	 }
	 return flag;
  }
    /**
     *@author tim_gao
     *@description У���¼floorFrom,floorTo;serialNumFrom,serialNumTo
     *@date 2011-06-28
     */
    
    protected void verifyTblBuilidngEntry(){
    	int temp = 0;
    	Integer zero = new Integer(0);
    	Integer one = new Integer(1);
    	Boolean isChart = Boolean.FALSE;
    	for (int i = 0; i < this.tblBuiding.getRowCount(); i++) {
			IRow row = this.tblBuiding.getRow(i);
			// ����0
			int v = i + 1;
			if (checkRowNullAndInteger(row, "floorFrom")) {
				row.getCell("floorFrom").setValue(zero);
			}
			if (checkRowNullAndInteger(row, "floorTo")) {
				row.getCell("floorTo").setValue(zero);
			}
			if (checkRowNullAndInteger(row, "serialNumFrom")) {
				row.getCell("serialNumFrom").setValue(one);
			}
			if (checkRowNullAndInteger(row, "serialNumTo")) {
				row.getCell("serialNumTo").setValue(one);
			}
			// ��Ų�Ϊ0
			temp = new Integer(row.getCell("serialNumFrom").getValue().toString()).intValue();
			if (zero.intValue() == temp) {
				row.getCell("serialNumFrom").setValue(one);
				FDCMsgBox.showWarning("��" + v + "�У���ʼ ��Ų���Ϊ0��");
				SysUtil.abort();

			}
			temp = new Integer(row.getCell("serialNumTo").getValue().toString()).intValue();
			if (null == row.getCell("serialNumTo").getValue()) {
				row.getCell("serialNumTo").setValue(one);
				FDCMsgBox.showWarning("��" + v + "�У� ������Ų���Ϊ0��");
				SysUtil.abort();
			}
			if (Boolean.TRUE.equals((Boolean) row.getCell("isConvertToChar").getValue())) {
				isChart = Boolean.TRUE;
			}

			// //�Ա�У��,ת��int�Ƚ�
			// if(((Integer)row.getCell("floorFrom").getValue()).intValue()>
			// ((Integer)row.getCell("floorTo").getValue()).intValue()){
			// FDCMsgBox.showError("��"+i+"�У���ʼ¥�㲻�ܴ��ڽ���¥�㣡");
			// row.getCell("floorTo").setValue(one);
			// row.getCell("floorFrom").setValue(one);
			// SysUtil.abort();
			// }
			// if(((Integer)row.getCell("serialNumFrom").getValue()).intValue()>
			// ((Integer)row.getCell("serialNumTo").getValue()).intValue()){
			// FDCMsgBox.showError("��"+i+"�У���ʼ��Ų��ܴ��ڽ�����ţ�");
			// row.getCell("serialNumFrom").setValue(one);
			// row.getCell("serialNumTo").setValue(one);
			// SysUtil.abort();
			// }
		}
    	if( isChart .equals(Boolean.TRUE)){//���帳ֵ
    		for(int i =0 ; i < this.tblBuiding.getRowCount() ; i++){
    			IRow row = this.tblBuiding.getRow(i);
    			row.getCell("isConvertToChar").setValue(isChart);
    		}
    	}
    
    }
    
    protected IObjectValue createNewData() {
		RoomDesInfo roomDesInfo = new RoomDesInfo();
		
		BuildingInfo building = (BuildingInfo)this.getUIContext().get("building");
			if (building == null ||building == null) {
				logger.error("buildingInfo is null.");
				MsgBox.showError(this, "��ѡ��¥����");
				this.abort();
			}
		
		
		
		this.prmtBuilding.setValue(building);

		roomDesInfo.setBuilding(building);

//		roomDesInfo.setBuilding(buildingInfo);
//		roomDesInfo.setHouseProperty(HousePropertyEnum.NoAttachment);
//		if (buildingInfo.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
//			roomDesInfo.setUnitBegin(1);
//			roomDesInfo.setUnitEnd(1);
//			roomDesInfo.setFloorBegin(1);
//			roomDesInfo.setFloorEnd(1);
//		} else if (buildingInfo.getCodingType().equals(CodingTypeEnum.FloorNum)) {
//			roomDesInfo.setUnitBegin(0);
//			roomDesInfo.setUnitEnd(0);
//			roomDesInfo.setFloorBegin(1);
//			roomDesInfo.setFloorEnd(1);
//		} else {
//			roomDesInfo.setUnitBegin(0);
//			roomDesInfo.setUnitEnd(0);
//			roomDesInfo.setFloorBegin(1);
//			roomDesInfo.setFloorEnd(1);
//		}
//		roomDesInfo.setSerialNumberBegin(1);
//		roomDesInfo.setSerialNumberEnd(1);
//		roomDesInfo.setNumPrefixType(RoomNumPrefixEnum.noBuildPrefix);
		return roomDesInfo;
	}
    
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	
//    	if(null==this.prmtBuildingProperty.getValue()){
//    		FDCMsgBox.showError("�������ʲ���Ϊ�գ�");
//			SysUtil.abort();
//    	}
//    	if(null==this.prmtProductType.getValue()){
//    		FDCMsgBox.showError("��Ʒ���Ͳ���Ϊ�գ�");
//			SysUtil.abort();
//    	}
//    	if(null==this.prmtRoomModel.getValue()){
//    		FDCMsgBox.showError("���Ͳ���Ϊ�գ�");
//			SysUtil.abort();
//    	}
    }
   

    /**
     * output btnAllSelected_actionPerformed method
     */
    protected void btnAllSelected_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	for(int i = 0 ; i < this.tblBuiding.getRowCount() ; i++)
    	{
    		IRow row = this.tblBuiding.getRow(i);
    		row.getCell("isSelected").setValue(Boolean.TRUE);
    		getRoomDesInfo(""+i);
    	}
    }
    /**
     * output btnAllDrop_actionPerformed method
     */
    protected void btnAllDrop_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	for(int i = 0 ; i < this.tblBuiding.getRowCount() ; i++)
    	{
    		IRow row = this.tblBuiding.getRow(i);
    		row.getCell("isSelected").setValue(Boolean.FALSE);
    	}
    }
    
    
    public IRow setRowEmpity(IRow row){
    	//������
    	Integer one = new Integer(1);
		if(null==row.getCell("floorFrom").getValue()){
			row.getCell("floorFrom").setValue(one);
		}
		if(null==row.getCell("floorTo").getValue()){
			row.getCell("floorTo").setValue(one);
		}
		if(null==row.getCell("serialNumFrom").getValue()){
			row.getCell("serialNumFrom").setValue(one);
		}
		if(null==row.getCell("serialNumTo").getValue()){
			row.getCell("serialNumTo").setValue(one);
		}
    	return row;
    }
    /**
     * output btnAddLineRoomDes_actionPerformed method
     */
    protected void btnAddLineRoomDes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {	if(0==unitCount){
    	this.tblBuiding.getColumn("isSelected").getStyleAttributes().setHided(true);
    		IRow row = this.tblBuiding.addRow();
//    		KDTDefaultCellEditor spinnerCellFloorFrom = new KDTDefaultCellEditor(new KDNumberTextField());
//    		KDTDefaultCellEditor spinnerCellFloorTo = new KDTDefaultCellEditor(new KDNumberTextField());
//    		KDTDefaultCellEditor spinnerCellSeqFrom = new KDTDefaultCellEditor(new KDNumberTextField());
//    		KDTDefaultCellEditor spinnerCellSeqTo = new KDTDefaultCellEditor(new KDNumberTextField());
//    		row.getCell("serialNumFrom").setEditor(spinnerCellSeqFrom);
//    		row.getCell("serialNumTo").setEditor(spinnerCellSeqTo);
//    		row.getCell("floorFrom").setEditor(spinnerCellFloorFrom);
//    		row.getCell("floorTo").setEditor(spinnerCellFloorTo);
    		//����ҳǩ
	  		//ÿ�ζ����ص�һ�θ������ҳǩ
	  		RoomDesInfo roomDesBase = this.getRoomDesInfo(""+0);
	  		if(null==roomDesBase){
	  			roomDesBase = this.editData;
	  		}
    		addRoomDesPanel(row,roomDesBase);
    		//������
    		row = setRowEmpity(row);
    		row.getCell("isConvertToChar").setValue(Boolean.FALSE);
    		row.getCell("isSelected").setValue(Boolean.FALSE);
    		//������������
    		row.getCell("unitSeq").setValue(new Integer(1));
//			row.getCell("unitName").setValue("��Ԫ");
    		//����
//    		newRow.getCell("roomModel").setValue(oldRow.getCell("roomModel").getValue());
    		row.getCell("unitName").setValue("1��Ԫ");
    		row.getCell("unitSeq").getStyleAttributes().setFontColor(Color.GRAY);
    		row.getCell("unitName").getStyleAttributes().setFontColor(Color.GRAY);
    		row.getCell("unitSeq").getStyleAttributes().setBackground(Color.GRAY);
    		row.getCell("unitName").getStyleAttributes().setBackground(Color.GRAY);
    	
    	}else{
    	
    	
    	for(int i = this.tblBuiding.getRowCount()-1; i >=0  ; i--)
    	{		//����һ��Ҫ�½���������ֻnew һ��kdspinnerCelleditor ÿ��CELL�����һ��kdspinnerCelleditor ���ڴ�
    		//�������ÿ��cell�����ô�����ͬ��ֵ��
    		
//  	       KDSpinnerCellEditor spinnerCellFloorFrom = new KDSpinnerCellEditor(new SpinnerNumberModel(1,-999,
//  	    		   999,1));
//  	       KDSpinnerCellEditor spinnerCellFloorTo = new KDSpinnerCellEditor(new SpinnerNumberModel(1,-999,
//  	    		   999,1));
//  	       KDSpinnerCellEditor spinnerCellSeqFrom = new KDSpinnerCellEditor(new SpinnerNumberModel(1,0,9999,1));
//  	      KDSpinnerCellEditor spinnerCellSeqTo = new KDSpinnerCellEditor(new SpinnerNumberModel(1,0,9999,1));

    		KDTDefaultCellEditor spinnerCellFloorFrom = new KDTDefaultCellEditor(new KDNumberTextField());
    		KDTDefaultCellEditor spinnerCellFloorTo = new KDTDefaultCellEditor(new KDNumberTextField());
    		KDTDefaultCellEditor spinnerCellSeqFrom = new KDTDefaultCellEditor(new KDNumberTextField());
    		KDTDefaultCellEditor spinnerCellSeqTo = new KDTDefaultCellEditor(new KDNumberTextField());
////  	      KDFormattedTextField txtnum =new KDFormattedTextField();
//  	      txtnum.setDataType(Integer.class);
  	       
  	       
//  	       KDTDefaultCellEditor spinner = new KDTDefaultCellEditor (txtnum);
  		
    		IRow oldRow = this.tblBuiding.getRow(i);
    		if(Boolean.TRUE==oldRow.getCell("isSelected").getValue()){
    			IRow newRow = this.tblBuiding.addRow(i+1);
    			newRow.getCell("serialNumFrom").setEditor(spinnerCellSeqFrom);
    	  		newRow.getCell("serialNumTo").setEditor(spinnerCellSeqTo);
    	  		newRow.getCell("floorFrom").setEditor(spinnerCellFloorFrom);
    	  		newRow.getCell("floorTo").setEditor(spinnerCellFloorTo);

        		//����ҳǩ
    	  		//ÿ�ζ����ص�һ�θ������ҳǩ
    	  		RoomDesInfo roomDesBase = this.getRoomDesInfo(""+0);
    	  		if(null==roomDesBase){
    	  			roomDesBase = this.editData;
    	  		}
        		addRoomDesPanel(newRow,roomDesBase);
    			//������
        		newRow = setRowEmpity(newRow);
        		newRow.getCell("isConvertToChar").setValue(Boolean.FALSE);
        		newRow.getCell("isSelected").setValue(Boolean.FALSE);
        		//������������
        		newRow.getCell("unitSeq").setValue(new Integer(oldRow.getCell("unitSeq").getValue().toString()));
//    			row.getCell("unitName").setValue("��Ԫ");
        		//����
//        		newRow.getCell("roomModel").setValue(oldRow.getCell("roomModel").getValue());
        		newRow.getCell("unitName").setValue(oldRow.getCell("unitName").getValue().toString());
    		}
    	}
    	
    
//    	//����ˢ�º���
//    	for(int i = 0 ; i<this.tblBuiding.getRowCount();i++){
//    		IRow row  = this.tblBuiding.getRow(i);
//    		row.getCell("defineInfo").setValue(new Integer(i));
//    	}
//		s.tblBuiding.getMergeManager().mergeBlock(0, 2, this.tblBuiding.getRowCount() -1,  3, KDTMergeManager.FREE_ROW_MERGE);
  
    }
	setTableCellIsChar();
	exchangePanelAndMap();
    	
      }
  
    /**
     * output btnDelLineRoomDes_actionPerformed
     */
    protected void btnDelLineRoomDes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.tblBuiding.getMergeManager().removeAllMergeBlock();
    	if(0==unitCount){
    		int index = this.tblBuiding.getSelectManager().getActiveRowIndex();
    		if(0>index){
    			FDCMsgBox.showWarning("��ѡ���У�");
    			SysUtil.abort();
    		}
    		IRow row = this.tblBuiding.getRow(index);
    		this.tblBuiding.removeRow(index);
    		delRoomDesPanel(row);
    	}
    	else{
    	
    	for(int i = this.tblBuiding.getRowCount()-1 ; i>=0 ; i--){
    		IRow row = this.tblBuiding.getRow(i);
    		Boolean flag = (Boolean) row.getCell("isSelected").getValue();
    		Integer seq = (Integer)row.getCell("unitSeq").getValue();
    		int sum=0;
    		if(Boolean.TRUE==flag){
    			for(int j = 0 ;j< this.tblBuiding.getRowCount();j ++){
    				//ͬ��Ԫ������
    				if(seq.equals((Integer)this.tblBuiding.getRow(j).getCell("unitSeq").getValue())){
    					sum++;
    				}
    			}
    			if(sum<2){
        			FDCMsgBox.showWarning("����ɾ��"+seq.toString()+"��Ԫȫ����Ϣ��");
        			
//        			return;
        		}else{
        			this.tblBuiding.removeRow(i);
        			//ɾ��ҳǩ
        			delRoomDesPanel(row);
        		}
    		
    		}
    		
    	}
    }
    	setTableCellIsChar();
    	exchangePanelAndMap();
//		.tblBuiding.getMergeManager().mergeBlock(0, 2, this.tblBuiding.getRowCount() -1,  3, KDTMergeManager.FREE_ROW_MERGE);
    }
    
    /**
     * @description  ��Ϊ���ܻ��������ݣ����ԣ������¥���£�����->���䶨����Ϣ->��Ԫ��Ϣ
     *@author tim_gao
     */
    protected void btnBuildingClearedUp_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	
    	BuildingInfo building = (BuildingInfo)this.getUIContext().get("building");
    	boolean flag=false;
    	if(null!=building ){
    		
    		if(RoomFactory.getRemoteInstance().exists("where building.id ='"+building.getId().toString()+"' and sellState != 'Init' ")) {
    			MsgBox.showInfo("�з����Ѿ����̻�������,����ֱ�����¥����");
    			return;
    		}
    		
    		
    		if (RoomFactory.getRemoteInstance().exists("where building.id ='"+building.getId().toString()+"'")) {
    			if (MsgBox.showConfirm2(this, "�Ѿ��з��䣬�Ƿ����?") == MsgBox.YES) {
    				flag = ((IRoomDes)this.getBizInterface()).cleanBuilding(building);
    			
    			}else{
    				return;
    			}
    		}
    		flag = ((IRoomDes)this.getBizInterface()).cleanBuilding(building);
    	}
    	if(flag){
    		FDCMsgBox.showWarning("���¥���ɹ���");
    		this.tblBuiding.removeRows();
    		this.unitAmount.setValue(new Integer(0));
    		this.unitCount=((Integer)this.unitAmount.getValue()).intValue();
    		this.editData = new RoomDesInfo();
    	}else{
    		FDCMsgBox.showWarning("���¥��ʧ�ܣ�");
    		SysUtil.abort();
    	}
//    	
//    	this.btnSubmit.setEnabled(true);
    	//ȥ������ҳǩ
		this.kDTabbedPane1.removeAll();
    	   super.actionAddNew_actionPerformed(e);
    }
    
    /**
     * output tblBuiding_editStopped method
     */
    protected void tblBuiding_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
//    	//����ˢ�º���
//    	for(int i = 0 ; i<this.tblBuiding.getRowCount();i++){
//    		IRow row  = this.tblBuiding.getRow(i);
//    		row.getCell("defineInfo").setValue(new Integer(i));
//    	}
//    	
//    	ICell cell =this.tblBuiding.getCell(e.getRowIndex(), e.getColIndex());
//    	e.getValue();
//    	(( KDSpinnerCellEditor)cell.getEditor()).getValue();
//    	(( KDSpinnerCellEditor)cell.getEditor()).getText();
//    	(( KDSpinnerCellEditor)cell.getEditor()).getComponent();
//    	((KDSpinner)(( KDSpinnerCellEditor)cell.getEditor()).getFocusComponent()).getValue();
//    	((KDSpinner)(( KDSpinnerCellEditor)cell.getEditor()).getFocusComponent()).getModel().getValue();
    }
//
//    /**
//     * output tblBuiding_editValueChanged method
//     */
//    protected void tblBuiding_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
//    {
//    	ICell cell =this.tblBuiding.getCell(e.getRowIndex(), e.getColIndex());
//    	e.getValue();
//    	(( KDSpinnerCellEditor)cell.getEditor()).getValue();
//    	(( KDSpinnerCellEditor)cell.getEditor()).getText();
//    	(( KDSpinnerCellEditor)cell.getEditor()).getComponent();
//    	((KDSpinner)(( KDSpinnerCellEditor)cell.getEditor()).getFocusComponent()).getValue();
//    	((KDSpinner)(( KDSpinnerCellEditor)cell.getEditor()).getFocusComponent()).getModel().getValue();
//    }
    /**
     * output tblBuiding_tableClicked method
     */
    protected void tblBuiding_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	int index = this.tblBuiding.getSelectManager().getActiveRowIndex();
    	this.kDTabbedPane1.setSelectedIndex(index);
    }

    /**
     * output tblBuiding_activeCellChanged method
     */
    protected void tblBuiding_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    	
    }
    
    /**
     * output tblBuiding_editStopping method
     */
    protected void tblBuiding_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    	
    	
//    	ICell cell =this.tblBuiding.getCell(e.getRowIndex(), e.getColIndex());
//    	e.getValue();
//    	(( KDSpinnerCellEditor)cell.getEditor()).getValue();
//    	(( KDSpinnerCellEditor)cell.getEditor()).getText();
//    	(( KDSpinnerCellEditor)cell.getEditor()).getComponent();
//    	((KDSpinner)(( KDSpinnerCellEditor)cell.getEditor()).getFocusComponent()).getValue();
//    	((KDSpinner)(( KDSpinnerCellEditor)cell.getEditor()).getFocusComponent()).getModel().getValue();
////    	((DefaultEditor)cell.getEditor()).getTextField().getValue();
////    	cell.setValue(cell.getEditor().get);
//    	cell.getEditor().getText();
    	verifyTblBuilidngEntry();
    	
    }
}