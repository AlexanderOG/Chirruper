/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.Serializable;

/**
 *
 * @author Alex
 */
public class Chirrup implements Serializable
{
    private String emisor; 
    private String fechaYHora;
    private String chirrup;

    public Chirrup(String emisor, String fechaYHora, String chirrup) 
    {
        this.emisor = emisor;
        this.fechaYHora = fechaYHora;
        this.chirrup = chirrup;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(String fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public String getChirrup() {
        return chirrup;
    }

    public void setChirrup(String chirrup) {
        this.chirrup = chirrup;
    }
    
}
