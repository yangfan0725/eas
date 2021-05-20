/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.BasicNumberTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.sellhouse.LoanCalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.LoanTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MortgageYearEnum;
import com.kingdee.eas.fdc.sellhouse.PaymentMethodEnum;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class MortgageCalcUI extends AbstractMortgageCalcUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8903085833445972192L;
	private static final Logger logger = CoreUIObject
			.getLogger(MortgageCalcUI.class);

	/**
	 * output class constructor
	 */
	public MortgageCalcUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		SpinnerNumberModel fol = new SpinnerNumberModel(1,0.01,2.01,0.10); //add by shilei
		this.texInterest.setEditable(false);
		this.numfloating.setModel(fol);
		this.txtRate.setValue(new Float(1));
		String companyID=SysContext.getSysContext().getCurrentSaleUnit().getId().toString();
		IObjectPK comPK = new ObjectUuidPK(BOSUuid.read(companyID));
		String str=ParamControlFactory.getRemoteInstance().getParamValue(comPK, "FDCSHE130_BANK");
		if(null!=str){
			texInterest.setText(str);
		}
		this.lblShangYeDaikuan.setVisible(false);
		this.kDLabelContainer1.setVisible(false);
		this.lblMonthTwo.setVisible(false);
		this.lblLoanTotalMoney.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionAbout.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.tbMain.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionVoucher.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionViewSignature.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionViewSubmitProccess.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.txtreserveRate.setEditable(false);
		this.actionCalc.setVisible(true);
		this.actionClear.setVisible(true);
		this.btnCalc.setEnabled(true);
		this.btnClear.setEnabled(true);
		txtTotalLoan.setDataType(BasicNumberTextField.BIGDECIMAL_TYPE);
		txtTotalLoan.setPrecision(2);
		txtTotalLoan.setRoundingMode(2);
		txtLixi.setDataType(BasicNumberTextField.BIGDECIMAL_TYPE);
		txtLixi.setPrecision(2);
		txtLixi.setRoundingMode(2);
		txtMonthReturn.setDataType(BasicNumberTextField.BIGDECIMAL_TYPE);
		txtMonthReturn.setPrecision(2);
		txtMonthReturn.setRoundingMode(2);
		txtShouqi.setDataType(BasicNumberTextField.BIGDECIMAL_TYPE);
		txtShouqi.setPrecision(2);
		txtShouqi.setRoundingMode(2);
		txtreserveRate.setDataType(BasicNumberTextField.BIGDECIMAL_TYPE);
		txtreserveRate.setPrecision(2);
		txtreserveRate.setRoundingMode(2);
		txtTotal.setEditable(false);
    	txtTotalLoan.setEditable(false);
    	txtReturn.setEditable(false);
		txtLixi.setEditable(false);
		txtShouqi.setEditable(false);
		txtLoanMonth.setEditable(false);
		txtMonthReturn.setEditable(false);
		this.tbMain.setEditable(false);
		cbcLoan.setSelectedIndex(5);
		cbcMonageYear.setSelectedIndex(19);
		this.tbMain.getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_NUMBER_FTM);
		
		//����¥���
		BigDecimal totalAmount =(BigDecimal) this.getUIContext().get("totalAmount");
		txtTotal.setValue(totalAmount);
		this.lblCalcType.setVisible(false);
		this.lblPrice.setVisible(false);
		this.lblare.setVisible(false);
		this.kDLabel1.setVisible(false);
		this.kDLabel2.setVisible(false);
		cbcCalcType.setSelectedIndex(1);
		lblMoange.setVisible(true);
		BigDecimal aFundAmount =(BigDecimal) this.getUIContext().get("aFundAmount");
		BigDecimal loanAmount =(BigDecimal) this.getUIContext().get("loanAmount");
		if(aFundAmount!=null&&loanAmount!=null){
			if(aFundAmount.compareTo(FDCHelper.ZERO) > 0 && loanAmount.compareTo(FDCHelper.ZERO) > 0){
				this.cbcLoanType.setSelectedIndex(2);
			}else if(aFundAmount.compareTo(FDCHelper.ZERO) == 0 && loanAmount.compareTo(FDCHelper.ZERO) > 0){
				this.cbcLoanType.setSelectedIndex(0);
			}else if(aFundAmount.compareTo(FDCHelper.ZERO) > 0 && loanAmount.compareTo(FDCHelper.ZERO) == 0){
				this.cbcLoanType.setSelectedIndex(1);
			}
		}
	}
	
	public void loadFields(){
		super.loadFields();
		
	}
	protected void checkIsOUSealUp() throws Exception {
		// super.checkIsOUSealUp();
	}
	/**
	 * output actionCalc_actionPerformed
	 */
	public void actionCalc_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalc_actionPerformed(e);

		if (this.cbcType.getSelectedItem() != null) {
			PaymentMethodEnum type = (PaymentMethodEnum) this.cbcType
					.getSelectedItem();
			if (type.equals(PaymentMethodEnum.InterestOnPrincipal)) {
				// �ȶϢ
				calcInterestOnPrincipal();
			} else {
				// �ȶ��
				calcCapital();
			}
		}
	}

	/**
	 * output actionClear_actionPerformed
	 */
	public void actionClear_actionPerformed(ActionEvent e) throws Exception {
		super.actionClear_actionPerformed(e);
		clearData();
	}

	protected KDTable getDetailTable() {
		return null;
	}
	protected void numfloatingstate_Changed(ChangeEvent e) throws Exception {   //add by shilei
		super.numfloatingstate_Changed(e);
		LoanTypeEnum loanTypeEnum = (LoanTypeEnum)cbcLoanType.getSelectedItem();
		String companyID=SysContext.getSysContext().getCurrentSaleUnit().getId().toString();
		IObjectPK comPK = new ObjectUuidPK(BOSUuid.read(companyID));
		float m = Float.parseFloat(ParamControlFactory.getRemoteInstance().getParamValue(comPK, "FDCSHE130_BANK"));
		float numfloat = Float.parseFloat(numfloating.getValue().toString());
		if(loanTypeEnum.equals(loanTypeEnum.accommodation)){ //����ǹ����𰴽�
			txtreserveRate.setValue(new Float(m*numfloat));
		}else if(loanTypeEnum.equals(loanTypeEnum.business)){//�������ҵ����
			txtRate.setValue(new Float(m*numfloat));
		}else if(loanTypeEnum.equals(loanTypeEnum.combination)){
			txtRate.setValue(new Float(m*numfloat));
			txtreserveRate.setValue(new Float(m*numfloat));
		}
	}
	public void cbcLoanType_itemStateChanged(java.awt.event.ItemEvent e) { 
		if (e.getItem() != null) {
			LoanTypeEnum type = (LoanTypeEnum) e.getItem();
			BigDecimal aFundAmount =(BigDecimal) this.getUIContext().get("aFundAmount");
			BigDecimal loanAmount =(BigDecimal) this.getUIContext().get("loanAmount");
			if (type.equals(LoanTypeEnum.combination)) {
				this.txtRate.setValue(new BigDecimal(1)); //add by shilei
				this.txtreserveRate.setValue(new BigDecimal(1));
				this.lblCalcType.setVisible(false);
				this.lblPrice.setVisible(false);
				this.lblare.setVisible(false);
				this.lblLoanTotalMoney.setVisible(false);
				this.kDLabelContainer1.setVisible(true);
				this.lblShangYeDaikuan.setVisible(true);
				kDLabel1.setVisible(false);
				kDLabel2.setVisible(false);
				this.txtreserveRate.setEditable(true);
				this.txtRate.setEditable(true);
				this.lblMoange.setVisible(false);
//				lblMoageYear.setBounds(21, 150, 270, 19);
//				this.lblMoageYear.setVisible(false);
//				lblLoanTotalMoney.setVisible(true);
				this.txtShangyeDaikuan.setValue(loanAmount);
				this.txtGongjiJinDaikuan.setValue(aFundAmount);
				
				lblMoageYear.setBounds(335, 69, 270, 19);
				this.lblMoageYear.setVisible(true);
				
			} else if (type.equals(LoanTypeEnum.accommodation)) {
				this.txtRate.setValue(null);    //add by shilei
				this.txtreserveRate.setValue(new BigDecimal(1));
				this.lblLoanTotalMoney.setVisible(false);
				this.kDLabelContainer1.setVisible(false);
				this.lblShangYeDaikuan.setVisible(false);
				this.txtreserveRate.setEditable(true);
				this.txtRate.setEditable(false);
//				this.cbcCalcType.setSelectedIndex(0);
				this.lblMoange.setVisible(true);
//				lblMoageYear.setBounds(335, 152, 270, 19);
				this.lblPrice.setVisible(false);
				this.lblare.setVisible(false);
				this.kDLabel1.setVisible(false);
				this.kDLabel2.setVisible(false);
				lblLoanTotalMoney.setVisible(true); 
//				txtreserveRate.setEnabled(true);
				this.txtLoanTotalMoney.setValue(aFundAmount);
				
				lblMoageYear.setBounds(335, 69, 270, 19);
				this.lblMoageYear.setVisible(true);
			} else if (type.equals(LoanTypeEnum.business)) {
				this.txtreserveRate.setValue(null);
				this.txtRate.setValue(new BigDecimal(1));  //add by  shilei
				this.lblLoanTotalMoney.setVisible(false);
				this.kDLabelContainer1.setVisible(false);
				this.lblShangYeDaikuan.setVisible(false);
				this.txtreserveRate.setEditable(false);
				this.txtRate.setEditable(true);
//				this.cbcCalcType.setSelectedIndex(0);
				this.lblMoange.setVisible(true);
//				lblMoageYear.setBounds(335, 152, 270, 19);
				lblLoanTotalMoney.setVisible(true);
				this.txtLoanTotalMoney.setValue(loanAmount);
				
				lblMoageYear.setBounds(335, 69, 270, 19);
				this.lblMoageYear.setVisible(true);
			}
		}
		
		clearData();
	}

	public void cbcCalcType_itemStateChanged(java.awt.event.ItemEvent e) {
		if (e.getItem() != null) {

			LoanCalcTypeEnum type = (LoanCalcTypeEnum) e.getItem();

			if (type.equals(LoanCalcTypeEnum.TotalMoney)) {
				this.lblPrice.setVisible(false);
				this.lblare.setVisible(false);
				this.lblLoanTotalMoney.setVisible(true);
				this.kDLabelContainer1.setVisible(false);
				this.lblShangYeDaikuan.setVisible(false);
				kDLabel1.setVisible(false);
				kDLabel2.setVisible(false);
				// this.lblMoageYear.setVisible(false);
				this.lblMoange.setVisible(false);
				this.lblrate.setBounds(21, 150, 270, 19);
				this.lblreserveRate.setBounds(21, 187, 270, 19);
			} else {
				this.lblPrice.setVisible(true);
				this.lblare.setVisible(true);
				this.lblLoanTotalMoney.setVisible(false);
				this.kDLabelContainer1.setVisible(false);
				this.lblShangYeDaikuan.setVisible(false);
				kDLabel1.setVisible(true);
				kDLabel2.setVisible(true);
				// this.lblMoageYear.setVisible(true);
				this.lblMoange.setVisible(true);
				this.lblrate.setBounds(21, 187, 270, 19);
				this.lblreserveRate.setBounds(335, 187, 270, 19);
			}
		}
	}

	public void cbcType_itemStateChanged(java.awt.event.ItemEvent e) {
		if (e.getItem() != null) {

			PaymentMethodEnum type = (PaymentMethodEnum) e.getItem();

			if (type.equals(PaymentMethodEnum.Principal)) {
				kDLabelContainer13.setVisible(false);
				this.lblMonthTwo.setVisible(true);
				this.tbMain.setVisible(true);
				this.kDScrollPane1.setVisible(true);
			} else {
				kDLabelContainer13.setVisible(true);
				this.lblMonthTwo.setVisible(false);
				this.tbMain.setVisible(false);
				this.kDScrollPane1.setVisible(false);
			}
		}

		clearData();
	}

	private void clearData() {
		this.txtPrice.setValue(null);
		this.txtArea.setValue(null);
//		this.txtRate.setValue(null);
//		this.txtreserveRate.setValue(null);
//		this.txtTotal.setValue(null);
//		this.txtTotalLoan.setValue(null);
		this.txtReturn.setValue(null);
		this.txtLixi.setValue(null);
		this.txtShouqi.setValue(null);
		this.txtLoanMonth.setValue(null);
		this.txtMonthReturn.setValue(null);
		this.tbMain.removeRows();
//		this.txtLoanTotalMoney.setValue(null);
//		this.txtShangyeDaikuan.setValue(null);
//		this.txtGongjiJinDaikuan.setValue(null);
	}

	/**
	 * �ȶ��
	 */
	private void calcCapital() {

		this.tbMain.removeRows();
		// ���
//		BigDecimal area = FDCHelper.ZERO;
		// ����
//		BigDecimal price = FDCHelper.ZERO;
		// �����ܶ�
		BigDecimal loanTotal = FDCHelper.ZERO;

		// ����
		BigDecimal rate = FDCHelper.ZERO;

		LoanTypeEnum loanType = (LoanTypeEnum) this.cbcLoanType
				.getSelectedItem();

		// ��ҵ����
		if (loanType.equals(LoanTypeEnum.business)) {
			if (this.txtRate.getBigDecimalValue() != null) {
				rate = this.txtRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "��ҵ���ʲ���Ϊ�գ�");
				SysUtil.abort();
			}
			if (rate.compareTo(FDCHelper.ZERO) <= 0) {
				FDCMsgBox.showWarning(this, "��ҵ���ʲ���С��0!");
				SysUtil.abort();
			}
			if (rate.compareTo(FDCHelper.ONE_HUNDRED) >0) {
				FDCMsgBox.showWarning(this, "��ҵ���ʲ��ܴ���100!");
				SysUtil.abort();
			}

//			LoanCalcTypeEnum calcType = (LoanCalcTypeEnum) this.cbcCalcType
//					.getSelectedItem();
//			// ��������͵��ۼ���
//			if (calcType.equals(LoanCalcTypeEnum.AreaAndPrice)) {
//				if (this.txtArea.getBigDecimalValue() != null) {
//					area = this.txtArea.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "�������Ϊ�գ�");
//					SysUtil.abort();
//				}
//				if (this.txtPrice.getBigDecimalValue() != null) {
//					price = this.txtPrice.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "���۲���Ϊ�գ�");
//					SysUtil.abort();
//				}
//				if(area.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "�������С��0��");
//					SysUtil.abort();
//				}
//				if(price.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "���۲���С��0��");
//					SysUtil.abort();
//				}
//
//				BigDecimal total = FDCHelper.multiply(area, price);

//				if (this.cbcLoan.getSelectedItem() != null) {
//					PercentageEnum percent = (PercentageEnum) this.cbcLoan
//							.getSelectedItem();
//
//					BigDecimal percentBig = FDCHelper.divide(new BigDecimal(
//							percent.getValue()), new BigDecimal(String
//							.valueOf("10")), BigDecimal.ROUND_UNNECESSARY, 2);
//
//					total = FDCHelper.multiply(total, percentBig);
//				}
//
//				if (this.cbcMonageYear.getSelectedItem() != null) {
//
//					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
//							.getSelectedItem();
//					int nianXian = Integer.parseInt(year.getValue());
//
//					// ������
//					BigDecimal rateMonth = FDCHelper.divide(rate,
//							new BigDecimal(String.valueOf("100")),
//							BigDecimal.ROUND_UNNECESSARY, 2);
//
//					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
//							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
//					// ����ÿ�¹̶���������
//					BigDecimal firstPart = FDCHelper.divide(total,
//							new BigDecimal(String.valueOf(nianXian * 12)));
//					BigDecimal zongji = FDCHelper.ZERO;
//					
//					for (int i = 1; i <= nianXian * 12; i++) {
//						// ������-1��
//						BigDecimal one = FDCHelper.subtract(String.valueOf(i),
//								String.valueOf("1"));
//						// ������/�������ޣ�
//						BigDecimal two = FDCHelper.divide(total,
//								new BigDecimal(String.valueOf(nianXian * 12)),
//								BigDecimal.ROUND_UNNECESSARY, 2);
//						// ������/�������ޣ�*������
//						BigDecimal three = FDCHelper.multiply(two, rateMonth);
//						// ������-1��������/�������ޣ�*������
//						BigDecimal four = FDCHelper.multiply(one, three);
//						// (����*������-������-1��������/�������ޣ�*������)
//						BigDecimal five = FDCHelper.subtract(FDCHelper
//								.multiply(total, rateMonth), four);
//
//						BigDecimal result = FDCHelper.add(firstPart, five);
//
//						IRow row = this.tbMain.addRow();
//						if (row != null) {
//							row.getCell("qishu").setValue(i + "��");
//							row.getCell("price").setValue(result);
//						}
//						zongji = FDCHelper.add(zongji, result);
//					}
//					
//					//����
//					txtTotal.setValue(FDCHelper.multiply(area, price));
//					//�����ܶ�
//					this.txtTotalLoan.setValue(total);
//					//�����ܶ�
//					txtReturn.setValue(zongji);
//					//��Ϣ
//					txtLixi.setValue(FDCHelper.subtract(zongji, total));
//					
//					//����
//					txtShouqi.setValue(FDCHelper.subtract(FDCHelper.multiply(area, price), total));
//					
//					//��������
//					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
//				}
//				// ���մ����ܶ�
//			} else {
				if (this.txtLoanTotalMoney.getBigDecimalValue() != null) {
					loanTotal = this.txtLoanTotalMoney.getBigDecimalValue();
				} else {
					FDCMsgBox.showWarning(this, "�����ܶ��Ϊ�գ�");
					SysUtil.abort();
				}

				if (this.cbcMonageYear.getSelectedItem() != null) {

					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
							.getSelectedItem();
					int nianXian = Integer.parseInt(year.getValue());

					// ������
					BigDecimal rateMonth = FDCHelper.divide(rate,
							new BigDecimal(String.valueOf("100")),
							BigDecimal.ROUND_UNNECESSARY, 2);

					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
					// ����ÿ�¹̶���������
					BigDecimal firstPart = FDCHelper.divide(loanTotal,
							new BigDecimal(String.valueOf(nianXian * 12)));

					BigDecimal zongji  = FDCHelper.ZERO;
					
					for (int i = 1; i <= nianXian * 12; i++) {
						// ������-1��
						BigDecimal one = FDCHelper.subtract(String.valueOf(i),
								String.valueOf("1"));
						// ������/�������ޣ�
						BigDecimal two = FDCHelper.divide(loanTotal,
								new BigDecimal(String.valueOf(nianXian * 12)),
								BigDecimal.ROUND_UNNECESSARY, 2);
						// ������/�������ޣ�*������
						BigDecimal three = FDCHelper.multiply(two, rateMonth);
						// ������-1��������/�������ޣ�*������
						BigDecimal four = FDCHelper.multiply(one, three);
						// (����*������-������-1��������/�������ޣ�*������)
						BigDecimal five = FDCHelper.subtract(FDCHelper
								.multiply(loanTotal, rateMonth), four);

						BigDecimal result = FDCHelper.add(firstPart, five);

						IRow row = this.tbMain.addRow();
						if (row != null) {
							row.getCell("qishu").setValue(i + "��");
							row.getCell("price").setValue(result);
						}
						
						zongji = FDCHelper.add(zongji, result);
					}
					
					//����
//					txtTotal.setValue(null);
					//�����ܶ�
					this.txtTotalLoan.setValue(loanTotal);
					//�����ܶ�
					txtReturn.setValue(zongji);
					//��Ϣ
					txtLixi.setValue(FDCHelper.subtract(zongji, loanTotal));
					
					//����
					txtShouqi.setValue(FDCHelper.ZERO);
					
					//��������
					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
					
				}
//			}
			// ������
		} else if (loanType.equals(LoanTypeEnum.accommodation)) {

			if (this.txtreserveRate.getBigDecimalValue() != null) {
				rate = this.txtreserveRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "��������ʲ���Ϊ�գ�");
				SysUtil.abort();
			}

			if (rate.compareTo(FDCHelper.ZERO) <=0) {
				FDCMsgBox.showWarning(this, "��������ʲ���С��0!");
				SysUtil.abort();
			}
			if (rate.compareTo(FDCHelper.ONE_HUNDRED)>0) {
				FDCMsgBox.showWarning(this, "��������ʲ��ܴ���100!");
				SysUtil.abort();
			}

