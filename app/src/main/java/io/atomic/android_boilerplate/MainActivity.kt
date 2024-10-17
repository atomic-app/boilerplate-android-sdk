package io.atomic.android_boilerplate

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.atomic.actioncards.analytics.services.AACEventName
import com.atomic.actioncards.feed.data.model.AACCardInstance
import com.atomic.actioncards.feed.data.model.Color
import com.atomic.actioncards.feed.feature.aacsdk_fragment.AACSDKCardFeedUpdateEvent
import com.atomic.actioncards.sdk.AACSDK
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import java.text.DateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BoilerPlateViewModel
    private var badge: BadgeDrawable? = null
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[BoilerPlateViewModel::class.java]
        setContentView(R.layout.activity_main)

        viewModel.initContainer()
        viewModel.streamContainer?.start(R.id.cardsContainer, supportFragmentManager)
        viewModel.streamContainer?.startUpdates()

        badge = BadgeDrawable.create(this).apply {
            isVisible = true
            backgroundColor = android.graphics.Color.RED
            number = 0
        }


        setSupportActionBar(findViewById(R.id.my_toolbar))
        toolbar = findViewById(R.id.my_toolbar)


        // get live count
        viewModel.streamContainer?.let { sc ->
            AACSDK.getLiveCardCountForStreamContainer(sc).observe(this, Observer { count ->
                badge?.number = count ?: 0
                badge?.let { b ->
                    if (b.number > 0) {
                        BadgeUtils.attachBadgeDrawable(b, toolbar, R.id.action_bell)
                    } else {
                        BadgeUtils.detachBadgeDrawable(badge, toolbar, R.id.action_bell)
                    }
                }
            })
        }

        // get a static count of total and unseen cards if required. This is not required, just an example
        AACSDK.userMetrics { metrics ->
            metrics?.let {
                if (metrics.totalCardsForStreamContainer(viewModel.streamContainerId) > 0) {
                    badge?.number = metrics.totalCardsForStreamContainer(viewModel.streamContainerId)

                    badge?.let { b ->
                        BadgeUtils.attachBadgeDrawable(b, toolbar, R.id.action_bell)
                    }
                } else {
                    BadgeUtils.detachBadgeDrawable(badge, toolbar, R.id.action_bell)
                }
            }

        }

        // observe SDK events
        AACSDK.observeSDKEvents { events ->
            if (events.eventName == AACEventName.CardFeedUpdated) {
                print("Card count ${events.cardCount}")
                // or call AACSDKUserMetrics here?
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.streamContainer?.stopUpdates()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_icons, menu)
        return super.onCreateOptionsMenu(menu)
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