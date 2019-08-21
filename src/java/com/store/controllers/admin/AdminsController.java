/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.admin;

import com.store.controllers.util.Rol;
import com.store.controllers.util.Util;
import com.store.entities.User;
import com.store.facade.UserFacade;
import com.store.lazydatamodel.LazyUserDataModel;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "adminsController")
@ViewScoped
public class AdminsController implements Serializable,LazyUserDataModel.OnLazyUserDataModel
{
    @EJB private UserFacade userEJB;
    private LazyDataModel<User> lazyModel;
    private User selectedUser;
    
    @PostConstruct
    public void init() {
        lazyModel = new LazyUserDataModel(this);
    }
    
    public LazyDataModel<User> getLazyModel() {
        return lazyModel;
    }
    
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    public User getSelectedUser() {
        return selectedUser;
    }

    @Override
    public User getUserById(Long usId) {
        return userEJB.findByUsId(usId);
    }

    @Override
    public List<User> getUsersByParametters(Map<String, Object> filters, int first, int limit) {
       return userEJB.findByParametters(filters, first, limit,Rol.admin);
    }

    @Override
    public long getUsersByParamettersCount(Map<String, Object> filters) {
       return userEJB.findByParamettersCount(filters,Rol.admin);
    }
    
    public void onRowSelect(SelectEvent event) {
        try {
            User user = (User)event.getObject();
            String uri = Util.projectPath+"/sAdmin/admin/admin.xhtml?i=0&u="+user.getUsId();
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(AdminsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goAddAdmin()
    {
        try {
            String uri = Util.projectPath+"/sAdmin/admin/addAdmin.xhtml?i=0";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(AdminsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
