/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.CloseCompanyPeriodFacadeFactory;
import com.kingdee.eas.fi.cas.client.PeriodHelper;
import com.kingdee.eas.fm.common.client.MutexUtils;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FinanceCostClosePeriodUI extends AbstractFinanceCostClosePeriodUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FinanceCostClosePeriodUI.class);
	private PeriodInfo companyCurrentPeriod = null;
	private CompanyOrgUnitInfo companyInfo = null;
	private PeriodInfo prevPeriodInfo = null;
	String resourcePath = "com.kingdee.eas.fdc.finance.client.FinanceResource";
	private boolean isOk = false;

	/**
	 * output class constructor
	 */
	public FinanceCostClosePeriodUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		companyInfo = SysContext.getSysContext().getCurrentFIUnit();
		if (!companyInfo.isIsBizUnit()) {
			MsgBox.showError(this, "��ǰ��˾Ϊ���壬������ʹ�øù��ܡ�");
			SysUtil.abort();
		}
		if (!FDCUtils.IsInCorporation(null, companyInfo.getId().toString())) {
			MsgBox.showWarning(this, "�˲�����֯δ���óɱ��½ᣡ");
			SysUtil.abort();
		} 
		boolean flag = SystemStatusCtrolUtils.isStart(null,SystemEnum.FDC, companyInfo);
		if(!flag){
			MsgBox.showInfo(this,EASResource.getString(resourcePath, "CheckOut_Msg"));
			SysUtil.abort();
		}
		super.onLoad();
		labPic.setIcon(EASResource.getIcon("imgGuide_pic260"));
		rbClose.setSelected(true);
		actionCancel.setEnabled(true);
		companyCurrentPeriod = SystemStatusCtrolUtils
		.getCurrentPeriod(null, SystemEnum.FDC, new ObjectUuidPK(companyInfo.getId()));
		PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
		StringBuffer sb = new StringBuffer();
		sb.append(EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.currentPeriod") + companyCurrentPeriod.getPeriodYear()
				+ EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.year") + companyCurrentPeriod.getPeriodNumber()
				+ EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.period") + "\n");
        if (nextPeriod != null) {
            sb.append(EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.nextPeriod") + nextPeriod.getPeriodYear()
                    + EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.year") + nextPeriod.getPeriodNumber()
                    + EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.period") + "\n\n");
        }
        txtResult.setText(""+sb);
	}

	protected void rbClose_stateChanged(ChangeEvent e) throws Exception {
		super.rbClose_stateChanged(e);
		if (rbClose.isSelected()) {
			txtDescription.setText(EASResource.getString(
					"com.kingdee.eas.fdc.finance.client.FinanceResource",
					"companyClose"));
			if(companyCurrentPeriod!=null){
				PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
				StringBuffer sb = new StringBuffer();
				sb.append(EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.currentPeriod") + companyCurrentPeriod.getPeriodYear()
						+ EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.year") + companyCurrentPeriod.getPeriodNumber()
						+ EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.period") + "\n");
		        if (nextPeriod != null) {
		            sb.append(EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.nextPeriod") + nextPeriod.getPeriodYear()
		                    + EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.year") + nextPeriod.getPeriodNumber()
		                    + EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.period") + "\n\n");
		        }
		        txtResult.setText(""+sb);
			}
		}		
	}

	protected void rbUnClose_stateChanged(ChangeEvent e) throws Exception {
		super.rbUnClose_stateChanged(e);
		if (rbUnClose.isSelected()) {
			txtDescription.setText(EASResource.getString(
					"com.kingdee.eas.fdc.finance.client.FinanceResource",
					"companyUnClose"));
			if(companyCurrentPeriod!=null){
				PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
				StringBuffer sb = new StringBuffer();
				sb.append(EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.currentPeriod") + companyCurrentPeriod.getPeriodYear()
						+ EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.year") + companyCurrentPeriod.getPeriodNumber()
						+ EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.period") + "\n");
		        if (nextPeriod != null) {
		            sb.append(EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.nextPeriod") + nextPeriod.getPeriodYear()
		                    + EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.year") + nextPeriod.getPeriodNumber()
		                    + EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.period") + "\n\n");
		        }
		        txtResult.setText(""+sb);
			}
		}
	}

	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		super.actionConfirm_actionPerformed(e);

		if (rbClose.isSelected()) {
			CloseCompanyPeriodFacadeFactory.getRemoteInstance().closeCompany(
					SysContext.getSysContext().getCurrentFIUnit().getId().toString());
			PeriodInfo companyCurrentPeriod = SystemStatusCtrolUtils
			.getCurrentPeriod(null, SystemEnum.FDC, new ObjectUuidPK(companyInfo.getId()));
			PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
			StringBuffer sb = new StringBuffer();
			sb.append(EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.currentPeriod") + companyCurrentPeriod.getPeriodYear()
					+ EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.year") + companyCurrentPeriod.getPeriodNumber()
					+ EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.period") + "\n");
	        if (nextPeriod != null) {
	            sb.append(EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.nextPeriod") + nextPeriod.getPeriodYear()
	                    + EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.year") + nextPeriod.getPeriodNumber()
	                    + EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.period") + "\n\n");
	        }
	        txtResult.setText(""+sb);
		} else if (rbUnClose.isSelected()) {
			PeriodInfo startPeriod = SystemStatusCtrolUtils.getStartPeriod(null,SystemEnum.FDC,companyInfo);
			prevPeriodInfo = PeriodUtils.getPrePeriodInfo(companyCurrentPeriod);
//			MsgBox.showError(this,"�������ݲ��ṩ��");
//			SysUtil.abort();
//			����ȵ�һ�ڼ䣬����������
			if (companyCurrentPeriod.equals(startPeriod))
			{			    
			    //	��ǰ�ڼ��������ڼ䣬���ܷ�����	MSG_unCheckout
				MsgBox.showInfo(this,EASResource.getString(resourcePath, "MSG_unCheckout"));				
				SysUtil.abort();				
			}
			if( !PeriodHelper.isAfterStartPeriod(startPeriod, prevPeriodInfo)){
				//������ʱ�����ڼ䲻��С�������ڼ�
				MsgBox.showInfo(this,EASResource.getString(resourcePath, "AntiCheckOut_PeriodMsg"));				
				SysUtil.abort();
			}
			String companyId = companyInfo.getId().toString();
			SystemStatusCtrolUtils sscUtils = new SystemStatusCtrolUtils();			
			//��ǰ�ڼ��Ѿ����������ã�����ȡ��������[����]��
			if(sscUtils.isRelatedAccount4Client(companyId,SystemEnum.FDC)){			
				PeriodInfo  glCurPeriod=SystemStatusCtrolUtils.getCurrentPeriod(null,SystemEnum.GENERALLEDGER,companyInfo);
				if(glCurPeriod!=null && companyCurrentPeriod!=null &&  companyCurrentPeriod.getPeriodYear()==glCurPeriod.getPeriodYear()&&
						companyCurrentPeriod.getPeriodNumber()==glCurPeriod.getPeriodNumber()){
					MsgBox.showInfo(this,"���ز���ǰ�ڼ�������һ�������ܷ����ˣ�");
					SysUtil.abort();
				}
				
			   if(sscUtils.isEqualsCurPeriodAndRelPeriod4Client(companyId,SystemEnum.CASHMANAGEMENT)){
				   MsgBox.showInfo(EASResource.getString(resourcePath, "CancelCASAndGL"));
				   SysUtil.abort();
			   }
			}
			
			CloseCompanyPeriodFacadeFactory.getRemoteInstance().antiCloseCompany(
					SysContext.getSysContext().getCurrentFIUnit().getId().toString());
			PeriodInfo companyCurrentPeriod = SystemStatusCtrolUtils
			.getCurrentPeriod(null, SystemEnum.FDC, new ObjectUuidPK(companyInfo.getId()));
			PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
			StringBuffer sb = new StringBuffer();
			sb.append(EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.currentPeriod") + companyCurrentPeriod.getPeriodYear()
					+ EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.year") + companyCurrentPeriod.getPeriodNumber()
					+ EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.period") + "\n");
	        if (nextPeriod != null) {
	            sb.append(EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.nextPeriod") + nextPeriod.getPeriodYear()
	                    + EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.year") + nextPeriod.getPeriodNumber()
	                    + EASResource.getString("com.kingdee.eas.fdc.finance.client.FinanceResource.period") + "\n\n");
	        }
	        txtResult.setText(""+sb);
		}
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
		setConfirm(false);
//		disposeUIWindow();
		super.destroyWindow();
	}

	public void setConfirm(boolean isOk) {
		this.isOk = isOk;
//		disposeUIWindow();
		super.destroyWindow();
	}
}