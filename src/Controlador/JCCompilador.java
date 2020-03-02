package Controlador;

import Modelo.Compilador;
import Vista.JDCompilador;
import Vista.JDCreditos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
        this.c.btnArchivo.addActionListener(this);
        this.c.btnAnalizar.addActionListener(this);
        this.c.btnPDF.addActionListener(this);
        this.c.btnBorrar.addActionListener(this);
        this.c.btnSalir.addActionListener(this);
        this.c.jtfRespuesta.addActionListener(this);
        this.c.jtfRespuesta.setEditable(false);
        this.c.jTable1.setEnabled(false);
        
        ocultar();
    }

    public void actionPerformed(ActionEvent a) {
        if (c.btnCreditos == a.getSource()) {
            c.dispose();
            JDCreditos cr = new JDCreditos(null, true);
            cr.setLocationRelativeTo(c);
            JCCreditos creditos = new JCCreditos(cr);
            cr.setVisible(true);
        } else if (c.btnArchivo == a.getSource()) {
            explador();
        } else if (c.btnPDF == a.getSource()) {
            imprimir();
        } else if (c.btnAnalizar == a.getSource()) {
            resultado();
        } else if (c.btnBorrar == a.getSource()) {
            c.jtfRespuesta.setText("");
            modelo.setRowCount(0);
            ocultar();
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
            resultado();
        }
    }
    
    public void ocultar(){
        c.btnBorrar.setVisible(false);
        c.jlResul.setVisible(false);
        c.jtfRespuesta.setVisible(false);
        c.btnBorrar.setVisible(false);
        c.btnPDF.setEnabled(false);
    }
    
    public void mostrar(){
        c.btnBorrar.setVisible(true);
        c.jlResul.setVisible(true);
        c.jtfRespuesta.setVisible(true);
        c.btnBorrar.setVisible(true);
        c.btnPDF.setEnabled(true);
    }
    
    public void resultado(){
        String operacion, opr;
        operacion = this.c.txtIngreso.getText();
        opr = this.comp.respu(operacion);
        this.c.jtfRespuesta.setText(opr);
        datos();
        mostrar();
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
    
    public void imprimir(){
        if (c.jtfRespuesta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Es necesario resolver la operación primero", "Informacion", JOptionPane.INFORMATION_MESSAGE);
        } else {
            MessageFormat header1 = new MessageFormat("Operación         Resultado: " + c.jtfRespuesta.getText() + "");
            
            MessageFormat footer = new MessageFormat("Página{0,number,integer}");
            try {
                c.jTable1.print(JTable.PrintMode.NORMAL, header1, footer);
            } catch (Exception e) {
                System.err.format("No se puede Generar", e.getMessage());
            }
        }
    }
    
    public void explador(){
        JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File archivo = new File(chooser.getSelectedFile().getAbsolutePath());
        
        try {
            String ST = new String(Files.readAllBytes(archivo.toPath()));
            c.txtIngreso.setText(ST);
        }
        catch(FileNotFoundException ex){
            Logger.getLogger(JDCompilador.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex){
            Logger.getLogger(JDCompilador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
