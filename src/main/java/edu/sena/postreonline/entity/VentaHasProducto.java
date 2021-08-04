/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.entity;

import java.io.Serializable;
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
import javax.persistence.Table;

/**
 *
 * @author Josarta
 */
@Entity
@Table(name = "tbl_venta_has_tbl_producto")
@NamedQueries({
    @NamedQuery(name = "VentaHasProducto.findAll", query = "SELECT v FROM VentaHasProducto v")})
public class VentaHasProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "vep_cantidad")
    private Integer vepCantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "vep_valorunidad")
    private Float vepValorunidad;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vep_ventaproductoid")
    private Integer vepVentaproductoid;
    @JoinColumn(name = "fk_producto", referencedColumnName = "pro_productoid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Producto fkProducto;
    @JoinColumn(name = "fk_ventaid", referencedColumnName = "ven_ventaid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Venta fkVentaid;

    public VentaHasProducto() {
    }

    public VentaHasProducto(Integer vepVentaproductoid) {
        this.vepVentaproductoid = vepVentaproductoid;
    }

    public Integer getVepCantidad() {
        return vepCantidad;
    }

    public void setVepCantidad(Integer vepCantidad) {
        this.vepCantidad = vepCantidad;
    }

    public Float getVepValorunidad() {
        return vepValorunidad;
    }

    public void setVepValorunidad(Float vepValorunidad) {
        this.vepValorunidad = vepValorunidad;
    }

    public Integer getVepVentaproductoid() {
        return vepVentaproductoid;
    }

    public void setVepVentaproductoid(Integer vepVentaproductoid) {
        this.vepVentaproductoid = vepVentaproductoid;
    }

    public Producto getFkProducto() {
        return fkProducto;
    }

    public void setFkProducto(Producto fkProducto) {
        this.fkProducto = fkProducto;
    }

    public Venta getFkVentaid() {
        return fkVentaid;
    }

    public void setFkVentaid(Venta fkVentaid) {
        this.fkVentaid = fkVentaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vepVentaproductoid != null ? vepVentaproductoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentaHasProducto)) {
            return false;
        }
        VentaHasProducto other = (VentaHasProducto) object;
        if ((this.vepVentaproductoid == null && other.vepVentaproductoid != null) || (this.vepVentaproductoid != null && !this.vepVentaproductoid.equals(other.vepVentaproductoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.sena.paraya.entity.VentaHasProducto[ vepVentaproductoid=" + vepVentaproductoid + " ]";
    }
    
}
