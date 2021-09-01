/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.controladores;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import edu.sena.postreonline.entity.Categoria;
import edu.sena.postreonline.entity.Foto;
import edu.sena.postreonline.entity.Producto;
import edu.sena.postreonline.facade.CategoriaFacadeLocal;
import edu.sena.postreonline.facade.FotoFacadeLocal;
import edu.sena.postreonline.facade.ProductoFacadeLocal;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;
import org.primefaces.PrimeFaces;
import org.primefaces.shaded.commons.io.FilenameUtils;

/**
 *
 * @author Josarta
 */
@Named(value = "productosView")
@ViewScoped
public class ProductosView implements Serializable {

    @EJB
    ProductoFacadeLocal productoFacadeLocal;
    @EJB
    CategoriaFacadeLocal categoriaFacadeLocal;
    @EJB
    FotoFacadeLocal fotoFacadeLocal;

    private Producto proNueva = new Producto();
    private Producto proTemporal = new Producto();
    private List<Producto> todosProductos = new ArrayList<>();
    private List<Categoria> listaCategorias = new ArrayList<>();
    private int fk_categoria = 0;
    private Part archivoCarga;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * Creates a new instance of ProductosView
     */
    public ProductosView() {
    }

    @PostConstruct
    public void cargaInicial() {
        todosProductos.clear();
        todosProductos.addAll(productoFacadeLocal.leertodos());
        listaCategorias.addAll(categoriaFacadeLocal.leertodos());
    }