//			LoanCalcTypeEnum calcType = (LoanCalcTypeEnum) this.cbcCalcType
//					.getSelectedItem();
//			// ��������͵��ۼ���
//			if (calcType.equals(LoanCalcTypeEnum.AreaAndPrice)) {
//				if (this.txtArea.getBigDecimalValue() != null) {
//					area = this.txtArea.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "�������Ϊ�գ�");
//					SysUtil.abort();
//				}
//				if (this.txtPrice.getBigDecimalValue() != null) {
//					price = this.txtPrice.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "���۲���Ϊ�գ�");
//					SysUtil.abort();
//				}
//				if(area.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "�������С��0��");
//					SysUtil.abort();
//				}
//				if(price.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "���۲���С��0��");
//					SysUtil.abort();
//				}
//
//				BigDecimal total = FDCHelper.multiply(area, price);
//
//				if (this.cbcLoan.getSelectedItem() != null) {
//					PercentageEnum percent = (PercentageEnum) this.cbcLoan
//							.getSelectedItem();
//
//					BigDecimal percentBig = FDCHelper.divide(new BigDecimal(
//							percent.getValue()), new BigDecimal(String
//							.valueOf("10")), BigDecimal.ROUND_UNNECESSARY, 2);
//
//					total = FDCHelper.multiply(total, percentBig);
//				}
//
//				if (this.cbcMonageYear.getSelectedItem() != null) {
//
//					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
//							.getSelectedItem();
//					int nianXian = Integer.parseInt(year.getValue());
//
//					// ������
//					BigDecimal rateMonth = FDCHelper.divide(rate,
//							new BigDecimal(String.valueOf("100")),
//							BigDecimal.ROUND_UNNECESSARY, 2);
//
//					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
//							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
//					// ����ÿ�¹̶���������
//					BigDecimal firstPart = FDCHelper.divide(total,
//							new BigDecimal(String.valueOf(nianXian * 12)));
//
//					BigDecimal zongji = FDCHelper.ZERO;
//					
//					for (int i = 1; i <= nianXian * 12; i++) {
//						// ������-1��
//						BigDecimal one = FDCHelper.subtract(String.valueOf(i),
//								String.valueOf("1"));
//						// ������/�������ޣ�
//						BigDecimal two = FDCHelper.divide(total,
//								new BigDecimal(String.valueOf(nianXian * 12)),
//								BigDecimal.ROUND_UNNECESSARY, 2);
//						// ������/�������ޣ�*������
//						BigDecimal three = FDCHelper.multiply(two, rateMonth);
//						// ������-1��������/�������ޣ�*������
//						BigDecimal four = FDCHelper.multiply(one, three);
//						// (����*������-������-1��������/�������ޣ�*������)
//						BigDecimal five = FDCHelper.subtract(FDCHelper
//								.multiply(total, rateMonth), four);
//
//						BigDecimal result = FDCHelper.add(firstPart, five);
//
//						IRow row = this.tbMain.addRow();
//						if (row != null) {
//							row.getCell("qishu").setValue(i + "��");
//							row.getCell("price").setValue(result);
//						}
//						
//						zongji = FDCHelper.add(zongji, result);
//					}
//					
//					//����
//					txtTotal.setValue(FDCHelper.multiply(area, price));
//					//�����ܶ�
//					this.txtTotalLoan.setValue(total);
//					//�����ܶ�
//					txtReturn.setValue(zongji);
//					//��Ϣ
//					txtLixi.setValue(FDCHelper.subtract(zongji, total));
//					
//					//����
//					txtShouqi.setValue(FDCHelper.subtract(FDCHelper.multiply(area, price), total));
//					
//					//��������
//					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
//				}
//				// ���մ����ܶ�
//			} else {
				if (this.txtLoanTotalMoney.getBigDecimalValue() != null) {
					loanTotal = this.txtLoanTotalMoney.getBigDecimalValue();
				} else {
					FDCMsgBox.showWarning(this, "�����ܶ��Ϊ�գ�");
					SysUtil.abort();
				}

				if (this.cbcMonageYear.getSelectedItem() != null) {

					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
							.getSelectedItem();
					int nianXian = Integer.parseInt(year.getValue());

					// ������
					BigDecimal rateMonth = FDCHelper.divide(rate,
							new BigDecimal(String.valueOf("100")),
							BigDecimal.ROUND_UNNECESSARY, 2);

					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
					// ����ÿ�¹̶���������
					BigDecimal firstPart = FDCHelper.divide(loanTotal,
							new BigDecimal(String.valueOf(nianXian * 12)));

					BigDecimal zongji = FDCHelper.ZERO;
					
					for (int i = 1; i <= nianXian * 12; i++) {
						// ������-1��
						BigDecimal one = FDCHelper.subtract(String.valueOf(i),
								String.valueOf("1"));
						// ������/�������ޣ�
						BigDecimal two = FDCHelper.divide(loanTotal,
								new BigDecimal(String.valueOf(nianXian * 12)),
								BigDecimal.ROUND_UNNECESSARY, 2);
						// ������/�������ޣ�*������
						BigDecimal three = FDCHelper.multiply(two, rateMonth);
						// ������-1��������/�������ޣ�*������
						BigDecimal four = FDCHelper.multiply(one, three);
						// (����*������-������-1��������/�������ޣ�*������)
						BigDecimal five = FDCHelper.subtract(FDCHelper
								.multiply(loanTotal, rateMonth), four);

						BigDecimal result = FDCHelper.add(firstPart, five);

						IRow row = this.tbMain.addRow();
						if (row != null) {
							row.getCell("qishu").setValue(i + "��");
							row.getCell("price").setValue(result);
						}
						zongji = FDCHelper.add(zongji, result);
					}
					
					//����
//					txtTotal.setValue(null);
					//�����ܶ�
					this.txtTotalLoan.setValue(loanTotal);
					//�����ܶ�
					txtReturn.setValue(zongji);
					//��Ϣ
					txtLixi.setValue(FDCHelper.subtract(zongji, loanTotal));
					
					//����
					txtShouqi.setValue(FDCHelper.ZERO);
					
					//��������
					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
				}
