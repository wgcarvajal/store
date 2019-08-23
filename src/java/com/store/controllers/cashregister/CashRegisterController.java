/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.cashregister;

import com.store.controllers.util.Encrypt;
import com.store.controllers.util.Util;
import com.store.entities.Price;
import com.store.entities.Product;
import com.store.entities.Purchase;
import com.store.entities.Purchaseitem;
import com.store.entities.User;
import com.store.facade.PriceFacade;
import com.store.facade.ProductFacade;
import com.store.facade.PurchaseFacade;
import com.store.facade.PurchaseitemFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "cashRegisterController")
@SessionScoped
public class CashRegisterController implements Serializable {
    
    
    private boolean openCash;
    private boolean closeCash;
    private boolean start;
    private boolean cancel;
    private boolean payment;
    private boolean searchProduct;
    private boolean searchClient;
    private boolean addClient;
    private boolean changeQuantityLastProdut;
    private boolean removeProduct;
    private boolean saleDescription;
    private boolean showList;
    
    private String password;
    private String code;
    
    private List<Purchaseitem> purchaseitems;
    private Purchase purchase;
    
    
    @EJB private PurchaseFacade purchaseEJB;
    @EJB private PurchaseitemFacade purchaseItemEJB;
    @EJB private ProductFacade productEJB;
    @EJB private PriceFacade priceEJB;
    
    
    private List<String> strings = Arrays.asList("sup1", "sup2", "sup3","sup1", "sup2", "sup3"
    ,"sup1", "sup2", "sup3","sup1", "sup2", "sup3","sup1", "sup2", "sup3","0,763");
    
    public CashRegisterController()
    {
        initFlags();
    }

    public boolean isOpenCash() {
        return openCash;
    }

