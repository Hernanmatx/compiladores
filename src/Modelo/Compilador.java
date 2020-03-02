package Modelo;

import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JOptionPane;

public class Compilador {

    ArrayList<Object> num = new ArrayList<>();
    ArrayList<Object> ide = new ArrayList<>();
    Stack<Integer> valores = new Stack<>();
    Stack<Character> operadores = new Stack<>();


    public String respu(String operacion) {
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

        char[] tokens = expresion.toCharArray();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ') {
                continue;
            }
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuffer sbuf = new StringBuffer();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') {
                    sbuf.append(tokens[i++]);
                }
                num.add(sbuf);
                ide.add("Número");
                valores.push(Integer.parseInt(sbuf.toString()));
            } else if (tokens[i] == '(') {
                num.add(tokens[i]);
                ide.add("Paréntesis Apertura");
                operadores.push(tokens[i]);
            } else if (tokens[i] == ')') {
                while (operadores.peek() != '(') {
                    valores.push(aplicarOperaciones(operadores.pop(), valores.pop(), valores.pop()));
                }
                num.add(tokens[i]);
                ide.add("Paréntesis Cierre");
                operadores.pop();
            } else if (tokens[i] == '+' || tokens[i] == '-'
                    || tokens[i] == '^' || tokens[i] == '*' || tokens[i] == '/') {
                while (!operadores.empty() && precedencia(tokens[i], operadores.peek())) {
                    valores.push(aplicarOperaciones(operadores.pop(), valores.pop(), valores.pop()));
                }
                operadores.push(tokens[i]);
                num.add(tokens[i]);
                ide.add("Operador Numérico");
            }
        }
        while (!operadores.empty()) {
            valores.push(aplicarOperaciones(operadores.pop(), valores.pop(), valores.pop()));
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
        if ((op1 == '^' || op1 == '/') && (op2 == '*' || op2 == '/')) {
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
                if (b == 0) {
                    JOptionPane.showMessageDialog(null, "No se puede dividir dentro de cero", "Error", JOptionPane.ERROR_MESSAGE);
                }
                return a / b;
            case '^':
                double s = Math.pow(a, b);
                return (int) s;
        }
        return 0;
    }

}
