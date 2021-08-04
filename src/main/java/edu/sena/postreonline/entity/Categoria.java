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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Josarta
 */
@Entity
@Table(name = "tbl_categoria")
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c")})
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cat_categoriaid")
    private Integer catCategoriaid;
    @Size(max = 45)
    @Column(name = "cat_nombre")
    private String catNombre;
    @Size(max = 255)
    @Column(name = "cat_descripcion")
    private String catDescripcion;
    @OneToMany(mappedBy = "fkCategoria", fetch = FetchType.LAZY)
    private Collection<Producto> productoCollection;

    public Categoria() {
    }

    public Categoria(Integer catCategoriaid) {
        this.catCategoriaid = catCategoriaid;
    }

    public Integer getCatCategoriaid() {
        return catCategoriaid;
    }

    public void setCatCategoriaid(Integer catCategoriaid) {
        this.catCategoriaid = catCategoriaid;
    }

    public String getCatNombre() {
        return catNombre;
    }

    public void setCatNombre(String catNombre) {
        this.catNombre = catNombre;
    }

    public String getCatDescripcion() {
        return catDescripcion;
    }

    public void setCatDescripcion(String catDescripcion) {
        this.catDescripcion = catDescripcion;
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
        hash += (catCategoriaid != null ? catCategoriaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.catCategoriaid == null && other.catCategoriaid != null) || (this.catCategoriaid != null && !this.catCategoriaid.equals(other.catCategoriaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.sena.paraya.entity.Categoria[ catCategoriaid=" + catCategoriaid + " ]";
    }
    
}
