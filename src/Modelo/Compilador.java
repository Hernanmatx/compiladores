package Modelo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Compilador {

    public int cona;
    ArrayList<Object> num = new ArrayList<>();
    ArrayList<Object> ide = new ArrayList<>();
    Stack<Float> valores = new Stack<>();
    Stack<Character> operadores = new Stack<>();
    private Stack<Character> stackLetras = new Stack<>();
    boolean balan;
    int conbalanA, conbalanC;

    public boolean procesarCadena(String cadena) {
        conbalanA = 0;
        conbalanC = 0;
        balan = false;
        stackLetras.clear();
        char arrayDeLetras[] = cadena.toCharArray();
        int i;
        for (i = 0; i < arrayDeLetras.length; i++) {
            if (arrayDeLetras[i] == '(') {
                stackLetras.push(arrayDeLetras[i]);
                switch (arrayDeLetras[i]) {
                    case '(':
                        conbalanA++;
                        break;
                }
            } else if (arrayDeLetras[i] == ')') {
                switch (arrayDeLetras[i]) {
                    case ')':
                        conbalanC++;
                        break;
                }
                if (!stackLetras.empty() && stackLetras.peek() != ')') {
                    stackLetras.pop();
                } else {
                    stackLetras.push(arrayDeLetras[i]);
                }
            }
        }
        balan = stackLetras.empty();
        return balan;
    }

    public int getConbalanA() {
        return conbalanA;
    }
    
    public int getConbalanC() {
        return conbalanC;
    }

    public String respu(String operacion) {
        UIManager.put("OptionPane.background", Color.white);
        UIManager.put("Panel.background", Color.white);
        String oper = operacion;
        float res;
        res = evaluacion(operacion);
        return Float.toString(res);
    }

    public Object getNum() {
        return num;
    }

    public Object getIde() {
        return ide;
    }

    public float evaluacion(String expresion) {
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
                valores.push(Float.parseFloat(sbuf.toString()));
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

    public float asignacionNombre(String expresion) {
        cona = 0;
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
                valores.push(Float.parseFloat(sbuff.toString()));
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
                switch (token[i]) {
                    case '+':
                        operadores.push(token[i]);
                        num.add(token[i]);
                        ide.add("Operador Suma");
                        break;
                    case '-':
                        operadores.push(token[i]);
                        num.add(token[i]);
                        ide.add("Operador Resta");
                        break;
                    case '^':
                        operadores.push(token[i]);
                        num.add(token[i]);
                        ide.add("Operador Exponente");
                        break;
                    case '*':
                        operadores.push(token[i]);
                        num.add(token[i]);
                        ide.add("Operador Multiplicación");
                        break;
                    case '/':
                        operadores.push(token[i]);
                        num.add(token[i]);
                        ide.add("Operador División");
                        break;
                }

            } else if (Character.isLetter(token[i])) {
                JOptionPane.showMessageDialog(null, "No se permiten letras", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (token[i] != '+' || token[i] != '-' || token[i] != '*' || token[i] != '/'
                    || token[i] != '^') {
                switch (token[i]) {
                    case '[':
                    case ']':
                        JOptionPane.showMessageDialog(null, "No se permiten corchetes", "Error", JOptionPane.ERROR_MESSAGE);
                        cona = 1;
                        break;
                    case '{':
                    case '}':
                        JOptionPane.showMessageDialog(null, "No se permiten llaves", "Error", JOptionPane.ERROR_MESSAGE);
                        cona = 1;
                        break;
                    case '\n':
                        JOptionPane.showMessageDialog(null, "No se permite más de un salto de linea", "Error", JOptionPane.ERROR_MESSAGE);
                        cona = 1;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Signo no reconocido", "Error", JOptionPane.ERROR_MESSAGE);
                        cona = 1;
                        break;
                }
            }
        }
        return valores.pop();
    }

    public int getCona() {
        return cona;
    }

    public static boolean precedencia(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '^' || op1 == '*') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        if ((op1 == '^' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        if ((op1 == '^') && (op2 == '/' || op2 == '*')) {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        } else {
            return true;
        }
    }

    public float aplicarOperaciones(char op, float b, float a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    cona = 1;
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
