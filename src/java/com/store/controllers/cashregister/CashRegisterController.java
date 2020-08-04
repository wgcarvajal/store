/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.cashregister;


import com.store.controllers.util.Encrypt;
import com.store.controllers.util.GeneratePdf;
import com.store.controllers.util.PrintPdf;
import com.store.controllers.util.Scale;
import com.store.controllers.util.Util;
import com.store.entities.Cash;
import com.store.entities.Client;
import com.store.entities.Lend;
import com.store.entities.Owner;
import com.store.entities.Pay;
import com.store.entities.Price;
import com.store.entities.Pricepurchase;
import com.store.entities.Product;
import com.store.entities.Purchase;
import com.store.entities.Purchaseitem;
import com.store.entities.Purchasetotal;
import com.store.entities.User;
import com.store.facade.CashFacade;
import com.store.facade.ClientFacade;
import com.store.facade.LendFacade;
import com.store.facade.PayFacade;
import com.store.facade.PriceFacade;
import com.store.facade.PricepurchaseFacade;
import com.store.facade.ProductFacade;
import com.store.facade.PurchaseFacade;
import com.store.facade.PurchaseitemFacade;
import com.store.facade.PurchasetotalFacade;
import com.store.model.Debt;
import com.store.model.ScaleIP;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "cashRegisterController")
@ViewScoped
public class CashRegisterController implements Serializable {
    
    public interface OnSesionUserController
    {
        boolean isCorrectPassword(String password);
        User getCurrentCashier();
    } 
    
    private final String TAG = "CashRegisterController";
    
    private static List<ScaleIP>listScaleIp;
    private Scale scale;
    
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
    private boolean selectProduct;
    
    private String password;
    private String code;
    private String quantity;
    private String receivedAmount;
    private String identification;
    private String codeForRemove;
    private String total;
    private String refund;
    private String action;
    private String weight;
    private String debtsValue;
    private String pays;
    private String lends;
    private String credits;
    private String pay;
    private String lend;
    private Product producWaitForWeight;
    private Product SelectedProduct;
    private Purchaseitem SelectedPurchaseItem;
    private Client selectedClient;
    private Purchase selectedPurchaseResume;
    private GeneratePdf generatePdf;
    private long totalProductIva0;
    private long totalProductIva5;
    private long totalProductIva19;
    
    private List<Purchaseitem> purchaseitems;
    private List<Pay> payList;
    private List<Lend> lendList;
    private List<Purchase> creditList;
    private List<Purchase> resumeSaleList;
    private Purchase purchase;
    
    private List<Purchasetotal> purchasetotals;
    private Cash currentCash;
    
    
    @EJB private PurchaseFacade purchaseEJB;
    @EJB private PurchaseitemFacade purchaseItemEJB;
    @EJB private ProductFacade productEJB;
    @EJB private PriceFacade priceEJB;
    @EJB private PricepurchaseFacade pricePurchaseEJB;
    @EJB private ClientFacade clientEJB;
    @EJB private PayFacade payEJB;
    @EJB private LendFacade lendEJB;
    @EJB private PurchasetotalFacade purchasetotalEJB;
    @EJB private CashFacade cashEJB;
    
    @PostConstruct
    public void init()
    {
       FacesContext fc = FacesContext.getCurrentInstance();
       HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
       String ip = req.getRemoteAddr();
       currentCash = cashEJB.findByCashIP(ip);
       if(currentCash==null)
       {
           Util.logWarning(TAG, "init", "ip no valida: "+ip);
       }
       else{
           Util.logInformation(TAG, "init", "ip valida: "+ip);
       }
    }
    
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

