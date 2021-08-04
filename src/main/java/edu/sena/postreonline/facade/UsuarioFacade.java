/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.facade;

import edu.sena.postreonline.entity.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Josarta
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "uppostresonline")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public Usuario usuarioLogin(String usuCorreo, String usuClave) {
        try {
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuClave = :usuClave AND u.usuCorreo = :usuCorreo");
            q.setParameter("usuClave", usuClave);
            q.setParameter("usuCorreo", usuCorreo);
            return (Usuario) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Usuario recuperarClave(String usuCorreo) {
        try {
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuCorreo = :usuCorreo");
            q.setParameter("usuCorreo", usuCorreo);
            return (Usuario) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean registroUsusario(Usuario usuReg) {
        try {
            Query q = em.createNativeQuery("INSERT INTO tbl_usuario (usu_tipodocumento, usu_numeroducumento, usu_nombres, usu_apellidos, usu_correo, usu_clave, usu_estado) VALUES (?, ?, ?, ?, ?, ?, ?)");
            q.setParameter(1, usuReg.getUsuTipodocumento());
            q.setParameter(2, usuReg.getUsuNumeroducumento());
            q.setParameter(3, usuReg.getUsuNombres());
            q.setParameter(4, usuReg.getUsuApellidos());
            q.setParameter(5, usuReg.getUsuCorreo());
            q.setParameter(6, usuReg.getUsuClave());
            q.setParameter(7, usuReg.getUsuEstado());
            q.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
