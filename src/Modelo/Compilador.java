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
    int conbalanA, conbalanC, tador, condor, tad;
    String nombreM;
    float res;

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

    public String respu(String operacion) {
        UIManager.put("OptionPane.background", Color.white);
        UIManager.put("Panel.background", Color.white);
        res = evaluacion(operacion);
        return Float.toString(res);
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
        tador = 0;
        cona = 0;
        tad = 0;
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
                setNumero(i, sbuff);
            } else if (token[i] == '(') {
                tador = (i - 2);
                tad = (tador - 2);
                if (i >= 2) {
                    if (token[tador] >= '0' && token[tador] <= '9') {
                        JOptionPane.showMessageDialog(null, "Debe existir un Operador entre un Número "
                                + "y un Paréntesis de Apertura", "Error", JOptionPane.ERROR_MESSAGE);
                        cona = 1;
                    }
                    if (token[tador] == '+' || token[tador] == '-' || token[tador] == '^'
                            || token[tador] == '*' || token[tador] == '/') {
                        if (tador <= expresion.length() && (token[tad] >= '0' && token[tad] <= '9')) {
                        } else {
                            //valInicio(tador, tad, token, expresion);
                        }//+ ( 4
                    }
                }
                setParAper(i, token);
            } else if (token[i] == ')') {
                condor = (expresion.length() - 2);
                tador = (i + 2);
                tad = (tador + 2);
                if (i <= condor) {
                    if (token[tador] >= '0' && token[tador] <= '9') {
                        JOptionPane.showMessageDialog(null, "Debe existir un Operador entre un Número "
                                + "y un Paréntesis de Cierre", "Error", JOptionPane.ERROR_MESSAGE);
                        cona = 1;
                    }
                    if (token[tador] == '+' || token[tador] == '-' || token[tador] == '^'
                            || token[tador] == '*' || token[tador] == '/') {
                        switch (token[tador]) {
                            case '+':
                                nombreM = "Suma";
                                valTerminar(tad, token, expresion, nombreM);
                                break;
                            case '-':
                                nombreM = "Resta";
                                valTerminar(tad, token, expresion, nombreM);
                                break;
                            case '^':
                                nombreM = "Exponente";
                                valTerminar(tad, token, expresion, nombreM);
                                break;
                            case '*':
                                nombreM = "Multiplicación";
                                valTerminar(tad, token, expresion, nombreM);
                                break;
                            case '/':
                                nombreM = "División";
                                valTerminar(tad, token, expresion, nombreM);
                                break;
                        }
                    }
                }
                setParCier(i, token);
            } else if (token[i] == '+' || token[i] == '-'
                    || token[i] == '^' || token[i] == '*' || token[i] == '/') {
                condor = (expresion.length() - 2);
                tador = (i + 2);
                tad = (tador - 4);
                switch (token[i]) {
                    case '+':
                        nombreM = "Suma";
                        if (i <= condor) {
                            setOpr(token, tad, tador, nombreM);
                            setSuma(i, token);
                        } else {
                            setSuma(i, token);
                        }
                        break;
                    case '-':
                        nombreM = "Resta";
                        if (i <= condor) {
                            setOpr(token, tad, tador, nombreM);
                            setResta(i, token);
                        } else {
                            setResta(i, token);
                        }
                        break;
                    case '^':
                        nombreM = "Exponente";
                        if (i <= condor) {
                            setOpr(token, tad, tador, nombreM);
                            setExpo(i, token);
                        } else {
                            setExpo(i, token);
                        }
                        break;
                    case '*':
                        nombreM = "Multiplicación";
                        if (i <= condor) {
                            setOpr(token, tad, tador, nombreM);
                            setMulti(i, token);
                        } else {
                            setMulti(i, token);
                        }
                        break;
                    case '/':
                        nombreM = "División";
                        if (i <= condor) {
                            setOpr(token, tad, tador, nombreM);
                            setDiv(i, token);
                        } else {
                            setDiv(i, token);
                        }
                        break;
                }
            } else if (Character.isLetter(token[i])) {
                JOptionPane.showMessageDialog(null, "No se permiten letras", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (token[i] != '+' || token[i] != '-'
                    || token[i] != '^' || token[i] != '*' || token[i] != '/') {
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
                        JOptionPane.showMessageDialog(null, "No se permite más de un "
                                + "salto de linea", "Error", JOptionPane.ERROR_MESSAGE);
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
            case '^':
                double s = Math.pow(a, b);
                return (int) s;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    JOptionPane.showMessageDialog(null, "No se puede dividir dentro "
                            + "de cero", "Error", JOptionPane.ERROR_MESSAGE);
                    cona = 1;
                }
                return a / b;
        }
        return 0;
    }

    public void valTerminar(int tad, char[] token, String expresion, String nombreM) {
        if (tad <= (expresion.length()) && token[tad] >= '0' && token[tad] <= '9') {
        } else {
            JOptionPane.showMessageDialog(null, "La expresión no puede terminar con "
                    + "un Operador " + nombreM, "Error", JOptionPane.ERROR_MESSAGE);
            cona = 1;
        }
    }

    /*
    public void valInicio(int tador, int tad, char[] token, String expresion) { //MEJORAR
        if (token.length <= (expresion.length()) && token[tador] == '+' || token[tador] == '*'
                || token[tador] == '-' || token[tador] == '/' || token[tador] == '^') {
            if (tad <= (expresion.length()) && token[tad] >= '0' && token[tad] <= '9') {
            } else {
                switch (token[tador]) {
                    case '+':
                        JOptionPane.showMessageDialog(null, "La expresión no puede iniciar con "
                                + "un operador suma", "Error", JOptionPane.ERROR_MESSAGE);
                        cona = 1;
                        break;
                    case '-':
                        JOptionPane.showMessageDialog(null, "La expresión no puede iniciar con "
                                + "un operador resta", "Error", JOptionPane.ERROR_MESSAGE);
                        cona = 1;
                        break;
                    case '/':
                        JOptionPane.showMessageDialog(null, "La expresión no puede iniciar con "
                                + "un operador división", "Error", JOptionPane.ERROR_MESSAGE);
                        cona = 1;
                        break;
                    case '*':
                        JOptionPane.showMessageDialog(null, "La expresión no puede iniciar con "
                                + "un Operador Multiplicación", "Error", JOptionPane.ERROR_MESSAGE);
                        cona = 1;
                        break;
                    case '^':
                        JOptionPane.showMessageDialog(null, "La expresión no puede iniciar con "
                                + "un Operador Exponente", "Error", JOptionPane.ERROR_MESSAGE);
                        cona = 1;
                        break;
                }
            }
        }
    }*/
    public Object getNum() {
        return num;
    }

    public Object getIde() {
        return ide;
    }

    public int getCona() {
        return cona;
    }

    public int getConbalanA() {
        return conbalanA;
    }

    public int getConbalanC() {
        return conbalanC;
    }

    public void setNumero(int i, StringBuffer sbuff) {
        num.add(sbuff);
        ide.add("Número");
        valores.push(Float.parseFloat(sbuff.toString()));
    }

    public void setParAper(int i, char[] token) {
        num.add(token[i]);
        ide.add("Paréntesis Apertura");
        operadores.push(token[i]);
    }

    public void setParCier(int i, char[] token) {
        num.add(token[i]);
        ide.add("Paréntesis Cierre");
        operadores.pop();
    }

    public void setSuma(int i, char[] token) {
        operadores.push(token[i]);
        num.add(token[i]);
        ide.add("Operador Suma");
    }

    public void setResta(int i, char[] token) {
        operadores.push(token[i]);
        num.add(token[i]);
        ide.add("Operador Resta");
    }

    public void setExpo(int i, char[] token) {
        operadores.push(token[i]);
        num.add(token[i]);
        ide.add("Operador Exponente");
    }

    public void setMulti(int i, char[] token) {
        operadores.push(token[i]);
        num.add(token[i]);
        ide.add("Operador Multiplicación");
    }

    public void setDiv(int i, char[] token) {
        operadores.push(token[i]);
        num.add(token[i]);
        ide.add("Operador División");
    }

    public void setOpr(char[] token, int tad, int tador, String nombreOpr) {
        switch (token[tador]) {
            case ')':
                JOptionPane.showMessageDialog(null, "Debe existir un Número entre un Operador " + nombreOpr
                        + " y un Paréntesis de Cierre", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
        switch (token[tad]) {
            case '(':
                JOptionPane.showMessageDialog(null, "Debe existir un Número entre un Operador " + nombreOpr
                        + " y un Paréntesis de Apertura", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}
