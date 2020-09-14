/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.products;

import com.store.controllers.util.Util;
import com.store.entities.Price;
import com.store.entities.Product;
import com.store.facade.PriceFacade;
import com.store.facade.ProductFacade;
import com.store.lazydatamodel.LazyProductDataModel;
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
@ManagedBean(name = "productsController")
@ViewScoped
public class ProductsController implements Serializable,LazyProductDataModel.OnLazyProductDataModel 
{
    @EJB private ProductFacade productEJB;
    @EJB private PriceFacade priceEJB;
    private LazyDataModel<Product> lazyModel;
    
    private Product selectedProduct;
    
    @PostConstruct
    public void init() {
        lazyModel = new LazyProductDataModel(this);
    }

    @Override
    public Product getProductById(Long prodId) {
        return productEJB.findByProdId(prodId);
    }

    @Override
    public List<Product> getProductsByParametters(Map<String,Object> filters,int first, int limit){
        return productEJB.findByParametters(filters,first,limit);
    }

    @Override
    public long getProductsByParamettersCount(Map<String, Object> filters) {
       return productEJB.findByParamettersCount(filters);
    }
    
    
    
    public LazyDataModel<Product> getLazyModel() {
        return lazyModel;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }
    
    public String price(Product product)
    {
        Price price = priceEJB.findCurrentByProdId(product.getProdId());
        if(price!=null)
        {
            return Util.getFormatPrice(price.getPriceValue());
        }
        return "0";
    }
    
    public void onRowSelect(SelectEvent event) {
        try {
            Product product = (Product)event.getObject();
            String uri = Util.projectPath+"/admin/product/product.xhtml?i=1&p="+product.getProdId();
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goAddProduct()
    {
        try {
            String uri = Util.projectPath+"/admin/product/addProduct.xhtml?i=1";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
}