//			}
			// ���
		} else if (loanType.equals(LoanTypeEnum.combination)) {
			/**
			 * �������
			 */

			BigDecimal shangye = FDCHelper.ZERO;
			BigDecimal gongjijin = FDCHelper.ZERO;

			BigDecimal shangyeDaikKuan = FDCHelper.ZERO;
			BigDecimal gongjijinDaiKuan = FDCHelper.ZERO;

			BigDecimal shangyeRateMonth = FDCHelper.ZERO;
			BigDecimal gongjijinRateMonth = FDCHelper.ZERO;

			if (this.txtShangyeDaikuan.getBigDecimalValue() != null) {
				shangyeDaikKuan = this.txtShangyeDaikuan.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "��ҵ�����Ϊ�գ�");
				SysUtil.abort();
			}
			if (this.txtGongjiJinDaikuan.getBigDecimalValue() != null) {
				gongjijinDaiKuan = this.txtGongjiJinDaikuan
						.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "����������Ϊ�գ�");
				SysUtil.abort();
			}
			if (this.txtRate.getBigDecimalValue() != null) {
				shangye = this.txtRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "��ҵ���ʲ���Ϊ�գ�");
				SysUtil.abort();
			}
			if (shangye.compareTo(FDCHelper.ZERO) <= 0) {
				FDCMsgBox.showWarning(this, "��ҵ���ʲ���С��0!");
				SysUtil.abort();
			}
			if (shangye.compareTo(FDCHelper.ONE_HUNDRED) >0) {
				FDCMsgBox.showWarning(this, "��ҵ���ʲ��ܴ���100!");
				SysUtil.abort();
			}
			if (this.txtreserveRate.getBigDecimalValue() != null) {
				gongjijin = this.txtreserveRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "��������ʲ���Ϊ�գ�");
				SysUtil.abort();
			}
			if (gongjijin.compareTo(FDCHelper.ZERO) <= 0) {
				FDCMsgBox.showWarning(this, "��������ʲ���С��0!");
				SysUtil.abort();
			}
			if (gongjijin.compareTo(FDCHelper.ONE_HUNDRED) >0) {
				FDCMsgBox.showWarning(this, "��������ʲ��ܴ���100!");
				SysUtil.abort();
			}
			BigDecimal total  = FDCHelper.add(shangyeDaikKuan, gongjijinDaiKuan);
			
			shangyeRateMonth = FDCHelper.divide(shangye, new BigDecimal(String
					.valueOf("100")), BigDecimal.ROUND_UNNECESSARY, 2);
			gongjijinRateMonth = FDCHelper.divide(gongjijin, new BigDecimal(
					String.valueOf("100")), BigDecimal.ROUND_UNNECESSARY, 2);
			shangyeRateMonth = FDCHelper.divide(shangyeRateMonth,
					new BigDecimal(String.valueOf("12")),
					BigDecimal.ROUND_UNNECESSARY, 2);
			gongjijinRateMonth = FDCHelper.divide(gongjijinRateMonth,
					new BigDecimal(String.valueOf("12")),
					BigDecimal.ROUND_UNNECESSARY, 2);

			MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
					.getSelectedItem();
			int nianXian = Integer.parseInt(year.getValue());

			// ����ÿ�¹̶���������
			BigDecimal firstPartShangye = FDCHelper.divide(shangyeDaikKuan,
					new BigDecimal(String.valueOf(nianXian * 12)));
			BigDecimal firstPartgongjijinDaiKuan = FDCHelper.divide(
					gongjijinDaiKuan, new BigDecimal(String
							.valueOf(nianXian * 12)));
			
			BigDecimal zongji = FDCHelper.ZERO;
			
			for (int i = 1; i <= nianXian * 12; i++) {

				/**
				 * ����Ϊ��ҵ�����
				 */
				// ������-1��
				BigDecimal one = FDCHelper.subtract(String.valueOf(i), String
						.valueOf("1"));
				// ������/�������ޣ�
				BigDecimal two = FDCHelper.divide(shangyeDaikKuan,
						new BigDecimal(String.valueOf(nianXian * 12)),
						BigDecimal.ROUND_UNNECESSARY, 2);
				// ������/�������ޣ�*������
				BigDecimal three = FDCHelper.multiply(two, shangyeRateMonth);
				// ������-1��������/�������ޣ�*������
				BigDecimal four = FDCHelper.multiply(one, three);
				// (����*������-������-1��������/�������ޣ�*������)
				BigDecimal five = FDCHelper.subtract(FDCHelper.multiply(
						shangyeDaikKuan, shangyeRateMonth), four);

				BigDecimal result1 = FDCHelper.add(firstPartShangye, five);

				/**
				 * ����Ϊ�����𲿷�
				 */
				// ������/�������ޣ�
				BigDecimal two1 = FDCHelper.divide(gongjijinDaiKuan,
						new BigDecimal(String.valueOf(nianXian * 12)),
						BigDecimal.ROUND_UNNECESSARY, 2);
				// ������/�������ޣ�*������
				BigDecimal three1 = FDCHelper
						.multiply(two1, gongjijinRateMonth);
				// ������-1��������/�������ޣ�*������
				BigDecimal four1 = FDCHelper.multiply(one, three1);
				// (����*������-������-1��������/�������ޣ�*������)
				BigDecimal five1 = FDCHelper.subtract(FDCHelper.multiply(
						gongjijinDaiKuan, gongjijinRateMonth), four1);

				BigDecimal result2 = FDCHelper.add(firstPartgongjijinDaiKuan,
						five1);
				IRow row = this.tbMain.addRow();
				if (row != null) {
					row.getCell("qishu").setValue(i + "��");
					row.getCell("price").setValue(
							FDCHelper.add(result1, result2));
				}
				
				zongji = FDCHelper.add(zongji, FDCHelper.add(result1, result2));
			}
			
			//����
