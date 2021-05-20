///**
// * output package name
// */
//package com.kingdee.eas.fdc.sellhouse.client;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
//import java.io.File;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.sql.SQLException;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//import java.util.Vector;
//
//import javax.swing.Action;
//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.DefaultTreeModel;
//import javax.swing.tree.MutableTreeNode;
//import javax.swing.tree.TreePath;
//import javax.swing.tree.TreeSelectionModel;
//
//import org.apache.log4j.Logger;
//import org.htmlparser.Node;
//import org.htmlparser.NodeFilter;
//import org.htmlparser.Parser;
//import org.htmlparser.filters.NodeClassFilter;
//import org.htmlparser.filters.OrFilter;
//import org.htmlparser.nodes.TagNode;
//import org.htmlparser.tags.InputTag;
//import org.htmlparser.tags.OptionTag;
//import org.htmlparser.tags.SelectTag;
//import org.htmlparser.util.NodeList;
//import org.htmlparser.util.ParserException;
//import org.jdesktop.jdic.browser.WebBrowser;
//import org.jdesktop.jdic.browser.WebBrowserEvent;
//import org.jdesktop.jdic.browser.WebBrowserListener;
//import org.jdesktop.jdic.init.JdicManager;
//
//import com.kingdee.bos.BOSException;
//import com.kingdee.bos.ctrl.kdf.table.ICell;
//import com.kingdee.bos.ctrl.kdf.table.IColumn;
//import com.kingdee.bos.ctrl.kdf.table.IRow;
//import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
//import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
//import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
//import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
//import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
//import com.kingdee.bos.ctrl.swing.KDComboBox;
//import com.kingdee.bos.ctrl.swing.KDTextField;
//import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
//import com.kingdee.bos.dao.IObjectValue;
//import com.kingdee.bos.dao.query.SQLExecutorFactory;
//import com.kingdee.bos.ui.face.CoreUIObject;
//import com.kingdee.bos.util.BOSUuid;
//import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
//import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFieldFactory;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFieldInfo;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFunctionCollection;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFunctionInfo;
//import com.kingdee.eas.fdc.sellhouse.WebMarkProcessCollection;
//import com.kingdee.eas.fdc.sellhouse.WebMarkProcessInfo;
//import com.kingdee.eas.fdc.sellhouse.WebMetaTypeEnum;
//import com.kingdee.eas.framework.CoreBaseCollection;
//import com.kingdee.eas.framework.ICoreBase;
//import com.kingdee.eas.util.SysUtil;
//import com.kingdee.eas.util.client.EASResource;
//import com.kingdee.eas.util.client.MsgBox;
//import com.kingdee.jdbc.rowset.IRowSet;
//import com.kingdee.util.enums.EnumUtils;
//
///**
// * output class name
// */
//public class WebMarkFieldEditUI extends AbstractWebMarkFieldEditUI {
//	private static final Logger logger = CoreUIObject
//			.getLogger(WebMarkFieldEditUI.class);
//
//	private boolean dataChange = false;
//
//	String sSchmID = "";
//
//	// String sModule = "";
//
//	IRowSet rsFunc = null;
//
//	HashMap fdcFieldMap = null;
//
//	WebBrowser webBrowser = null;
//
//	boolean isLoadProcEnd = false;
//
//	protected boolean isEnabled = false;
//
//	// HashMap funcMap = null;
//	// WebMarkFunctionCollection funcColls = null;
//	// HashMap procMap = null;
//	// WebMarkProcessCollection procColls = null;
//
//	/**
//	 * output class constructor
//	 */
//	public WebMarkFieldEditUI() throws Exception {
//		super();
//	}
//
//	/**
//	 * output storeFields method
//	 */
//	public void storeFields() {
//		super.storeFields();
//	}
//
//	/**
//	 * output actionOnLoad_actionPerformed
//	 */
//	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
//		super.actionOnLoad_actionPerformed(e);
//	}
//
//	/**
//	 * output actionSave_actionPerformed
//	 */
//	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
//		// super.actionSave_actionPerformed(e);
//		saveField();
//	}
//
//	/*
//	 * װ�ر��
//	 */
//	private void setFields(IRowSet rs) {
//		this.tblEntrys.removeRows();
//		try {
//			while (rs.next()) {
//				IRow row = this.tblEntrys.addRow();
//				// ��������
//				row.getCell("FName").setValue(rs.getString("FName"));
//				// ID
//				row.getCell("FWebID").setValue(rs.getString("FWebID"));
//				// name
//				row.getCell("FWebName").setValue(rs.getString("FWebName"));
//				// Ԫ������
//				row.getCell("FWebMetaType").setValue(
//						WebMetaTypeEnum.getEnum(rs.getString("FWebMetaType")));
//				// ʵ��ֵ
//				row.getCell("FWebValue").setValue(rs.getString("FWebValue"));
//				// ϵͳ����
//				row.getCell("FFileName").setValue(
//						reMapName(fdcFieldMap, rs.getString("FFileName")));
//				// ��Ӧ����
//				row.getCell("FDefultValue").setValue(
//						rs.getString("FDefultValue"));
//				// webid
//				row.getCell("FID").setValue(rs.getString("FID"));
//			}
//		} catch (SQLException e) {
//			// TODO �Զ����� catch ��
//			e.printStackTrace();
//		}
//	}
//
//	private String reMapName(HashMap map, String value) {
//		String sRe = "";
//		Set set = map.entrySet();
//		Iterator it = set.iterator();
//		while (it.hasNext()) {
//			Map.Entry entry = (Map.Entry) it.next();
//			if (entry.getValue().equals(value)) {
//				sRe = entry.getKey().toString();
//				break;
//			}
//		}
//		return sRe;
//	} /*
//		 * pack Entrys
//		 */
//
//	private CoreBaseCollection packFields() {
//		WebMarkProcessCollection procColls = (WebMarkProcessCollection) cmbProc
//				.getUserObject();
//		if (procColls == null || procColls.size() < 1)
//			return null;
//		WebMarkProcessInfo procInfo = procColls.get(cmbProc.getSelectedIndex());
//		String pk = procInfo.getId().toString();
//
//		CoreBaseCollection colls = new CoreBaseCollection();
//		WebMarkFieldInfo wkFieldInfo = null;
//		for (int i = 0; i < this.tblEntrys.getRowCount(); i++) {
//			IRow row = this.tblEntrys.getRow(i);
//			wkFieldInfo = new WebMarkFieldInfo();
//			if (row.getCell(1).getValue()==null || row.getCell(1).getValue().toString().equals(""))
//				break;
//
//			// ��������
//			wkFieldInfo.setName(toString(row.getCell("FName").getValue(), ""));
//			// ID
//			wkFieldInfo
//					.setWebID(toString(row.getCell("FWebID").getValue(), ""));
//			// name
//			wkFieldInfo.setWebName(toString(row.getCell("FWebName").getValue(),
//					""));
//			// Ԫ������
//			wkFieldInfo.setWebMetaType((WebMetaTypeEnum) row.getCell(
//					"FWebMetaType").getValue());
//			// ʵ��ֵ
//			wkFieldInfo.setWebValue(toString(row.getCell("FWebValue")
//					.getValue(), ""));
//			// ϵͳ����
//			String selField = toString(fdcFieldMap.get(toString(row.getCell(
//					"FFileName").getValue().toString().trim(), "")), "");
//			wkFieldInfo.setFileName(selField.toString());
//			// ��Ӧ����
//			wkFieldInfo.setDefultValue(toString(row.getCell("FDefultValue")
//					.getValue(), ""));
//			// parentid
//			WebMarkProcessInfo wkProcInfo = new WebMarkProcessInfo();
//			// String pk = ((HashMap)
//			// procMap.get(cmbProc.getSelectedItem())).get(
//			// "FID").toString().trim();
//			wkProcInfo.setId(BOSUuid.read(pk));
//			wkFieldInfo.setParent(wkProcInfo);
//
//			// fseq
//			wkFieldInfo.setSeq(i);
//			colls.add(wkFieldInfo);
//		}
//		return colls;
//	}
//
//	private String toString(Object str, String rt) {
//		String rrt = rt;
//		try {
//			if (str != null) {
//				rrt = str.toString();
//			}
//		} catch (Exception ex) {
//		} finally {
//			return rrt;
//		}
//	}
//
//	/**
//	 * output actionSubmit_actionPerformed
//	 */
//	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
//		// super.actionSubmit_actionPerformed(e);
//		saveField();
//	}
//
//	public void loadFields() {
//		super.loadFields();
//	}
//
//	/*
//	 * ���桢�ݴ桢�޸Ĳ���
//	 */
//
//	public void saveField() throws Exception {
//		try {
//			
//			checkSystemData();
//			CoreBaseCollection wkFieldColls = packFields();
//			if (wkFieldColls == null)
//				return;
//			if (wkFieldColls.size() < 1)
//				return;
//			WebMarkFieldFactory.getRemoteInstance().submit(wkFieldColls);
//			IRowSet rs = loadField();
//			if (rs != null)
//				setFields(rs);
//			dataChange = false;
//			FDCMsgBox.showInfo("����ɹ���");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * У�顰ϵͳ���ݡ�����Ϊ��
//	 */
//	private void checkSystemData(){
//		for (int i = 0; i < this.tblEntrys.getRowCount(); i++) {
//			IRow row = tblEntrys.getRow(i);
//			if(row==null){
//				continue;
//			}
//			if(row.getCell(1).getValue()==null || "".equals(row.getCell(1).getValue().toString())){
//				FDCMsgBox.showWarning(this,"��"+(i+1)+"�е�ID����Ϊ��!");
//				SysUtil.abort();
//			}else if(row.getCell("FFileName").getValue()==null || "".equals(row.getCell("FFileName").getValue().toString())){
//				FDCMsgBox.showWarning(this,"��"+(i+1)+"�е�ϵͳ���ݲ���Ϊ��!");
//				SysUtil.abort();
//			}
//		}
//	}
//	/**
//	 * output actionAddMeta_actionPerformed method
//	 */
//	public void actionAddMeta_actionPerformed(ActionEvent e) throws Exception {
//		if (this.kDTreeWeb.getRowCount() <= 1) {
//			FDCMsgBox.showInfo("Ԫ��tree��û��Ԫ�أ�������ˢ��Ԫ�أ�");
//			return;
//		}
//		if (kDTreeWeb.getSelectionCount() < 1) {
//			FDCMsgBox.showInfo("û��ѡ��Ԫ�أ�");
//			return;
//		}
//		this.tblEntrys.checkParsed();
//		int index = -1;
//		index = this.tblEntrys.getRowCount();
//
//		if (index != -1) {
//			tblEntrys.addRow(index);
//		} else {
//			tblEntrys.addRow();
//		}
//	
//		if(this.tblEntrys.getColumn("FWebID")!=null){
//			this.tblEntrys.getColumn("FWebID").setRequired(true);
//		}
//		if(this.tblEntrys.getColumn("FFileName")!=null){
//			this.tblEntrys.getColumn("FFileName").setRequired(true);
//		}
//		
//		index = this.tblEntrys.getRowCount() - 1;
//		HashMap treeMap = (HashMap) ((DefaultKingdeeTreeNode) kDTreeWeb
//				.getLastSelectedPathComponent()).getUserObject();
//		if (treeMap == null)
//			return;
//
//		// ID
//		this.tblEntrys.getCell(index, "FWebID").setValue(
//				toString(treeMap.get("id"), ""));
//		// name
//		this.tblEntrys.getCell(index, "FWebName").setValue(
//				toString(treeMap.get("name"), ""));
//		// Ԫ������
//		WebMetaTypeEnum metaType = (WebMetaTypeEnum) treeMap.get("type");
//		this.tblEntrys.getCell(index, "FWebMetaType").setValue(metaType);
//		// ϵͳ�ֶ�
//		// KDComboBox combo= (KDComboBox)
//		// this.tblEntrys.getCell(index,"FFileName").getEditor().getComponent();
//		// if(combo.getItemCount()>0)
//		// combo.setSelectedIndex(0);
////		KDTDefaultCellEditor txtEditor = (KDTDefaultCellEditor) this.tblEntrys
////				.getCell(index, "FFileName").getEditor();
//		// ʵ��ֵ
//		tblEntrys.getCell(index, "FWebValue").setEditor(
//				new KDTDefaultCellEditor(new KDTextField()));
//		if (metaType.getValue() == WebMetaTypeEnum.select.getValue()) {
//			tblEntrys.getCell(index, "FWebValue").setEditor(
//					new KDTDefaultCellEditor(new KDComboBox()));
//			KDTDefaultCellEditor txtEditor = (KDTDefaultCellEditor) tblEntrys
//					.getCell(index, "FWebValue").getEditor();
//			KDComboBox combobox = (KDComboBox) txtEditor.getComponent();
//			Vector v = (Vector) treeMap.get("selectList");
//			if (v == null)
//				return;
//			for (int i = 0; i < v.size(); i++) {
//				combobox.addItem(v.get(i));
//			}
//		}
//
//	}
//
//	/**
//	 * output actionDelMeta_actionPerformed method
//	 */
//	public void actionDelMeta_actionPerformed(ActionEvent e) throws Exception {
//		tblEntrys.checkParsed();
//		int i = -1;
//		i = this.tblEntrys.getSelectManager().getActiveRowIndex();
//		if (i == -1) {
//			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString(
//					"com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
//					"Selected_Delete"));
//			return;
//		}
//		this.tblEntrys.removeRow(i);
//	}
//
//	/**
//	 * output actionUpMeta_actionPerformed method
//	 */
//	public void actionUpMeta_actionPerformed(ActionEvent e) throws Exception {
//		try {
//			tblEntrys.checkParsed();
//			int lCurRow = this.tblEntrys.getSelectManager().getActiveRowIndex();
//			if (lCurRow < 1)
//				return;
//			this.tblEntrys.moveRow(lCurRow, lCurRow - 1);
//			this.tblEntrys.getSelectManager().setActiveRowIndex(lCurRow - 1);
//			this.tblEntrys.getSelectManager().select(lCurRow - 1, 0,
//					lCurRow - 1, this.tblEntrys.getColumnCount());
//		} catch (Exception e1) {
//			e1 = null;
//		}
//	}
//
//	/**
//	 * output actionDownMeta_actionPerformed method
//	 */
//	public void actionDownMeta_actionPerformed(ActionEvent e) throws Exception {
//		try {
//			int lCurRow = this.tblEntrys.getSelectManager().getActiveRowIndex();
//			if (lCurRow == this.tblEntrys.getRowCount() - 1)
//				return;
//			boolean bRe = this.tblEntrys.exchangeRow(lCurRow, lCurRow + 1);
//			System.out.println(bRe);
//			this.tblEntrys.getSelectManager().setActiveRowIndex(lCurRow + 1);
//			this.tblEntrys.getSelectManager().select(lCurRow + 1, 0,
//					lCurRow + 1, this.tblEntrys.getColumnCount());
//		} catch (Exception e1) {
//			e1 = null;
//		}
//
//	}
//
//	/**
//	 * output actionRefreshMate_actionPerformed method
//	 */
//	public void actionRefreshMate_actionPerformed(ActionEvent e)
//			throws Exception {
//		if (this.cmbMetaType.getSelectedItem().toString().trim().equals(""))
//			return;
//		extractText(cmbMetaType.getSelectedItem().toString());
//	}
//
//	/**
//	 * output actionRefreshWeb_actionPerformed method
//	 */
//	public void actionRefreshWeb_actionPerformed(ActionEvent e)
//			throws Exception {
//		loadURL();
//	}
//
//	private void loadContext() {
//		// rsFunc = (IRowSet) this.getUIContext().get("funcData");
//		sSchmID = (String) this.getUIContext().get("schemaid");
//		// sModule = (String) this.getUIContext().get("moduleid");
//	}
//
//	/*
//	 * �õ���������
//	 */
//	private IRowSet getFunctionData(String sSchmID) {
//		StringBuffer SQLProcess = new StringBuffer();
//		// String ssql = arrrayList2String(sqlList);
//		SQLProcess.append("SELECT * FROM t_she_WebMarkFunction where 1=1");
//
//		if (!sSchmID.trim().equals("")) {
//			SQLProcess.append(" and FParentID='" + sSchmID.toString() + "'");
//		}
//		try {
//			IRowSet rs = SQLExecutorFactory.getRemoteInstance(
//					SQLProcess.toString()).executeSQL();
//			return rs;
//		} catch (BOSException e) {
//			// TODO �Զ����� catch ��
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	private void funcCombox_ActionChange(ActionEvent e) {
//		isLoadProcEnd = false;
//		cmbProc.removeAllItems();
//		WebMarkProcessCollection procColls = new WebMarkProcessCollection();
//		// procMap = new HashMap();
//		// HashMap innerMap = null;
//		if (cmbFunc.getUserObject() == null)
//			return;
//		WebMarkFunctionInfo funcInfo = ((WebMarkFunctionCollection) cmbFunc
//				.getUserObject()).get(cmbFunc.getSelectedIndex());
//		if (funcInfo == null || funcInfo.getId().toString().equals(""))
//			return;
//		KDComboBox cb = (KDComboBox) e.getSource();
//		String sName = (String) cb.getSelectedItem();
//		// String sID = funcMap.get(sName).toString();
//		IRowSet rsProc = getProcessData(toString(funcInfo.getId(), ""));
//		WebMarkProcessInfo procInfo;
//		try {
//			while (rsProc.next()) {
//				// innerMap = new HashMap();
//				procInfo = new WebMarkProcessInfo();
//				procInfo.setId(BOSUuid.read(rsProc.getString("FID").trim()
//						.toString()));
//				procInfo.setUrl(rsProc.getString("FUrl").trim().toString());
//				procInfo.setProcessName(rsProc.getString("FProcessName_l2")
//						.trim().toString());
//				// innerMap
//				// .put("FUrl", rsProc.getString("FUrl").trim().toString());
//				// innerMap.put("FID",
//				// rsProc.getString("FID").trim().toString());
//				// rsProc.getString("FUrl").trim().toString()
//				// procMap.put(rsProc.getString("FProcessName_l2").trim()
//				// .toString(), innerMap);
//				cmbProc.addItem(rsProc.getString("FProcessName_l2").trim()
//						.toString());
//				procColls.add(procInfo);
//			}
//			cmbProc.setUserObject(procColls);
//		} catch (SQLException e1) {
//			// TODO �Զ����� catch ��
//			e1.printStackTrace();
//		}
//		if (cb.getItemCount() < 1) {
//			return;
//		}
//		cmbProc.setSelectedIndex(-1);
//		isLoadProcEnd = true;
//	}
//
//	/*
//	 * ����COMBOXѡ���¼�
//	 */
//	private void procCombox_itemStateChanged(ItemEvent e) throws SQLException {
//		if (isLoadProcEnd == false) {
//			return;
//		}
//		KDComboBox cb = (KDComboBox) e.getSource();
//		if (cb.getItemCount() < 1) {
//			return;
//		}
//		this.txtURL.setText("");
//		WebMarkProcessCollection procColls = (WebMarkProcessCollection) cmbProc
//				.getUserObject();
//		if (procColls == null) {
//			return;
//		}
//		if (cb == null) {
//			return;
//		}
//		// String sName = (String) cb.getSelectedItem();
//		WebMarkProcessInfo procInfo = procColls.get(cmbProc.getSelectedIndex());
//		this.txtURL.setText(procInfo.getUrl().toString());
//		// this.txtURL.setText(((HashMap) procMap.get(sName)).get("FUrl")
//		// .toString().trim());
//
//		loadURL();
//		// extractText("root");
//		IRowSet rs = loadField();
//		if (rs != null)
//			setFields(rs);
//	}
//
//	/*
//	 * �����ֶ�
//	 */
//	private IRowSet loadField() {
//		IRowSet rs = null;
//		try {
//			WebMarkProcessCollection procColls = (WebMarkProcessCollection) cmbProc
//					.getUserObject();
//			if (procColls == null || procColls.size() < 1)
//				return null;
//			WebMarkProcessInfo procInfo = procColls.get(cmbProc
//					.getSelectedIndex());
//
//			String sFuncID = procInfo.getId().toString();
//			// String sFuncID = ((HashMap) procMap.get(this.cmbProc
//			// .getSelectedItem())).get("FID").toString().trim();
//			StringBuffer SQLProcess = new StringBuffer();
//			SQLProcess
//					.append("select * from t_SHE_WebMarkField where FParentID='"
//							+ sFuncID.toString() + "' order by FSeq");
//			rs = SQLExecutorFactory.getRemoteInstance(SQLProcess.toString())
//					.executeSQL();
//		} catch (BOSException e) {
//			// TODO �Զ����� catch ��
//			e.printStackTrace();
//		} finally {
//			return rs;
//		}
//	}
//
//	public void setFilterComb() {
//		this.cmbMetaType.removeAllItems();
//		this.cmbMetaType.addItem("ALL");
//		this.cmbMetaType.addItems(EnumUtils.getEnumList(
//				"com.kingdee.eas.fdc.sellhouse.WebMetaTypeEnum").toArray());
//		this.cmbMetaType.setSelectedIndex(0);
//	}
//
//	private void setControlEvent() {
//		// ����-�¼�����
//		this.cmbFunc.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				funcCombox_ActionChange(arg0);
//			}
//		});
//		// ����-�¼�����
//		this.cmbProc.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent arg0) {
//				// TODO �Զ����ɷ������
//				try {
//					procCombox_itemStateChanged(arg0);
//				} catch (SQLException e) {
//					// TODO �Զ����� catch ��
//					e.printStackTrace();
//				}
//			}
//		});
//
//		tblEntrys.addKDTEditListener(new KDTEditAdapter() {
//			// �༭������
//			public void editStopped(KDTEditEvent e) {
//				dataChange = true;
//			}
//		});
//
//	}
//
//	private void setBtnStatus() {
//		this.actionAddRow.putValue(Action.SMALL_ICON, EASResource
//				.getIcon("imgTbtn_addline"));
//		this.actionInsertRow.putValue(Action.SMALL_ICON, EASResource
//				.getIcon("imgTbtn_insert"));
//		this.actionDelRow.putValue(Action.SMALL_ICON, EASResource
//				.getIcon("imgTbtn_deleteline"));
//
//		// this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.SellProjectQuery");
//	}
//
//	
//	
//	/*
//	 * ��ʼ��
//	 */
//	public void onLoad() throws Exception {
//		loadContext();
//		super.onLoad();
//		// �ؼ���ʼ��
//		txtMsg.setText("");
//		this.actionCopy.setEnabled(false);
//		this.actionCopy.setVisible(false);
//		this.actionAttachment.setEnabled(false);
//		this.actionAttachment.setVisible(false);
//		
//		this.actionNext.setEnabled(false);
//		this.actionNext.setVisible(false);
//		this.actionLast.setEnabled(false);
//		this.actionLast.setVisible(false);
//		this.actionFirst.setEnabled(false);
//		this.actionFirst.setVisible(false);
//		this.actionLast.setEnabled(false);
//		this.actionLast.setVisible(false);
//		this.actionAddNew.setEnabled(false);
//		this.actionAddNew.setVisible(false);
//		this.actionCancel.setEnabled(false);
//		this.actionCancel.setVisible(false);
//		this.actionCancelCancel.setEnabled(false);
//		this.actionCancelCancel.setVisible(false);
//		this.actionPre.setEnabled(false);
//		this.actionPre.setVisible(false);
//		this.actionPrint.setEnabled(false);
//		this.actionPrint.setVisible(false);
//		this.actionPrintPreview.setEnabled(false);
//		this.actionPrintPreview.setVisible(false);
//		this.actionEdit.setEnabled(false);
//		this.actionEdit.setVisible(false);
//		this.actionRemove.setEnabled(false);
//		this.actionRemove.setVisible(false);
//		
//		this.menuSubmitOption.setVisible(false);
//		this.actionSave.setEnabled(false);
//		this.actionSave.setVisible(false);
//		
//		this.btnAddMeta.setEnabled(true);
//		this.btnDelMeta.setEnabled(true);
//		this.btnUpMeta.setEnabled(true);
//		this.btnDownMeta.setEnabled(true);
//		this.btnRefreshMate.setEnabled(true);
//		this.btnRefreshWeb.setEnabled(true);
//		this.tblEntrys.setColumnMoveable(true);
//		this.kDTreeWeb
//				.removeAllChildrenFromParent((MutableTreeNode) this.kDTreeWeb
//						.getModel().getRoot());
//		this.kDTreeWeb.getSelectionModel().setSelectionMode(
//				TreeSelectionModel.SINGLE_TREE_SELECTION);// ���õ�ѡģʽ
//		tblEntrys.checkParsed();
//	
//		//setBtnStatus();		by jeegan �о�ûʲô��
//		setFilterComb();
//		cmbFunc.removeAllItems();
//		// ���ع��ܺͲ���
//		rsFunc = this.getFunctionData(sSchmID);
//		if (rsFunc != null) {
//			// funcMap = new HashMap();
//			WebMarkFunctionCollection funcColls = new WebMarkFunctionCollection();
//			WebMarkFunctionInfo funcInfo;
//			while (rsFunc.next()) {
//				funcInfo = new WebMarkFunctionInfo();
//				if(rsFunc.getString("FID")!=null && !"".equals(rsFunc.getString("FID").toString())){
//					funcInfo.setId(BOSUuid.read(rsFunc.getString("FID").trim()
//							.toString()));
//				
//				}
//				
//				if(rsFunc.getString("FFunctionName_l2")!=null && !"".equals(rsFunc.getString("FFunctionName_l2").toString())){
//					funcInfo.setFunctionName(rsFunc.getString("FFunctionName_l2")
//							.trim().toString());
//				}
//				
//				funcColls.add(funcInfo);
//				// funcMap.put(rsFunc.getString("FFunctionName_l2").trim()
//				// .toString(), rsFunc.getString("FID").trim().toString());
//				
//				if(rsFunc.getString("FFunctionName_l2")!=null && !"".equals(rsFunc.getString("FFunctionName_l2").toString())){
//					this.cmbFunc.addItem(rsFunc.getString("FFunctionName_l2")
//							.trim().toString());
//				}
//				
//			
//			}
//			this.cmbFunc.setUserObject(funcColls);
//		}
//		cmbFunc.setSelectedIndex(-1);
//		// �ؼ��¼�
//		setControlEvent();
//		// ��¼��ʼ��
//		setEntrys();
//		// this.tblEntrys.getViewManager().freezeView(1,1);
//		try {
//			// ��ʼ��WebBrowser
//			// ���￪ʼ�Ѿ�������jdic��org.jdesktop.jdic.init.JdicManager,initShareNative����
//			// �ͼ�����setBinaryPath()����
//			String path = System.getProperty("easclient.root");
//			/**
//			 * ��������ļ���dll��Ŀ¼����
//			 * update by renliang 
//			 */
//			CRMClientHelper.getDLLForWebMark(path);
//			
//			logger.info("WebMark:***JdicManager.getManager().setBinaryPath(path);");
//			JdicManager.getManager().setBinaryPath(path);
//			logger.info("WebMark:***webBrowser = new WebBrowser();");
//			webBrowser = new WebBrowser();
//			logger.info("WebMark:***initWebBowser();");
//			initWebBowser();
//			logger.info("WebMark:***kdPWebBowser.setAutoscrolls(true);");
//			kdPWebBowser.setAutoscrolls(true);
//			sclPanel.setViewportView(webBrowser);
//			sclPanel.setKeyBoardControl(true);
//			sclPanel.setAutoscrolls(true);
//
//			logger.info("WebMark:***this.webBrowser.setURL();");
//			this.webBrowser.setURL();
//		} catch (Exception e) {
////			e.printStackTrace();
//			MsgBox.showError("easclient.root="+System.getProperty("easclient.root")+";jdic.dll�����Ѿ�����ģ����أ�");
//		}
//	}
//
//	/*
//	 * htmlparser -webBowser ��ʼ����ҳ��������¼�
//	 */
//	private void initWebBowser() {
//		webBrowser.addWebBrowserListener(new WebBrowserListener() {
//			public void downloadStarted(WebBrowserEvent event) {
//				updateStatusInfo("Loading started.");
//			}
//
//			public void downloadCompleted(WebBrowserEvent event) {
//				updateStatusInfo("Loading completed.");
//			}
//
//			public void downloadProgress(WebBrowserEvent event) {
//				// updateStatusInfo("Loading in progress...");
//			}
//
//			public void downloadError(WebBrowserEvent event) {
//				updateStatusInfo("Loading error.");
//			}
//
//			public void documentCompleted(WebBrowserEvent event) {
//				webBrowser
//						.executeScript("document.onclick=function(){document.title=document.activeElement.name;}");
//				// updateStatusInfo("Document loading completed.");
//			}
//
//			public void titleChange(WebBrowserEvent event) {
//				setTreeSelect(event);
//				// updateStatusInfo("Title of the browser window changed.");
//			}
//
//			public void statusTextChange(WebBrowserEvent event) {
//				// updateStatusInfo("Status text changed.");
//			}
//
//			public void windowClose(WebBrowserEvent arg0) {
//				// TODO �Զ����ɷ������
//
//			}
//
//			public void initializationCompleted(WebBrowserEvent arg0) {
//				// TODO �Զ����ɷ������
//				
//			}
//		});
//	}
//
//	/*
//	 * ����webBrowsers�¼��е��ֶΣ���kdTree�е��ֶν���ѡ��
//	 */
//	private void setTreeSelect(WebBrowserEvent event) {
//		boolean isGet = false;
//		txtMsg.setText("");
//		if (webBrowser.getURL() == null)
//			return;
//		if (webBrowser.getURL().toString().equals(""))
//			return;
//		try {
//			String nodeName = event.getData().toString();
//			// System.out.println(nodeName);
//			if (kDTreeWeb.getRowCount() < 1)
//				return;
//			DefaultMutableTreeNode myRoot = (DefaultMutableTreeNode) this.kDTreeWeb
//					.getModel().getRoot();
//			Enumeration e = myRoot.breadthFirstEnumeration();
//			DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
//			String sSelNode;
//			while (e.hasMoreElements()) {
//				node = (DefaultMutableTreeNode) e.nextElement();
//				sSelNode = node.toString();
//				if (sSelNode.equals(nodeName)) {
//					kDTreeWeb.setSelectionPath(new TreePath(node.getPath()));
//					kDTreeWeb.scrollPathToVisible(new TreePath(node.getPath()));
//					isGet = true;
//					txtMsg.setText("Ԫ����Tree����ѡ��");
//					break;
//				}
//			}
//			if (isGet == false
//					&& kDTreeWeb.getRowCount() > 1
//					&& kDTreeWeb.getModel().getChildCount(
//							kDTreeWeb.getModel().getRoot()) > 0) {
//				txtMsg.setText("��ʾ��Ϣ��û���ҵ���ҳԪ�أ�");
//				kDTreeWeb.setSelectionRow(-1);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			e = null;
//		}
//	}
//
//	/**
//	 * ������ҳ
//	 * ����ĵ�ַ�����ǿͻ����ļ��ĵ�ַ ���磺c:/test.txt ��ת��Ϊ file:///C:/txt.txt
//	 * ���������ַ������ ftp.*.* ��ת��Ϊ ftp://ftp.*.* 
//	 * gopher.*.* ��ת��Ϊ gopher://gopher.*.*
//	 * http.*.* ��ת��Ϊ http://http.*.*
//	 */
//	void loadURL() {
//		this.webBrowser.setURL();
//		this.kDTreeWeb
//				.removeAllChildrenFromParent((MutableTreeNode) this.kDTreeWeb
//						.getModel().getRoot());
//		String sInputUrl = txtURL.getText();
//		if (sInputUrl == null) {
//			FDCMsgBox.showInfo("���ص�ַΪ��!");
//			return;
//		} else {
//			URL curUrl = null;
//			try {
//				// ����Ƿ��Ǳ����ļ�
//				File[] roots = File.listRoots();
//
//				for (int i = 0; i < roots.length; i++) {
//					if (sInputUrl.toLowerCase().startsWith(
//							roots[i].toString().toLowerCase())) {
//						File curLocalFile = new File(sInputUrl);
//
//						curUrl = curLocalFile.toURL();
//						break;
//					}
//				}
//
//				if (curUrl == null) {
//					// Check if the text value is a valid URL.
//					try {
//						curUrl = new URL(sInputUrl);
//					} catch (MalformedURLException e) {
//						if (sInputUrl.toLowerCase().startsWith("ftp.")) {
//							curUrl = new URL("ftp://" + sInputUrl);
//						} else if (sInputUrl.toLowerCase()
//								.startsWith("gopher.")) {
//							curUrl = new URL("gopher://" + sInputUrl);
//						} else if (sInputUrl.toLowerCase().startsWith("http.")) {
//							curUrl = new URL("http://" + sInputUrl);
//						} else {
//							curUrl = new URL(sInputUrl);
//						}
//					}
//				}
//				webBrowser.setURL(curUrl);
//				updateStatusInfo("Loading " + curUrl.toString() + " ......");
//
//			} catch (MalformedURLException mue) {
//				FDCMsgBox.showWarning("û���ҵ�·��:" + sInputUrl);
//			} catch (Exception e) {
//				MsgBox
//						.showError(com.kingdee.eas.util.client.EASResource
//								.getString(
//										"com.kingdee.eas.fdc.sellhouse.client.AbstractWebMarkFieldEditUI:"
//												+ e.getMessage().toString(),
//										"loadURL"));
//			}
//
//		}
//	}
//
//	/**
//	 * �õ���ҳԪ��
//	 */
//	private Node[] getWebNodes() {
//		Node[] nodes = null;
//		NodeClassFilter tag = null;
//		String sFilter = "";
//		try {
//			StringBuffer inputHtml = new StringBuffer();
//			System.out.println("1122");
//			inputHtml.append(webBrowser.getContent().toString());
//			System.out.println("getContent");
//			if (inputHtml == null)
//				return null;
//			if (inputHtml.toString().trim().equals(""))
//				return null;
//			Parser parser = Parser.createParser(inputHtml.toString(), "GBK");
//			// ����һ��
//			OrFilter lastFilter = new OrFilter();
//			int lSel = this.cmbMetaType.getSelectedIndex();
//			switch (lSel) {
//			default:
//				System.out.println("default");
//				lastFilter.setPredicates(new NodeFilter[] {
//						new NodeClassFilter(InputTag.class),
//						new NodeClassFilter(SelectTag.class) });
//				break;
//			case 1:
//				System.out.println("input");
//				sFilter = WebMetaTypeEnum.INPUT_VALUE;
//				lastFilter
//						.setPredicates(new NodeFilter[] { new NodeClassFilter(
//								InputTag.class) });
//				break;
//			case 2:
//				System.out.println("select");
//				sFilter = WebMetaTypeEnum.SELECT_VALUE;
//				lastFilter
//						.setPredicates(new NodeFilter[] { new NodeClassFilter(
//								SelectTag.class) });
//				break;
//			}
//			NodeList nodelist;
//			nodelist = parser.parse(lastFilter);
//			nodes = nodelist.toNodeArray();
//
//			// System.out.println("nodes.length=" + nodes.length);
//			// ��������
//			// NodeList nodelist = parser
//			// .extractAllNodesThatMatch(new TagNameFilter(sFilter));
//			// System.out.println(" nodes size 1=== " + nodelist.size());
//			// for (int i = 0; i < nodelist.size(); i++) {
//			// treeMap = new HashMap();
//			// // LinkTag link=(LinkTag) nodelist.elementAt(i);
//			// InputTag input = (InputTag) nodelist.elementAt(i);
//			// System.out.println(input.getAttribute("name") + "\n");
//			// treeMap.put("name", input.getAttribute("name"));
//			// }
//			// ��������
//			// HtmlPage page = new HtmlPage(parser);
//			// parser.visitAllNodesWith(page);
//			// NodeList nodelist = page.getBody();
//			// System.out.println(" nodes size 1=== " + nodelist.size());
//
//		} catch (ParserException e) {
//			// TODO �Զ����� catch ��
//			e.printStackTrace();
//		} finally {
//			return nodes;
//		}
//	}
//
//	/**
//	 * �������еĽڵ�
//	 */
//	private void extractText(String sRoot) {
//		try {
//			// System.out.println("11111");
//			Node[] nodes = getWebNodes();
//			if (nodes == null)
//				return;
//			DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode(sRoot);
//			((DefaultTreeModel) this.kDTreeWeb.getModel()).setRoot(root);
//			// this.kDTreeWeb.setNodeText(root,root.toString());
//			DefaultKingdeeTreeNode child = null;
//			HashMap treeMap = null;
//			TagNode tagNode = null;
//			for (int i = 0; i < nodes.length; i++) {
//				treeMap = new HashMap();
//				Node node = nodes[i];
//				if (node instanceof InputTag) {
//					tagNode = (InputTag) node;
//					treeMap.put("type", WebMetaTypeEnum.input);
//				} else if (node instanceof SelectTag) {
//					tagNode = (SelectTag) node;
//					treeMap.put("type", WebMetaTypeEnum.select);
//					// Vector v = tagNode.getAttributesEx();
//					Vector vector = new Vector();
//					NodeList nlist = node.getChildren();
//					Node[] nodeArry = nlist.toNodeArray();
//					for (int j = 0; j < nodeArry.length; j++) {
//						Node optnode = (Node) nodeArry[j];
//						if (optnode instanceof OptionTag) {
//							OptionTag opttag = (OptionTag) optnode;
//							// Vector vv = opttag.getAttributesEx();
//							vector.add(opttag.getOptionText());
//							// if (vv.toString().indexOf("selected") != -1)
//							// select_value = opttag.getOptionText();
//						}
//					}
//					treeMap.put("selectList", vector);
//				} else {
//					treeMap.put("type", "");
//				}
//				treeMap.put("name", tagNode.getAttribute("name"));
//				treeMap.put("id", tagNode.getAttribute("id"));
//				child = new DefaultKingdeeTreeNode(tagNode.getAttribute("name"));
//				child.setUserObject(treeMap);
//				kDTreeWeb.addNodeInto(child, root);
//			}
//			kDTreeWeb.expandAllNodes(true, (DefaultKingdeeTreeNode) kDTreeWeb
//					.getModel().getRoot());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * �õ����ܲ���
//	 */
//	private IRowSet getProcessData(String sFuncID) {
//		if (sFuncID.trim().equals("")) {
//			return null;
//		}
//		StringBuffer SQLProcess = new StringBuffer();
//		// String ssql = arrrayList2String(sqlList);
//		SQLProcess.append("SELECT A.* FROM t_she_WebMarkProcess a");
//		SQLProcess
//				.append(" inner join t_she_WebMarkFunction b on a.FParentID=b.FID ");
//		SQLProcess.append(" WHERE 1 = 1 AND A.FParentID= '"
//				+ sFuncID.toString() + "'");
//		try {
//			IRowSet rs = SQLExecutorFactory.getRemoteInstance(
//					SQLProcess.toString()).executeSQL();
//			return rs;
//		} catch (BOSException e) {
//			// TODO �Զ����� catch ��
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	void updateStatusInfo(String statusMessage) {
//
//	}
//
//	private void closeBrowser() {
//		try {
//			webBrowser.stop();
//			webBrowser.setVisible(false);
//			// webBrowser.dispose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		super.disposeUIWindow();
//	}
//
//	protected void disposeUIWindow() {
//		// ��Ҫ�����ֶζ�Ӧ������ͬ������dataChange״̬
//		if (dataChange) {
//			int rtn = MsgBox.showConfirm3("�������޸�,�Ƿ��ڱ���֮���˳�?");
//			if (rtn == MsgBox.OK) {
//				try {
//					saveField();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				closeBrowser();
//			} else if (rtn == MsgBox.NO) {
//				closeBrowser();
//			} else if (rtn == MsgBox.CANCEL) {
//				return;
//			}
//		} else {
//			closeBrowser();
//		}
//	}
//
//	protected void verifyInput(ActionEvent e) throws Exception {
//		super.verifyInput(e);
//	}
//
//	protected IObjectValue createNewData() {
//		// TODO �Զ����ɷ������
//		// WebMarkFieldInfo fieldInfo=new WebMarkFieldInfo();
//		// fieldInfo.setIsEnabled(isEnabled);
//		return null;
//	}
//
//	protected ICoreBase getBizInterface() throws Exception {
//		// TODO �Զ����ɷ������
//		return WebMarkFieldFactory.getRemoteInstance();
//	}
//
//	private void setEntrys() {
//		// �������
//		tblEntrys.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
//		tblEntrys.removeHeadRows();
//		tblEntrys.removeRows();
//		tblEntrys.addHeadRow();
//		tblEntrys.checkParsed();
//		tblEntrys.getStyleAttributes().setLocked(false);
//
//		addTxtFieldColumn("FName", "��������", 80, false);
//		addTxtFieldColumn("FWebID", "ID", 80, false);
//		addTxtFieldColumn("FWebName", "name", 80, false);
//		addCombColumn("FWebMetaType", "Ԫ������", null,
//				"com.kingdee.eas.fdc.sellhouse.WebMetaTypeEnum", false);
//		addTxtFieldColumn("FWebValue", "ʵ��ֵ", 80, false);
//
//		KDComboBox combobox = setFieldComb(new KDComboBox());
//		addCombColumn("FFileName", "ϵͳ����", combobox, "", false);
//
//		addTxtFieldColumn("FDefultValue", "��Ӧ����", 80, false);
//		addTxtFieldColumn("FID", "webID", 80, true);
//	}
//
//	/*
//	 * ����һ��textField��ʽ��Column ; ֻ��������һ�б����� ����ʽ��������;
//	 */
//	private void addTxtFieldColumn(String sKey, String sTitle,
//			int txtMaxLenght, boolean isHide) {
//		// ����������
//		IRow headRow = tblEntrys.getHeadRow(0);
//		// ����һ��
//		IColumn headColumn = tblEntrys.addColumn();
//		headColumn.setKey(sKey);
//		headColumn.getStyleAttributes().setHided(isHide);
//		ICell headCell = headRow.getCell(sKey);
//		headCell.setValue(sTitle);
//		KDTextField textField = new KDTextField();
//		textField.setMaxLength(txtMaxLenght);
//		ICellEditor cellEditor = new KDTDefaultCellEditor(textField);
//		headColumn.setEditor(cellEditor);
//	}
//
//	/*
//	 * ����һ��Combobox��ʽ��Column; ֻ��������һ�б����� ����ʽ��������;
//	 */
//	private void addCombColumn(String sKey, String sTitle, KDComboBox comb,
//			String sEnumClass, boolean isHide) {
//		// ����������
//		IRow headRow = tblEntrys.getHeadRow(0);
//		// ����һ��
//		IColumn headColumn = tblEntrys.addColumn();
//		headColumn.getStyleAttributes().setHided(isHide);
//		headColumn.setKey(sKey);
//		ICell headCell = headRow.getCell(sKey);
//		headCell.setValue(sTitle);
//		KDComboBox combobox = new KDComboBox();
//		if (comb != null) {
//			combobox = comb;
//		} else if (!sEnumClass.trim().equals("")) {
//			combobox = new KDComboBox();
//			combobox.addItems(EnumUtils.getEnumList(
//					toString(sEnumClass, "").trim()).toArray());
//		}
//		combobox.setLightWeightPopupEnabled(false);
//		ICellEditor cellEditor = new KDTDefaultCellEditor(combobox);
//		headColumn.setEditor(cellEditor);
//	}
//
//	/*
//	 * xwbȡ�ֶ���Ϣ��ʵ����COMBOBOX
//	 */
//	private KDComboBox setFieldComb(KDComboBox combobox) {
//		StringBuffer SQLProcess = new StringBuffer();
//		SQLProcess
//				.append("select FID,FName,FField,FFromTable,FFilter,FEntry from t_she_webmarkfieldRelation order by FModule,FID");
//		// if (!sModule.trim().equals(""))
//		// SQLProcess.append(" where FModule=" + sModule);
//		try {
//			IRowSet rs = SQLExecutorFactory.getRemoteInstance(
//					SQLProcess.toString()).executeSQL();
//			fdcFieldMap = new HashMap();
//			// ����none�ֶΣ���ʾʹ��ʵ��Ĭ��ֵ
//			combobox.addItem("none");
//			fdcFieldMap.put("none", "none");
//			while (rs.next()) {
//				fdcFieldMap.put(rs.getString("FName").trim(), rs
//						.getString("FID"));
//				combobox.addItem(rs.getString("FName").trim());
//			}
//		} catch (BOSException e) {
//			// TODO �Զ����� catch ��
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO �Զ����� catch ��
//			e.printStackTrace();
//		} finally {
//			return combobox;
//		}
//	}
//}
