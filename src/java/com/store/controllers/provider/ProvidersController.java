/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.provider;

import com.store.controllers.util.Util;
import com.store.entities.Provider;
import com.store.facade.ProviderFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "providersController")
@ViewScoped
public class ProvidersController implements Serializable{
    
    @EJB
    private ProviderFacade providerEJB;
    private List<Provider> providers;
    private Provider selectedProvider;
    
    
    @PostConstruct
    public void init() {
        
    }

    public List<Provider> getProviders() {
        if(providers == null || providers.isEmpty())
        {
            providers = providerEJB.findAllProviders();
        }
        return providers;
    }

    public Provider getSelectedProvider() {
        return selectedProvider;
    }

    public void setSelectedProvider(Provider selectedProvider) {
        this.selectedProvider = selectedProvider;
    }
    
    public void editProvider(Provider provider) {
        try {
            String uri = Util.projectPath+"/admin/provider/provider.xhtml?i=4&p="+provider.getProvId();
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(ProvidersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goAddProvider()
    {
        try {
            String uri = Util.projectPath+"/admin/provider/addProvider.xhtml?i=4";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(ProvidersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
