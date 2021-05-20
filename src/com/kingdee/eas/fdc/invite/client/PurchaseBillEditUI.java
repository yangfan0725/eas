/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.MaterialPromptSelector;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.invite.PartABOMEntryInfo;
import com.kingdee.eas.fdc.invite.PurchaseBillEntryInfo;
import com.kingdee.eas.fdc.invite.PurchaseBillFactory;
import com.kingdee.eas.fdc.invite.PurchaseBillInfo;
import com.kingdee.eas.fdc.invite.PurchaseTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;

/**
 * �ɹ����뵥 �༭����
 */
public class PurchaseBillEditUI extends AbstractPurchaseBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PurchaseBillEditUI.class);
    
    private boolean isMustFromPartA = false; 
    private KDBizPromptBox f7MatNum = new KDBizPromptBox();
    
    private final String COL_MATNUM= "material.number";
    
    /**
     * output class constructor
     */
    public PurchaseBillEditUI() throws Exception
    {
        super();
    }

    public void handleCodingRule() throws BOSException, EASBizException{
    	
    	String orgId = "";
    	
    	if(this.editData instanceof PurchaseBillInfo && this.editData.getOrgUnit().getId() != null){
    		orgId = this.editData.getOrgUnit().getId().toString();
    	}else{
    		orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
    	}
    	
    	String number = null;
    	
    	ICodingRuleManager codingRuleManager = getEncodingRule();
    	
    	if(this.getOprtState() != STATUS_ADDNEW){
    		return ;
    	}
    	PurchaseBillInfo bill = editData;
    	boolean isExist = codingRuleManager.isExist(bill,orgId);
    	if(isExist){
    		if(codingRuleManager.isAddView(editData, orgId)){
      		  number = codingRuleManager.getNumber(editData, orgId);
      		 this.txtNumber.setText(number);
      		 this.txtNumber.setEnabled(false);
    		}
    		
    		setNumberTextEnabled();
    	}
    	
    }
    
    public ICodingRuleManager getEncodingRule()
    {
    	
    	ICodingRuleManager codingRuleManager = null;
		try {
			if(this.getUIContext().get("codingRuleManager")== null){
			codingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			this.getUIContext().put("codingRuleManager", codingRuleManager);
			}else{
				
				codingRuleManager = (ICodingRuleManager) this.getUIContext().get("codingRuleManager");
			}
			
			
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codingRuleManager;
    }
    
 
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
        if(STATUS_EDIT.equals(this.getOprtState()))
		{
			if(this.editData!=null&&FDCBillStateEnum.SUBMITTED.equals(this.editData.getState()))
			{
				this.actionSave.setEnabled(false);
			}
		}
    }


    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
        this.txtBillType.setRequired(true);
		this.txtBillType.setEnabled(false);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        if(editData!=null&&FDCBillStateEnum.SUBMITTED.equals(editData.getState())){
        	this.actionSave.setEnabled(false);
        }
		this.actionAddLine.setEnabled(true);
		this.actionRemoveLine.setEnabled(true);
		this.actionInsertLine.setEnabled(true);
		this.actionCopyLine.setEnabled(true);
		
		this.txtBillType.setRequired(true);
		this.txtBillType.setEnabled(false);
    }
	
	protected KDTable getDetailTable() {		
		return this.kdtEntrys;
	}

	
	protected IObjectValue createNewData() {		
		PurchaseBillInfo object = new PurchaseBillInfo();
		object.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		object.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		object.setPurchaseType(PurchaseTypeEnum.material);
		return object;
	}

	
	protected IObjectValue createNewDetailData(KDTable table) {
		PurchaseBillEntryInfo info = new PurchaseBillEntryInfo();
		return info;
	}

	
	protected ICoreBase getBizInterface() throws Exception {
		
		return PurchaseBillFactory.getRemoteInstance();
	}

	protected void addLine(KDTable table) {
		if(this.isMustFromPartA&&this.prmtCurProject.getValue()==null){
			FDCMsgBox.showWarning(this,	"�����˲��������ϱ�����Դ�ڼ׹������嵥������ѡ�񹤳���Ŀ");
			return;
		}
		super.addLine(table);
	}

	
	protected void initWorkButton() {		
		super.initWorkButton();
		initUI();
	}
	
	private void initUI() {
    	JButton btnAddLine = this.kDContainer1.add(actionAddLine);
    	JButton btnInsertLine = this.kDContainer1.add(actionInsertLine);
		JButton btnDelLine = this.kDContainer1.add(actionRemoveLine);
		btnAddLine.setIcon(EASResource.getIcon("imgTbtn_addline"));		
		btnAddLine.setSize(22, 19);
		btnInsertLine.setIcon(EASResource.getIcon("imgTbtn_insert"));
		btnInsertLine.setSize(22, 19);
		btnDelLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelLine.setSize(22, 19);
		
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);
		
		
		this.actionNextPerson.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionWorkflowList.setVisible(false);
		
    }
	
	/**
	 * ����������¼�У����Ͽ�����Դ�ڼ׹�����PartABOMEntryInfo��������Դ����ͨ����MaterialInfo<p>
	 * ����Ǽ׹����ϣ���Ҫ�����̡���;����Ϣһ�����������
	 * @param theMaterial Ҫ��������
	 */
	private void fillRowWithMaterial(Object theMaterial)
	{
		int index = this.kdtEntrys.getSelectManager().getActiveRowIndex();
    	IRow row = this.kdtEntrys.getRow(index);
    	PurchaseBillEntryInfo purchaseBillEntry = (PurchaseBillEntryInfo)row.getUserObject();
    	if(theMaterial != null)
    	{
    		if(theMaterial instanceof PartABOMEntryInfo){
    			PartABOMEntryInfo partABOMEntry = (PartABOMEntryInfo)theMaterial;
    			row.getCell(COL_MATNUM).setValue(partABOMEntry.getMaterial().getNumber());
    			row.getCell("material.name").setValue(partABOMEntry.getMaterial().getName());
        		row.getCell("model").setValue(partABOMEntry.getModel());
        		row.getCell("unit").setValue(partABOMEntry.getUnit());
        		row.getCell("brand").setValue(partABOMEntry.getBrand());
        		row.getCell("factory").setValue(partABOMEntry.getFactory());
        		row.getCell("use").setValue(partABOMEntry.getUse());
        		row.getCell("install").setValue(partABOMEntry.getInstall());
        		row.getCell("enterTime").setValue(partABOMEntry.getEnterTime());
        		row.getCell("isNeedService").setValue(new Boolean(partABOMEntry.isIsNeedService()));
        		row.getCell("remark").setValue(partABOMEntry.getRemark());
        		row.getCell("sourceBillNum").setValue(partABOMEntry.getParent().getNumber());
        		
        		purchaseBillEntry.setMaterial(partABOMEntry.getMaterial());
    		}else if(theMaterial instanceof MaterialInfo){
    			MaterialInfo materialInfo = (MaterialInfo)theMaterial;
    			row.getCell(COL_MATNUM).setValue(materialInfo.getNumber());
        		row.getCell("material.name").setValue(materialInfo.getName());
        		row.getCell("model").setValue(materialInfo.getModel());
        		row.getCell("unit").setValue(materialInfo.getBaseUnit());
        		purchaseBillEntry.setMaterial(materialInfo);
    		}
    		
    	}
    	else
    	{
    		row.getCell(COL_MATNUM).setValue(null);
    		row.getCell("model").setValue(null);
    		row.getCell("unit").setValue(null);
    	}
	}

	/**
	 * ���������Դ�ڼ׹����ʣ��ж���������֮���Ƿ񳬳��׹� ���û�ѡ��ȷ�����Կɼ���������ύ
	 */
	private void checkIsGreaterThanPartA(){
		KDTable materialTable =  this.getDetailTable();
		String sourceBillNumber = "";
		String curProjectId = this.editData.getCurProject().getId().toString();
		String materialId = "";
		PurchaseBillEntryInfo purchaseBillEntry = null;
		for(int i = 0, size = materialTable.getRowCount(); i < size; i++){
			purchaseBillEntry = (PurchaseBillEntryInfo)materialTable.getRow(i).getUserObject();
			materialId = purchaseBillEntry.getMaterial().getId().toString();
			sourceBillNumber = purchaseBillEntry.getSourceBillNum();
			if ("".equals(sourceBillNumber) || sourceBillNumber == null){
				continue;
			}
			else {
				try {
					FDCSQLBuilder fbd = new FDCSQLBuilder();
					
					//�ȼ��� �ɹ����뵥�е����϶�Ӧ�������ۼ�
					fbd.appendSql("select sum(T_INV_PurchaseBillEntry.FAmount) from T_INV_PurchaseBillEntry ");
					fbd.appendSql("inner join T_INV_PurchaseBill on T_INV_PurchaseBillEntry.FParentId = T_INV_PurchaseBill.FId ");
					fbd.appendSql("inner join T_FDC_CurProject on T_FDC_CurProject.FId = T_INV_PurchaseBill.FCurProjectId " );
					fbd.appendSql("where T_FDC_CurProject.Fid = ? and T_INV_PurchaseBillEntry.FSourceBillNum = ? and FMaterialID = ?");
					fbd.addParam(curProjectId);
					fbd.addParam(sourceBillNumber);
					fbd.addParam(materialId);
					IRowSet rs =  fbd.executeQuery();
					double matSum = 0;
					if (rs.next()) 
						matSum = rs.getDouble(1);
					
					
					// �쳣��������п������޸ĵ�ǰ�ķ�¼������
					// ��Ҫ��ǰ��¼�Ƿ��Ѿ��������ݿ��У����У���Ҫ�������ۼ��м�ȥ
					fbd.clear();
					fbd.appendSql("select FAmount from T_INV_PurchaseBillEntry ");
					fbd.appendSql("where FId= ? and FMaterialID = ?");
					//������û�������¼����û��ID�����⴫��һ��nullֵ�������ݿ�鲻�����ݣ�����ͳһ����
					fbd.addParam(purchaseBillEntry.getId()==null? null: purchaseBillEntry.getId().toString());
					fbd.addParam(materialId);
					rs =  fbd.executeQuery();
					
					if (rs.next()) { //˵�������е����������ݿ����Ѿ��м�¼������ֻ�޸�����������
						double oldAmount = rs.getDouble(1);
						matSum = matSum - oldAmount;
					}
					matSum = matSum + purchaseBillEntry.getAmount().doubleValue();
					
					// ��ȡ���׹������嵥�еĿ��ṩ������
					fbd.clear();
					fbd.appendSql("select T_INV_PartABOMEntry.FAmount from T_INV_PartABOMEntry ");
					fbd.appendSql("inner join T_INV_PartABOM on T_INV_PartABOMEntry.FParentID = T_INV_PartABOM.FId ");
					fbd.appendSql("inner join T_FDC_CurProject on T_FDC_CurProject.Fid = T_INV_PartABOM.FCurProjectId ");
					fbd.appendSql("where  T_FDC_CurProject.Fid = ? and  T_INV_PartABOM.FNumber = ? and FMaterialID = ? " );
					fbd.addParam(curProjectId);
					fbd.addParam(sourceBillNumber);
					fbd.addParam(materialId);
					rs =  fbd.executeQuery();
					int planAmount = 0;
					if (rs.next()) 
						planAmount = rs.getInt(1);
					
					if (planAmount - matSum < 0){
						if (FDCMsgBox.showConfirm2(this, "�ɹ������ " +  purchaseBillEntry.getMaterial().getName() + " �����������ڼ׹������嵥�е��������Ƿ��ύ��") == FDCMsgBox.CANCEL)
						{
							abort();
							break;
						}
					}
				}
				catch (BOSException e){
					e.printStackTrace();
				}
				catch (SQLException e){
					logger.info("SQL����쳣");
					e.printStackTrace();
				}
			}
		}
	}	
	
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		int cIndex = e.getColIndex();
		if(COL_MATNUM.equals(kdtEntrys.getColumnKey(cIndex))){
			if(e.getValue() != null){
				Object[] materials = filterMaterial(e);
				if (materials != null)
					insertDataToRow(materials);	
			}
		}
	}
	
	/**
	 * ������F7�ж�ѡ�������������ɸѡ���ˣ������������ĳ���ϣ����丳Ϊnull<p>
	 * ���������������������ͬ���ϱ����������˵�
	 * @param e KDTEditEvent KDTable�༭�¼� 
	 * @author owen_wen 2010-7-13
	 * @return ɸѡ�����������
	 */
	private Object[] filterMaterial(KDTEditEvent e){
		KDTable table = getDetailTable();
		if( (e.getValue() != null) 
				&& (e.getValue() instanceof Object[])){
			Object[] materials = (Object[]) e.getValue();
							
			int activeRow = table.getSelectManager().getActiveRowIndex();
			
			//�����ظ�����
			//Ҫ���������: ��Դ�ڼ׹�����PartABOMEntryInfo��������Դ����ͨ����MaterialInfo
			
			MaterialInfo material = null;
			PartABOMEntryInfo partABOMEntryInfo = null;
			
			for(int i = 0; i < materials.length; i++)
			{				
				if(materials[i] instanceof MaterialInfo){ // ��Դ����ͨ����MaterialInfo
					material = (MaterialInfo)materials[i];				
					for(int j = 0; j < table.getRowCount(); j++)
					{
						//���j�ƶ�������ϣ�����Ҫ�ж��Ƿ��ظ�����Ϊ�û���п���ѡ���˶������
						if(j != activeRow)
						{
							if(table.getRow(j).getCell(COL_MATNUM).getValue() == null)
								continue;   //����û�����ϵ��У�����
													
							String preMateralNumber = ""; //��ȡ��j�е����ϱ���
							
							//�п����Ǹ�����δ����������У�getValue�õ��Ļ�����MaterialInfo�������Ҫ�жϴ���
							if (table.getRow(j).getCell(COL_MATNUM).getValue() instanceof MaterialInfo) 
								preMateralNumber = ((MaterialInfo)table.getRow(j).getCell(COL_MATNUM).getValue()).getNumber();
							else
								preMateralNumber = table.getRow(j).getCell(COL_MATNUM).getValue().toString();
																			
							if(material.getNumber().equals(preMateralNumber)
									&& table.getRow(j).getCell("sourceBillNum").getValue() == null){								
								materials[i] = null; //����i�����ϣ����Ϊ��
								break; 		//�����ظ�,�Һ�ͬ�ظ���ֱ������ѭ��������һ�����Ͻ����ж�
							}
						}
					}
				}
				else if (materials[i] instanceof PartABOMEntryInfo){ // ��Դ�ڼ׹�����PartABOMEntryInfo
					partABOMEntryInfo = (PartABOMEntryInfo) materials[i];
					for(int j = 0; j < table.getRowCount(); j++)
					{
						//���j�ƶ�������ϣ�����Ҫ�ж��Ƿ��ظ�����Ϊ�û���п���ѡ���˶������
						if(j != activeRow)
						{
							if(table.getRow(j).getCell(COL_MATNUM).getValue() == null)
								continue;   //����û�����ϵ��У�����
													
							String preMateralNumber = ""; //��ȡ��j�е����ϱ���
							
							//�п����Ǹ�����δ����������У�getValue�õ��Ļ�����MaterialInfo�������Ҫ�жϴ���
							if (table.getRow(j).getCell(COL_MATNUM).getValue() instanceof MaterialInfo) 
								preMateralNumber = ((MaterialInfo)table.getRow(j).getCell(COL_MATNUM).getValue()).getNumber();
							else
								preMateralNumber = table.getRow(j).getCell(COL_MATNUM).getValue().toString();
																			
							if(partABOMEntryInfo.getMaterial().getNumber().equals(preMateralNumber)){
								if (table.getRow(j).getCell("sourceBillNum").getValue() != null)
									if (partABOMEntryInfo.getParent().getNumber().equals(table.getRow(j).getCell("sourceBillNum").getValue().toString())){
										materials[i] = null; //����i�����ϣ����Ϊ��
										break; 		//�����ظ�,ֱ������ѭ��������һ�����Ͻ����ж�
								}		
							}
						}
					}
				}
			}
			
			return materials;
		}else {
			return null;
		}
	}
	
	
	/**
	 * ����¼���в�������
	 * @param rowIndex ���������
	 * @param materials ��������������飬������ĳ��Ϊnull����ʾ���ظ�����Ҫ����
	 * @author owen_wen 2010-7-13
	 */
	private void insertDataToRow(Object[] materials) {	
		//��ȡ��е��к�
		int activeRow = kdtEntrys.getSelectManager().getActiveRowIndex();
		
		for(int i = 0; i < materials.length; i++){
			MaterialInfo materialInfo = null;
			PartABOMEntryInfo partABOMEntryInfo = null;
			
			if(materials[i] instanceof MaterialInfo)
				materialInfo = (MaterialInfo)materials[i];			
			else if(materials[i] instanceof PartABOMEntryInfo )
				partABOMEntryInfo = (PartABOMEntryInfo) materials[i];
			
			if(i == 0 && materialInfo == null && partABOMEntryInfo == null)
			{
				kdtEntrys.removeRow(activeRow);
				continue;
			}
			
			//����Ƕ�ѡ(��i>0ʱ)����Ҫ�ж��Ƿ�ҪaddLine��������һ�з�¼��
			if(i > 0){
				if(materialInfo != null || partABOMEntryInfo != null)
					super.insertLine(kdtEntrys);//�ڻ�д����һ�� 
				else
					continue;
			}
			
			// �� i==0, and material != null �������ֱ�ӽ�materialInfo���뵽��ǰ����		
			if (materialInfo != null){
				fillRowWithMaterial(materialInfo);
			}else if (partABOMEntryInfo != null){
				fillRowWithMaterial(partABOMEntryInfo);
			}
		}	
	}
	
	protected void insertLine(KDTable table) {
		if(this.isMustFromPartA&&this.prmtCurProject.getValue()==null){
			FDCMsgBox.showWarning(this,	"�����˲��������ϱ�����Դ�ڼ׹������嵥������ѡ�񹤳���Ŀ");
			return;
		}
		super.insertLine(table);
	}
	
	public void onLoad() throws Exception {
		this.isMustFromPartA = FDCUtils.getDefaultFDCParamByKey(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString(), FDCConstants.FDC_PARAM_INVITE_PURCHASEBOMMUSTPARTA);
		super.onLoad();
		handleCodingRule();
		if(STATUS_EDIT.equals(this.getOprtState())) 
		{
			if(this.editData!=null&&FDCBillStateEnum.SUBMITTED.equals(this.editData.getState()))
			{
				this.actionSave.setEnabled(false);
			}
		}else if(STATUS_VIEW.equals(this.getOprtState()))
		{
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionCopyLine.setEnabled(false);
		}
		
		if(editData!=null&&FDCBillStateEnum.AUDITTED.equals(editData.getState())){
			this.actionEdit.setEnabled(false);
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
		}
		
		this.txtName.setRequired(true);
		this.txtNumber.setRequired(true);
		this.txtName.setMaxLength(80);
		this.txtNumber.setMaxLength(80);
		this.txtBillType.setRequired(true);
		this.txtBillType.setEnabled(false);
		
		this.prmtCurProject.setDisplayFormat("$number$ $name$");
		this.prmtCurProject.setEditFormat("$name$");
		this.prmtCurProject.setCommitFormat("$name$");
 		this.prmtCurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		this.prmtCurProject.setRequired(isMustFromPartA);
		
		if(editData!=null&&editData.getCurProject()!=null){
			setF7MatNumFilter(editData.getCurProject());
		}
		
		prmtCurProject.addSelectorListener(new SelectorListener()
				{
					public void willShow(SelectorEvent e) {
						try {
							setProjectFilter(e);
						} catch (Exception e1) {
							logger.error(e1);
						}
					}
				});
		prmtCurProject.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				try {
					setF7MatNumFilter(eventObj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		
		IColumn matNumCol = this.kdtEntrys.getColumn(COL_MATNUM);
		matNumCol.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		
		matNumCol.setRequired(true);
		
		
		String renderKey = isMustFromPartA?"$material.number$":"$number$";
		String renderQuery = isMustFromPartA?"com.kingdee.eas.fdc.invite.app.F7PartAMaterialQuery"
				:"com.kingdee.eas.basedata.master.material.app.F7MaterialQuery";
		ObjectValueRender segNum = new ObjectValueRender();
		
		segNum.setFormat(new BizDataFormat(renderKey));
		matNumCol.setRenderer(segNum);
		
		f7MatNum.setDisplayFormat(renderKey);
		f7MatNum.setEditFormat(renderKey);
		f7MatNum.setCommitFormat(renderKey);
		f7MatNum.setEnabledMultiSelection(true);
		if (!isMustFromPartA){
			f7MatNum.setSelector(new MaterialPromptSelector(this));  // ʹ���Զ��������ұ��F7 Added by owen_wen 2010-8-27
		}else{
			f7MatNum.setQueryInfo(renderQuery);
		}
		
		KDTDefaultCellEditor f7EditorMatNum = new KDTDefaultCellEditor(f7MatNum);
		matNumCol.setEditor(f7EditorMatNum);
		
		f7MatNum.addSelectorListener(new SelectorListener()
		{
			public void willShow(SelectorEvent e) {
				try {
//					setF7MatNumFilter(e);
				} catch (Exception e1) {
					logger.error(e1);
				}
			}
		});
//		f7MatNum.addDataChangeListener(new DataChangeListener()
//		{
//			public void dataChanged(DataChangeEvent eventObj) {
//				fillRowWithMaterial(eventObj);
//			}
//		});
		
		/**
		 * ���ü�����λΪF7�ؼ�
		 */
		IColumn colUnit = this.kdtEntrys.getColumn("unit");
		colUnit.setRequired(true);
		
		colUnit.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		KDBizPromptBox f7MeasureUnit = new KDBizPromptBox();
		
		f7MeasureUnit.setDisplayFormat("$number$");
		f7MeasureUnit.setEditFormat("$number$");
		f7MeasureUnit.setCommitFormat("$number$");
		f7MeasureUnit.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
		
		KDTDefaultCellEditor f7EditorUnit = new KDTDefaultCellEditor(f7MeasureUnit);
		colUnit.setEditor(f7EditorUnit);
		
		/**
		 * ����������ֻ����ʾ����,��Ϊ������
		 */
		KDFormattedTextField amountField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		
		amountField.setPrecision(2);
		amountField.setNegatived(false);
		amountField.setSupportedEmpty(true);
		
		KDTDefaultCellEditor qtyDecimalEditor = new KDTDefaultCellEditor(amountField);
		
		this.kdtEntrys.getColumn("amount").setEditor(qtyDecimalEditor);
		this.kdtEntrys.getColumn("amount").setRequired(true);
		this.kdtEntrys.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntrys.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00");
		
		/**
		 * ���ý�������
		 */
		IColumn inputDateCol = this.kdtEntrys.getColumn("enterTime");
		
		KDDatePicker inputDateField = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(inputDateField);
		inputDateCol.setEditor(dateEditor);
		
		FDCHelper.formatTableDate(kdtEntrys, "enterTime");
		
		/**
		 * ���ù���ͺ�
		 */
		KDTextField txtSize = new KDTextField();
		txtSize.setMaxLength(100);
		KDTDefaultCellEditor sizeCellEditor = new KDTDefaultCellEditor(txtSize);
		this.kdtEntrys.getColumn("model").setEditor(sizeCellEditor);
		this.kdtEntrys.getColumn("brand").setEditor(sizeCellEditor);
		this.kdtEntrys.getColumn("factory").setEditor(sizeCellEditor);
		this.kdtEntrys.getColumn("use").setEditor(sizeCellEditor);
		
		KDComboBox box = new KDComboBox();
		box.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.InstallTypeEnum").toArray());
		KDTDefaultCellEditor boxCellEditor = new KDTDefaultCellEditor(box);
		this.kdtEntrys.getColumn("install").setEditor(boxCellEditor);
		
		KDComboBox sectionBox = new KDComboBox();
		sectionBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.SectionEnum").toArray());
		KDTDefaultCellEditor sectionBoxCellEditor = new KDTDefaultCellEditor(sectionBox);
		this.kdtEntrys.getColumn("section").setEditor(sectionBoxCellEditor);
		
		/**
		 * ���ñ�עcell����
		 */
		KDTextField txtDescription = new KDTextField();
		txtDescription.setMaxLength(100);
		KDTDefaultCellEditor descriptionCellEditor = new KDTDefaultCellEditor(txtDescription);
		
		this.kdtEntrys.getColumn("remark").setEditor(descriptionCellEditor);
		
		
		this.actionAuditResult.setVisible(false);
		
		this.btnAddLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		
//		R100524-228����
//		add ����ͼ ��������鿴 �༶���� ��һ�������� ��ť  by jiadong at 2010-6-11
		this.actionWorkFlowG.setVisible(true);
		this.actionAuditResult.setVisible(true);
		this.actionMultiapprove.setVisible(true);
		this.actionNextPerson.setVisible(true);
	}

	
	protected void removeLine(KDTable table) {
		
		super.removeLine(table);
	}
	private SelectorItemCollection getCurProjectSelectorCols()
	{
		SelectorItemCollection sic = new SelectorItemCollection();
		
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("name"));
		
		sic.add(new SelectorItemInfo("landDeveloper.name"));
		sic.add(new SelectorItemInfo("startDate"));
		sic.add(new SelectorItemInfo("sortNo"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		
		sic.add(new SelectorItemInfo("parent.id"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("projectStatus.name"));
		sic.add(new SelectorItemInfo("projectPeriod"));
		sic.add(new SelectorItemInfo("projectType.name"));
		
		sic.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sic.add(new SelectorItemInfo("projectStatus.name"));
		sic.add(new SelectorItemInfo("projectType.name"));
		
		sic.add(new SelectorItemInfo("isLeaf"));
	
		return sic ;
	}
	private void setF7MatNumFilter(CurProjectInfo project) throws Exception{
		if(this.isMustFromPartA){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",SysContext.getSysContext().getCurrentOrgUnit().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("parent.curProject.id", null,CompareType.EQUALS));
			
			if(project!=null)
			{
				filter.getFilterItems().add(new FilterItemInfo("parent.curProject.id", project.getId().toString()));
				filter.setMaskString("#0 and #1 and (#2 or #3)");
			}
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add( new SelectorItemInfo("*"));
			sic.add( new SelectorItemInfo("unit.number"));
			sic.add( new SelectorItemInfo("unit.name"));
			sic.add( new SelectorItemInfo("unit.id"));
			sic.add( new SelectorItemInfo("material.name"));
			sic.add( new SelectorItemInfo("material.number"));
			sic.add( new SelectorItemInfo("material.id"));
			sic.add( new SelectorItemInfo("parent.number"));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			f7MatNum.setEntityViewInfo(view);
			f7MatNum.setSelectorCollection(sic);
		}else{
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add( new SelectorItemInfo("number"));
			sic.add( new SelectorItemInfo("name"));
			sic.add( new SelectorItemInfo("model"));
			sic.add( new SelectorItemInfo("baseUnit.id"));
			sic.add( new SelectorItemInfo("baseUnit.number"));
			sic.add( new SelectorItemInfo("baseUnit.name"));
			
			f7MatNum.setSelectorCollection(sic);
		}
		
	}
	private void setF7MatNumFilter(DataChangeEvent e) throws Exception{
		if(this.isMustFromPartA){
			FilterInfo filter = new FilterInfo();
			
			CurProjectInfo project = (CurProjectInfo)e.getNewValue();
			
			filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",SysContext.getSysContext().getCurrentOrgUnit().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("parent.curProject.id", null,CompareType.EQUALS));
			
			if(project!=null)
			{
				filter.getFilterItems().add(new FilterItemInfo("parent.curProject.id", project.getId().toString()));
				filter.setMaskString("#0 and #1 and (#2 or #3)");
			}
			
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add( new SelectorItemInfo("*"));
			sic.add( new SelectorItemInfo("unit.number"));
			sic.add( new SelectorItemInfo("unit.name"));
			sic.add( new SelectorItemInfo("unit.id"));
			sic.add( new SelectorItemInfo("material.name"));
			sic.add( new SelectorItemInfo("material.number"));
			sic.add( new SelectorItemInfo("material.id"));
			sic.add( new SelectorItemInfo("parent.number"));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			f7MatNum.setEntityViewInfo(view);
			f7MatNum.setSelectorCollection(sic);
		}else{
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add( new SelectorItemInfo("number"));
			sic.add( new SelectorItemInfo("name"));
			sic.add( new SelectorItemInfo("model"));
			sic.add( new SelectorItemInfo("baseUnit.id"));
			sic.add( new SelectorItemInfo("baseUnit.number"));
			sic.add( new SelectorItemInfo("baseUnit.name"));
			
			f7MatNum.setSelectorCollection(sic);
		}
		
	}
	private void setProjectFilter(SelectorEvent e) throws Exception
	{
//		Set authorizedOrgs = new HashSet();
//		Map orgs = null;
//		if(orgs==null){
//			orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
//					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
//		            OrgType.CostCenter,  null,  null, null);
//		}
//		if(orgs!=null){
//			Set orgSet = orgs.keySet();
//			Iterator it = orgSet.iterator();
//			while(it.hasNext()){
//				authorizedOrgs.add(it.next());
//			}
//		}
//		EntityViewInfo view = new EntityViewInfo();
//		
//		view.setSelector(getCurProjectSelectorCols());
//		
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
//		
//		
//		/**
//		 * ��֯�ܹ���:������֯��������ȷ�Ϻ����޸�
//		 */
//		FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();
//		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.longNumber",orgUnit.getLongNumber()));
//
//		//��ϸ������Ŀ
//		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.valueOf(true)));
//		
//		view.setFilter(filter);
//		prmtCurProject.setEntityViewInfo(view);
//		if (prmtCurProject.getSelector() != null && prmtCurProject.getSelector() instanceof com.kingdee.eas.framework.client.ListUI) {
//			((com.kingdee.eas.framework.client.ListUI)prmtCurProject.getSelector()).setFilterForQuery(filter);
//			((com.kingdee.eas.framework.client.ListUI)prmtCurProject.getSelector()).onLoad();
//		}else {
//			prmtCurProject.getEntityViewInfo().setFilter(filter);
//			prmtCurProject.getQueryAgent().resetRuntimeEntityView();
//			prmtCurProject.setRefresh(true);
//		}
		
		
		
		
		Set authorizedOrgs = new HashSet();
		Map orgs = null;
		if(orgs==null){
			orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
		            OrgType.CostCenter,  null,  null, null);
		}
		if(orgs!=null){
			Set orgSet = orgs.keySet();
			Iterator it = orgSet.iterator();
			while(it.hasNext()){
				authorizedOrgs.add(it.next());
			}
		}
		
		EntityViewInfo view = new EntityViewInfo();
		
		view.setSelector(getCurProjectSelectorCols());
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
		
		
		/**
		 * ��֯�ܹ���:������֯��������ȷ�Ϻ����޸�
		 */
		FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.longNumber",orgUnit.getLongNumber()+"%",CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",authorizedOrgs,CompareType.INCLUDE));

		//��ϸ������Ŀ
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.valueOf(true)));
		
		view.setFilter(filter);
		prmtCurProject.setEntityViewInfo(view);
		if (prmtCurProject.getSelector() != null && prmtCurProject.getSelector() instanceof com.kingdee.eas.framework.client.ListUI) {
			((com.kingdee.eas.framework.client.ListUI)prmtCurProject.getSelector()).setFilterForQuery(filter);
			((com.kingdee.eas.framework.client.ListUI)prmtCurProject.getSelector()).onLoad();
			

		}else {
			prmtCurProject.getEntityViewInfo().setFilter(filter);
			prmtCurProject.getQueryAgent().resetRuntimeEntityView();
			prmtCurProject.setRefresh(true);
		}
	}

	
	protected void verifyInput(ActionEvent e) throws Exception {
		
		super.verifyInput(e);
		if(txtNumber.isEnabled()&&StringUtils.isEmpty(editData.getNumber()))
		{
			FDCMsgBox.showWarning(this,"���ݱ�Ų���Ϊ��");
			abort();
		}
		if(StringUtils.isEmpty(editData.getName()))
		{
			FDCMsgBox.showWarning(this,"�������Ʋ���Ϊ��");
			abort();
		}
		
//		if(editData.getCurProject() == null)
//		{
//			FDCMsgBox.showWarning(this,"������Ŀ����Ϊ��");
//			abort();
//		}
		
		if(editData.getPurchaseType() == null)
		{
			FDCMsgBox.showWarning(this,"��ѡ�񵥾����");
			abort();
		}
		if(editData.getEntrys().size() == 0)
		{
			FDCMsgBox.showWarning(this,"������¼��һ�з�¼");
			abort();
		}
		
		
		//����¼
		for(int i = 0; i < kdtEntrys.getRowCount(); ++i)
		{
			IRow row = kdtEntrys.getRow(i);
			Integer index = new Integer(i+1);
			String warning = "��" + index.toString() + "�С���" ;
			if(row.getCell(COL_MATNUM).getValue() == null)
			{
				warning = warning + "1�����ϱ��벻��Ϊ��";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			
			if(row.getCell("unit").getValue() == null)
			{
				warning = warning + "5�м�����λ����Ϊ��";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			
			if(row.getCell("amount").getValue() == null)
			{
				warning = warning + "6����������Ϊ��";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
		}
		
//		for(int rowIndex = 0; rowIndex < kdtEntrys.getRowCount(); ++rowIndex)
//		{
//			for(int j = 0; j < kdtEntrys.getRowCount(); ++ j)
//			{
//				if(rowIndex != j)
//				{
//					if(kdtEntrys.getRow(rowIndex).getCell("material.number").getValue() != null &&
//							kdtEntrys.getRow(j).getCell("material.number").getValue() != null	)
//					{
//						
//					}
//					MaterialInfo rowIndexMat = (MaterialInfo)(kdtEntrys.getRow(rowIndex).getCell("material.number").getValue());
//					
//					MaterialInfo jMat = (MaterialInfo)(kdtEntrys.getRow(j).getCell("material.number").getValue());
//					
//					if(rowIndexMat.getId().equals(jMat.getId()))
//					{
//						Integer indexInt = new Integer(rowIndex);
//						Integer jInt = new Integer(j);
//						StringBuffer buffer = new StringBuffer("��" + indexInt.toString());
//						buffer.append("�����ϱ����");
//						buffer.append("��");
//						
//						buffer.append(jInt.toString() +"�����ϱ����ظ������ܱ���");
//						FDCMsgBox.showWarning(this,buffer.toString());
//						abort();
//					}
//						
//				}
//			}
//		}
		
		//���Ҫ�ɹ����������Ƿ���ڼ׹������嵥
		if (isMustFromPartA) 	
			checkIsGreaterThanPartA();
	}
	
	public SelectorItemCollection getSelectors(){
		SelectorItemCollection sic = super.getSelectors();
		sic.add("state");
		sic.add("orgUnit.id");
		sic.add("CU.id");
		return sic;
	}

	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
}