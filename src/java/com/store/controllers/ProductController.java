package com.store.controllers;



import com.store.entities.Product;
import com.store.facade.ProductFacade;
import com.store.lazydatamodel.LazyProductDataModel;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "productController")
@ViewScoped
public class ProductController implements Serializable,LazyProductDataModel.OnLazyProductDataModel 
{
    @EJB
    private ProductFacade productEJB;
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
    
    public String precio(Product product)
    {
        return "100";
    }
    
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Car Selected", "product:"+((Product) event.getObject()).getProdName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
