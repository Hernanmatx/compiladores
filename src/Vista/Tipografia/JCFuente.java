package Vista.Tipografia;

import java.awt.Font;
import java.io.InputStream;

/**
 *
 * @author fransuany
 */
public class JCFuente {

    private Font font = null;
    public String ki = "Asia.otf";
    public String ne = "NixieOne.ttf";
    public String so = "Sophia.ttf";
    public String su = "Sunday.ttf";
    public String co = "Compass.ttf";
    
    public Font fuente(String fontName, int estilo, float tamanio) {
        try {
            InputStream is = getClass().getResourceAsStream(fontName);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception ex) {
            System.err.println(fontName + " No se cargo la fuente");
            font = new Font("Arial", Font.PLAIN, 14);
        }
        Font tfont = font.deriveFont(estilo, tamanio);
        return tfont;
    }
}
