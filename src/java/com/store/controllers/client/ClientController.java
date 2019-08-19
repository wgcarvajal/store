package com.store.controllers.client;


import com.store.controllers.util.Util;
import com.store.entities.Client;
import com.store.facade.ClientFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "clientController")
@ViewScoped
public class ClientController implements Serializable {

    private Client client;
    
    @EJB private ClientFacade clientEJB;
    
    @PostConstruct
    public void init()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String cliId = paramMap.get("c");
        
        if (cliId != null && !cliId.equals("")) {
            try {
                Long id = Long.parseLong(cliId);
                client = clientEJB.find(id);
                if (client == null) {
                    goClients();
                }
            } catch (NumberFormatException e) {
                goClients();
            }
        } else {
            goClients();
        }
    }
    
    private void goClients() {
        try {
            String uri = Util.projectPath+"/admin/client/clients.xhtml?i=6";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Client getClient()
    {
        return client;
    }

}
