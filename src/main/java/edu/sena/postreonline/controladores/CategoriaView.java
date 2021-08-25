/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.controladores;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import edu.sena.postreonline.entity.Categoria;
import edu.sena.postreonline.facade.CategoriaFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

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

}
