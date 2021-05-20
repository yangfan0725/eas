package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.IKDComponent;
import com.kingdee.bos.ctrl.swing.IKDEditor;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;

/**
 * �׶��Գɹ��༭��
 * <p>
 * ���ڱ༭��Ԫ���е�List
 * 
 * @author emanon
 * 
 */
public class AchievementManagerCellEditor extends JComponent implements
		IKDComponent, IKDEditor {

	public static Color COLOR_RED = new Color(220, 190, 190);
	public static Color COLOR_GREEN = new Color(200, 250, 200);
	public static Color COLOR_YELLOW = new Color(200, 200, 100);
	public static Color COLOR_GRAY = new Color(200, 200, 200);

	// ��Ԫ��listֵ
	private List value;
	// �Զ������
	private Object userObject;
	// ��ǰ��Ԫ���������
	private int maxSize = 1;
	// ��ui
	private AchievementManagerListUI ui;

	public AchievementManagerCellEditor(AchievementManagerListUI ui) {
		super();
		this.ui = ui;
		createUI();
	}

	protected void createUI() {
		initUI();
	}

	private void initUI() {
		removeAll();
		if (value != null) {
			setLayout(new GridLayout(maxSize, 1));
			for (int i = 0; i < value.size(); i++) {
				Map cellData = (Map) value.get(i);
				String id = (String) cellData.get("id");
				String name = (String) cellData.get("name");
				String state = (String) cellData.get("state");
				KDLabel lb = new KDLabel(name);
				if (id.equals(ui.getSelectedID())) {
					lb.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {
					lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
				lb.setOpaque(true);
				lb.setUserObject(id);
				// ��ɫ��ɫ����δ�ύ�ĳɹ���
				if (FDCBillStateEnum.SAVED_VALUE.equals(state)) {
					lb.setBackground(COLOR_GRAY);
					// ��ɫ��ɫ�������еĳɹ���
				} else if (FDCBillStateEnum.SUBMITTED_VALUE.equals(state)
						|| FDCBillStateEnum.AUDITTING_VALUE.equals(state)) {
					lb.setBackground(COLOR_YELLOW);
					// ��ɫ��ɫ��������ͨ���ĳɹ���
				} else if (FDCBillStateEnum.AUDITTED_VALUE.equals(state)) {
					lb.setBackground(COLOR_GREEN);
				}
				lb.addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 1) {
							// ѡ��ĳ��
							// �õ�����
							KDLabel compt = (KDLabel) e.getComponent();
							// �õ�������װ�Ķ���Id
							String id = (String) compt.getUserObject();
							ui.setSelectedID(id);
						} else if (e.getClickCount() == 2) {
							// ��ѡ�еĳɹ�
							try {
								actionViewCellData_ActionPerformed(e);
							} catch (BOSException e1) {
								ui.handUIException(e1);
							}
						}
					}

					public void mouseEntered(MouseEvent e) {
						KDLabel compt = (KDLabel) e.getComponent();
						compt.setBorder(BorderFactory
								.createLineBorder(Color.RED));
					}

					public void mouseExited(MouseEvent e) {
						KDLabel compt = (KDLabel) e.getComponent();
						compt.setBorder(BorderFactory
								.createLineBorder(Color.BLACK));
					}

					public void mousePressed(MouseEvent e) {
					}

					public void mouseReleased(MouseEvent e) {
					}

				});
				add(lb);
			}
		}
	}

	public void actionViewCellData_ActionPerformed(MouseEvent e)
			throws BOSException {
		// �õ�����
		KDLabel compt = (KDLabel) e.getComponent();
		// �õ�������װ�Ķ���Id
		String id = (String) compt.getUserObject();
		// MsgBox.showInfo(id);
		if (id != null) {
			IUIFactory uiFactory = null;
			try {
				uiFactory = UIFactory
						.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
				UIContext uicontext = new UIContext(this);
				uicontext.put(UIContext.ID, id);
				IUIWindow uiwindow = uiFactory.create(
						AchievementManagerEditUI.class.getName(), uicontext,
						null, OprtState.VIEW);
				uiwindow.show();
			} catch (UIException e1) {
				e1.printStackTrace();
			}
		}
		// ui.fillTable();
	}

	public void addDataChangeListener(DataChangeListener datachangelistener) {
		// TODO Auto-generated method stub

	}

	public Object getValue() {
		return value;
	}

	public boolean isDisplay() {
		return true;
	}

	public void removeDataChangeListener(DataChangeListener datachangelistener) {
		// TODO Auto-generated method stub

	}

	public void setDisplay(boolean flag) {
		// TODO Auto-generated method stub

	}

	public void setValue(Object obj) {
		if (obj instanceof List) {
			value = (List) obj;
		} else {
			value = null;
		}
		initUI();
	}

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object obj) {
		userObject = obj;
	}

	/**
	 * ȡ�õ�ǰ��Ԫ���������
	 * 
	 * @description
	 * @author ���
	 * @createDate 2011-8-27
	 * @param maxSize
	 *            void
	 * @version EAS7.0
	 * @see
	 */
	public void setMaxSize(int maxSize) {
		if (maxSize > 0) {
			this.maxSize = maxSize;
		} else {
			this.maxSize = 1;
		}
	}

	public int getMaxSize() {
		return maxSize;
	}

}
