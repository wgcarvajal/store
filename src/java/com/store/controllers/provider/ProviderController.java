package com.store.controllers.provider;

import com.store.controllers.util.Util;
import com.store.entities.Provider;
import com.store.facade.ProviderFacade;
import com.store.facade.ProvidesFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "providerController")
@ViewScoped
public class ProviderController implements Serializable 
{
    private Provider provider;
    private String name;
    private String address;
    private String phone;
    private String employee;
    private String nit;
    private String rut;
    private String web;
    private List provides;
    
    @EJB private ProviderFacade providerEJB;
    @EJB private ProvidesFacade providesEJB;
    
    @PostConstruct
    public void init()
    {
        
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String provId = paramMap.get("p");
        
        if (provId != null && !provId.equals("")) {
            try {
                Long id = Long.parseLong(provId);
                provider = providerEJB.findProvider(id);
                if (provider == null) {
                    goProviders();
                }
            } catch (NumberFormatException e) {
                goProviders();
            }
        } else {
            goProviders();
        }
    }

    
    public Provider getProvider() {
        return provider;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public List getProvides() {
        if(provides==null)
        {
            provides = providesEJB.findProductsByProvId(provider.getProvId());
        }
        return provides;
    }
    
    
    
    private void goProviders() {
        try {
            String uri = Util.projectPath+"/admin/provider/providers.xhtml?i=4";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(ProviderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void editName()
    {
        name = provider.getProvName();
        Util.update(":formEditName");
        Util.openDialog("editNameDialog");
    }
    
    
    
    public void okEditName()
    {
        if(!Util.formatText(name).equals(provider.getProvName()))
        {
            provider.setProvName(Util.formatText(name));
            providerEJB.edit(provider);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formProvider:panelProvider");
            Util.update(":formProvider:messageGrowl");
            Util.closeDialog("editNameDialog");
        }
        else
        {
            Util.closeDialog("editNameDialog");
        }
    }
    
    
    public void editAddress()
    {
        address = provider.getProvAddress();
        Util.update(":formEditAddress");
        Util.openDialog("editAddressDialog");
    }
    
    public void okEditAddress()
    {
        if(!Util.formatText(address).equals(provider.getProvAddress()))
        {
            provider.setProvAddress(Util.formatText(address));
            providerEJB.edit(provider);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formProvider:panelProvider");
            Util.update(":formProvider:messageGrowl");
            Util.closeDialog("editAddressDialog");
        }
        else
        {
            Util.closeDialog("editAddressDialog");
        }
    }
    
     public void editPhones()
    {
        phone = provider.getProvPhones();
        Util.update(":formEditPhones");
        Util.openDialog("editPhonesDialog");
    }
    
    
    public void okEditPhones()
    {
        if(!phone.equals(provider.getProvPhones()))
        {
            provider.setProvPhones(phone);
            providerEJB.edit(provider);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formProvider:panelProvider");
            Util.update(":formProvider:messageGrowl");
            Util.closeDialog("editPhonesDialog");
        }
        else
        {
            Util.closeDialog("editPhonesDialog");
        }
    }
    
    public void editEmployee()
    {
        employee = provider.getProvEmployeeName();
        Util.update(":formEditEmployee");
        Util.openDialog("editEmployeeDialog");
    }
    
    
    public void okEditEmployee()
    {
        
        if(employee==null || employee.isEmpty())
        {
            provider.setProvEmployeeName(null);
        }
        else
        {
            if(!Util.formatText(employee).equals(provider.getProvEmployeeName()))
            {
                provider.setProvEmployeeName(Util.formatText(employee));
            }
        }
        
        providerEJB.edit(provider);
        Util.addInfoMessage(ResourceBundle.
                getBundle("/Bundle").
                getString("EditSuccessfull"),ResourceBundle.
                getBundle("/Bundle").
                getString("EditSuccessfull"));

        Util.update(":formProvider:panelProvider");
        Util.update(":formProvider:messageGrowl");
        Util.closeDialog("editEmployeeDialog");
        
    }
    
    public void editNit()
    {
        nit = provider.getProvNit();
        Util.update(":formEditNit");
        Util.openDialog("editNitDialog");
    }
    
    
    public void okEditNit()
    {
        
        if(nit==null || nit.isEmpty())
        {
            provider.setProvNit(null);
        }
        else
        {
            if(!nit.equals(provider.getProvNit()))
            {
                provider.setProvNit(nit);
            }
        }
        
        providerEJB.edit(provider);
        Util.addInfoMessage(ResourceBundle.
                getBundle("/Bundle").
                getString("EditSuccessfull"),ResourceBundle.
                getBundle("/Bundle").
                getString("EditSuccessfull"));

        Util.update(":formProvider:panelProvider");
        Util.update(":formProvider:messageGrowl");
        Util.closeDialog("editNitDialog");
        
    }
    
    public void editRut()
    {
        rut = provider.getProvRut();
        Util.update(":formEditRut");
        Util.openDialog("editRutDialog");
    }
    
    
    public void okEditRut()
    {
        
        if(rut==null || rut.isEmpty())
        {
            provider.setProvRut(null);
        }
        else
        {
            if(!rut.equals(provider.getProvRut()))
            {
                provider.setProvRut(rut);
            }
        }
        
        providerEJB.edit(provider);
        Util.addInfoMessage(ResourceBundle.
                getBundle("/Bundle").
                getString("EditSuccessfull"),ResourceBundle.
                getBundle("/Bundle").
                getString("EditSuccessfull"));

        Util.update(":formProvider:panelProvider");
        Util.update(":formProvider:messageGrowl");
        Util.closeDialog("editRutDialog");
        
    }
    
    public void editWeb()
    {
        web = provider.getProvWeb();
        Util.update(":formEditWeb");
        Util.openDialog("editWebDialog");
    }
    
    
    public void okEditWeb()
    {
        
        if(web==null || web.isEmpty())
        {
            provider.setProvRut(null);
        }
        else
        {
            if(!web.equals(provider.getProvWeb()))
            {
                provider.setProvWeb(web);
            }
        }
        
        providerEJB.edit(provider);
        Util.addInfoMessage(ResourceBundle.
                getBundle("/Bundle").
                getString("EditSuccessfull"),ResourceBundle.
                getBundle("/Bundle").
                getString("EditSuccessfull"));

        Util.update(":formProvider:panelProvider");
        Util.update(":formProvider:messageGrowl");
        Util.closeDialog("editWebDialog");
        
    }
    
}
 