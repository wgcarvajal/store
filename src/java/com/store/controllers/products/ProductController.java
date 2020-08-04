package com.store.controllers.products;

import com.store.controllers.util.Util;
import com.store.entities.Brand;
import com.store.entities.Category;
import com.store.entities.Owner;
import com.store.entities.Price;
import com.store.entities.Pricepurchase;
import com.store.entities.Product;
import com.store.entities.Productimage;
import com.store.entities.Producttype;
import com.store.entities.Provider;
import com.store.entities.Provides;
import com.store.entities.Unity;
import com.store.facade.BrandFacade;
import com.store.facade.CategoryFacade;
import com.store.facade.OwnerFacade;
import com.store.facade.PriceFacade;
import com.store.facade.PricepurchaseFacade;
import com.store.facade.ProductFacade;
import com.store.facade.ProductimageFacade;
import com.store.facade.ProducttypeFacade;
import com.store.facade.ProviderFacade;
import com.store.facade.ProvidesFacade;
import com.store.facade.UnityFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

@ManagedBean(name = "productController")
@ViewScoped
public class ProductController implements Serializable 
{
    private Product product;
    private Provider provider;
    private String barCode;
    private String name;
    private String billName;
    private String stock;
    private String baseNumber;
    private String composite;
    private String compositeValue;
    private Unity unity;
    private String unitValue;
    private Category category;
    private Brand brand;
    private Owner owner;
    private Price price;
    private Pricepurchase pricepurchase;
    private String priceValue;
    private String pricepurchaseValue;
    private String iva;
    private Producttype producttype;
    private Provides currentProvides;
    private Productimage productimage;
    private Product productComposite;
    
    private List<Unity> unities;
    private List<Category> categories;
    private List<Brand> brands;
    private List<Owner> owners;
    private List<Producttype> producttypes;
    private List<Provider> providers;
    private List<Productimage> productimages;
    private List<Product>productsComposite;
    
    private boolean editImage = false;
    
