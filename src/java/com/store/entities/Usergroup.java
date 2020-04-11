/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "usergroup", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usergroup.findAll", query = "SELECT u FROM Usergroup u"),
    @NamedQuery(name = "Usergroup.findById", query = "SELECT u FROM Usergroup u WHERE u.id = :id"),
    @NamedQuery(name = "Usergroup.findByUsUserName", query = "SELECT u FROM Usergroup u WHERE u.usUserName = :usUserName")})
public class Usergroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "usUserName")
    private String usUserName;
    @JoinColumn(name = "grouId", referencedColumnName = "grouId")
    @ManyToOne(optional = false)
    private Groupp grouId;
    @JoinColumn(name = "usId", referencedColumnName = "usId")
    @ManyToOne(optional = false)
    private User usId;

    public Usergroup() {
    }

    public Usergroup(Long id) {
        this.id = id;
    }

    public Usergroup(Long id, String usUserName) {
        this.id = id;
        this.usUserName = usUserName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsUserName() {
        return usUserName;
    }

    public void setUsUserName(String usUserName) {
        this.usUserName = usUserName;
    }

    public Groupp getGrouId() {
        return grouId;
    }

    public void setGrouId(Groupp grouId) {
        this.grouId = grouId;
    }

    public User getUsId() {
        return usId;
    }

    public void setUsId(User usId) {
        this.usId = usId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usergroup)) {
            return false;
        }
        Usergroup other = (Usergroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Usergroup[ id=" + id + " ]";
    }
    
}
