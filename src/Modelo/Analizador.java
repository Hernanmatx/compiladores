package Modelo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Analizador {

    ArrayList<Object> num = new ArrayList<>();
    ArrayList<Object> ide = new ArrayList<>();
    Stack<Float> valores = new Stack<>();
    Stack<Character> operadores = new Stack<>();
    private Stack<Character> letras = new Stack<>();
    boolean balan;
    int conbalanA, conbalanC, posAdelante, tamañoExp, posAtras, validError, conNum;
    String nombreOpr;
    char[] token, tokens;
    float res;

    public boolean balancearP(String cadena) {
        conbalanA = 0;
        conbalanC = 0;
        balan = false;
        letras.clear();
        char arrayLetras[] = cadena.toCharArray();
        int i;
        for (i = 0; i < arrayLetras.length; i++) {
            if (arrayLetras[i] == '(') {
                letras.push(arrayLetras[i]);
                switch (arrayLetras[i]) {
                    case '(':
                        conbalanA++;
                        break;
                }
            } else if (arrayLetras[i] == ')') {
                switch (arrayLetras[i]) {
                    case ')':
                        conbalanC++;
                        break;
                }
                if (!letras.empty() && letras.peek() != ')') {
                    letras.pop();
                } else {
                    letras.push(arrayLetras[i]);
                }
            }
        }
        balan = letras.empty();
        return balan;
    }

    public String respu(String operacion) {
        UIManager.put("OptionPane.background", Color.white);
        UIManager.put("Panel.background", Color.white);
        res = ejecucion(operacion);
        return Float.toString(res);
    }

    public float ejecucion(String expresion) {
        tokens = null;
        valores.clear();
        operadores.clear();
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
                while (i < token.length && token[i] == '.') {
                    sbuf.append(token[i++]);
                    while (i < token.length && token[i] >= '0' && token[i] <= '9') {
                        sbuf.append(token[i++]);
                    }
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
                while (!operadores.empty() && importancia(tokens[i], operadores.peek())) {
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
        posAdelante = 0;
        validError = 0;
        posAtras = 0;
        token = null;
        valores.clear();
        operadores.clear();
        token = expresion.toCharArray();

        for (int i = 0; i < token.length; i++) {
            if (token[i] == ' ') {
                continue;
            }
            if (token[i] >= '0' && token[i] <= '9') {
                StringBuffer sbuff = new StringBuffer();
                while (i < token.length && token[i] >= '0' && token[i] <= '9') {
                    sbuff.append(token[i++]);
                    conNum = 1;
                }
                while (i < token.length && token[i] == '.') {
                    sbuff.append(token[i++]);
                    while (i < token.length && token[i] >= '0' && token[i] <= '9') {
                        sbuff.append(token[i++]);
                    }
                    conNum = 0;
                }
                if (conNum == 1) {
                    setNumEntero(i, sbuff);
                } else {
                    setNumDecimal(i, sbuff);
                }
                while (i < token.length) {
                    switch (token[i]) {
                        case '+':
                            setValidEspacio();
                            setSuma(i, token);
                            break;
                        case '-':
                            setValidEspacio();
                            setResta(i, token);
                            break;
                        case '^':
                            setValidEspacio();
                            setPoten(i, token);
                            break;
                        case '*':
                            setValidEspacio();
                            setMulti(i, token);
                            break;
                        case '/':
                            setValidEspacio();
                            setDiv(i, token);
                            break;
                        case '(':
                            setValidEspacio();
                            setParAper(i, token);
                            break;
                        case ')':
                            setValidEspacio();
                            setParCier(i, token);
                            break;
                        case ' ':
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "No se permite más de un "
                                    + "salto de linea", "Error", JOptionPane.ERROR_MESSAGE);
                            setValidError();
                            break;
                    }
                    break;
                }
            } else if (token[i] == '(') {
                posAtras = (i - 2);
                if (i >= 2) {
                    if (token[posAtras] >= '0' && token[posAtras] <= '9') {
                        JOptionPane.showMessageDialog(null, "Debe existir un Operador entre un Número "
                                + "y un Paréntesis de Apertura", "Error", JOptionPane.ERROR_MESSAGE);
                        setValidError();
                    }
                }
                setParAper(i, token);
            } else if (token[i] == ')') {
                tamañoExp = (expresion.length() - 2);
                posAdelante = (i + 2);
                if (i <= tamañoExp) {
                    if (token[posAdelante] >= '0' && token[posAdelante] <= '9') {
                        JOptionPane.showMessageDialog(null, "Debe existir un Operador entre un Paréntesis de Cierre "
                                + "y un Número", "Error", JOptionPane.ERROR_MESSAGE);
                        setValidError();
                    }
                }
                setParCier(i, token);
            } else if (token[i] == '+' || token[i] == '-'
                    || token[i] == '^' || token[i] == '*' || token[i] == '/') {
                tamañoExp = (expresion.length() - 2);
                posAdelante = (i + 2);
                posAtras = (i - 2);
                switch (token[i]) {
                    case '+':
                        nombreOpr = "Suma";
                        if (i <= tamañoExp && i >= 1) {
                            validOperador(token, i, posAdelante);
                            setOpr(token, posAtras, posAdelante, nombreOpr);
                            setSuma(i, token);
                        } else if (i == 0) {
                            validInicio(nombreOpr);
                            setResta(i, token);
                        } else {
                            validTerminar(posAtras, posAdelante, token, expresion, nombreOpr);
                            setSuma(i, token);
                        }
                        break;
                    case '-':
                        nombreOpr = "Resta";
                        if (i <= tamañoExp && i >= 1) {
                            validOperador(token, i, posAdelante);
                            setOpr(token, posAtras, posAdelante, nombreOpr);
                            setResta(i, token);
                        } else if (i == 0) {
                            validInicio(nombreOpr);
                            setResta(i, token);
                        } else {
                            validTerminar(posAtras, posAdelante, token, expresion, nombreOpr);
                            setResta(i, token);
                        }
                        break;
                    case '^':
                        nombreOpr = "Potencia";
                        if (i <= tamañoExp && i >= 1) {
                            validOperador(token, i, posAdelante);
                            setOpr(token, posAtras, posAdelante, nombreOpr);
                            setPoten(i, token);
                        } else if (i == 0) {
                            validInicio(nombreOpr);
                            setResta(i, token);
                        } else {
                            validTerminar(posAtras, posAdelante, token, expresion, nombreOpr);
                            setPoten(i, token);
                        }
                        break;
                    case '*':
                        nombreOpr = "Multiplicación";
                        if (i <= tamañoExp && i >= 1) {
                            validOperador(token, i, posAdelante);
                            setOpr(token, posAtras, posAdelante, nombreOpr);
                            setMulti(i, token);
                        } else if (i == 0) {
                            validInicio(nombreOpr);
                            setResta(i, token);
                        } else {
                            validTerminar(posAtras, posAdelante, token, expresion, nombreOpr);
                            setMulti(i, token);
                        }
                        break;
                    case '/':
                        nombreOpr = "División";
                        if (i <= tamañoExp && i >= 1) {
                            validOperador(token, i, posAdelante);
                            setOpr(token, posAtras, posAdelante, nombreOpr);
                            setDiv(i, token);
                        } else if (i == 0) {
                            validInicio(nombreOpr);
                            setResta(i, token);
                        } else {
                            validTerminar(posAtras, posAdelante, token, expresion, nombreOpr);
                            setDiv(i, token);
                        }
                        break;
                }
            } else if (Character.isLetter(token[i])) {
                JOptionPane.showMessageDialog(null, "Letra  ' " + token[i] + " '  no permitida", "Error", JOptionPane.ERROR_MESSAGE);
                setValidError();
            } else if (token[i] != '+' || token[i] != '-'
                    || token[i] != '^' || token[i] != '*' || token[i] != '/') {
                switch (token[i]) {
                    case '[':
                    case ']':
                        JOptionPane.showMessageDialog(null, "No se permiten corchetes", "Error", JOptionPane.ERROR_MESSAGE);
                        setValidError();
                        break;
                    case '{':
                    case '}':
                        JOptionPane.showMessageDialog(null, "No se permiten llaves", "Error", JOptionPane.ERROR_MESSAGE);
                        setValidError();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Signo  ' " + token[i] + " '  no"
                                + " reconocido", "Error", JOptionPane.ERROR_MESSAGE);
                        setValidError();
                        break;
                }
            }
        }
        return valores.pop();
    }

    public static boolean importancia(char op1, char op2) {
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
                    setValidError();
                }
                return a / b;
        }
        return 0;
    }

    public void validOperador(char[] token, int i, int posAdelante) {
        if (token[posAdelante] == '+' || token[posAdelante] == '-' || token[posAdelante] == '*'
                || token[posAdelante] == '/' || token[posAdelante] == '^') {
            JOptionPane.showMessageDialog(null, "Es necesario un número entre un Operador  ' " + token[i]
                    + " '  y un Operador  ' " + token[posAdelante] + " '", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void validTerminar(int posAtras, int posAdelante, char[] token, String expresion, String nombreOpr) {
        if (posAtras <= expresion.length()) {
            JOptionPane.showMessageDialog(null, "La expresión no puede terminar con "
                    + "un Operador " + nombreOpr, "Error", JOptionPane.ERROR_MESSAGE);
            setValidError();
        }
    }

    public void validInicio(String nombreOpr) {
        JOptionPane.showMessageDialog(null, "La expresión no puede iniciar con "
                + "un Operador " + nombreOpr, "Error", JOptionPane.ERROR_MESSAGE);
        setValidError();
    }

    public Object getNum() {
        return num;
    }

    public Object getIde() {
        return ide;
    }

    public int getValidError() {
        return validError;
    }

    public int getConbalanA() {
        return conbalanA;
    }

    public int getConbalanC() {
        return conbalanC;
    }

    public void setValidError() {
        validError = 1;
    }

    public void setValidEspacio() {
        JOptionPane.showMessageDialog(null, "Ingrese expresión separada "
                + "por espacios", "Error", JOptionPane.ERROR_MESSAGE);
        setValidError();
    }

    public void setNumEntero(int i, StringBuffer sbuff) {
        num.add(sbuff);
        ide.add("Número Entero");
        valores.push(Float.parseFloat(sbuff.toString()));
    }

    public void setNumDecimal(int i, StringBuffer sbuff) {
        num.add(sbuff);
        ide.add("Número Decimal");
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

    public void setPoten(int i, char[] token) {
        operadores.push(token[i]);
        num.add(token[i]);
        ide.add("Operador Potencia");
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

    public void setOpr(char[] token, int posAtras, int posAdelante, String nombreOpr) {
        switch (token[posAdelante]) {
            case ')':
                JOptionPane.showMessageDialog(null, "Debe existir un Número entre un Operador " + nombreOpr
                        + " y un Paréntesis de Cierre", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
        switch (token[posAtras]) {
            case '(':
                JOptionPane.showMessageDialog(null, "Debe existir un Número entre un Paréntesis de Apertura "
                        + " y un Operador " + nombreOpr, "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}
