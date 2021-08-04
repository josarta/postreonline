/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.facade;

import edu.sena.postreonline.entity.VentaHasProducto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Josarta
 */
@Local
public interface VentaHasProductoFacadeLocal {

    void create(VentaHasProducto ventaHasProducto);

    void edit(VentaHasProducto ventaHasProducto);

    void remove(VentaHasProducto ventaHasProducto);

    VentaHasProducto find(Object id);

    List<VentaHasProducto> findAll();

    List<VentaHasProducto> findRange(int[] range);

    int count();
    
}
