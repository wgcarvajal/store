/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.lazydatamodel;

import com.store.entities.User;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Wilson Carvajal
 */
public class LazyUserDataModel extends LazyDataModel<User> {

    private OnLazyUserDataModel mListener;
    
    public interface OnLazyUserDataModel
    {
        User getUserById(Long usId);
        List<User> getUsersByParametters(Map<String,Object> filters, int first,int limit);
        long getUsersByParamettersCount(Map<String,Object> filters);
    }
    
    public LazyUserDataModel(Object mListener)
    {
        if(mListener instanceof OnLazyUserDataModel)
        {
            this.mListener = (OnLazyUserDataModel)mListener;
        }
    }
    
    @Override
    public User getRowData(String rowKey) {
        if(mListener!=null)
        {
            Long id = Long.parseLong(rowKey);
            return mListener.getUserById(id);
        }
        return null;
    }
    
    
    @Override
    public Object getRowKey(User object) {
        return object.getUsId();
    }
    
    
    @Override
    public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<User> data = mListener.getUsersByParametters(filters,first,first+pageSize);
        //rowCount
        long dataSize = mListener.getUsersByParamettersCount(filters);
        this.setRowCount(Integer.parseInt(dataSize+""));
        return data;
    }
    
    
}
