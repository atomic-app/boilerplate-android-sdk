package io.atomic.android_boilerplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.atomic.actioncards.feed.data.model.AACCardInstance
import java.text.DateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BoilerPlateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[BoilerPlateViewModel::class.java]
        setContentView(R.layout.activity_main)

        viewModel.initContainer()
        viewModel.streamContainer?.start(R.id.cardsContainer, supportFragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.streamContainer?.destroy(supportFragmentManager)
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

            val userName = "My Test User"
            card.resolveVariableWithNameAndValue("name", userName)

        }

        done(cards)
    }
}