/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.facade;

import edu.sena.postreonline.entity.Categoria;
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
public class CategoriaFacade extends AbstractFacade<Categoria> implements CategoriaFacadeLocal {

    @PersistenceContext(unitName = "uppostresonline")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaFacade() {
        super(Categoria.class);
    }

    @Override
    public boolean registroCategoria(Categoria catReg) {
        try {
            Query q = em.createNativeQuery("INSERT INTO tbl_categoria (cat_nombre, cat_descripcion) VALUES (?,?)");
            q.setParameter(1, catReg.getCatNombre());
            q.setParameter(2, catReg.getCatDescripcion());
            q.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Categoria> leertodos() {
        em.getEntityManagerFactory().getCache().evictAll();
        Query q = em.createQuery("SELECT c FROM Categoria c");
        return q.getResultList();
    }

    @Override
    public Categoria buscarPorId(int catId) {
        em.getEntityManagerFactory().getCache().evictAll();
        Query q = em.createQuery("SELECT c FROM Categoria c WHERE c.catCategoriaid = :catId");
        q.setParameter("catId", catId);
        return (Categoria) q.getSingleResult();
    }

    @Override
    public Categoria validarSiExiste(String catNom) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT c FROM Categoria c WHERE c.catNombre LIKE CONCAT('%',:catNom,'%')");
            q.setParameter("catNom", catNom);
            return (Categoria) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

}
