/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.cashregister;

import com.store.controllers.util.Encrypt;
import com.store.controllers.util.Util;
import com.store.entities.Client;
import com.store.entities.Price;
import com.store.entities.Product;
import com.store.entities.Purchase;
import com.store.entities.Purchaseitem;
import com.store.entities.User;
import com.store.facade.ClientFacade;
import com.store.facade.PriceFacade;
import com.store.facade.ProductFacade;
import com.store.facade.PurchaseFacade;
import com.store.facade.PurchaseitemFacade;
import java.io.Serializable;
import java.util.ArrayList;
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
    
    public interface OnSesionUserController
    {
        boolean isCorrectPassword(String password);
        User getCurrentCashier();
    } 
    
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
    private String quantity;
    private String receivedAmount;
    private String identification;
    private String codeForRemove;
    private String total;
    private String refund;
    private String action;
    
    private List<Purchaseitem> purchaseitems;
    private Purchase purchase;
    
    private List<Purchaseitem> copyPrintPurchaseitems;
    private Purchase copyPrintPurchase;
    
    
    @EJB private PurchaseFacade purchaseEJB;
    @EJB private PurchaseitemFacade purchaseItemEJB;
    @EJB private ProductFacade productEJB;
    @EJB private PriceFacade priceEJB;
    @EJB private ClientFacade clientEJB;
    
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getCodeForRemove() {
        return codeForRemove;
    }

    public void setCodeForRemove(String codeForRemove) {
        this.codeForRemove = codeForRemove;
    }

    public String getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(String receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public String getTotal() {
        return total;
    }

    public String getRefund() {
        return refund;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
            case "24":
                if(changeQuantityLastProdut)
                {
                    openChangeQuantityProduct();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "25":
                if(addClient)
                {
                    openAddClient();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "26":
                if(removeProduct)
                {
                    openRemoveProducto();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "27":
                if(payment)
                {
                    openPayment();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "28":
                if(searchProduct)
                {
                    openSearchProduct();
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
        if(purchase!=null)
        {
            purchaseItemEJB.deleteByPurId(purchase.getPurId());
            purchaseEJB.remove(purchase);
            purchase = null;
        }
        purchaseitems = null;
        Util.update(":formCode:focusCode");
        Util.update(":formMenu");
        Util.update(":formSaleDescription");
        Util.closeDialog("openCancelSalePasswordRequest");
    }
    
    private void searchAndAddProduct(OnSesionUserController onSesionUserController)
    {
        Product product = productEJB.findByBarCode(code);
        if (product != null) {
            boolean productType;
            Price price = priceEJB.findCurrentByProdId(product.getProdId());
            if (purchaseitems == null || purchaseitems.isEmpty()) {
                purchase = new Purchase();
                purchase.setPurDate(new Date());
                User cashier = onSesionUserController.getCurrentCashier();
                purchase.setUsId(cashier);
                purchase.setPurState(0);
                purchaseEJB.create(purchase);
                purchaseitems = new ArrayList<>();
                addClient = true;
                showList = true;
                removeProduct = true;
                payment = true;
            }
            
            if (!purchaseitems.isEmpty() && product.getProdId().equals(purchaseitems.get(purchaseitems.size() - 1).getProdId().getProdId())) {
                
                int q = 1;
                productType = product.getProdtypeId().getProdtypeValue().equals("Sin empaquetar");
                if(productType)
                {
                    q = getSimulatedBalance();
                    changeQuantityLastProdut = false;
                    
                }
                
                purchaseitems.get(purchaseitems.size() - 1).
                        setPurItemQuantity(purchaseitems.get(purchaseitems.size() - 1).
                        getPurItemQuantity() +q);
                purchaseItemEJB.edit(purchaseitems.get(purchaseitems.size() - 1));
            }
            else
            {
                Purchaseitem purchaseitem = new Purchaseitem();
                purchaseitem.setProdId(product);
                productType = product.getProdtypeId().getProdtypeValue().equals("Sin empaquetar");
                if(productType)
                {
                    
                    purchaseitem.setPurItemQuantity(getSimulatedBalance());
                }
                else
                {
                    purchaseitem.setPurItemQuantity(1);
                }
                purchaseitem.setPriceValue(price.getPriceValue());
                purchaseitems.add(purchaseitem);
                purchaseitem.setPurId(purchase);
                purchaseItemEJB.create(purchaseitem);
            }
            changeQuantityLastProdut = !productType;
            Util.update(":formCode:focusCode");
            Util.update(":formMenu");
            Util.update(":formSaleDescription");
        }
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
    
    public int getAmountTotalIntegerFormat()
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
        return amount;
    }
    
    public int getSimulatedBalance()
    {
        Random rand = new Random();

        int finalX = rand.nextInt(2000);
        return finalX;
    }
    
    public void openChangeQuantityProduct()
    {
        quantity = null;
        Util.update(":formOpenChangeQuantityProduct");
        Util.openDialog("openChangeQuantityProduct");
    }
    
    public void escKeyChangeQuantityProduct()
    {
        Util.update(":formCode:focusCode");
        Util.closeDialog("openChangeQuantityProduct");
    }
    
    public void okChangeQuantityProduct()
    {
        if(!quantity.isEmpty())
        {
            try
            {
                int q = Integer.parseInt(quantity);
                if(q>0)
                {
                    int index = purchaseitems.size() -1;
                    purchaseitems.get(index).setPurItemQuantity(q);
                    purchaseItemEJB.edit(purchaseitems.get(index));
                    Util.update(":formCode:focusCode");
                    Util.closeDialog("openChangeQuantityProduct");
                    Util.update(":formSaleDescription");
                }
                
            }
            catch(NumberFormatException e)
            {
                
            }
        }
    }
    
    public void openAddClient()
    {
        identification = null;
        Util.update(":formOpenAddClient");
        Util.openDialog("openAddClient");
    }
    
    public void escKeyOpenAddClient()
    {
        Util.update(":formCode:focusCode");
        Util.closeDialog("openAddClient");
    }
    
    public void okAddClient()
    {
        if(!identification.isEmpty())
        {
            Client client = clientEJB.findByCliIdentity(identification);
            if(client!=null)
            {
                purchase.setCliId(client);
                purchaseEJB.edit(purchase);
                Util.update(":formCode:focusCode");
                Util.closeDialog("openAddClient");
                Util.update(":formSaleDescription");
            }
        }
    }
    
    public String nameAndLastNameClient()
    {
        return purchase!=null && purchase.getCliId()!=null?purchase.getCliId().getCliName() +" "+purchase.getCliId().getCliLastName():"";
        
    }
    
    public void openRemoveProducto()
    {
        codeForRemove = null;
        Util.update(":formOpenRemoveProducto");
        Util.openDialog("openRemoveProducto");
    }
    
    public void escKeyRemoveProducto()
    {
        Util.update(":formCode:focusCode");
        Util.closeDialog("openRemoveProducto");
    }
    
    public void okRemoveProducto()
    {
        if(!codeForRemove.isEmpty())
        {
            Product product = productEJB.findByBarCode(codeForRemove);
            if (product != null) {
                for (int i = purchaseitems.size() - 1; i >= 0; i--) {
                     Purchaseitem p = purchaseitems.get(i);
                     if(p.getProdId().getProdBarCode().equals(product.getProdBarCode()))
                     {
                         purchaseitems.remove(i);
                     }
                }
                purchaseItemEJB.deleteByPurIdAndProdId(purchase.getPurId(),product.getProdId());
                if(purchaseitems.isEmpty())
                {
                    purchaseEJB.remove(purchase);
                    addClient = false;
                    removeProduct = false;
                    showList = false;
                    changeQuantityLastProdut=false;
                    payment = false;
                    purchaseitems = null;
                    purchase = null;
                }
                Util.update(":formCode:focusCode");
                Util.closeDialog("openRemoveProducto");
                Util.update(":formMenu");
                Util.update(":formSaleDescription");
            }
        }
    }
    
    public void openPayment()
    {
        Util.update(":formOpenPayment");
        Util.openDialog("openPayment");
    }
    
    public void escKeyPayment()
    {
        Util.update(":formCode:focusCode");
        Util.closeDialog("openPayment");
    }
    
    public void okPaymentCash()
    {
        receivedAmount = null;
        Util.closeDialog("openPayment");
        Util.update(":formOpenReceivedAmount");
        Util.openDialog("openReceivedAmount");
    }
    
    public void escKeyReceivedAmount()
    {
        receivedAmount = null;
        Util.update(":formCode:focusCode");
        Util.closeDialog("openReceivedAmount");
    }
    
    public void okReceivedAmount()
    {
        if(!receivedAmount.isEmpty())
        {
            try
            {
                int rm = Integer.parseInt(receivedAmount);
                if(rm>0)
                {
                    int amount = getAmountTotalIntegerFormat();
                    if(rm>=amount)
                    {
                        int r = rm-amount;
                        refund = Util.getFormatPrice(r);
                        receivedAmount = Util.getFormatPrice(rm);
                        total = Util.getFormatPrice(amount);
                        purchase.setPurFinalAmount(amount);
                        purchase.setPurState(1);
                        purchaseEJB.edit(purchase);
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
                        copyPrintPurchase = purchase;
                        copyPrintPurchaseitems = purchaseitems;
                        purchaseitems = null;
                        purchase = null;
                        action = null;
                        Util.update(":formCode:focusCode");
                        Util.update(":formMenu");
                        Util.update(":formSaleDescription");
                        Util.closeDialog("openReceivedAmount");
                        Util.update(":formOpenSuccessfulPayment");
                        Util.openDialog("openSuccessfulPayment");
                    }
                }
            }catch(NumberFormatException e)
            {
            }
        }
    }
    
    public void okAction()
    {
        if(!action.isEmpty())
        {
            try{
                int a = Integer.parseInt(action);
                if(a==1)
                {
                    Util.update(":formCode:focusCode");
                    Util.closeDialog("openSuccessfulPayment");
                }
                else if(a == 2)
                {
                   Util.update(":formCode:focusCode");
                   Util.closeDialog("openSuccessfulPayment");         
                }
                    
            }
            catch(NumberFormatException e)
            {
                
            }
        }
    }
    
    public void openSearchProduct()
    {
        Util.update(":formOpenSearchProduct");
        Util.openDialog("openSearchProduct");
    }
    
    public void closeSearchProduct()
    {
        Util.closeDialog("openSearchProduct");
        Util.update(":formCode:focusCode");
    }
    
    
}
