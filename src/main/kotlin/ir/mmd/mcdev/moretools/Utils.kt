package ir.mmd.mcdev.moretools

import net.minecraft.resources.Identifier

fun id(i: String): Identifier = Identifier.fromNamespaceAndPath(Constants.MOD_ID, i)