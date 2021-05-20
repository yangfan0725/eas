package com.kingdee.eas.fdc.schedule.util.subway;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;

/**
 * ����ͼ�ڵ�
 * <p>
 * һ���ڵ�ʵ������һ����ť���������ʱ��ʾ��ϸ��Ϣ���Ƴ�ʱ����<br>
 * ��ϸ��Ϣ��չʾ������Ⱦ��KDSubwayItemRenderer����<br>
 * ��Ⱦ��������ϸֵ����������壬Ȼ�����չʾ����
 * 
 * @author emanon
 * 
 */
public class KDSubwayItemButton extends JButton {
	
	private static final long serialVersionUID = -2027361468833294534L;

	// ��������
	private JDialog dlgDetail = null;
	// ��ϸ���
	private JComponent comptDetail = null;
	// ��Ⱦ��
	private KDSubwayItemRenderer renderer = null;
	// ��ϸֵ
	private Object detail = null;
	// ��ϸֵ
	private boolean  isExecutStage = false;
	
	public KDSubwayItemButton() {
//		addMouseListener(new MouseListener() {
//
//			public void mouseClicked(MouseEvent arg0) {
//				showDetail();
//			}
//
//			public void mouseEntered(MouseEvent arg0) {
//				showDetail();
//			}
//
//			public void mouseExited(MouseEvent arg0) {
//				unShowDetail();
//			}
//
//			public void mousePressed(MouseEvent arg0) {
//				showDetail();
//			}
//
//			public void mouseReleased(MouseEvent arg0) {
//				unShowDetail();
//			}
//		});
	    setEnabled(false);

	}
	public KDSubwayItemButton(boolean isExecutStage) {
		
		this.isExecutStage =isExecutStage;
		
		if(this.isExecutStage){
		
			addMouseListener(new MouseListener() {
				
				public void mouseClicked(MouseEvent arg0) {
//					showDetail();					
				}
				
				public void mouseEntered(MouseEvent arg0) {
					showDetail();
				}
				
				public void mouseExited(MouseEvent arg0) {
					unShowDetail();
				}
				
				public void mousePressed(MouseEvent arg0) {
//					showDetail();
				}
				
				public void mouseReleased(MouseEvent arg0) {
//					unShowDetail();
				}
			});
		}
	    setEnabled(false);
	}
	public void setRenderer(KDSubwayItemRenderer renderer) {
		this.renderer = renderer;
	}

	public KDSubwayItemRenderer getRenderer() {
		return renderer;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}

	public Object getDetail() {
		return detail;
	}
//	  // �ü򵥵Ļ�����ť�ı߽硣
//	protected void paintBorder(Graphics g) {
//		g.setColor(new Color(56, 93, 138));
//	}
	/**
	 * չʾ��ϸ��Ϣ<br>
	 * ����renderer���������������Dialog�ڣ���չʾ
	 */
	private void showDetail() {		
		if (renderer != null) {
			comptDetail = renderer.getSubwayRendererCompt(this, detail);
			if (comptDetail != null) {
				dlgDetail = new JDialog();
				dlgDetail.setUndecorated(true);
				Dimension preferredSize = comptDetail.getPreferredSize();
				// dlgDetail.setPreferredSize(preferredSize);
				dlgDetail.setSize(preferredSize);
				Point location = this.getLocationOnScreen();
				dlgDetail.setBounds(location.x, location.y + this.getHeight() + 2, preferredSize.width, preferredSize.height);
				dlgDetail.add(comptDetail);
				dlgDetail.setVisible(true);
			}
		}
	}

	/**
	 * ������ϸ��Ϣ
	 */
	private void unShowDetail() {
		if (dlgDetail == null) {
			return;
		}
		dlgDetail.setVisible(false);
		comptDetail = null;
	}

}
