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
import io.atomic.android_boilerplate.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val viewModel: BoilerPlateViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentHomeBinding.inflate(layoutInflater)

        initListeners()

        // This is used to initialize the value
        viewModel.countLiveData.observe(this) {
            binding.cardCountDash.text = it ?: ""
        }

        // This is used to update the value
        AACSDK.getLiveCardCountForStreamContainer(viewModel.getDashboardId)
            .observe(this) {
                it?.let { value ->
                    Log.d("Atomic", "Dashboard count: $value")
                    binding.cardCountDash.text = "Dash Cards Count: $value"
                }
            }

        // This is used to initialize the value
        viewModel.countLiveDataAllCards.observe(this) {
            binding.cardCountAllCards.text = it ?: ""
        }

        // This is used to update the value
        AACSDK.getLiveCardCountForStreamContainer(viewModel.getAllCardsId)
            .observe(this) {
                it?.let { value ->
                    Log.d("Atomic", "All Cards count: $value")
                    binding.cardCountAllCards.text = "All Cards Count: $value"
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
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