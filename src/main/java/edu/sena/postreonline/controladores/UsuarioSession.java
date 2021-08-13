/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.controladores;

import edu.sena.postreonline.entity.Rol;
import edu.sena.postreonline.entity.Usuario;
import edu.sena.postreonline.facade.RolFacadeLocal;
import edu.sena.postreonline.facade.UsuarioFacadeLocal;
import edu.sena.postreonline.utilidades.Mail;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import org.primefaces.PrimeFaces;
import org.primefaces.shaded.commons.io.FilenameUtils;

/**
 *
 * @author Josarta
 */
@Named(value = "usuarioSession")
@SessionScoped
public class UsuarioSession implements Serializable {

    @EJB
    UsuarioFacadeLocal usuarioFacadeLocal;

    @EJB
    RolFacadeLocal rolFacadeLocal;

    private Part archivoFoto;

    /**
     * Creates a new instance of UsuarioSession
     */
    private Usuario usuReg = new Usuario();
    private Usuario usuLogin = new Usuario();
    private Usuario usuTemporal = new Usuario();
    private boolean tablaFilas;

    private String usuCorreo = "";
    private String usuClave = "";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private List<Rol> todosLosRoles = new ArrayList<>();
    private List<Rol> rolesSinAsignar = new ArrayList<>();

    public UsuarioSession() {
    }

    @PostConstruct
    public void cargaInicial() {
        todosLosRoles.addAll(rolFacadeLocal.findAll());
    }

