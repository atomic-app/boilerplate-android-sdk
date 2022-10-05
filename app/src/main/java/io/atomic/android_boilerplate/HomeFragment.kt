package io.atomic.android_boilerplate

import android.os.Bundle
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

        binding.showCardsButton.setOnClickListener {
            val cardFragment = CardFragment()
            parentFragmentManager.beginTransaction()
                .add(R.id.fragment_container, cardFragment, "CARD_FRAGMENT")
                .addToBackStack(null)
                .commit()
        }

        binding.logOutButton.setOnClickListener {
            AACSDK.logout {
                requireActivity().runOnUiThread {
                    Toast.makeText(context, "Logged off", Toast.LENGTH_SHORT).show()
                }
                requireActivity().finish()
            }
        }

        // This is used to initialize the value
        viewModel.countLiveData.observe(this) {
            binding.cardCountTv.text = it ?: ""
        }

        // This is used to update the value
        AACSDK.getLiveCardCountForStreamContainer(viewModel.getStreamContainerId)
            .observe(this) {
                it?.let { value ->
                    binding.cardCountTv.text = "Card Count: $value"
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
}