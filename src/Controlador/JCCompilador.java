package Controlador;

import Modelo.Compilador;
import Vista.JDCompilador;
import Vista.JDCreditos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.table.DefaultTableModel;

public class JCCompilador implements ActionListener, KeyListener {

    JDCompilador c = new JDCompilador(null, true);
    Compilador comp = new Compilador(c);
    DefaultTableModel modelo = new DefaultTableModel();

    public JCCompilador(JDCompilador compilador) {
        this.c = compilador;
        this.c.txtIngreso.addKeyListener(this);
        this.c.btnCreditos.addActionListener(this);
        this.c.btnSalir.addActionListener(this);
        this.c.jtfRespuesta.addActionListener(this);
    }

    public void actionPerformed(ActionEvent a) {
        if (c.btnCreditos == a.getSource()) {
            c.dispose();
            JDCreditos cr = new JDCreditos(null, true);
            cr.setLocationRelativeTo(c);
            JCCreditos creditos = new JCCreditos(cr);
            cr.setVisible(true);
        } else if (c.btnSalir == a.getSource()) {
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String operacion;
            operacion = this.c.txtIngreso.getText();
            this.comp.respu(operacion);
        }
    }

}
