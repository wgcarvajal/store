package com.store.controllers.products;

import com.store.controllers.util.Util;
import com.store.entities.Price;
import com.store.entities.Product;
import com.store.entities.Provider;
import com.store.entities.Provides;
import com.store.facade.PriceFacade;
import com.store.facade.ProductFacade;
import com.store.facade.ProvidesFacade;
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

@ManagedBean(name = "productController")
@ViewScoped
public class ProductController implements Serializable 
{
    private Product product;
    private Provider provider;
    private String barCode;
    @EJB private ProductFacade productEJB;
    @EJB private PriceFacade priceEJB;
    @EJB private ProvidesFacade providesEJB;
    
    @PostConstruct
    public void init()
    {
        
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String prodId = paramMap.get("p");
        
        if (prodId != null && !prodId.equals("")) {
            try {
                Long id = Long.parseLong(prodId);
                product = productEJB.find(id);
                if (product == null) {
                    goProducts();
                }
                else
                {
                    initValues();
                }
            } catch (NumberFormatException e) {
                goProducts();
            }
        } else {
            goProducts();
        }
    }
    
    public void initValues()
    {
        Provides provides = providesEJB.findCurrentByProdId(product.getProdId());
        if(provides!=null)
        {
            provider = provides.getProvId();
        }
    }
    
    public Product getProduct()
    {
        return product;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
    
    
    
    private void goProducts() {
        try {
            String uri = Util.projectPath+"/admin/product/products.xhtml?i=1";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String price()
    {
        Price price = priceEJB.findCurrentByProdId(product.getProdId());
        if(price!=null)
        {
            return Util.getFormatPrice(price.getPriceValue());
        }
        return "0";
    }
    
    public Provider getProvider()
    {
        return provider;
    }
    
    public void editBarCode()
    {
        barCode = product.getProdBarCode();
        Util.update(":formEditBarCode");
        Util.openDialog("editBarCodeDialog");
    }
    
    public void okEditBarCode()
    {
        if(!barCode.equals(product.getProdBarCode()))
        {
            if(barCode.length()>50)
            {
                Util.addErrorMessage(String.
                        format(ResourceBundle.getBundle("/Bundle").
                                getString("BrandSavedSuccessfully"),50));
            }
            else
            {
               if(productEJB.barcodeAlreadyExists(barCode))
               {
                   Util.addErrorMessage(ResourceBundle.
                           getBundle("/Bundle").
                           getString("UniqueFieldAlredyExists"));
               }
               else
               {
                   product.setProdBarCode(barCode);
                   productEJB.edit(product);
                   Util.addInfoMessage(ResourceBundle.
                           getBundle("/Bundle").
                           getString("EditSuccessfull"));
                   
                   Util.update(":formProduct:panelProduct");
                   Util.update(":formProduct:messageGrowl");
                   Util.closeDialog("editBarCodeDialog");
               }
            }
        }
        else
        {
            Util.closeDialog("editBarCodeDialog");
        }
    }
    
    
    
}
 