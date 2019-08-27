package com.store.controllers.client;


import com.store.controllers.util.Util;
import com.store.entities.Client;
import com.store.facade.ClientFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;
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
    private String identification;
    private String name;
    private String lastName;
    private String address;
    private String phones;
    private String credit;
    
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

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
    
    public void editIdentification()
    {
        identification = client.getCliIdentity();
        Util.update(":formEditIdentification");
        Util.openDialog("editIdentificationDialog");
    }
    
    public void editName()
    {
        name = client.getCliName();
        Util.update(":formEditName");
        Util.openDialog("editNameDialog");
    }
    
    public void editLastName()
    {
        lastName = client.getCliLastName();
        Util.update(":formEditLastName");
        Util.openDialog("editLastNameDialog");
    }
    
    public void editAddress()
    {
        address = client.getCliAddress();
        Util.update(":formEditAddress");
        Util.openDialog("editAddressDialog");
    }
    
    public void editPhones()
    {
        phones = client.getCliPhones();
        Util.update(":formEditPhones");
        Util.openDialog("editPhonesDialog");
    }
    
    public void editCredit()
    {
        credit = client.getCliCredit()?"Si":"No";
        Util.update(":formEditCredit");
        Util.openDialog("editCreditDialog");
    }
    
    public void okIdentification()
    {
        if(!identification.equals(client.getCliIdentity()))
        {
            if(identification.length()>50)
            {
                Util.addErrorMessage(String.
                        format(ResourceBundle.getBundle("/Bundle").
                                getString("TextLengthMax"),50),String.
                        format(ResourceBundle.getBundle("/Bundle").
                                getString("TextLengthMax"),50));
            }
            else
            {
               if(clientEJB.identificationAlreadyExists(identification))
               {
                   Util.addErrorMessage(ResourceBundle.
                           getBundle("/Bundle").
                           getString("UniqueFieldAlredyExists"),
                           ResourceBundle.
                           getBundle("/Bundle").
                           getString("UniqueFieldAlredyExists"));
               }
               else
               {
                   client.setCliIdentity(identification);
                   clientEJB.edit(client);
                   Util.addInfoMessage(ResourceBundle.
                           getBundle("/Bundle").
                           getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));
                   
                   Util.update(":formClient:panelClient");
                   Util.update(":formClient:messageGrowl");
                   Util.closeDialog("editIdentificationDialog");
               }
            }
        }
        else
        {
            Util.closeDialog("editIdentificationDialog");
        }
    }
    
    public void okEditName()
    {
        String formatName = Util.formatTextWithSapace(name);
        if(!formatName.equals(client.getCliName()))
        {
            client.setCliName(formatName);
            clientEJB.edit(client);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formClient:panelClient");
            Util.update(":formClient:messageGrowl");
            Util.closeDialog("editNameDialog");
        }
        else
        {
            Util.closeDialog("editNameDialog");
        }
    }
    
    public void okEditLastName()
    {
        String formatName = Util.formatTextWithSapace(lastName);
        if(!formatName.equals(client.getCliLastName()))
        {
            client.setCliLastName(formatName);
            clientEJB.edit(client);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formClient:panelClient");
            Util.update(":formClient:messageGrowl");
            Util.closeDialog("editLastNameDialog");
        }
        else
        {
            Util.closeDialog("editLastNameDialog");
        }
    }
    
    public void okEditAddress()
    {
        if(!address.equals(client.getCliAddress()))
        {
            client.setCliAddress(address);
            clientEJB.edit(client);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formClient:panelClient");
            Util.update(":formClient:messageGrowl");
            Util.closeDialog("editAddressDialog");
        }
        else
        {
            Util.closeDialog("editAddressDialog");
        }
    }
    
    public void okEditPhones()
    {
        if(!phones.equals(client.getCliPhones()))
        {
            client.setCliPhones(phones);
            clientEJB.edit(client);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formClient:panelClient");
            Util.update(":formClient:messageGrowl");
            Util.closeDialog("editPhonesDialog");
        }
        else
        {
            Util.closeDialog("editPhonesDialog");
        }
    }
    
    public void okEditCredit()
    {
        
        String c = client.getCliCredit()?"Si":"No";
        if(!c.equals(credit))
        {
            client.setCliCredit(credit.equals("Si"));
            clientEJB.edit(client);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formClient:panelClient");
            Util.update(":formClient:messageGrowl");
            Util.closeDialog("editCreditDialog");
        }
        else
        {
            Util.closeDialog("editCreditDialog");
        }
    }
    
}