    public boolean isCloseCash() {
        return closeCash;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isCancel() {
        return cancel;
    }

    public boolean isPayment() {
        return payment;
    }

    public boolean isSearchProduct() {
        return searchProduct;
    }

    public boolean isSearchClient() {
        return searchClient;
    }

    public boolean isAddClient() {
        return addClient;
    }

    public boolean isChangeQuantityLastProdut() {
        return changeQuantityLastProdut;
    }

    public boolean isRemoveProduct() {
        return removeProduct;
    }

    public boolean isSaleDescription() {
        return saleDescription;
    } 

    public boolean isShowList() {
        return showList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Purchaseitem> getPurchaseitems() {
        if(purchaseitems!=null)
        {
            if(purchaseitems.size()>15)
            {
                return purchaseitems.subList(purchaseitems.size() - 15, purchaseitems.size());
            }
        }
        return purchaseitems;
    }

    public Purchase getPurchase() {
        return purchase;
    }
    
    private void initFlags() {
        openCash = true;
        closeCash = false;
        start = false;
        cancel = false;
        payment = false;
        searchProduct = false;
        searchClient = false;
        addClient = false;
        changeQuantityLastProdut = false;
        removeProduct = false;
        saleDescription = false;
        showList = false;
    }
    
    public void openCashPasswordRequest()
    {
        password = null;
        Util.update(":formOpenCashPasswordRequest:panelOpenCashPasswordRequest");
        Util.openDialog("openCashPasswordRequest");
    }
    
    public void escKeyCashPasswordRequest()
    {
        Util.update(":formCode:focusCode");
        Util.closeDialog("openCashPasswordRequest");
    }
    
    public void okPasswordRequest(OnSesionUserController onSesionUserController)
    {
        if(!password.isEmpty())
        {
            String passEncrypt = Encrypt.sha256(password);
            if(onSesionUserController.isCorrectPassword(passEncrypt))
            {
                openCashRegister();
            }
        }
    }
    
    private void openCashRegister()
    {
        openCash = false;
        closeCash = true;
        start = true;
        cancel = false;
        payment = false;
        searchProduct = true;
        searchClient = true;
        addClient = false;
        changeQuantityLastProdut = false;
        removeProduct = false;
        saleDescription = false;
        Util.update(":formCode:focusCode");
        Util.update(":formMenu");
        Util.closeDialog("openCashPasswordRequest");
    }
    
    public void closeCashRegister()
    {
        openCash = true;
        closeCash = false;
        start = false;
        cancel = false;
        payment = false;
        searchProduct = false;
        searchClient = false;
        addClient = false;
        changeQuantityLastProdut = false;
        removeProduct = false;
        saleDescription = false;
        showList = false;
        Util.update(":formCode:focusCode");
        Util.update(":formMenu");
    }
    
    public void readCode(OnSesionUserController onSesionUserController)
    {
        if(!code.isEmpty())
        {
          evaluateCode(onSesionUserController);
        }
        code = null;
        Util.update(":formCode");
        Util.update(":formMenu");
        
    }
    
    private void evaluateCode(OnSesionUserController onSesionUserController)
    {
        switch(code)
        {
            case "20":
                if(openCash)
                {
                    openCashPasswordRequest();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "21":
                if(closeCash)
                {
                    closeCashRegister();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "22":
                if (start) {
                    startSale();
                } else {
                    openNoAction();
                }
                break;
            case "23":
                if(cancel)
                {
                    openCancelSalePasswordRequest();
                }
                else
                {
                    openNoAction();
                }
                break;
            default:
                if(cancel)
                {
                    searchAndAddProduct(onSesionUserController);
                }
                break;
        }
    }
    
    private void openNoAction()
    {
        Util.openDialog("noAction");
    }

    public void okNoAction()
    {
        Util.update(":formCode");
        Util.closeDialog("noAction");
    }
    
    public void startSale()
    {
        start = false;
        cancel = true;
        saleDescription = true;
        closeCash = false;
        Util.update(":formCode:focusCode");
        Util.update(":formMenu");
        Util.update(":formSaleDescription");
    }
    
    public void openCancelSalePasswordRequest()
    {
        password = null;
        String message = ResourceBundle.getBundle("/Bundle").getString("CancelSaleConfirmation");
        Util.addWarnMessage(message, message);
        Util.update(":formOpenCancelSalePasswordRequest");
        Util.openDialog("openCancelSalePasswordRequest");
    }
    
    public void escKeyCancelSalePasswordRequest()
    {
        Util.update(":formCode:focusCode");
        Util.closeDialog("openCancelSalePasswordRequest");
    }
    
    public void okCancelSalePasswordRequest(OnSesionUserController onSesionUserController)
    {
        if(!password.isEmpty())
        {
            String passEncrypt = Encrypt.sha256(password);
            if(onSesionUserController.isCorrectPassword(passEncrypt))
            {
                cancelSale();
            }
        }
    }
    
    private void cancelSale()
    {
        openCash = false;
        closeCash = true;
        start = true;
        cancel = false;
        payment = false;
        searchProduct = true;
        searchClient = true;
        addClient = false;
        changeQuantityLastProdut = false;
        removeProduct = false;
        saleDescription = false;
        showList = false;
        Util.update(":formCode:focusCode");
        Util.update(":formMenu");
        Util.update(":formSaleDescription");
        Util.closeDialog("openCancelSalePasswordRequest");
    }
    
    private void searchAndAddProduct(OnSesionUserController onSesionUserController)
    {
        Product product = productEJB.findByBarCode(code);
        if (product != null) {
            Price price = priceEJB.findCurrentByProdId(product.getProdId());
            if (purchaseitems == null || purchaseitems.isEmpty()) {
                purchase = new Purchase();
                purchase.setPurDate(new Date());
                User cashier = onSesionUserController.getCurrentCashier();
                purchase.setUsId(cashier);
                purchaseitems = new ArrayList<>();
                addClient = true;
                showList = true;
            }
            
            if (!purchaseitems.isEmpty() && product.getProdId().equals(purchaseitems.get(purchaseitems.size() - 1).getProdId().getProdId())) {
                
                int quantity = 1;
                if(product.getProdtypeId().getProdtypeValue().equals("Sin empaquetar"))
                {
                    quantity = getSimulatedBalance();
                }
                
                purchaseitems.get(purchaseitems.size() - 1).
                        setPurItemQuantity(purchaseitems.get(purchaseitems.size() - 1).
                        getPurItemQuantity() +quantity);
            }
            else
            {
                Purchaseitem purchaseitem = new Purchaseitem();
                purchaseitem.setProdId(product);
                if(product.getProdtypeId().getProdtypeValue().equals("Sin empaquetar"))
                {
                    
                    purchaseitem.setPurItemQuantity(getSimulatedBalance());
                }
                else
                {
                    purchaseitem.setPurItemQuantity(1);
                }
                purchaseitem.setPriceValue(price.getPriceValue());
                purchaseitems.add(purchaseitem);
            }
            Util.update(":formCode:focusCode");
            Util.update(":formMenu");
            Util.update(":formSaleDescription");
        }
    }
    
    public interface OnSesionUserController
    {
        boolean isCorrectPassword(String password);
        User getCurrentCashier();
    } 
    
    public String getQuantity(Purchaseitem purchaseitem)
    {
        if(purchaseitem.getProdId().getProdtypeId().getProdtypeValue().equals("Sin empaquetar"))
        {
           String unity = purchaseitem.getProdId().getUniId().getUniAbbreviation();
           switch(unity)
           { 
               case "gr":
                   float fv = (float)purchaseitem.getPurItemQuantity() / 1000.0f;
                   return String.format("%.3f", fv);
                   
           }
        }
        return purchaseitem.getPurItemQuantity()+"";
    }
    
    public String getPrice(Purchaseitem purchaseitem)
    {
        return Util.getFormatPrice(purchaseitem.getPriceValue());
    }
    
    public String getTotal(Purchaseitem purchaseitem)
    {
        if(purchaseitem.getProdId().getProdtypeId().getProdtypeValue().equals("Sin empaquetar"))
        {
           String unity = purchaseitem.getProdId().getUniId().getUniAbbreviation();
           switch(unity)
           { 
               case "gr":
                   float fv = (float)purchaseitem.getPurItemQuantity()/1000.0f;
                   fv = fv * purchaseitem.getPriceValue();
                   int round = Math.round(fv);
                   return Util.getFormatPrice(round);
                   
           }
        }
        return Util.getFormatPrice(purchaseitem.getPurItemQuantity() * purchaseitem.getPriceValue());
    }
    
    public String getAmountTotal()
    {
        int amount = 0;
        if (purchaseitems != null) {
            for (Purchaseitem purchaseitem : purchaseitems) {
                if (purchaseitem.getProdId().getProdtypeId().getProdtypeValue().equals("Sin empaquetar")) {
                    String unity = purchaseitem.getProdId().getUniId().getUniAbbreviation();
                    switch (unity) {
                        case "gr":
                            float fv = (float)purchaseitem.getPurItemQuantity() / 1000.0f;
                            fv = fv * purchaseitem.getPriceValue();
                            amount = amount + Math.round(fv);
                    }
                }
                else
                    amount = amount + (purchaseitem.getPurItemQuantity() * purchaseitem.getPriceValue());
            }
        }
        return "$"+Util.getFormatPrice(amount);
    }
    
    public int getSimulatedBalance()
    {
        Random rand = new Random();

        int finalX = rand.nextInt(2000);
        return finalX;
    }
}
