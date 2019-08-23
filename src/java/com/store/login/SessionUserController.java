/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.login;

import com.store.controllers.cashregister.CashRegisterController;
import com.store.controllers.util.Rol;
import com.store.controllers.util.Util;
import com.store.entities.User;
import com.store.entities.Usergroup;
import com.store.facade.UserFacade;
import com.store.facade.UsergroupFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "sessionUserController")
@SessionScoped
public class SessionUserController implements Serializable,CashRegisterController.OnSesionUserController {

    @EJB
    private UsergroupFacade usergroupEJB;
    @EJB
    private UserFacade userEJB;
    String userName;
    String password;

    private Boolean thereIsSession;
    private boolean sessionError;
    private User user;

    public SessionUserController() {
        thereIsSession = false;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getThereIsSession() {
        return thereIsSession;
    }

    public void setThereIsSession(Boolean thereIsSession) {
        this.thereIsSession = thereIsSession;
    }

    public boolean isSessionError() {
        return sessionError;
    }

    public void setSessionError(boolean sessionError) {
        this.sessionError = sessionError;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

   public void sessionActive() throws IOException, ServletException
   {
       FacesContext fc = FacesContext.getCurrentInstance();
       HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
       if (req.getUserPrincipal() != null) {
          userName = req.getUserPrincipal().getName();
          User u = userEJB.findByUsUserName(userName);
          if(u!=null && u.getUsActive())
          {
            redirectView();
          }
          else
          {
              logout();
          }
       }
   }

    public void login() throws IOException, ServletException {
        User u = userEJB.findByUsUserName(userName);
        if(u!=null && u.getUsActive())
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
            if (req.getUserPrincipal() == null) {
                try {
                    req.login(this.userName, this.password);

                    req.getServletContext().log(ResourceBundle.getBundle("/Bundle").
                            getString("SuccessfulAuthentication"));
                    redirectView();

                } catch (ServletException e) {

                    this.sessionError = true;
                }
            }
        }
        else
        {
            this.sessionError = true;
        }
    }
    
    private void redirectView() throws IOException
    {
        thereIsSession = true;
        this.sessionError = false;
        Usergroup usergroup = usergroupEJB.findByUsUserName(userName);
        if(usergroup.getGrouId().getGrouId().equals(Rol.sAdmin))
        {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .redirect(Util.projectPath+"/sAdmin/admin/admins.xhtml?i=0");
        }
        else if(usergroup.getGrouId().getGrouId().equals(Rol.admin))
        {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .redirect(Util.projectPath+"/admin/index.xhtml?i=0");
        }
        else if(usergroup.getGrouId().getGrouId().equals(Rol.cashier))
        {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .redirect(Util.projectPath+"/cashier/index.xhtml");
        }
    }

    public void logout() throws IOException, ServletException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        try {
            req.logout();
            req.getSession().invalidate();
            fc.getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect(Util.projectPath);

        } catch (ServletException e) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILED", "Logout failed on backend"));
        }

    }

    public boolean esusuarioSinSession() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (req.getUserPrincipal() == null) {
            return true;
        }
        return false;
    }

    public boolean esusuarioConSession() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (req.getUserPrincipal() == null) {
            return false;

        } else {
            /*if(this.usuarioGrupoEJB.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getMuUsuariogrupoPK().getGruId().equals(2))
            {
                return true;
            }*/
            return false;
        }

    }

    public boolean esAdministrador() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (req.getUserPrincipal() == null) {
            return false;

        } else {
            /* if(this.usuarioGrupoEJB.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuariogrupoPK().getGruid().equals("admin"))
            {
                return true;
            }*/
            return false;
        }

    }

    public String getUserName() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (req.getUserPrincipal() == null) {
            userName = null;
            return "";
        } else {
            User u = userEJB.findByUsUserName(req.getUserPrincipal().getName());
            if (u == null) {
                userName = null;
                return "";
            } else {
                userName = u.getUsUserName();
                return userName;
            }

        }
    }

    @Override
    public boolean isCorrectPassword(String password) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        User u = userEJB.findByUsUserName(req.getUserPrincipal().getName());
        return u.getUsPassword().equals(password);
    }

    @Override
    public User getCurrentCashier() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        return  userEJB.findByUsUserName(req.getUserPrincipal().getName());
    }
        
}
