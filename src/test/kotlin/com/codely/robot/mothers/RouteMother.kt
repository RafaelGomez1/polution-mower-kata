package com.codely.robot.mothers

import com.codely.robot.domain.Route
import com.codely.shared.robot.LocationMother
import com.google.maps.model.EncodedPolyline
import com.google.maps.model.LatLng

object RouteMother {
    fun fromPolyline(polyline: String): Route {
        return Route(decodePolyline(polyline))
    }

    fun invoke(): Route {
        return Route(listOf(LocationMother.invoke().value))
    }

    fun fromPolyline(): Route {
        return Route(decodePolyline(POLYLINE))
    }

    private fun decodePolyline(polyline: String): List<LatLng> {
        val encodedPolyline = EncodedPolyline(polyline)
        return encodedPolyline.decodePath()
    }

    private const val POLYLINE = "mpjyHx`i@VjAVKnAh@BHHX@LZR@Bj@Ml@WWc@]w@bAyAfBmCb@o@pLeQfCsDVa@@ODQR}AJ{A?{BGu\\n\" +\n" +
        "            \"AD_@FKb@MTUX]Le@^kBVcAVo@Ta@|EaFh@m@FWaA{DCo@q@mCm@cC{A_GWeA}@sGSeAcA_EOSMa\\n\" +\n" +
        "            \"@}A_GsAwFkAiEoAaFaBoEGo@]_AIWW{AQyAUyBQqAI_BFkEd@aHZcDlAyJLaBPqDDeD?mBEiA}@F]yKWqGSkI\\n\" +\n" +
        "            \"CmCIeZIuZi@_Sw@{WgAoXS{DOcAWq@KQGIFQDGn@Y`@MJEFIHyAVQVOJGHgFRJBBCCSKBcAKoACyA?m@^y\\n\" +\n" +
        "            \"VJmLJ{FGGWq@e@eBIe@Ei@?q@Bk@Hs@Le@Rk@gCuIkJcZsDwLd@g@Oe@o@mB{BgHQYq@qBQYOMSM\\n\" +\n" +
        "            \"GBUBGCYc@E_@H]DWJST?JFFHBDNBJ?LED?LBv@WfAc@@EDGNK|@e@hAa@`Bk@b@OEk@Go@IeACoA@\\n\" +\n" +
        "            \"a@PyB`@yDDc@e@K{Bi@oA_@w@]m@_@]QkBoAwC{BmAeAo@s@uAoB_AaBmAwCa@mAo@iCgAwFg@iD\\n\" +\n" +
        "            \"q@}G[uEU_GBuP@cICmA?eI?qCB{FBkCI}BOyCMiAGcAC{AN{YFqD^}FR}CNu@JcAHu@b@_E`@}DVsB^mBTsAQ\\n\" +\n" +
        "            \"KkCmAg@[YQOIOvAi@[m@e@s@g@GKCKAEJIn@g@GYGIc@ScBoAf@{A`@uAlBfAG`@"
}
