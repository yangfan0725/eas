package com.kingdee.eas.fdc.schedule.util.subway;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

 class RButton extends KDSubwayItemButton{
	  //�졢�ơ��̰�ť���ڱߡ�����
	 
	  //��
	  private Color BLUE = new Color(79, 129, 189);
	  //Ĭ����������
	  private Color DARK_BULE =new Color(56, 93, 138);
	  //�Ƿ���ʾ���
	  private boolean isRedRim = false;
	  
	  private Color TAN=new Color(196, 189, 151);
	  
	  private Color Background= BLUE;
	  
	  public RButton(boolean isRedRim) {		  
//	    super();
	    // ��Щ�����Ѱ�ť��չΪһ��Բ������һ����Բ��
	    Dimension size = getPreferredSize();
	    size.width = size.height = Math.max(size.width, size.height);
	    setPreferredSize(size);
	    //�������ʹJButton�������������������ǻ�һ��Բ�ı�����
	    setContentAreaFilled(false);
	    this.setBackground(Background);
	    this.isRedRim = isRedRim;
	    this.setOpaque(false);
	    setEnabled(false);
	  }
	  
	  public RButton() {		  
//		    super();
		    // ��Щ�����Ѱ�ť��չΪһ��Բ������һ����Բ��
		    Dimension size = getPreferredSize();
		    size.width = size.height = Math.max(size.width+5, size.height+5);
		    setPreferredSize(size);
		    //�������ʹJButton�������������������ǻ�һ��Բ�ı�����
		    setContentAreaFilled(false);
		    this.setBackground(Background);
//		    this.isRedRim = isRedRim;
		    this.setOpaque(false);
		    setEnabled(false);
	  }
	  
	  //��ʾhtml��ʾ
	  public RButton(Color bgColor) {		  
		    super(true);
		    Background=bgColor;
		    // ��Щ�����Ѱ�ť��չΪһ��Բ������һ����Բ��
		    Dimension size = getPreferredSize();
		    size.width = size.height = Math.max(size.width+5, size.height+5);
		    setPreferredSize(size);
		    //�������ʹJButton�������������������ǻ�һ��Բ�ı�����
		    setContentAreaFilled(false);
//		    this.setBackground(bgColor);
//		    this.isRedRim = isRedRim;
		    this.setOpaque(false);
		    setEnabled(false);

	  }
	  
	  // ��Բ�ı����ͱ�ǩ
	  protected void paintComponent(Graphics g) {
//		  super.paintComponent(g);
	    if (getModel().isArmed()) {
	      // �����ѡһ����������ɫ��ΪԲ�ΰ�ť�������
	      g.setColor(Background);
	    }
	    else {
	      g.setColor(Background);
//	      g.setColor(getBackground());
	    }
	    g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
	    //������ûửһ����ǩ�ͽ�����Ρ�
	  }
	
	  // �ü򵥵Ļ�����ť�ı߽硣
	  protected void paintBorder(Graphics g) {
		  if(isRedRim){			  
			  g.setColor(Color.RED);
		  }else{
			  g.setColor(DARK_BULE);
		  }		  
	    g.drawOval(0, 0, getSize().width - 2 , getSize().height - 2);
//	    this.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200),2));
//	    this.setBorder(new LineBorder(new Color(0,0,0),6));//���ñ߿�


	  }

	  // ������¼�
	  Shape shape;
	  
	  public boolean contains(int x, int y) {
	    // �����ť�ı��С������һ���µ���״����
	    if (shape == null || !shape.getBounds().equals(getBounds())) {
	      shape = new Ellipse2D.Float(0, 0,getWidth(), getHeight());
	    }
	    return shape.contains(x, y);
	  }
	
//	  // ���Գ���
//	  public static void main(String[] args) {
//	    // ����һ������Jackpot����ǩ�İ�ť��
//	    JButton button = new RButton(true);
//	//    ImageIcon ic = new ImageIcon("E://clientForMssql//Icons//item_group.gif");
//	//    JButton button2 = new JButton(ic);
//	    JButton button2 = new JButton("hh");
//	    JButton button3 = new RButton();
////	    button.setBackground(BLUE);
//	    // ����һ���������ʾ�����ť��
//	    JFrame frame = new JFrame();
//	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    //frame.getContentPane().setBackground(Color.GRAY);
//	    frame.getContentPane().add(button);
//	    frame.getContentPane().add(button2);
//	    frame.getContentPane().add(button3);
//	    frame.getContentPane().setLayout(new FlowLayout());
//	    frame.setSize(200, 200);
//	    frame.setVisible(true);
//	  }
//��ȡ��Ļ�ֱ���
//		    public static void main(String[] args) {
//		       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		       System.out.println("Width: " + dim.width);
//		       System.out.println("Height: " + dim.height);
//		    }
		
}
 


