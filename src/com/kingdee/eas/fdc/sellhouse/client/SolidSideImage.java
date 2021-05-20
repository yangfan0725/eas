package com.kingdee.eas.fdc.sellhouse.client;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.Border;

import com.kingdee.bos.ctrl.swing.KDPanel;


public class SolidSideImage{
	/**
	 *  ����  �������� ���ȸ߶� ��ȵĵط��� ������� �������Ź���ʵ��
	 */
	private BigDecimal scale=new BigDecimal("1");	

	/**
	 *  Ĭ�ϸ߶�
	 */
	private BigDecimal high= new BigDecimal("50");
	/**
	 *  Ĭ�ϳ���
	 */
	private BigDecimal width=new BigDecimal("70");
	//��Ԫ�񼯺�   ����ΪSolidSideInfo�ļ���
	private List list;
	//������
	private int floor;
	
	private int x;
	
	private String name=null;
	
	private IBackgroundColor bc; //�����߱���ʵ�� ����ɫ���÷���
	private Color c;//��ʱ������¼��Ԫ�񱻵���ǰ�ı���ɫ
	private ISolidSideImageListener listener; //������
	private Border border=BorderFactory.createLineBorder(new Color(128,128,128), 1);//��������button�߿�
	/**
	 *  ����ʱ�贫�� ���ϣ�����������ɫ��ʾ������������
	 * @param list  
	 * @param floor
	 * @param bc
	 * @param listener
	 */
	public SolidSideImage(List list,int floor,IBackgroundColor bc,ISolidSideImageListener listener){
		this.list=list;
		this.floor=floor;
		this.bc=bc;
		this.listener=listener;
	}
	
	private List groupData(int rowIndex){
		List roomList=new ArrayList();
		//�����ȡ�� һ�����е�Ԫ��ļ���
		for(int i=0;i<list.size();i++){
			if(list.get(i) instanceof SolidSideInfo){
				SolidSideInfo info=(SolidSideInfo)list.get(i);
				if(info.getRowIndex()==rowIndex){
					roomList.add(info);
				}
			}
		}
		return roomList;
	}
	//��ȡ ���ص�panel�Ŀ��
	private int getPanelWidth(){
		List widthList=new ArrayList();
		for(int i=floor;i>0;i--){
			List wlist=groupData(i);
			if(wlist.size()==0){
				continue;
			}
			int panelWidth=0;
			for(int j=0;j<wlist.size();j++){
				SolidSideInfo info=(SolidSideInfo)wlist.get(j);
				if(info.getExtent()!=null){
					panelWidth+=bigDecimalToInt(info.getExtent().multiply(scale));
				}else{
					panelWidth+=bigDecimalToInt(width.multiply(scale));
				}
			}
			panelWidth+=40;
			widthList.add(new Integer(panelWidth));
		}
		int w=Integer.valueOf(Collections.max(widthList).toString()).intValue();
		return w+1;
	}
	//��ȡ ���ص�panel�ĸ߶�
	private int getPanelHeight(){
		int panelHeight=0;
		for(int i=floor;i>0;i--){
			List hlist=groupData(i);
			if(hlist.size()==0){
				continue;
			}
			SolidSideInfo info=(SolidSideInfo)hlist.get(0);
			if(info.getRowhigh()!=null){
				panelHeight+=bigDecimalToInt(info.getRowhigh().multiply(scale));
			}else{
				panelHeight+=bigDecimalToInt(high.multiply(scale));
			}
		}
		return panelHeight+1;
	}
	int y=0;
	public KDPanel getPanel(){
		KDPanel panel=new KDPanel();
		panel.setLayout(null);
		//���� ����
		SolidSideInfo ssdInfo =(SolidSideInfo)list.get(0);
		name=ssdInfo.getName();
		if(name!=null){			
			WJButton titleBtn=new WJButton(name);
			titleBtn.setEnabled(false);
			titleBtn.setBorder(border);
			titleBtn.setBackground(new Color(165,165,165));
			titleBtn.setFont(new Font("����",Font.PLAIN,16));
			titleBtn.setBounds(0, y, getPanelWidth(), bigDecimalToInt(high.multiply(scale)));
			panel.add(titleBtn);
			panel.setPreferredSize(new Dimension(getPanelWidth(),getPanelHeight()+bigDecimalToInt(high.multiply(scale))));
			y+=bigDecimalToInt(high.multiply(scale));
		}else{
			panel.setPreferredSize(new Dimension(getPanelWidth(),getPanelHeight()));
		}
		for(int i=floor;i>0;i--){
			KDPanel p=getFloorPanel(i);
			List roomList=groupData(i);
			int panelWidth=0;
			int panelHeight=0;
			if(roomList.size()==0){
				continue;
			}
			SolidSideInfo info=(SolidSideInfo)roomList.get(0);
			if(info.getRowhigh()!=null){
				panelHeight+=bigDecimalToInt(info.getRowhigh().multiply(scale));
			}else{
				panelHeight+=bigDecimalToInt(high.multiply(scale));
			}
			for(int j=0;j<roomList.size();j++){
				SolidSideInfo sinfo=(SolidSideInfo)roomList.get(j);
				if(sinfo.getExtent()!=null){
					panelWidth+=bigDecimalToInt(sinfo.getExtent().multiply(scale));
				}else{
					panelWidth+=bigDecimalToInt(width.multiply(scale));
				}
			}
			panelWidth+=40;
			p.setBounds(0, y, panelWidth, panelHeight);
			panel.add(p);
			y+=panelHeight;
			x=0;
		}
		return panel;
	}	
	
