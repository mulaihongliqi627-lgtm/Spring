package com.amadeus.lotterysystem.dao.dataobject;

import lombok.Data;

import java.io.Serializable;

@Data
public class Encrypt implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String value;
    
    public Encrypt() {}
    
    public Encrypt(String value) {
        this.value = value;
    }
}
