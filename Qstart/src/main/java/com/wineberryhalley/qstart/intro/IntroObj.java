package com.wineberryhalley.qstart.intro;

public class IntroObj {

    public IntroObj(String assetName, String texto){
this.assetName = assetName;
this.texto = texto;
    }

    private String assetName;

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    private String texto;
}
