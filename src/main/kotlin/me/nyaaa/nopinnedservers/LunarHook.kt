package me.nyaaa.nopinnedservers

import net.weavemc.loader.api.Hook
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.LdcInsnNode

class LunarHook : Hook() {
    override fun transform(cn: ClassNode, cfg: AssemblerConfig) {
        if (!cn.name.startsWith("com/moonsworth/lunar/client")) return

        if (cn.hasCst("pinnedServers", "sentryFilteredExceptions")) {
            cn.replaceCst("pinnedServers", "")
        }

        if (cn.hasCst("starServers", "sentryFilteredExceptions")) {
            cn.replaceCst("starServers", "")
        }
    }
}

fun ClassNode.hasCst(vararg cst: Any): Boolean {
    return methods.flatMap { it.instructions }
        .filterIsInstance<LdcInsnNode>()
        .map { it.cst }
        .containsAll(cst.toList())
}

fun ClassNode.replaceCst(old: Any, new: Any) {
    methods.flatMap { it.instructions }
        .filterIsInstance<LdcInsnNode>()
        .forEach { if (it.cst == old) it.cst = new }
}