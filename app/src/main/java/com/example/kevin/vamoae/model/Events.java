package com.example.kevin.vamoae.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by felix on 04/11/2017.
 */

public class Events {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("idUsuarioCriacao")
    @Expose
    private String idUser;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cidade")
    @Expose
    private String city;
    @SerializedName("UF")
    @Expose
    private String uf;
    @SerializedName("like")
    @Expose
    private int like;
    @SerializedName("deslike")
    @Expose
    private int deslike;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("ativo")
    @Expose
    private String active;
    @SerializedName("liked")
    @Expose
    private int liked;

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDeslike() {
        return deslike;
    }

    public void setDeslike(int deslike) {
        this.deslike = deslike;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @SerializedName("descEvento")
    @Expose
    private String descEvent;
    private String site;
    private String facebook;
    private String instagram;
    @SerializedName("dataInicial")
    @Expose
    private String startDate;
    @SerializedName("dataFinal")
    @Expose
    private String finalDate;
    private String CEP;
    @SerializedName("endereco")
    @Expose
    private String address;
    @SerializedName("numeroEndereco")
    @Expose
    private String addressNumber;
    @SerializedName("complemento")
    @Expose
    private String complement;
    @SerializedName("bairro")
    @Expose
    private String district;

    public String getDescEvent() {
        return descEvent;
    }

    public void setDescEvent(String descEvent) {
        this.descEvent = descEvent;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
