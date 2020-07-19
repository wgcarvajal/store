/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.services;

import com.store.controllers.util.Encrypt;
import com.store.controllers.util.Util;
import com.store.entities.Price;
import com.store.entities.Pricepurchase;
import com.store.entities.Product;
import com.store.entities.User;
import com.store.entities.Usergroup;
import com.store.facade.PriceFacade;
import com.store.facade.PricepurchaseFacade;
import com.store.facade.ProductFacade;
import com.store.facade.UserFacade;
import com.store.facade.UsergroupFacade;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

/**
 * REST Web Service
 *
 * @author aranda
 */
@Path("services")
public class Services {
    
    private final String TAG = "Services";
    
    @EJB
    private ProductFacade productEJB;
    @EJB
    private PriceFacade priceEJB;
    @EJB
    private PricepurchaseFacade pricepurchaseEJB;
    @EJB
    private UserFacade userEJB;
    @EJB
    private UsergroupFacade usergroupEJB;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Services
     */
    public Services() {
    }

    /**
     * Retrieves representation of an instance of com.store.services.Services
     * @param name 
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/searchName")
    public String serachProductName(@QueryParam("name") String name) {
        //TODO return proper representation object
       JSONObject jSONProducts = new JSONObject();
       try{
       List<Object[]> list = productEJB.searchProductName(name);
       jSONProducts.put("type", "listProducts");
       JSONArray jSONArray = new JSONArray();
       for(Object[] p: list)
       {
           Product product = (Product)p[0];
           Price price = (Price)p[1];
           Pricepurchase pricepurchase = (Pricepurchase)p[2];
           JSONObject jSONProduct = new JSONObject();
           jSONProduct.put("prodId", product.getProdId());
           jSONProduct.put("barcode", product.getProdBarCode());
           jSONProduct.put("name", product.getProdName());
           jSONProduct.put("price", price.getPriceValue());
           jSONProduct.put("pricepurchase", pricepurchase.getPricePurValue());
           jSONProduct.put("stock", product.getProdStock());
           jSONProduct.put("base", product.getProdBaseNumber());
           jSONArray.put(jSONProduct);
       }
       jSONProducts.put("list", jSONArray);
       jSONProducts.put("size", list.size());
       System.out.println("entro");
       
       }catch(Exception e)
       {
          Util.logError(TAG, "serachProductName", "param name: "+name+" exception: "+e.getMessage());
       }
       return jSONProducts.toString();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/searchId")
    public String serachProductId(@QueryParam("id") long id) {
        //TODO return proper representation object
        JSONObject jSONProduct = new JSONObject();
        try {
            List<Object[]> list = productEJB.searchProductId(id);
            Object[] p = list.get(0);
            Product product = (Product) p[0];
            Price price = (Price) p[1];
            Pricepurchase pricepurchase = (Pricepurchase) p[2];
            jSONProduct.put("prodId", product.getProdId());
            jSONProduct.put("name", product.getProdName());
            jSONProduct.put("price", price.getPriceValue());
            jSONProduct.put("pricepurchase", pricepurchase.getPricePurValue());
            jSONProduct.put("stock", product.getProdStock());
            jSONProduct.put("base", product.getProdBaseNumber());
            jSONProduct.put("iva", product.getProdIva());
            jSONProduct.put("barcode", product.getProdBarCode());
            jSONProduct.put("type", product.getProdtypeId().getProdtypeValue());
            jSONProduct.put("category", product.getCatId().getCatName());
            jSONProduct.put("brand", product.getBrandId().getBrandName());
            jSONProduct.put("unitvalue", product.getProdUnitValue());
            jSONProduct.put("unit", product.getUniId().getUniAbbreviation());
            System.out.println("entro");

        } catch (Exception e) {
            Util.logError(TAG, "serachProductName", "param id: " + id + " exception: " + e.getMessage());
        }
        return jSONProduct.toString();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/changePrice")
    public String changePrice(String request)
    {
        JSONObject jsonResponse = new JSONObject();
        try{
            JSONObject jsonRequest = new JSONObject(request);
            long prodId = jsonRequest.getLong("prodId");
            int newPrice = jsonRequest.getInt("newPrice");
            int oldPrice = jsonRequest.getInt("oldPrice");
            int newPricePurchase = jsonRequest.getInt("newPricePurchase");
            int oldPricePurchase = jsonRequest.getInt("oldPricePurchase");
            
            
            if(newPrice!=oldPrice)
            {
                Price oldP = priceEJB.findCurrentByProdId(prodId);
                oldP.setPriceFinalDate(new Date());
                priceEJB.edit(oldP);
                Price newP = new Price();
                newP.setProdId(oldP.getProdId());
                newP.setPriceValue(newPrice);
                newP.setPriceInitialDate(new Date());
                priceEJB.create(newP);
            }
            
            if(newPricePurchase!=oldPricePurchase)
            {
                Pricepurchase oldP = pricepurchaseEJB.findCurrentByProdId(prodId);
                oldP.setPricePurFinalDate(new Date());
                pricepurchaseEJB.edit(oldP);
                Pricepurchase newP = new Pricepurchase();
                newP.setProdId(oldP.getProdId());
                newP.setPricePurValue(newPricePurchase);
                newP.setPricePurInitialDate(new Date());
                pricepurchaseEJB.create(newP);
            }
            
            jsonResponse.put("isValid", true);
            
        }catch(Exception e)
        {
           Util.logError(TAG, "changePrice", "request: "+request+" exception: "+e.getMessage()); 
           jsonResponse.put("isValid", false);
        }
        return jsonResponse.toString();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addStock")
    public String addStock(String request)
    {
        JSONObject jsonResponse = new JSONObject();
        try{
            JSONObject jsonRequest = new JSONObject(request);
            long prodId = jsonRequest.getLong("prodId");
            int addStock = jsonRequest.getInt("addStock");
            
            Product p = productEJB.findByProdId(prodId);
            p.setProdStock(p.getProdStock()+addStock);
            productEJB.edit(p);
            jsonResponse.put("isValid", true);
            
        }catch(Exception e)
        {
           Util.logError(TAG, "addStock", "request: "+request+" exception: "+e.getMessage()); 
           jsonResponse.put("isValid", false);
        }
        return jsonResponse.toString();
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/changeBase")
    public String changeBase(String request)
    {
        JSONObject jsonResponse = new JSONObject();
        try{
            JSONObject jsonRequest = new JSONObject(request);
            long prodId = jsonRequest.getLong("prodId");
            int base = jsonRequest.getInt("base");
            Product p = productEJB.findByProdId(prodId);
            p.setProdBaseNumber(base);
            productEJB.edit(p);
            jsonResponse.put("isValid", true);
            
        }catch(Exception e)
        {
           Util.logError(TAG, "changeBase", "request: "+request+" exception: "+e.getMessage()); 
           jsonResponse.put("isValid", false);
        }
        return jsonResponse.toString();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public String login(String request)
    {
        JSONObject jsonResponse = new JSONObject();
        try{
            JSONObject jsonRequest = new JSONObject(request);
            String username = jsonRequest.getString("user");
            String password = jsonRequest.getString("password");
            User user = userEJB.findByUsUserName(username);
            if(user.getUsPassword().equals(Encrypt.sha256(password)) &&
                    user.getUsActive())
            {
                Usergroup usergroup = usergroupEJB.findByUsUserName(user.getUsUserName());
                jsonResponse.put("isValid", true);
                jsonResponse.put("rol", usergroup.getGrouId().getGrouId());
                jsonResponse.put("userName",user.getUsUserName());
            }
            else
            {
               jsonResponse.put("isValid", false); 
            }
            
        }catch(Exception e)
        {
           Util.logError(TAG, "login", "request: "+request+" exception: "+e.getMessage()); 
           jsonResponse.put("isValid", false);
        }
        return jsonResponse.toString();
    }
    
    
}
