/*
 * Pallo.java
 *
 * Luotu 30. syyskuuta 2006, 13:15
 */

package parkanoid;

/**
 * Luokka pomppiville palloille
 * @author Toni Paloniemi, Severi Vidn‰s
 * @version 1.0
 */
import java.awt.*;
public class Pallo extends Objekti{
    private double x;
    private double y;
    private double liikex;
    private double liikey;
    private boolean liimautunut;
    private int liimauskohta;
    private int sade;
    private double vauhti;
    public static final int PALLOKORKEUS = 10;
    public static final int PALLOLEVEYS = 10;
     /**
     * Pallon konstruktori.
     * @param x Luotavan pallon x-akselin koordinaatti.
     * @param y Luotavan pallon y-akselin koordinaatti.
     * @param suunta Luotavan pallon suunta.
     * @param vauhti Luotavan pallon vauhti <code>int</code>:n‰.
     */
    public Pallo(int x,int y, Image kuva, double suunta, double vauhti) {
        super(x,y,kuva);
        this.x = x;
        this.y = y;
        liimautunut = false;
        super.asetaKorkeus(PALLOKORKEUS);
        super.asetaLeveys(PALLOLEVEYS);
        this.sade = super.palautaLeveys()/2;
        this.liikex = (Math.cos(Math.toRadians(suunta))*vauhti);
        this.liikey = (Math.sin(Math.toRadians(suunta))*vauhti);
    }
    /**
     * Metodilla asetetaan pallon x ja y koordinaatti.
     * @param x Pallon x koordinaatti <code>int</code>:n‰.
     * @param y Pallon y koordinaatti <code>int</code>:n‰.
     */
    public void aseta(int x, int y){
        this.x = x;
        this.y = y;
    }
    /**
     * Metodi palauttaa tiedon pallon liimaamisesta mailaan.
     * @return <code>true</code> tai <code>false</code>
     */
    public boolean palautaLiimaus(){
        return this.liimautunut;
    }
    /**
     * Metodi palauttaa kohdan johon pallo on liimattuna.
     * @return Liimauskohta <code>int</code>:n‰.
     */
    public int palautaLiimauskohta(){
        return this.liimauskohta;
    }
    /**
     * Metodi asettaa liimauksen p‰‰lle tai pois p‰‰lt‰.
     * @param liimautunut Asetettava tila <code>boolean</code> muodossa.
     */
    public void asetaLiimaus(boolean liimautunut){
        this.liimautunut = liimautunut;
    }
    /**
     * Metodi asettaa liimauskohdan pallolle.
     * @param liimauskohta Liimauskohdan x-koordinaatti <code>int</code>:n‰.
     */
    public void asetaLiimauskohta(int liimauskohta){
        this.liimauskohta = liimauskohta;
    }
    /**
     * Metodi pallon piirt‰miseksi
     * @param g Graphics luokan olio jota tarvitaan piirt‰miseen.
     */
    public void piirra(Graphics g){
        g.drawImage(super.palautaKuva(),this.palautaX(), this.palautaY(), null);        
    }
    /**
     * Metodi pallon liikuttamiseksi. Muutetaan pallon x ja y koordinaattia liikex:n ja liikey:n verran.
     */
    public void liiku(){
        this.x += liikex;
        this.y += liikey;       
    }
    /**
     * Metodilla pidet‰‰n liimautunutta palloa mailan mukana.
     * @param mailax Mailan x-akselin koordinaatti.
     */
    public void liimaLiiku(int mailax){
        this.aseta(mailax+this.palautaLiimauskohta(),this.palautaY());
    }
    /**
     * Metodilla siirret‰‰n palloa takaisin sen tulosuuntaan liikex:n ja liikey:n verran.
     */
    public void peruuta(){
        this.x -= liikex;
        this.y -= liikey;
    }
    /**
     * Metodi pallon x-akselin koordinaatin palauttamiseksi.
     * @return Pallon x koordinaatti <code>int</code>:n‰.
     */
    public int palautaX(){
        return (int)this.x;
        
    }
    /**
     * Metodi pallon y-akselin koordinaatin palauttamiseksi.
     * @return Pallon y koordinaatti <code>int</code>:n‰.
     */
    public int palautaY(){
        return (int)this.y;
    }
    /**
     * Metodi pallon s‰teen palauttamiseksi.
     * @return Pallon s‰de <code>int</code>:n‰.
     */
    public int palautasade(){
        return this.sade;
    }
    /**
     * Asettaa pallolle suunnan ja vauhdin
     * @param suunta Pallon menosuunta <code>double</code>:na.
     * @param vauhti Pallon vauhti <code>double</code>:na.
     */
    public void asetaSuunta(double suunta, double vauhti){
        this.liikex = (Math.cos(Math.toRadians(suunta))*vauhti);
        this.liikey = (Math.sin(Math.toRadians(suunta))*vauhti);
        
    }
    /**
     * Metodilla lasketaan pallon x- ja y-vauhdista pallolle oikea liikesuunta eli kulma.
     * @param dx Pallon x-vauhti
     * @param dy Pallon y-vauhti
     * @return Palauttaa lasketun kulman <code>int</code>:n‰.
     */
    public int laskeKulma(double dx, double dy){
        double kulma;
        kulma = Math.atan2(-dx, dy);
        kulma = Math.toDegrees(kulma)+90;
        if(kulma < 0) kulma += 360;
        return (int)Math.round(kulma);
    }
    /**
     * Metodilla k‰‰nnet‰‰n pallon x-akselin menosuunta ymp‰ri.
     */
    public void kaannaX(){
        this.liikex *= -1;
    }
    /**
     * Metodilla k‰‰nnet‰‰n pallon y-akselin menosuunta ymp‰ri.
     */
    public void kaannaY(){
        this.liikey *= -1;
    }
    /**
     * Metodilla asetetaan pallon vauhti x-akselilla
     * @param dx Asetettava liike <int>code</code>:n‰.
     */
    public void asetaliikeX(int dx){
        this.liikex = dx;
    }
    /**
     * Metodilla asetetaan pallolle vauhti jonka j‰lkeen se asettaa oman menosuuntansa uudella vauhdilla.
     * @param vauhti Asetettava vauhti.
     */
    public void asetaVauhti (double vauhti){
        this.vauhti = vauhti;
        asetaSuunta(laskeKulma(liikex, liikey),vauhti);
    }
    /**
     * Metodi palauttaa pallon vauhdin.
     * @return Palauttaa vauhdin <code>double</code>:na.
     */
    public double palautaVauhti (){
        return this.vauhti;
    }
}
