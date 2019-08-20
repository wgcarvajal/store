/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.client;

import com.store.controllers.util.Util;
import com.store.entities.Client;
import com.store.facade.ClientFacade;
import com.store.lazydatamodel.LazyClientDataModel;
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
@ManagedBean(name = "clientsController")
@ViewScoped
public class ClientsController implements Serializable,LazyClientDataModel.OnLazyClientDataModel 
{
    @EJB private ClientFacade clientEJB;
    private LazyDataModel<Client> lazyModel;
    private Client selectedClient;

    @PostConstruct
    public void init() {
        lazyModel = new LazyClientDataModel(this);
    }
    
    public LazyDataModel<Client> getLazyModel() {
        return lazyModel;
    }

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }
    
    @Override
    public Client getClientById(Long cliId) {
       return clientEJB.findByProdId(cliId);
    }

    @Override
    public List<Client> getClientsByParametters(Map<String, Object> filters, int first, int limit) {
        return clientEJB.findByParametters(filters, first, limit);
    }

    @Override
    public long getClientsByParamettersCount(Map<String, Object> filters) {
        return clientEJB.findByParamettersCount(filters);
    }
    
    public void onRowSelect(SelectEvent event) {
        try {
            Client client = (Client)event.getObject();
            String uri = Util.projectPath+"/admin/client/client.xhtml?i=6&c="+client.getCliId();
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goAddClient()
    {
        try {
            String uri = Util.projectPath+"/admin/client/addClient.xhtml?i=6";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
