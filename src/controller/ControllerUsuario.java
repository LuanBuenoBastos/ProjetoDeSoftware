package controller;

import model.ModelUsuario;
import DAO.DAOUsuario;
import java.util.ArrayList;

/**
*
* @author Luan Bueno
*/
public class ControllerUsuario {

    private DAOUsuario daoUsuario = new DAOUsuario();

    /**
    * grava Usuario
    * @param pModelUsuario
    * return int
    */
    public int salvarUsuarioController(ModelUsuario pModelUsuario){
        return this.daoUsuario.salvarUsuarioDAO(pModelUsuario);
    }

    /**
    * recupera Usuario
    * @param pIdUsuario
    * return ModelUsuario
     * @return 
    */
    public ModelUsuario getUsuarioController(int  pIdUsuario){
        return this.daoUsuario.getUsuarioDAO(pIdUsuario);
    }

    /**
    * recupera uma lista deUsuario
    * @param pIdUsuario
    * return ArrayList
     * @return 
    */
    public ArrayList<ModelUsuario> getListaUsuarioController(){
        return this.daoUsuario.getListaUsuarioDAO();
    }

    /**
    * atualiza Usuario
    * @param pModelUsuario
    * return boolean
    */
    public boolean atualizarUsuarioController(ModelUsuario pModelUsuario){
        return this.daoUsuario.atualizarUsuarioDAO(pModelUsuario);
    }

    /**
    * exclui Usuario
    * @param pIdUsuario
    * return boolean
    */
    public boolean excluirUsuarioController(int  pIdUsuario){
        return this.daoUsuario.excluirUsuarioDAO(pIdUsuario);
    }
    
    public boolean getValidaUsuarioController(ModelUsuario modelUsuario){
        return this.daoUsuario.getValidarUsuarioDAO(modelUsuario);
    }
}