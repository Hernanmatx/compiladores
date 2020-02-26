package Controlador;

import Vista.JDCompilador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

/**
 *
 * @author frans
 */
public class JCCompilador implements ActionListener, KeyListener {
    JDCompilador c = new JDCompilador(null, true);
    
    public JCCompilador(JDCompilador compilador){
        this.c = compilador;
        this.c.txtIngreso.addKeyListener(this);
        this.c.btnSalir.addActionListener(this);
    }

    
    public void actionPerformed(ActionEvent a) {
        if (c.btnSalir == a.getSource()) {
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
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
        }
    }
    
}