//			txtTotal.setValue(null);
			//�����ܶ�
			this.txtTotalLoan.setValue(total);
			//�����ܶ�
			txtReturn.setValue(zongji);
			//��Ϣ
			txtLixi.setValue(FDCHelper.subtract(zongji, total));
			
			//����
			txtShouqi.setValue(FDCHelper.ZERO);
			
			//��������
			txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
		}
	}

	private void calcInterestOnPrincipal() {
		this.tbMain.removeRows();
		// ���
//		BigDecimal area = FDCHelper.ZERO;
		// ����
//		BigDecimal price = FDCHelper.ZERO;
		// �����ܶ�
		BigDecimal loanTotal = FDCHelper.ZERO;
		
		// �����ܶ�
		BigDecimal totalAmount = FDCHelper.ZERO;
		if(this.txtTotal.getBigDecimalValue() != null){
			totalAmount = this.txtTotal.getBigDecimalValue();
		}

		// ����
		BigDecimal rate = FDCHelper.ZERO;

		LoanTypeEnum loanType = (LoanTypeEnum) this.cbcLoanType
				.getSelectedItem();

		// ��ҵ����
		if (loanType.equals(LoanTypeEnum.business)) {
			if (this.txtRate.getBigDecimalValue() != null) {
				rate = this.txtRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "��ҵ���ʲ���Ϊ�գ�");
				SysUtil.abort();
			}
			if (rate.compareTo(FDCHelper.ZERO) <= 0) {
				FDCMsgBox.showWarning(this, "��ҵ���ʲ���С��0!");
				SysUtil.abort();
			}
			if (rate.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
				FDCMsgBox.showWarning(this, "��ҵ���ʲ��ܴ���100!");
				SysUtil.abort();
			}

//			LoanCalcTypeEnum calcType = (LoanCalcTypeEnum) this.cbcCalcType
//					.getSelectedItem();
//			// ��������͵��ۼ���
//			if (calcType.equals(LoanCalcTypeEnum.AreaAndPrice)) {
//				if (this.txtArea.getBigDecimalValue() != null) {
//					area = this.txtArea.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "�������Ϊ�գ�");
//					SysUtil.abort();
//				}
//				if (this.txtPrice.getBigDecimalValue() != null) {
//					price = this.txtPrice.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "���۲���Ϊ�գ�");
//					SysUtil.abort();
//				}
//				if(area.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "�������С��0��");
//					SysUtil.abort();
//				}
//				if(price.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "���۲���С��0��");
//					SysUtil.abort();
//				}
//
//				BigDecimal total = FDCHelper.multiply(area, price);
//				BigDecimal percentBig = FDCHelper.ZERO;
//				
//				if (this.cbcLoan.getSelectedItem() != null) {
//					PercentageEnum percent = (PercentageEnum) this.cbcLoan
//							.getSelectedItem();
//
//					percentBig = FDCHelper.divide(new BigDecimal(
//							percent.getValue()), new BigDecimal(String
//							.valueOf("10")), BigDecimal.ROUND_UNNECESSARY, 2);
//
//					total = FDCHelper.multiply(total, percentBig);
//				}
//
//				if (this.cbcMonageYear.getSelectedItem() != null) {
//
//					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
//							.getSelectedItem();
//					int nianXian = Integer.parseInt(year.getValue());
//
//					// ������
//					BigDecimal rateMonth = FDCHelper.divide(rate,
//							new BigDecimal(String.valueOf("100")),
//							BigDecimal.ROUND_UNNECESSARY, 2);
//
//					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
//							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
//					// ����ÿ�¹̶���������
//					// BigDecimal firstPart = FDCHelper.divide(total, new
//					// BigDecimal(String.valueOf(nianXian*12)));
//					// M=A*i*(1��i)^n/((1��i)^n-1)
//
//					// ����*������*��1+�����ʣ�^n/((1��������)^n-1)
//
//					// ��1+�����ʣ�
//					BigDecimal one = FDCHelper.add(rateMonth, String
//							.valueOf("1"));
//					// ��1+�����ʣ�^n
//					BigDecimal two = pow(one,nianXian * 12);
//					// ��1+�����ʣ�^n-1
//					BigDecimal three = FDCHelper.subtract(two, new BigDecimal("1")); 
//					//one.pow(nianXian * 12 - 1);
//
//					// A*i
//					BigDecimal four = FDCHelper.multiply(total, rateMonth);
//
//					// M=A*i*(1��i)^n
//					BigDecimal five = FDCHelper.multiply(four, two);
//
//					// ����*������*��1+�����ʣ�^n/((1��������)^n-1)
//					BigDecimal six = FDCHelper.divide(five, three,
//							BigDecimal.ROUND_UNNECESSARY, 2);
//
//					this.txtMonthReturn.setValue(six);
//					
//					//����
//					txtTotal.setValue(FDCHelper.multiply(area, price));
//					//�����ܶ�
//					this.txtTotalLoan.setValue(total);
//					//�����ܶ�
//					txtReturn.setValue(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))));
//					//��Ϣ
//					txtLixi.setValue(FDCHelper.subtract(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))), total));
//					
//					//����
//					txtShouqi.setValue(FDCHelper.subtract(FDCHelper.multiply(area, price), total));
//					
//					//��������
//					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
//				}
//				
//				
//
//				// ���մ����ܶ�
//			} else {
				if (this.txtLoanTotalMoney.getBigDecimalValue() != null) {
					loanTotal = this.txtLoanTotalMoney.getBigDecimalValue();
				} else {
					FDCMsgBox.showWarning(this, "�����ܶ��Ϊ�գ�");
					SysUtil.abort();
				}
				if(FDCHelper.subtract(loanTotal, totalAmount).compareTo(FDCHelper.ZERO) > 0){
					FDCMsgBox.showWarning(this, "�����ܶ�ܴ��ڷ����ܶ");
					SysUtil.abort();
				}

				if (this.cbcMonageYear.getSelectedItem() != null) {

					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
							.getSelectedItem();
					int nianXian = Integer.parseInt(year.getValue());

					// ������
					BigDecimal rateMonth = FDCHelper.divide(rate,
							new BigDecimal(String.valueOf("100")),
							BigDecimal.ROUND_UNNECESSARY, 2);

					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
					// ����ÿ�¹̶���������
					// BigDecimal firstPart = FDCHelper.divide(total, new
					// BigDecimal(String.valueOf(nianXian*12)));
					// M=A*i*(1��i)^n/((1��i)^n-1)

					// ����*������*��1+�����ʣ�^n/((1��������)^n-1)

					// ��1+�����ʣ�
					BigDecimal one = FDCHelper.add(rateMonth, String
							.valueOf("1"));
					// ��1+�����ʣ�^n
					BigDecimal two = pow(one,nianXian * 12);
					// ��1+�����ʣ�^n-1
					BigDecimal three = FDCHelper.subtract(two, new BigDecimal("1")); 
					//one.pow(nianXian * 12 - 1);

					// A*i
					BigDecimal four = FDCHelper.multiply(loanTotal, rateMonth);

					// M=A*i*(1��i)^n
					BigDecimal five = FDCHelper.multiply(four, two);

					// ����*������*��1+�����ʣ�^n/((1��������)^n-1)
					BigDecimal six = FDCHelper.divide(five, three,
							BigDecimal.ROUND_UNNECESSARY, 2);

					this.txtMonthReturn.setValue(six);
					

					//����
//					txtTotal.setValue(null);
					//�����ܶ�
					this.txtTotalLoan.setValue(loanTotal);
					//�����ܶ�
					//txtReturn.setValue(zongji);
					txtReturn.setValue(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))));
					//��Ϣ
					//txtLixi.setValue(FDCHelper.subtract(zongji, loanTotal));
					txtLixi.setValue(FDCHelper.subtract(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))), loanTotal));
					
					//����
					txtShouqi.setValue(FDCHelper.ZERO);
					
					//��������
					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
				}
