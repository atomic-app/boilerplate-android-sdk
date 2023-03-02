package io.atomic.android_boilerplate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import com.atomic.actioncards.feed.data.model.AACCardInstance
import com.atomic.actioncards.sdk.AACStreamContainer
import java.text.DateFormat
import java.util.*


class CardFragment : Fragment() {

    private val viewModel: BoilerPlateViewModel by activityViewModels()

    private var container: AACStreamContainer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // add custom back code here if needed. We just pop the stack in this case
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().supportFragmentManager.popBackStack()
        }

        container = AACStreamContainer(BoilerPlateViewModel.containerId)
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
        container?.start(R.id.card_container, this.childFragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()
        container?.destroy(childFragmentManager)
    }

}