package io.atomic.android_boilerplate

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atomic.actioncards.sdk.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class BoilerPlateViewModel : ViewModel() {
    companion object {
        /** Hardcoded for boiler plate...
         * In your own app you to get these:
         * Open the Atomic Workbench, and navigate to the Configuration area.
         * Under the 'SDK' header, your API host is in the 'API Host' section,
         * your API key is in the 'API Keys' section,
         * and your environment ID is at the top of the page under 'Environment ID'. */

        // Id for a stream container that shows a selected amount of cards
        const val singleCardID = "dEJe9LJa"
        // ID for a stream container that shows all cards
        const val containerId = "XjJ8XgJr"
        const val apiHost = "https://53-2.client-api.atomic.io"
        const val apiKey = "ib-development"
        const val environmentId = "zaMD3g"

        /* Hard coded for the purposes of this app. You would normally get via
         your authentication process */
        const val requestTokenStr = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMDAwIiwiaXNzIjoiSUIiLCJuYmYiOjE2ODEyNzM5ODYsImV4cCI6MTY4MTg3ODc4NiwiaWF0IjoxNjgxMjczOTg2fQ.pTshSJ0MfT0W9hGrPjXzfolAZNzfxVyAJfkFAC9CogjJWhPlZdSUhy-_bw1R6HAnRJsuAWql3rfQHYLxqnUG5kPbF-j7Sc2XJy3igXQG5pCAbrm-oThag5ehhxxtbwp7f2wzu4Uz8VytagBArRDn2XkdxLiDqmhTclY6w7fAToPS-o-8CO9e9ErQQcDaPNN4Drrn_xG87vlSpRcSlJagVQyxRWmruiT8S8g8raDm0soes-labe42ANpIKdSBIj11bIQ_PP2cvk8Hu1VsATtXDPF44DE9LxdM_1ggSNNfDVPXh10HEskBFKEQS_TCQCk5cRi6lpp2T2w-Fte0ykBtp7MhwGiNpqchen3_B-34jYMp_eC-kJ_DPkJLKrhOVijg-OqeznVeg7x2BR0UWi9CYINUWIg-YCCcMxuCvlKfngxfZqrbbochESYMgjGK5sLRUzL2UEwx3tqG0ZjYc44YnfgE200tLksbxmF2XC4HtlJGObPY2eff37xC80UI1pd8aCeO8oGXyWEqrSIxk1wbCmkAuXDNU7XBy9E5nZb8kXderb4Yg3ly233KJlsDdUNGFvbwCpz-bPpIcggSqp1CBebQk6aMAXckhifU_dmYegOUMwtm_wmt9eZoXWc9EsrwuGho3xDGBzlWGUVz48z5HwXSaqd4xF3KBgieR-CiGLA"
    }
}

