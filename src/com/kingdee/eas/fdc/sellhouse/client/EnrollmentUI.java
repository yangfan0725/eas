/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;


import java.awt.Color;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.insider.IVipLevel;
import com.kingdee.eas.fdc.insider.VipLevelCollection;
import com.kingdee.eas.fdc.insider.VipLevelFactory;
import com.kingdee.eas.fdc.insider.VipLevelInfo;
import com.kingdee.eas.fdc.sellhouse.EnrollmentUIFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.IEnrollmentUIFacade;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class EnrollmentUI extends AbstractEnrollmentUI
{
    private static final Logger logger = CoreUIObject.getLogger(EnrollmentUI.class);

    public EnrollmentUI() throws Exception
    {
        super();
    }
    
    private CommonQueryDialog commonQueryDialog = null;
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		commonQueryDialog.setUiObject(null);
		return commonQueryDialog;
	}
    
	private EnrollmentFilterUI filterUI = null;
	private EnrollmentFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new EnrollmentFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				SysUtil.abort(e);
			}
		}
		return this.filterUI;
	}

    public void storeFields()
    {
        super.storeFields();
    }

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	
	protected void initTree() throws Exception {
		SaleOrgUnitInfo fiOrg = SysContext.getSysContext().getCurrentSaleUnit();
		String fiOrgId = fiOrg.getId().toString();
		IMetaDataPK actionPK = FDCHelper.getActionPK(this.actionOnLoad);
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.SALE,
				"", fiOrgId, null, actionPK);
		treeMain.setModel(orgTreeModel);
		treeMain.setSelectionNode((DefaultKingdeeTreeNode)orgTreeModel.getRoot());
	}
	
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		execQuery();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		actionAddNew.setEnabled(false);
		actionAddNew.setVisible(false);
		actionView.setEnabled(false);
		actionView.setVisible(false);
		actionEdit.setEnabled(false);
		actionEdit.setVisible(false);
		actionRemove.setEnabled(false);
		actionRemove.setVisible(false);
		actionRefresh.setEnabled(false);
		actionRefresh.setVisible(false);
		actionLocate.setEnabled(false);
		actionLocate.setVisible(false);
		actionPrint.setEnabled(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setEnabled(false);
		actionPrintPreview.setVisible(false);
	}
	
	protected void initTblMain() throws BOSException, EASBizException, SQLException{
		this.tblMain.removeRows(false);
//		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.removeColumns();
		this.tblMain.addHeadRow(0);
		this.tblMain.addHeadRow(1);
		IVipLevel ivip=VipLevelFactory.getRemoteInstance();
		VipLevelCollection vipColl=ivip.getVipLevelCollection();
		if(vipColl!=null && vipColl.size()>0){
			//���ݲ�ѯ��Ա�ȼ�������table
			IColumn proColumn= tblMain.addColumn();
			proColumn.setKey("saleOrgUnit");
			tblMain.getHeadRow(0).getCell("saleOrgUnit").setValue("������֯");
			tblMain.getHeadRow(1).getCell("saleOrgUnit").setValue("������֯");
			for(int i =0;i<vipColl.size();i++){
				VipLevelInfo vipInfo=vipColl.get(i);
				for(int j=0;j<4;j++){
					IColumn icolumn= tblMain.addColumn();
					String key="";
					String value="";
					if(j==0){
						key="11"+vipInfo.getNumber();
						value="�ֳ����";
					}
					if(j==1){
						key="13"+vipInfo.getNumber();;
						value="�������";
					}
					if(j==2){
						key="12"+vipInfo.getNumber();;
						value="�������";
					}
					if(j==3){
						key="00"+vipInfo.getNumber();;
						value="С��";
					}
					icolumn.setKey(key);
					tblMain.getHeadRow(0).getCell(key).setValue(vipInfo.getName());
					tblMain.getHeadRow(1).getCell(key).setValue(value);
				}
			}
			// ��ͷָ���ں�
			tblMain.getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
			// ��ȡ��ͷ�ںϹ�����
			KDTMergeManager mm = tblMain.getHeadMergeManager();
			// ����ָ���ں�
			mm.mergeBlock(0, 0, 1, 0, KDTMergeManager.SPECIFY_MERGE);
			for(int i = 0 ; i <vipColl.size();i++){
				mm.mergeBlock(0, i*4+1, 0, i*4+3+1, KDTMergeManager.SPECIFY_MERGE);
			}
			
			//��ȡ��ѡ������֯
			DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			if (orgNode == null) {
				return;
			}
			FullOrgUnitInfo fullOrgUnitInfo=null;
			if (orgNode.getUserObject() instanceof OrgStructureInfo){
				final OrgStructureInfo info = (OrgStructureInfo)orgNode.getUserObject();
				fullOrgUnitInfo = info.getUnit();
			}
			
			//TODO �ӹ��˽��������ڼӵ� bizDate �Ĺ�����,ע����ʱ����Ĵ���
			Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
			String beginTime="";
			String endTime="";
			if(filterMap.get("BeginQueryDate")!=null){
				Timestamp BeginQueryDate = (Timestamp) filterMap.get("BeginQueryDate");
				beginTime=BeginQueryDate.toString().substring(0, 10);
			}
			
			if(filterMap.get("EndQueryDate")!=null){
				Timestamp EndQueryDate = (Timestamp) filterMap.get("EndQueryDate");
				endTime=EndQueryDate.toString().substring(0, 10);
			}
			//������ѡ������֯�͹������ڣ���ѯ���и�������֯�»�Ա����ͳ��
			IEnrollmentUIFacade facade=(IEnrollmentUIFacade) EnrollmentUIFacadeFactory.getRemoteInstance();
			IRowSet rowSet= facade.getInisder(fullOrgUnitInfo.getLongNumber(),beginTime, endTime);
			while(rowSet.next()){
				String orgunitname=rowSet.getString("orgunitname");
				String enterwaynumber=rowSet.getString("enterwaynumber");
				String viplevelnumber=rowSet.getString("viplevelnumber");
				Integer count =new Integer(rowSet.getString("count"));
				if(tblMain.getRowCount()==0){
					IRow row= tblMain.addRow();
					tblMain.setRowCount(tblMain.getRowCount()+1);
					row.getCell("saleOrgUnit").setValue(orgunitname);
					//�������
					//�ж����;���Ƿ����������,nullΪ�������
					if(row.getCell(enterwaynumber+viplevelnumber)==null){
						row.getCell("12"+viplevelnumber).setValue(count);
					}else{
						row.getCell(enterwaynumber+viplevelnumber).setValue(count);
					}
					//��ϼ�
					row.getCell("00"+viplevelnumber).setValue(count);
				}else{
					boolean has=false;
					IRow oldRow=null;
					for(int i=0;i<tblMain.getRowCount();i++){
						IRow row=tblMain.getRow(i);
						String oldSaleOrgUnit=row.getCell("saleOrgUnit").getValue().toString();
						if(oldSaleOrgUnit.equals(orgunitname)){
							has=true;
							oldRow=row;
						}
					}
					if(has){
						//�������
						//�ж����;���Ƿ����������,nullΪ�������
						Integer oldAmount=new Integer(0);
						if(oldRow.getCell(enterwaynumber+viplevelnumber)==null){
							//�ж������Ƿ�Ϊ0
							if(oldRow.getCell("12"+viplevelnumber).getValue()!=null){
								oldAmount=(Integer) oldRow.getCell("12"+viplevelnumber).getValue();
							}
							Integer newAmount=new Integer(oldAmount.intValue()+count.intValue());
							oldRow.getCell("12"+viplevelnumber).setValue(newAmount);
							
						}else{
							//�ж������Ƿ�Ϊ0
							if(oldRow.getCell(enterwaynumber+viplevelnumber).getValue()!=null){
								oldAmount=(Integer) oldRow.getCell(enterwaynumber+viplevelnumber).getValue();
							}
							Integer newAmount=new Integer(oldAmount.intValue()+count.intValue());
							oldRow.getCell(enterwaynumber+viplevelnumber).setValue(newAmount);
						}
						//��ϼ�
						Integer oldCount=new Integer(0);
						//�ж������Ƿ�Ϊ0
						if(oldRow.getCell("00"+viplevelnumber).getValue()!=null){
							oldCount=(Integer) oldRow.getCell("00"+viplevelnumber).getValue();
						}
						Integer newCount=new Integer(oldCount.intValue()+count.intValue());
						oldRow.getCell("00"+viplevelnumber).setValue(newCount);
					}else{
						IRow row= tblMain.addRow();
						tblMain.setRowCount(tblMain.getRowCount()+1);
						row.getCell("saleOrgUnit").setValue(orgunitname);
						//�������
						//�ж����;���Ƿ����������,nullΪ�������
						if(row.getCell(enterwaynumber+viplevelnumber)==null){
							row.getCell("12"+viplevelnumber).setValue(count);
						}else{
							row.getCell(enterwaynumber+viplevelnumber).setValue(count);
						}
						//��ϼ�
						row.getCell("00"+viplevelnumber).setValue(count);
					}
				}
			}
			
			//���ɼ�����
            IRow footRow=null;
			KDTFootManager footRowManager= tblMain.getFootManager();
//			if (footRowManager==null)
//            {
//                String total=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
                footRowManager = new KDTFootManager(this.tblMain);
                footRowManager.addFootView();
                this.tblMain.setFootManager(footRowManager);
                footRow= footRowManager.addFootRow(0);
                footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
                this.tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
                this.tblMain.getIndexColumn().setWidth(30);
                footRowManager.addIndexText(0, "�ܼ�");
//            }else{
//                footRow=footRowManager.getFootRow(0);
//            }
			footRow.getStyleAttributes().setBackground(new Color(0xf6, 0xf6, 0xbf));
			
			//��Ӻϼ��У��ܺϼ���
			IColumn amountColumn= tblMain.addColumn();
			amountColumn.setKey("amountCount");
			mm.mergeBlock(0, tblMain.getColumnCount()-1, 1, tblMain.getColumnCount()-1, KDTMergeManager.SPECIFY_MERGE);
			tblMain.getHeadRow(0).getCell("amountCount").setValue("�ϼ�");
			Integer rowAmounyCount=new Integer(0);
			Integer amountCount=new Integer(0);
			for(int i =0;i<tblMain.getRowCount();i++){
				IRow row=tblMain.getRow(i);
				rowAmounyCount=new Integer(0);
				for(int j=0;j<vipColl.size();j++){
					if(row.getCell(j*4+4).getValue()!=null){
						Integer count=(Integer) row.getCell(j*4+4).getValue();
						rowAmounyCount=new Integer(rowAmounyCount.intValue()+count.intValue());
						if(footRow.getCell(j*4+4).getValue()!=null){
							Integer amount=(Integer) footRow.getCell(j*4+4).getValue();
							footRow.getCell(j*4+4).setValue(new Integer(amount.intValue()+count.intValue()));
						}else{
							footRow.getCell(j*4+4).setValue(count);
						}
					}
				}
				row.getCell("amountCount").setValue(rowAmounyCount);
				amountCount=new Integer(amountCount.intValue()+rowAmounyCount.intValue());
			}
			footRow.getCell("amountCount").setValue(amountCount);
//			IRow row=tblMain.addRow();
//			tblMain.setRowCount(tblMain.getRowCount()+1);			
//			row.getStyleAttributes().setBackground(Color.yellow);
//			row.getCell("saleOrgUnit").setValue("�ϼ�");
			
		}
	}
	
	protected void execQuery() {
		try {
			initTblMain();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	}
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
	}
	
	protected String getKeyFieldName() {
		return "saleOrgUnit";
	}
}