    public void cargarInicialDatos() {
        if (archivoCarga != null) {
            if (archivoCarga.getSize() > 700000) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Error!',"
                        + "  text: 'El archivo excede el tamaño permitido',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Cambie de archivo !!!'"
                        + "})");
            } else if (archivoCarga.getContentType().equalsIgnoreCase("application/vnd.ms-excel")) {
                File carpeta = new File("C:/imgpostres/Administrador/Archivos");
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                } else {
                    try (InputStream is = archivoCarga.getInputStream()) {
                        Files.copy(is, (new File(carpeta, archivoCarga.getSubmittedFileName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        CSVParser conPuntoYComa = new CSVParserBuilder().withSeparator(';').build();
                        CSVReader reader = new CSVReaderBuilder(new FileReader("C:/imgpostres/Administrador/Archivos/" + archivoCarga.getSubmittedFileName())).withCSVParser(conPuntoYComa).build();;
                        String[] nextLine;
                        while ((nextLine = reader.readNext()) != null) {
                            // nombre : nextLine[0]
                            // descripcion : nextLine[1]
                            // valor : nextLine[2]
                            // cantidad : nextLine[3]
                            // f_categoria : nextLine[4]

                            Producto tempPro = productoFacadeLocal.validarSiExiste(nextLine[0]);

                            if (tempPro != null) {
                                tempPro.setProDescripcion(nextLine[1]);
                                tempPro.setProValor(Double.parseDouble(nextLine[2]));
                                tempPro.setProCantidaddisponiblel(Integer.parseInt(nextLine[3]));
                                productoFacadeLocal.actualizaProducto(tempPro,Integer.parseInt(nextLine[4]));
                            } else {
                                Producto proNew = new Producto();
                                proNew.setProNombre(nextLine[0]);
                                proNew.setProDescripcion(nextLine[1]);
                                proNew.setProValor(Double.parseDouble(nextLine[2]));
                                proNew.setProCantidaddisponiblel(Integer.parseInt(nextLine[3]));
                                productoFacadeLocal.registroProducto(proNew, Integer.parseInt(nextLine[4]));
                            }

                        }

                        todosProductos.clear();
                        todosProductos.addAll(productoFacadeLocal.leertodos());

                        PrimeFaces.current().executeScript("Swal.fire({"
                                + "  title: 'Productos',"
                                + "  text: 'Cargados correctamente',"
                                + "  icon: 'success',"
                                + "  confirmButtonText: 'OK'"
                                + "})");
                        
                        reader.close();

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
                        + "  text: 'El archivo ingresado no es un archivo.csv',"
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

    public void cargarCategoriaTemporal(Producto pro) {
        this.proTemporal = pro;
        this.fk_categoria = pro.getFkCategoria().getCatCategoriaid();
    }

    public void actualizarTempDatos() {
        try {
            productoFacadeLocal.edit(proTemporal);
            todosProductos.clear();
            todosProductos.addAll(productoFacadeLocal.leertodos());
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Producto',"
                    + "  text: 'Actualizado correctamente',"
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

    public void registroProducto() {
        try {
            if (productoFacadeLocal.registroProducto(proNueva, fk_categoria)) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Producto',"
                        + "  text: 'Creado correctamente',"
                        + "  icon: 'success',"
                        + "  confirmButtonText: 'OK'"
                        + "})");
                todosProductos.clear();
                todosProductos.addAll(productoFacadeLocal.leertodos());
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
        proNueva = new Producto();
    }

    public void removerProducto(Producto prt) {
        try {
            productoFacadeLocal.remove(prt);
            todosProductos.clear();
            todosProductos.addAll(productoFacadeLocal.leertodos());
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Producto',"
                    + "  text: 'Removido correctamente',"
                    + "  icon: 'success',"
                    + "  confirmButtonText: 'OK'"
                    + "})");
        } catch (Exception e) {
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Error!',"
                    + "  text: 'No se pudo realizar esta accion',"
                    + "  icon: 'error',"
                    + "  confirmButtonText: 'Intente mas tarde !!!'"
                    + "})");
        }

    }

    public void removerFotoProducto(int id_foto) {
        try {
            productoFacadeLocal.removerFoto(proTemporal.getProProductoid(), id_foto);
            proTemporal = productoFacadeLocal.buscarPorId(proTemporal.getProProductoid());
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Foto',"
                    + "  text: 'Removida correctamente',"
                    + "  icon: 'success',"
                    + "  confirmButtonText: 'OK'"
                    + "})");
        } catch (Exception e) {
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Error!',"
                    + "  text: 'No se pudo realizar esta accion',"
                    + "  icon: 'error',"
                    + "  confirmButtonText: 'Intente mas tarde !!!'"
                    + "})");
        }

    }

    public void cargarFotoProducto() {
        if (archivoCarga != null) {
            if (archivoCarga.getSize() > 700000) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Error!',"
                        + "  text: 'El archivo excede el tamaño permitido',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Cambie de archivo !!!'"
                        + "})");
            } else if (archivoCarga.getContentType().equalsIgnoreCase("image/png") || archivoCarga.getContentType().equalsIgnoreCase("image/jpeg")) {
                File carpeta = new File("C:/imgpostres/Productos");
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                } else {
                    try (InputStream is = archivoCarga.getInputStream()) {
                        Calendar hoy = Calendar.getInstance();
                        String nuevonombre = sdf.format(hoy.getTime()) + ".";
                        nuevonombre += FilenameUtils.getExtension(archivoCarga.getSubmittedFileName());
                        Files.copy(is, (new File(carpeta, nuevonombre)).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        Foto fotoNueva = new Foto();
                        fotoNueva.setFotDescripcion(archivoCarga.getSubmittedFileName());
                        fotoNueva.setFotRuta(nuevonombre);
                        fotoFacadeLocal.create(fotoNueva);
                        productoFacadeLocal.adicionarFoto(proTemporal.getProProductoid(), fotoNueva.getFotFotoid());
                        proTemporal = productoFacadeLocal.buscarPorId(proTemporal.getProProductoid());
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

    public Producto getProNueva() {
        return proNueva;
    }

    public void setProNueva(Producto proNueva) {
        this.proNueva = proNueva;
    }

    public Producto getProTemporal() {
        return proTemporal;
    }

    public void setProTemporal(Producto proTemporal) {
        this.proTemporal = proTemporal;
    }

    public List<Producto> getTodosProductos() {
        return todosProductos;
    }

    public void setTodosProductos(List<Producto> todosProductos) {
        this.todosProductos = todosProductos;
    }

    public int getFk_categoria() {
        return fk_categoria;
    }

    public void setFk_categoria(int fk_categoria) {
        this.fk_categoria = fk_categoria;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public Part getArchivoCarga() {
        return archivoCarga;
    }

    public void setArchivoCarga(Part archivoCarga) {
        this.archivoCarga = archivoCarga;
    }

}
