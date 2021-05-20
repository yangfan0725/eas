package com.kingdee.eas.fdc.aimcost.client;
import java.math.BigDecimal;
import java.util.Stack;

import com.kingdee.eas.fdc.basedata.FDCHelper;

public class Calc {  
  
    /** 
     * ���üӼ��˳������ַ� 
     */  
    private static final String OPERATOR = "+-*/()";  
    /** 
     * �㷨���ŵ����ȼ���ά�� 
     */  
    private static int[][] OPERATORCOMPARE = new int[][] { { 0, 0, -1, -1, -1, 1 },  
            { 0, 0, -1, -1, -1, 1 }, { 1, 1, 0, 0, -1, 1 },  
            { 1, 1, 0, 0, -1, 1 }, { 1, 1, 1, 1, 0, 1 },  
            { -1, -1, -1, -1, -1, 0 } };  
    /** 
     * �����ַ����ڱ�Ǹ������ʽ��β 
     */  
    private static final char ENDCHAR='\n';  
      
    /** 
     * JAVA ִ����� 
     *  
     * @param args 
     */  
    public static void main(String[] args) {  
        println(OPERATOR, OPERATORCOMPARE);  
        String solution = "((2*(19-13*(1+2)/39)/6+4)-5)/5+((2+3)*2-5)";  
        System.out.println(solution+"="+calc(solution));  
    }  
  
    /** 
     * @param solution 
     */  
    public static BigDecimal calc(String solution) {  
        //��Ҫ�����ʽ��ӽ�����  
        solution+=ENDCHAR;  
        Stack<BigDecimal> digital = new Stack<BigDecimal>();  
        Stack<Character> opeators = new Stack<Character>();  
        int begin = 0;  
        int length = 0;  
        char current = 0;  
        for (int i = 0; i < solution.length(); i++) {  
            current = solution.charAt(i);  
            //��������  
            if (('0' <= current && current <= '9')||current=='.') {  
                length++;  
                continue;  
            } else {  
                //����������ѹ������ջ  
                if (length > 0) {  
                    digital.add(new BigDecimal(solution.substring(begin, begin + length)));  
                    begin += length;  
                    length = 0;  
                }  
                begin++;  
                //���ɨ�����,������ջ������ջ����������Ҫ����,����1+2+(1+3),�����д˲���"[3, 4],[+]"Ϊ����ջ��Ԫ��  
                if (ENDCHAR == current) {  
                       while(!opeators.isEmpty()){  
                           operator(digital, opeators);  
                       }  
                } else {  
                    //����ǰ�����ַ�,����ѭ��,����ǰ�����ַ�������ѭ��  
                    while (true) {  
                        if (opeators.isEmpty()) {  
                            opeators.push(current);  
                            break;  
                        } else {  
                            if (OPERATORCOMPARE[OPERATOR.indexOf(current)][OPERATOR  
                                    .indexOf(opeators.lastElement())] > 0) {  
                                opeators.push(current);  
                                break;  
                            } else if (opeators.lastElement() == '('  
                                    && current == ')') {  
                                opeators.pop();  
                                break;  
                            } else if (opeators.lastElement() == '('  
                                    && current != ')') {  
                                opeators.push(current);  
                                break;  
                            } else {  
                                operator(digital, opeators);  
                            }  
                        }  
                    }  
                }  
  
            }  
        }  
        System.out.println(digital);  
        System.out.println(opeators);  
        return digital.pop();  
    }  
  
    /** 
     * �ӷ���ջ����һ������,������ջ������������,������ѧ���㲢ѹ������ջ 
     * @param digital 
     * @param opeators 
     */  
    private static void operator(Stack<BigDecimal> digital,  
            Stack<Character> opeators) {  
        BigDecimal number1=digital.pop();  
        BigDecimal number2=digital.pop();  
        switch (opeators.pop()) {  
        case '+':  
            digital.push(number2.add(number1));  
            break;  
        case '-':  
            digital.push(number2.subtract(number1));  
            break;  
        case '*':  
            digital.push(number2.multiply(number1));  
            break;  
        case '/':  
            digital.push(FDCHelper.divide(number2, number1, 4, BigDecimal.ROUND_HALF_UP));  
            break;  
        }  
    }  
  
    /** 
     * ��ӡ�������ȼ���ά�� 
     *  
     * @param operator 
     * @param operatorCompare 
     */  
    private static void println(String operator, int[][] operatorCompare) {  
        System.out.print(" \t");  
        for (int i = 0; i < operator.length(); i++) {  
            System.out.print(operator.charAt(i));  
            System.out.print('\t');  
        }  
        for (int i = 0; i < operatorCompare.length; i++) {  
            System.out.println();  
            System.out.print(operator.charAt(i));  
            System.out.print('\t');  
            for (int j = 0; j < operatorCompare[i].length; j++) {  
                System.out.print(operatorCompare[i][j] == 0 ? '='  
                        : (operatorCompare[i][j] > 0 ? '>' : '<'));  
                System.out.print('\t');  
            }  
        }  
        System.out.println();  
    }  
  
} 