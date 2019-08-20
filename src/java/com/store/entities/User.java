/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "user", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUsId", query = "SELECT u FROM User u WHERE u.usId = :usId"),
    @NamedQuery(name = "User.findByUsIdentification", query = "SELECT u FROM User u WHERE u.usIdentification = :usIdentification"),
    @NamedQuery(name = "User.findByUsName", query = "SELECT u FROM User u WHERE u.usName = :usName"),
    @NamedQuery(name = "User.findByUsLastName", query = "SELECT u FROM User u WHERE u.usLastName = :usLastName"),
    @NamedQuery(name = "User.findByUsUserName", query = "SELECT u FROM User u WHERE u.usUserName = :usUserName"),
    @NamedQuery(name = "User.findByUsPassword", query = "SELECT u FROM User u WHERE u.usPassword = :usPassword"),
    @NamedQuery(name = "User.findByUsEmail", query = "SELECT u FROM User u WHERE u.usEmail = :usEmail"),
    @NamedQuery(name = "User.findByUsAddress", query = "SELECT u FROM User u WHERE u.usAddress = :usAddress"),
    @NamedQuery(name = "User.findByUsPhone", query = "SELECT u FROM User u WHERE u.usPhone = :usPhone"),
    @NamedQuery(name = "User.findByUsActive", query = "SELECT u FROM User u WHERE u.usActive = :usActive")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usId")
    private Long usId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "usIdentification")
    private String usIdentification;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "usName")
    private String usName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "usLastName")
    private String usLastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "usUserName")
    private String usUserName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "usPassword")
    private String usPassword;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "usEmail")
    private String usEmail;
    @Size(max = 200)
    @Column(name = "usAddress")
    private String usAddress;
    @Size(max = 200)
    @Column(name = "usPhone")
    private String usPhone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usActive")
    private boolean usActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usId")
    private List<Purchase> purchaseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usId")
    private List<Usergroup> usergroupList;

    public User() {
    }

    public User(Long usId) {
        this.usId = usId;
    }

    public User(Long usId, String usIdentification, String usName, String usLastName, String usUserName, String usPassword, String usEmail, boolean usActive) {
        this.usId = usId;
        this.usIdentification = usIdentification;
        this.usName = usName;
        this.usLastName = usLastName;
        this.usUserName = usUserName;
        this.usPassword = usPassword;
        this.usEmail = usEmail;
        this.usActive = usActive;
    }

    public Long getUsId() {
        return usId;
    }

    public void setUsId(Long usId) {
        this.usId = usId;
    }

    public String getUsIdentification() {
        return usIdentification;
    }

    public void setUsIdentification(String usIdentification) {
        this.usIdentification = usIdentification;
    }

    public String getUsName() {
        return usName;
    }

    public void setUsName(String usName) {
        this.usName = usName;
    }

    public String getUsLastName() {
        return usLastName;
    }

    public void setUsLastName(String usLastName) {
        this.usLastName = usLastName;
    }

    public String getUsUserName() {
        return usUserName;
    }

    public void setUsUserName(String usUserName) {
        this.usUserName = usUserName;
    }

    public String getUsPassword() {
        return usPassword;
    }

    public void setUsPassword(String usPassword) {
        this.usPassword = usPassword;
    }

    public String getUsEmail() {
        return usEmail;
    }

    public void setUsEmail(String usEmail) {
        this.usEmail = usEmail;
    }

    public String getUsAddress() {
        return usAddress;
    }

    public void setUsAddress(String usAddress) {
        this.usAddress = usAddress;
    }

    public String getUsPhone() {
        return usPhone;
    }

    public void setUsPhone(String usPhone) {
        this.usPhone = usPhone;
    }

    public boolean getUsActive() {
        return usActive;
    }

    public void setUsActive(boolean usActive) {
        this.usActive = usActive;
    }

    @XmlTransient
    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
    }

    @XmlTransient
    public List<Usergroup> getUsergroupList() {
        return usergroupList;
    }

    public void setUsergroupList(List<Usergroup> usergroupList) {
        this.usergroupList = usergroupList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usId != null ? usId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.usId == null && other.usId != null) || (this.usId != null && !this.usId.equals(other.usId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.User[ usId=" + usId + " ]";
    }
    
}
