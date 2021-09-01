/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.facade;

import edu.sena.postreonline.entity.Producto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Josarta
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> implements ProductoFacadeLocal {

    @PersistenceContext(unitName = "uppostresonline")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }

    @Override
    public boolean registroProducto(Producto prodReg, int fk_categoria) {
        try {
            Query q = em.createNativeQuery("INSERT INTO tbl_producto (pro_nombre, pro_descripcion, pro_valor, pro_cantidaddisponiblel, fk_categoria) VALUES (?,?,?,?,?);");
            q.setParameter(1, prodReg.getProNombre());
            q.setParameter(2, prodReg.getProDescripcion());
            q.setParameter(3, prodReg.getProValor());
            q.setParameter(4, prodReg.getProCantidaddisponiblel());
            q.setParameter(5, fk_categoria);
            q.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    
    
     @Override
    public boolean actualizaProducto(Producto prodReg, int fk_categoria) {
        try {
            Query q = em.createNativeQuery("UPDATE tbl_producto SET pro_nombre = ?, pro_descripcion = ?, pro_valor = ?, pro_cantidaddisponiblel = ?, fk_categoria = ? WHERE (pro_productoid = ?)");
            q.setParameter(1, prodReg.getProNombre());
            q.setParameter(2, prodReg.getProDescripcion());
            q.setParameter(3, prodReg.getProValor());
            q.setParameter(4, prodReg.getProCantidaddisponiblel());
            q.setParameter(5, fk_categoria);
            q.setParameter(6, prodReg.getProProductoid());            
            q.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    

    @Override
    public List<Producto> leertodos() {
        em.getEntityManagerFactory().getCache().evictAll();
        Query q = em.createQuery("SELECT c FROM Producto c");
        return q.getResultList();
    }

    @Override
    public Producto buscarPorId(int proId) {
        em.getEntityManagerFactory().getCache().evictAll();
        Query q = em.createQuery("SELECT p FROM Producto p WHERE p.proProductoid = :proId");
        q.setParameter("proId", proId);
        return (Producto) q.getSingleResult();
    }

    @Override
    public boolean adicionarFoto(int fk_producto, int fk_foto) {
        try {
            Query q = em.createNativeQuery("INSERT INTO tbl_producto_has_tbl_foto (fk_productoid,fk_fotoid) values (?,?);");
            q.setParameter(1, fk_producto);
            q.setParameter(2, fk_foto);

            q.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removerFoto(int fk_producto, int fk_foto) {
        try {
            Query q = em.createNativeQuery("DELETE FROM tbl_producto_has_tbl_foto WHERE fk_productoid = ? AND fk_fotoid = ?;");
            q.setParameter(1, fk_producto);
            q.setParameter(2, fk_foto);

            q.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    @Override
    public Producto validarSiExiste(String proNom) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT c FROM Producto c WHERE c.proNombre LIKE CONCAT('%',:proNom,'%')");
            q.setParameter("proNom", proNom);
            return (Producto) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

}
