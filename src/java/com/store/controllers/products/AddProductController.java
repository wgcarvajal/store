/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.products;

import com.store.controllers.util.Util;
import com.store.entities.Brand;
import com.store.entities.Category;
import com.store.entities.Price;
import com.store.entities.Product;
import com.store.entities.Provider;
import com.store.entities.Provides;
import com.store.entities.Unity;
import com.store.facade.BrandFacade;
import com.store.facade.CategoryFacade;
import com.store.facade.PriceFacade;
import com.store.facade.ProductFacade;
import com.store.facade.ProviderFacade;
import com.store.facade.ProvidesFacade;
import com.store.facade.UnityFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "addProductController")
@ViewScoped
public class AddProductController implements Serializable 
{
    private String barCode;
    private String name;
    private String price;
    private String unitValue;
    private String stock;
    private String baseNumber;
    private Brand brand;
    private Category category;
    private Unity unity;
    private Provider provider;
    private List<Brand> brands;
    private List<Category> categories;
    private List<Unity> unities;
    private List<Provider> providers;
    @EJB private ProductFacade productEJB;
    @EJB private BrandFacade brandEJB;
    @EJB private CategoryFacade categoryEJB;
    @EJB private UnityFacade unityEJB;
    @EJB private ProviderFacade providerEJB;
    @EJB private PriceFacade priceEJB;
    @EJB private ProvidesFacade providesEJB;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(String baseNumber) {
        this.baseNumber = baseNumber;
    }
    
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Brand> getBrands() {
        if(brands==null)
        {
            brands = brandEJB.findAll();
        }
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getCategories() {
        if(categories == null)
        {
            categories = categoryEJB.findAll();
        }
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Unity getUnity() {
        return unity;
    }

    public void setUnity(Unity unity) {
        this.unity = unity;
    }

    public List<Unity> getUnities() {
        if(unities == null)
        {
            unities = unityEJB.findAll();
        }
        return unities;
    }

    public void setUnities(List<Unity> unities) {
        this.unities = unities;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public List<Provider> getProviders() {
        if(providers == null)
        {
            providers = providerEJB.findAll();
        }
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }
    
    public void add()
    {
        Date date = new Date();
        Product product = new Product();
        product.setProdBarCode(barCode);
        product.setProdName(Util.formatText(name));
        product.setBrandId(brand);
        product.setCatId(category);
        product.setUniId(unity);
        product.setProdUnitValue(Integer.parseInt(unitValue));
        
        if(stock!=null && !stock.isEmpty())
            product.setProdStock(Integer.parseInt(stock));
        else
            product.setProdStock(0);
        if(baseNumber!=null && !baseNumber.isEmpty())
            product.setProdBaseNumber(Integer.parseInt(baseNumber));
        else
            product.setProdBaseNumber(0);
        
        productEJB.create(product);
        
        Price p = new Price();
        p.setProdId(product);
        p.setPriceValue(Integer.parseInt(price));
        p.setPriceInitialDate(date);
        priceEJB.create(p);
        
        if(provider != null)
        {
            Provides provides = new Provides();
            provides.setProdId(product);
            provides.setProvId(provider);
            provides.setProvidesInitialDate(date);
            providesEJB.create(provides);
        }
        cleanFields();
        showMessageSuccessfull();
    }
    
    public void cancel()
    {
        System.out.println("entro");
        goProducts();
    }
    
    private void showMessageSuccessfull()
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                ResourceBundle.getBundle("/Bundle").getString("Info")+":", 
                ResourceBundle.getBundle("/Bundle").getString("ProductAddSuccessfull")));
        PrimeFaces.current().ajax().update("form"); 
    }
    
    private void cleanFields()
    {
        barCode = null;
        name = null;
        price = null;
        unitValue = null;
        stock = null;
        baseNumber = null;
        brand = null;
        category = null;
        unity = null;
        provider = null;
    }
    
    private void goProducts()
    {
        try {
            String uri = Util.projectPath+"/admin/product/products.xhtml?i=1";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