//			}
			// ������
		} else if (loanType.equals(LoanTypeEnum.accommodation)) {

			if (this.txtreserveRate.getBigDecimalValue() != null) {
				rate = this.txtreserveRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "��������ʲ���Ϊ�գ�");
				SysUtil.abort();
			}

			if (rate.compareTo(FDCHelper.ZERO) <= 0) {
				FDCMsgBox.showWarning(this, "��������ʲ���С��0!");
				SysUtil.abort();
			}
			if (rate.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
				FDCMsgBox.showWarning(this, "��������ʲ��ܴ���100!");
				SysUtil.abort();
			}

//			LoanCalcTypeEnum calcType = (LoanCalcTypeEnum) this.cbcCalcType
//					.getSelectedItem();
//			// ��������͵��ۼ���
//			if (calcType.equals(LoanCalcTypeEnum.AreaAndPrice)) {
//				if (this.txtArea.getBigDecimalValue() != null) {
//					area = this.txtArea.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "�������Ϊ�գ�");
//					SysUtil.abort();
//				}
//				if (this.txtPrice.getBigDecimalValue() != null) {
//					price = this.txtPrice.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "���۲���Ϊ�գ�");
//					SysUtil.abort();
//				}
//				if(area.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "�������С��0��");
//					SysUtil.abort();
//				}
//				if(price.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "���۲���С��0��");
//					SysUtil.abort();
//				}
//
//				BigDecimal total = FDCHelper.multiply(area, price);
//				BigDecimal percentBig  = FDCHelper.ZERO;
//				if (this.cbcLoan.getSelectedItem() != null) {
//					PercentageEnum percent = (PercentageEnum) this.cbcLoan
//							.getSelectedItem();
//
//					percentBig = FDCHelper.divide(new BigDecimal(
//							percent.getValue()), new BigDecimal(String
//							.valueOf("10")), BigDecimal.ROUND_UNNECESSARY, 2);
//
//					total = FDCHelper.multiply(total, percentBig);
//				}
//
//				if (this.cbcMonageYear.getSelectedItem() != null) {
//
//					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
//							.getSelectedItem();
//					int nianXian = Integer.parseInt(year.getValue());
//
//					// ������
//					BigDecimal rateMonth = FDCHelper.divide(rate,
//							new BigDecimal(String.valueOf("100")),
//							BigDecimal.ROUND_UNNECESSARY, 2);
//
//					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
//							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
//					// ����ÿ�¹̶���������
//					// BigDecimal firstPart = FDCHelper.divide(total, new
//					// BigDecimal(String.valueOf(nianXian*12)));
//					// M=A*i*(1��i)^n/((1��i)^n-1)
//
//					// ����*������*��1+�����ʣ�^n/((1��������)^n-1)
//
//					// ��1+�����ʣ�
//					BigDecimal one = FDCHelper.add(rateMonth, String
//							.valueOf("1"));
//					// ��1+�����ʣ�^n
//					BigDecimal two = pow(one,nianXian * 12);
//					// ��1+�����ʣ�^n-1
//					BigDecimal three = FDCHelper.subtract(two, FDCHelper.ONE); 
//					//one.pow(nianXian * 12 - 1);
//
//					// A*i
//					BigDecimal four = FDCHelper.multiply(total, rateMonth);
//
//					// M=A*i*(1��i)^n
//					BigDecimal five = FDCHelper.multiply(four, two);
//
//					// ����*������*��1+�����ʣ�^n/((1��������)^n-1)
//					BigDecimal six = FDCHelper.divide(five, three,
//							BigDecimal.ROUND_UNNECESSARY, 2);
//
//					this.txtMonthReturn.setValue(six);
//					
//					//����
//					txtTotal.setValue(FDCHelper.multiply(area, price));
//					//�����ܶ�
//					this.txtTotalLoan.setValue(total);
//					//�����ܶ�
//					txtReturn.setValue(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))));
//					//��Ϣ
//					txtLixi.setValue(FDCHelper.subtract(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))), total));
//					
//					//����
//					txtShouqi.setValue(FDCHelper.subtract(FDCHelper.multiply(area, price), total));
//					
//					//��������
//					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
//			
//				}
//				// ���մ����ܶ�
//			} else {
				if (this.txtLoanTotalMoney.getBigDecimalValue() != null) {
					loanTotal = this.txtLoanTotalMoney.getBigDecimalValue();
				} else {
					FDCMsgBox.showWarning(this, "�����ܶ��Ϊ�գ�");
					SysUtil.abort();
				}

				if(FDCHelper.subtract(loanTotal, totalAmount).compareTo(FDCHelper.ZERO) > 0){
					FDCMsgBox.showWarning(this, "�����ܶ�ܴ��ڷ����ܶ");
					SysUtil.abort();
				}
				if (this.cbcMonageYear.getSelectedItem() != null) {

					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
							.getSelectedItem();
					int nianXian = Integer.parseInt(year.getValue());

					// ������
					BigDecimal rateMonth = FDCHelper.divide(rate,
							new BigDecimal(String.valueOf("100")),
							BigDecimal.ROUND_UNNECESSARY, 2);

					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY,6);
				
					// ����*������*��1+�����ʣ�^n/((1��������)^n-1)

					// ��1+�����ʣ�
					BigDecimal one = FDCHelper.add(rateMonth, String
							.valueOf("1"));
					// ��1+�����ʣ�^n
					BigDecimal two = pow(one,nianXian * 12);
					// ��1+�����ʣ�^n-1
					BigDecimal three = FDCHelper.subtract(two, new BigDecimal("1")); 
					//one.pow(nianXian * 12 - 1);

					// A*i
					BigDecimal four = FDCHelper.multiply(loanTotal, rateMonth);

					// M=A*i*(1��i)^n
					BigDecimal five = FDCHelper.multiply(four, two);

					// ����*������*��1+�����ʣ�^n/((1��������)^n-1)
					BigDecimal six = FDCHelper.divide(five, three,
							BigDecimal.ROUND_UNNECESSARY, 2);

					this.txtMonthReturn.setValue(six);
					
					//����
