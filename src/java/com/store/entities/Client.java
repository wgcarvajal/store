/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "client", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findByCliId", query = "SELECT c FROM Client c WHERE c.cliId = :cliId"),
    @NamedQuery(name = "Client.findByCliIdentity", query = "SELECT c FROM Client c WHERE c.cliIdentity = :cliIdentity"),
    @NamedQuery(name = "Client.findByCliName", query = "SELECT c FROM Client c WHERE c.cliName = :cliName"),
    @NamedQuery(name = "Client.findByCliLastName", query = "SELECT c FROM Client c WHERE c.cliLastName = :cliLastName"),
    @NamedQuery(name = "Client.findByCliPhones", query = "SELECT c FROM Client c WHERE c.cliPhones = :cliPhones"),
    @NamedQuery(name = "Client.findByCliAddress", query = "SELECT c FROM Client c WHERE c.cliAddress = :cliAddress")})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cliId")
    private Long cliId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "cliIdentity")
    private String cliIdentity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "cliName")
    private String cliName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "cliLastName")
    private String cliLastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "cliPhones")
    private String cliPhones;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "cliAddress")
    private String cliAddress;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cliCredit")
    private boolean cliCredit;
    @OneToMany(mappedBy = "cliId")
    private List<Purchase> purchaseList;

    public Client() {
    }

    public Client(Long cliId) {
        this.cliId = cliId;
    }

    public Client(Long cliId, String cliIdentity, String cliName, String cliLastName, String cliPhones, String cliAddress, boolean cliCredit) {
        this.cliId = cliId;
        this.cliIdentity = cliIdentity;
        this.cliName = cliName;
        this.cliLastName = cliLastName;
        this.cliPhones = cliPhones;
        this.cliAddress = cliAddress;
        this.cliCredit = cliCredit;
    }

    public Long getCliId() {
        return cliId;
    }

    public void setCliId(Long cliId) {
        this.cliId = cliId;
    }

    public String getCliIdentity() {
        return cliIdentity;
    }

    public void setCliIdentity(String cliIdentity) {
        this.cliIdentity = cliIdentity;
    }

    public String getCliName() {
        return cliName;
    }

    public void setCliName(String cliName) {
        this.cliName = cliName;
    }

    public String getCliLastName() {
        return cliLastName;
    }

    public void setCliLastName(String cliLastName) {
        this.cliLastName = cliLastName;
    }

    public String getCliPhones() {
        return cliPhones;
    }

    public void setCliPhones(String cliPhones) {
        this.cliPhones = cliPhones;
    }

    public String getCliAddress() {
        return cliAddress;
    }

    public void setCliAddress(String cliAddress) {
        this.cliAddress = cliAddress;
    }

    public boolean getCliCredit() {
        return cliCredit;
    }

    public void setCliCredit(boolean cliCredit) {
        this.cliCredit = cliCredit;
    }

    @XmlTransient
    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cliId != null ? cliId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.cliId == null && other.cliId != null) || (this.cliId != null && !this.cliId.equals(other.cliId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Client[ cliId=" + cliId + " ]";
    }
    
}
