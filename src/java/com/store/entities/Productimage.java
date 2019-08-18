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
@Table(name = "productimage", catalog = "storebd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productimage.findAll", query = "SELECT p FROM Productimage p"),
    @NamedQuery(name = "Productimage.findByProdimgId", query = "SELECT p FROM Productimage p WHERE p.prodimgId = :prodimgId"),
    @NamedQuery(name = "Productimage.findByProdId", query = "SELECT p FROM Productimage p WHERE p.prodId.prodId = :prodId"),
    @NamedQuery(name = "Productimage.findByProdimgPath", query = "SELECT p FROM Productimage p WHERE p.prodimgPath = :prodimgPath")})
public class Productimage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prodimgId")
    private Long prodimgId;
    @Size(min = 1, max = 100)
    @Column(name = "prodimgPath")
    private String prodimgPath;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prodimgMain")
    private boolean prodimgMain;
    @JoinColumn(name = "prodId", referencedColumnName = "prodId")
    @ManyToOne(optional = false)
    private Product prodId;

    public Productimage() {
    }

    public Productimage(Long prodimgId) {
        this.prodimgId = prodimgId;
    }

    public Productimage(Long prodimgId, boolean prodimgMain) {
        this.prodimgId = prodimgId;
        this.prodimgMain = prodimgMain;
    }

    public Long getProdimgId() {
        return prodimgId;
    }

    public void setProdimgId(Long prodimgId) {
        this.prodimgId = prodimgId;
    }

    public String getProdimgPath() {
        return prodimgPath;
    }

    public void setProdimgPath(String prodimgPath) {
        this.prodimgPath = prodimgPath;
    }

    public boolean getProdimgMain() {
        return prodimgMain;
    }

    public void setProdimgMain(boolean prodimgMain) {
        this.prodimgMain = prodimgMain;
    }

    public Product getProdId() {
        return prodId;
    }

    public void setProdId(Product prodId) {
        this.prodId = prodId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prodimgId != null ? prodimgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productimage)) {
            return false;
        }
        Productimage other = (Productimage) object;
        if ((this.prodimgId == null && other.prodimgId != null) || (this.prodimgId != null && !this.prodimgId.equals(other.prodimgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Productimage[ prodimgId=" + prodimgId + " ]";
    }
}
