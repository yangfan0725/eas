/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.ParseException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import org.apache.log4j.Logger;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JHTMLEditor;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.invite.InviteGetUserPortalFacadeFactory;
import com.kingdee.eas.fdc.invite.InviteProjectException;
import com.kingdee.eas.fdc.invite.TemplateFileCategoryCollection;
import com.kingdee.eas.fdc.invite.TemplateFileCategoryFactory;
import com.kingdee.eas.fdc.invite.TemplateFileCategoryInfo;
import com.kingdee.eas.fdc.invite.TemplateFileFactory;
import com.kingdee.eas.fdc.invite.TemplateFileInfo;
import com.kingdee.eas.fdc.invite.TemplateTypeInfo;
import com.kingdee.eas.fdc.invite.client.TreeUtil.ISearchTreeEvent;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * �б��ļ�ģ�� �༭����
 */
public class TemplateFileEditUI extends AbstractTemplateFileEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(TemplateFileEditUI.class);

	private MyWebBrowser htmlEditor = null;

	/**
	 * output class constructor
	 */
	public TemplateFileEditUI() throws Exception
	{
		super();
	}

	public boolean isModify() {
		// TODO Auto-generated method stub
		if (STATUS_VIEW.equals(getOprtState())) {
			return false;
		}
		if(this.editData==null)//||oldData==null)
        {
            return false;
        }
        try
        {
            com.kingdee.bos.ctrl.common.util.ControlUtilities.checkFocusAndCommit();
        }
        catch (ParseException e)
        {
			handleControlException();
			// ��������Ҫ֪���Ƿ������쳣
			//wfContext.setThrowException(true);

			abort();
        }

        //�鿴״̬���ж��Ƿ��޸�
        if (OprtState.VIEW.equals(getOprtState())) {
            return false;
        }

        try {
            storeFields();
            this.editData.setSyncOldValue(false);
        } catch (Exception exc) {
            return false;
        }
        AbstractObjectValue objectValue = (AbstractObjectValue) editData;
        return objectValue.isValueChange();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
	
		editData.getFileCategory().clear();
		editData.getFileCategory().addCollection(getTreeNodesCategory(this.treeCategory));
		super.storeFields();

		
		
//		oldEditData = editData;
		
//		initOldData(editData);
	}

	public void onLoad() throws Exception {
		super.onLoad();
//		categoryCols.clear();

		initCategoryTree();

		showEditor(null);
		initHeadStyle();

		treeCategory.setSelectionRow(0);
		this.actionAuditResult.setVisible(false);

		this.actionAudit.setVisible(false);
		this.actionAbout.setEnabled(false);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);

		this.actionWorkflowList.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionSendingMessage.setVisible(false);
		this.actionSendMail.setVisible(false);
		this.actionSendMessage.setVisible(false);

		this.actionMsgFormat.setVisible(false);
		this.actionDelVoucher.setVisible(false);
		this.actionViewDoProccess.setVisible(false);

		this.MenuItemWFG.setVisible(false);
		this.menuItemEnterToNextRow.setVisible(false);

		Component[] components = menuWorkflow.getPopupMenu().getComponents();

		for(int i=components.length-1; i>=0; --i){
			menuWorkflow.remove(i);
		}
		
		if(getOprtState().equals(OprtState.VIEW))
		{
			if(getUIContext().get("CAN_ADD") != null)
			{
				Boolean canAdd = (Boolean)(getUIContext().get("CAN_ADD") );
				if(canAdd.booleanValue())
				{
					this.actionAddNew.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(false);
					this.actionSave.setEnabled(false);
				}
				else
				{
					this.actionAddNew.setEnabled(false);
					this.actionEdit.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(false);
					this.actionSave.setEnabled(false);
				}
			}
			
			pnlEditor.setEnabled(false);
			
		}
		else if(getOprtState().equals(OprtState.EDIT))
		{
			if(editData.getState().equals(FDCBillStateEnum.SUBMITTED))
			{
				this.actionSave.setEnabled(false);
			}
			else
			{
				this.actionSave.setEnabled(true);
			}
			
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(false);
			this.actionSubmit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			
			pnlEditor.setEnabled(true);
		}
		else if(getOprtState().equals(OprtState.ADDNEW))
		{
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(false);
			this.actionSubmit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionSave.setEnabled(true);
			
			pnlEditor.setEnabled(true);
		}
		else
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionSubmit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionSave.setEnabled(false);
			
			pnlEditor.setEnabled(false);
		}
		
		this.btnAttachment.setVisible(true);
		this.btnAttachment.setEnabled(true);
		this.actionAttachment.setVisible(true);
		this.actionAttachment.setEnabled(true);
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return TemplateFileFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() 
	{
		TemplateFileInfo info = new TemplateFileInfo();

		BOSUuid id = BOSUuid.create(info.getBOSType());
		info.setId(id);

		info.setOrgUnit((FullOrgUnitInfo)SysContext.getSysContext().getCurrentOrgUnit());
		info.setState(FDCBillStateEnum.SAVED);

		info.setCreator((UserInfo)SysContext.getSysContext().getCurrentUserInfo());

		if(getUIContext().get("TEMPLATE_TYPE") != null)
		{
			TemplateTypeInfo typeInfo = (TemplateTypeInfo)(getUIContext().get("TEMPLATE_TYPE"));
			info.setTemplateType(typeInfo);
		}
		
		return info ;
	}

	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		this.treeCategory.removeAllChildrenFromParent((MutableTreeNode)this.treeCategory.getModel().getRoot());
		this.actionAddCategory.setEnabled(true);
		this.btnAddCategory.setEnabled(true);
		this.actionRemoveCategory.setEnabled(true);
		this.btnRemoveCategory.setEnabled(true);
		this.actionEditCategory.setEnabled(true);
		this.btnEditCategory.setEnabled(true);
		htmlEditor.setEditorContent("");
		this.btnAttachment.setVisible(true);
		this.btnAttachment.setEnabled(true);
		this.actionAttachment.setVisible(true);
		this.actionAttachment.setEnabled(true);
	}

	protected IObjectValue createNewDetailData(KDTable table) 
	{
		return null ;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);

		this.btnAddCategory.setEnabled(true);
		this.btnEditCategory.setEnabled(true);
		this.btnRemoveCategory.setEnabled(true);

		htmlEditor.setEnabled(true);
	
	}

	 
	/**
	 * ��ʼ�������ؼ���״̬
	 * �˴�����һ��TOOLBAR��һ��bug��
	 * ���������ϵİ�ť�������࣬Ȼ��ʹ��remove��addButton��������TOOLBAR�ϣ��޷���ʾ
	 */
	protected void initHeadStyle()
	{
		String templateTypeQueryInfo = "com.kingdee.eas.fdc.invite.app.F7TemplateFileTypeQuery";

		this.prmtTemplateType.setQueryInfo(templateTypeQueryInfo);

		this.prmtTemplateType.setDisplayFormat("$number$ $name$");
		this.prmtTemplateType.setEditFormat("$name$");
		this.prmtTemplateType.setCommitFormat("$name$");

		this.prmtTemplateType.setEnabled(false);
		this.contTemplateType.setEnabled(false);

		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);

		this.contCategory.setEnableActive(false);

		this.contCreator.setEnabled(false);
		this.prmtCreator.setEnabled(false);
		//this.contCreateTime.setEnabled(false);
		this.pkCreateTime.setEnabled(false);

		this.contCategory.setTitle("Ŀ¼");
		this.contCategory.setTitleStyle(1);

		this.actionAddLine.setEnabled(false);
		this.actionAddLine.setVisible(false);

		this.actionInsertLine.setEnabled(false);
		this.actionInsertLine.setVisible(false);

		this.actionRemoveLine.setEnabled(false);
		this.actionRemoveLine.setVisible(false);

		remove(btnAddCategory);
		remove(btnEditCategory);
		remove(btnRemoveCategory);

		contCategory.addButton(btnAddCategory);
		contCategory.addButton(btnEditCategory);
		contCategory.addButton(btnRemoveCategory);

		treeCategory.setRootVisible(true);

		this.kDSplitPane1.setDividerLocation(300);

		if(OprtState.VIEW.equals(getOprtState()))
		{
			this.btnAddCategory.setEnabled(false);
			this.btnEditCategory.setEnabled(false);
			this.btnRemoveCategory.setEnabled(false);

			if(htmlEditor != null)
			{
				htmlEditor.setEnabled(false);
			}
		}
		else
		{
			this.btnAddCategory.setEnabled(true);
			this.btnEditCategory.setEnabled(true);
			this.btnRemoveCategory.setEnabled(true);

			if(htmlEditor != null)
			{
				htmlEditor.setEnabled(true);
			}
		}
	}
	/**
	 * ��ʼ���ļ���Ŀ��
	 * @throws Exception
	 */
	protected void initCategoryTree() throws Exception
	{
		buildCategoryTree();

	}
	private TreeSelectionListener treeSelListener = null ;
	private ITreeBuilder treeBuilder = null ;

	protected ILNTreeNodeCtrl getLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getTreeInterface());
	}
	/**
	 * ���ݸ�ʵ����ȡ�������Ľڵ�
	 * @return
	 */
	private ITreeBase getTreeInterface()
	{
		ITreeBase treebase = null ;
		try {
			treebase = TemplateFileCategoryFactory.getRemoteInstance();
		} catch (BOSException e) {
			abort(e);
		}
		return treebase ;
	}
	/**
	 * ͨ������FLEVELʵ�ֳ�ʼ����ʱҪ��ʾ�Ĳ㼶
	 * @return
	 */
	private int getTreeInitialLevel()
	{
		return  TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
	}
	private int getTreeExpandLevel()
	{
		return  TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
	}
	/**
	 * ���˵��͵�ǰeditData�޹صķ�¼����
	 * �������ӹ����������������еķ�¼Ŀ¼���ݹ������
	 * @return
	 */
	private FilterInfo getDefaultFilterForTree()
	{
		FilterInfo filter = new FilterInfo();
		String id = editData.getId().toString();
		filter.getFilterItems().add(new FilterItemInfo("tempFile.id", id));

		return filter ;
	}
	/**
	 * �����������ļ�Ŀ¼����
	 * @throws Exception
	 */
	private void buildCategoryTree() throws Exception
	{
		TreeSelectionListener[] listeners = treeCategory.getTreeSelectionListeners();
		if(listeners.length > 0)
		{
			treeSelListener = listeners[0];
			treeCategory.removeTreeSelectionListener(treeSelListener);
		}
		treeBuilder = TreeBuilderFactory.createTreeBuilder(getLNTreeNodeCtrl(), 
				getTreeInitialLevel(), getTreeExpandLevel(), this.getDefaultFilterForTree());

		if (getRootName() != null) {
			KDTreeNode rootNode = new KDTreeNode(getRootObject());
			((DefaultTreeModel) treeCategory.getModel()).setRoot(rootNode);

		} else {
			((DefaultTreeModel) treeCategory.getModel()).setRoot(null);
		}

		treeBuilder.buildTree(treeCategory);

		treeCategory.addTreeSelectionListener(treeSelListener);
		treeCategory.setShowPopMenuDefaultItem(true);

		treeCategory.setSelectionRow(0);

	}

	private void setLookAndFeel() 
	{
		try {
			String systemLookAndFeelClassName = UIManager
			.getSystemLookAndFeelClassName();
			if (!"com.kingdee.bos.ctrl.swing.plaf.KingdeeLookAndFeel"
					.equals(systemLookAndFeelClassName)) {
				UIManager
				.setLookAndFeel("com.kingdee.bos.ctrl.swing.plaf.KingdeeLookAndFeel");
			}
		} catch (Exception e) {
			handUIException(e);
		}
	}
	/**
	 * ��ʾ�ı��༭��
	 * ͨ��ʵ��URL���÷���˵������Ѿ�ʵ�ֵĺ���ҳ����Ƕ���ڸý����С�
	 * @param initText
	 */
	private void showEditor(String initText) 
	{
		setLookAndFeel();
		
		if (!NativeInterface.isOpen())
			NativeInterface.open();
		
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				String configurationScript = "FCKConfig.ToolbarSets[\"Default\"] = [ [&apos;Preview&apos;,&apos;-&apos;,&apos;Templates&apos;],"
					+ "[&apos;Cut&apos;,&apos;Copy&apos;,&apos;Paste&apos;,&apos;PasteText&apos;,&apos;PasteWord&apos;,&apos;-&apos;,&apos;SpellCheck&apos;],"
					+ "[&apos;Undo&apos;,&apos;Redo&apos;,&apos;-&apos;,&apos;Find&apos;,&apos;Replace&apos;,&apos;-&apos;,&apos;SelectAll&apos;,&apos;RemoveFormat&apos;],"
					+ "[&apos;TextField&apos;,&apos;Textarea&apos;,&apos;ImageButton&apos;,&apos;HiddenField&apos;],"
					+ "&apos;/&apos;,"
					+ "[&apos;Bold&apos;,&apos;Italic&apos;,&apos;Underline&apos;,&apos;StrikeThrough&apos;,&apos;-&apos;,&apos;Subscript&apos;,&apos;Superscript&apos;],"
					+ "[&apos;OrderedList&apos;,&apos;UnorderedList&apos;,&apos;-&apos;,&apos;Outdent&apos;,&apos;Indent&apos;,&apos;Blockquote&apos;,&apos;CreateDiv&apos;],"
					+ "[&apos;JustifyLeft&apos;,&apos;JustifyCenter&apos;,&apos;JustifyRight&apos;,&apos;JustifyFull&apos;],"
					+ "[&apos;Link&apos;,&apos;Unlink&apos;,&apos;Anchor&apos;],"
					+ "[&apos;Image&apos;,&apos;Flash&apos;,&apos;Table&apos;,&apos;Rule&apos;,&apos;Smiley&apos;,&apos;SpecialChar&apos;,&apos;PageBreak&apos;],"
					+ "&apos;/&apos;,"
					+ "[&apos;Style&apos;,&apos;FontFormat&apos;,&apos;FontName&apos;,&apos;FontSize&apos;],"
					+ "[&apos;TextColor&apos;,&apos;BGColor&apos;],"
					+ "[&apos;FitWindow&apos;,&apos;ShowBlocks&apos;,&apos;-&apos;,&apos;About&apos;]"
					+ "] ;\n" + "FCKConfig.ToolbarCanCollapse = false;\n";
				
				String url = "http://127.0.0.1:6888/easportal/fckeditor/index.html";
				try {
					url = InviteGetUserPortalFacadeFactory.getRemoteInstance().getUserPortal(System.getenv("UPDATE_SERVER"));
				} catch (InviteProjectException e) {
					handUIExceptionAndAbort(e);
				} catch (BOSException e) {
					e.printStackTrace();
				}
				if(url.equalsIgnoreCase("http://null/easportal/fckeditor/index.html")){
					url = "http://127.0.0.1:6888/easportal/fckeditor/index.html";
				}
				htmlEditor = new MyWebBrowser(url, true, JHTMLEditor.FCKEditorOptions.setCustomJavascriptConfiguration(configurationScript));
				logger.info("UPDATE_SERVER:"+ System.getenv("UPDATE_SERVER"));
				kDSplitPane1.add(htmlEditor, "right");			
			}
		});
		
		if (!InviteFileItemEditUI.invoked) {
			NativeInterface.runEventPump();
			InviteFileItemEditUI.invoked = true;
		}		
	}
	
	/**
	 * ʵ������ļ�Ŀ¼����
	 * �Ƚ����ϼ�Ŀ¼ͨ��uiContext���ݵ��༭���棬Ȼ���ٽ���༭�����ݴ������������µĽڵ㡣
	 * �����µĽڵ��ʱ���ر�ע��Ҫ�ֶ�ʵ�ַ�¼��FLEVEL�����û���ֶ�ά�����޷��������
	 */
	public void actionAddCategory_actionPerformed(ActionEvent e)
	throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);

		TemplateFileCategoryInfo parentInfo = null ;

		BigDecimal children = new BigDecimal("0");
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeCategory.getLastSelectedPathComponent();

		if(node == null)
		{
			FDCMsgBox.showWarning(this,"ѡ����ڵ㲻������");
			abort();
		}
		if(node != null && node.getUserObject() != null && !OrgViewUtils.isTreeNodeDisable(node))
		{
			if(node.getUserObject() instanceof TemplateFileCategoryInfo)
			{
				parentInfo = (TemplateFileCategoryInfo)(node.getUserObject());
			}
		}
		if(node != null)
		{
			children = children.add(new BigDecimal(node.getChildCount()));
		}

		uiContext.put("PARENT_CATEGORY", parentInfo);
		uiContext.put("IS_EDIT", Boolean.valueOf(false));
		
		uiContext.put("CUR_CATEGORY_SET", getTreeNodesCategory(this.treeCategory));

		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL); 
		IUIWindow uiWindow = uiFactory.create(FileCategoryEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);

		uiWindow.show();

		Boolean isEdit = (Boolean)(uiWindow.getUIObject().getUIContext().get("IS_EDIT"));

		if(!isEdit.booleanValue() && (uiWindow.getUIObject().getUIContext().get("CATEGORY") != null))
		{
			String category = uiWindow.getUIObject().getUIContext().get("CATEGORY").toString();

			TemplateFileCategoryInfo  categoryInfo = new TemplateFileCategoryInfo();

			BOSUuid categoryId = BOSUuid.create(categoryInfo.getBOSType());
			categoryInfo.setId(categoryId);

			categoryInfo.setParent(parentInfo);

			categoryInfo.setName(category);

			if(parentInfo == null)
			{
				categoryInfo.setLevel(0);
			}
			else
			{
				categoryInfo.setLevel(parentInfo.getLevel()+1);
			}

			//����һ���ڵ�
			//����ά��
			BigDecimal number = new BigDecimal("1");
			number = number.add(children);

			String strNumber = number.toString();
			switch(strNumber.length())
			{
			case 1:
				strNumber = "000" + strNumber;
				break ;
			case 2:
				strNumber = "00" + strNumber ;
				break ;
			case 3:
				strNumber = "0" + strNumber ;
				break ;
			case 4:
				break ;
			}
			categoryInfo.setNumber(strNumber);

			//���������ڵ�ĳ�����
			if(parentInfo != null)
			{
				String strLongNumber = parentInfo.getNumber() + "!" + strNumber;
				categoryInfo.setLongNumber(strLongNumber);
			}
			else
			{
				categoryInfo.setLongNumber(strNumber);
			}
			KDTreeNode categoryNode = new KDTreeNode(categoryInfo);
			categoryNode.setUserObject(categoryInfo);
			categoryNode.setText(category);

			treeCategory.addNodeInto(categoryNode, node);
//			categoryCols.add(categoryInfo);
		}
	}
	/**
	 * ɾ����ѡ���Ŀ¼��
	 * ����ϸ�ڵ㲻��ֱ��ɾ��
	 */
	public void actionRemoveCategory_actionPerformed(ActionEvent e)
	throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeCategory
		.getLastSelectedPathComponent();

		if(node == null)
		{
			MsgBox.showWarning("��ѡ��ڵ�");
			SysUtil.abort();
		}
		if(node.isRoot())
		{
			MsgBox.showWarning("��Ŀ¼����ɾ��");
			SysUtil.abort();
		}
		if(node.isLeaf())
		{
			((KingdeeTreeModel) treeCategory.getModel()).removeNodeFromParent(node);

			if(node != null && node.getUserObject() != null && !OrgViewUtils.isTreeNodeDisable(node))
			{
				TemplateFileCategoryInfo categoryInfo = (TemplateFileCategoryInfo)(node.getUserObject());
//				categoryCols.remove(categoryInfo);

				this.htmlEditor.setEditorContent("");
			}
		}
		else
		{
			MsgBox.showWarning("����ϸĿ¼����ֱ��ɾ��");
			SysUtil.abort();
		}

	}
	/**
	 * �༭��ѡ���ļ�Ŀ¼
	 */
	public void actionEditCategory_actionPerformed(ActionEvent e)
	throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeCategory
		.getLastSelectedPathComponent();

		if(node.isRoot())
		{
			MsgBox.showWarning("��Ŀ¼���ܱ༭");
			SysUtil.abort();
		}
		else
		{
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);

			TemplateFileCategoryInfo parentInfo = null ;

			if(node != null && node.getUserObject() != null && !OrgViewUtils.isTreeNodeDisable(node))
			{
				if(((DefaultKingdeeTreeNode)(node.getParent())).getUserObject() instanceof TemplateFileCategoryInfo)
				{
					parentInfo = (TemplateFileCategoryInfo)((DefaultKingdeeTreeNode)(node.getParent())).getUserObject();
				}
			}

			uiContext.put("PARENT_CATEGORY", parentInfo);
			uiContext.put("IS_EDIT", Boolean.valueOf(true));

			String strCategory = node.getText();
			uiContext.put("CATEGORY", strCategory);
			
			uiContext.put("CUR_CATEGORY_SET", getTreeNodesCategory(this.treeCategory));

			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL); 
			IUIWindow uiWindow = uiFactory.create(FileCategoryEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();

			Boolean isEdit = (Boolean)(uiWindow.getUIObject().getUIContext().get("IS_EDIT"));

			if(isEdit.booleanValue()&& (uiWindow.getUIObject().getUIContext().get("CATEGORY") != null))
			{
				String category = uiWindow.getUIObject().getUIContext().get("CATEGORY").toString();

				TemplateFileCategoryInfo categoryInfo = (TemplateFileCategoryInfo)(node.getUserObject());
				TemplateFileCategoryInfo tmpCategoryInfo = new TemplateFileCategoryInfo();

				tmpCategoryInfo.setId(categoryInfo.getId());
				tmpCategoryInfo.setParent(categoryInfo.getParent());
				tmpCategoryInfo.setContext(categoryInfo.getContext());

				tmpCategoryInfo.setName(category);
				tmpCategoryInfo.setLevel(categoryInfo.getLevel());

				tmpCategoryInfo.setNumber(categoryInfo.getNumber());
				tmpCategoryInfo.setLongNumber(categoryInfo.getLongNumber());
//
//				categoryCols.remove(categoryInfo);
//				categoryCols.add(tmpCategoryInfo);

				//����Ŀ¼�е�����
				node.setUserObject(categoryInfo);
				node.setText(category);
			}
		}
	}
	private void loadNewNodeContent(TreeSelectionEvent e)
	{
		if (e.getNewLeadSelectionPath() != null
				&& e.getNewLeadSelectionPath().getLastPathComponent() instanceof KDTreeNode) {
			Object newData = ((KDTreeNode) e.getNewLeadSelectionPath()
					.getLastPathComponent()).getUserObject();
			if (newData != null && newData instanceof TemplateFileCategoryInfo) {
				loadContent((TemplateFileCategoryInfo) newData);
			} else {
				if(htmlEditor != null)
				{
					htmlEditor.setEditorContent("");
				}
			}
		}
	}
	private void storeOldNodeContent(TreeSelectionEvent e)
	{
		if (e.getOldLeadSelectionPath() != null
				&& e.getOldLeadSelectionPath().getLastPathComponent() instanceof KDTreeNode) {
			Object oldData = ((KDTreeNode) e.getOldLeadSelectionPath()
					.getLastPathComponent()).getUserObject();
			if (oldData != null && oldData instanceof TemplateFileCategoryInfo) {
				storeContent((TemplateFileCategoryInfo) oldData);
			}
		}
	}
	/**
	 * �����ı��༭���е�����
	 * ��ѡ������һ��Ŀ¼ʱ�ᱣ���˸��ı��༭���е�����
	 * ����ѡ����ɸ�Ŀ¼�л���������Ŀ¼�ǲ����б������
	 */
	protected void treeCategory_valueChanged(TreeSelectionEvent e)throws Exception {
		if(getOprtState().equals(OprtState.VIEW))
		{
			loadNewNodeContent(e);
		}
		else if(getOprtState().equals(OprtState.EDIT))
		{
			storeOldNodeContent(e);
			loadNewNodeContent(e);
		
		}
		else if(getOprtState().equals(OprtState.ADDNEW))
		{
			storeOldNodeContent(e);
			loadNewNodeContent(e);
			
		}
		else
		{
			loadNewNodeContent(e);
		}
	}

	private String getRootName()
	{
		String rootName = "Ŀ¼";
		return rootName;
	}
	private Object getRootObject()
	{
		return getRootName();
	}
	/**
	 * ����Ŀ¼��Ӧ�ĺ�ͬ����
	 * ����������ɾ����Ŀ¼���������ݺ����������
	 * @param categoryInfo
	 */
	private void storeContent(TemplateFileCategoryInfo categoryInfo) {

		if (categoryInfo != null && htmlEditor != null)
		{
//			categoryCols.remove(categoryInfo);
//			categoryInfo.setContext(htmlEditor.getEditorContent());
//			categoryCols.add(categoryInfo);
			
			categoryInfo.setContext(htmlEditor.getEditorContent());
		}
	}
	private void loadContent(TemplateFileCategoryInfo c) {
		if (c != null && htmlEditor != null)
		{
			if(c.getContext() != null)
			{
				htmlEditor.setEditorContent(c.getContext());
			}else{
				htmlEditor.setEditorContent("");
			}
		}
		else{
			htmlEditor.setEditorContent("");
		}
//			htmlEditor.setEditorContent(c.getContext() == null ? null : c
//					.getContext());
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);


		if(editData.getNumber()==null||editData.getNumber().trim().length()==0){
			FDCMsgBox.showWarning(this,"������벻��Ϊ��");
			abort();
		}

		if(editData.getName()==null||editData.getName().trim().length()==0){
			FDCMsgBox.showWarning(this,"�������Ʋ���Ϊ��");
			abort();
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();

		this.actionCopy.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionPrintPreview.setEnabled(false);
		this.actionPrintPreview.setVisible(false);

		this.actionPrint.setEnabled(false);
		this.actionPrint.setVisible(false);
		this.actionPre.setEnabled(false);
		this.actionPre.setVisible(false);

		this.actionFirst.setEnabled(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setEnabled(false);
		this.actionNext.setVisible(false);

		this.actionLast.setVisible(false);
		this.actionLast.setEnabled(false);

		this.actionCancel.setEnabled(false);
		this.actionCancel.setVisible(false);

		this.actionCancelCancel.setEnabled(false);
		this.actionCancelCancel.setVisible(false);

		this.actionTraceDown.setEnabled(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setEnabled(false);
		this.actionTraceUp.setVisible(false);

		this.actionNextPerson.setEnabled(false);
		this.actionNextPerson.setVisible(false);
		this.actionCalculator.setEnabled(false);
		this.actionCalculator.setVisible(false);

		this.actionMultiapprove.setEnabled(false);
		this.actionMultiapprove.setVisible(false);
		this.actionCreateFrom.setEnabled(false);
		this.actionCreateFrom.setVisible(false);

		this.actionAddLine.setEnabled(false);
		this.actionAddLine.setVisible(false);

		this.actionInsertLine.setEnabled(false);
		this.actionInsertLine.setVisible(false);

		this.actionRemoveLine.setEnabled(false);
		this.actionRemoveLine.setVisible(false);

		this.actionCopyLine.setVisible(false);
		this.actionSubmitOption.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyFrom.setVisible(false);

		this.actionWorkFlowG.setVisible(false);
		this.actionAuditResult.setVisible(false);
		
		this.btnAttachment.setVisible(true);
		this.btnAttachment.setEnabled(true);
		this.actionAttachment.setVisible(true);
		this.actionAttachment.setEnabled(true);
	}

	protected boolean isShowAttachmentAction() {
		return false;
	}

	protected void verifyInputForSave() throws Exception 
	{
		super.verifyInputForSave();

		if(editData.getNumber() == null || editData.getNumber().trim().length() == 0)
		{
			FDCMsgBox.showWarning(this,"ģ���ļ����벻��Ϊ��");
			abort();
		}

		if(editData.getNumber() != null && editData.getNumber().trim().length() > 80)
		{
			FDCMsgBox.showWarning(this,"ģ���ļ����볤�Ȳ��ܳ���80�ַ�");
			abort();
		}
	}

	protected void verifyInputForSubmint() throws Exception 
	{
		super.verifyInputForSubmint();

		if(editData.getNumber() == null|| editData.getNumber().trim().length() == 0)
		{
			FDCMsgBox.showWarning(this,"ģ���ļ����벻��Ϊ��");
			abort();
		}

		if(editData.getName() == null|| editData.getName().trim().length() == 0)
		{
			FDCMsgBox.showWarning(this,"ģ���ļ����Ʋ���Ϊ��");
			abort();
		}

		if(editData.getNumber() != null && editData.getNumber().trim().length() > 80)
		{
			FDCMsgBox.showWarning(this,"ģ���ļ����볤�Ȳ��ܳ���80�ַ�");
			abort();
		}

		if(editData.getName() != null && editData.getName().trim().length() > 80)
		{
			FDCMsgBox.showWarning(this,"ģ���ļ����Ƴ��Ȳ��ܳ���80�ַ�");
			abort();
		}
	}

	protected void doBeforeSave(ActionEvent e) throws Exception 
	{
		TemplateFileCategoryInfo c = getSelectedNodeValue();
		if (c != null)
			storeContent(c);
		super.doBeforeSave(e);
	}

	protected void doBeforeSubmit(ActionEvent e) throws Exception 
	{
		TemplateFileCategoryInfo c = getSelectedNodeValue();
		if (c != null)
			storeContent(c);

		initOldData(editData);
		
		super.doBeforeSubmit(e);
	}

	protected TemplateFileCategoryInfo getSelectedNodeValue() {
		KDTreeNode treeNode = getSelectedTreeNode();
		if (treeNode != null
				&& treeNode.getUserObject() instanceof TemplateFileCategoryInfo) {
			return (TemplateFileCategoryInfo) treeNode.getUserObject();
		} else
			return null;
	}

	public KDTreeNode getSelectedTreeNode() {
		Object object = treeCategory.getLastSelectedPathComponent();
		if (object != null)
			return (KDTreeNode) object;
		else
			return null;
	}

	private TemplateFileCategoryCollection categoryCols = new TemplateFileCategoryCollection();
	protected TemplateFileCategoryCollection getTreeNodesCategory(KDTree storeTree)
	{
		
		//�����������һ��ѡ�нڵ�����ݣ���ֹ�����Ľڵ������޸�
		if (OprtState.EDIT.equals(getOprtState())
				|| OprtState.ADDNEW.equals(getOprtState())) {

			DefaultKingdeeTreeNode selectedNode = getSelectedTreeNode(storeTree);
			if (selectedNode != null) {
				Object obj = selectedNode.getUserObject();
				if (obj != null && obj instanceof TemplateFileCategoryInfo) {
					storeContent((TemplateFileCategoryInfo)obj);
				}
			}
		}


		//�������нڵ㲢�ұ������
		categoryCols.clear();
		if(storeTree.getModel().getRoot() != null && storeTree.getModel().getRoot() instanceof KDTreeNode)
		{
			TreeUtil.searchTree((KDTreeNode)storeTree.getModel().getRoot(), new ISearchTreeEvent(){
				public boolean doTask(Object params,
						DefaultKingdeeTreeNode node) {
					Object obj = node.getUserObject();
					if (obj != null && obj instanceof TemplateFileCategoryInfo) {
						categoryCols.addObject((TemplateFileCategoryInfo) obj);
					}
					return true;
				}
			});
		}
	
		return categoryCols;
	}

	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree)
	{
		if(selectTree.getLastSelectedPathComponent() != null)
		{
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)selectTree.getLastSelectedPathComponent();
			return treeNode;
		}
		return null ;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("auditor.*"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("templateType.*"));
		
		sic.add(new SelectorItemInfo("state"));
		return sic;
	}
	
	/**
	 * �ڵ�һ��ִ�б����ʱ�򣬻����Thread����
	 * ���ڱ����ʱ���̨�и�����߳�û�н������������������
	 */
	protected void handleOldData() {
		if(!(getOprtState()==STATUS_FINDVIEW||getOprtState()==STATUS_VIEW)){
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
				public void run() {
					dataBinder.storeFields();
					initOldData(editData);
				}
			});
		}
	}

}