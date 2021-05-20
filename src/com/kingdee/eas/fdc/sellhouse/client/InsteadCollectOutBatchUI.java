/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillCollection;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillFactory;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class InsteadCollectOutBatchUI extends AbstractInsteadCollectOutBatchUI
{
    private static final Logger logger = CoreUIObject.getLogger(InsteadCollectOutBatchUI.class);
    
    /**
     * output class constructor
     */
    public InsteadCollectOutBatchUI() throws Exception
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

	public void onLoad() throws Exception
	{
		// TODO Auto-generated method stub
		super.onLoad();
		queryAndFill();
		initTable();
		this.pkDate.setValue(new Date());
		showUnFittableData();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		tblMain.getColumn("entryID").getStyleAttributes().setHided(true);
		tblMain.getColumn("number").getStyleAttributes().setHided(true);
		this.prmtPerson.setValue((com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUserInfo().getPerson()));
	}
	
	
    public void onShow() throws Exception
	{
		// TODO Auto-generated method stub
		super.onShow();
	}
    /**
     * ��ʾת�����ò����������
     */
    private void showUnFittableData() {
    	BigDecimal outFee = null;
    	IRow row = null;
    	for(int i = 0;i<tblMain.getBody().size();i++) {
    		row = tblMain.getRow(i);
    		outFee = (BigDecimal)row.getCell("curInsteadAmount").getValue();
    		if(outFee.compareTo(new BigDecimal(0))<=0) {
    			row.getStyleAttributes().setBackground(Color.yellow);
    		}
    	}
    }

	/**
     * ƴSql
     * @return
     */
	private FDCSQLBuilder getSql() {
		FDCSQLBuilder sql = new FDCSQLBuilder();
		Map map = this.getUIContext();
		if(map==null)return null;
		Set entryIDs = (Set) map.get("entryIDs");
		if(entryIDs==null)return null;
		Iterator it = entryIDs.iterator();
		StringBuffer condition = new StringBuffer("where elseEntry.fid in(");
		while(it.hasNext()) {
			condition.append("'"+it.next().toString().trim()+"',");
		}
		condition.append("'')");
		sql.appendSql(" select distinct sellProject.Fname_L2 sellProject, sellProject.fid sellProjectId, --��Ŀ\r\n" + 
				"       building.fname_l2 building,  --¥��\r\n" + 
				"       buildingUnit.Fname_L2 unit,  --��Ԫ\r\n" + 
				"       room.fnumber roomNum,   --����\r\n" + 
				"       moneyDefine.Fname_L2 moneyDefine, --��������\r\n" + 
				"       elseEntry.Fappamount apAmount,--Ӧ�ս��\r\n" + 
				"       elseEntry.FActrevAmount actAmount, --���ս��\r\n" + 
				"       elseEntry.Frefundmentamount returnedAmount, --���˽��\r\n" + 
				"       insteaded.insteadedAmount  insteadedAmount, --�Ѵ������\r\n" + 
				" elseEntry.fid entryID --����Ӧ����¼id\r\n"+
				"from  t_she_purchaseelsepaylistentry elseEntry\r\n" + 
				"left join t_She_Purchase purchase on elseEntry.fheadid = purchase.fid \r\n" + 
				"left join t_she_room room on purchase.froomid = room.fid\r\n" + 
				"left join t_she_buildingunit buildingUnit on room.fbuildunitid = buildingUnit.Fid\r\n" + 
				"left join t_she_building building on room.fbuildingid = building.fid\r\n" + 
				"left join t_she_sellproject sellProject on building.fsellprojectid = sellProject.Fid\r\n" + 
				"left join t_she_moneydefine moneyDefine on elseEntry.Fmoneydefineid = moneyDefine.Fid\r\n" + 
				"left join \r\n" + 
				"(SELECT \r\n" + 
				"\r\n" + 
				"insteaded.FPurchaseElsePayListEntryID entryID,\r\n" + 
				"Sum(insteaded.FMoneyInsteadCur) insteadedAmount,\r\n" + 
				"insteaded.FState state \r\n" + 
				"FROM T_SHE_InsteadCollectOutBill insteaded\r\n" + 
				"\r\n" + 
				"LEFT OUTER JOIN T_SHE_PurchaseElsePayListEntry elseEntry\r\n" + 
				"ON insteaded.FPurchaseElsePayListEntryID = elseEntry.FID\r\n" + 
				" where insteaded.FState = '4AUDITTED'\r\n" +
				"GROUP BY \r\n" + 
				"insteaded.FPurchaseElsePayListEntryID, insteaded.FState)insteaded\r\n" + 
				"ON elseEntry.FID = insteaded.entryID\r\n" );
				sql.appendSql(condition.toString());
				sql.appendSql(
				" order by sellProject.Fname_L2,building.fname_l2, buildingUnit.Fname_L2,room.fnumber,moneyDefine.Fname_L2");
		return sql;
	}
	/**
	 * ��ѯ�������
	 * @throws BOSException 
	 * @throws SQLException 
	 */
    private void queryAndFill() throws BOSException, SQLException {
    	FDCSQLBuilder sql = getSql();
    	if(sql==null)return;
    	IRowSet set = null;
    	set = sql.executeQuery();
    	fillRsToTable(set);
    }
    /**
     * �����
     * @param set
     * @throws SQLException 
     * @throws BOSException 
     */
	private void fillRsToTable(IRowSet set) throws SQLException, BOSException{
		if(set==null)return;
//		if(set.ge)
			tblMain.checkParsed(true);
		
		while(set.next()) {
			
			String sellProject = set.getString("sellProject");  //��Ŀ
			String building = set.getString("building");  //¥��
			String unit = set.getString("unit");   //��Ԫ
			String roomNum = set.getString("roomNum");   //����
			String moneyDefine = set.getString("moneyDefine");   //��������
			
			BigDecimal apAmount = new BigDecimal("0.00");
			if(set.getBigDecimal("apAmount")!=null){
				apAmount = set.getBigDecimal("apAmount");  //Ӧ�ս��
			}
			
			BigDecimal actAmount = new BigDecimal("0.00");
			if(set.getBigDecimal("actAmount")!=null){
				actAmount = set.getBigDecimal("actAmount");  //Ӧ�ս��
			}
			
			
			BigDecimal returnedAmount = new BigDecimal("0.00");
			if(set.getBigDecimal("returnedAmount")!=null){
				returnedAmount = set.getBigDecimal("returnedAmount");  //Ӧ�ս��
			}
			
			
			BigDecimal insteadedAmount = new BigDecimal("0.00");
			if(set.getBigDecimal("insteadedAmount")!=null){
				insteadedAmount = set.getBigDecimal("insteadedAmount");  //Ӧ�ս��
			}
		
			/**
			 * �����Ŀid
			 */
			String sellProjectId =  "";
			if(set.getString("sellProjectId")!=null || !"".equals(set.getString("sellProjectId"))){
				sellProjectId = set.getString("sellProjectId");
			}
			String entryID = set.getString("entryID");
			IRow row = this.tblMain.addRow();
			row.getCell(1).setValue(sellProject);
			row.getCell(2).setValue(building);
			row.getCell(3).setValue(unit);
			row.getCell(4).setValue(roomNum);
			row.getCell(5).setValue(moneyDefine);
			row.getCell(6).setValue(apAmount);
			row.getCell(7).setValue(actAmount);
			row.getCell(8).setValue(returnedAmount);
			row.getCell(9).setValue(insteadedAmount);
			row.getCell("entryID").setValue(entryID);
			row.getCell("sellProjectId").setValue(sellProjectId);
			BigDecimal allInsteadedAmount = new BigDecimal( getTrasferedCount(entryID));//�����Ѵ����Ľ�����δ������
			if(returnedAmount==null) {//���˿� ���δ���=Ӧ�ս��-�������
				row.getCell("curInsteadAmount").setValue(apAmount.subtract(allInsteadedAmount));
			}else {//���˿�  ���δ���=Ӧ�ս��-���˽��-�������
				row.getCell("curInsteadAmount").setValue(apAmount.subtract(allInsteadedAmount).subtract(returnedAmount));
			}
		}
		
	}

    /**
     * ��ȡĳ���Ϲ�������Ӧ����¼������ת����
     * @return
     * @throws BOSException
     */
    private InsteadCollectOutBillCollection getAllInsteadBill(String entryID) throws BOSException{
    	InsteadCollectOutBillCollection collection = null;
    	EntityViewInfo evi = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("purchaseElsePayListEntry.id",entryID,CompareType.EQUALS));
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("*"));
    	evi.setSelector(sic);
    	evi.setFilter(filter);
    	collection = InsteadCollectOutBillFactory.getRemoteInstance().getInsteadCollectOutBillCollection(evi);
    	return collection;
    }
    /**
     * ��������ת���ķ���
     * @throws BOSException 
     */
    private float getTrasferedCount(String entryID) throws BOSException{
    	float transferedCount = 0.0f;
    	InsteadCollectOutBillCollection bills = getAllInsteadBill(entryID);
    	InsteadCollectOutBillInfo bill = null;
    	float account = 0.0f;
    	for(int i = 0;i<bills.size();i++){
    		bill = bills.get(i);
    		account = account + bill.getMoneyInsteadCur().floatValue();
    	}
    	transferedCount = account;
    	return transferedCount;
    }
   
	private void initTable() {
		for(int i=0;i<tblMain.getColumnCount();i++) {
			if(i!=0&&i!=10&&i!=12) {
				tblMain.getColumn(i).getStyleAttributes().setLocked(true);
			}
		}
	}
	/**
	 * ȡ��������������
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private Set getRows() throws EASBizException, BOSException {
		Set rows = new HashSet();
		Map map = null;
		IRow row = null;
		for(int i=0;i<tblMain.getBody().size();i++) {
			row = tblMain.getRow(i);
			if(row.getStyleAttributes().getBackground().equals(Color.yellow)) {
				return null;
			}
			//TODO:���ݱ���������ñ���
			ICell cell = row.getCell("number");
//			setBillNumber(cell);
			map = new HashMap();
			map.put("number", row.getCell("number").getValue());
			map.put("curAmount", row.getCell("curInsteadAmount").getValue().toString());
			map.put("remark", row.getCell("remark").getValue());
			map.put("elseEntryID", row.getCell("entryID").getValue().toString());
			
			//���������ĿID
			if(row.getCell("sellProjectId").getValue()!=null){
				map.put("sellProjectId", row.getCell("sellProjectId").getValue().toString());
			}
			
			rows.add(map);
		}
		return rows;
	}
	
	/**
	 * ��������
	 */
	public void actionBatchInstead_actionPerformed(ActionEvent e)
			throws Exception
	{
		super.actionBatchInstead_actionPerformed(e);
		if(tblMain.getBody().size()==0) {
			MsgBox.showError("û��Ҫ��������ݣ�");
			return;
		}
		Set rows = getRows();
		if(rows==null) {
			MsgBox.showError("���δ���������в���������ݣ����飡");
			return;
		}
		PersonInfo personInfo = (PersonInfo) this.prmtPerson.getValue();
		Date bizDate = (Date) this.pkDate.getValue();
		InsteadCollectOutBillFactory.getRemoteInstance().generateNewData(personInfo, bizDate, rows);
		MsgBox.showInfo("���������ɹ���");
		this.uiWindow.close();
	}
	
	
	
	/**
	 * ȡ��
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionCancel_actionPerformed(e);
		this.uiWindow.close();
	}
	/**
	 * ��¼�༭�����¼�
	 */
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception
	{
		super.tblMain_editStopped(e);
		int rowNum = e.getRowIndex();
		int colNum = e.getColIndex();
		if(colNum != tblMain.getColumnIndex("curInsteadAmount"))return;
		try {
			String value = (String) tblMain.getCell(rowNum, colNum).getValue();
			BigDecimal bigDecimal = new BigDecimal(value==null?"0":value.toString());
			BigDecimal aptAmount = (BigDecimal)tblMain.getCell(rowNum, "shouldCollect").getValue()==null?new BigDecimal(0):(BigDecimal)tblMain.getCell(rowNum, "shouldCollect").getValue();//Ӧ�ս��
//			BigDecimal insteadedAmount = (BigDecimal)tblMain.getCell(rowNum, "insteadedAmount").getValue()==null?new BigDecimal(0):(BigDecimal)tblMain.getCell(rowNum, "insteadedAmount").getValue();//�Ѵ������
			BigDecimal returnedAmount = (BigDecimal)tblMain.getCell(rowNum, "returnedAmount").getValue()==null?new BigDecimal(0):(BigDecimal)tblMain.getCell(rowNum, "returnedAmount").getValue();//���˽��
			BigDecimal allInsteadedAmount = new BigDecimal( getTrasferedCount(tblMain.getCell(rowNum, "entryID").getValue().toString()));//�����Ѵ����Ľ�����δ������
			if(bigDecimal.add(allInsteadedAmount).add(returnedAmount).compareTo(aptAmount)>0) {
				MsgBox.showWarning("���δ������ܴ���"+aptAmount.subtract(allInsteadedAmount).subtract(returnedAmount));
				tblMain.getCell(rowNum, "curInsteadAmount").setValue(e.getOldValue());
				return;
			}
			if(bigDecimal.compareTo(new BigDecimal(0))>0) {
				tblMain.getRow(rowNum).getStyleAttributes().setBackground(Color.white);
			}else {
				tblMain.getRow(rowNum).getStyleAttributes().setBackground(Color.yellow);
			}
		}catch(Exception e1) {
			tblMain.getCell(rowNum, colNum).setValue(e.getOldValue());
			tblMain.getRow(rowNum).getStyleAttributes().setBackground(Color.yellow);
		}
		
	}
	
	/**
	 * ɾ����
	 */
	public void actionBtnRemove_actionPerformed(ActionEvent e) throws Exception
	{
		// TODO Auto-generated method stub
		super.actionBtnRemove_actionPerformed(e);
		int []rows = KDTableUtil.getSelectedRows(tblMain);
		if(rows == null ||rows.length == 0) {
			MsgBox.showWarning("��ѡ��Ҫɾ�����У�");
			return;
		}
		
		int rowIndex = -1;
		for(int i = rows.length-1;i>=0;i--) {
			rowIndex = rows[i];
			tblMain.removeRow(rowIndex);
		}
	}
	/**
	 * ���ݱ���������õ��ݱ��
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void setBillNumber(ICell cell) throws BOSException, EASBizException {
		if(cell != null && cell.getValue() != null && !cell.getValue().toString().equals("")){
			return;
		}
		String entryID = tblMain.getCell(cell.getRowIndex(), "entryID").getValue().toString();
		InsteadCollectOutBillCollection bills = getAllInsteadBill(entryID);
//		IObjectValue obj = new InsteadCollectOutBillInfo();
		IObjectValue obj = bills.get(0);
		String currentOrgId = FDCClientHelper.getCurrentOrgId();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		if (iCodingRuleManager.isExist(obj, currentOrgId)) {
			// EditUI�ṩ�˷�������û�е��ã���onload����ã��Ը��ǳ�����loadfields����ĵ��ã��õ���û�д���Ϻ�ѡ��

			boolean isAddView = FDCClientHelper.isCodingRuleAddView(obj, currentOrgId);
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(obj, currentOrgId)) { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(obj, currentOrgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(obj, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager.isDHExist(obj, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				cell.setValue(number);
		}
	}
}