package Controlador;

import Vista.JDAnalizador;
import Vista.JDCreditos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JCCreditos implements ActionListener {

    JDCreditos c = new JDCreditos(null, true);

    public JCCreditos(JDCreditos creditos) {
        this.c = creditos;
        c.btnCerrar.addActionListener(this);
    }

    public void actionPerformed(ActionEvent a) {
        if (c.btnCerrar == a.getSource()) {
            c.dispose();
            JDAnalizador com = new JDAnalizador(null, true);
            com.setLocationRelativeTo(null);
            JCAnalizador nuevo = new JCAnalizador(com);
            com.setVisible(true);
        }
    }
}
