/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "tbl_usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usu_usuarioid")
    private Integer usuUsuarioid;
    @Size(max = 45)
    @Column(name = "usu_tipodocumento")
    private String usuTipodocumento;
    @Column(name = "usu_numeroducumento")
    private BigInteger usuNumeroducumento;
    @Size(max = 45)
    @Column(name = "usu_nombres")
    private String usuNombres;
    @Size(max = 45)
    @Column(name = "usu_apellidos")
    private String usuApellidos;
    @Size(max = 60)
    @Column(name = "usu_correo")
    private String usuCorreo;
    @Size(max = 8)
    @Column(name = "usu_clave")
    private String usuClave;
    @Column(name = "usu_estado")
    private Short usuEstado;
    @Size(max = 255)
    @Column(name = "usu_foto")
    private String usuFoto;
    @ManyToMany(mappedBy = "usuarioCollection", fetch = FetchType.LAZY)
    private Collection<Rol> rolCollection;
    @OneToMany(mappedBy = "fkUsucomprador", fetch = FetchType.LAZY)
    private Collection<Venta> ventaCollection;
    @OneToMany(mappedBy = "fkUsuvendedor", fetch = FetchType.LAZY)
    private Collection<Venta> ventaCollection1;
    @OneToMany(mappedBy = "fkUsuarioid", fetch = FetchType.LAZY)
    private Collection<Proveedor> proveedorCollection;

    public Usuario() {
    }

    public Usuario(Integer usuUsuarioid) {
        this.usuUsuarioid = usuUsuarioid;
    }

    public Integer getUsuUsuarioid() {
        return usuUsuarioid;
    }

    public void setUsuUsuarioid(Integer usuUsuarioid) {
        this.usuUsuarioid = usuUsuarioid;
    }

    public String getUsuTipodocumento() {
        return usuTipodocumento;
    }

    public void setUsuTipodocumento(String usuTipodocumento) {
        this.usuTipodocumento = usuTipodocumento;
    }

    public BigInteger getUsuNumeroducumento() {
        return usuNumeroducumento;
    }

    public void setUsuNumeroducumento(BigInteger usuNumeroducumento) {
        this.usuNumeroducumento = usuNumeroducumento;
    }

    public String getUsuNombres() {
        return usuNombres;
    }

    public void setUsuNombres(String usuNombres) {
        this.usuNombres = usuNombres;
    }

    public String getUsuApellidos() {
        return usuApellidos;
    }

    public void setUsuApellidos(String usuApellidos) {
        this.usuApellidos = usuApellidos;
    }

    public String getUsuCorreo() {
        return usuCorreo;
    }

    public void setUsuCorreo(String usuCorreo) {
        this.usuCorreo = usuCorreo;
    }

    public String getUsuClave() {
        return usuClave;
    }

    public void setUsuClave(String usuClave) {
        this.usuClave = usuClave;
    }

    public Short getUsuEstado() {
        return usuEstado;
    }

    public void setUsuEstado(Short usuEstado) {
        this.usuEstado = usuEstado;
    }

    public String getUsuFoto() {
        return usuFoto;
    }

    public void setUsuFoto(String usuFoto) {
        this.usuFoto = usuFoto;
    }

    public Collection<Rol> getRolCollection() {
        return rolCollection;
    }

    public void setRolCollection(Collection<Rol> rolCollection) {
        this.rolCollection = rolCollection;
    }

    public Collection<Venta> getVentaCollection() {
        return ventaCollection;
    }

    public void setVentaCollection(Collection<Venta> ventaCollection) {
        this.ventaCollection = ventaCollection;
    }

    public Collection<Venta> getVentaCollection1() {
        return ventaCollection1;
    }

    public void setVentaCollection1(Collection<Venta> ventaCollection1) {
        this.ventaCollection1 = ventaCollection1;
    }

    public Collection<Proveedor> getProveedorCollection() {
        return proveedorCollection;
    }

    public void setProveedorCollection(Collection<Proveedor> proveedorCollection) {
        this.proveedorCollection = proveedorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuUsuarioid != null ? usuUsuarioid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuUsuarioid == null && other.usuUsuarioid != null) || (this.usuUsuarioid != null && !this.usuUsuarioid.equals(other.usuUsuarioid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.sena.paraya.entity.Usuario[ usuUsuarioid=" + usuUsuarioid + " ]";
    }
    
}
