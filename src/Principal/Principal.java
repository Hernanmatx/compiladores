package Principal;

import Vista.JDCompilador;
import Vista.JFPrincipal;

public class Principal {
    
    public static void main(String[] args) {
        JFPrincipal principal = new JFPrincipal();
        principal.setExtendedState(JFPrincipal.MAXIMIZED_BOTH);
        principal.setVisible(true);
        
        JDCompilador c = new JDCompilador(principal, true);
        c.setLocationRelativeTo(null);
        //ompilador nc = new Compilador(c);
        c.setVisible(true);
    }
    
}
