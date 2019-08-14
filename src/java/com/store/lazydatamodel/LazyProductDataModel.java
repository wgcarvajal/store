/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.lazydatamodel;

import com.store.entities.Product;
import com.store.lazysorter.LazySorter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author aranda
 */
public class LazyProductDataModel extends LazyDataModel<Product> {

    private OnLazyProductDataModel mListener;
    
    public interface OnLazyProductDataModel
    {
        Product getProductById(Long prodId);
        List<Product> getProductsByParametters(Map<String,Object> filters, int first,int limit);
        long getProductsByParamettersCount(Map<String,Object> filters);
    }
    
    public LazyProductDataModel(Object mListener)
    {
        if(mListener instanceof OnLazyProductDataModel)
        {
            this.mListener = (OnLazyProductDataModel)mListener;
        }
    }
    
    @Override
    public Product getRowData(String rowKey) {
        if(mListener!=null)
        {
            Long id = Long.parseLong(rowKey);
            return mListener.getProductById(id);
        }
        return null;
    }
    
    
    @Override
    public Object getRowKey(Product object) {
        return object.getProdId();
    }
    
    
    @Override
    public List<Product> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<Product> data = mListener.getProductsByParametters(filters,first,first+pageSize);
        
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }
 
        //rowCount
        long dataSize = mListener.getProductsByParamettersCount(filters);
        
        this.setRowCount(Integer.parseInt(dataSize+""));
 
        //paginate
        
        return data;
    }
    
    
}
