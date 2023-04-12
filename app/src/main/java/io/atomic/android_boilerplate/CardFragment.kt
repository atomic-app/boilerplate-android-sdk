package io.atomic.android_boilerplate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.atomic.actioncards.feed.data.model.AACCardInstance
import com.atomic.actioncards.sdk.AACStreamContainer
import java.text.DateFormat
import java.util.*


class CardFragment : Fragment() {

    private val viewModel: BoilerPlateViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // add custom back code here if needed. We just pop the stack in this case
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().supportFragmentManager.popBackStack()
        }

        AtomicClass.onCreate()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onStart() {
        super.onStart()
        // add the stream container to our layout
        AtomicClass.getMessageCenter()?.start(R.id.card_container, this.childFragmentManager)
    }

    override fun onPause() {
        super.onPause()
        AtomicClass.onPause(childFragmentManager)
    }

}