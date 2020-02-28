package Modelo;

import Controlador.JCCompilador;
import Vista.JDCompilador;
import Vista.JFPrincipal;

public class Principal {

    public static void main(String[] args) {
        JFPrincipal principal = new JFPrincipal();
        principal.setExtendedState(JFPrincipal.MAXIMIZED_BOTH);
        principal.setVisible(true);

        JDCompilador c = new JDCompilador(principal, true);
        c.setLocationRelativeTo(null);
        JCCompilador nuevo = new JCCompilador(c);
        c.setVisible(true);
    }

}
