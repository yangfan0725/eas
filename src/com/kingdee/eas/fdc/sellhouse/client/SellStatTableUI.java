/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellStatRptFacadeFactory;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * ��������ͳ�Ʊ��� <br>
 */
public class SellStatTableUI extends AbstractSellStatTableUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SellStatTableUI.class);
	
	/**
	 * ��¥��IDΪKey,��¥����RowΪValue��Map,������fillData�п��ٶ�λĳһ¥����Row
	 * */
	private Map rowMap = new HashMap();

	private SellStatTableFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	private Date beginDate = null;
	private Date endDate = null;
	private boolean isShowAll = false;
	private boolean isPreIntoSale = false;    //Ԥ��ҵ���������ͳ��
	private boolean isIncludeAttach = false;    //�Ƿ�ͳ�Ƹ�������
	private int isAreaA = 0;   //����������Aѡ��ֵ
	private int isAreaB = 0;   //����������Bѡ��ֵ
	

	/**
	 * output class constructor
	 */
	public SellStatTableUI() throws Exception {
		super();
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
		// super.tblMain_doRequestRowSet(e);
	}

	private SellStatTableFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new SellStatTableFilterUI(this, this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	
	protected void execQuery()
	{
		//ͨ�� portal �������֮��
		Map timeMap = new HashMap();
		Map map = this.getUIContext();
		Object obj = map.get(UIContext.UICLASSPARAM);
		if(obj != null && obj.toString().trim().length() > 0)
		{
			String [] para = obj.toString().split("&");
			for(int i = 0; i < para.length; i ++)
			{
				String t = para[i];
		
				if(t != null)
				{
					String[] p1 = t.split("=");
					if(p1 == null || p1.length != 2)
						continue;
				
					if(p1[0] != null && p1[0].trim().length() > 0)
					{
						if(p1[1]  != null && p1[1].trim().length() > 0)
						{
							if("beginDate".equalsIgnoreCase(p1[0]))
							{
								String time = p1[1];
								Date d = null;
								try
								{
									d = DateTimeUtils.parseDate(time);
			
								} catch (ParseException e)
								{
									logger.error(e);
									MsgBox.showWarning("beginDate������ʽ����Ӧ��Ϊ ��beginDate=YYYY-MM-DD��!");
								}
								timeMap.put("beginDate", d);
							}
							else if("endDate".equalsIgnoreCase(p1[0]))
							{

								String time = p1[1];
								Date d = null;
								try
								{
									d = DateTimeUtils.parseDate(time);

								} catch (ParseException e)
								{
									logger.error(e);
									MsgBox.showWarning("endDate������ʽ����Ӧ��Ϊ ��endDate=YYYY-MM-DD��!");
								}
								timeMap.put("endDate", d);
							}
							else if("route".equalsIgnoreCase(p1[0]))
							{
								timeMap.put("route", "web");
							}
							else if("org".equalsIgnoreCase(p1[0]))
							{
								String id = p1[1];
								timeMap.put("org", id);
							}
						}
						else
						{

							if("beginDate".equalsIgnoreCase(p1[0]))
							{
								
								timeMap.put("beginDate", null);
							}
							else if("endDate".equalsIgnoreCase(p1[0]))
							{

								timeMap.put("endDate", null);
							}
							else if("route".equalsIgnoreCase(p1[0]))
							{
								timeMap.put("route", "web");
							}
							else if("org".equalsIgnoreCase(p1[0]))
							{
								timeMap.put("org", null);
							}
						
						}
					}
				}
			}
		}
		
		try
		{
			initTable();
			fillProjectAndBuilding(timeMap);
			
		} catch (EASBizException e) {
			handleException(e);
		} catch (BOSException e) {
			handleException(e);
		} catch (SQLException e) {
			handleException(e);
		}

		
		if(tblMain.getRowCount() == 0){
			return;
		}
	
		
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		if(timeMap.get("route") != null)
		{
			if(timeMap.get("beginDate") != null) beginDate = (Date)timeMap.get("beginDate");
			if(timeMap.get("endDate") != null) endDate = (Date)timeMap.get("endDate");
			
			map.put(UIContext.UICLASSPARAM, null);
		}
		else
		{
			if(filterMap.get("beginDate")!=null)	beginDate = new Date(((Timestamp)filterMap.get("beginDate")).getTime());
			if(filterMap.get("endDate")!=null)	endDate = new Date(((Timestamp)filterMap.get("endDate")).getTime());
		}
		isShowAll = ((Integer)filterMap.get("isShowAll")).intValue()>0?true:false;
		isPreIntoSale = ((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false;
		isIncludeAttach = ((Integer)filterMap.get("isIncludeAttach")).intValue()>0?true:false;
		isAreaA = Integer.parseInt(filterMap.get("isAreaA").toString());   //����������Aѡ��ֵ
		isAreaB = Integer.parseInt(filterMap.get("isAreaB").toString());   //����������Bѡ��ֵ
		
		try
		{
			//��ȡ������������
			Map paramMap = fillParamMap();
			// ��ȡ������������
			Map rptMap = SellStatRptFacadeFactory.getRemoteInstance().getSellStatRptData(paramMap);
			
			fillData(rptMap);
		} catch (Exception e)
		{
			super.handUIException(e);
		}
		
		//���½��в�ѯ�������ʾ
		Date beginDate = DateTimeUtils.truncateDate(this.beginDate);
		Date endDate = DateTimeUtils.truncateDate(this.endDate);
		int termCol = 11;
		if (!isShowAll)
		{
			if (beginDate == null && endDate == null)
			{
				this.tblMain.getHeadRow(0).getCell(termCol).setValue("��ѯ��������(ȫ����ʾ)");
			}
			else if(beginDate == null && endDate != null)
			{
				Calendar cal = new GregorianCalendar();
				cal.setTime(endDate);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE));
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				String des = "��ѯ��������(" 	+ FORMAT_DAY.format(cal.getTime()) + "֮ǰ)";
				this.tblMain.getHeadRow(0).getCell(termCol).setValue(des);
			}
			else if(beginDate != null && endDate == null)
			{
				Calendar cal = new GregorianCalendar();
				cal.set(Calendar.DATE, cal.get(Calendar.DATE));
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				String des = "��ѯ��������(" + FORMAT_DAY.format(beginDate) + "֮��)";
				this.tblMain.getHeadRow(0).getCell(termCol).setValue(des);
			}
			else
			{
				Calendar cal = new GregorianCalendar();
				cal.setTime(endDate);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE));
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				String des = "��ѯ��������(" + FORMAT_DAY.format(beginDate) + "��"
						+ FORMAT_DAY.format(cal.getTime()) + ")";
				this.tblMain.getHeadRow(0).getCell(termCol).setValue(des);
			}
		} else
		{
			this.tblMain.getHeadRow(0).getCell(termCol).setValue("��ѯ��������(ȫ����ʾ)");
		}
	}

	protected void checkTableParsed() {
		tblMain.checkParsed();
	}

	public void onLoad() throws Exception {
		this.tblMain.checkParsed();
		FDCClientHelper.addSqlMenu(this, this.menuFile);
		super.onLoad();
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setEnabled(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.menuEdit.setVisible(false);
	}

	protected String[] getLocateNames() {
		String[] locateNames = new String[1];
		locateNames[0] = "name";
		return locateNames;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	private void fillData(Map map) throws BOSException, SQLException {
		//Ӧ������������¥���к󣬸ú�����������Ӧ��¥������������ݡ����¥������Ϊ0,��ô���²�ѯ���������ý����ˡ�
		if(this.tblMain.getRowCount() == 0){
			return;
		}
		
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			for (int j = 1; j < this.tblMain.getColumnCount(); j++) {
				this.tblMain.getCell(i, j).setValue(null);
			}
		}
		
		try {
			Map areaMap = (Map) map.get("areaMap");
			Map keepMap = (Map) map.get("keepMap");
			Map quitMap = (Map) map.get("quitMap");
			Map sellMap = (Map) map.get("sellMap");   //��������������������׼��ܼۡ��׼۾��ۡ���׼�ܼۡ���׼���ۡ��ɽ��ܼۡ��ɽ�����
			Map sellMap1 = (Map) map.get("sellMap1");   //Ӧ�ղ�����
			Map sellMap2 = (Map) map.get("sellMap2");   //ʵ�ղ�����
			Map signMap = (Map) map.get("signMap");
			Map unsignMap = (Map) map.get("unsignMap");
			Map purMap = (Map) map.get("purMap");  //������ؿ�
			Map purMap1 = (Map) map.get("purMap1");  //����Ƿ��
			Map purMap2 = (Map) map.get("purMap2");  //�Ƕ�����ؿ�
			Map conMap = (Map) map.get("conMap");
			IRowSet recSet =  (IRowSet) map.get("recSet");
			
			
			fillTermAllAmount(areaMap);     //����������������ܱ�׼��
			
			fillTermKeepAmount(keepMap);     //�������������������������׼�ܼ�
			
			fillTermQuitAmount(quitMap);     //����������������������̱�׼�ܼ�
			
			fillTermSellAmount(sellMap,sellMap1,sellMap2);     //��������������������׼��ܼۡ���׼�ܼۡ��Żݶ�ɽ��ܼۡ�Ӧ�ղ����ʵ�ղ�����
			
			fillTermSignAmount(signMap);     //ǩԼ��ͬ�ܼۡ�ǩԼ��ͬ������ǩԼ��ͬ�����ǩԼ��ͬ�ɽ��ܼ�
			
			fillTermContractAmount(conMap);     //��ͬ�ܼ�
			
			fillTermReceiveAmount(recSet);     //���ۻؿ�
			
			fillTermUnsignAmount(unsignMap);     //����δǩԼ����������δǩԼ���������δǩԼ��ͬ�ܼۡ�����δǩԼ�ɽ��ܼ�
			
			fillTermPurchaseAmount(purMap,purMap1,purMap2);     //������ؿ����Ƿ��Ƕ�����ؿ�
			
		} catch (Exception e) {
			super.handUIException(e);
		}
		
		setUnionData();
		resetUnionData();
	}
	
	//��������
	protected Map fillParamMap(){
		Map paraMap = new HashMap();
		
		String fillArea = fillArea(isAreaA,isAreaB);   //�������
		Map map = fillTime();    //��ȡʱ�俪ʼ����ʱ��

		paraMap.put("PreIntoSale", Boolean.valueOf(isPreIntoSale));
		paraMap.put("IncludeAttach", Boolean.valueOf(isIncludeAttach));
		paraMap.put("checkArea", fillArea);
		paraMap.put("beginDate", map.get("beginDate"));
		paraMap.put("endDate", map.get("endDate"));
		paraMap.put("ShowAll", map.get("ShowAll"));
		
		return paraMap;
	}

	//��������ж�
	private String fillArea(int isAreaA,int isAreaB){
		String fillArea = "";
		if(isAreaA == 0){
			if(isAreaB == 0){
				fillArea = "FBuildingArea";
			}else if(isAreaB == 1){
				fillArea = "FActualBuildingArea";
			}
		}else if(isAreaA == 1){
			if(isAreaB == 0){
				fillArea = "FRoomArea";
			}else if(isAreaB == 1){
				fillArea = "FActualRoomArea";
			}
		}
		return fillArea;
	}

	/**
	 * ��� ���ۻؿ�
	 * */
	private void fillTermReceiveAmount(IRowSet map) throws BOSException, SQLException {

		if (this.rowMap == null || this.rowMap.size() == 0)
			return;
//		Iterator iter = map.keySet().iterator();
		while (map.next()) {
//			String buildIdSr = (String) iter.next();
			String buildIdSr = map.getString("FBuildingId");
			BigDecimal ysAmount = map.getBigDecimal("revAmount");
			///�տ��ȡ��  ֱ�Ӽ���
			BigDecimal yzAmount = map.getBigDecimal("trsAmount");
			BigDecimal yqAmount = map.getBigDecimal("refAmount");
			BigDecimal ytAmount = map.getBigDecimal("adjAmount");
			
			//boolean isIn = map.getBoolean("isIn");
			String moneyType = map.getString("moneyType");
			
			IRow row = (IRow) this.rowMap.get(buildIdSr);
			
			if(row == null){
				continue;
			}
			/*if(!isIn && moneyType.equals(MoneyTypeEnum.EARNESTMONEY_VALUE)){
				continue;
			}*/

			if(ysAmount == null || "".equals(ysAmount)){
				ysAmount = FDCHelper.ZERO;
			}
			if(yzAmount == null || "".equals(yzAmount)){
				yzAmount = FDCHelper.ZERO;
			}
			if(yqAmount == null || "".equals(yqAmount)){
				yqAmount = FDCHelper.ZERO;
			}
			if(ytAmount == null || "".equals(ytAmount)){
				ytAmount = FDCHelper.ZERO;
			}			
			
			BigDecimal sum = ysAmount.subtract(yzAmount).subtract(yqAmount).subtract(ytAmount);

//			BigDecimal sum = ysAmount;

			Object obj = row.getCell("termReceiveSumAmount").getValue();
			if(obj == null){
				row.getCell("termReceiveSumAmount").setValue(sum);
			}else{
				sum = sum.add((BigDecimal)obj);
				row.getCell("termReceiveSumAmount").setValue(sum);
			}
			
//			row.getCell("termReceiveSumAmount").setValue(recMap.get("termReceiveSumAmount"));
		}
	}

	/**
	 * ��� ��ͬ�ܼ�
	 * */
	private void fillTermContractAmount(Map map) throws BOSException, SQLException {

		if (this.rowMap == null || this.rowMap.size() == 0)
			return;
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String buildIdSr = (String) iter.next();
			IRow row = (IRow) this.rowMap.get(buildIdSr);
			
			Map conMap = (Map)map.get(buildIdSr);
		
			if(row == null){
				continue;
			}
			row.getCell("termContractTotalAmount").setValue(conMap.get("termContractTotalAmount"));
		}
	}

	/**
	 * ��� ǩԼ��ͬ������ǩԼ��ͬ�����ǩԼ��ͬ�ܼۡ�ǩԼ��ͬ�ɽ��ܼ�
	 * */
	private void fillTermSignAmount(Map map) throws BOSException, SQLException {
		
		if (this.rowMap == null || this.rowMap.size() == 0)
			return;
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String buildIdSr = (String) iter.next();
			IRow row = (IRow) this.rowMap.get(buildIdSr);
			
			Map signMap = (Map)map.get(buildIdSr);
		
			if(row == null){
				continue;
			}
			row.getCell("termSignCount").setValue(signMap.get("termSignCount"));
			
			row.getCell("termSignArea").setValue(signMap.get("termSignArea"));

			row.getCell("termSignTotalAmount").setValue(signMap.get("termSignTotalAmount"));
			
			row.getCell("termSignDealTotalAmount").setValue(signMap.get("termSignDealTotalAmount"));
		}
	}
	
	/**
	 * �������δǩԼ����������δǩԼ���������δǩԼ��ͬ�ܼۡ�����δǩԼ�ɽ��ܼ�
	 */
	public void fillTermUnsignAmount(Map map) throws BOSException, SQLException{

		if (this.rowMap == null || this.rowMap.size() == 0)
			return;
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String buildIdSr = (String) iter.next();
			IRow row = (IRow) this.rowMap.get(buildIdSr);
			
			Map unsignMap = (Map)map.get(buildIdSr);
		
			if(row == null){
				continue;
			}
			row.getCell("termUnsignCount").setValue(unsignMap.get("termUnsignCount"));
			
			row.getCell("termUnsignArea").setValue(unsignMap.get("termUnsignArea"));
			
			row.getCell("termUnsignStandardTotalAmount").setValue(unsignMap.get("termUnsignStandardTotalAmount"));
			
			row.getCell("termUnsignDealTotalAmount").setValue(unsignMap.get("termUnsignDealTotalAmount"));
		}
	}
	
	/**
	 * ��� ����������������� ���׼��ܼۣ���׼�ܼۣ��Żݶ�ɽ��ܼۣ�������
	 * ע����ʵ����ľ��۾����ü���,�����resetUnionData�����л��ǻ���о��ۼ����
	 * */
	private void fillTermSellAmount(Map map,Map map1,Map map2) throws BOSException, SQLException {
		if (this.rowMap == null || this.rowMap.size() == 0)
			return;
		//����������������� ���׼��ܼۣ���׼�ܼۣ��ɽ��ܼ�
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String buildIdSr = (String) iter.next();
			IRow row = (IRow) this.rowMap.get(buildIdSr);
			
			Map sellMap = (Map)map.get(buildIdSr);
		
			if(row == null){
				continue;
			}
			row.getCell("termSumCount").setValue(sellMap.get("termSumCount"));
			
			row.getCell("termSumArea").setValue(sellMap.get("termSumArea"));
			
			row.getCell("termSumAmount").setValue(sellMap.get("termSumAmount"));
			
			row.getCell("termDealTotalAmount").setValue(sellMap.get("termDealTotalAmount"));
			
			row.getCell("termSubAmount").setValue(sellMap.get("termSubAmount"));
			
			row.getCell("termSumBaseAmount").setValue(sellMap.get("termSumBaseAmount"));
		}
		//Ӧ�ղ�����
		Iterator iter1 = map1.keySet().iterator();
		while (iter1.hasNext()) {
			String buildIdSr = (String) iter1.next();
			IRow row = (IRow) this.rowMap.get(buildIdSr);
			
			Map sellMap = (Map)map1.get(buildIdSr);
		
			if(row == null){
				continue;
			}
			BigDecimal areacomAmount = (BigDecimal)sellMap.get("termAreaCompensateAmount");
			if(areacomAmount==null){
				areacomAmount = new BigDecimal(0);
			}
			row.getCell("termAreaCompensateAmount").setValue(areacomAmount);
		}
		//ʵ�ղ�����
		Iterator iter2 = map2.keySet().iterator();
		while (iter2.hasNext()) {
			String buildIdSr = (String) iter2.next();
			IRow row = (IRow) this.rowMap.get(buildIdSr);
			
			Map sellMap = (Map)map2.get(buildIdSr);
		
			if(row == null){
				continue;
			}
			BigDecimal areacomAmount = (BigDecimal)sellMap.get("termAreaComAmount");
			if(areacomAmount==null){
				areacomAmount = new BigDecimal(0);
			}
			row.getCell("termAreaComAmount").setValue(areacomAmount);
		}
	}
	
	/**
	 * �������������������ܾ��ۡ��ܱ�׼��
	 */
	public void fillTermAllAmount(Map map) throws BOSException, SQLException{
		if (this.rowMap == null || this.rowMap.size() == 0)
		return;
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String buildIdSr = (String) iter.next();
			IRow row = (IRow) this.rowMap.get(buildIdSr);
			
			Map areaMap = (Map)map.get(buildIdSr);
		
			if(row == null){
				continue;
			}
			row.getCell("termAllCount").setValue(new BigDecimal(areaMap.get("termAllCount").toString()).setScale(0));
			
			row.getCell("termAllArea").setValue(areaMap.get("termAllArea"));
			
			row.getCell("termAllPrice").setValue(areaMap.get("termAllPrice"));
		}
	}
	
	/**
	 * ��䱣�����������������������׼�ܼ�
	 */
	public void fillTermKeepAmount(Map map) throws BOSException, SQLException{
		if (this.rowMap == null || this.rowMap.size() == 0)
			return;
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String buildIdSr = (String) iter.next();
			IRow row = (IRow) this.rowMap.get(buildIdSr);
			
			Map keepMap = (Map)map.get(buildIdSr);
		
			if(row == null){
				continue;
			}
			row.getCell("termKeepCount").setValue(new BigDecimal(keepMap.get("termKeepCount").toString()));
			
			row.getCell("termKeepArea").setValue(keepMap.get("termKeepArea"));
			
			row.getCell("termKeepStandardTotalAmount").setValue(keepMap.get("termKeepStandardTotalAmount"));
		}
	}
	
	/**
	 * ��䳷��������������������̱�׼�ܼ�  
	 */
	public void fillTermQuitAmount(Map map) throws BOSException, SQLException{
		if (this.rowMap == null || this.rowMap.size() == 0)
			return;
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String buildIdSr = (String) iter.next();
			IRow row = (IRow) this.rowMap.get(buildIdSr);
			
			Map quitMap = (Map)map.get(buildIdSr);
		
			if(row == null){
				continue;
			}
			row.getCell("termQuitCount").setValue(new BigDecimal(quitMap.get("termQuitCount").toString()));
			
			row.getCell("termQuitArea").setValue(quitMap.get("termQuitArea"));
			
			row.getCell("termQuitStandardTotalAmount").setValue(quitMap.get("termQuitStandardTotalAmount"));
		}
	}
	
	/**
	 * ��䶨����ؿ����Ƿ��Ƕ�����ؿ�
	 */
	public void fillTermPurchaseAmount(Map map,Map map1,Map map2) throws BOSException, SQLException{
		if (this.rowMap == null || this.rowMap.size() == 0)
			return;
		//������ؿ�
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String buildIdSr = (String) iter.next();
			IRow row = (IRow) this.rowMap.get(buildIdSr);
			
			Map purMap = (Map)map.get(buildIdSr);
		
			if(row == null){
				continue;
			}
			row.getCell("termPurchaseAmount").setValue(purMap.get("termPurchaseAmount"));
		}
		//����Ƿ��
		Iterator iter1 = map1.keySet().iterator();
		while (iter1.hasNext()) {
			String buildIdSr = (String) iter1.next();
			IRow row = (IRow) this.rowMap.get(buildIdSr);
			
			Map purMap = (Map)map1.get(buildIdSr);
		
			if(row == null){
				continue;
			}
			BigDecimal areageAmount = (BigDecimal)purMap.get("termAreageAmount");
			if(areageAmount==null){
				areageAmount = new BigDecimal(0.00);
			}
			row.getCell("termAreageAmount").setValue(areageAmount);
		}
		//�Ƕ�����ؿ�
		Iterator iter2 = map2.keySet().iterator();
		while (iter2.hasNext()) {
			String buildIdSr = (String) iter2.next();
			IRow row = (IRow) this.rowMap.get(buildIdSr);
			
			Map purMap = (Map)map2.get(buildIdSr);
		
			if(row == null){
				continue;
			}
			row.getCell("termUnpurchaseAmount").setValue(purMap.get("termUnpurchaseAmount"));
		}
	}
	
	public Map fillTime() {
		Map map = new HashMap();
		if (!isShowAll) {
			Date beginDate = this.beginDate;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (beginDate != null) {
//				map.put("beginDate", FDCDateHelper.getSqlDate(beginDate));
				map.put("beginDate", sdf.format(beginDate));
			}
//			Date endDate = this.endDate;
			Date endDate = addDays(this.endDate);
			if (endDate != null) {
//				map.put("endDate", FDCDateHelper.getSqlDate(FDCDateHelper.getNextDay(endDate)));
				map.put("endDate", sdf.format(endDate));
			}
		}
		map.put("ShowAll", Boolean.valueOf(isShowAll));
		return map;
	}
	//��һ������
	private Date addDays(Date date)
    {
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	calendar.add(Calendar.DATE,1);
    	return calendar.getTime();
    }
	protected boolean initDefaultFilter() {
		return true;
	}


	
	private void initTable1(){
		
		for(int i = 0; i < 11; i++){
			this.tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
		this.tblMain.getHeadMergeManager().mergeBlock(0, 11, 0, tblMain.getColumnCount() - 1);
	}
	
	private void initTable() {
		//��ͷ�ϲ�
		initTable1();
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		for (int i = 1; i < this.tblMain.getColumnCount(); i++) {
			tblMain.getColumn(i).getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
		}
		tblMain.getColumn("termAllCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termSumCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termSignCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termUnsignCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termKeepCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termQuitCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
	}

	public void loadFields() {
		super.loadFields();
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException {
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++) {
			space += " ";
		}
		if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			row.getCell("name").setValue(space + building.getName());
			row.setUserObject(node.getUserObject());
			this.rowMap.put(building.getId().toString(), row);
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("name").setValue(space + node);
		}
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
		}
	}

	private void fillProjectAndBuilding(Map timeMap) throws BOSException, EASBizException,
			SQLException {
		tblMain.removeRows();
		TreeModel buildingTree = null;
		
		if (timeMap.get("org") != null)
		{
			try
			{
				String id = (String) timeMap.get("org");

				SaleOrgUnitInfo org = new SaleOrgUnitInfo();
				org.setId(BOSUuid.read(id));
				
				buildingTree = FDCTreeHelper.getBuildingTree(org,MoneySysTypeEnum.SalehouseSys);
			} catch (Exception e)
			{
				MsgBox.showWarning("������֯����¥������������Ĭ����֯��ȡ¥������");
				try
				{
//					String sql = CommerceHelper.getPermitProjectIdSql(); //�û�Ȩ��
//					buildingTree = SHEHelper.getBuildingTree(this.actionOnLoad,	MoneySysTypeEnum.SalehouseSys, false);
					buildingTree = SHEHelper.getBuildingTree(this.actionOnLoad,	MoneySysTypeEnum.SalehouseSys, SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit());
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
		} 
		else
		{
			try
			{
//				buildingTree = SHEHelper.getBuildingTree(this.actionOnLoad,	MoneySysTypeEnum.SalehouseSys, false);
				buildingTree = SHEHelper.getBuildingTree(this.actionOnLoad,	MoneySysTypeEnum.SalehouseSys, SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit());
			} catch (Exception e)
			{
				this.handleException(e);
			}
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) buildingTree.getRoot();
		tblMain.getTreeColumn().setDepth(root.getDepth() + 1);
		fillNode(tblMain, (DefaultMutableTreeNode) root);
	}

	/**
	 * ����¥�������ݵ���������Ŀ����֯��
	 * */
	public void setUnionData() {
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row.getUserObject() == null) {
				// ���û�����
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < tblMain.getRowCount(); j++) {
					IRow rowAfter = tblMain.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int j = 1; j < this.tblMain.getColumnCount(); j++) {
					BigDecimal aimCost = FMConstants.ZERO;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(j).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								aimCost = aimCost.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								aimCost = aimCost.add(new BigDecimal(((Integer) value).toString()));
							}
						}
					}

					row.getCell(j).setValue(aimCost);

					// ���۲�Ӧ����
					row.getCell("termAvgPrice").setValue(null);
					row.getCell("termAvgBasePrice").setValue(null);
					row.getCell("termAvgDealTotalAmount").setValue(null);
					row.getCell("termAvgSubAmount").setValue(null);
				}
			}
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			IRow row = this.tblMain.getRow(rowIndex);
			if (row != null) {
				BuildingInfo building = (BuildingInfo) row.getUserObject();
				if (building != null) {					
					   //����ҵ���������ͳ��  ����
//					if(this.isSpecialBizIntoSale){
//						MsgBox.showInfo(this, "��ϸ���в�����ʾ����ҵ��ļ�¼��");
//					}
					
					DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode(building);
					
					SellStatRoomRptNewUI.showUI(this, node,
							this.beginDate, this.endDate,
							this.isPreIntoSale,this.isIncludeAttach, isShowAll);
				}
			}
		}
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	protected String getEditUIName() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected void setActionState() {

	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	public void actionPublishReport_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionPublishReport);
		super.actionPublishReport_actionPerformed(e);
	}

	/**
	 * �����������=�ܽ��/�����
	 * ���Ӽ��㣺����Ƿ�� = �����ܼ� - ���ۻؿ�   ȡ�� ������
	 * ���Ӽ��㣺�Ƕ�����ؿ� = ���ۻؿ�-������ؿ�   ȡ�� ������
	 */
	private void resetUnionData() {
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row == null)
				return;

			// ��Ŀ�ϵľ��۲����û��ܵ�ֵ,�������ܼ�/�������
			//�ܾ��� = �ܱ�׼�� / �����
			if (row.getCell("termAllPrice").getValue() instanceof BigDecimal
					&& row.getCell("termAllArea").getValue() instanceof BigDecimal
					&& FDCHelper.ZERO.compareTo((BigDecimal) row.getCell("termAllArea").getValue()) != 0) {
				row.getCell("termAllAvgPrice").setValue(
						((BigDecimal) row.getCell("termAllPrice").getValue()).divide(((BigDecimal)
								row.getCell("termAllArea").getValue()), 2,BigDecimal.ROUND_HALF_UP));
			}
			//�׼۾��� = �׼��ܼ� / �������
			if (row.getCell("termSumBaseAmount").getValue() instanceof BigDecimal
					&& row.getCell("termSumArea").getValue() instanceof BigDecimal
					&& FDCHelper.ZERO.compareTo((BigDecimal) row.getCell("termSumArea").getValue()) != 0) {
				row.getCell("termAvgBasePrice").setValue(
						((BigDecimal) row.getCell("termSumBaseAmount").getValue()).divide(((BigDecimal) 
								row.getCell("termSumArea").getValue()), 2, BigDecimal.ROUND_HALF_UP));
			}
			//��׼���� = ��׼�ܼ� / �������
			if (row.getCell("termSumAmount").getValue() instanceof BigDecimal
					&& row.getCell("termSumArea").getValue() instanceof BigDecimal
					&& FDCHelper.ZERO.compareTo((BigDecimal) row.getCell("termSumArea").getValue()) != 0) {
				row.getCell("termAvgPrice").setValue(
						((BigDecimal) row.getCell("termSumAmount").getValue()).divide(((BigDecimal) 
								row.getCell("termSumArea").getValue()), 2, BigDecimal.ROUND_HALF_UP));
			}
			//�Żݶ� = ��׼�ܼ� - �ɽ��ܼ�
			if (row.getCell("termSumAmount").getValue() instanceof BigDecimal
					&& row.getCell("termDealTotalAmount").getValue() instanceof BigDecimal) {
				Object subAmount = row.getCell("termSubAmount").getValue();
				if(subAmount == null){
					row.getCell("termSubAmount").setValue(
							((BigDecimal) row.getCell("termSumAmount").getValue()).subtract((BigDecimal) 
									row.getCell("termDealTotalAmount").getValue()));
				}else{
					row.getCell("termSubAmount").setValue(
							((BigDecimal) row.getCell("termSumAmount").getValue()).subtract((BigDecimal) 
									row.getCell("termDealTotalAmount").getValue()).subtract((BigDecimal) subAmount));
				}
			}
			//�Żݾ��� = �Żݶ� / �������
			if (row.getCell("termSubAmount").getValue() instanceof BigDecimal
					&& row.getCell("termSumArea").getValue() instanceof BigDecimal
					&& FDCHelper.ZERO.compareTo((BigDecimal) row.getCell("termSumArea").getValue()) != 0) {
				row.getCell("termAvgSubAmount").setValue(
						((BigDecimal) row.getCell("termSubAmount").getValue()).divide(((BigDecimal) 
								row.getCell("termSumArea").getValue()), 2, BigDecimal.ROUND_HALF_UP));
			}
			//�ɽ����� = �ɽ��ܼ� / �������
			if (row.getCell("termDealTotalAmount").getValue() instanceof BigDecimal
					&& row.getCell("termSumArea").getValue() instanceof BigDecimal
					&& FDCHelper.ZERO.compareTo((BigDecimal) row.getCell("termSumArea").getValue()) != 0) {
				row.getCell("termAvgDealTotalAmount").setValue(
						((BigDecimal) row.getCell("termDealTotalAmount").getValue()).divide(((BigDecimal) 
								row.getCell("termSumArea").getValue()), 2, BigDecimal.ROUND_HALF_UP));
			}
			// ǩԼ��ͬ����=ǩԼ��ͬ�ܼ�/ǩԼ��ͬ���
			if(row.getCell("termSignTotalAmount").getValue() instanceof BigDecimal 
					&& row.getCell("termSignArea").getValue() instanceof BigDecimal
					&& FDCHelper.ZERO.compareTo((BigDecimal)row.getCell("termSignArea").getValue()) != 0){
				row.getCell("termSignAvgPrice").setValue(((BigDecimal)row.getCell("termSignTotalAmount").getValue()
						).divide((BigDecimal)row.getCell("termSignArea").getValue(), 2, BigDecimal.ROUND_HALF_UP));
			}
			// ����δǩԼ��ͬ����=����δǩԼ��ͬ�ܼ�/����δǩԼ��ͬ���
			if(row.getCell("termUnsignStandardTotalAmount").getValue() instanceof BigDecimal 
					&& row.getCell("termUnsignArea").getValue() instanceof BigDecimal
					&& FDCHelper.ZERO.compareTo((BigDecimal)row.getCell("termUnsignArea").getValue()) != 0){
				row.getCell("termUnsignAvgPrice").setValue(((BigDecimal)row.getCell("termUnsignStandardTotalAmount").getValue()
						).divide((BigDecimal)row.getCell("termUnsignArea").getValue(), 2, BigDecimal.ROUND_HALF_UP));
			}    
			// �����ܼ� = ��ͬ�ܼ�+ ������  
