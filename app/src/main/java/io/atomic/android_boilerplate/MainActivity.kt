package io.atomic.android_boilerplate

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.atomic.actioncards.feed.data.model.AACCardInstance
import com.atomic.actioncards.feed.lib.Logger
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.text.DateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BoilerPlateViewModel

    // secondary container contained in a BottomSheet
    private val bottomSheetView by lazy { findViewById<FrameLayout>(R.id.bottomSheetView) }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private var bottomSheetVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[BoilerPlateViewModel::class.java]
        setContentView(R.layout.activity_main)

        viewModel.initContainer()
        viewModel.streamContainer?.start(R.id.cardsContainer, supportFragmentManager)

        viewModel.initSecondaryContainer()
        viewModel.secondaryContainer?.start(R.id.bottomSheetView, supportFragmentManager)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        setBottomSheetVisibility(bottomSheetVisible)

    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.streamContainer?.destroy(supportFragmentManager)
        viewModel.secondaryContainer?.destroy(supportFragmentManager)
    }

    override fun onResume() {
        super.onResume()
        applyHandlers()
    }

    override fun onPause() {
        super.onPause()
        applyHandlers(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.notif_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.notif_item -> {
                bottomSheetVisible = !bottomSheetVisible
                setBottomSheetVisibility(bottomSheetVisible)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

    private fun setBottomSheetVisibility(isVisible: Boolean) {
        val updatedState = if (isVisible) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.state = updatedState
    }
}