	private KDPanel getFloorPanel(int rowIndex){
		KDPanel panel=new KDPanel();
		panel.setLayout(null);
		List roomList=new ArrayList();
		//�����ȡ�� һ�����е�Ԫ��ļ���
		for(int i=0;i<list.size();i++){
			if(list.get(i) instanceof SolidSideInfo){
				SolidSideInfo info=(SolidSideInfo)list.get(i);
				if(info.getRowIndex()==rowIndex){
					roomList.add(info);
				}
			}
		}
		//���� ���� ��������������
		Collections.sort(roomList, new Comparator(){
			public int compare(Object arg0, Object arg1) {
				SolidSideInfo info0=(SolidSideInfo)arg0;
				SolidSideInfo info1=(SolidSideInfo)arg1;
				if(info0 == null  ||  info1 == null){
					return 0;
				}
				return info0.getColIndex() - info1.getColIndex();
			}
		
		});
		
		if(roomList.size()==0){
			return null;
		}
		SolidSideInfo ssi=(SolidSideInfo)roomList.get(0);
		JButton btn=new JButton(ssi.getRowName());
		btn.setBackground(new Color(165,165,165));
		btn.setBorder(border);
		btn.setFont(new Font("����",Font.PLAIN,13));
		BigDecimal h=new BigDecimal("0.00");
		if(ssi.getRowhigh()==null){
			h=h.add(high);
		}else{
			h=h.add(ssi.getRowhigh());
		}	
		btn.setBounds(0,0,40,bigDecimalToInt(scale.multiply(h)));
		btn.setEnabled(false);
		panel.add(btn);
		x=40;
		for(int i=0;i<roomList.size();i++){
			final SolidSideInfo info=(SolidSideInfo)roomList.get(i);
			final WJButton btn1=new WJButton(info.getText());	
			btn1.setModel(new DefaultButtonModel(){ //ȥ��button��������ı䱳��ɫ
				public void setRollover(boolean b) {
//					super.setRollover(b);
				}
			});
			
			if(info.getState()==null){
				btn1.setBackground(Color.WHITE);//���û�д���״̬ ����ʾ ��ɫ
			}
			btn1.setBackground(bc.getCellBackgroundColor(info.getState()));
			btn1.setFont(new Font("����",Font.PLAIN,13));			
			btn1.setToolTipText(btn1.getText());
			btn1.setObj(info.getBizObj());	
			btn1.setBorder(border);
//			btn1.setMargin(new Insets(0,0,0,0));
			btn1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					
					WJButton activeButton = (WJButton)e.getSource();
					c=activeButton.getBackground();					
					activeButton.setFocusable(false);
					try {
						listener.setColor(activeButton,c);
						listener.actionResponse(activeButton.getObj());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			
			});
			btn1.addMouseListener(new MouseListener(){

				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2 && e.getSource() instanceof WJButton){
						WJButton activeButton = (WJButton)e.getSource();
						try {
							listener.actionBothClicked(activeButton.getObj());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}

				public void mouseEntered(MouseEvent e) {
					
				}

				public void mouseExited(MouseEvent e) {
					
				}

				public void mousePressed(MouseEvent e) {
					
				}

				public void mouseReleased(MouseEvent e) {
					
				}
				
			});
			if(info.getExtent()==null){
				btn1.setBounds(x,0,bigDecimalToInt(scale.multiply(width)), bigDecimalToInt(scale.multiply(h)));
				x+=bigDecimalToInt(scale.multiply(width));
			}else{
				btn1.setBounds(x,0,bigDecimalToInt(info.getExtent().multiply(scale)), bigDecimalToInt(scale.multiply(h)));
				x+=bigDecimalToInt(info.getExtent().multiply(scale));
			}
			panel.add(btn1);
		}
		return panel;
	}
	/**
	 * 
	 * �Ѵ����bigdecimalת��Ϊint
	 * 
	 * @param dec
	 * @return
	 */
	private int bigDecimalToInt(BigDecimal dec){
		return dec.intValue();
	}
	
	public BigDecimal getScale() {
		return scale;
	}

	public void setScale(BigDecimal scale) {
		this.scale = scale;
	}
}
class WJButton extends JButton{

	private Object obj;
	public WJButton() {
		super();
	}
	public WJButton(Action a) {
		super(a);
	}
	public WJButton(Icon icon) {
		super(icon);
	}
	public WJButton(String text, Icon icon) {
		super(text, icon);
	}

	public WJButton(String text) {
		super(text);
	}
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
