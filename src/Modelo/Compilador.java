/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Scanner;
import java.util.Stack;
/**
 *
 * @author frans
 */
public class Compilador {
    public static int evaluate(String expression) {
        char[] tokens = expression.toCharArray();
        System.out.println(tokens);

        // Stack for numbers: 'values' 
        Stack<Integer> values = new Stack<Integer>();
        // Stack for Operators: 'ops' 
        Stack<Character> ops = new Stack<Character>();
        for (int i = 0; i < tokens.length; i++) {
            // Current token is a whitespace, skip it 
            if (tokens[i] == ' ') {
                continue;
            }
            // Current token is a number, push it to stack for numbers 
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number 
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') {
                    sbuf.append(tokens[i++]);
                }
                System.out.println(sbuf + " = Numero");
                values.push(Integer.parseInt(sbuf.toString()));
            } // Current token is an opening brace, push it to 'ops' 
            else if (tokens[i] == '(') {
                System.out.println(tokens[i] + " = Parentesis Apertura");
                ops.push(tokens[i]);
            } // Closing brace encountered, solve entire brace 
            else if (tokens[i] == ')') {
                while (ops.peek() != '(') {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                System.out.println(tokens[i] + " = Parentesis Cierre");
                ops.pop();
            } // Current token is an operator. 
            else if (tokens[i] == '+' || tokens[i] == '-'
                    || tokens[i] == '^'|| tokens[i] == '*' || tokens[i] == '/') {
                // While top of 'ops' has same or greater precedence to current 
                // token, which is an operator. Apply operator on top of 'ops' 
                // to top two elements in values stack 
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }

                // Push current token to 'ops'. 
                ops.push(tokens[i]);
                System.out.println(tokens[i] + " = Operador Numerico");
            }
        }

        // Entire expression has been parsed at this point, apply remaining 
        // ops to remaining values
        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        // Top of 'values' contains result, return it 
        return values.pop();
    }

    // Returns true if 'op2' has higher or same precedence as 'op1', 
    // otherwise returns false. 
    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '^' ||op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        } else {
            return true;
        }
    }

    // A utility method to apply an operator 'op' on operands 'a' 
    // and 'b'. Return the result. 
    public static int applyOp(char op, int b, int a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
            case '^': 
                double s = Math.pow(a, b);
                return (int) s;
        }
        return 0;
    }
    
    
    
}
