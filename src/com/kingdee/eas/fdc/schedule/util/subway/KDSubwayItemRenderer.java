package com.kingdee.eas.fdc.schedule.util.subway;

import javax.swing.JComponent;

/**
 * ��ʾ����ͼ�ڵ����ϸ��Ϣ��Ⱦ������
 * <p>
 * ��������̳�JLabel<br>
 * ���ñ���ɫΪ���̣�setBackground(new Color(221, 255, 221));<br>
 * ���ñ߿�Ϊ������setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));<br>
 * 
 * ����ʾ����TestRenderer
 * 
 * @author emanon
 * 
 */
public interface KDSubwayItemRenderer {

	/**
	 * ����һ����壬����Ϊ��Ҫ��ʾ����Ϣ
	 * 
	 * @param btnItem
	 *            ����ͼ�ڵ㰴ť
	 * @param detail
	 * @return
	 */
	public abstract JComponent getSubwayRendererCompt(
			KDSubwayItemButton btnItem, Object detail);

}
