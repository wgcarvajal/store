package com.store.controllers;


import com.store.entities.Brand;
import com.store.facade.BrandFacade;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "brandController")
@ViewScoped
public class BrandController implements Serializable {

    @EJB 
    private BrandFacade brandEJB;
    private String nombre;
    private List<Brand> brands;
    private String brand;
    private Brand brandSelected;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public List<Brand> getBrands()
    {
        if(brands==null)
        {
            brands = brandEJB.findAllOderByName("brandName");
        }
        return brands;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    public void openRegisterBrandDialog() {
        this.nombre = "";
        PrimeFaces.current().executeScript("PF('addBrandDialog').show()");
    }
    
    public void saveBrands()
    {
        if(!nombre.equals(""))
        {
         nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1).toLowerCase();
         if(!brandEJB.existBrand(nombre))
         {
             Brand b = new Brand();
             b.setBrandName(nombre);
             brandEJB.create(b);
             brands = null;
             nombre = "";
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", ResourceBundle.getBundle("/Bundle").getString("BrandSavedSuccessfully")));
             PrimeFaces.current().ajax().update("brandForm:brandPanel");
         }
         else
           {
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", ResourceBundle.getBundle("/Bundle").getString("BrandSavedError")));
              PrimeFaces.current().ajax().update("brandForm:brandPanel"); 
           }
        }
    }
    
    public void openEditarBrandDialog(Brand brand)
    {
       this.nombre = brand.getBrandName();
       this.brandSelected = brand;
       PrimeFaces.current().executeScript("PF('editBrandDialog').show()");
    }
    
    
    public void openDeleteBrandDialog(Brand brand)
    {
       this.brandSelected = brand;
       FacesContext.getCurrentInstance().addMessage("deleteMessage", new FacesMessage(FacesMessage.SEVERITY_WARN, "", ResourceBundle.getBundle("/Bundle").getString("YouWantToDeleteTheBrand")+" "+brand.getBrandName()+"?"));
       PrimeFaces.current().executeScript("PF('deteletBrandDialog').show()");
    }
    
    
    public void searchBrand()
    {
        brands = brandEJB.findByColumn(brand);
    }
    
    public void editBrand()
    {
        if(!nombre.equals(""))
        {
           nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1).toLowerCase();
           if(!nombre.equals(brandSelected.getBrandName()))
           {
               if(!brandEJB.existBrand(nombre))
                {
                    brandSelected.setBrandName(nombre);
                    brandEJB.edit(brandSelected);
                    brands = null;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", ResourceBundle.getBundle("/Bundle").getString("BrandEditSuccessfully")));
                    PrimeFaces.current().ajax().update("editBrandForm:editBrandPanel");
                }
                else
                {
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", ResourceBundle.getBundle("/Bundle").getString("BrandEditError")));
                   PrimeFaces.current().ajax().update("editBrandForm:editBrandPanel"); 
                }
           }
        }
    }
    
    public void deleteBrand()
    {
        brands=null;
        if(!brandEJB.isBrandUsed(brandSelected.getBrandId()))
        {
            brandEJB.remove(brandSelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", ResourceBundle.getBundle("/Bundle").getString("BrandDeletedSuccessfully")));
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ResourceBundle.getBundle("/Bundle").getString("BrandDeletedError")));
        }
        PrimeFaces.current().executeScript("PF('deteletBrandDialog').hide()");
        PrimeFaces.current().executeScript("PF('deteletBrandResultDialog').show()");
    
    }

}
