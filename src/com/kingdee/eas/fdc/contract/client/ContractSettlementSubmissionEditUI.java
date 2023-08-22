/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementSubmissionFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementSubmissionInfo;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractSettlementSubmissionEditUI extends AbstractContractSettlementSubmissionEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractSettlementSubmissionEditUI.class);
    
    public ContractSettlementSubmissionEditUI() throws Exception
    {
        super();
    }
	@Override
	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return ContractSettlementSubmissionFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}
	public void loadFields() {
		super.loadFields();
		try {
			fillAttachmnetTable();
		} catch (EASBizException e) {
			handleException(e);
		} catch (BOSException e) {
			handleException(e);
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.btnCalculator.setVisible(true);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
		this.actionAuditResult.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		this.prmtCurProject.setEnabled(false);
		this.txtPartA.setEnabled(false);
		this.txtPartB.setEnabled(false);
		this.pkSignDate.setEnabled(false);
		
		CurProjectInfo curProjct=this.editData.getCurProject();
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",curProjct.getId().toString()));
		view.setFilter(filter);
		this.prmtContractInfo.setEntityViewInfo(view);
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic=super.getSelectors();
		sic.add("state");
		return sic;
	}
	protected void prmtContractInfo_dataChanged(DataChangeEvent e)throws Exception {
		ContractBillInfo info=(ContractBillInfo) this.prmtContractInfo.getValue();
		if(info!=null){
			info=ContractBillFactory.getRemoteInstance().getContractBillInfo("select partB.name,landDeveloper.name,bookedDate from where id='"+info.getId().toString()+"'");
			this.txtPartB.setText(info.getPartB().getName());
			this.txtPartA.setText(info.getLandDeveloper().getName());
			this.pkSignDate.setValue(info.getBookedDate());
		}else{
			this.txtPartB.setText(null);
			this.txtPartA.setText(null);
			this.pkSignDate.setValue(null);
		}
	}
	public void fillAttachmnetTable() throws EASBizException, BOSException {
			this.tblAttachement.removeRows();
			String boId = null;
			if (this.editData.getId() == null) {
				return;
			} else {
				boId = this.editData.getId().toString();
			}

			if (boId != null) {
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("id"));
				sic.add(new SelectorItemInfo("attachment.id"));
				sic.add(new SelectorItemInfo("attachment.name"));
				sic.add(new SelectorItemInfo("attachment.createTime"));
				sic.add(new SelectorItemInfo("attachment.attachID"));
				sic.add(new SelectorItemInfo("attachment.beizhu"));
				sic.add(new SelectorItemInfo("assoType"));
				sic.add(new SelectorItemInfo("boID"));

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("boID", boId));
				EntityViewInfo evi = new EntityViewInfo();
				evi.getSorter().add(new SorterItemInfo("boID"));
				evi.getSorter().add(new SorterItemInfo("attachment.name"));
				evi.setFilter(filter);
				evi.setSelector(sic);
				BoAttchAssoCollection cols = null;
				try {
					cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
				} catch (BOSException e) {
					e.printStackTrace();
				}
				boolean flag = false;
				if (cols != null && cols.size() > 0) {
					tblAttachement.checkParsed();
					for (Iterator it = cols.iterator(); it.hasNext();) {
						BoAttchAssoInfo boaInfo = (BoAttchAssoInfo)it.next();
						AttachmentInfo attachment = boaInfo.getAttachment();
						IRow row = tblAttachement.addRow();
						row.getCell("id").setValue(attachment.getId().toString());
						row.getCell("name").setValue(attachment.getName());
					}
				}
			}
		}
	 public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
    		super.actionWorkFlowG_actionPerformed(e);
		}
	 protected void tblAttachement_tableClicked(KDTMouseEvent e)throws Exception {
			if(e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2)
			{
				IRow row  =  tblAttachement.getRow(e.getRowIndex());
				getFileGetter();
				Object selectObj= row.getCell("id").getValue();
				if(selectObj!=null){
					String attachId=selectObj.toString();
					fileGetter.viewAttachment(attachId);
				}
				
			}
	}
	 private  FileGetter fileGetter;
		private  FileGetter getFileGetter() throws Exception {
	        if (fileGetter == null)
	            fileGetter = new FileGetter((IAttachment) AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
	        return fileGetter;
	    }
		@Override
		protected IObjectValue createNewData() {
			ContractSettlementSubmissionInfo info=new ContractSettlementSubmissionInfo();
			BOSUuid projId=(BOSUuid) this.getUIContext().get("projectId");
			try {
				info.setCurProject(CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projId)));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			return info;
		}
		
}