//			if(row.getCell("termContractTotalAmount").getValue() instanceof BigDecimal && row.getCell("termAreaCompensateAmount").getValue() instanceof BigDecimal){
			if(row.getCell("termContractTotalAmount").getValue() instanceof BigDecimal){
				BigDecimal areacomAmount = (BigDecimal)row.getCell("termAreaCompensateAmount").getValue();
				if(row.getCell("termAreaCompensateAmount").getValue() == null){
					areacomAmount = new BigDecimal(0);
				}
				BigDecimal termSumSaleAmount=((BigDecimal)row.getCell("termContractTotalAmount").getValue()).add(areacomAmount);
				
				row.getCell("termSumSaleAmount").setValue(termSumSaleAmount);
			}
			// ����Ƿ�� = �����ܼ� - ���ۻؿ� - ʵ�ղ���  
			if(row.getCell("termSumSaleAmount").getValue() instanceof BigDecimal){
//					&& row.getCell("termAreageAmount").getValue() instanceof BigDecimal){
//				BigDecimal term = (BigDecimal)row.getCell("termAreageAmount").getValue();
//				if(term==null){
//					term = new BigDecimal("0");
//				}
				BigDecimal term1 = (BigDecimal)row.getCell("termReceiveSumAmount").getValue();
				if(term1==null){
					term1 = new BigDecimal("0");
				}
				BigDecimal term2 = (BigDecimal)row.getCell("termAreaComAmount").getValue();
				if(term2==null){
					term2 = new BigDecimal("0");
				}
				BigDecimal termAreageAmount=((BigDecimal)row.getCell("termSumSaleAmount").getValue()).subtract(term1).subtract(term2);
				row.getCell("termAreageAmount").setValue(termAreageAmount);
			}
		}

	}
}