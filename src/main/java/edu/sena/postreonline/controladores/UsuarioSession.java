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
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
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

    @Resource(lookup = "java:app/dbs_ventas")
    DataSource dataSource;

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

    public void descargaArchivoPdf() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext context = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment; filename=Lista usuarios.pdf");
        try {
            File jasper = new File(context.getRealPath("/reportes/rusuarios.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), new HashMap(), dataSource.getConnection());

            OutputStream os = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp, os);
            os.flush();
            os.close();
            facesContext.responseComplete();

        } catch (IOException | SQLException | JRException e) {
            System.out.println("ProductosView.descargaArchivoPdf() " + e.getMessage());
        }
    }

    public void descargaArchivoPdfCertificado(String cedula) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext context = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment; filename=certificado.pdf");
        try {
            Calendar hoy = Calendar.getInstance();
            
            
            Map parametros = new HashMap();
            parametros.put("documento", cedula);
            parametros.put("nombres", "");
            parametros.put("dia", ""+(hoy.get(Calendar.DATE)< 10?"0"+hoy.get(Calendar.DATE):hoy.get(Calendar.DATE)));
            String mes = "";
            
            switch (hoy.get(Calendar.MONTH)){
                case 0:
                    mes = "Enero";
                break;
                
                case 1:
                    mes = "Febrero";
                break;
                case 2:
                    mes = "Marzo";
                break;
                case 3:
                    mes = "Abril";
                break;
                case 4:
                    mes = "Mayo";
                break;
                case 5:
                    mes = "Junio";
                break;
                case 6:
                    mes = "Julio";
                break;
                case 7:
                    mes = "Agosto";
                break;
                case 8:
                    mes = "Septiembre";
                break;
                case 9:
                    mes = "Octubre";
                break;
                
                case 10:
                    mes = "Noviembre";
                break;
                
                case 11:
                    mes = "Diciembre";
                break;
               
            }           
            parametros.put("mes", mes);
            parametros.put("annio", ""+hoy.get(Calendar.YEAR));
            
            
            File jasper = new File(context.getRealPath("/reportes/certificado.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, dataSource.getConnection());

            OutputStream os = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp, os);
            os.flush();
            os.close();
            facesContext.responseComplete();

        } catch (IOException | SQLException | JRException e) {
            System.out.println("ProductosView.descargaArchivoPdf() " + e.getMessage());
        }
    }

    public void descargaArchivoXlsx() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext context = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.addHeader("Content-disposition", "attachment; filename=Lista usuarios.xlsx");
        try {
            File jasper = new File(context.getRealPath("/reportes/rusuarios.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), new HashMap(), dataSource.getConnection());

            JRXlsxExporter exporter = new JRXlsxExporter(); // initialize exporter 
            exporter.setExporterInput(new SimpleExporterInput(jp)); // set compiled report as input
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true); // setup configuration
            configuration.setDetectCellType(true);
            configuration.setSheetNames(new String[]{"Usuarios"});
            exporter.setConfiguration(configuration); // set configuration    
            exporter.exportReport();
            facesContext.responseComplete();

        } catch (IOException | SQLException | JRException e) {
            System.out.println("ProductosView.descargaArchivoPdf() " + e.getMessage());
        }
    }

    public void descargaArchivoDocx() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext context = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.addHeader("Content-disposition", "attachment; filename=Lista usuarios.doc");
        try {
            File jasper = new File(context.getRealPath("/reportes/rusuarios.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), new HashMap(), dataSource.getConnection());

            JRDocxExporter exporter = new JRDocxExporter(); // initialize exporter 
            exporter.setExporterInput(new SimpleExporterInput(jp)); // set compiled report as input
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

            SimpleDocxExporterConfiguration configuration = new SimpleDocxExporterConfiguration();
            configuration.setMetadataAuthor("Jose Luis Sarta A."); // setup configuration
            configuration.setMetadataTitle("Reporte de usuarios");
            configuration.setMetadataSubject("Listado de usuarios");

            exporter.setConfiguration(configuration); // set configuration    
            exporter.exportReport();
            facesContext.responseComplete();

        } catch (IOException | SQLException | JRException e) {
            System.out.println("ProductosView.descargaArchivoPdf() " + e.getMessage());
        }
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
