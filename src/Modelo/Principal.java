package Modelo;

import Controlador.JCAnalizador;
import Vista.JDAnalizador;
import Vista.JFPrincipal;

public class Principal {

    public static void main(String[] args) {
        JFPrincipal principal = new JFPrincipal();
        principal.setExtendedState(JFPrincipal.MAXIMIZED_BOTH);
        principal.setVisible(true);

        JDAnalizador c = new JDAnalizador(principal, true);
        c.setLocationRelativeTo(null);
        JCAnalizador nuevo = new JCAnalizador(c);
        c.setVisible(true);
    }

}
