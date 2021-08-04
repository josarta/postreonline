/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Josarta
 */
@Entity
@Table(name = "tbl_foto")
@NamedQueries({
    @NamedQuery(name = "Foto.findAll", query = "SELECT f FROM Foto f")})
public class Foto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fot_fotoid")
    private Integer fotFotoid;
    @Size(max = 255)
    @Column(name = "fot_ruta")
    private String fotRuta;
    @Size(max = 255)
    @Column(name = "fot_descripcion")
    private String fotDescripcion;
    @JoinTable(name = "tbl_producto_has_tbl_foto", joinColumns = {
        @JoinColumn(name = "fk_fotoid", referencedColumnName = "fot_fotoid")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_productoid", referencedColumnName = "pro_productoid")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Producto> productoCollection;

    public Foto() {
    }

    public Foto(Integer fotFotoid) {
        this.fotFotoid = fotFotoid;
    }

    public Integer getFotFotoid() {
        return fotFotoid;
    }

    public void setFotFotoid(Integer fotFotoid) {
        this.fotFotoid = fotFotoid;
    }

    public String getFotRuta() {
        return fotRuta;
    }

    public void setFotRuta(String fotRuta) {
        this.fotRuta = fotRuta;
    }

    public String getFotDescripcion() {
        return fotDescripcion;
    }

    public void setFotDescripcion(String fotDescripcion) {
        this.fotDescripcion = fotDescripcion;
    }

    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fotFotoid != null ? fotFotoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Foto)) {
            return false;
        }
        Foto other = (Foto) object;
        if ((this.fotFotoid == null && other.fotFotoid != null) || (this.fotFotoid != null && !this.fotFotoid.equals(other.fotFotoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.sena.paraya.entity.Foto[ fotFotoid=" + fotFotoid + " ]";
    }
    
}
