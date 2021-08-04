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
import javax.validation.constraints.Size;

/**
 *
 * @author Josarta
 */
@Entity
@Table(name = "tbl_proveedor")
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p")})
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pro_prevedorid")
    private Integer proPrevedorid;
    @Size(max = 255)
    @Column(name = "pro_direccion")
    private String proDireccion;
    @Size(max = 30)
    @Column(name = "pro_telefono")
    private String proTelefono;
    @Size(max = 45)
    @Column(name = "pro_ext")
    private String proExt;
    @Size(max = 255)
    @Column(name = "pro_movil")
    private String proMovil;
    @Size(max = 45)
    @Column(name = "pro_contacto")
    private String proContacto;
    @JoinColumn(name = "fk_usuarioid", referencedColumnName = "usu_usuarioid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario fkUsuarioid;

    public Proveedor() {
    }

    public Proveedor(Integer proPrevedorid) {
        this.proPrevedorid = proPrevedorid;
    }

    public Integer getProPrevedorid() {
        return proPrevedorid;
    }

    public void setProPrevedorid(Integer proPrevedorid) {
        this.proPrevedorid = proPrevedorid;
    }

    public String getProDireccion() {
        return proDireccion;
    }

    public void setProDireccion(String proDireccion) {
        this.proDireccion = proDireccion;
    }

    public String getProTelefono() {
        return proTelefono;
    }

    public void setProTelefono(String proTelefono) {
        this.proTelefono = proTelefono;
    }

    public String getProExt() {
        return proExt;
    }

    public void setProExt(String proExt) {
        this.proExt = proExt;
    }

    public String getProMovil() {
        return proMovil;
    }

    public void setProMovil(String proMovil) {
        this.proMovil = proMovil;
    }

    public String getProContacto() {
        return proContacto;
    }

    public void setProContacto(String proContacto) {
        this.proContacto = proContacto;
    }

    public Usuario getFkUsuarioid() {
        return fkUsuarioid;
    }

    public void setFkUsuarioid(Usuario fkUsuarioid) {
        this.fkUsuarioid = fkUsuarioid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proPrevedorid != null ? proPrevedorid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.proPrevedorid == null && other.proPrevedorid != null) || (this.proPrevedorid != null && !this.proPrevedorid.equals(other.proPrevedorid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.sena.paraya.entity.Proveedor[ proPrevedorid=" + proPrevedorid + " ]";
    }
    
}
