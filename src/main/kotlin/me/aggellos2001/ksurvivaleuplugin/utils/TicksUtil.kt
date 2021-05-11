package me.aggellos2001.ksurvivaleuplugin.utils

import kotlin.time.Duration

fun Duration.inTicks(): Long = (this.inWholeSeconds * 20)