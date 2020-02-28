package Modelo;

import java.util.Stack;
import javax.swing.JOptionPane;

public class Compilador {
    
    public String respu(String operacion){
        String com;
        int res;
        res = Compilador.evaluacion(operacion);
        com = Integer.toString(res);
        return com;
    }

    public static int evaluacion(String expresion) {
        char[] tokens = expresion.toCharArray();
        System.out.println(tokens);

        Stack<Integer> valores = new Stack<Integer>();
        Stack<Character> operadores = new Stack<Character>();
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ') {
                continue;
            }
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuffer sbuf = new StringBuffer();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') {
                    sbuf.append(tokens[i++]);
                }
                System.out.println(sbuf + " = Numero");
                valores.push(Integer.parseInt(sbuf.toString()));
            } else if (tokens[i] == '(') {
                System.out.println(tokens[i] + " = Parentesis Apertura");
                operadores.push(tokens[i]);
            } else if (tokens[i] == ')') {
                while (operadores.peek() != '(') {
                    valores.push(aplicarOperaciones(operadores.pop(), valores.pop(), valores.pop()));
                }
                System.out.println(tokens[i] + " = Parentesis Cierre");
                operadores.pop();
            } else if (tokens[i] == '+' || tokens[i] == '-'
                    || tokens[i] == '^' || tokens[i] == '*' || tokens[i] == '/') {
                while (!operadores.empty() && precedencia(tokens[i], operadores.peek())) {
                    valores.push(aplicarOperaciones(operadores.pop(), valores.pop(), valores.pop()));
                }
                operadores.push(tokens[i]);
                System.out.println(tokens[i] + " = Operador Numerico");
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