    public boolean isSelectProduct() {
        return selectProduct;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPay() {
        return pay;
    }

    public String getLend() {
        return lend;
    }

    public void setLend(String lend) {
        this.lend = lend;
    }
    
    public void setPay(String pay) {
        this.pay = pay;
    }
    
    public String getDebtsValue() {
        lendList =null;
        payList = null;
        creditList = null;
        lends = "0";
        pays = "0";
        credits = "0";
        
        int dv = 0;
        if(selectedClient!=null)
        {
            
            lendList = lendEJB.findByCliId(selectedClient.getCliId());
            if(lendList!=null)
            {
                int v = 0;
                for(Lend l: lendList)
                {
                    v = v + l.getLendValue();
                }
                dv = v;
                lends = Util.getFormatPrice(v);
            }
            
            creditList = purchaseEJB.findByCliIdCredit(selectedClient.getCliId());
            if(creditList!=null)
            {
                int v = 0;
                for(Purchase p: creditList)
                {
                    v = v + p.getPurFinalAmount();
                }
                dv = dv + v;
                credits = Util.getFormatPrice(v);
            }
            
            
            payList = payEJB.findByCliId(selectedClient.getCliId());
            if(payList!=null)
            {
                int v = 0;
                for(Pay p: payList)
                {
                    v = v + p.getPayValue();
                }
                dv = dv-v;
                pays = Util.getFormatPrice(v);
            }
            
        }
        
        
        debtsValue = Util.getFormatPrice(dv);
        return debtsValue;
    }

    public String getPays() {
        return pays;
    }

    public String getLends() {
        return lends;
    }

    public String getCredits() {
        return credits;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
    
    public List<Purchaseitem> getPurchaseitems() {
        /*if(purchaseitems!=null)
        {
            if(purchaseitems.size()>15)
            {
                return purchaseitems.subList(purchaseitems.size() - 15, purchaseitems.size());
            }
    }*/
        return purchaseitems;
    }

    public List<Pay> getPayList() {
        return payList;
    }

    public List<Lend> getLendList() {
        return lendList;
    }

    public List<Purchase> getCreditList() {
        return creditList;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public List<Purchase> getResumeSaleList() {
        return resumeSaleList;
    }

    public void setResumeSaleList(List<Purchase> resumeSaleList) {
        this.resumeSaleList = resumeSaleList;
    }

    public Product getSelectedProduct() {
        return SelectedProduct;
    }

    public void setSelectedProduct(Product SelectedProduct) {
        this.SelectedProduct = SelectedProduct;
    }

    public Purchaseitem getSelectedPurchaseItem() {
        return SelectedPurchaseItem;
    }

    public void setSelectedPurchaseItem(Purchaseitem SelectedPurchaseItem) {
        this.SelectedPurchaseItem = SelectedPurchaseItem;
    }
    
    

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }

    public Purchase getSelectedPurchaseResume() {
        return selectedPurchaseResume;
    }

    public void setSelectedPurchaseResume(Purchase selectedPurchaseResume) {
        this.selectedPurchaseResume = selectedPurchaseResume;
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
        selectProduct = false;
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
        selectProduct = false;
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
        selectProduct = false;
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
            case "001":
                //purchaseEJB.findTotalEachMonthByYear(2015);
                if(openCash)
                {
                    openCashPasswordRequest();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "010":
                if(closeCash)
                {
                    closeCashRegister();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "002":
                if (start) {
                    startSale();
                } else {
                    openNoAction();
                }
                break;
            case "020":
                if(cancel)
                {
                    openCancelSalePasswordRequest();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "003":
                if(changeQuantityLastProdut)
                {
                    openChangeQuantityProduct();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "004":
                if(addClient)
                {
                    openAddClient();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "030":
                if(removeProduct)
                {
                    openRemoveProducto();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "005":
                if(payment)
                {
                    openPaymentCash();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "040":
                if(searchProduct)
                {
                    openSearchProduct();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "050":
                if(searchClient)
                {
                    openSearchClient();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "006":
                if(selectProduct)
                {
                    openSelectProduct();
                }
                else
                {
                    openNoAction();
                }
                break;
            case "007":
                if(start)
                {
                    resumeSale(onSesionUserController);
                }
                else
                {
                    openNoAction();
                }
                break;
            case "008":
                if(start)
                {
                    openCurrentDayTotal();
                }
                else{
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
        selectProduct = true;
        initScale();
        Util.update(":formCode:focusCode");
        Util.update(":formMenu");
        Util.update(":formSaleDescription");
    }
    
    public void initScale()
    {
        if(currentCash==null)
        {
            Util.logError(TAG, "initScale", "currentCash is null");
            return;
        }
        
        if(scale!=null){
            scale.stop();
        }
        
        if(listScaleIp==null)
        {
            listScaleIp = new ArrayList<>();
        }
        else if(!listScaleIp.isEmpty()){
            for(int i = listScaleIp.size() - 1 ; i>=0; i--)
            {
                ScaleIP scaleIP = listScaleIp.get(i);
                if(scaleIP.getIp().equals(currentCash.getCashIP()))
                {
                    scaleIP.getScale().stop();
                    listScaleIp.remove(scaleIP);
                }
            }
        }
        String scalePortSerialName = currentCash.getCashScalePortSerialName();
        scale = new Scale(scalePortSerialName);
        Util.logInformation(TAG, "initScale", "scalePortSerialName: "+ scalePortSerialName);
        if(scale.start())
        {
            Util.logInformation(TAG, "initScale", "start true");
            ScaleIP scaleIP = new ScaleIP(currentCash.getCashIP(), scale);
            listScaleIp.add(scaleIP);
        }
        else
        {
            Util.logInformation(TAG, "initScale", "start false");
        }
    }
    
    public void stopScale()
    {
        if(scale!=null){
            scale.stop();
            scale = null;
        }
    }
    
    public void resumeSale(OnSesionUserController onSesionUserController)
    {
        Long usId = onSesionUserController.getCurrentCashier().getUsId();
        resumeSaleList = purchaseEJB.findSaleForResume(usId);
        Util.update(":formResumePurchase");
        Util.openDialog("openResumePurchase");
    }
    
    public void closeResumeSale()
    {
        Util.update(":formCode:focusCode");
        Util.update(":formMenu");
        Util.closeDialog("openResumePurchase");
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
        selectProduct = false;
        if(purchase!=null)
        {
            purchaseItemEJB.deleteByPurId(purchase.getPurId());
            purchaseEJB.remove(purchase);
            purchase = null;
        }
        purchaseitems = null;
        stopScale();
        Util.update(":formCode:focusCode");
        Util.update(":formMenu");
        Util.update(":formSaleDescription");
        Util.closeDialog("openCancelSalePasswordRequest");
    }
    
    private void searchAndAddProduct(OnSesionUserController onSesionUserController)
    {
        Product product = productEJB.findByBarCode(code);
        if (product != null) {
            boolean productType = product.getProdtypeId().getProdtypeValue().equals("Sin empaquetar");
            if(!productType)
            {
                if(product.getProdComposition()!=null){
                    addProduct(product, onSesionUserController, productType);
                }
                else if(product.getProdStock()>0)
                {
                    addProduct(product, onSesionUserController, productType);
                }
                else{
                   String message = ResourceBundle.getBundle("/Bundle").getString("ProductSoldOut");
                   Util.addErrorMessage(message, message);
                   Util.update(":formGeneralMessage"); 
                }
            }
            else{
                producWaitForWeight = product;
                readWeight(onSesionUserController);
            }
        }
        else
        {
            String message = ResourceBundle.getBundle("/Bundle").getString("CodeNoFound");
            Util.addErrorMessage(message, message);
            Util.update(":formGeneralMessage");
        }
    }
    
    private void addProduct(Product product,OnSesionUserController onSesionUserController,boolean productType)
    {
        Price price = priceEJB.findCurrentByProdId(product.getProdId());
        Pricepurchase pricepurchase = pricePurchaseEJB.findCurrentByProdId(product.getProdId());
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
                if(productType)
                {
                    
                    purchaseitem.setPurItemQuantity(getSimulatedBalance());
                }
                else
                {
                    purchaseitem.setPurItemQuantity(1);
                }
                purchaseitem.setPriceValue(price.getPriceValue());
                if(pricepurchase!=null)
                    purchaseitem.setPricePurValue(pricepurchase.getPricePurValue());
                else
                    purchaseitem.setPricePurValue(price.getPriceValue());
                purchaseitem.setIva(product.getProdIva());
                purchaseitems.add(purchaseitem);
                purchaseitem.setPurId(purchase);
                purchaseitem.setOwnId(product.getOwnId());
                purchaseItemEJB.create(purchaseitem);
            }
            changeQuantityLastProdut = !productType;
            Util.update(":formCode:focusCode");
            Util.update(":formMenu");
            Util.update(":formSaleDescription");
    }
    
    private void openAddWeight()
    {
        weight = null;
        Util.update(":formOpenAddWeight");
        Util.openDialog("openAddWeight");
    }
    
    public void escKeyOpenAddWeight()
    {
        producWaitForWeight = null;
        Util.update(":formCode:focusCode");
        Util.closeDialog("openAddWeight");
    }
    
    public void okAddWeight(OnSesionUserController onSesionUserController)
    {
        if(!weight.isEmpty())
        {
            try
            {
               int w = Integer.parseInt(weight);
               addProduct(producWaitForWeight, onSesionUserController, true);
               Util.closeDialog("openAddWeight");
            }
            catch(NumberFormatException e)
            {
            }
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
                   double fv = purchaseitem.getPurItemQuantity() / 1000.0;
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
                   double fv = purchaseitem.getPurItemQuantity()/1000.0;
                   fv = fv * purchaseitem.getPriceValue();
                   long round = Math.round(fv);
                   return Util.getFormatPrice(round);
                   
           }
        }
        return Util.getFormatPrice(purchaseitem.getPurItemQuantity() * purchaseitem.getPriceValue());
    }
    
    public String getAmountTotal()
    {
        long amount = 0;
        if (purchaseitems != null) {
            for (Purchaseitem purchaseitem : purchaseitems) {
                if (purchaseitem.getProdId().getProdtypeId().getProdtypeValue().equals("Sin empaquetar")) {
                    String unity = purchaseitem.getProdId().getUniId().getUniAbbreviation();
                    switch (unity) {
                        case "gr":
                            double fv = purchaseitem.getPurItemQuantity() / 1000.0;
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
        purchasetotals = new ArrayList();
        long amount = 0;
        totalProductIva0=0;
        totalProductIva5=0;
        totalProductIva19=0;
        if (purchaseitems != null) {
            for (Purchaseitem purchaseitem : purchaseitems) {
                if(generatePdf!=null)
                {
                    generatePdf.secondStringHtml(purchaseitem);
                }
                Purchasetotal purchasetotal = getPurchasetotal(purchaseitem.getProdId().getOwnId());
                double iva = 0.0;
                double vIva = 0.0;
                long gain = 0;
                long v = 0;
                if(purchaseitem.getIva()== 5)
                {
                    iva = 1.05;
                }
                else if(purchaseitem.getIva()== 19)
                {
                    iva = 1.19;
                }
                
                if(purchasetotal == null)
                {
                    purchasetotal = new Purchasetotal();
                    purchasetotal.setOwnId(purchaseitem.getProdId().getOwnId());
                    purchasetotal.setPurId(purchase);
                    purchasetotal.setPurToTotal(0);
                    purchasetotal.setPurToGain(0);
                    purchasetotal.setPurToIva(0);
                    purchasetotals.add(purchasetotal);
                }
                if (purchaseitem.getProdId().getProdtypeId().getProdtypeValue().equals("Sin empaquetar")) {
                    String unity = purchaseitem.getProdId().getUniId().getUniAbbreviation();
                    switch (unity) {
                        case "gr":
                            double fv = purchaseitem.getPurItemQuantity() / 1000.0;
                            double pfv= fv * purchaseitem.getPricePurValue();
                            fv = fv * purchaseitem.getPriceValue();
                            v = Math.round(fv);
                            long pv = Math.round(pfv);
                            gain = v - pv;
                            
                            if(purchaseitem.getIva() > 0)
                            {
                                double vWIva = gain / iva;
                                vIva = gain - vWIva;
                            }
                            else
                            {
                                vIva = 0;
                            }
                            amount = amount + v;
                           
                    }
                }
                else{
                    v = purchaseitem.getPurItemQuantity() * purchaseitem.getPriceValue();
                    int pv = purchaseitem.getPurItemQuantity() * purchaseitem.getPricePurValue();
                    gain = v - pv;
                    if(purchaseitem.getIva() > 0)
                    {
                        double vWIva = gain/iva;
                        vIva = gain - vWIva;
                    }
                    else
                    {
                        vIva = 0;
                    }
                    amount = amount + v;
                }
                
                if(purchaseitem.getIva()==0)
                {
                    totalProductIva0=totalProductIva0 + v;
                }
                else if(purchaseitem.getIva()==5)
                {
                    totalProductIva5=totalProductIva5+v;
                }
                else if(purchaseitem.getIva()==19)
                {
                    totalProductIva19=totalProductIva19+v;
                }
                purchasetotal.setPurToTotal((int)(purchasetotal.getPurToTotal() + v));
                purchasetotal.setPurToGain((int)(purchasetotal.getPurToGain()+ gain));
                purchasetotal.setPurToIva((float)(purchasetotal.getPurToIva()+ vIva));
            }
        }
        return (int)amount;
    }
    
    private Purchasetotal getPurchasetotal(Owner owner)
    {
        if(purchasetotals.size()>0)
        {
            for(Purchasetotal pt: purchasetotals)
            {
                if(pt.getOwnId().getOwnId() == owner.getOwnId())
                {
                    return pt;
                }
            }
        }
        return null;
    }
    
    public int getSimulatedBalance()
    {
        return Integer.parseInt(weight);
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
    
    public void openPaymentCash()
    {
        receivedAmount = null;
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
                    generatePdf=null;
                    if(currentCash!=null){
                        generatePdf = new GeneratePdf(currentCash.getCashPaperSize());
                        generatePdf.firstStringHtml(purchase);
                    }
                    int amount = getAmountTotalIntegerFormat();
                    if(rm>=amount)
                    {
                        int r = rm-amount;
                        String id = purchase.getPurId()+"";
                        int length = id.length();
                        String barCode = "100000000000000000000000000000";
                        length = 30 - length;
                        barCode = barCode.substring(0, length);
                        barCode = barCode +id;
                        if(generatePdf!=null)
                        {
                            generatePdf.thirdStringHtml(amount, rm, r,barCode,purchase, totalProductIva0, totalProductIva5, totalProductIva19);
                            generatePdf.generatePdf(purchase,barCode);
                        }
                        refund = Util.getFormatPrice(r);
                        receivedAmount = Util.getFormatPrice(rm);
                        total = Util.getFormatPrice(amount);
                        purchase.setPurFinalAmount(amount);
                        purchase.setPurReceivedAmount(rm);
                        purchase.setPurState(1);
                        purchaseEJB.edit(purchase);
                        for(Purchasetotal pt : purchasetotals)
                        {
                            purchasetotalEJB.create(pt);
                        }
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
                        selectProduct = false;
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
                if(a==1)//print
                {
                    
                    if(currentCash!=null)
                    {
                       //print
                       PrintPdf printPdf = new PrintPdf(currentCash.getCashPaperSize());
                       printPdf.imprimirTicket(generatePdf.getFicheroPdf(),currentCash.getCashPrintName());
                       printPdf.openCashDrawer(currentCash.getCashPrintName(), currentCash.getCashPrintCommandOpenCashDrawer());
                    }
                    Util.closeDialog("openSuccessfulPayment");
                    Util.update(":formOpenFinishPayment");
                    Util.openDialog("openOpenFinishPayment");
                }
                else if(a == 2)//end
                {
                   if(currentCash!=null)
                   {
                       PrintPdf printPdf = new PrintPdf();
                       printPdf.openCashDrawer(currentCash.getCashPrintName(), currentCash.getCashPrintCommandOpenCashDrawer());
                   }
                   Util.closeDialog("openSuccessfulPayment"); 
                   Util.update(":formOpenFinishPayment");
                   Util.openDialog("openOpenFinishPayment");
                }
                    
            }
            catch(NumberFormatException e)
            {
                
            }
        }
    }
    
    public void okFinishPaymentAction()
    {       
        Util.update(":formCode:focusCode");
        Util.closeDialog("openOpenFinishPayment");
    }
    
    public void openSearchProduct()
    {
        Util.clearFilters("searchProductTable");
        Util.update(":formOpenSearchProduct");
        Util.openDialog("openSearchProduct");
    }
    
    public void closeSearchProduct()
    {
        Util.closeDialog("openSearchProduct");
        Util.update(":formCode:focusCode");
    }
    
    
    public void openSearchClient()
    {
        selectedClient = null;
        Util.clearFilters("clientTable");
        Util.update(":formOpenSearchClient");
        Util.openDialog("openSearchClient");
    }
    
    public void openCurrentDayTotal()
    {
        Util.update("formTotalPurchase");
        Util.openDialog("totalPurchase");
    }
    
    public String getPurchaseTotal(OnSesionUserController onSesionUserController)
    {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        Date initial = now.getTime();
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        Date end = now.getTime();
        List<Purchase> plist= purchaseEJB.findPurshaseUsIdAndDay(onSesionUserController.getCurrentCashier().getUsId(), initial, end);
        int amount = 0;
        for(Purchase p: plist)
        {
            amount = amount + p.getPurFinalAmount();
        }
        
        return Util.getFormatPrice(amount);
    }
    
    public void closeCurrentDayTotal()
    {
        Util.closeDialog("totalPurchase");
        Util.update(":formCode:focusCode");
    }
    
    public void closeSearchClient()
    {
        Util.closeDialog("openSearchClient");
        Util.update(":formCode:focusCode");
    }
    
    public void openSelectProduct()
    {
        Util.clearFilters("selectProductTable");
        Util.update(":formOpenSelectProduct");
        Util.openDialog("openSelectProduct");
    }
    
    public void closeSelectProduct()
    {
        Util.closeDialog("openSelectProduct");
        Util.update(":formCode:focusCode");
    }
    
    public void onRowSelectedProduct(OnSesionUserController onSesionUserController) {
        code = SelectedProduct.getProdBarCode();
        SelectedProduct = null;
        Util.closeDialog("openSelectProduct");
        searchAndAddProduct(onSesionUserController);
        code = null;
    }
    
    public void onRowSelectedResumePurchase() {
        start = false;
        cancel = true;
        saleDescription = true;
        closeCash = false;
        selectProduct = true;
        addClient = true;
        showList = true;
        removeProduct = true;
        payment = true;
        purchase = selectedPurchaseResume;
        purchaseitems = purchaseItemEJB.findByPurId(purchase.getPurId());
        initScale();
        Util.update(":formCode:focusCode");
        Util.update(":formMenu");
        Util.update(":formSaleDescription");
        Util.closeDialog("openResumePurchase");
    }
    
    public void onRowSelectClient(SelectEvent event) {
        Util.closeDialog("openSearchClient");
        Util.update(":formOpenDebtor:panelDebtorHeader");
        Util.update(":formOpenDebtor:panelDebtorCenter");
        Util.update(":formOpenDebtor:panelDebtorBottom");
        Util.openDialog("openDebtor");
    }
    
    public void closeDebtor()
    {
        Util.closeDialog("openDebtor");
        Util.update(":formCode:focusCode");
    }
    
    public boolean allowCredit()
    {
        return !(purchase!=null && purchase.getCliId()!=null && purchase.getCliId().getCliCredit());
    }
    
    public void okCredit()
    {
        int amount = getAmountTotalIntegerFormat();
        refund = "0";
        receivedAmount = "0";
        total = Util.getFormatPrice(amount);
        purchase.setPurFinalAmount(amount);
        purchase.setPurState(2);
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
        selectProduct = false;
        purchaseitems = null;
        purchase = null;
        action = null;
        Util.update(":formCode:focusCode");
        Util.update(":formMenu");
        Util.update(":formSaleDescription");
        Util.update(":formOpenSuccessfulPayment");
        Util.closeDialog("openPayment");
        Util.openDialog("openSuccessfulPayment");
    }
    
    public void openMakeLoan()
    {
        lend = null;
        Util.update(":formOpenMakeLoan");
        Util.openDialog("openMakeLoan");
    }
    
    public void escKeyopenMakeLoan()
    {
        Util.closeDialog("openMakeLoan");
    }
    
    public void okMakeLoan(OnSesionUserController onSesionUserController)
    {
        if(!lend.isEmpty())
        {
            try{
                int v = Integer.parseInt(lend);
                Lend l = new Lend();
                l.setCliId(selectedClient);
                l.setUsId(onSesionUserController.getCurrentCashier());
                l.setLendValue(v);
                l.setLendState(0);
                l.setLendDate(new Date());
                lendEJB.create(l);
                Util.closeDialog("openMakeLoan");
                Util.update(":formOpenDebtor:panelDebtorHeader");
                Util.update(":formOpenDebtor:panelDebtorCenter");
                Util.update(":formOpenDebtor:panelDebtorBottom");
            }catch(NumberFormatException e)
            {

            }
        }
    }
    
    public void openPay()
    {
        pay = null;
        Util.update(":formOpenPay");
        Util.openDialog("openPay");
    }
    
    public void escKeyopenPay()
    {
        Util.closeDialog("openPay");
    }
    
    public void okPay(OnSesionUserController onSesionUserController)
    {
        if(!pay.isEmpty())
        {
            try{
                int v = Integer.parseInt(pay);
                List<Purchase> pList = purchaseEJB.findByCliIdAndStatePending(selectedClient.getCliId());
                List<Lend> lList = lendEJB.findByCliIdAndStatePending(selectedClient.getCliId());
                List<Debt> dList = new ArrayList();
                if(pList!=null || lList!=null)
                {
                    int pv=0;
                    if (pList != null) {
                        for (Purchase p : pList) {
                            int finalAmount = p.getPurFinalAmount();
                            if (p.getPurPayment() != null) {
                                finalAmount = finalAmount - p.getPurPayment();
                            }
                            pv = pv + finalAmount;
                            Debt d = new Debt();
                            d.setDate(p.getPurDate());
                            d.setObject(p);
                            dList.add(d);
                        }
                    }
                    if (lList != null) {
                        for (Lend l : lList) {
                            int finalAmount = l.getLendValue();
                            if (l.getLendPayment() != null) {
                                finalAmount = finalAmount - l.getLendPayment();
                            }
                            pv = pv + finalAmount;
                            Debt d = new Debt();
                            d.setDate(l.getLendDate());
                            d.setObject(l);
                            dList.add(d);
                        }
                    }
                    if(pv>=v)
                    {
                        Collections.sort(dList, (Debt o1, Debt o2) -> o1.getDate().compareTo(o2.getDate()));
                        makePay(v, dList,onSesionUserController);
                    }
                    else
                    {
                        //message
                    }
                }
                else{
                    //message
                }
                
            }catch(NumberFormatException e)
            {

            }
        }
    }
    
    private void makePay(int value, List<Debt> dList,OnSesionUserController onSesionUserController) {
        int v = value;
        for (Debt d : dList) {
            if (d.getObject() instanceof Purchase) {
                Purchase p = (Purchase) d.getObject();
                int totalDebts = p.getPurFinalAmount();
                int totalPay = 0;
                if (p.getPurPayment() != null) {
                    totalPay = p.getPurPayment();
                }
                totalDebts = totalDebts - totalPay;
                if (value >= totalDebts) {
                    value = value - totalDebts;
                    p.setPurPayment(p.getPurFinalAmount());
                    p.setPurState(3);
                } else {
                    totalPay = totalPay + value;
                    value = 0;
                    p.setPurPayment(totalPay);
                }
                purchaseEJB.edit(p);
            } else {
                Lend l = (Lend) d.getObject();
                int totalDebts = l.getLendValue();
                int totalPay = 0;
                if (l.getLendPayment() != null) {
                    totalPay = l.getLendPayment();
                }
                totalDebts = totalDebts - totalPay;
                if (value >= totalDebts) {
                    value = value - totalDebts;
                    l.setLendPayment(l.getLendValue());
                    l.setLendState(1);
                } else {
                    totalPay = totalPay + value;
                    value = 0;
                    l.setLendPayment(totalPay);
                }
                lendEJB.edit(l);
            }
            if(value == 0)
            {
                break;
            }
        }
        
        Pay p = new Pay();
        p.setCliId(selectedClient);
        p.setPayValue(v);
        p.setPayDate(new Date());
        p.setUsId(onSesionUserController.getCurrentCashier());
        payEJB.create(p);
        Util.closeDialog("openPay");
        Util.update(":formOpenDebtor:panelDebtorHeader");
        Util.update(":formOpenDebtor:panelDebtorCenter");
        Util.update(":formOpenDebtor:panelDebtorBottom");
    }
    
    @PreDestroy
    public void destroy()
    {
        stopScale();
        System.out.println("destroy");
    }

    public void readWeight(OnSesionUserController onSesionUserController)
    {
        Calendar c = Calendar.getInstance();
        long initTime = c.getTimeInMillis();
        if (scale != null) {
            scale.setWeight("0");
            while(scale.getWeight().equals("0")) {
                c = Calendar.getInstance();
                long currentTime = c.getTimeInMillis();
                if(currentTime>(initTime + 4000l))
                {
                    break;
                }
            }
            if(scale.getWeight().equals("0"))
            {
                Util.logInformation(TAG, "readWeight", "scale.getWeight == 0, call openAddWeight()");
                openAddWeight();
            }else{
                weight = scale.getWeight();
                Util.logInformation(TAG, "readWeight", "scale.getWeight sucessfull, Weight = weight");
                addProduct(producWaitForWeight, onSesionUserController, true);
            }
        } else
        {
            Util.logInformation(TAG, "readWeight", "scale is null, call openAddWeight()");
            openAddWeight();
        }
    }
         
}
