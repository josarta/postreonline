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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "tbl_producto")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pro_productoid")
    private Integer proProductoid;
    @Size(max = 255)
    @Column(name = "pro_nombre")
    private String proNombre;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "pro_descripcion")
    private String proDescripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pro_valor")
    private Double proValor;
    @Column(name = "pro_cantidaddisponiblel")
    private Integer proCantidaddisponiblel;
    @ManyToMany(mappedBy = "productoCollection", fetch = FetchType.LAZY)
    private Collection<Foto> fotoCollection;
    @JoinColumn(name = "fk_categoria", referencedColumnName = "cat_categoriaid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria fkCategoria;
    @OneToMany(mappedBy = "fkProducto", fetch = FetchType.LAZY)
    private Collection<VentaHasProducto> ventaHasProductoCollection;

    public Producto() {
    }

    public Producto(Integer proProductoid) {
        this.proProductoid = proProductoid;
    }

    public Integer getProProductoid() {
        return proProductoid;
    }

    public void setProProductoid(Integer proProductoid) {
        this.proProductoid = proProductoid;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public String getProDescripcion() {
        return proDescripcion;
    }

    public void setProDescripcion(String proDescripcion) {
        this.proDescripcion = proDescripcion;
    }

    public Double getProValor() {
        return proValor;
    }

    public void setProValor(Double proValor) {
        this.proValor = proValor;
    }

    public Integer getProCantidaddisponiblel() {
        return proCantidaddisponiblel;
    }

    public void setProCantidaddisponiblel(Integer proCantidaddisponiblel) {
        this.proCantidaddisponiblel = proCantidaddisponiblel;
    }

    public Collection<Foto> getFotoCollection() {
        return fotoCollection;
    }

    public void setFotoCollection(Collection<Foto> fotoCollection) {
        this.fotoCollection = fotoCollection;
    }

    public Categoria getFkCategoria() {
        return fkCategoria;
    }

    public void setFkCategoria(Categoria fkCategoria) {
        this.fkCategoria = fkCategoria;
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
        hash += (proProductoid != null ? proProductoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.proProductoid == null && other.proProductoid != null) || (this.proProductoid != null && !this.proProductoid.equals(other.proProductoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.sena.paraya.entity.Producto[ proProductoid=" + proProductoid + " ]";
    }
    
}
