package io.atomic.android_boilerplate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.atomic.actioncards.sdk.AACSDK
import com.atomic.actioncards.sdk.AACSingleCardView
import io.atomic.android_boilerplate.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val viewModel: BoilerPlateViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    private var singleCard: AACSingleCardView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentHomeBinding.inflate(layoutInflater)

        initListeners()

        singleCard = AACSingleCardView(BoilerPlateViewModel.singleCardID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        singleCard?.start(binding.singleCardView.id, childFragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()
        singleCard?.destroy(childFragmentManager)
    }


    /** Initialise the button click listeners */
    private fun initListeners() {
        // CARDS BUTTON
        binding.showCardsButton.setOnClickListener {
            val cardFragment = CardFragment()
            parentFragmentManager.beginTransaction()
                .add(R.id.fragment_container, cardFragment, "CARD_FRAGMENT")
                .addToBackStack(null)
                .commit()
        }

        // BLANK BUTTON
        binding.blankFragmentButton.setOnClickListener {
            val blankFragment = BlankFragment()
//            parentFragmentManager.beginTransaction()
//                .add(R.id.fragment_container, blankFragment, "BLANK_FRAGMENT")
//                .addToBackStack(null)
//                .commit()

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, blankFragment, "BLANK_FRAGMENT")
            .addToBackStack(this.javaClass.simpleName)
            .commit()
        }
        // LOGOUT BUTTON
        binding.logOutButton.setOnClickListener {
            AACSDK.logout {
                requireActivity().runOnUiThread {
                    Toast.makeText(context, "Logged off", Toast.LENGTH_SHORT).show()
                }
                requireActivity().finish()
            }
        }
    }
}