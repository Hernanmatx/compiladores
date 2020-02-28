package Controlador;

import Vista.JDCompilador;
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
            JDCompilador com = new JDCompilador(null, true);
            com.setLocationRelativeTo(null);
            JCCompilador nuevo = new JCCompilador(com);
            com.setVisible(true);
        }
    }
}
