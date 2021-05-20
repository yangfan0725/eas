package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.eas.util.client.EASResource;

public class KDTaskStatePanel extends KDPanel {
	private static final long serialVersionUID = -5540592290581369462L;

	Logger logger = Logger.getLogger(KDTaskStatePanel.class);

	public static String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
	// ��ʱ���
	public static String WELLDONE = EASResource.getString(rsPath, "welldone");
	// ��ʱ���
//	public static String DONE = EASResource.getString(rsPath, "done");
	public static String DONE = "��ʱ���+����ʱδ���";
	// ��ʱ��δ���
	public static String LATE = "������";
	// ��ȷ��
	public static String TODO = EASResource.getString(rsPath, "todo");
	// �ո�
	public static String space = " ";
	// ð��
	public static String colon = "��";

	// ��
	public static String achieve = EASResource.getString(rsPath, "achieve");
	// Ȧ
	public static String pending = EASResource.getString(rsPath, "pending");

	// ��
	public static Color RED = new Color(245, 0, 0);
	// ��
	public static Color GREEN = new Color(10, 220, 10);
	// ��
	public static Color ORANGE = new Color(220, 180, 0);

	public KDTaskStatePanel() {
		initLayout();
		initDefualtPanel();
	}

	public KDTaskStatePanel(String[] names, String[] values, Color[] colors, boolean[] isBords) {
		initLayout();
		initPanel(names, values, colors, isBords);
	}

	/**
	 * ˮƽ���Ϊ0
	 */
	private void initLayout() {
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		layout.setHgap(3);
		setLayout(layout);
	}

	/**
	 * Ĭ����壬���� ��ʱ��ɡ���ʱ��ɡ���ʱ��δ��ɡ���ȷ��
	 */
	protected void initDefualtPanel() {
		int width = 0;

		JLabel lbWDV = new JLabel(pending);
		lbWDV.setForeground(GREEN);
		lbWDV.setFont(lbWDV.getFont().deriveFont(Font.BOLD));
		add(lbWDV);
		
		String wd = space + WELLDONE;
		JLabel lbWD = new JLabel(wd);
		width += lbWD.getFontMetrics(lbWD.getFont()).stringWidth(lbWD.getText());
		add(lbWD);
		
		width += lbWDV.getFontMetrics(lbWDV.getFont()).stringWidth(lbWDV.getText());

		
		JLabel lbDNV = new JLabel(pending);
		lbDNV.setForeground(RED);
		lbDNV.setFont(lbDNV.getFont().deriveFont(Font.BOLD));
		add(lbDNV);
		width += lbDNV.getFontMetrics(lbDNV.getFont()).stringWidth(lbDNV.getText());
		
		String dn = space + DONE;
		JLabel lbDN = new JLabel(dn);
		add(lbDN);
		width += lbDN.getFontMetrics(lbDN.getFont()).stringWidth(lbDN.getText());
		

		JLabel lbLTV = new JLabel(pending);
		lbLTV.setForeground(ORANGE);
		add(lbLTV);
		width += lbLTV.getFontMetrics(lbLTV.getFont()).stringWidth(lbLTV.getText());
		
		String lt = space + LATE;
		JLabel lbLT = new JLabel(lt);
		add(lbLT);
		width += lbLT.getFontMetrics(lbLT.getFont()).stringWidth(lbLT.getText());

//		String td = space + TODO + colon;
//		JLabel lbTD = new JLabel(td);
//		add(lbTD);
//		width += lbTD.getFontMetrics(lbTD.getFont()).stringWidth(lbTD.getText());
//		JLabel lbTDV = new JLabel(pending);
//		lbTDV.setForeground(ORANGE);
//		add(lbTDV);
//		width += lbTDV.getFontMetrics(lbTDV.getFont()).stringWidth(lbTDV.getText());

		Dimension size = new Dimension(1000, 20);
		setPreferredSize(size);
		setSize(size);
	}

	/**
	 * ��ʼ��˵�����
	 * 
	 * @param names
	 *            ����
	 * @param values
	 *            ��ʾ�ַ�
	 * @param colors
	 *            �ַ���ɫ
	 * @param isBords
	 *            �Ƿ����
	 */
	private void initPanel(String[] names, String[] values, Color[] colors, boolean[] isBords) {
		if (names == null || values == null || colors == null) {
			logger.error("null params");
			return;
		} else {
			int nlen = names.length;
			int vlen = values.length;
			int clen = colors.length;
			if (nlen == 0 || vlen == 0 || clen == 0) {
				logger.error("null params");
				return;
			} else if (nlen != vlen || nlen != clen) {
				logger.error("error params length");
				return;
			} else {
				int width = 0;
				for (int i = 0; i < nlen; i++) {
					JLabel lbName = new JLabel(space + names[i] + colon);
					add(lbName);
					width += lbName.getFontMetrics(lbName.getFont()).stringWidth(lbName.getText());
					JLabel lbValue = new JLabel(values[i]);
					lbValue.setForeground(colors[i]);
					if (isBords != null && isBords.length > i && isBords[i]) {
						Font font = lbValue.getFont().deriveFont(Font.BOLD);
						lbValue.setFont(font);
					}
					add(lbValue);
					width += lbValue.getFontMetrics(lbValue.getFont()).stringWidth(lbValue.getText());
				}
				Dimension size = new Dimension(width, 20);
				setPreferredSize(size);
				setSize(size);
			}
		}
	}
}
