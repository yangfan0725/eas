/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class SpecialBusinessDetailFilterUI extends AbstractSpecialBusinessDetailFilterUI {

	private static final String MONTH_TO = "monthTo";

	private static final String MONTH_FROM = "monthFrom";

	private static final String YEAR_TO = "yearTo";

	private static final String YEAR_FROM = "yearFrom";

	private static final String DATE_TO = "dateTo";

	private static final String DATE_FROM = "dateFrom";

	private static final String IS_SHOW_ALL = "isShowAll";

	private static final String DATE_TYPE = "dateType";

	private static final String ROOM_SELL_TYPE_PREPURCHASE = "roomSellTypePrepurchase";

	private static final String ROOM_SELL_TYPE_PURCHASE = "roomSellTypePurchase";

	private static final String ROOM_SELL_TYPE_SIGN = "roomSellTypeSign";

	protected ItemAction actionListOnLoad;

	private boolean isLoaded;

	protected ListUI listUI;

	//��ӷ���ͳ������
	private final String ROOM_CHECK_OUT = "roomCheckout";
	private final String ROOM_ORDER_QUIT = "orderQuit";
	private final String ROOM_PURCHASE_QUIT = "purchaseQuit";
	private final String ROOM_SIGN_QUIT = "signQuit";
	
	private final String ROOM_SWAP = "roowSwap";
	private final String ROOM_CHANGE = "roomChange";
	private final String ROOM_RENAME = "roomRename";
	private final String ROOM_ADJUST = "roomAdjust";

	/**
	 * output class constructor
	 */
	public SpecialBusinessDetailFilterUI(ListUI listUI,ItemAction actionListOnLoad) throws Exception
	{
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
	}

	public SpecialBusinessDetailFilterUI() throws Exception
	{
		super();
	}
	
	public void clear()
	{
		this.pkDateFrom.setValue(new Date());
		this.pkDateTo.setValue(new Date());
		this.radioByDay.setSelected(true);
		initControlByDateType();
	}

	public FilterInfo getFilterInfo()
	{
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
	    FilterInfo filter = new FilterInfo();
		/*filter.getFilterItems().add(new FilterItemInfo("id", null, CompareType.NOTEQUALS));
		if (para.isNotNull(IS_SHOW_ALL) && !para.getBoolean(IS_SHOW_ALL)&& para.isNotNull(DATE_TYPE)) {
		if (getBeginQueryDate(para) != null) {
		filter.getFilterItems().add(
		new FilterItemInfo("date", getBeginQueryDate(para),
		 CompareType.GREATER_EQUALS));
		 }
		 if (getEndQueryDate(para) != null) {
		 filter.getFilterItems().add(
		 new FilterItemInfo("date", FDCHelper
		 .getNextDay(getEndQueryDate(para)),
		 CompareType.LESS));
		
		 }
		 }
		if (this.checkPrePurchase.isSelected()
		 || this.checkPurchase.isSelected()
		 || this.checkSign.isSelected()) {
		
		 if (this.checkPrePurchase.isSelected()) {
		 filter.getFilterItems().add(
		 new FilterItemInfo("sellState", "PrePurchase",
		 CompareType.EQUALS));
		 }
		 if (this.checkPurchase.isSelected()) {
		 filter.getFilterItems().add(
		 new FilterItemInfo("sellState", "Purchase",
		 CompareType.EQUALS));
		 }
		 if (this.checkSign.isSelected()) {
		 filter.getFilterItems().add(
		 new FilterItemInfo("sellState", "Sign",
		 CompareType.EQUALS));
		 }
		
		 }*/
	    filter.getFilterItems().add(new FilterItemInfo("sellState","1"));
		return filter;
	}

	public Date getBeginQueryDate(FDCCustomerParams para)
	{
		Date date = null;
		int dateType = para.getInt(DATE_TYPE);
		if (dateType == 0)
		{
			date = para.getDate(DATE_FROM);
		} else if (dateType == 1)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, para.getInt(MONTH_FROM) - 1);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 2)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, (para.getInt(MONTH_FROM) - 1) * 3);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 3)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}

	public Date getEndQueryDate(FDCCustomerParams para)
	{
		Date date = null;
		int dateType = para.getInt(DATE_TYPE);
		if (dateType == 0)
		{
			date = para.getDate(DATE_TO);
		} else if (dateType == 1)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO));
			cal.set(Calendar.MONTH, para.getInt(MONTH_TO));
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 2)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO));
			cal.set(Calendar.MONTH, para.getInt(MONTH_TO) * 3);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 3)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO) + 1);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}
	/**
	 * ��ӷ���ѡ��ʵ��
	 */
	public boolean getIsCheckOut(FDCCustomerParams para)
	{
		return para.getBoolean(ROOM_CHECK_OUT);
	}
	public boolean getIsOrderQuit(FDCCustomerParams para)
	{
		return para.getBoolean(ROOM_ORDER_QUIT);
	}
	public boolean getIsPurchaseQuit(FDCCustomerParams para)
	{
		return para.getBoolean(ROOM_PURCHASE_QUIT);
	}
	public boolean getIsSignQuit(FDCCustomerParams para)
	{
		return para.getBoolean(ROOM_SIGN_QUIT);
	}
	
	public boolean getIsSwap(FDCCustomerParams para)
	{
		return para.getBoolean(ROOM_SWAP);
	}
	public boolean getIsChange(FDCCustomerParams para)
	{
		return para.getBoolean(ROOM_CHANGE);
	}
	public boolean getIsRename(FDCCustomerParams para)
	{
		return para.getBoolean(ROOM_RENAME);
	}
	public boolean getIsAdjust(FDCCustomerParams para)
	{
		return para.getBoolean(ROOM_ADJUST);
	}
	
	public CustomerParams getCustomerParams()
	{
		FDCCustomerParams param = new FDCCustomerParams();

		if (this.radioByDay.isSelected())
		{
			param.add(DATE_FROM, (Date) this.pkDateFrom.getValue());
			param.add(DATE_TO, (Date) this.pkDateTo.getValue());
			param.add(DATE_TYPE, 0);
		} else if (this.radioByMonth.isSelected())
		{
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param
					.add(YEAR_TO, ((Integer) this.spiYearTo.getValue())
							.intValue());
			param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue())
					.intValue());
			param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue())
					.intValue());
			param.add(DATE_TYPE, 1);
		} else if (this.radioByQuarter.isSelected())
		{
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param
					.add(YEAR_TO, ((Integer) this.spiYearTo.getValue())
							.intValue());
			param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue())
					.intValue());
			param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue())
					.intValue());
			param.add(DATE_TYPE, 2);
		} else if (this.radioByYear.isSelected())
		{
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param
					.add(YEAR_TO, ((Integer) this.spiYearTo.getValue())
							.intValue());
			param.add(DATE_TYPE, 3);
		}
		param.add(IS_SHOW_ALL, this.chkIsShowAll.isSelected());
		
		//��ӷ���ͳ��
		param.add(ROOM_CHECK_OUT, this.checkIsQuit.isSelected());
		param.add(ROOM_ORDER_QUIT, this.checkIsOrder.isSelected());
		param.add(ROOM_PURCHASE_QUIT, this.checkIsPurchase.isSelected());
		param.add(ROOM_SIGN_QUIT, this.checkIsSign.isSelected());
		
		
		param.add(ROOM_SWAP, this.checkIsSwap.isSelected());
		param.add(ROOM_CHANGE, this.checkIsChange.isSelected());
		param.add(ROOM_RENAME, this.checkIsRename.isSelected());
		param.add(ROOM_ADJUST, this.checkIsAdjust.isSelected());
		
		
		return param.getCustomerParams();
	}

	private void initControlByDateType()
	{
		SpinnerNumberModel yearMo1 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearFrom.setModel(yearMo1);
		SpinnerNumberModel yearMo2 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearTo.setModel(yearMo2);
		if (this.radioByDay.isSelected())
		{
			this.contYearFrom.setVisible(false);
			this.contYearTo.setVisible(false);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);
			this.contDateFrom.setVisible(true);
			this.contDateTo.setVisible(true);

		} else if (this.radioByMonth.isSelected())
		{
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(true);
			this.lblMonthTo.setVisible(true);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(true);
			this.spiMonthTo.setVisible(true);
			this.contDateFrom.setVisible(false);
			this.contDateTo.setVisible(false);
			SpinnerNumberModel monthMo1 = new SpinnerNumberModel(Calendar
					.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
			spiMonthFrom.setModel(monthMo1);
			SpinnerNumberModel monthMo2 = new SpinnerNumberModel(Calendar
					.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
			spiMonthTo.setModel(monthMo2);
		} else if (this.radioByQuarter.isSelected())
		{
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(true);
			this.lblQuarterTo.setVisible(true);
			this.spiMonthFrom.setVisible(true);
			this.spiMonthTo.setVisible(true);
			this.contDateFrom.setVisible(false);
			this.contDateTo.setVisible(false);
			int[] SEASON =
			{ 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
			SpinnerNumberModel quarterMo1 = new SpinnerNumberModel(
					SEASON[Calendar.getInstance().get(Calendar.MONTH)], 1, 4, 1);
			spiMonthFrom.setModel(quarterMo1);
			SpinnerNumberModel quarterMo2 = new SpinnerNumberModel(
					SEASON[Calendar.getInstance().get(Calendar.MONTH)], 1, 4, 1);
			spiMonthTo.setModel(quarterMo2);

		} else if (this.radioByYear.isSelected())
		{
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);
			this.contDateFrom.setVisible(false);
			this.contDateTo.setVisible(false);
		}

		if (this.chkIsShowAll.isSelected())
		{
			this.radioByDay.setEnabled(false);
			this.radioByMonth.setEnabled(false);
			this.radioByQuarter.setEnabled(false);
			this.radioByYear.setEnabled(false);
			this.spiYearFrom.setEnabled(false);
			this.spiYearTo.setEnabled(false);
			this.spiMonthFrom.setEnabled(false);
			this.spiMonthTo.setEnabled(false);
			this.pkDateFrom.setEnabled(false);
			this.pkDateTo.setEnabled(false);
		} else
		{
			this.radioByDay.setEnabled(true);
			this.radioByMonth.setEnabled(true);
			this.radioByQuarter.setEnabled(true);
			this.radioByYear.setEnabled(true);
			this.plDateType.setEnabled(true);
			this.spiYearFrom.setEnabled(true);
			this.spiYearTo.setEnabled(true);
			this.spiMonthFrom.setEnabled(true);
			this.spiMonthTo.setEnabled(true);
			this.pkDateFrom.setEnabled(true);
			this.pkDateTo.setEnabled(true);
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
		
		this.checkIsChange.setSelected(true);
		this.checkIsOrder.setSelected(true);
		this.checkIsPurchase.setSelected(true);
		this.checkIsSign.setSelected(true);
		
		this.checkIsQuit.setSelected(true);
		this.checkIsRename.setSelected(true);
		this.checkIsSwap.setSelected(true);
	}

	protected void radioByDay_actionPerformed(ActionEvent e) throws Exception
	{
		super.radioByDay_actionPerformed(e);
		this.initControlByDateType();
	}

	protected void radioByMonth_actionPerformed(ActionEvent e) throws Exception
	{
		super.radioByMonth_actionPerformed(e);
		this.initControlByDateType();
	}

	protected void radioByQuarter_actionPerformed(ActionEvent e)
			throws Exception
	{
		super.radioByQuarter_actionPerformed(e);
		this.initControlByDateType();
	}

	protected void radioByYear_actionPerformed(ActionEvent e) throws Exception
	{
		super.radioByYear_actionPerformed(e);
		this.initControlByDateType();
	}

	public void setCustomerParams(CustomerParams cp)
	{
		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		if (para.getInt(DATE_TYPE) == 0)
		{
			this.radioByDay.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 1)
		{
			this.radioByMonth.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 2)
		{
			this.radioByQuarter.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 3)
		{
			this.radioByYear.setSelected(true);
		}
		this.chkIsShowAll.setSelected(para.getBoolean(IS_SHOW_ALL));
		this.initControlByDateType();

		if (para.getInt(DATE_TYPE) == 0)
		{
			this.pkDateFrom.setValue(para.getDate(DATE_FROM));
			this.pkDateTo.setValue(para.getDate(DATE_TO));
		} else if (para.getInt(DATE_TYPE) == 1)
		{
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
		} else if (para.getInt(DATE_TYPE) == 2)
		{
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
		} else if (para.getInt(DATE_TYPE) == 3)
		{
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
		}
		
		//��ӷ���ͳ��
		this.checkIsQuit.setSelected(para.getBoolean(ROOM_CHECK_OUT));
		this.checkIsOrder.setSelected(para.getBoolean(ROOM_ORDER_QUIT));
		this.checkIsPurchase.setSelected(para.getBoolean(ROOM_PURCHASE_QUIT));
		this.checkIsSign.setSelected(para.getBoolean(ROOM_SIGN_QUIT));
		
		
		
		this.checkIsSwap.setSelected(para.getBoolean(ROOM_SWAP));
		this.checkIsChange.setSelected(para.getBoolean(ROOM_CHANGE));
		this.checkIsRename.setSelected(para.getBoolean(ROOM_RENAME));
		this.checkIsAdjust.setSelected(para.getBoolean(ROOM_ADJUST));
		
		super.setCustomerParams(para.getCustomerParams());
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	public boolean verify()
	{
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		Date beginQueryDate = this.getBeginQueryDate(para);
		Date endQueryDate = this.getEndQueryDate(para);
		if (beginQueryDate != null && endQueryDate != null)
		{
			if (endQueryDate.before(beginQueryDate))
			{
				MsgBox.showWarning(this, "��ʼ���ڲ��ܴ��ڽ�������!");
				return false;
			}
		}
		return true;
	}

	protected void chkIsShowAll_actionPerformed(ActionEvent e) throws Exception
	{
		super.chkIsShowAll_actionPerformed(e);
		this.initControlByDateType();
	}

	boolean hasCurrency = false;

	public boolean hasCurrency()
	{
		return hasCurrency;
	}
	
	protected void checkIsQuit_actionPerformed(ActionEvent e) throws Exception {
		super.checkIsQuit_actionPerformed(e);
		
		/**
		 * ���ѡ�У�������Ԥ�����Ϲ���ǩԼ�˷���Ϊѡ��
		 */
		this.checkIsOrder.setSelected(this.checkIsQuit.isSelected());
		this.checkIsPurchase.setSelected(this.checkIsQuit.isSelected());
		this.checkIsSign.setSelected(this.checkIsQuit.isSelected());
	}
	
	protected void checkIsOrder_actionPerformed(ActionEvent e) throws Exception {
		super.checkIsOrder_actionPerformed(e);
		
		if(this.checkIsOrder.isSelected())
		{
			this.checkIsQuit.setSelected(true);
		}
		else if( !this.checkIsOrder.isSelected() && !this.checkIsPurchase.isSelected() && !this.checkIsSign.isSelected())
		{
			this.checkIsQuit.setSelected(false);
		}
	}
	
	protected void checkIsPurchase_actionPerformed(ActionEvent e)
			throws Exception {
		super.checkIsPurchase_actionPerformed(e);
		
		if(this.checkIsPurchase.isSelected())
		{
			this.checkIsQuit.setSelected(true);
		}
		else if( !this.checkIsOrder.isSelected() && !this.checkIsPurchase.isSelected() && !this.checkIsSign.isSelected())
		{
			this.checkIsQuit.setSelected(false);
		}
	}
	
	protected void checkIsSign_actionPerformed(ActionEvent e) throws Exception {
		super.checkIsSign_actionPerformed(e);
		
		if(this.checkIsSign.isSelected())
		{
			this.checkIsQuit.setSelected(true);
		}
		else if( !this.checkIsOrder.isSelected() && !this.checkIsPurchase.isSelected() && !this.checkIsSign.isSelected())
		{
			this.checkIsQuit.setSelected(false);
		}
	}
}