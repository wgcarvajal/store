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
@Table(name = "groupp", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groupp.findAll", query = "SELECT g FROM Groupp g"),
    @NamedQuery(name = "Groupp.findByGrouId", query = "SELECT g FROM Groupp g WHERE g.grouId = :grouId"),
    @NamedQuery(name = "Groupp.findByGrouDescription", query = "SELECT g FROM Groupp g WHERE g.grouDescription = :grouDescription")})
public class Groupp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "grouId")
    private String grouId;
    @Column(name = "grouDescription")
    private String grouDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grouId")
    private List<Usergroup> usergroupList;

    public Groupp() {
    }

    public Groupp(String grouId) {
        this.grouId = grouId;
    }

    public String getGrouId() {
        return grouId;
    }

    public void setGrouId(String grouId) {
        this.grouId = grouId;
    }

    public String getGrouDescription() {
        return grouDescription;
    }

    public void setGrouDescription(String grouDescription) {
        this.grouDescription = grouDescription;
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
        hash += (grouId != null ? grouId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groupp)) {
            return false;
        }
        Groupp other = (Groupp) object;
        if ((this.grouId == null && other.grouId != null) || (this.grouId != null && !this.grouId.equals(other.grouId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Groupp[ grouId=" + grouId + " ]";
    }
    
}
