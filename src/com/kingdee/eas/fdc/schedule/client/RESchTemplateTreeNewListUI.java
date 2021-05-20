/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.tree.TreeNode;
import org.apache.log4j.Logger;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.RESchTemplateFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;

/**
 * 
 * @(#)						
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		�ƻ�ģ���б����
 *		
 * @author		��С��������ΰ
 * @version		EAS7.0		
 * @createDate	2011-08-311
 * @see
 */
public class RESchTemplateTreeNewListUI extends AbstractRESchTemplateTreeNewListUI
{
	
    /** ȱ�ٰ汾�� */
	private static final long serialVersionUID = 1L;
	
	/** ��־�ļ� */
	private static final Logger logger = CoreUIObject.getLogger(RESchTemplateTreeNewListUI.class);
	
	/** ���������� */
    protected ITreeBuilder treeBuilder;
    
    /**
     * output class constructor
     */
    public RESchTemplateTreeNewListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	initTree();
    	initDisplayStyle();
    	
    	/* �ӵ���¼� */
    	this.btnOk.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				confrim();
			}
			
			public void mouseEntered(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}

			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}
    		
    	});
    	
    	/* �ӵ���¼� */
    	this.btnNo.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				destroyWindow();
			}
			
			public void mouseEntered(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}

			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}
    		
    	});
    }

    /**
     * @discription  <ȷ��>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/31> <p>
     * @param        <��>
     * @return       <����ֵ����>
     * 
     * modifier      <��> <p>
     * modifyDate    <��> <p>
     * modifyInfo	 <��> <p>
     * @see          <��ص���>
     */
    public void confrim() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		Object obj = node.getUserObject();
		RESchTemplateInfo info = (RESchTemplateInfo) obj;
		if (!info.isIsLeaf()) {
			FDCMsgBox.showWarning("��ѡ����ϸ");
			SysUtil.abort();
		}
		getUIContext().put("template", obj);

		destroyWindow();
	}
    
    /**
     * @discription  <��ʼ����ʾ��ʽ>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/31> <p>
     * @param        <��>
     * @return       <����ֵ����>
     * 
     * modifier      <��> <p>
     * modifyDate    <��> <p>
     * modifyInfo	 <��> <p>
     * @see          <��ص���>
     */
    public void initDisplayStyle(){
    	this.tblMain.setVisible(false);
    	this.btnOk.setEnabled(true);
    	this.btnNo.setEnabled(true);
    	
    	//���ø��ڵ㲻��ʾ
    	this.treeMain.setRootVisible(false);
    	
    	//�������ڵ�ȫչ��
    	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
    }

    /**
     * @discription  <������>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/31> <p>
     * @param        <��>
     * @return       <����ֵ����>
     * 
     * modifier      <��> <p>
     * modifyDate    <��> <p>
     * modifyInfo	 <��> <p>
     * @see          <��ص���>
     */
    protected void initTree() throws Exception {

		buildTemplateTree();
		treeMain.setRootVisible(false);
		treeMain.setShowsRootHandles(true);
		treeMain.expandRow(0);
		treeMain.setSelectionRow(0);
	}
    
    /**
     * ����ʾ������
     */
    public void initUIToolBarLayout() {
//    	super.initUIToolBarLayout();
    }
    
    /**
     * @discription  <������>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/31> <p>
     * @param        <��>
     * @return       <����ֵ����>
     * 
     * modifier      <��> <p>
     * modifyDate    <��> <p>
     * modifyInfo	 <��> <p>
     * @throws Exception 
     * @see          <��ص���>
     */
    private void buildTemplateTree() throws Exception{
    	treeBuilder = TreeBuilderFactory.createTreeBuilder(getTempLNTreeNodeCtrl(), 50, 5, new FilterInfo(), new SelectorItemCollection());
    	treeBuilder.buildTree(treeMain);
    }
    
    
    private ILNTreeNodeCtrl getTempLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(RESchTemplateFactory.getRemoteInstance());
	}

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionView_actionPerformed(e);
    }


	protected ICoreBase getBizInterface() throws Exception {
		return RESchTemplateFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}
		

}