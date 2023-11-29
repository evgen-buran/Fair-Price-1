package com.buranchikov.fairprice.Fragments

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.buranchikov.fairprice.R
import com.buranchikov.fairprice.databinding.FragmentJoinBinding

class VolumeFragment : Fragment() {
    private val TAG = "myLog"
    lateinit var binding: FragmentJoinBinding
    lateinit var viewModel: JoinViewModel
    private var weight = 0.0F
    private var price = 0.0F
    private lateinit var lpLittlePrice: LinearLayout.LayoutParams
    private val arrayTextView: ArrayList<TextView> = ArrayList()
    val textColorAccent = R.color.blue_accent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJoinBinding.inflate(inflater, container, false)
        binding.tvSubtitleProduct.text = getString(R.string.volume_product_subtitle)
        binding.tvPostfixPrice.text = getString(R.string.postfix_rub_liter)

        lpLittlePrice =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lpLittlePrice.setMargins(7, 0, 7, 0)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.etWeightProduct?.text?.toString().let {
            weight = it?.toFloatOrNull() ?: 0.0F
        }
        binding.etPriceProduct?.text?.toString().let {
            price = it?.toFloatOrNull() ?: 0.0F
        }
        viewModel = ViewModelProvider(this).get(JoinViewModel::class.java)
        viewModel.getLiveDataFairPrice().observe(this) {
            binding.tvWeightResultPrice.text = it
            if (it != getString(R.string.NaN_string) && it != "0,00")
                binding.btnWeightRec.isVisible = true
            else
                binding.btnWeightRec.isVisible = false
        }

        viewModel.calcFairPrice(price, weight)

        binding.etWeightProduct.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                try {
                    s?.toString().let {
                        weight = it?.toFloat() ?: 0f
                        viewModel.calcFairPrice(price, weight)
                    }
                } catch (e: NumberFormatException) {
                    binding.etWeightProduct.setText("0")
                }
            }

        })
        binding.etPriceProduct.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                try {

                    s?.toString().let {
                        price = it?.toFloat() ?: 0f
                        viewModel.calcFairPrice(price, weight)
                    }
                } catch (e: NumberFormatException) {
                    binding.etPriceProduct.setText("0.00")
                }
            }
        })

        binding.btnWeightRec.setOnClickListener {
            var indexMinPrice = 0
            binding.llRecordPricesContainer.isVisible = true
            if (viewModel.getPricesArray().size == 4) binding.btnWeightRec.isVisible = false
            val fairPrice = viewModel.getFairPrice()
            viewModel.addPriceList(fairPrice)
            binding.llRecordPricesContainer.removeAllViews()
            Log.d(TAG, "onStart: ${viewModel.getPricesArray()}")
            arrayTextView.clear()

            // Min price
            for (i in 1 until viewModel.getPricesArray().size) {
                var minPrice = viewModel.getPricesArray().get(0)
                if (viewModel.getPricesArray().get(i) < minPrice) indexMinPrice = i
            }

            // textViews
            for (i in 0 until viewModel.getPricesArray().size) {
                val tvPriceLittle = TextView(activity)
                tvPriceLittle.layoutParams = lpLittlePrice
                tvPriceLittle.text = String.format("%.1f", viewModel.getPricesArray().get(i))
                tvPriceLittle.textSize = 18f
                arrayTextView.add(tvPriceLittle)


            }

            // display textViews, selecting Min price
            for (i in 0 until arrayTextView.size) {
                binding.llRecordPricesContainer.addView(arrayTextView.get(i))
                arrayTextView.get(indexMinPrice).setTextColor(textColorAccent)
                arrayTextView.get(indexMinPrice).textSize = 24f
                arrayTextView.get(indexMinPrice).typeface = Typeface.DEFAULT_BOLD
                arrayTextView.get(indexMinPrice).background =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.background_input)
                arrayTextView.get(indexMinPrice).setPadding(3,0,3,0)

            }

        }

        binding.btnWeightReset.setOnClickListener {
            reset()
        }
    }

    private fun reset() {
        viewModel.getPricesArray().clear()
        binding.llRecordPricesContainer.removeAllViews()
        weight = 0f
        price = 0.0f
        binding.etWeightProduct.setText("0")
        binding.etPriceProduct.setText("0.00")
    }


    }
