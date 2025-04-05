package momoi.mod.qqpro.hook

import com.tencent.qqnt.kernel.nativeinterface.MsgRecord
import com.tencent.qqnt.msg.api.impl.MsgUtilApiImpl
import momoi.anno.mixin.Mixin
import momoi.anno.mixin.StaticHook
import moye.wearqq.MsgOperation
import java.util.*


@Mixin
object Hook : MsgOperation(){
    @StaticHook
    @JvmStatic
    fun getDetail_(msgRecord: MsgRecord?): String? {
        val elements = msgRecord?.elements
        val msgElement = elements?.get(0)
        if (msgElement?.fileElement != null){
            msgElement.fileElement?.let {
                return "[文件]\n文件名：${it.fileName}\n文件大小：${humanReadableFileSize(it.fileSize)}\n文件链接：${it.filePath}"
            }
        }
        return msgRecord?.let { MsgUtilApiImpl().getElementSummary(it) }
    }
    fun humanReadableFileSize(j: Long): String {
        if (j <= 0) {
            return "0B"
        }
        val strArr = arrayOf("B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB")
        var d2 = j.toDouble()
        var i = 0
        while (d2 >= 1024.0 && i < strArr.size - 1) {
            val d3 = 1024.0
            java.lang.Double.isNaN(d3)
            d2 /= d3
            i++
        }
        val format = String.format(
            Locale.getDefault(), "%.2f%s", *arrayOf<Any>(
                d2,
                strArr[i]
            ).copyOf(2)
        )
        return format
    }


}