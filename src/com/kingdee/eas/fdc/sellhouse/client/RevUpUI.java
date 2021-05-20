/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.RevUpFacadeFactory;
import com.kingdee.eas.fm.common.EnvUtils;
import com.kingdee.eas.fm.common.client.FMISQLEditorStyleModel;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;


/**
 * output class name
 */
public class RevUpUI extends AbstractRevUpUI
{
    private static final Logger logger = CoreUIObject.getLogger(RevUpUI.class);
    public RevUpUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	this.setUITitle("�տ��������");
    	
    	txtScript.setStyleModel(new FMISQLEditorStyleModel());
    	KDWorkButton btnRun = new KDWorkButton();
    	btnRun.setText("���������ű�");
    	this.toolBar.add(btnRun);
    	btnRun.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				loadFromFile("revup_20100518.sql");
			}
    	});
    	
    	KDWorkButton btnRun1 = new KDWorkButton();
    	btnRun1.setText("��������������ݽű�");
    	this.toolBar.add(btnRun1);
    	btnRun1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				loadFromFile("revupCL_20100525.sql");
			}
    	});
    	
    	KDWorkButton btnRun2 = new KDWorkButton();
    	btnRun2.setText("ִ��");
    	this.toolBar.add(btnRun2);
    	btnRun2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				execute();
			}
    	});
    	
		// ������ǰ�����sql���
    	loadFromFile("revup_20100518.sql");
    }
    
    private void loadFromFile(String fileName) {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			reader = new BufferedReader(new InputStreamReader(RevUpUI.class.getResource("/com/kingdee/eas/fdc/sellhouse/client/" + fileName).openStream()));
			String s = null;
			do {
				s = reader.readLine();
				if (s != null) {
					sb.append(s).append(EnvUtils.getLineSeparator());
				}
			} while (s != null);

//			this.txtScript.setText(new String(sb.toString().getBytes("GBK"), "UTF-8"));
			this.txtScript.setText(sb.toString());
		} catch (IOException e) {
			logger.error(e);
			this.txtMsg.append(e.toString());
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
    
	private void execute() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LongTimeDialog longTimeDialog = UITools.getDialog(RevUpUI.this);
				longTimeDialog.setLongTimeTask(new ILongTimeTask() {
					public void afterExec(Object arg0) throws Exception {
						UITools.showMsg(RevUpUI.this, "��ɣ�", false);
					}

					public Object exec() throws Exception {
						
						//�жϲ����ظ�����
						final String sql = "select * from KSQL_USERTABLES where KSQL_TABNAME='t_tmp_revgaizao_s'";
						//TODO ��������ĵ��˰�t_tmp_revgaizao_s��ɾ����ô��.
						IRowSet rs = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql, null);
						if(rs.next()){
							MsgBox.showError("�Ѿ������������������ظ�������");
							return "";
						}
						
						//�ж��Ƿ����������˷��������������Ѿ�����
						FilterInfo filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
						filter.getFilterItems().add(new FilterItemInfo("isBlance", new Integer(1), CompareType.NOTEQUALS));
						if(QuitRoomFactory.getRemoteInstance().exists(filter)){
							MsgBox.showError("������������δ������˷����������˷����㣡");
							return "";
						}
						
						filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
						filter.getFilterItems().add(new FilterItemInfo("hasSettleMent", Boolean.TRUE, CompareType.NOTEQUALS));
						if(ChangeRoomFactory.getRemoteInstance().exists(filter)){
							MsgBox.showError("������������δ����Ļ������������˷����㣡");
							return "";
						}
						
						
						RevUpUI.this.txtMsg.setText(null);
						String alS = RevUpUI.this.txtScript.getText();
						String[] ss = alS.split(";");
						
						try {
							String res = RevUpFacadeFactory.getRemoteInstance().executeSQL(Arrays.asList(ss));
							RevUpUI.this.txtMsg.append(res);
						} catch (BOSException e) {
							logger.error(e);
							RevUpUI.this.txtMsg.append(e.getMessage());
							RevUpUI.this.handleException(e);
						}
						return "";
					}
				});
				longTimeDialog.show();
			}
		});
	}
    

	
}