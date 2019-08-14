package com.store.controllers;

import com.store.entities.Category;
import com.store.facade.CategoryFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "categoryController")
@ViewScoped
public class CategoryController implements Serializable {

    @EJB 
    private CategoryFacade categoryEJB;
    private String nombre;
    private List<Category> categories;
    private String category;
    private Category categorySelected;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public List<Category> getCategories()
    {
        if(categories==null)
        {
            categories = categoryEJB.findAllOderByName("catName");
        }
        return categories;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    public void openRegisterCategoryDialog() {
        this.nombre = "";
        PrimeFaces.current().executeScript("PF('addCategoryDialog').show()");
    }
    
    public void saveCategories()
    {
        if(!nombre.equals(""))
        {
         nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1).toLowerCase();
         if(!categoryEJB.existCategory(nombre))
         {
             Category categoria = new Category();
             categoria.setCatName(nombre);
             categoryEJB.create(categoria);
             categories = null;
             nombre = "";
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", ResourceBundle.getBundle("/Bundle").getString("CategorySavedSuccessfully")));
             PrimeFaces.current().ajax().update("categoryForm:categoryPanel");
         }
         else
           {
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", ResourceBundle.getBundle("/Bundle").getString("CategorySavedError")));
              PrimeFaces.current().ajax().update("categoryForm:categoryPanel"); 
           }
        }
    }
    
    public void openEditarCategoryDialog(Category category)
    {         
       this.nombre = category.getCatName();
       this.categorySelected = category;
       PrimeFaces.current().executeScript("PF('editCategoryDialog').show()");
    }
    
    
    public void openDeleteCategoryDialog(Category category)
    {  
       this.categorySelected = category;
       FacesContext.getCurrentInstance().addMessage("deleteMessage", new FacesMessage(FacesMessage.SEVERITY_WARN, "", ResourceBundle.getBundle("/Bundle").getString("YouWantToDeleteTheCategory")+" "+category.getCatName()+"?"));
       PrimeFaces.current().executeScript("PF('deteletCategoryDialog').show()");
    }
    
    
    public void searchCategory()
    {
        categories = categoryEJB.findByColumn(category);
    }
    
    public void editCategory()
    {
        if(!nombre.equals(""))
        {
           nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1).toLowerCase();
           if(!nombre.equals(categorySelected.getCatName()))
           {
               if(!categoryEJB.existCategory(nombre))
                {
                    categorySelected.setCatName(nombre);
                    categoryEJB.edit(categorySelected);
                    categories = null;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", ResourceBundle.getBundle("/Bundle").getString("CategoryEditSuccessfully")));
                    PrimeFaces.current().ajax().update("editCategoryForm:editCategoryPanel");
                }
                else
                {
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", ResourceBundle.getBundle("/Bundle").getString("CategoryEditError")));
                   PrimeFaces.current().ajax().update("editCategoryForm:editCategoryPanel"); 
                }
           }
        }
    }
    
    public void deleteCategory()
    {
        categories=null;
        if(!categoryEJB.isCategoryUsed(categorySelected.getCatId()))
        {
            categoryEJB.remove(categorySelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", ResourceBundle.getBundle("/Bundle").getString("CategoryDeletedSuccessfully")));
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ResourceBundle.getBundle("/Bundle").getString("CategoryDeletedError")));
        }
        PrimeFaces.current().executeScript("PF('deteletCategoryDialog').hide()");
        PrimeFaces.current().executeScript("PF('deteletCategoryResultDialog').show()");
    
    }

}
