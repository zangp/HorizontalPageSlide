package com.perry.horizontal.slide.utils

import android.content.Context
import kotlin.math.roundToInt

/**
 * @CreateDate: 2022/7/10
 * @CreateTime: 09:05
 *
 * @Author : zang.peng
 * @Email : zangp_hq@163.com
 * @Version : 1.0
 */

fun Int.dp2px(context: Context): Int {
    val scale = context.resources?.displayMetrics?.density ?: 0f
    return (this * scale + 0.5f).roundToInt()
}