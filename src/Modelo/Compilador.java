package Modelo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.UIManager;

public class Compilador {

    ArrayList<Object> num = new ArrayList<>();
    ArrayList<Object> ide = new ArrayList<>();
    Stack<Integer> valores = new Stack<>();
    Stack<Character> operadores = new Stack<>();

    public String respu(String operacion) {
        UIManager.put("OptionPane.background", Color.white);
        UIManager.put("Panel.background", Color.white);
        String oper = operacion;
        int res;
        res = evaluacion(operacion);
        return Integer.toString(res);
    }

    public Object getNum() {
        return num;
    }

    public Object getIde() {
        return ide;
    }

    public int evaluacion(String expresion) {
        valores.clear();
        operadores.clear();
        char[] tokens = null;
        tokens = expresion.toCharArray();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ') {
                continue;
            }
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuffer sbuf = new StringBuffer();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') {
                    sbuf.append(tokens[i++]);
                }
                valores.push(Integer.parseInt(sbuf.toString()));
            } else if (tokens[i] == '(') {
                operadores.push(tokens[i]);
            } else if (tokens[i] == ')') {
                while (operadores.peek() != '(') {
                    valores.push(aplicarOperaciones(operadores.pop(), valores.pop(), valores.pop()));
                }
                operadores.pop();
            } else if (tokens[i] == '+' || tokens[i] == '-'
                    || tokens[i] == '^' || tokens[i] == '*' || tokens[i] == '/') {
                while (!operadores.empty() && precedencia(tokens[i], operadores.peek())) {
                    valores.push(aplicarOperaciones(operadores.pop(), valores.pop(), valores.pop()));
                }
                operadores.push(tokens[i]);
            }
        }
        while (!operadores.empty()) {
            valores.push(aplicarOperaciones(operadores.pop(), valores.pop(), valores.pop()));
        }
        return valores.pop();
    }

    public int asignacionNombre(String expresion) {
        valores.clear();
        operadores.clear();
        char[] token = null;
        token = expresion.toCharArray();

        for (int i = 0; i < token.length; i++) {
            if (token[i] == ' ') {
                continue;
            }
            if (token[i] >= '0' && token[i] <= '9') {
                StringBuffer sbuff = new StringBuffer();
                while (i < token.length && token[i] >= '0' && token[i] <= '9') {
                    sbuff.append(token[i++]);
                }
                num.add(sbuff);
                ide.add("Número");
                valores.push(Integer.parseInt(sbuff.toString()));
            } else if (token[i] == '(') {
                num.add(token[i]);
                ide.add("Paréntesis Apertura");
                operadores.push(token[i]);
            } else if (token[i] == ')') {
                num.add(token[i]);
                ide.add("Paréntesis Cierre");
                operadores.pop();
            } else if (token[i] == '+' || token[i] == '-'
                    || token[i] == '^' || token[i] == '*' || token[i] == '/') {
                operadores.push(token[i]);
                num.add(token[i]);
                ide.add("Operador Numérico");
            }
        }
        return valores.pop();
    }

    public static boolean precedencia(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        if ((op1 == '^' || op1 == '*') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        if ((op1 == '^' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        if ((op1 == '^' || op1 == '*') && (op2 == '*' || op2 == '/')) {
            return false;
        }
        if ((op1 == '^' || op1 == '/') && (op2 == '/' || op2 == '*')) {
            return false;
        } else {
            return true;
        }
    }

    public static int aplicarOperaciones(char op, int b, int a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            case '^':
                double s = Math.pow(a, b);
                return (int) s;
        }
        return 0;
    }

}
