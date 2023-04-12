package io.atomic.android_boilerplate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.atomic.actioncards.sdk.AACSDK
import com.atomic.actioncards.sdk.AACSingleCardView
import com.atomic.actioncards.sdk.VotingOption
import io.atomic.android_boilerplate.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentHomeBinding.inflate(layoutInflater)

        initListeners()

        AtomicClass.onCreate()
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

        AtomicClass.getMessageCenter()?.startUpdates(requireContext())

        AtomicClass.getDashboard()?.apply {
            cardVotingOptions = EnumSet.of(VotingOption.Useful, VotingOption.NotUseful)
        }?.start(R.id.single_card_view, childFragmentManager)
    }

    override fun onResume() {
        super.onResume()

        // Dashboard
        AtomicClass.getLiveCardCount(AtomicClass.dashboardID()).observe(viewLifecycleOwner) { count ->
            val countLog: String = count.toString()
            Log.d("Atomic", "dash count: $countLog")
            binding.cardCountDash.text = "Dash Cards: $count"
        }

        // Update the notification badge to show the number of cards
        AtomicClass.getLiveCardCount(AtomicClass.messageCentreID()).observe(viewLifecycleOwner) { count ->
            val countLog: String = count.toString()
            Log.d("Atomic", "msgc count: $countLog")
            binding.cardCountAllCards.text = "All Cards: $count"
        }
    }

    override fun onPause() {
        super.onPause()
        AtomicClass.onPause(childFragmentManager)
    }

    /** Initialise the button click listeners */
    private fun initListeners() {
        // CARDS BUTTON
        binding.showCardsButton.setOnClickListener {
            val direction = HomeFragmentDirections.clickCardButton()
            findNavController().navigate(direction)
        }

        // BLANK BUTTON
        binding.blankFragmentButton.setOnClickListener {
            val direction = HomeFragmentDirections.clickBlankButton()
            findNavController().navigate(direction)
        }

        // LOGOUT BUTTON
        binding.logOutButton.setOnClickListener {
            AtomicClass.endSession {
                requireActivity().runOnUiThread {
                    Toast.makeText(context, "Logged off", Toast.LENGTH_SHORT).show()
                }
                requireActivity().finish()
            }
        }
    }
}