package Controlador;

import Modelo.Compilador;
import Vista.JDCompilador;
import Vista.JDCreditos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class JCCompilador implements ActionListener, KeyListener {

    JDCompilador c = new JDCompilador(null, true);
    Compilador comp = new Compilador();
    DefaultTableModel modelo = new DefaultTableModel();
    private int contador = 0, cont = 1;
    
    public JCCompilador(JDCompilador compilador) {
        this.c = compilador;
        this.c.txtIngreso.addKeyListener(this);
        this.c.btnCreditos.addActionListener(this);
        this.c.btnSalir.addActionListener(this);
        this.c.jtfRespuesta.addActionListener(this);
        this.c.jtfRespuesta.setEditable(false);
        this.c.jTable1.setEnabled(false);
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
            String operacion, opr;
            operacion = this.c.txtIngreso.getText();
            opr = this.comp.respu(operacion);
            this.c.jtfRespuesta.setText(opr);
            datos();
        }
    }

    public void datos() {
        cont = 1;
        this.modelo.setRowCount(0);
        ArrayList<Object> num = new ArrayList<>();
        num = (ArrayList<Object>) this.comp.getNum();
        ArrayList<Object> ide = new ArrayList<>();
        ide = (ArrayList<Object>) this.comp.getIde();
        Object[] numero = new Object[num.size()];
        Object[] identi = new Object[ide.size()];
        for (int i = 0; i < num.size(); i++) {
            numero[i] = num.get(i);
        }
        for (int i = 0; i < ide.size(); i++) {
            identi[i] = ide.get(i);
        }
        modelo.setColumnIdentifiers(new Object[]{"Número", "Token", "Identificador"});
        while(contador < num.size()) {
            modelo.addRow(new Object[]{cont++, numero[contador], identi[contador]});
            contador++;
        }
        this.c.jTable1.setModel(modelo);
    }
}
