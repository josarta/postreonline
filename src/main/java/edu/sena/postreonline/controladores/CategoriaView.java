/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.controladores;

import com.opencsv.CSVReader;
import edu.sena.postreonline.entity.Categoria;
import edu.sena.postreonline.facade.CategoriaFacadeLocal;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
@Named(value = "categoriaView")
@ViewScoped
public class CategoriaView implements Serializable {

    @EJB
    CategoriaFacadeLocal categoriaFacadeLocal;
    private Categoria catNueva = new Categoria();
    private Categoria catTemporal = new Categoria();
    private List<Categoria> todasCategorias = new ArrayList<>();
    private Part archivoCarga;

    /**
     * Creates a new instance of CategoriaView
     */
    public CategoriaView() {
    }

    @PostConstruct
    public void cargaInicial() {
        todasCategorias.clear();
        todasCategorias.addAll(categoriaFacadeLocal.leertodos());
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
                        CSVReader reader = new CSVReader(new FileReader("C:/imgpostres/Administrador/Archivos/" + archivoCarga.getSubmittedFileName()));
                        String[] nextLine;
                        while ((nextLine = reader.readNext()) != null) {
                            // nombre : nextLine[0]
                            // descripcion : nextLine[1]

                            Categoria tempCat = categoriaFacadeLocal.validarSiExiste(nextLine[0]);

                            if (tempCat != null) {
                                tempCat.setCatDescripcion(nextLine[1]);
                                categoriaFacadeLocal.edit(tempCat);
                            } else {
                                Categoria catNew = new Categoria();
                                catNew.setCatNombre(nextLine[0]);
                                catNew.setCatDescripcion(nextLine[1]);
                                categoriaFacadeLocal.registroCategoria(catNew);
                            }
                        }
                        todasCategorias.clear();
                        todasCategorias.addAll(categoriaFacadeLocal.leertodos());
                        PrimeFaces.current().executeScript("Swal.fire({"
                                + "  title: 'Usuarios',"
                                + "  text: 'Cargado correctamente',"
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

    public void actualizarTempDatos() {
        try {
            categoriaFacadeLocal.edit(catTemporal);
            todasCategorias.clear();
            todasCategorias.addAll(categoriaFacadeLocal.leertodos());
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Categoria',"
                    + "  text: 'Actualizada correctamente',"
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

    public void registroCategoria() {
        try {
            if (categoriaFacadeLocal.registroCategoria(catNueva)) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Categoria',"
                        + "  text: 'Creada correctamente',"
                        + "  icon: 'success',"
                        + "  confirmButtonText: 'OK'"
                        + "})");
                todasCategorias.clear();
                todasCategorias.addAll(categoriaFacadeLocal.leertodos());
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
        catNueva = new Categoria();
    }

    public void cargarCategoriaTemporal(Categoria cat) {
        this.catTemporal = cat;
    }

    public void removerCategoria(Categoria cat) {
        try {
            categoriaFacadeLocal.remove(cat);
            todasCategorias.clear();
            todasCategorias.addAll(categoriaFacadeLocal.leertodos());
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Categoria',"
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

    public Categoria getCatNueva() {
        return catNueva;
    }

    public void setCatNueva(Categoria catNueva) {
        this.catNueva = catNueva;
    }

    public Categoria getCatTemporal() {
        return catTemporal;
    }

    public void setCatTemporal(Categoria catTemporal) {
        this.catTemporal = catTemporal;
    }

    public List<Categoria> getTodasCategorias() {
        return todasCategorias;
    }

    public void setTodasCategorias(List<Categoria> todasCategorias) {
        this.todasCategorias = todasCategorias;
    }

    public Part getArchivoCarga() {
        return archivoCarga;
    }

    public void setArchivoCarga(Part archivoCarga) {
        this.archivoCarga = archivoCarga;
    }

}
