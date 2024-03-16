package ru.elenandreyuk.hw11

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.elenandreyuk.hw11.databinding.ActivityMainBinding


private const val PREFERENCE_NAME = "preference_name"
private const val SHARED_PREFS_KEY = "shared_prefs_key"

class Repository{
    private lateinit var sharedPreference: SharedPreferences
    private var localValue: String? = null

    fun getText(context: Context): String{
        sharedPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return when{
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            getDataFromSharedPreference(context) != null -> getDataFromSharedPreference(context)!!
            else ->""
        }
    }

    private fun getDataFromLocalVariable(): String? {
        return localValue
    }

    private fun getDataFromSharedPreference(context: Context): String?{
        val prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        return prefs.getString(SHARED_PREFS_KEY, null)

    }

    fun saveText(text: String){
        sharedPreference.edit().putString(SHARED_PREFS_KEY, text).apply()
        localValue = text
    }

    fun clearText(){
        sharedPreference.edit().clear().apply()
        localValue = null

    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val repository = Repository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateUI()

        binding.buttonSave.setOnClickListener {
            val inputText = binding.editText.text
            repository.saveText(inputText.toString())
            binding.editText.let { it.text?.clear()}
            updateUI()
        }
        binding.buttonClear.setOnClickListener{
            repository.clearText()
            updateUI()
        }
    }

    private fun updateUI(){
        binding.textView.text = repository.getText(this)
    }



}