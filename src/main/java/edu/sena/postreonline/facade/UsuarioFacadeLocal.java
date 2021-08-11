/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.facade;

import edu.sena.postreonline.entity.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Josarta
 */
@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuario);

    void edit(Usuario usuario);

    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();

    public boolean registroUsusario(Usuario usuReg);

    public Usuario recuperarClave(String usuCorreo);

    public Usuario usuarioLogin(String usuCorreo, String usuClave);

    public List<Usuario> leertodos();

    public Usuario buscarPorId(int usuarioId);
    
    
    
}
