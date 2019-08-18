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
import com.store.facade.ProvidesFacade;
import com.store.facade.UnityFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
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

@ManagedBean(name = "productController")
@ViewScoped
public class ProductController implements Serializable 
{
    private Product product;
    private Provider provider;
    private String barCode;
    private String name;
    private Unity unity;
    private String unitValue;
    private Category category;
    private Brand brand;
    private Price price;
    private String priceValue;
    
    private List<Unity> unities;
    private List<Category> categories;
    private List<Brand> brands;
    
    @EJB private ProductFacade productEJB;
    @EJB private PriceFacade priceEJB;
    @EJB private ProvidesFacade providesEJB;
    @EJB private UnityFacade unityEJB;
    @EJB private CategoryFacade categoryEJB;
    @EJB private BrandFacade brandEJB;
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Unity getUnity() {
        return unity;
    }

    public void setUnity(Unity unity) {
        this.unity = unity;
    }

    public List<Unity> getUnities() {
        return unities;
    }

    public void setUnities(List<Unity> unities) {
        this.unities = unities;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public Price getPrice() {
        if(price == null)
        {
            price = priceEJB.findCurrentByProdId(product.getProdId());
        }
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }
    
    
    private void goProducts() {
        try {
            String uri = Util.projectPath+"/admin/product/products.xhtml?i=1";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String formatPrice(int price)
    {
        return Util.getFormatPrice(price);
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
    
    public void editName()
    {
        name = product.getProdName();
        Util.update(":formEditName");
        Util.openDialog("editNameDialog");
    }
    
    public void editPresentation()
    {
        unity = product.getUniId();
        unitValue = product.getProdUnitValue()+"";
        if(unities==null)
            unities = unityEJB.findAll();
        Util.update(":formPresentation");
        Util.openDialog("editPresentationDialog");
    }
    
    public void editCategory()
    {
        category = product.getCatId();
        if(categories==null)
            categories = categoryEJB.findAll();
        Util.update(":formCategory");
        Util.openDialog("editCategoryDialog");
    }
    
    public void editBrand()
    {
        brand = product.getBrandId();
        if(brands==null)
            brands = brandEJB.findAll();
        Util.update(":formBrand");
        Util.openDialog("editBrandDialog");
    }
    
    public void editPrice()
    {
        priceValue = price.getPriceValue()+"";
        Util.update(":formEditPrice");
        Util.openDialog("editPriceDialog");
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
    
    public void okEditName()
    {
        if(!Util.formatText(name).equals(product.getProdName()))
        {
            product.setProdName(Util.formatText(name));
            productEJB.edit(product);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formProduct:panelProduct");
            Util.update(":formProduct:messageGrowl");
            Util.closeDialog("editNameDialog");
        }
        else
        {
            Util.closeDialog("editNameDialog");
        }
    }
    
    public void okEditPresentation()
    {
        int uv = Integer.parseInt(unitValue);
        if(!product.getUniId().getUniId().equals(unity.getUniId()) || product.getProdUnitValue() != uv )
        {
            product.setUniId(unity);
            product.setProdUnitValue(uv);
            productEJB.edit(product);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formProduct:panelProduct");
            Util.update(":formProduct:messageGrowl");
            Util.closeDialog("editPresentationDialog");
        }
        else
        {
            Util.closeDialog("editPresentationDialog");
        }
    }
    
    public void okEditCategory()
    {
        if(!product.getCatId().getCatId().equals(category.getCatId()))
        {
            product.setCatId(category);
            productEJB.edit(product);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formProduct:panelProduct");
            Util.update(":formProduct:messageGrowl");
            Util.closeDialog("editCategoryDialog");
        }
        else
        {
            Util.closeDialog("editCategoryDialog");
        }
    }
    
    public void okEditBrand()
    {
        if(!product.getBrandId().getBrandId().equals(brand.getBrandId()))
        {
            product.setBrandId(brand);
            productEJB.edit(product);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formProduct:panelProduct");
            Util.update(":formProduct:messageGrowl");
            Util.closeDialog("editBrandDialog");
        }
        else
        {
            Util.closeDialog("editBrandDialog");
        }
    }
    
    public void okEditPrice()
    {
        int pv = Integer.parseInt(priceValue);
        if(price.getPriceValue() != pv)
        {
            Date date = new Date();
            price.setPriceFinalDate(date);
            priceEJB.edit(price);
            price = new Price();
            price.setProdId(product);
            price.setPriceValue(pv);
            price.setPriceInitialDate(date);
            priceEJB.create(price);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formProduct:panelProduct");
            Util.update(":formProduct:messageGrowl");
            Util.closeDialog("editPriceDialog");
        }
        else
        {
            Util.closeDialog("editPriceDialog");
        }
    }
}
 