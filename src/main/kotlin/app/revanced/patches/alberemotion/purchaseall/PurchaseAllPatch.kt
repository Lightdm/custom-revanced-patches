package app.revanced.patches.alberemotion.purchaseall

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import com.android.tools.smali.dexlib2.AccessFlags

@Patch(
    name = "Purchase all",
    description = "This Patch sets all add-ons as purchased",
    compatiblePackages = [CompatiblePackage("de.alber.emotion_m25")]
)

object PurchaseAllPatch : BytecodePatch(emptySet()) {
    override fun execute(context: BytecodeContext) {
        context.classes.forEach { it ->
            if(it.accessFlags.and(AccessFlags.INTERFACE.value) == 0)
                if(it.methods.any{zit -> zit.name.endsWith("Purchased")})
                context.proxy(it).mutableClass.methods.forEach {  mit ->
                    if(mit.name.endsWith("Purchased") && mit.name.startsWith("is")){
                        mit.apply { addInstructions(
                        0,
                        """
                        const/4 v0, 0x1
                        return v0
                        """
                        ) }
                    }
                }
        }
    }
}