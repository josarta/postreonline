/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.facade;

import edu.sena.postreonline.entity.Producto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Josarta
 */
@Local
public interface ProductoFacadeLocal {

    void create(Producto producto);

    void edit(Producto producto);

    void remove(Producto producto);

    Producto find(Object id);

    List<Producto> findAll();

    List<Producto> findRange(int[] range);

    int count();

    public Producto buscarPorId(int proId);

    public List<Producto> leertodos();

    public boolean registroProducto(Producto prodReg, int fk_categoria);

    public boolean removerFoto(int fk_producto, int fk_foto);

    public boolean adicionarFoto(int fk_producto, int fk_foto);
    
}
