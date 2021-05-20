package com.kingdee.eas.fdc.schedule.util.subway;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.schedule.client.FDCScheduleTaskPropertiesNewUI;
import com.kingdee.eas.fdc.schedule.client.FDCSpecialScheduleTaskPropertiesUI;
import com.kingdee.eas.fdc.schedule.client.TasksIn2MStoneListUI;

/**
 * �ڵ�����������
 * <p>
 * ���������߱��뱻�ڵ㸲�ǣ��˴������������߻���һ�����<br>
 * �ڹ�������ͼ��ʱ�����Ȼ������ߣ�Ȼ�������ϻ��ڵ㡣<br>
 * ������Ҳͬ�ڵ�һ������һ��һ�λ��������������չ��ÿ���ڵ����߲�ͬ
 * 
 * @author emanon
 * 
 */
public class KDSubwayLinePanel extends JPanel {
	private static final long serialVersionUID = 5344132260589737475L;

	// �ڵ����
	private int itemNums = 0;
	// �������߳���(���ڵ��е��ľ���)
	private int lineLength = 60;
	// �������߿��
	private int lineHeight = 4;
	// ����������ɫ
	private Color lineColor = Color.BLACK;
//	// ��ʼ���� longNumber
//	private String fromTask = null;
//	// �������� longNumber
//	private String toTask = null;
//	// ��ĿID
//	private String project = null;
	

	public KDSubwayLinePanel(int itemNums, int lineLength, int lineHeight,
			Color lineColor, final String fromTask, final String toTask, final String project ,final boolean isNeedEvaluation,final  boolean isNeedClick) {
		this.itemNums = itemNums;
		this.lineLength = lineLength;
		this.lineHeight = lineHeight;
		this.lineColor = lineColor;
//		this.fromTask = fromTask;
//		this.toTask = toTask;
//		this.project = project;
//		
		// ����͸��
		this.setOpaque(false);
		if(isNeedClick){			
			this.addMouseListener(new MouseListener() {			
				public void mouseClicked(MouseEvent arg0) {
					if (arg0.getClickCount() == 2) {	
						String TasksIn2MStoneListName = TasksIn2MStoneListUI.class.getName();
						//��Ӳ�ѯ����
						UIContext uiContext = new UIContext();
						uiContext.put("fromTask",fromTask );
						uiContext.put("toTask", toTask);
						uiContext.put("project", project);
						uiContext.put("isNeedEvaluation", isNeedEvaluation);
						
						try {
							UIFactory.createUIFactory().create(TasksIn2MStoneListName, uiContext).show();
						} catch (UIException e) {
							e.printStackTrace();
						}
						
						
					}			
				}
				public void mouseEntered(MouseEvent arg0) {
//				showDetail();
				}
				
				public void mouseExited(MouseEvent arg0) {
//				unShowDetail();
				}
				
				public void mousePressed(MouseEvent arg0) {
//				showDetail();
				}
				
				public void mouseReleased(MouseEvent arg0) {
//				unShowDetail();
				}
			});
		}
		initPanel();
	}

	/**
	 * ��������С
	 */
	private void initPanel() {
		int width = (itemNums - 1) * lineLength;
		Dimension size = new Dimension(width, lineHeight);
		this.setSize(size);
		this.setPreferredSize(size);
	}

	public void paint(Graphics g) {
		super.paint(g);
		drawLines(g);
	}

	/**
	 * ע��<br>
	 * �����������ʵ��һ������β��������ȫһ���ĳ�����<br>
	 * ֮���Բ�һ���Ի��꣬��Ϊ�˷����Ժ���չ�����ܻ����ÿ���ڵ㻭��ͬ������<br>
	 * ������䲻ͬ��ɫ�Ա�ʾ״̬��
	 */
	private void drawLines(Graphics g) {
		for (int i = 0; i < itemNums; i++) {
			drawLine(g, i);
		}
	}

	/**
	 * ��������<br>
	 * ��һ���ڵ㲻����<br>
	 * 
	 * @param g
	 * 
	 * @param index
	 */
	private void drawLine(Graphics g, int index) {
		if (index == 0) {
			return;
		}
		g.setColor(lineColor);
		g.fillRect((index - 1) * lineLength, 0, lineLength, lineHeight);
	}

}