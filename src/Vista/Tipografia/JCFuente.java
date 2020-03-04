package Vista.Tipografia;

import java.awt.Font;
import java.io.InputStream;

/**
 *
 * @author fransuany
 */
public class JCFuente {

    private Font font = null;
    public String as = "Asia.otf";
    public String be = "Bergie.otf";
    public String ce = "Celine.ttf";
    public String co = "Compass.ttf";
    public String cor = "Core.otf";
    public String ge = "Geometos.ttf";
    public String ho = "Hoam.otf";
    public String mat = "Mati.otf";
    public String ne = "NixieOne.ttf";
    public String op = "OpenMine.ttf";
    public String pr = "Providence.ttf";
    public String re = "Rekobip.ttf";
    public String so = "Sophia.ttf";
    public String sp = "Spotnik.ttf";
    public String sr = "SrabiScript.ttf";
    public String su = "Sunday.ttf";
    public String tr = "Traveling.ttf";
    public String ki = "King.ttf";
    
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
