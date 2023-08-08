package com.skydevices.viacepclone.api

import com.skydevices.viacepclone.model.Endereco
import retrofit2.http.GET
import retrofit2.http.Path

interface EnderecoAPI {

    //URL_BASE = "https://viacep.com.br/"
    @GET("ws/{cep}/json/")
    suspend fun recuperarEndereco(
       @Path("cep") cep:String
    ) : retrofit2.Response<Endereco>

}