    @EJB private ProductFacade productEJB;
    @EJB private PriceFacade priceEJB;
    @EJB private ProvidesFacade providesEJB;
    @EJB private UnityFacade unityEJB;
    @EJB private CategoryFacade categoryEJB;
    @EJB private BrandFacade brandEJB;
    @EJB private ProducttypeFacade producttypeEJB;
    @EJB private ProviderFacade providerEJB;
    @EJB private ProductimageFacade productimageEJB;
    @EJB private PricepurchaseFacade pricepurchaseEJB;
    @EJB private OwnerFacade ownerEJB;
    
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
        currentProvides = providesEJB.findCurrentByProdId(product.getProdId());
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

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }
    
    public Unity getUnity() {
        return unity;
    }

    public void setUnity(Unity unity) {
        this.unity = unity;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }
    
    public String getComposite() {
        return composite;
    }

    public void setComposite(String composite) {
        this.composite = composite;
    }

    public String getCompositeValue() {
        return compositeValue;
    }

    public void setCompositeValue(String compositeValue) {
        this.compositeValue = compositeValue;
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }
    
    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
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

    public Pricepurchase getPricepurchase() {
        if(pricepurchase == null)
        {
            pricepurchase = pricepurchaseEJB.findCurrentByProdId(product.getProdId());
        }
        return pricepurchase;
    }

    public List<Product> getProductsComposite() {
        productsComposite = productEJB.findAllWithoutComposite();
        return productsComposite;
    }

    public void setProductsComposite(List<Product> productsComposite) {
        this.productsComposite = productsComposite;
    }
    
    

    public void setPricepurchase(Pricepurchase pricepurchase) {
        this.pricepurchase = pricepurchase;
    }

    public Product getProductComposite() {
        return productComposite;
    }

    public void setProductComposite(Product productComposite) {
        this.productComposite = productComposite;
    }
    
    

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }

    public String getPricepurchaseValue() {
        return pricepurchaseValue;
    }

    public void setPricepurchaseValue(String pricepurchaseValue) {
        this.pricepurchaseValue = pricepurchaseValue;
    }
    public Producttype getProducttype() {
        return producttype;
    }

    public void setProducttype(Producttype producttype) {
        this.producttype = producttype;
    }

    public List<Producttype> getProducttypes() {
        return producttypes;
    }

    public void setProducttypes(List<Producttype> producttypes) {
        this.producttypes = producttypes;
    }

    public Provides getCurrentProvides() {
        return currentProvides;
    }

    public void setCurrentProvides(Provides currentProvides) {
        this.currentProvides = currentProvides;
    }

    public boolean isEditImage() {
        return editImage;
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
    
    public void editBillName()
    {
        billName = product.getProdNameBill();
        Util.update(":formEditNameBill");
        Util.openDialog("editNameBillDialog");
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
    
    public void editOwner()
    {
        owner = product.getOwnId();
        if(owners==null)
            owners = ownerEJB.findAll();
        Util.update(":formOwner");
        Util.openDialog("editOwnerDialog");
    }
    
    public void editIVA()
    {
        iva=product.getProdIva() +"";
        Util.update(":formEditIVA");
        Util.openDialog("editIVADialog");
    }
    
    public void editPricepurchase()
    {
        pricepurchaseValue=null;
        if(pricepurchase!=null)
        {
            pricepurchaseValue = pricepurchase.getPricePurValue()+"";
        }
        Util.update(":formEditPricepurchase");
        Util.openDialog("editPricepurchaseDialog");
    }
    
    public void editPrice()
    {
        priceValue = price.getPriceValue()+"";
        Util.update(":formEditPrice");
        Util.openDialog("editPriceDialog");
    }
    
    public void editProducttype()
    {
        producttype = product.getProdtypeId();
        if(producttypes == null)
            producttypes = producttypeEJB.findAll();
        Util.update(":formEditProducttype");
        Util.openDialog("editProducttypeDialog");
    }
    
    public void editBaseNumber()
    {
        baseNumber = product.getProdBaseNumber()+"";
        Util.update(":formEditBaseNumber");
        Util.openDialog("editBaseNumberDialog");
    }
    
    public void editComposition()
    {
        if(product.getProdComposition()!=null)
        {
            composite = "1";
            productComposite = product.getProdComposition();
        }
        else
        {
            composite = "0";
        }
        Util.update(":formEditChangeComposition");
        Util.openDialog("changeComposition");
    }
    
    public void editCompositionOk()
    {
        product.setProdStock(0);
        product.setProdBaseNumber(0);
        product.setProdCompositionValue(null);
        product.setProdComposition(null);
        if(composite.equals("1"))
        {
            product.setProdCompositionValue(0);
            product.setProdComposition(productComposite);
        }
        
        productEJB.edit(product);
        Util.update(":formProduct:panelProduct");
        Util.closeDialog("changeComposition");
    }
    
    public void changeComposition()
    {
        System.out.println("entro");
        Util.update("formEditChangeComposition:changeCompositionPanel");
    }
    
    public void editStock()
    {
        stock = product.getProdStock()+"";
        Util.update(":formEditStock");
        Util.openDialog("editStockDialog");
    }
    
    public void editCompositeValue()
    {
        compositeValue = product.getProdCompositionValue()+"";
        Util.update(":formEditCompositeValue");
        Util.openDialog("editCompositeValueDialog");
    }
    
    public void editProvider()
    {
       provider = null;
       if(currentProvides!=null)
       {
           provider = currentProvides.getProvId();
       }
       Util.update(":formEditProvider");
       Util.openDialog("editProviderDialog");
    }
    
    public void okEditBarCode()
    {
        if(!barCode.equals(product.getProdBarCode()))
        {
            if(barCode.length()>50)
            {
                Util.addErrorMessage(String.
                        format(ResourceBundle.getBundle("/Bundle").
                                getString("BrandSavedSuccessfully"),50),String.
                        format(ResourceBundle.getBundle("/Bundle").
                                getString("BrandSavedSuccessfully"),50));
            }
            else
            {
               if(productEJB.barcodeAlreadyExists(barCode))
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
                   product.setProdBarCode(barCode);
                   productEJB.edit(product);
                   Util.addInfoMessage(ResourceBundle.
                           getBundle("/Bundle").
                           getString("EditSuccessfull"),ResourceBundle.
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
                    getString("EditSuccessfull"),ResourceBundle.
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
    
    public void okEditNameBill()
    {
        if(!billName.equals(product.getProdNameBill()))
        {
            product.setProdNameBill(billName);
            productEJB.edit(product);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formProduct:panelProduct");
            Util.update(":formProduct:messageGrowl");
            Util.closeDialog("editNameBillDialog");
        }
        else
        {
            Util.closeDialog("editNameBillDialog");
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
                    getString("EditSuccessfull"),ResourceBundle.
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
    
    public void okEditProducttype()
    {
        if(!product.getProdtypeId().getProdtypeId().equals(producttype.getProdtypeId()))
        {
            product.setProdtypeId(producttype);
            productEJB.edit(product);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formProduct:panelProduct");
            Util.update(":formProduct:messageGrowl");
            Util.closeDialog("editProducttypeDialog");
        }
        else
        {
            Util.closeDialog("editProducttypeDialog");
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
                    getString("EditSuccessfull"),ResourceBundle.
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
                    getString("EditSuccessfull"),ResourceBundle.
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
    
    public void okEditOwner()
    {
        if(!product.getOwnId().getOwnId().equals(owner.getOwnId()))
        {
            product.setOwnId(owner);
            productEJB.edit(product);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formProduct:panelProduct");
            Util.update(":formProduct:messageGrowl");
            Util.closeDialog("editOwnerDialog");
        }
        else
        {
            Util.closeDialog("editOwnerDialog");
        }
    }
    
    public void okEditIVA()
    {
        int i = Integer.parseInt(iva);
        if(i != product.getProdIva())
        {
            product.setProdIva(i);
            productEJB.edit(product);
        }
        Util.addInfoMessage(ResourceBundle.
                getBundle("/Bundle").
                getString("EditSuccessfull"),ResourceBundle.
                getBundle("/Bundle").
                getString("EditSuccessfull"));

        Util.update(":formProduct:panelProduct");
        Util.update(":formProduct:messageGrowl");
        Util.closeDialog("editIVADialog");
        
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
                    getString("EditSuccessfull"),ResourceBundle.
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
    
    
    public void okEditPricepurchase()
    {
        int pv = Integer.parseInt(pricepurchaseValue);
        if(pricepurchase ==null || pricepurchase.getPricePurValue() != pv)
        {
            Date date = new Date();
            if(pricepurchase!=null)
            {
                pricepurchase.setPricePurFinalDate(date);
                pricepurchaseEJB.edit(pricepurchase);
            }
            pricepurchase = new Pricepurchase();
            pricepurchase.setProdId(product);
            pricepurchase.setPricePurValue(pv);
            pricepurchase.setPricePurInitialDate(date);
            pricepurchaseEJB.create(pricepurchase);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formProduct:panelProduct");
            Util.update(":formProduct:messageGrowl");
            Util.closeDialog("editPricepurchaseDialog");
        }
        else
        {
            Util.closeDialog("editPricepurchaseDialog");
        }
    }
    
    public void okEditCompositionValue()
    {
        if(compositeValue!=null && !compositeValue.isEmpty()){
            int compsiteV = Integer.parseInt(compositeValue);
            if(product.getProdCompositionValue()!= compsiteV)
            {
                product.setProdCompositionValue(compsiteV);
                productEJB.edit(product);
                Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));
                Util.update(":formProduct:panelProduct");
                Util.update(":formProduct:messageGrowl");
                Util.closeDialog("editCompositeValueDialog");
            }
            else{
                Util.closeDialog("editCompositeValueDialog"); 
            }
        }
        else
        {
            if(product.getProdCompositionValue()!=0)
            {
                product.setProdCompositionValue(0);
                productEJB.edit(product);
                Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));
                Util.update(":formProduct:panelProduct");
                Util.update(":formProduct:messageGrowl");
                Util.closeDialog("editCompositeValueDialog");
            }
            else
            {
                Util.closeDialog("editCompositeValueDialog"); 
            }
        }
    }
    
    public void okEditStock()
    {
        
        if(stock!=null && !stock.isEmpty()){
            int stockValue = Integer.parseInt(stock);
            if(product.getProdStock() != stockValue)
            {
                product.setProdStock(stockValue);
                productEJB.edit(product);
                Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));
                Util.update(":formProduct:panelProduct");
                Util.update(":formProduct:messageGrowl");
                Util.closeDialog("editStockDialog");
            }
            else{
                Util.closeDialog("editStockDialog"); 
            }
        }
        else
        {
            if(product.getProdStock()!=0)
            {
                product.setProdStock(0);
                productEJB.edit(product);
                Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));
                Util.update(":formProduct:panelProduct");
                Util.update(":formProduct:messageGrowl");
                Util.closeDialog("editStockDialog");
            }
            else
            {
                Util.closeDialog("editStockDialog"); 
            }
        }
    }
    
    public void okEditBaseNumber()
    {
        
        if(baseNumber!=null && !baseNumber.isEmpty()){
            int baseNumberValue = Integer.parseInt(baseNumber);
            if(product.getProdBaseNumber()!= baseNumberValue)
            {
                product.setProdBaseNumber(baseNumberValue);
                productEJB.edit(product);
                Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));
                Util.update(":formProduct:panelProduct");
                Util.update(":formProduct:messageGrowl");
                Util.closeDialog("editBaseNumberDialog");
            }
            else{
                Util.closeDialog("editBaseNumberDialog"); 
            }
        }
        else
        {
            if(product.getProdBaseNumber()!=0)
            {
                product.setProdBaseNumber(0);
                productEJB.edit(product);
                Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));
                Util.update(":formProduct:panelProduct");
                Util.update(":formProduct:messageGrowl");
                Util.closeDialog("editBaseNumberDialog");
            }
            else
            {
                Util.closeDialog("editBaseNumberDialog"); 
            }
        }
    }
    
    public void okEditProvider()
    {
        Date date = new Date();
        if(currentProvides!=null)
        {
            if(provider!=null)
            {
                if(!currentProvides.getProvId().getProvId().equals(provider.getProvId()))
                {
                    currentProvides.setProvidesFinalDate(date);
                    providesEJB.edit(currentProvides);
                    currentProvides = new Provides();
                    currentProvides.setProdId(product);
                    currentProvides.setProvId(provider);
                    currentProvides.setProvidesInitialDate(date);
                    providesEJB.create(currentProvides);
                    Util.addInfoMessage(ResourceBundle.
                        getBundle("/Bundle").
                        getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));
                    Util.update(":formProduct:panelProduct");
                    Util.update(":formProduct:messageGrowl");
                    Util.closeDialog("editProviderDialog");
                }
                else
                {
                    Util.closeDialog("editProviderDialog");
                }
            }
            else
            {
                currentProvides.setProvidesFinalDate(date);
                providesEJB.edit(currentProvides);
                currentProvides = null;
                Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));
                Util.update(":formProduct:panelProduct");
                Util.update(":formProduct:messageGrowl");
                Util.closeDialog("editProviderDialog");
            }
        }
        else
        {
            if(provider !=null)
            {
                currentProvides = new Provides();
                currentProvides.setProdId(product);
                currentProvides.setProvId(provider);
                currentProvides.setProvidesInitialDate(date);
                providesEJB.create(currentProvides);
                Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));
                Util.update(":formProduct:panelProduct");
                Util.update(":formProduct:messageGrowl");
                Util.closeDialog("editProviderDialog");
            }
            else
            {
                Util.closeDialog("editProviderDialog");
            }
        }
    }
    
    public List<Productimage> getEditProductimages()
    {
        if(productimages==null || productimages.isEmpty())
        {
            productimages = productimageEJB.findByProdId(product.getProdId());
        }
        return productimages;
    }
    
    public List<Productimage> getProductimages() {
        if(productimages==null || productimages.isEmpty())
        {
            productimages = productimageEJB.findByProdId(product.getProdId());
            if(productimages==null || productimages.isEmpty())
            {
                Productimage pi = new Productimage();
                pi.setProdimgPath("");
                productimages = new ArrayList<>();
                productimages.add(pi);
            }
        }
        return productimages;
    }
    
    public void editImageActive()
    {
        editImage= true;
        productimages = null;
    }
    
    public void loadImage(FileUploadEvent event) {
        try {
            if(productimages.size()<6)
            {
                if(copyFile(event.getFile().getFileName(), event.getFile().getInputstream()))
                {
                    productimages = null;
                    Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("Info")+":", ResourceBundle.
                    getBundle("/Bundle").
                    getString("UploadImageSuccessfull"));
                }
                else
                {
                    Util.addErrorMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("Error")+":",ResourceBundle.
                    getBundle("/Bundle").
                    getString("UploadImageError"));
                }
            }
            else
            {
                Util.addErrorMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("Error")+":",String.format(ResourceBundle.
                    getBundle("/Bundle").
                    getString("UploadImageMax"),6));
            }
        } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean copyFile(String fileName, InputStream in) {
        try {

            String filePath = Util.PRODUCTIMAGEDIR + product.getProdId();
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String[] split = fileName.split(Pattern.quote("."));
            int indice = split.length - 1;
            System.out.println("indice:" + indice);
            String extension = split[indice];

            Productimage pi = new Productimage();
            pi.setProdId(product);
            if (productimages == null || productimages.isEmpty()) {
                pi.setProdimgMain(true);
            } else {
                pi.setProdimgMain(false);
            }
            productimageEJB.create(pi);

            filePath = filePath + File.separator + pi.getProdimgId()+ "." + extension;
            pi.setProdimgPath(product.getProdId()+ File.separator + pi.getProdimgId() + "." + extension);
            productimageEJB.edit(pi);
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(filePath));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public void openDefaultImagenDialog(Productimage productimage)
    {
        this.productimage = productimage;
        Util.addWarnMessage("", ResourceBundle.
                    getBundle("/Bundle").
                    getString("DoyouwantSetImageDefault"));
        Util.openDialog("defaulImagenDialog");
    }
    
    public void openDeleteImagenDialog(Productimage productimage)
    {
        this.productimage = productimage;
        Util.addWarnMessage("", ResourceBundle.
                    getBundle("/Bundle").
                    getString("DoyouwantDeleteImage"));
        Util.openDialog("deleteImagenDialog");
        
    }
    
    public void editImagesOk(){
        editImage = false;
        productimages = null;
    }
    
    public void setDefaultImage()
    {
        for(Productimage pi : productimages)
        {
            if(pi.getProdimgMain())
            {
                pi.setProdimgMain(false);
                productimage.setProdimgMain(true);
                productimageEJB.edit(pi);
                productimageEJB.edit(productimage);
                productimages = null;
                break;
            }
        }
        Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("Info")+":", ResourceBundle.
                    getBundle("/Bundle").
                    getString("ImageSetByDefault"));
        Util.closeDialog("defaulImagenDialog");
        Util.openDialog("defaulImagenResultDialog");
    }
    
    public void deleteImagen()
    {  
        File file = new File(Util.PRODUCTIMAGEDIR+productimage.getProdimgPath());
        file.delete();
        productimageEJB.remove(productimage);
        productimages = null;
        Util.addInfoMessage("", ResourceBundle.
                    getBundle("/Bundle").
                    getString("ImageDeleted"));
        Util.closeDialog("deleteImagenDialog");
        Util.openDialog("deleteImageResultDialog");
    }
}
 