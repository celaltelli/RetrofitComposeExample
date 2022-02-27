package com.tellioglu.retrofitcompose.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class CurrencyModel {
    @SerializedName("TCMB_AnlikKurBilgileri")
    var currencyModList: List<CurrencyMod> = ArrayList()

    @SerializedName("Developer")
    var developer = DeveloperModel()

    //"TCMB_AnlikKurBilgileri":[{"Isim":"ABD DOLARI","CurrencyName":"US DOLLAR","ForexBuying":13.4287,"ForexSelling":13.4529,"BanknoteBuying":13.4193,"BanknoteSelling":13.4731,"CrossRateUSD":"","CrossRateOther":""}
    inner class CurrencyMod {
        @SerializedName("Isim")
        var name: String? = null

        @SerializedName("CurrencyName")
        var currencyName: String? = null

        @SerializedName("ForexBuying")
        var forexBuying: String? = null

        @SerializedName("ForexSelling")
        var forexSelling: String? = null

        @SerializedName("BanknoteBuying")
        var banknoteBuying: String? = null

        @SerializedName("BanknoteSelling")
        var banknoteSelling: String? = null

        @SerializedName("CrossRateUSD")
        var crossRateUSD: String? = null

        @SerializedName("CrossRateOther")
        var crossRateOther: String? = null
    }
}