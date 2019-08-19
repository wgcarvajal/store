/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.lazydatamodel;

import com.store.entities.Client;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author aranda
 */
public class LazyClientDataModel extends LazyDataModel<Client> {

    private OnLazyClientDataModel mListener;
    
    public interface OnLazyClientDataModel
    {
        Client getClientById(Long cliId);
        List<Client> getClientsByParametters(Map<String,Object> filters, int first,int limit);
        long getClientsByParamettersCount(Map<String,Object> filters);
    }
    
    public LazyClientDataModel(Object mListener)
    {
        if(mListener instanceof OnLazyClientDataModel)
        {
            this.mListener = (OnLazyClientDataModel)mListener;
        }
    }
    
    @Override
    public Client getRowData(String rowKey) {
        if(mListener!=null)
        {
            Long id = Long.parseLong(rowKey);
            return mListener.getClientById(id);
        }
        return null;
    }
    
    
    @Override
    public Object getRowKey(Client object) {
        return object.getCliId();
    }
    
    
    @Override
    public List<Client> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<Client> data = mListener.getClientsByParametters(filters,first,first+pageSize);
        //rowCount
        long dataSize = mListener.getClientsByParamettersCount(filters);
        this.setRowCount(Integer.parseInt(dataSize+""));
        return data;
    }
    
    
}
