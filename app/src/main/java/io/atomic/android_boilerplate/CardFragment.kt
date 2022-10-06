package io.atomic.android_boilerplate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import com.atomic.actioncards.feed.data.model.AACCardInstance
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
        viewModel.streamContainer?.start(R.id.card_container, this.childFragmentManager)
    }

    override fun onResume() {
        super.onResume()
        applyHandlers()
    }

    override fun onPause() {
        super.onPause()
        applyHandlers(true)
    }

    /** This is currently only setting runtime variables handler, but you could also setup
     * any handlers for link and submit buttons in here too */
    private fun applyHandlers(shallReset: Boolean = false){
        if (shallReset) {
            viewModel.streamContainer?.cardDidRequestRunTimeVariablesHandler = null
        }

        viewModel.streamContainer?.cardDidRequestRunTimeVariablesHandler = { cards, done ->
            cardDidRequestRunTimeVariablesHandler(cards, done)
        }
    }

    /** here is where we apply runtime variables to a card.
     * Action any you have in your cards here. */
    private fun cardDidRequestRunTimeVariablesHandler(cards: List<AACCardInstance>, done: (cardsWithResolvedVariables: List<AACCardInstance>) -> Unit) {

        for (card in cards) {
            val longDf: DateFormat = DateFormat.getDateInstance(DateFormat.LONG)
            val shortDf: DateFormat = DateFormat.getDateInstance(DateFormat.SHORT)
            val today = Calendar.getInstance().time
            val formattedLongDate = longDf.format(today)
            val formattedShortDate = shortDf.format(today)

            card.resolveVariableWithNameAndValue("dateShort", formattedShortDate)
            card.resolveVariableWithNameAndValue("dateLong", formattedLongDate)

            val userName = "A variable changed at runtime"
            card.resolveVariableWithNameAndValue("name", userName)
        }

        done(cards)
    }
}