/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Josarta
 */
@Entity
@Table(name = "tbl_venta")
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v")})
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ven_ventaid")
    private Integer venVentaid;
    @Size(max = 255)
    @Column(name = "ven_direccioncomprador")
    private String venDireccioncomprador;
    @Column(name = "ven_telefonocomprador")
    private Integer venTelefonocomprador;
    @Size(max = 45)
    @Column(name = "ven_ciudadcomprador")
    private String venCiudadcomprador;
    @Column(name = "ven_fecha")
    @Temporal(TemporalType.DATE)
    private Date venFecha;
    @Column(name = "ven_hora")
    @Temporal(TemporalType.TIME)
    private Date venHora;
    @JoinColumn(name = "fk_usucomprador", referencedColumnName = "usu_usuarioid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario fkUsucomprador;
    @JoinColumn(name = "fk_usuvendedor", referencedColumnName = "usu_usuarioid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario fkUsuvendedor;
    @OneToMany(mappedBy = "fkVentaid", fetch = FetchType.LAZY)
    private Collection<VentaHasProducto> ventaHasProductoCollection;

    public Venta() {
    }

    public Venta(Integer venVentaid) {
        this.venVentaid = venVentaid;
    }

    public Integer getVenVentaid() {
        return venVentaid;
    }

    public void setVenVentaid(Integer venVentaid) {
        this.venVentaid = venVentaid;
    }

    public String getVenDireccioncomprador() {
        return venDireccioncomprador;
    }

    public void setVenDireccioncomprador(String venDireccioncomprador) {
        this.venDireccioncomprador = venDireccioncomprador;
    }

    public Integer getVenTelefonocomprador() {
        return venTelefonocomprador;
    }

    public void setVenTelefonocomprador(Integer venTelefonocomprador) {
        this.venTelefonocomprador = venTelefonocomprador;
    }

    public String getVenCiudadcomprador() {
        return venCiudadcomprador;
    }

    public void setVenCiudadcomprador(String venCiudadcomprador) {
        this.venCiudadcomprador = venCiudadcomprador;
    }

    public Date getVenFecha() {
        return venFecha;
    }

    public void setVenFecha(Date venFecha) {
        this.venFecha = venFecha;
    }

    public Date getVenHora() {
        return venHora;
    }

    public void setVenHora(Date venHora) {
        this.venHora = venHora;
    }

    public Usuario getFkUsucomprador() {
        return fkUsucomprador;
    }

    public void setFkUsucomprador(Usuario fkUsucomprador) {
        this.fkUsucomprador = fkUsucomprador;
    }

    public Usuario getFkUsuvendedor() {
        return fkUsuvendedor;
    }

    public void setFkUsuvendedor(Usuario fkUsuvendedor) {
        this.fkUsuvendedor = fkUsuvendedor;
    }

    public Collection<VentaHasProducto> getVentaHasProductoCollection() {
        return ventaHasProductoCollection;
    }

    public void setVentaHasProductoCollection(Collection<VentaHasProducto> ventaHasProductoCollection) {
        this.ventaHasProductoCollection = ventaHasProductoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (venVentaid != null ? venVentaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.venVentaid == null && other.venVentaid != null) || (this.venVentaid != null && !this.venVentaid.equals(other.venVentaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.sena.paraya.entity.Venta[ venVentaid=" + venVentaid + " ]";
    }
    
}