    public void iniciarSession() {
        try {
            usuLogin = usuarioFacadeLocal.usuarioLogin(usuCorreo, usuClave);
            if (usuLogin != null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().redirect("usuario/index.xhtml");
            } else {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Error!',"
                        + "  text: 'Usuario no encontrado',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Intentar de nuevo'"
                        + "})");
            }
        } catch (Exception e) {
        }
    }

    public void cerrarSession() throws IOException {
        usuLogin = null;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.getExternalContext().invalidateSession();
        fc.getExternalContext().redirect("../index.xhtml");

    }

    public void validarUsuarioActivo() throws IOException {
        if (usuLogin == null || usuLogin.getUsuCorreo() == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().invalidateSession();
            fc.getExternalContext().redirect("../index.xhtml");
        }

    }

    public void recuperarClave() {
        usuReg = null;
        usuReg = usuarioFacadeLocal.recuperarClave(usuCorreo);
        if (usuReg != null) {
            try {

                double valor = 100000 * Math.random();
                usuReg.setUsuClave("NW-" + (int) valor);
                usuarioFacadeLocal.edit(usuReg);
                Mail.recuperarClaves(usuReg.getUsuCorreo(), usuReg.getUsuClave());
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Correo enviado',"
                        + "  text: 'Por favor verifique su bandeja de entrada',"
                        + "  icon: 'success',"
                        + "  confirmButtonText: 'OK'"
                        + "})");

            } catch (Exception e) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Error!',"
                        + "  text: 'No se pudo enviar el correo',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Intente mas tarde !!!'"
                        + "})");
            }

        } else {
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Error!',"
                    + "  text: 'Correo no encontrado',"
                    + "  icon: 'error',"
                    + "  confirmButtonText: 'Intentar de nuevo'"
                    + "})");
        }

    }

    public void actualizarMisDatos() {
        try {
            usuarioFacadeLocal.edit(usuLogin);
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Datos personales',"
                    + "  text: 'Actualizados correctamente',"
                    + "  icon: 'success',"
                    + "  confirmButtonText: 'OK'"
                    + "})");
        } catch (Exception e) {
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Error!',"
                    + "  text: 'No se pudo actualizar',"
                    + "  icon: 'error',"
                    + "  confirmButtonText: 'Intente mas tarde !!!'"
                    + "})");
        }
    }

    public void actualizarTempDatos() {
        try {
            usuarioFacadeLocal.edit(usuTemporal);
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Datos personales',"
                    + "  text: 'Actualizados correctamente',"
                    + "  icon: 'success',"
                    + "  confirmButtonText: 'OK'"
                    + "})");
        } catch (Exception e) {
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Error!',"
                    + "  text: 'No se pudo actualizar',"
                    + "  icon: 'error',"
                    + "  confirmButtonText: 'Intente mas tarde !!!'"
                    + "})");
        }
    }

    public void cargarFotoPerfil() {
        if (archivoFoto != null) {
            if (archivoFoto.getSize() > 700000) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Error!',"
                        + "  text: 'El archivo excede el tamaño permitido',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Cambie de archivo !!!'"
                        + "})");
            } else if (archivoFoto.getContentType().equalsIgnoreCase("image/png") || archivoFoto.getContentType().equalsIgnoreCase("image/jpeg")) {
                File carpeta = new File("C:/imgpostres/Usuarios/Perfil");
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                } else {
                    try (InputStream is = archivoFoto.getInputStream()) {
                        Calendar hoy = Calendar.getInstance();
                        String nuevonombre = sdf.format(hoy.getTime()) + ".";
                        nuevonombre += FilenameUtils.getExtension(archivoFoto.getSubmittedFileName());
                        Files.copy(is, (new File(carpeta, nuevonombre)).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        usuLogin.setUsuFoto(nuevonombre);
                        usuarioFacadeLocal.edit(usuLogin);
                    } catch (Exception e) {
                        PrimeFaces.current().executeScript("Swal.fire({"
                                + "  title: 'Error!',"
                                + "  text: 'No se pudo realizar esta accion',"
                                + "  icon: 'error',"
                                + "  confirmButtonText: 'Intente mas tarde !!!'"
                                + "})");
                    }
                }
            } else {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Error!',"
                        + "  text: 'El archivo ingresado no es una imagen',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Cambie de archivo !!!'"
                        + "})");
            }
        } else {
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Error!',"
                    + "  text: 'No se pudo realizar esta accion',"
                    + "  icon: 'error',"
                    + "  confirmButtonText: 'Intente mas tarde !!!'"
                    + "})");
        }
        PrimeFaces.current().executeScript("document.getElementById('resetFromImg').click();");
    }

    public void registrarUsuario() {
        try {
            usuReg.setUsuEstado(Short.parseShort("1"));
            if (usuarioFacadeLocal.registroUsusario(usuReg)) {

                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Usuario',"
                        + "  text: 'Registrado correctamente',"
                        + "  icon: 'success',"
                        + "  confirmButtonText: 'OK'"
                        + "})");

            } else {

                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Error!',"
                        + "  text: 'No se pudo realizar esta accion',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Intente mas tarde !!!'"
                        + "})");
            }
        } catch (NumberFormatException e) {
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Error!',"
                    + "  text: 'No se pudo realizar esta accion',"
                    + "  icon: 'error',"
                    + "  confirmButtonText: 'Intente mas tarde !!!'"
                    + "})");
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void showInfo() {
        addMessage(FacesMessage.SEVERITY_INFO, "Info Message", "Message Content");
    }

    public void resetClaveTodosUsuarios() {
        List<Usuario> todaListaUsuarios = usuarioFacadeLocal.findAll();
        todaListaUsuarios.remove(usuLogin);
        for (Usuario lUsu : todaListaUsuarios) {
            try {
                double valor = 100000 * Math.random();
                lUsu.setUsuClave("NW-" + (int) valor);
                usuarioFacadeLocal.edit(lUsu);
                Mail.recuperarClaves(lUsu.getUsuCorreo(), lUsu.getUsuClave());
                addMessage(FacesMessage.SEVERITY_INFO, "Correo enviado", "Usuario," + lUsu.getUsuNombres() + " " + lUsu.getUsuApellidos());
            } catch (Exception e) {
                addMessage(FacesMessage.SEVERITY_ERROR, "No se pudo enviar el correo", "Usuario," + lUsu.getUsuNombres() + " " + lUsu.getUsuApellidos());
            }
        }

    }

    public void removerRol(int rolId) {
        rolFacadeLocal.removerRol(usuTemporal.getUsuUsuarioid(), rolId);
        usuTemporal = usuarioFacadeLocal.buscarPorId(usuTemporal.getUsuUsuarioid());
        actualizaRoles();
    }

    public void addRol(int rolId) {
        rolFacadeLocal.addRol(usuTemporal.getUsuUsuarioid(), rolId);
        usuTemporal = usuarioFacadeLocal.buscarPorId(usuTemporal.getUsuUsuarioid());
        actualizaRoles();
    }

    public List<Rol> todosRoles() {
        return rolFacadeLocal.findAll();
    }

    public void actualizaRoles() {
        rolesSinAsignar.clear();
        for (Rol rolPrincial : todosLosRoles) {
            boolean flag = true;
            for (Rol rolUsu : this.usuTemporal.getRolCollection()) {
                if (Objects.equals(rolPrincial.getRolRolid(), rolUsu.getRolRolid())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                rolesSinAsignar.add(rolPrincial);
            }
        }
    }

    public void cargarUsuarioTemporal(Usuario usu) {
        this.usuTemporal = usu;
        actualizaRoles();
    }

    public void cambiarEstado(Usuario usu) {
        if (usu.getUsuEstado() == Short.parseShort("0")) {
            usu.setUsuEstado(Short.parseShort("1"));
        } else {
            usu.setUsuEstado(Short.parseShort("0"));
        }
        usuarioFacadeLocal.edit(usu);
    }

    public List<Usuario> leertodos() {
        return usuarioFacadeLocal.leertodos();
    }

    public Usuario getUsuReg() {
        return usuReg;
    }

    public void setUsuReg(Usuario usuReg) {
        this.usuReg = usuReg;
    }

    public Usuario getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(Usuario usuLogin) {
        this.usuLogin = usuLogin;
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

    public Part getArchivoFoto() {
        return archivoFoto;
    }

    public void setArchivoFoto(Part archivoFoto) {
        this.archivoFoto = archivoFoto;
    }

    public Usuario getUsuTemporal() {
        return usuTemporal;
    }

    public void setUsuTemporal(Usuario usuTemporal) {
        this.usuTemporal = usuTemporal;
    }

    public boolean isTablaFilas() {
        return tablaFilas;
    }

    public void setTablaFilas(boolean tablaFilas) {
        this.tablaFilas = tablaFilas;
    }

    public List<Rol> getTodosLosRoles() {
        return todosLosRoles;
    }

    public void setTodosLosRoles(List<Rol> todosLosRoles) {
        this.todosLosRoles = todosLosRoles;
    }

    public List<Rol> getRolesSinAsignar() {
        return rolesSinAsignar;
    }

    public void setRolesSinAsignar(List<Rol> rolesSinAsignar) {
        this.rolesSinAsignar = rolesSinAsignar;
    }

}
