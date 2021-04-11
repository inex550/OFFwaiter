package com.example.coffwaiter.ui.wait

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.coffwaiter.R
import com.example.coffwaiter.databinding.FragmentThanksBinding
import kotlin.random.Random

class OrderCompleteFragment: Fragment() {

    private lateinit var pinCodeTv: TextView
    private lateinit var orderGetBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_order_complete, container,false)

        pinCodeTv = v.findViewById(R.id.pin_code_tv)

        pinCodeTv.text = Random.nextInt(100, 1000).toString()

        orderGetBtn = v.findViewById(R.id.order_get_btn)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderGetBtn.setOnClickListener {
            (requireActivity() as WaitActivity).openFragment(ThanksFragment())
        }
    }
}