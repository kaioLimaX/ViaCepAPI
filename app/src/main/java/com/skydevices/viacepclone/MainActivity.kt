package com.skydevices.viacepclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.skydevices.viacepclone.api.EnderecoAPI
import com.skydevices.viacepclone.api.RetrofitHelper
import com.skydevices.viacepclone.databinding.ActivityMainBinding
import com.skydevices.viacepclone.model.Endereco
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val retrofit by lazy {
        RetrofitHelper.retrofit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            btnIniciar.setOnClickListener {
                if (edtCep.length() == 8) {
                    CoroutineScope(Dispatchers.IO).launch {
                        recuperarEndereco()
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "insira no minimo 8 digitos",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
    }

    private suspend fun recuperarEndereco() {

        val cep = binding.edtCep.text.toString()
        var retorno: Response<Endereco>? = null

        try {
            val enderecoAPI = retrofit.create(EnderecoAPI::class.java)

            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.VISIBLE

            }
            retorno = enderecoAPI.recuperarEndereco(cep)


        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_endereco", "Erro ao recuperar")
        }

        if (retorno != null) {
            if (retorno.isSuccessful) {
                val endereco = retorno.body()
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.INVISIBLE
                    val customDialog = CustomDialog(this@MainActivity)
                    customDialog.setRua("Cidade: ${endereco?.logradouro}")
                    customDialog.setCep("CEP: ${endereco?.cep}")
                    customDialog.setLocalidade("Bairro: ${endereco?.localidade}")
                    customDialog.setIbge("IBGE: ${endereco?.ibge}")
                    customDialog.show()
                }

                val rua = endereco?.logradouro
                val cep = endereco?.cep
                val cidade = endereco?.localidade
                Log.i("info_endereco", "endereço : $rua , $cidade ,  txt : $cep")

            }

        }
    }
}