//					txtTotal.setValue(null);
					//�����ܶ�
					this.txtTotalLoan.setValue(loanTotal);
					//�����ܶ�
					//txtReturn.setValue(zongji);
					txtReturn.setValue(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))));
					//��Ϣ
					//txtLixi.setValue(FDCHelper.subtract(zongji, loanTotal));
					txtLixi.setValue(FDCHelper.subtract(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))), loanTotal));
					
					//����
					txtShouqi.setValue(FDCHelper.ZERO);
					
					//��������
					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
				
				}
//			}
			// ���
		} else if (loanType.equals(LoanTypeEnum.combination)) {
			/**
			 * �������
			 */

			BigDecimal shangye = FDCHelper.ZERO;
			BigDecimal gongjijin = FDCHelper.ZERO;

			BigDecimal shangyeDaikKuan = FDCHelper.ZERO;
			BigDecimal gongjijinDaiKuan = FDCHelper.ZERO;

			BigDecimal shangyeRateMonth = FDCHelper.ZERO;
			BigDecimal gongjijinRateMonth = FDCHelper.ZERO;

			if (this.txtShangyeDaikuan.getBigDecimalValue() != null) {
				shangyeDaikKuan = this.txtShangyeDaikuan.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "��ҵ�����Ϊ�գ�");
				SysUtil.abort();
			}
			if (this.txtGongjiJinDaikuan.getBigDecimalValue() != null) {
				gongjijinDaiKuan = this.txtGongjiJinDaikuan
						.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "����������Ϊ�գ�");
				SysUtil.abort();
			}
			if (this.txtRate.getBigDecimalValue() != null) {
				shangye = this.txtRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "��ҵ���ʲ���Ϊ�գ�");
				SysUtil.abort();
			}
			if (shangye.compareTo(FDCHelper.ZERO) <= 0) {
				FDCMsgBox.showWarning(this, "��ҵ���ʲ���С��0!");
				SysUtil.abort();
			}
			if (shangye.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
				FDCMsgBox.showWarning(this, "��ҵ���ʲ��ܴ���100!");
				SysUtil.abort();
			}
			if (this.txtreserveRate.getBigDecimalValue() != null) {
				gongjijin = this.txtreserveRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "��������ʲ���Ϊ�գ�");
				SysUtil.abort();
			}
			if (gongjijin.compareTo(FDCHelper.ZERO) <=0) {
				FDCMsgBox.showWarning(this, "��������ʲ���С��0!");
				SysUtil.abort();
			}
			if (gongjijin.compareTo(FDCHelper.ONE_HUNDRED) >0) {
				FDCMsgBox.showWarning(this, "��������ʲ��ܴ���100!");
				SysUtil.abort();
			}
			
			if(FDCHelper.subtract(shangyeDaikKuan.add(gongjijinDaiKuan), totalAmount).compareTo(FDCHelper.ZERO) > 0){
				FDCMsgBox.showWarning(this, "�����ܶ�ܴ��ڷ����ܶ");
				SysUtil.abort();
			}

			shangyeRateMonth = FDCHelper.divide(shangye, new BigDecimal(String
					.valueOf("100")), BigDecimal.ROUND_UNNECESSARY, 2);
			gongjijinRateMonth = FDCHelper.divide(gongjijin, new BigDecimal(
					String.valueOf("100")), BigDecimal.ROUND_UNNECESSARY, 2);
			shangyeRateMonth = FDCHelper.divide(shangyeRateMonth,
					new BigDecimal(String.valueOf("12")),
					BigDecimal.ROUND_UNNECESSARY, 2);
			gongjijinRateMonth = FDCHelper.divide(gongjijinRateMonth,
					new BigDecimal(String.valueOf("12")),
					BigDecimal.ROUND_UNNECESSARY, 2);

			MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
					.getSelectedItem();
			int nianXian = Integer.parseInt(year.getValue());
			/**
			 * ����Ϊ��ҵ�����
			 */
			// ������
			// ����ÿ�¹̶���������
			// BigDecimal firstPart = FDCHelper.divide(total, new
			// BigDecimal(String.valueOf(nianXian*12)));
			// M=A*i*(1��i)^n/((1��i)^n-1)
			// ����*������*��1+�����ʣ�^n/((1��������)^n-1)
			// ��1+�����ʣ�
			BigDecimal one = FDCHelper.add(shangyeRateMonth, String
					.valueOf("1"));
			// ��1+�����ʣ�^n
			BigDecimal two = pow(one,nianXian * 12);
			// ��1+�����ʣ�^n-1
			BigDecimal three = FDCHelper.subtract(two, new BigDecimal("1")); 
			//one.pow(nianXian * 12 - 1);

			// A*i
			BigDecimal four = FDCHelper.multiply(shangyeDaikKuan,
					shangyeRateMonth);

			// M=A*i*(1��i)^n
			BigDecimal five = FDCHelper.multiply(four, two);

			// ����*������*��1+�����ʣ�^n/((1��������)^n-1)
			BigDecimal six = FDCHelper.divide(five, three,
					BigDecimal.ROUND_UNNECESSARY, 2);

			/**
			 * ����Ϊ�����𲿷�
			 */
			// ������
			// M=A*i*(1��i)^n/((1��i)^n-1)
			// ����*������*��1+�����ʣ�^n/((1��������)^n-1)
			// ��1+�����ʣ�
			BigDecimal one1 = FDCHelper.add(gongjijinRateMonth, String
					.valueOf("1"));
			// ��1+�����ʣ�^n
			BigDecimal two1 = pow(one1,nianXian * 12);
			// ��1+�����ʣ�^n-1
			BigDecimal three1 = FDCHelper.subtract(two1, new BigDecimal("1")); 
			//one.pow(nianXian * 12 - 1);

			// A*i
			BigDecimal four1 = FDCHelper.multiply(gongjijinDaiKuan,
					gongjijinRateMonth);

			// M=A*i*(1��i)^n
			BigDecimal five1 = FDCHelper.multiply(four1, two1);

			// ����*������*��1+�����ʣ�^n/((1��������)^n-1)
			BigDecimal six1 = FDCHelper.divide(five1, three1,
					BigDecimal.ROUND_UNNECESSARY, 2);

			this.txtMonthReturn.setValue(FDCHelper.add(six, six1));
			
			BigDecimal daiKuanTotal = FDCHelper.ZERO;
			
			daiKuanTotal = FDCHelper.add(this.txtShangyeDaikuan.getBigDecimalValue(), this.txtGongjiJinDaikuan.getBigDecimalValue());
			//����
//			txtTotal.setValue(null);
			//�����ܶ�
			this.txtTotalLoan.setValue(daiKuanTotal);
			//�����ܶ�
			//txtReturn.setValue(zongji);
			txtReturn.setValue(FDCHelper.multiply(FDCHelper.add(six, six1), new BigDecimal(String.valueOf(nianXian*12))));
			//��Ϣ
			//txtLixi.setValue(FDCHelper.subtract(zongji, loanTotal));
			txtLixi.setValue(FDCHelper.subtract(FDCHelper.multiply(FDCHelper.add(six, six1), new BigDecimal(String.valueOf(nianXian*12))), daiKuanTotal));
			
			//����
			txtShouqi.setValue(FDCHelper.ZERO);
			
			//��������
			txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));

		}
	}
	
	public static BigDecimal pow(BigDecimal dec1,int dec2){
		if(dec1==null){
			return null;
		}
		if(dec2<0){
			return null;
		}
		BigDecimal temp  = FDCHelper.ONE;
		
		if(dec2==0){
			return FDCHelper.ONE;
		}
		
		for (int i = 1; i <=dec2; i++) {
			temp =temp.multiply(dec1);
		}
		
		return temp;
	}
	
}