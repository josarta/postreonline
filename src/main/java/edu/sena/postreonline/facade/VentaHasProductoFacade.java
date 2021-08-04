/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.facade;

import edu.sena.postreonline.entity.VentaHasProducto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Josarta
 */
@Stateless
public class VentaHasProductoFacade extends AbstractFacade<VentaHasProducto> implements VentaHasProductoFacadeLocal {

    @PersistenceContext(unitName = "uppostresonline")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VentaHasProductoFacade() {
        super(VentaHasProducto.class);
    }
    
}
